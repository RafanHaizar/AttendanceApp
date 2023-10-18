package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.DerivationParameters;

public class MGFParameters implements DerivationParameters {
    byte[] seed;

    public MGFParameters(byte[] bArr) {
        this(bArr, 0, bArr.length);
    }

    public MGFParameters(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        this.seed = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, i2);
    }

    public byte[] getSeed() {
        return this.seed;
    }
}
