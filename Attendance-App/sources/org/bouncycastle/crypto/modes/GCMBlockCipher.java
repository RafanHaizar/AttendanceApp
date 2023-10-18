package org.bouncycastle.crypto.modes;

import kotlin.UByte;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.modes.gcm.BasicGCMExponentiator;
import org.bouncycastle.crypto.modes.gcm.GCMExponentiator;
import org.bouncycastle.crypto.modes.gcm.GCMMultiplier;
import org.bouncycastle.crypto.modes.gcm.GCMUtil;
import org.bouncycastle.crypto.modes.gcm.Tables4kGCMMultiplier;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

public class GCMBlockCipher implements AEADBlockCipher {
    private static final int BLOCK_SIZE = 16;

    /* renamed from: H */
    private byte[] f513H;

    /* renamed from: J0 */
    private byte[] f514J0;

    /* renamed from: S */
    private byte[] f515S;
    private byte[] S_at;
    private byte[] S_atPre;
    private byte[] atBlock;
    private int atBlockPos;
    private long atLength;
    private long atLengthPre;
    private int blocksRemaining;
    private byte[] bufBlock;
    private int bufOff;
    private BlockCipher cipher;
    private byte[] counter;
    private GCMExponentiator exp;
    private boolean forEncryption;
    private byte[] initialAssociatedText;
    private boolean initialised;
    private byte[] lastKey;
    private byte[] macBlock;
    private int macSize;
    private GCMMultiplier multiplier;
    private byte[] nonce;
    private long totalLength;

    public GCMBlockCipher(BlockCipher blockCipher) {
        this(blockCipher, (GCMMultiplier) null);
    }

    public GCMBlockCipher(BlockCipher blockCipher, GCMMultiplier gCMMultiplier) {
        if (blockCipher.getBlockSize() == 16) {
            gCMMultiplier = gCMMultiplier == null ? new Tables4kGCMMultiplier() : gCMMultiplier;
            this.cipher = blockCipher;
            this.multiplier = gCMMultiplier;
            return;
        }
        throw new IllegalArgumentException("cipher required with a block size of 16.");
    }

    private void checkStatus() {
        if (this.initialised) {
            return;
        }
        if (this.forEncryption) {
            throw new IllegalStateException("GCM cipher cannot be reused for encryption");
        }
        throw new IllegalStateException("GCM cipher needs to be initialised");
    }

    private void gHASH(byte[] bArr, byte[] bArr2, int i) {
        for (int i2 = 0; i2 < i; i2 += 16) {
            gHASHPartial(bArr, bArr2, i2, Math.min(i - i2, 16));
        }
    }

    private void gHASHBlock(byte[] bArr, byte[] bArr2) {
        GCMUtil.xor(bArr, bArr2);
        this.multiplier.multiplyH(bArr);
    }

    private void gHASHBlock(byte[] bArr, byte[] bArr2, int i) {
        GCMUtil.xor(bArr, bArr2, i);
        this.multiplier.multiplyH(bArr);
    }

    private void gHASHPartial(byte[] bArr, byte[] bArr2, int i, int i2) {
        GCMUtil.xor(bArr, bArr2, i, i2);
        this.multiplier.multiplyH(bArr);
    }

    private void getNextCTRBlock(byte[] bArr) {
        int i = this.blocksRemaining;
        if (i != 0) {
            this.blocksRemaining = i - 1;
            byte[] bArr2 = this.counter;
            int i2 = (bArr2[15] & UByte.MAX_VALUE) + 1;
            bArr2[15] = (byte) i2;
            int i3 = (i2 >>> 8) + (bArr2[14] & UByte.MAX_VALUE);
            bArr2[14] = (byte) i3;
            int i4 = (i3 >>> 8) + (bArr2[13] & UByte.MAX_VALUE);
            bArr2[13] = (byte) i4;
            bArr2[12] = (byte) ((i4 >>> 8) + (bArr2[12] & UByte.MAX_VALUE));
            this.cipher.processBlock(bArr2, 0, bArr, 0);
            return;
        }
        throw new IllegalStateException("Attempt to process too many blocks");
    }

    private void initCipher() {
        if (this.atLength > 0) {
            System.arraycopy(this.S_at, 0, this.S_atPre, 0, 16);
            this.atLengthPre = this.atLength;
        }
        int i = this.atBlockPos;
        if (i > 0) {
            gHASHPartial(this.S_atPre, this.atBlock, 0, i);
            this.atLengthPre += (long) this.atBlockPos;
        }
        if (this.atLengthPre > 0) {
            System.arraycopy(this.S_atPre, 0, this.f515S, 0, 16);
        }
    }

