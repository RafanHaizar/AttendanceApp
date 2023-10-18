package org.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.math.p018ec.WNafUtil;
import org.bouncycastle.util.BigIntegers;

class DHParametersHelper {
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private static final BigInteger TWO = BigInteger.valueOf(2);

    DHParametersHelper() {
    }

    static BigInteger[] generateSafePrimes(int i, int i2, SecureRandom secureRandom) {
        int i3 = i - 1;
        int i4 = i >>> 2;
        while (true) {
            BigInteger createRandomPrime = BigIntegers.createRandomPrime(i3, 2, secureRandom);
            BigInteger add = createRandomPrime.shiftLeft(1).add(ONE);
            if (add.isProbablePrime(i2) && ((i2 <= 2 || createRandomPrime.isProbablePrime(i2 - 2)) && WNafUtil.getNafWeight(add) >= i4)) {
                return new BigInteger[]{add, createRandomPrime};
            }
        }
    }

    static BigInteger selectGenerator(BigInteger bigInteger, BigInteger bigInteger2, SecureRandom secureRandom) {
        BigInteger modPow;
        BigInteger subtract = bigInteger.subtract(TWO);
        do {
            BigInteger bigInteger3 = TWO;
            modPow = BigIntegers.createRandomInRange(bigInteger3, subtract, secureRandom).modPow(bigInteger3, bigInteger);
        } while (modPow.equals(ONE));
        return modPow;
    }
}
