package org.bouncycastle.crypto.digests;

import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;
import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.crypto.signers.PSSSigner;
import org.bouncycastle.util.Memoable;

public class MD2Digest implements ExtendedDigest, Memoable {
    private static final int DIGEST_LENGTH = 16;

    /* renamed from: S */
    private static final byte[] f214S = {41, 46, 67, -55, -94, -40, 124, 1, 61, 54, 84, -95, -20, -16, 6, 19, 98, -89, 5, -13, -64, -57, 115, -116, -104, -109, 43, -39, PSSSigner.TRAILER_IMPLICIT, 76, -126, -54, 30, -101, 87, 60, -3, -44, -32, 22, 103, 66, 111, 24, -118, 23, -27, 18, -66, 78, -60, -42, -38, -98, -34, 73, -96, -5, -11, -114, -69, 47, -18, 122, -87, 104, 121, -111, 21, -78, 7, 63, -108, -62, Tnaf.POW_2_WIDTH, -119, 11, 34, 95, 33, ByteCompanionObject.MIN_VALUE, ByteCompanionObject.MAX_VALUE, 93, -102, 90, -112, 50, 39, 53, 62, -52, -25, -65, -9, -105, 3, -1, 25, 48, -77, 72, -91, -75, -47, -41, 94, -110, 42, -84, 86, -86, -58, 79, -72, 56, -46, -106, -92, 125, -74, 118, -4, 107, -30, -100, 116, 4, -15, 69, -99, 112, 89, 100, 113, -121, 32, -122, 91, -49, 101, -26, 45, -88, 2, 27, 96, 37, -83, -82, -80, -71, -10, 28, 70, 97, 105, 52, 64, 126, 15, 85, 71, -93, 35, -35, 81, -81, 58, -61, 92, -7, -50, -70, -59, -22, 38, 44, 83, 13, 110, -123, 40, -124, 9, -45, -33, -51, -12, 65, -127, 77, 82, 106, -36, 55, -56, 108, -63, -85, -6, 36, -31, 123, 8, 12, -67, -79, 74, 120, -120, -107, -117, -29, 99, -24, 109, -23, -53, -43, -2, 59, 0, 29, 57, -14, -17, -73, 14, 102, 88, -48, -28, -90, 119, 114, -8, -21, 117, 75, 10, 49, 68, 80, -76, -113, -19, 31, 26, -37, -103, -115, 51, -97, 17, -125, 20};

    /* renamed from: C */
    private byte[] f215C = new byte[16];
    private int COff;

    /* renamed from: M */
    private byte[] f216M = new byte[16];

    /* renamed from: X */
    private byte[] f217X = new byte[48];
    private int mOff;
    private int xOff;

    public MD2Digest() {
        reset();
    }

    public MD2Digest(MD2Digest mD2Digest) {
        copyIn(mD2Digest);
    }

    private void copyIn(MD2Digest mD2Digest) {
        byte[] bArr = mD2Digest.f217X;
        System.arraycopy(bArr, 0, this.f217X, 0, bArr.length);
        this.xOff = mD2Digest.xOff;
        byte[] bArr2 = mD2Digest.f216M;
        System.arraycopy(bArr2, 0, this.f216M, 0, bArr2.length);
        this.mOff = mD2Digest.mOff;
        byte[] bArr3 = mD2Digest.f215C;
        System.arraycopy(bArr3, 0, this.f215C, 0, bArr3.length);
        this.COff = mD2Digest.COff;
    }

    public Memoable copy() {
        return new MD2Digest(this);
    }

    public int doFinal(byte[] bArr, int i) {
        int length = this.f216M.length;
        int i2 = this.mOff;
        byte b = (byte) (length - i2);
        while (true) {
            byte[] bArr2 = this.f216M;
            if (i2 < bArr2.length) {
                bArr2[i2] = b;
                i2++;
            } else {
                processCheckSum(bArr2);
                processBlock(this.f216M);
                processBlock(this.f215C);
                System.arraycopy(this.f217X, this.xOff, bArr, i, 16);
                reset();
                return 16;
            }
        }
    }

    public String getAlgorithmName() {
        return "MD2";
    }

    public int getByteLength() {
        return 16;
    }

    public int getDigestSize() {
        return 16;
    }

    /* access modifiers changed from: protected */
    public void processBlock(byte[] bArr) {
        for (int i = 0; i < 16; i++) {
            byte[] bArr2 = this.f217X;
            bArr2[i + 16] = bArr[i];
            bArr2[i + 32] = (byte) (bArr[i] ^ bArr2[i]);
        }
        int i2 = 0;
        for (int i3 = 0; i3 < 18; i3++) {
            for (int i4 = 0; i4 < 48; i4++) {
                byte[] bArr3 = this.f217X;
                byte b = (byte) (f214S[i2] ^ bArr3[i4]);
                bArr3[i4] = b;
                i2 = b & UByte.MAX_VALUE;
            }
            i2 = (i2 + i3) % 256;
        }
    }

    /* access modifiers changed from: protected */
    public void processCheckSum(byte[] bArr) {
        byte b = this.f215C[15];
        for (int i = 0; i < 16; i++) {
            byte[] bArr2 = this.f215C;
            b = (byte) (f214S[(b ^ bArr[i]) & UByte.MAX_VALUE] ^ bArr2[i]);
            bArr2[i] = b;
        }
    }

    public void reset() {
        this.xOff = 0;
        int i = 0;
        while (true) {
            byte[] bArr = this.f217X;
            if (i == bArr.length) {
                break;
            }
            bArr[i] = 0;
            i++;
        }
        this.mOff = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr2 = this.f216M;
            if (i2 == bArr2.length) {
                break;
            }
            bArr2[i2] = 0;
            i2++;
        }
        this.COff = 0;
        int i3 = 0;
        while (true) {
            byte[] bArr3 = this.f215C;
            if (i3 != bArr3.length) {
                bArr3[i3] = 0;
                i3++;
            } else {
                return;
            }
        }
    }

    public void reset(Memoable memoable) {
        copyIn((MD2Digest) memoable);
    }

    public void update(byte b) {
        byte[] bArr = this.f216M;
        int i = this.mOff;
        int i2 = i + 1;
        this.mOff = i2;
        bArr[i] = b;
        if (i2 == 16) {
            processCheckSum(bArr);
            processBlock(this.f216M);
            this.mOff = 0;
        }
    }

    public void update(byte[] bArr, int i, int i2) {
        while (this.mOff != 0 && i2 > 0) {
            update(bArr[i]);
            i++;
            i2--;
        }
        while (i2 > 16) {
            System.arraycopy(bArr, i, this.f216M, 0, 16);
            processCheckSum(this.f216M);
            processBlock(this.f216M);
            i2 -= 16;
            i += 16;
        }
        while (i2 > 0) {
            update(bArr[i]);
            i++;
            i2--;
        }
    }
}
