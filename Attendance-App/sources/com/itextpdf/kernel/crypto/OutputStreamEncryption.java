package com.itextpdf.kernel.crypto;

import java.io.IOException;
import java.io.OutputStream;

public abstract class OutputStreamEncryption extends OutputStream {
    protected OutputStream out;

    /* renamed from: sb */
    private byte[] f1254sb = new byte[1];

    public abstract void finish();

    public abstract void write(byte[] bArr, int i, int i2) throws IOException;

    protected OutputStreamEncryption(OutputStream out2) {
        this.out = out2;
    }

    public void close() throws IOException {
        finish();
        this.out.close();
    }

    public void flush() throws IOException {
        this.out.flush();
    }

    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    public void write(int b) throws IOException {
        byte[] bArr = this.f1254sb;
        bArr[0] = (byte) b;
        write(bArr, 0, 1);
    }
}
