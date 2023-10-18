package org.bouncycastle.crypto.params;

import java.math.BigInteger;

public class DHPrivateKeyParameters extends DHKeyParameters {

    /* renamed from: x */
    private BigInteger f567x;

    public DHPrivateKeyParameters(BigInteger bigInteger, DHParameters dHParameters) {
        super(true, dHParameters);
        this.f567x = bigInteger;
    }

    public boolean equals(Object obj) {
        return (obj instanceof DHPrivateKeyParameters) && ((DHPrivateKeyParameters) obj).getX().equals(this.f567x) && super.equals(obj);
    }

    public BigInteger getX() {
        return this.f567x;
    }

    public int hashCode() {
        return this.f567x.hashCode() ^ super.hashCode();
    }
}
