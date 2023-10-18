package org.bouncycastle.math.p018ec.rfc7748;

import kotlin.UByte;

/* renamed from: org.bouncycastle.math.ec.rfc7748.X25519Field */
public abstract class X25519Field {
    private static final int M24 = 16777215;
    private static final int M25 = 33554431;
    private static final int M26 = 67108863;
    private static final int[] ROOT_NEG_ONE = {34513072, 59165138, 4688974, 3500415, 6194736, 33281959, 54535759, 32551604, 163342, 5703241};
    public static final int SIZE = 10;

    protected X25519Field() {
    }

    public static void add(int[] iArr, int[] iArr2, int[] iArr3) {
        for (int i = 0; i < 10; i++) {
            iArr3[i] = iArr[i] + iArr2[i];
        }
    }

    public static void addOne(int[] iArr) {
        iArr[0] = iArr[0] + 1;
    }

    public static void addOne(int[] iArr, int i) {
        iArr[i] = iArr[i] + 1;
    }

    public static void apm(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4) {
        for (int i = 0; i < 10; i++) {
            int i2 = iArr[i];
            int i3 = iArr2[i];
            iArr3[i] = i2 + i3;
            iArr4[i] = i2 - i3;
        }
    }

    public static void carry(int[] iArr) {
        int i = iArr[0];
        int i2 = iArr[1];
        int i3 = iArr[2];
        int i4 = iArr[3];
        int i5 = iArr[4];
        int i6 = iArr[5];
        int i7 = iArr[6];
        int i8 = iArr[7];
        int i9 = iArr[8];
        int i10 = iArr[9];
        int i11 = i4 + (i3 >> 25);
        int i12 = i3 & M25;
        int i13 = i6 + (i5 >> 25);
        int i14 = i5 & M25;
        int i15 = i9 + (i8 >> 25);
        int i16 = i8 & M25;
        int i17 = i + ((i10 >> 25) * 38);
        int i18 = i10 & M25;
        int i19 = i2 + (i17 >> 26);
        int i20 = i17 & M26;
        int i21 = i7 + (i13 >> 26);
        int i22 = i13 & M26;
        int i23 = i12 + (i19 >> 26);
        int i24 = i19 & M26;
        int i25 = i14 + (i11 >> 26);
        int i26 = i11 & M26;
        int i27 = i16 + (i21 >> 26);
        int i28 = i21 & M26;
        int i29 = i15 & M26;
        iArr[0] = i20;
        iArr[1] = i24;
        iArr[2] = i23;
        iArr[3] = i26;
        iArr[4] = i25;
        iArr[5] = i22;
        iArr[6] = i28;
        iArr[7] = i27;
        iArr[8] = i29;
        iArr[9] = i18 + (i15 >> 26);
    }

    public static void cmov(int i, int[] iArr, int i2, int[] iArr2, int i3) {
        for (int i4 = 0; i4 < 10; i4++) {
            int i5 = i3 + i4;
            int i6 = iArr2[i5];
            iArr2[i5] = i6 ^ ((iArr[i2 + i4] ^ i6) & i);
        }
    }

    public static void cnegate(int i, int[] iArr) {
        int i2 = 0 - i;
        for (int i3 = 0; i3 < 10; i3++) {
            iArr[i3] = (iArr[i3] ^ i2) - i2;
        }
    }

    public static void copy(int[] iArr, int i, int[] iArr2, int i2) {
        for (int i3 = 0; i3 < 10; i3++) {
            iArr2[i2 + i3] = iArr[i + i3];
        }
    }

    public static int[] create() {
        return new int[10];
    }

    public static int[] createTable(int i) {
        return new int[(i * 10)];
    }

    public static void cswap(int i, int[] iArr, int[] iArr2) {
        int i2 = 0 - i;
        for (int i3 = 0; i3 < 10; i3++) {
            int i4 = iArr[i3];
            int i5 = iArr2[i3];
            int i6 = (i4 ^ i5) & i2;
            iArr[i3] = i4 ^ i6;
            iArr2[i3] = i5 ^ i6;
        }
    }

