package org.bouncycastle.crypto.agreement.kdf;

import org.bouncycastle.crypto.DerivationParameters;

public class GSKKDFParameters implements DerivationParameters {
    private final byte[] nonce;
    private final int startCounter;

    /* renamed from: z */
    private final byte[] f144z;

    public GSKKDFParameters(byte[] bArr, int i) {
        this(bArr, i, (byte[]) null);
    }

    public GSKKDFParameters(byte[] bArr, int i, byte[] bArr2) {
        this.f144z = bArr;
        this.startCounter = i;
        this.nonce = bArr2;
    }

    public byte[] getNonce() {
        return this.nonce;
    }

    public int getStartCounter() {
        return this.startCounter;
    }

    public byte[] getZ() {
        return this.f144z;
    }
}
