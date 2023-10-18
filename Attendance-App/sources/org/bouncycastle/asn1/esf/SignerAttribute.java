package org.bouncycastle.asn1.esf;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.Attribute;
import org.bouncycastle.asn1.x509.AttributeCertificate;

public class SignerAttribute extends ASN1Object {
    private Object[] values;

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v6, resolved type: java.lang.Object[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private SignerAttribute(org.bouncycastle.asn1.ASN1Sequence r8) {
        /*
            r7 = this;
            r7.<init>()
            int r0 = r8.size()
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r7.values = r0
            java.util.Enumeration r8 = r8.getObjects()
            r0 = 0
            r1 = 0
        L_0x0011:
            boolean r2 = r8.hasMoreElements()
            if (r2 == 0) goto L_0x0077
            java.lang.Object r2 = r8.nextElement()
            org.bouncycastle.asn1.ASN1TaggedObject r2 = org.bouncycastle.asn1.ASN1TaggedObject.getInstance(r2)
            int r3 = r2.getTagNo()
            r4 = 1
            if (r3 != 0) goto L_0x0045
            org.bouncycastle.asn1.ASN1Sequence r2 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r2, r4)
            int r3 = r2.size()
            org.bouncycastle.asn1.x509.Attribute[] r4 = new org.bouncycastle.asn1.x509.Attribute[r3]
            r5 = 0
        L_0x0031:
            if (r5 == r3) goto L_0x0040
            org.bouncycastle.asn1.ASN1Encodable r6 = r2.getObjectAt(r5)
            org.bouncycastle.asn1.x509.Attribute r6 = org.bouncycastle.asn1.x509.Attribute.getInstance(r6)
            r4[r5] = r6
            int r5 = r5 + 1
            goto L_0x0031
        L_0x0040:
            java.lang.Object[] r2 = r7.values
            r2[r1] = r4
            goto L_0x0057
        L_0x0045:
            int r3 = r2.getTagNo()
            if (r3 != r4) goto L_0x005a
            java.lang.Object[] r3 = r7.values
            org.bouncycastle.asn1.ASN1Sequence r2 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r2, r4)
            org.bouncycastle.asn1.x509.AttributeCertificate r2 = org.bouncycastle.asn1.x509.AttributeCertificate.getInstance(r2)
            r3[r1] = r2
        L_0x0057:
            int r1 = r1 + 1
            goto L_0x0011
        L_0x005a:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "illegal tag: "
            java.lang.StringBuilder r0 = r0.append(r1)
            int r1 = r2.getTagNo()
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            r8.<init>(r0)
            throw r8
        L_0x0077:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.asn1.esf.SignerAttribute.<init>(org.bouncycastle.asn1.ASN1Sequence):void");
    }

    public SignerAttribute(AttributeCertificate attributeCertificate) {
        Object[] objArr = new Object[1];
        this.values = objArr;
        objArr[0] = attributeCertificate;
    }

    public SignerAttribute(Attribute[] attributeArr) {
        Object[] objArr = new Object[1];
        this.values = objArr;
        objArr[0] = attributeArr;
    }

    public static SignerAttribute getInstance(Object obj) {
        if (obj instanceof SignerAttribute) {
            return (SignerAttribute) obj;
        }
        if (obj != null) {
            return new SignerAttribute(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public Object[] getValues() {
        Object[] objArr = this.values;
        int length = objArr.length;
        Object[] objArr2 = new Object[length];
        System.arraycopy(objArr, 0, objArr2, 0, length);
        return objArr2;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(this.values.length);
        int i = 0;
        while (true) {
            Object[] objArr = this.values;
            if (i == objArr.length) {
                return new DERSequence(aSN1EncodableVector);
            }
            Object obj = objArr[i];
            aSN1EncodableVector.add(obj instanceof Attribute[] ? new DERTaggedObject(0, new DERSequence((ASN1Encodable[]) (Attribute[]) obj)) : new DERTaggedObject(1, (AttributeCertificate) obj));
            i++;
        }
    }
}
