package org.bouncycastle.crypto.signers;

import java.security.SecureRandom;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.SignerWithRecovery;
import org.bouncycastle.util.Arrays;

public class ISO9796d2PSSSigner implements SignerWithRecovery {
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
    private int hLen;
    private int keyBits;
    private byte[] mBuf;
    private int messageLength;
    private byte[] preBlock;
    private int preMStart;
    private byte[] preSig;
    private int preTLength;
    private SecureRandom random;
    private byte[] recoveredMessage;
    private int saltLength;
    private byte[] standardSalt;
    private int trailer;

    public ISO9796d2PSSSigner(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest2, int i) {
        this(asymmetricBlockCipher, digest2, i, false);
    }

    public ISO9796d2PSSSigner(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest2, int i, boolean z) {
        int intValue;
        this.cipher = asymmetricBlockCipher;
        this.digest = digest2;
        this.hLen = digest2.getDigestSize();
        this.saltLength = i;
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

    private void ItoOSP(int i, byte[] bArr) {
        bArr[0] = (byte) (i >>> 24);
        bArr[1] = (byte) (i >>> 16);
        bArr[2] = (byte) (i >>> 8);
        bArr[3] = (byte) (i >>> 0);
    }

    private void LtoOSP(long j, byte[] bArr) {
        bArr[0] = (byte) ((int) (j >>> 56));
        bArr[1] = (byte) ((int) (j >>> 48));
        bArr[2] = (byte) ((int) (j >>> 40));
        bArr[3] = (byte) ((int) (j >>> 32));
        bArr[4] = (byte) ((int) (j >>> 24));
        bArr[5] = (byte) ((int) (j >>> 16));
        bArr[6] = (byte) ((int) (j >>> 8));
        bArr[7] = (byte) ((int) (j >>> 0));
    }

    private void clearBlock(byte[] bArr) {
        for (int i = 0; i != bArr.length; i++) {
            bArr[i] = 0;
        }
    }

    private boolean isSameAs(byte[] bArr, byte[] bArr2) {
        boolean z = this.messageLength == bArr2.length;
        for (int i = 0; i != bArr2.length; i++) {
            if (bArr[i] != bArr2[i]) {
                z = false;
            }
        }
        return z;
    }

    private byte[] maskGeneratorFunction1(byte[] bArr, int i, int i2, int i3) {
        int i4;
        byte[] bArr2 = new byte[i3];
        byte[] bArr3 = new byte[this.hLen];
        byte[] bArr4 = new byte[4];
        this.digest.reset();
        int i5 = 0;
        while (true) {
            i4 = this.hLen;
            if (i5 >= i3 / i4) {
                break;
            }
            ItoOSP(i5, bArr4);
            this.digest.update(bArr, i, i2);
            this.digest.update(bArr4, 0, 4);
            this.digest.doFinal(bArr3, 0);
            int i6 = this.hLen;
            System.arraycopy(bArr3, 0, bArr2, i5 * i6, i6);
            i5++;
        }
        if (i4 * i5 < i3) {
            ItoOSP(i5, bArr4);
            this.digest.update(bArr, i, i2);
            this.digest.update(bArr4, 0, 4);
            this.digest.doFinal(bArr3, 0);
            int i7 = this.hLen;
            System.arraycopy(bArr3, 0, bArr2, i5 * i7, i3 - (i5 * i7));
        }
        return bArr2;
    }

    public byte[] generateSignature() throws CryptoException {
        int digestSize = this.digest.getDigestSize();
        byte[] bArr = new byte[digestSize];
        this.digest.doFinal(bArr, 0);
        byte[] bArr2 = new byte[8];
        LtoOSP((long) (this.messageLength * 8), bArr2);
        this.digest.update(bArr2, 0, 8);
        this.digest.update(this.mBuf, 0, this.messageLength);
        this.digest.update(bArr, 0, digestSize);
        byte[] bArr3 = this.standardSalt;
        if (bArr3 == null) {
            bArr3 = new byte[this.saltLength];
            this.random.nextBytes(bArr3);
        }
        this.digest.update(bArr3, 0, bArr3.length);
        int digestSize2 = this.digest.getDigestSize();
        byte[] bArr4 = new byte[digestSize2];
        this.digest.doFinal(bArr4, 0);
        boolean z = true;
        int i = this.trailer == 188 ? 1 : 2;
        byte[] bArr5 = this.block;
        int length = bArr5.length;
        int i2 = this.messageLength;
        int length2 = ((((length - i2) - bArr3.length) - this.hLen) - i) - 1;
        bArr5[length2] = 1;
        int i3 = length2 + 1;
        System.arraycopy(this.mBuf, 0, bArr5, i3, i2);
        System.arraycopy(bArr3, 0, this.block, i3 + this.messageLength, bArr3.length);
        byte[] maskGeneratorFunction1 = maskGeneratorFunction1(bArr4, 0, digestSize2, (this.block.length - this.hLen) - i);
        for (int i4 = 0; i4 != maskGeneratorFunction1.length; i4++) {
            byte[] bArr6 = this.block;
            bArr6[i4] = (byte) (bArr6[i4] ^ maskGeneratorFunction1[i4]);
        }
        byte[] bArr7 = this.block;
        int length3 = bArr7.length;
        int i5 = this.hLen;
        System.arraycopy(bArr4, 0, bArr7, (length3 - i5) - i, i5);
        int i6 = this.trailer;
        if (i6 == 188) {
            byte[] bArr8 = this.block;
            bArr8[bArr8.length - 1] = PSSSigner.TRAILER_IMPLICIT;
        } else {
            byte[] bArr9 = this.block;
            bArr9[bArr9.length - 2] = (byte) (i6 >>> 8);
            bArr9[bArr9.length - 1] = (byte) i6;
        }
        byte[] bArr10 = this.block;
        bArr10[0] = (byte) (bArr10[0] & ByteCompanionObject.MAX_VALUE);
        byte[] processBlock = this.cipher.processBlock(bArr10, 0, bArr10.length);
        int i7 = this.messageLength;
        byte[] bArr11 = new byte[i7];
        this.recoveredMessage = bArr11;
        byte[] bArr12 = this.mBuf;
        if (i7 > bArr12.length) {
            z = false;
        }
        this.fullMessage = z;
        System.arraycopy(bArr12, 0, bArr11, 0, bArr11.length);
        clearBlock(this.mBuf);
        clearBlock(this.block);
        this.messageLength = 0;
        return processBlock;
    }

    public byte[] getRecoveredMessage() {
        return this.recoveredMessage;
    }

    public boolean hasFullMessage() {
        return this.fullMessage;
    }

    /* JADX WARNING: type inference failed for: r0v2, types: [org.bouncycastle.crypto.CipherParameters] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0061  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0072  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void init(boolean r4, org.bouncycastle.crypto.CipherParameters r5) {
        /*
            r3 = this;
            int r0 = r3.saltLength
            boolean r1 = r5 instanceof org.bouncycastle.crypto.params.ParametersWithRandom
            if (r1 == 0) goto L_0x0017
            org.bouncycastle.crypto.params.ParametersWithRandom r5 = (org.bouncycastle.crypto.params.ParametersWithRandom) r5
            org.bouncycastle.crypto.CipherParameters r1 = r5.getParameters()
            org.bouncycastle.crypto.params.RSAKeyParameters r1 = (org.bouncycastle.crypto.params.RSAKeyParameters) r1
            if (r4 == 0) goto L_0x0043
            java.security.SecureRandom r5 = r5.getRandom()
        L_0x0014:
            r3.random = r5
            goto L_0x0043
        L_0x0017:
            boolean r1 = r5 instanceof org.bouncycastle.crypto.params.ParametersWithSalt
            if (r1 == 0) goto L_0x0039
            org.bouncycastle.crypto.params.ParametersWithSalt r5 = (org.bouncycastle.crypto.params.ParametersWithSalt) r5
            org.bouncycastle.crypto.CipherParameters r0 = r5.getParameters()
            r1 = r0
            org.bouncycastle.crypto.params.RSAKeyParameters r1 = (org.bouncycastle.crypto.params.RSAKeyParameters) r1
            byte[] r5 = r5.getSalt()
            r3.standardSalt = r5
            int r0 = r5.length
            int r5 = r5.length
            int r2 = r3.saltLength
            if (r5 != r2) goto L_0x0031
            goto L_0x0043
        L_0x0031:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.String r5 = "Fixed salt is of wrong length"
            r4.<init>(r5)
            throw r4
        L_0x0039:
            r1 = r5
            org.bouncycastle.crypto.params.RSAKeyParameters r1 = (org.bouncycastle.crypto.params.RSAKeyParameters) r1
            if (r4 == 0) goto L_0x0043
            java.security.SecureRandom r5 = org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom()
            goto L_0x0014
        L_0x0043:
            org.bouncycastle.crypto.AsymmetricBlockCipher r5 = r3.cipher
            r5.init(r4, r1)
            java.math.BigInteger r4 = r1.getModulus()
            int r4 = r4.bitLength()
            r3.keyBits = r4
            int r4 = r4 + 7
            int r4 = r4 / 8
            byte[] r4 = new byte[r4]
            r3.block = r4
            int r5 = r3.trailer
            r1 = 188(0xbc, float:2.63E-43)
            int r4 = r4.length
            if (r5 != r1) goto L_0x0072
            org.bouncycastle.crypto.Digest r5 = r3.digest
            int r5 = r5.getDigestSize()
            int r4 = r4 - r5
            int r4 = r4 - r0
            int r4 = r4 + -1
            int r4 = r4 + -1
            byte[] r4 = new byte[r4]
            r3.mBuf = r4
            goto L_0x0082
        L_0x0072:
            org.bouncycastle.crypto.Digest r5 = r3.digest
            int r5 = r5.getDigestSize()
            int r4 = r4 - r5
            int r4 = r4 - r0
            int r4 = r4 + -1
            int r4 = r4 + -2
            byte[] r4 = new byte[r4]
            r3.mBuf = r4
        L_0x0082:
            r3.reset()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.signers.ISO9796d2PSSSigner.init(boolean, org.bouncycastle.crypto.CipherParameters):void");
    }

    public void reset() {
        this.digest.reset();
        this.messageLength = 0;
        byte[] bArr = this.mBuf;
        if (bArr != null) {
            clearBlock(bArr);
        }
        byte[] bArr2 = this.recoveredMessage;
        if (bArr2 != null) {
            clearBlock(bArr2);
            this.recoveredMessage = null;
        }
        this.fullMessage = false;
        if (this.preSig != null) {
            this.preSig = null;
            clearBlock(this.preBlock);
            this.preBlock = null;
        }
    }

    public void update(byte b) {
        if (this.preSig == null) {
            int i = this.messageLength;
            byte[] bArr = this.mBuf;
            if (i < bArr.length) {
                this.messageLength = i + 1;
                bArr[i] = b;
                return;
            }
        }
        this.digest.update(b);
    }

    public void update(byte[] bArr, int i, int i2) {
        if (this.preSig == null) {
            while (i2 > 0 && this.messageLength < this.mBuf.length) {
                update(bArr[i]);
                i++;
                i2--;
            }
        }
        if (i2 > 0) {
            this.digest.update(bArr, i, i2);
        }
    }

    public void updateWithRecoveredMessage(byte[] bArr) throws InvalidCipherTextException {
        int i;
        byte[] processBlock = this.cipher.processBlock(bArr, 0, bArr.length);
        int length = processBlock.length;
        int i2 = this.keyBits;
        if (length < (i2 + 7) / 8) {
            int i3 = (i2 + 7) / 8;
            byte[] bArr2 = new byte[i3];
            System.arraycopy(processBlock, 0, bArr2, i3 - processBlock.length, processBlock.length);
            clearBlock(processBlock);
            processBlock = bArr2;
        }
        boolean z = true;
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
        this.digest.doFinal(new byte[this.hLen], 0);
        int length2 = processBlock.length;
        int i4 = this.hLen;
        byte[] maskGeneratorFunction1 = maskGeneratorFunction1(processBlock, (length2 - i4) - i, i4, (processBlock.length - i4) - i);
        for (int i5 = 0; i5 != maskGeneratorFunction1.length; i5++) {
            processBlock[i5] = (byte) (processBlock[i5] ^ maskGeneratorFunction1[i5]);
        }
        processBlock[0] = (byte) (processBlock[0] & ByteCompanionObject.MAX_VALUE);
        int i6 = 0;
        while (i6 != processBlock.length && processBlock[i6] != 1) {
            i6++;
        }
        int i7 = i6 + 1;
        if (i7 >= processBlock.length) {
            clearBlock(processBlock);
        }
        if (i7 <= 1) {
            z = false;
        }
        this.fullMessage = z;
        byte[] bArr3 = new byte[((maskGeneratorFunction1.length - i7) - this.saltLength)];
        this.recoveredMessage = bArr3;
        System.arraycopy(processBlock, i7, bArr3, 0, bArr3.length);
        byte[] bArr4 = this.recoveredMessage;
        System.arraycopy(bArr4, 0, this.mBuf, 0, bArr4.length);
        this.preSig = bArr;
        this.preBlock = processBlock;
        this.preMStart = i7;
        this.preTLength = i;
    }

    public boolean verifySignature(byte[] bArr) {
        int i = this.hLen;
        byte[] bArr2 = new byte[i];
        this.digest.doFinal(bArr2, 0);
        byte[] bArr3 = this.preSig;
        if (bArr3 == null) {
            try {
                updateWithRecoveredMessage(bArr);
            } catch (Exception e) {
                return false;
            }
        } else if (!Arrays.areEqual(bArr3, bArr)) {
            throw new IllegalStateException("updateWithRecoveredMessage called on different signature");
        }
        byte[] bArr4 = this.preBlock;
        int i2 = this.preMStart;
        int i3 = this.preTLength;
        this.preSig = null;
        this.preBlock = null;
        byte[] bArr5 = new byte[8];
        LtoOSP((long) (this.recoveredMessage.length * 8), bArr5);
        this.digest.update(bArr5, 0, 8);
        byte[] bArr6 = this.recoveredMessage;
        if (bArr6.length != 0) {
            this.digest.update(bArr6, 0, bArr6.length);
        }
        this.digest.update(bArr2, 0, i);
        byte[] bArr7 = this.standardSalt;
        if (bArr7 != null) {
            this.digest.update(bArr7, 0, bArr7.length);
        } else {
            this.digest.update(bArr4, i2 + this.recoveredMessage.length, this.saltLength);
        }
        int digestSize = this.digest.getDigestSize();
        byte[] bArr8 = new byte[digestSize];
        this.digest.doFinal(bArr8, 0);
        int length = (bArr4.length - i3) - digestSize;
        boolean z = true;
        for (int i4 = 0; i4 != digestSize; i4++) {
            if (bArr8[i4] != bArr4[length + i4]) {
                z = false;
            }
        }
        clearBlock(bArr4);
        clearBlock(bArr8);
        if (!z) {
            this.fullMessage = false;
            this.messageLength = 0;
            clearBlock(this.recoveredMessage);
            return false;
        } else if (this.messageLength == 0 || isSameAs(this.mBuf, this.recoveredMessage)) {
            this.messageLength = 0;
            clearBlock(this.mBuf);
            return true;
        } else {
            this.messageLength = 0;
            clearBlock(this.mBuf);
            return false;
        }
    }
}
