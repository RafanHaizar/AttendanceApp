package org.bouncycastle.math.p018ec.custom.sec;

import com.itextpdf.p026io.codec.TIFFConstants;
import java.math.BigInteger;
import org.bouncycastle.asn1.cmc.BodyPartID;
import org.bouncycastle.crypto.tls.CipherSuite;
import org.bouncycastle.math.raw.Interleave;
import org.bouncycastle.math.raw.Nat;
import org.bouncycastle.math.raw.Nat320;

/* renamed from: org.bouncycastle.math.ec.custom.sec.SecT283Field */
public class SecT283Field {
    private static final long M27 = 134217727;
    private static final long M57 = 144115188075855871L;
    private static final long[] ROOT_Z = {878416384462358536L, 3513665537849438403L, -9076969306111048948L, 585610922974906400L, 34087042};

    public static void add(long[] jArr, long[] jArr2, long[] jArr3) {
        jArr3[0] = jArr[0] ^ jArr2[0];
        jArr3[1] = jArr[1] ^ jArr2[1];
        jArr3[2] = jArr[2] ^ jArr2[2];
        jArr3[3] = jArr[3] ^ jArr2[3];
        jArr3[4] = jArr2[4] ^ jArr[4];
    }

    public static void addExt(long[] jArr, long[] jArr2, long[] jArr3) {
        jArr3[0] = jArr[0] ^ jArr2[0];
        jArr3[1] = jArr[1] ^ jArr2[1];
        jArr3[2] = jArr[2] ^ jArr2[2];
        jArr3[3] = jArr[3] ^ jArr2[3];
        jArr3[4] = jArr[4] ^ jArr2[4];
        jArr3[5] = jArr[5] ^ jArr2[5];
        jArr3[6] = jArr[6] ^ jArr2[6];
        jArr3[7] = jArr[7] ^ jArr2[7];
        jArr3[8] = jArr2[8] ^ jArr[8];
    }

    public static void addOne(long[] jArr, long[] jArr2) {
        jArr2[0] = jArr[0] ^ 1;
        jArr2[1] = jArr[1];
        jArr2[2] = jArr[2];
        jArr2[3] = jArr[3];
        jArr2[4] = jArr[4];
    }

    private static void addTo(long[] jArr, long[] jArr2) {
        jArr2[0] = jArr2[0] ^ jArr[0];
        jArr2[1] = jArr2[1] ^ jArr[1];
        jArr2[2] = jArr2[2] ^ jArr[2];
        jArr2[3] = jArr2[3] ^ jArr[3];
        jArr2[4] = jArr2[4] ^ jArr[4];
    }

    public static long[] fromBigInteger(BigInteger bigInteger) {
        return Nat.fromBigInteger64(TIFFConstants.TIFFTAG_YRESOLUTION, bigInteger);
    }

    public static void halfTrace(long[] jArr, long[] jArr2) {
        long[] create64 = Nat.create64(9);
        Nat320.copy64(jArr, jArr2);
        for (int i = 1; i < 283; i += 2) {
            implSquare(jArr2, create64);
            reduce(create64, jArr2);
            implSquare(jArr2, create64);
            reduce(create64, jArr2);
            addTo(jArr, jArr2);
        }
    }

    protected static void implCompactExt(long[] jArr) {
        long j = jArr[0];
        long j2 = jArr[1];
        long j3 = jArr[2];
        long j4 = jArr[3];
        long j5 = jArr[4];
        long j6 = jArr[5];
        long j7 = jArr[6];
        long j8 = jArr[7];
        long j9 = jArr[8];
        long j10 = jArr[9];
        jArr[0] = j ^ (j2 << 57);
        jArr[1] = (j2 >>> 7) ^ (j3 << 50);
        jArr[2] = (j3 >>> 14) ^ (j4 << 43);
        jArr[3] = (j4 >>> 21) ^ (j5 << 36);
        jArr[4] = (j5 >>> 28) ^ (j6 << 29);
        jArr[5] = (j6 >>> 35) ^ (j7 << 22);
        jArr[6] = (j7 >>> 42) ^ (j8 << 15);
        jArr[7] = (j8 >>> 49) ^ (j9 << 8);
        jArr[8] = (j9 >>> 56) ^ (j10 << 1);
        jArr[9] = j10 >>> 63;
    }

