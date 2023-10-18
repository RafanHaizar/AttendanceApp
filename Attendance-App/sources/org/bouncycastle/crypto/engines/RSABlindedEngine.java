package org.bouncycastle.crypto.engines;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;

public class RSABlindedEngine implements AsymmetricBlockCipher {
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private RSACoreEngine core = new RSACoreEngine();
    private RSAKeyParameters key;
    private SecureRandom random;

    public int getInputBlockSize() {
        return this.core.getInputBlockSize();
    }

    public int getOutputBlockSize() {
        return this.core.getOutputBlockSize();
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        SecureRandom secureRandom;
        this.core.init(z, cipherParameters);
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            RSAKeyParameters rSAKeyParameters = (RSAKeyParameters) parametersWithRandom.getParameters();
            this.key = rSAKeyParameters;
            if (rSAKeyParameters instanceof RSAPrivateCrtKeyParameters) {
                secureRandom = parametersWithRandom.getRandom();
            }
            this.random = null;
            return;
        }
        RSAKeyParameters rSAKeyParameters2 = (RSAKeyParameters) cipherParameters;
        this.key = rSAKeyParameters2;
        if (rSAKeyParameters2 instanceof RSAPrivateCrtKeyParameters) {
            secureRandom = CryptoServicesRegistrar.getSecureRandom();
        }
        this.random = null;
        return;
        this.random = secureRandom;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0010, code lost:
        r5 = (org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters) r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] processBlock(byte[] r4, int r5, int r6) {
        /*
            r3 = this;
            org.bouncycastle.crypto.params.RSAKeyParameters r0 = r3.key
            if (r0 == 0) goto L_0x0066
            org.bouncycastle.crypto.engines.RSACoreEngine r0 = r3.core
            java.math.BigInteger r4 = r0.convertInput(r4, r5, r6)
            org.bouncycastle.crypto.params.RSAKeyParameters r5 = r3.key
            boolean r6 = r5 instanceof org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters
            if (r6 == 0) goto L_0x0059
            org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters r5 = (org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters) r5
            java.math.BigInteger r6 = r5.getPublicExponent()
            if (r6 == 0) goto L_0x0059
            java.math.BigInteger r5 = r5.getModulus()
            java.math.BigInteger r0 = ONE
            java.math.BigInteger r1 = r5.subtract(r0)
            java.security.SecureRandom r2 = r3.random
            java.math.BigInteger r0 = org.bouncycastle.util.BigIntegers.createRandomInRange(r0, r1, r2)
            java.math.BigInteger r1 = r0.modPow(r6, r5)
            java.math.BigInteger r1 = r1.multiply(r4)
            java.math.BigInteger r1 = r1.mod(r5)
            org.bouncycastle.crypto.engines.RSACoreEngine r2 = r3.core
            java.math.BigInteger r1 = r2.processBlock(r1)
            java.math.BigInteger r0 = r0.modInverse(r5)
            java.math.BigInteger r0 = r1.multiply(r0)
            java.math.BigInteger r0 = r0.mod(r5)
            java.math.BigInteger r5 = r0.modPow(r6, r5)
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0051
            goto L_0x005f
        L_0x0051:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "RSA engine faulty decryption/signing detected"
            r4.<init>(r5)
            throw r4
        L_0x0059:
            org.bouncycastle.crypto.engines.RSACoreEngine r5 = r3.core
            java.math.BigInteger r0 = r5.processBlock(r4)
        L_0x005f:
            org.bouncycastle.crypto.engines.RSACoreEngine r4 = r3.core
            byte[] r4 = r4.convertOutput(r0)
            return r4
        L_0x0066:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "RSA engine not initialised"
            r4.<init>(r5)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.engines.RSABlindedEngine.processBlock(byte[], int, int):byte[]");
    }
}
