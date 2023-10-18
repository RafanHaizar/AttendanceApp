package org.bouncycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;

class LazyEncodedSequence extends ASN1Sequence {
    private byte[] encoded;

    LazyEncodedSequence(byte[] bArr) throws IOException {
        this.encoded = bArr;
    }

    private void force() {
        if (this.encoded != null) {
            ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
            LazyConstructionEnumeration lazyConstructionEnumeration = new LazyConstructionEnumeration(this.encoded);
            while (lazyConstructionEnumeration.hasMoreElements()) {
                aSN1EncodableVector.add((ASN1Primitive) lazyConstructionEnumeration.nextElement());
            }
            this.elements = aSN1EncodableVector.takeElements();
            this.encoded = null;
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void encode(ASN1OutputStream aSN1OutputStream, boolean z) throws IOException {
        byte[] bArr = this.encoded;
        if (bArr != null) {
            aSN1OutputStream.writeEncoded(z, 48, bArr);
        } else {
            super.toDLObject().encode(aSN1OutputStream, z);
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized int encodedLength() throws IOException {
        byte[] bArr = this.encoded;
        if (bArr != null) {
            return StreamUtil.calculateBodyLength(bArr.length) + 1 + this.encoded.length;
        }
        return super.toDLObject().encodedLength();
    }

    public synchronized ASN1Encodable getObjectAt(int i) {
        force();
        return super.getObjectAt(i);
    }

    public synchronized Enumeration getObjects() {
        byte[] bArr = this.encoded;
        if (bArr != null) {
            return new LazyConstructionEnumeration(bArr);
        }
        return super.getObjects();
    }

    public synchronized int hashCode() {
        force();
        return super.hashCode();
    }

    public synchronized Iterator<ASN1Encodable> iterator() {
        force();
        return super.iterator();
    }

    public synchronized int size() {
        force();
        return super.size();
    }

    public synchronized ASN1Encodable[] toArray() {
        force();
        return super.toArray();
    }

    /* access modifiers changed from: package-private */
    public ASN1Encodable[] toArrayInternal() {
        force();
        return super.toArrayInternal();
    }

    /* access modifiers changed from: package-private */
    public synchronized ASN1Primitive toDERObject() {
        force();
        return super.toDERObject();
    }

    /* access modifiers changed from: package-private */
    public synchronized ASN1Primitive toDLObject() {
        force();
        return super.toDLObject();
    }
}
