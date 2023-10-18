package org.bouncycastle.crypto.signers;

import kotlin.UByte;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.SignerWithRecovery;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.util.Arrays;

public class ISO9796d2Signer implements SignerWithRecovery {
    public static final int TRAILER_IMPLICIT = 188;
    public static final int TRAILER_RIPEMD128 = 13004;
    public static final int TRAILER_RIPEMD160 = 12748;
    public static final int TRAILER_SHA1 = 13260;
    public static final int TRAILER_SHA256 = 13516;
    public static final int TRAILER_SHA384 = 14028;
    public static final int TRAILER_SHA512 = 13772;
    public static final int TRAILER_WHIRLPOOL = 14284;
    private byte[] block;
    private AsymmetricBlockCipher cipher;
    private Digest digest;
    private boolean fullMessage;
    private int keyBits;
    private byte[] mBuf;
    private int messageLength;
    private byte[] preBlock;
    private byte[] preSig;
    private byte[] recoveredMessage;
    private int trailer;

    public ISO9796d2Signer(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest2) {
        this(asymmetricBlockCipher, digest2, false);
    }

    public ISO9796d2Signer(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest2, boolean z) {
        int intValue;
        this.cipher = asymmetricBlockCipher;
        this.digest = digest2;
        if (z) {
            intValue = 188;
        } else {
            Integer trailer2 = ISOTrailers.getTrailer(digest2);
            if (trailer2 != null) {
                intValue = trailer2.intValue();
            } else {
                throw new IllegalArgumentException("no valid trailer for digest: " + digest2.getAlgorithmName());
            }
        }
        this.trailer = intValue;
    }

    private void clearBlock(byte[] bArr) {
        for (int i = 0; i != bArr.length; i++) {
            bArr[i] = 0;
        }
    }

    private boolean isSameAs(byte[] bArr, byte[] bArr2) {
        int i = this.messageLength;
        byte[] bArr3 = this.mBuf;
        boolean z = true;
        if (i > bArr3.length) {
            if (bArr3.length > bArr2.length) {
                z = false;
            }
            for (int i2 = 0; i2 != this.mBuf.length; i2++) {
                if (bArr[i2] != bArr2[i2]) {
                    z = false;
                }
            }
        } else {
            if (i != bArr2.length) {
                z = false;
            }
            for (int i3 = 0; i3 != bArr2.length; i3++) {
                if (bArr[i3] != bArr2[i3]) {
                    z = false;
                }
            }
        }
        return z;
    }

    private boolean returnFalse(byte[] bArr) {
        this.messageLength = 0;
        clearBlock(this.mBuf);
        clearBlock(bArr);
        return false;
    }

    public byte[] generateSignature() throws CryptoException {
        int i;
        int i2;
        int i3;
        int i4;
        int digestSize = this.digest.getDigestSize();
        boolean z = true;
        if (this.trailer == 188) {
            byte[] bArr = this.block;
            i = (bArr.length - digestSize) - 1;
            this.digest.doFinal(bArr, i);
            byte[] bArr2 = this.block;
            bArr2[bArr2.length - 1] = PSSSigner.TRAILER_IMPLICIT;
            i2 = 8;
        } else {
            byte[] bArr3 = this.block;
            i = (bArr3.length - digestSize) - 2;
            this.digest.doFinal(bArr3, i);
            byte[] bArr4 = this.block;
            int i5 = this.trailer;
            bArr4[bArr4.length - 2] = (byte) (i5 >>> 8);
            bArr4[bArr4.length - 1] = (byte) i5;
            i2 = 16;
        }
        int i6 = this.messageLength;
        int i7 = ((((digestSize + i6) * 8) + i2) + 4) - this.keyBits;
        if (i7 > 0) {
            int i8 = i6 - ((i7 + 7) / 8);
            i3 = i - i8;
            System.arraycopy(this.mBuf, 0, this.block, i3, i8);
            this.recoveredMessage = new byte[i8];
            i4 = 96;
        } else {
            i3 = i - i6;
            System.arraycopy(this.mBuf, 0, this.block, i3, i6);
            this.recoveredMessage = new byte[this.messageLength];
            i4 = 64;
        }
        int i9 = i3 - 1;
        if (i9 > 0) {
            for (int i10 = i9; i10 != 0; i10--) {
                this.block[i10] = -69;
            }
            byte[] bArr5 = this.block;
            bArr5[i9] = (byte) (bArr5[i9] ^ 1);
            bArr5[0] = 11;
            bArr5[0] = (byte) (11 | i4);
        } else {
            byte[] bArr6 = this.block;
            bArr6[0] = 10;
            bArr6[0] = (byte) (10 | i4);
        }
        AsymmetricBlockCipher asymmetricBlockCipher = this.cipher;
        byte[] bArr7 = this.block;
        byte[] processBlock = asymmetricBlockCipher.processBlock(bArr7, 0, bArr7.length);
        if ((i4 & 32) != 0) {
            z = false;
        }
        this.fullMessage = z;
        byte[] bArr8 = this.mBuf;
        byte[] bArr9 = this.recoveredMessage;
        System.arraycopy(bArr8, 0, bArr9, 0, bArr9.length);
        this.messageLength = 0;
        clearBlock(this.mBuf);
        clearBlock(this.block);
        return processBlock;
    }

