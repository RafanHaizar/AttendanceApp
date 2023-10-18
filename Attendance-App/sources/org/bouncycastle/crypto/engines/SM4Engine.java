package org.bouncycastle.crypto.engines;

import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.signers.PSSSigner;
import org.bouncycastle.util.Pack;

public class SM4Engine implements BlockCipher {
    private static final int BLOCK_SIZE = 16;

    /* renamed from: CK */
    private static final int[] f414CK = {462357, 472066609, 943670861, 1415275113, 1886879365, -1936483679, -1464879427, -993275175, -521670923, -66909679, 404694573, 876298825, 1347903077, 1819507329, -2003855715, -1532251463, -1060647211, -589042959, -117504499, 337322537, 808926789, 1280531041, 1752135293, -2071227751, -1599623499, -1128019247, -656414995, -184876535, 269950501, 741554753, 1213159005, 1684763257};

    /* renamed from: FK */
    private static final int[] f415FK = {-1548633402, 1453994832, 1736282519, -1301273892};
    private static final byte[] Sbox = {-42, -112, -23, -2, -52, -31, 61, -73, 22, -74, 20, -62, 40, -5, 44, 5, 43, 103, -102, 118, 42, -66, 4, -61, -86, 68, 19, 38, 73, -122, 6, -103, -100, 66, 80, -12, -111, -17, -104, 122, 51, 84, 11, 67, -19, -49, -84, 98, -28, -77, 28, -87, -55, 8, -24, -107, ByteCompanionObject.MIN_VALUE, -33, -108, -6, 117, -113, 63, -90, 71, 7, -89, -4, -13, 115, 23, -70, -125, 89, 60, 25, -26, -123, 79, -88, 104, 107, -127, -78, 113, 100, -38, -117, -8, -21, 15, 75, 112, 86, -99, 53, 30, 36, 14, 94, 99, 88, -47, -94, 37, 34, 124, 59, 1, 33, 120, -121, -44, 0, 70, 87, -97, -45, 39, 82, 76, 54, 2, -25, -96, -60, -56, -98, -22, -65, -118, -46, 64, -57, 56, -75, -93, -9, -14, -50, -7, 97, 21, -95, -32, -82, 93, -92, -101, 52, 26, 85, -83, -109, 50, 48, -11, -116, -79, -29, 29, -10, -30, 46, -126, 102, -54, 96, -64, 41, 35, -85, 13, 83, 78, 111, -43, -37, 55, 69, -34, -3, -114, 47, 3, -1, 106, 114, 109, 108, 91, 81, -115, 27, -81, -110, -69, -35, PSSSigner.TRAILER_IMPLICIT, ByteCompanionObject.MAX_VALUE, 17, -39, 92, 65, 31, Tnaf.POW_2_WIDTH, 90, -40, 10, -63, 49, -120, -91, -51, 123, -67, 45, 116, -48, 18, -72, -27, -76, -80, -119, 105, -105, 74, 12, -106, 119, 126, 101, -71, -15, 9, -59, 110, -58, -124, 24, -16, 125, -20, 58, -36, 77, 32, 121, -18, 95, 62, -41, -53, 57, 72};

    /* renamed from: X */
    private final int[] f416X = new int[4];

    /* renamed from: rk */
    private int[] f417rk;

    /* renamed from: F0 */
    private int m105F0(int[] iArr, int i) {
        return m110T((iArr[3] ^ (iArr[1] ^ iArr[2])) ^ i) ^ iArr[0];
    }

    /* renamed from: F1 */
    private int m106F1(int[] iArr, int i) {
        return m110T((iArr[0] ^ (iArr[2] ^ iArr[3])) ^ i) ^ iArr[1];
    }

    /* renamed from: F2 */
    private int m107F2(int[] iArr, int i) {
        return m110T((iArr[1] ^ (iArr[3] ^ iArr[0])) ^ i) ^ iArr[2];
    }

    /* renamed from: F3 */
    private int m108F3(int[] iArr, int i) {
        return m110T((iArr[2] ^ (iArr[0] ^ iArr[1])) ^ i) ^ iArr[3];
    }

    /* renamed from: L */
    private int m109L(int i) {
        return rotateLeft(i, 24) ^ (((rotateLeft(i, 2) ^ i) ^ rotateLeft(i, 10)) ^ rotateLeft(i, 18));
    }

    private int L_ap(int i) {
        return rotateLeft(i, 23) ^ (rotateLeft(i, 13) ^ i);
    }

    /* renamed from: T */
    private int m110T(int i) {
        return m109L(tau(i));
    }

    private int T_ap(int i) {
        return L_ap(tau(i));
    }

