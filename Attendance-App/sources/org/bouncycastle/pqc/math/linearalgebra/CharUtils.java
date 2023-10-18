package org.bouncycastle.pqc.math.linearalgebra;

public final class CharUtils {
    private CharUtils() {
    }

    public static char[] clone(char[] cArr) {
        char[] cArr2 = new char[cArr.length];
        System.arraycopy(cArr, 0, cArr2, 0, cArr.length);
        return cArr2;
    }

    public static boolean equals(char[] cArr, char[] cArr2) {
        if (cArr.length != cArr2.length) {
            return false;
        }
        boolean z = true;
        for (int length = cArr.length - 1; length >= 0; length--) {
            z &= cArr[length] == cArr2[length];
        }
        return z;
    }

    public static byte[] toByteArray(char[] cArr) {
        byte[] bArr = new byte[cArr.length];
        for (int length = cArr.length - 1; length >= 0; length--) {
            bArr[length] = (byte) cArr[length];
        }
        return bArr;
    }

    public static byte[] toByteArrayForPBE(char[] cArr) {
        int length = cArr.length;
        byte[] bArr = new byte[length];
        for (int i = 0; i < cArr.length; i++) {
            bArr[i] = (byte) cArr[i];
        }
        int i2 = length * 2;
        byte[] bArr2 = new byte[(i2 + 2)];
        for (int i3 = 0; i3 < length; i3++) {
            int i4 = i3 * 2;
            bArr2[i4] = 0;
            bArr2[i4 + 1] = bArr[i3];
        }
        bArr2[i2] = 0;
        bArr2[i2 + 1] = 0;
        return bArr2;
    }
}
