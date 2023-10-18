package org.bouncycastle.jce.spec;

import java.math.BigInteger;
import java.security.spec.KeySpec;

public class GOST3410PublicKeySpec implements KeySpec {

    /* renamed from: a */
    private BigInteger f710a;

    /* renamed from: p */
    private BigInteger f711p;

    /* renamed from: q */
    private BigInteger f712q;

    /* renamed from: y */
    private BigInteger f713y;

    public GOST3410PublicKeySpec(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4) {
        this.f713y = bigInteger;
        this.f711p = bigInteger2;
        this.f712q = bigInteger3;
        this.f710a = bigInteger4;
    }

    public BigInteger getA() {
        return this.f710a;
    }

    public BigInteger getP() {
        return this.f711p;
    }

    public BigInteger getQ() {
        return this.f712q;
    }

    public BigInteger getY() {
        return this.f713y;
    }
}
