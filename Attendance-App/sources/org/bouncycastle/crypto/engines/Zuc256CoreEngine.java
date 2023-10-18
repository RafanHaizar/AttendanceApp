package org.bouncycastle.crypto.engines;

import kotlin.UByte;
import org.bouncycastle.util.Memoable;

public class Zuc256CoreEngine extends Zuc128CoreEngine {
    private static final byte[] EK_d = {34, 47, 36, 42, 109, 64, 64, 64, 64, 64, 64, 64, 64, 82, Tnaf.POW_2_WIDTH, 48};
    private static final byte[] EK_d128 = {35, 47, 37, 42, 109, 64, 64, 64, 64, 64, 64, 64, 64, 82, Tnaf.POW_2_WIDTH, 48};
    private static final byte[] EK_d32 = {34, 47, 37, 42, 109, 64, 64, 64, 64, 64, 64, 64, 64, 82, Tnaf.POW_2_WIDTH, 48};
    private static final byte[] EK_d64 = {35, 47, 36, 42, 109, 64, 64, 64, 64, 64, 64, 64, 64, 82, Tnaf.POW_2_WIDTH, 48};
    private byte[] theD;

    protected Zuc256CoreEngine() {
        this.theD = EK_d;
    }

    protected Zuc256CoreEngine(int i) {
        switch (i) {
            case 32:
                this.theD = EK_d32;
                return;
            case 64:
                this.theD = EK_d64;
                return;
            case 128:
                this.theD = EK_d128;
                return;
            default:
                throw new IllegalArgumentException("Unsupported length: " + i);
        }
    }

    protected Zuc256CoreEngine(Zuc256CoreEngine zuc256CoreEngine) {
        super(zuc256CoreEngine);
    }

    private static int MAKEU31(byte b, byte b2, byte b3, byte b4) {
        return ((b & UByte.MAX_VALUE) << 23) | ((b2 & UByte.MAX_VALUE) << Tnaf.POW_2_WIDTH) | ((b3 & UByte.MAX_VALUE) << 8) | (b4 & UByte.MAX_VALUE);
    }

    public Memoable copy() {
        return new Zuc256CoreEngine(this);
    }

    public String getAlgorithmName() {
        return "Zuc-256";
    }

    /* access modifiers changed from: protected */
    public int getMaxIterations() {
        return 625;
    }

    public void reset(Memoable memoable) {
        super.reset(memoable);
        this.theD = ((Zuc256CoreEngine) memoable).theD;
    }

    /* access modifiers changed from: protected */
    public void setKeyAndIV(int[] iArr, byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = bArr;
        byte[] bArr4 = bArr2;
        if (bArr3 == null || bArr3.length != 32) {
            throw new IllegalArgumentException("A key of 32 bytes is needed");
        } else if (bArr4 == null || bArr4.length != 25) {
            throw new IllegalArgumentException("An IV of 25 bytes is needed");
        } else {
            iArr[0] = MAKEU31(bArr3[0], this.theD[0], bArr3[21], bArr3[16]);
            iArr[1] = MAKEU31(bArr3[1], this.theD[1], bArr3[22], bArr3[17]);
            iArr[2] = MAKEU31(bArr3[2], this.theD[2], bArr3[23], bArr3[18]);
            iArr[3] = MAKEU31(bArr3[3], this.theD[3], bArr3[24], bArr3[19]);
            iArr[4] = MAKEU31(bArr3[4], this.theD[4], bArr3[25], bArr3[20]);
            iArr[5] = MAKEU31(bArr4[0], (byte) (this.theD[5] | (bArr4[17] & 63)), bArr3[5], bArr3[26]);
            iArr[6] = MAKEU31(bArr4[1], (byte) (this.theD[6] | (bArr4[18] & 63)), bArr3[6], bArr3[27]);
            iArr[7] = MAKEU31(bArr4[10], (byte) (this.theD[7] | (bArr4[19] & 63)), bArr3[7], bArr4[2]);
            iArr[8] = MAKEU31(bArr3[8], (byte) (this.theD[8] | (bArr4[20] & 63)), bArr4[3], bArr4[11]);
            iArr[9] = MAKEU31(bArr3[9], (byte) ((bArr4[21] & 63) | this.theD[9]), bArr4[12], bArr4[4]);
            iArr[10] = MAKEU31(bArr4[5], (byte) (this.theD[10] | (bArr4[22] & 63)), bArr3[10], bArr3[28]);
            iArr[11] = MAKEU31(bArr3[11], (byte) (this.theD[11] | (bArr4[23] & 63)), bArr4[6], bArr4[13]);
            iArr[12] = MAKEU31(bArr3[12], (byte) (this.theD[12] | (bArr4[24] & 63)), bArr4[7], bArr4[14]);
            iArr[13] = MAKEU31(bArr3[13], this.theD[13], bArr4[15], bArr4[8]);
            iArr[14] = MAKEU31(bArr3[14], (byte) (this.theD[14] | ((bArr3[31] >>> 4) & 15)), bArr4[16], bArr4[9]);
            iArr[15] = MAKEU31(bArr3[15], (byte) (this.theD[15] | (bArr3[31] & 15)), bArr3[30], bArr3[29]);
        }
    }
}
