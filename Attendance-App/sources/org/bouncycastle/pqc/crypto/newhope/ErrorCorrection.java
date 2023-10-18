package org.bouncycastle.pqc.crypto.newhope;

import com.google.android.material.internal.ViewUtils;
import org.bouncycastle.crypto.tls.CipherSuite;
import org.bouncycastle.util.Arrays;

class ErrorCorrection {
    ErrorCorrection() {
    }

    static short LDDecode(int i, int i2, int i3, int i4) {
        return (short) (((((m137g(i) + m137g(i2)) + m137g(i3)) + m137g(i4)) - 98312) >>> 31);
    }

    static int abs(int i) {
        int i2 = i >> 31;
        return (i ^ i2) - i2;
    }

    /* renamed from: f */
    static int m136f(int[] iArr, int i, int i2, int i3) {
        int i4 = (i3 * 2730) >> 25;
        int i5 = i4 - ((12288 - (i3 - (i4 * 12289))) >> 31);
        iArr[i] = (i5 >> 1) + (i5 & 1);
        int i6 = i5 - 1;
        iArr[i2] = (i6 >> 1) + (i6 & 1);
        return abs(i3 - ((iArr[i] * 2) * 12289));
    }

    /* renamed from: g */
    static int m137g(int i) {
        int i2 = (i * 2730) >> 27;
        int i3 = i2 - ((CipherSuite.TLS_ECDH_ECDSA_WITH_3DES_EDE_CBC_SHA - (i - (CipherSuite.TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA * i2))) >> 31);
        return abs((((i3 >> 1) + (i3 & 1)) * 98312) - i);
    }

    static void helpRec(short[] sArr, short[] sArr2, byte[] bArr, byte b) {
        byte[] bArr2 = new byte[8];
        bArr2[0] = b;
        byte[] bArr3 = new byte[32];
        ChaCha20.process(bArr, bArr2, bArr3, 0, 32);
        int[] iArr = new int[8];
        int[] iArr2 = new int[4];
        for (int i = 0; i < 256; i++) {
            int i2 = i + 0;
            int i3 = ((bArr3[i >>> 3] >>> (i & 7)) & 1) * 4;
            int i4 = i + 256;
            int i5 = i + 512;
            int f = m136f(iArr, 0, 4, (sArr2[i2] * 8) + i3) + m136f(iArr, 1, 5, (sArr2[i4] * 8) + i3) + m136f(iArr, 2, 6, (sArr2[i5] * 8) + i3);
            int i6 = i + ViewUtils.EDGE_TO_EDGE_FLAGS;
            int f2 = (24577 - (f + m136f(iArr, 3, 7, (sArr2[i6] * 8) + i3))) >> 31;
            int i7 = f2 ^ -1;
            int i8 = (i7 & iArr[0]) ^ (f2 & iArr[4]);
            iArr2[0] = i8;
            int i9 = (i7 & iArr[1]) ^ (f2 & iArr[5]);
            iArr2[1] = i9;
            int i10 = (iArr[2] & i7) ^ (f2 & iArr[6]);
            iArr2[2] = i10;
            int i11 = (i7 & iArr[3]) ^ (iArr[7] & f2);
            iArr2[3] = i11;
            sArr[i2] = (short) ((i8 - i11) & 3);
            sArr[i4] = (short) ((i9 - i11) & 3);
            sArr[i5] = (short) ((i10 - i11) & 3);
            sArr[i6] = (short) (3 & ((-f2) + (i11 * 2)));
        }
    }

    static void rec(byte[] bArr, short[] sArr, short[] sArr2) {
        Arrays.fill(bArr, (byte) 0);
        int[] iArr = new int[4];
        for (int i = 0; i < 256; i++) {
            int i2 = i + 0;
            int i3 = i + ViewUtils.EDGE_TO_EDGE_FLAGS;
            short s = sArr2[i3];
            int i4 = ((sArr[i2] * 8) + 196624) - (((sArr2[i2] * 2) + s) * 12289);
            iArr[0] = i4;
            int i5 = i + 256;
            int i6 = ((sArr[i5] * 8) + 196624) - (((sArr2[i5] * 2) + s) * 12289);
            iArr[1] = i6;
            int i7 = i + 512;
            int i8 = ((sArr[i7] * 8) + 196624) - (((sArr2[i7] * 2) + s) * 12289);
            iArr[2] = i8;
            int i9 = ((sArr[i3] * 8) + 196624) - (s * 12289);
            iArr[3] = i9;
            int i10 = i >>> 3;
            bArr[i10] = (byte) ((LDDecode(i4, i6, i8, i9) << (i & 7)) | bArr[i10]);
        }
    }
}
