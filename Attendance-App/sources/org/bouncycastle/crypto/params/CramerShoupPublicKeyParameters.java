package org.bouncycastle.crypto.params;

import java.math.BigInteger;

public class CramerShoupPublicKeyParameters extends CramerShoupKeyParameters {

    /* renamed from: c */
    private BigInteger f558c;

    /* renamed from: d */
    private BigInteger f559d;

    /* renamed from: h */
    private BigInteger f560h;

    public CramerShoupPublicKeyParameters(CramerShoupParameters cramerShoupParameters, BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        super(false, cramerShoupParameters);
        this.f558c = bigInteger;
        this.f559d = bigInteger2;
        this.f560h = bigInteger3;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CramerShoupPublicKeyParameters)) {
            return false;
        }
        CramerShoupPublicKeyParameters cramerShoupPublicKeyParameters = (CramerShoupPublicKeyParameters) obj;
        return cramerShoupPublicKeyParameters.getC().equals(this.f558c) && cramerShoupPublicKeyParameters.getD().equals(this.f559d) && cramerShoupPublicKeyParameters.getH().equals(this.f560h) && super.equals(obj);
    }

    public BigInteger getC() {
        return this.f558c;
    }

    public BigInteger getD() {
        return this.f559d;
    }

    public BigInteger getH() {
        return this.f560h;
    }

    public int hashCode() {
        return ((this.f558c.hashCode() ^ this.f559d.hashCode()) ^ this.f560h.hashCode()) ^ super.hashCode();
    }
}
