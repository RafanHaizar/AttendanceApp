package org.bouncycastle.crypto.params;

import java.math.BigInteger;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.util.Memoable;

public class CramerShoupParameters implements CipherParameters {

    /* renamed from: H */
    private Digest f548H;

    /* renamed from: g1 */
    private BigInteger f549g1;

    /* renamed from: g2 */
    private BigInteger f550g2;

    /* renamed from: p */
    private BigInteger f551p;

    public CramerShoupParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, Digest digest) {
        this.f551p = bigInteger;
        this.f549g1 = bigInteger2;
        this.f550g2 = bigInteger3;
        Digest digest2 = (Digest) ((Memoable) digest).copy();
        this.f548H = digest2;
        digest2.reset();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CramerShoupParameters)) {
            return false;
        }
        CramerShoupParameters cramerShoupParameters = (CramerShoupParameters) obj;
        return cramerShoupParameters.getP().equals(this.f551p) && cramerShoupParameters.getG1().equals(this.f549g1) && cramerShoupParameters.getG2().equals(this.f550g2);
    }

    public BigInteger getG1() {
        return this.f549g1;
    }

    public BigInteger getG2() {
        return this.f550g2;
    }

    public Digest getH() {
        return (Digest) ((Memoable) this.f548H).copy();
    }

    public BigInteger getP() {
        return this.f551p;
    }

    public int hashCode() {
        return (getP().hashCode() ^ getG1().hashCode()) ^ getG2().hashCode();
    }
}