    private void processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (bArr2.length - i2 >= 16) {
            if (this.totalLength == 0) {
                initCipher();
            }
            byte[] bArr3 = new byte[16];
            getNextCTRBlock(bArr3);
            if (this.forEncryption) {
                GCMUtil.xor(bArr3, bArr, i);
                gHASHBlock(this.f515S, bArr3);
                System.arraycopy(bArr3, 0, bArr2, i2, 16);
            } else {
                gHASHBlock(this.f515S, bArr, i);
                GCMUtil.xor(bArr3, 0, bArr, i, bArr2, i2);
            }
            this.totalLength += 16;
            return;
        }
        throw new OutputLengthException("Output buffer too short");
    }

    private void processPartial(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        byte[] bArr3 = new byte[16];
        getNextCTRBlock(bArr3);
        if (this.forEncryption) {
            GCMUtil.xor(bArr, i, bArr3, 0, i2);
            gHASHPartial(this.f515S, bArr, i, i2);
        } else {
            gHASHPartial(this.f515S, bArr, i, i2);
            GCMUtil.xor(bArr, i, bArr3, 0, i2);
        }
        System.arraycopy(bArr, i, bArr2, i3, i2);
        this.totalLength += (long) i2;
    }

    private void reset(boolean z) {
        this.cipher.reset();
        this.f515S = new byte[16];
        this.S_at = new byte[16];
        this.S_atPre = new byte[16];
        this.atBlock = new byte[16];
        this.atBlockPos = 0;
        this.atLength = 0;
        this.atLengthPre = 0;
        this.counter = Arrays.clone(this.f514J0);
        this.blocksRemaining = -2;
        this.bufOff = 0;
        this.totalLength = 0;
        byte[] bArr = this.bufBlock;
        if (bArr != null) {
            Arrays.fill(bArr, (byte) 0);
        }
        if (z) {
            this.macBlock = null;
        }
        if (this.forEncryption) {
            this.initialised = false;
            return;
        }
        byte[] bArr2 = this.initialAssociatedText;
        if (bArr2 != null) {
            processAADBytes(bArr2, 0, bArr2.length);
        }
    }

    public int doFinal(byte[] bArr, int i) throws IllegalStateException, InvalidCipherTextException {
        checkStatus();
        if (this.totalLength == 0) {
            initCipher();
        }
        int i2 = this.bufOff;
        if (!this.forEncryption) {
            int i3 = this.macSize;
            if (i2 >= i3) {
                i2 -= i3;
                if (bArr.length - i < i2) {
                    throw new OutputLengthException("Output buffer too short");
                }
            } else {
                throw new InvalidCipherTextException("data too short");
            }
        } else if (bArr.length - i < this.macSize + i2) {
            throw new OutputLengthException("Output buffer too short");
        }
        if (i2 > 0) {
            processPartial(this.bufBlock, 0, i2, bArr, i);
        }
        long j = this.atLength;
        int i4 = this.atBlockPos;
        long j2 = j + ((long) i4);
        this.atLength = j2;
        if (j2 > this.atLengthPre) {
            if (i4 > 0) {
                gHASHPartial(this.S_at, this.atBlock, 0, i4);
            }
            if (this.atLengthPre > 0) {
                GCMUtil.xor(this.S_at, this.S_atPre);
            }
            long j3 = ((this.totalLength * 8) + 127) >>> 7;
            byte[] bArr2 = new byte[16];
            if (this.exp == null) {
                BasicGCMExponentiator basicGCMExponentiator = new BasicGCMExponentiator();
                this.exp = basicGCMExponentiator;
                basicGCMExponentiator.init(this.f513H);
            }
            this.exp.exponentiateX(j3, bArr2);
            GCMUtil.multiply(this.S_at, bArr2);
            GCMUtil.xor(this.f515S, this.S_at);
        }
        byte[] bArr3 = new byte[16];
        Pack.longToBigEndian(this.atLength * 8, bArr3, 0);
        Pack.longToBigEndian(this.totalLength * 8, bArr3, 8);
        gHASHBlock(this.f515S, bArr3);
        byte[] bArr4 = new byte[16];
        this.cipher.processBlock(this.f514J0, 0, bArr4, 0);
        GCMUtil.xor(bArr4, this.f515S);
        int i5 = this.macSize;
        byte[] bArr5 = new byte[i5];
        this.macBlock = bArr5;
        System.arraycopy(bArr4, 0, bArr5, 0, i5);
        if (this.forEncryption) {
            System.arraycopy(this.macBlock, 0, bArr, i + this.bufOff, this.macSize);
            i2 += this.macSize;
        } else {
            int i6 = this.macSize;
            byte[] bArr6 = new byte[i6];
            System.arraycopy(this.bufBlock, i2, bArr6, 0, i6);
            if (!Arrays.constantTimeAreEqual(this.macBlock, bArr6)) {
                throw new InvalidCipherTextException("mac check in GCM failed");
            }
        }
        reset(false);
        return i2;
    }

    public String getAlgorithmName() {
        return this.cipher.getAlgorithmName() + "/GCM";
    }

    public byte[] getMac() {
        byte[] bArr = this.macBlock;
        return bArr == null ? new byte[this.macSize] : Arrays.clone(bArr);
    }

    public int getOutputSize(int i) {
        int i2 = i + this.bufOff;
        if (this.forEncryption) {
            return i2 + this.macSize;
        }
        int i3 = this.macSize;
        if (i2 < i3) {
            return 0;
        }
        return i2 - i3;
    }

    public BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    public int getUpdateOutputSize(int i) {
        int i2 = i + this.bufOff;
        if (!this.forEncryption) {
            int i3 = this.macSize;
            if (i2 < i3) {
                return 0;
            }
            i2 -= i3;
        }
        return i2 - (i2 % 16);
    }

    public void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException {
        KeyParameter keyParameter;
        byte[] bArr;
        byte[] bArr2;
        this.forEncryption = z;
        this.macBlock = null;
        this.initialised = true;
        if (cipherParameters instanceof AEADParameters) {
            AEADParameters aEADParameters = (AEADParameters) cipherParameters;
            bArr = aEADParameters.getNonce();
            this.initialAssociatedText = aEADParameters.getAssociatedText();
            int macSize2 = aEADParameters.getMacSize();
            if (macSize2 < 32 || macSize2 > 128 || macSize2 % 8 != 0) {
                throw new IllegalArgumentException("Invalid value for MAC size: " + macSize2);
            }
            this.macSize = macSize2 / 8;
            keyParameter = aEADParameters.getKey();
        } else if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            bArr = parametersWithIV.getIV();
            this.initialAssociatedText = null;
            this.macSize = 16;
            keyParameter = (KeyParameter) parametersWithIV.getParameters();
        } else {
            throw new IllegalArgumentException("invalid parameters passed to GCM");
        }
        this.bufBlock = new byte[(z ? 16 : this.macSize + 16)];
        if (bArr == null || bArr.length < 1) {
            throw new IllegalArgumentException("IV must be at least 1 byte");
        }
        if (z && (bArr2 = this.nonce) != null && Arrays.areEqual(bArr2, bArr)) {
            if (keyParameter != null) {
                byte[] bArr3 = this.lastKey;
                if (bArr3 != null && Arrays.areEqual(bArr3, keyParameter.getKey())) {
                    throw new IllegalArgumentException("cannot reuse nonce for GCM encryption");
                }
            } else {
                throw new IllegalArgumentException("cannot reuse nonce for GCM encryption");
            }
        }
        this.nonce = bArr;
        if (keyParameter != null) {
            this.lastKey = keyParameter.getKey();
        }
        if (keyParameter != null) {
            this.cipher.init(true, keyParameter);
            byte[] bArr4 = new byte[16];
            this.f513H = bArr4;
            this.cipher.processBlock(bArr4, 0, bArr4, 0);
            this.multiplier.init(this.f513H);
            this.exp = null;
        } else if (this.f513H == null) {
            throw new IllegalArgumentException("Key must be specified in initial init");
        }
        byte[] bArr5 = new byte[16];
        this.f514J0 = bArr5;
        byte[] bArr6 = this.nonce;
        if (bArr6.length == 12) {
            System.arraycopy(bArr6, 0, bArr5, 0, bArr6.length);
            this.f514J0[15] = 1;
        } else {
            gHASH(bArr5, bArr6, bArr6.length);
            byte[] bArr7 = new byte[16];
            Pack.longToBigEndian(((long) this.nonce.length) * 8, bArr7, 8);
            gHASHBlock(this.f514J0, bArr7);
        }
        this.f515S = new byte[16];
        this.S_at = new byte[16];
        this.S_atPre = new byte[16];
        this.atBlock = new byte[16];
        this.atBlockPos = 0;
        this.atLength = 0;
        this.atLengthPre = 0;
        this.counter = Arrays.clone(this.f514J0);
        this.blocksRemaining = -2;
        this.bufOff = 0;
        this.totalLength = 0;
        byte[] bArr8 = this.initialAssociatedText;
        if (bArr8 != null) {
            processAADBytes(bArr8, 0, bArr8.length);
        }
    }

    public void processAADByte(byte b) {
        checkStatus();
        byte[] bArr = this.atBlock;
        int i = this.atBlockPos;
        bArr[i] = b;
        int i2 = i + 1;
        this.atBlockPos = i2;
        if (i2 == 16) {
            gHASHBlock(this.S_at, bArr);
            this.atBlockPos = 0;
            this.atLength += 16;
        }
    }

    public void processAADBytes(byte[] bArr, int i, int i2) {
        checkStatus();
        for (int i3 = 0; i3 < i2; i3++) {
            byte[] bArr2 = this.atBlock;
            int i4 = this.atBlockPos;
            bArr2[i4] = bArr[i + i3];
            int i5 = i4 + 1;
            this.atBlockPos = i5;
            if (i5 == 16) {
                gHASHBlock(this.S_at, bArr2);
                this.atBlockPos = 0;
                this.atLength += 16;
            }
        }
    }

    public int processByte(byte b, byte[] bArr, int i) throws DataLengthException {
        checkStatus();
        byte[] bArr2 = this.bufBlock;
        int i2 = this.bufOff;
        bArr2[i2] = b;
        int i3 = i2 + 1;
        this.bufOff = i3;
        if (i3 != bArr2.length) {
            return 0;
        }
        processBlock(bArr2, 0, bArr, i);
        if (this.forEncryption) {
            this.bufOff = 0;
        } else {
            byte[] bArr3 = this.bufBlock;
            System.arraycopy(bArr3, 16, bArr3, 0, this.macSize);
            this.bufOff = this.macSize;
        }
        return 16;
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0034 A[LOOP:1: B:12:0x0032->B:13:0x0034, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0042  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int processBytes(byte[] r8, int r9, int r10, byte[] r11, int r12) throws org.bouncycastle.crypto.DataLengthException {
        /*
            r7 = this;
            r7.checkStatus()
            int r0 = r8.length
            int r0 = r0 - r9
            if (r0 < r10) goto L_0x0075
            boolean r0 = r7.forEncryption
            r1 = 16
            r2 = 0
            if (r0 == 0) goto L_0x004a
            int r0 = r7.bufOff
            if (r0 == 0) goto L_0x0031
        L_0x0012:
            if (r10 <= 0) goto L_0x0031
            int r10 = r10 + -1
            byte[] r0 = r7.bufBlock
            int r3 = r7.bufOff
            int r4 = r9 + 1
            byte r9 = r8[r9]
            r0[r3] = r9
            int r3 = r3 + 1
            r7.bufOff = r3
            if (r3 != r1) goto L_0x002f
            r7.processBlock(r0, r2, r11, r12)
            r7.bufOff = r2
            r9 = r4
            r0 = 16
            goto L_0x0032
        L_0x002f:
            r9 = r4
            goto L_0x0012
        L_0x0031:
            r0 = 0
        L_0x0032:
            if (r10 < r1) goto L_0x0040
            int r3 = r12 + r0
            r7.processBlock(r8, r9, r11, r3)
            int r9 = r9 + 16
            int r10 = r10 + -16
            int r0 = r0 + 16
            goto L_0x0032
        L_0x0040:
            if (r10 <= 0) goto L_0x0074
            byte[] r11 = r7.bufBlock
            java.lang.System.arraycopy(r8, r9, r11, r2, r10)
            r7.bufOff = r10
            goto L_0x0074
        L_0x004a:
            r0 = 0
            r3 = 0
        L_0x004c:
            if (r3 >= r10) goto L_0x0074
            byte[] r4 = r7.bufBlock
            int r5 = r7.bufOff
            int r6 = r9 + r3
            byte r6 = r8[r6]
            r4[r5] = r6
            int r5 = r5 + 1
            r7.bufOff = r5
            int r6 = r4.length
            if (r5 != r6) goto L_0x0071
            int r5 = r12 + r0
            r7.processBlock(r4, r2, r11, r5)
            byte[] r4 = r7.bufBlock
            int r5 = r7.macSize
            java.lang.System.arraycopy(r4, r1, r4, r2, r5)
            int r4 = r7.macSize
            r7.bufOff = r4
            int r0 = r0 + 16
        L_0x0071:
            int r3 = r3 + 1
            goto L_0x004c
        L_0x0074:
            return r0
        L_0x0075:
            org.bouncycastle.crypto.DataLengthException r8 = new org.bouncycastle.crypto.DataLengthException
            java.lang.String r9 = "Input buffer too short"
            r8.<init>(r9)
            goto L_0x007e
        L_0x007d:
            throw r8
        L_0x007e:
            goto L_0x007d
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.modes.GCMBlockCipher.processBytes(byte[], int, int, byte[], int):int");
    }

    public void reset() {
        reset(true);
    }
}
