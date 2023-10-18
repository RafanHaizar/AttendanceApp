package org.bouncycastle.crypto.agreement;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.RawAgreement;
import org.bouncycastle.crypto.params.X448PrivateKeyParameters;
import org.bouncycastle.crypto.params.X448PublicKeyParameters;

public final class X448Agreement implements RawAgreement {
    private X448PrivateKeyParameters privateKey;

    public void calculateAgreement(CipherParameters cipherParameters, byte[] bArr, int i) {
        this.privateKey.generateSecret((X448PublicKeyParameters) cipherParameters, bArr, i);
    }

    public int getAgreementSize() {
        return 56;
    }

    public void init(CipherParameters cipherParameters) {
        this.privateKey = (X448PrivateKeyParameters) cipherParameters;
    }
}
