package org.bouncycastle.asn1.p008x9;

import java.math.BigInteger;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;

/* renamed from: org.bouncycastle.asn1.x9.DomainParameters */
public class DomainParameters extends ASN1Object {

    /* renamed from: g */
    private final ASN1Integer f102g;

    /* renamed from: j */
    private final ASN1Integer f103j;

    /* renamed from: p */
    private final ASN1Integer f104p;

    /* renamed from: q */
    private final ASN1Integer f105q;
    private final ValidationParams validationParams;

    public DomainParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, ValidationParams validationParams2) {
        if (bigInteger == null) {
            throw new IllegalArgumentException("'p' cannot be null");
        } else if (bigInteger2 == null) {
            throw new IllegalArgumentException("'g' cannot be null");
        } else if (bigInteger3 != null) {
            this.f104p = new ASN1Integer(bigInteger);
            this.f102g = new ASN1Integer(bigInteger2);
            this.f105q = new ASN1Integer(bigInteger3);
            this.f103j = bigInteger4 != null ? new ASN1Integer(bigInteger4) : null;
            this.validationParams = validationParams2;
        } else {
            throw new IllegalArgumentException("'q' cannot be null");
        }
    }

    private DomainParameters(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() < 3 || aSN1Sequence.size() > 5) {
            throw new IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        Enumeration objects = aSN1Sequence.getObjects();
        this.f104p = ASN1Integer.getInstance(objects.nextElement());
        this.f102g = ASN1Integer.getInstance(objects.nextElement());
        this.f105q = ASN1Integer.getInstance(objects.nextElement());
        ASN1Encodable next = getNext(objects);
        if (next == null || !(next instanceof ASN1Integer)) {
            this.f103j = null;
        } else {
            this.f103j = ASN1Integer.getInstance(next);
            next = getNext(objects);
        }
        if (next != null) {
            this.validationParams = ValidationParams.getInstance(next.toASN1Primitive());
        } else {
            this.validationParams = null;
        }
    }

    public static DomainParameters getInstance(Object obj) {
        if (obj instanceof DomainParameters) {
            return (DomainParameters) obj;
        }
        if (obj != null) {
            return new DomainParameters(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static DomainParameters getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    private static ASN1Encodable getNext(Enumeration enumeration) {
        if (enumeration.hasMoreElements()) {
            return (ASN1Encodable) enumeration.nextElement();
        }
        return null;
    }

    public BigInteger getG() {
        return this.f102g.getPositiveValue();
    }

    public BigInteger getJ() {
        ASN1Integer aSN1Integer = this.f103j;
        if (aSN1Integer == null) {
            return null;
        }
        return aSN1Integer.getPositiveValue();
    }

    public BigInteger getP() {
        return this.f104p.getPositiveValue();
    }

    public BigInteger getQ() {
        return this.f105q.getPositiveValue();
    }

    public ValidationParams getValidationParams() {
        return this.validationParams;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(5);
        aSN1EncodableVector.add(this.f104p);
        aSN1EncodableVector.add(this.f102g);
        aSN1EncodableVector.add(this.f105q);
        ASN1Integer aSN1Integer = this.f103j;
        if (aSN1Integer != null) {
            aSN1EncodableVector.add(aSN1Integer);
        }
        ValidationParams validationParams2 = this.validationParams;
        if (validationParams2 != null) {
            aSN1EncodableVector.add(validationParams2);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
