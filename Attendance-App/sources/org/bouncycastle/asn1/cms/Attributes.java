package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DLSet;

public class Attributes extends ASN1Object {
    private ASN1Set attributes;

    public Attributes(ASN1EncodableVector aSN1EncodableVector) {
        this.attributes = new DLSet(aSN1EncodableVector);
    }

    private Attributes(ASN1Set aSN1Set) {
        this.attributes = aSN1Set;
    }

    public static Attributes getInstance(Object obj) {
        if (obj instanceof Attributes) {
            return (Attributes) obj;
        }
        if (obj != null) {
            return new Attributes(ASN1Set.getInstance(obj));
        }
        return null;
    }

    public static Attributes getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Set.getInstance(aSN1TaggedObject, z));
    }

    public Attribute[] getAttributes() {
        int size = this.attributes.size();
        Attribute[] attributeArr = new Attribute[size];
        for (int i = 0; i != size; i++) {
            attributeArr[i] = Attribute.getInstance(this.attributes.getObjectAt(i));
        }
        return attributeArr;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.attributes;
    }
}
