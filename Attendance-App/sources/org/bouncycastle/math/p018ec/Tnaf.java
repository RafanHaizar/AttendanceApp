package org.bouncycastle.math.p018ec;

import java.math.BigInteger;
import org.bouncycastle.math.p018ec.ECCurve;
import org.bouncycastle.math.p018ec.ECPoint;

/* renamed from: org.bouncycastle.math.ec.Tnaf */
class Tnaf {
    private static final BigInteger MINUS_ONE;
    private static final BigInteger MINUS_THREE;
    private static final BigInteger MINUS_TWO = ECConstants.TWO.negate();
    public static final byte POW_2_WIDTH = 16;
    public static final byte WIDTH = 4;
    public static final ZTauElement[] alpha0;
    public static final byte[][] alpha0Tnaf = {null, new byte[]{1}, null, new byte[]{-1, 0, 1}, null, new byte[]{1, 0, 1}, null, new byte[]{-1, 0, 0, 1}};
    public static final ZTauElement[] alpha1;
    public static final byte[][] alpha1Tnaf = {null, new byte[]{1}, null, new byte[]{-1, 0, 1}, null, new byte[]{1, 0, 1}, null, new byte[]{-1, 0, 0, -1}};

    static {
        BigInteger negate = ECConstants.ONE.negate();
        MINUS_ONE = negate;
        BigInteger negate2 = ECConstants.THREE.negate();
        MINUS_THREE = negate2;
        alpha0 = new ZTauElement[]{null, new ZTauElement(ECConstants.ONE, ECConstants.ZERO), null, new ZTauElement(negate2, negate), null, new ZTauElement(negate, negate), null, new ZTauElement(ECConstants.ONE, negate), null};
        alpha1 = new ZTauElement[]{null, new ZTauElement(ECConstants.ONE, ECConstants.ZERO), null, new ZTauElement(negate2, ECConstants.ONE), null, new ZTauElement(negate, ECConstants.ONE), null, new ZTauElement(ECConstants.ONE, ECConstants.ONE), null};
    }

    Tnaf() {
    }

    public static SimpleBigDecimal approximateDivisionByN(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, byte b, int i, int i2) {
        int i3 = ((i + 5) / 2) + i2;
        BigInteger multiply = bigInteger2.multiply(bigInteger.shiftRight(((i - i3) - 2) + b));
        BigInteger add = multiply.add(bigInteger3.multiply(multiply.shiftRight(i)));
        int i4 = i3 - i2;
        BigInteger shiftRight = add.shiftRight(i4);
        if (add.testBit(i4 - 1)) {
            shiftRight = shiftRight.add(ECConstants.ONE);
        }
        return new SimpleBigDecimal(shiftRight, i2);
    }

    public static BigInteger[] getLucas(byte b, int i, boolean z) {
        BigInteger bigInteger;
        BigInteger bigInteger2;
        if (b == 1 || b == -1) {
            if (z) {
                bigInteger = ECConstants.TWO;
                bigInteger2 = BigInteger.valueOf((long) b);
            } else {
                bigInteger = ECConstants.ZERO;
                bigInteger2 = ECConstants.ONE;
            }
            int i2 = 1;
            while (i2 < i) {
                i2++;
                BigInteger bigInteger3 = bigInteger2;
                bigInteger2 = (b == 1 ? bigInteger2 : bigInteger2.negate()).subtract(bigInteger.shiftLeft(1));
                bigInteger = bigInteger3;
            }
            return new BigInteger[]{bigInteger, bigInteger2};
        }
        throw new IllegalArgumentException("mu must be 1 or -1");
    }

    public static byte getMu(int i) {
        return (byte) (i == 0 ? -1 : 1);
    }

    public static byte getMu(ECCurve.AbstractF2m abstractF2m) {
        if (abstractF2m.isKoblitz()) {
            return abstractF2m.getA().isZero() ? (byte) -1 : 1;
        }
        throw new IllegalArgumentException("No Koblitz curve (ABC), TNAF multiplication not possible");
    }

    public static byte getMu(ECFieldElement eCFieldElement) {
        return (byte) (eCFieldElement.isZero() ? -1 : 1);
    }

