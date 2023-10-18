package org.bouncycastle.jcajce.provider.asymmetric.x509;

import com.itextpdf.signatures.OID;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateParsingException;
import java.util.Date;
import java.util.Enumeration;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;

class X509CertificateObject extends X509CertificateImpl implements PKCS12BagAttributeCarrier {
    private PKCS12BagAttributeCarrier attrCarrier = new PKCS12BagAttributeCarrierImpl();
    private final Object cacheLock = new Object();
    private volatile int hashValue;
    private volatile boolean hashValueSet;
    private X509CertificateInternal internalCertificateValue;
    private X500Principal issuerValue;
    private PublicKey publicKeyValue;
    private X500Principal subjectValue;
    private long[] validityValues;

    X509CertificateObject(JcaJceHelper jcaJceHelper, Certificate certificate) throws CertificateParsingException {
        super(jcaJceHelper, certificate, createBasicConstraints(certificate), createKeyUsage(certificate));
    }

    private static BasicConstraints createBasicConstraints(Certificate certificate) throws CertificateParsingException {
        try {
            byte[] extensionOctets = getExtensionOctets(certificate, OID.X509Extensions.BASIC_CONSTRAINTS);
            if (extensionOctets == null) {
                return null;
            }
            return BasicConstraints.getInstance(ASN1Primitive.fromByteArray(extensionOctets));
        } catch (Exception e) {
            throw new CertificateParsingException("cannot construct BasicConstraints: " + e);
        }
    }

