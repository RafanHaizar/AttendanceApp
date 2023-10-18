package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.CipherParameters;

public class ParametersWithUKM implements CipherParameters {
    private CipherParameters parameters;
    private byte[] ukm;

    public ParametersWithUKM(CipherParameters cipherParameters, byte[] bArr) {
        this(cipherParameters, bArr, 0, bArr.length);
    }

    public ParametersWithUKM(CipherParameters cipherParameters, byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        this.ukm = bArr2;
        this.parameters = cipherParameters;
        System.arraycopy(bArr, i, bArr2, 0, i2);
    }

    public CipherParameters getParameters() {
        return this.parameters;
    }

    public byte[] getUKM() {
        return this.ukm;
    }
}
