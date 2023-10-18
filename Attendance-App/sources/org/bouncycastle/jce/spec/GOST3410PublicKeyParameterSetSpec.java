package org.bouncycastle.jce.spec;

import java.math.BigInteger;

public class GOST3410PublicKeyParameterSetSpec {

    /* renamed from: a */
    private BigInteger f707a;

    /* renamed from: p */
    private BigInteger f708p;

    /* renamed from: q */
    private BigInteger f709q;

    public GOST3410PublicKeyParameterSetSpec(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this.f708p = bigInteger;
        this.f709q = bigInteger2;
        this.f707a = bigInteger3;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GOST3410PublicKeyParameterSetSpec)) {
            return false;
        }
        GOST3410PublicKeyParameterSetSpec gOST3410PublicKeyParameterSetSpec = (GOST3410PublicKeyParameterSetSpec) obj;
        return this.f707a.equals(gOST3410PublicKeyParameterSetSpec.f707a) && this.f708p.equals(gOST3410PublicKeyParameterSetSpec.f708p) && this.f709q.equals(gOST3410PublicKeyParameterSetSpec.f709q);
    }

    public BigInteger getA() {
        return this.f707a;
    }

    public BigInteger getP() {
        return this.f708p;
    }

    public BigInteger getQ() {
        return this.f709q;
    }

    public int hashCode() {
        return (this.f707a.hashCode() ^ this.f708p.hashCode()) ^ this.f709q.hashCode();
    }
}
