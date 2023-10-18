package org.bouncycastle.crypto.engines;

import kotlin.UByte;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;

public class RC6Engine implements BlockCipher {
    private static final int LGW = 5;
    private static final int P32 = -1209970333;
    private static final int Q32 = -1640531527;
    private static final int _noRounds = 20;
    private static final int bytesPerWord = 4;
    private static final int wordSize = 32;

    /* renamed from: _S */
    private int[] f404_S = null;
    private boolean forEncryption;

    private int bytesToWord(byte[] bArr, int i) {
        int i2 = 0;
        for (int i3 = 3; i3 >= 0; i3--) {
            i2 = (i2 << 8) + (bArr[i3 + i] & UByte.MAX_VALUE);
        }
        return i2;
    }

    private int decryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        int bytesToWord = bytesToWord(bArr, i);
        int bytesToWord2 = bytesToWord(bArr, i + 4);
        int bytesToWord3 = bytesToWord(bArr, i + 8);
        int bytesToWord4 = bytesToWord(bArr, i + 12);
        int[] iArr = this.f404_S;
        int i3 = bytesToWord3 - iArr[43];
        int i4 = bytesToWord - iArr[42];
        int i5 = 20;
        while (i5 >= 1) {
            int rotateLeft = rotateLeft(((i4 * 2) + 1) * i4, 5);
            int rotateLeft2 = rotateLeft(((i3 * 2) + 1) * i3, 5);
            int i6 = i5 * 2;
            i5--;
            int i7 = i4;
            i4 = rotateRight(bytesToWord4 - this.f404_S[i6], rotateLeft2) ^ rotateLeft;
            bytesToWord4 = i3;
            i3 = rotateRight(bytesToWord2 - this.f404_S[i6 + 1], rotateLeft) ^ rotateLeft2;
            bytesToWord2 = i7;
        }
        int[] iArr2 = this.f404_S;
        wordToBytes(i4, bArr2, i2);
        wordToBytes(bytesToWord2 - iArr2[0], bArr2, i2 + 4);
        wordToBytes(i3, bArr2, i2 + 8);
        wordToBytes(bytesToWord4 - iArr2[1], bArr2, i2 + 12);
        return 16;
    }

    private int encryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        int bytesToWord = bytesToWord(bArr, i);
        int bytesToWord2 = bytesToWord(bArr, i + 4);
        int bytesToWord3 = bytesToWord(bArr, i + 8);
        int bytesToWord4 = bytesToWord(bArr, i + 12);
        int[] iArr = this.f404_S;
        int i3 = bytesToWord2 + iArr[0];
        int i4 = bytesToWord4 + iArr[1];
        int i5 = 1;
        while (i5 <= 20) {
            int rotateLeft = rotateLeft(((i3 * 2) + 1) * i3, 5);
            int rotateLeft2 = rotateLeft(((i4 * 2) + 1) * i4, 5);
            int i6 = i5 * 2;
            i5++;
            int rotateLeft3 = rotateLeft(bytesToWord3 ^ rotateLeft2, rotateLeft) + this.f404_S[i6 + 1];
            bytesToWord3 = i4;
            i4 = rotateLeft(bytesToWord ^ rotateLeft, rotateLeft2) + this.f404_S[i6];
            bytesToWord = i3;
            i3 = rotateLeft3;
        }
        int[] iArr2 = this.f404_S;
        int i7 = bytesToWord3 + iArr2[43];
        wordToBytes(bytesToWord + iArr2[42], bArr2, i2);
        wordToBytes(i3, bArr2, i2 + 4);
        wordToBytes(i7, bArr2, i2 + 8);
        wordToBytes(i4, bArr2, i2 + 12);
        return 16;
    }

    private int rotateLeft(int i, int i2) {
        return (i >>> (-i2)) | (i << i2);
    }

    private int rotateRight(int i, int i2) {
        return (i << (-i2)) | (i >>> i2);
    }

    private void setKey(byte[] bArr) {
        int[] iArr;
        int length = (bArr.length + 3) / 4;
        int length2 = ((bArr.length + 4) - 1) / 4;
        int[] iArr2 = new int[length2];
        for (int length3 = bArr.length - 1; length3 >= 0; length3--) {
            int i = length3 / 4;
            iArr2[i] = (iArr2[i] << 8) + (bArr[length3] & UByte.MAX_VALUE);
        }
        int[] iArr3 = new int[44];
        this.f404_S = iArr3;
        iArr3[0] = P32;
        int i2 = 1;
        while (true) {
            iArr = this.f404_S;
            if (i2 >= iArr.length) {
                break;
            }
            iArr[i2] = iArr[i2 - 1] + Q32;
            i2++;
        }
        int length4 = length2 > iArr.length ? length2 * 3 : iArr.length * 3;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 < length4; i7++) {
            int[] iArr4 = this.f404_S;
            i4 = rotateLeft(iArr4[i3] + i4 + i5, 3);
            iArr4[i3] = i4;
            i5 = rotateLeft(iArr2[i6] + i4 + i5, i5 + i4);
            iArr2[i6] = i5;
            i3 = (i3 + 1) % this.f404_S.length;
            i6 = (i6 + 1) % length2;
        }
    }

    private void wordToBytes(int i, byte[] bArr, int i2) {
        for (int i3 = 0; i3 < 4; i3++) {
            bArr[i3 + i2] = (byte) i;
            i >>>= 8;
        }
    }

    public String getAlgorithmName() {
        return "RC6";
    }

    public int getBlockSize() {
        return 16;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (cipherParameters instanceof KeyParameter) {
            this.forEncryption = z;
            setKey(((KeyParameter) cipherParameters).getKey());
            return;
        }
        throw new IllegalArgumentException("invalid parameter passed to RC6 init - " + cipherParameters.getClass().getName());
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        int blockSize = getBlockSize();
        if (this.f404_S == null) {
            throw new IllegalStateException("RC6 engine not initialised");
        } else if (i + blockSize > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (blockSize + i2 <= bArr2.length) {
            return this.forEncryption ? encryptBlock(bArr, i, bArr2, i2) : decryptBlock(bArr, i, bArr2, i2);
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    public void reset() {
    }
}
