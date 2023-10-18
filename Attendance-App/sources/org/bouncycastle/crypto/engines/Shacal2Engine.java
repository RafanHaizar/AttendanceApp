package org.bouncycastle.crypto.engines;

import kotlin.UByte;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;

public class Shacal2Engine implements BlockCipher {
    private static final int BLOCK_SIZE = 32;

    /* renamed from: K */
    private static final int[] f423K = {1116352408, 1899447441, -1245643825, -373957723, 961987163, 1508970993, -1841331548, -1424204075, -670586216, 310598401, 607225278, 1426881987, 1925078388, -2132889090, -1680079193, -1046744716, -459576895, -272742522, 264347078, 604807628, 770255983, 1249150122, 1555081692, 1996064986, -1740746414, -1473132947, -1341970488, -1084653625, -958395405, -710438585, 113926993, 338241895, 666307205, 773529912, 1294757372, 1396182291, 1695183700, 1986661051, -2117940946, -1838011259, -1564481375, -1474664885, -1035236496, -949202525, -778901479, -694614492, -200395387, 275423344, 430227734, 506948616, 659060556, 883997877, 958139571, 1322822218, 1537002063, 1747873779, 1955562222, 2024104815, -2067236844, -1933114872, -1866530822, -1538233109, -1090935817, -965641998};
    private static final int ROUNDS = 64;
    private boolean forEncryption = false;
    private int[] workingKey = null;

    private void byteBlockToInts(byte[] bArr, int[] iArr, int i, int i2) {
        while (i2 < 8) {
            int i3 = i + 1;
            int i4 = i3 + 1;
            byte b = ((bArr[i] & UByte.MAX_VALUE) << 24) | ((bArr[i3] & UByte.MAX_VALUE) << Tnaf.POW_2_WIDTH);
            int i5 = i4 + 1;
            iArr[i2] = b | ((bArr[i4] & UByte.MAX_VALUE) << 8) | (bArr[i5] & UByte.MAX_VALUE);
            i2++;
            i = i5 + 1;
        }
    }

    private void bytes2ints(byte[] bArr, int[] iArr, int i, int i2) {
        while (i2 < bArr.length / 4) {
            int i3 = i + 1;
            int i4 = i3 + 1;
            byte b = ((bArr[i] & UByte.MAX_VALUE) << 24) | ((bArr[i3] & UByte.MAX_VALUE) << Tnaf.POW_2_WIDTH);
            int i5 = i4 + 1;
            byte b2 = b | ((bArr[i4] & UByte.MAX_VALUE) << 8);
            iArr[i2] = b2 | (bArr[i5] & UByte.MAX_VALUE);
            i2++;
            i = i5 + 1;
        }
    }

    private void decryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        int[] iArr = new int[8];
        byteBlockToInts(bArr, iArr, i, 0);
        for (int i3 = 63; i3 > -1; i3--) {
            int i4 = iArr[0];
            int i5 = iArr[1];
            int i6 = iArr[2];
            int i7 = iArr[3];
            int i8 = (i4 - ((((i5 >>> 2) | (i5 << -2)) ^ ((i5 >>> 13) | (i5 << -13))) ^ ((i5 >>> 22) | (i5 << -22)))) - (((i5 & i6) ^ (i5 & i7)) ^ (i6 & i7));
            iArr[0] = i5;
            iArr[1] = i6;
            iArr[2] = i7;
            iArr[3] = iArr[4] - i8;
            int i9 = iArr[5];
            iArr[4] = i9;
            int i10 = iArr[6];
            iArr[5] = i10;
            int i11 = iArr[7];
            iArr[6] = i11;
            iArr[7] = (((i8 - f423K[i3]) - this.workingKey[i3]) - ((((i9 >>> 6) | (i9 << -6)) ^ ((i9 >>> 11) | (i9 << -11))) ^ ((i9 >>> 25) | (i9 << -25)))) - (((-1 ^ i9) & i11) ^ (i9 & i10));
        }
        ints2bytes(iArr, bArr2, i2);
    }

    private void encryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        int[] iArr = new int[8];
        byteBlockToInts(bArr, iArr, i, 0);
        for (int i3 = 0; i3 < 64; i3++) {
            int i4 = iArr[4];
            int i5 = iArr[5];
            int i6 = iArr[6];
            int i7 = ((((i4 >>> 6) | (i4 << -6)) ^ ((i4 >>> 11) | (i4 << -11))) ^ ((i4 >>> 25) | (i4 << -25))) + ((i4 & i5) ^ ((i4 ^ -1) & i6)) + iArr[7] + f423K[i3] + this.workingKey[i3];
            iArr[7] = i6;
            iArr[6] = i5;
            iArr[5] = i4;
            iArr[4] = iArr[3] + i7;
            int i8 = iArr[2];
            iArr[3] = i8;
            int i9 = iArr[1];
            iArr[2] = i9;
            int i10 = iArr[0];
            iArr[1] = i10;
            iArr[0] = i7 + ((((i10 >>> 2) | (i10 << -2)) ^ ((i10 >>> 13) | (i10 << -13))) ^ ((i10 >>> 22) | (i10 << -22))) + (((i10 & i8) ^ (i10 & i9)) ^ (i9 & i8));
        }
        ints2bytes(iArr, bArr2, i2);
    }

    private void ints2bytes(int[] iArr, byte[] bArr, int i) {
        for (int i2 : iArr) {
            int i3 = i + 1;
            bArr[i] = (byte) (i2 >>> 24);
            int i4 = i3 + 1;
            bArr[i3] = (byte) (i2 >>> 16);
            int i5 = i4 + 1;
            bArr[i4] = (byte) (i2 >>> 8);
            i = i5 + 1;
            bArr[i5] = (byte) i2;
        }
    }

    public String getAlgorithmName() {
        return "Shacal2";
    }

    public int getBlockSize() {
        return 32;
    }

    public void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException {
        if (cipherParameters instanceof KeyParameter) {
            this.forEncryption = z;
            this.workingKey = new int[64];
            setKey(((KeyParameter) cipherParameters).getKey());
            return;
        }
        throw new IllegalArgumentException("only simple KeyParameter expected.");
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) throws DataLengthException, IllegalStateException {
        if (this.workingKey == null) {
            throw new IllegalStateException("Shacal2 not initialised");
        } else if (i + 32 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i2 + 32 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        } else if (this.forEncryption) {
            encryptBlock(bArr, i, bArr2, i2);
            return 32;
        } else {
            decryptBlock(bArr, i, bArr2, i2);
            return 32;
        }
    }

    public void reset() {
    }

    public void setKey(byte[] bArr) {
        if (bArr.length != 0 && bArr.length <= 64) {
            if (bArr.length >= 16 && bArr.length % 8 == 0) {
                bytes2ints(bArr, this.workingKey, 0, 0);
                for (int i = 16; i < 64; i++) {
                    int[] iArr = this.workingKey;
                    int i2 = iArr[i - 2];
                    int i3 = ((i2 >>> 10) ^ (((i2 >>> 17) | (i2 << -17)) ^ ((i2 >>> 19) | (i2 << -19)))) + iArr[i - 7];
                    int i4 = iArr[i - 15];
                    iArr[i] = i3 + ((i4 >>> 3) ^ (((i4 >>> 7) | (i4 << -7)) ^ ((i4 >>> 18) | (i4 << -18)))) + iArr[i - 16];
                }
                return;
            }
        }
        throw new IllegalArgumentException("Shacal2-key must be 16 - 64 bytes and multiple of 8");
    }
}