    public static void decode(byte[] bArr, int i, int[] iArr) {
        decode128(bArr, i, iArr, 0);
        decode128(bArr, i + 16, iArr, 5);
        iArr[9] = iArr[9] & 16777215;
    }

    private static void decode128(byte[] bArr, int i, int[] iArr, int i2) {
        int decode32 = decode32(bArr, i + 0);
        int decode322 = decode32(bArr, i + 4);
        int decode323 = decode32(bArr, i + 8);
        int decode324 = decode32(bArr, i + 12);
        iArr[i2 + 0] = decode32 & M26;
        iArr[i2 + 1] = ((decode32 >>> 26) | (decode322 << 6)) & M26;
        iArr[i2 + 2] = ((decode323 << 12) | (decode322 >>> 20)) & M25;
        iArr[i2 + 3] = ((decode324 << 19) | (decode323 >>> 13)) & M26;
        iArr[i2 + 4] = decode324 >>> 7;
    }

    private static int decode32(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = i2 + 1;
        return (bArr[i3 + 1] << 24) | (bArr[i] & UByte.MAX_VALUE) | ((bArr[i2] & UByte.MAX_VALUE) << 8) | ((bArr[i3] & UByte.MAX_VALUE) << Tnaf.POW_2_WIDTH);
    }

    public static void encode(int[] iArr, byte[] bArr, int i) {
        encode128(iArr, 0, bArr, i);
        encode128(iArr, 5, bArr, i + 16);
    }

    private static void encode128(int[] iArr, int i, byte[] bArr, int i2) {
        int i3 = iArr[i + 0];
        int i4 = iArr[i + 1];
        int i5 = iArr[i + 2];
        int i6 = iArr[i + 3];
        int i7 = iArr[i + 4];
        encode32((i4 << 26) | i3, bArr, i2 + 0);
        encode32((i4 >>> 6) | (i5 << 20), bArr, i2 + 4);
        encode32((i5 >>> 12) | (i6 << 13), bArr, i2 + 8);
        encode32((i7 << 7) | (i6 >>> 19), bArr, i2 + 12);
    }

