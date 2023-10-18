package org.bouncycastle.jce.spec;

import java.math.BigInteger;

public class ElGamalPrivateKeySpec extends ElGamalKeySpec {

    /* renamed from: x */
    private BigInteger f701x;

    public ElGamalPrivateKeySpec(BigInteger bigInteger, ElGamalParameterSpec elGamalParameterSpec) {
        super(elGamalParameterSpec);
        this.f701x = bigInteger;
    }

    public BigInteger getX() {
        return this.f701x;
    }
}
