package com.itextpdf.signatures;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.PdfSigFieldLock;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfSignatureFormField;
import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDate;
import com.itextpdf.kernel.pdf.PdfDeveloperExtension;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfLiteral;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfOutputStream;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.StampingProperties;
import com.itextpdf.kernel.pdf.annot.PdfWidgetAnnotation;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.source.ByteBuffer;
import com.itextpdf.p026io.source.IRandomAccessSource;
import com.itextpdf.p026io.source.RASInputStream;
import com.itextpdf.p026io.source.RandomAccessSourceFactory;
import com.itextpdf.p026io.util.DateTimeUtil;
import com.itextpdf.p026io.util.FileUtil;
import com.itextpdf.p026io.util.StreamUtil;
import com.itextpdf.pdfa.PdfADocument;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bouncycastle.asn1.esf.SignaturePolicyIdentifier;
import org.bouncycastle.crypto.tls.CipherSuite;
import org.slf4j.LoggerFactory;

public class PdfSigner {
    public static final int CERTIFIED_FORM_FILLING = 2;
    public static final int CERTIFIED_FORM_FILLING_AND_ANNOTATIONS = 3;
    public static final int CERTIFIED_NO_CHANGES_ALLOWED = 1;
    public static final int NOT_CERTIFIED = 0;
    protected PdfSignatureAppearance appearance;
    protected byte[] bout;
    protected int certificationLevel;
    protected boolean closed;
    protected PdfSignature cryptoDictionary;
    private PdfName digestMethod;
    protected PdfDocument document;
    protected Map<PdfName, PdfLiteral> exclusionLocations;
    protected PdfSigFieldLock fieldLock;
    protected String fieldName;
    protected OutputStream originalOS;
    protected boolean preClosed;
    protected RandomAccessFile raf;
    protected long[] range;
    protected Calendar signDate;
    protected ISignatureEvent signatureEvent;
    protected File tempFile;
    protected ByteArrayOutputStream temporaryOS;

    public enum CryptoStandard {
        CMS,
        CADES
    }

    public interface ISignatureEvent {
        void getSignatureDictionary(PdfSignature pdfSignature);
    }

    @Deprecated
    public PdfSigner(PdfReader reader, OutputStream outputStream, boolean append) throws IOException {
        this(reader, outputStream, (String) null, append);
    }

    @Deprecated
    public PdfSigner(PdfReader reader, OutputStream outputStream, String path, boolean append) throws IOException {
        this(reader, outputStream, path, initStampingProperties(append));
    }

    public PdfSigner(PdfReader reader, OutputStream outputStream, StampingProperties properties) throws IOException {
        this(reader, outputStream, (String) null, properties);
    }

    public PdfSigner(PdfReader reader, OutputStream outputStream, String path, StampingProperties properties) throws IOException {
        this.certificationLevel = 0;
        this.preClosed = false;
        StampingProperties localProps = new StampingProperties(properties).preserveEncryption();
        if (path == null) {
            this.temporaryOS = new ByteArrayOutputStream();
            this.document = initDocument(reader, new PdfWriter((OutputStream) this.temporaryOS), localProps);
        } else {
            this.tempFile = FileUtil.createTempFile(path);
            this.document = initDocument(reader, new PdfWriter((OutputStream) FileUtil.getFileOutputStream(this.tempFile)), localProps);
        }
        this.originalOS = outputStream;
        this.signDate = DateTimeUtil.getCurrentTimeCalendar();
        this.fieldName = getNewSigFieldName();
        PdfSignatureAppearance pdfSignatureAppearance = new PdfSignatureAppearance(this.document, new Rectangle(0.0f, 0.0f), 1);
        this.appearance = pdfSignatureAppearance;
        pdfSignatureAppearance.setSignDate(this.signDate);
        this.closed = false;
    }

    /* access modifiers changed from: protected */
    public PdfDocument initDocument(PdfReader reader, PdfWriter writer, StampingProperties properties) {
        if (reader.getPdfAConformanceLevel() == null) {
            return new PdfDocument(reader, writer, properties);
        }
        return new PdfADocument(reader, writer, properties);
    }

    public Calendar getSignDate() {
        return this.signDate;
    }

    public void setSignDate(Calendar signDate2) {
        this.signDate = signDate2;
        this.appearance.setSignDate(signDate2);
    }

    public PdfSignatureAppearance getSignatureAppearance() {
        return this.appearance;
    }

    public int getCertificationLevel() {
        return this.certificationLevel;
    }

