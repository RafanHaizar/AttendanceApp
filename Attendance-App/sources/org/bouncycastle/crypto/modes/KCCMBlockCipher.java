package org.bouncycastle.crypto.modes;

import java.io.ByteArrayOutputStream;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;

public class KCCMBlockCipher implements AEADBlockCipher {
    private static final int BITS_IN_BYTE = 8;
    private static final int BYTES_IN_INT = 4;
    private static final int MAX_MAC_BIT_LENGTH = 512;
    private static final int MIN_MAC_BIT_LENGTH = 64;

    /* renamed from: G1 */
    private byte[] f521G1;
    private int Nb_;
    private ExposedByteArrayOutputStream associatedText;
    private byte[] buffer;
    private byte[] counter;
    private ExposedByteArrayOutputStream data;
    private BlockCipher engine;
    private boolean forEncryption;
    private byte[] initialAssociatedText;
    private byte[] mac;
    private byte[] macBlock;
    private int macSize;
    private byte[] nonce;

    /* renamed from: s */
    private byte[] f522s;

    private class ExposedByteArrayOutputStream extends ByteArrayOutputStream {
        public ExposedByteArrayOutputStream() {
        }

        public byte[] getBuffer() {
            return this.buf;
        }
    }

    public KCCMBlockCipher(BlockCipher blockCipher) {
        this(blockCipher, 4);
    }

    public KCCMBlockCipher(BlockCipher blockCipher, int i) {
        this.associatedText = new ExposedByteArrayOutputStream();
        this.data = new ExposedByteArrayOutputStream();
        this.Nb_ = 4;
        this.engine = blockCipher;
        this.macSize = blockCipher.getBlockSize();
        this.nonce = new byte[blockCipher.getBlockSize()];
        this.initialAssociatedText = new byte[blockCipher.getBlockSize()];
        this.mac = new byte[blockCipher.getBlockSize()];
        this.macBlock = new byte[blockCipher.getBlockSize()];
        this.f521G1 = new byte[blockCipher.getBlockSize()];
        this.buffer = new byte[blockCipher.getBlockSize()];
        this.f522s = new byte[blockCipher.getBlockSize()];
        this.counter = new byte[blockCipher.getBlockSize()];
        setNb(i);
    }

    private void CalculateMac(byte[] bArr, int i, int i2) {
        while (i2 > 0) {
            for (int i3 = 0; i3 < this.engine.getBlockSize(); i3++) {
                byte[] bArr2 = this.macBlock;
                bArr2[i3] = (byte) (bArr2[i3] ^ bArr[i + i3]);
            }
            BlockCipher blockCipher = this.engine;
            byte[] bArr3 = this.macBlock;
            blockCipher.processBlock(bArr3, 0, bArr3, 0);
            i2 -= this.engine.getBlockSize();
            i += this.engine.getBlockSize();
        }
    }

