package org.bouncycastle.crypto.params;

import java.math.BigInteger;

public class DSAPublicKeyParameters extends DSAKeyParameters {
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private static final BigInteger TWO = BigInteger.valueOf(2);

    /* renamed from: y */
    private BigInteger f575y;

    public DSAPublicKeyParameters(BigInteger bigInteger, DSAParameters dSAParameters) {
        super(false, dSAParameters);
        this.f575y = validate(bigInteger, dSAParameters);
    }

    private BigInteger validate(BigInteger bigInteger, DSAParameters dSAParameters) {
        if (dSAParameters == null) {
            return bigInteger;
        }
        BigInteger bigInteger2 = TWO;
        if (bigInteger2.compareTo(bigInteger) <= 0 && dSAParameters.getP().subtract(bigInteger2).compareTo(bigInteger) >= 0 && ONE.equals(bigInteger.modPow(dSAParameters.getQ(), dSAParameters.getP()))) {
            return bigInteger;
        }
        throw new IllegalArgumentException("y value does not appear to be in correct group");
    }

    public BigInteger getY() {
        return this.f575y;
    }
}
