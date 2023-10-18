package org.bouncycastle.crypto.params;

import java.math.BigInteger;

public class NaccacheSternKeyParameters extends AsymmetricKeyParameter {

    /* renamed from: g */
    private BigInteger f602g;
    int lowerSigmaBound;

    /* renamed from: n */
    private BigInteger f603n;

    public NaccacheSternKeyParameters(boolean z, BigInteger bigInteger, BigInteger bigInteger2, int i) {
        super(z);
        this.f602g = bigInteger;
        this.f603n = bigInteger2;
        this.lowerSigmaBound = i;
    }

    public BigInteger getG() {
        return this.f602g;
    }

    public int getLowerSigmaBound() {
        return this.lowerSigmaBound;
    }

    public BigInteger getModulus() {
        return this.f603n;
    }
}
