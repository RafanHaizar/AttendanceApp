package com.itextpdf.signatures;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.kernel.counter.event.IMetaInfo;
import com.itextpdf.kernel.pdf.DocumentProperties;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.p026io.util.DateTimeUtil;
import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.signatures.LtvVerification;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.Certificate;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.bouncycastle.cert.ocsp.BasicOCSPResp;
import org.bouncycastle.cert.ocsp.OCSPException;
import org.bouncycastle.cert.ocsp.OCSPResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LtvVerifier extends RootStoreVerifier {
    protected static final Logger LOGGER = LoggerFactory.getLogger((Class<?>) LtvVerifier.class);
    protected PdfAcroForm acroForm;
    protected PdfDocument document;
    protected PdfDictionary dss;
    protected boolean latestRevision = true;
    protected IMetaInfo metaInfo;
    protected LtvVerification.CertificateOption option = LtvVerification.CertificateOption.SIGNING_CERTIFICATE;
    protected PdfPKCS7 pkcs7;
    protected String securityProviderCode = null;
    private SignatureUtil sgnUtil;
    protected Date signDate;
    protected String signatureName;
    protected boolean verifyRootCertificate = true;

    public LtvVerifier(PdfDocument document2) throws GeneralSecurityException {
        super((CertificateVerifier) null);
        initLtvVerifier(document2);
    }

    public LtvVerifier(PdfDocument document2, String securityProviderCode2) throws GeneralSecurityException {
        super((CertificateVerifier) null);
        this.securityProviderCode = securityProviderCode2;
        initLtvVerifier(document2);
    }

    public void setVerifier(CertificateVerifier verifier) {
        this.verifier = verifier;
    }

    public void setCertificateOption(LtvVerification.CertificateOption option2) {
        this.option = option2;
    }

    public void setVerifyRootCertificate(boolean verifyRootCertificate2) {
        this.verifyRootCertificate = verifyRootCertificate2;
    }

    public void setEventCountingMetaInfo(IMetaInfo metaInfo2) {
        this.metaInfo = metaInfo2;
    }

    public List<VerificationOK> verify(List<VerificationOK> result) throws IOException, GeneralSecurityException {
        if (result == null) {
            result = new ArrayList<>();
        }
        while (this.pkcs7 != null) {
            result.addAll(verifySignature());
        }
        return result;
    }

    public List<VerificationOK> verifySignature() throws GeneralSecurityException, IOException {
        LOGGER.info("Verifying signature.");
        List<VerificationOK> result = new ArrayList<>();
        Certificate[] chain = this.pkcs7.getSignCertificateChain();
        verifyChain(chain);
        int total = 1;
        if (LtvVerification.CertificateOption.WHOLE_CHAIN.equals(this.option)) {
            total = chain.length;
        }
        int i = 0;
        while (i < total) {
            int i2 = i + 1;
            X509Certificate signCert = (X509Certificate) chain[i];
            X509Certificate issuerCert = null;
            if (i2 < chain.length) {
                issuerCert = (X509Certificate) chain[i2];
            }
            LOGGER.info(signCert.getSubjectDN().getName());
            List<VerificationOK> list = verify(signCert, issuerCert, this.signDate);
            if (list.size() == 0) {
                try {
                    signCert.verify(signCert.getPublicKey());
                    if (this.latestRevision && chain.length > 1) {
                        list.add(new VerificationOK(signCert, getClass(), "Root certificate in final revision"));
                    }
                    if (list.size() == 0) {
                        if (this.verifyRootCertificate) {
                            throw new GeneralSecurityException();
                        }
                    }
                    if (chain.length > 1) {
                        list.add(new VerificationOK(signCert, getClass(), "Root certificate passed without checking"));
                    }
                } catch (GeneralSecurityException e) {
                    throw new VerificationException(signCert, "Couldn't verify with CRL or OCSP or trusted anchor");
                }
            }
            result.addAll(list);
            i = i2;
        }
        switchToPreviousRevision();
        return result;
    }

    public void verifyChain(Certificate[] chain) throws GeneralSecurityException {
        for (int i = 0; i < chain.length; i++) {
            chain[i].checkValidity(this.signDate);
            if (i > 0) {
                chain[i - 1].verify(chain[i].getPublicKey());
            }
        }
        LOGGER.info("All certificates are valid on " + this.signDate.toString());
    }

    public List<VerificationOK> verify(X509Certificate signCert, X509Certificate issuerCert, Date signDate2) throws GeneralSecurityException, IOException {
        RootStoreVerifier rootStoreVerifier = new RootStoreVerifier(this.verifier);
        rootStoreVerifier.setRootStore(this.rootStore);
        CRLVerifier crlVerifier = new CRLVerifier(rootStoreVerifier, getCRLsFromDSS());
        crlVerifier.setRootStore(this.rootStore);
        boolean z = false;
        crlVerifier.setOnlineCheckingAllowed(this.latestRevision || this.onlineCheckingAllowed);
        OCSPVerifier ocspVerifier = new OCSPVerifier(crlVerifier, getOCSPResponsesFromDSS());
        ocspVerifier.setRootStore(this.rootStore);
        if (this.latestRevision || this.onlineCheckingAllowed) {
            z = true;
        }
        ocspVerifier.setOnlineCheckingAllowed(z);
        return ocspVerifier.verify(signCert, issuerCert, signDate2);
    }

    public void switchToPreviousRevision() throws IOException, GeneralSecurityException {
        Logger logger = LOGGER;
        logger.info("Switching to previous revision.");
        this.latestRevision = false;
        this.dss = ((PdfDictionary) this.document.getCatalog().getPdfObject()).getAsDictionary(PdfName.DSS);
        Calendar cal = this.pkcs7.getTimeStampDate();
        if (cal == TimestampConstants.UNDEFINED_TIMESTAMP_DATE) {
            cal = this.pkcs7.getSignDate();
        }
        this.signDate = cal.getTime();
        List<String> names = this.sgnUtil.getSignatureNames();
        if (names.size() > 1) {
            this.signatureName = names.get(names.size() - 2);
            PdfDocument pdfDocument = new PdfDocument(new PdfReader(this.sgnUtil.extractRevision(this.signatureName)), new DocumentProperties().setEventCountingMetaInfo(this.metaInfo));
            this.document = pdfDocument;
            this.acroForm = PdfAcroForm.getAcroForm(pdfDocument, true);
            SignatureUtil signatureUtil = new SignatureUtil(this.document);
            this.sgnUtil = signatureUtil;
            List<String> names2 = signatureUtil.getSignatureNames();
            this.signatureName = names2.get(names2.size() - 1);
            PdfPKCS7 coversWholeDocument = coversWholeDocument();
            this.pkcs7 = coversWholeDocument;
            Object[] objArr = new Object[2];
            objArr[0] = coversWholeDocument.isTsp() ? "document-level timestamp " : "";
            objArr[1] = this.signatureName;
            logger.info(MessageFormatUtil.format("Checking {0}signature {1}", objArr));
            return;
        }
        logger.info("No signatures in revision");
        this.pkcs7 = null;
    }

    public List<X509CRL> getCRLsFromDSS() throws GeneralSecurityException, IOException {
        PdfArray crlarray;
        List<X509CRL> crls = new ArrayList<>();
        PdfDictionary pdfDictionary = this.dss;
        if (pdfDictionary == null || (crlarray = pdfDictionary.getAsArray(PdfName.CRLs)) == null) {
            return crls;
        }
        for (int i = 0; i < crlarray.size(); i++) {
            crls.add((X509CRL) SignUtils.parseCrlFromStream(new ByteArrayInputStream(crlarray.getAsStream(i).getBytes())));
        }
        return crls;
    }

    public List<BasicOCSPResp> getOCSPResponsesFromDSS() throws IOException, GeneralSecurityException {
        PdfArray ocsparray;
        List<BasicOCSPResp> ocsps = new ArrayList<>();
        PdfDictionary pdfDictionary = this.dss;
        if (pdfDictionary == null || (ocsparray = pdfDictionary.getAsArray(PdfName.OCSPs)) == null) {
            return ocsps;
        }
        for (int i = 0; i < ocsparray.size(); i++) {
            OCSPResp ocspResponse = new OCSPResp(ocsparray.getAsStream(i).getBytes());
            if (ocspResponse.getStatus() == 0) {
                try {
                    ocsps.add((BasicOCSPResp) ocspResponse.getResponseObject());
                } catch (OCSPException e) {
                    throw new GeneralSecurityException(e.toString());
                }
            }
        }
        return ocsps;
    }

    /* access modifiers changed from: protected */
    public void initLtvVerifier(PdfDocument document2) throws GeneralSecurityException {
        this.document = document2;
        this.acroForm = PdfAcroForm.getAcroForm(document2, true);
        SignatureUtil signatureUtil = new SignatureUtil(document2);
        this.sgnUtil = signatureUtil;
        List<String> names = signatureUtil.getSignatureNames();
        this.signatureName = names.get(names.size() - 1);
        this.signDate = DateTimeUtil.getCurrentTimeDate();
        PdfPKCS7 coversWholeDocument = coversWholeDocument();
        this.pkcs7 = coversWholeDocument;
        Logger logger = LOGGER;
        Object[] objArr = new Object[2];
        objArr[0] = coversWholeDocument.isTsp() ? "document-level timestamp " : "";
        objArr[1] = this.signatureName;
        logger.info(MessageFormatUtil.format("Checking {0}signature {1}", objArr));
    }

    /* access modifiers changed from: protected */
    public PdfPKCS7 coversWholeDocument() throws GeneralSecurityException {
        PdfPKCS7 pkcs72 = this.sgnUtil.readSignatureData(this.signatureName, this.securityProviderCode);
        if (this.sgnUtil.signatureCoversWholeDocument(this.signatureName)) {
            Logger logger = LOGGER;
            logger.info("The timestamp covers whole document.");
            if (pkcs72.verifySignatureIntegrityAndAuthenticity()) {
                logger.info("The signed document has not been modified.");
                return pkcs72;
            }
            Certificate certificate = null;
            throw new VerificationException((Certificate) null, "The document was altered after the final signature was applied.");
        }
        Certificate certificate2 = null;
        throw new VerificationException((Certificate) null, "Signature doesn't cover whole document.");
    }
}
