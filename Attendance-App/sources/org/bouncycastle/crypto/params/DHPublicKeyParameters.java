package org.bouncycastle.crypto.params;

import java.math.BigInteger;

public class DHPublicKeyParameters extends DHKeyParameters {
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private static final BigInteger TWO = BigInteger.valueOf(2);

    /* renamed from: y */
    private BigInteger f568y;

    public DHPublicKeyParameters(BigInteger bigInteger, DHParameters dHParameters) {
        super(false, dHParameters);
        this.f568y = validate(bigInteger, dHParameters);
    }

    private BigInteger validate(BigInteger bigInteger, DHParameters dHParameters) {
        if (bigInteger != null) {
            BigInteger bigInteger2 = TWO;
            if (bigInteger.compareTo(bigInteger2) < 0 || bigInteger.compareTo(dHParameters.getP().subtract(bigInteger2)) > 0) {
                throw new IllegalArgumentException("invalid DH public key");
            } else if (dHParameters.getQ() == null || ONE.equals(bigInteger.modPow(dHParameters.getQ(), dHParameters.getP()))) {
                return bigInteger;
            } else {
                throw new IllegalArgumentException("Y value does not appear to be in correct group");
            }
        } else {
            throw new NullPointerException("y value cannot be null");
        }
    }

    public boolean equals(Object obj) {
        return (obj instanceof DHPublicKeyParameters) && ((DHPublicKeyParameters) obj).getY().equals(this.f568y) && super.equals(obj);
    }

    public BigInteger getY() {
        return this.f568y;
    }

    public int hashCode() {
        return this.f568y.hashCode() ^ super.hashCode();
    }
}
