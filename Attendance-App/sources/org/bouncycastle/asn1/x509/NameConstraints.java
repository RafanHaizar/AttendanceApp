package org.bouncycastle.asn1.x509;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;

public class NameConstraints extends ASN1Object {
    private GeneralSubtree[] excluded;
    private GeneralSubtree[] permitted;

    private NameConstraints(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            ASN1TaggedObject instance = ASN1TaggedObject.getInstance(objects.nextElement());
            switch (instance.getTagNo()) {
                case 0:
                    this.permitted = createArray(ASN1Sequence.getInstance(instance, false));
                    break;
                case 1:
                    this.excluded = createArray(ASN1Sequence.getInstance(instance, false));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown tag encountered: " + instance.getTagNo());
            }
        }
    }

    public NameConstraints(GeneralSubtree[] generalSubtreeArr, GeneralSubtree[] generalSubtreeArr2) {
        this.permitted = cloneSubtree(generalSubtreeArr);
        this.excluded = cloneSubtree(generalSubtreeArr2);
    }

    private static GeneralSubtree[] cloneSubtree(GeneralSubtree[] generalSubtreeArr) {
        if (generalSubtreeArr == null) {
            return null;
        }
        int length = generalSubtreeArr.length;
        GeneralSubtree[] generalSubtreeArr2 = new GeneralSubtree[length];
        System.arraycopy(generalSubtreeArr, 0, generalSubtreeArr2, 0, length);
        return generalSubtreeArr2;
    }

    private GeneralSubtree[] createArray(ASN1Sequence aSN1Sequence) {
        int size = aSN1Sequence.size();
        GeneralSubtree[] generalSubtreeArr = new GeneralSubtree[size];
        for (int i = 0; i != size; i++) {
            generalSubtreeArr[i] = GeneralSubtree.getInstance(aSN1Sequence.getObjectAt(i));
        }
        return generalSubtreeArr;
    }

    public static NameConstraints getInstance(Object obj) {
        if (obj instanceof NameConstraints) {
            return (NameConstraints) obj;
        }
        if (obj != null) {
            return new NameConstraints(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public GeneralSubtree[] getExcludedSubtrees() {
        return cloneSubtree(this.excluded);
    }

    public GeneralSubtree[] getPermittedSubtrees() {
        return cloneSubtree(this.permitted);
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(2);
        GeneralSubtree[] generalSubtreeArr = this.permitted;
        if (generalSubtreeArr != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, new DERSequence((ASN1Encodable[]) generalSubtreeArr)));
        }
        GeneralSubtree[] generalSubtreeArr2 = this.excluded;
        if (generalSubtreeArr2 != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, new DERSequence((ASN1Encodable[]) generalSubtreeArr2)));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
