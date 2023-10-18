package org.bouncycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

public class ASN1OutputStream {

    /* renamed from: os */
    private OutputStream f16os;

    public ASN1OutputStream(OutputStream outputStream) {
        this.f16os = outputStream;
    }

    public static ASN1OutputStream create(OutputStream outputStream) {
        return new ASN1OutputStream(outputStream);
    }

    public static ASN1OutputStream create(OutputStream outputStream, String str) {
        return str.equals(ASN1Encoding.DER) ? new DEROutputStream(outputStream) : str.equals(ASN1Encoding.f15DL) ? new DLOutputStream(outputStream) : new ASN1OutputStream(outputStream);
    }

    public void close() throws IOException {
        this.f16os.close();
    }

    public void flush() throws IOException {
        this.f16os.flush();
    }

    /* access modifiers changed from: package-private */
    public void flushInternal() throws IOException {
    }

    /* access modifiers changed from: package-private */
    public DEROutputStream getDERSubStream() {
        return new DEROutputStream(this.f16os);
    }

    /* access modifiers changed from: package-private */
    public ASN1OutputStream getDLSubStream() {
        return new DLOutputStream(this.f16os);
    }

    /* access modifiers changed from: package-private */
    public final void write(int i) throws IOException {
        this.f16os.write(i);
    }

    /* access modifiers changed from: package-private */
    public final void write(byte[] bArr, int i, int i2) throws IOException {
        this.f16os.write(bArr, i, i2);
    }

    /* access modifiers changed from: package-private */
    public final void writeElements(Enumeration enumeration) throws IOException {
        while (enumeration.hasMoreElements()) {
            writePrimitive(((ASN1Encodable) enumeration.nextElement()).toASN1Primitive(), true);
        }
    }

    /* access modifiers changed from: package-private */
    public final void writeElements(ASN1Encodable[] aSN1EncodableArr) throws IOException {
        for (ASN1Encodable aSN1Primitive : aSN1EncodableArr) {
            writePrimitive(aSN1Primitive.toASN1Primitive(), true);
        }
    }

    /* access modifiers changed from: package-private */
    public final void writeEncoded(boolean z, int i, byte b) throws IOException {
        if (z) {
            write(i);
        }
        writeLength(1);
        write(b);
    }

    /* access modifiers changed from: package-private */
    public final void writeEncoded(boolean z, int i, byte b, byte[] bArr) throws IOException {
        if (z) {
            write(i);
        }
        writeLength(bArr.length + 1);
        write(b);
        write(bArr, 0, bArr.length);
    }

    /* access modifiers changed from: package-private */
    public final void writeEncoded(boolean z, int i, byte b, byte[] bArr, int i2, int i3, byte b2) throws IOException {
        if (z) {
            write(i);
        }
        writeLength(i3 + 2);
        write(b);
        write(bArr, i2, i3);
        write(b2);
    }

    /* access modifiers changed from: package-private */
    public final void writeEncoded(boolean z, int i, int i2, byte[] bArr) throws IOException {
        writeTag(z, i, i2);
        writeLength(bArr.length);
        write(bArr, 0, bArr.length);
    }

    /* access modifiers changed from: package-private */
    public final void writeEncoded(boolean z, int i, byte[] bArr) throws IOException {
        if (z) {
            write(i);
        }
        writeLength(bArr.length);
        write(bArr, 0, bArr.length);
    }

    /* access modifiers changed from: package-private */
    public final void writeEncoded(boolean z, int i, byte[] bArr, int i2, int i3) throws IOException {
        if (z) {
            write(i);
        }
        writeLength(i3);
        write(bArr, i2, i3);
    }

    /* access modifiers changed from: package-private */
    public final void writeEncodedIndef(boolean z, int i, int i2, byte[] bArr) throws IOException {
        writeTag(z, i, i2);
        write(128);
        write(bArr, 0, bArr.length);
        write(0);
        write(0);
    }

    /* access modifiers changed from: package-private */
    public final void writeEncodedIndef(boolean z, int i, Enumeration enumeration) throws IOException {
        if (z) {
            write(i);
        }
        write(128);
        writeElements(enumeration);
        write(0);
        write(0);
    }

    /* access modifiers changed from: package-private */
    public final void writeEncodedIndef(boolean z, int i, ASN1Encodable[] aSN1EncodableArr) throws IOException {
        if (z) {
            write(i);
        }
        write(128);
        writeElements(aSN1EncodableArr);
        write(0);
        write(0);
    }

    /* access modifiers changed from: package-private */
    public final void writeLength(int i) throws IOException {
        if (i > 127) {
            int i2 = i;
            int i3 = 1;
            while (true) {
                i2 >>>= 8;
                if (i2 == 0) {
                    break;
                }
                i3++;
            }
            write((byte) (i3 | 128));
            for (int i4 = (i3 - 1) * 8; i4 >= 0; i4 -= 8) {
                write((byte) (i >> i4));
            }
            return;
        }
        write((byte) i);
    }

    /* access modifiers changed from: protected */
    public void writeNull() throws IOException {
        write(5);
        write(0);
    }

    public void writeObject(ASN1Encodable aSN1Encodable) throws IOException {
        if (aSN1Encodable != null) {
            writePrimitive(aSN1Encodable.toASN1Primitive(), true);
            flushInternal();
            return;
        }
        throw new IOException("null object detected");
    }

    public void writeObject(ASN1Primitive aSN1Primitive) throws IOException {
        if (aSN1Primitive != null) {
            writePrimitive(aSN1Primitive, true);
            flushInternal();
            return;
        }
        throw new IOException("null object detected");
    }

    /* access modifiers changed from: package-private */
    public void writePrimitive(ASN1Primitive aSN1Primitive, boolean z) throws IOException {
        aSN1Primitive.encode(this, z);
    }

    /* access modifiers changed from: package-private */
    public final void writeTag(boolean z, int i, int i2) throws IOException {
        if (z) {
            if (i2 < 31) {
                write(i | i2);
                return;
            }
            write(31 | i);
            if (i2 < 128) {
                write(i2);
                return;
            }
            byte[] bArr = new byte[5];
            int i3 = 4;
            bArr[4] = (byte) (i2 & 127);
            do {
                i2 >>= 7;
                i3--;
                bArr[i3] = (byte) ((i2 & 127) | 128);
            } while (i2 > 127);
            write(bArr, i3, 5 - i3);
        }
    }
}
