package org.bouncycastle.crypto.engines;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.Pack;

public class ISAACEngine implements StreamCipher {

    /* renamed from: a */
    private int f395a = 0;

    /* renamed from: b */
    private int f396b = 0;

    /* renamed from: c */
    private int f397c = 0;
    private int[] engineState = null;
    private int index = 0;
    private boolean initialised = false;
    private byte[] keyStream = new byte[1024];
    private int[] results = null;
    private final int sizeL = 8;
    private final int stateArraySize = 256;
    private byte[] workingKey = null;

    private void isaac() {
        int i;
        int i2;
        int i3 = this.f396b;
        int i4 = this.f397c + 1;
        this.f397c = i4;
        this.f396b = i3 + i4;
        for (int i5 = 0; i5 < 256; i5++) {
            int[] iArr = this.engineState;
            int i6 = iArr[i5];
            switch (i5 & 3) {
                case 0:
                    i2 = this.f395a;
                    i = i2 << 13;
                    break;
                case 1:
                    i2 = this.f395a;
                    i = i2 >>> 6;
                    break;
                case 2:
                    i2 = this.f395a;
                    i = i2 << 2;
                    break;
                case 3:
                    i2 = this.f395a;
                    i = i2 >>> 16;
                    break;
            }
            this.f395a = i2 ^ i;
            int i7 = this.f395a + iArr[(i5 + 128) & 255];
            this.f395a = i7;
            int i8 = iArr[(i6 >>> 2) & 255] + i7 + this.f396b;
            iArr[i5] = i8;
            int[] iArr2 = this.results;
            int i9 = iArr[(i8 >>> 10) & 255] + i6;
            this.f396b = i9;
            iArr2[i5] = i9;
        }
    }

    private void mix(int[] iArr) {
        int i = iArr[0];
        int i2 = iArr[1];
        int i3 = i ^ (i2 << 11);
        iArr[0] = i3;
        int i4 = iArr[3] + i3;
        iArr[3] = i4;
        int i5 = iArr[2];
        int i6 = i2 + i5;
        iArr[1] = i6;
        int i7 = i6 ^ (i5 >>> 2);
        iArr[1] = i7;
        int i8 = iArr[4] + i7;
        iArr[4] = i8;
        int i9 = i5 + i4;
        iArr[2] = i9;
        int i10 = i9 ^ (i4 << 8);
        iArr[2] = i10;
        int i11 = iArr[5] + i10;
        iArr[5] = i11;
        int i12 = i4 + i8;
        iArr[3] = i12;
        int i13 = i12 ^ (i8 >>> 16);
        iArr[3] = i13;
        int i14 = iArr[6] + i13;
        iArr[6] = i14;
        int i15 = i8 + i11;
        iArr[4] = i15;
        int i16 = (i11 << 10) ^ i15;
        iArr[4] = i16;
        int i17 = iArr[7] + i16;
        iArr[7] = i17;
        int i18 = i11 + i14;
        iArr[5] = i18;
        int i19 = (i14 >>> 4) ^ i18;
        iArr[5] = i19;
        int i20 = i3 + i19;
        iArr[0] = i20;
        int i21 = i14 + i17;
        iArr[6] = i21;
        int i22 = (i17 << 8) ^ i21;
        iArr[6] = i22;
        int i23 = i7 + i22;
        iArr[1] = i23;
        int i24 = i17 + i20;
        iArr[7] = i24;
        int i25 = (i20 >>> 9) ^ i24;
        iArr[7] = i25;
        iArr[2] = i10 + i25;
        iArr[0] = i20 + i23;
    }

    private void setKey(byte[] bArr) {
        this.workingKey = bArr;
        if (this.engineState == null) {
            this.engineState = new int[256];
        }
        if (this.results == null) {
            this.results = new int[256];
        }
        for (int i = 0; i < 256; i++) {
            int[] iArr = this.engineState;
            this.results[i] = 0;
            iArr[i] = 0;
        }
        this.f397c = 0;
        this.f396b = 0;
        this.f395a = 0;
        this.index = 0;
        int length = bArr.length + (bArr.length & 3);
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        for (int i2 = 0; i2 < length; i2 += 4) {
            this.results[i2 >>> 2] = Pack.littleEndianToInt(bArr2, i2);
        }
        int[] iArr2 = new int[8];
        for (int i3 = 0; i3 < 8; i3++) {
            iArr2[i3] = -1640531527;
        }
        for (int i4 = 0; i4 < 4; i4++) {
            mix(iArr2);
        }
        int i5 = 0;
        while (i5 < 2) {
            for (int i6 = 0; i6 < 256; i6 += 8) {
                for (int i7 = 0; i7 < 8; i7++) {
                    iArr2[i7] = iArr2[i7] + (i5 < 1 ? this.results[i6 + i7] : this.engineState[i6 + i7]);
                }
                mix(iArr2);
                for (int i8 = 0; i8 < 8; i8++) {
                    this.engineState[i6 + i8] = iArr2[i8];
                }
            }
            i5++;
        }
        isaac();
        this.initialised = true;
    }

    public String getAlgorithmName() {
        return "ISAAC";
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (cipherParameters instanceof KeyParameter) {
            setKey(((KeyParameter) cipherParameters).getKey());
            return;
        }
        throw new IllegalArgumentException("invalid parameter passed to ISAAC init - " + cipherParameters.getClass().getName());
    }

    public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        if (!this.initialised) {
            throw new IllegalStateException(getAlgorithmName() + " not initialised");
        } else if (i + i2 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i3 + i2 <= bArr2.length) {
            for (int i4 = 0; i4 < i2; i4++) {
                if (this.index == 0) {
                    isaac();
                    this.keyStream = Pack.intToBigEndian(this.results);
                }
                byte[] bArr3 = this.keyStream;
                int i5 = this.index;
                bArr2[i4 + i3] = (byte) (bArr3[i5] ^ bArr[i4 + i]);
                this.index = (i5 + 1) & 1023;
            }
            return i2;
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    public void reset() {
        setKey(this.workingKey);
    }

    public byte returnByte(byte b) {
        if (this.index == 0) {
            isaac();
            this.keyStream = Pack.intToBigEndian(this.results);
        }
        byte[] bArr = this.keyStream;
        int i = this.index;
        byte b2 = (byte) (b ^ bArr[i]);
        this.index = (i + 1) & 1023;
        return b2;
    }
}
