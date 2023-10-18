package org.bouncycastle.operator.p021bc;

import org.bouncycastle.crypto.engines.AESWrapEngine;
import org.bouncycastle.crypto.params.KeyParameter;

/* renamed from: org.bouncycastle.operator.bc.BcAESSymmetricKeyUnwrapper */
public class BcAESSymmetricKeyUnwrapper extends BcSymmetricKeyUnwrapper {
    public BcAESSymmetricKeyUnwrapper(KeyParameter keyParameter) {
        super(AESUtil.determineKeyEncAlg(keyParameter), new AESWrapEngine(), keyParameter);
    }
}