    private int[] expandKey(boolean z, byte[] bArr) {
        int[] iArr = new int[32];
        int bigEndianToInt = Pack.bigEndianToInt(bArr, 12);
        int[] iArr2 = {Pack.bigEndianToInt(bArr, 0), Pack.bigEndianToInt(bArr, 4), Pack.bigEndianToInt(bArr, 8), bigEndianToInt};
        int i = iArr2[0];
        int[] iArr3 = f415FK;
        int i2 = i ^ iArr3[0];
        int i3 = iArr2[1] ^ iArr3[1];
        int i4 = iArr2[2] ^ iArr3[2];
        int i5 = bigEndianToInt ^ iArr3[3];
        int[] iArr4 = {i2, i3, i4, i5};
        if (z) {
            int i6 = (i3 ^ i4) ^ i5;
            int[] iArr5 = f414CK;
            int T_ap = T_ap(i6 ^ iArr5[0]) ^ i2;
            iArr[0] = T_ap;
            int T_ap2 = T_ap((T_ap ^ (iArr4[2] ^ iArr4[3])) ^ iArr5[1]) ^ iArr4[1];
            iArr[1] = T_ap2;
            int T_ap3 = T_ap((T_ap2 ^ (iArr4[3] ^ iArr[0])) ^ iArr5[2]) ^ iArr4[2];
            iArr[2] = T_ap3;
            iArr[3] = T_ap((T_ap3 ^ (iArr[0] ^ iArr[1])) ^ iArr5[3]) ^ iArr4[3];
            for (int i7 = 4; i7 < 32; i7++) {
                iArr[i7] = iArr[i7 - 4] ^ T_ap(((iArr[i7 - 3] ^ iArr[i7 - 2]) ^ iArr[i7 - 1]) ^ f414CK[i7]);
            }
        } else {
            int i8 = (i3 ^ i4) ^ i5;
            int[] iArr6 = f414CK;
            int T_ap4 = T_ap(i8 ^ iArr6[0]) ^ i2;
            iArr[31] = T_ap4;
            int T_ap5 = T_ap((T_ap4 ^ (iArr4[2] ^ iArr4[3])) ^ iArr6[1]) ^ iArr4[1];
            iArr[30] = T_ap5;
            int T_ap6 = T_ap((T_ap5 ^ (iArr4[3] ^ iArr[31])) ^ iArr6[2]) ^ iArr4[2];
            iArr[29] = T_ap6;
            iArr[28] = T_ap((T_ap6 ^ (iArr[31] ^ iArr[30])) ^ iArr6[3]) ^ iArr4[3];
            for (int i9 = 27; i9 >= 0; i9--) {
                iArr[i9] = iArr[i9 + 4] ^ T_ap(((iArr[i9 + 3] ^ iArr[i9 + 2]) ^ iArr[i9 + 1]) ^ f414CK[31 - i9]);
            }
        }
        return iArr;
    }

    private int rotateLeft(int i, int i2) {
        return (i >>> (-i2)) | (i << i2);
    }

    private int tau(int i) {
        byte[] bArr = Sbox;
        return (bArr[i & 255] & UByte.MAX_VALUE) | ((bArr[(i >> 24) & 255] & UByte.MAX_VALUE) << 24) | ((bArr[(i >> 16) & 255] & UByte.MAX_VALUE) << Tnaf.POW_2_WIDTH) | ((bArr[(i >> 8) & 255] & UByte.MAX_VALUE) << 8);
    }

    public String getAlgorithmName() {
        return "SM4";
    }

    public int getBlockSize() {
        return 16;
    }

    public void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException {
        if (cipherParameters instanceof KeyParameter) {
            byte[] key = ((KeyParameter) cipherParameters).getKey();
            if (key.length == 16) {
                this.f417rk = expandKey(z, key);
                return;
            }
            throw new IllegalArgumentException("SM4 requires a 128 bit key");
        }
        throw new IllegalArgumentException("invalid parameter passed to SM4 init - " + cipherParameters.getClass().getName());
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) throws DataLengthException, IllegalStateException {
        if (this.f417rk == null) {
            throw new IllegalStateException("SM4 not initialised");
        } else if (i + 16 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i2 + 16 <= bArr2.length) {
            this.f416X[0] = Pack.bigEndianToInt(bArr, i);
            this.f416X[1] = Pack.bigEndianToInt(bArr, i + 4);
            this.f416X[2] = Pack.bigEndianToInt(bArr, i + 8);
            this.f416X[3] = Pack.bigEndianToInt(bArr, i + 12);
            for (int i3 = 0; i3 < 32; i3 += 4) {
                int[] iArr = this.f416X;
                iArr[0] = m105F0(iArr, this.f417rk[i3]);
                int[] iArr2 = this.f416X;
                iArr2[1] = m106F1(iArr2, this.f417rk[i3 + 1]);
                int[] iArr3 = this.f416X;
                iArr3[2] = m107F2(iArr3, this.f417rk[i3 + 2]);
                int[] iArr4 = this.f416X;
                iArr4[3] = m108F3(iArr4, this.f417rk[i3 + 3]);
            }
            Pack.intToBigEndian(this.f416X[3], bArr2, i2);
            Pack.intToBigEndian(this.f416X[2], bArr2, i2 + 4);
            Pack.intToBigEndian(this.f416X[1], bArr2, i2 + 8);
            Pack.intToBigEndian(this.f416X[0], bArr2, i2 + 12);
            return 16;
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    public void reset() {
    }
}
