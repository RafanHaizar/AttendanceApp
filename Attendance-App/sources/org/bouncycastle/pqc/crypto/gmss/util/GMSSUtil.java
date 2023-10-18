package org.bouncycastle.pqc.crypto.gmss.util;

import kotlin.UByte;

public class GMSSUtil {
    public int bytesToIntLittleEndian(byte[] bArr) {
        return ((bArr[3] & UByte.MAX_VALUE) << 24) | (bArr[0] & UByte.MAX_VALUE) | ((bArr[1] & UByte.MAX_VALUE) << 8) | ((bArr[2] & UByte.MAX_VALUE) << Tnaf.POW_2_WIDTH);
    }

    public int bytesToIntLittleEndian(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = i2 + 1;
        byte b = (bArr[i] & UByte.MAX_VALUE) | ((bArr[i2] & UByte.MAX_VALUE) << 8);
        return ((bArr[i3 + 1] & UByte.MAX_VALUE) << 24) | b | ((bArr[i3] & UByte.MAX_VALUE) << Tnaf.POW_2_WIDTH);
    }

    public byte[] concatenateArray(byte[][] bArr) {
        byte[] bArr2 = new byte[(bArr.length * bArr[0].length)];
        int i = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            byte[] bArr3 = bArr[i2];
            System.arraycopy(bArr3, 0, bArr2, i, bArr3.length);
            i += bArr[i2].length;
        }
        return bArr2;
    }

    public int getLog(int i) {
        int i2 = 1;
        int i3 = 2;
        while (i3 < i) {
            i3 <<= 1;
            i2++;
        }
        return i2;
    }

    public byte[] intToBytesLittleEndian(int i) {
        return new byte[]{(byte) (i & 255), (byte) ((i >> 8) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 24) & 255)};
    }

    public void printArray(String str, byte[] bArr) {
        System.out.println(str);
        int i = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            System.out.println(i + "; " + bArr[i2]);
            i++;
        }
    }

    public void printArray(String str, byte[][] bArr) {
        System.out.println(str);
        int i = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            for (int i3 = 0; i3 < bArr[0].length; i3++) {
                System.out.println(i + "; " + bArr[i2][i3]);
                i++;
            }
        }
    }

    public boolean testPowerOfTwo(int i) {
        int i2 = 1;
        while (i2 < i) {
            i2 <<= 1;
        }
        return i == i2;
    }
}
