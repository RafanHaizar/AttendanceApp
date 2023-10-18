package com.itextpdf.kernel.crypto;

public class StandardDecryptor implements IDecryptor {
    protected ARCFOUREncryption arcfour;

    public StandardDecryptor(byte[] key, int off, int len) {
        ARCFOUREncryption aRCFOUREncryption = new ARCFOUREncryption();
        this.arcfour = aRCFOUREncryption;
        aRCFOUREncryption.prepareARCFOURKey(key, off, len);
    }

    public byte[] update(byte[] b, int off, int len) {
        byte[] b2 = new byte[len];
        this.arcfour.encryptARCFOUR(b, off, len, b2, 0);
        return b2;
    }

    public byte[] finish() {
        return null;
    }
}
