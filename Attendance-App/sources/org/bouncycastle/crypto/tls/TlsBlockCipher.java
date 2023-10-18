package org.bouncycastle.crypto.tls;

import java.io.IOException;
import java.security.SecureRandom;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;

public class TlsBlockCipher implements TlsCipher {
    protected TlsContext context;
    protected BlockCipher decryptCipher;
    protected BlockCipher encryptCipher;
    protected boolean encryptThenMAC;
    protected byte[] randomData = new byte[256];
    protected TlsMac readMac;
    protected boolean useExplicitIV;
    protected TlsMac writeMac;

    public TlsBlockCipher(TlsContext tlsContext, BlockCipher blockCipher, BlockCipher blockCipher2, Digest digest, Digest digest2, int i) throws IOException {
        byte[] bArr;
        byte[] bArr2;
        ParametersWithIV parametersWithIV;
        ParametersWithIV parametersWithIV2;
        TlsContext tlsContext2 = tlsContext;
        BlockCipher blockCipher3 = blockCipher;
        BlockCipher blockCipher4 = blockCipher2;
        int i2 = i;
        this.context = tlsContext2;
        tlsContext.getNonceRandomGenerator().nextBytes(this.randomData);
        this.useExplicitIV = TlsUtils.isTLSv11(tlsContext);
        this.encryptThenMAC = tlsContext.getSecurityParameters().encryptThenMAC;
        int digestSize = (i2 * 2) + digest.getDigestSize() + digest2.getDigestSize();
        int blockSize = !this.useExplicitIV ? digestSize + blockCipher.getBlockSize() + blockCipher2.getBlockSize() : digestSize;
        byte[] calculateKeyBlock = TlsUtils.calculateKeyBlock(tlsContext2, blockSize);
        TlsContext tlsContext3 = tlsContext;
        byte[] bArr3 = calculateKeyBlock;
        TlsMac tlsMac = new TlsMac(tlsContext3, digest, bArr3, 0, digest.getDigestSize());
        int digestSize2 = digest.getDigestSize() + 0;
        TlsMac tlsMac2 = r1;
        TlsMac tlsMac3 = new TlsMac(tlsContext3, digest2, bArr3, digestSize2, digest2.getDigestSize());
        int digestSize3 = digestSize2 + digest2.getDigestSize();
        KeyParameter keyParameter = new KeyParameter(calculateKeyBlock, digestSize3, i2);
        int i3 = digestSize3 + i2;
        KeyParameter keyParameter2 = new KeyParameter(calculateKeyBlock, i3, i2);
        int i4 = i3 + i2;
        if (this.useExplicitIV) {
            bArr2 = new byte[blockCipher.getBlockSize()];
            bArr = new byte[blockCipher2.getBlockSize()];
        } else {
            bArr2 = Arrays.copyOfRange(calculateKeyBlock, i4, blockCipher.getBlockSize() + i4);
            int blockSize2 = i4 + blockCipher.getBlockSize();
            bArr = Arrays.copyOfRange(calculateKeyBlock, blockSize2, blockCipher2.getBlockSize() + blockSize2);
            i4 = blockSize2 + blockCipher2.getBlockSize();
        }
        if (i4 == blockSize) {
            if (tlsContext.isServer()) {
                this.writeMac = tlsMac2;
                this.readMac = tlsMac;
                this.encryptCipher = blockCipher4;
                this.decryptCipher = blockCipher3;
                parametersWithIV = new ParametersWithIV(keyParameter2, bArr);
                parametersWithIV2 = new ParametersWithIV(keyParameter, bArr2);
            } else {
                this.writeMac = tlsMac;
                this.readMac = tlsMac2;
                this.encryptCipher = blockCipher3;
                this.decryptCipher = blockCipher4;
                parametersWithIV = new ParametersWithIV(keyParameter, bArr2);
                parametersWithIV2 = new ParametersWithIV(keyParameter2, bArr);
            }
            this.encryptCipher.init(true, parametersWithIV);
            this.decryptCipher.init(false, parametersWithIV2);
            return;
        }
        throw new TlsFatalAlert(80);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0028, code lost:
        if (r8 != 0) goto L_0x0019;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int checkPaddingConstantTime(byte[] r5, int r6, int r7, int r8, int r9) {
        /*
            r4 = this;
            int r6 = r6 + r7
            int r0 = r6 + -1
            byte r0 = r5[r0]
            r1 = r0 & 255(0xff, float:3.57E-43)
            int r1 = r1 + 1
            org.bouncycastle.crypto.tls.TlsContext r2 = r4.context
            boolean r2 = org.bouncycastle.crypto.tls.TlsUtils.isSSL(r2)
            r3 = 0
            if (r2 == 0) goto L_0x0014
            if (r1 > r8) goto L_0x0017
        L_0x0014:
            int r9 = r9 + r1
            if (r9 <= r7) goto L_0x001b
        L_0x0017:
            r5 = 0
            r8 = 0
        L_0x0019:
            r1 = 0
            goto L_0x002b
        L_0x001b:
            int r7 = r6 - r1
            r8 = 0
        L_0x001e:
            int r9 = r7 + 1
            byte r7 = r5[r7]
            r7 = r7 ^ r0
            r7 = r7 | r8
            byte r8 = (byte) r7
            if (r9 < r6) goto L_0x0041
            r5 = r1
            if (r8 == 0) goto L_0x002b
            goto L_0x0019
        L_0x002b:
            byte[] r6 = r4.randomData
        L_0x002d:
            r7 = 256(0x100, float:3.59E-43)
            if (r5 >= r7) goto L_0x003a
            int r7 = r5 + 1
            byte r5 = r6[r5]
            r5 = r5 ^ r0
            r5 = r5 | r8
            byte r8 = (byte) r5
            r5 = r7
            goto L_0x002d
        L_0x003a:
            byte r5 = r6[r3]
            r5 = r5 ^ r8
            byte r5 = (byte) r5
            r6[r3] = r5
            return r1
        L_0x0041:
            r7 = r9
            goto L_0x001e
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.tls.TlsBlockCipher.checkPaddingConstantTime(byte[], int, int, int, int):int");
    }

    /* access modifiers changed from: protected */
    public int chooseExtraPadBlocks(SecureRandom secureRandom, int i) {
        return Math.min(lowestBitSet(secureRandom.nextInt()), i);
    }

    public byte[] decodeCiphertext(long j, short s, byte[] bArr, int i, int i2) throws IOException {
        int i3;
        byte[] bArr2;
        byte[] bArr3 = bArr;
        int i4 = i;
        int i5 = i2;
        int blockSize = this.decryptCipher.getBlockSize();
        int size = this.readMac.getSize();
        int max = this.encryptThenMAC ? blockSize + size : Math.max(blockSize, size + 1);
        if (this.useExplicitIV) {
            max += blockSize;
        }
        if (i5 >= max) {
            boolean z = this.encryptThenMAC;
            int i6 = z ? i5 - size : i5;
            if (i6 % blockSize == 0) {
                if (z) {
                    int i7 = i4 + i5;
                    if (!Arrays.constantTimeAreEqual(this.readMac.calculateMac(j, s, bArr, i, i5 - size), Arrays.copyOfRange(bArr3, i7 - size, i7))) {
                        throw new TlsFatalAlert(20);
                    }
                }
                if (this.useExplicitIV) {
                    this.decryptCipher.init(false, new ParametersWithIV((CipherParameters) null, bArr3, i4, blockSize));
                    i4 += blockSize;
                    i6 -= blockSize;
                }
                int i8 = i4;
                int i9 = i6;
                for (int i10 = 0; i10 < i9; i10 += blockSize) {
                    int i11 = i8 + i10;
                    this.decryptCipher.processBlock(bArr3, i11, bArr3, i11);
                }
                int checkPaddingConstantTime = checkPaddingConstantTime(bArr, i8, i9, blockSize, this.encryptThenMAC ? 0 : size);
                boolean z2 = checkPaddingConstantTime == 0;
                int i12 = i9 - checkPaddingConstantTime;
                if (!this.encryptThenMAC) {
                    i12 -= size;
                    int i13 = i8 + i12;
                    i3 = i8;
                    bArr2 = bArr3;
                    z2 |= !Arrays.constantTimeAreEqual(this.readMac.calculateMacConstantTime(j, s, bArr, i8, i12, i9 - size, this.randomData), Arrays.copyOfRange(bArr3, i13, i13 + size));
                } else {
                    i3 = i8;
                    bArr2 = bArr3;
                }
                if (!z2) {
                    return Arrays.copyOfRange(bArr2, i3, i3 + i12);
                }
                throw new TlsFatalAlert(20);
            }
            throw new TlsFatalAlert(21);
        }
        throw new TlsFatalAlert(50);
    }

    public byte[] encodePlaintext(long j, short s, byte[] bArr, int i, int i2) {
        int i3;
        int i4;
        byte[] bArr2;
        int i5 = i2;
        int blockSize = this.encryptCipher.getBlockSize();
        int size = this.writeMac.getSize();
        ProtocolVersion serverVersion = this.context.getServerVersion();
        boolean z = this.encryptThenMAC;
        int i6 = (blockSize - 1) - ((!z ? i5 + size : i5) % blockSize);
        if ((z || !this.context.getSecurityParameters().truncatedHMac) && !serverVersion.isDTLS() && !serverVersion.isSSL()) {
            i6 += chooseExtraPadBlocks(this.context.getSecureRandom(), (255 - i6) / blockSize) * blockSize;
        }
        int i7 = i6;
        int i8 = size + i5 + i7 + 1;
        boolean z2 = this.useExplicitIV;
        if (z2) {
            i8 += blockSize;
        }
        byte[] bArr3 = new byte[i8];
        if (z2) {
            byte[] bArr4 = new byte[blockSize];
            this.context.getNonceRandomGenerator().nextBytes(bArr4);
            this.encryptCipher.init(true, new ParametersWithIV((CipherParameters) null, bArr4));
            System.arraycopy(bArr4, 0, bArr3, 0, blockSize);
            bArr2 = bArr;
            i4 = i;
            i3 = blockSize + 0;
        } else {
            bArr2 = bArr;
            i4 = i;
            i3 = 0;
        }
        System.arraycopy(bArr2, i4, bArr3, i3, i5);
        int i9 = i3 + i5;
        if (!this.encryptThenMAC) {
            byte[] calculateMac = this.writeMac.calculateMac(j, s, bArr, i, i2);
            System.arraycopy(calculateMac, 0, bArr3, i9, calculateMac.length);
            i9 += calculateMac.length;
        }
        int i10 = i9;
        int i11 = 0;
        while (i11 <= i7) {
            bArr3[i10] = (byte) i7;
            i11++;
            i10++;
        }
        while (i3 < i10) {
            this.encryptCipher.processBlock(bArr3, i3, bArr3, i3);
            i3 += blockSize;
        }
        if (!this.encryptThenMAC) {
            return bArr3;
        }
        byte[] bArr5 = bArr3;
        byte[] calculateMac2 = this.writeMac.calculateMac(j, s, bArr3, 0, i10);
        System.arraycopy(calculateMac2, 0, bArr5, i10, calculateMac2.length);
        int length = calculateMac2.length;
        return bArr5;
    }

    public int getPlaintextLimit(int i) {
        int i2;
        int blockSize = this.encryptCipher.getBlockSize();
        int size = this.writeMac.getSize();
        if (this.useExplicitIV) {
            i -= blockSize;
        }
        if (this.encryptThenMAC) {
            int i3 = i - size;
            i2 = i3 - (i3 % blockSize);
        } else {
            i2 = (i - (i % blockSize)) - size;
        }
        return i2 - 1;
    }

    public TlsMac getReadMac() {
        return this.readMac;
    }

    public TlsMac getWriteMac() {
        return this.writeMac;
    }

    /* access modifiers changed from: protected */
    public int lowestBitSet(int i) {
        if (i == 0) {
            return 32;
        }
        int i2 = 0;
        while ((i & 1) == 0) {
            i2++;
            i >>= 1;
        }
        return i2;
    }
}