    public byte[] getRecoveredMessage() {
        return this.recoveredMessage;
    }

    public boolean hasFullMessage() {
        return this.fullMessage;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        RSAKeyParameters rSAKeyParameters = (RSAKeyParameters) cipherParameters;
        this.cipher.init(z, rSAKeyParameters);
        int bitLength = rSAKeyParameters.getModulus().bitLength();
        this.keyBits = bitLength;
        byte[] bArr = new byte[((bitLength + 7) / 8)];
        this.block = bArr;
        int i = this.trailer;
        int length = bArr.length;
        if (i == 188) {
            this.mBuf = new byte[((length - this.digest.getDigestSize()) - 2)];
        } else {
            this.mBuf = new byte[((length - this.digest.getDigestSize()) - 3)];
        }
        reset();
    }

    public void reset() {
        this.digest.reset();
        this.messageLength = 0;
        clearBlock(this.mBuf);
        byte[] bArr = this.recoveredMessage;
        if (bArr != null) {
            clearBlock(bArr);
        }
        this.recoveredMessage = null;
        this.fullMessage = false;
        if (this.preSig != null) {
            this.preSig = null;
            clearBlock(this.preBlock);
            this.preBlock = null;
        }
    }

    public void update(byte b) {
        this.digest.update(b);
        int i = this.messageLength;
        byte[] bArr = this.mBuf;
        if (i < bArr.length) {
            bArr[i] = b;
        }
        this.messageLength = i + 1;
    }

    public void update(byte[] bArr, int i, int i2) {
        while (i2 > 0 && this.messageLength < this.mBuf.length) {
            update(bArr[i]);
            i++;
            i2--;
        }
        this.digest.update(bArr, i, i2);
        this.messageLength += i2;
    }

    public void updateWithRecoveredMessage(byte[] bArr) throws InvalidCipherTextException {
        int i;
        byte[] processBlock = this.cipher.processBlock(bArr, 0, bArr.length);
        if (((processBlock[0] & 192) ^ 64) != 0) {
            throw new InvalidCipherTextException("malformed signature");
        } else if (((processBlock[processBlock.length - 1] & 15) ^ 12) == 0) {
            if (((processBlock[processBlock.length - 1] & UByte.MAX_VALUE) ^ PSSSigner.TRAILER_IMPLICIT) == 0) {
                i = 1;
            } else {
                i = 2;
                byte b = ((processBlock[processBlock.length - 2] & UByte.MAX_VALUE) << 8) | (processBlock[processBlock.length - 1] & UByte.MAX_VALUE);
                Integer trailer2 = ISOTrailers.getTrailer(this.digest);
                if (trailer2 != null) {
                    int intValue = trailer2.intValue();
                    if (!(b == intValue || (intValue == 15052 && b == 16588))) {
                        throw new IllegalStateException("signer initialised with wrong digest for trailer " + b);
                    }
                } else {
                    throw new IllegalArgumentException("unrecognised hash in signature");
                }
            }
            int i2 = 0;
            while (i2 != processBlock.length && ((processBlock[i2] & 15) ^ 10) != 0) {
                i2++;
            }
            int i3 = i2 + 1;
            int length = ((processBlock.length - i) - this.digest.getDigestSize()) - i3;
            if (length > 0) {
                if ((processBlock[0] & 32) == 0) {
                    this.fullMessage = true;
                    byte[] bArr2 = new byte[length];
                    this.recoveredMessage = bArr2;
                    System.arraycopy(processBlock, i3, bArr2, 0, bArr2.length);
                } else {
                    this.fullMessage = false;
                    byte[] bArr3 = new byte[length];
                    this.recoveredMessage = bArr3;
                    System.arraycopy(processBlock, i3, bArr3, 0, bArr3.length);
                }
                this.preSig = bArr;
                this.preBlock = processBlock;
                Digest digest2 = this.digest;
                byte[] bArr4 = this.recoveredMessage;
                digest2.update(bArr4, 0, bArr4.length);
                byte[] bArr5 = this.recoveredMessage;
                this.messageLength = bArr5.length;
                System.arraycopy(bArr5, 0, this.mBuf, 0, bArr5.length);
                return;
            }
            throw new InvalidCipherTextException("malformed block");
        } else {
            throw new InvalidCipherTextException("malformed signature");
        }
    }

