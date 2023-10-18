package org.bouncycastle.pqc.crypto.sphincs;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.android.material.internal.ViewUtils;
import kotlin.UByte;

class Horst {
    static final int HORST_K = 32;
    static final int HORST_LOGT = 16;
    static final int HORST_SIGBYTES = 13312;
    static final int HORST_SKBYTES = 32;
    static final int HORST_T = 65536;
    static final int N_MASKS = 32;

    Horst() {
    }

    static void expand_seed(byte[] bArr, byte[] bArr2) {
        Seed.prg(bArr, 0, 2097152, bArr2, 0);
    }

    static int horst_sign(HashFunctions hashFunctions, byte[] bArr, int i, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[] bArr5) {
        byte[] bArr6 = new byte[2097152];
        byte[] bArr7 = new byte[4194272];
        expand_seed(bArr6, bArr3);
        for (int i2 = 0; i2 < 65536; i2++) {
            hashFunctions.hash_n_n(bArr7, (65535 + i2) * 32, bArr6, i2 * 32);
        }
        HashFunctions hashFunctions2 = hashFunctions;
        for (int i3 = 0; i3 < 16; i3++) {
            int i4 = 16 - i3;
            long j = (long) ((1 << i4) - 1);
            int i5 = 1 << (i4 - 1);
            long j2 = (long) (i5 - 1);
            int i6 = 0;
            while (i6 < i5) {
                hashFunctions.hash_2n_n_mask(bArr7, (int) ((((long) i6) + j2) * 32), bArr7, (int) ((((long) (i6 * 2)) + j) * 32), bArr4, i3 * 2 * 32);
                i6++;
                i5 = i5;
                j2 = j2;
            }
        }
        int i7 = i;
        int i8 = 2016;
        while (i8 < 4064) {
            bArr[i7] = bArr7[i8];
            i8++;
            i7++;
        }
        for (int i9 = 0; i9 < 32; i9++) {
            int i10 = i9 * 2;
            int i11 = (bArr5[i10] & UByte.MAX_VALUE) + ((bArr5[i10 + 1] & UByte.MAX_VALUE) << 8);
            int i12 = 0;
            while (i12 < 32) {
                bArr[i7] = bArr6[(i11 * 32) + i12];
                i12++;
                i7++;
            }
            int i13 = i11 + 65535;
            for (int i14 = 0; i14 < 10; i14++) {
                int i15 = (i13 & 1) != 0 ? i13 + 1 : i13 - 1;
                int i16 = 0;
                while (i16 < 32) {
                    bArr[i7] = bArr7[(i15 * 32) + i16];
                    i16++;
                    i7++;
                }
                i13 = (i15 - 1) / 2;
            }
        }
        for (int i17 = 0; i17 < 32; i17++) {
            bArr2[i17] = bArr7[i17];
        }
        return HORST_SIGBYTES;
    }

    static int horst_verify(HashFunctions hashFunctions, byte[] bArr, byte[] bArr2, int i, byte[] bArr3, byte[] bArr4) {
        int i2;
        HashFunctions hashFunctions2 = hashFunctions;
        byte[] bArr5 = bArr2;
        int i3 = i;
        byte[] bArr6 = new byte[1024];
        int i4 = i3 + 2048;
        int i5 = 0;
        while (i5 < 32) {
            int i6 = i5 * 2;
            int i7 = (bArr4[i6] & UByte.MAX_VALUE) + ((bArr4[i6 + 1] & UByte.MAX_VALUE) << 8);
            if ((i7 & 1) == 0) {
                hashFunctions2.hash_n_n(bArr6, 0, bArr5, i4);
                for (int i8 = 0; i8 < 32; i8++) {
                    bArr6[i8 + 32] = bArr5[i4 + 32 + i8];
                }
            } else {
                hashFunctions2.hash_n_n(bArr6, 32, bArr5, i4);
                for (int i9 = 0; i9 < 32; i9++) {
                    bArr6[i9] = bArr5[i4 + 32 + i9];
                }
            }
            int i10 = i4 + 64;
            int i11 = 1;
            while (i11 < 10) {
                int i12 = i7 >>> 1;
                if ((i12 & 1) == 0) {
                    i2 = i11;
                    hashFunctions.hash_2n_n_mask(bArr6, 0, bArr6, 0, bArr3, (i11 - 1) * 2 * 32);
                    for (int i13 = 0; i13 < 32; i13++) {
                        bArr6[i13 + 32] = bArr5[i10 + i13];
                    }
                } else {
                    i2 = i11;
                    hashFunctions.hash_2n_n_mask(bArr6, 32, bArr6, 0, bArr3, (i2 - 1) * 2 * 32);
                    for (int i14 = 0; i14 < 32; i14++) {
                        bArr6[i14] = bArr5[i10 + i14];
                    }
                }
                i10 += 32;
                i11 = i2 + 1;
                i7 = i12;
            }
            int i15 = i7 >>> 1;
            hashFunctions.hash_2n_n_mask(bArr6, 0, bArr6, 0, bArr3, 576);
            for (int i16 = 0; i16 < 32; i16++) {
                if (bArr5[(i15 * 32) + i3 + i16] != bArr6[i16]) {
                    for (int i17 = 0; i17 < 32; i17++) {
                        bArr[i17] = 0;
                    }
                    return -1;
                }
            }
            i5++;
            i4 = i10;
        }
        for (int i18 = 0; i18 < 32; i18++) {
            HashFunctions hashFunctions3 = hashFunctions;
            byte[] bArr7 = bArr6;
            hashFunctions3.hash_2n_n_mask(bArr7, i18 * 32, bArr2, i3 + (i18 * 2 * 32), bArr3, 640);
        }
        for (int i19 = 0; i19 < 16; i19++) {
            HashFunctions hashFunctions4 = hashFunctions;
            byte[] bArr8 = bArr6;
            hashFunctions4.hash_2n_n_mask(bArr8, i19 * 32, bArr6, i19 * 2 * 32, bArr3, TypedValues.TransitionType.TYPE_AUTO_TRANSITION);
        }
        for (int i20 = 0; i20 < 8; i20++) {
            HashFunctions hashFunctions5 = hashFunctions;
            byte[] bArr9 = bArr6;
            hashFunctions5.hash_2n_n_mask(bArr9, i20 * 32, bArr6, i20 * 2 * 32, bArr3, ViewUtils.EDGE_TO_EDGE_FLAGS);
        }
        for (int i21 = 0; i21 < 4; i21++) {
            HashFunctions hashFunctions6 = hashFunctions;
            byte[] bArr10 = bArr6;
            hashFunctions6.hash_2n_n_mask(bArr10, i21 * 32, bArr6, i21 * 2 * 32, bArr3, 832);
        }
        for (int i22 = 0; i22 < 2; i22++) {
            HashFunctions hashFunctions7 = hashFunctions;
            byte[] bArr11 = bArr6;
            hashFunctions7.hash_2n_n_mask(bArr11, i22 * 32, bArr6, i22 * 2 * 32, bArr3, 896);
        }
        hashFunctions.hash_2n_n_mask(bArr, 0, bArr6, 0, bArr3, 960);
        return 0;
    }
}