    public void setCertificationLevel(int certificationLevel2) {
        this.certificationLevel = certificationLevel2;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public PdfSignature getSignatureDictionary() {
        return this.cryptoDictionary;
    }

    public ISignatureEvent getSignatureEvent() {
        return this.signatureEvent;
    }

    public void setSignatureEvent(ISignatureEvent signatureEvent2) {
        this.signatureEvent = signatureEvent2;
    }

    public String getNewSigFieldName() {
        int step = 1;
        while (PdfAcroForm.getAcroForm(this.document, true).getField("Signature" + step) != null) {
            step++;
        }
        return "Signature" + step;
    }

    public void setFieldName(String fieldName2) {
        if (fieldName2 == null) {
            return;
        }
        if (fieldName2.indexOf(46) < 0) {
            PdfAcroForm acroForm = PdfAcroForm.getAcroForm(this.document, true);
            if (acroForm.getField(fieldName2) != null) {
                PdfFormField field = acroForm.getField(fieldName2);
                if (!PdfName.Sig.equals(field.getFormType())) {
                    throw new IllegalArgumentException(PdfException.FieldTypeIsNotASignatureFieldType);
                } else if (field.getValue() == null) {
                    this.appearance.setFieldName(fieldName2);
                    List<PdfWidgetAnnotation> widgets = field.getWidgets();
                    if (widgets.size() > 0) {
                        PdfWidgetAnnotation widget = widgets.get(0);
                        this.appearance.setPageRect(getWidgetRectangle(widget));
                        this.appearance.setPageNumber(getWidgetPageNumber(widget));
                    }
                } else {
                    throw new IllegalArgumentException(PdfException.FieldAlreadySigned);
                }
            }
            this.fieldName = fieldName2;
            return;
        }
        throw new IllegalArgumentException(PdfException.FieldNamesCannotContainADot);
    }

    public PdfDocument getDocument() {
        return this.document;
    }

    /* access modifiers changed from: protected */
    public void setDocument(PdfDocument document2) {
        this.document = document2;
    }

    public void setOriginalOutputStream(OutputStream originalOS2) {
        this.originalOS = originalOS2;
    }

    public PdfSigFieldLock getFieldLockDict() {
        return this.fieldLock;
    }

    public void setFieldLockDict(PdfSigFieldLock fieldLock2) {
        this.fieldLock = fieldLock2;
    }

    public void signDetached(IExternalDigest externalDigest, IExternalSignature externalSignature, Certificate[] chain, Collection<ICrlClient> crlList, IOcspClient ocspClient, ITSAClient tsaClient, int estimatedSize, CryptoStandard sigtype) throws IOException, GeneralSecurityException {
        SignaturePolicyIdentifier signaturePolicyIdentifier = null;
        signDetached(externalDigest, externalSignature, chain, crlList, ocspClient, tsaClient, estimatedSize, sigtype, (SignaturePolicyIdentifier) null);
    }

    public void signDetached(IExternalDigest externalDigest, IExternalSignature externalSignature, Certificate[] chain, Collection<ICrlClient> crlList, IOcspClient ocspClient, ITSAClient tsaClient, int estimatedSize, CryptoStandard sigtype, SignaturePolicyInfo signaturePolicy) throws IOException, GeneralSecurityException {
        signDetached(externalDigest, externalSignature, chain, crlList, ocspClient, tsaClient, estimatedSize, sigtype, signaturePolicy.toSignaturePolicyIdentifier());
    }

    public void signDetached(IExternalDigest externalDigest, IExternalSignature externalSignature, Certificate[] chain, Collection<ICrlClient> crlList, IOcspClient ocspClient, ITSAClient tsaClient, int estimatedSize, CryptoStandard sigtype, SignaturePolicyIdentifier signaturePolicy) throws IOException, GeneralSecurityException {
        int estimatedSize2;
        Certificate[] certificateArr = chain;
        IOcspClient iOcspClient = ocspClient;
        CryptoStandard cryptoStandard = sigtype;
        SignaturePolicyIdentifier signaturePolicyIdentifier = signaturePolicy;
        if (this.closed) {
            Collection<ICrlClient> collection = crlList;
            throw new PdfException(PdfException.ThisInstanceOfPdfSignerAlreadyClosed);
        } else if (this.certificationLevel <= 0 || !isDocumentPdf2() || !documentContainsCertificationOrApprovalSignatures()) {
            Collection<byte[]> crlBytes = null;
            int i = 0;
            while (crlBytes == null && i < certificateArr.length) {
                crlBytes = processCrl(certificateArr[i], crlList);
                i++;
            }
            Collection<ICrlClient> collection2 = crlList;
            if (estimatedSize == 0) {
                int estimatedSize3 = 8192;
                if (crlBytes != null) {
                    for (byte[] element : crlBytes) {
                        estimatedSize3 += element.length + 10;
                    }
                }
                if (iOcspClient != null) {
                    estimatedSize3 += 4192;
                }
                if (tsaClient != null) {
                    estimatedSize2 = estimatedSize3 + 4192;
                } else {
                    estimatedSize2 = estimatedSize3;
                }
            } else {
                estimatedSize2 = estimatedSize;
            }
            PdfSignatureAppearance appearance2 = getSignatureAppearance();
            appearance2.setCertificate(certificateArr[0]);
            if (cryptoStandard == CryptoStandard.CADES && !isDocumentPdf2()) {
                addDeveloperExtension(PdfDeveloperExtension.ESIC_1_7_EXTENSIONLEVEL2);
            }
            String hashAlgorithm = externalSignature.getHashAlgorithm();
            PdfSignature dic = new PdfSignature(PdfName.Adobe_PPKLite, cryptoStandard == CryptoStandard.CADES ? PdfName.ETSI_CAdES_DETACHED : PdfName.Adbe_pkcs7_detached);
            dic.setReason(appearance2.getReason());
            dic.setLocation(appearance2.getLocation());
            dic.setSignatureCreator(appearance2.getSignatureCreator());
            dic.setContact(appearance2.getContact());
            dic.setDate(new PdfDate(getSignDate()));
            this.cryptoDictionary = dic;
            this.digestMethod = getHashAlgorithmNameInCompatibleForPdfForm(hashAlgorithm);
            HashMap hashMap = new HashMap();
            hashMap.put(PdfName.Contents, Integer.valueOf((estimatedSize2 * 2) + 2));
            preClose(hashMap);
            PrivateKey privateKey = null;
            HashMap hashMap2 = hashMap;
            PdfSignature pdfSignature = dic;
            String hashAlgorithm2 = hashAlgorithm;
            PdfSignatureAppearance pdfSignatureAppearance = appearance2;
            PdfPKCS7 sgn = new PdfPKCS7((PrivateKey) null, chain, hashAlgorithm, (String) null, externalDigest, false);
            if (signaturePolicyIdentifier != null) {
                sgn.setSignaturePolicy(signaturePolicyIdentifier);
            }
            InputStream data = getRangeStream();
            byte[] hash = DigestAlgorithms.digest(data, SignUtils.getMessageDigest(hashAlgorithm2, externalDigest));
            List<byte[]> ocspList = new ArrayList<>();
            if (certificateArr.length <= 1 || iOcspClient == null) {
            } else {
                int j = 0;
                for (int i2 = 1; j < certificateArr.length - i2; i2 = 1) {
                    InputStream data2 = data;
                    byte[] ocsp = iOcspClient.getEncoded((X509Certificate) certificateArr[j], (X509Certificate) certificateArr[j + 1], (String) null);
                    if (ocsp != null) {
                        ocspList.add(ocsp);
                    }
                    j++;
                    IExternalDigest iExternalDigest = externalDigest;
                    data = data2;
                }
            }
            byte[] sh = sgn.getAuthenticatedAttributeBytes(hash, cryptoStandard, (Collection<byte[]>) ocspList, crlBytes);
            byte[] extSignature = externalSignature.sign(sh);
            sgn.setExternalDigest(extSignature, (byte[]) null, externalSignature.getEncryptionAlgorithm());
            byte[] bArr = hash;
            PdfPKCS7 pdfPKCS7 = sgn;
            String str = hashAlgorithm2;
            byte[] bArr2 = extSignature;
            byte[] bArr3 = sh;
            byte[] encodedSig = sgn.getEncodedPKCS7(hash, sigtype, tsaClient, (Collection<byte[]>) ocspList, crlBytes);
            if (estimatedSize2 >= encodedSig.length) {
                byte[] paddedSig = new byte[estimatedSize2];
                System.arraycopy(encodedSig, 0, paddedSig, 0, encodedSig.length);
                PdfDictionary dic2 = new PdfDictionary();
                dic2.put(PdfName.Contents, new PdfString(paddedSig).setHexWriting(true));
                close(dic2);
                this.closed = true;
                return;
            }
            throw new IOException("Not enough space");
        } else {
            throw new PdfException(PdfException.CertificationSignatureCreationFailedDocShallNotContainSigs);
        }
    }

    public void signExternalContainer(IExternalSignatureContainer externalSignatureContainer, int estimatedSize) throws GeneralSecurityException, IOException {
        if (!this.closed) {
            PdfSignature dic = new PdfSignature();
            PdfSignatureAppearance appearance2 = getSignatureAppearance();
            dic.setReason(appearance2.getReason());
            dic.setLocation(appearance2.getLocation());
            dic.setSignatureCreator(appearance2.getSignatureCreator());
            dic.setContact(appearance2.getContact());
            dic.setDate(new PdfDate(getSignDate()));
            externalSignatureContainer.modifySigningDictionary((PdfDictionary) dic.getPdfObject());
            this.cryptoDictionary = dic;
            Map<PdfName, Integer> exc = new HashMap<>();
            exc.put(PdfName.Contents, Integer.valueOf((estimatedSize * 2) + 2));
            preClose(exc);
            byte[] encodedSig = externalSignatureContainer.sign(getRangeStream());
            if (estimatedSize >= encodedSig.length) {
                byte[] paddedSig = new byte[estimatedSize];
                System.arraycopy(encodedSig, 0, paddedSig, 0, encodedSig.length);
                PdfDictionary dic2 = new PdfDictionary();
                dic2.put(PdfName.Contents, new PdfString(paddedSig).setHexWriting(true));
                close(dic2);
                this.closed = true;
                return;
            }
            throw new IOException("Not enough space");
        }
        throw new PdfException(PdfException.ThisInstanceOfPdfSignerAlreadyClosed);
    }

    public void timestamp(ITSAClient tsa, String signatureName) throws IOException, GeneralSecurityException {
        if (!this.closed) {
            int contentEstimated = tsa.getTokenSizeEstimate();
            if (!isDocumentPdf2()) {
                addDeveloperExtension(PdfDeveloperExtension.ESIC_1_7_EXTENSIONLEVEL5);
            }
            setFieldName(signatureName);
            PdfSignature dic = new PdfSignature(PdfName.Adobe_PPKLite, PdfName.ETSI_RFC3161);
            dic.put(PdfName.Type, PdfName.DocTimeStamp);
            this.cryptoDictionary = dic;
            Map<PdfName, Integer> exc = new HashMap<>();
            exc.put(PdfName.Contents, Integer.valueOf((contentEstimated * 2) + 2));
            preClose(exc);
            InputStream data = getRangeStream();
            MessageDigest messageDigest = tsa.getMessageDigest();
            byte[] buf = new byte[4096];
            while (true) {
                int read = data.read(buf);
                int n = read;
                if (read <= 0) {
                    break;
                }
                messageDigest.update(buf, 0, n);
            }
            try {
                byte[] tsToken = tsa.getTimeStampToken(messageDigest.digest());
                if (contentEstimated + 2 >= tsToken.length) {
                    byte[] paddedSig = new byte[contentEstimated];
                    System.arraycopy(tsToken, 0, paddedSig, 0, tsToken.length);
                    PdfDictionary dic2 = new PdfDictionary();
                    byte[] bArr = tsToken;
                    dic2.put(PdfName.Contents, new PdfString(paddedSig).setHexWriting(true));
                    close(dic2);
                    this.closed = true;
                    return;
                }
                throw new IOException("Not enough space");
            } catch (Exception e) {
                Exception e2 = e;
                throw new GeneralSecurityException(e2.getMessage(), e2);
            }
        } else {
            ITSAClient iTSAClient = tsa;
            String str = signatureName;
            throw new PdfException(PdfException.ThisInstanceOfPdfSignerAlreadyClosed);
        }
    }

    public static void signDeferred(PdfDocument document2, String fieldName2, OutputStream outs, IExternalSignatureContainer externalSignatureContainer) throws IOException, GeneralSecurityException {
        String str = fieldName2;
        SignatureUtil signatureUtil = new SignatureUtil(document2);
        PdfSignature signature = signatureUtil.getSignature(str);
        if (signature == null) {
            throw new PdfException(PdfException.ThereIsNoFieldInTheDocumentWithSuchName1).setMessageParams(str);
        } else if (signatureUtil.signatureCoversWholeDocument(str)) {
            PdfArray b = signature.getByteRange();
            long[] gaps = b.toLongArray();
            if (b.size() == 4 && gaps[0] == 0) {
                IRandomAccessSource readerSource = document2.getReader().getSafeFile().createSourceView();
                byte[] signedContent = externalSignatureContainer.sign(new RASInputStream(new RandomAccessSourceFactory().createRanged(readerSource, gaps)));
                int spaceAvailable = ((int) (gaps[2] - gaps[1])) - 2;
                if ((spaceAvailable & 1) == 0) {
                    int spaceAvailable2 = spaceAvailable / 2;
                    if (spaceAvailable2 >= signedContent.length) {
                        byte[] signedContent2 = signedContent;
                        int spaceAvailable3 = spaceAvailable2;
                        StreamUtil.copyBytes(readerSource, 0, gaps[1] + 1, outs);
                        ByteBuffer bb = new ByteBuffer(spaceAvailable3 * 2);
                        for (byte bi : signedContent2) {
                            bb.appendHex(bi);
                        }
                        int remain = (spaceAvailable3 - signedContent2.length) * 2;
                        for (int k = 0; k < remain; k++) {
                            bb.append((byte) 48);
                        }
                        byte[] bbArr = bb.toByteArray();
                        outs.write(bbArr);
                        byte[] bArr = bbArr;
                        ByteBuffer byteBuffer = bb;
                        StreamUtil.copyBytes(readerSource, gaps[2] - 1, gaps[3] + 1, outs);
                        return;
                    }
                    throw new PdfException(PdfException.AvailableSpaceIsNotEnoughForSignature);
                }
                throw new IllegalArgumentException("Gap is not a multiple of 2");
            }
            throw new IllegalArgumentException("Single exclusion space supported");
        } else {
            throw new PdfException(PdfException.SignatureWithName1IsNotTheLastItDoesntCoverWholeDocument).setMessageParams(str);
        }
    }

    /* access modifiers changed from: protected */
    public Collection<byte[]> processCrl(Certificate cert, Collection<ICrlClient> crlList) {
        Collection<byte[]> b;
        if (crlList == null) {
            return null;
        }
        List<byte[]> crlBytes = new ArrayList<>();
        for (ICrlClient cc : crlList) {
            if (!(cc == null || (b = cc.getEncoded((X509Certificate) cert, (String) null)) == null)) {
                crlBytes.addAll(b);
            }
        }
        if (crlBytes.size() == 0) {
            return null;
        }
        return crlBytes;
    }

    /* access modifiers changed from: protected */
    public void addDeveloperExtension(PdfDeveloperExtension extension) {
        this.document.getCatalog().addDeveloperExtension(extension);
    }

    /* access modifiers changed from: protected */
    public boolean isPreClosed() {
        return this.preClosed;
    }

    /* access modifiers changed from: protected */
    public void preClose(Map<PdfName, Integer> exclusionSizes) throws IOException {
        PdfSigFieldLock fieldLock2;
        PdfSigFieldLock pdfSigFieldLock;
        if (!this.preClosed) {
            this.preClosed = true;
            PdfAcroForm acroForm = PdfAcroForm.getAcroForm(this.document, true);
            SignatureUtil sgnUtil = new SignatureUtil(this.document);
            String name = getFieldName();
            boolean fieldExist = sgnUtil.doesSignatureFieldExist(name);
            acroForm.setSignatureFlags(3);
            PdfSigFieldLock fieldLock3 = null;
            PdfSignature pdfSignature = this.cryptoDictionary;
            if (pdfSignature != null) {
                ((PdfDictionary) pdfSignature.getPdfObject()).makeIndirect(this.document);
                if (fieldExist) {
                    PdfSignatureFormField sigField = (PdfSignatureFormField) acroForm.getField(this.fieldName);
                    sigField.put(PdfName.f1406V, this.cryptoDictionary.getPdfObject());
                    fieldLock2 = sigField.getSigFieldLockDictionary();
                    if (fieldLock2 == null && (pdfSigFieldLock = this.fieldLock) != null) {
                        ((PdfDictionary) pdfSigFieldLock.getPdfObject()).makeIndirect(this.document);
                        sigField.put(PdfName.Lock, this.fieldLock.getPdfObject());
                        fieldLock2 = this.fieldLock;
                    }
                    sigField.put(PdfName.f1367P, this.document.getPage(this.appearance.getPageNumber()).getPdfObject());
                    sigField.put(PdfName.f1406V, this.cryptoDictionary.getPdfObject());
                    PdfObject obj = ((PdfDictionary) sigField.getPdfObject()).get(PdfName.f1324F);
                    int flags = 0;
                    if (obj != null && obj.isNumber()) {
                        flags = ((PdfNumber) obj).intValue();
                    }
                    sigField.put(PdfName.f1324F, new PdfNumber(flags | 128));
                    PdfDictionary ap = new PdfDictionary();
                    ap.put(PdfName.f1357N, this.appearance.getAppearance().getPdfObject());
                    sigField.put(PdfName.f1291AP, ap);
                    sigField.setModified();
                } else {
                    PdfWidgetAnnotation widget = new PdfWidgetAnnotation(this.appearance.getPageRect());
                    widget.setFlags(CipherSuite.TLS_RSA_WITH_CAMELLIA_256_CBC_SHA);
                    PdfSignatureFormField sigField2 = PdfFormField.createSignature(this.document);
                    sigField2.setFieldName(name);
                    sigField2.put(PdfName.f1406V, this.cryptoDictionary.getPdfObject());
                    sigField2.addKid(widget);
                    PdfSigFieldLock pdfSigFieldLock2 = this.fieldLock;
                    if (pdfSigFieldLock2 != null) {
                        ((PdfDictionary) pdfSigFieldLock2.getPdfObject()).makeIndirect(this.document);
                        sigField2.put(PdfName.Lock, this.fieldLock.getPdfObject());
                        fieldLock3 = this.fieldLock;
                    }
                    int pagen = this.appearance.getPageNumber();
                    widget.setPage(this.document.getPage(pagen));
                    PdfDictionary ap2 = widget.getAppearanceDictionary();
                    if (ap2 == null) {
                        ap2 = new PdfDictionary();
                        widget.put(PdfName.f1291AP, ap2);
                    }
                    ap2.put(PdfName.f1357N, this.appearance.getAppearance().getPdfObject());
                    acroForm.addField(sigField2, this.document.getPage(pagen));
                    if (((PdfDictionary) acroForm.getPdfObject()).isIndirect()) {
                        acroForm.setModified();
                    } else {
                        this.document.getCatalog().setModified();
                    }
                }
                this.exclusionLocations = new HashMap();
                PdfLiteral lit = new PdfLiteral(80);
                this.exclusionLocations.put(PdfName.ByteRange, lit);
                this.cryptoDictionary.put(PdfName.ByteRange, lit);
                for (Map.Entry<PdfName, Integer> entry : exclusionSizes.entrySet()) {
                    PdfName key = entry.getKey();
                    PdfLiteral lit2 = new PdfLiteral(entry.getValue().intValue());
                    this.exclusionLocations.put(key, lit2);
                    this.cryptoDictionary.put(key, lit2);
                }
                if (this.certificationLevel > 0) {
                    addDocMDP(this.cryptoDictionary);
                }
                if (fieldLock2 != null) {
                    addFieldMDP(this.cryptoDictionary, fieldLock2);
                }
                ISignatureEvent iSignatureEvent = this.signatureEvent;
                if (iSignatureEvent != null) {
                    iSignatureEvent.getSignatureDictionary(this.cryptoDictionary);
                }
                if (this.certificationLevel > 0) {
                    PdfDictionary docmdp = new PdfDictionary();
                    docmdp.put(PdfName.DocMDP, this.cryptoDictionary.getPdfObject());
                    this.document.getCatalog().put(PdfName.Perms, docmdp);
                    this.document.getCatalog().setModified();
                }
                ((PdfDictionary) this.cryptoDictionary.getPdfObject()).flush(false);
                this.document.close();
                this.range = new long[(this.exclusionLocations.size() * 2)];
                long byteRangePosition = this.exclusionLocations.get(PdfName.ByteRange).getPosition();
                this.exclusionLocations.remove(PdfName.ByteRange);
                int idx = 1;
                for (PdfLiteral lit1 : this.exclusionLocations.values()) {
                    long n = lit1.getPosition();
                    long[] jArr = this.range;
                    int idx2 = idx + 1;
                    jArr[idx] = n;
                    idx = idx2 + 1;
                    jArr[idx2] = ((long) lit1.getBytesCount()) + n;
                    acroForm = acroForm;
                    sgnUtil = sgnUtil;
                }
                SignatureUtil signatureUtil = sgnUtil;
                long[] jArr2 = this.range;
                Arrays.sort(jArr2, 1, jArr2.length - 1);
                int k = 3;
                while (true) {
                    long[] jArr3 = this.range;
                    if (k >= jArr3.length - 2) {
                        break;
                    }
                    jArr3[k] = jArr3[k] - jArr3[k - 1];
                    k += 2;
                }
                File file = this.tempFile;
                if (file == null) {
                    byte[] byteArray = this.temporaryOS.toByteArray();
                    this.bout = byteArray;
                    long[] jArr4 = this.range;
                    jArr4[jArr4.length - 1] = ((long) byteArray.length) - jArr4[jArr4.length - 2];
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    PdfOutputStream os = new PdfOutputStream(bos);
                    os.write(91);
                    int k2 = 0;
                    while (true) {
                        long[] jArr5 = this.range;
                        if (k2 < jArr5.length) {
                            ((PdfOutputStream) os.writeLong(jArr5[k2])).write(32);
                            k2++;
                        } else {
                            os.write(93);
                            System.arraycopy(bos.toByteArray(), 0, this.bout, (int) byteRangePosition, bos.size());
                            return;
                        }
                    }
                } else {
                    try {
                        RandomAccessFile randomAccessFile = FileUtil.getRandomAccessFile(file);
                        this.raf = randomAccessFile;
                        long len = randomAccessFile.length();
                        long[] jArr6 = this.range;
                        jArr6[jArr6.length - 1] = len - jArr6[jArr6.length - 2];
                        ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
                        PdfOutputStream os2 = new PdfOutputStream(bos2);
                        os2.write(91);
                        int k3 = 0;
                        while (true) {
                            long[] jArr7 = this.range;
                            if (k3 < jArr7.length) {
                                ((PdfOutputStream) os2.writeLong(jArr7[k3])).write(32);
                                k3++;
                            } else {
                                os2.write(93);
                                this.raf.seek(byteRangePosition);
                                this.raf.write(bos2.toByteArray(), 0, bos2.size());
                                return;
                            }
                        }
                    } catch (IOException e) {
                        IOException e2 = e;
                        try {
                            this.raf.close();
                        } catch (Exception e3) {
                        }
                        try {
                            this.tempFile.delete();
                        } catch (Exception e4) {
                        }
                        throw e2;
                    }
                }
            } else {
                throw new PdfException(PdfException.NoCryptoDictionaryDefined);
            }
        } else {
            throw new PdfException(PdfException.DocumentAlreadyPreClosed);
        }
    }

    /* access modifiers changed from: protected */
    public InputStream getRangeStream() throws IOException {
        return new RASInputStream(new RandomAccessSourceFactory().createRanged(getUnderlyingSource(), this.range));
    }

    /* access modifiers changed from: protected */
    public void close(PdfDictionary update) throws IOException {
        try {
            if (this.preClosed) {
                ByteArrayOutputStream bous = new ByteArrayOutputStream();
                PdfOutputStream os = new PdfOutputStream(bous);
                for (PdfName key : update.keySet()) {
                    PdfObject obj = update.get(key);
                    PdfLiteral lit = this.exclusionLocations.get(key);
                    if (lit != null) {
                        bous.reset();
                        os.write(obj);
                        if (bous.size() > lit.getBytesCount()) {
                            throw new IllegalArgumentException("The key is too big");
                        } else if (this.tempFile == null) {
                            System.arraycopy(bous.toByteArray(), 0, this.bout, (int) lit.getPosition(), bous.size());
                        } else {
                            this.raf.seek(lit.getPosition());
                            this.raf.write(bous.toByteArray(), 0, bous.size());
                        }
                    } else {
                        throw new IllegalArgumentException("The key didn't reserve space in preclose");
                    }
                }
                if (update.size() == this.exclusionLocations.size()) {
                    if (this.tempFile == null) {
                        OutputStream outputStream = this.originalOS;
                        byte[] bArr = this.bout;
                        outputStream.write(bArr, 0, bArr.length);
                    } else if (this.originalOS != null) {
                        this.raf.seek(0);
                        long length = this.raf.length();
                        byte[] buf = new byte[8192];
                        while (length > 0) {
                            int r = this.raf.read(buf, 0, (int) Math.min((long) buf.length, length));
                            if (r >= 0) {
                                this.originalOS.write(buf, 0, r);
                                length -= (long) r;
                            } else {
                                throw new EOFException("unexpected eof");
                            }
                        }
                    }
                } else {
                    throw new IllegalArgumentException("The update dictionary has less keys than required");
                }
            } else {
                throw new PdfException(PdfException.DocumentMustBePreClosed);
            }
        } finally {
            if (this.tempFile != null) {
                this.raf.close();
                if (this.originalOS != null) {
                    this.tempFile.delete();
                }
            }
            OutputStream outputStream2 = this.originalOS;
            if (outputStream2 != null) {
                try {
                    outputStream2.close();
                } catch (Exception e) {
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public IRandomAccessSource getUnderlyingSource() throws IOException {
        RandomAccessSourceFactory fac = new RandomAccessSourceFactory();
        RandomAccessFile randomAccessFile = this.raf;
        return randomAccessFile == null ? fac.createSource(this.bout) : fac.createSource(randomAccessFile);
    }

    /* access modifiers changed from: protected */
    public void addDocMDP(PdfSignature crypto) {
        PdfDictionary reference = new PdfDictionary();
        PdfDictionary transformParams = new PdfDictionary();
        transformParams.put(PdfName.f1367P, new PdfNumber(this.certificationLevel));
        transformParams.put(PdfName.f1406V, new PdfName("1.2"));
        transformParams.put(PdfName.Type, PdfName.TransformParams);
        reference.put(PdfName.TransformMethod, PdfName.DocMDP);
        reference.put(PdfName.Type, PdfName.SigRef);
        reference.put(PdfName.TransformParams, transformParams);
        setDigestParamToSigRefIfNeeded(reference);
        reference.put(PdfName.Data, this.document.getTrailer().get(PdfName.Root));
        PdfArray types = new PdfArray();
        types.add(reference);
        crypto.put(PdfName.Reference, types);
    }

    /* access modifiers changed from: protected */
    public void addFieldMDP(PdfSignature crypto, PdfSigFieldLock fieldLock2) {
        PdfDictionary reference = new PdfDictionary();
        PdfDictionary transformParams = new PdfDictionary();
        transformParams.putAll((PdfDictionary) fieldLock2.getPdfObject());
        transformParams.put(PdfName.Type, PdfName.TransformParams);
        transformParams.put(PdfName.f1406V, new PdfName("1.2"));
        reference.put(PdfName.TransformMethod, PdfName.FieldMDP);
        reference.put(PdfName.Type, PdfName.SigRef);
        reference.put(PdfName.TransformParams, transformParams);
        setDigestParamToSigRefIfNeeded(reference);
        reference.put(PdfName.Data, this.document.getTrailer().get(PdfName.Root));
        PdfArray types = ((PdfDictionary) crypto.getPdfObject()).getAsArray(PdfName.Reference);
        if (types == null) {
            types = new PdfArray();
            crypto.put(PdfName.Reference, types);
        }
        types.add(reference);
    }

    /* access modifiers changed from: protected */
    public boolean documentContainsCertificationOrApprovalSignatures() {
        PdfDictionary sigDict;
        PdfDictionary urSignature = null;
        PdfDictionary catalogPerms = ((PdfDictionary) this.document.getCatalog().getPdfObject()).getAsDictionary(PdfName.Perms);
        if (catalogPerms != null) {
            urSignature = catalogPerms.getAsDictionary(PdfName.UR3);
        }
        PdfAcroForm acroForm = PdfAcroForm.getAcroForm(this.document, false);
        if (acroForm == null) {
            return false;
        }
        for (Map.Entry<String, PdfFormField> entry : acroForm.getFormFields().entrySet()) {
            PdfDictionary fieldDict = (PdfDictionary) entry.getValue().getPdfObject();
            if (PdfName.Sig.equals(fieldDict.get(PdfName.f1327FT)) && (sigDict = fieldDict.getAsDictionary(PdfName.f1406V)) != null) {
                PdfSignature pdfSignature = new PdfSignature(sigDict);
                if (!(pdfSignature.getContents() == null || pdfSignature.getByteRange() == null || pdfSignature.getType().equals(PdfName.DocTimeStamp) || sigDict == urSignature)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public Rectangle getWidgetRectangle(PdfWidgetAnnotation widget) {
        return widget.getRectangle().toRectangle();
    }

    /* access modifiers changed from: protected */
    public int getWidgetPageNumber(PdfWidgetAnnotation widget) {
        PdfDictionary pageDict = ((PdfDictionary) widget.getPdfObject()).getAsDictionary(PdfName.f1367P);
        if (pageDict != null) {
            return this.document.getPageNumber(pageDict);
        }
        for (int i = 1; i <= this.document.getNumberOfPages(); i++) {
            PdfPage page = this.document.getPage(i);
            if (!page.isFlushed() && page.containsAnnotation(widget)) {
                return i;
            }
        }
        return 0;
    }

    private void setDigestParamToSigRefIfNeeded(PdfDictionary reference) {
        if (this.document.getPdfVersion().compareTo(PdfVersion.PDF_1_6) < 0) {
            reference.put(PdfName.DigestValue, new PdfString("aa"));
            PdfArray loc = new PdfArray();
            loc.add(new PdfNumber(0));
            loc.add(new PdfNumber(0));
            reference.put(PdfName.DigestLocation, loc);
            reference.put(PdfName.DigestMethod, PdfName.MD5);
        } else if (!isDocumentPdf2()) {
        } else {
            if (this.digestMethod != null) {
                reference.put(PdfName.DigestMethod, this.digestMethod);
            } else {
                LoggerFactory.getLogger((Class<?>) PdfSigner.class).error(LogMessageConstant.UNKNOWN_DIGEST_METHOD);
            }
        }
    }

    private PdfName getHashAlgorithmNameInCompatibleForPdfForm(String hashAlgorithm) {
        String hashAlgorithmNameInCompatibleForPdfForm;
        String hashAlgOid = DigestAlgorithms.getAllowedDigest(hashAlgorithm);
        if (hashAlgOid == null || (hashAlgorithmNameInCompatibleForPdfForm = DigestAlgorithms.getDigest(hashAlgOid)) == null) {
            return null;
        }
        return new PdfName(hashAlgorithmNameInCompatibleForPdfForm);
    }

    private boolean isDocumentPdf2() {
        return this.document.getPdfVersion().compareTo(PdfVersion.PDF_2_0) >= 0;
    }

    private static StampingProperties initStampingProperties(boolean append) {
        StampingProperties properties = new StampingProperties();
        if (append) {
            properties.useAppendMode();
        }
        return properties;
    }
}
