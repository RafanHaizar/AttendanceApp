package com.itextpdf.signatures;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.signatures.PdfSigner;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CRL;
import java.security.cert.Certificate;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Enumerated;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1OutputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.esf.SignaturePolicyIdentifier;
import org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cert.ocsp.BasicOCSPResp;
import org.bouncycastle.cert.ocsp.CertificateID;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.tsp.TimeStampTokenInfo;

public class PdfPKCS7 {
    private BasicOCSPResp basicResp;
    private Collection<Certificate> certs;
    private Collection<CRL> crls;
    private byte[] digest;
    private String digestAlgorithmOid;
    private byte[] digestAttr;
    private String digestEncryptionAlgorithmOid;
    private Set<String> digestalgos;
    private MessageDigest encContDigest;
    private byte[] externalDigest;
    private byte[] externalRsaData;
    private PdfName filterSubtype;
    private IExternalDigest interfaceDigest;
    private boolean isCades;
    private boolean isTsp;
    private String location;
    private MessageDigest messageDigest;
    private String provider;
    private String reason;
    private byte[] rsaData;
    private Signature sig;
    private byte[] sigAttr;
    private byte[] sigAttrDer;
    private X509Certificate signCert;
    private Collection<Certificate> signCerts;
    private Calendar signDate;
    private String signName;
    private SignaturePolicyIdentifier signaturePolicyIdentifier;
    private int signerversion = 1;
    private TimeStampToken timeStampToken;
    private boolean verified;
    private boolean verifyResult;
    private int version = 1;

    public PdfPKCS7(PrivateKey privKey, Certificate[] certChain, String hashAlgorithm, String provider2, IExternalDigest interfaceDigest2, boolean hasRSAdata) throws InvalidKeyException, NoSuchProviderException, NoSuchAlgorithmException {
        this.provider = provider2;
        this.interfaceDigest = interfaceDigest2;
        String allowedDigest = DigestAlgorithms.getAllowedDigest(hashAlgorithm);
        this.digestAlgorithmOid = allowedDigest;
        if (allowedDigest != null) {
            this.signCert = certChain[0];
            this.certs = new ArrayList();
            for (Certificate element : certChain) {
                this.certs.add(element);
            }
            HashSet hashSet = new HashSet();
            this.digestalgos = hashSet;
            hashSet.add(this.digestAlgorithmOid);
            if (privKey != null) {
                String privateKeyAlgorithm = SignUtils.getPrivateKeyAlgorithm(privKey);
                this.digestEncryptionAlgorithmOid = privateKeyAlgorithm;
                if (privateKeyAlgorithm.equals("RSA")) {
                    this.digestEncryptionAlgorithmOid = SecurityIDs.ID_RSA;
                } else if (this.digestEncryptionAlgorithmOid.equals("DSA")) {
                    this.digestEncryptionAlgorithmOid = SecurityIDs.ID_DSA;
                } else {
                    throw new PdfException(PdfException.UnknownKeyAlgorithm1).setMessageParams(this.digestEncryptionAlgorithmOid);
                }
            }
            if (hasRSAdata) {
                this.rsaData = new byte[0];
                this.messageDigest = DigestAlgorithms.getMessageDigest(getHashAlgorithm(), provider2);
            }
            if (privKey != null) {
                this.sig = initSignature(privKey);
                return;
            }
            return;
        }
        throw new PdfException(PdfException.UnknownHashAlgorithm1).setMessageParams(hashAlgorithm);
    }

