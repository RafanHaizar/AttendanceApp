package org.bouncycastle.crypto.params;

import java.math.BigInteger;

public class DSAPrivateKeyParameters extends DSAKeyParameters {

    /* renamed from: x */
    private BigInteger f574x;

    public DSAPrivateKeyParameters(BigInteger bigInteger, DSAParameters dSAParameters) {
        super(true, dSAParameters);
        this.f574x = bigInteger;
    }

    public BigInteger getX() {
        return this.f574x;
    }
}
