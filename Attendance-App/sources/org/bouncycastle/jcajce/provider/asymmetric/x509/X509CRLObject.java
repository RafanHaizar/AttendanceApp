package org.bouncycastle.jcajce.provider.asymmetric.x509;

import java.security.cert.CRLException;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.x509.CertificateList;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.IssuingDistributionPoint;
import org.bouncycastle.jcajce.util.JcaJceHelper;

class X509CRLObject extends X509CRLImpl {
    private final Object cacheLock = new Object();
    private volatile int hashValue;
    private volatile boolean hashValueSet;
    private X509CRLInternal internalCRLValue;

    X509CRLObject(JcaJceHelper jcaJceHelper, CertificateList certificateList) throws CRLException {
        super(jcaJceHelper, certificateList, createSigAlgName(certificateList), createSigAlgParams(certificateList), isIndirectCRL(certificateList));
    }

    private static String createSigAlgName(CertificateList certificateList) throws CRLException {
        try {
            return X509SignatureUtil.getSignatureName(certificateList.getSignatureAlgorithm());
        } catch (Exception e) {
            throw new CRLException("CRL contents invalid: " + e);
        }
    }

    private static byte[] createSigAlgParams(CertificateList certificateList) throws CRLException {
        try {
            ASN1Encodable parameters = certificateList.getSignatureAlgorithm().getParameters();
            if (parameters == null) {
                return null;
            }
            return parameters.toASN1Primitive().getEncoded(ASN1Encoding.DER);
        } catch (Exception e) {
            throw new CRLException("CRL contents invalid: " + e);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0011, code lost:
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:?, code lost:
        r0 = getEncoded();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLInternal getInternalCRL() {
        /*
            r8 = this;
            java.lang.Object r0 = r8.cacheLock
            monitor-enter(r0)
            org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLInternal r1 = r8.internalCRLValue     // Catch:{ all -> 0x0033 }
            if (r1 == 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x0033 }
            return r1
        L_0x0009:
            monitor-exit(r0)     // Catch:{ all -> 0x0033 }
            byte[] r0 = r8.getEncoded()     // Catch:{ CRLException -> 0x0010 }
        L_0x000e:
            r7 = r0
            goto L_0x0013
        L_0x0010:
            r0 = move-exception
            r0 = 0
            goto L_0x000e
        L_0x0013:
            org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLInternal r0 = new org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLInternal
            org.bouncycastle.jcajce.util.JcaJceHelper r2 = r8.bcHelper
            org.bouncycastle.asn1.x509.CertificateList r3 = r8.f664c
            java.lang.String r4 = r8.sigAlgName
            byte[] r5 = r8.sigAlgParams
            boolean r6 = r8.isIndirect
            r1 = r0
            r1.<init>(r2, r3, r4, r5, r6, r7)
            java.lang.Object r1 = r8.cacheLock
            monitor-enter(r1)
            org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLInternal r2 = r8.internalCRLValue     // Catch:{ all -> 0x0030 }
            if (r2 != 0) goto L_0x002c
            r8.internalCRLValue = r0     // Catch:{ all -> 0x0030 }
        L_0x002c:
            org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLInternal r0 = r8.internalCRLValue     // Catch:{ all -> 0x0030 }
            monitor-exit(r1)     // Catch:{ all -> 0x0030 }
            return r0
        L_0x0030:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0030 }
            throw r0
        L_0x0033:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0033 }
            goto L_0x0037
        L_0x0036:
            throw r1
        L_0x0037:
            goto L_0x0036
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLObject.getInternalCRL():org.bouncycastle.jcajce.provider.asymmetric.x509.X509CRLInternal");
    }

    private static boolean isIndirectCRL(CertificateList certificateList) throws CRLException {
        try {
            byte[] extensionOctets = getExtensionOctets(certificateList, Extension.issuingDistributionPoint.getId());
            if (extensionOctets == null) {
                return false;
            }
            return IssuingDistributionPoint.getInstance(extensionOctets).isIndirectCRL();
        } catch (Exception e) {
            throw new ExtCRLException("Exception reading IssuingDistributionPoint", e);
        }
    }

    public boolean equals(Object obj) {
        DERBitString signature;
        if (this == obj) {
            return true;
        }
        if (obj instanceof X509CRLObject) {
            X509CRLObject x509CRLObject = (X509CRLObject) obj;
            if (!this.hashValueSet || !x509CRLObject.hashValueSet) {
                if ((this.internalCRLValue == null || x509CRLObject.internalCRLValue == null) && (signature = this.f664c.getSignature()) != null && !signature.equals((ASN1Primitive) x509CRLObject.f664c.getSignature())) {
                    return false;
                }
            } else if (this.hashValue != x509CRLObject.hashValue) {
                return false;
            }
        }
        return getInternalCRL().equals(obj);
    }

    public int hashCode() {
        if (!this.hashValueSet) {
            this.hashValue = getInternalCRL().hashCode();
            this.hashValueSet = true;
        }
        return this.hashValue;
    }
}
