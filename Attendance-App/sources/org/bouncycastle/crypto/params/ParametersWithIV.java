package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.CipherParameters;

public class ParametersWithIV implements CipherParameters {

    /* renamed from: iv */
    private byte[] f605iv;
    private CipherParameters parameters;

    public ParametersWithIV(CipherParameters cipherParameters, byte[] bArr) {
        this(cipherParameters, bArr, 0, bArr.length);
    }

    public ParametersWithIV(CipherParameters cipherParameters, byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        this.f605iv = bArr2;
        this.parameters = cipherParameters;
        System.arraycopy(bArr, i, bArr2, 0, i2);
    }

    public byte[] getIV() {
        return this.f605iv;
    }

    public CipherParameters getParameters() {
        return this.parameters;
    }
}
