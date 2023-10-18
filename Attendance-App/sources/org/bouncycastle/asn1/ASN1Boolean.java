package org.bouncycastle.asn1;

import java.io.IOException;

public class ASN1Boolean extends ASN1Primitive {
    public static final ASN1Boolean FALSE = new ASN1Boolean((byte) 0);
    private static final byte FALSE_VALUE = 0;
    public static final ASN1Boolean TRUE = new ASN1Boolean((byte) -1);
    private static final byte TRUE_VALUE = -1;
    private final byte value;

    private ASN1Boolean(byte b) {
        this.value = b;
    }

    static ASN1Boolean fromOctetString(byte[] bArr) {
        if (bArr.length == 1) {
            byte b = bArr[0];
            switch (b) {
                case -1:
                    return TRUE;
                case 0:
                    return FALSE;
                default:
                    return new ASN1Boolean(b);
            }
        } else {
            throw new IllegalArgumentException("BOOLEAN value should have 1 byte in it");
        }
    }

    public static ASN1Boolean getInstance(int i) {
        return i != 0 ? TRUE : FALSE;
    }

    public static ASN1Boolean getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1Boolean)) {
            return (ASN1Boolean) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return (ASN1Boolean) fromByteArray((byte[]) obj);
            } catch (IOException e) {
                throw new IllegalArgumentException("failed to construct boolean from byte[]: " + e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
        }
    }

    public static ASN1Boolean getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        return (z || (object instanceof ASN1Boolean)) ? getInstance((Object) object) : fromOctetString(ASN1OctetString.getInstance(object).getOctets());
    }

    public static ASN1Boolean getInstance(boolean z) {
        return z ? TRUE : FALSE;
    }

    /* access modifiers changed from: package-private */
    public boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        return (aSN1Primitive instanceof ASN1Boolean) && isTrue() == ((ASN1Boolean) aSN1Primitive).isTrue();
    }

    /* access modifiers changed from: package-private */
    public void encode(ASN1OutputStream aSN1OutputStream, boolean z) throws IOException {
        aSN1OutputStream.writeEncoded(z, 1, this.value);
    }

    /* access modifiers changed from: package-private */
    public int encodedLength() {
        return 3;
    }

    public int hashCode() {
        return isTrue() ? 1 : 0;
    }

    /* access modifiers changed from: package-private */
    public boolean isConstructed() {
        return false;
    }

    public boolean isTrue() {
        return this.value != 0;
    }

    /* access modifiers changed from: package-private */
    public ASN1Primitive toDERObject() {
        return isTrue() ? TRUE : FALSE;
    }

    public String toString() {
        return isTrue() ? "TRUE" : "FALSE";
    }
}
