package org.bouncycastle.math.p018ec;

import java.math.BigInteger;

/* renamed from: org.bouncycastle.math.ec.ReferenceMultiplier */
public class ReferenceMultiplier extends AbstractECMultiplier {
    /* access modifiers changed from: protected */
    public ECPoint multiplyPositive(ECPoint eCPoint, BigInteger bigInteger) {
        return ECAlgorithms.referenceMultiply(eCPoint, bigInteger);
    }
}
