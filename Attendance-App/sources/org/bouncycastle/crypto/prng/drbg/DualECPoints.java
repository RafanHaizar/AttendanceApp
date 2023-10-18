package org.bouncycastle.crypto.prng.drbg;

import org.bouncycastle.math.p018ec.ECPoint;

public class DualECPoints {
    private final int cofactor;

    /* renamed from: p */
    private final ECPoint f621p;

    /* renamed from: q */
    private final ECPoint f622q;
    private final int securityStrength;

    public DualECPoints(int i, ECPoint eCPoint, ECPoint eCPoint2, int i2) {
        if (eCPoint.getCurve().equals(eCPoint2.getCurve())) {
            this.securityStrength = i;
            this.f621p = eCPoint;
            this.f622q = eCPoint2;
            this.cofactor = i2;
            return;
        }
        throw new IllegalArgumentException("points need to be on the same curve");
    }

    private static int log2(int i) {
        int i2 = 0;
        while (true) {
            i >>= 1;
            if (i == 0) {
                return i2;
            }
            i2++;
        }
    }

    public int getCofactor() {
        return this.cofactor;
    }

    public int getMaxOutlen() {
        return ((this.f621p.getCurve().getFieldSize() - (log2(this.cofactor) + 13)) / 8) * 8;
    }

    public ECPoint getP() {
        return this.f621p;
    }

    public ECPoint getQ() {
        return this.f622q;
    }

    public int getSecurityStrength() {
        return this.securityStrength;
    }

    public int getSeedLen() {
        return this.f621p.getCurve().getFieldSize();
    }
}
