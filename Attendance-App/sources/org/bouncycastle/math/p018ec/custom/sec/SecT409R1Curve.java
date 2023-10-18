package org.bouncycastle.math.p018ec.custom.sec;

import java.math.BigInteger;
import org.bouncycastle.math.p018ec.AbstractECLookupTable;
import org.bouncycastle.math.p018ec.ECConstants;
import org.bouncycastle.math.p018ec.ECCurve;
import org.bouncycastle.math.p018ec.ECFieldElement;
import org.bouncycastle.math.p018ec.ECLookupTable;
import org.bouncycastle.math.p018ec.ECPoint;
import org.bouncycastle.math.raw.Nat448;
import org.bouncycastle.util.encoders.Hex;

/* renamed from: org.bouncycastle.math.ec.custom.sec.SecT409R1Curve */
public class SecT409R1Curve extends ECCurve.AbstractF2m {
    /* access modifiers changed from: private */
    public static final ECFieldElement[] SECT409R1_AFFINE_ZS = {new SecT409FieldElement(ECConstants.ONE)};
    private static final int SECT409R1_DEFAULT_COORDS = 6;
    protected SecT409R1Point infinity = new SecT409R1Point(this, (ECFieldElement) null, (ECFieldElement) null);

    public SecT409R1Curve() {
        super(409, 87, 0, 0);
        this.f714a = fromBigInteger(BigInteger.valueOf(1));
        this.f715b = fromBigInteger(new BigInteger(1, Hex.decodeStrict("0021A5C2C8EE9FEB5C4B9A753B7B476B7FD6422EF1F3DD674761FA99D6AC27C8A9A197B272822F6CD57A55AA4F50AE317B13545F")));
        this.order = new BigInteger(1, Hex.decodeStrict("010000000000000000000000000000000000000000000000000001E2AAD6A612F33307BE5FA47C3C9E052F838164CD37D9A21173"));
        this.cofactor = BigInteger.valueOf(2);
        this.coord = 6;
    }

    /* access modifiers changed from: protected */
    public ECCurve cloneCurve() {
        return new SecT409R1Curve();
    }

    public ECLookupTable createCacheSafeLookupTable(ECPoint[] eCPointArr, int i, final int i2) {
        final long[] jArr = new long[(i2 * 7 * 2)];
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            ECPoint eCPoint = eCPointArr[i + i4];
            Nat448.copy64(((SecT409FieldElement) eCPoint.getRawXCoord()).f811x, 0, jArr, i3);
            int i5 = i3 + 7;
            Nat448.copy64(((SecT409FieldElement) eCPoint.getRawYCoord()).f811x, 0, jArr, i5);
            i3 = i5 + 7;
        }
        return new AbstractECLookupTable() {
            private ECPoint createPoint(long[] jArr, long[] jArr2) {
                return SecT409R1Curve.this.createRawPoint(new SecT409FieldElement(jArr), new SecT409FieldElement(jArr2), SecT409R1Curve.SECT409R1_AFFINE_ZS);
            }

            public int getSize() {
                return i2;
            }

            public ECPoint lookup(int i) {
                long[] create64 = Nat448.create64();
                long[] create642 = Nat448.create64();
                int i2 = 0;
                for (int i3 = 0; i3 < i2; i3++) {
                    long j = (long) (((i3 ^ i) - 1) >> 31);
                    for (int i4 = 0; i4 < 7; i4++) {
                        long j2 = create64[i4];
                        long[] jArr = jArr;
                        create64[i4] = j2 ^ (jArr[i2 + i4] & j);
                        create642[i4] = create642[i4] ^ (jArr[(i2 + 7) + i4] & j);
                    }
                    i2 += 14;
                }
                return createPoint(create64, create642);
            }

            public ECPoint lookupVar(int i) {
                long[] create64 = Nat448.create64();
                long[] create642 = Nat448.create64();
                int i2 = i * 7 * 2;
                for (int i3 = 0; i3 < 7; i3++) {
                    long[] jArr = jArr;
                    create64[i3] = jArr[i2 + i3];
                    create642[i3] = jArr[i2 + 7 + i3];
                }
                return createPoint(create64, create642);
            }
        };
    }

    /* access modifiers changed from: protected */
    public ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        return new SecT409R1Point(this, eCFieldElement, eCFieldElement2);
    }

    /* access modifiers changed from: protected */
    public ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr) {
        return new SecT409R1Point(this, eCFieldElement, eCFieldElement2, eCFieldElementArr);
    }

    public ECFieldElement fromBigInteger(BigInteger bigInteger) {
        return new SecT409FieldElement(bigInteger);
    }

    public int getFieldSize() {
        return 409;
    }

    public ECPoint getInfinity() {
        return this.infinity;
    }

    public int getK1() {
        return 87;
    }

    public int getK2() {
        return 0;
    }

    public int getK3() {
        return 0;
    }

    public int getM() {
        return 409;
    }

    public boolean isKoblitz() {
        return false;
    }

    public boolean isTrinomial() {
        return true;
    }

    public boolean supportsCoordinateSystem(int i) {
        switch (i) {
            case 6:
                return true;
            default:
                return false;
        }
    }
}
