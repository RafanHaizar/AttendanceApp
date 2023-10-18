package org.bouncycastle.crypto.modes.kgcm;

import org.bouncycastle.math.raw.Interleave;

public class KGCMUtil_512 {
    public static final int SIZE = 8;

    public static void add(long[] jArr, long[] jArr2, long[] jArr3) {
        jArr3[0] = jArr[0] ^ jArr2[0];
        jArr3[1] = jArr[1] ^ jArr2[1];
        jArr3[2] = jArr[2] ^ jArr2[2];
        jArr3[3] = jArr[3] ^ jArr2[3];
        jArr3[4] = jArr[4] ^ jArr2[4];
        jArr3[5] = jArr[5] ^ jArr2[5];
        jArr3[6] = jArr[6] ^ jArr2[6];
        jArr3[7] = jArr2[7] ^ jArr[7];
    }

    public static void copy(long[] jArr, long[] jArr2) {
        jArr2[0] = jArr[0];
        jArr2[1] = jArr[1];
        jArr2[2] = jArr[2];
        jArr2[3] = jArr[3];
        jArr2[4] = jArr[4];
        jArr2[5] = jArr[5];
        jArr2[6] = jArr[6];
        jArr2[7] = jArr[7];
    }

    public static boolean equal(long[] jArr, long[] jArr2) {
        return ((jArr2[7] ^ jArr[7]) | ((((((((jArr[0] ^ jArr2[0]) | 0) | (jArr[1] ^ jArr2[1])) | (jArr[2] ^ jArr2[2])) | (jArr[3] ^ jArr2[3])) | (jArr[4] ^ jArr2[4])) | (jArr[5] ^ jArr2[5])) | (jArr[6] ^ jArr2[6]))) == 0;
    }

    public static void multiply(long[] jArr, long[] jArr2, long[] jArr3) {
        int i = 0;
        long j = jArr2[0];
        long j2 = jArr2[1];
        long j3 = jArr2[2];
        long j4 = jArr2[3];
        long j5 = jArr2[4];
        long j6 = jArr2[5];
        long j7 = jArr2[6];
        long j8 = jArr2[7];
        long j9 = 0;
        long j10 = 0;
        long j11 = 0;
        long j12 = 0;
        long j13 = 0;
        long j14 = 0;
        long j15 = 0;
        long j16 = 0;
        long j17 = 0;
        int i2 = 0;
        while (i2 < 8) {
            long j18 = jArr[i2];
            long j19 = jArr[i2 + 1];
            long j20 = j2;
            long j21 = j7;
            j7 = j6;
            j6 = j5;
            j5 = j4;
            j4 = j3;
            long j22 = j20;
            while (i < 64) {
                long j23 = j22;
                long j24 = -(j18 & 1);
                j18 >>>= 1;
                j9 ^= j & j24;
                long j25 = j4;
                long j26 = -(j19 & 1);
                j19 >>>= 1;
                j11 = (j11 ^ (j23 & j24)) ^ (j & j26);
                j12 = (j12 ^ (j4 & j24)) ^ (j23 & j26);
                j13 = (j13 ^ (j5 & j24)) ^ (j25 & j26);
                j14 = (j14 ^ (j6 & j24)) ^ (j5 & j26);
                j15 = (j15 ^ (j7 & j24)) ^ (j6 & j26);
                j16 = (j16 ^ (j21 & j24)) ^ (j7 & j26);
                j17 = (j17 ^ (j8 & j24)) ^ (j21 & j26);
                j10 ^= j8 & j26;
                long j27 = j8 >> 63;
                j8 = (j8 << 1) | (j21 >>> 63);
                j21 = (j21 << 1) | (j7 >>> 63);
                j7 = (j7 << 1) | (j6 >>> 63);
                j6 = (j6 << 1) | (j5 >>> 63);
                j5 = (j5 << 1) | (j25 >>> 63);
                j = (j << 1) ^ (j27 & 293);
                i++;
                j22 = (j23 << 1) | (j >>> 63);
                j4 = (j25 << 1) | (j23 >>> 63);
            }
            long j28 = j4;
            long j29 = ((j ^ (j8 >>> 62)) ^ (j8 >>> 59)) ^ (j8 >>> 56);
            i2 += 2;
            j8 = j21;
            i = 0;
            j2 = j29;
            j = ((j8 ^ (j8 << 2)) ^ (j8 << 5)) ^ (j8 << 8);
            j3 = j22;
        }
        jArr3[0] = j9 ^ (((j10 ^ (j10 << 2)) ^ (j10 << 5)) ^ (j10 << 8));
        jArr3[1] = j11 ^ (((j10 >>> 62) ^ (j10 >>> 59)) ^ (j10 >>> 56));
        jArr3[2] = j12;
        jArr3[3] = j13;
        jArr3[4] = j14;
        jArr3[5] = j15;
        jArr3[6] = j16;
        jArr3[7] = j17;
    }

