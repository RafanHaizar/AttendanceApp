package org.bouncycastle.crypto.engines;

import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.Wrapper;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.util.DigestFactory;
import org.bouncycastle.util.Arrays;

public class DESedeWrapEngine implements Wrapper {
    private static final byte[] IV2 = {74, -35, -94, 44, 121, -24, 33, 5};
    byte[] digest = new byte[20];
    private CBCBlockCipher engine;
    private boolean forWrapping;

    /* renamed from: iv */
    private byte[] f372iv;
    private KeyParameter param;
    private ParametersWithIV paramPlusIV;
    Digest sha1 = DigestFactory.createSHA1();

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

    private static byte[] reverse(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        int i = 0;
        while (i < bArr.length) {
            int i2 = i + 1;
            bArr2[i] = bArr[bArr.length - i2];
            i = i2;
        }
        return bArr2;
    }

    public String getAlgorithmName() {
        return "DESede";
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        SecureRandom secureRandom;
        this.forWrapping = z;
        this.engine = new CBCBlockCipher(new DESedeEngine());
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            CipherParameters parameters = parametersWithRandom.getParameters();
            SecureRandom random = parametersWithRandom.getRandom();
            cipherParameters = parameters;
            secureRandom = random;
        } else {
            secureRandom = CryptoServicesRegistrar.getSecureRandom();
        }
        if (cipherParameters instanceof KeyParameter) {
            this.param = (KeyParameter) cipherParameters;
            if (this.forWrapping) {
                byte[] bArr = new byte[8];
                this.f372iv = bArr;
                secureRandom.nextBytes(bArr);
                this.paramPlusIV = new ParametersWithIV(this.param, this.f372iv);
            }
        } else if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            this.paramPlusIV = parametersWithIV;
            this.f372iv = parametersWithIV.getIV();
            this.param = (KeyParameter) this.paramPlusIV.getParameters();
            if (this.forWrapping) {
                byte[] bArr2 = this.f372iv;
                if (bArr2 == null || bArr2.length != 8) {
                    throw new IllegalArgumentException("IV is not 8 octets");
                }
                return;
            }
            throw new IllegalArgumentException("You should not supply an IV for unwrapping");
        }
    }

    public byte[] unwrap(byte[] bArr, int i, int i2) throws InvalidCipherTextException {
        if (this.forWrapping) {
            throw new IllegalStateException("Not set for unwrapping");
        } else if (bArr != null) {
            int blockSize = this.engine.getBlockSize();
            if (i2 % blockSize == 0) {
                this.engine.init(false, new ParametersWithIV(this.param, IV2));
                byte[] bArr2 = new byte[i2];
                for (int i3 = 0; i3 != i2; i3 += blockSize) {
                    this.engine.processBlock(bArr, i + i3, bArr2, i3);
                }
                byte[] reverse = reverse(bArr2);
                byte[] bArr3 = new byte[8];
                this.f372iv = bArr3;
                int length = reverse.length - 8;
                byte[] bArr4 = new byte[length];
                System.arraycopy(reverse, 0, bArr3, 0, 8);
                System.arraycopy(reverse, 8, bArr4, 0, reverse.length - 8);
                ParametersWithIV parametersWithIV = new ParametersWithIV(this.param, this.f372iv);
                this.paramPlusIV = parametersWithIV;
                this.engine.init(false, parametersWithIV);
                byte[] bArr5 = new byte[length];
                for (int i4 = 0; i4 != length; i4 += blockSize) {
                    this.engine.processBlock(bArr4, i4, bArr5, i4);
                }
                int i5 = length - 8;
                byte[] bArr6 = new byte[i5];
                byte[] bArr7 = new byte[8];
                System.arraycopy(bArr5, 0, bArr6, 0, i5);
                System.arraycopy(bArr5, i5, bArr7, 0, 8);
                if (checkCMSKeyChecksum(bArr6, bArr7)) {
                    return bArr6;
                }
                throw new InvalidCipherTextException("Checksum inside ciphertext is corrupted");
            }
            throw new InvalidCipherTextException("Ciphertext not multiple of " + blockSize);
        } else {
            throw new InvalidCipherTextException("Null pointer as ciphertext");
        }
    }

    public byte[] wrap(byte[] bArr, int i, int i2) {
        if (this.forWrapping) {
            byte[] bArr2 = new byte[i2];
            System.arraycopy(bArr, i, bArr2, 0, i2);
            byte[] calculateCMSKeyChecksum = calculateCMSKeyChecksum(bArr2);
            int length = calculateCMSKeyChecksum.length + i2;
            byte[] bArr3 = new byte[length];
            System.arraycopy(bArr2, 0, bArr3, 0, i2);
            System.arraycopy(calculateCMSKeyChecksum, 0, bArr3, i2, calculateCMSKeyChecksum.length);
            int blockSize = this.engine.getBlockSize();
            if (length % blockSize == 0) {
                this.engine.init(true, this.paramPlusIV);
                byte[] bArr4 = new byte[length];
                for (int i3 = 0; i3 != length; i3 += blockSize) {
                    this.engine.processBlock(bArr3, i3, bArr4, i3);
                }
                byte[] bArr5 = this.f372iv;
                byte[] bArr6 = new byte[(bArr5.length + length)];
                System.arraycopy(bArr5, 0, bArr6, 0, bArr5.length);
                System.arraycopy(bArr4, 0, bArr6, this.f372iv.length, length);
                byte[] reverse = reverse(bArr6);
                this.engine.init(true, new ParametersWithIV(this.param, IV2));
                for (int i4 = 0; i4 != reverse.length; i4 += blockSize) {
                    this.engine.processBlock(reverse, i4, reverse, i4);
                }
                return reverse;
            }
            throw new IllegalStateException("Not multiple of block length");
        }
        throw new IllegalStateException("Not initialized for wrapping");
    }
}
