package org.bouncycastle.crypto.p012ec;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.math.p018ec.ECConstants;
import org.bouncycastle.util.BigIntegers;

/* renamed from: org.bouncycastle.crypto.ec.ECUtil */
class ECUtil {
    ECUtil() {
    }

    static BigInteger generateK(BigInteger bigInteger, SecureRandom secureRandom) {
        int bitLength = bigInteger.bitLength();
        while (true) {
            BigInteger createRandomBigInteger = BigIntegers.createRandomBigInteger(bitLength, secureRandom);
            if (!createRandomBigInteger.equals(ECConstants.ZERO) && createRandomBigInteger.compareTo(bigInteger) < 0) {
                return createRandomBigInteger;
            }
        }
    }
}