    private static boolean[] createKeyUsage(Certificate certificate) throws CertificateParsingException {
        try {
            byte[] extensionOctets = getExtensionOctets(certificate, OID.X509Extensions.KEY_USAGE);
            if (extensionOctets == null) {
                return null;
            }
            DERBitString instance = DERBitString.getInstance(ASN1Primitive.fromByteArray(extensionOctets));
            byte[] bytes = instance.getBytes();
            int length = (bytes.length * 8) - instance.getPadBits();
            int i = 9;
            if (length >= 9) {
                i = length;
            }
            boolean[] zArr = new boolean[i];
            for (int i2 = 0; i2 != length; i2++) {
                zArr[i2] = (bytes[i2 / 8] & (128 >>> (i2 % 8))) != 0;
            }
            return zArr;
        } catch (Exception e) {
            throw new CertificateParsingException("cannot construct KeyUsage: " + e);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0011, code lost:
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:?, code lost:
        r0 = getEncoded();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateInternal getInternalCertificate() {
        /*
            r7 = this;
            java.lang.Object r0 = r7.cacheLock
            monitor-enter(r0)
            org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateInternal r1 = r7.internalCertificateValue     // Catch:{ all -> 0x0031 }
            if (r1 == 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x0031 }
            return r1
        L_0x0009:
            monitor-exit(r0)     // Catch:{ all -> 0x0031 }
            byte[] r0 = r7.getEncoded()     // Catch:{ CertificateEncodingException -> 0x0010 }
        L_0x000e:
            r6 = r0
            goto L_0x0013
        L_0x0010:
            r0 = move-exception
            r0 = 0
            goto L_0x000e
        L_0x0013:
            org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateInternal r0 = new org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateInternal
            org.bouncycastle.jcajce.util.JcaJceHelper r2 = r7.bcHelper
            org.bouncycastle.asn1.x509.Certificate r3 = r7.f665c
            org.bouncycastle.asn1.x509.BasicConstraints r4 = r7.basicConstraints
            boolean[] r5 = r7.keyUsage
            r1 = r0
            r1.<init>(r2, r3, r4, r5, r6)
            java.lang.Object r1 = r7.cacheLock
            monitor-enter(r1)
            org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateInternal r2 = r7.internalCertificateValue     // Catch:{ all -> 0x002e }
            if (r2 != 0) goto L_0x002a
            r7.internalCertificateValue = r0     // Catch:{ all -> 0x002e }
        L_0x002a:
            org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateInternal r0 = r7.internalCertificateValue     // Catch:{ all -> 0x002e }
            monitor-exit(r1)     // Catch:{ all -> 0x002e }
            return r0
        L_0x002e:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x002e }
            throw r0
        L_0x0031:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0031 }
            goto L_0x0035
        L_0x0034:
            throw r1
        L_0x0035:
            goto L_0x0034
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateObject.getInternalCertificate():org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateInternal");
    }

    public void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {
        long time = date.getTime();
        long[] validityValues2 = getValidityValues();
        if (time > validityValues2[1]) {
            throw new CertificateExpiredException("certificate expired on " + this.f665c.getEndDate().getTime());
        } else if (time < validityValues2[0]) {
            throw new CertificateNotYetValidException("certificate not valid till " + this.f665c.getStartDate().getTime());
        }
    }

    public boolean equals(Object obj) {
        DERBitString signature;
        if (obj == this) {
            return true;
        }
        if (obj instanceof X509CertificateObject) {
            X509CertificateObject x509CertificateObject = (X509CertificateObject) obj;
            if (!this.hashValueSet || !x509CertificateObject.hashValueSet) {
                if ((this.internalCertificateValue == null || x509CertificateObject.internalCertificateValue == null) && (signature = this.f665c.getSignature()) != null && !signature.equals((ASN1Primitive) x509CertificateObject.f665c.getSignature())) {
                    return false;
                }
            } else if (this.hashValue != x509CertificateObject.hashValue) {
                return false;
            }
        }
        return getInternalCertificate().equals(obj);
    }

    public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return this.attrCarrier.getBagAttribute(aSN1ObjectIdentifier);
    }

    public Enumeration getBagAttributeKeys() {
        return this.attrCarrier.getBagAttributeKeys();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0013, code lost:
        if (r3.issuerValue != null) goto L_0x0017;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0015, code lost:
        r3.issuerValue = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0017, code lost:
        r0 = r3.issuerValue;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0019, code lost:
        monitor-exit(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x001a, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000a, code lost:
        r0 = super.getIssuerX500Principal();
        r1 = r3.cacheLock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0010, code lost:
        monitor-enter(r1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public javax.security.auth.x500.X500Principal getIssuerX500Principal() {
        /*
            r3 = this;
            java.lang.Object r0 = r3.cacheLock
            monitor-enter(r0)
            javax.security.auth.x500.X500Principal r1 = r3.issuerValue     // Catch:{ all -> 0x001e }
            if (r1 == 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x001e }
            return r1
        L_0x0009:
            monitor-exit(r0)     // Catch:{ all -> 0x001e }
            javax.security.auth.x500.X500Principal r0 = super.getIssuerX500Principal()
            java.lang.Object r1 = r3.cacheLock
            monitor-enter(r1)
            javax.security.auth.x500.X500Principal r2 = r3.issuerValue     // Catch:{ all -> 0x001b }
            if (r2 != 0) goto L_0x0017
            r3.issuerValue = r0     // Catch:{ all -> 0x001b }
        L_0x0017:
            javax.security.auth.x500.X500Principal r0 = r3.issuerValue     // Catch:{ all -> 0x001b }
            monitor-exit(r1)     // Catch:{ all -> 0x001b }
            return r0
        L_0x001b:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x001b }
            throw r0
        L_0x001e:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x001e }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateObject.getIssuerX500Principal():javax.security.auth.x500.X500Principal");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0010, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0012, code lost:
        r1 = r3.cacheLock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0014, code lost:
        monitor-enter(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0017, code lost:
        if (r3.publicKeyValue != null) goto L_0x001b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0019, code lost:
        r3.publicKeyValue = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x001b, code lost:
        r0 = r3.publicKeyValue;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x001d, code lost:
        monitor-exit(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x001e, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000a, code lost:
        r0 = super.getPublicKey();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x000e, code lost:
        if (r0 != null) goto L_0x0012;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.security.PublicKey getPublicKey() {
        /*
            r3 = this;
            java.lang.Object r0 = r3.cacheLock
            monitor-enter(r0)
            java.security.PublicKey r1 = r3.publicKeyValue     // Catch:{ all -> 0x0022 }
            if (r1 == 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x0022 }
            return r1
        L_0x0009:
            monitor-exit(r0)     // Catch:{ all -> 0x0022 }
            java.security.PublicKey r0 = super.getPublicKey()
            if (r0 != 0) goto L_0x0012
            r0 = 0
            return r0
        L_0x0012:
            java.lang.Object r1 = r3.cacheLock
            monitor-enter(r1)
            java.security.PublicKey r2 = r3.publicKeyValue     // Catch:{ all -> 0x001f }
            if (r2 != 0) goto L_0x001b
            r3.publicKeyValue = r0     // Catch:{ all -> 0x001f }
        L_0x001b:
            java.security.PublicKey r0 = r3.publicKeyValue     // Catch:{ all -> 0x001f }
            monitor-exit(r1)     // Catch:{ all -> 0x001f }
            return r0
        L_0x001f:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x001f }
            throw r0
        L_0x0022:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0022 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateObject.getPublicKey():java.security.PublicKey");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0013, code lost:
        if (r3.subjectValue != null) goto L_0x0017;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0015, code lost:
        r3.subjectValue = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0017, code lost:
        r0 = r3.subjectValue;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0019, code lost:
        monitor-exit(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x001a, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000a, code lost:
        r0 = super.getSubjectX500Principal();
        r1 = r3.cacheLock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0010, code lost:
        monitor-enter(r1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public javax.security.auth.x500.X500Principal getSubjectX500Principal() {
        /*
            r3 = this;
            java.lang.Object r0 = r3.cacheLock
            monitor-enter(r0)
            javax.security.auth.x500.X500Principal r1 = r3.subjectValue     // Catch:{ all -> 0x001e }
            if (r1 == 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x001e }
            return r1
        L_0x0009:
            monitor-exit(r0)     // Catch:{ all -> 0x001e }
            javax.security.auth.x500.X500Principal r0 = super.getSubjectX500Principal()
            java.lang.Object r1 = r3.cacheLock
            monitor-enter(r1)
            javax.security.auth.x500.X500Principal r2 = r3.subjectValue     // Catch:{ all -> 0x001b }
            if (r2 != 0) goto L_0x0017
            r3.subjectValue = r0     // Catch:{ all -> 0x001b }
        L_0x0017:
            javax.security.auth.x500.X500Principal r0 = r3.subjectValue     // Catch:{ all -> 0x001b }
            monitor-exit(r1)     // Catch:{ all -> 0x001b }
            return r0
        L_0x001b:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x001b }
            throw r0
        L_0x001e:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x001e }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateObject.getSubjectX500Principal():javax.security.auth.x500.X500Principal");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0028, code lost:
        if (r4.validityValues != null) goto L_0x002c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002a, code lost:
        r4.validityValues = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002c, code lost:
        r0 = r4.validityValues;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002e, code lost:
        monitor-exit(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002f, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000a, code lost:
        r0 = new long[]{super.getNotBefore().getTime(), super.getNotAfter().getTime()};
        r1 = r4.cacheLock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0025, code lost:
        monitor-enter(r1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long[] getValidityValues() {
        /*
            r4 = this;
            java.lang.Object r0 = r4.cacheLock
            monitor-enter(r0)
            long[] r1 = r4.validityValues     // Catch:{ all -> 0x0033 }
            if (r1 == 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x0033 }
            return r1
        L_0x0009:
            monitor-exit(r0)     // Catch:{ all -> 0x0033 }
            r0 = 2
            long[] r0 = new long[r0]
            java.util.Date r1 = super.getNotBefore()
            long r1 = r1.getTime()
            r3 = 0
            r0[r3] = r1
            java.util.Date r1 = super.getNotAfter()
            long r1 = r1.getTime()
            r3 = 1
            r0[r3] = r1
            java.lang.Object r1 = r4.cacheLock
            monitor-enter(r1)
            long[] r2 = r4.validityValues     // Catch:{ all -> 0x0030 }
            if (r2 != 0) goto L_0x002c
            r4.validityValues = r0     // Catch:{ all -> 0x0030 }
        L_0x002c:
            long[] r0 = r4.validityValues     // Catch:{ all -> 0x0030 }
            monitor-exit(r1)     // Catch:{ all -> 0x0030 }
            return r0
        L_0x0030:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0030 }
            throw r0
        L_0x0033:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0033 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateObject.getValidityValues():long[]");
    }

    public int hashCode() {
        if (!this.hashValueSet) {
            this.hashValue = getInternalCertificate().hashCode();
            this.hashValueSet = true;
        }
        return this.hashValue;
    }

    public int originalHashCode() {
        try {
            byte[] encoded = getInternalCertificate().getEncoded();
            int i = 0;
            for (int i2 = 1; i2 < encoded.length; i2++) {
                i += encoded[i2] * i2;
            }
            return i;
        } catch (CertificateEncodingException e) {
            return 0;
        }
    }

    public void setBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.attrCarrier.setBagAttribute(aSN1ObjectIdentifier, aSN1Encodable);
    }
}
