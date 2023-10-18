package org.bouncycastle.crypto.p012ec;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.math.p018ec.ECAlgorithms;
import org.bouncycastle.math.p018ec.ECCurve;
import org.bouncycastle.math.p018ec.ECPoint;

/* renamed from: org.bouncycastle.crypto.ec.ECElGamalDecryptor */
public class ECElGamalDecryptor implements ECDecryptor {
    private ECPrivateKeyParameters key;

    public ECPoint decrypt(ECPair eCPair) {
        ECPrivateKeyParameters eCPrivateKeyParameters = this.key;
        if (eCPrivateKeyParameters != null) {
            ECCurve curve = eCPrivateKeyParameters.getParameters().getCurve();
            return ECAlgorithms.cleanPoint(curve, eCPair.getY()).subtract(ECAlgorithms.cleanPoint(curve, eCPair.getX()).multiply(this.key.getD())).normalize();
        }
        throw new IllegalStateException("ECElGamalDecryptor not initialised");
    }

    public void init(CipherParameters cipherParameters) {
        if (cipherParameters instanceof ECPrivateKeyParameters) {
            this.key = (ECPrivateKeyParameters) cipherParameters;
            return;
        }
        throw new IllegalArgumentException("ECPrivateKeyParameters are required for decryption.");
    }
}
