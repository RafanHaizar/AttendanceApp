package org.bouncycastle.math.p018ec.custom.sec;

import com.itextpdf.p026io.codec.TIFFConstants;
import java.math.BigInteger;
import org.bouncycastle.math.p018ec.AbstractECLookupTable;
import org.bouncycastle.math.p018ec.ECConstants;
import org.bouncycastle.math.p018ec.ECCurve;
import org.bouncycastle.math.p018ec.ECFieldElement;
import org.bouncycastle.math.p018ec.ECLookupTable;
import org.bouncycastle.math.p018ec.ECMultiplier;
import org.bouncycastle.math.p018ec.ECPoint;
import org.bouncycastle.math.p018ec.WTauNafMultiplier;
import org.bouncycastle.math.raw.Nat320;
import org.bouncycastle.util.encoders.Hex;

/* renamed from: org.bouncycastle.math.ec.custom.sec.SecT283K1Curve */
public class SecT283K1Curve extends ECCurve.AbstractF2m {
    /* access modifiers changed from: private */
    public static final ECFieldElement[] SECT283K1_AFFINE_ZS = {new SecT283FieldElement(ECConstants.ONE)};
    private static final int SECT283K1_DEFAULT_COORDS = 6;
    protected SecT283K1Point infinity = new SecT283K1Point(this, (ECFieldElement) null, (ECFieldElement) null);

    public SecT283K1Curve() {
        super(TIFFConstants.TIFFTAG_YRESOLUTION, 5, 7, 12);
        this.f714a = fromBigInteger(BigInteger.valueOf(0));
        this.f715b = fromBigInteger(BigInteger.valueOf(1));
        this.order = new BigInteger(1, Hex.decodeStrict("01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFE9AE2ED07577265DFF7F94451E061E163C61"));
        this.cofactor = BigInteger.valueOf(4);
        this.coord = 6;
    }

    /* access modifiers changed from: protected */
    public ECCurve cloneCurve() {
        return new SecT283K1Curve();
    }

    public ECLookupTable createCacheSafeLookupTable(ECPoint[] eCPointArr, int i, final int i2) {
        final long[] jArr = new long[(i2 * 5 * 2)];
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            ECPoint eCPoint = eCPointArr[i + i4];
            Nat320.copy64(((SecT283FieldElement) eCPoint.getRawXCoord()).f810x, 0, jArr, i3);
            int i5 = i3 + 5;
            Nat320.copy64(((SecT283FieldElement) eCPoint.getRawYCoord()).f810x, 0, jArr, i5);
            i3 = i5 + 5;
        }
        return new AbstractECLookupTable() {
            private ECPoint createPoint(long[] jArr, long[] jArr2) {
                return SecT283K1Curve.this.createRawPoint(new SecT283FieldElement(jArr), new SecT283FieldElement(jArr2), SecT283K1Curve.SECT283K1_AFFINE_ZS);
            }

            public int getSize() {
                return i2;
            }

            public ECPoint lookup(int i) {
                long[] create64 = Nat320.create64();
                long[] create642 = Nat320.create64();
                int i2 = 0;
                for (int i3 = 0; i3 < i2; i3++) {
                    long j = (long) (((i3 ^ i) - 1) >> 31);
                    for (int i4 = 0; i4 < 5; i4++) {
                        long j2 = create64[i4];
                        long[] jArr = jArr;
                        create64[i4] = j2 ^ (jArr[i2 + i4] & j);
                        create642[i4] = create642[i4] ^ (jArr[(i2 + 5) + i4] & j);
                    }
                    i2 += 10;
                }
                return createPoint(create64, create642);
            }

            public ECPoint lookupVar(int i) {
                long[] create64 = Nat320.create64();
                long[] create642 = Nat320.create64();
                int i2 = i * 5 * 2;
                for (int i3 = 0; i3 < 5; i3++) {
                    long[] jArr = jArr;
                    create64[i3] = jArr[i2 + i3];
                    create642[i3] = jArr[i2 + 5 + i3];
                }
                return createPoint(create64, create642);
            }
        };
    }

    /* access modifiers changed from: protected */
    public ECMultiplier createDefaultMultiplier() {
        return new WTauNafMultiplier();
    }

    /* access modifiers changed from: protected */
    public ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        return new SecT283K1Point(this, eCFieldElement, eCFieldElement2);
    }

    /* access modifiers changed from: protected */
    public ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr) {
        return new SecT283K1Point(this, eCFieldElement, eCFieldElement2, eCFieldElementArr);
    }

    public ECFieldElement fromBigInteger(BigInteger bigInteger) {
        return new SecT283FieldElement(bigInteger);
    }

    public int getFieldSize() {
        return TIFFConstants.TIFFTAG_YRESOLUTION;
    }

    public ECPoint getInfinity() {
        return this.infinity;
    }

    public int getK1() {
        return 5;
    }

    public int getK2() {
        return 7;
    }

    public int getK3() {
        return 12;
    }

    public int getM() {
        return TIFFConstants.TIFFTAG_YRESOLUTION;
    }

    public boolean isKoblitz() {
        return true;
    }

    public boolean isTrinomial() {
        return false;
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
