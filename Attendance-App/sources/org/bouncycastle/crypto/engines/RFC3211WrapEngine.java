package org.bouncycastle.crypto.engines;

import java.security.SecureRandom;
import kotlin.UByte;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.Wrapper;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.util.Arrays;

public class RFC3211WrapEngine implements Wrapper {
    private CBCBlockCipher engine;
    private boolean forWrapping;
    private ParametersWithIV param;
    private SecureRandom rand;

    public RFC3211WrapEngine(BlockCipher blockCipher) {
        this.engine = new CBCBlockCipher(blockCipher);
    }

    public String getAlgorithmName() {
        return this.engine.getUnderlyingCipher().getAlgorithmName() + "/RFC3211Wrap";
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        this.forWrapping = z;
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            this.rand = parametersWithRandom.getRandom();
            if (parametersWithRandom.getParameters() instanceof ParametersWithIV) {
                this.param = (ParametersWithIV) parametersWithRandom.getParameters();
                return;
            }
            throw new IllegalArgumentException("RFC3211Wrap requires an IV");
        }
        if (z) {
            this.rand = CryptoServicesRegistrar.getSecureRandom();
        }
        if (cipherParameters instanceof ParametersWithIV) {
            this.param = (ParametersWithIV) cipherParameters;
            return;
        }
        throw new IllegalArgumentException("RFC3211Wrap requires an IV");
    }

    public byte[] unwrap(byte[] bArr, int i, int i2) throws InvalidCipherTextException {
        if (!this.forWrapping) {
            int blockSize = this.engine.getBlockSize();
            if (i2 >= blockSize * 2) {
                byte[] bArr2 = new byte[i2];
                byte[] bArr3 = new byte[blockSize];
                boolean z = false;
                System.arraycopy(bArr, i, bArr2, 0, i2);
                System.arraycopy(bArr, i, bArr3, 0, blockSize);
                this.engine.init(false, new ParametersWithIV(this.param.getParameters(), bArr3));
                for (int i3 = blockSize; i3 < i2; i3 += blockSize) {
                    this.engine.processBlock(bArr2, i3, bArr2, i3);
                }
                System.arraycopy(bArr2, i2 - blockSize, bArr3, 0, blockSize);
                this.engine.init(false, new ParametersWithIV(this.param.getParameters(), bArr3));
                this.engine.processBlock(bArr2, 0, bArr2, 0);
                this.engine.init(false, this.param);
                for (int i4 = 0; i4 < i2; i4 += blockSize) {
                    this.engine.processBlock(bArr2, i4, bArr2, i4);
                }
                byte b = bArr2[0];
                int i5 = i2 - 4;
                boolean z2 = (b & UByte.MAX_VALUE) > i5;
                byte[] bArr4 = z2 ? new byte[i5] : new byte[(b & UByte.MAX_VALUE)];
                System.arraycopy(bArr2, 4, bArr4, 0, bArr4.length);
                int i6 = 0;
                byte b2 = 0;
                while (i6 != 3) {
                    int i7 = i6 + 1;
                    b2 |= bArr2[i6 + 4] ^ ((byte) (bArr2[i7] ^ -1));
                    i6 = i7;
                }
                Arrays.clear(bArr2);
                if (b2 != 0) {
                    z = true;
                }
                if (!z2 && !z) {
                    return bArr4;
                }
                throw new InvalidCipherTextException("wrapped key corrupted");
            }
            throw new InvalidCipherTextException("input too short");
        }
        throw new IllegalStateException("not set for unwrapping");
    }

    public byte[] wrap(byte[] bArr, int i, int i2) {
        if (!this.forWrapping) {
            throw new IllegalStateException("not set for wrapping");
        } else if (i2 > 255 || i2 < 0) {
            throw new IllegalArgumentException("input must be from 0 to 255 bytes");
        } else {
            this.engine.init(true, this.param);
            int blockSize = this.engine.getBlockSize();
            int i3 = i2 + 4;
            int i4 = blockSize * 2;
            if (i3 >= i4) {
                i4 = i3 % blockSize == 0 ? i3 : ((i3 / blockSize) + 1) * blockSize;
            }
            byte[] bArr2 = new byte[i4];
            bArr2[0] = (byte) i2;
            System.arraycopy(bArr, i, bArr2, 4, i2);
            int length = bArr2.length - i3;
            byte[] bArr3 = new byte[length];
            this.rand.nextBytes(bArr3);
            System.arraycopy(bArr3, 0, bArr2, i3, length);
            bArr2[1] = (byte) (bArr2[4] ^ -1);
            bArr2[2] = (byte) (bArr2[5] ^ -1);
            bArr2[3] = (byte) (bArr2[6] ^ -1);
            for (int i5 = 0; i5 < bArr2.length; i5 += blockSize) {
                this.engine.processBlock(bArr2, i5, bArr2, i5);
            }
            for (int i6 = 0; i6 < bArr2.length; i6 += blockSize) {
                this.engine.processBlock(bArr2, i6, bArr2, i6);
            }
            return bArr2;
        }
    }
}
