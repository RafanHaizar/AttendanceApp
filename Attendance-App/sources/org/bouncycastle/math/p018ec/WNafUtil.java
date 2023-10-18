package org.bouncycastle.math.p018ec;

import com.itextpdf.p026io.codec.TIFFConstants;
import java.math.BigInteger;

/* renamed from: org.bouncycastle.math.ec.WNafUtil */
public abstract class WNafUtil {
    private static final int[] DEFAULT_WINDOW_SIZE_CUTOFFS = {13, 41, 121, TIFFConstants.TIFFTAG_TARGETPRINTER, 897, 2305};
    private static final byte[] EMPTY_BYTES = new byte[0];
    private static final int[] EMPTY_INTS = new int[0];
    /* access modifiers changed from: private */
    public static final ECPoint[] EMPTY_POINTS = new ECPoint[0];
    private static final int MAX_WIDTH = 16;
    public static final String PRECOMP_NAME = "bc_wnaf";

    public static void configureBasepoint(ECPoint eCPoint) {
        ECCurve curve = eCPoint.getCurve();
        if (curve != null) {
            BigInteger order = curve.getOrder();
            final int min = Math.min(16, getWindowSize(order == null ? curve.getFieldSize() + 1 : order.bitLength()) + 3);
            curve.precompute(eCPoint, PRECOMP_NAME, new PreCompCallback() {
                public PreCompInfo precompute(PreCompInfo preCompInfo) {
                    WNafPreCompInfo wNafPreCompInfo = preCompInfo instanceof WNafPreCompInfo ? (WNafPreCompInfo) preCompInfo : null;
                    if (wNafPreCompInfo == null || wNafPreCompInfo.getConfWidth() != min) {
                        WNafPreCompInfo wNafPreCompInfo2 = new WNafPreCompInfo();
                        wNafPreCompInfo2.setPromotionCountdown(0);
                        wNafPreCompInfo2.setConfWidth(min);
                        if (wNafPreCompInfo != null) {
                            wNafPreCompInfo2.setPreComp(wNafPreCompInfo.getPreComp());
                            wNafPreCompInfo2.setPreCompNeg(wNafPreCompInfo.getPreCompNeg());
                            wNafPreCompInfo2.setTwice(wNafPreCompInfo.getTwice());
                            wNafPreCompInfo2.setWidth(wNafPreCompInfo.getWidth());
                        }
                        return wNafPreCompInfo2;
                    }
                    wNafPreCompInfo.setPromotionCountdown(0);
                    return wNafPreCompInfo;
                }
            });
        }
    }

    public static int[] generateCompactNaf(BigInteger bigInteger) {
        if ((bigInteger.bitLength() >>> 16) != 0) {
            throw new IllegalArgumentException("'k' must have bitlength < 2^16");
        } else if (bigInteger.signum() == 0) {
            return EMPTY_INTS;
        } else {
            BigInteger add = bigInteger.shiftLeft(1).add(bigInteger);
            int bitLength = add.bitLength();
            int i = bitLength >> 1;
            int[] iArr = new int[i];
            BigInteger xor = add.xor(bigInteger);
            int i2 = bitLength - 1;
            int i3 = 0;
            int i4 = 0;
            int i5 = 1;
            while (i5 < i2) {
                if (!xor.testBit(i5)) {
                    i4++;
                } else {
                    iArr[i3] = i4 | ((bigInteger.testBit(i5) ? -1 : 1) << 16);
                    i5++;
                    i3++;
                    i4 = 1;
                }
                i5++;
            }
            int i6 = i3 + 1;
            iArr[i3] = 65536 | i4;
            return i > i6 ? trim(iArr, i6) : iArr;
        }
    }

