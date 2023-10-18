package org.bouncycastle.crypto.signers;

import java.math.BigInteger;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.util.BigIntegers;

public class X931Signer implements Signer {
    public static final int TRAILER_IMPLICIT = 188;
    public static final int TRAILER_RIPEMD128 = 13004;
    public static final int TRAILER_RIPEMD160 = 12748;
    public static final int TRAILER_SHA1 = 13260;
    public static final int TRAILER_SHA224 = 14540;
    public static final int TRAILER_SHA256 = 13516;
    public static final int TRAILER_SHA384 = 14028;
    public static final int TRAILER_SHA512 = 13772;
    public static final int TRAILER_WHIRLPOOL = 14284;
    private byte[] block;
    private AsymmetricBlockCipher cipher;
    private Digest digest;
    private RSAKeyParameters kParam;
    private int keyBits;
    private int trailer;

    public X931Signer(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest2) {
        this(asymmetricBlockCipher, digest2, false);
    }

    public X931Signer(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest2, boolean z) {
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

    private void createSignatureBlock(int i) {
        int i2;
        int digestSize = this.digest.getDigestSize();
        if (i == 188) {
            byte[] bArr = this.block;
            i2 = (bArr.length - digestSize) - 1;
            this.digest.doFinal(bArr, i2);
            byte[] bArr2 = this.block;
            bArr2[bArr2.length - 1] = PSSSigner.TRAILER_IMPLICIT;
        } else {
            byte[] bArr3 = this.block;
            int length = (bArr3.length - digestSize) - 2;
            this.digest.doFinal(bArr3, length);
            byte[] bArr4 = this.block;
            bArr4[bArr4.length - 2] = (byte) (i >>> 8);
            bArr4[bArr4.length - 1] = (byte) i;
            i2 = length;
        }
        this.block[0] = 107;
        for (int i3 = i2 - 2; i3 != 0; i3--) {
            this.block[i3] = -69;
        }
        this.block[i2 - 1] = -70;
    }

    public byte[] generateSignature() throws CryptoException {
        createSignatureBlock(this.trailer);
        AsymmetricBlockCipher asymmetricBlockCipher = this.cipher;
        byte[] bArr = this.block;
        BigInteger bigInteger = new BigInteger(1, asymmetricBlockCipher.processBlock(bArr, 0, bArr.length));
        clearBlock(this.block);
        return BigIntegers.asUnsignedByteArray(BigIntegers.getUnsignedByteLength(this.kParam.getModulus()), bigInteger.min(this.kParam.getModulus().subtract(bigInteger)));
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        RSAKeyParameters rSAKeyParameters = (RSAKeyParameters) cipherParameters;
        this.kParam = rSAKeyParameters;
        this.cipher.init(z, rSAKeyParameters);
        int bitLength = this.kParam.getModulus().bitLength();
        this.keyBits = bitLength;
        this.block = new byte[((bitLength + 7) / 8)];
        reset();
    }

    public void reset() {
        this.digest.reset();
    }

    public void update(byte b) {
        this.digest.update(b);
    }

    public void update(byte[] bArr, int i, int i2) {
        this.digest.update(bArr, i, i2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x002d, code lost:
        if ((r4.intValue() & 15) == 12) goto L_0x002f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean verifySignature(byte[] r4) {
        /*
            r3 = this;
            r0 = 0
            org.bouncycastle.crypto.AsymmetricBlockCipher r1 = r3.cipher     // Catch:{ Exception -> 0x005f }
            int r2 = r4.length     // Catch:{ Exception -> 0x005f }
            byte[] r4 = r1.processBlock(r4, r0, r2)     // Catch:{ Exception -> 0x005f }
            r3.block = r4     // Catch:{ Exception -> 0x005f }
            java.math.BigInteger r4 = new java.math.BigInteger
            r1 = 1
            byte[] r2 = r3.block
            r4.<init>(r1, r2)
            int r1 = r4.intValue()
            r1 = r1 & 15
            r2 = 12
            if (r1 != r2) goto L_0x001d
            goto L_0x002f
        L_0x001d:
            org.bouncycastle.crypto.params.RSAKeyParameters r1 = r3.kParam
            java.math.BigInteger r1 = r1.getModulus()
            java.math.BigInteger r4 = r1.subtract(r4)
            int r1 = r4.intValue()
            r1 = r1 & 15
            if (r1 != r2) goto L_0x005e
        L_0x002f:
            int r0 = r3.trailer
            r3.createSignatureBlock(r0)
            byte[] r0 = r3.block
            int r0 = r0.length
            byte[] r4 = org.bouncycastle.util.BigIntegers.asUnsignedByteArray(r0, r4)
            byte[] r0 = r3.block
            boolean r0 = org.bouncycastle.util.Arrays.constantTimeAreEqual(r0, r4)
            int r1 = r3.trailer
            r2 = 15052(0x3acc, float:2.1092E-41)
            if (r1 != r2) goto L_0x0056
            if (r0 != 0) goto L_0x0056
            byte[] r0 = r3.block
            int r1 = r0.length
            int r1 = r1 + -2
            r2 = 64
            r0[r1] = r2
            boolean r0 = org.bouncycastle.util.Arrays.constantTimeAreEqual(r0, r4)
        L_0x0056:
            byte[] r1 = r3.block
            r3.clearBlock(r1)
            r3.clearBlock(r4)
        L_0x005e:
            return r0
        L_0x005f:
            r4 = move-exception
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.signers.X931Signer.verifySignature(byte[]):boolean");
    }
}