    private static void encode32(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) i;
        int i3 = i2 + 1;
        bArr[i3] = (byte) (i >>> 8);
        int i4 = i3 + 1;
        bArr[i4] = (byte) (i >>> 16);
        bArr[i4 + 1] = (byte) (i >>> 24);
    }

    public static void inv(int[] iArr, int[] iArr2) {
        int[] create = create();
        int[] create2 = create();
        powPm5d8(iArr, create, create2);
        sqr(create2, 3, create2);
        mul(create2, create, iArr2);
    }

    public static int isZero(int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 < 10; i2++) {
            i |= iArr[i2];
        }
        return (((i >>> 1) | (i & 1)) - 1) >> 31;
    }

    public static boolean isZeroVar(int[] iArr) {
        return isZero(iArr) != 0;
    }

    public static void mul(int[] iArr, int i, int[] iArr2) {
        int i2 = iArr[0];
        int i3 = iArr[1];
        int i4 = iArr[2];
        int i5 = iArr[3];
        int i6 = iArr[4];
        int i7 = iArr[5];
        int i8 = iArr[6];
        int i9 = iArr[7];
        int i10 = iArr[8];
        int i11 = iArr[9];
        long j = (long) i;
        long j2 = ((long) i4) * j;
        int i12 = ((int) j2) & M25;
        long j3 = ((long) i6) * j;
        int i13 = ((int) j3) & M25;
        long j4 = ((long) i9) * j;
        int i14 = ((int) j4) & M25;
        long j5 = ((long) i11) * j;
        int i15 = ((int) j5) & M25;
        long j6 = ((j5 >> 25) * 38) + (((long) i2) * j);
        iArr2[0] = ((int) j6) & M26;
        long j7 = (j3 >> 25) + (((long) i7) * j);
        iArr2[5] = ((int) j7) & M26;
        long j8 = (j6 >> 26) + (((long) i3) * j);
        iArr2[1] = ((int) j8) & M26;
        long j9 = (j2 >> 25) + (((long) i5) * j);
        iArr2[3] = ((int) j9) & M26;
        long j10 = (j7 >> 26) + (((long) i8) * j);
        iArr2[6] = ((int) j10) & M26;
        long j11 = (j4 >> 25) + (((long) i10) * j);
        iArr2[8] = ((int) j11) & M26;
        iArr2[2] = i12 + ((int) (j8 >> 26));
        iArr2[4] = i13 + ((int) (j9 >> 26));
        iArr2[7] = i14 + ((int) (j10 >> 26));
        iArr2[9] = i15 + ((int) (j11 >> 26));
    }

    public static void mul(int[] iArr, int[] iArr2, int[] iArr3) {
        int i = iArr[0];
        int i2 = iArr2[0];
        int i3 = iArr[1];
        int i4 = iArr2[1];
        int i5 = iArr[2];
        int i6 = iArr2[2];
        int i7 = iArr[3];
        int i8 = iArr2[3];
        int i9 = iArr[4];
        int i10 = iArr2[4];
        int i11 = iArr[5];
        int i12 = iArr2[5];
        int i13 = iArr[6];
        int i14 = iArr2[6];
        int i15 = iArr[7];
        int i16 = iArr2[7];
        int i17 = i15;
        int i18 = iArr[8];
        int i19 = iArr2[8];
        int i20 = iArr[9];
        int i21 = i11;
        long j = (long) i;
        int i22 = iArr2[9];
        long j2 = (long) i2;
        long j3 = j * j2;
        int i23 = i2;
        int i24 = i16;
        long j4 = (long) i4;
        int i25 = i4;
        long j5 = (long) i3;
        long j6 = (j * j4) + (j5 * j2);
        int i26 = i10;
        int i27 = i13;
        long j7 = (long) i6;
        int i28 = i6;
        long j8 = (long) i5;
        long j9 = (j * j7) + (j5 * j4) + (j8 * j2);
        long j10 = j4;
        long j11 = (long) i8;
        long j12 = j * j11;
        long j13 = j11;
        long j14 = (long) i7;
        long j15 = (((j5 * j7) + (j8 * j4)) << 1) + j12 + (j14 * j2);
        int i29 = i8;
        long j16 = j7;
        long j17 = (long) i26;
        long j18 = (j * j17) + (j5 * j13) + (j14 * j10);
        long j19 = j14;
        int i30 = i9;
        long j20 = (long) i30;
        long j21 = ((j8 * j7) << 1) + j18 + (j2 * j20);
        long j22 = (((j8 * j17) + (j20 * j16)) << 1) + (j19 * j13);
        long j23 = (j19 * j17) + (j20 * j13);
        int i31 = i21;
        long j24 = (long) i31;
        int i32 = i7;
        int i33 = i12;
        long j25 = (long) i33;
        long j26 = j24 * j25;
        int i34 = i33;
        int i35 = i5;
        int i36 = i14;
        int i37 = i3;
        long j27 = (long) i36;
        int i38 = i36;
        int i39 = i31;
        long j28 = (long) i27;
        long j29 = (j24 * j27) + (j28 * j25);
        long j30 = j23;
        long j31 = (long) i24;
        long j32 = j22;
        long j33 = (long) i17;
        long j34 = (j24 * j31) + (j28 * j27) + (j33 * j25);
        long j35 = ((((j5 * j17) + (j8 * j13)) + (j19 * j16)) + (j20 * j10)) << 1;
        long j36 = (long) i19;
        long j37 = j27;
        int i40 = i39;
        long j38 = (long) i18;
        long j39 = (((j28 * j31) + (j33 * j27)) << 1) + (j24 * j36) + (j38 * j25);
        long j40 = j31;
        long j41 = (long) i22;
        long j42 = (j24 * j41) + (j28 * j36) + (j38 * j37);
        long j43 = j38;
        int i41 = i20;
        long j44 = (long) i41;
        long j45 = ((j33 * j31) << 1) + j42 + (j25 * j44);
        long j46 = (j28 * j41) + (j33 * j36) + (j43 * j40) + (j44 * j37);
        long j47 = j3 - (j46 * 76);
        long j48 = j6 - (((((j33 * j41) + (j44 * j40)) << 1) + (j43 * j36)) * 38);
        long j49 = j9 - (((j43 * j41) + (j36 * j44)) * 38);
        long j50 = j15 - ((j44 * j41) * 76);
        int i42 = i35 + i17;
        long j51 = j30 - j34;
        int i43 = i29 + i19;
        int i44 = i30 + i41;
        int i45 = i26 + i22;
        long j52 = (long) (i + i40);
        long j53 = j35 - j26;
        long j54 = (long) (i23 + i34);
        long j55 = j52 * j54;
        long j56 = ((j20 * j17) << 1) - j39;
        long j57 = (long) (i25 + i38);
        long j58 = (long) (i37 + i27);
        long j59 = (j52 * j57) + (j58 * j54);
        int i46 = i44;
        long j60 = (long) (i28 + i24);
        int i47 = i46;
        long j61 = (long) i42;
        long j62 = j57;
        long j63 = (long) i43;
        long j64 = j63;
        long j65 = (long) (i32 + i18);
        long j66 = (((j58 * j60) + (j61 * j57)) << 1) + (j52 * j63) + (j65 * j54);
        long j67 = j60;
        long j68 = (long) i45;
        long j69 = (long) i47;
        long j70 = (((j61 * j68) + (j69 * j67)) << 1) + (j65 * j64);
        long j71 = j56 + (j66 - j50);
        int i48 = ((int) j71) & M26;
        long j72 = (j71 >> 26) + (((((j61 * j60) << 1) + ((((j52 * j68) + (j58 * j64)) + (j65 * j62)) + (j54 * j69))) - j21) - j45);
        int i49 = ((int) j72) & M25;
        long j73 = j47 + ((((j72 >> 25) + (((((j58 * j68) + (j61 * j64)) + (j65 * j67)) + (j69 * j62)) << 1)) - j53) * 38);
        iArr3[0] = ((int) j73) & M26;
        long j74 = j32 - j29;
        long j75 = (j73 >> 26) + j48 + ((j70 - j74) * 38);
        iArr3[1] = ((int) j75) & M26;
        long j76 = (j75 >> 26) + j49 + ((((j65 * j68) + (j69 * j64)) - j51) * 38);
        iArr3[2] = ((int) j76) & M25;
        long j77 = (j76 >> 25) + j50 + ((((j69 * j68) << 1) - j56) * 38);
        iArr3[3] = ((int) j77) & M26;
        long j78 = (j77 >> 26) + j21 + (j45 * 38);
        iArr3[4] = ((int) j78) & M25;
        long j79 = (j78 >> 25) + j53 + (j55 - j47);
        iArr3[5] = ((int) j79) & M26;
        long j80 = (j79 >> 26) + j74 + (j59 - j48);
        iArr3[6] = ((int) j80) & M26;
        long j81 = (j80 >> 26) + j51 + ((((j52 * j60) + (j58 * j57)) + (j61 * j54)) - j49);
        iArr3[7] = ((int) j81) & M25;
        long j82 = (j81 >> 25) + ((long) i48);
        iArr3[8] = ((int) j82) & M26;
        iArr3[9] = i49 + ((int) (j82 >> 26));
    }

    public static void negate(int[] iArr, int[] iArr2) {
        for (int i = 0; i < 10; i++) {
            iArr2[i] = -iArr[i];
        }
    }

    public static void normalize(int[] iArr) {
        int i = (iArr[9] >>> 23) & 1;
        reduce(iArr, i);
        reduce(iArr, -i);
    }

    public static void one(int[] iArr) {
        iArr[0] = 1;
        for (int i = 1; i < 10; i++) {
            iArr[i] = 0;
        }
    }

    private static void powPm5d8(int[] iArr, int[] iArr2, int[] iArr3) {
        sqr(iArr, iArr2);
        mul(iArr, iArr2, iArr2);
        int[] create = create();
        sqr(iArr2, create);
        mul(iArr, create, create);
        sqr(create, 2, create);
        mul(iArr2, create, create);
        int[] create2 = create();
        sqr(create, 5, create2);
        mul(create, create2, create2);
        int[] create3 = create();
        sqr(create2, 5, create3);
        mul(create, create3, create3);
        sqr(create3, 10, create);
        mul(create2, create, create);
        sqr(create, 25, create2);
        mul(create, create2, create2);
        sqr(create2, 25, create3);
        mul(create, create3, create3);
        sqr(create3, 50, create);
        mul(create2, create, create);
        sqr(create, 125, create2);
        mul(create, create2, create2);
        sqr(create2, 2, create);
        mul(create, iArr, iArr3);
    }

    private static void reduce(int[] iArr, int i) {
        int i2 = iArr[9];
        int i3 = 16777215 & i2;
        int i4 = (((i2 >> 24) + i) * 19) + iArr[0];
        iArr[0] = i4 & M26;
        int i5 = (i4 >> 26) + iArr[1];
        iArr[1] = i5 & M26;
        int i6 = (i5 >> 26) + iArr[2];
        iArr[2] = i6 & M25;
        int i7 = (i6 >> 25) + iArr[3];
        iArr[3] = i7 & M26;
        int i8 = (i7 >> 26) + iArr[4];
        iArr[4] = i8 & M25;
        int i9 = (i8 >> 25) + iArr[5];
        iArr[5] = i9 & M26;
        int i10 = (i9 >> 26) + iArr[6];
        iArr[6] = i10 & M26;
        int i11 = (i10 >> 26) + iArr[7];
        iArr[7] = M25 & i11;
        int i12 = (i11 >> 25) + iArr[8];
        iArr[8] = M26 & i12;
        iArr[9] = (i12 >> 26) + i3;
    }

    public static void sqr(int[] iArr, int i, int[] iArr2) {
        sqr(iArr, iArr2);
        while (true) {
            i--;
            if (i > 0) {
                sqr(iArr2, iArr2);
            } else {
                return;
            }
        }
    }

    public static void sqr(int[] iArr, int[] iArr2) {
        int i = iArr[0];
        int i2 = iArr[1];
        int i3 = iArr[2];
        int i4 = iArr[3];
        int i5 = iArr[4];
        int i6 = iArr[5];
        int i7 = iArr[6];
        int i8 = iArr[7];
        long j = (long) i;
        long j2 = j * j;
        long j3 = (long) (i2 * 2);
        long j4 = j * j3;
        long j5 = (long) (i3 * 2);
        int i9 = iArr[8];
        int i10 = i2;
        int i11 = i8;
        long j6 = (long) i10;
        long j7 = (j * j5) + (j6 * j6);
        int i12 = iArr[9];
        int i13 = i7;
        long j8 = (long) (i4 * 2);
        long j9 = (j3 * j5) + (j * j8);
        int i14 = i10;
        int i15 = i5;
        long j10 = (long) (i5 * 2);
        long j11 = (((long) i3) * j5) + (j * j10) + (j6 * j8);
        long j12 = (j3 * j10) + (j8 * j5);
        long j13 = (long) i4;
        long j14 = (j5 * j10) + (j13 * j13);
        long j15 = ((long) i15) * j10;
        long j16 = j11;
        int i16 = i6;
        int i17 = i15;
        long j17 = (long) i16;
        long j18 = j17 * j17;
        int i18 = i16;
        long j19 = (long) (i13 * 2);
        long j20 = j17 * j19;
        long j21 = j15;
        long j22 = (long) (i11 * 2);
        long j23 = j13 * j10;
        long j24 = (long) i13;
        long j25 = (j17 * j22) + (j24 * j24);
        long j26 = (long) (i9 * 2);
        long j27 = (j19 * j22) + (j17 * j26);
        long j28 = j14;
        long j29 = (long) (i12 * 2);
        long j30 = (((long) i11) * j22) + (j17 * j29) + (j24 * j26);
        long j31 = (j19 * j29) + (j26 * j22);
        int i19 = i9;
        long j32 = (long) i19;
        int i20 = i12;
        long j33 = j2 - (j31 * 38);
        long j34 = j4 - (((j22 * j29) + (j32 * j32)) * 38);
        long j35 = j7 - ((j32 * j29) * 38);
        long j36 = j9 - ((((long) i20) * j29) * 38);
        long j37 = j12 - j18;
        long j38 = j21 - j27;
        int i21 = i + i18;
        int i22 = i14 + i13;
        int i23 = i3 + i11;
        int i24 = i4 + i19;
        int i25 = i17 + i20;
        long j39 = j23 - j25;
        long j40 = j28 - j20;
        long j41 = j37;
        long j42 = (long) i21;
        long j43 = j42 * j42;
        long j44 = j30;
        long j45 = (long) (i22 * 2);
        long j46 = j42 * j45;
        long j47 = (long) (i23 * 2);
        long j48 = (long) i22;
        long j49 = (j42 * j47) + (j48 * j48);
        long j50 = j38;
        long j51 = (long) (i24 * 2);
        int i26 = i24;
        long j52 = (long) (i25 * 2);
        long j53 = (j45 * j52) + (j51 * j47);
        long j54 = (long) i26;
        long j55 = (j47 * j52) + (j54 * j54);
        long j56 = j54 * j52;
        long j57 = ((long) i25) * j52;
        long j58 = j50 + (((j45 * j47) + (j42 * j51)) - j36);
        int i27 = ((int) j58) & M26;
        long j59 = (j58 >> 26) + (((((((long) i23) * j47) + (j42 * j52)) + (j48 * j51)) - j16) - j44);
        int i28 = ((int) j59) & M25;
        long j60 = j33 + ((((j59 >> 25) + j53) - j41) * 38);
        iArr2[0] = ((int) j60) & M26;
        long j61 = (j60 >> 26) + j34 + ((j55 - j40) * 38);
        iArr2[1] = ((int) j61) & M26;
        long j62 = (j61 >> 26) + j35 + ((j56 - j39) * 38);
        iArr2[2] = ((int) j62) & M25;
        long j63 = (j62 >> 25) + j36 + ((j57 - j50) * 38);
        iArr2[3] = ((int) j63) & M26;
        long j64 = (j63 >> 26) + j16 + (38 * j44);
        iArr2[4] = ((int) j64) & M25;
        long j65 = (j64 >> 25) + j41 + (j43 - j33);
        iArr2[5] = ((int) j65) & M26;
        long j66 = (j65 >> 26) + j40 + (j46 - j34);
        iArr2[6] = ((int) j66) & M26;
        long j67 = (j66 >> 26) + j39 + (j49 - j35);
        iArr2[7] = ((int) j67) & M25;
        long j68 = (j67 >> 25) + ((long) i27);
        iArr2[8] = ((int) j68) & M26;
        iArr2[9] = i28 + ((int) (j68 >> 26));
    }

    public static boolean sqrtRatioVar(int[] iArr, int[] iArr2, int[] iArr3) {
        int[] create = create();
        int[] create2 = create();
        mul(iArr, iArr2, create);
        sqr(iArr2, create2);
        mul(create, create2, create);
        sqr(create2, create2);
        mul(create2, create, create2);
        int[] create3 = create();
        int[] create4 = create();
        powPm5d8(create2, create3, create4);
        mul(create4, create, create4);
        int[] create5 = create();
        sqr(create4, create5);
        mul(create5, iArr2, create5);
        sub(create5, iArr, create3);
        normalize(create3);
        if (isZeroVar(create3)) {
            copy(create4, 0, iArr3, 0);
            return true;
        }
        add(create5, iArr, create3);
        normalize(create3);
        if (!isZeroVar(create3)) {
            return false;
        }
        mul(create4, ROOT_NEG_ONE, iArr3);
        return true;
    }

    public static void sub(int[] iArr, int[] iArr2, int[] iArr3) {
        for (int i = 0; i < 10; i++) {
            iArr3[i] = iArr[i] - iArr2[i];
        }
    }

    public static void subOne(int[] iArr) {
        iArr[0] = iArr[0] - 1;
    }

    public static void zero(int[] iArr) {
        for (int i = 0; i < 10; i++) {
            iArr[i] = 0;
        }
    }
}
