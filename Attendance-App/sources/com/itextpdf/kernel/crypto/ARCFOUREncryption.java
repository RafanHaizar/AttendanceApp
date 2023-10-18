package com.itextpdf.kernel.crypto;

import java.io.Serializable;

public class ARCFOUREncryption implements Serializable {
    private static final long serialVersionUID = 1450279022122017100L;
    private byte[] state = new byte[256];

    /* renamed from: x */
    private int f1251x;

    /* renamed from: y */
    private int f1252y;

    public void prepareARCFOURKey(byte[] key) {
        prepareARCFOURKey(key, 0, key.length);
    }

    public void prepareARCFOURKey(byte[] key, int off, int len) {
        int index1 = 0;
        int index2 = 0;
        for (int k = 0; k < 256; k++) {
            this.state[k] = (byte) k;
        }
        this.f1251x = 0;
        this.f1252y = 0;
        for (int k2 = 0; k2 < 256; k2++) {
            byte b = key[index1 + off];
            byte[] bArr = this.state;
            index2 = (b + bArr[k2] + index2) & 255;
            byte tmp = bArr[k2];
            bArr[k2] = bArr[index2];
            bArr[index2] = tmp;
            index1 = (index1 + 1) % len;
        }
    }

    public void encryptARCFOUR(byte[] dataIn, int off, int len, byte[] dataOut, int offOut) {
        int length = len + off;
        for (int k = off; k < length; k++) {
            int i = (this.f1251x + 1) & 255;
            this.f1251x = i;
            byte[] bArr = this.state;
            int i2 = (bArr[i] + this.f1252y) & 255;
            this.f1252y = i2;
            byte tmp = bArr[i];
            bArr[i] = bArr[i2];
            bArr[i2] = tmp;
            dataOut[(k - off) + offOut] = (byte) (bArr[(bArr[i] + tmp) & 255] ^ dataIn[k]);
        }
    }

    public void encryptARCFOUR(byte[] data, int off, int len) {
        encryptARCFOUR(data, off, len, data, off);
    }

    public void encryptARCFOUR(byte[] dataIn, byte[] dataOut) {
        encryptARCFOUR(dataIn, 0, dataIn.length, dataOut, 0);
    }

    public void encryptARCFOUR(byte[] data) {
        encryptARCFOUR(data, 0, data.length, data, 0);
    }
}
