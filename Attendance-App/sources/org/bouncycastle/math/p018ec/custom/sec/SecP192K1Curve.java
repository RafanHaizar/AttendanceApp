package org.bouncycastle.math.p018ec.custom.sec;

import java.math.BigInteger;
import org.bouncycastle.math.p018ec.AbstractECLookupTable;
import org.bouncycastle.math.p018ec.ECConstants;
import org.bouncycastle.math.p018ec.ECCurve;
import org.bouncycastle.math.p018ec.ECFieldElement;
import org.bouncycastle.math.p018ec.ECLookupTable;
import org.bouncycastle.math.p018ec.ECPoint;
import org.bouncycastle.math.raw.Nat192;
import org.bouncycastle.util.encoders.Hex;

/* renamed from: org.bouncycastle.math.ec.custom.sec.SecP192K1Curve */
public class SecP192K1Curve extends ECCurve.AbstractFp {
    /* access modifiers changed from: private */
    public static final ECFieldElement[] SECP192K1_AFFINE_ZS = {new SecP192K1FieldElement(ECConstants.ONE)};
    private static final int SECP192K1_DEFAULT_COORDS = 2;

    /* renamed from: q */
    public static final BigInteger f762q = SecP192K1FieldElement.f765Q;
    protected SecP192K1Point infinity = new SecP192K1Point(this, (ECFieldElement) null, (ECFieldElement) null);

    public SecP192K1Curve() {
        super(f762q);
        this.f714a = fromBigInteger(ECConstants.ZERO);
        this.f715b = fromBigInteger(BigInteger.valueOf(3));
        this.order = new BigInteger(1, Hex.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFE26F2FC170F69466A74DEFD8D"));
        this.cofactor = BigInteger.valueOf(1);
        this.coord = 2;
    }

    /* access modifiers changed from: protected */
    public ECCurve cloneCurve() {
        return new SecP192K1Curve();
    }

    public ECLookupTable createCacheSafeLookupTable(ECPoint[] eCPointArr, int i, final int i2) {
        final int[] iArr = new int[(i2 * 6 * 2)];
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            ECPoint eCPoint = eCPointArr[i + i4];
            Nat192.copy(((SecP192K1FieldElement) eCPoint.getRawXCoord()).f766x, 0, iArr, i3);
            int i5 = i3 + 6;
            Nat192.copy(((SecP192K1FieldElement) eCPoint.getRawYCoord()).f766x, 0, iArr, i5);
            i3 = i5 + 6;
        }
        return new AbstractECLookupTable() {
            private ECPoint createPoint(int[] iArr, int[] iArr2) {
                return SecP192K1Curve.this.createRawPoint(new SecP192K1FieldElement(iArr), new SecP192K1FieldElement(iArr2), SecP192K1Curve.SECP192K1_AFFINE_ZS);
            }

            public int getSize() {
                return i2;
            }

            public ECPoint lookup(int i) {
                int[] create = Nat192.create();
                int[] create2 = Nat192.create();
                int i2 = 0;
                for (int i3 = 0; i3 < i2; i3++) {
                    int i4 = ((i3 ^ i) - 1) >> 31;
                    for (int i5 = 0; i5 < 6; i5++) {
                        int i6 = create[i5];
                        int[] iArr = iArr;
                        create[i5] = i6 ^ (iArr[i2 + i5] & i4);
                        create2[i5] = create2[i5] ^ (iArr[(i2 + 6) + i5] & i4);
                    }
                    i2 += 12;
                }
                return createPoint(create, create2);
            }

            public ECPoint lookupVar(int i) {
                int[] create = Nat192.create();
                int[] create2 = Nat192.create();
                int i2 = i * 6 * 2;
                for (int i3 = 0; i3 < 6; i3++) {
                    int[] iArr = iArr;
                    create[i3] = iArr[i2 + i3];
                    create2[i3] = iArr[i2 + 6 + i3];
                }
                return createPoint(create, create2);
            }
        };
    }

    /* access modifiers changed from: protected */
    public ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        return new SecP192K1Point(this, eCFieldElement, eCFieldElement2);
    }

    /* access modifiers changed from: protected */
    public ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr) {
        return new SecP192K1Point(this, eCFieldElement, eCFieldElement2, eCFieldElementArr);
    }

    public ECFieldElement fromBigInteger(BigInteger bigInteger) {
        return new SecP192K1FieldElement(bigInteger);
    }

    public int getFieldSize() {
        return f762q.bitLength();
    }

    public ECPoint getInfinity() {
        return this.infinity;
    }

    public BigInteger getQ() {
        return f762q;
    }

    public boolean supportsCoordinateSystem(int i) {
        switch (i) {
            case 2:
                return true;
            default:
                return false;
        }
    }
}