    public static void multiplyX(long[] jArr, long[] jArr2) {
        long j = jArr[0];
        long j2 = jArr[1];
        long j3 = jArr[2];
        long j4 = jArr[3];
        long j5 = jArr[4];
        long j6 = jArr[5];
        long j7 = jArr[6];
        long j8 = jArr[7];
        jArr2[0] = (j << 1) ^ ((j8 >> 63) & 293);
        jArr2[1] = (j2 << 1) | (j >>> 63);
        jArr2[2] = (j3 << 1) | (j2 >>> 63);
        jArr2[3] = (j4 << 1) | (j3 >>> 63);
        jArr2[4] = (j5 << 1) | (j4 >>> 63);
        jArr2[5] = (j6 << 1) | (j5 >>> 63);
        jArr2[6] = (j7 << 1) | (j6 >>> 63);
        jArr2[7] = (j8 << 1) | (j7 >>> 63);
    }

    public static void multiplyX8(long[] jArr, long[] jArr2) {
        long j = jArr[0];
        long j2 = jArr[1];
        long j3 = jArr[2];
        long j4 = jArr[3];
        long j5 = jArr[4];
        long j6 = jArr[5];
        long j7 = jArr[6];
        long j8 = jArr[7];
        long j9 = j8 >>> 56;
        jArr2[0] = ((((j << 8) ^ j9) ^ (j9 << 2)) ^ (j9 << 5)) ^ (j9 << 8);
        jArr2[1] = (j2 << 8) | (j >>> 56);
        jArr2[2] = (j3 << 8) | (j2 >>> 56);
        jArr2[3] = (j4 << 8) | (j3 >>> 56);
        jArr2[4] = (j5 << 8) | (j4 >>> 56);
        jArr2[5] = (j6 << 8) | (j5 >>> 56);
        jArr2[6] = (j7 << 8) | (j6 >>> 56);
        jArr2[7] = (j8 << 8) | (j7 >>> 56);
    }

    public static void one(long[] jArr) {
        jArr[0] = 1;
        jArr[1] = 0;
        jArr[2] = 0;
        jArr[3] = 0;
        jArr[4] = 0;
        jArr[5] = 0;
        jArr[6] = 0;
        jArr[7] = 0;
    }

    public static void square(long[] jArr, long[] jArr2) {
        int i = 16;
        long[] jArr3 = new long[16];
        for (int i2 = 0; i2 < 8; i2++) {
            Interleave.expand64To128(jArr[i2], jArr3, i2 << 1);
        }
        while (true) {
            i--;
            if (i >= 8) {
                long j = jArr3[i];
                int i3 = i - 8;
                jArr3[i3] = jArr3[i3] ^ ((((j << 2) ^ j) ^ (j << 5)) ^ (j << 8));
                int i4 = i3 + 1;
                jArr3[i4] = ((j >>> 56) ^ ((j >>> 62) ^ (j >>> 59))) ^ jArr3[i4];
            } else {
                copy(jArr3, jArr2);
                return;
            }
        }
    }

    /* renamed from: x */
    public static void m126x(long[] jArr) {
        jArr[0] = 2;
        jArr[1] = 0;
        jArr[2] = 0;
        jArr[3] = 0;
        jArr[4] = 0;
        jArr[5] = 0;
        jArr[6] = 0;
        jArr[7] = 0;
    }

    public static void zero(long[] jArr) {
        jArr[0] = 0;
        jArr[1] = 0;
        jArr[2] = 0;
        jArr[3] = 0;
        jArr[4] = 0;
        jArr[5] = 0;
        jArr[6] = 0;
        jArr[7] = 0;
    }
}
