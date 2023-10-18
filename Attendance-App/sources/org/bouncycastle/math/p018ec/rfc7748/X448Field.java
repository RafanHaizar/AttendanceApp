package org.bouncycastle.math.p018ec.rfc7748;

import kotlin.UByte;

/* renamed from: org.bouncycastle.math.ec.rfc7748.X448Field */
public abstract class X448Field {
    private static final int M28 = 268435455;
    public static final int SIZE = 16;
    private static final long U32 = 4294967295L;

    protected X448Field() {
    }

    public static void add(int[] iArr, int[] iArr2, int[] iArr3) {
        for (int i = 0; i < 16; i++) {
            iArr3[i] = iArr[i] + iArr2[i];
        }
    }

    public static void addOne(int[] iArr) {
        iArr[0] = iArr[0] + 1;
    }

    public static void addOne(int[] iArr, int i) {
        iArr[i] = iArr[i] + 1;
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
        int i11 = iArr[10];
        int i12 = iArr[11];
        int i13 = iArr[12];
        int i14 = iArr[13];
        int i15 = iArr[14];
        int i16 = iArr[15];
        int i17 = i3 + (i2 >>> 28);
        int i18 = i2 & M28;
        int i19 = i7 + (i6 >>> 28);
        int i20 = i6 & M28;
        int i21 = i11 + (i10 >>> 28);
        int i22 = i10 & M28;
        int i23 = i15 + (i14 >>> 28);
        int i24 = i14 & M28;
        int i25 = i4 + (i17 >>> 28);
        int i26 = i17 & M28;
        int i27 = i8 + (i19 >>> 28);
        int i28 = i19 & M28;
        int i29 = i12 + (i21 >>> 28);
        int i30 = i21 & M28;
        int i31 = i16 + (i23 >>> 28);
        int i32 = i23 & M28;
        int i33 = i31 >>> 28;
        int i34 = i31 & M28;
        int i35 = i + i33;
        int i36 = i5 + (i25 >>> 28);
        int i37 = i25 & M28;
        int i38 = i9 + i33 + (i27 >>> 28);
        int i39 = i27 & M28;
        int i40 = i13 + (i29 >>> 28);
        int i41 = i29 & M28;
        int i42 = i18 + (i35 >>> 28);
        int i43 = i35 & M28;
        int i44 = i20 + (i36 >>> 28);
        int i45 = i36 & M28;
        int i46 = i22 + (i38 >>> 28);
        int i47 = i38 & M28;
        int i48 = i40 & M28;
        iArr[0] = i43;
        iArr[1] = i42;
        iArr[2] = i26;
        iArr[3] = i37;
        iArr[4] = i45;
        iArr[5] = i44;
        iArr[6] = i28;
        iArr[7] = i39;
        iArr[8] = i47;
        iArr[9] = i46;
        iArr[10] = i30;
        iArr[11] = i41;
        iArr[12] = i48;
        iArr[13] = i24 + (i40 >>> 28);
        iArr[14] = i32;
        iArr[15] = i34;
    }

    public static void cmov(int i, int[] iArr, int i2, int[] iArr2, int i3) {
        for (int i4 = 0; i4 < 16; i4++) {
            int i5 = i3 + i4;
            int i6 = iArr2[i5];
            iArr2[i5] = i6 ^ ((iArr[i2 + i4] ^ i6) & i);
        }
    }

    public static void cnegate(int i, int[] iArr) {
        int[] create = create();
        sub(create, iArr, create);
        cmov(-i, create, 0, iArr, 0);
    }

    public static void copy(int[] iArr, int i, int[] iArr2, int i2) {
        for (int i3 = 0; i3 < 16; i3++) {
            iArr2[i2 + i3] = iArr[i + i3];
        }
    }

    public static int[] create() {
        return new int[16];
    }

    public static int[] createTable(int i) {
        return new int[(i * 16)];
    }

    public static void cswap(int i, int[] iArr, int[] iArr2) {
        int i2 = 0 - i;
        for (int i3 = 0; i3 < 16; i3++) {
            int i4 = iArr[i3];
            int i5 = iArr2[i3];
            int i6 = (i4 ^ i5) & i2;
            iArr[i3] = i4 ^ i6;
            iArr2[i3] = i5 ^ i6;
        }
    }

    public static void decode(byte[] bArr, int i, int[] iArr) {
        decode56(bArr, i, iArr, 0);
        decode56(bArr, i + 7, iArr, 2);
        decode56(bArr, i + 14, iArr, 4);
        decode56(bArr, i + 21, iArr, 6);
        decode56(bArr, i + 28, iArr, 8);
        decode56(bArr, i + 35, iArr, 10);
        decode56(bArr, i + 42, iArr, 12);
        decode56(bArr, i + 49, iArr, 14);
    }

