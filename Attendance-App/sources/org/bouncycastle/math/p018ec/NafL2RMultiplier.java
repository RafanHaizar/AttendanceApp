package org.bouncycastle.math.p018ec;

import java.math.BigInteger;

/* renamed from: org.bouncycastle.math.ec.NafL2RMultiplier */
public class NafL2RMultiplier extends AbstractECMultiplier {
    /* access modifiers changed from: protected */
    public ECPoint multiplyPositive(ECPoint eCPoint, BigInteger bigInteger) {
        int[] generateCompactNaf = WNafUtil.generateCompactNaf(bigInteger);
        ECPoint normalize = eCPoint.normalize();
        ECPoint negate = normalize.negate();
        ECPoint infinity = eCPoint.getCurve().getInfinity();
        int length = generateCompactNaf.length;
        while (true) {
            length--;
            if (length < 0) {
                return infinity;
            }
            int i = generateCompactNaf[length];
            infinity = infinity.twicePlus((i >> 16) < 0 ? negate : normalize).timesPow2(i & 65535);
        }
    }
}
