package org.bouncycastle.pqc.crypto.qteslarnd1;

import org.bouncycastle.asn1.cmc.BodyPartID;
import org.bouncycastle.util.Arrays;

class Polynomial {
    public static final int HASH = 32;
    public static final int MESSAGE = 64;
    public static final int PRIVATE_KEY_I = 1344;
    public static final int PRIVATE_KEY_III_P = 12352;
    public static final int PRIVATE_KEY_III_SIZE = 2112;
    public static final int PRIVATE_KEY_III_SPEED = 2368;
    public static final int PRIVATE_KEY_I_P = 5184;
    public static final int PUBLIC_KEY_I = 1504;
    public static final int PUBLIC_KEY_III_P = 39712;
    public static final int PUBLIC_KEY_III_SIZE = 2976;
    public static final int PUBLIC_KEY_III_SPEED = 3104;
    public static final int PUBLIC_KEY_I_P = 14880;
    public static final int RANDOM = 32;
    public static final int SEED = 32;
    public static final int SIGNATURE_I = 1376;
    public static final int SIGNATURE_III_P = 6176;
    public static final int SIGNATURE_III_SIZE = 2720;
    public static final int SIGNATURE_III_SPEED = 2848;
    public static final int SIGNATURE_I_P = 2848;

    Polynomial() {
    }

    public static int barrett(int i, int i2, int i3, int i4) {
        return i - (((int) ((((long) i) * ((long) i3)) >> i4)) * i2);
    }

    public static long barrett(long j, int i, int i2, int i3) {
        return j - (((((long) i2) * j) >> i3) * ((long) i));
    }

    private static void componentWisePolynomialMultiplication(int[] iArr, int[] iArr2, int[] iArr3, int i, int i2, long j) {
        for (int i3 = 0; i3 < i; i3++) {
            iArr[i3] = montgomery(((long) iArr2[i3]) * ((long) iArr3[i3]), i2, j);
        }
    }

    private static void componentWisePolynomialMultiplication(long[] jArr, int i, long[] jArr2, int i2, long[] jArr3, int i3, int i4, int i5, long j) {
        for (int i6 = 0; i6 < i4; i6++) {
            jArr[i + i6] = montgomeryP(jArr2[i2 + i6] * jArr3[i3 + i6], i5, j);
        }
    }

    private static void inverseNumberTheoreticTransform(int[] iArr, int[] iArr2, int i, int i2, long j, int i3, int i4, int i5) {
        int i6 = i;
        int i7 = i2;
        long j2 = j;
        int i8 = 1;
        int i9 = 0;
        while (i8 < i6) {
            int i10 = 0;
            while (i10 < i6) {
                int i11 = i9 + 1;
                long j3 = (long) iArr2[i9];
                int i12 = i10;
                while (i12 < i10 + i8) {
                    int i13 = iArr[i12];
                    if (i8 == 16) {
                        iArr[i12] = barrett(iArr[i12 + i8] + i13, i7, i4, i5);
                    } else {
                        int i14 = i4;
                        int i15 = i5;
                        iArr[i12] = iArr[i12 + i8] + i13;
                    }
                    int i16 = i12 + i8;
                    iArr[i16] = montgomery(((long) (i13 - iArr[i16])) * j3, i7, j2);
                    i12++;
                    i8 = i8;
                }
                int i17 = i4;
                int i18 = i5;
                i10 = i12 + i8;
                i9 = i11;
            }
            int i19 = i4;
            int i20 = i5;
            i8 *= 2;
        }
        for (int i21 = 0; i21 < i6 / 2; i21++) {
            iArr[i21] = montgomery(((long) i3) * ((long) iArr[i21]), i7, j2);
        }
    }

