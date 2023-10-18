package org.bouncycastle.math.p018ec;

import java.math.BigInteger;

/* renamed from: org.bouncycastle.math.ec.DoubleAddMultiplier */
public class DoubleAddMultiplier extends AbstractECMultiplier {
    /* access modifiers changed from: protected */
    public ECPoint multiplyPositive(ECPoint eCPoint, BigInteger bigInteger) {
        ECPoint[] eCPointArr = {eCPoint.getCurve().getInfinity(), eCPoint};
        int bitLength = bigInteger.bitLength();
        for (int i = 0; i < bitLength; i++) {
            int testBit = bigInteger.testBit(i);
            int i2 = 1 - testBit;
            eCPointArr[i2] = eCPointArr[i2].twicePlus(eCPointArr[testBit]);
        }
        return eCPointArr[0];
    }
}
