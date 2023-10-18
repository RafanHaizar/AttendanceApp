package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;

public class G3413CBCBlockCipher implements BlockCipher {

    /* renamed from: R */
    private byte[] f502R;
    private byte[] R_init;
    private int blockSize;
    private BlockCipher cipher;
    private boolean forEncryption;
    private boolean initialized = false;

    /* renamed from: m */
    private int f503m;

    public G3413CBCBlockCipher(BlockCipher blockCipher) {
        this.blockSize = blockCipher.getBlockSize();
        this.cipher = blockCipher;
    }

    private int decrypt(byte[] bArr, int i, byte[] bArr2, int i2) {
        byte[] MSB = GOST3413CipherUtil.MSB(this.f502R, this.blockSize);
        byte[] copyFromInput = GOST3413CipherUtil.copyFromInput(bArr, this.blockSize, i);
        byte[] bArr3 = new byte[copyFromInput.length];
        this.cipher.processBlock(copyFromInput, 0, bArr3, 0);
        byte[] sum = GOST3413CipherUtil.sum(bArr3, MSB);
        System.arraycopy(sum, 0, bArr2, i2, sum.length);
        if (bArr2.length > i2 + sum.length) {
            generateR(copyFromInput);
        }
        return sum.length;
    }

    private int encrypt(byte[] bArr, int i, byte[] bArr2, int i2) {
        byte[] sum = GOST3413CipherUtil.sum(GOST3413CipherUtil.copyFromInput(bArr, this.blockSize, i), GOST3413CipherUtil.MSB(this.f502R, this.blockSize));
        int length = sum.length;
        byte[] bArr3 = new byte[length];
        this.cipher.processBlock(sum, 0, bArr3, 0);
        System.arraycopy(bArr3, 0, bArr2, i2, length);
        if (bArr2.length > i2 + sum.length) {
            generateR(bArr3);
        }
        return length;
    }

    private void generateR(byte[] bArr) {
        byte[] LSB = GOST3413CipherUtil.LSB(this.f502R, this.f503m - this.blockSize);
        System.arraycopy(LSB, 0, this.f502R, 0, LSB.length);
        System.arraycopy(bArr, 0, this.f502R, LSB.length, this.f503m - LSB.length);
    }

    private void initArrays() {
        int i = this.f503m;
        this.f502R = new byte[i];
        this.R_init = new byte[i];
    }

    private void setupDefaultParams() {
        this.f503m = this.blockSize;
    }

    public String getAlgorithmName() {
        return this.cipher.getAlgorithmName() + "/CBC";
    }

    public int getBlockSize() {
        return this.blockSize;
    }

    public void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException {
        BlockCipher blockCipher;
        this.forEncryption = z;
        if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            byte[] iv = parametersWithIV.getIV();
            if (iv.length >= this.blockSize) {
                this.f503m = iv.length;
                initArrays();
                byte[] clone = Arrays.clone(iv);
                this.R_init = clone;
                System.arraycopy(clone, 0, this.f502R, 0, clone.length);
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
        System.arraycopy(bArr, 0, this.f502R, 0, bArr.length);
        if (cipherParameters != null) {
            blockCipher = this.cipher;
        }
        this.initialized = true;
        blockCipher.init(z, cipherParameters);
        this.initialized = true;
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) throws DataLengthException, IllegalStateException {
        return this.forEncryption ? encrypt(bArr, i, bArr2, i2) : decrypt(bArr, i, bArr2, i2);
    }

    public void reset() {
        if (this.initialized) {
            byte[] bArr = this.R_init;
            System.arraycopy(bArr, 0, this.f502R, 0, bArr.length);
            this.cipher.reset();
        }
    }
}
