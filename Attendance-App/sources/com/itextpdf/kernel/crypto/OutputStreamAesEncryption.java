package com.itextpdf.kernel.crypto;

import com.itextpdf.kernel.PdfException;
import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamAesEncryption extends OutputStreamEncryption {
    protected AESCipher cipher;
    private boolean finished;

    public OutputStreamAesEncryption(OutputStream out, byte[] key, int off, int len) {
        super(out);
        byte[] iv = IVGenerator.getIV();
        byte[] nkey = new byte[len];
        System.arraycopy(key, off, nkey, 0, len);
        this.cipher = new AESCipher(true, nkey, iv);
        try {
            write(iv);
        } catch (IOException e) {
            throw new PdfException(PdfException.PdfEncryption, (Throwable) e);
        }
    }

    public OutputStreamAesEncryption(OutputStream out, byte[] key) {
        this(out, key, 0, key.length);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        byte[] b2 = this.cipher.update(b, off, len);
        if (b2 != null && b2.length != 0) {
            this.out.write(b2, 0, b2.length);
        }
    }

    public void finish() {
        if (!this.finished) {
            this.finished = true;
            byte[] b = this.cipher.doFinal();
            try {
                this.out.write(b, 0, b.length);
            } catch (IOException e) {
                throw new PdfException(PdfException.PdfEncryption, (Throwable) e);
            }
        }
    }
}
