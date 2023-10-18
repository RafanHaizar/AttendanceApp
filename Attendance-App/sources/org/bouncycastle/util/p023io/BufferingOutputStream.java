package org.bouncycastle.util.p023io;

import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.util.Arrays;

/* renamed from: org.bouncycastle.util.io.BufferingOutputStream */
public class BufferingOutputStream extends OutputStream {
    private final byte[] buf;
    private int bufOff;
    private final OutputStream other;

    public BufferingOutputStream(OutputStream outputStream) {
        this.other = outputStream;
        this.buf = new byte[4096];
    }

    public BufferingOutputStream(OutputStream outputStream, int i) {
        this.other = outputStream;
        this.buf = new byte[i];
    }

    public void close() throws IOException {
        flush();
        this.other.close();
    }

    public void flush() throws IOException {
        this.other.write(this.buf, 0, this.bufOff);
        this.bufOff = 0;
        Arrays.fill(this.buf, (byte) 0);
    }

    public void write(int i) throws IOException {
        byte[] bArr = this.buf;
        int i2 = this.bufOff;
        int i3 = i2 + 1;
        this.bufOff = i3;
        bArr[i2] = (byte) i;
        if (i3 == bArr.length) {
            flush();
        }
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        byte[] bArr2;
        byte[] bArr3 = this.buf;
        int length = bArr3.length;
        int i3 = this.bufOff;
        if (i2 < length - i3) {
            System.arraycopy(bArr, i, bArr3, i3, i2);
        } else {
            int length2 = bArr3.length - i3;
            System.arraycopy(bArr, i, bArr3, i3, length2);
            this.bufOff += length2;
            flush();
            int i4 = i + length2;
            i2 -= length2;
            while (true) {
                bArr2 = this.buf;
                if (i2 < bArr2.length) {
                    break;
                }
                this.other.write(bArr, i4, bArr2.length);
                byte[] bArr4 = this.buf;
                i4 += bArr4.length;
                i2 -= bArr4.length;
            }
            if (i2 > 0) {
                System.arraycopy(bArr, i4, bArr2, this.bufOff, i2);
            } else {
                return;
            }
        }
        this.bufOff += i2;
    }
}