    public static int[] generateCompactWindowNaf(int i, BigInteger bigInteger) {
        if (i == 2) {
            return generateCompactNaf(bigInteger);
        }
        if (i < 2 || i > 16) {
            throw new IllegalArgumentException("'width' must be in the range [2, 16]");
        } else if ((bigInteger.bitLength() >>> 16) != 0) {
            throw new IllegalArgumentException("'k' must have bitlength < 2^16");
        } else if (bigInteger.signum() == 0) {
            return EMPTY_INTS;
        } else {
            int bitLength = (bigInteger.bitLength() / i) + 1;
            int[] iArr = new int[bitLength];
            int i2 = 1 << i;
            int i3 = i2 - 1;
            int i4 = i2 >>> 1;
            int i5 = 0;
            int i6 = 0;
            boolean z = false;
            while (i5 <= bigInteger.bitLength()) {
                if (bigInteger.testBit(i5) == z) {
                    i5++;
                } else {
                    bigInteger = bigInteger.shiftRight(i5);
                    int intValue = bigInteger.intValue() & i3;
                    if (z) {
                        intValue++;
                    }
                    z = (intValue & i4) != 0;
                    if (z) {
                        intValue -= i2;
                    }
                    if (i6 > 0) {
                        i5--;
                    }
                    iArr[i6] = i5 | (intValue << 16);
                    i5 = i;
                    i6++;
                }
            }
            return bitLength > i6 ? trim(iArr, i6) : iArr;
        }
    }

