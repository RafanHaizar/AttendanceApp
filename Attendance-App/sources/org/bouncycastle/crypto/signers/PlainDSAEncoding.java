package org.bouncycastle.crypto.signers;

import java.math.BigInteger;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;

public class PlainDSAEncoding implements DSAEncoding {
    public static final PlainDSAEncoding INSTANCE = new PlainDSAEncoding();

    private void encodeValue(BigInteger bigInteger, BigInteger bigInteger2, byte[] bArr, int i, int i2) {
        byte[] byteArray = checkValue(bigInteger, bigInteger2).toByteArray();
        int max = Math.max(0, byteArray.length - i2);
        int length = byteArray.length - max;
        int i3 = (i2 - length) + i;
        Arrays.fill(bArr, i, i3, (byte) 0);
        System.arraycopy(byteArray, max, bArr, i3, length);
    }

    /* access modifiers changed from: protected */
    public BigInteger checkValue(BigInteger bigInteger, BigInteger bigInteger2) {
        if (bigInteger2.signum() >= 0 && bigInteger2.compareTo(bigInteger) < 0) {
            return bigInteger2;
        }
        throw new IllegalArgumentException("Value out of range");
    }

    public BigInteger[] decode(BigInteger bigInteger, byte[] bArr) {
        int unsignedByteLength = BigIntegers.getUnsignedByteLength(bigInteger);
        if (bArr.length == unsignedByteLength * 2) {
            return new BigInteger[]{decodeValue(bigInteger, bArr, 0, unsignedByteLength), decodeValue(bigInteger, bArr, unsignedByteLength, unsignedByteLength)};
        }
        throw new IllegalArgumentException("Encoding has incorrect length");
    }

    /* access modifiers changed from: protected */
    public BigInteger decodeValue(BigInteger bigInteger, byte[] bArr, int i, int i2) {
        return checkValue(bigInteger, new BigInteger(1, Arrays.copyOfRange(bArr, i, i2 + i)));
    }

    public byte[] encode(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        int unsignedByteLength = BigIntegers.getUnsignedByteLength(bigInteger);
        byte[] bArr = new byte[(unsignedByteLength * 2)];
        BigInteger bigInteger4 = bigInteger;
        byte[] bArr2 = bArr;
        int i = unsignedByteLength;
        encodeValue(bigInteger4, bigInteger2, bArr2, 0, i);
        encodeValue(bigInteger4, bigInteger3, bArr2, unsignedByteLength, i);
        return bArr;
    }
}
