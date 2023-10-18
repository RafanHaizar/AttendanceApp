package org.bouncycastle.pqc.asn1;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.util.Arrays;

public class ParSet extends ASN1Object {
    private static final BigInteger ZERO = BigInteger.valueOf(0);

    /* renamed from: h */
    private int[] f863h;

    /* renamed from: k */
    private int[] f864k;

    /* renamed from: t */
    private int f865t;

    /* renamed from: w */
    private int[] f866w;

    public ParSet(int i, int[] iArr, int[] iArr2, int[] iArr3) {
        this.f865t = i;
        this.f863h = iArr;
        this.f866w = iArr2;
        this.f864k = iArr3;
    }

    private ParSet(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() == 4) {
            this.f865t = checkBigIntegerInIntRangeAndPositive(aSN1Sequence.getObjectAt(0));
            ASN1Sequence aSN1Sequence2 = (ASN1Sequence) aSN1Sequence.getObjectAt(1);
            ASN1Sequence aSN1Sequence3 = (ASN1Sequence) aSN1Sequence.getObjectAt(2);
            ASN1Sequence aSN1Sequence4 = (ASN1Sequence) aSN1Sequence.getObjectAt(3);
            if (aSN1Sequence2.size() == this.f865t && aSN1Sequence3.size() == this.f865t && aSN1Sequence4.size() == this.f865t) {
                this.f863h = new int[aSN1Sequence2.size()];
                this.f866w = new int[aSN1Sequence3.size()];
                this.f864k = new int[aSN1Sequence4.size()];
                for (int i = 0; i < this.f865t; i++) {
                    this.f863h[i] = checkBigIntegerInIntRangeAndPositive(aSN1Sequence2.getObjectAt(i));
                    this.f866w[i] = checkBigIntegerInIntRangeAndPositive(aSN1Sequence3.getObjectAt(i));
                    this.f864k[i] = checkBigIntegerInIntRangeAndPositive(aSN1Sequence4.getObjectAt(i));
                }
                return;
            }
            throw new IllegalArgumentException("invalid size of sequences");
        }
        throw new IllegalArgumentException("sie of seqOfParams = " + aSN1Sequence.size());
    }

    private static int checkBigIntegerInIntRangeAndPositive(ASN1Encodable aSN1Encodable) {
        int intValueExact = ((ASN1Integer) aSN1Encodable).intValueExact();
        if (intValueExact > 0) {
            return intValueExact;
        }
        throw new IllegalArgumentException("BigInteger not in Range: " + intValueExact);
    }

    public static ParSet getInstance(Object obj) {
        if (obj instanceof ParSet) {
            return (ParSet) obj;
        }
        if (obj != null) {
            return new ParSet(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public int[] getH() {
        return Arrays.clone(this.f863h);
    }

    public int[] getK() {
        return Arrays.clone(this.f864k);
    }

    public int getT() {
        return this.f865t;
    }

    public int[] getW() {
        return Arrays.clone(this.f866w);
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
        ASN1EncodableVector aSN1EncodableVector3 = new ASN1EncodableVector();
        int i = 0;
        while (true) {
            int[] iArr = this.f863h;
            if (i < iArr.length) {
                aSN1EncodableVector.add(new ASN1Integer((long) iArr[i]));
                aSN1EncodableVector2.add(new ASN1Integer((long) this.f866w[i]));
                aSN1EncodableVector3.add(new ASN1Integer((long) this.f864k[i]));
                i++;
            } else {
                ASN1EncodableVector aSN1EncodableVector4 = new ASN1EncodableVector();
                aSN1EncodableVector4.add(new ASN1Integer((long) this.f865t));
                aSN1EncodableVector4.add(new DERSequence(aSN1EncodableVector));
                aSN1EncodableVector4.add(new DERSequence(aSN1EncodableVector2));
                aSN1EncodableVector4.add(new DERSequence(aSN1EncodableVector3));
                return new DERSequence(aSN1EncodableVector4);
            }
        }
    }
}