    public boolean verifySignature(byte[] bArr) {
        byte[] bArr2;
        int i;
        byte[] bArr3 = this.preSig;
        if (bArr3 == null) {
            try {
                bArr2 = this.cipher.processBlock(bArr, 0, bArr.length);
            } catch (Exception e) {
                return false;
            }
        } else if (Arrays.areEqual(bArr3, bArr)) {
            bArr2 = this.preBlock;
            this.preSig = null;
            this.preBlock = null;
        } else {
            throw new IllegalStateException("updateWithRecoveredMessage called on different signature");
        }
        if (((bArr2[0] & 192) ^ 64) != 0) {
            return returnFalse(bArr2);
        }
        if (((bArr2[bArr2.length - 1] & 15) ^ 12) != 0) {
            return returnFalse(bArr2);
        }
        if (((bArr2[bArr2.length - 1] & UByte.MAX_VALUE) ^ PSSSigner.TRAILER_IMPLICIT) == 0) {
            i = 1;
        } else {
            i = 2;
            byte b = ((bArr2[bArr2.length - 2] & UByte.MAX_VALUE) << 8) | (bArr2[bArr2.length - 1] & UByte.MAX_VALUE);
            Integer trailer2 = ISOTrailers.getTrailer(this.digest);
            if (trailer2 != null) {
                int intValue = trailer2.intValue();
                if (!(b == intValue || (intValue == 15052 && b == 16588))) {
                    throw new IllegalStateException("signer initialised with wrong digest for trailer " + b);
                }
            } else {
                throw new IllegalArgumentException("unrecognised hash in signature");
            }
        }
        int i2 = 0;
        while (i2 != bArr2.length && ((bArr2[i2] & 15) ^ 10) != 0) {
            i2++;
        }
        int i3 = i2 + 1;
        int digestSize = this.digest.getDigestSize();
        byte[] bArr4 = new byte[digestSize];
        int length = (bArr2.length - i) - digestSize;
        int i4 = length - i3;
        if (i4 <= 0) {
            return returnFalse(bArr2);
        }
        if ((bArr2[0] & 32) == 0) {
            this.fullMessage = true;
            if (this.messageLength > i4) {
                return returnFalse(bArr2);
            }
            this.digest.reset();
            this.digest.update(bArr2, i3, i4);
            this.digest.doFinal(bArr4, 0);
            boolean z = true;
            for (int i5 = 0; i5 != digestSize; i5++) {
                int i6 = length + i5;
                byte b2 = (byte) (bArr2[i6] ^ bArr4[i5]);
                bArr2[i6] = b2;
                if (b2 != 0) {
                    z = false;
                }
            }
            if (!z) {
                return returnFalse(bArr2);
            }
            byte[] bArr5 = new byte[i4];
            this.recoveredMessage = bArr5;
            System.arraycopy(bArr2, i3, bArr5, 0, bArr5.length);
        } else {
            this.fullMessage = false;
            this.digest.doFinal(bArr4, 0);
            boolean z2 = true;
            for (int i7 = 0; i7 != digestSize; i7++) {
                int i8 = length + i7;
                byte b3 = (byte) (bArr2[i8] ^ bArr4[i7]);
                bArr2[i8] = b3;
                if (b3 != 0) {
                    z2 = false;
                }
            }
            if (!z2) {
                return returnFalse(bArr2);
            }
            byte[] bArr6 = new byte[i4];
            this.recoveredMessage = bArr6;
            System.arraycopy(bArr2, i3, bArr6, 0, bArr6.length);
        }
        if (this.messageLength != 0 && !isSameAs(this.mBuf, this.recoveredMessage)) {
            return returnFalse(bArr2);
        }
        clearBlock(this.mBuf);
        clearBlock(bArr2);
        this.messageLength = 0;
        return true;
    }
}
