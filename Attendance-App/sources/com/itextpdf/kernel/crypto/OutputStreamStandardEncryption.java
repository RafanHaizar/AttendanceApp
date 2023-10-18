package com.itextpdf.kernel.crypto;

import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamStandardEncryption extends OutputStreamEncryption {
    protected ARCFOUREncryption arcfour;

    public OutputStreamStandardEncryption(OutputStream out, byte[] key, int off, int len) {
        super(out);
        ARCFOUREncryption aRCFOUREncryption = new ARCFOUREncryption();
        this.arcfour = aRCFOUREncryption;
        aRCFOUREncryption.prepareARCFOURKey(key, off, len);
    }

    public OutputStreamStandardEncryption(OutputStream out, byte[] key) {
        this(out, key, 0, key.length);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        byte[] b2 = new byte[Math.min(len, 4192)];
        while (len > 0) {
            int sz = Math.min(len, b2.length);
            this.arcfour.encryptARCFOUR(b, off, sz, b2, 0);
            this.out.write(b2, 0, sz);
            len -= sz;
            off += sz;
        }
    }

    public void finish() {
    }
}
