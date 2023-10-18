package org.bouncycastle.crypto.engines;

import com.google.android.material.internal.ViewUtils;
import kotlin.UByte;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class HC256Engine implements StreamCipher {
    private byte[] buf = new byte[4];
    private int cnt = 0;
    private int idx = 0;
    private boolean initialised;

    /* renamed from: iv */
    private byte[] f390iv;
    private byte[] key;

    /* renamed from: p */
    private int[] f391p = new int[1024];

    /* renamed from: q */
    private int[] f392q = new int[1024];

    private byte getByte() {
        if (this.idx == 0) {
            int step = step();
            byte[] bArr = this.buf;
            bArr[0] = (byte) (step & 255);
            int i = step >> 8;
            bArr[1] = (byte) (i & 255);
            int i2 = i >> 8;
            bArr[2] = (byte) (i2 & 255);
            bArr[3] = (byte) ((i2 >> 8) & 255);
        }
        byte[] bArr2 = this.buf;
        int i3 = this.idx;
        byte b = bArr2[i3];
        this.idx = 3 & (i3 + 1);
        return b;
    }

    private void init() {
        byte[] bArr = this.key;
        if (bArr.length != 32 && bArr.length != 16) {
            throw new IllegalArgumentException("The key must be 128/256 bits long");
        } else if (this.f390iv.length >= 16) {
            if (bArr.length != 32) {
                byte[] bArr2 = new byte[32];
                System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                byte[] bArr3 = this.key;
                System.arraycopy(bArr3, 0, bArr2, 16, bArr3.length);
                this.key = bArr2;
            }
            byte[] bArr4 = this.f390iv;
            if (bArr4.length < 32) {
                byte[] bArr5 = new byte[32];
                System.arraycopy(bArr4, 0, bArr5, 0, bArr4.length);
                byte[] bArr6 = this.f390iv;
                System.arraycopy(bArr6, 0, bArr5, bArr6.length, 32 - bArr6.length);
                this.f390iv = bArr5;
            }
            this.idx = 0;
            this.cnt = 0;
            int[] iArr = new int[2560];
            for (int i = 0; i < 32; i++) {
                int i2 = i >> 2;
                iArr[i2] = iArr[i2] | ((this.key[i] & UByte.MAX_VALUE) << ((i & 3) * 8));
            }
            for (int i3 = 0; i3 < 32; i3++) {
                int i4 = (i3 >> 2) + 8;
                iArr[i4] = iArr[i4] | ((this.f390iv[i3] & UByte.MAX_VALUE) << ((i3 & 3) * 8));
            }
            for (int i5 = 16; i5 < 2560; i5++) {
                int i6 = iArr[i5 - 2];
                int i7 = iArr[i5 - 15];
                iArr[i5] = ((i6 >>> 10) ^ (rotateRight(i6, 17) ^ rotateRight(i6, 19))) + iArr[i5 - 7] + ((i7 >>> 3) ^ (rotateRight(i7, 7) ^ rotateRight(i7, 18))) + iArr[i5 - 16] + i5;
            }
            System.arraycopy(iArr, 512, this.f391p, 0, 1024);
            System.arraycopy(iArr, 1536, this.f392q, 0, 1024);
            for (int i8 = 0; i8 < 4096; i8++) {
                step();
            }
            this.cnt = 0;
        } else {
            throw new IllegalArgumentException("The IV must be at least 128 bits long");
        }
    }

    private static int rotateRight(int i, int i2) {
        return (i << (-i2)) | (i >>> i2);
    }

    private int step() {
        int i;
        int i2;
        int i3 = this.cnt;
        int i4 = i3 & 1023;
        if (i3 < 1024) {
            int[] iArr = this.f391p;
            int i5 = iArr[(i4 - 3) & 1023];
            int i6 = iArr[(i4 - 1023) & 1023];
            int i7 = iArr[i4];
            int rotateRight = iArr[(i4 - 10) & 1023] + (rotateRight(i6, 23) ^ rotateRight(i5, 10));
            int[] iArr2 = this.f392q;
            iArr[i4] = i7 + rotateRight + iArr2[(i5 ^ i6) & 1023];
            int[] iArr3 = this.f391p;
            int i8 = iArr3[(i4 - 12) & 1023];
            i = iArr2[i8 & 255] + iArr2[((i8 >> 8) & 255) + 256] + iArr2[((i8 >> 16) & 255) + 512] + iArr2[((i8 >> 24) & 255) + ViewUtils.EDGE_TO_EDGE_FLAGS];
            i2 = iArr3[i4];
        } else {
            int[] iArr4 = this.f392q;
            int i9 = iArr4[(i4 - 3) & 1023];
            int i10 = iArr4[(i4 - 1023) & 1023];
            int i11 = iArr4[i4];
            int rotateRight2 = iArr4[(i4 - 10) & 1023] + (rotateRight(i10, 23) ^ rotateRight(i9, 10));
            int[] iArr5 = this.f391p;
            iArr4[i4] = i11 + rotateRight2 + iArr5[(i9 ^ i10) & 1023];
            int[] iArr6 = this.f392q;
            int i12 = iArr6[(i4 - 12) & 1023];
            i = iArr5[i12 & 255] + iArr5[((i12 >> 8) & 255) + 256] + iArr5[((i12 >> 16) & 255) + 512] + iArr5[((i12 >> 24) & 255) + ViewUtils.EDGE_TO_EDGE_FLAGS];
            i2 = iArr6[i4];
        }
        int i13 = i2 ^ i;
        this.cnt = (this.cnt + 1) & 2047;
        return i13;
    }

    public String getAlgorithmName() {
        return "HC-256";
    }

    public void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException {
        CipherParameters cipherParameters2;
        if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            this.f390iv = parametersWithIV.getIV();
            cipherParameters2 = parametersWithIV.getParameters();
        } else {
            this.f390iv = new byte[0];
            cipherParameters2 = cipherParameters;
        }
        if (cipherParameters2 instanceof KeyParameter) {
            this.key = ((KeyParameter) cipherParameters2).getKey();
            init();
            this.initialised = true;
            return;
        }
        throw new IllegalArgumentException("Invalid parameter passed to HC256 init - " + cipherParameters.getClass().getName());
    }

    public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws DataLengthException {
        if (!this.initialised) {
            throw new IllegalStateException(getAlgorithmName() + " not initialised");
        } else if (i + i2 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i3 + i2 <= bArr2.length) {
            for (int i4 = 0; i4 < i2; i4++) {
                bArr2[i3 + i4] = (byte) (bArr[i + i4] ^ getByte());
            }
            return i2;
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    public void reset() {
        init();
    }

    public byte returnByte(byte b) {
        return (byte) (b ^ getByte());
    }
}
