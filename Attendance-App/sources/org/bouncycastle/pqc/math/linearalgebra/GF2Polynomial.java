package org.bouncycastle.pqc.math.linearalgebra;

import androidx.core.view.ViewCompat;
import com.itextpdf.barcodes.Barcode128;
import java.math.BigInteger;
import java.util.Random;
import kotlin.UByte;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;
import org.bouncycastle.asn1.cmc.BodyPartID;
import org.bouncycastle.util.Arrays;

public class GF2Polynomial {
    private static final int[] bitMask = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576, 2097152, 4194304, 8388608, 16777216, 33554432, PagedChannelRandomAccessSource.DEFAULT_TOTAL_BUFSIZE, 134217728, 268435456, 536870912, 1073741824, Integer.MIN_VALUE, 0};
    private static final boolean[] parity = {false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false};
    private static Random rand = new Random();
    private static final int[] reverseRightMask = {0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535, 131071, 262143, 524287, 1048575, 2097151, 4194303, Parameter.B_III_P, ViewCompat.MEASURED_SIZE_MASK, 33554431, 67108863, 134217727, 268435455, 536870911, LockFreeTaskQueueCore.MAX_CAPACITY_MASK, Integer.MAX_VALUE, -1};
    private static final short[] squaringTable = {0, 1, 4, 5, 16, 17, 20, 21, 64, 65, 68, 69, 80, 81, 84, 85, 256, 257, 260, 261, 272, 273, 276, 277, 320, 321, 324, 325, 336, 337, 340, 341, 1024, 1025, 1028, 1029, 1040, 1041, 1044, 1045, 1088, 1089, 1092, 1093, 1104, 1105, 1108, 1109, 1280, 1281, 1284, 1285, 1296, 1297, 1300, 1301, 1344, 1345, 1348, 1349, 1360, 1361, 1364, 1365, 4096, 4097, 4100, 4101, 4112, 4113, 4116, 4117, 4160, 4161, 4164, 4165, 4176, 4177, 4180, 4181, 4352, 4353, 4356, 4357, 4368, 4369, 4372, 4373, 4416, 4417, 4420, 4421, 4432, 4433, 4436, 4437, 5120, 5121, 5124, 5125, 5136, 5137, 5140, 5141, 5184, 5185, 5188, 5189, 5200, 5201, 5204, 5205, 5376, 5377, 5380, 5381, 5392, 5393, 5396, 5397, 5440, 5441, 5444, 5445, 5456, 5457, 5460, 5461, 16384, 16385, 16388, 16389, 16400, 16401, 16404, 16405, 16448, 16449, 16452, 16453, 16464, 16465, 16468, 16469, 16640, 16641, 16644, 16645, 16656, 16657, 16660, 16661, 16704, 16705, 16708, 16709, 16720, 16721, 16724, 16725, 17408, 17409, 17412, 17413, 17424, 17425, 17428, 17429, 17472, 17473, 17476, 17477, 17488, 17489, 17492, 17493, 17664, 17665, 17668, 17669, 17680, 17681, 17684, 17685, 17728, 17729, 17732, 17733, 17744, 17745, 17748, 17749, 20480, 20481, 20484, 20485, 20496, 20497, 20500, 20501, 20544, 20545, 20548, 20549, 20560, 20561, 20564, 20565, 20736, 20737, 20740, 20741, 20752, 20753, 20756, 20757, 20800, 20801, 20804, 20805, 20816, 20817, 20820, 20821, 21504, 21505, 21508, 21509, 21520, 21521, 21524, 21525, 21568, 21569, 21572, 21573, 21584, 21585, 21588, 21589, 21760, 21761, 21764, 21765, 21776, 21777, 21780, 21781, 21824, 21825, 21828, 21829, 21840, 21841, 21844, 21845};
    private int blocks;
    private int len;
    private int[] value;

    public GF2Polynomial(int i) {
        i = i < 1 ? 1 : i;
        int i2 = ((i - 1) >> 5) + 1;
        this.blocks = i2;
        this.value = new int[i2];
        this.len = i;
    }

    public GF2Polynomial(int i, String str) {
        i = i < 1 ? 1 : i;
        int i2 = ((i - 1) >> 5) + 1;
        this.blocks = i2;
        this.value = new int[i2];
        this.len = i;
        if (str.equalsIgnoreCase("ZERO")) {
            assignZero();
        } else if (str.equalsIgnoreCase("ONE")) {
            assignOne();
        } else if (str.equalsIgnoreCase("RANDOM")) {
            randomize();
        } else if (str.equalsIgnoreCase("X")) {
            assignX();
        } else if (str.equalsIgnoreCase("ALL")) {
            assignAll();
        } else {
            throw new IllegalArgumentException("Error: GF2Polynomial was called using " + str + " as value!");
        }
    }

    public GF2Polynomial(int i, BigInteger bigInteger) {
        i = i < 1 ? 1 : i;
        int i2 = ((i - 1) >> 5) + 1;
        this.blocks = i2;
        this.value = new int[i2];
        this.len = i;
        byte[] byteArray = bigInteger.toByteArray();
        if (byteArray[0] == 0) {
            int length = byteArray.length - 1;
            byte[] bArr = new byte[length];
            System.arraycopy(byteArray, 1, bArr, 0, length);
            byteArray = bArr;
        }
        int length2 = byteArray.length & 3;
        int length3 = ((byteArray.length - 1) >> 2) + 1;
        for (int i3 = 0; i3 < length2; i3++) {
            int[] iArr = this.value;
            int i4 = length3 - 1;
            iArr[i4] = iArr[i4] | ((byteArray[i3] & UByte.MAX_VALUE) << (((length2 - 1) - i3) << 3));
        }
        for (int i5 = 0; i5 <= ((byteArray.length - 4) >> 2); i5++) {
            int length4 = (byteArray.length - 1) - (i5 << 2);
            int[] iArr2 = this.value;
            int i6 = byteArray[length4] & UByte.MAX_VALUE;
            iArr2[i5] = i6;
            int i7 = i6 | ((byteArray[length4 - 1] << 8) & 65280);
            iArr2[i5] = i7;
            int i8 = i7 | ((byteArray[length4 - 2] << Tnaf.POW_2_WIDTH) & 16711680);
            iArr2[i5] = i8;
            iArr2[i5] = ((byteArray[length4 - 3] << 24) & ViewCompat.MEASURED_STATE_MASK) | i8;
        }
        int i9 = this.len;
        if ((i9 & 31) != 0) {
            int[] iArr3 = this.value;
            int i10 = this.blocks - 1;
            iArr3[i10] = reverseRightMask[i9 & 31] & iArr3[i10];
        }
        reduceN();
    }

    public GF2Polynomial(int i, Random random) {
        i = i < 1 ? 1 : i;
        int i2 = ((i - 1) >> 5) + 1;
        this.blocks = i2;
        this.value = new int[i2];
        this.len = i;
        randomize(random);
    }

    public GF2Polynomial(int i, byte[] bArr) {
        int i2;
        i = i < 1 ? 1 : i;
        int i3 = ((i - 1) >> 5) + 1;
        this.blocks = i3;
        this.value = new int[i3];
        this.len = i;
        int min = Math.min(((bArr.length - 1) >> 2) + 1, i3);
        int i4 = 0;
        while (true) {
            i2 = min - 1;
            if (i4 >= i2) {
                break;
            }
            int length = (bArr.length - (i4 << 2)) - 1;
            int[] iArr = this.value;
            int i5 = bArr[length] & UByte.MAX_VALUE;
            iArr[i4] = i5;
            int i6 = (65280 & (bArr[length - 1] << 8)) | i5;
            iArr[i4] = i6;
            int i7 = (16711680 & (bArr[length - 2] << Tnaf.POW_2_WIDTH)) | i6;
            iArr[i4] = i7;
            iArr[i4] = ((bArr[length - 3] << 24) & ViewCompat.MEASURED_STATE_MASK) | i7;
            i4++;
        }
        int length2 = (bArr.length - (i2 << 2)) - 1;
        int[] iArr2 = this.value;
        int i8 = bArr[length2] & UByte.MAX_VALUE;
        iArr2[i2] = i8;
        if (length2 > 0) {
            iArr2[i2] = (65280 & (bArr[length2 - 1] << 8)) | i8;
        }
        if (length2 > 1) {
            iArr2[i2] = iArr2[i2] | (16711680 & (bArr[length2 - 2] << Tnaf.POW_2_WIDTH));
        }
        if (length2 > 2) {
            iArr2[i2] = ((bArr[length2 - 3] << 24) & ViewCompat.MEASURED_STATE_MASK) | iArr2[i2];
        }
        zeroUnusedBits();
        reduceN();
    }

    public GF2Polynomial(int i, int[] iArr) {
        i = i < 1 ? 1 : i;
        int i2 = ((i - 1) >> 5) + 1;
        this.blocks = i2;
        this.value = new int[i2];
        this.len = i;
        System.arraycopy(iArr, 0, this.value, 0, Math.min(i2, iArr.length));
        zeroUnusedBits();
    }

    public GF2Polynomial(GF2Polynomial gF2Polynomial) {
        this.len = gF2Polynomial.len;
        this.blocks = gF2Polynomial.blocks;
        this.value = IntUtils.clone(gF2Polynomial.value);
    }

    private void doShiftBlocksLeft(int i) {
        int i2 = this.blocks;
        int[] iArr = this.value;
        if (i2 <= iArr.length) {
            for (int i3 = i2 - 1; i3 >= i; i3--) {
                int[] iArr2 = this.value;
                iArr2[i3] = iArr2[i3 - i];
            }
            for (int i4 = 0; i4 < i; i4++) {
                this.value[i4] = 0;
            }
            return;
        }
        int[] iArr3 = new int[i2];
        System.arraycopy(iArr, 0, iArr3, i, i2 - i);
        this.value = null;
        this.value = iArr3;
    }

    private GF2Polynomial karaMult(GF2Polynomial gF2Polynomial) {
        GF2Polynomial gF2Polynomial2 = new GF2Polynomial(this.len << 1);
        int i = this.len;
        if (i <= 32) {
            gF2Polynomial2.value = mult32(this.value[0], gF2Polynomial.value[0]);
            return gF2Polynomial2;
        } else if (i <= 64) {
            gF2Polynomial2.value = mult64(this.value, gF2Polynomial.value);
            return gF2Polynomial2;
        } else if (i <= 128) {
            gF2Polynomial2.value = mult128(this.value, gF2Polynomial.value);
            return gF2Polynomial2;
        } else if (i <= 256) {
            gF2Polynomial2.value = mult256(this.value, gF2Polynomial.value);
            return gF2Polynomial2;
        } else if (i <= 512) {
            gF2Polynomial2.value = mult512(this.value, gF2Polynomial.value);
            return gF2Polynomial2;
        } else {
            int i2 = bitMask[IntegerFunctions.floorLog(i - 1)];
            int i3 = ((i2 - 1) >> 5) + 1;
            GF2Polynomial lower = lower(i3);
            GF2Polynomial upper = upper(i3);
            GF2Polynomial lower2 = gF2Polynomial.lower(i3);
            GF2Polynomial upper2 = gF2Polynomial.upper(i3);
            GF2Polynomial karaMult = upper.karaMult(upper2);
            GF2Polynomial karaMult2 = lower.karaMult(lower2);
            lower.addToThis(upper);
            lower2.addToThis(upper2);
            GF2Polynomial karaMult3 = lower.karaMult(lower2);
            gF2Polynomial2.shiftLeftAddThis(karaMult, i2 << 1);
            gF2Polynomial2.shiftLeftAddThis(karaMult, i2);
            gF2Polynomial2.shiftLeftAddThis(karaMult3, i2);
            gF2Polynomial2.shiftLeftAddThis(karaMult2, i2);
            gF2Polynomial2.addToThis(karaMult2);
            return gF2Polynomial2;
        }
    }

    private GF2Polynomial lower(int i) {
        GF2Polynomial gF2Polynomial = new GF2Polynomial(i << 5);
        System.arraycopy(this.value, 0, gF2Polynomial.value, 0, Math.min(i, this.blocks));
        return gF2Polynomial;
    }

    private static int[] mult128(int[] iArr, int[] iArr2) {
        int[] iArr3 = new int[8];
        int[] iArr4 = new int[2];
        System.arraycopy(iArr, 0, iArr4, 0, Math.min(2, iArr.length));
        int[] iArr5 = new int[2];
        if (iArr.length > 2) {
            System.arraycopy(iArr, 2, iArr5, 0, Math.min(2, iArr.length - 2));
        }
        int[] iArr6 = new int[2];
        System.arraycopy(iArr2, 0, iArr6, 0, Math.min(2, iArr2.length));
        int[] iArr7 = new int[2];
        if (iArr2.length > 2) {
            System.arraycopy(iArr2, 2, iArr7, 0, Math.min(2, iArr2.length - 2));
        }
        if (iArr5[1] == 0 && iArr7[1] == 0) {
            int i = iArr5[0];
            if (!(i == 0 && iArr7[0] == 0)) {
                int[] mult32 = mult32(i, iArr7[0]);
                int i2 = iArr3[5];
                int i3 = mult32[1];
                iArr3[5] = i2 ^ i3;
                int i4 = iArr3[4];
                int i5 = mult32[0];
                iArr3[4] = i4 ^ i5;
                iArr3[3] = iArr3[3] ^ i3;
                iArr3[2] = i5 ^ iArr3[2];
            }
        } else {
            int[] mult64 = mult64(iArr5, iArr7);
            int i6 = iArr3[7];
            int i7 = mult64[3];
            iArr3[7] = i6 ^ i7;
            int i8 = iArr3[6];
            int i9 = mult64[2];
            iArr3[6] = i8 ^ i9;
            int i10 = iArr3[5];
            int i11 = mult64[1];
            iArr3[5] = i10 ^ (i7 ^ i11);
            int i12 = iArr3[4];
            int i13 = mult64[0];
            iArr3[4] = i12 ^ (i13 ^ i9);
            iArr3[3] = iArr3[3] ^ i11;
            iArr3[2] = i13 ^ iArr3[2];
        }
        iArr5[0] = iArr5[0] ^ iArr4[0];
        iArr5[1] = iArr5[1] ^ iArr4[1];
        int i14 = iArr7[0] ^ iArr6[0];
        iArr7[0] = i14;
        int i15 = iArr7[1] ^ iArr6[1];
        iArr7[1] = i15;
        if (iArr5[1] == 0 && i15 == 0) {
            int[] mult322 = mult32(iArr5[0], i14);
            iArr3[3] = iArr3[3] ^ mult322[1];
            iArr3[2] = mult322[0] ^ iArr3[2];
        } else {
            int[] mult642 = mult64(iArr5, iArr7);
            iArr3[5] = iArr3[5] ^ mult642[3];
            iArr3[4] = iArr3[4] ^ mult642[2];
            iArr3[3] = iArr3[3] ^ mult642[1];
            iArr3[2] = mult642[0] ^ iArr3[2];
        }
        if (iArr4[1] == 0 && iArr6[1] == 0) {
            int[] mult323 = mult32(iArr4[0], iArr6[0]);
            int i16 = iArr3[3];
            int i17 = mult323[1];
            iArr3[3] = i16 ^ i17;
            int i18 = iArr3[2];
            int i19 = mult323[0];
            iArr3[2] = i18 ^ i19;
            iArr3[1] = iArr3[1] ^ i17;
            iArr3[0] = i19 ^ iArr3[0];
        } else {
            int[] mult643 = mult64(iArr4, iArr6);
            int i20 = iArr3[5];
            int i21 = mult643[3];
            iArr3[5] = i20 ^ i21;
            int i22 = iArr3[4];
            int i23 = mult643[2];
            iArr3[4] = i22 ^ i23;
            int i24 = iArr3[3];
            int i25 = mult643[1];
            iArr3[3] = i24 ^ (i21 ^ i25);
            int i26 = iArr3[2];
            int i27 = mult643[0];
            iArr3[2] = i26 ^ (i27 ^ i23);
            iArr3[1] = iArr3[1] ^ i25;
            iArr3[0] = i27 ^ iArr3[0];
        }
        return iArr3;
    }

    private static int[] mult256(int[] iArr, int[] iArr2) {
        int[] iArr3 = iArr;
        int[] iArr4 = iArr2;
        int[] iArr5 = new int[16];
        int[] iArr6 = new int[4];
        System.arraycopy(iArr3, 0, iArr6, 0, Math.min(4, iArr3.length));
        int[] iArr7 = new int[4];
        if (iArr3.length > 4) {
            System.arraycopy(iArr3, 4, iArr7, 0, Math.min(4, iArr3.length - 4));
        }
        int[] iArr8 = new int[4];
        System.arraycopy(iArr4, 0, iArr8, 0, Math.min(4, iArr4.length));
        int[] iArr9 = new int[4];
        if (iArr4.length > 4) {
            System.arraycopy(iArr4, 4, iArr9, 0, Math.min(4, iArr4.length - 4));
        }
        if (iArr7[3] != 0 || iArr7[2] != 0 || iArr9[3] != 0 || iArr9[2] != 0) {
            int[] mult128 = mult128(iArr7, iArr9);
            int i = iArr5[15];
            int i2 = mult128[7];
            iArr5[15] = i ^ i2;
            int i3 = iArr5[14];
            int i4 = mult128[6];
            iArr5[14] = i3 ^ i4;
            int i5 = iArr5[13];
            int i6 = mult128[5];
            iArr5[13] = i5 ^ i6;
            int i7 = iArr5[12];
            int i8 = mult128[4];
            iArr5[12] = i7 ^ i8;
            int i9 = iArr5[11];
            int i10 = mult128[3];
            iArr5[11] = i9 ^ (i10 ^ i2);
            int i11 = iArr5[10];
            int i12 = mult128[2];
            iArr5[10] = i11 ^ (i12 ^ i4);
            int i13 = iArr5[9];
            int i14 = mult128[1];
            iArr5[9] = i13 ^ (i14 ^ i6);
            int i15 = iArr5[8];
            int i16 = mult128[0];
            iArr5[8] = i15 ^ (i16 ^ i8);
            iArr5[7] = iArr5[7] ^ i10;
            iArr5[6] = iArr5[6] ^ i12;
            iArr5[5] = iArr5[5] ^ i14;
            iArr5[4] = i16 ^ iArr5[4];
        } else if (iArr7[1] == 0 && iArr9[1] == 0) {
            int i17 = iArr7[0];
            if (!(i17 == 0 && iArr9[0] == 0)) {
                int[] mult32 = mult32(i17, iArr9[0]);
                int i18 = iArr5[9];
                int i19 = mult32[1];
                iArr5[9] = i18 ^ i19;
                int i20 = iArr5[8];
                int i21 = mult32[0];
                iArr5[8] = i20 ^ i21;
                iArr5[5] = iArr5[5] ^ i19;
                iArr5[4] = i21 ^ iArr5[4];
            }
        } else {
            int[] mult64 = mult64(iArr7, iArr9);
            int i22 = iArr5[11];
            int i23 = mult64[3];
            iArr5[11] = i22 ^ i23;
            int i24 = iArr5[10];
            int i25 = mult64[2];
            iArr5[10] = i24 ^ i25;
            int i26 = iArr5[9];
            int i27 = mult64[1];
            iArr5[9] = i26 ^ i27;
            int i28 = iArr5[8];
            int i29 = mult64[0];
            iArr5[8] = i28 ^ i29;
            iArr5[7] = iArr5[7] ^ i23;
            iArr5[6] = iArr5[6] ^ i25;
            iArr5[5] = iArr5[5] ^ i27;
            iArr5[4] = i29 ^ iArr5[4];
        }
        iArr7[0] = iArr7[0] ^ iArr6[0];
        iArr7[1] = iArr7[1] ^ iArr6[1];
        iArr7[2] = iArr7[2] ^ iArr6[2];
        iArr7[3] = iArr7[3] ^ iArr6[3];
        iArr9[0] = iArr9[0] ^ iArr8[0];
        iArr9[1] = iArr9[1] ^ iArr8[1];
        iArr9[2] = iArr9[2] ^ iArr8[2];
        iArr9[3] = iArr9[3] ^ iArr8[3];
        int[] mult1282 = mult128(iArr7, iArr9);
        iArr5[11] = iArr5[11] ^ mult1282[7];
        iArr5[10] = iArr5[10] ^ mult1282[6];
        iArr5[9] = iArr5[9] ^ mult1282[5];
        iArr5[8] = iArr5[8] ^ mult1282[4];
        iArr5[7] = iArr5[7] ^ mult1282[3];
        iArr5[6] = iArr5[6] ^ mult1282[2];
        iArr5[5] = iArr5[5] ^ mult1282[1];
        iArr5[4] = mult1282[0] ^ iArr5[4];
        int[] mult1283 = mult128(iArr6, iArr8);
        int i30 = iArr5[11];
        int i31 = mult1283[7];
        iArr5[11] = i30 ^ i31;
        int i32 = iArr5[10];
        int i33 = mult1283[6];
        iArr5[10] = i32 ^ i33;
        int i34 = iArr5[9];
        int i35 = mult1283[5];
        iArr5[9] = i34 ^ i35;
        int i36 = iArr5[8];
        int i37 = mult1283[4];
        iArr5[8] = i36 ^ i37;
        int i38 = iArr5[7];
        int i39 = mult1283[3];
        iArr5[7] = i38 ^ (i31 ^ i39);
        int i40 = iArr5[6];
        int i41 = mult1283[2];
        iArr5[6] = i40 ^ (i33 ^ i41);
        int i42 = iArr5[5];
        int i43 = mult1283[1];
        iArr5[5] = i42 ^ (i35 ^ i43);
        int i44 = iArr5[4];
        int i45 = mult1283[0];
        iArr5[4] = i44 ^ (i45 ^ i37);
        iArr5[3] = iArr5[3] ^ i39;
        iArr5[2] = iArr5[2] ^ i41;
        iArr5[1] = iArr5[1] ^ i43;
        iArr5[0] = i45 ^ iArr5[0];
        return iArr5;
    }

    private static int[] mult32(int i, int i2) {
        int[] iArr = new int[2];
        if (!(i == 0 || i2 == 0)) {
            long j = ((long) i2) & BodyPartID.bodyIdMax;
            long j2 = 0;
            for (int i3 = 1; i3 <= 32; i3++) {
                if ((bitMask[i3 - 1] & i) != 0) {
                    j2 ^= j;
                }
                j <<= 1;
            }
            iArr[1] = (int) (j2 >>> 32);
            iArr[0] = (int) (j2 & BodyPartID.bodyIdMax);
        }
        return iArr;
    }

    private static int[] mult512(int[] iArr, int[] iArr2) {
        int[] iArr3 = iArr;
        int[] iArr4 = iArr2;
        int[] iArr5 = new int[32];
        int[] iArr6 = new int[8];
        System.arraycopy(iArr3, 0, iArr6, 0, Math.min(8, iArr3.length));
        int[] iArr7 = new int[8];
        if (iArr3.length > 8) {
            System.arraycopy(iArr3, 8, iArr7, 0, Math.min(8, iArr3.length - 8));
        }
        int[] iArr8 = new int[8];
        System.arraycopy(iArr4, 0, iArr8, 0, Math.min(8, iArr4.length));
        int[] iArr9 = new int[8];
        if (iArr4.length > 8) {
            System.arraycopy(iArr4, 8, iArr9, 0, Math.min(8, iArr4.length - 8));
        }
        int[] mult256 = mult256(iArr7, iArr9);
        int i = iArr5[31];
        int i2 = mult256[15];
        iArr5[31] = i ^ i2;
        int i3 = iArr5[30];
        int i4 = mult256[14];
        iArr5[30] = i3 ^ i4;
        int i5 = iArr5[29];
        int i6 = mult256[13];
        iArr5[29] = i5 ^ i6;
        int i7 = iArr5[28];
        int i8 = mult256[12];
        iArr5[28] = i7 ^ i8;
        int i9 = iArr5[27];
        int i10 = mult256[11];
        iArr5[27] = i9 ^ i10;
        int i11 = iArr5[26];
        int i12 = mult256[10];
        iArr5[26] = i11 ^ i12;
        int i13 = iArr5[25];
        int i14 = mult256[9];
        iArr5[25] = i13 ^ i14;
        int i15 = iArr5[24];
        int i16 = mult256[8];
        iArr5[24] = i15 ^ i16;
        int i17 = iArr5[23];
        int i18 = mult256[7];
        iArr5[23] = i17 ^ (i18 ^ i2);
        int i19 = iArr5[22];
        int i20 = mult256[6];
        iArr5[22] = i19 ^ (i20 ^ i4);
        int i21 = iArr5[21];
        int i22 = mult256[5];
        iArr5[21] = i21 ^ (i22 ^ i6);
        int i23 = iArr5[20];
        int i24 = mult256[4];
        iArr5[20] = i23 ^ (i24 ^ i8);
        int i25 = iArr5[19];
        int i26 = mult256[3];
        iArr5[19] = i25 ^ (i26 ^ i10);
        int i27 = iArr5[18];
        int i28 = mult256[2];
        iArr5[18] = i27 ^ (i28 ^ i12);
        int i29 = iArr5[17];
        int i30 = mult256[1];
        iArr5[17] = i29 ^ (i30 ^ i14);
        int i31 = iArr5[16];
        int i32 = mult256[0];
        iArr5[16] = i31 ^ (i32 ^ i16);
        iArr5[15] = iArr5[15] ^ i18;
        iArr5[14] = iArr5[14] ^ i20;
        iArr5[13] = iArr5[13] ^ i22;
        iArr5[12] = iArr5[12] ^ i24;
        iArr5[11] = iArr5[11] ^ i26;
        iArr5[10] = iArr5[10] ^ i28;
        iArr5[9] = iArr5[9] ^ i30;
        iArr5[8] = iArr5[8] ^ i32;
        iArr7[0] = iArr7[0] ^ iArr6[0];
        iArr7[1] = iArr7[1] ^ iArr6[1];
        iArr7[2] = iArr7[2] ^ iArr6[2];
        iArr7[3] = iArr7[3] ^ iArr6[3];
        iArr7[4] = iArr7[4] ^ iArr6[4];
        iArr7[5] = iArr7[5] ^ iArr6[5];
        iArr7[6] = iArr7[6] ^ iArr6[6];
        iArr7[7] = iArr7[7] ^ iArr6[7];
        iArr9[0] = iArr9[0] ^ iArr8[0];
        iArr9[1] = iArr9[1] ^ iArr8[1];
        iArr9[2] = iArr9[2] ^ iArr8[2];
        iArr9[3] = iArr9[3] ^ iArr8[3];
        iArr9[4] = iArr9[4] ^ iArr8[4];
        iArr9[5] = iArr9[5] ^ iArr8[5];
        iArr9[6] = iArr9[6] ^ iArr8[6];
        iArr9[7] = iArr9[7] ^ iArr8[7];
        int[] mult2562 = mult256(iArr7, iArr9);
        iArr5[23] = iArr5[23] ^ mult2562[15];
        iArr5[22] = iArr5[22] ^ mult2562[14];
        iArr5[21] = iArr5[21] ^ mult2562[13];
        iArr5[20] = iArr5[20] ^ mult2562[12];
        iArr5[19] = iArr5[19] ^ mult2562[11];
        iArr5[18] = iArr5[18] ^ mult2562[10];
        iArr5[17] = iArr5[17] ^ mult2562[9];
        iArr5[16] = iArr5[16] ^ mult2562[8];
        iArr5[15] = iArr5[15] ^ mult2562[7];
        iArr5[14] = iArr5[14] ^ mult2562[6];
        iArr5[13] = iArr5[13] ^ mult2562[5];
        iArr5[12] = iArr5[12] ^ mult2562[4];
        iArr5[11] = iArr5[11] ^ mult2562[3];
        iArr5[10] = iArr5[10] ^ mult2562[2];
        iArr5[9] = iArr5[9] ^ mult2562[1];
        iArr5[8] = mult2562[0] ^ iArr5[8];
        int[] mult2563 = mult256(iArr6, iArr8);
        int i33 = iArr5[23];
        int i34 = mult2563[15];
        iArr5[23] = i33 ^ i34;
        int i35 = iArr5[22];
        int i36 = mult2563[14];
        iArr5[22] = i35 ^ i36;
        int i37 = iArr5[21];
        int i38 = mult2563[13];
        iArr5[21] = i37 ^ i38;
        int i39 = iArr5[20];
        int i40 = mult2563[12];
        iArr5[20] = i39 ^ i40;
        int i41 = iArr5[19];
        int i42 = mult2563[11];
        iArr5[19] = i41 ^ i42;
        int i43 = iArr5[18];
        int i44 = mult2563[10];
        iArr5[18] = i43 ^ i44;
        int i45 = iArr5[17];
        int i46 = mult2563[9];
        iArr5[17] = i45 ^ i46;
        int i47 = iArr5[16];
        int i48 = mult2563[8];
        iArr5[16] = i47 ^ i48;
        int i49 = iArr5[15];
        int i50 = mult2563[7];
        iArr5[15] = i49 ^ (i34 ^ i50);
        int i51 = iArr5[14];
        int i52 = mult2563[6];
        iArr5[14] = i51 ^ (i36 ^ i52);
        int i53 = iArr5[13];
        int i54 = mult2563[5];
        iArr5[13] = i53 ^ (i38 ^ i54);
        int i55 = iArr5[12];
        int i56 = mult2563[4];
        iArr5[12] = i55 ^ (i40 ^ i56);
        int i57 = iArr5[11];
        int i58 = mult2563[3];
        iArr5[11] = i57 ^ (i42 ^ i58);
        int i59 = iArr5[10];
        int i60 = mult2563[2];
        iArr5[10] = i59 ^ (i60 ^ i44);
        int i61 = iArr5[9];
        int i62 = mult2563[1];
        iArr5[9] = i61 ^ (i62 ^ i46);
        int i63 = iArr5[8];
        int i64 = mult2563[0];
        iArr5[8] = i63 ^ (i64 ^ i48);
        iArr5[7] = iArr5[7] ^ i50;
        iArr5[6] = iArr5[6] ^ i52;
        iArr5[5] = iArr5[5] ^ i54;
        iArr5[4] = iArr5[4] ^ i56;
        iArr5[3] = iArr5[3] ^ i58;
        iArr5[2] = iArr5[2] ^ i60;
        iArr5[1] = iArr5[1] ^ i62;
        iArr5[0] = i64 ^ iArr5[0];
        return iArr5;
    }

    private static int[] mult64(int[] iArr, int[] iArr2) {
        int[] iArr3 = new int[4];
        int i = iArr[0];
        int i2 = iArr.length > 1 ? iArr[1] : 0;
        int i3 = iArr2[0];
        int i4 = iArr2.length > 1 ? iArr2[1] : 0;
        if (!(i2 == 0 && i4 == 0)) {
            int[] mult32 = mult32(i2, i4);
            int i5 = iArr3[3];
            int i6 = mult32[1];
            iArr3[3] = i5 ^ i6;
            int i7 = iArr3[2];
            int i8 = mult32[0];
            iArr3[2] = i7 ^ (i8 ^ i6);
            iArr3[1] = i8 ^ iArr3[1];
        }
        int[] mult322 = mult32(i2 ^ i, i4 ^ i3);
        iArr3[2] = iArr3[2] ^ mult322[1];
        iArr3[1] = mult322[0] ^ iArr3[1];
        int[] mult323 = mult32(i, i3);
        int i9 = iArr3[2];
        int i10 = mult323[1];
        iArr3[2] = i9 ^ i10;
        int i11 = iArr3[1];
        int i12 = mult323[0];
        iArr3[1] = i11 ^ (i10 ^ i12);
        iArr3[0] = i12 ^ iArr3[0];
        return iArr3;
    }

    private GF2Polynomial upper(int i) {
        int min = Math.min(i, this.blocks - i);
        GF2Polynomial gF2Polynomial = new GF2Polynomial(min << 5);
        if (this.blocks >= i) {
            System.arraycopy(this.value, i, gF2Polynomial.value, 0, min);
        }
        return gF2Polynomial;
    }

    private void zeroUnusedBits() {
        int i = this.len;
        if ((i & 31) != 0) {
            int[] iArr = this.value;
            int i2 = this.blocks - 1;
            iArr[i2] = reverseRightMask[i & 31] & iArr[i2];
        }
    }

    public GF2Polynomial add(GF2Polynomial gF2Polynomial) {
        return xor(gF2Polynomial);
    }

    public void addToThis(GF2Polynomial gF2Polynomial) {
        expandN(gF2Polynomial.len);
        xorThisBy(gF2Polynomial);
    }

    public void assignAll() {
        for (int i = 0; i < this.blocks; i++) {
            this.value[i] = -1;
        }
        zeroUnusedBits();
    }

    public void assignOne() {
        for (int i = 1; i < this.blocks; i++) {
            this.value[i] = 0;
        }
        this.value[0] = 1;
    }

    public void assignX() {
        for (int i = 1; i < this.blocks; i++) {
            this.value[i] = 0;
        }
        this.value[0] = 2;
    }

    public void assignZero() {
        for (int i = 0; i < this.blocks; i++) {
            this.value[i] = 0;
        }
    }

    public Object clone() {
        return new GF2Polynomial(this);
    }

    public GF2Polynomial[] divide(GF2Polynomial gF2Polynomial) throws RuntimeException {
        GF2Polynomial[] gF2PolynomialArr = new GF2Polynomial[2];
        GF2Polynomial gF2Polynomial2 = new GF2Polynomial(this.len);
        GF2Polynomial gF2Polynomial3 = new GF2Polynomial(this);
        GF2Polynomial gF2Polynomial4 = new GF2Polynomial(gF2Polynomial);
        if (!gF2Polynomial4.isZero()) {
            gF2Polynomial3.reduceN();
            gF2Polynomial4.reduceN();
            int i = gF2Polynomial3.len;
            int i2 = gF2Polynomial4.len;
            if (i < i2) {
                gF2PolynomialArr[0] = new GF2Polynomial(0);
                gF2PolynomialArr[1] = gF2Polynomial3;
                return gF2PolynomialArr;
            }
            int i3 = i - i2;
            gF2Polynomial2.expandN(i3 + 1);
            while (i3 >= 0) {
                gF2Polynomial3.subtractFromThis(gF2Polynomial4.shiftLeft(i3));
                gF2Polynomial3.reduceN();
                gF2Polynomial2.xorBit(i3);
                i3 = gF2Polynomial3.len - gF2Polynomial4.len;
            }
            gF2PolynomialArr[0] = gF2Polynomial2;
            gF2PolynomialArr[1] = gF2Polynomial3;
            return gF2PolynomialArr;
        }
        throw new RuntimeException();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof GF2Polynomial)) {
            return false;
        }
        GF2Polynomial gF2Polynomial = (GF2Polynomial) obj;
        if (this.len != gF2Polynomial.len) {
            return false;
        }
        for (int i = 0; i < this.blocks; i++) {
            if (this.value[i] != gF2Polynomial.value[i]) {
                return false;
            }
        }
        return true;
    }

    public void expandN(int i) {
        if (this.len < i) {
            this.len = i;
            int i2 = ((i - 1) >>> 5) + 1;
            int i3 = this.blocks;
            if (i3 < i2) {
                int[] iArr = this.value;
                if (iArr.length >= i2) {
                    while (i3 < i2) {
                        this.value[i3] = 0;
                        i3++;
                    }
                    this.blocks = i2;
                    return;
                }
                int[] iArr2 = new int[i2];
                System.arraycopy(iArr, 0, iArr2, 0, i3);
                this.blocks = i2;
                this.value = null;
                this.value = iArr2;
            }
        }
    }

    public GF2Polynomial gcd(GF2Polynomial gF2Polynomial) throws RuntimeException {
        if (isZero() && gF2Polynomial.isZero()) {
            throw new ArithmeticException("Both operands of gcd equal zero.");
        } else if (isZero()) {
            return new GF2Polynomial(gF2Polynomial);
        } else {
            if (gF2Polynomial.isZero()) {
                return new GF2Polynomial(this);
            }
            GF2Polynomial gF2Polynomial2 = new GF2Polynomial(this);
            GF2Polynomial gF2Polynomial3 = new GF2Polynomial(gF2Polynomial);
            GF2Polynomial gF2Polynomial4 = gF2Polynomial2;
            GF2Polynomial gF2Polynomial5 = gF2Polynomial3;
            while (!gF2Polynomial5.isZero()) {
                GF2Polynomial gF2Polynomial6 = gF2Polynomial5;
                gF2Polynomial5 = gF2Polynomial4.remainder(gF2Polynomial5);
                gF2Polynomial4 = gF2Polynomial6;
            }
            return gF2Polynomial4;
        }
    }

    public int getBit(int i) {
        if (i < 0) {
            throw new RuntimeException();
        } else if (i > this.len - 1) {
            return 0;
        } else {
            return (bitMask[i & 31] & this.value[i >>> 5]) != 0 ? 1 : 0;
        }
    }

    public int getLength() {
        return this.len;
    }

    public int hashCode() {
        return this.len + Arrays.hashCode(this.value);
    }

    public GF2Polynomial increase() {
        GF2Polynomial gF2Polynomial = new GF2Polynomial(this);
        gF2Polynomial.increaseThis();
        return gF2Polynomial;
    }

    public void increaseThis() {
        xorBit(0);
    }

    public boolean isIrreducible() {
        if (isZero()) {
            return false;
        }
        GF2Polynomial gF2Polynomial = new GF2Polynomial(this);
        gF2Polynomial.reduceN();
        int i = gF2Polynomial.len;
        int i2 = i - 1;
        GF2Polynomial gF2Polynomial2 = new GF2Polynomial(i, "X");
        for (int i3 = 1; i3 <= (i2 >> 1); i3++) {
            gF2Polynomial2.squareThisPreCalc();
            gF2Polynomial2 = gF2Polynomial2.remainder(gF2Polynomial);
            GF2Polynomial add = gF2Polynomial2.add(new GF2Polynomial(32, "X"));
            if (add.isZero() || !gF2Polynomial.gcd(add).isOne()) {
                return false;
            }
        }
        return true;
    }

    public boolean isOne() {
        for (int i = 1; i < this.blocks; i++) {
            if (this.value[i] != 0) {
                return false;
            }
        }
        return this.value[0] == 1;
    }

    public boolean isZero() {
        if (this.len == 0) {
            return true;
        }
        for (int i = 0; i < this.blocks; i++) {
            if (this.value[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public GF2Polynomial multiply(GF2Polynomial gF2Polynomial) {
        int max = Math.max(this.len, gF2Polynomial.len);
        expandN(max);
        gF2Polynomial.expandN(max);
        return karaMult(gF2Polynomial);
    }

    public GF2Polynomial multiplyClassic(GF2Polynomial gF2Polynomial) {
        GF2Polynomial gF2Polynomial2 = new GF2Polynomial(Math.max(this.len, gF2Polynomial.len) << 1);
        GF2Polynomial[] gF2PolynomialArr = new GF2Polynomial[32];
        gF2PolynomialArr[0] = new GF2Polynomial(this);
        for (int i = 1; i <= 31; i++) {
            gF2PolynomialArr[i] = gF2PolynomialArr[i - 1].shiftLeft();
        }
        for (int i2 = 0; i2 < gF2Polynomial.blocks; i2++) {
            for (int i3 = 0; i3 <= 31; i3++) {
                if ((gF2Polynomial.value[i2] & bitMask[i3]) != 0) {
                    gF2Polynomial2.xorThisBy(gF2PolynomialArr[i3]);
                }
            }
            for (int i4 = 0; i4 <= 31; i4++) {
                gF2PolynomialArr[i4].shiftBlocksLeft();
            }
        }
        return gF2Polynomial2;
    }

    public GF2Polynomial quotient(GF2Polynomial gF2Polynomial) throws RuntimeException {
        GF2Polynomial gF2Polynomial2 = new GF2Polynomial(this.len);
        GF2Polynomial gF2Polynomial3 = new GF2Polynomial(this);
        GF2Polynomial gF2Polynomial4 = new GF2Polynomial(gF2Polynomial);
        if (!gF2Polynomial4.isZero()) {
            gF2Polynomial3.reduceN();
            gF2Polynomial4.reduceN();
            int i = gF2Polynomial3.len;
            int i2 = gF2Polynomial4.len;
            if (i < i2) {
                return new GF2Polynomial(0);
            }
            int i3 = i - i2;
            gF2Polynomial2.expandN(i3 + 1);
            while (i3 >= 0) {
                gF2Polynomial3.subtractFromThis(gF2Polynomial4.shiftLeft(i3));
                gF2Polynomial3.reduceN();
                gF2Polynomial2.xorBit(i3);
                i3 = gF2Polynomial3.len - gF2Polynomial4.len;
            }
            return gF2Polynomial2;
        }
        throw new RuntimeException();
    }

    public void randomize() {
        for (int i = 0; i < this.blocks; i++) {
            this.value[i] = rand.nextInt();
        }
        zeroUnusedBits();
    }

    public void randomize(Random random) {
        for (int i = 0; i < this.blocks; i++) {
            this.value[i] = random.nextInt();
        }
        zeroUnusedBits();
    }

    public void reduceN() {
        int i;
        int i2 = this.blocks;
        while (true) {
            i2--;
            i = this.value[i2];
            if (i != 0 || i2 <= 0) {
                int i3 = 0;
            }
        }
        int i32 = 0;
        while (i != 0) {
            i >>>= 1;
            i32++;
        }
        this.len = (i2 << 5) + i32;
        this.blocks = i2 + 1;
    }

    /* access modifiers changed from: package-private */
    public void reducePentanomial(int i, int[] iArr) {
        GF2Polynomial gF2Polynomial = this;
        int i2 = i;
        int i3 = i2 >>> 5;
        int i4 = i2 & 31;
        int i5 = 32 - i4;
        int i6 = iArr[0];
        int i7 = (i2 - i6) >>> 5;
        int i8 = 32 - ((i2 - i6) & 31);
        int i9 = iArr[1];
        int i10 = (i2 - i9) >>> 5;
        int i11 = 32 - ((i2 - i9) & 31);
        int i12 = iArr[2];
        int i13 = (i2 - i12) >>> 5;
        int i14 = 32 - ((i2 - i12) & 31);
        int i15 = ((i2 << 1) - 2) >>> 5;
        while (i15 > i3) {
            int[] iArr2 = gF2Polynomial.value;
            long j = ((long) iArr2[i15]) & BodyPartID.bodyIdMax;
            int i16 = i15 - i3;
            int i17 = i16 - 1;
            int i18 = i3;
            int i19 = i4;
            iArr2[i17] = iArr2[i17] ^ ((int) (j << i5));
            iArr2[i16] = (int) (((long) iArr2[i16]) ^ (j >>> (32 - i5)));
            int i20 = i15 - i7;
            int i21 = i20 - 1;
            iArr2[i21] = iArr2[i21] ^ ((int) (j << i8));
            iArr2[i20] = (int) (((long) iArr2[i20]) ^ (j >>> (32 - i8)));
            int i22 = i15 - i10;
            int i23 = i22 - 1;
            iArr2[i23] = iArr2[i23] ^ ((int) (j << i11));
            iArr2[i22] = (int) (((long) iArr2[i22]) ^ (j >>> (32 - i11)));
            int i24 = i15 - i13;
            int i25 = i24 - 1;
            iArr2[i25] = iArr2[i25] ^ ((int) (j << i14));
            iArr2[i24] = (int) ((j >>> (32 - i14)) ^ ((long) iArr2[i24]));
            iArr2[i15] = 0;
            i15--;
            gF2Polynomial = this;
            int i26 = i;
            i3 = i18;
            i4 = i19;
        }
        int i27 = i3;
        int i28 = i4;
        int[] iArr3 = gF2Polynomial.value;
        long j2 = ((long) iArr3[i27]) & BodyPartID.bodyIdMax & (BodyPartID.bodyIdMax << i28);
        iArr3[0] = (int) ((j2 >>> (32 - i5)) ^ ((long) iArr3[0]));
        int i29 = i27 - i7;
        int i30 = i29 - 1;
        if (i30 >= 0) {
            iArr3[i30] = iArr3[i30] ^ ((int) (j2 << i8));
        }
        iArr3[i29] = (int) (((long) iArr3[i29]) ^ (j2 >>> (32 - i8)));
        int i31 = i27 - i10;
        int i32 = i31 - 1;
        if (i32 >= 0) {
            iArr3[i32] = iArr3[i32] ^ ((int) (j2 << i11));
        }
        iArr3[i31] = (int) (((long) iArr3[i31]) ^ (j2 >>> (32 - i11)));
        int i33 = i27 - i13;
        int i34 = i33 - 1;
        if (i34 >= 0) {
            iArr3[i34] = iArr3[i34] ^ ((int) (j2 << i14));
        }
        iArr3[i33] = (int) ((j2 >>> (32 - i14)) ^ ((long) iArr3[i33]));
        iArr3[i27] = iArr3[i27] & reverseRightMask[i28];
        int i35 = i;
        this.blocks = ((i35 - 1) >>> 5) + 1;
        this.len = i35;
    }

    /* access modifiers changed from: package-private */
    public void reduceTrinomial(int i, int i2) {
        int i3 = i;
        int i4 = i3 >>> 5;
        int i5 = i3 & 31;
        int i6 = 32 - i5;
        int i7 = i3 - i2;
        int i8 = i7 >>> 5;
        int i9 = 32 - (i7 & 31);
        int i10 = ((i3 << 1) - 2) >>> 5;
        while (i10 > i4) {
            int[] iArr = this.value;
            long j = BodyPartID.bodyIdMax & ((long) iArr[i10]);
            int i11 = i10 - i4;
            int i12 = i11 - 1;
            int i13 = i4;
            iArr[i12] = iArr[i12] ^ ((int) (j << i6));
            iArr[i11] = (int) (((long) iArr[i11]) ^ (j >>> (32 - i6)));
            int i14 = i10 - i8;
            int i15 = i14 - 1;
            iArr[i15] = iArr[i15] ^ ((int) (j << i9));
            iArr[i14] = (int) ((j >>> (32 - i9)) ^ ((long) iArr[i14]));
            iArr[i10] = 0;
            i10--;
            int i16 = i;
            i4 = i13;
        }
        int i17 = i4;
        int[] iArr2 = this.value;
        long j2 = (BodyPartID.bodyIdMax << i5) & ((long) iArr2[i17]) & BodyPartID.bodyIdMax;
        iArr2[0] = (int) (((long) iArr2[0]) ^ (j2 >>> (32 - i6)));
        int i18 = i17 - i8;
        int i19 = i18 - 1;
        if (i19 >= 0) {
            iArr2[i19] = iArr2[i19] ^ ((int) (j2 << i9));
        }
        iArr2[i18] = (int) ((j2 >>> (32 - i9)) ^ ((long) iArr2[i18]));
        iArr2[i17] = iArr2[i17] & reverseRightMask[i5];
        int i20 = i;
        this.blocks = ((i20 - 1) >>> 5) + 1;
        this.len = i20;
    }

    public GF2Polynomial remainder(GF2Polynomial gF2Polynomial) throws RuntimeException {
        GF2Polynomial gF2Polynomial2 = new GF2Polynomial(this);
        GF2Polynomial gF2Polynomial3 = new GF2Polynomial(gF2Polynomial);
        if (!gF2Polynomial3.isZero()) {
            gF2Polynomial2.reduceN();
            gF2Polynomial3.reduceN();
            int i = gF2Polynomial2.len;
            int i2 = gF2Polynomial3.len;
            if (i < i2) {
                return gF2Polynomial2;
            }
            while (true) {
                int i3 = i - i2;
                if (i3 < 0) {
                    return gF2Polynomial2;
                }
                gF2Polynomial2.subtractFromThis(gF2Polynomial3.shiftLeft(i3));
                gF2Polynomial2.reduceN();
                i = gF2Polynomial2.len;
                i2 = gF2Polynomial3.len;
            }
        } else {
            throw new RuntimeException();
        }
    }

    public void resetBit(int i) throws RuntimeException {
        if (i < 0) {
            throw new RuntimeException();
        } else if (i <= this.len - 1) {
            int[] iArr = this.value;
            int i2 = i >>> 5;
            iArr[i2] = (bitMask[i & 31] ^ -1) & iArr[i2];
        }
    }

    public void setBit(int i) throws RuntimeException {
        if (i < 0 || i > this.len - 1) {
            throw new RuntimeException();
        }
        int[] iArr = this.value;
        int i2 = i >>> 5;
        iArr[i2] = bitMask[i & 31] | iArr[i2];
    }

    /* access modifiers changed from: package-private */
    public void shiftBlocksLeft() {
        int i = this.blocks + 1;
        this.blocks = i;
        this.len += 32;
        int[] iArr = this.value;
        if (i <= iArr.length) {
            for (int i2 = i - 1; i2 >= 1; i2--) {
                int[] iArr2 = this.value;
                iArr2[i2] = iArr2[i2 - 1];
            }
            this.value[0] = 0;
            return;
        }
        int[] iArr3 = new int[i];
        System.arraycopy(iArr, 0, iArr3, 1, i - 1);
        this.value = null;
        this.value = iArr3;
    }

    public GF2Polynomial shiftLeft() {
        GF2Polynomial gF2Polynomial = new GF2Polynomial(this.len + 1, this.value);
        for (int i = gF2Polynomial.blocks - 1; i >= 1; i--) {
            int[] iArr = gF2Polynomial.value;
            int i2 = iArr[i] << 1;
            iArr[i] = i2;
            iArr[i] = i2 | (iArr[i - 1] >>> 31);
        }
        int[] iArr2 = gF2Polynomial.value;
        iArr2[0] = iArr2[0] << 1;
        return gF2Polynomial;
    }

    public GF2Polynomial shiftLeft(int i) {
        GF2Polynomial gF2Polynomial = new GF2Polynomial(this.len + i, this.value);
        if (i >= 32) {
            gF2Polynomial.doShiftBlocksLeft(i >>> 5);
        }
        int i2 = i & 31;
        if (i2 != 0) {
            for (int i3 = gF2Polynomial.blocks - 1; i3 >= 1; i3--) {
                int[] iArr = gF2Polynomial.value;
                int i4 = iArr[i3] << i2;
                iArr[i3] = i4;
                iArr[i3] = i4 | (iArr[i3 - 1] >>> (32 - i2));
            }
            int[] iArr2 = gF2Polynomial.value;
            iArr2[0] = iArr2[0] << i2;
        }
        return gF2Polynomial;
    }

    public void shiftLeftAddThis(GF2Polynomial gF2Polynomial, int i) {
        int i2;
        if (i == 0) {
            addToThis(gF2Polynomial);
            return;
        }
        expandN(gF2Polynomial.len + i);
        int i3 = i >>> 5;
        for (int i4 = gF2Polynomial.blocks - 1; i4 >= 0; i4--) {
            int i5 = i4 + i3;
            int i6 = i5 + 1;
            if (i6 < this.blocks && (i2 = i & 31) != 0) {
                int[] iArr = this.value;
                iArr[i6] = (gF2Polynomial.value[i4] >>> (32 - i2)) ^ iArr[i6];
            }
            int[] iArr2 = this.value;
            iArr2[i5] = iArr2[i5] ^ (gF2Polynomial.value[i4] << (i & 31));
        }
    }

    public void shiftLeftThis() {
        int i = this.len;
        int i2 = i & 31;
        this.len = i + 1;
        int i3 = this.blocks;
        if (i2 == 0) {
            int i4 = i3 + 1;
            this.blocks = i4;
            int[] iArr = this.value;
            if (i4 > iArr.length) {
                int[] iArr2 = new int[i4];
                System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
                this.value = null;
                this.value = iArr2;
            }
            for (int i5 = this.blocks - 1; i5 >= 1; i5--) {
                int[] iArr3 = this.value;
                int i6 = i5 - 1;
                iArr3[i5] = iArr3[i5] | (iArr3[i6] >>> 31);
                iArr3[i6] = iArr3[i6] << 1;
            }
            return;
        }
        for (int i7 = i3 - 1; i7 >= 1; i7--) {
            int[] iArr4 = this.value;
            int i8 = iArr4[i7] << 1;
            iArr4[i7] = i8;
            iArr4[i7] = i8 | (iArr4[i7 - 1] >>> 31);
        }
        int[] iArr5 = this.value;
        iArr5[0] = iArr5[0] << 1;
    }

    public GF2Polynomial shiftRight() {
        int i;
        GF2Polynomial gF2Polynomial = new GF2Polynomial(this.len - 1);
        int i2 = 0;
        System.arraycopy(this.value, 0, gF2Polynomial.value, 0, gF2Polynomial.blocks);
        while (true) {
            i = gF2Polynomial.blocks;
            if (i2 > i - 2) {
                break;
            }
            int[] iArr = gF2Polynomial.value;
            int i3 = iArr[i2] >>> 1;
            iArr[i2] = i3;
            int i4 = i2 + 1;
            iArr[i2] = i3 | (iArr[i4] << 31);
            i2 = i4;
        }
        int[] iArr2 = gF2Polynomial.value;
        int i5 = i - 1;
        iArr2[i5] = iArr2[i5] >>> 1;
        if (i < this.blocks) {
            int i6 = i - 1;
            iArr2[i6] = (this.value[i] << 31) | iArr2[i6];
        }
        return gF2Polynomial;
    }

    public void shiftRightThis() {
        int i;
        int i2 = this.len - 1;
        this.len = i2;
        this.blocks = ((i2 - 1) >>> 5) + 1;
        int i3 = 0;
        while (true) {
            i = this.blocks;
            if (i3 > i - 2) {
                break;
            }
            int[] iArr = this.value;
            int i4 = iArr[i3] >>> 1;
            iArr[i3] = i4;
            int i5 = i3 + 1;
            iArr[i3] = i4 | (iArr[i5] << 31);
            i3 = i5;
        }
        int[] iArr2 = this.value;
        int i6 = i - 1;
        iArr2[i6] = iArr2[i6] >>> 1;
        if ((this.len & 31) == 0) {
            int i7 = i - 1;
            iArr2[i7] = (iArr2[i] << 31) | iArr2[i7];
        }
    }

    public void squareThisBitwise() {
        if (!isZero()) {
            int i = this.blocks;
            int i2 = i << 1;
            int[] iArr = new int[i2];
            for (int i3 = i - 1; i3 >= 0; i3--) {
                int i4 = this.value[i3];
                int i5 = 1;
                for (int i6 = 0; i6 < 16; i6++) {
                    if ((i4 & 1) != 0) {
                        int i7 = i3 << 1;
                        iArr[i7] = iArr[i7] | i5;
                    }
                    if ((65536 & i4) != 0) {
                        int i8 = (i3 << 1) + 1;
                        iArr[i8] = iArr[i8] | i5;
                    }
                    i5 <<= 2;
                    i4 >>>= 1;
                }
            }
            this.value = null;
            this.value = iArr;
            this.blocks = i2;
            this.len = (this.len << 1) - 1;
        }
    }

    public void squareThisPreCalc() {
        int i;
        int i2;
        if (!isZero()) {
            int length = this.value.length;
            int i3 = this.blocks;
            if (length >= (i3 << 1)) {
                for (int i4 = i3 - 1; i4 >= 0; i4--) {
                    int[] iArr = this.value;
                    int i5 = i4 << 1;
                    short[] sArr = squaringTable;
                    int i6 = iArr[i4];
                    iArr[i5 + 1] = (sArr[(i6 & ViewCompat.MEASURED_STATE_MASK) >>> 24] << 16) | sArr[(i6 & 16711680) >>> 16];
                    int i7 = iArr[i4];
                    iArr[i5] = (sArr[(i7 & 65280) >>> 8] << 16) | sArr[i7 & 255];
                }
                i = this.blocks << 1;
            } else {
                int[] iArr2 = new int[(i3 << 1)];
                int i8 = 0;
                while (true) {
                    i2 = this.blocks;
                    if (i8 >= i2) {
                        break;
                    }
                    int i9 = i8 << 1;
                    short[] sArr2 = squaringTable;
                    int[] iArr3 = this.value;
                    int i10 = iArr3[i8];
                    iArr2[i9] = (sArr2[(i10 & 65280) >>> 8] << 16) | sArr2[i10 & 255];
                    int i11 = iArr3[i8];
                    iArr2[i9 + 1] = (sArr2[(i11 & ViewCompat.MEASURED_STATE_MASK) >>> 24] << 16) | sArr2[(i11 & 16711680) >>> 16];
                    i8++;
                }
                this.value = null;
                this.value = iArr2;
                i = i2 << 1;
            }
            this.blocks = i;
            this.len = (this.len << 1) - 1;
        }
    }

    public GF2Polynomial subtract(GF2Polynomial gF2Polynomial) {
        return xor(gF2Polynomial);
    }

    public void subtractFromThis(GF2Polynomial gF2Polynomial) {
        expandN(gF2Polynomial.len);
        xorThisBy(gF2Polynomial);
    }

    public boolean testBit(int i) {
        if (i < 0) {
            throw new RuntimeException();
        } else if (i > this.len - 1) {
            return false;
        } else {
            return (bitMask[i & 31] & this.value[i >>> 5]) != 0;
        }
    }

    public byte[] toByteArray() {
        int i = ((this.len - 1) >> 3) + 1;
        int i2 = i & 3;
        byte[] bArr = new byte[i];
        for (int i3 = 0; i3 < (i >> 2); i3++) {
            int i4 = (i - (i3 << 2)) - 1;
            int i5 = this.value[i3];
            bArr[i4] = (byte) (i5 & 255);
            bArr[i4 - 1] = (byte) ((65280 & i5) >>> 8);
            bArr[i4 - 2] = (byte) ((16711680 & i5) >>> 16);
            bArr[i4 - 3] = (byte) ((i5 & ViewCompat.MEASURED_STATE_MASK) >>> 24);
        }
        for (int i6 = 0; i6 < i2; i6++) {
            int i7 = ((i2 - i6) - 1) << 3;
            bArr[i6] = (byte) ((this.value[this.blocks - 1] & (255 << i7)) >>> i7);
        }
        return bArr;
    }

    public BigInteger toFlexiBigInt() {
        return (this.len == 0 || isZero()) ? new BigInteger(0, new byte[0]) : new BigInteger(1, toByteArray());
    }

    public int[] toIntegerArray() {
        int i = this.blocks;
        int[] iArr = new int[i];
        System.arraycopy(this.value, 0, iArr, 0, i);
        return iArr;
    }

    public String toString(int i) {
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', Barcode128.CODE_AB_TO_C, Barcode128.CODE_AC_TO_B, Barcode128.CODE_BC_TO_A, Barcode128.FNC1_INDEX};
        String[] strArr = {"0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"};
        String str = new String();
        if (i == 16) {
            for (int i2 = this.blocks - 1; i2 >= 0; i2--) {
                str = ((((((((str + cArr[(this.value[i2] >>> 28) & 15]) + cArr[(this.value[i2] >>> 24) & 15]) + cArr[(this.value[i2] >>> 20) & 15]) + cArr[(this.value[i2] >>> 16) & 15]) + cArr[(this.value[i2] >>> 12) & 15]) + cArr[(this.value[i2] >>> 8) & 15]) + cArr[(this.value[i2] >>> 4) & 15]) + cArr[this.value[i2] & 15]) + " ";
            }
        } else {
            for (int i3 = this.blocks - 1; i3 >= 0; i3--) {
                str = ((((((((str + strArr[(this.value[i3] >>> 28) & 15]) + strArr[(this.value[i3] >>> 24) & 15]) + strArr[(this.value[i3] >>> 20) & 15]) + strArr[(this.value[i3] >>> 16) & 15]) + strArr[(this.value[i3] >>> 12) & 15]) + strArr[(this.value[i3] >>> 8) & 15]) + strArr[(this.value[i3] >>> 4) & 15]) + strArr[this.value[i3] & 15]) + " ";
            }
        }
        return str;
    }

    public boolean vectorMult(GF2Polynomial gF2Polynomial) throws RuntimeException {
        if (this.len == gF2Polynomial.len) {
            boolean z = false;
            for (int i = 0; i < this.blocks; i++) {
                int i2 = this.value[i] & gF2Polynomial.value[i];
                boolean[] zArr = parity;
                z = (((z ^ zArr[i2 & 255]) ^ zArr[(i2 >>> 8) & 255]) ^ zArr[(i2 >>> 16) & 255]) ^ zArr[(i2 >>> 24) & 255];
            }
            return z;
        }
        throw new RuntimeException();
    }

    public GF2Polynomial xor(GF2Polynomial gF2Polynomial) {
        GF2Polynomial gF2Polynomial2;
        int min = Math.min(this.blocks, gF2Polynomial.blocks);
        int i = 0;
        if (this.len >= gF2Polynomial.len) {
            gF2Polynomial2 = new GF2Polynomial(this);
            while (i < min) {
                int[] iArr = gF2Polynomial2.value;
                iArr[i] = iArr[i] ^ gF2Polynomial.value[i];
                i++;
            }
        } else {
            gF2Polynomial2 = new GF2Polynomial(gF2Polynomial);
            while (i < min) {
                int[] iArr2 = gF2Polynomial2.value;
                iArr2[i] = iArr2[i] ^ this.value[i];
                i++;
            }
        }
        gF2Polynomial2.zeroUnusedBits();
        return gF2Polynomial2;
    }

    public void xorBit(int i) throws RuntimeException {
        if (i < 0 || i > this.len - 1) {
            throw new RuntimeException();
        }
        int[] iArr = this.value;
        int i2 = i >>> 5;
        iArr[i2] = bitMask[i & 31] ^ iArr[i2];
    }

    public void xorThisBy(GF2Polynomial gF2Polynomial) {
        for (int i = 0; i < Math.min(this.blocks, gF2Polynomial.blocks); i++) {
            int[] iArr = this.value;
            iArr[i] = iArr[i] ^ gF2Polynomial.value[i];
        }
        zeroUnusedBits();
    }
}