    public static ECPoint.AbstractF2m[] getPreComp(ECPoint.AbstractF2m abstractF2m, byte b) {
        byte[][] bArr = b == 0 ? alpha0Tnaf : alpha1Tnaf;
        ECPoint.AbstractF2m[] abstractF2mArr = new ECPoint.AbstractF2m[((bArr.length + 1) >>> 1)];
        abstractF2mArr[0] = abstractF2m;
        int length = bArr.length;
        for (int i = 3; i < length; i += 2) {
            abstractF2mArr[i >>> 1] = multiplyFromTnaf(abstractF2m, bArr[i]);
        }
        abstractF2m.getCurve().normalizeAll(abstractF2mArr);
        return abstractF2mArr;
    }

    protected static int getShiftsForCofactor(BigInteger bigInteger) {
        if (bigInteger != null) {
            if (bigInteger.equals(ECConstants.TWO)) {
                return 1;
            }
            if (bigInteger.equals(ECConstants.FOUR)) {
                return 2;
            }
        }
        throw new IllegalArgumentException("h (Cofactor) must be 2 or 4");
    }

    public static BigInteger[] getSi(int i, int i2, BigInteger bigInteger) {
        byte mu = getMu(i2);
        int shiftsForCofactor = getShiftsForCofactor(bigInteger);
        BigInteger[] lucas = getLucas(mu, (i + 3) - i2, false);
        if (mu == 1) {
            lucas[0] = lucas[0].negate();
            lucas[1] = lucas[1].negate();
        }
        return new BigInteger[]{ECConstants.ONE.add(lucas[1]).shiftRight(shiftsForCofactor), ECConstants.ONE.add(lucas[0]).shiftRight(shiftsForCofactor).negate()};
    }

    public static BigInteger[] getSi(ECCurve.AbstractF2m abstractF2m) {
        if (abstractF2m.isKoblitz()) {
            int fieldSize = abstractF2m.getFieldSize();
            int intValue = abstractF2m.getA().toBigInteger().intValue();
            byte mu = getMu(intValue);
            int shiftsForCofactor = getShiftsForCofactor(abstractF2m.getCofactor());
            BigInteger[] lucas = getLucas(mu, (fieldSize + 3) - intValue, false);
            if (mu == 1) {
                lucas[0] = lucas[0].negate();
                lucas[1] = lucas[1].negate();
            }
            return new BigInteger[]{ECConstants.ONE.add(lucas[1]).shiftRight(shiftsForCofactor), ECConstants.ONE.add(lucas[0]).shiftRight(shiftsForCofactor).negate()};
        }
        throw new IllegalArgumentException("si is defined for Koblitz curves only");
    }

    public static BigInteger getTw(byte b, int i) {
        if (i == 4) {
            return b == 1 ? BigInteger.valueOf(6) : BigInteger.valueOf(10);
        }
        BigInteger[] lucas = getLucas(b, i, false);
        BigInteger bit = ECConstants.ZERO.setBit(i);
        return ECConstants.TWO.multiply(lucas[0]).multiply(lucas[1].modInverse(bit)).mod(bit);
    }

    public static ECPoint.AbstractF2m multiplyFromTnaf(ECPoint.AbstractF2m abstractF2m, byte[] bArr) {
        ECPoint.AbstractF2m abstractF2m2 = (ECPoint.AbstractF2m) abstractF2m.getCurve().getInfinity();
        ECPoint.AbstractF2m abstractF2m3 = (ECPoint.AbstractF2m) abstractF2m.negate();
        int i = 0;
        for (int length = bArr.length - 1; length >= 0; length--) {
            i++;
            byte b = bArr[length];
            if (b != 0) {
                abstractF2m2 = (ECPoint.AbstractF2m) abstractF2m2.tauPow(i).add(b > 0 ? abstractF2m : abstractF2m3);
                i = 0;
            }
        }
        return i > 0 ? abstractF2m2.tauPow(i) : abstractF2m2;
    }

    public static ECPoint.AbstractF2m multiplyRTnaf(ECPoint.AbstractF2m abstractF2m, BigInteger bigInteger) {
        ECCurve.AbstractF2m abstractF2m2 = (ECCurve.AbstractF2m) abstractF2m.getCurve();
        int fieldSize = abstractF2m2.getFieldSize();
        int intValue = abstractF2m2.getA().toBigInteger().intValue();
        byte mu = getMu(intValue);
        byte b = (byte) intValue;
        return multiplyTnaf(abstractF2m, partModReduction(bigInteger, fieldSize, b, abstractF2m2.getSi(), mu, (byte) 10));
    }

