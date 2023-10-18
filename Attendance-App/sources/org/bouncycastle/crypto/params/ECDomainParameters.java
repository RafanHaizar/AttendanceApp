package org.bouncycastle.crypto.params;

import java.math.BigInteger;
import org.bouncycastle.math.p018ec.ECAlgorithms;
import org.bouncycastle.math.p018ec.ECConstants;
import org.bouncycastle.math.p018ec.ECCurve;
import org.bouncycastle.math.p018ec.ECPoint;
import org.bouncycastle.util.Arrays;

public class ECDomainParameters implements ECConstants {

    /* renamed from: G */
    private final ECPoint f576G;
    private final ECCurve curve;

    /* renamed from: h */
    private final BigInteger f577h;
    private BigInteger hInv;

    /* renamed from: n */
    private final BigInteger f578n;
    private final byte[] seed;

    public ECDomainParameters(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger) {
        this(eCCurve, eCPoint, bigInteger, ONE, (byte[]) null);
    }

    public ECDomainParameters(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2) {
        this(eCCurve, eCPoint, bigInteger, bigInteger2, (byte[]) null);
    }

    public ECDomainParameters(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2, byte[] bArr) {
        this.hInv = null;
        if (eCCurve == null) {
            throw new NullPointerException("curve");
        } else if (bigInteger != null) {
            this.curve = eCCurve;
            this.f576G = validatePublicPoint(eCCurve, eCPoint);
            this.f578n = bigInteger;
            this.f577h = bigInteger2;
            this.seed = Arrays.clone(bArr);
        } else {
            throw new NullPointerException("n");
        }
    }

    static ECPoint validatePublicPoint(ECCurve eCCurve, ECPoint eCPoint) {
        if (eCPoint != null) {
            ECPoint normalize = ECAlgorithms.importPoint(eCCurve, eCPoint).normalize();
            if (normalize.isInfinity()) {
                throw new IllegalArgumentException("Point at infinity");
            } else if (normalize.isValid()) {
                return normalize;
            } else {
                throw new IllegalArgumentException("Point not on curve");
            }
        } else {
            throw new NullPointerException("Point cannot be null");
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ECDomainParameters)) {
            return false;
        }
        ECDomainParameters eCDomainParameters = (ECDomainParameters) obj;
        return this.curve.equals(eCDomainParameters.curve) && this.f576G.equals(eCDomainParameters.f576G) && this.f578n.equals(eCDomainParameters.f578n);
    }

    public ECCurve getCurve() {
        return this.curve;
    }

    public ECPoint getG() {
        return this.f576G;
    }

    public BigInteger getH() {
        return this.f577h;
    }

    public synchronized BigInteger getHInv() {
        if (this.hInv == null) {
            this.hInv = this.f577h.modInverse(this.f578n);
        }
        return this.hInv;
    }

    public BigInteger getN() {
        return this.f578n;
    }

    public byte[] getSeed() {
        return Arrays.clone(this.seed);
    }

    public int hashCode() {
        return ((((this.curve.hashCode() ^ 1028) * 257) ^ this.f576G.hashCode()) * 257) ^ this.f578n.hashCode();
    }

    public BigInteger validatePrivateScalar(BigInteger bigInteger) {
        if (bigInteger == null) {
            throw new NullPointerException("Scalar cannot be null");
        } else if (bigInteger.compareTo(ECConstants.ONE) >= 0 && bigInteger.compareTo(getN()) < 0) {
            return bigInteger;
        } else {
            throw new IllegalArgumentException("Scalar is not in the interval [1, n - 1]");
        }
    }

    public ECPoint validatePublicPoint(ECPoint eCPoint) {
        return validatePublicPoint(getCurve(), eCPoint);
    }
}