    private static void inverseNumberTheoreticTransformI(int[] iArr, int[] iArr2) {
        int i = 0;
        for (int i2 = 1; i2 < 512; i2 *= 2) {
            int i3 = 0;
            while (i3 < 512) {
                int i4 = i + 1;
                long j = (long) iArr2[i];
                int i5 = i3;
                while (i5 < i3 + i2) {
                    int i6 = iArr[i5];
                    int i7 = i5 + i2;
                    iArr[i5] = iArr[i7] + i6;
                    iArr[i7] = montgomery(((long) (i6 - iArr[i7])) * j, Parameter.Q_I, Parameter.Q_INVERSE_I);
                    i5++;
                }
                i3 = i5 + i2;
                i = i4;
            }
        }
        for (int i8 = 0; i8 < 256; i8++) {
            iArr[i8] = montgomery(((long) iArr[i8]) * 1081347, Parameter.Q_I, Parameter.Q_INVERSE_I);
        }
    }

    private static void inverseNumberTheoreticTransformIIIP(long[] jArr, int i, long[] jArr2, int i2) {
        int i3 = 0;
        for (int i4 = 1; i4 < 2048; i4 *= 2) {
            int i5 = 0;
            while (i5 < 2048) {
                int i6 = i3 + 1;
                long j = jArr2[i2 + i3];
                int i7 = i5;
                while (i7 < i5 + i4) {
                    int i8 = i + i7;
                    long j2 = jArr[i8];
                    int i9 = i8 + i4;
                    jArr[i8] = barrett(jArr[i9] + j2, (int) Parameter.Q_III_P, 15, 34);
                    jArr[i9] = barrett(montgomeryP((j2 + (2259451906L - jArr[i9])) * j, Parameter.Q_III_P, Parameter.Q_INVERSE_III_P), (int) Parameter.Q_III_P, 15, 34);
                    i7++;
                }
                i5 = i7 + i4;
                i3 = i6;
            }
        }
    }

    private static void inverseNumberTheoreticTransformIP(long[] jArr, int i, long[] jArr2, int i2) {
        int i3 = 1;
        int i4 = 0;
        while (true) {
            if (i3 < 1024) {
                int i5 = 0;
                while (i5 < 1024) {
                    int i6 = i4 + 1;
                    long j = jArr2[i2 + i4];
                    int i7 = i5;
                    while (i7 < i5 + i3) {
                        int i8 = i + i7;
                        long j2 = jArr[i8];
                        int i9 = i8 + i3;
                        jArr[i8] = j2 + jArr[i9];
                        jArr[i9] = montgomeryP(j * (j2 + (971956226 - jArr[i9])), Parameter.Q_I_P, Parameter.Q_INVERSE_I_P);
                        i7++;
                    }
                    i5 = i7 + i3;
                    i4 = i6;
                }
                int i10 = i3 * 2;
                int i11 = 0;
                for (int i12 = 1024; i11 < i12; i12 = 1024) {
                    int i13 = i4 + 1;
                    long j3 = jArr2[i2 + i4];
                    int i14 = i11;
                    while (i14 < i11 + i10) {
                        int i15 = i + i14;
                        long j4 = jArr[i15];
                        int i16 = i15 + i10;
                        jArr[i15] = barrett(j4 + jArr[i16], (int) Parameter.Q_I_P, 1, 29);
                        jArr[i16] = montgomeryP(j3 * (j4 + (971956226 - jArr[i16])), Parameter.Q_I_P, Parameter.Q_INVERSE_I_P);
                        i14++;
                        i11 = i11;
                    }
                    i11 = i14 + i10;
                    i4 = i13;
                }
                i3 = i10 * 2;
            } else {
                return;
            }
        }
    }

    private static int montgomery(long j, int i, long j2) {
        return (int) ((j + (((j2 * j) & BodyPartID.bodyIdMax) * ((long) i))) >> 32);
    }

    private static long montgomeryP(long j, int i, long j2) {
        return (j + (((j2 * j) & BodyPartID.bodyIdMax) * ((long) i))) >> 32;
    }

