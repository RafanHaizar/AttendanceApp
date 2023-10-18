package org.bouncycastle.asn1;

import java.io.IOException;

public class BERSequence extends ASN1Sequence {
    public BERSequence() {
    }

    public BERSequence(ASN1Encodable aSN1Encodable) {
        super(aSN1Encodable);
    }

    public BERSequence(ASN1EncodableVector aSN1EncodableVector) {
        super(aSN1EncodableVector);
    }

    public BERSequence(ASN1Encodable[] aSN1EncodableArr) {
        super(aSN1EncodableArr);
    }

    /* access modifiers changed from: package-private */
    public void encode(ASN1OutputStream aSN1OutputStream, boolean z) throws IOException {
        aSN1OutputStream.writeEncodedIndef(z, 48, this.elements);
    }

    /* access modifiers changed from: package-private */
    public int encodedLength() throws IOException {
        int i = 0;
        for (ASN1Encodable aSN1Primitive : this.elements) {
            i += aSN1Primitive.toASN1Primitive().encodedLength();
        }
        return i + 2 + 2;
    }
}
