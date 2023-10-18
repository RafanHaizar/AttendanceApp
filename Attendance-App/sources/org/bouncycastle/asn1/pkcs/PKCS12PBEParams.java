package org.bouncycastle.asn1.pkcs;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;

public class PKCS12PBEParams extends ASN1Object {
    ASN1Integer iterations;

    /* renamed from: iv */
    ASN1OctetString f51iv;

    private PKCS12PBEParams(ASN1Sequence aSN1Sequence) {
        this.f51iv = (ASN1OctetString) aSN1Sequence.getObjectAt(0);
        this.iterations = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(1));
    }

    public PKCS12PBEParams(byte[] bArr, int i) {
        this.f51iv = new DEROctetString(bArr);
        this.iterations = new ASN1Integer((long) i);
    }

    public static PKCS12PBEParams getInstance(Object obj) {
        if (obj instanceof PKCS12PBEParams) {
            return (PKCS12PBEParams) obj;
        }
        if (obj != null) {
            return new PKCS12PBEParams(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public byte[] getIV() {
        return this.f51iv.getOctets();
    }

    public BigInteger getIterations() {
        return this.iterations.getValue();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.f51iv);
        aSN1EncodableVector.add(this.iterations);
        return new DERSequence(aSN1EncodableVector);
    }
}
