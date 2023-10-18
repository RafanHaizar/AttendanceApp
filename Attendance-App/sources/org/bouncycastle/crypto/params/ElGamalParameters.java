package org.bouncycastle.crypto.params;

import java.math.BigInteger;
import org.bouncycastle.crypto.CipherParameters;

public class ElGamalParameters implements CipherParameters {

    /* renamed from: g */
    private BigInteger f581g;

    /* renamed from: l */
    private int f582l;

    /* renamed from: p */
    private BigInteger f583p;

    public ElGamalParameters(BigInteger bigInteger, BigInteger bigInteger2) {
        this(bigInteger, bigInteger2, 0);
    }

    public ElGamalParameters(BigInteger bigInteger, BigInteger bigInteger2, int i) {
        this.f581g = bigInteger2;
        this.f583p = bigInteger;
        this.f582l = i;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ElGamalParameters)) {
            return false;
        }
        ElGamalParameters elGamalParameters = (ElGamalParameters) obj;
        return elGamalParameters.getP().equals(this.f583p) && elGamalParameters.getG().equals(this.f581g) && elGamalParameters.getL() == this.f582l;
    }

    public BigInteger getG() {
        return this.f581g;
    }

    public int getL() {
        return this.f582l;
    }

    public BigInteger getP() {
        return this.f583p;
    }

    public int hashCode() {
        return (getP().hashCode() ^ getG().hashCode()) + this.f582l;
    }
}
