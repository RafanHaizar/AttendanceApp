package org.bouncycastle.crypto.modes;

import androidx.core.view.ViewCompat;
import kotlin.UByte;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.StreamBlockCipher;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class GOFBBlockCipher extends StreamBlockCipher {

    /* renamed from: C1 */
    static final int f516C1 = 16843012;

    /* renamed from: C2 */
    static final int f517C2 = 16843009;

    /* renamed from: IV */
    private byte[] f518IV;

    /* renamed from: N3 */
    int f519N3;

    /* renamed from: N4 */
    int f520N4;
    private final int blockSize;
    private int byteCount;
    private final BlockCipher cipher;
    boolean firstStep = true;
    private byte[] ofbOutV;
    private byte[] ofbV;

    public GOFBBlockCipher(BlockCipher blockCipher) {
        super(blockCipher);
        this.cipher = blockCipher;
        int blockSize2 = blockCipher.getBlockSize();
        this.blockSize = blockSize2;
        if (blockSize2 == 8) {
            this.f518IV = new byte[blockCipher.getBlockSize()];
            this.ofbV = new byte[blockCipher.getBlockSize()];
            this.ofbOutV = new byte[blockCipher.getBlockSize()];
            return;
        }
        throw new IllegalArgumentException("GCTR only for 64 bit block ciphers");
    }

    private int bytesToint(byte[] bArr, int i) {
        return ((bArr[i + 3] << 24) & ViewCompat.MEASURED_STATE_MASK) + ((bArr[i + 2] << Tnaf.POW_2_WIDTH) & 16711680) + ((bArr[i + 1] << 8) & 65280) + (bArr[i] & UByte.MAX_VALUE);
    }

    private void intTobytes(int i, byte[] bArr, int i2) {
        bArr[i2 + 3] = (byte) (i >>> 24);
        bArr[i2 + 2] = (byte) (i >>> 16);
        bArr[i2 + 1] = (byte) (i >>> 8);
        bArr[i2] = (byte) i;
    }

    /* access modifiers changed from: protected */
    public byte calculateByte(byte b) {
        if (this.byteCount == 0) {
            if (this.firstStep) {
                this.firstStep = false;
                this.cipher.processBlock(this.ofbV, 0, this.ofbOutV, 0);
                this.f519N3 = bytesToint(this.ofbOutV, 0);
                this.f520N4 = bytesToint(this.ofbOutV, 4);
            }
            int i = this.f519N3 + f517C2;
            this.f519N3 = i;
            int i2 = this.f520N4 + f516C1;
            this.f520N4 = i2;
            if (i2 < f516C1 && i2 > 0) {
                this.f520N4 = i2 + 1;
            }
            intTobytes(i, this.ofbV, 0);
            intTobytes(this.f520N4, this.ofbV, 4);
            this.cipher.processBlock(this.ofbV, 0, this.ofbOutV, 0);
        }
        byte[] bArr = this.ofbOutV;
        int i3 = this.byteCount;
        int i4 = i3 + 1;
        this.byteCount = i4;
        byte b2 = (byte) (b ^ bArr[i3]);
        int i5 = this.blockSize;
        if (i4 == i5) {
            this.byteCount = 0;
            byte[] bArr2 = this.ofbV;
            System.arraycopy(bArr2, i5, bArr2, 0, bArr2.length - i5);
            byte[] bArr3 = this.ofbOutV;
            byte[] bArr4 = this.ofbV;
            int length = bArr4.length;
            int i6 = this.blockSize;
            System.arraycopy(bArr3, 0, bArr4, length - i6, i6);
        }
        return b2;
    }

    public String getAlgorithmName() {
        return this.cipher.getAlgorithmName() + "/GCTR";
    }

    public int getBlockSize() {
        return this.blockSize;
    }

    public void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException {
        BlockCipher blockCipher;
        this.firstStep = true;
        this.f519N3 = 0;
        this.f520N4 = 0;
        if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            byte[] iv = parametersWithIV.getIV();
            int length = iv.length;
            byte[] bArr = this.f518IV;
            if (length < bArr.length) {
                System.arraycopy(iv, 0, bArr, bArr.length - iv.length, iv.length);
                int i = 0;
                while (true) {
                    byte[] bArr2 = this.f518IV;
                    if (i >= bArr2.length - iv.length) {
                        break;
                    }
                    bArr2[i] = 0;
                    i++;
                }
            } else {
                System.arraycopy(iv, 0, bArr, 0, bArr.length);
            }
            reset();
            if (parametersWithIV.getParameters() != null) {
                blockCipher = this.cipher;
                cipherParameters = parametersWithIV.getParameters();
            } else {
                return;
            }
        } else {
            reset();
            if (cipherParameters != null) {
                blockCipher = this.cipher;
            } else {
                return;
            }
        }
        blockCipher.init(true, cipherParameters);
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) throws DataLengthException, IllegalStateException {
        processBytes(bArr, i, this.blockSize, bArr2, i2);
        return this.blockSize;
    }

    public void reset() {
        this.firstStep = true;
        this.f519N3 = 0;
        this.f520N4 = 0;
        byte[] bArr = this.f518IV;
        System.arraycopy(bArr, 0, this.ofbV, 0, bArr.length);
        this.byteCount = 0;
        this.cipher.reset();
    }
}
