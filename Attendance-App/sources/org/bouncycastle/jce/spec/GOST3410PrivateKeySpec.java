package org.bouncycastle.jce.spec;

import java.math.BigInteger;
import java.security.spec.KeySpec;

public class GOST3410PrivateKeySpec implements KeySpec {

    /* renamed from: a */
    private BigInteger f703a;

    /* renamed from: p */
    private BigInteger f704p;

    /* renamed from: q */
    private BigInteger f705q;

    /* renamed from: x */
    private BigInteger f706x;

    public GOST3410PrivateKeySpec(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4) {
        this.f706x = bigInteger;
        this.f704p = bigInteger2;
        this.f705q = bigInteger3;
        this.f703a = bigInteger4;
    }

    public BigInteger getA() {
        return this.f703a;
    }

    public BigInteger getP() {
        return this.f704p;
    }

    public BigInteger getQ() {
        return this.f705q;
    }

    public BigInteger getX() {
        return this.f706x;
    }
}
