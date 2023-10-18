package org.bouncycastle.crypto.agreement.srp;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.params.SRP6GroupParameters;

public class SRP6Server {

    /* renamed from: A */
    protected BigInteger f157A;

    /* renamed from: B */
    protected BigInteger f158B;
    protected BigInteger Key;

    /* renamed from: M1 */
    protected BigInteger f159M1;

    /* renamed from: M2 */
    protected BigInteger f160M2;

    /* renamed from: N */
    protected BigInteger f161N;

    /* renamed from: S */
    protected BigInteger f162S;

    /* renamed from: b */
    protected BigInteger f163b;
    protected Digest digest;

    /* renamed from: g */
    protected BigInteger f164g;
    protected SecureRandom random;

    /* renamed from: u */
    protected BigInteger f165u;

    /* renamed from: v */
    protected BigInteger f166v;

    private BigInteger calculateS() {
        return this.f166v.modPow(this.f165u, this.f161N).multiply(this.f157A).mod(this.f161N).modPow(this.f163b, this.f161N);
    }

    public BigInteger calculateSecret(BigInteger bigInteger) throws CryptoException {
        BigInteger validatePublicValue = SRP6Util.validatePublicValue(this.f161N, bigInteger);
        this.f157A = validatePublicValue;
        this.f165u = SRP6Util.calculateU(this.digest, this.f161N, validatePublicValue, this.f158B);
        BigInteger calculateS = calculateS();
        this.f162S = calculateS;
        return calculateS;
    }

    public BigInteger calculateServerEvidenceMessage() throws CryptoException {
        BigInteger bigInteger;
        BigInteger bigInteger2;
        BigInteger bigInteger3 = this.f157A;
        if (bigInteger3 == null || (bigInteger = this.f159M1) == null || (bigInteger2 = this.f162S) == null) {
            throw new CryptoException("Impossible to compute M2: some data are missing from the previous operations (A,M1,S)");
        }
        BigInteger calculateM2 = SRP6Util.calculateM2(this.digest, this.f161N, bigInteger3, bigInteger, bigInteger2);
        this.f160M2 = calculateM2;
        return calculateM2;
    }

    public BigInteger calculateSessionKey() throws CryptoException {
        BigInteger bigInteger = this.f162S;
        if (bigInteger == null || this.f159M1 == null || this.f160M2 == null) {
            throw new CryptoException("Impossible to compute Key: some data are missing from the previous operations (S,M1,M2)");
        }
        BigInteger calculateKey = SRP6Util.calculateKey(this.digest, this.f161N, bigInteger);
        this.Key = calculateKey;
        return calculateKey;
    }

    public BigInteger generateServerCredentials() {
        BigInteger calculateK = SRP6Util.calculateK(this.digest, this.f161N, this.f164g);
        this.f163b = selectPrivateValue();
        BigInteger mod = calculateK.multiply(this.f166v).mod(this.f161N).add(this.f164g.modPow(this.f163b, this.f161N)).mod(this.f161N);
        this.f158B = mod;
        return mod;
    }

    public void init(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, Digest digest2, SecureRandom secureRandom) {
        this.f161N = bigInteger;
        this.f164g = bigInteger2;
        this.f166v = bigInteger3;
        this.random = secureRandom;
        this.digest = digest2;
    }

    public void init(SRP6GroupParameters sRP6GroupParameters, BigInteger bigInteger, Digest digest2, SecureRandom secureRandom) {
        init(sRP6GroupParameters.getN(), sRP6GroupParameters.getG(), bigInteger, digest2, secureRandom);
    }

    /* access modifiers changed from: protected */
    public BigInteger selectPrivateValue() {
        return SRP6Util.generatePrivateValue(this.digest, this.f161N, this.f164g, this.random);
    }

    public boolean verifyClientEvidenceMessage(BigInteger bigInteger) throws CryptoException {
        BigInteger bigInteger2;
        BigInteger bigInteger3;
        BigInteger bigInteger4 = this.f157A;
        if (bigInteger4 == null || (bigInteger2 = this.f158B) == null || (bigInteger3 = this.f162S) == null) {
            throw new CryptoException("Impossible to compute and verify M1: some data are missing from the previous operations (A,B,S)");
        } else if (!SRP6Util.calculateM1(this.digest, this.f161N, bigInteger4, bigInteger2, bigInteger3).equals(bigInteger)) {
            return false;
        } else {
            this.f159M1 = bigInteger;
            return true;
        }
    }
}
