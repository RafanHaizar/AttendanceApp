package org.bouncycastle.mime.encoding;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.util.encoders.Base64;

public class Base64OutputStream extends FilterOutputStream {
    int bufOff;
    byte[] buffer = new byte[54];

    public Base64OutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    private void doWrite(byte b) throws IOException {
        byte[] bArr = this.buffer;
        int i = this.bufOff;
        int i2 = i + 1;
        this.bufOff = i2;
        bArr[i] = b;
        if (i2 == bArr.length) {
            Base64.encode(bArr, 0, bArr.length, this.out);
            this.out.write(13);
            this.out.write(10);
            this.bufOff = 0;
        }
    }

    public void close() throws IOException {
        int i = this.bufOff;
        if (i > 0) {
            Base64.encode(this.buffer, 0, i, this.out);
        }
        this.out.close();
    }

    public void write(int i) throws IOException {
        doWrite((byte) i);
    }

    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        for (int i3 = 0; i3 != i2; i3++) {
            doWrite(bArr[i + i3]);
        }
    }
}