    public static ECPoint.AbstractF2m multiplyTnaf(ECPoint.AbstractF2m abstractF2m, ZTauElement zTauElement) {
        return multiplyFromTnaf(abstractF2m, tauAdicNaf(getMu(((ECCurve.AbstractF2m) abstractF2m.getCurve()).getA()), zTauElement));
    }

    public static BigInteger norm(byte b, ZTauElement zTauElement) {
        BigInteger subtract;
        BigInteger multiply = zTauElement.f732u.multiply(zTauElement.f732u);
        BigInteger multiply2 = zTauElement.f732u.multiply(zTauElement.f733v);
        BigInteger shiftLeft = zTauElement.f733v.multiply(zTauElement.f733v).shiftLeft(1);
        if (b == 1) {
            subtract = multiply.add(multiply2);
        } else if (b == -1) {
            subtract = multiply.subtract(multiply2);
        } else {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
        return subtract.add(shiftLeft);
    }

    public static SimpleBigDecimal norm(byte b, SimpleBigDecimal simpleBigDecimal, SimpleBigDecimal simpleBigDecimal2) {
        SimpleBigDecimal subtract;
        SimpleBigDecimal multiply = simpleBigDecimal.multiply(simpleBigDecimal);
        SimpleBigDecimal multiply2 = simpleBigDecimal.multiply(simpleBigDecimal2);
        SimpleBigDecimal shiftLeft = simpleBigDecimal2.multiply(simpleBigDecimal2).shiftLeft(1);
        if (b == 1) {
            subtract = multiply.add(multiply2);
        } else if (b == -1) {
            subtract = multiply.subtract(multiply2);
        } else {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
        return subtract.add(shiftLeft);
    }

    public static ZTauElement partModReduction(BigInteger bigInteger, int i, byte b, BigInteger[] bigIntegerArr, byte b2, byte b3) {
        byte b4 = b2;
        BigInteger add = b4 == 1 ? bigIntegerArr[0].add(bigIntegerArr[1]) : bigIntegerArr[0].subtract(bigIntegerArr[1]);
        int i2 = i;
        BigInteger bigInteger2 = bigInteger;
        BigInteger bigInteger3 = getLucas(b4, i, true)[1];
        byte b5 = b;
        int i3 = i;
        byte b6 = b3;
        ZTauElement round = round(approximateDivisionByN(bigInteger2, bigIntegerArr[0], bigInteger3, b5, i3, b6), approximateDivisionByN(bigInteger2, bigIntegerArr[1], bigInteger3, b5, i3, b6), b4);
        BigInteger bigInteger4 = bigInteger;
        return new ZTauElement(bigInteger.subtract(add.multiply(round.f732u)).subtract(BigInteger.valueOf(2).multiply(bigIntegerArr[1]).multiply(round.f733v)), bigIntegerArr[1].multiply(round.f732u).subtract(bigIntegerArr[0].multiply(round.f733v)));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0066, code lost:
        if (r5.compareTo(MINUS_ONE) < 0) goto L_0x0071;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x006f, code lost:
        if (r7.compareTo(org.bouncycastle.math.p018ec.ECConstants.TWO) >= 0) goto L_0x0071;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0081, code lost:
        if (r5.compareTo(org.bouncycastle.math.p018ec.ECConstants.ONE) >= 0) goto L_0x008c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x008a, code lost:
        if (r7.compareTo(MINUS_TWO) < 0) goto L_0x008c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.bouncycastle.math.p018ec.ZTauElement round(org.bouncycastle.math.p018ec.SimpleBigDecimal r7, org.bouncycastle.math.p018ec.SimpleBigDecimal r8, byte r9) {
        /*
            int r0 = r7.getScale()
            int r1 = r8.getScale()
            if (r1 != r0) goto L_0x00a7
            r0 = -1
            r1 = 1
            if (r9 == r1) goto L_0x0019
            if (r9 != r0) goto L_0x0011
            goto L_0x0019
        L_0x0011:
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException
            java.lang.String r8 = "mu must be 1 or -1"
            r7.<init>(r8)
            throw r7
        L_0x0019:
            java.math.BigInteger r2 = r7.round()
            java.math.BigInteger r3 = r8.round()
            org.bouncycastle.math.ec.SimpleBigDecimal r7 = r7.subtract((java.math.BigInteger) r2)
            org.bouncycastle.math.ec.SimpleBigDecimal r8 = r8.subtract((java.math.BigInteger) r3)
            org.bouncycastle.math.ec.SimpleBigDecimal r4 = r7.add((org.bouncycastle.math.p018ec.SimpleBigDecimal) r7)
            if (r9 != r1) goto L_0x0034
            org.bouncycastle.math.ec.SimpleBigDecimal r4 = r4.add((org.bouncycastle.math.p018ec.SimpleBigDecimal) r8)
            goto L_0x0038
        L_0x0034:
            org.bouncycastle.math.ec.SimpleBigDecimal r4 = r4.subtract((org.bouncycastle.math.p018ec.SimpleBigDecimal) r8)
        L_0x0038:
            org.bouncycastle.math.ec.SimpleBigDecimal r5 = r8.add((org.bouncycastle.math.p018ec.SimpleBigDecimal) r8)
            org.bouncycastle.math.ec.SimpleBigDecimal r5 = r5.add((org.bouncycastle.math.p018ec.SimpleBigDecimal) r8)
            org.bouncycastle.math.ec.SimpleBigDecimal r8 = r5.add((org.bouncycastle.math.p018ec.SimpleBigDecimal) r8)
            if (r9 != r1) goto L_0x004f
            org.bouncycastle.math.ec.SimpleBigDecimal r5 = r7.subtract((org.bouncycastle.math.p018ec.SimpleBigDecimal) r5)
            org.bouncycastle.math.ec.SimpleBigDecimal r7 = r7.add((org.bouncycastle.math.p018ec.SimpleBigDecimal) r8)
            goto L_0x0057
        L_0x004f:
            org.bouncycastle.math.ec.SimpleBigDecimal r5 = r7.add((org.bouncycastle.math.p018ec.SimpleBigDecimal) r5)
            org.bouncycastle.math.ec.SimpleBigDecimal r7 = r7.subtract((org.bouncycastle.math.p018ec.SimpleBigDecimal) r8)
        L_0x0057:
            java.math.BigInteger r8 = org.bouncycastle.math.p018ec.ECConstants.ONE
            int r8 = r4.compareTo((java.math.BigInteger) r8)
            r6 = 0
            if (r8 < 0) goto L_0x0069
            java.math.BigInteger r8 = MINUS_ONE
            int r8 = r5.compareTo((java.math.BigInteger) r8)
            if (r8 >= 0) goto L_0x0073
            goto L_0x0071
        L_0x0069:
            java.math.BigInteger r8 = org.bouncycastle.math.p018ec.ECConstants.TWO
            int r8 = r7.compareTo((java.math.BigInteger) r8)
            if (r8 < 0) goto L_0x0072
        L_0x0071:
            r6 = r9
        L_0x0072:
            r1 = 0
        L_0x0073:
            java.math.BigInteger r8 = MINUS_ONE
            int r8 = r4.compareTo((java.math.BigInteger) r8)
            if (r8 >= 0) goto L_0x0084
            java.math.BigInteger r7 = org.bouncycastle.math.p018ec.ECConstants.ONE
            int r7 = r5.compareTo((java.math.BigInteger) r7)
            if (r7 < 0) goto L_0x008f
            goto L_0x008c
        L_0x0084:
            java.math.BigInteger r8 = MINUS_TWO
            int r7 = r7.compareTo((java.math.BigInteger) r8)
            if (r7 >= 0) goto L_0x008e
        L_0x008c:
            int r7 = -r9
            byte r6 = (byte) r7
        L_0x008e:
            r0 = r1
        L_0x008f:
            long r7 = (long) r0
            java.math.BigInteger r7 = java.math.BigInteger.valueOf(r7)
            java.math.BigInteger r7 = r2.add(r7)
            long r8 = (long) r6
            java.math.BigInteger r8 = java.math.BigInteger.valueOf(r8)
            java.math.BigInteger r8 = r3.add(r8)
            org.bouncycastle.math.ec.ZTauElement r9 = new org.bouncycastle.math.ec.ZTauElement
            r9.<init>(r7, r8)
            return r9
        L_0x00a7:
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException
            java.lang.String r8 = "lambda0 and lambda1 do not have same scale"
            r7.<init>(r8)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.math.p018ec.Tnaf.round(org.bouncycastle.math.ec.SimpleBigDecimal, org.bouncycastle.math.ec.SimpleBigDecimal, byte):org.bouncycastle.math.ec.ZTauElement");
    }

    public static ECPoint.AbstractF2m tau(ECPoint.AbstractF2m abstractF2m) {
        return abstractF2m.tau();
    }

    public static byte[] tauAdicNaf(byte b, ZTauElement zTauElement) {
        BigInteger bigInteger;
        if (b == 1 || b == -1) {
            int bitLength = norm(b, zTauElement).bitLength();
            byte[] bArr = new byte[(bitLength > 30 ? bitLength + 4 : 34)];
            BigInteger bigInteger2 = zTauElement.f732u;
            BigInteger bigInteger3 = zTauElement.f733v;
            int i = 0;
            int i2 = 0;
            while (true) {
                if (!bigInteger.equals(ECConstants.ZERO) || !bigInteger3.equals(ECConstants.ZERO)) {
                    if (bigInteger.testBit(0)) {
                        byte intValue = (byte) ECConstants.TWO.subtract(bigInteger.subtract(bigInteger3.shiftLeft(1)).mod(ECConstants.FOUR)).intValue();
                        bArr[i2] = intValue;
                        bigInteger = intValue == 1 ? bigInteger.clearBit(0) : bigInteger.add(ECConstants.ONE);
                        i = i2;
                    } else {
                        bArr[i2] = 0;
                    }
                    BigInteger shiftRight = bigInteger.shiftRight(1);
                    BigInteger add = b == 1 ? bigInteger3.add(shiftRight) : bigInteger3.subtract(shiftRight);
                    i2++;
                    BigInteger negate = bigInteger.shiftRight(1).negate();
                    bigInteger2 = add;
                    bigInteger3 = negate;
                } else {
                    int i3 = i + 1;
                    byte[] bArr2 = new byte[i3];
                    System.arraycopy(bArr, 0, bArr2, 0, i3);
                    return bArr2;
                }
            }
        } else {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
    }

    public static byte[] tauAdicWNaf(byte b, ZTauElement zTauElement, byte b2, BigInteger bigInteger, BigInteger bigInteger2, ZTauElement[] zTauElementArr) {
        boolean z;
        if (b == 1 || b == -1) {
            int bitLength = norm(b, zTauElement).bitLength();
            byte[] bArr = new byte[(bitLength > 30 ? bitLength + 4 + b2 : b2 + 34)];
            BigInteger shiftRight = bigInteger.shiftRight(1);
            BigInteger bigInteger3 = zTauElement.f732u;
            BigInteger bigInteger4 = zTauElement.f733v;
            int i = 0;
            while (true) {
                if (bigInteger3.equals(ECConstants.ZERO) && bigInteger4.equals(ECConstants.ZERO)) {
                    return bArr;
                }
                if (bigInteger3.testBit(0)) {
                    BigInteger mod = bigInteger3.add(bigInteger4.multiply(bigInteger2)).mod(bigInteger);
                    if (mod.compareTo(shiftRight) >= 0) {
                        mod = mod.subtract(bigInteger);
                    }
                    byte intValue = (byte) mod.intValue();
                    bArr[i] = intValue;
                    if (intValue < 0) {
                        intValue = (byte) (-intValue);
                        z = false;
                    } else {
                        z = true;
                    }
                    if (z) {
                        bigInteger3 = bigInteger3.subtract(zTauElementArr[intValue].f732u);
                        bigInteger4 = bigInteger4.subtract(zTauElementArr[intValue].f733v);
                    } else {
                        bigInteger3 = bigInteger3.add(zTauElementArr[intValue].f732u);
                        bigInteger4 = bigInteger4.add(zTauElementArr[intValue].f733v);
                    }
                } else {
                    bArr[i] = 0;
                }
                BigInteger shiftRight2 = bigInteger3.shiftRight(1);
                BigInteger add = b == 1 ? bigInteger4.add(shiftRight2) : bigInteger4.subtract(shiftRight2);
                i++;
                BigInteger negate = bigInteger3.shiftRight(1).negate();
                bigInteger3 = add;
                bigInteger4 = negate;
            }
        } else {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
    }
}
