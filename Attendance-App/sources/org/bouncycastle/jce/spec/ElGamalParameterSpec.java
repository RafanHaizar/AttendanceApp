package org.bouncycastle.jce.spec;

import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;

public class ElGamalParameterSpec implements AlgorithmParameterSpec {

    /* renamed from: g */
    private BigInteger f699g;

    /* renamed from: p */
    private BigInteger f700p;

    public ElGamalParameterSpec(BigInteger bigInteger, BigInteger bigInteger2) {
        this.f700p = bigInteger;
        this.f699g = bigInteger2;
    }

    public BigInteger getG() {
        return this.f699g;
    }

    public BigInteger getP() {
        return this.f700p;
    }
}
