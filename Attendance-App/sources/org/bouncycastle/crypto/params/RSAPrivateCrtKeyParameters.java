package org.bouncycastle.crypto.params;

import java.math.BigInteger;

public class RSAPrivateCrtKeyParameters extends RSAKeyParameters {

    /* renamed from: dP */
    private BigInteger f606dP;

    /* renamed from: dQ */
    private BigInteger f607dQ;

    /* renamed from: e */
    private BigInteger f608e;

    /* renamed from: p */
    private BigInteger f609p;

    /* renamed from: q */
    private BigInteger f610q;
    private BigInteger qInv;

    public RSAPrivateCrtKeyParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, BigInteger bigInteger5, BigInteger bigInteger6, BigInteger bigInteger7, BigInteger bigInteger8) {
        super(true, bigInteger, bigInteger3);
        this.f608e = bigInteger2;
        this.f609p = bigInteger4;
        this.f610q = bigInteger5;
        this.f606dP = bigInteger6;
        this.f607dQ = bigInteger7;
        this.qInv = bigInteger8;
    }

    public BigInteger getDP() {
        return this.f606dP;
    }

    public BigInteger getDQ() {
        return this.f607dQ;
    }

    public BigInteger getP() {
        return this.f609p;
    }

    public BigInteger getPublicExponent() {
        return this.f608e;
    }

    public BigInteger getQ() {
        return this.f610q;
    }

    public BigInteger getQInv() {
        return this.qInv;
    }
}
