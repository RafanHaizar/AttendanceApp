package org.bouncycastle.crypto.params;

import java.math.BigInteger;
import org.bouncycastle.crypto.CipherParameters;

public class GOST3410Parameters implements CipherParameters {

    /* renamed from: a */
    private BigInteger f586a;

    /* renamed from: p */
    private BigInteger f587p;

    /* renamed from: q */
    private BigInteger f588q;
    private GOST3410ValidationParameters validation;

    public GOST3410Parameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this.f587p = bigInteger;
        this.f588q = bigInteger2;
        this.f586a = bigInteger3;
    }

    public GOST3410Parameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, GOST3410ValidationParameters gOST3410ValidationParameters) {
        this.f586a = bigInteger3;
        this.f587p = bigInteger;
        this.f588q = bigInteger2;
        this.validation = gOST3410ValidationParameters;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GOST3410Parameters)) {
            return false;
        }
        GOST3410Parameters gOST3410Parameters = (GOST3410Parameters) obj;
        return gOST3410Parameters.getP().equals(this.f587p) && gOST3410Parameters.getQ().equals(this.f588q) && gOST3410Parameters.getA().equals(this.f586a);
    }

    public BigInteger getA() {
        return this.f586a;
    }

    public BigInteger getP() {
        return this.f587p;
    }

    public BigInteger getQ() {
        return this.f588q;
    }

    public GOST3410ValidationParameters getValidationParameters() {
        return this.validation;
    }

    public int hashCode() {
        return (this.f587p.hashCode() ^ this.f588q.hashCode()) ^ this.f586a.hashCode();
    }
}
