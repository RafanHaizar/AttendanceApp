package org.bouncycastle.crypto.params;

import java.math.BigInteger;

public class ElGamalPublicKeyParameters extends ElGamalKeyParameters {

    /* renamed from: y */
    private BigInteger f585y;

    public ElGamalPublicKeyParameters(BigInteger bigInteger, ElGamalParameters elGamalParameters) {
        super(false, elGamalParameters);
        this.f585y = bigInteger;
    }

    public boolean equals(Object obj) {
        return (obj instanceof ElGamalPublicKeyParameters) && ((ElGamalPublicKeyParameters) obj).getY().equals(this.f585y) && super.equals(obj);
    }

    public BigInteger getY() {
        return this.f585y;
    }

    public int hashCode() {
        return this.f585y.hashCode() ^ super.hashCode();
    }
}