    public static byte[] generateJSF(BigInteger bigInteger, BigInteger bigInteger2) {
        int max = Math.max(bigInteger.bitLength(), bigInteger2.bitLength()) + 1;
        byte[] bArr = new byte[max];
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if ((i | i2) == 0 && bigInteger.bitLength() <= i3 && bigInteger2.bitLength() <= i3) {
                break;
            }
            int intValue = ((bigInteger.intValue() >>> i3) + i) & 7;
            int intValue2 = ((bigInteger2.intValue() >>> i3) + i2) & 7;
            int i5 = intValue & 1;
            if (i5 != 0) {
                i5 -= intValue & 2;
                if (intValue + i5 == 4 && (intValue2 & 3) == 2) {
                    i5 = -i5;
                }
            }
            int i6 = intValue2 & 1;
            if (i6 != 0) {
                i6 -= intValue2 & 2;
                if (intValue2 + i6 == 4 && (intValue & 3) == 2) {
                    i6 = -i6;
                }
            }
            if ((i << 1) == i5 + 1) {
                i ^= 1;
            }
            if ((i2 << 1) == i6 + 1) {
                i2 ^= 1;
            }
            i3++;
            if (i3 == 30) {
                bigInteger = bigInteger.shiftRight(30);
                bigInteger2 = bigInteger2.shiftRight(30);
                i3 = 0;
            }
            bArr[i4] = (byte) ((i5 << 4) | (i6 & 15));
            i4++;
        }
        return max > i4 ? trim(bArr, i4) : bArr;
    }

    public static byte[] generateNaf(BigInteger bigInteger) {
        if (bigInteger.signum() == 0) {
            return EMPTY_BYTES;
        }
        BigInteger add = bigInteger.shiftLeft(1).add(bigInteger);
        int bitLength = add.bitLength() - 1;
        byte[] bArr = new byte[bitLength];
        BigInteger xor = add.xor(bigInteger);
        int i = 1;
        while (i < bitLength) {
            if (xor.testBit(i)) {
                bArr[i - 1] = (byte) (bigInteger.testBit(i) ? -1 : 1);
                i++;
            }
            i++;
        }
        bArr[bitLength - 1] = 1;
        return bArr;
    }

    public static byte[] generateWindowNaf(int i, BigInteger bigInteger) {
        if (i == 2) {
            return generateNaf(bigInteger);
        }
        if (i < 2 || i > 8) {
            throw new IllegalArgumentException("'width' must be in the range [2, 8]");
        } else if (bigInteger.signum() == 0) {
            return EMPTY_BYTES;
        } else {
            int bitLength = bigInteger.bitLength() + 1;
            byte[] bArr = new byte[bitLength];
            int i2 = 1 << i;
            int i3 = i2 - 1;
            int i4 = i2 >>> 1;
            int i5 = 0;
            int i6 = 0;
            boolean z = false;
            while (i5 <= bigInteger.bitLength()) {
                if (bigInteger.testBit(i5) == z) {
                    i5++;
                } else {
                    bigInteger = bigInteger.shiftRight(i5);
                    int intValue = bigInteger.intValue() & i3;
                    if (z) {
                        intValue++;
                    }
                    z = (intValue & i4) != 0;
                    if (z) {
                        intValue -= i2;
                    }
                    if (i6 > 0) {
                        i5--;
                    }
                    int i7 = i6 + i5;
                    bArr[i7] = (byte) intValue;
                    i6 = i7 + 1;
                    i5 = i;
                }
            }
            return bitLength > i6 ? trim(bArr, i6) : bArr;
        }
    }

    public static int getNafWeight(BigInteger bigInteger) {
        if (bigInteger.signum() == 0) {
            return 0;
        }
        return bigInteger.shiftLeft(1).add(bigInteger).xor(bigInteger).bitCount();
    }

    public static WNafPreCompInfo getWNafPreCompInfo(ECPoint eCPoint) {
        return getWNafPreCompInfo(eCPoint.getCurve().getPreCompInfo(eCPoint, PRECOMP_NAME));
    }

    public static WNafPreCompInfo getWNafPreCompInfo(PreCompInfo preCompInfo) {
        if (preCompInfo instanceof WNafPreCompInfo) {
            return (WNafPreCompInfo) preCompInfo;
        }
        return null;
    }

    public static int getWindowSize(int i) {
        return getWindowSize(i, DEFAULT_WINDOW_SIZE_CUTOFFS, 16);
    }

    public static int getWindowSize(int i, int i2) {
        return getWindowSize(i, DEFAULT_WINDOW_SIZE_CUTOFFS, i2);
    }

    public static int getWindowSize(int i, int[] iArr) {
        return getWindowSize(i, iArr, 16);
    }

    public static int getWindowSize(int i, int[] iArr, int i2) {
        int i3 = 0;
        while (i3 < iArr.length && i >= iArr[i3]) {
            i3++;
        }
        return Math.max(2, Math.min(i2, i3 + 2));
    }

    public static ECPoint mapPointWithPrecomp(ECPoint eCPoint, int i, final boolean z, final ECPointMap eCPointMap) {
        ECCurve curve = eCPoint.getCurve();
        final WNafPreCompInfo precompute = precompute(eCPoint, i, z);
        ECPoint map = eCPointMap.map(eCPoint);
        curve.precompute(map, PRECOMP_NAME, new PreCompCallback() {
            public PreCompInfo precompute(PreCompInfo preCompInfo) {
                WNafPreCompInfo wNafPreCompInfo = new WNafPreCompInfo();
                wNafPreCompInfo.setConfWidth(precompute.getConfWidth());
                ECPoint twice = precompute.getTwice();
                if (twice != null) {
                    wNafPreCompInfo.setTwice(eCPointMap.map(twice));
                }
                ECPoint[] preComp = precompute.getPreComp();
                int length = preComp.length;
                ECPoint[] eCPointArr = new ECPoint[length];
                for (int i = 0; i < preComp.length; i++) {
                    eCPointArr[i] = eCPointMap.map(preComp[i]);
                }
                wNafPreCompInfo.setPreComp(eCPointArr);
                wNafPreCompInfo.setWidth(precompute.getWidth());
                if (z) {
                    ECPoint[] eCPointArr2 = new ECPoint[length];
                    for (int i2 = 0; i2 < length; i2++) {
                        eCPointArr2[i2] = eCPointArr[i2].negate();
                    }
                    wNafPreCompInfo.setPreCompNeg(eCPointArr2);
                }
                return wNafPreCompInfo;
            }
        });
        return map;
    }

    public static WNafPreCompInfo precompute(final ECPoint eCPoint, final int i, final boolean z) {
        final ECCurve curve = eCPoint.getCurve();
        return (WNafPreCompInfo) curve.precompute(eCPoint, PRECOMP_NAME, new PreCompCallback() {
            private boolean checkExisting(WNafPreCompInfo wNafPreCompInfo, int i, int i2, boolean z) {
                return wNafPreCompInfo != null && wNafPreCompInfo.getWidth() >= Math.max(wNafPreCompInfo.getConfWidth(), i) && checkTable(wNafPreCompInfo.getPreComp(), i2) && (!z || checkTable(wNafPreCompInfo.getPreCompNeg(), i2));
            }

            private boolean checkTable(ECPoint[] eCPointArr, int i) {
                return eCPointArr != null && eCPointArr.length >= i;
            }

            /* JADX WARNING: Removed duplicated region for block: B:51:0x0111 A[LOOP:1: B:50:0x010f->B:51:0x0111, LOOP_END] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public org.bouncycastle.math.p018ec.PreCompInfo precompute(org.bouncycastle.math.p018ec.PreCompInfo r14) {
                /*
                    r13 = this;
                    boolean r0 = r14 instanceof org.bouncycastle.math.p018ec.WNafPreCompInfo
                    r1 = 0
                    if (r0 == 0) goto L_0x0008
                    org.bouncycastle.math.ec.WNafPreCompInfo r14 = (org.bouncycastle.math.p018ec.WNafPreCompInfo) r14
                    goto L_0x0009
                L_0x0008:
                    r14 = r1
                L_0x0009:
                    int r0 = r3
                    r2 = 16
                    int r0 = java.lang.Math.min(r2, r0)
                    r3 = 2
                    int r0 = java.lang.Math.max(r3, r0)
                    int r4 = r0 + -2
                    r5 = 1
                    int r4 = r5 << r4
                    boolean r6 = r4
                    boolean r4 = r13.checkExisting(r14, r0, r4, r6)
                    if (r4 == 0) goto L_0x0027
                    r14.decrementPromotionCountdown()
                    return r14
                L_0x0027:
                    org.bouncycastle.math.ec.WNafPreCompInfo r4 = new org.bouncycastle.math.ec.WNafPreCompInfo
                    r4.<init>()
                    if (r14 == 0) goto L_0x0049
                    int r6 = r14.decrementPromotionCountdown()
                    r4.setPromotionCountdown(r6)
                    int r6 = r14.getConfWidth()
                    r4.setConfWidth(r6)
                    org.bouncycastle.math.ec.ECPoint[] r6 = r14.getPreComp()
                    org.bouncycastle.math.ec.ECPoint[] r7 = r14.getPreCompNeg()
                    org.bouncycastle.math.ec.ECPoint r14 = r14.getTwice()
                    goto L_0x004c
                L_0x0049:
                    r14 = r1
                    r6 = r14
                    r7 = r6
                L_0x004c:
                    int r8 = r4.getConfWidth()
                    int r0 = java.lang.Math.max(r8, r0)
                    int r0 = java.lang.Math.min(r2, r0)
                    int r2 = r0 + -2
                    int r2 = r5 << r2
                    r8 = 0
                    if (r6 != 0) goto L_0x0065
                    org.bouncycastle.math.ec.ECPoint[] r6 = org.bouncycastle.math.p018ec.WNafUtil.EMPTY_POINTS
                    r9 = 0
                    goto L_0x0066
                L_0x0065:
                    int r9 = r6.length
                L_0x0066:
                    if (r9 >= r2) goto L_0x00fd
                    org.bouncycastle.math.ec.ECPoint[] r6 = org.bouncycastle.math.p018ec.WNafUtil.resizeTable(r6, r2)
                    if (r2 != r5) goto L_0x0078
                    org.bouncycastle.math.ec.ECPoint r1 = r2
                    org.bouncycastle.math.ec.ECPoint r1 = r1.normalize()
                    r6[r8] = r1
                    goto L_0x00fd
                L_0x0078:
                    if (r9 != 0) goto L_0x0080
                    org.bouncycastle.math.ec.ECPoint r10 = r2
                    r6[r8] = r10
                    r10 = 1
                    goto L_0x0081
                L_0x0080:
                    r10 = r9
                L_0x0081:
                    if (r2 != r3) goto L_0x008c
                    org.bouncycastle.math.ec.ECPoint r3 = r2
                    org.bouncycastle.math.ec.ECPoint r3 = r3.threeTimes()
                    r6[r5] = r3
                    goto L_0x00f6
                L_0x008c:
                    int r3 = r10 + -1
                    r3 = r6[r3]
                    if (r14 != 0) goto L_0x00e9
                    r14 = r6[r8]
                    org.bouncycastle.math.ec.ECPoint r14 = r14.twice()
                    boolean r5 = r14.isInfinity()
                    if (r5 != 0) goto L_0x00e9
                    org.bouncycastle.math.ec.ECCurve r5 = r0
                    boolean r5 = org.bouncycastle.math.p018ec.ECAlgorithms.isFpCurve(r5)
                    if (r5 == 0) goto L_0x00e9
                    org.bouncycastle.math.ec.ECCurve r5 = r0
                    int r5 = r5.getFieldSize()
                    r11 = 64
                    if (r5 < r11) goto L_0x00e9
                    org.bouncycastle.math.ec.ECCurve r5 = r0
                    int r5 = r5.getCoordinateSystem()
                    switch(r5) {
                        case 2: goto L_0x00ba;
                        case 3: goto L_0x00ba;
                        case 4: goto L_0x00ba;
                        default: goto L_0x00b9;
                    }
                L_0x00b9:
                    goto L_0x00e9
                L_0x00ba:
                    org.bouncycastle.math.ec.ECFieldElement r1 = r14.getZCoord(r8)
                    org.bouncycastle.math.ec.ECCurve r5 = r0
                    org.bouncycastle.math.ec.ECFieldElement r11 = r14.getXCoord()
                    java.math.BigInteger r11 = r11.toBigInteger()
                    org.bouncycastle.math.ec.ECFieldElement r12 = r14.getYCoord()
                    java.math.BigInteger r12 = r12.toBigInteger()
                    org.bouncycastle.math.ec.ECPoint r5 = r5.createPoint(r11, r12)
                    org.bouncycastle.math.ec.ECFieldElement r11 = r1.square()
                    org.bouncycastle.math.ec.ECFieldElement r12 = r11.multiply(r1)
                    org.bouncycastle.math.ec.ECPoint r3 = r3.scaleX(r11)
                    org.bouncycastle.math.ec.ECPoint r3 = r3.scaleY(r12)
                    if (r9 != 0) goto L_0x00ea
                    r6[r8] = r3
                    goto L_0x00ea
                L_0x00e9:
                    r5 = r14
                L_0x00ea:
                    if (r10 >= r2) goto L_0x00f6
                    int r11 = r10 + 1
                    org.bouncycastle.math.ec.ECPoint r3 = r3.add(r5)
                    r6[r10] = r3
                    r10 = r11
                    goto L_0x00ea
                L_0x00f6:
                    org.bouncycastle.math.ec.ECCurve r3 = r0
                    int r5 = r2 - r9
                    r3.normalizeAll(r6, r9, r5, r1)
                L_0x00fd:
                    boolean r1 = r4
                    if (r1 == 0) goto L_0x011c
                    if (r7 != 0) goto L_0x0107
                    org.bouncycastle.math.ec.ECPoint[] r1 = new org.bouncycastle.math.p018ec.ECPoint[r2]
                L_0x0105:
                    r7 = r1
                    goto L_0x010f
                L_0x0107:
                    int r8 = r7.length
                    if (r8 >= r2) goto L_0x010f
                    org.bouncycastle.math.ec.ECPoint[] r1 = org.bouncycastle.math.p018ec.WNafUtil.resizeTable(r7, r2)
                    goto L_0x0105
                L_0x010f:
                    if (r8 >= r2) goto L_0x011c
                    r1 = r6[r8]
                    org.bouncycastle.math.ec.ECPoint r1 = r1.negate()
                    r7[r8] = r1
                    int r8 = r8 + 1
                    goto L_0x010f
                L_0x011c:
                    r4.setPreComp(r6)
                    r4.setPreCompNeg(r7)
                    r4.setTwice(r14)
                    r4.setWidth(r0)
                    return r4
                */
                throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.math.p018ec.WNafUtil.C03533.precompute(org.bouncycastle.math.ec.PreCompInfo):org.bouncycastle.math.ec.PreCompInfo");
            }
        });
    }

    public static WNafPreCompInfo precomputeWithPointMap(ECPoint eCPoint, final ECPointMap eCPointMap, final WNafPreCompInfo wNafPreCompInfo, final boolean z) {
        return (WNafPreCompInfo) eCPoint.getCurve().precompute(eCPoint, PRECOMP_NAME, new PreCompCallback() {
            private boolean checkExisting(WNafPreCompInfo wNafPreCompInfo, int i, int i2, boolean z) {
                return wNafPreCompInfo != null && wNafPreCompInfo.getWidth() >= i && checkTable(wNafPreCompInfo.getPreComp(), i2) && (!z || checkTable(wNafPreCompInfo.getPreCompNeg(), i2));
            }

            private boolean checkTable(ECPoint[] eCPointArr, int i) {
                return eCPointArr != null && eCPointArr.length >= i;
            }

            public PreCompInfo precompute(PreCompInfo preCompInfo) {
                WNafPreCompInfo wNafPreCompInfo = preCompInfo instanceof WNafPreCompInfo ? (WNafPreCompInfo) preCompInfo : null;
                int width = wNafPreCompInfo.getWidth();
                if (checkExisting(wNafPreCompInfo, width, wNafPreCompInfo.getPreComp().length, z)) {
                    wNafPreCompInfo.decrementPromotionCountdown();
                    return wNafPreCompInfo;
                }
                WNafPreCompInfo wNafPreCompInfo2 = new WNafPreCompInfo();
                wNafPreCompInfo2.setPromotionCountdown(wNafPreCompInfo.getPromotionCountdown());
                ECPoint twice = wNafPreCompInfo.getTwice();
                if (twice != null) {
                    wNafPreCompInfo2.setTwice(eCPointMap.map(twice));
                }
                ECPoint[] preComp = wNafPreCompInfo.getPreComp();
                int length = preComp.length;
                ECPoint[] eCPointArr = new ECPoint[length];
                for (int i = 0; i < preComp.length; i++) {
                    eCPointArr[i] = eCPointMap.map(preComp[i]);
                }
                wNafPreCompInfo2.setPreComp(eCPointArr);
                wNafPreCompInfo2.setWidth(width);
                if (z) {
                    ECPoint[] eCPointArr2 = new ECPoint[length];
                    for (int i2 = 0; i2 < length; i2++) {
                        eCPointArr2[i2] = eCPointArr[i2].negate();
                    }
                    wNafPreCompInfo2.setPreCompNeg(eCPointArr2);
                }
                return wNafPreCompInfo2;
            }
        });
    }

    /* access modifiers changed from: private */
    public static ECPoint[] resizeTable(ECPoint[] eCPointArr, int i) {
        ECPoint[] eCPointArr2 = new ECPoint[i];
        System.arraycopy(eCPointArr, 0, eCPointArr2, 0, eCPointArr.length);
        return eCPointArr2;
    }

    private static byte[] trim(byte[] bArr, int i) {
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 0, bArr2, 0, i);
        return bArr2;
    }

    private static int[] trim(int[] iArr, int i) {
        int[] iArr2 = new int[i];
        System.arraycopy(iArr, 0, iArr2, 0, i);
        return iArr2;
    }
}