    protected static void implExpand(long[] jArr, long[] jArr2) {
        long j = jArr[0];
        long j2 = jArr[1];
        long j3 = jArr[2];
        long j4 = jArr[3];
        long j5 = jArr[4];
        jArr2[0] = j & M57;
        jArr2[1] = ((j >>> 57) ^ (j2 << 7)) & M57;
        jArr2[2] = ((j2 >>> 50) ^ (j3 << 14)) & M57;
        jArr2[3] = ((j3 >>> 43) ^ (j4 << 21)) & M57;
        jArr2[4] = (j4 >>> 36) ^ (j5 << 28);
    }

    protected static void implMultiply(long[] jArr, long[] jArr2, long[] jArr3) {
        long[] jArr4 = new long[5];
        long[] jArr5 = new long[5];
        implExpand(jArr, jArr4);
        implExpand(jArr2, jArr5);
        long[] jArr6 = new long[26];
        long[] jArr7 = jArr6;
        implMulw(jArr4[0], jArr5[0], jArr7, 0);
        implMulw(jArr4[1], jArr5[1], jArr7, 2);
        implMulw(jArr4[2], jArr5[2], jArr7, 4);
        implMulw(jArr4[3], jArr5[3], jArr7, 6);
        implMulw(jArr4[4], jArr5[4], jArr7, 8);
        long j = jArr4[0];
        long j2 = j ^ jArr4[1];
        long j3 = jArr5[0];
        long j4 = j3 ^ jArr5[1];
        long j5 = jArr4[2];
        long j6 = j ^ j5;
        long j7 = jArr5[2];
        long j8 = j3 ^ j7;
        long j9 = jArr4[4];
        long j10 = j5 ^ j9;
        long j11 = jArr5[4];
        long j12 = j7 ^ j11;
        long j13 = jArr4[3];
        long j14 = j13 ^ j9;
        long j15 = jArr5[3];
        long j16 = j15 ^ j11;
        long[] jArr8 = jArr6;
        implMulw(j6 ^ j13, j8 ^ j15, jArr8, 18);
        implMulw(j10 ^ jArr4[1], j12 ^ jArr5[1], jArr8, 20);
        long j17 = j2 ^ j14;
        long j18 = j4 ^ j16;
        long j19 = j17 ^ jArr4[2];
        long[] jArr9 = jArr6;
        implMulw(j17, j18, jArr9, 22);
        implMulw(j19, jArr5[2] ^ j18, jArr9, 24);
        implMulw(j2, j4, jArr9, 10);
        implMulw(j6, j8, jArr9, 12);
        implMulw(j10, j12, jArr9, 14);
        implMulw(j14, j16, jArr9, 16);
        jArr3[0] = jArr6[0];
        jArr3[9] = jArr6[9];
        long j20 = jArr6[0];
        long j21 = jArr6[1] ^ j20;
        long j22 = jArr6[2] ^ j21;
        long j23 = jArr6[10] ^ j22;
        jArr3[1] = j23;
        long j24 = jArr6[3] ^ jArr6[4];
        long j25 = j22 ^ (j24 ^ (jArr6[11] ^ jArr6[12]));
        jArr3[2] = j25;
        long j26 = j21 ^ j24;
        long j27 = jArr6[5] ^ jArr6[6];
        long j28 = jArr6[8];
        long j29 = (j26 ^ j27) ^ j28;
        long j30 = jArr6[13] ^ jArr6[14];
        long j31 = jArr6[18];
        long j32 = jArr6[22];
        long j33 = jArr6[24];
        jArr3[3] = (j29 ^ j30) ^ ((j31 ^ j32) ^ j33);
        long j34 = jArr6[7] ^ j28;
        long j35 = jArr6[9];
        long j36 = j34 ^ j35;
        long j37 = j36 ^ jArr6[17];
        jArr3[8] = j37;
        long j38 = (j36 ^ j27) ^ (jArr6[15] ^ jArr6[16]);
        jArr3[7] = j38;
        long j39 = j38 ^ j23;
        long j40 = jArr6[19] ^ jArr6[20];
        long j41 = jArr6[25];
        long j42 = j41 ^ j33;
        long j43 = jArr6[23];
        long j44 = j40 ^ j42;
        jArr3[4] = (j44 ^ (j31 ^ j43)) ^ j39;
        long j45 = jArr6[21];
        jArr3[5] = ((j25 ^ j37) ^ j44) ^ (j45 ^ j32);
        jArr3[6] = (((((j29 ^ j20) ^ j35) ^ j30) ^ j45) ^ j43) ^ j41;
        implCompactExt(jArr3);
    }