    private static void numberTheoreticTransform(int[] iArr, int[] iArr2, int i, int i2, long j) {
        int i3 = 0;
        for (int i4 = i >> 1; i4 > 0; i4 >>= 1) {
            int i5 = 0;
            while (i5 < i) {
                int i6 = i3 + 1;
                long j2 = (long) iArr2[i3];
                int i7 = i5;
                while (i7 < i5 + i4) {
                    int i8 = i7 + i4;
                    int montgomery = montgomery(((long) iArr[i8]) * j2, i2, j);
                    iArr[i8] = iArr[i7] - montgomery;
                    iArr[i7] = iArr[i7] + montgomery;
                    i7++;
                }
                i5 = i7 + i4;
                i3 = i6;
            }
        }
    }

    private static void numberTheoreticTransformIIIP(long[] jArr, long[] jArr2) {
        int i = 0;
        for (int i2 = 1024; i2 > 0; i2 >>= 1) {
            int i3 = 0;
            while (i3 < 2048) {
                int i4 = i + 1;
                int i5 = (int) jArr2[i];
                int i6 = i3;
                while (i6 < i3 + i2) {
                    int i7 = i6 + i2;
                    long barrett = barrett(montgomeryP(((long) i5) * jArr[i7], Parameter.Q_III_P, Parameter.Q_INVERSE_III_P), (int) Parameter.Q_III_P, 15, 34);
                    jArr[i7] = barrett(jArr[i6] + (2259451906L - barrett), (int) Parameter.Q_III_P, 15, 34);
                    jArr[i6] = barrett(jArr[i6] + barrett, (int) Parameter.Q_III_P, 15, 34);
                    i6++;
                }
                i3 = i6 + i2;
                i = i4;
            }
        }
    }

    private static void numberTheoreticTransformIP(long[] jArr, long[] jArr2) {
        int i = 0;
        for (int i2 = 512; i2 > 0; i2 >>= 1) {
            int i3 = 0;
            while (i3 < 1024) {
                int i4 = i + 1;
                long j = jArr2[i];
                int i5 = i3;
                while (i5 < i3 + i2) {
                    int i6 = i5 + i2;
                    long montgomeryP = montgomeryP(jArr[i6] * j, Parameter.Q_I_P, Parameter.Q_INVERSE_I_P);
                    jArr[i6] = jArr[i5] + (485978113 - montgomeryP);
                    jArr[i5] = jArr[i5] + montgomeryP;
                    i5++;
                }
                i3 = i5 + i2;
                i = i4;
            }
        }
    }