    public PdfPKCS7(byte[] contentsKey, byte[] certsKey, String provider2) {
        try {
            this.provider = provider2;
            Collection<Certificate> readAllCerts = SignUtils.readAllCerts(certsKey);
            this.certs = readAllCerts;
            this.signCerts = readAllCerts;
            this.signCert = (X509Certificate) SignUtils.getFirstElement(readAllCerts);
            this.crls = new ArrayList();
            this.digest = ((ASN1OctetString) new ASN1InputStream((InputStream) new ByteArrayInputStream(contentsKey)).readObject()).getOctets();
            Signature signatureHelper = SignUtils.getSignatureHelper("SHA1withRSA", provider2);
            this.sig = signatureHelper;
            signatureHelper.initVerify(this.signCert.getPublicKey());
            this.digestAlgorithmOid = "1.2.840.10040.4.3";
            this.digestEncryptionAlgorithmOid = "1.3.36.3.3.1.2";
        } catch (Exception e) {
            throw new PdfException((Throwable) e);
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public PdfPKCS7(byte[] r36, com.itextpdf.kernel.pdf.PdfName r37, java.lang.String r38) {
        /*
            r35 = this;
            r1 = r35
            r2 = r37
            r3 = r38
            r35.<init>()
            r0 = 1
            r1.version = r0
            r1.signerversion = r0
            r1.filterSubtype = r2
            com.itextpdf.kernel.pdf.PdfName r4 = com.itextpdf.kernel.pdf.PdfName.ETSI_RFC3161
            boolean r4 = r4.equals(r2)
            r1.isTsp = r4
            com.itextpdf.kernel.pdf.PdfName r4 = com.itextpdf.kernel.pdf.PdfName.ETSI_CAdES_DETACHED
            boolean r4 = r4.equals(r2)
            r1.isCades = r4
            r1.provider = r3     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1InputStream r4 = new org.bouncycastle.asn1.ASN1InputStream     // Catch:{ Exception -> 0x04b8 }
            java.io.ByteArrayInputStream r5 = new java.io.ByteArrayInputStream     // Catch:{ Exception -> 0x04b8 }
            r6 = r36
            r5.<init>(r6)     // Catch:{ Exception -> 0x04b8 }
            r4.<init>((java.io.InputStream) r5)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Primitive r5 = r4.readObject()     // Catch:{ IOException -> 0x04ab }
            boolean r7 = r5 instanceof org.bouncycastle.asn1.ASN1Sequence     // Catch:{ Exception -> 0x04b8 }
            if (r7 == 0) goto L_0x049f
            r7 = r5
            org.bouncycastle.asn1.ASN1Sequence r7 = (org.bouncycastle.asn1.ASN1Sequence) r7     // Catch:{ Exception -> 0x04b8 }
            r8 = 0
            org.bouncycastle.asn1.ASN1Encodable r9 = r7.getObjectAt(r8)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r9 = (org.bouncycastle.asn1.ASN1ObjectIdentifier) r9     // Catch:{ Exception -> 0x04b8 }
            java.lang.String r10 = r9.getId()     // Catch:{ Exception -> 0x04b8 }
            java.lang.String r11 = "1.2.840.113549.1.7.2"
            boolean r10 = r10.equals(r11)     // Catch:{ Exception -> 0x04b8 }
            if (r10 == 0) goto L_0x0491
            org.bouncycastle.asn1.ASN1Encodable r10 = r7.getObjectAt(r0)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1TaggedObject r10 = (org.bouncycastle.asn1.ASN1TaggedObject) r10     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Primitive r10 = r10.getObject()     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Sequence r10 = (org.bouncycastle.asn1.ASN1Sequence) r10     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Encodable r11 = r10.getObjectAt(r8)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Integer r11 = (org.bouncycastle.asn1.ASN1Integer) r11     // Catch:{ Exception -> 0x04b8 }
            java.math.BigInteger r11 = r11.getValue()     // Catch:{ Exception -> 0x04b8 }
            int r11 = r11.intValue()     // Catch:{ Exception -> 0x04b8 }
            r1.version = r11     // Catch:{ Exception -> 0x04b8 }
            java.util.HashSet r11 = new java.util.HashSet     // Catch:{ Exception -> 0x04b8 }
            r11.<init>()     // Catch:{ Exception -> 0x04b8 }
            r1.digestalgos = r11     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Encodable r11 = r10.getObjectAt(r0)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Set r11 = (org.bouncycastle.asn1.ASN1Set) r11     // Catch:{ Exception -> 0x04b8 }
            java.util.Enumeration r11 = r11.getObjects()     // Catch:{ Exception -> 0x04b8 }
        L_0x007a:
            boolean r12 = r11.hasMoreElements()     // Catch:{ Exception -> 0x04b8 }
            if (r12 == 0) goto L_0x0097
            java.lang.Object r12 = r11.nextElement()     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Sequence r12 = (org.bouncycastle.asn1.ASN1Sequence) r12     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Encodable r13 = r12.getObjectAt(r8)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r13 = (org.bouncycastle.asn1.ASN1ObjectIdentifier) r13     // Catch:{ Exception -> 0x04b8 }
            java.util.Set<java.lang.String> r14 = r1.digestalgos     // Catch:{ Exception -> 0x04b8 }
            java.lang.String r15 = r13.getId()     // Catch:{ Exception -> 0x04b8 }
            r14.add(r15)     // Catch:{ Exception -> 0x04b8 }
            goto L_0x007a
        L_0x0097:
            r12 = 2
            org.bouncycastle.asn1.ASN1Encodable r13 = r10.getObjectAt(r12)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Sequence r13 = (org.bouncycastle.asn1.ASN1Sequence) r13     // Catch:{ Exception -> 0x04b8 }
            int r14 = r13.size()     // Catch:{ Exception -> 0x04b8 }
            if (r14 <= r0) goto L_0x00b6
            org.bouncycastle.asn1.ASN1Encodable r14 = r13.getObjectAt(r0)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1TaggedObject r14 = (org.bouncycastle.asn1.ASN1TaggedObject) r14     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Primitive r14 = r14.getObject()     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1OctetString r14 = (org.bouncycastle.asn1.ASN1OctetString) r14     // Catch:{ Exception -> 0x04b8 }
            byte[] r15 = r14.getOctets()     // Catch:{ Exception -> 0x04b8 }
            r1.rsaData = r15     // Catch:{ Exception -> 0x04b8 }
        L_0x00b6:
            r14 = 3
        L_0x00b7:
            org.bouncycastle.asn1.ASN1Encodable r15 = r10.getObjectAt(r14)     // Catch:{ Exception -> 0x04b8 }
            boolean r15 = r15 instanceof org.bouncycastle.asn1.ASN1TaggedObject     // Catch:{ Exception -> 0x04b8 }
            if (r15 == 0) goto L_0x00c2
            int r14 = r14 + 1
            goto L_0x00b7
        L_0x00c2:
            java.util.Collection r15 = com.itextpdf.signatures.SignUtils.readAllCerts(r36)     // Catch:{ Exception -> 0x04b8 }
            r1.certs = r15     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Encodable r15 = r10.getObjectAt(r14)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Set r15 = (org.bouncycastle.asn1.ASN1Set) r15     // Catch:{ Exception -> 0x04b8 }
            int r12 = r15.size()     // Catch:{ Exception -> 0x04b8 }
            if (r12 != r0) goto L_0x047d
            org.bouncycastle.asn1.ASN1Encodable r12 = r15.getObjectAt(r8)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Sequence r12 = (org.bouncycastle.asn1.ASN1Sequence) r12     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Encodable r17 = r12.getObjectAt(r8)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Integer r17 = (org.bouncycastle.asn1.ASN1Integer) r17     // Catch:{ Exception -> 0x04b8 }
            java.math.BigInteger r17 = r17.getValue()     // Catch:{ Exception -> 0x04b8 }
            int r8 = r17.intValue()     // Catch:{ Exception -> 0x04b8 }
            r1.signerversion = r8     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Encodable r8 = r12.getObjectAt(r0)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Sequence r8 = (org.bouncycastle.asn1.ASN1Sequence) r8     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.jce.X509Principal r17 = com.itextpdf.signatures.SignUtils.getIssuerX509Name(r8)     // Catch:{ Exception -> 0x04b8 }
            r19 = r17
            org.bouncycastle.asn1.ASN1Encodable r17 = r8.getObjectAt(r0)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Integer r17 = (org.bouncycastle.asn1.ASN1Integer) r17     // Catch:{ Exception -> 0x04b8 }
            java.math.BigInteger r17 = r17.getValue()     // Catch:{ Exception -> 0x04b8 }
            r20 = r17
            java.util.Collection<java.security.cert.Certificate> r0 = r1.certs     // Catch:{ Exception -> 0x04b8 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ Exception -> 0x04b8 }
        L_0x0108:
            boolean r21 = r0.hasNext()     // Catch:{ Exception -> 0x04b8 }
            if (r21 == 0) goto L_0x014d
            java.lang.Object r21 = r0.next()     // Catch:{ Exception -> 0x04b8 }
            r22 = r21
            java.security.cert.X509Certificate r22 = (java.security.cert.X509Certificate) r22     // Catch:{ Exception -> 0x04b8 }
            r23 = r22
            r22 = r0
            java.security.Principal r0 = r23.getIssuerDN()     // Catch:{ Exception -> 0x04b8 }
            r2 = r19
            boolean r0 = r0.equals(r2)     // Catch:{ Exception -> 0x04b8 }
            if (r0 == 0) goto L_0x013c
            java.math.BigInteger r0 = r23.getSerialNumber()     // Catch:{ Exception -> 0x04b8 }
            r19 = r4
            r4 = r20
            boolean r0 = r4.equals(r0)     // Catch:{ Exception -> 0x04b8 }
            if (r0 == 0) goto L_0x0139
            r0 = r23
            r1.signCert = r0     // Catch:{ Exception -> 0x04b8 }
            goto L_0x0153
        L_0x0139:
            r0 = r23
            goto L_0x0142
        L_0x013c:
            r19 = r4
            r4 = r20
            r0 = r23
        L_0x0142:
            r20 = r4
            r4 = r19
            r0 = r22
            r19 = r2
            r2 = r37
            goto L_0x0108
        L_0x014d:
            r2 = r19
            r19 = r4
            r4 = r20
        L_0x0153:
            java.security.cert.X509Certificate r0 = r1.signCert     // Catch:{ Exception -> 0x04b8 }
            if (r0 == 0) goto L_0x043e
            r35.signCertificateChain()     // Catch:{ Exception -> 0x04b8 }
            r0 = 2
            org.bouncycastle.asn1.ASN1Encodable r0 = r12.getObjectAt(r0)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Sequence r0 = (org.bouncycastle.asn1.ASN1Sequence) r0     // Catch:{ Exception -> 0x04b8 }
            r16 = r5
            r5 = 0
            org.bouncycastle.asn1.ASN1Encodable r0 = r0.getObjectAt(r5)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = (org.bouncycastle.asn1.ASN1ObjectIdentifier) r0     // Catch:{ Exception -> 0x04b8 }
            java.lang.String r0 = r0.getId()     // Catch:{ Exception -> 0x04b8 }
            r1.digestAlgorithmOid = r0     // Catch:{ Exception -> 0x04b8 }
            r0 = 3
            r5 = 0
            org.bouncycastle.asn1.ASN1Encodable r14 = r12.getObjectAt(r0)     // Catch:{ Exception -> 0x04b8 }
            boolean r14 = r14 instanceof org.bouncycastle.asn1.ASN1TaggedObject     // Catch:{ Exception -> 0x04b8 }
            if (r14 == 0) goto L_0x0349
            org.bouncycastle.asn1.ASN1Encodable r14 = r12.getObjectAt(r0)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1TaggedObject r14 = (org.bouncycastle.asn1.ASN1TaggedObject) r14     // Catch:{ Exception -> 0x04b8 }
            r20 = r5
            r5 = 0
            org.bouncycastle.asn1.ASN1Set r21 = org.bouncycastle.asn1.ASN1Set.getInstance(r14, r5)     // Catch:{ Exception -> 0x04b8 }
            r5 = r21
            byte[] r6 = r5.getEncoded()     // Catch:{ Exception -> 0x04b8 }
            r1.sigAttr = r6     // Catch:{ Exception -> 0x04b8 }
            java.lang.String r6 = "DER"
            byte[] r6 = r5.getEncoded(r6)     // Catch:{ Exception -> 0x04b8 }
            r1.sigAttrDer = r6     // Catch:{ Exception -> 0x04b8 }
            r6 = 0
        L_0x0198:
            r21 = r8
            int r8 = r5.size()     // Catch:{ Exception -> 0x04b8 }
            if (r6 >= r8) goto L_0x032e
            org.bouncycastle.asn1.ASN1Encodable r8 = r5.getObjectAt(r6)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Sequence r8 = (org.bouncycastle.asn1.ASN1Sequence) r8     // Catch:{ Exception -> 0x04b8 }
            r22 = r5
            r5 = 0
            org.bouncycastle.asn1.ASN1Encodable r23 = r8.getObjectAt(r5)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r23 = (org.bouncycastle.asn1.ASN1ObjectIdentifier) r23     // Catch:{ Exception -> 0x04b8 }
            java.lang.String r5 = r23.getId()     // Catch:{ Exception -> 0x04b8 }
            r23 = r9
            java.lang.String r9 = "1.2.840.113549.1.9.4"
            boolean r9 = r5.equals(r9)     // Catch:{ Exception -> 0x04b8 }
            if (r9 == 0) goto L_0x01db
            r9 = 1
            org.bouncycastle.asn1.ASN1Encodable r24 = r8.getObjectAt(r9)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Set r24 = (org.bouncycastle.asn1.ASN1Set) r24     // Catch:{ Exception -> 0x04b8 }
            r9 = r24
            r24 = r10
            r10 = 0
            org.bouncycastle.asn1.ASN1Encodable r25 = r9.getObjectAt(r10)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1OctetString r25 = (org.bouncycastle.asn1.ASN1OctetString) r25     // Catch:{ Exception -> 0x04b8 }
            byte[] r10 = r25.getOctets()     // Catch:{ Exception -> 0x04b8 }
            r1.digestAttr = r10     // Catch:{ Exception -> 0x04b8 }
            r25 = r11
            r28 = r13
            goto L_0x031e
        L_0x01db:
            r24 = r10
            java.lang.String r9 = "1.2.840.113583.1.1.8"
            boolean r9 = r5.equals(r9)     // Catch:{ Exception -> 0x04b8 }
            if (r9 == 0) goto L_0x0243
            r9 = 1
            org.bouncycastle.asn1.ASN1Encodable r10 = r8.getObjectAt(r9)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Set r10 = (org.bouncycastle.asn1.ASN1Set) r10     // Catch:{ Exception -> 0x04b8 }
            r9 = r10
            r10 = 0
            org.bouncycastle.asn1.ASN1Encodable r25 = r9.getObjectAt(r10)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Sequence r25 = (org.bouncycastle.asn1.ASN1Sequence) r25     // Catch:{ Exception -> 0x04b8 }
            r10 = r25
            r25 = 0
            r26 = r9
            r9 = r25
        L_0x01fc:
            r25 = r11
            int r11 = r10.size()     // Catch:{ Exception -> 0x04b8 }
            if (r9 >= r11) goto L_0x023d
            org.bouncycastle.asn1.ASN1Encodable r11 = r10.getObjectAt(r9)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1TaggedObject r11 = (org.bouncycastle.asn1.ASN1TaggedObject) r11     // Catch:{ Exception -> 0x04b8 }
            int r27 = r11.getTagNo()     // Catch:{ Exception -> 0x04b8 }
            if (r27 != 0) goto L_0x0220
            org.bouncycastle.asn1.ASN1Primitive r27 = r11.getObject()     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Sequence r27 = (org.bouncycastle.asn1.ASN1Sequence) r27     // Catch:{ Exception -> 0x04b8 }
            r28 = r27
            r27 = r10
            r10 = r28
            r1.findCRL(r10)     // Catch:{ Exception -> 0x04b8 }
            goto L_0x0222
        L_0x0220:
            r27 = r10
        L_0x0222:
            int r10 = r11.getTagNo()     // Catch:{ Exception -> 0x04b8 }
            r28 = r13
            r13 = 1
            if (r10 != r13) goto L_0x0234
            org.bouncycastle.asn1.ASN1Primitive r10 = r11.getObject()     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Sequence r10 = (org.bouncycastle.asn1.ASN1Sequence) r10     // Catch:{ Exception -> 0x04b8 }
            r1.findOcsp(r10)     // Catch:{ Exception -> 0x04b8 }
        L_0x0234:
            int r9 = r9 + 1
            r11 = r25
            r10 = r27
            r13 = r28
            goto L_0x01fc
        L_0x023d:
            r27 = r10
            r28 = r13
            goto L_0x031e
        L_0x0243:
            r25 = r11
            r28 = r13
            boolean r9 = r1.isCades     // Catch:{ Exception -> 0x04b8 }
            java.lang.String r10 = "Signing certificate doesn't match the ESS information."
            if (r9 == 0) goto L_0x02ab
            java.lang.String r9 = "1.2.840.113549.1.9.16.2.12"
            boolean r9 = r5.equals(r9)     // Catch:{ Exception -> 0x04b8 }
            if (r9 == 0) goto L_0x02ab
            r9 = 1
            org.bouncycastle.asn1.ASN1Encodable r11 = r8.getObjectAt(r9)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Set r11 = (org.bouncycastle.asn1.ASN1Set) r11     // Catch:{ Exception -> 0x04b8 }
            r9 = r11
            r11 = 0
            org.bouncycastle.asn1.ASN1Encodable r13 = r9.getObjectAt(r11)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Sequence r13 = (org.bouncycastle.asn1.ASN1Sequence) r13     // Catch:{ Exception -> 0x04b8 }
            r11 = r13
            org.bouncycastle.asn1.ess.SigningCertificate r13 = org.bouncycastle.asn1.ess.SigningCertificate.getInstance(r11)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ess.ESSCertID[] r26 = r13.getCerts()     // Catch:{ Exception -> 0x04b8 }
            r18 = 0
            r27 = r26[r18]     // Catch:{ Exception -> 0x04b8 }
            r29 = r9
            java.security.cert.X509Certificate r9 = r1.signCert     // Catch:{ Exception -> 0x04b8 }
            byte[] r9 = r9.getEncoded()     // Catch:{ Exception -> 0x04b8 }
            java.lang.String r30 = "SHA-1"
            java.security.MessageDigest r30 = com.itextpdf.signatures.SignUtils.getMessageDigest(r30)     // Catch:{ Exception -> 0x04b8 }
            r31 = r30
            r30 = r11
            r11 = r31
            byte[] r31 = r11.digest(r9)     // Catch:{ Exception -> 0x04b8 }
            r32 = r31
            byte[] r31 = r27.getCertHash()     // Catch:{ Exception -> 0x04b8 }
            r33 = r31
            r31 = r9
            r9 = r32
            r32 = r11
            r11 = r33
            boolean r33 = java.util.Arrays.equals(r9, r11)     // Catch:{ Exception -> 0x04b8 }
            if (r33 == 0) goto L_0x02a3
            r20 = 1
            goto L_0x031e
        L_0x02a3:
            r33 = r9
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException     // Catch:{ Exception -> 0x04b8 }
            r9.<init>(r10)     // Catch:{ Exception -> 0x04b8 }
            throw r9     // Catch:{ Exception -> 0x04b8 }
        L_0x02ab:
            boolean r9 = r1.isCades     // Catch:{ Exception -> 0x04b8 }
            if (r9 == 0) goto L_0x031a
            java.lang.String r9 = "1.2.840.113549.1.9.16.2.47"
            boolean r9 = r5.equals(r9)     // Catch:{ Exception -> 0x04b8 }
            if (r9 == 0) goto L_0x031a
            r9 = 1
            org.bouncycastle.asn1.ASN1Encodable r11 = r8.getObjectAt(r9)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Set r11 = (org.bouncycastle.asn1.ASN1Set) r11     // Catch:{ Exception -> 0x04b8 }
            r9 = r11
            r11 = 0
            org.bouncycastle.asn1.ASN1Encodable r13 = r9.getObjectAt(r11)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Sequence r13 = (org.bouncycastle.asn1.ASN1Sequence) r13     // Catch:{ Exception -> 0x04b8 }
            r11 = r13
            org.bouncycastle.asn1.ess.SigningCertificateV2 r13 = org.bouncycastle.asn1.ess.SigningCertificateV2.getInstance(r11)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ess.ESSCertIDv2[] r26 = r13.getCerts()     // Catch:{ Exception -> 0x04b8 }
            r18 = 0
            r27 = r26[r18]     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r29 = r27.getHashAlgorithm()     // Catch:{ Exception -> 0x04b8 }
            r30 = r5
            java.security.cert.X509Certificate r5 = r1.signCert     // Catch:{ Exception -> 0x04b8 }
            byte[] r5 = r5.getEncoded()     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r31 = r29.getAlgorithm()     // Catch:{ Exception -> 0x04b8 }
            java.lang.String r31 = r31.getId()     // Catch:{ Exception -> 0x04b8 }
            java.lang.String r31 = com.itextpdf.signatures.DigestAlgorithms.getDigest(r31)     // Catch:{ Exception -> 0x04b8 }
            java.security.MessageDigest r31 = com.itextpdf.signatures.SignUtils.getMessageDigest(r31)     // Catch:{ Exception -> 0x04b8 }
            r32 = r31
            r31 = r8
            r8 = r32
            byte[] r32 = r8.digest(r5)     // Catch:{ Exception -> 0x04b8 }
            r33 = r32
            byte[] r32 = r27.getCertHash()     // Catch:{ Exception -> 0x04b8 }
            r34 = r32
            r32 = r5
            r5 = r33
            r33 = r8
            r8 = r34
            boolean r34 = java.util.Arrays.equals(r5, r8)     // Catch:{ Exception -> 0x04b8 }
            if (r34 == 0) goto L_0x0312
            r20 = 1
            goto L_0x031e
        L_0x0312:
            r34 = r5
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException     // Catch:{ Exception -> 0x04b8 }
            r5.<init>(r10)     // Catch:{ Exception -> 0x04b8 }
            throw r5     // Catch:{ Exception -> 0x04b8 }
        L_0x031a:
            r30 = r5
            r31 = r8
        L_0x031e:
            int r6 = r6 + 1
            r8 = r21
            r5 = r22
            r9 = r23
            r10 = r24
            r11 = r25
            r13 = r28
            goto L_0x0198
        L_0x032e:
            r22 = r5
            r23 = r9
            r24 = r10
            r25 = r11
            r28 = r13
            byte[] r5 = r1.digestAttr     // Catch:{ Exception -> 0x04b8 }
            if (r5 == 0) goto L_0x0341
            int r0 = r0 + 1
            r5 = r20
            goto L_0x0355
        L_0x0341:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException     // Catch:{ Exception -> 0x04b8 }
            java.lang.String r6 = "Authenticated attribute is missing the digest."
            r5.<init>(r6)     // Catch:{ Exception -> 0x04b8 }
            throw r5     // Catch:{ Exception -> 0x04b8 }
        L_0x0349:
            r20 = r5
            r21 = r8
            r23 = r9
            r24 = r10
            r25 = r11
            r28 = r13
        L_0x0355:
            boolean r6 = r1.isCades     // Catch:{ Exception -> 0x04b8 }
            if (r6 == 0) goto L_0x0364
            if (r5 == 0) goto L_0x035c
            goto L_0x0364
        L_0x035c:
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException     // Catch:{ Exception -> 0x04b8 }
            java.lang.String r8 = "CAdES ESS information missing."
            r6.<init>(r8)     // Catch:{ Exception -> 0x04b8 }
            throw r6     // Catch:{ Exception -> 0x04b8 }
        L_0x0364:
            int r6 = r0 + 1
            org.bouncycastle.asn1.ASN1Encodable r0 = r12.getObjectAt(r0)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Sequence r0 = (org.bouncycastle.asn1.ASN1Sequence) r0     // Catch:{ Exception -> 0x04b8 }
            r8 = 0
            org.bouncycastle.asn1.ASN1Encodable r0 = r0.getObjectAt(r8)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = (org.bouncycastle.asn1.ASN1ObjectIdentifier) r0     // Catch:{ Exception -> 0x04b8 }
            java.lang.String r0 = r0.getId()     // Catch:{ Exception -> 0x04b8 }
            r1.digestEncryptionAlgorithmOid = r0     // Catch:{ Exception -> 0x04b8 }
            int r0 = r6 + 1
            org.bouncycastle.asn1.ASN1Encodable r6 = r12.getObjectAt(r6)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1OctetString r6 = (org.bouncycastle.asn1.ASN1OctetString) r6     // Catch:{ Exception -> 0x04b8 }
            byte[] r6 = r6.getOctets()     // Catch:{ Exception -> 0x04b8 }
            r1.digest = r6     // Catch:{ Exception -> 0x04b8 }
            int r6 = r12.size()     // Catch:{ Exception -> 0x04b8 }
            if (r0 >= r6) goto L_0x03d6
            org.bouncycastle.asn1.ASN1Encodable r6 = r12.getObjectAt(r0)     // Catch:{ Exception -> 0x04b8 }
            boolean r6 = r6 instanceof org.bouncycastle.asn1.ASN1TaggedObject     // Catch:{ Exception -> 0x04b8 }
            if (r6 == 0) goto L_0x03d6
            org.bouncycastle.asn1.ASN1Encodable r6 = r12.getObjectAt(r0)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1TaggedObject r6 = (org.bouncycastle.asn1.ASN1TaggedObject) r6     // Catch:{ Exception -> 0x04b8 }
            r8 = 0
            org.bouncycastle.asn1.ASN1Set r9 = org.bouncycastle.asn1.ASN1Set.getInstance(r6, r8)     // Catch:{ Exception -> 0x04b8 }
            r8 = r9
            org.bouncycastle.asn1.cms.AttributeTable r9 = new org.bouncycastle.asn1.cms.AttributeTable     // Catch:{ Exception -> 0x04b8 }
            r9.<init>((org.bouncycastle.asn1.ASN1Set) r8)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r10 = org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_aa_signatureTimeStampToken     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.cms.Attribute r10 = r9.get(r10)     // Catch:{ Exception -> 0x04b8 }
            if (r10 == 0) goto L_0x03d3
            org.bouncycastle.asn1.ASN1Set r11 = r10.getAttrValues()     // Catch:{ Exception -> 0x04b8 }
            int r11 = r11.size()     // Catch:{ Exception -> 0x04b8 }
            if (r11 <= 0) goto L_0x03d3
            org.bouncycastle.asn1.ASN1Set r11 = r10.getAttrValues()     // Catch:{ Exception -> 0x04b8 }
            r13 = 0
            org.bouncycastle.asn1.ASN1Encodable r13 = r11.getObjectAt(r13)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1Sequence r13 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r13)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.cms.ContentInfo r14 = org.bouncycastle.asn1.cms.ContentInfo.getInstance(r13)     // Catch:{ Exception -> 0x04b8 }
            r17 = r0
            org.bouncycastle.tsp.TimeStampToken r0 = new org.bouncycastle.tsp.TimeStampToken     // Catch:{ Exception -> 0x04b8 }
            r0.<init>((org.bouncycastle.asn1.cms.ContentInfo) r14)     // Catch:{ Exception -> 0x04b8 }
            r1.timeStampToken = r0     // Catch:{ Exception -> 0x04b8 }
            goto L_0x03d8
        L_0x03d3:
            r17 = r0
            goto L_0x03d8
        L_0x03d6:
            r17 = r0
        L_0x03d8:
            boolean r0 = r1.isTsp     // Catch:{ Exception -> 0x04b8 }
            if (r0 == 0) goto L_0x03ff
            org.bouncycastle.asn1.cms.ContentInfo r0 = org.bouncycastle.asn1.cms.ContentInfo.getInstance(r7)     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.tsp.TimeStampToken r6 = new org.bouncycastle.tsp.TimeStampToken     // Catch:{ Exception -> 0x04b8 }
            r6.<init>((org.bouncycastle.asn1.cms.ContentInfo) r0)     // Catch:{ Exception -> 0x04b8 }
            r1.timeStampToken = r6     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.tsp.TimeStampTokenInfo r6 = r6.getTimeStampInfo()     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r8 = r6.getHashAlgorithm()     // Catch:{ Exception -> 0x04b8 }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r8 = r8.getAlgorithm()     // Catch:{ Exception -> 0x04b8 }
            java.lang.String r8 = r8.getId()     // Catch:{ Exception -> 0x04b8 }
            r9 = 0
            java.security.MessageDigest r9 = com.itextpdf.signatures.DigestAlgorithms.getMessageDigestFromOid(r8, r9)     // Catch:{ Exception -> 0x04b8 }
            r1.messageDigest = r9     // Catch:{ Exception -> 0x04b8 }
            goto L_0x043c
        L_0x03ff:
            byte[] r0 = r1.rsaData     // Catch:{ Exception -> 0x04b8 }
            if (r0 != 0) goto L_0x0407
            byte[] r0 = r1.digestAttr     // Catch:{ Exception -> 0x04b8 }
            if (r0 == 0) goto L_0x0430
        L_0x0407:
            com.itextpdf.kernel.pdf.PdfName r0 = com.itextpdf.kernel.pdf.PdfName.Adbe_pkcs7_sha1     // Catch:{ Exception -> 0x04b8 }
            com.itextpdf.kernel.pdf.PdfName r6 = r35.getFilterSubtype()     // Catch:{ Exception -> 0x04b8 }
            boolean r0 = r0.equals(r6)     // Catch:{ Exception -> 0x04b8 }
            if (r0 == 0) goto L_0x041c
            java.lang.String r0 = "SHA1"
            java.security.MessageDigest r0 = com.itextpdf.signatures.DigestAlgorithms.getMessageDigest(r0, r3)     // Catch:{ Exception -> 0x04b8 }
            r1.messageDigest = r0     // Catch:{ Exception -> 0x04b8 }
            goto L_0x0426
        L_0x041c:
            java.lang.String r0 = r35.getHashAlgorithm()     // Catch:{ Exception -> 0x04b8 }
            java.security.MessageDigest r0 = com.itextpdf.signatures.DigestAlgorithms.getMessageDigest(r0, r3)     // Catch:{ Exception -> 0x04b8 }
            r1.messageDigest = r0     // Catch:{ Exception -> 0x04b8 }
        L_0x0426:
            java.lang.String r0 = r35.getHashAlgorithm()     // Catch:{ Exception -> 0x04b8 }
            java.security.MessageDigest r0 = com.itextpdf.signatures.DigestAlgorithms.getMessageDigest(r0, r3)     // Catch:{ Exception -> 0x04b8 }
            r1.encContDigest = r0     // Catch:{ Exception -> 0x04b8 }
        L_0x0430:
            java.security.cert.X509Certificate r0 = r1.signCert     // Catch:{ Exception -> 0x04b8 }
            java.security.PublicKey r0 = r0.getPublicKey()     // Catch:{ Exception -> 0x04b8 }
            java.security.Signature r0 = r1.initSignature((java.security.PublicKey) r0)     // Catch:{ Exception -> 0x04b8 }
            r1.sig = r0     // Catch:{ Exception -> 0x04b8 }
        L_0x043c:
            return
        L_0x043e:
            r16 = r5
            r21 = r8
            r23 = r9
            r24 = r10
            r25 = r11
            r28 = r13
            com.itextpdf.kernel.PdfException r0 = new com.itextpdf.kernel.PdfException     // Catch:{ Exception -> 0x04b8 }
            java.lang.String r5 = "Cannot find signing certificate with serial {0}."
            r0.<init>((java.lang.String) r5)     // Catch:{ Exception -> 0x04b8 }
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Exception -> 0x04b8 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x04b8 }
            r6.<init>()     // Catch:{ Exception -> 0x04b8 }
            java.lang.String r8 = r2.getName()     // Catch:{ Exception -> 0x04b8 }
            java.lang.StringBuilder r6 = r6.append(r8)     // Catch:{ Exception -> 0x04b8 }
            java.lang.String r8 = " / "
            java.lang.StringBuilder r6 = r6.append(r8)     // Catch:{ Exception -> 0x04b8 }
            r8 = 16
            java.lang.String r8 = r4.toString(r8)     // Catch:{ Exception -> 0x04b8 }
            java.lang.StringBuilder r6 = r6.append(r8)     // Catch:{ Exception -> 0x04b8 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x04b8 }
            r8 = 0
            r5[r8] = r6     // Catch:{ Exception -> 0x04b8 }
            com.itextpdf.kernel.PdfException r0 = r0.setMessageParams(r5)     // Catch:{ Exception -> 0x04b8 }
            throw r0     // Catch:{ Exception -> 0x04b8 }
        L_0x047d:
            r19 = r4
            r16 = r5
            r23 = r9
            r24 = r10
            r25 = r11
            r28 = r13
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException     // Catch:{ Exception -> 0x04b8 }
            java.lang.String r2 = "This PKCS#7 object has multiple SignerInfos. Only one is supported at this time."
            r0.<init>(r2)     // Catch:{ Exception -> 0x04b8 }
            throw r0     // Catch:{ Exception -> 0x04b8 }
        L_0x0491:
            r19 = r4
            r16 = r5
            r23 = r9
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException     // Catch:{ Exception -> 0x04b8 }
            java.lang.String r2 = "Not a valid PKCS#7 object - not signed data."
            r0.<init>(r2)     // Catch:{ Exception -> 0x04b8 }
            throw r0     // Catch:{ Exception -> 0x04b8 }
        L_0x049f:
            r19 = r4
            r16 = r5
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException     // Catch:{ Exception -> 0x04b8 }
            java.lang.String r2 = "Not a valid PKCS#7 object - not a sequence"
            r0.<init>(r2)     // Catch:{ Exception -> 0x04b8 }
            throw r0     // Catch:{ Exception -> 0x04b8 }
        L_0x04ab:
            r0 = move-exception
            r19 = r4
            r2 = r0
            r0 = r2
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException     // Catch:{ Exception -> 0x04b8 }
            java.lang.String r4 = "Cannot decode PKCS#7 SignedData object."
            r2.<init>(r4)     // Catch:{ Exception -> 0x04b8 }
            throw r2     // Catch:{ Exception -> 0x04b8 }
        L_0x04b8:
            r0 = move-exception
            com.itextpdf.kernel.PdfException r2 = new com.itextpdf.kernel.PdfException
            r2.<init>((java.lang.Throwable) r0)
            goto L_0x04c0
        L_0x04bf:
            throw r2
        L_0x04c0:
            goto L_0x04bf
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.signatures.PdfPKCS7.<init>(byte[], com.itextpdf.kernel.pdf.PdfName, java.lang.String):void");
    }

    public void setSignaturePolicy(SignaturePolicyInfo signaturePolicy) {
        this.signaturePolicyIdentifier = signaturePolicy.toSignaturePolicyIdentifier();
    }

    public void setSignaturePolicy(SignaturePolicyIdentifier signaturePolicy) {
        this.signaturePolicyIdentifier = signaturePolicy;
    }

    public String getSignName() {
        return this.signName;
    }

    public void setSignName(String signName2) {
        this.signName = signName2;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason2) {
        this.reason = reason2;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location2) {
        this.location = location2;
    }

    public Calendar getSignDate() {
        Calendar dt = getTimeStampDate();
        if (dt == TimestampConstants.UNDEFINED_TIMESTAMP_DATE) {
            return this.signDate;
        }
        return dt;
    }

    public void setSignDate(Calendar signDate2) {
        this.signDate = signDate2;
    }

    public int getVersion() {
        return this.version;
    }

    public int getSigningInfoVersion() {
        return this.signerversion;
    }

    public String getDigestAlgorithmOid() {
        return this.digestAlgorithmOid;
    }

    public String getHashAlgorithm() {
        return DigestAlgorithms.getDigest(this.digestAlgorithmOid);
    }

    public String getDigestEncryptionAlgorithmOid() {
        return this.digestEncryptionAlgorithmOid;
    }

    public String getDigestAlgorithm() {
        return getHashAlgorithm() + "with" + getEncryptionAlgorithm();
    }

    public void setExternalDigest(byte[] digest2, byte[] rsaData2, String digestEncryptionAlgorithm) {
        this.externalDigest = digest2;
        this.externalRsaData = rsaData2;
        if (digestEncryptionAlgorithm == null) {
            return;
        }
        if (digestEncryptionAlgorithm.equals("RSA")) {
            this.digestEncryptionAlgorithmOid = SecurityIDs.ID_RSA;
        } else if (digestEncryptionAlgorithm.equals("DSA")) {
            this.digestEncryptionAlgorithmOid = SecurityIDs.ID_DSA;
        } else if (digestEncryptionAlgorithm.equals("ECDSA")) {
            this.digestEncryptionAlgorithmOid = SecurityIDs.ID_ECDSA;
        } else {
            throw new PdfException(PdfException.UnknownKeyAlgorithm1).setMessageParams(digestEncryptionAlgorithm);
        }
    }

    private Signature initSignature(PrivateKey key) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        Signature signature = SignUtils.getSignatureHelper(getDigestAlgorithm(), this.provider);
        signature.initSign(key);
        return signature;
    }

    private Signature initSignature(PublicKey key) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        String digestAlgorithm = getDigestAlgorithm();
        if (PdfName.Adbe_x509_rsa_sha1.equals(getFilterSubtype())) {
            digestAlgorithm = "SHA1withRSA";
        }
        Signature signature = SignUtils.getSignatureHelper(digestAlgorithm, this.provider);
        signature.initVerify(key);
        return signature;
    }

    public void update(byte[] buf, int off, int len) throws SignatureException {
        if (this.rsaData == null && this.digestAttr == null && !this.isTsp) {
            this.sig.update(buf, off, len);
        } else {
            this.messageDigest.update(buf, off, len);
        }
    }

    public byte[] getEncodedPKCS1() {
        try {
            byte[] bArr = this.externalDigest;
            if (bArr != null) {
                this.digest = bArr;
            } else {
                this.digest = this.sig.sign();
            }
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            ASN1OutputStream dout = new ASN1OutputStream(bOut);
            dout.writeObject((ASN1Primitive) new DEROctetString(this.digest));
            dout.close();
            return bOut.toByteArray();
        } catch (Exception e) {
            throw new PdfException((Throwable) e);
        }
    }

    public byte[] getEncodedPKCS7() {
        return getEncodedPKCS7((byte[]) null, (ITSAClient) null, (byte[]) null, (Collection<byte[]>) null, PdfSigner.CryptoStandard.CMS);
    }

    public byte[] getEncodedPKCS7(byte[] secondDigest) {
        return getEncodedPKCS7(secondDigest, (ITSAClient) null, (byte[]) null, (Collection<byte[]>) null, PdfSigner.CryptoStandard.CMS);
    }

    @Deprecated
    public byte[] getEncodedPKCS7(byte[] secondDigest, ITSAClient tsaClient, byte[] ocsp, Collection<byte[]> crlBytes, PdfSigner.CryptoStandard sigtype) {
        return getEncodedPKCS7(secondDigest, sigtype, tsaClient, (Collection<byte[]>) ocsp != null ? Collections.singleton(ocsp) : null, crlBytes);
    }

    public byte[] getEncodedPKCS7(byte[] secondDigest, PdfSigner.CryptoStandard sigtype, ITSAClient tsaClient, Collection<byte[]> ocsp, Collection<byte[]> crlBytes) {
        byte[] tsToken;
        ASN1EncodableVector unauthAttributes;
        byte[] bArr = secondDigest;
        ITSAClient iTSAClient = tsaClient;
        try {
            byte[] bArr2 = this.externalDigest;
            if (bArr2 != null) {
                this.digest = bArr2;
                if (this.rsaData != null) {
                    this.rsaData = this.externalRsaData;
                }
            } else {
                byte[] bArr3 = this.externalRsaData;
                if (bArr3 == null || this.rsaData == null) {
                    if (this.rsaData != null) {
                        byte[] digest2 = this.messageDigest.digest();
                        this.rsaData = digest2;
                        this.sig.update(digest2);
                    }
                    this.digest = this.sig.sign();
                } else {
                    this.rsaData = bArr3;
                    this.sig.update(bArr3);
                    this.digest = this.sig.sign();
                }
            }
            ASN1EncodableVector digestAlgorithms = new ASN1EncodableVector();
            for (String next : this.digestalgos) {
                ASN1EncodableVector algos = new ASN1EncodableVector();
                algos.add(new ASN1ObjectIdentifier(next));
                algos.add(DERNull.INSTANCE);
                digestAlgorithms.add(new DERSequence(algos));
            }
            ASN1EncodableVector v = new ASN1EncodableVector();
            v.add(new ASN1ObjectIdentifier(SecurityIDs.ID_PKCS7_DATA));
            if (this.rsaData != null) {
                v.add(new DERTaggedObject(0, new DEROctetString(this.rsaData)));
            }
            DERSequence contentinfo = new DERSequence(v);
            ASN1EncodableVector v2 = new ASN1EncodableVector();
            Iterator<Certificate> it = this.certs.iterator();
            while (it.hasNext()) {
                v2.add(new ASN1InputStream((InputStream) new ByteArrayInputStream(((X509Certificate) it.next()).getEncoded())).readObject());
            }
            DERSet dercertificates = new DERSet(v2);
            ASN1EncodableVector signerinfo = new ASN1EncodableVector();
            signerinfo.add(new ASN1Integer((long) this.signerversion));
            ASN1EncodableVector v3 = new ASN1EncodableVector();
            v3.add(CertificateInfo.getIssuer(this.signCert.getTBSCertificate()));
            v3.add(new ASN1Integer(this.signCert.getSerialNumber()));
            signerinfo.add(new DERSequence(v3));
            ASN1EncodableVector v4 = new ASN1EncodableVector();
            v4.add(new ASN1ObjectIdentifier(this.digestAlgorithmOid));
            v4.add(DERNull.INSTANCE);
            signerinfo.add(new DERSequence(v4));
            if (bArr != null) {
                try {
                    signerinfo.add(new DERTaggedObject(false, 0, getAuthenticatedAttributeSet(bArr, ocsp, crlBytes, sigtype)));
                } catch (Exception e) {
                    e = e;
                    throw new PdfException((Throwable) e);
                }
            } else {
                PdfSigner.CryptoStandard cryptoStandard = sigtype;
                Collection<byte[]> collection = ocsp;
                Collection<byte[]> collection2 = crlBytes;
            }
            ASN1EncodableVector v5 = new ASN1EncodableVector();
            v5.add(new ASN1ObjectIdentifier(this.digestEncryptionAlgorithmOid));
            v5.add(DERNull.INSTANCE);
            signerinfo.add(new DERSequence(v5));
            signerinfo.add(new DEROctetString(this.digest));
            if (!(iTSAClient == null || (tsToken = iTSAClient.getTimeStampToken(tsaClient.getMessageDigest().digest(this.digest))) == null || (unauthAttributes = buildUnauthenticatedAttributes(tsToken)) == null)) {
                signerinfo.add(new DERTaggedObject(false, 1, new DERSet(unauthAttributes)));
            }
            ASN1EncodableVector body = new ASN1EncodableVector();
            body.add(new ASN1Integer((long) this.version));
            body.add(new DERSet(digestAlgorithms));
            body.add(contentinfo);
            body.add(new DERTaggedObject(false, 0, dercertificates));
            body.add(new DERSet((ASN1Encodable) new DERSequence(signerinfo)));
            ASN1EncodableVector whole = new ASN1EncodableVector();
            whole.add(new ASN1ObjectIdentifier(SecurityIDs.ID_PKCS7_SIGNED_DATA));
            whole.add(new DERTaggedObject(0, new DERSequence(body)));
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            ASN1OutputStream dout = new ASN1OutputStream(bOut);
            dout.writeObject((ASN1Primitive) new DERSequence(whole));
            dout.close();
            return bOut.toByteArray();
        } catch (Exception e2) {
            e = e2;
            PdfSigner.CryptoStandard cryptoStandard2 = sigtype;
            Collection<byte[]> collection3 = ocsp;
            Collection<byte[]> collection4 = crlBytes;
            throw new PdfException((Throwable) e);
        }
    }

    private ASN1EncodableVector buildUnauthenticatedAttributes(byte[] timeStampToken2) throws IOException {
        if (timeStampToken2 == null) {
            return null;
        }
        ASN1InputStream tempstream = new ASN1InputStream((InputStream) new ByteArrayInputStream(timeStampToken2));
        ASN1EncodableVector unauthAttributes = new ASN1EncodableVector();
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.add(new ASN1ObjectIdentifier("1.2.840.113549.1.9.16.2.14"));
        v.add(new DERSet((ASN1Encodable) (ASN1Sequence) tempstream.readObject()));
        unauthAttributes.add(new DERSequence(v));
        return unauthAttributes;
    }

    @Deprecated
    public byte[] getAuthenticatedAttributeBytes(byte[] secondDigest, byte[] ocsp, Collection<byte[]> crlBytes, PdfSigner.CryptoStandard sigtype) {
        return getAuthenticatedAttributeBytes(secondDigest, sigtype, (Collection<byte[]>) ocsp != null ? Collections.singleton(ocsp) : null, crlBytes);
    }

    public byte[] getAuthenticatedAttributeBytes(byte[] secondDigest, PdfSigner.CryptoStandard sigtype, Collection<byte[]> ocsp, Collection<byte[]> crlBytes) {
        try {
            return getAuthenticatedAttributeSet(secondDigest, ocsp, crlBytes, sigtype).getEncoded(ASN1Encoding.DER);
        } catch (Exception e) {
            throw new PdfException((Throwable) e);
        }
    }

    private DERSet getAuthenticatedAttributeSet(byte[] secondDigest, Collection<byte[]> ocsp, Collection<byte[]> crlBytes, PdfSigner.CryptoStandard sigtype) {
        try {
            ASN1EncodableVector attribute = new ASN1EncodableVector();
            ASN1EncodableVector v = new ASN1EncodableVector();
            v.add(new ASN1ObjectIdentifier(SecurityIDs.ID_CONTENT_TYPE));
            v.add(new DERSet((ASN1Encodable) new ASN1ObjectIdentifier(SecurityIDs.ID_PKCS7_DATA)));
            attribute.add(new DERSequence(v));
            ASN1EncodableVector v2 = new ASN1EncodableVector();
            v2.add(new ASN1ObjectIdentifier(SecurityIDs.ID_MESSAGE_DIGEST));
            try {
                v2.add(new DERSet((ASN1Encodable) new DEROctetString(secondDigest)));
                attribute.add(new DERSequence(v2));
                boolean haveCrl = false;
                if (crlBytes != null) {
                    Iterator<byte[]> it = crlBytes.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        } else if (it.next() != null) {
                            haveCrl = true;
                            break;
                        }
                    }
                }
                if ((ocsp == null || ocsp.isEmpty()) && !haveCrl) {
                } else {
                    ASN1EncodableVector v3 = new ASN1EncodableVector();
                    v3.add(new ASN1ObjectIdentifier(SecurityIDs.ID_ADBE_REVOCATION));
                    ASN1EncodableVector revocationV = new ASN1EncodableVector();
                    int i = 0;
                    if (haveCrl) {
                        ASN1EncodableVector v22 = new ASN1EncodableVector();
                        for (byte[] bCrl : crlBytes) {
                            if (bCrl != null) {
                                v22.add(new ASN1InputStream((InputStream) new ByteArrayInputStream(bCrl)).readObject());
                            }
                        }
                        revocationV.add(new DERTaggedObject(true, 0, new DERSequence(v22)));
                    }
                    if (ocsp == null || ocsp.isEmpty()) {
                    } else {
                        ASN1EncodableVector vo1 = new ASN1EncodableVector();
                        for (byte[] ocspBytes : ocsp) {
                            DEROctetString doctet = new DEROctetString(ocspBytes);
                            ASN1EncodableVector v23 = new ASN1EncodableVector();
                            v23.add(OCSPObjectIdentifiers.id_pkix_ocsp_basic);
                            v23.add(doctet);
                            ASN1Enumerated den = new ASN1Enumerated(i);
                            ASN1EncodableVector v32 = new ASN1EncodableVector();
                            v32.add(den);
                            v32.add(new DERTaggedObject(true, 0, new DERSequence(v23)));
                            vo1.add(new DERSequence(v32));
                            haveCrl = haveCrl;
                            i = 0;
                        }
                        revocationV.add(new DERTaggedObject(true, 1, new DERSequence(vo1)));
                    }
                    v3.add(new DERSet((ASN1Encodable) new DERSequence(revocationV)));
                    attribute.add(new DERSequence(v3));
                }
                if (sigtype == PdfSigner.CryptoStandard.CADES) {
                    try {
                        ASN1EncodableVector v4 = new ASN1EncodableVector();
                        v4.add(new ASN1ObjectIdentifier(SecurityIDs.ID_AA_SIGNING_CERTIFICATE_V2));
                        ASN1EncodableVector aaV2 = new ASN1EncodableVector();
                        aaV2.add(new AlgorithmIdentifier(new ASN1ObjectIdentifier(this.digestAlgorithmOid), (ASN1Encodable) null));
                        aaV2.add(new DEROctetString(SignUtils.getMessageDigest(getHashAlgorithm(), this.interfaceDigest).digest(this.signCert.getEncoded())));
                        v4.add(new DERSet((ASN1Encodable) new DERSequence((ASN1Encodable) new DERSequence((ASN1Encodable) new DERSequence(aaV2)))));
                        attribute.add(new DERSequence(v4));
                    } catch (Exception e) {
                        e = e;
                        throw new PdfException((Throwable) e);
                    }
                }
                if (this.signaturePolicyIdentifier != null) {
                    attribute.add(new Attribute(PKCSObjectIdentifiers.id_aa_ets_sigPolicyId, new DERSet((ASN1Encodable) this.signaturePolicyIdentifier)));
                }
                return new DERSet(attribute);
            } catch (Exception e2) {
                e = e2;
                PdfSigner.CryptoStandard cryptoStandard = sigtype;
                throw new PdfException((Throwable) e);
            }
        } catch (Exception e3) {
            e = e3;
            byte[] bArr = secondDigest;
            PdfSigner.CryptoStandard cryptoStandard2 = sigtype;
            throw new PdfException((Throwable) e);
        }
    }

    @Deprecated
    public boolean verify() throws GeneralSecurityException {
        return verifySignatureIntegrityAndAuthenticity();
    }

    public boolean verifySignatureIntegrityAndAuthenticity() throws GeneralSecurityException {
        if (this.verified) {
            return this.verifyResult;
        }
        if (this.isTsp) {
            this.verifyResult = Arrays.equals(this.messageDigest.digest(), this.timeStampToken.getTimeStampInfo().toASN1Structure().getMessageImprint().getHashedMessage());
        } else if (this.sigAttr == null && this.sigAttrDer == null) {
            if (this.rsaData != null) {
                this.sig.update(this.messageDigest.digest());
            }
            this.verifyResult = this.sig.verify(this.digest);
        } else {
            byte[] msgDigestBytes = this.messageDigest.digest();
            boolean verifyRSAdata = true;
            boolean encContDigestCompare = false;
            byte[] bArr = this.rsaData;
            if (bArr != null) {
                verifyRSAdata = Arrays.equals(msgDigestBytes, bArr);
                this.encContDigest.update(this.rsaData);
                encContDigestCompare = Arrays.equals(this.encContDigest.digest(), this.digestAttr);
            }
            boolean z = false;
            boolean concludingDigestCompare = Arrays.equals(msgDigestBytes, this.digestAttr) || encContDigestCompare;
            boolean sigVerify = verifySigAttributes(this.sigAttr) || verifySigAttributes(this.sigAttrDer);
            if (concludingDigestCompare && sigVerify && verifyRSAdata) {
                z = true;
            }
            this.verifyResult = z;
        }
        this.verified = true;
        return this.verifyResult;
    }

    private boolean verifySigAttributes(byte[] attr) throws GeneralSecurityException {
        Signature signature = initSignature(this.signCert.getPublicKey());
        signature.update(attr);
        return signature.verify(this.digest);
    }

    public boolean verifyTimestampImprint() throws GeneralSecurityException {
        TimeStampToken timeStampToken2 = this.timeStampToken;
        if (timeStampToken2 == null) {
            return false;
        }
        TimeStampTokenInfo info = timeStampToken2.getTimeStampInfo();
        return Arrays.equals(SignUtils.getMessageDigest(DigestAlgorithms.getDigest(info.getHashAlgorithm().getAlgorithm().getId())).digest(this.digest), info.toASN1Structure().getMessageImprint().getHashedMessage());
    }

    public Certificate[] getCertificates() {
        Collection<Certificate> collection = this.certs;
        return (Certificate[]) collection.toArray(new X509Certificate[collection.size()]);
    }

    public Certificate[] getSignCertificateChain() {
        Collection<Certificate> collection = this.signCerts;
        return (Certificate[]) collection.toArray(new X509Certificate[collection.size()]);
    }

    public X509Certificate getSigningCertificate() {
        return this.signCert;
    }

    private void signCertificateChain() {
        List<Certificate> cc = new ArrayList<>();
        cc.add(this.signCert);
        List<Certificate> oc = new ArrayList<>(this.certs);
        int k = 0;
        while (k < oc.size()) {
            if (this.signCert.equals(oc.get(k))) {
                oc.remove(k);
                k--;
            }
            k++;
        }
        boolean found = true;
        while (found) {
            X509Certificate v = (X509Certificate) cc.get(cc.size() - 1);
            found = false;
            int k2 = 0;
            while (true) {
                if (k2 >= oc.size()) {
                    break;
                } else if (SignUtils.verifyCertificateSignature(v, ((X509Certificate) oc.get(k2)).getPublicKey(), this.provider)) {
                    found = true;
                    cc.add(oc.get(k2));
                    oc.remove(k2);
                    break;
                } else {
                    k2++;
                }
            }
        }
        this.signCerts = cc;
    }

    public Collection<CRL> getCRLs() {
        return this.crls;
    }

    private void findCRL(ASN1Sequence seq) {
        try {
            this.crls = new ArrayList();
            for (int k = 0; k < seq.size(); k++) {
                this.crls.add((X509CRL) SignUtils.parseCrlFromStream(new ByteArrayInputStream(seq.getObjectAt(k).toASN1Primitive().getEncoded(ASN1Encoding.DER))));
            }
        } catch (Exception e) {
        }
    }

    public BasicOCSPResp getOcsp() {
        return this.basicResp;
    }

    public boolean isRevocationValid() {
        if (this.basicResp == null || this.signCerts.size() < 2) {
            return false;
        }
        try {
            CertificateID cid = this.basicResp.getResponses()[0].getCertID();
            return SignUtils.generateCertificateId(((X509Certificate[]) getSignCertificateChain())[1], getSigningCertificate().getSerialNumber(), cid.getHashAlgOID()).equals(cid);
        } catch (Exception e) {
            return false;
        }
    }

    /* JADX WARNING: type inference failed for: r1v3, types: [org.bouncycastle.asn1.ASN1Encodable] */
    /* JADX WARNING: type inference failed for: r3v7, types: [org.bouncycastle.asn1.ASN1Primitive] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void findOcsp(org.bouncycastle.asn1.ASN1Sequence r6) throws java.io.IOException {
        /*
            r5 = this;
            r0 = 0
            r1 = r0
            org.bouncycastle.cert.ocsp.BasicOCSPResp r1 = (org.bouncycastle.cert.ocsp.BasicOCSPResp) r1
            r5.basicResp = r0
            r0 = 0
        L_0x0007:
            r1 = 0
            org.bouncycastle.asn1.ASN1Encodable r2 = r6.getObjectAt(r1)
            boolean r2 = r2 instanceof org.bouncycastle.asn1.ASN1ObjectIdentifier
            if (r2 == 0) goto L_0x0047
            org.bouncycastle.asn1.ASN1Encodable r2 = r6.getObjectAt(r1)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = (org.bouncycastle.asn1.ASN1ObjectIdentifier) r2
            java.lang.String r2 = r2.getId()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers.id_pkix_ocsp_basic
            java.lang.String r3 = r3.getId()
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0047
            r1 = 1
            org.bouncycastle.asn1.ASN1Encodable r1 = r6.getObjectAt(r1)
            org.bouncycastle.asn1.ASN1OctetString r1 = (org.bouncycastle.asn1.ASN1OctetString) r1
            org.bouncycastle.asn1.ASN1InputStream r2 = new org.bouncycastle.asn1.ASN1InputStream
            byte[] r3 = r1.getOctets()
            r2.<init>((byte[]) r3)
            org.bouncycastle.asn1.ASN1Primitive r3 = r2.readObject()
            org.bouncycastle.asn1.ocsp.BasicOCSPResponse r3 = org.bouncycastle.asn1.ocsp.BasicOCSPResponse.getInstance(r3)
            org.bouncycastle.cert.ocsp.BasicOCSPResp r4 = new org.bouncycastle.cert.ocsp.BasicOCSPResp
            r4.<init>(r3)
            r5.basicResp = r4
            return
        L_0x0047:
            r0 = 1
            r2 = 0
        L_0x0049:
            int r3 = r6.size()
            if (r2 >= r3) goto L_0x0083
            org.bouncycastle.asn1.ASN1Encodable r3 = r6.getObjectAt(r2)
            boolean r3 = r3 instanceof org.bouncycastle.asn1.ASN1Sequence
            if (r3 == 0) goto L_0x0060
            org.bouncycastle.asn1.ASN1Encodable r1 = r6.getObjectAt(r1)
            r6 = r1
            org.bouncycastle.asn1.ASN1Sequence r6 = (org.bouncycastle.asn1.ASN1Sequence) r6
            r0 = 0
            goto L_0x0083
        L_0x0060:
            org.bouncycastle.asn1.ASN1Encodable r3 = r6.getObjectAt(r2)
            boolean r3 = r3 instanceof org.bouncycastle.asn1.ASN1TaggedObject
            if (r3 == 0) goto L_0x0080
            org.bouncycastle.asn1.ASN1Encodable r1 = r6.getObjectAt(r2)
            org.bouncycastle.asn1.ASN1TaggedObject r1 = (org.bouncycastle.asn1.ASN1TaggedObject) r1
            org.bouncycastle.asn1.ASN1Primitive r3 = r1.getObject()
            boolean r3 = r3 instanceof org.bouncycastle.asn1.ASN1Sequence
            if (r3 == 0) goto L_0x007f
            org.bouncycastle.asn1.ASN1Primitive r3 = r1.getObject()
            r6 = r3
            org.bouncycastle.asn1.ASN1Sequence r6 = (org.bouncycastle.asn1.ASN1Sequence) r6
            r0 = 0
            goto L_0x0083
        L_0x007f:
            return
        L_0x0080:
            int r2 = r2 + 1
            goto L_0x0049
        L_0x0083:
            if (r0 == 0) goto L_0x0007
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.signatures.PdfPKCS7.findOcsp(org.bouncycastle.asn1.ASN1Sequence):void");
    }

    public boolean isTsp() {
        return this.isTsp;
    }

    public TimeStampToken getTimeStampToken() {
        return this.timeStampToken;
    }

    public Calendar getTimeStampDate() {
        TimeStampToken timeStampToken2 = this.timeStampToken;
        if (timeStampToken2 == null) {
            return (Calendar) TimestampConstants.UNDEFINED_TIMESTAMP_DATE;
        }
        return SignUtils.getTimeStampDate(timeStampToken2);
    }

    public PdfName getFilterSubtype() {
        return this.filterSubtype;
    }

    public String getEncryptionAlgorithm() {
        String encryptAlgo = EncryptionAlgorithms.getAlgorithm(this.digestEncryptionAlgorithmOid);
        if (encryptAlgo == null) {
            return this.digestEncryptionAlgorithmOid;
        }
        return encryptAlgo;
    }
}