    private static int decode24(byte[] bArr, int i) {
        int i2 = i + 1;
        return ((bArr[i2 + 1] & UByte.MAX_VALUE) << Tnaf.POW_2_WIDTH) | (bArr[i] & UByte.MAX_VALUE) | ((bArr[i2] & UByte.MAX_VALUE) << 8);
    }

    private static int decode32(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = i2 + 1;
        return (bArr[i3 + 1] << 24) | (bArr[i] & UByte.MAX_VALUE) | ((bArr[i2] & UByte.MAX_VALUE) << 8) | ((bArr[i3] & UByte.MAX_VALUE) << Tnaf.POW_2_WIDTH);
    }

    private static void decode56(byte[] bArr, int i, int[] iArr, int i2) {
        int decode32 = decode32(bArr, i);
        int decode24 = decode24(bArr, i + 4);
        iArr[i2] = M28 & decode32;
        iArr[i2 + 1] = (decode24 << 4) | (decode32 >>> 28);
    }

    public static void encode(int[] iArr, byte[] bArr, int i) {
        encode56(iArr, 0, bArr, i);
        encode56(iArr, 2, bArr, i + 7);
        encode56(iArr, 4, bArr, i + 14);
        encode56(iArr, 6, bArr, i + 21);
        encode56(iArr, 8, bArr, i + 28);
        encode56(iArr, 10, bArr, i + 35);
        encode56(iArr, 12, bArr, i + 42);
        encode56(iArr, 14, bArr, i + 49);
    }

