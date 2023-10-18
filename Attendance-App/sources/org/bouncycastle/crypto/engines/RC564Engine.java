package org.bouncycastle.crypto.engines;

import kotlin.UByte;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.RC5Parameters;

public class RC564Engine implements BlockCipher {
    private static final long P64 = -5196783011329398165L;
    private static final long Q64 = -7046029254386353131L;
    private static final int bytesPerWord = 8;
    private static final int wordSize = 64;

    /* renamed from: _S */
    private long[] f403_S = null;
    private int _noRounds = 12;
    private boolean forEncryption;

    private long bytesToWord(byte[] bArr, int i) {
        long j = 0;
        for (int i2 = 7; i2 >= 0; i2--) {
            j = (j << 8) + ((long) (bArr[i2 + i] & UByte.MAX_VALUE));
        }
        return j;
    }

    private int decryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        long bytesToWord = bytesToWord(bArr, i);
        long bytesToWord2 = bytesToWord(bArr, i + 8);
        for (int i3 = this._noRounds; i3 >= 1; i3--) {
            int i4 = i3 * 2;
            bytesToWord2 = rotateRight(bytesToWord2 - this.f403_S[i4 + 1], bytesToWord) ^ bytesToWord;
            bytesToWord = rotateRight(bytesToWord - this.f403_S[i4], bytesToWord2) ^ bytesToWord2;
        }
        wordToBytes(bytesToWord - this.f403_S[0], bArr2, i2);
        wordToBytes(bytesToWord2 - this.f403_S[1], bArr2, i2 + 8);
        return 16;
    }

    private int encryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        long bytesToWord = bytesToWord(bArr, i) + this.f403_S[0];
        long bytesToWord2 = bytesToWord(bArr, i + 8) + this.f403_S[1];
        for (int i3 = 1; i3 <= this._noRounds; i3++) {
            int i4 = i3 * 2;
            bytesToWord = rotateLeft(bytesToWord ^ bytesToWord2, bytesToWord2) + this.f403_S[i4];
            bytesToWord2 = rotateLeft(bytesToWord2 ^ bytesToWord, bytesToWord) + this.f403_S[i4 + 1];
        }
        wordToBytes(bytesToWord, bArr2, i2);
        wordToBytes(bytesToWord2, bArr2, i2 + 8);
        return 16;
    }

    private long rotateLeft(long j, long j2) {
        long j3 = j2 & 63;
        return (j >>> ((int) (64 - j3))) | (j << ((int) j3));
    }

    private long rotateRight(long j, long j2) {
        long j3 = j2 & 63;
        return (j << ((int) (64 - j3))) | (j >>> ((int) j3));
    }

    private void setKey(byte[] bArr) {
        long[] jArr;
        int length = (bArr.length + 7) / 8;
        long[] jArr2 = new long[length];
        for (int i = 0; i != bArr.length; i++) {
            int i2 = i / 8;
            jArr2[i2] = jArr2[i2] + (((long) (bArr[i] & UByte.MAX_VALUE)) << ((i % 8) * 8));
        }
        long[] jArr3 = new long[((this._noRounds + 1) * 2)];
        this.f403_S = jArr3;
        jArr3[0] = -5196783011329398165L;
        int i3 = 1;
        while (true) {
            jArr = this.f403_S;
            if (i3 >= jArr.length) {
                break;
            }
            jArr[i3] = jArr[i3 - 1] + Q64;
            i3++;
        }
        int length2 = length > jArr.length ? length * 3 : jArr.length * 3;
        long j = 0;
        long j2 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < length2; i6++) {
            long[] jArr4 = this.f403_S;
            j = rotateLeft(jArr4[i4] + j + j2, 3);
            jArr4[i4] = j;
            j2 = rotateLeft(jArr2[i5] + j + j2, j2 + j);
            jArr2[i5] = j2;
            i4 = (i4 + 1) % this.f403_S.length;
            i5 = (i5 + 1) % length;
        }
    }

    private void wordToBytes(long j, byte[] bArr, int i) {
        for (int i2 = 0; i2 < 8; i2++) {
            bArr[i2 + i] = (byte) ((int) j);
            j >>>= 8;
        }
    }

    public String getAlgorithmName() {
        return "RC5-64";
    }

    public int getBlockSize() {
        return 16;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (cipherParameters instanceof RC5Parameters) {
            RC5Parameters rC5Parameters = (RC5Parameters) cipherParameters;
            this.forEncryption = z;
            this._noRounds = rC5Parameters.getRounds();
            setKey(rC5Parameters.getKey());
            return;
        }
        throw new IllegalArgumentException("invalid parameter passed to RC564 init - " + cipherParameters.getClass().getName());
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        return this.forEncryption ? encryptBlock(bArr, i, bArr2, i2) : decryptBlock(bArr, i, bArr2, i2);
    }

    public void reset() {
    }
}
