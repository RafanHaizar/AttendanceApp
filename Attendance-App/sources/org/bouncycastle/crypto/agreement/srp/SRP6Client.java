package org.bouncycastle.crypto.agreement.srp;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.params.SRP6GroupParameters;

public class SRP6Client {

    /* renamed from: A */
    protected BigInteger f147A;

    /* renamed from: B */
    protected BigInteger f148B;
    protected BigInteger Key;

    /* renamed from: M1 */
    protected BigInteger f149M1;

    /* renamed from: M2 */
    protected BigInteger f150M2;

    /* renamed from: N */
    protected BigInteger f151N;

    /* renamed from: S */
    protected BigInteger f152S;

    /* renamed from: a */
    protected BigInteger f153a;
    protected Digest digest;

    /* renamed from: g */
    protected BigInteger f154g;
    protected SecureRandom random;

    /* renamed from: u */
    protected BigInteger f155u;

    /* renamed from: x */
    protected BigInteger f156x;

    private BigInteger calculateS() {
        BigInteger calculateK = SRP6Util.calculateK(this.digest, this.f151N, this.f154g);
        return this.f148B.subtract(this.f154g.modPow(this.f156x, this.f151N).multiply(calculateK).mod(this.f151N)).mod(this.f151N).modPow(this.f155u.multiply(this.f156x).add(this.f153a), this.f151N);
    }

    public BigInteger calculateClientEvidenceMessage() throws CryptoException {
        BigInteger bigInteger;
        BigInteger bigInteger2;
        BigInteger bigInteger3 = this.f147A;
        if (bigInteger3 == null || (bigInteger = this.f148B) == null || (bigInteger2 = this.f152S) == null) {
            throw new CryptoException("Impossible to compute M1: some data are missing from the previous operations (A,B,S)");
        }
        BigInteger calculateM1 = SRP6Util.calculateM1(this.digest, this.f151N, bigInteger3, bigInteger, bigInteger2);
        this.f149M1 = calculateM1;
        return calculateM1;
    }

    public BigInteger calculateSecret(BigInteger bigInteger) throws CryptoException {
        BigInteger validatePublicValue = SRP6Util.validatePublicValue(this.f151N, bigInteger);
        this.f148B = validatePublicValue;
        this.f155u = SRP6Util.calculateU(this.digest, this.f151N, this.f147A, validatePublicValue);
        BigInteger calculateS = calculateS();
        this.f152S = calculateS;
        return calculateS;
    }

    public BigInteger calculateSessionKey() throws CryptoException {
        BigInteger bigInteger = this.f152S;
        if (bigInteger == null || this.f149M1 == null || this.f150M2 == null) {
            throw new CryptoException("Impossible to compute Key: some data are missing from the previous operations (S,M1,M2)");
        }
        BigInteger calculateKey = SRP6Util.calculateKey(this.digest, this.f151N, bigInteger);
        this.Key = calculateKey;
        return calculateKey;
    }

    public BigInteger generateClientCredentials(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        this.f156x = SRP6Util.calculateX(this.digest, this.f151N, bArr, bArr2, bArr3);
        BigInteger selectPrivateValue = selectPrivateValue();
        this.f153a = selectPrivateValue;
        BigInteger modPow = this.f154g.modPow(selectPrivateValue, this.f151N);
        this.f147A = modPow;
        return modPow;
    }

    public void init(BigInteger bigInteger, BigInteger bigInteger2, Digest digest2, SecureRandom secureRandom) {
        this.f151N = bigInteger;
        this.f154g = bigInteger2;
        this.digest = digest2;
        this.random = secureRandom;
    }

    public void init(SRP6GroupParameters sRP6GroupParameters, Digest digest2, SecureRandom secureRandom) {
        init(sRP6GroupParameters.getN(), sRP6GroupParameters.getG(), digest2, secureRandom);
    }

    /* access modifiers changed from: protected */
    public BigInteger selectPrivateValue() {
        return SRP6Util.generatePrivateValue(this.digest, this.f151N, this.f154g, this.random);
    }

    public boolean verifyServerEvidenceMessage(BigInteger bigInteger) throws CryptoException {
        BigInteger bigInteger2;
        BigInteger bigInteger3;
        BigInteger bigInteger4 = this.f147A;
        if (bigInteger4 == null || (bigInteger2 = this.f149M1) == null || (bigInteger3 = this.f152S) == null) {
            throw new CryptoException("Impossible to compute and verify M2: some data are missing from the previous operations (A,M1,S)");
        } else if (!SRP6Util.calculateM2(this.digest, this.f151N, bigInteger4, bigInteger2, bigInteger3).equals(bigInteger)) {
            return false;
        } else {
            this.f150M2 = bigInteger;
            return true;
        }
    }
}