    protected static void implMulw(long j, long j2, long[] jArr, int i) {
        long j3 = j;
        long[] jArr2 = new long[8];
        jArr2[1] = j2;
        long j4 = j2 << 1;
        jArr2[2] = j4;
        long j5 = j4 ^ j2;
        jArr2[3] = j5;
        long j6 = j4 << 1;
        jArr2[4] = j6;
        jArr2[5] = j6 ^ j2;
        long j7 = j5 << 1;
        jArr2[6] = j7;
        jArr2[7] = j7 ^ j2;
        long j8 = jArr2[((int) j3) & 7];
        long j9 = 0;
        int i2 = 48;
        do {
            int i3 = (int) (j3 >>> i2);
            long j10 = (jArr2[(i3 >>> 6) & 7] << 6) ^ (jArr2[i3 & 7] ^ (jArr2[(i3 >>> 3) & 7] << 3));
            j8 ^= j10 << i2;
            j9 ^= j10 >>> (-i2);
            i2 -= 9;
        } while (i2 > 0);
        jArr[i] = M57 & j8;
        jArr[i + 1] = (((((j3 & 72198606942111744L) & ((j2 << 7) >> 63)) >>> 8) ^ j9) << 7) ^ (j8 >>> 57);
    }

    protected static void implSquare(long[] jArr, long[] jArr2) {
        Interleave.expand64To128(jArr[0], jArr2, 0);
        Interleave.expand64To128(jArr[1], jArr2, 2);
        Interleave.expand64To128(jArr[2], jArr2, 4);
        Interleave.expand64To128(jArr[3], jArr2, 6);
        jArr2[8] = Interleave.expand32to64((int) jArr[4]);
    }

    public static void invert(long[] jArr, long[] jArr2) {
        if (!Nat320.isZero64(jArr)) {
            long[] create64 = Nat320.create64();
            long[] create642 = Nat320.create64();
            square(jArr, create64);
            multiply(create64, jArr, create64);
            squareN(create64, 2, create642);
            multiply(create642, create64, create642);
            squareN(create642, 4, create64);
            multiply(create64, create642, create64);
            squareN(create64, 8, create642);
            multiply(create642, create64, create642);
            square(create642, create642);
            multiply(create642, jArr, create642);
            squareN(create642, 17, create64);
            multiply(create64, create642, create64);
            square(create64, create64);
            multiply(create64, jArr, create64);
            squareN(create64, 35, create642);
            multiply(create642, create64, create642);
            squareN(create642, 70, create64);
            multiply(create64, create642, create64);
            square(create64, create64);
            multiply(create64, jArr, create64);
            squareN(create64, CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA, create642);
            multiply(create642, create64, create642);
            square(create642, jArr2);
            return;
        }
        throw new IllegalStateException();
    }

    public static void multiply(long[] jArr, long[] jArr2, long[] jArr3) {
        long[] createExt64 = Nat320.createExt64();
        implMultiply(jArr, jArr2, createExt64);
        reduce(createExt64, jArr3);
    }

    public static void multiplyAddToExt(long[] jArr, long[] jArr2, long[] jArr3) {
        long[] createExt64 = Nat320.createExt64();
        implMultiply(jArr, jArr2, createExt64);
        addExt(jArr3, createExt64, jArr3);
    }

