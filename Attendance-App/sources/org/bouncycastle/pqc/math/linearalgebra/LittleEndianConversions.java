package org.bouncycastle.pqc.math.linearalgebra;

import kotlin.UByte;

public final class LittleEndianConversions {
    private LittleEndianConversions() {
    }

    public static void I2OSP(int i, byte[] bArr, int i2) {
        int i3 = i2 + 1;
        bArr[i2] = (byte) i;
        int i4 = i3 + 1;
        bArr[i3] = (byte) (i >>> 8);
        bArr[i4] = (byte) (i >>> 16);
        bArr[i4 + 1] = (byte) (i >>> 24);
    }

    public static void I2OSP(int i, byte[] bArr, int i2, int i3) {
        for (int i4 = i3 - 1; i4 >= 0; i4--) {
            bArr[i2 + i4] = (byte) (i >>> (i4 * 8));
        }
    }

    public static void I2OSP(long j, byte[] bArr, int i) {
        int i2 = i + 1;
        bArr[i] = (byte) ((int) j);
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((int) (j >>> 8));
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((int) (j >>> 16));
        int i5 = i4 + 1;
        bArr[i4] = (byte) ((int) (j >>> 24));
        int i6 = i5 + 1;
        bArr[i5] = (byte) ((int) (j >>> 32));
        int i7 = i6 + 1;
        bArr[i6] = (byte) ((int) (j >>> 40));
        bArr[i7] = (byte) ((int) (j >>> 48));
        bArr[i7 + 1] = (byte) ((int) (j >>> 56));
    }

    public static byte[] I2OSP(int i) {
        return new byte[]{(byte) i, (byte) (i >>> 8), (byte) (i >>> 16), (byte) (i >>> 24)};
    }

    public static byte[] I2OSP(long j) {
        return new byte[]{(byte) ((int) j), (byte) ((int) (j >>> 8)), (byte) ((int) (j >>> 16)), (byte) ((int) (j >>> 24)), (byte) ((int) (j >>> 32)), (byte) ((int) (j >>> 40)), (byte) ((int) (j >>> 48)), (byte) ((int) (j >>> 56))};
    }

    public static int OS2IP(byte[] bArr) {
        return ((bArr[3] & UByte.MAX_VALUE) << 24) | (bArr[0] & UByte.MAX_VALUE) | ((bArr[1] & UByte.MAX_VALUE) << 8) | ((bArr[2] & UByte.MAX_VALUE) << Tnaf.POW_2_WIDTH);
    }

    public static int OS2IP(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = i2 + 1;
        byte b = (bArr[i] & UByte.MAX_VALUE) | ((bArr[i2] & UByte.MAX_VALUE) << 8);
        return ((bArr[i3 + 1] & UByte.MAX_VALUE) << 24) | b | ((bArr[i3] & UByte.MAX_VALUE) << Tnaf.POW_2_WIDTH);
    }

    public static int OS2IP(byte[] bArr, int i, int i2) {
        int i3 = 0;
        for (int i4 = i2 - 1; i4 >= 0; i4--) {
            i3 |= (bArr[i + i4] & UByte.MAX_VALUE) << (i4 * 8);
        }
        return i3;
    }

    public static long OS2LIP(byte[] bArr, int i) {
        int i2 = i + 1;
        long j = (long) (bArr[i] & UByte.MAX_VALUE);
        int i3 = i2 + 1;
        int i4 = i3 + 1;
        int i5 = i4 + 1;
        int i6 = i5 + 1;
        int i7 = i6 + 1;
        return ((((long) bArr[i7 + 1]) & 255) << 56) | j | ((long) ((bArr[i2] & UByte.MAX_VALUE) << 8)) | ((long) ((bArr[i3] & UByte.MAX_VALUE) << Tnaf.POW_2_WIDTH)) | ((((long) bArr[i4]) & 255) << 24) | ((((long) bArr[i5]) & 255) << 32) | ((((long) bArr[i6]) & 255) << 40) | ((((long) bArr[i7]) & 255) << 48);
    }

    public static byte[] toByteArray(int[] iArr, int i) {
        int length = iArr.length;
        byte[] bArr = new byte[i];
        int i2 = 0;
        int i3 = 0;
        while (i2 <= length - 2) {
            I2OSP(iArr[i2], bArr, i3);
            i2++;
            i3 += 4;
        }
        I2OSP(iArr[length - 1], bArr, i3, i - i3);
        return bArr;
    }

    public static int[] toIntArray(byte[] bArr) {
        int length = (bArr.length + 3) / 4;
        int length2 = bArr.length & 3;
        int[] iArr = new int[length];
        int i = 0;
        int i2 = 0;
        while (i <= length - 2) {
            iArr[i] = OS2IP(bArr, i2);
            i++;
            i2 += 4;
        }
        int i3 = length - 1;
        if (length2 != 0) {
            iArr[i3] = OS2IP(bArr, i2, length2);
        } else {
            iArr[i3] = OS2IP(bArr, i2);
        }
        return iArr;
    }
}
