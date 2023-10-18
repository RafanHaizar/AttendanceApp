package org.bouncycastle.crypto.agreement.jpake;

import com.itextpdf.svg.SvgConstants;
import java.math.BigInteger;

public class JPAKEPrimeOrderGroup {

    /* renamed from: g */
    private final BigInteger f137g;

    /* renamed from: p */
    private final BigInteger f138p;

    /* renamed from: q */
    private final BigInteger f139q;

    public JPAKEPrimeOrderGroup(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this(bigInteger, bigInteger2, bigInteger3, false);
    }

    JPAKEPrimeOrderGroup(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, boolean z) {
        JPAKEUtil.validateNotNull(bigInteger, "p");
        JPAKEUtil.validateNotNull(bigInteger2, "q");
        JPAKEUtil.validateNotNull(bigInteger3, SvgConstants.Tags.f1648G);
        if (!z) {
            if (!bigInteger.subtract(JPAKEUtil.ONE).mod(bigInteger2).equals(JPAKEUtil.ZERO)) {
                throw new IllegalArgumentException("p-1 must be evenly divisible by q");
            } else if (bigInteger3.compareTo(BigInteger.valueOf(2)) == -1 || bigInteger3.compareTo(bigInteger.subtract(JPAKEUtil.ONE)) == 1) {
                throw new IllegalArgumentException("g must be in [2, p-1]");
            } else if (!bigInteger3.modPow(bigInteger2, bigInteger).equals(JPAKEUtil.ONE)) {
                throw new IllegalArgumentException("g^q mod p must equal 1");
            } else if (!bigInteger.isProbablePrime(20)) {
                throw new IllegalArgumentException("p must be prime");
            } else if (!bigInteger2.isProbablePrime(20)) {
                throw new IllegalArgumentException("q must be prime");
            }
        }
        this.f138p = bigInteger;
        this.f139q = bigInteger2;
        this.f137g = bigInteger3;
    }

    public BigInteger getG() {
        return this.f137g;
    }

    public BigInteger getP() {
        return this.f138p;
    }

    public BigInteger getQ() {
        return this.f139q;
    }
}