    private void ProcessBlock(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        int i4 = 0;
        while (true) {
            byte[] bArr3 = this.counter;
            if (i4 >= bArr3.length) {
                break;
            }
            byte[] bArr4 = this.f522s;
            bArr4[i4] = (byte) (bArr4[i4] + bArr3[i4]);
            i4++;
        }
        this.engine.processBlock(this.f522s, 0, this.buffer, 0);
        for (int i5 = 0; i5 < this.engine.getBlockSize(); i5++) {
            bArr2[i3 + i5] = (byte) (this.buffer[i5] ^ bArr[i + i5]);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0036 A[LOOP:0: B:12:0x002f->B:14:0x0036, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private byte getFlag(boolean r4, int r5) {
        /*
            r3 = this;
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            java.lang.String r1 = "0"
            if (r4 == 0) goto L_0x000f
            java.lang.String r4 = "1"
            r0.append(r4)
            goto L_0x0012
        L_0x000f:
            r0.append(r1)
        L_0x0012:
            switch(r5) {
                case 8: goto L_0x0022;
                case 16: goto L_0x001f;
                case 32: goto L_0x001c;
                case 48: goto L_0x0019;
                case 64: goto L_0x0016;
                default: goto L_0x0015;
            }
        L_0x0015:
            goto L_0x0027
        L_0x0016:
            java.lang.String r4 = "110"
            goto L_0x0024
        L_0x0019:
            java.lang.String r4 = "101"
            goto L_0x0024
        L_0x001c:
            java.lang.String r4 = "100"
            goto L_0x0024
        L_0x001f:
            java.lang.String r4 = "011"
            goto L_0x0024
        L_0x0022:
            java.lang.String r4 = "010"
        L_0x0024:
            r0.append(r4)
        L_0x0027:
            int r4 = r3.Nb_
            int r4 = r4 + -1
            java.lang.String r4 = java.lang.Integer.toBinaryString(r4)
        L_0x002f:
            int r5 = r4.length()
            r2 = 4
            if (r5 >= r2) goto L_0x0045
            java.lang.StringBuffer r5 = new java.lang.StringBuffer
            r5.<init>(r4)
            r4 = 0
            java.lang.StringBuffer r4 = r5.insert(r4, r1)
            java.lang.String r4 = r4.toString()
            goto L_0x002f
        L_0x0045:
            r0.append(r4)
            java.lang.String r4 = r0.toString()
            r5 = 2
            int r4 = java.lang.Integer.parseInt(r4, r5)
            byte r4 = (byte) r4
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.modes.KCCMBlockCipher.getFlag(boolean, int):byte");
    }

    private void intToBytes(int i, byte[] bArr, int i2) {
        bArr[i2 + 3] = (byte) (i >> 24);
        bArr[i2 + 2] = (byte) (i >> 16);
        bArr[i2 + 1] = (byte) (i >> 8);
        bArr[i2] = (byte) i;
    }

    private void processAAD(byte[] bArr, int i, int i2, int i3) {
        if (i2 - i < this.engine.getBlockSize()) {
            throw new IllegalArgumentException("authText buffer too short");
        } else if (i2 % this.engine.getBlockSize() == 0) {
            byte[] bArr2 = this.nonce;
            System.arraycopy(bArr2, 0, this.f521G1, 0, (bArr2.length - this.Nb_) - 1);
            intToBytes(i3, this.buffer, 0);
            System.arraycopy(this.buffer, 0, this.f521G1, (this.nonce.length - this.Nb_) - 1, 4);
            byte[] bArr3 = this.f521G1;
            bArr3[bArr3.length - 1] = getFlag(true, this.macSize);
            this.engine.processBlock(this.f521G1, 0, this.macBlock, 0);
            intToBytes(i2, this.buffer, 0);
            if (i2 <= this.engine.getBlockSize() - this.Nb_) {
                for (int i4 = 0; i4 < i2; i4++) {
                    byte[] bArr4 = this.buffer;
                    int i5 = this.Nb_ + i4;
                    bArr4[i5] = (byte) (bArr4[i5] ^ bArr[i + i4]);
                }
                for (int i6 = 0; i6 < this.engine.getBlockSize(); i6++) {
                    byte[] bArr5 = this.macBlock;
                    bArr5[i6] = (byte) (bArr5[i6] ^ this.buffer[i6]);
                }
                BlockCipher blockCipher = this.engine;
                byte[] bArr6 = this.macBlock;
                blockCipher.processBlock(bArr6, 0, bArr6, 0);
                return;
            }
            for (int i7 = 0; i7 < this.engine.getBlockSize(); i7++) {
                byte[] bArr7 = this.macBlock;
                bArr7[i7] = (byte) (bArr7[i7] ^ this.buffer[i7]);
            }
            BlockCipher blockCipher2 = this.engine;
            byte[] bArr8 = this.macBlock;
            blockCipher2.processBlock(bArr8, 0, bArr8, 0);
            while (i2 != 0) {
                for (int i8 = 0; i8 < this.engine.getBlockSize(); i8++) {
                    byte[] bArr9 = this.macBlock;
                    bArr9[i8] = (byte) (bArr9[i8] ^ bArr[i8 + i]);
                }
                BlockCipher blockCipher3 = this.engine;
                byte[] bArr10 = this.macBlock;
                blockCipher3.processBlock(bArr10, 0, bArr10, 0);
                i += this.engine.getBlockSize();
                i2 -= this.engine.getBlockSize();
            }
        } else {
            throw new IllegalArgumentException("padding not supported");
        }
    }

    private void setNb(int i) {
        if (i == 4 || i == 6 || i == 8) {
            this.Nb_ = i;
            return;
        }
        throw new IllegalArgumentException("Nb = 4 is recommended by DSTU7624 but can be changed to only 6 or 8 in this implementation");
    }

    public int doFinal(byte[] bArr, int i) throws IllegalStateException, InvalidCipherTextException {
        int processPacket = processPacket(this.data.getBuffer(), 0, this.data.size(), bArr, i);
        reset();
        return processPacket;
    }

    public String getAlgorithmName() {
        return this.engine.getAlgorithmName() + "/KCCM";
    }

    public byte[] getMac() {
        return Arrays.clone(this.mac);
    }

    public int getOutputSize(int i) {
        return i + this.macSize;
    }

    public BlockCipher getUnderlyingCipher() {
        return this.engine;
    }

    public int getUpdateOutputSize(int i) {
        return i;
    }

    public void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException {
        CipherParameters cipherParameters2;
        if (cipherParameters instanceof AEADParameters) {
            AEADParameters aEADParameters = (AEADParameters) cipherParameters;
            if (aEADParameters.getMacSize() > 512 || aEADParameters.getMacSize() < 64 || aEADParameters.getMacSize() % 8 != 0) {
                throw new IllegalArgumentException("Invalid mac size specified");
            }
            this.nonce = aEADParameters.getNonce();
            this.macSize = aEADParameters.getMacSize() / 8;
            this.initialAssociatedText = aEADParameters.getAssociatedText();
            cipherParameters2 = aEADParameters.getKey();
        } else if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            this.nonce = parametersWithIV.getIV();
            this.macSize = this.engine.getBlockSize();
            this.initialAssociatedText = null;
            cipherParameters2 = parametersWithIV.getParameters();
        } else {
            throw new IllegalArgumentException("Invalid parameters specified");
        }
        this.mac = new byte[this.macSize];
        this.forEncryption = z;
        this.engine.init(true, cipherParameters2);
        this.counter[0] = 1;
        byte[] bArr = this.initialAssociatedText;
        if (bArr != null) {
            processAADBytes(bArr, 0, bArr.length);
        }
    }

    public void processAADByte(byte b) {
        this.associatedText.write(b);
    }

    public void processAADBytes(byte[] bArr, int i, int i2) {
        this.associatedText.write(bArr, i, i2);
    }

    public int processByte(byte b, byte[] bArr, int i) throws DataLengthException, IllegalStateException {
        this.data.write(b);
        return 0;
    }

    public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws DataLengthException, IllegalStateException {
        if (bArr.length >= i + i2) {
            this.data.write(bArr, i, i2);
            return 0;
        }
        throw new DataLengthException("input buffer too short");
    }

    public int processPacket(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalStateException, InvalidCipherTextException {
        int i4;
        if (bArr.length - i < i2) {
            throw new DataLengthException("input buffer too short");
        } else if (bArr2.length - i3 >= i2) {
            if (this.associatedText.size() > 0) {
                if (this.forEncryption) {
                    processAAD(this.associatedText.getBuffer(), 0, this.associatedText.size(), this.data.size());
                } else {
                    processAAD(this.associatedText.getBuffer(), 0, this.associatedText.size(), this.data.size() - this.macSize);
                }
            }
            if (this.forEncryption) {
                if (i2 % this.engine.getBlockSize() == 0) {
                    CalculateMac(bArr, i, i2);
                    this.engine.processBlock(this.nonce, 0, this.f522s, 0);
                    int i5 = i2;
                    while (i5 > 0) {
                        ProcessBlock(bArr, i, i2, bArr2, i3);
                        i5 -= this.engine.getBlockSize();
                        i += this.engine.getBlockSize();
                        i3 += this.engine.getBlockSize();
                    }
                    int i6 = 0;
                    while (true) {
                        byte[] bArr3 = this.counter;
                        if (i6 >= bArr3.length) {
                            break;
                        }
                        byte[] bArr4 = this.f522s;
                        bArr4[i6] = (byte) (bArr4[i6] + bArr3[i6]);
                        i6++;
                    }
                    this.engine.processBlock(this.f522s, 0, this.buffer, 0);
                    int i7 = 0;
                    while (true) {
                        int i8 = this.macSize;
                        if (i7 < i8) {
                            bArr2[i3 + i7] = (byte) (this.buffer[i7] ^ this.macBlock[i7]);
                            i7++;
                        } else {
                            System.arraycopy(this.macBlock, 0, this.mac, 0, i8);
                            reset();
                            return i2 + this.macSize;
                        }
                    }
                } else {
                    throw new DataLengthException("partial blocks not supported");
                }
            } else if ((i2 - this.macSize) % this.engine.getBlockSize() == 0) {
                this.engine.processBlock(this.nonce, 0, this.f522s, 0);
                int blockSize = i2 / this.engine.getBlockSize();
                for (int i9 = 0; i9 < blockSize; i9++) {
                    ProcessBlock(bArr, i, i2, bArr2, i3);
                    i += this.engine.getBlockSize();
                    i3 += this.engine.getBlockSize();
                }
                if (i2 > i) {
                    int i10 = 0;
                    while (true) {
                        byte[] bArr5 = this.counter;
                        if (i10 >= bArr5.length) {
                            break;
                        }
                        byte[] bArr6 = this.f522s;
                        bArr6[i10] = (byte) (bArr6[i10] + bArr5[i10]);
                        i10++;
                    }
                    this.engine.processBlock(this.f522s, 0, this.buffer, 0);
                    int i11 = 0;
                    while (true) {
                        i4 = this.macSize;
                        if (i11 >= i4) {
                            break;
                        }
                        bArr2[i3 + i11] = (byte) (this.buffer[i11] ^ bArr[i + i11]);
                        i11++;
                    }
                    i3 += i4;
                }
                int i12 = 0;
                while (true) {
                    byte[] bArr7 = this.counter;
                    if (i12 >= bArr7.length) {
                        break;
                    }
                    byte[] bArr8 = this.f522s;
                    bArr8[i12] = (byte) (bArr8[i12] + bArr7[i12]);
                    i12++;
                }
                this.engine.processBlock(this.f522s, 0, this.buffer, 0);
                int i13 = this.macSize;
                System.arraycopy(bArr2, i3 - i13, this.buffer, 0, i13);
                CalculateMac(bArr2, 0, i3 - this.macSize);
                System.arraycopy(this.macBlock, 0, this.mac, 0, this.macSize);
                int i14 = this.macSize;
                byte[] bArr9 = new byte[i14];
                System.arraycopy(this.buffer, 0, bArr9, 0, i14);
                if (Arrays.constantTimeAreEqual(this.mac, bArr9)) {
                    reset();
                    return i2 - this.macSize;
                }
                throw new InvalidCipherTextException("mac check failed");
            } else {
                throw new DataLengthException("partial blocks not supported");
            }
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    public void reset() {
        Arrays.fill(this.f521G1, (byte) 0);
        Arrays.fill(this.buffer, (byte) 0);
        Arrays.fill(this.counter, (byte) 0);
        Arrays.fill(this.macBlock, (byte) 0);
        this.counter[0] = 1;
        this.data.reset();
        this.associatedText.reset();
        byte[] bArr = this.initialAssociatedText;
        if (bArr != null) {
            processAADBytes(bArr, 0, bArr.length);
        }
    }
}
