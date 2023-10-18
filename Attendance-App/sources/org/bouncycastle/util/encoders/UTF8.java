package org.bouncycastle.util.encoders;

import kotlin.jvm.internal.ByteCompanionObject;

public class UTF8 {
    private static final byte C_CR1 = 1;
    private static final byte C_CR2 = 2;
    private static final byte C_CR3 = 3;
    private static final byte C_ILL = 0;
    private static final byte C_L2A = 4;
    private static final byte C_L3A = 5;
    private static final byte C_L3B = 6;
    private static final byte C_L3C = 7;
    private static final byte C_L4A = 8;
    private static final byte C_L4B = 9;
    private static final byte C_L4C = 10;
    private static final byte S_CS1 = 0;
    private static final byte S_CS2 = 16;
    private static final byte S_CS3 = 32;
    private static final byte S_END = -1;
    private static final byte S_ERR = -2;
    private static final byte S_P3A = 48;
    private static final byte S_P3B = 64;
    private static final byte S_P4A = 80;
    private static final byte S_P4B = 96;
    private static final short[] firstUnitTable = new short[128];
    private static final byte[] transitionTable;

    static {
        byte[] bArr = new byte[112];
        transitionTable = bArr;
        byte[] bArr2 = new byte[128];
        fill(bArr2, 0, 15, (byte) 1);
        fill(bArr2, 16, 31, (byte) 2);
        fill(bArr2, 32, 63, (byte) 3);
        fill(bArr2, 64, 65, (byte) 0);
        fill(bArr2, 66, 95, (byte) 4);
        fill(bArr2, 96, 96, (byte) 5);
        fill(bArr2, 97, 108, (byte) 6);
        fill(bArr2, 109, 109, (byte) 7);
        fill(bArr2, 110, 111, (byte) 6);
        fill(bArr2, 112, 112, (byte) 8);
        fill(bArr2, 113, 115, (byte) 9);
        fill(bArr2, 116, 116, (byte) 10);
        fill(bArr2, 117, 127, (byte) 0);
        fill(bArr, 0, bArr.length - 1, S_ERR);
        fill(bArr, 8, 11, (byte) -1);
        fill(bArr, 24, 27, (byte) 0);
        fill(bArr, 40, 43, (byte) 16);
        fill(bArr, 58, 59, (byte) 0);
        fill(bArr, 72, 73, (byte) 0);
        fill(bArr, 89, 91, (byte) 16);
        fill(bArr, 104, 104, (byte) 16);
        byte[] bArr3 = {0, 0, 0, 0, 31, 15, 15, 15, 7, 7, 7};
        byte[] bArr4 = {S_ERR, S_ERR, S_ERR, S_ERR, 0, S_P3A, 16, S_P3B, S_P4A, S_CS3, S_P4B};
        for (int i = 0; i < 128; i++) {
            byte b = bArr2[i];
            firstUnitTable[i] = (short) (bArr4[b] | ((bArr3[b] & i) << 8));
        }
    }

    private static void fill(byte[] bArr, int i, int i2, byte b) {
        while (i <= i2) {
            bArr[i] = b;
            i++;
        }
    }

    public static int transcodeToUTF16(byte[] bArr, char[] cArr) {
        int i = 0;
        int i2 = 0;
        while (i < bArr.length) {
            int i3 = i + 1;
            byte b = bArr[i];
            if (b < 0) {
                short s = firstUnitTable[b & ByteCompanionObject.MAX_VALUE];
                int i4 = s >>> 8;
                byte b2 = (byte) s;
                while (b2 >= 0) {
                    if (i3 >= bArr.length) {
                        return -1;
                    }
                    int i5 = i3 + 1;
                    byte b3 = bArr[i3];
                    i4 = (i4 << 6) | (b3 & 63);
                    b2 = transitionTable[b2 + ((b3 & 255) >>> 4)];
                    i3 = i5;
                }
                if (b2 == -2) {
                    return -1;
                }
                if (i4 <= 65535) {
                    if (i2 >= cArr.length) {
                        return -1;
                    }
                    cArr[i2] = (char) i4;
                    i2++;
                } else if (i2 >= cArr.length - 1) {
                    return -1;
                } else {
                    int i6 = i2 + 1;
                    cArr[i2] = (char) ((i4 >>> 10) + 55232);
                    i2 = i6 + 1;
                    cArr[i6] = (char) ((i4 & 1023) | 56320);
                }
                i = i3;
            } else if (i2 >= cArr.length) {
                return -1;
            } else {
                cArr[i2] = (char) b;
                i = i3;
                i2++;
            }
        }
        return i2;
    }
}
