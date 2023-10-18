package com.itextpdf.kernel.crypto;

public class AesDecryptor implements IDecryptor {
    private AESCipher cipher;
    private boolean initiated;

    /* renamed from: iv */
    private byte[] f1253iv = new byte[16];
    private int ivptr;
    private byte[] key;

    public AesDecryptor(byte[] key2, int off, int len) {
        byte[] bArr = new byte[len];
        this.key = bArr;
        System.arraycopy(key2, off, bArr, 0, len);
    }

    public byte[] update(byte[] b, int off, int len) {
        if (this.initiated) {
            return this.cipher.update(b, off, len);
        }
        int left = Math.min(this.f1253iv.length - this.ivptr, len);
        System.arraycopy(b, off, this.f1253iv, this.ivptr, left);
        int off2 = off + left;
        int len2 = len - left;
        int i = this.ivptr + left;
        this.ivptr = i;
        byte[] bArr = this.f1253iv;
        if (i != bArr.length) {
            return null;
        }
        AESCipher aESCipher = new AESCipher(false, this.key, bArr);
        this.cipher = aESCipher;
        this.initiated = true;
        if (len2 > 0) {
            return aESCipher.update(b, off2, len2);
        }
        return null;
    }

    public byte[] finish() {
        AESCipher aESCipher = this.cipher;
        if (aESCipher != null) {
            return aESCipher.doFinal();
        }
        return null;
    }
}
