package org.bouncycastle.crypto.params;

import java.math.BigInteger;
import org.bouncycastle.crypto.CipherParameters;

public class DSAParameters implements CipherParameters {

    /* renamed from: g */
    private BigInteger f571g;

    /* renamed from: p */
    private BigInteger f572p;

    /* renamed from: q */
    private BigInteger f573q;
    private DSAValidationParameters validation;

    public DSAParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this.f571g = bigInteger3;
        this.f572p = bigInteger;
        this.f573q = bigInteger2;
    }

    public DSAParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, DSAValidationParameters dSAValidationParameters) {
        this.f571g = bigInteger3;
        this.f572p = bigInteger;
        this.f573q = bigInteger2;
        this.validation = dSAValidationParameters;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DSAParameters)) {
            return false;
        }
        DSAParameters dSAParameters = (DSAParameters) obj;
        return dSAParameters.getP().equals(this.f572p) && dSAParameters.getQ().equals(this.f573q) && dSAParameters.getG().equals(this.f571g);
    }

    public BigInteger getG() {
        return this.f571g;
    }

    public BigInteger getP() {
        return this.f572p;
    }

    public BigInteger getQ() {
        return this.f573q;
    }

    public DSAValidationParameters getValidationParameters() {
        return this.validation;
    }

    public int hashCode() {
        return (getP().hashCode() ^ getQ().hashCode()) ^ getG().hashCode();
    }
}
