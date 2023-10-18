package org.bouncycastle.crypto.params;

import java.math.BigInteger;

public class SRP6GroupParameters {

    /* renamed from: N */
    private BigInteger f611N;

    /* renamed from: g */
    private BigInteger f612g;

    public SRP6GroupParameters(BigInteger bigInteger, BigInteger bigInteger2) {
        this.f611N = bigInteger;
        this.f612g = bigInteger2;
    }

    public BigInteger getG() {
        return this.f612g;
    }

    public BigInteger getN() {
        return this.f611N;
    }
}