    public static void reduce(long[] jArr, long[] jArr2) {
        long j = jArr[0];
        long j2 = jArr[1];
        long j3 = jArr[2];
        long j4 = jArr[3];
        long j5 = jArr[4];
        long j6 = jArr[5];
        long j7 = jArr[6];
        long j8 = jArr[7];
        long j9 = jArr[8];
        long j10 = j5 ^ ((((j9 >>> 27) ^ (j9 >>> 22)) ^ (j9 >>> 20)) ^ (j9 >>> 15));
        long j11 = j ^ ((((j6 << 37) ^ (j6 << 42)) ^ (j6 << 44)) ^ (j6 << 49));
        long j12 = (j2 ^ ((((j7 << 37) ^ (j7 << 42)) ^ (j7 << 44)) ^ (j7 << 49))) ^ ((((j6 >>> 27) ^ (j6 >>> 22)) ^ (j6 >>> 20)) ^ (j6 >>> 15));
        long j13 = j10 >>> 27;
        jArr2[0] = (((j11 ^ j13) ^ (j13 << 5)) ^ (j13 << 7)) ^ (j13 << 12);
        jArr2[1] = j12;
        jArr2[2] = (j3 ^ ((((j8 << 37) ^ (j8 << 42)) ^ (j8 << 44)) ^ (j8 << 49))) ^ ((((j7 >>> 27) ^ (j7 >>> 22)) ^ (j7 >>> 20)) ^ (j7 >>> 15));
        jArr2[3] = (j4 ^ ((((j9 << 37) ^ (j9 << 42)) ^ (j9 << 44)) ^ (j9 << 49))) ^ ((((j8 >>> 27) ^ (j8 >>> 22)) ^ (j8 >>> 20)) ^ (j8 >>> 15));
        jArr2[4] = M27 & j10;
    }

    public static void reduce37(long[] jArr, int i) {
        int i2 = i + 4;
        long j = jArr[i2];
        long j2 = j >>> 27;
        jArr[i] = ((j2 << 12) ^ (((j2 << 5) ^ j2) ^ (j2 << 7))) ^ jArr[i];
        jArr[i2] = j & M27;
    }

    public static void sqrt(long[] jArr, long[] jArr2) {
        long[] jArr3 = jArr2;
        long[] create64 = Nat320.create64();
        long unshuffle = Interleave.unshuffle(jArr[0]);
        long unshuffle2 = Interleave.unshuffle(jArr[1]);
        long j = (unshuffle & BodyPartID.bodyIdMax) | (unshuffle2 << 32);
        create64[0] = (unshuffle >>> 32) | (unshuffle2 & -4294967296L);
        long unshuffle3 = Interleave.unshuffle(jArr[2]);
        long unshuffle4 = Interleave.unshuffle(jArr[3]);
        create64[1] = (unshuffle3 >>> 32) | (-4294967296L & unshuffle4);
        long unshuffle5 = Interleave.unshuffle(jArr[4]);
        long j2 = BodyPartID.bodyIdMax & unshuffle5;
        create64[2] = unshuffle5 >>> 32;
        multiply(create64, ROOT_Z, jArr3);
        jArr3[0] = jArr3[0] ^ j;
        jArr3[1] = jArr3[1] ^ ((unshuffle3 & BodyPartID.bodyIdMax) | (unshuffle4 << 32));
        jArr3[2] = jArr3[2] ^ j2;
    }

    public static void square(long[] jArr, long[] jArr2) {
        long[] create64 = Nat.create64(9);
        implSquare(jArr, create64);
        reduce(create64, jArr2);
    }

    public static void squareAddToExt(long[] jArr, long[] jArr2) {
        long[] create64 = Nat.create64(9);
        implSquare(jArr, create64);
        addExt(jArr2, create64, jArr2);
    }

    public static void squareN(long[] jArr, int i, long[] jArr2) {
        long[] create64 = Nat.create64(9);
        implSquare(jArr, create64);
        while (true) {
            reduce(create64, jArr2);
            i--;
            if (i > 0) {
                implSquare(jArr2, create64);
            } else {
                return;
            }
        }
    }

    public static int trace(long[] jArr) {
        return ((int) (jArr[0] ^ (jArr[4] >>> 15))) & 1;
    }
}
