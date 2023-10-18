package org.bouncycastle.asn1.p007ua;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

/* renamed from: org.bouncycastle.asn1.ua.DSTU4145BinaryField */
public class DSTU4145BinaryField extends ASN1Object {

    /* renamed from: j */
    private int f53j;

    /* renamed from: k */
    private int f54k;

    /* renamed from: l */
    private int f55l;

    /* renamed from: m */
    private int f56m;

    public DSTU4145BinaryField(int i, int i2) {
        this(i, i2, 0, 0);
    }

    public DSTU4145BinaryField(int i, int i2, int i3, int i4) {
        this.f56m = i;
        this.f54k = i2;
        this.f53j = i3;
        this.f55l = i4;
    }

    private DSTU4145BinaryField(ASN1Sequence aSN1Sequence) {
        this.f56m = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0)).intPositiveValueExact();
        if (aSN1Sequence.getObjectAt(1) instanceof ASN1Integer) {
            this.f54k = ((ASN1Integer) aSN1Sequence.getObjectAt(1)).intPositiveValueExact();
        } else if (aSN1Sequence.getObjectAt(1) instanceof ASN1Sequence) {
            ASN1Sequence instance = ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(1));
            this.f54k = ASN1Integer.getInstance(instance.getObjectAt(0)).intPositiveValueExact();
            this.f53j = ASN1Integer.getInstance(instance.getObjectAt(1)).intPositiveValueExact();
            this.f55l = ASN1Integer.getInstance(instance.getObjectAt(2)).intPositiveValueExact();
        } else {
            throw new IllegalArgumentException("object parse error");
        }
    }

    public static DSTU4145BinaryField getInstance(Object obj) {
        if (obj instanceof DSTU4145BinaryField) {
            return (DSTU4145BinaryField) obj;
        }
        if (obj != null) {
            return new DSTU4145BinaryField(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public int getK1() {
        return this.f54k;
    }

    public int getK2() {
        return this.f53j;
    }

    public int getK3() {
        return this.f55l;
    }

    public int getM() {
        return this.f56m;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(2);
        aSN1EncodableVector.add(new ASN1Integer((long) this.f56m));
        if (this.f53j == 0) {
            aSN1EncodableVector.add(new ASN1Integer((long) this.f54k));
        } else {
            ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector(3);
            aSN1EncodableVector2.add(new ASN1Integer((long) this.f54k));
            aSN1EncodableVector2.add(new ASN1Integer((long) this.f53j));
            aSN1EncodableVector2.add(new ASN1Integer((long) this.f55l));
            aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector2));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