    private static void encode24(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) i;
        int i3 = i2 + 1;
        bArr[i3] = (byte) (i >>> 8);
        bArr[i3 + 1] = (byte) (i >>> 16);
    }

    private static void encode32(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) i;
        int i3 = i2 + 1;
        bArr[i3] = (byte) (i >>> 8);
        int i4 = i3 + 1;
        bArr[i4] = (byte) (i >>> 16);
        bArr[i4 + 1] = (byte) (i >>> 24);
    }

    private static void encode56(int[] iArr, int i, byte[] bArr, int i2) {
        int i3 = iArr[i];
        int i4 = iArr[i + 1];
        encode32((i4 << 28) | i3, bArr, i2);
        encode24(i4 >>> 4, bArr, i2 + 4);
    }

    public static void inv(int[] iArr, int[] iArr2) {
        int[] create = create();
        powPm3d4(iArr, create);
        sqr(create, 2, create);
        mul(create, iArr, iArr2);
    }

    public static int isZero(int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 < 16; i2++) {
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
        int i12 = iArr[10];
        int i13 = iArr[11];
        int i14 = iArr[12];
        int i15 = iArr[13];
        int i16 = iArr[14];
        int i17 = i6;
        long j = (long) i3;
        long j2 = (long) i;
        long j3 = j * j2;
        int i18 = i2;
        int i19 = i14;
        long j4 = ((long) i7) * j2;
        int i20 = ((int) j3) & M28;
        long j5 = ((long) i11) * j2;
        int i21 = ((int) j4) & M28;
        long j6 = ((long) i15) * j2;
        int i22 = ((int) j5) & M28;
        int i23 = ((int) j6) & M28;
        long j7 = (j3 >>> 28) + (((long) i4) * j2);
        iArr2[2] = ((int) j7) & M28;
        long j8 = j7 >>> 28;
        long j9 = (j4 >>> 28) + (((long) i8) * j2);
        iArr2[6] = ((int) j9) & M28;
        long j10 = (j5 >>> 28) + (((long) i12) * j2);
        iArr2[10] = ((int) j10) & M28;
        long j11 = (j6 >>> 28) + (((long) i16) * j2);
        iArr2[14] = ((int) j11) & M28;
        long j12 = j8 + (((long) i5) * j2);
        iArr2[3] = ((int) j12) & M28;
        long j13 = (j9 >>> 28) + (((long) i9) * j2);
        iArr2[7] = ((int) j13) & M28;
        long j14 = (j10 >>> 28) + (((long) i13) * j2);
        iArr2[11] = ((int) j14) & M28;
        long j15 = (j11 >>> 28) + (((long) iArr[15]) * j2);
        iArr2[15] = ((int) j15) & M28;
        long j16 = j15 >>> 28;
        long j17 = (j12 >>> 28) + (((long) i17) * j2);
        iArr2[4] = ((int) j17) & M28;
        long j18 = (j13 >>> 28) + j16 + (((long) i10) * j2);
        iArr2[8] = ((int) j18) & M28;
        long j19 = (j14 >>> 28) + (((long) i19) * j2);
        iArr2[12] = ((int) j19) & M28;
        long j20 = j16 + (((long) i18) * j2);
        iArr2[0] = ((int) j20) & M28;
        iArr2[1] = i20 + ((int) (j20 >>> 28));
        iArr2[5] = i21 + ((int) (j17 >>> 28));
        iArr2[9] = i22 + ((int) (j18 >>> 28));
        iArr2[13] = i23 + ((int) (j19 >>> 28));
    }

    public static void mul(int[] iArr, int[] iArr2, int[] iArr3) {
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
        int i11 = iArr[10];
        int i12 = iArr[11];
        int i13 = iArr[12];
        int i14 = iArr[13];
        int i15 = iArr[14];
        int i16 = i8;
        int i17 = iArr[15];
        int i18 = iArr2[0];
        int i19 = iArr2[1];
        int i20 = iArr2[2];
        int i21 = iArr2[3];
        int i22 = iArr2[4];
        int i23 = iArr2[5];
        int i24 = iArr2[6];
        int i25 = iArr2[7];
        int i26 = iArr2[8];
        int i27 = iArr2[9];
        int i28 = iArr2[10];
        int i29 = iArr2[11];
        int i30 = iArr2[12];
        int i31 = iArr2[13];
        int i32 = iArr2[14];
        int i33 = iArr2[15];
        int i34 = i + i9;
        int i35 = i2 + i10;
        int i36 = i3 + i11;
        int i37 = i4 + i12;
        int i38 = i5 + i13;
        int i39 = i6 + i14;
        int i40 = i7 + i15;
        int i41 = i16 + i17;
        int i42 = i19 + i27;
        int i43 = i21 + i29;
        int i44 = i23 + i31;
        int i45 = i25 + i33;
        long j = (long) i;
        int i46 = i9;
        long j2 = (long) i18;
        long j3 = j * j2;
        long j4 = j;
        long j5 = (long) i16;
        long j6 = j2;
        long j7 = (long) i19;
        long j8 = j5 * j7;
        long j9 = j5;
        long j10 = (long) i7;
        long j11 = j7;
        long j12 = (long) i20;
        long j13 = j8 + (j10 * j12);
        long j14 = j10;
        long j15 = (long) i6;
        long j16 = j12;
        long j17 = (long) i21;
        long j18 = j13 + (j15 * j17);
        long j19 = j15;
        long j20 = (long) i5;
        long j21 = j17;
        long j22 = (long) i22;
        long j23 = j18 + (j20 * j22);
        long j24 = j20;
        long j25 = (long) i4;
        long j26 = j22;
        long j27 = (long) i23;
        long j28 = j23 + (j25 * j27);
        long j29 = j25;
        long j30 = (long) i3;
        long j31 = j27;
        long j32 = (long) i24;
        long j33 = j28 + (j30 * j32);
        long j34 = j30;
        long j35 = (long) i2;
        long j36 = j32;
        long j37 = (long) i25;
        long j38 = j33 + (j35 * j37);
        long j39 = j37;
        long j40 = (long) i46;
        long j41 = j35;
        long j42 = (long) i26;
        long j43 = j40 * j42;
        long j44 = j40;
        long j45 = (long) i17;
        long j46 = j42;
        long j47 = (long) i27;
        long j48 = j45 * j47;
        long j49 = (long) i15;
        long j50 = j45;
        long j51 = (long) i28;
        long j52 = j48 + (j49 * j51);
        long j53 = (long) i14;
        long j54 = j49;
        long j55 = (long) i29;
        long j56 = (long) i13;
        long j57 = j53;
        long j58 = (long) i30;
        long j59 = j52 + (j53 * j55) + (j56 * j58);
        long j60 = (long) i12;
        long j61 = j56;
        long j62 = (long) i31;
        long j63 = (long) i11;
        long j64 = j60;
        long j65 = (long) i32;
        long j66 = j59 + (j60 * j62) + (j63 * j65);
        long j67 = j63;
        long j68 = (long) i10;
        long j69 = j65;
        long j70 = (long) i33;
        long j71 = j66 + (j68 * j70);
        long j72 = j70;
        long j73 = (long) i34;
        long j74 = j62;
        long j75 = (long) (i18 + i26);
        long j76 = j73 * j75;
        int i47 = i41;
        long j77 = j73;
        long j78 = (long) i47;
        long j79 = j75;
        long j80 = (long) i42;
        long j81 = j78 * j80;
        long j82 = j78;
        long j83 = (long) i40;
        long j84 = j80;
        long j85 = (long) (i20 + i28);
        long j86 = j81 + (j83 * j85);
        int i48 = i39;
        long j87 = j83;
        long j88 = (long) i48;
        long j89 = j85;
        long j90 = (long) i43;
        long j91 = j86 + (j88 * j90);
        long j92 = j88;
        long j93 = (long) i38;
        long j94 = j90;
        long j95 = (long) (i22 + i30);
        long j96 = j91 + (j93 * j95);
        int i49 = i37;
        long j97 = j93;
        long j98 = (long) i49;
        long j99 = j95;
        long j100 = (long) i44;
        long j101 = j96 + (j98 * j100);
        long j102 = j98;
        long j103 = (long) i36;
        long j104 = j100;
        long j105 = (long) (i24 + i32);
        long j106 = j103;
        long j107 = (long) i35;
        int i50 = i45;
        long j108 = j105;
        long j109 = (long) i50;
        long j110 = j101 + (j103 * j105) + (j107 * j109);
        long j111 = j109;
        long j112 = ((j3 + j43) + j110) - j38;
        long j113 = (j71 + j76) - j3;
        long j114 = j112 >>> 28;
        long j115 = j113 + j110;
        int i51 = ((int) j112) & M28;
        long j116 = (j41 * j6) + (j4 * j11);
        long j117 = j69;
        long j118 = (j107 * j79) + (j77 * j84);
        long j119 = (j82 * j89) + (j87 * j94) + (j92 * j99) + (j97 * j104) + (j102 * j108) + (j106 * j111);
        long j120 = ((j116 + ((j68 * j46) + (j44 * j47))) + j119) - ((((((j9 * j16) + (j14 * j21)) + (j19 * j26)) + (j24 * j31)) + (j29 * j36)) + (j34 * j39));
        long j121 = j107;
        long j122 = j114 + j120;
        int i52 = ((int) j115) & M28;
        long j123 = (j115 >>> 28) + ((((((((j50 * j51) + (j54 * j55)) + (j57 * j58)) + (j61 * j74)) + (j64 * j117)) + (j67 * j72)) + j118) - j116) + j119;
        int i53 = ((int) j122) & M28;
        long j124 = (j34 * j6) + (j41 * j11) + (j4 * j16);
        long j125 = (j82 * j94) + (j87 * j99) + (j92 * j104) + (j97 * j108) + (j102 * j111);
        long j126 = (j122 >>> 28) + (((j124 + (((j67 * j46) + (j68 * j47)) + (j44 * j51))) + j125) - (((((j9 * j21) + (j14 * j26)) + (j19 * j31)) + (j24 * j36)) + (j29 * j39)));
        int i54 = ((int) j123) & M28;
        long j127 = (j123 >>> 28) + (((((((j50 * j55) + (j54 * j58)) + (j57 * j74)) + (j61 * j117)) + (j64 * j72)) + (((j106 * j79) + (j121 * j84)) + (j77 * j89))) - j124) + j125;
        int i55 = ((int) j126) & M28;
        long j128 = (j29 * j6) + (j34 * j11) + (j41 * j16) + (j4 * j21);
        long j129 = (j82 * j99) + (j87 * j104) + (j92 * j108) + (j97 * j111);
        long j130 = (j126 >>> 28) + (((j128 + ((((j64 * j46) + (j67 * j47)) + (j68 * j51)) + (j44 * j55))) + j129) - ((((j9 * j26) + (j14 * j31)) + (j19 * j36)) + (j24 * j39)));
        int i56 = ((int) j127) & M28;
        long j131 = (j127 >>> 28) + ((((((j50 * j58) + (j54 * j74)) + (j57 * j117)) + (j61 * j72)) + ((((j102 * j79) + (j106 * j84)) + (j121 * j89)) + (j77 * j94))) - j128) + j129;
        int i57 = ((int) j130) & M28;
        long j132 = (j24 * j6) + (j29 * j11) + (j34 * j16) + (j41 * j21) + (j4 * j26);
        long j133 = (j82 * j104) + (j87 * j108) + (j92 * j111);
        long j134 = (j130 >>> 28) + (((j132 + (((((j61 * j46) + (j64 * j47)) + (j67 * j51)) + (j68 * j55)) + (j44 * j58))) + j133) - (((j9 * j31) + (j14 * j36)) + (j19 * j39)));
        int i58 = ((int) j131) & M28;
        long j135 = (j131 >>> 28) + (((((j50 * j74) + (j54 * j117)) + (j57 * j72)) + (((((j97 * j79) + (j102 * j84)) + (j106 * j89)) + (j121 * j94)) + (j77 * j99))) - j132) + j133;
        int i59 = ((int) j134) & M28;
        long j136 = (j19 * j6) + (j24 * j11) + (j29 * j16) + (j34 * j21) + (j41 * j26) + (j4 * j31);
        long j137 = (j82 * j108) + (j87 * j111);
        long j138 = (j134 >>> 28) + (((j136 + ((((((j57 * j46) + (j61 * j47)) + (j64 * j51)) + (j67 * j55)) + (j68 * j58)) + (j44 * j74))) + j137) - ((j9 * j36) + (j14 * j39)));
        int i60 = ((int) j135) & M28;
        long j139 = (j135 >>> 28) + ((((j50 * j117) + (j54 * j72)) + ((((((j92 * j79) + (j97 * j84)) + (j102 * j89)) + (j106 * j94)) + (j121 * j99)) + (j77 * j104))) - j136) + j137;
        int i61 = ((int) j138) & M28;
        long j140 = (j14 * j6) + (j19 * j11) + (j24 * j16) + (j29 * j21) + (j34 * j26) + (j41 * j31) + (j4 * j36);
        long j141 = j82 * j111;
        long j142 = (j138 >>> 28) + (((j140 + (((((((j54 * j46) + (j57 * j47)) + (j61 * j51)) + (j64 * j55)) + (j67 * j58)) + (j68 * j74)) + (j44 * j117))) + j141) - (j9 * j39));
        int i62 = ((int) j139) & M28;
        long j143 = (j139 >>> 28) + (((j50 * j72) + (((((((j87 * j79) + (j92 * j84)) + (j97 * j89)) + (j102 * j94)) + (j106 * j99)) + (j121 * j104)) + (j77 * j108))) - j140) + j141;
        int i63 = ((int) j142) & M28;
        int i64 = ((int) j143) & M28;
        long j144 = (j6 * j9) + (j11 * j14) + (j19 * j16) + (j24 * j21) + (j29 * j26) + (j34 * j31) + (j41 * j36) + (j4 * j39);
        long j145 = (j142 >>> 28) + j144 + (j50 * j46) + (j47 * j54) + (j57 * j51) + (j61 * j55) + (j64 * j58) + (j67 * j74) + (j68 * j117) + (j44 * j72);
        int i65 = ((int) j145) & M28;
        long j146 = (j143 >>> 28) + (((((((((j82 * j79) + (j87 * j84)) + (j92 * j89)) + (j97 * j94)) + (j102 * j99)) + (j106 * j104)) + (j121 * j108)) + (j77 * j111)) - j144);
        int i66 = ((int) j146) & M28;
        long j147 = j146 >>> 28;
        long j148 = (j145 >>> 28) + j147 + ((long) i52);
        int i67 = ((int) j148) & M28;
        long j149 = j147 + ((long) i51);
        iArr3[0] = ((int) j149) & M28;
        iArr3[1] = i53 + ((int) (j149 >>> 28));
        iArr3[2] = i55;
        iArr3[3] = i57;
        iArr3[4] = i59;
        iArr3[5] = i61;
        iArr3[6] = i63;
        iArr3[7] = i65;
        iArr3[8] = i67;
        iArr3[9] = i54 + ((int) (j148 >>> 28));
        iArr3[10] = i56;
        iArr3[11] = i58;
        iArr3[12] = i60;
        iArr3[13] = i62;
        iArr3[14] = i64;
        iArr3[15] = i66;
    }

    public static void negate(int[] iArr, int[] iArr2) {
        sub(create(), iArr, iArr2);
    }

    public static void normalize(int[] iArr) {
        reduce(iArr, 1);
        reduce(iArr, -1);
    }

    public static void one(int[] iArr) {
        iArr[0] = 1;
        for (int i = 1; i < 16; i++) {
            iArr[i] = 0;
        }
    }

    private static void powPm3d4(int[] iArr, int[] iArr2) {
        int[] create = create();
        sqr(iArr, create);
        mul(iArr, create, create);
        int[] create2 = create();
        sqr(create, create2);
        mul(iArr, create2, create2);
        int[] create3 = create();
        sqr(create2, 3, create3);
        mul(create2, create3, create3);
        int[] create4 = create();
        sqr(create3, 3, create4);
        mul(create2, create4, create4);
        int[] create5 = create();
        sqr(create4, 9, create5);
        mul(create4, create5, create5);
        int[] create6 = create();
        sqr(create5, create6);
        mul(iArr, create6, create6);
        int[] create7 = create();
        sqr(create6, 18, create7);
        mul(create5, create7, create7);
        int[] create8 = create();
        sqr(create7, 37, create8);
        mul(create7, create8, create8);
        int[] create9 = create();
        sqr(create8, 37, create9);
        mul(create7, create9, create9);
        int[] create10 = create();
        sqr(create9, 111, create10);
        mul(create9, create10, create10);
        int[] create11 = create();
        sqr(create10, create11);
        mul(iArr, create11, create11);
        int[] create12 = create();
        sqr(create11, 223, create12);
        mul(create12, create10, iArr2);
    }

    private static void reduce(int[] iArr, int i) {
        int i2 = iArr[15];
        int i3 = i2 & M28;
        int i4 = (i2 >> 28) + i;
        iArr[8] = iArr[8] + i4;
        for (int i5 = 0; i5 < 15; i5++) {
            int i6 = i4 + iArr[i5];
            iArr[i5] = i6 & M28;
            i4 = i6 >> 28;
        }
        iArr[15] = i3 + i4;
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
        int i9 = iArr[8];
        int i10 = iArr[9];
        int i11 = iArr[10];
        int i12 = iArr[11];
        int i13 = iArr[12];
        int i14 = iArr[13];
        int i15 = iArr[14];
        int i16 = iArr[15];
        int i17 = i * 2;
        int i18 = i2 * 2;
        int i19 = i3 * 2;
        int i20 = i4 * 2;
        int i21 = i5 * 2;
        int i22 = i6 * 2;
        int i23 = i7 * 2;
        int i24 = i9 * 2;
        int i25 = i10 * 2;
        int i26 = i11 * 2;
        int i27 = i12 * 2;
        int i28 = i13 * 2;
        int i29 = i14 * 2;
        int i30 = i15 * 2;
        int i31 = i + i9;
        int i32 = i9;
        int i33 = i2 + i10;
        int i34 = i10;
        int i35 = i3 + i11;
        int i36 = i11;
        int i37 = i4 + i12;
        int i38 = i12;
        int i39 = i5 + i13;
        int i40 = i4;
        int i41 = i6 + i14;
        int i42 = i3;
        int i43 = i7 + i15;
        int i44 = i2;
        int i45 = i31 * 2;
        int i46 = i33 * 2;
        int i47 = i33;
        int i48 = i35 * 2;
        int i49 = i35;
        int i50 = i37 * 2;
        int i51 = i37;
        int i52 = i39 * 2;
        int i53 = i41 * 2;
        int i54 = i41;
        int i55 = i39;
        long j = (long) i;
        long j2 = j * j;
        long j3 = (long) i8;
        int i56 = i18;
        int i57 = i48;
        long j4 = (long) i56;
        long j5 = j3 * j4;
        long j6 = j4;
        long j7 = (long) i7;
        long j8 = j3;
        long j9 = (long) i19;
        long j10 = (long) i6;
        long j11 = j7;
        long j12 = (long) i20;
        long j13 = j10;
        long j14 = (long) i5;
        long j15 = j5 + (j7 * j9) + (j10 * j12) + (j14 * j14);
        long j16 = j14;
        long j17 = (long) i32;
        long j18 = j12;
        long j19 = (long) i16;
        long j20 = j9;
        long j21 = (long) i25;
        long j22 = j19 * j21;
        int i58 = i43 * 2;
        long j23 = (long) i15;
        long j24 = j21;
        long j25 = (long) i26;
        long j26 = j22 + (j23 * j25);
        long j27 = j23;
        long j28 = (long) i14;
        long j29 = j25;
        long j30 = (long) i27;
        long j31 = j26 + (j28 * j30);
        long j32 = j28;
        long j33 = (long) i13;
        long j34 = j33;
        long j35 = (long) i31;
        long j36 = j30;
        long j37 = (long) (i8 + i16);
        long j38 = ((long) i46) & 4294967295L;
        long j39 = j37 * j38;
        long j40 = (long) i43;
        long j41 = j38;
        long j42 = ((long) i57) & 4294967295L;
        long j43 = j39 + (j40 * j42);
        long j44 = j40;
        long j45 = (long) i54;
        int i59 = i58;
        long j46 = ((long) i50) & 4294967295L;
        int i60 = i55;
        long j47 = j45;
        long j48 = (long) i60;
        long j49 = j43 + (j45 * j46) + (j48 * j48);
        long j50 = ((j2 + (j17 * j17)) + j49) - j15;
        long j51 = (((j31 + (j33 * j33)) + (j35 * j35)) - j2) + j49;
        int i61 = ((int) j50) & M28;
        int i62 = ((int) j51) & M28;
        int i63 = i44;
        long j52 = j48;
        long j53 = (long) i63;
        long j54 = j51 >>> 28;
        long j55 = (long) i17;
        long j56 = j53 * j55;
        long j57 = j53;
        long j58 = (long) i21;
        long j59 = (j8 * j20) + (j11 * j18) + (j13 * j58);
        long j60 = j58;
        long j61 = (long) i34;
        long j62 = j55;
        long j63 = (long) i24;
        long j64 = j61 * j63;
        long j65 = (j19 * j29) + (j27 * j36);
        long j66 = j19;
        long j67 = (long) i28;
        long j68 = j65 + (j32 * j67);
        long j69 = j67;
        long j70 = (long) i47;
        int i64 = i45;
        long j71 = j61;
        long j72 = ((long) i64) & 4294967295L;
        long j73 = (j37 * j42) + (j44 * j46);
        long j74 = j42;
        long j75 = ((long) i52) & 4294967295L;
        long j76 = j73 + (j47 * j75);
        long j77 = (j50 >>> 28) + (((j56 + j64) + j76) - j59);
        int i65 = i59;
        long j78 = j54 + ((j68 + (j70 * j72)) - j56) + j76;
        long j79 = j78 >>> 28;
        int i66 = ((int) j77) & M28;
        long j80 = (long) i42;
        long j81 = (j80 * j62) + (j57 * j57);
        long j82 = j80;
        long j83 = (long) i36;
        long j84 = (j83 * j63) + (j71 * j71);
        long j85 = j83;
        long j86 = (long) i49;
        long j87 = (j86 * j72) + (j70 * j70);
        long j88 = (j37 * j46) + (j44 * j75) + (j47 * j47);
        long j89 = ((j81 + j84) + j88) - (((j8 * j18) + (j11 * j60)) + (j13 * j13));
        long j90 = j46;
        int i67 = ((int) j78) & M28;
        long j91 = (j77 >>> 28) + j89;
        long j92 = j79 + (((((j66 * j36) + (j27 * j69)) + (j32 * j32)) + j87) - j81) + j88;
        int i68 = ((int) j91) & M28;
        int i69 = ((int) j92) & M28;
        int i70 = i40;
        int i71 = i67;
        long j93 = (long) i70;
        long j94 = (j93 * j62) + (j82 * j6);
        long j95 = j93;
        long j96 = (long) i22;
        long j97 = (j8 * j60) + (j11 * j96);
        long j98 = j96;
        long j99 = (long) i38;
        long j100 = (j99 * j63) + (j85 * j24);
        long j101 = j66 * j69;
        long j102 = j99;
        long j103 = (long) i29;
        long j104 = j101 + (j27 * j103);
        long j105 = j103;
        long j106 = (long) i51;
        long j107 = j75 * j37;
        long j108 = j37;
        long j109 = ((long) i53) & 4294967295L;
        long j110 = j107 + (j44 * j109);
        long j111 = (j91 >>> 28) + (((j94 + j100) + j110) - j97);
        long j112 = j109;
        int i72 = ((int) j111) & M28;
        long j113 = (j92 >>> 28) + ((j104 + ((j106 * j72) + (j86 * j41))) - j94) + j110;
        long j114 = (j16 * j62) + (j95 * j6) + (j82 * j82);
        long j115 = (j52 * j72) + (j106 * j41) + (j86 * j86);
        long j116 = (j108 * j112) + (j44 * j44);
        long j117 = (j111 >>> 28) + (((j114 + (((j34 * j63) + (j102 * j24)) + (j85 * j85))) + j116) - ((j8 * j98) + (j11 * j11)));
        int i73 = ((int) j113) & M28;
        int i74 = ((int) j117) & M28;
        long j118 = (j113 >>> 28) + ((((j66 * j105) + (j27 * j27)) + j115) - j114) + j116;
        long j119 = (j13 * j62) + (j16 * j6) + (j95 * j20);
        int i75 = i23;
        int i76 = ((int) j118) & M28;
        int i77 = i74;
        int i78 = i30;
        int i79 = i72;
        long j120 = (j47 * j72) + (j52 * j41) + (j106 * j74);
        long j121 = j106;
        long j122 = (((long) i65) & 4294967295L) * j108;
        long j123 = (j117 >>> 28) + (((j119 + (((j32 * j63) + (j34 * j24)) + (j102 * j29))) + j122) - (((long) i75) * j8));
        int i80 = ((int) j123) & M28;
        long j124 = (j118 >>> 28) + (((((long) i78) * j66) + j120) - j119) + j122;
        int i81 = ((int) j124) & M28;
        long j125 = (j11 * j62) + (j13 * j6) + (j16 * j20) + (j95 * j95);
        long j126 = (j44 * j72) + (j47 * j41) + (j52 * j74) + (j121 * j121);
        long j127 = j108 * j108;
        long j128 = (j123 >>> 28) + (((j125 + ((((j27 * j63) + (j32 * j24)) + (j34 * j29)) + (j102 * j102))) + j127) - (j8 * j8));
        int i82 = ((int) j128) & M28;
        long j129 = (j124 >>> 28) + (((j66 * j66) + j126) - j125) + j127;
        int i83 = ((int) j129) & M28;
        long j130 = (j8 * j62) + (j11 * j6) + (j13 * j20) + (j16 * j18);
        long j131 = (j128 >>> 28) + (j63 * j66) + (j27 * j24) + (j32 * j29) + (j34 * j36) + j130;
        int i84 = ((int) j131) & M28;
        long j132 = (j129 >>> 28) + (((((j72 * j108) + (j44 * j41)) + (j47 * j74)) + (j52 * j90)) - j130);
        int i85 = ((int) j132) & M28;
        long j133 = j132 >>> 28;
        long j134 = (j131 >>> 28) + j133 + ((long) i62);
        int i86 = ((int) j134) & M28;
        long j135 = j133 + ((long) i61);
        iArr2[0] = ((int) j135) & M28;
        iArr2[1] = i66 + ((int) (j135 >>> 28));
        iArr2[2] = i68;
        iArr2[3] = i79;
        iArr2[4] = i77;
        iArr2[5] = i80;
        iArr2[6] = i82;
        iArr2[7] = i84;
        iArr2[8] = i86;
        iArr2[9] = i71 + ((int) (j134 >>> 28));
        iArr2[10] = i69;
        iArr2[11] = i73;
        iArr2[12] = i76;
        iArr2[13] = i81;
        iArr2[14] = i83;
        iArr2[15] = i85;
    }

    public static boolean sqrtRatioVar(int[] iArr, int[] iArr2, int[] iArr3) {
        int[] create = create();
        int[] create2 = create();
        sqr(iArr, create);
        mul(create, iArr2, create);
        sqr(create, create2);
        mul(create, iArr, create);
        mul(create2, iArr, create2);
        mul(create2, iArr2, create2);
        int[] create3 = create();
        powPm3d4(create2, create3);
        mul(create3, create, create3);
        int[] create4 = create();
        sqr(create3, create4);
        mul(create4, iArr2, create4);
        sub(iArr, create4, create4);
        normalize(create4);
        if (!isZeroVar(create4)) {
            return false;
        }
        copy(create3, 0, iArr3, 0);
        return true;
    }

    public static void sub(int[] iArr, int[] iArr2, int[] iArr3) {
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
        int i11 = iArr[10];
        int i12 = iArr[11];
        int i13 = iArr[12];
        int i14 = iArr[13];
        int i15 = iArr[14];
        int i16 = iArr[15];
        int i17 = iArr2[0];
        int i18 = iArr2[1];
        int i19 = iArr2[2];
        int i20 = iArr2[3];
        int i21 = iArr2[4];
        int i22 = iArr2[5];
        int i23 = iArr2[6];
        int i24 = iArr2[7];
        int i25 = iArr2[8];
        int i26 = iArr2[9];
        int i27 = iArr2[10];
        int i28 = iArr2[11];
        int i29 = iArr2[12];
        int i30 = iArr2[13];
        int i31 = iArr2[14];
        int i32 = (i2 + 536870910) - i18;
        int i33 = (i6 + 536870910) - i22;
        int i34 = (i10 + 536870910) - i26;
        int i35 = (i14 + 536870910) - i30;
        int i36 = ((i3 + 536870910) - i19) + (i32 >>> 28);
        int i37 = i32 & M28;
        int i38 = ((i7 + 536870910) - i23) + (i33 >>> 28);
        int i39 = i33 & M28;
        int i40 = ((i11 + 536870910) - i27) + (i34 >>> 28);
        int i41 = i34 & M28;
        int i42 = ((i15 + 536870910) - i31) + (i35 >>> 28);
        int i43 = i35 & M28;
        int i44 = ((i4 + 536870910) - i20) + (i36 >>> 28);
        int i45 = i36 & M28;
        int i46 = ((i8 + 536870910) - i24) + (i38 >>> 28);
        int i47 = i38 & M28;
        int i48 = ((i12 + 536870910) - i28) + (i40 >>> 28);
        int i49 = i40 & M28;
        int i50 = ((i16 + 536870910) - iArr2[15]) + (i42 >>> 28);
        int i51 = i42 & M28;
        int i52 = i50 >>> 28;
        int i53 = i50 & M28;
        int i54 = ((i + 536870910) - i17) + i52;
        int i55 = ((i5 + 536870910) - i21) + (i44 >>> 28);
        int i56 = i44 & M28;
        int i57 = ((i9 + 536870908) - i25) + i52 + (i46 >>> 28);
        int i58 = i46 & M28;
        int i59 = ((i13 + 536870910) - i29) + (i48 >>> 28);
        int i60 = i48 & M28;
        int i61 = i37 + (i54 >>> 28);
        int i62 = i54 & M28;
        int i63 = i39 + (i55 >>> 28);
        int i64 = i55 & M28;
        int i65 = i41 + (i57 >>> 28);
        int i66 = i57 & M28;
        int i67 = i59 & M28;
        iArr3[0] = i62;
        iArr3[1] = i61;
        iArr3[2] = i45;
        iArr3[3] = i56;
        iArr3[4] = i64;
        iArr3[5] = i63;
        iArr3[6] = i47;
        iArr3[7] = i58;
        iArr3[8] = i66;
        iArr3[9] = i65;
        iArr3[10] = i49;
        iArr3[11] = i60;
        iArr3[12] = i67;
        iArr3[13] = i43 + (i59 >>> 28);
        iArr3[14] = i51;
        iArr3[15] = i53;
    }

    public static void subOne(int[] iArr) {
        int[] create = create();
        create[0] = 1;
        sub(iArr, create, iArr);
    }

    public static void zero(int[] iArr) {
        for (int i = 0; i < 16; i++) {
            iArr[i] = 0;
        }
    }
}
