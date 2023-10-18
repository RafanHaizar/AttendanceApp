package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.CipherParameters;

public class ParametersWithID implements CipherParameters {

    /* renamed from: id */
    private byte[] f604id;
    private CipherParameters parameters;

    public ParametersWithID(CipherParameters cipherParameters, byte[] bArr) {
        this.parameters = cipherParameters;
        this.f604id = bArr;
    }

    public byte[] getID() {
        return this.f604id;
    }

    public CipherParameters getParameters() {
        return this.parameters;
    }
}
