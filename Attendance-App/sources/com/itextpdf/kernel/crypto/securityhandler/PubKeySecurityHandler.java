package com.itextpdf.kernel.crypto.securityhandler;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.crypto.securityhandler.EncryptionUtils;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfLiteral;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.security.IExternalDecryptionProcess;
import com.itextpdf.p026io.util.StreamUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DEROutputStream;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.EncryptedContentInfo;
import org.bouncycastle.asn1.cms.EnvelopedData;
import org.bouncycastle.asn1.cms.IssuerAndSerialNumber;
import org.bouncycastle.asn1.cms.KeyTransRecipientInfo;
import org.bouncycastle.asn1.cms.OriginatorInfo;
import org.bouncycastle.asn1.cms.RecipientIdentifier;
import org.bouncycastle.asn1.cms.RecipientInfo;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.TBSCertificateStructure;

public abstract class PubKeySecurityHandler extends SecurityHandler {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int SEED_LENGTH = 20;
    private static final long serialVersionUID = -6093031394871440268L;
    private List<PublicKeyRecipient> recipients;
    private byte[] seed;

    /* access modifiers changed from: protected */
    public abstract String getDigestAlgorithm();

    /* access modifiers changed from: protected */
    public abstract void initKey(byte[] bArr, int i);

    /* access modifiers changed from: protected */
    public abstract void setPubSecSpecificHandlerDicEntries(PdfDictionary pdfDictionary, boolean z, boolean z2);

    protected PubKeySecurityHandler() {
        this.recipients = null;
        this.seed = EncryptionUtils.generateSeed(20);
        this.recipients = new ArrayList();
    }

    /* access modifiers changed from: protected */
    public byte[] computeGlobalKey(String messageDigestAlgorithm, boolean encryptMetadata) {
        try {
            MessageDigest md = MessageDigest.getInstance(messageDigestAlgorithm);
            md.update(getSeed());
            for (int i = 0; i < getRecipientsSize(); i++) {
                md.update(getEncodedRecipient(i));
            }
            if (!encryptMetadata) {
                md.update(new byte[]{-1, -1, -1, -1});
            }
            return md.digest();
        } catch (Exception e) {
            throw new PdfException(PdfException.PdfEncryption, (Throwable) e);
        }
    }

    protected static byte[] computeGlobalKeyOnReading(PdfDictionary encryptionDictionary, PrivateKey certificateKey, Certificate certificate, String certificateKeyProvider, IExternalDecryptionProcess externalDecryptionProcess, boolean encryptMetadata, String digestAlgorithm) {
        PdfArray recipients2 = encryptionDictionary.getAsArray(PdfName.Recipients);
        if (recipients2 == null) {
            recipients2 = encryptionDictionary.getAsDictionary(PdfName.f1304CF).getAsDictionary(PdfName.DefaultCryptFilter).getAsArray(PdfName.Recipients);
        }
        byte[] envelopedData = EncryptionUtils.fetchEnvelopedData(certificateKey, certificate, certificateKeyProvider, externalDecryptionProcess, recipients2);
        try {
            MessageDigest md = MessageDigest.getInstance(digestAlgorithm);
            md.update(envelopedData, 0, 20);
            for (int i = 0; i < recipients2.size(); i++) {
                md.update(recipients2.getAsString(i).getValueBytes());
            }
            if (!encryptMetadata) {
                md.update(new byte[]{-1, -1, -1, -1});
            }
            return md.digest();
        } catch (Exception f) {
            throw new PdfException(PdfException.PdfDecryption, (Throwable) f);
        }
    }

    /* access modifiers changed from: protected */
    public void addAllRecipients(Certificate[] certs, int[] permissions) {
        if (certs != null) {
            for (int i = 0; i < certs.length; i++) {
                addRecipient(certs[i], permissions[i]);
            }
        }
    }

    /* access modifiers changed from: protected */
    public PdfArray createRecipientsArray() {
        try {
            return getEncodedRecipients();
        } catch (Exception e) {
            throw new PdfException(PdfException.PdfEncryption, (Throwable) e);
        }
    }

    /* access modifiers changed from: protected */
    public void initKeyAndFillDictionary(PdfDictionary encryptionDictionary, Certificate[] certs, int[] permissions, boolean encryptMetadata, boolean embeddedFilesOnly) {
        addAllRecipients(certs, permissions);
        Integer keyLen = encryptionDictionary.getAsInt(PdfName.Length);
        initKey(computeGlobalKey(getDigestAlgorithm(), encryptMetadata), keyLen != null ? keyLen.intValue() : 40);
        setPubSecSpecificHandlerDicEntries(encryptionDictionary, encryptMetadata, embeddedFilesOnly);
    }

