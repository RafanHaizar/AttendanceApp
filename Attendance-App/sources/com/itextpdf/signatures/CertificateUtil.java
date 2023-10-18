package com.itextpdf.signatures;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.i18n.LocalizedMessage;

public class CertificateUtil {
    public static CRL getCRL(X509Certificate certificate) throws CertificateException, CRLException, IOException {
        return getCRL(getCRLURL(certificate));
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0019 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x001a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getCRLURL(java.security.cert.X509Certificate r16) throws java.security.cert.CertificateParsingException {
        /*
            r1 = 0
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = org.bouncycastle.asn1.x509.Extension.cRLDistributionPoints     // Catch:{ IOException -> 0x0010 }
            java.lang.String r0 = r0.getId()     // Catch:{ IOException -> 0x0010 }
            r2 = r16
            org.bouncycastle.asn1.ASN1Primitive r0 = getExtensionValue(r2, r0)     // Catch:{ IOException -> 0x000e }
            goto L_0x0017
        L_0x000e:
            r0 = move-exception
            goto L_0x0013
        L_0x0010:
            r0 = move-exception
            r2 = r16
        L_0x0013:
            r3 = r1
            org.bouncycastle.asn1.ASN1Primitive r3 = (org.bouncycastle.asn1.ASN1Primitive) r3
            r0 = r3
        L_0x0017:
            if (r0 != 0) goto L_0x001a
            return r1
        L_0x001a:
            org.bouncycastle.asn1.x509.CRLDistPoint r3 = org.bouncycastle.asn1.x509.CRLDistPoint.getInstance(r0)
            org.bouncycastle.asn1.x509.DistributionPoint[] r4 = r3.getDistributionPoints()
            int r5 = r4.length
            r6 = 0
            r7 = 0
        L_0x0025:
            if (r7 >= r5) goto L_0x0063
            r8 = r4[r7]
            org.bouncycastle.asn1.x509.DistributionPointName r9 = r8.getDistributionPoint()
            int r10 = r9.getType()
            if (r10 == 0) goto L_0x0034
            goto L_0x005f
        L_0x0034:
            org.bouncycastle.asn1.ASN1Encodable r10 = r9.getName()
            org.bouncycastle.asn1.x509.GeneralNames r10 = (org.bouncycastle.asn1.x509.GeneralNames) r10
            org.bouncycastle.asn1.x509.GeneralName[] r11 = r10.getNames()
            int r12 = r11.length
            r13 = 0
        L_0x0040:
            if (r13 >= r12) goto L_0x005f
            r14 = r11[r13]
            int r15 = r14.getTagNo()
            r1 = 6
            if (r15 == r1) goto L_0x0050
            int r13 = r13 + 1
            r1 = 0
            goto L_0x0040
        L_0x0050:
            org.bouncycastle.asn1.ASN1Primitive r1 = r14.toASN1Primitive()
            org.bouncycastle.asn1.ASN1TaggedObject r1 = (org.bouncycastle.asn1.ASN1TaggedObject) r1
            org.bouncycastle.asn1.DERIA5String r1 = org.bouncycastle.asn1.DERIA5String.getInstance(r1, r6)
            java.lang.String r5 = r1.getString()
            return r5
        L_0x005f:
            int r7 = r7 + 1
            r1 = 0
            goto L_0x0025
        L_0x0063:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.signatures.CertificateUtil.getCRLURL(java.security.cert.X509Certificate):java.lang.String");
    }

    public static CRL getCRL(String url) throws IOException, CertificateException, CRLException {
        if (url == null) {
            return null;
        }
        return SignUtils.parseCrlFromStream(new URL(url).openStream());
    }

    public static String getOCSPURL(X509Certificate certificate) {
        try {
            ASN1Primitive obj = getExtensionValue(certificate, Extension.authorityInfoAccess.getId());
            if (obj == null) {
                return null;
            }
            ASN1Sequence AccessDescriptions = (ASN1Sequence) obj;
            for (int i = 0; i < AccessDescriptions.size(); i++) {
                ASN1Sequence AccessDescription = (ASN1Sequence) AccessDescriptions.getObjectAt(i);
                if (AccessDescription.size() == 2) {
                    if ((AccessDescription.getObjectAt(0) instanceof ASN1ObjectIdentifier) && SecurityIDs.ID_OCSP.equals(((ASN1ObjectIdentifier) AccessDescription.getObjectAt(0)).getId())) {
                        String AccessLocation = getStringFromGeneralName((ASN1Primitive) AccessDescription.getObjectAt(1));
                        if (AccessLocation == null) {
                            return "";
                        }
                        return AccessLocation;
                    }
                }
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public static String getTSAURL(X509Certificate certificate) {
        byte[] der = SignUtils.getExtensionValueByOid(certificate, SecurityIDs.ID_TSA);
        if (der == null) {
            return null;
        }
        try {
            return getStringFromGeneralName(ASN1Sequence.getInstance(ASN1Primitive.fromByteArray(((DEROctetString) ASN1Primitive.fromByteArray(der)).getOctets())).getObjectAt(1).toASN1Primitive());
        } catch (IOException e) {
            return null;
        }
    }

    private static ASN1Primitive getExtensionValue(X509Certificate certificate, String oid) throws IOException {
        byte[] bytes = SignUtils.getExtensionValueByOid(certificate, oid);
        if (bytes == null) {
            return null;
        }
        return new ASN1InputStream((InputStream) new ByteArrayInputStream(((ASN1OctetString) new ASN1InputStream((InputStream) new ByteArrayInputStream(bytes)).readObject()).getOctets())).readObject();
    }

    private static String getStringFromGeneralName(ASN1Primitive names) throws IOException {
        return new String(ASN1OctetString.getInstance((ASN1TaggedObject) names, false).getOctets(), LocalizedMessage.DEFAULT_ENCODING);
    }
}