    public static void polynomialAddition(int[] iArr, int[] iArr2, int[] iArr3, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            iArr[i2] = iArr2[i2] + iArr3[i2];
        }
    }

    public static void polynomialAddition(long[] jArr, int i, long[] jArr2, int i2, long[] jArr3, int i3, int i4) {
        for (int i5 = 0; i5 < i4; i5++) {
            jArr[i + i5] = jArr2[i2 + i5] + jArr3[i3 + i5];
        }
    }

    public static void polynomialAdditionCorrection(int[] iArr, int[] iArr2, int[] iArr3, int i, int i2) {
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = iArr2[i3] + iArr3[i3];
            iArr[i3] = i4;
            int i5 = i4 + ((i4 >> 31) & i2);
            iArr[i3] = i5;
            int i6 = i5 - i2;
            iArr[i3] = i6;
            iArr[i3] = i6 + ((i6 >> 31) & i2);
        }
    }

    public static void polynomialMultiplication(int[] iArr, int[] iArr2, int[] iArr3, int i, int i2, long j, int[] iArr4) {
        int i3 = i;
        int i4 = i2;
        int[] iArr5 = new int[i3];
        for (int i5 = 0; i5 < i3; i5++) {
            iArr5[i5] = iArr3[i5];
        }
        numberTheoreticTransform(iArr5, iArr4, i, i2, j);
        componentWisePolynomialMultiplication(iArr, iArr2, iArr5, i, i2, j);
        if (i4 == 4205569) {
            inverseNumberTheoreticTransformI(iArr, PolynomialHeuristic.ZETA_INVERSE_I);
        } else {
            int[] iArr6 = iArr;
        }
        if (i4 == 4206593) {
            inverseNumberTheoreticTransform(iArr, PolynomialHeuristic.ZETA_INVERSE_III_SIZE, 1024, Parameter.Q_III_SIZE, Parameter.Q_INVERSE_III_SIZE, Parameter.R_III_SIZE, 1021, 32);
        }
        if (i4 == 8404993) {
            inverseNumberTheoreticTransform(iArr, PolynomialHeuristic.ZETA_INVERSE_III_SPEED, 1024, Parameter.Q_III_SPEED, Parameter.Q_INVERSE_III_SPEED, Parameter.R_III_SPEED, 511, 32);
        }
    }

    public static void polynomialMultiplication(long[] jArr, int i, long[] jArr2, int i2, long[] jArr3, int i3, int i4, int i5, long j) {
        componentWisePolynomialMultiplication(jArr, i, jArr2, i2, jArr3, i3, i4, i5, j);
        if (i5 == 485978113) {
            inverseNumberTheoreticTransformIP(jArr, i, PolynomialProvablySecure.ZETA_INVERSE_I_P, 0);
        }
        if (i5 == 1129725953) {
            inverseNumberTheoreticTransformIIIP(jArr, i, PolynomialProvablySecure.ZETA_INVERSE_III_P, 0);
        }
    }

    public static void polynomialNumberTheoreticTransform(long[] jArr, long[] jArr2, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            jArr[i2] = jArr2[i2];
        }
        if (i == 1024) {
            numberTheoreticTransformIP(jArr, PolynomialProvablySecure.ZETA_I_P);
        }
        if (i == 2048) {
            numberTheoreticTransformIIIP(jArr, PolynomialProvablySecure.ZETA_III_P);
        }
    }

    public static void polynomialSubtraction(long[] jArr, int i, long[] jArr2, int i2, long[] jArr3, int i3, int i4, int i5, int i6, int i7) {
        for (int i8 = 0; i8 < i4; i8++) {
            jArr[i + i8] = barrett(jArr2[i2 + i8] - jArr3[i3 + i8], i5, i6, i7);
        }
    }

    public static void polynomialSubtractionCorrection(int[] iArr, int[] iArr2, int[] iArr3, int i, int i2) {
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = iArr2[i3] - iArr3[i3];
            iArr[i3] = i4;
            iArr[i3] = i4 + ((i4 >> 31) & i2);
        }
    }

    public static void polynomialSubtractionMontgomery(int[] iArr, int[] iArr2, int[] iArr3, int i, int i2, long j, int i3) {
        for (int i4 = 0; i4 < i; i4++) {
            iArr[i4] = montgomery(((long) i3) * ((long) (iArr2[i4] - iArr3[i4])), i2, j);
        }
    }

    public static void polynomialUniform(int[] iArr, byte[] bArr, int i, int i2, int i3, long j, int i4, int i5, int i6) {
        byte[] bArr2;
        int i7;
        int i8;
        int i9;
        byte[] bArr3;
        int i10;
        int i11 = i2;
        int i12 = i3;
        long j2 = j;
        int i13 = i6;
        int i14 = (i4 + 7) / 8;
        int i15 = (1 << i4) - 1;
        int i16 = i5;
        int i17 = i16 * 168;
        byte[] bArr4 = new byte[i17];
        byte[] bArr5 = bArr4;
        HashUtils.customizableSecureHashAlgorithmKECCAK128Simple(bArr4, 0, i17, 0, bArr, i, 32);
        short s = (short) 1;
        int i18 = 0;
        int i19 = 0;
        while (i18 < i11) {
            if (i19 > (i16 * 168) - (i14 * 4)) {
                bArr2 = bArr5;
                HashUtils.customizableSecureHashAlgorithmKECCAK128Simple(bArr2, 0, 168, s, bArr, i, 32);
                s = (short) (s + 1);
                i16 = 1;
                i19 = 0;
            } else {
                bArr2 = bArr5;
            }
            int load32 = CommonFunction.load32(bArr2, i19) & i15;
            int i20 = i19 + i14;
            int load322 = CommonFunction.load32(bArr2, i20) & i15;
            int i21 = i20 + i14;
            int load323 = CommonFunction.load32(bArr2, i21) & i15;
            int i22 = i21 + i14;
            int load324 = CommonFunction.load32(bArr2, i22) & i15;
            int i23 = i22 + i14;
            if (load32 >= i12 || i18 >= i11) {
                i9 = i15;
                i7 = i16;
                i8 = i23;
                bArr3 = bArr2;
            } else {
                i9 = i15;
                i7 = i16;
                long j3 = (long) load32;
                i8 = i23;
                bArr3 = bArr2;
                iArr[i18] = montgomery(j3 * ((long) i13), i12, j2);
                i18++;
            }
            if (load322 < i12 && i18 < i11) {
                long j4 = (long) load322;
                iArr[i18] = montgomery(j4 * ((long) i13), i12, j2);
                i18++;
            }
            if (load323 >= i12 || i18 >= i11) {
                i10 = i14;
            } else {
                i10 = i14;
                iArr[i18] = montgomery(((long) load323) * ((long) i13), i12, j2);
                i18++;
            }
            if (load324 < i12 && i18 < i11) {
                iArr[i18] = montgomery(((long) load324) * ((long) i13), i12, j2);
                i18++;
            }
            i14 = i10;
            bArr5 = bArr3;
            i15 = i9;
            i19 = i8;
            i16 = i7;
        }
    }

    public static void polynomialUniform(long[] jArr, byte[] bArr, int i, int i2, int i3, int i4, long j, int i5, int i6, int i7) {
        byte[] bArr2;
        int i8;
        int i9;
        int i10;
        int i11;
        byte[] bArr3;
        int i12 = i4;
        long j2 = j;
        int i13 = i7;
        int i14 = (i5 + 7) / 8;
        int i15 = (1 << i5) - 1;
        int i16 = i6;
        int i17 = i16 * 168;
        byte[] bArr4 = new byte[i17];
        HashUtils.customizableSecureHashAlgorithmKECCAK128Simple(bArr4, 0, i17, 0, bArr, i, 32);
        short s = (short) 1;
        int i18 = 0;
        int i19 = 0;
        while (true) {
            int i20 = i2 * i3;
            if (i18 < i20) {
                if (i19 > (i16 * 168) - (i14 * 4)) {
                    i8 = i20;
                    HashUtils.customizableSecureHashAlgorithmKECCAK128Simple(bArr4, 0, 168, s, bArr, i, 32);
                    bArr2 = bArr4;
                    s = (short) (s + 1);
                    i16 = 1;
                    i19 = 0;
                } else {
                    i8 = i20;
                    bArr2 = bArr4;
                }
                int load32 = CommonFunction.load32(bArr2, i19) & i15;
                int i21 = i19 + i14;
                int load322 = CommonFunction.load32(bArr2, i21) & i15;
                int i22 = i21 + i14;
                int load323 = CommonFunction.load32(bArr2, i22) & i15;
                int i23 = i22 + i14;
                int load324 = CommonFunction.load32(bArr2, i23) & i15;
                int i24 = i23 + i14;
                if (load32 >= i12 || i18 >= i8) {
                    i11 = i15;
                    i9 = i16;
                    i10 = i24;
                    bArr3 = bArr2;
                } else {
                    i11 = i15;
                    i9 = i16;
                    long j3 = (long) load32;
                    i10 = i24;
                    bArr3 = bArr2;
                    jArr[i18] = montgomeryP(j3 * ((long) i13), i12, j2);
                    i18++;
                }
                if (load322 < i12 && i18 < i8) {
                    long j4 = (long) load322;
                    jArr[i18] = montgomeryP(j4 * ((long) i13), i12, j2);
                    i18++;
                }
                if (load323 < i12 && i18 < i8) {
                    jArr[i18] = montgomeryP(((long) load323) * ((long) i13), i12, j2);
                    i18++;
                }
                if (load324 >= i12 || i18 >= i8) {
                    i16 = i9;
                } else {
                    jArr[i18] = montgomeryP(((long) load324) * ((long) i13), i12, j2);
                    i16 = i9;
                    i18++;
                }
                bArr4 = bArr3;
                i15 = i11;
                i19 = i10;
            } else {
                return;
            }
        }
    }

    public static void sparsePolynomialMultiplication16(int[] iArr, short[] sArr, int[] iArr2, short[] sArr2, int i, int i2) {
        Arrays.fill(iArr, 0);
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = iArr2[i3];
            for (int i5 = 0; i5 < i4; i5++) {
                iArr[i5] = iArr[i5] - (sArr2[i3] * sArr[(i + i5) - i4]);
            }
            for (int i6 = i4; i6 < i; i6++) {
                iArr[i6] = iArr[i6] + (sArr2[i3] * sArr[i6 - i4]);
            }
        }
    }

    public static void sparsePolynomialMultiplication32(int[] iArr, int[] iArr2, int[] iArr3, short[] sArr, int i, int i2) {
        Arrays.fill(iArr, 0);
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = iArr3[i3];
            for (int i5 = 0; i5 < i4; i5++) {
                iArr[i5] = iArr[i5] - (sArr[i3] * iArr2[(i + i5) - i4]);
            }
            for (int i6 = i4; i6 < i; i6++) {
                iArr[i6] = iArr[i6] + (sArr[i3] * iArr2[i6 - i4]);
            }
        }
    }

    public static void sparsePolynomialMultiplication32(long[] jArr, int i, int[] iArr, int i2, int[] iArr2, short[] sArr, int i3, int i4, int i5, int i6, int i7) {
        long[] jArr2 = jArr;
        int i8 = i3;
        Arrays.fill(jArr, 0);
        int i9 = i4;
        for (int i10 = 0; i10 < i9; i10++) {
            int i11 = iArr2[i10];
            for (int i12 = 0; i12 < i11; i12++) {
                int i13 = i + i12;
                jArr2[i13] = jArr2[i13] - ((long) (sArr[i10] * iArr[((i2 + i8) + i12) - i11]));
            }
            for (int i14 = i11; i14 < i8; i14++) {
                int i15 = i + i14;
                jArr2[i15] = jArr2[i15] + ((long) (sArr[i10] * iArr[(i2 + i14) - i11]));
            }
        }
        for (int i16 = 0; i16 < i8; i16++) {
            int i17 = i + i16;
            jArr2[i17] = barrett(jArr2[i17], i5, i6, i7);
        }
    }

    public static void sparsePolynomialMultiplication8(long[] jArr, int i, byte[] bArr, int i2, int[] iArr, short[] sArr, int i3, int i4) {
        long[] jArr2 = jArr;
        int i5 = i3;
        Arrays.fill(jArr, 0);
        int i6 = i4;
        for (int i7 = 0; i7 < i6; i7++) {
            int i8 = iArr[i7];
            for (int i9 = 0; i9 < i8; i9++) {
                int i10 = i + i9;
                jArr2[i10] = jArr2[i10] - ((long) (sArr[i7] * bArr[((i2 + i5) + i9) - i8]));
            }
            for (int i11 = i8; i11 < i5; i11++) {
                int i12 = i + i11;
                jArr2[i12] = jArr2[i12] + ((long) (sArr[i7] * bArr[(i2 + i11) - i8]));
            }
        }
    }
}
