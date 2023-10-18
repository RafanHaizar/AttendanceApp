package org.bouncycastle.asn1.misc;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.util.Arrays;

public class CAST5CBCParameters extends ASN1Object {

    /* renamed from: iv */
    ASN1OctetString f42iv;
    ASN1Integer keyLength;

    private CAST5CBCParameters(ASN1Sequence aSN1Sequence) {
        this.f42iv = (ASN1OctetString) aSN1Sequence.getObjectAt(0);
        this.keyLength = (ASN1Integer) aSN1Sequence.getObjectAt(1);
    }

    public CAST5CBCParameters(byte[] bArr, int i) {
        this.f42iv = new DEROctetString(Arrays.clone(bArr));
        this.keyLength = new ASN1Integer((long) i);
    }

    public static CAST5CBCParameters getInstance(Object obj) {
        if (obj instanceof CAST5CBCParameters) {
            return (CAST5CBCParameters) obj;
        }
        if (obj != null) {
            return new CAST5CBCParameters(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public byte[] getIV() {
        return Arrays.clone(this.f42iv.getOctets());
    }

    public int getKeyLength() {
        return this.keyLength.intValueExact();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.f42iv);
        aSN1EncodableVector.add(this.keyLength);
        return new DERSequence(aSN1EncodableVector);
    }
}
