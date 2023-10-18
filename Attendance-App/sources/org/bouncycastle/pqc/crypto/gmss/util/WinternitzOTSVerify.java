package org.bouncycastle.pqc.crypto.gmss.util;

import kotlin.UByte;
import org.bouncycastle.crypto.Digest;

public class WinternitzOTSVerify {
    private Digest messDigestOTS;

    /* renamed from: w */
    private int f881w;

    public WinternitzOTSVerify(Digest digest, int i) {
        this.f881w = i;
        this.messDigestOTS = digest;
    }

    public byte[] Verify(byte[] bArr, byte[] bArr2) {
        int i;
        int i2;
        int i3;
        byte[] bArr3 = bArr;
        byte[] bArr4 = bArr2;
        int digestSize = this.messDigestOTS.getDigestSize();
        byte[] bArr5 = new byte[digestSize];
        int i4 = 0;
        this.messDigestOTS.update(bArr3, 0, bArr3.length);
        int digestSize2 = this.messDigestOTS.getDigestSize();
        byte[] bArr6 = new byte[digestSize2];
        this.messDigestOTS.doFinal(bArr6, 0);
        int i5 = digestSize << 3;
        int i6 = this.f881w;
        int i7 = ((i6 - 1) + i5) / i6;
        int log = getLog((i7 << i6) + 1);
        int i8 = this.f881w;
        int i9 = ((((log + i8) - 1) / i8) + i7) * digestSize;
        if (i9 != bArr4.length) {
            return null;
        }
        byte[] bArr7 = new byte[i9];
        int i10 = 8;
        if (8 % i8 == 0) {
            int i11 = 8 / i8;
            int i12 = (1 << i8) - 1;
            byte[] bArr8 = new byte[digestSize];
            int i13 = 0;
            byte b = 0;
            int i14 = 0;
            while (i13 < digestSize2) {
                while (i4 < i11) {
                    byte b2 = bArr6[i13] & i12;
                    b += b2;
                    int i15 = digestSize2;
                    int i16 = i14 * digestSize;
                    int i17 = i11;
                    System.arraycopy(bArr4, i16, bArr8, 0, digestSize);
                    int i18 = b2;
                    while (i18 < i12) {
                        this.messDigestOTS.update(bArr8, 0, bArr8.length);
                        bArr8 = new byte[this.messDigestOTS.getDigestSize()];
                        this.messDigestOTS.doFinal(bArr8, 0);
                        i18++;
                        byte[] bArr9 = bArr2;
                        b = b;
                        i9 = i9;
                    }
                    int i19 = i9;
                    int i20 = b;
                    System.arraycopy(bArr8, 0, bArr7, i16, digestSize);
                    bArr6[i13] = (byte) (bArr6[i13] >>> this.f881w);
                    i14++;
                    i4++;
                    digestSize2 = i15;
                    bArr4 = bArr2;
                    i11 = i17;
                }
                int i21 = digestSize2;
                int i22 = i9;
                int i23 = i11;
                i13++;
                bArr4 = bArr2;
                i4 = 0;
            }
            i = i9;
            int i24 = (i7 << this.f881w) - b;
            int i25 = 0;
            while (i25 < log) {
                int i26 = i14 * digestSize;
                System.arraycopy(bArr2, i26, bArr8, 0, digestSize);
                for (int i27 = i24 & i12; i27 < i12; i27++) {
                    this.messDigestOTS.update(bArr8, 0, bArr8.length);
                    bArr8 = new byte[this.messDigestOTS.getDigestSize()];
                    this.messDigestOTS.doFinal(bArr8, 0);
                }
                System.arraycopy(bArr8, 0, bArr7, i26, digestSize);
                int i28 = this.f881w;
                i24 >>>= i28;
                i14++;
                i25 += i28;
            }
        } else {
            i = i9;
            byte[] bArr10 = bArr4;
            if (i8 < 8) {
                int i29 = digestSize / i8;
                int i30 = (1 << i8) - 1;
                byte[] bArr11 = new byte[digestSize];
                int i31 = 0;
                int i32 = 0;
                int i33 = 0;
                int i34 = 0;
                while (i31 < i29) {
                    int i35 = 0;
                    long j = 0;
                    while (i35 < this.f881w) {
                        j ^= (long) ((bArr6[i32] & UByte.MAX_VALUE) << (i35 << 3));
                        i32++;
                        i35++;
                        bArr11 = bArr11;
                    }
                    byte[] bArr12 = bArr11;
                    int i36 = 0;
                    while (i36 < i10) {
                        int i37 = i31;
                        int i38 = (int) (j & ((long) i30));
                        i33 += i38;
                        int i39 = i34 * digestSize;
                        System.arraycopy(bArr10, i39, bArr11, 0, digestSize);
                        while (true) {
                            int i40 = i29;
                            if (i38 >= i30) {
                                break;
                            }
                            this.messDigestOTS.update(bArr11, 0, bArr11.length);
                            bArr11 = new byte[this.messDigestOTS.getDigestSize()];
                            this.messDigestOTS.doFinal(bArr11, 0);
                            i38++;
                            i29 = i40;
                            i32 = i32;
                        }
                        int i41 = i32;
                        System.arraycopy(bArr11, 0, bArr7, i39, digestSize);
                        j >>>= this.f881w;
                        i34++;
                        i36++;
                        i31 = i37;
                        i10 = 8;
                    }
                    int i42 = i29;
                    int i43 = i32;
                    i31++;
                    i10 = 8;
                }
                byte[] bArr13 = bArr11;
                int i44 = digestSize % this.f881w;
                long j2 = 0;
                for (int i45 = 0; i45 < i44; i45++) {
                    j2 ^= (long) ((bArr6[i32] & UByte.MAX_VALUE) << (i45 << 3));
                    i32++;
                }
                int i46 = i44 << 3;
                byte[] bArr14 = bArr13;
                int i47 = 0;
                while (i47 < i46) {
                    int i48 = (int) (j2 & ((long) i30));
                    i33 += i48;
                    int i49 = i34 * digestSize;
                    System.arraycopy(bArr10, i49, bArr14, 0, digestSize);
                    while (i48 < i30) {
                        this.messDigestOTS.update(bArr14, 0, bArr14.length);
                        bArr14 = new byte[this.messDigestOTS.getDigestSize()];
                        this.messDigestOTS.doFinal(bArr14, 0);
                        i48++;
                    }
                    System.arraycopy(bArr14, 0, bArr7, i49, digestSize);
                    int i50 = this.f881w;
                    j2 >>>= i50;
                    i34++;
                    i47 += i50;
                }
                int i51 = (i7 << this.f881w) - i33;
                int i52 = 0;
                while (i52 < log) {
                    int i53 = i34 * digestSize;
                    System.arraycopy(bArr10, i53, bArr14, 0, digestSize);
                    for (int i54 = i51 & i30; i54 < i30; i54++) {
                        this.messDigestOTS.update(bArr14, 0, bArr14.length);
                        bArr14 = new byte[this.messDigestOTS.getDigestSize()];
                        this.messDigestOTS.doFinal(bArr14, 0);
                    }
                    System.arraycopy(bArr14, 0, bArr7, i53, digestSize);
                    int i55 = this.f881w;
                    i51 >>>= i55;
                    i34++;
                    i52 += i55;
                }
            } else if (i8 < 57) {
                int i56 = i5 - i8;
                int i57 = (1 << i8) - 1;
                byte[] bArr15 = new byte[digestSize];
                int i58 = 0;
                int i59 = 0;
                int i60 = 0;
                while (i59 <= i56) {
                    int i61 = i59 >>> 3;
                    int i62 = i59 % 8;
                    int i63 = i59 + this.f881w;
                    int i64 = (i63 + 7) >>> 3;
                    int i65 = 0;
                    long j3 = 0;
                    while (true) {
                        i2 = i56;
                        if (i61 >= i64) {
                            break;
                        }
                        j3 ^= (long) ((bArr6[i61] & UByte.MAX_VALUE) << (i65 << 3));
                        i65++;
                        i61++;
                        i56 = i2;
                        log = log;
                        i7 = i7;
                    }
                    int i66 = log;
                    int i67 = i7;
                    long j4 = (long) i57;
                    long j5 = (j3 >>> i62) & j4;
                    int i68 = i63;
                    i60 = (int) (((long) i60) + j5);
                    int i69 = i58 * digestSize;
                    System.arraycopy(bArr10, i69, bArr15, 0, digestSize);
                    while (true) {
                        i3 = i68;
                        if (j5 >= j4) {
                            break;
                        }
                        this.messDigestOTS.update(bArr15, 0, bArr15.length);
                        bArr15 = new byte[this.messDigestOTS.getDigestSize()];
                        this.messDigestOTS.doFinal(bArr15, 0);
                        j5++;
                        i68 = i3;
                        i60 = i60;
                    }
                    int i70 = i60;
                    System.arraycopy(bArr15, 0, bArr7, i69, digestSize);
                    i58++;
                    i59 = i3;
                    i56 = i2;
                    log = i66;
                    i7 = i67;
                }
                int i71 = log;
                int i72 = i7;
                int i73 = i59 >>> 3;
                if (i73 < digestSize) {
                    int i74 = i59 % 8;
                    int i75 = 0;
                    long j6 = 0;
                    while (i73 < digestSize) {
                        j6 ^= (long) ((bArr6[i73] & UByte.MAX_VALUE) << (i75 << 3));
                        i75++;
                        i73++;
                    }
                    long j7 = (long) i57;
                    long j8 = (j6 >>> i74) & j7;
                    i60 = (int) (((long) i60) + j8);
                    int i76 = i58 * digestSize;
                    System.arraycopy(bArr10, i76, bArr15, 0, digestSize);
                    while (j8 < j7) {
                        this.messDigestOTS.update(bArr15, 0, bArr15.length);
                        bArr15 = new byte[this.messDigestOTS.getDigestSize()];
                        this.messDigestOTS.doFinal(bArr15, 0);
                        j8++;
                    }
                    System.arraycopy(bArr15, 0, bArr7, i76, digestSize);
                    i58++;
                }
                int i77 = (i72 << this.f881w) - i60;
                int i78 = i71;
                int i79 = 0;
                while (i79 < i78) {
                    int i80 = i58 * digestSize;
                    System.arraycopy(bArr10, i80, bArr15, 0, digestSize);
                    for (long j9 = (long) (i77 & i57); j9 < ((long) i57); j9++) {
                        this.messDigestOTS.update(bArr15, 0, bArr15.length);
                        bArr15 = new byte[this.messDigestOTS.getDigestSize()];
                        this.messDigestOTS.doFinal(bArr15, 0);
                    }
                    System.arraycopy(bArr15, 0, bArr7, i80, digestSize);
                    int i81 = this.f881w;
                    i77 >>>= i81;
                    i58++;
                    i79 += i81;
                }
            }
        }
        byte[] bArr16 = new byte[digestSize];
        this.messDigestOTS.update(bArr7, 0, i);
        byte[] bArr17 = new byte[this.messDigestOTS.getDigestSize()];
        this.messDigestOTS.doFinal(bArr17, 0);
        return bArr17;
    }

    public int getLog(int i) {
        int i2 = 1;
        int i3 = 2;
        while (i3 < i) {
            i3 <<= 1;
            i2++;
        }
        return i2;
    }

    public int getSignatureLength() {
        int digestSize = this.messDigestOTS.getDigestSize();
        int i = this.f881w;
        int i2 = ((digestSize << 3) + (i - 1)) / i;
        int log = getLog((i2 << i) + 1);
        int i3 = this.f881w;
        return digestSize * (i2 + (((log + i3) - 1) / i3));
    }
}
