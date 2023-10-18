package com.itextpdf.kernel.crypto;

public interface IDecryptor {
    byte[] finish();

    byte[] update(byte[] bArr, int i, int i2);
}
