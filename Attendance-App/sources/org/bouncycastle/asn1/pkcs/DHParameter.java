package org.bouncycastle.asn1.pkcs;

import java.math.BigInteger;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

public class DHParameter extends ASN1Object {

    /* renamed from: g */
    ASN1Integer f48g;

    /* renamed from: l */
    ASN1Integer f49l;

    /* renamed from: p */
    ASN1Integer f50p;

    public DHParameter(BigInteger bigInteger, BigInteger bigInteger2, int i) {
        this.f50p = new ASN1Integer(bigInteger);
        this.f48g = new ASN1Integer(bigInteger2);
        this.f49l = i != 0 ? new ASN1Integer((long) i) : null;
    }

    private DHParameter(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.f50p = ASN1Integer.getInstance(objects.nextElement());
        this.f48g = ASN1Integer.getInstance(objects.nextElement());
        this.f49l = objects.hasMoreElements() ? (ASN1Integer) objects.nextElement() : null;
    }

    public static DHParameter getInstance(Object obj) {
        if (obj instanceof DHParameter) {
            return (DHParameter) obj;
        }
        if (obj != null) {
            return new DHParameter(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public BigInteger getG() {
        return this.f48g.getPositiveValue();
    }

    public BigInteger getL() {
        ASN1Integer aSN1Integer = this.f49l;
        if (aSN1Integer == null) {
            return null;
        }
        return aSN1Integer.getPositiveValue();
    }

    public BigInteger getP() {
        return this.f50p.getPositiveValue();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(3);
        aSN1EncodableVector.add(this.f50p);
        aSN1EncodableVector.add(this.f48g);
        if (getL() != null) {
            aSN1EncodableVector.add(this.f49l);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
