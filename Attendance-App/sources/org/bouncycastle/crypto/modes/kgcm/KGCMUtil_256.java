package org.bouncycastle.crypto.modes.kgcm;

import org.bouncycastle.math.raw.Interleave;

public class KGCMUtil_256 {
    public static final int SIZE = 4;

    public static void add(long[] jArr, long[] jArr2, long[] jArr3) {
        jArr3[0] = jArr[0] ^ jArr2[0];
        jArr3[1] = jArr[1] ^ jArr2[1];
        jArr3[2] = jArr[2] ^ jArr2[2];
        jArr3[3] = jArr2[3] ^ jArr[3];
    }

    public static void copy(long[] jArr, long[] jArr2) {
        jArr2[0] = jArr[0];
        jArr2[1] = jArr[1];
        jArr2[2] = jArr[2];
        jArr2[3] = jArr[3];
    }

    public static boolean equal(long[] jArr, long[] jArr2) {
        return ((jArr2[3] ^ jArr[3]) | ((((jArr[0] ^ jArr2[0]) | 0) | (jArr[1] ^ jArr2[1])) | (jArr[2] ^ jArr2[2]))) == 0;
    }

    public static void multiply(long[] jArr, long[] jArr2, long[] jArr3) {
        int i;
        long j;
        long j2 = jArr[0];
        long j3 = jArr[1];
        long j4 = jArr[2];
        long j5 = jArr[3];
        long j6 = jArr2[0];
        long j7 = jArr2[1];
        long j8 = jArr2[2];
        long j9 = jArr2[3];
        long j10 = 0;
        long j11 = 0;
        long j12 = 0;
        long j13 = 0;
        long j14 = 0;
        int i2 = 0;
        while (true) {
            j = j4;
            if (i2 >= 64) {
                break;
            }
            long j15 = -(j2 & 1);
            j10 ^= j6 & j15;
            long j16 = -(j3 & 1);
            j3 >>>= 1;
            j11 = (j11 ^ (j7 & j15)) ^ (j6 & j16);
            j12 = (j12 ^ (j8 & j15)) ^ (j7 & j16);
            j13 = (j13 ^ (j9 & j15)) ^ (j8 & j16);
            j14 ^= j9 & j16;
            long j17 = j9 >> 63;
            j9 = (j9 << 1) | (j8 >>> 63);
            j8 = (j8 << 1) | (j7 >>> 63);
            j7 = (j6 >>> 63) | (j7 << 1);
            j6 = (j6 << 1) ^ (j17 & 1061);
            i2++;
            j2 >>>= 1;
            j4 = j;
        }
        long j18 = (((j9 >>> 62) ^ j6) ^ (j9 >>> 59)) ^ (j9 >>> 54);
        long j19 = ((j9 ^ (j9 << 2)) ^ (j9 << 5)) ^ (j9 << 10);
        int i3 = 0;
        for (i = 64; i3 < i; i = 64) {
            long j20 = -(j & 1);
            j >>>= 1;
            j10 ^= j19 & j20;
            long j21 = -(j5 & 1);
            j5 >>>= 1;
            long j22 = (j11 ^ (j18 & j20)) ^ (j19 & j21);
            j12 = (j12 ^ (j7 & j20)) ^ (j18 & j21);
            j13 = (j13 ^ (j8 & j20)) ^ (j7 & j21);
            j14 ^= j8 & j21;
            long j23 = j8 >> 63;
            j8 = (j8 << 1) | (j7 >>> 63);
            j7 = (j18 >>> 63) | (j7 << 1);
            j18 = (j18 << 1) | (j19 >>> 63);
            j19 = (j19 << 1) ^ (j23 & 1061);
            i3++;
            j11 = j22;
        }
        jArr3[0] = j10 ^ (((j14 ^ (j14 << 2)) ^ (j14 << 5)) ^ (j14 << 10));
        jArr3[1] = j11 ^ (((j14 >>> 62) ^ (j14 >>> 59)) ^ (j14 >>> 54));
        jArr3[2] = j12;
        jArr3[3] = j13;
    }

    public static void multiplyX(long[] jArr, long[] jArr2) {
        long j = jArr[0];
        long j2 = jArr[1];
        long j3 = jArr[2];
        long j4 = jArr[3];
        jArr2[0] = ((j4 >> 63) & 1061) ^ (j << 1);
        jArr2[1] = (j >>> 63) | (j2 << 1);
        jArr2[2] = (j3 << 1) | (j2 >>> 63);
        jArr2[3] = (j4 << 1) | (j3 >>> 63);
    }

    public static void multiplyX8(long[] jArr, long[] jArr2) {
        long j = jArr[0];
        long j2 = jArr[1];
        long j3 = jArr[2];
        long j4 = jArr[3];
        long j5 = j4 >>> 56;
        jArr2[0] = ((((j << 8) ^ j5) ^ (j5 << 2)) ^ (j5 << 5)) ^ (j5 << 10);
        jArr2[1] = (j >>> 56) | (j2 << 8);
        jArr2[2] = (j3 << 8) | (j2 >>> 56);
        jArr2[3] = (j4 << 8) | (j3 >>> 56);
    }

    public static void one(long[] jArr) {
        jArr[0] = 1;
        jArr[1] = 0;
        jArr[2] = 0;
        jArr[3] = 0;
    }

    public static void square(long[] jArr, long[] jArr2) {
        int i = 8;
        long[] jArr3 = new long[8];
        for (int i2 = 0; i2 < 4; i2++) {
            Interleave.expand64To128(jArr[i2], jArr3, i2 << 1);
        }
        while (true) {
            i--;
            if (i >= 4) {
                long j = jArr3[i];
                int i3 = i - 4;
                jArr3[i3] = jArr3[i3] ^ ((((j << 2) ^ j) ^ (j << 5)) ^ (j << 10));
                int i4 = i3 + 1;
                jArr3[i4] = ((j >>> 54) ^ ((j >>> 62) ^ (j >>> 59))) ^ jArr3[i4];
            } else {
                copy(jArr3, jArr2);
                return;
            }
        }
    }

    /* renamed from: x */
    public static void m125x(long[] jArr) {
        jArr[0] = 2;
        jArr[1] = 0;
        jArr[2] = 0;
        jArr[3] = 0;
    }

    public static void zero(long[] jArr) {
        jArr[0] = 0;
        jArr[1] = 0;
        jArr[2] = 0;
        jArr[3] = 0;
    }
}
