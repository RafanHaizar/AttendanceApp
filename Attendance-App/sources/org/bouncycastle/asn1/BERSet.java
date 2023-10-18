package org.bouncycastle.asn1;

import java.io.IOException;

public class BERSet extends ASN1Set {
    public BERSet() {
    }

    public BERSet(ASN1Encodable aSN1Encodable) {
        super(aSN1Encodable);
    }

    public BERSet(ASN1EncodableVector aSN1EncodableVector) {
        super(aSN1EncodableVector, false);
    }

    BERSet(boolean z, ASN1Encodable[] aSN1EncodableArr) {
        super(z, aSN1EncodableArr);
    }

    public BERSet(ASN1Encodable[] aSN1EncodableArr) {
        super(aSN1EncodableArr, false);
    }

    /* access modifiers changed from: package-private */
    public void encode(ASN1OutputStream aSN1OutputStream, boolean z) throws IOException {
        aSN1OutputStream.writeEncodedIndef(z, 49, this.elements);
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
