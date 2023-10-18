package org.bouncycastle.asn1.p008x9;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.math.p018ec.ECAlgorithms;
import org.bouncycastle.math.p018ec.ECCurve;
import org.bouncycastle.util.Arrays;

/* renamed from: org.bouncycastle.asn1.x9.X9Curve */
public class X9Curve extends ASN1Object implements X9ObjectIdentifiers {
    private ECCurve curve;
    private ASN1ObjectIdentifier fieldIdentifier;
    private byte[] seed;

    /* JADX WARNING: type inference failed for: r2v24, types: [org.bouncycastle.math.ec.ECCurve] */
    /* JADX WARNING: type inference failed for: r7v8, types: [org.bouncycastle.math.ec.ECCurve$F2m] */
    /* JADX WARNING: type inference failed for: r6v10, types: [org.bouncycastle.math.ec.ECCurve$Fp] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public X9Curve(org.bouncycastle.asn1.p008x9.X9FieldID r17, java.math.BigInteger r18, java.math.BigInteger r19, org.bouncycastle.asn1.ASN1Sequence r20) {
        /*
            r16 = this;
            r0 = r16
            r1 = r20
            r16.<init>()
            r2 = 0
            r0.fieldIdentifier = r2
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = r17.getIdentifier()
            r0.fieldIdentifier = r2
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = prime_field
            boolean r2 = r2.equals((org.bouncycastle.asn1.ASN1Primitive) r3)
            r3 = 2
            r4 = 0
            r5 = 1
            if (r2 == 0) goto L_0x0055
            org.bouncycastle.asn1.ASN1Primitive r2 = r17.getParameters()
            org.bouncycastle.asn1.ASN1Integer r2 = (org.bouncycastle.asn1.ASN1Integer) r2
            java.math.BigInteger r7 = r2.getValue()
            java.math.BigInteger r8 = new java.math.BigInteger
            org.bouncycastle.asn1.ASN1Encodable r2 = r1.getObjectAt(r4)
            org.bouncycastle.asn1.ASN1OctetString r2 = org.bouncycastle.asn1.ASN1OctetString.getInstance(r2)
            byte[] r2 = r2.getOctets()
            r8.<init>(r5, r2)
            java.math.BigInteger r9 = new java.math.BigInteger
            org.bouncycastle.asn1.ASN1Encodable r2 = r1.getObjectAt(r5)
            org.bouncycastle.asn1.ASN1OctetString r2 = org.bouncycastle.asn1.ASN1OctetString.getInstance(r2)
            byte[] r2 = r2.getOctets()
            r9.<init>(r5, r2)
            org.bouncycastle.math.ec.ECCurve$Fp r2 = new org.bouncycastle.math.ec.ECCurve$Fp
            r6 = r2
            r10 = r18
            r11 = r19
            r6.<init>(r7, r8, r9, r10, r11)
        L_0x0051:
            r0.curve = r2
            goto L_0x00f4
        L_0x0055:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = r0.fieldIdentifier
            org.bouncycastle.asn1.ASN1ObjectIdentifier r6 = characteristic_two_field
            boolean r2 = r2.equals((org.bouncycastle.asn1.ASN1Primitive) r6)
            if (r2 == 0) goto L_0x0110
            org.bouncycastle.asn1.ASN1Primitive r2 = r17.getParameters()
            org.bouncycastle.asn1.ASN1Sequence r2 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r2)
            org.bouncycastle.asn1.ASN1Encodable r6 = r2.getObjectAt(r4)
            org.bouncycastle.asn1.ASN1Integer r6 = (org.bouncycastle.asn1.ASN1Integer) r6
            int r8 = r6.intValueExact()
            org.bouncycastle.asn1.ASN1Encodable r6 = r2.getObjectAt(r5)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r6 = (org.bouncycastle.asn1.ASN1ObjectIdentifier) r6
            org.bouncycastle.asn1.ASN1ObjectIdentifier r7 = tpBasis
            boolean r7 = r6.equals((org.bouncycastle.asn1.ASN1Primitive) r7)
            if (r7 == 0) goto L_0x008f
            org.bouncycastle.asn1.ASN1Encodable r2 = r2.getObjectAt(r3)
            org.bouncycastle.asn1.ASN1Integer r2 = org.bouncycastle.asn1.ASN1Integer.getInstance(r2)
            int r2 = r2.intValueExact()
            r9 = r2
            r10 = 0
            r11 = 0
            goto L_0x00c6
        L_0x008f:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r7 = ppBasis
            boolean r6 = r6.equals((org.bouncycastle.asn1.ASN1Primitive) r7)
            if (r6 == 0) goto L_0x0108
            org.bouncycastle.asn1.ASN1Encodable r2 = r2.getObjectAt(r3)
            org.bouncycastle.asn1.ASN1Sequence r2 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r2)
            org.bouncycastle.asn1.ASN1Encodable r6 = r2.getObjectAt(r4)
            org.bouncycastle.asn1.ASN1Integer r6 = org.bouncycastle.asn1.ASN1Integer.getInstance(r6)
            int r6 = r6.intValueExact()
            org.bouncycastle.asn1.ASN1Encodable r7 = r2.getObjectAt(r5)
            org.bouncycastle.asn1.ASN1Integer r7 = org.bouncycastle.asn1.ASN1Integer.getInstance(r7)
            int r7 = r7.intValueExact()
            org.bouncycastle.asn1.ASN1Encodable r2 = r2.getObjectAt(r3)
            org.bouncycastle.asn1.ASN1Integer r2 = org.bouncycastle.asn1.ASN1Integer.getInstance(r2)
            int r2 = r2.intValueExact()
            r11 = r2
            r9 = r6
            r10 = r7
        L_0x00c6:
            java.math.BigInteger r12 = new java.math.BigInteger
            org.bouncycastle.asn1.ASN1Encodable r2 = r1.getObjectAt(r4)
            org.bouncycastle.asn1.ASN1OctetString r2 = org.bouncycastle.asn1.ASN1OctetString.getInstance(r2)
            byte[] r2 = r2.getOctets()
            r12.<init>(r5, r2)
            java.math.BigInteger r13 = new java.math.BigInteger
            org.bouncycastle.asn1.ASN1Encodable r2 = r1.getObjectAt(r5)
            org.bouncycastle.asn1.ASN1OctetString r2 = org.bouncycastle.asn1.ASN1OctetString.getInstance(r2)
            byte[] r2 = r2.getOctets()
            r13.<init>(r5, r2)
            org.bouncycastle.math.ec.ECCurve$F2m r2 = new org.bouncycastle.math.ec.ECCurve$F2m
            r7 = r2
            r14 = r18
            r15 = r19
            r7.<init>((int) r8, (int) r9, (int) r10, (int) r11, (java.math.BigInteger) r12, (java.math.BigInteger) r13, (java.math.BigInteger) r14, (java.math.BigInteger) r15)
            goto L_0x0051
        L_0x00f4:
            int r2 = r20.size()
            r4 = 3
            if (r2 != r4) goto L_0x0107
            org.bouncycastle.asn1.ASN1Encodable r1 = r1.getObjectAt(r3)
            org.bouncycastle.asn1.DERBitString r1 = (org.bouncycastle.asn1.DERBitString) r1
            byte[] r1 = r1.getBytes()
            r0.seed = r1
        L_0x0107:
            return
        L_0x0108:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "This type of EC basis is not implemented"
            r1.<init>(r2)
            throw r1
        L_0x0110:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "This type of ECCurve is not implemented"
            r1.<init>(r2)
            goto L_0x0119
        L_0x0118:
            throw r1
        L_0x0119:
            goto L_0x0118
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.asn1.p008x9.X9Curve.<init>(org.bouncycastle.asn1.x9.X9FieldID, java.math.BigInteger, java.math.BigInteger, org.bouncycastle.asn1.ASN1Sequence):void");
    }

    public X9Curve(ECCurve eCCurve) {
        this(eCCurve, (byte[]) null);
    }

    public X9Curve(ECCurve eCCurve, byte[] bArr) {
        this.fieldIdentifier = null;
        this.curve = eCCurve;
        this.seed = Arrays.clone(bArr);
        setFieldIdentifier();
    }

    private void setFieldIdentifier() {
        ASN1ObjectIdentifier aSN1ObjectIdentifier;
        if (ECAlgorithms.isFpCurve(this.curve)) {
            aSN1ObjectIdentifier = prime_field;
        } else if (ECAlgorithms.isF2mCurve(this.curve)) {
            aSN1ObjectIdentifier = characteristic_two_field;
        } else {
            throw new IllegalArgumentException("This type of ECCurve is not implemented");
        }
        this.fieldIdentifier = aSN1ObjectIdentifier;
    }

    public ECCurve getCurve() {
        return this.curve;
    }

    public byte[] getSeed() {
        return Arrays.clone(this.seed);
    }

    /* JADX WARNING: Removed duplicated region for block: B:9:0x0061  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        /*
            r3 = this;
            org.bouncycastle.asn1.ASN1EncodableVector r0 = new org.bouncycastle.asn1.ASN1EncodableVector
            r1 = 3
            r0.<init>(r1)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r3.fieldIdentifier
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = prime_field
            boolean r1 = r1.equals((org.bouncycastle.asn1.ASN1Primitive) r2)
            if (r1 == 0) goto L_0x0035
            org.bouncycastle.asn1.x9.X9FieldElement r1 = new org.bouncycastle.asn1.x9.X9FieldElement
            org.bouncycastle.math.ec.ECCurve r2 = r3.curve
            org.bouncycastle.math.ec.ECFieldElement r2 = r2.getA()
            r1.<init>(r2)
            org.bouncycastle.asn1.ASN1Primitive r1 = r1.toASN1Primitive()
            r0.add(r1)
            org.bouncycastle.asn1.x9.X9FieldElement r1 = new org.bouncycastle.asn1.x9.X9FieldElement
            org.bouncycastle.math.ec.ECCurve r2 = r3.curve
            org.bouncycastle.math.ec.ECFieldElement r2 = r2.getB()
            r1.<init>(r2)
        L_0x002d:
            org.bouncycastle.asn1.ASN1Primitive r1 = r1.toASN1Primitive()
            r0.add(r1)
            goto L_0x005d
        L_0x0035:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r3.fieldIdentifier
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = characteristic_two_field
            boolean r1 = r1.equals((org.bouncycastle.asn1.ASN1Primitive) r2)
            if (r1 == 0) goto L_0x005d
            org.bouncycastle.asn1.x9.X9FieldElement r1 = new org.bouncycastle.asn1.x9.X9FieldElement
            org.bouncycastle.math.ec.ECCurve r2 = r3.curve
            org.bouncycastle.math.ec.ECFieldElement r2 = r2.getA()
            r1.<init>(r2)
            org.bouncycastle.asn1.ASN1Primitive r1 = r1.toASN1Primitive()
            r0.add(r1)
            org.bouncycastle.asn1.x9.X9FieldElement r1 = new org.bouncycastle.asn1.x9.X9FieldElement
            org.bouncycastle.math.ec.ECCurve r2 = r3.curve
            org.bouncycastle.math.ec.ECFieldElement r2 = r2.getB()
            r1.<init>(r2)
            goto L_0x002d
        L_0x005d:
            byte[] r1 = r3.seed
            if (r1 == 0) goto L_0x006b
            org.bouncycastle.asn1.DERBitString r1 = new org.bouncycastle.asn1.DERBitString
            byte[] r2 = r3.seed
            r1.<init>((byte[]) r2)
            r0.add(r1)
        L_0x006b:
            org.bouncycastle.asn1.DERSequence r1 = new org.bouncycastle.asn1.DERSequence
            r1.<init>((org.bouncycastle.asn1.ASN1EncodableVector) r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.asn1.p008x9.X9Curve.toASN1Primitive():org.bouncycastle.asn1.ASN1Primitive");
    }
}
