package org.bouncycastle.crypto.p012ec;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.math.p018ec.ECPoint;

/* renamed from: org.bouncycastle.crypto.ec.ECDecryptor */
public interface ECDecryptor {
    ECPoint decrypt(ECPair eCPair);

    void init(CipherParameters cipherParameters);
}
