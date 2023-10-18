package org.bouncycastle.crypto.engines;

import java.security.SecureRandom;
import kotlin.UByte;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.Wrapper;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.util.DigestFactory;
import org.bouncycastle.util.Arrays;

public class RC2WrapEngine implements Wrapper {
    private static final byte[] IV2 = {74, -35, -94, 44, 121, -24, 33, 5};
    byte[] digest = new byte[20];
    private CBCBlockCipher engine;
    private boolean forWrapping;

    /* renamed from: iv */
    private byte[] f398iv;
    private CipherParameters param;
    private ParametersWithIV paramPlusIV;
    Digest sha1 = DigestFactory.createSHA1();

    /* renamed from: sr */
    private SecureRandom f399sr;

    private byte[] calculateCMSKeyChecksum(byte[] bArr) {
        byte[] bArr2 = new byte[8];
        this.sha1.update(bArr, 0, bArr.length);
        this.sha1.doFinal(this.digest, 0);
        System.arraycopy(this.digest, 0, bArr2, 0, 8);
        return bArr2;
    }

    private boolean checkCMSKeyChecksum(byte[] bArr, byte[] bArr2) {
        return Arrays.constantTimeAreEqual(calculateCMSKeyChecksum(bArr), bArr2);
    }

    public String getAlgorithmName() {
        return "RC2";
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        this.forWrapping = z;
        this.engine = new CBCBlockCipher(new RC2Engine());
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            this.f399sr = parametersWithRandom.getRandom();
            cipherParameters = parametersWithRandom.getParameters();
        } else {
            this.f399sr = CryptoServicesRegistrar.getSecureRandom();
        }
        if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            this.paramPlusIV = parametersWithIV;
            this.f398iv = parametersWithIV.getIV();
            this.param = this.paramPlusIV.getParameters();
            if (this.forWrapping) {
                byte[] bArr = this.f398iv;
                if (bArr == null || bArr.length != 8) {
                    throw new IllegalArgumentException("IV is not 8 octets");
                }
                return;
            }
            throw new IllegalArgumentException("You should not supply an IV for unwrapping");
        }
        this.param = cipherParameters;
        if (this.forWrapping) {
            byte[] bArr2 = new byte[8];
            this.f398iv = bArr2;
            this.f399sr.nextBytes(bArr2);
            this.paramPlusIV = new ParametersWithIV(this.param, this.f398iv);
        }
    }

    public byte[] unwrap(byte[] bArr, int i, int i2) throws InvalidCipherTextException {
        if (this.forWrapping) {
            throw new IllegalStateException("Not set for unwrapping");
        } else if (bArr == null) {
            throw new InvalidCipherTextException("Null pointer as ciphertext");
        } else if (i2 % this.engine.getBlockSize() == 0) {
            this.engine.init(false, new ParametersWithIV(this.param, IV2));
            byte[] bArr2 = new byte[i2];
            System.arraycopy(bArr, i, bArr2, 0, i2);
            for (int i3 = 0; i3 < i2 / this.engine.getBlockSize(); i3++) {
                int blockSize = this.engine.getBlockSize() * i3;
                this.engine.processBlock(bArr2, blockSize, bArr2, blockSize);
            }
            byte[] bArr3 = new byte[i2];
            int i4 = 0;
            while (i4 < i2) {
                int i5 = i4 + 1;
                bArr3[i4] = bArr2[i2 - i5];
                i4 = i5;
            }
            byte[] bArr4 = new byte[8];
            this.f398iv = bArr4;
            int i6 = i2 - 8;
            byte[] bArr5 = new byte[i6];
            System.arraycopy(bArr3, 0, bArr4, 0, 8);
            System.arraycopy(bArr3, 8, bArr5, 0, i6);
            ParametersWithIV parametersWithIV = new ParametersWithIV(this.param, this.f398iv);
            this.paramPlusIV = parametersWithIV;
            this.engine.init(false, parametersWithIV);
            byte[] bArr6 = new byte[i6];
            System.arraycopy(bArr5, 0, bArr6, 0, i6);
            for (int i7 = 0; i7 < i6 / this.engine.getBlockSize(); i7++) {
                int blockSize2 = this.engine.getBlockSize() * i7;
                this.engine.processBlock(bArr6, blockSize2, bArr6, blockSize2);
            }
            int i8 = i6 - 8;
            byte[] bArr7 = new byte[i8];
            byte[] bArr8 = new byte[8];
            System.arraycopy(bArr6, 0, bArr7, 0, i8);
            System.arraycopy(bArr6, i8, bArr8, 0, 8);
            if (checkCMSKeyChecksum(bArr7, bArr8)) {
                byte b = bArr7[0];
                if (i8 - ((b & UByte.MAX_VALUE) + 1) <= 7) {
                    byte[] bArr9 = new byte[b];
                    System.arraycopy(bArr7, 1, bArr9, 0, b);
                    return bArr9;
                }
                throw new InvalidCipherTextException("too many pad bytes (" + (i8 - ((bArr7[0] & UByte.MAX_VALUE) + 1)) + ")");
            }
            throw new InvalidCipherTextException("Checksum inside ciphertext is corrupted");
        } else {
            throw new InvalidCipherTextException("Ciphertext not multiple of " + this.engine.getBlockSize());
        }
    }

    public byte[] wrap(byte[] bArr, int i, int i2) {
        if (this.forWrapping) {
            int i3 = i2 + 1;
            int i4 = i3 % 8;
            int i5 = i4 != 0 ? (8 - i4) + i3 : i3;
            byte[] bArr2 = new byte[i5];
            bArr2[0] = (byte) i2;
            System.arraycopy(bArr, i, bArr2, 1, i2);
            int i6 = (i5 - i2) - 1;
            byte[] bArr3 = new byte[i6];
            if (i6 > 0) {
                this.f399sr.nextBytes(bArr3);
                System.arraycopy(bArr3, 0, bArr2, i3, i6);
            }
            byte[] calculateCMSKeyChecksum = calculateCMSKeyChecksum(bArr2);
            int length = calculateCMSKeyChecksum.length + i5;
            byte[] bArr4 = new byte[length];
            System.arraycopy(bArr2, 0, bArr4, 0, i5);
            System.arraycopy(calculateCMSKeyChecksum, 0, bArr4, i5, calculateCMSKeyChecksum.length);
            byte[] bArr5 = new byte[length];
            System.arraycopy(bArr4, 0, bArr5, 0, length);
            int blockSize = length / this.engine.getBlockSize();
            if (length % this.engine.getBlockSize() == 0) {
                this.engine.init(true, this.paramPlusIV);
                for (int i7 = 0; i7 < blockSize; i7++) {
                    int blockSize2 = this.engine.getBlockSize() * i7;
                    this.engine.processBlock(bArr5, blockSize2, bArr5, blockSize2);
                }
                byte[] bArr6 = this.f398iv;
                int length2 = bArr6.length + length;
                byte[] bArr7 = new byte[length2];
                System.arraycopy(bArr6, 0, bArr7, 0, bArr6.length);
                System.arraycopy(bArr5, 0, bArr7, this.f398iv.length, length);
                byte[] bArr8 = new byte[length2];
                int i8 = 0;
                while (i8 < length2) {
                    int i9 = i8 + 1;
                    bArr8[i8] = bArr7[length2 - i9];
                    i8 = i9;
                }
                this.engine.init(true, new ParametersWithIV(this.param, IV2));
                for (int i10 = 0; i10 < blockSize + 1; i10++) {
                    int blockSize3 = this.engine.getBlockSize() * i10;
                    this.engine.processBlock(bArr8, blockSize3, bArr8, blockSize3);
                }
                return bArr8;
            }
            throw new IllegalStateException("Not multiple of block length");
        }
        throw new IllegalStateException("Not initialized for wrapping");
    }
}
