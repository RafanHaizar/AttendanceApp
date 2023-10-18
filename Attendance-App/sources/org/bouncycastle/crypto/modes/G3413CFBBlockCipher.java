package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.StreamBlockCipher;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;

public class G3413CFBBlockCipher extends StreamBlockCipher {

    /* renamed from: R */
    private byte[] f504R;
    private byte[] R_init;
    private int blockSize;
    private int byteCount;
    private BlockCipher cipher;
    private boolean forEncryption;
    private byte[] gamma;
    private byte[] inBuf;
    private boolean initialized;

    /* renamed from: m */
    private int f505m;

    /* renamed from: s */
    private final int f506s;

    public G3413CFBBlockCipher(BlockCipher blockCipher) {
        this(blockCipher, blockCipher.getBlockSize() * 8);
    }

    public G3413CFBBlockCipher(BlockCipher blockCipher, int i) {
        super(blockCipher);
        this.initialized = false;
        if (i < 0 || i > blockCipher.getBlockSize() * 8) {
            throw new IllegalArgumentException("Parameter bitBlockSize must be in range 0 < bitBlockSize <= " + (blockCipher.getBlockSize() * 8));
        }
        this.blockSize = blockCipher.getBlockSize();
        this.cipher = blockCipher;
        this.f506s = i / 8;
        this.inBuf = new byte[getBlockSize()];
    }

    private void initArrays() {
        int i = this.f505m;
        this.f504R = new byte[i];
        this.R_init = new byte[i];
    }

    private void setupDefaultParams() {
        this.f505m = this.blockSize * 2;
    }

    /* access modifiers changed from: protected */
    public byte calculateByte(byte b) {
        if (this.byteCount == 0) {
            this.gamma = createGamma();
        }
        byte[] bArr = this.gamma;
        int i = this.byteCount;
        byte b2 = (byte) (bArr[i] ^ b);
        byte[] bArr2 = this.inBuf;
        int i2 = i + 1;
        this.byteCount = i2;
        if (this.forEncryption) {
            b = b2;
        }
        bArr2[i] = b;
        if (i2 == getBlockSize()) {
            this.byteCount = 0;
            generateR(this.inBuf);
        }
        return b2;
    }

    /* access modifiers changed from: package-private */
    public byte[] createGamma() {
        byte[] MSB = GOST3413CipherUtil.MSB(this.f504R, this.blockSize);
        byte[] bArr = new byte[MSB.length];
        this.cipher.processBlock(MSB, 0, bArr, 0);
        return GOST3413CipherUtil.MSB(bArr, this.f506s);
    }

    /* access modifiers changed from: package-private */
    public void generateR(byte[] bArr) {
        byte[] LSB = GOST3413CipherUtil.LSB(this.f504R, this.f505m - this.f506s);
        System.arraycopy(LSB, 0, this.f504R, 0, LSB.length);
        System.arraycopy(bArr, 0, this.f504R, LSB.length, this.f505m - LSB.length);
    }

    public String getAlgorithmName() {
        return this.cipher.getAlgorithmName() + "/CFB" + (this.blockSize * 8);
    }

    public int getBlockSize() {
        return this.f506s;
    }

    public void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException {
        BlockCipher blockCipher;
        this.forEncryption = z;
        if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            byte[] iv = parametersWithIV.getIV();
            if (iv.length >= this.blockSize) {
                this.f505m = iv.length;
                initArrays();
                byte[] clone = Arrays.clone(iv);
                this.R_init = clone;
                System.arraycopy(clone, 0, this.f504R, 0, clone.length);
                if (parametersWithIV.getParameters() != null) {
                    blockCipher = this.cipher;
                    cipherParameters = parametersWithIV.getParameters();
                }
                this.initialized = true;
            }
            throw new IllegalArgumentException("Parameter m must blockSize <= m");
        }
        setupDefaultParams();
        initArrays();
        byte[] bArr = this.R_init;
        System.arraycopy(bArr, 0, this.f504R, 0, bArr.length);
        if (cipherParameters != null) {
            blockCipher = this.cipher;
        }
        this.initialized = true;
        blockCipher.init(true, cipherParameters);
        this.initialized = true;
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) throws DataLengthException, IllegalStateException {
        processBytes(bArr, i, getBlockSize(), bArr2, i2);
        return getBlockSize();
    }

    public void reset() {
        this.byteCount = 0;
        Arrays.clear(this.inBuf);
        Arrays.clear(this.gamma);
        if (this.initialized) {
            byte[] bArr = this.R_init;
            System.arraycopy(bArr, 0, this.f504R, 0, bArr.length);
            this.cipher.reset();
        }
    }
}
