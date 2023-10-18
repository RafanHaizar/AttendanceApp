package org.bouncycastle.asn1.x500;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x500.style.BCStyle;

public class X500Name extends ASN1Object implements ASN1Choice {
    private static X500NameStyle defaultStyle = BCStyle.INSTANCE;
    private int hashCodeValue;
    private boolean isHashCodeCalculated;
    private DERSequence rdnSeq;
    private RDN[] rdns;
    private X500NameStyle style;

    public X500Name(String str) {
        this(defaultStyle, str);
    }

    private X500Name(ASN1Sequence aSN1Sequence) {
        this(defaultStyle, aSN1Sequence);
    }

    public X500Name(X500NameStyle x500NameStyle, String str) {
        this(x500NameStyle.fromString(str));
        this.style = x500NameStyle;
    }

    private X500Name(X500NameStyle x500NameStyle, ASN1Sequence aSN1Sequence) {
        this.style = x500NameStyle;
        this.rdns = new RDN[aSN1Sequence.size()];
        Enumeration objects = aSN1Sequence.getObjects();
        boolean z = true;
        int i = 0;
        while (objects.hasMoreElements()) {
            Object nextElement = objects.nextElement();
            RDN instance = RDN.getInstance(nextElement);
            z &= instance == nextElement;
            this.rdns[i] = instance;
            i++;
        }
        this.rdnSeq = z ? DERSequence.convert(aSN1Sequence) : new DERSequence((ASN1Encodable[]) this.rdns);
    }

    public X500Name(X500NameStyle x500NameStyle, X500Name x500Name) {
        this.style = x500NameStyle;
        this.rdns = x500Name.rdns;
        this.rdnSeq = x500Name.rdnSeq;
    }

    public X500Name(X500NameStyle x500NameStyle, RDN[] rdnArr) {
        this.style = x500NameStyle;
        RDN[] rdnArr2 = (RDN[]) rdnArr.clone();
        this.rdns = rdnArr2;
        this.rdnSeq = new DERSequence((ASN1Encodable[]) rdnArr2);
    }

    public X500Name(RDN[] rdnArr) {
        this(defaultStyle, rdnArr);
    }

    public static X500NameStyle getDefaultStyle() {
        return defaultStyle;
    }

    public static X500Name getInstance(Object obj) {
        if (obj instanceof X500Name) {
            return (X500Name) obj;
        }
        if (obj != null) {
            return new X500Name(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static X500Name getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, true));
    }

    public static X500Name getInstance(X500NameStyle x500NameStyle, Object obj) {
        if (obj instanceof X500Name) {
            return new X500Name(x500NameStyle, (X500Name) obj);
        }
        if (obj != null) {
            return new X500Name(x500NameStyle, ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static void setDefaultStyle(X500NameStyle x500NameStyle) {
        if (x500NameStyle != null) {
            defaultStyle = x500NameStyle;
            return;
        }
        throw new NullPointerException("cannot set style to null");
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof X500Name) && !(obj instanceof ASN1Sequence)) {
            return false;
        }
        if (toASN1Primitive().equals(((ASN1Encodable) obj).toASN1Primitive())) {
            return true;
        }
        try {
            return this.style.areEqual(this, new X500Name(ASN1Sequence.getInstance(((ASN1Encodable) obj).toASN1Primitive())));
        } catch (Exception e) {
            return false;
        }
    }

    public ASN1ObjectIdentifier[] getAttributeTypes() {
        int i = 0;
        for (RDN size : this.rdns) {
            i += size.size();
        }
        ASN1ObjectIdentifier[] aSN1ObjectIdentifierArr = new ASN1ObjectIdentifier[i];
        int i2 = 0;
        for (RDN collectAttributeTypes : this.rdns) {
            i2 += collectAttributeTypes.collectAttributeTypes(aSN1ObjectIdentifierArr, i2);
        }
        return aSN1ObjectIdentifierArr;
    }

    public RDN[] getRDNs() {
        return (RDN[]) this.rdns.clone();
    }

    public RDN[] getRDNs(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        int length = this.rdns.length;
        RDN[] rdnArr = new RDN[length];
        int i = 0;
        int i2 = 0;
        while (true) {
            RDN[] rdnArr2 = this.rdns;
            if (i == rdnArr2.length) {
                break;
            }
            RDN rdn = rdnArr2[i];
            if (rdn.containsAttributeType(aSN1ObjectIdentifier)) {
                rdnArr[i2] = rdn;
                i2++;
            }
            i++;
        }
        if (i2 >= length) {
            return rdnArr;
        }
        RDN[] rdnArr3 = new RDN[i2];
        System.arraycopy(rdnArr, 0, rdnArr3, 0, i2);
        return rdnArr3;
    }

    public int hashCode() {
        if (this.isHashCodeCalculated) {
            return this.hashCodeValue;
        }
        this.isHashCodeCalculated = true;
        int calculateHashCode = this.style.calculateHashCode(this);
        this.hashCodeValue = calculateHashCode;
        return calculateHashCode;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.rdnSeq;
    }

    public String toString() {
        return this.style.toString(this);
    }
}
