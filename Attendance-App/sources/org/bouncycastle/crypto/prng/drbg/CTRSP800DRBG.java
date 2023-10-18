package org.bouncycastle.crypto.prng.drbg;

import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.prng.EntropySource;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;

public class CTRSP800DRBG implements SP80090DRBG {
    private static final int AES_MAX_BITS_REQUEST = 262144;
    private static final long AES_RESEED_MAX = 140737488355328L;
    private static final byte[] K_BITS = Hex.decodeStrict("000102030405060708090A0B0C0D0E0F101112131415161718191A1B1C1D1E1F");
    private static final int TDEA_MAX_BITS_REQUEST = 4096;
    private static final long TDEA_RESEED_MAX = 2147483648L;
    private byte[] _Key;

    /* renamed from: _V */
    private byte[] f620_V;
    private BlockCipher _engine;
    private EntropySource _entropySource;
    private boolean _isTDEA = false;
    private int _keySizeInBits;
    private long _reseedCounter = 0;
    private int _securityStrength;
    private int _seedLength;

    public CTRSP800DRBG(BlockCipher blockCipher, int i, int i2, EntropySource entropySource, byte[] bArr, byte[] bArr2) {
        this._entropySource = entropySource;
        this._engine = blockCipher;
        this._keySizeInBits = i;
        this._securityStrength = i2;
        this._seedLength = (blockCipher.getBlockSize() * 8) + i;
        this._isTDEA = isTDEA(blockCipher);
        if (i2 > 256) {
            throw new IllegalArgumentException("Requested security strength is not supported by the derivation function");
        } else if (getMaxSecurityStrength(blockCipher, i) < i2) {
            throw new IllegalArgumentException("Requested security strength is not supported by block cipher and key size");
        } else if (entropySource.entropySize() >= i2) {
            CTR_DRBG_Instantiate_algorithm(getEntropy(), bArr2, bArr);
        } else {
            throw new IllegalArgumentException("Not enough entropy for security strength required");
        }
    }

    private void BCC(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) {
        int blockSize = this._engine.getBlockSize();
        byte[] bArr5 = new byte[blockSize];
        int length = bArr4.length / blockSize;
        byte[] bArr6 = new byte[blockSize];
        this._engine.init(true, new KeyParameter(expandKey(bArr2)));
        this._engine.processBlock(bArr3, 0, bArr5, 0);
        for (int i = 0; i < length; i++) {
            XOR(bArr6, bArr5, bArr4, i * blockSize);
            this._engine.processBlock(bArr6, 0, bArr5, 0);
        }
        System.arraycopy(bArr5, 0, bArr, 0, bArr.length);
    }

    private byte[] Block_Cipher_df(byte[] bArr, int i) {
        byte[] bArr2 = bArr;
        int blockSize = this._engine.getBlockSize();
        int length = bArr2.length;
        int i2 = i / 8;
        int i3 = length + 8;
        byte[] bArr3 = new byte[(((((i3 + 1) + blockSize) - 1) / blockSize) * blockSize)];
        copyIntToByteArray(bArr3, length, 0);
        copyIntToByteArray(bArr3, i2, 4);
        System.arraycopy(bArr2, 0, bArr3, 8, length);
        bArr3[i3] = ByteCompanionObject.MIN_VALUE;
        int i4 = this._keySizeInBits;
        int i5 = (i4 / 8) + blockSize;
        byte[] bArr4 = new byte[i5];
        byte[] bArr5 = new byte[blockSize];
        byte[] bArr6 = new byte[blockSize];
        int i6 = i4 / 8;
        byte[] bArr7 = new byte[i6];
        System.arraycopy(K_BITS, 0, bArr7, 0, i6);
        int i7 = 0;
        while (true) {
            int i8 = i7 * blockSize;
            if (i8 * 8 >= this._keySizeInBits + (blockSize * 8)) {
                break;
            }
            copyIntToByteArray(bArr6, i7, 0);
            BCC(bArr5, bArr7, bArr6, bArr3);
            int i9 = i5 - i8;
            if (i9 > blockSize) {
                i9 = blockSize;
            }
            System.arraycopy(bArr5, 0, bArr4, i8, i9);
            i7++;
        }
        byte[] bArr8 = new byte[blockSize];
        System.arraycopy(bArr4, 0, bArr7, 0, i6);
        System.arraycopy(bArr4, i6, bArr8, 0, blockSize);
        byte[] bArr9 = new byte[i2];
        this._engine.init(true, new KeyParameter(expandKey(bArr7)));
        int i10 = 0;
        while (true) {
            int i11 = i10 * blockSize;
            if (i11 >= i2) {
                return bArr9;
            }
            this._engine.processBlock(bArr8, 0, bArr8, 0);
            int i12 = i2 - i11;
            if (i12 > blockSize) {
                i12 = blockSize;
            }
            System.arraycopy(bArr8, 0, bArr9, i11, i12);
            i10++;
        }
    }

