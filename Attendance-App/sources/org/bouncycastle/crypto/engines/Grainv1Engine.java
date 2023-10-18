package org.bouncycastle.crypto.engines;

import kotlin.UByte;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class Grainv1Engine implements StreamCipher {
    private static final int STATE_SIZE = 5;
    private int index = 2;
    private boolean initialised = false;
    private int[] lfsr;
    private int[] nfsr;
    private byte[] out;
    private int output;
    private byte[] workingIV;
    private byte[] workingKey;

    private byte getKeyStream() {
        if (this.index > 1) {
            oneRound();
            this.index = 0;
        }
        byte[] bArr = this.out;
        int i = this.index;
        this.index = i + 1;
        return bArr[i];
    }

    private int getOutput() {
        int[] iArr = this.nfsr;
        int i = iArr[0];
        int i2 = iArr[1];
        int i3 = (i >>> 1) | (i2 << 15);
        int i4 = (i >>> 2) | (i2 << 14);
        int i5 = (i >>> 4) | (i2 << 12);
        int i6 = (i >>> 10) | (i2 << 6);
        int i7 = iArr[2];
        int i8 = (i2 >>> 15) | (i7 << 1);
        int i9 = iArr[3];
        int i10 = (i7 >>> 11) | (i9 << 5);
        int i11 = iArr[4];
        int i12 = (i9 >>> 8) | (i11 << 8);
        int i13 = (i11 << 1) | (i9 >>> 15);
        int[] iArr2 = this.lfsr;
        int i14 = iArr2[1];
        int i15 = (iArr2[0] >>> 3) | (i14 << 13);
        int i16 = iArr2[2];
        int i17 = (i14 >>> 9) | (i16 << 7);
        int i18 = (iArr2[3] << 2) | (i16 >>> 14);
        int i19 = iArr2[4];
        int i20 = (i17 ^ i13) ^ (i15 & i19);
        int i21 = i18 & i19;
        int i22 = i15 & i18;
        int i23 = i19 & i22;
        int i24 = i22 & i13;
        return (((((((((i13 & i21) ^ ((i24 ^ (i23 ^ (((i20 ^ i21) ^ (i19 & i13)) ^ ((i15 & i17) & i18)))) ^ ((i17 & i18) & i13))) ^ i3) ^ i4) ^ i5) ^ i6) ^ i8) ^ i10) ^ i12) & 65535;
    }

    private int getOutputLFSR() {
        int[] iArr = this.lfsr;
        int i = iArr[0];
        int i2 = iArr[1];
        int i3 = iArr[2];
        int i4 = (i2 >>> 7) | (i3 << 9);
        int i5 = iArr[3];
        int i6 = iArr[4];
        return (((i6 << 2) | (i5 >>> 14)) ^ ((((i ^ ((i >>> 13) | (i2 << 3))) ^ i4) ^ ((i3 >>> 6) | (i5 << 10))) ^ ((i5 >>> 3) | (i6 << 13)))) & 65535;
    }

    private int getOutputNFSR() {
        int[] iArr = this.nfsr;
        int i = iArr[0];
        int i2 = iArr[1];
        int i3 = (i >>> 9) | (i2 << 7);
        int i4 = (i >>> 14) | (i2 << 2);
        int i5 = (i >>> 15) | (i2 << 1);
        int i6 = iArr[2];
        int i7 = (i2 >>> 5) | (i6 << 11);
        int i8 = (i2 >>> 12) | (i6 << 4);
        int i9 = iArr[3];
        int i10 = (i6 >>> 1) | (i9 << 15);
        int i11 = (i6 >>> 5) | (i9 << 11);
        int i12 = (i6 >>> 13) | (i9 << 3);
        int i13 = iArr[4];
        int i14 = (i9 >>> 4) | (i13 << 12);
        int i15 = (i9 >>> 12) | (i13 << 4);
        int i16 = (i13 << 1) | (i9 >>> 15);
        int i17 = i16 & i15;
        int i18 = i15 & i14;
        int i19 = ((((i ^ (((((((((((i9 >>> 14) | (i13 << 2)) ^ i15) ^ i14) ^ i12) ^ i11) ^ i10) ^ i8) ^ i7) ^ i4) ^ i3)) ^ i17) ^ (i11 & i10)) ^ (i5 & i3)) ^ (i18 & i12);
        int i20 = i10 & i8 & i7;
        return (((((((((i16 & i12) & i8) & i3) ^ (i19 ^ i20)) ^ ((i18 & i11) & i10)) ^ ((i17 & i7) & i5)) ^ (((i17 & i14) & i12) & i11)) ^ ((i20 & i5) & i3)) ^ (((((i14 & i12) & i11) & i10) & i8) & i7)) & 65535;
    }

    private void initGrain() {
        for (int i = 0; i < 10; i++) {
            this.output = getOutput();
            this.nfsr = shift(this.nfsr, (getOutputNFSR() ^ this.lfsr[0]) ^ this.output);
            this.lfsr = shift(this.lfsr, getOutputLFSR() ^ this.output);
        }
        this.initialised = true;
    }

    private void oneRound() {
        int output2 = getOutput();
        this.output = output2;
        byte[] bArr = this.out;
        bArr[0] = (byte) output2;
        bArr[1] = (byte) (output2 >> 8);
        this.nfsr = shift(this.nfsr, getOutputNFSR() ^ this.lfsr[0]);
        this.lfsr = shift(this.lfsr, getOutputLFSR());
    }

    private void setKey(byte[] bArr, byte[] bArr2) {
        bArr2[8] = -1;
        bArr2[9] = -1;
        this.workingKey = bArr;
        this.workingIV = bArr2;
        int i = 0;
        int i2 = 0;
        while (true) {
            int[] iArr = this.nfsr;
            if (i < iArr.length) {
                byte[] bArr3 = this.workingKey;
                int i3 = i2 + 1;
                iArr[i] = ((bArr3[i2] & UByte.MAX_VALUE) | (bArr3[i3] << 8)) & UByte.MAX_VALUE;
                int[] iArr2 = this.lfsr;
                byte[] bArr4 = this.workingIV;
                iArr2[i] = ((bArr4[i2] & UByte.MAX_VALUE) | (bArr4[i3] << 8)) & UByte.MAX_VALUE;
                i2 += 2;
                i++;
            } else {
                return;
            }
        }
    }

    private int[] shift(int[] iArr, int i) {
        iArr[0] = iArr[1];
        iArr[1] = iArr[2];
        iArr[2] = iArr[3];
        iArr[3] = iArr[4];
        iArr[4] = i;
        return iArr;
    }

    public String getAlgorithmName() {
        return "Grain v1";
    }

    public void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException {
        if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            byte[] iv = parametersWithIV.getIV();
            if (iv == null || iv.length != 8) {
                throw new IllegalArgumentException("Grain v1 requires exactly 8 bytes of IV");
            } else if (parametersWithIV.getParameters() instanceof KeyParameter) {
                KeyParameter keyParameter = (KeyParameter) parametersWithIV.getParameters();
                this.workingIV = new byte[keyParameter.getKey().length];
                this.workingKey = new byte[keyParameter.getKey().length];
                this.lfsr = new int[5];
                this.nfsr = new int[5];
                this.out = new byte[2];
                System.arraycopy(iv, 0, this.workingIV, 0, iv.length);
                System.arraycopy(keyParameter.getKey(), 0, this.workingKey, 0, keyParameter.getKey().length);
                reset();
            } else {
                throw new IllegalArgumentException("Grain v1 Init parameters must include a key");
            }
        } else {
            throw new IllegalArgumentException("Grain v1 Init parameters must include an IV");
        }
    }

    public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws DataLengthException {
        if (!this.initialised) {
            throw new IllegalStateException(getAlgorithmName() + " not initialised");
        } else if (i + i2 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i3 + i2 <= bArr2.length) {
            for (int i4 = 0; i4 < i2; i4++) {
                bArr2[i3 + i4] = (byte) (bArr[i + i4] ^ getKeyStream());
            }
            return i2;
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    public void reset() {
        this.index = 2;
        setKey(this.workingKey, this.workingIV);
        initGrain();
    }

    public byte returnByte(byte b) {
        if (this.initialised) {
            return (byte) (b ^ getKeyStream());
        }
        throw new IllegalStateException(getAlgorithmName() + " not initialised");
    }
}
