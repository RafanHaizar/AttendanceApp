package org.bouncycastle.operator.p021bc;

import org.bouncycastle.crypto.engines.AESWrapEngine;
import org.bouncycastle.crypto.params.KeyParameter;

/* renamed from: org.bouncycastle.operator.bc.BcAESSymmetricKeyWrapper */
public class BcAESSymmetricKeyWrapper extends BcSymmetricKeyWrapper {
    public BcAESSymmetricKeyWrapper(KeyParameter keyParameter) {
        super(AESUtil.determineKeyEncAlg(keyParameter), new AESWrapEngine(), keyParameter);
    }
}