    private void CTR_DRBG_Instantiate_algorithm(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        byte[] Block_Cipher_df = Block_Cipher_df(Arrays.concatenate(bArr, bArr2, bArr3), this._seedLength);
        int blockSize = this._engine.getBlockSize();
        byte[] bArr4 = new byte[((this._keySizeInBits + 7) / 8)];
        this._Key = bArr4;
        byte[] bArr5 = new byte[blockSize];
        this.f620_V = bArr5;
        CTR_DRBG_Update(Block_Cipher_df, bArr4, bArr5);
        this._reseedCounter = 1;
    }

    private void CTR_DRBG_Reseed_algorithm(byte[] bArr) {
        CTR_DRBG_Update(Block_Cipher_df(Arrays.concatenate(getEntropy(), bArr), this._seedLength), this._Key, this.f620_V);
        this._reseedCounter = 1;
    }

    private void CTR_DRBG_Update(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        int length = bArr.length;
        byte[] bArr4 = new byte[length];
        byte[] bArr5 = new byte[this._engine.getBlockSize()];
        int blockSize = this._engine.getBlockSize();
        this._engine.init(true, new KeyParameter(expandKey(bArr2)));
        int i = 0;
        while (true) {
            int i2 = i * blockSize;
            if (i2 < bArr.length) {
                addOneTo(bArr3);
                this._engine.processBlock(bArr3, 0, bArr5, 0);
                int i3 = length - i2;
                if (i3 > blockSize) {
                    i3 = blockSize;
                }
                System.arraycopy(bArr5, 0, bArr4, i2, i3);
                i++;
            } else {
                XOR(bArr4, bArr, bArr4, 0);
                System.arraycopy(bArr4, 0, bArr2, 0, bArr2.length);
                System.arraycopy(bArr4, bArr2.length, bArr3, 0, bArr3.length);
                return;
            }
        }
    }

