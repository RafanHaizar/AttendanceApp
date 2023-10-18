package org.bouncycastle.asn1.cmp;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

public class RevReqContent extends ASN1Object {
    private ASN1Sequence content;

    private RevReqContent(ASN1Sequence aSN1Sequence) {
        this.content = aSN1Sequence;
    }

    public RevReqContent(RevDetails revDetails) {
        this.content = new DERSequence((ASN1Encodable) revDetails);
    }

    public RevReqContent(RevDetails[] revDetailsArr) {
        this.content = new DERSequence((ASN1Encodable[]) revDetailsArr);
    }

    public static RevReqContent getInstance(Object obj) {
        if (obj instanceof RevReqContent) {
            return (RevReqContent) obj;
        }
        if (obj != null) {
            return new RevReqContent(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.content;
    }

    public RevDetails[] toRevDetailsArray() {
        int size = this.content.size();
        RevDetails[] revDetailsArr = new RevDetails[size];
        for (int i = 0; i != size; i++) {
            revDetailsArr[i] = RevDetails.getInstance(this.content.getObjectAt(i));
        }
        return revDetailsArr;
    }
}
