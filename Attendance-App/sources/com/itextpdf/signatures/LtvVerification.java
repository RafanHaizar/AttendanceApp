package com.itextpdf.signatures;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfCatalog;
import com.itextpdf.kernel.pdf.PdfDeveloperExtension;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfIndirectReference;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.p026io.font.PdfEncodings;
import com.itextpdf.p026io.source.ByteBuffer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;
import org.bouncycastle.asn1.ocsp.OCSPResponse;
import org.bouncycastle.asn1.ocsp.OCSPResponseStatus;
import org.bouncycastle.asn1.ocsp.ResponseBytes;
import org.bouncycastle.cert.ocsp.OCSPResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LtvVerification {
    private Logger LOGGER;
    private PdfAcroForm acroForm;
    private PdfDocument document;
    private String securityProviderCode;
    private SignatureUtil sgnUtil;
    private boolean used;
    private Map<PdfName, ValidationData> validated;

    public enum CertificateInclusion {
        YES,
        NO
    }

    public enum CertificateOption {
        SIGNING_CERTIFICATE,
        WHOLE_CHAIN
    }

    public enum Level {
        OCSP,
        CRL,
        OCSP_CRL,
        OCSP_OPTIONAL_CRL
    }

    public LtvVerification(PdfDocument document2) {
        this.LOGGER = LoggerFactory.getLogger((Class<?>) LtvVerification.class);
        this.validated = new HashMap();
        this.used = false;
        this.securityProviderCode = null;
        this.document = document2;
        this.acroForm = PdfAcroForm.getAcroForm(document2, true);
        this.sgnUtil = new SignatureUtil(document2);
    }

    public LtvVerification(PdfDocument document2, String securityProviderCode2) {
        this(document2);
        this.securityProviderCode = securityProviderCode2;
    }

    public boolean addVerification(String signatureName, IOcspClient ocsp, ICrlClient crl, CertificateOption certOption, Level level, CertificateInclusion certInclude) throws IOException, GeneralSecurityException {
        Collection<byte[]> cims;
        String str = signatureName;
        IOcspClient iOcspClient = ocsp;
        ICrlClient iCrlClient = crl;
        Level level2 = level;
        if (!this.used) {
            PdfPKCS7 pk = this.sgnUtil.readSignatureData(str, this.securityProviderCode);
            this.LOGGER.info("Adding verification for " + str);
            Certificate[] xc = pk.getCertificates();
            X509Certificate signingCert = pk.getSigningCertificate();
            String str2 = null;
            ValidationData vd = new ValidationData();
            int k = 0;
            while (k < xc.length) {
                X509Certificate cert = (X509Certificate) xc[k];
                this.LOGGER.info("Certificate: " + cert.getSubjectDN());
                if (certOption != CertificateOption.SIGNING_CERTIFICATE || cert.equals(signingCert)) {
                    byte[] ocspEnc = null;
                    if (!(iOcspClient == null || level2 == Level.CRL || (ocspEnc = iOcspClient.getEncoded(cert, getParent(cert, xc), str2)) == null)) {
                        vd.ocsps.add(buildOCSPResponse(ocspEnc));
                        this.LOGGER.info("OCSP added");
                    }
                    if (iCrlClient != null && ((level2 == Level.CRL || level2 == Level.OCSP_CRL || (level2 == Level.OCSP_OPTIONAL_CRL && ocspEnc == null)) && (cims = iCrlClient.getEncoded(cert, str2)) != null)) {
                        for (byte[] cim : cims) {
                            boolean dup = false;
                            Iterator<byte[]> it = vd.crls.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                Iterator<byte[]> it2 = it;
                                if (Arrays.equals(it.next(), cim)) {
                                    dup = true;
                                    break;
                                }
                                it = it2;
                            }
                            if (!dup) {
                                vd.crls.add(cim);
                                this.LOGGER.info("CRL added");
                            }
                            String str3 = signatureName;
                            IOcspClient iOcspClient2 = ocsp;
                        }
                    }
                    if (certInclude == CertificateInclusion.YES) {
                        vd.certs.add(cert.getEncoded());
                    }
                } else {
                    CertificateInclusion certificateInclusion = certInclude;
                }
                k++;
                String str4 = signatureName;
                iOcspClient = ocsp;
                str2 = null;
            }
            CertificateOption certificateOption = certOption;
            CertificateInclusion certificateInclusion2 = certInclude;
            if (vd.crls.size() == 0 && vd.ocsps.size() == 0) {
                return false;
            }
            this.validated.put(getSignatureHashKey(signatureName), vd);
            return true;
        }
        CertificateOption certificateOption2 = certOption;
        CertificateInclusion certificateInclusion3 = certInclude;
        throw new IllegalStateException(PdfException.VerificationAlreadyOutput);
    }

    private X509Certificate getParent(X509Certificate cert, Certificate[] certs) {
        for (X509Certificate parent : certs) {
            if (cert.getIssuerDN().equals(parent.getSubjectDN())) {
                try {
                    cert.verify(parent.getPublicKey());
                    return parent;
                } catch (Exception e) {
                }
            }
        }
        return null;
    }

    public boolean addVerification(String signatureName, Collection<byte[]> ocsps, Collection<byte[]> crls, Collection<byte[]> certs) throws IOException, GeneralSecurityException {
        if (!this.used) {
            ValidationData vd = new ValidationData();
            if (ocsps != null) {
                for (byte[] ocsp : ocsps) {
                    vd.ocsps.add(buildOCSPResponse(ocsp));
                }
            }
            if (crls != null) {
                for (byte[] crl : crls) {
                    vd.crls.add(crl);
                }
            }
            if (certs != null) {
                for (byte[] cert : certs) {
                    vd.certs.add(cert);
                }
            }
            this.validated.put(getSignatureHashKey(signatureName), vd);
            return true;
        }
        throw new IllegalStateException(PdfException.VerificationAlreadyOutput);
    }

    private static byte[] buildOCSPResponse(byte[] basicOcspResponse) throws IOException {
        return new OCSPResp(new OCSPResponse(new OCSPResponseStatus(0), new ResponseBytes(OCSPObjectIdentifiers.id_pkix_ocsp_basic, new DEROctetString(basicOcspResponse)))).getEncoded();
    }

    private PdfName getSignatureHashKey(String signatureName) throws NoSuchAlgorithmException, IOException {
        PdfSignature sig = this.sgnUtil.getSignature(signatureName);
        byte[] bc = PdfEncodings.convertToBytes(sig.getContents().getValue(), (String) null);
        if (PdfName.ETSI_RFC3161.equals(sig.getSubFilter())) {
            bc = new ASN1InputStream((InputStream) new ByteArrayInputStream(bc)).readObject().getEncoded();
        }
        return new PdfName(convertToHex(hashBytesSha1(bc)));
    }

    private static byte[] hashBytesSha1(byte[] b) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("SHA1").digest(b);
    }

    public void merge() {
        if (!this.used && this.validated.size() != 0) {
            this.used = true;
            if (((PdfDictionary) this.document.getCatalog().getPdfObject()).get(PdfName.DSS) == null) {
                createDss();
            } else {
                updateDss();
            }
        }
    }

    private void updateDss() {
        PdfArray ocsps;
        PdfArray crls;
        PdfArray certs;
        PdfDictionary vrim;
        PdfDictionary vri;
        PdfDictionary catalog = (PdfDictionary) this.document.getCatalog().getPdfObject();
        catalog.setModified();
        PdfDictionary dss = catalog.getAsDictionary(PdfName.DSS);
        PdfArray ocsps2 = dss.getAsArray(PdfName.OCSPs);
        PdfArray crls2 = dss.getAsArray(PdfName.CRLs);
        PdfArray certs2 = dss.getAsArray(PdfName.Certs);
        dss.remove(PdfName.OCSPs);
        dss.remove(PdfName.CRLs);
        dss.remove(PdfName.Certs);
        PdfDictionary vrim2 = dss.getAsDictionary(PdfName.VRI);
        if (vrim2 != null) {
            for (PdfName n : vrim2.keySet()) {
                if (this.validated.containsKey(n) && (vri = vrim2.getAsDictionary(n)) != null) {
                    deleteOldReferences(ocsps2, vri.getAsArray(PdfName.OCSP));
                    deleteOldReferences(crls2, vri.getAsArray(PdfName.CRL));
                    deleteOldReferences(certs2, vri.getAsArray(PdfName.Cert));
                }
            }
        }
        if (ocsps2 == null) {
            ocsps = new PdfArray();
        } else {
            ocsps = ocsps2;
        }
        if (crls2 == null) {
            crls = new PdfArray();
        } else {
            crls = crls2;
        }
        if (certs2 == null) {
            certs = new PdfArray();
        } else {
            certs = certs2;
        }
        if (vrim2 == null) {
            vrim = new PdfDictionary();
        } else {
            vrim = vrim2;
        }
        outputDss(dss, vrim, ocsps, crls, certs);
    }

    private static void deleteOldReferences(PdfArray all, PdfArray toDelete) {
        if (all != null && toDelete != null) {
            Iterator<PdfObject> it = toDelete.iterator();
            while (it.hasNext()) {
                PdfIndirectReference pir = it.next().getIndirectReference();
                if (pir != null) {
                    int k = 0;
                    while (k < all.size()) {
                        PdfIndirectReference pod = all.get(k).getIndirectReference();
                        if (pod != null && pir.getObjNumber() == pod.getObjNumber()) {
                            all.remove(k);
                            k--;
                        }
                        k++;
                    }
                }
            }
        }
    }

    private void createDss() {
        outputDss(new PdfDictionary(), new PdfDictionary(), new PdfArray(), new PdfArray(), new PdfArray());
    }

    private void outputDss(PdfDictionary dss, PdfDictionary vrim, PdfArray ocsps, PdfArray crls, PdfArray certs) {
        PdfDictionary pdfDictionary = dss;
        PdfDictionary pdfDictionary2 = vrim;
        PdfArray pdfArray = ocsps;
        PdfArray pdfArray2 = crls;
        PdfArray pdfArray3 = certs;
        PdfCatalog catalog = this.document.getCatalog();
        if (this.document.getPdfVersion().compareTo(PdfVersion.PDF_2_0) < 0) {
            catalog.addDeveloperExtension(PdfDeveloperExtension.ESIC_1_7_EXTENSIONLEVEL5);
        }
        Iterator<PdfName> it = this.validated.keySet().iterator();
        while (it.hasNext()) {
            PdfName vkey = it.next();
            PdfArray ocsp = new PdfArray();
            PdfArray crl = new PdfArray();
            PdfArray cert = new PdfArray();
            PdfDictionary vri = new PdfDictionary();
            for (byte[] b : this.validated.get(vkey).crls) {
                PdfStream ps = new PdfStream(b);
                ps.setCompressionLevel(-1);
                ps.makeIndirect(this.document);
                crl.add(ps);
                pdfArray2.add(ps);
                crls.setModified();
                it = it;
            }
            Iterator<PdfName> it2 = it;
            for (byte[] b2 : this.validated.get(vkey).ocsps) {
                PdfStream ps2 = new PdfStream(b2);
                ps2.setCompressionLevel(-1);
                ocsp.add(ps2);
                pdfArray.add(ps2);
                ocsps.setModified();
            }
            for (byte[] b3 : this.validated.get(vkey).certs) {
                PdfStream ps3 = new PdfStream(b3);
                ps3.setCompressionLevel(-1);
                ps3.makeIndirect(this.document);
                cert.add(ps3);
                pdfArray3.add(ps3);
                certs.setModified();
            }
            if (ocsp.size() > 0) {
                ocsp.makeIndirect(this.document);
                vri.put(PdfName.OCSP, ocsp);
            }
            if (crl.size() > 0) {
                crl.makeIndirect(this.document);
                vri.put(PdfName.CRL, crl);
            }
            if (cert.size() > 0) {
                cert.makeIndirect(this.document);
                vri.put(PdfName.Cert, cert);
            }
            vri.makeIndirect(this.document);
            pdfDictionary2.put(vkey, vri);
            it = it2;
        }
        pdfDictionary2.makeIndirect(this.document);
        vrim.setModified();
        pdfDictionary.put(PdfName.VRI, pdfDictionary2);
        if (ocsps.size() > 0) {
            pdfArray.makeIndirect(this.document);
            pdfDictionary.put(PdfName.OCSPs, pdfArray);
        }
        if (crls.size() > 0) {
            pdfArray2.makeIndirect(this.document);
            pdfDictionary.put(PdfName.CRLs, pdfArray2);
        }
        if (certs.size() > 0) {
            pdfArray3.makeIndirect(this.document);
            pdfDictionary.put(PdfName.Certs, pdfArray3);
        }
        pdfDictionary.makeIndirect(this.document);
        dss.setModified();
        catalog.put(PdfName.DSS, pdfDictionary);
    }

    private static class ValidationData {
        public List<byte[]> certs;
        public List<byte[]> crls;
        public List<byte[]> ocsps;

        private ValidationData() {
            this.crls = new ArrayList();
            this.ocsps = new ArrayList();
            this.certs = new ArrayList();
        }
    }

    public static String convertToHex(byte[] bytes) {
        ByteBuffer buf = new ByteBuffer();
        for (byte b : bytes) {
            buf.appendHex(b);
        }
        return PdfEncodings.convertToString(buf.toByteArray(), (String) null).toUpperCase();
    }
}
