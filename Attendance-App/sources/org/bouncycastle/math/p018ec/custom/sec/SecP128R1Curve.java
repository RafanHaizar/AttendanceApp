package org.bouncycastle.math.p018ec.custom.sec;

import java.math.BigInteger;
import org.bouncycastle.math.p018ec.AbstractECLookupTable;
import org.bouncycastle.math.p018ec.ECConstants;
import org.bouncycastle.math.p018ec.ECCurve;
import org.bouncycastle.math.p018ec.ECFieldElement;
import org.bouncycastle.math.p018ec.ECLookupTable;
import org.bouncycastle.math.p018ec.ECPoint;
import org.bouncycastle.math.raw.Nat128;
import org.bouncycastle.util.encoders.Hex;

/* renamed from: org.bouncycastle.math.ec.custom.sec.SecP128R1Curve */
public class SecP128R1Curve extends ECCurve.AbstractFp {
    /* access modifiers changed from: private */
    public static final ECFieldElement[] SECP128R1_AFFINE_ZS = {new SecP128R1FieldElement(ECConstants.ONE)};
    private static final int SECP128R1_DEFAULT_COORDS = 2;

    /* renamed from: q */
    public static final BigInteger f745q = SecP128R1FieldElement.f748Q;
    protected SecP128R1Point infinity = new SecP128R1Point(this, (ECFieldElement) null, (ECFieldElement) null);

    public SecP128R1Curve() {
        super(f745q);
        this.f714a = fromBigInteger(new BigInteger(1, Hex.decodeStrict("FFFFFFFDFFFFFFFFFFFFFFFFFFFFFFFC")));
        this.f715b = fromBigInteger(new BigInteger(1, Hex.decodeStrict("E87579C11079F43DD824993C2CEE5ED3")));
        this.order = new BigInteger(1, Hex.decodeStrict("FFFFFFFE0000000075A30D1B9038A115"));
        this.cofactor = BigInteger.valueOf(1);
        this.coord = 2;
    }

    /* access modifiers changed from: protected */
    public ECCurve cloneCurve() {
        return new SecP128R1Curve();
    }

    public ECLookupTable createCacheSafeLookupTable(ECPoint[] eCPointArr, int i, final int i2) {
        final int[] iArr = new int[(i2 * 4 * 2)];
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            ECPoint eCPoint = eCPointArr[i + i4];
            Nat128.copy(((SecP128R1FieldElement) eCPoint.getRawXCoord()).f749x, 0, iArr, i3);
            int i5 = i3 + 4;
            Nat128.copy(((SecP128R1FieldElement) eCPoint.getRawYCoord()).f749x, 0, iArr, i5);
            i3 = i5 + 4;
        }
        return new AbstractECLookupTable() {
            private ECPoint createPoint(int[] iArr, int[] iArr2) {
                return SecP128R1Curve.this.createRawPoint(new SecP128R1FieldElement(iArr), new SecP128R1FieldElement(iArr2), SecP128R1Curve.SECP128R1_AFFINE_ZS);
            }

            public int getSize() {
                return i2;
            }

            public ECPoint lookup(int i) {
                int[] create = Nat128.create();
                int[] create2 = Nat128.create();
                int i2 = 0;
                for (int i3 = 0; i3 < i2; i3++) {
                    int i4 = ((i3 ^ i) - 1) >> 31;
                    for (int i5 = 0; i5 < 4; i5++) {
                        int i6 = create[i5];
                        int[] iArr = iArr;
                        create[i5] = i6 ^ (iArr[i2 + i5] & i4);
                        create2[i5] = create2[i5] ^ (iArr[(i2 + 4) + i5] & i4);
                    }
                    i2 += 8;
                }
                return createPoint(create, create2);
            }

            public ECPoint lookupVar(int i) {
                int[] create = Nat128.create();
                int[] create2 = Nat128.create();
                int i2 = i * 4 * 2;
                for (int i3 = 0; i3 < 4; i3++) {
                    int[] iArr = iArr;
                    create[i3] = iArr[i2 + i3];
                    create2[i3] = iArr[i2 + 4 + i3];
                }
                return createPoint(create, create2);
            }
        };
    }

    /* access modifiers changed from: protected */
    public ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        return new SecP128R1Point(this, eCFieldElement, eCFieldElement2);
    }

    /* access modifiers changed from: protected */
    public ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr) {
        return new SecP128R1Point(this, eCFieldElement, eCFieldElement2, eCFieldElementArr);
    }

    public ECFieldElement fromBigInteger(BigInteger bigInteger) {
        return new SecP128R1FieldElement(bigInteger);
    }

    public int getFieldSize() {
        return f745q.bitLength();
    }

    public ECPoint getInfinity() {
        return this.infinity;
    }

    public BigInteger getQ() {
        return f745q;
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