    /* access modifiers changed from: protected */
    public void initKeyAndReadDictionary(PdfDictionary encryptionDictionary, Key certificateKey, Certificate certificate, String certificateKeyProvider, IExternalDecryptionProcess externalDecryptionProcess, boolean encryptMetadata) {
        byte[] encryptionKey = computeGlobalKeyOnReading(encryptionDictionary, (PrivateKey) certificateKey, certificate, certificateKeyProvider, externalDecryptionProcess, encryptMetadata, getDigestAlgorithm());
        Integer keyLen = encryptionDictionary.getAsInt(PdfName.Length);
        initKey(encryptionKey, keyLen != null ? keyLen.intValue() : 40);
    }

    private void addRecipient(Certificate cert, int permission) {
        this.recipients.add(new PublicKeyRecipient(cert, permission));
    }

    private byte[] getSeed() {
        byte[] bArr = this.seed;
        byte[] clonedSeed = new byte[bArr.length];
        System.arraycopy(bArr, 0, clonedSeed, 0, bArr.length);
        return clonedSeed;
    }

    private int getRecipientsSize() {
        return this.recipients.size();
    }

    private byte[] getEncodedRecipient(int index) throws IOException, GeneralSecurityException {
        PublicKeyRecipient recipient = this.recipients.get(index);
        byte[] cms = recipient.getCms();
        if (cms != null) {
            return cms;
        }
        Certificate certificate = recipient.getCertificate();
        int permission = ((recipient.getPermission() | -3904) & -4) + 1;
        byte[] pkcs7input = new byte[24];
        System.arraycopy(this.seed, 0, pkcs7input, 0, 20);
        pkcs7input[20] = (byte) (permission >> 24);
        pkcs7input[21] = (byte) (permission >> 16);
        pkcs7input[22] = (byte) (permission >> 8);
        pkcs7input[23] = (byte) permission;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new DEROutputStream(baos).writeObject(createDERForRecipient(pkcs7input, (X509Certificate) certificate));
        byte[] cms2 = baos.toByteArray();
        recipient.setCms(cms2);
        return cms2;
    }

    private PdfArray getEncodedRecipients() {
        PdfArray EncodedRecipients = new PdfArray();
        int i = 0;
        while (i < this.recipients.size()) {
            try {
                EncodedRecipients.add(new PdfLiteral(StreamUtil.createEscapedString(getEncodedRecipient(i))));
                i++;
            } catch (GeneralSecurityException e) {
                return null;
            } catch (IOException e2) {
                return null;
            }
        }
        return EncodedRecipients;
    }

    private ASN1Primitive createDERForRecipient(byte[] in, X509Certificate cert) throws IOException, GeneralSecurityException {
        EncryptionUtils.DERForRecipientParams parameters = EncryptionUtils.calculateDERForRecipientParams(in);
        ASN1Set aSN1Set = null;
        return new ContentInfo(PKCSObjectIdentifiers.envelopedData, new EnvelopedData((OriginatorInfo) null, (ASN1Set) new DERSet((ASN1Encodable) new RecipientInfo(computeRecipientInfo(cert, parameters.abyte0))), new EncryptedContentInfo(PKCSObjectIdentifiers.data, parameters.algorithmIdentifier, new DEROctetString(parameters.abyte1)), (ASN1Set) null)).toASN1Primitive();
    }

    private KeyTransRecipientInfo computeRecipientInfo(X509Certificate x509certificate, byte[] abyte0) throws GeneralSecurityException, IOException {
        TBSCertificateStructure tbscertificatestructure = TBSCertificateStructure.getInstance(new ASN1InputStream((InputStream) new ByteArrayInputStream(x509certificate.getTBSCertificate())).readObject());
        if (tbscertificatestructure != null) {
            AlgorithmIdentifier algorithmidentifier = tbscertificatestructure.getSubjectPublicKeyInfo().getAlgorithm();
            IssuerAndSerialNumber issuerandserialnumber = new IssuerAndSerialNumber(tbscertificatestructure.getIssuer(), tbscertificatestructure.getSerialNumber().getValue());
            return new KeyTransRecipientInfo(new RecipientIdentifier(issuerandserialnumber), algorithmidentifier, new DEROctetString(EncryptionUtils.cipherBytes(x509certificate, abyte0, algorithmidentifier)));
        }
        throw new AssertionError();
    }
}