    private void XOR(byte[] bArr, byte[] bArr2, byte[] bArr3, int i) {
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr[i2] = (byte) (bArr2[i2] ^ bArr3[i2 + i]);
        }
    }

    private void addOneTo(byte[] bArr) {
        byte b = 1;
        for (int i = 1; i <= bArr.length; i++) {
            int i2 = (bArr[bArr.length - i] & UByte.MAX_VALUE) + b;
            b = i2 > 255 ? (byte) 1 : 0;
            bArr[bArr.length - i] = (byte) i2;
        }
    }

    private void copyIntToByteArray(byte[] bArr, int i, int i2) {
        bArr[i2 + 0] = (byte) (i >> 24);
        bArr[i2 + 1] = (byte) (i >> 16);
        bArr[i2 + 2] = (byte) (i >> 8);
        bArr[i2 + 3] = (byte) i;
    }

    private byte[] getEntropy() {
        byte[] entropy = this._entropySource.getEntropy();
        if (entropy.length >= (this._securityStrength + 7) / 8) {
            return entropy;
        }
        throw new IllegalStateException("Insufficient entropy provided by entropy source");
    }

    private int getMaxSecurityStrength(BlockCipher blockCipher, int i) {
        if (isTDEA(blockCipher) && i == 168) {
            return 112;
        }
        if (blockCipher.getAlgorithmName().equals("AES")) {
            return i;
        }
        return -1;
    }

    private boolean isTDEA(BlockCipher blockCipher) {
        return blockCipher.getAlgorithmName().equals("DESede") || blockCipher.getAlgorithmName().equals("TDEA");
    }

    private void padKey(byte[] bArr, int i, byte[] bArr2, int i2) {
        int i3 = i + 0;
        bArr2[i2 + 0] = (byte) (bArr[i3] & 254);
        int i4 = i + 1;
        bArr2[i2 + 1] = (byte) ((bArr[i3] << 7) | ((bArr[i4] & 252) >>> 1));
        int i5 = i + 2;
        bArr2[i2 + 2] = (byte) ((bArr[i4] << 6) | ((bArr[i5] & 248) >>> 2));
        int i6 = i + 3;
        bArr2[i2 + 3] = (byte) ((bArr[i5] << 5) | ((bArr[i6] & 240) >>> 3));
        int i7 = i + 4;
        bArr2[i2 + 4] = (byte) ((bArr[i6] << 4) | ((bArr[i7] & 224) >>> 4));
        int i8 = i + 5;
        bArr2[i2 + 5] = (byte) ((bArr[i7] << 3) | ((bArr[i8] & 192) >>> 5));
        int i9 = i + 6;
        bArr2[i2 + 6] = (byte) ((bArr[i8] << 2) | ((bArr[i9] & ByteCompanionObject.MIN_VALUE) >>> 6));
        int i10 = i2 + 7;
        bArr2[i10] = (byte) (bArr[i9] << 1);
        while (i2 <= i10) {
            byte b = bArr2[i2];
            bArr2[i2] = (byte) (((((b >> 7) ^ ((((((b >> 1) ^ (b >> 2)) ^ (b >> 3)) ^ (b >> 4)) ^ (b >> 5)) ^ (b >> 6))) ^ 1) & 1) | (b & 254));
            i2++;
        }
    }

    /* access modifiers changed from: package-private */
    public byte[] expandKey(byte[] bArr) {
        if (!this._isTDEA) {
            return bArr;
        }
        byte[] bArr2 = new byte[24];
        padKey(bArr, 0, bArr2, 0);
        padKey(bArr, 7, bArr2, 8);
        padKey(bArr, 14, bArr2, 16);
        return bArr2;
    }

    public int generate(byte[] bArr, byte[] bArr2, boolean z) {
        byte[] bArr3;
        boolean z2 = this._isTDEA;
        long j = this._reseedCounter;
        if (z2) {
            if (j > TDEA_RESEED_MAX) {
                return -1;
            }
            if (Utils.isTooLarge(bArr, 512)) {
                throw new IllegalArgumentException("Number of bits per request limited to 4096");
            }
        } else if (j > AES_RESEED_MAX) {
            return -1;
        } else {
            if (Utils.isTooLarge(bArr, 32768)) {
                throw new IllegalArgumentException("Number of bits per request limited to 262144");
            }
        }
        if (z) {
            CTR_DRBG_Reseed_algorithm(bArr2);
            bArr2 = null;
        }
        if (bArr2 != null) {
            bArr3 = Block_Cipher_df(bArr2, this._seedLength);
            CTR_DRBG_Update(bArr3, this._Key, this.f620_V);
        } else {
            bArr3 = new byte[(this._seedLength / 8)];
        }
        int length = this.f620_V.length;
        byte[] bArr4 = new byte[length];
        this._engine.init(true, new KeyParameter(expandKey(this._Key)));
        for (int i = 0; i <= bArr.length / length; i++) {
            int i2 = i * length;
            int length2 = bArr.length - i2 > length ? length : bArr.length - (this.f620_V.length * i);
            if (length2 != 0) {
                addOneTo(this.f620_V);
                this._engine.processBlock(this.f620_V, 0, bArr4, 0);
                System.arraycopy(bArr4, 0, bArr, i2, length2);
            }
        }
        CTR_DRBG_Update(bArr3, this._Key, this.f620_V);
        this._reseedCounter++;
        return bArr.length * 8;
    }

    public int getBlockSize() {
        return this.f620_V.length * 8;
    }

    public void reseed(byte[] bArr) {
        CTR_DRBG_Reseed_algorithm(bArr);
    }
}
