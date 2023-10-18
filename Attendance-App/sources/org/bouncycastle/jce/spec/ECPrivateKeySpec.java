package org.bouncycastle.jce.spec;

import java.math.BigInteger;

public class ECPrivateKeySpec extends ECKeySpec {

    /* renamed from: d */
    private BigInteger f697d;

    public ECPrivateKeySpec(BigInteger bigInteger, ECParameterSpec eCParameterSpec) {
        super(eCParameterSpec);
        this.f697d = bigInteger;
    }

    public BigInteger getD() {
        return this.f697d;
    }
}
