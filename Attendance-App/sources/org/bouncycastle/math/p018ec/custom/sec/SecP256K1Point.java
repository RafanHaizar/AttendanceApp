package org.bouncycastle.math.p018ec.custom.sec;

import org.bouncycastle.math.p018ec.ECCurve;
import org.bouncycastle.math.p018ec.ECFieldElement;
import org.bouncycastle.math.p018ec.ECPoint;
import org.bouncycastle.math.raw.Nat;
import org.bouncycastle.math.raw.Nat256;

/* renamed from: org.bouncycastle.math.ec.custom.sec.SecP256K1Point */
public class SecP256K1Point extends ECPoint.AbstractFp {
    SecP256K1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        super(eCCurve, eCFieldElement, eCFieldElement2);
    }

    SecP256K1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr) {
        super(eCCurve, eCFieldElement, eCFieldElement2, eCFieldElementArr);
    }

    public ECPoint add(ECPoint eCPoint) {
        int[] iArr;
        int[] iArr2;
        int[] iArr3;
        int[] iArr4;
        if (isInfinity()) {
            return eCPoint;
        }
        if (eCPoint.isInfinity()) {
            return this;
        }
        if (this == eCPoint) {
            return twice();
        }
        ECCurve curve = getCurve();
        SecP256K1FieldElement secP256K1FieldElement = (SecP256K1FieldElement) this.f729x;
        SecP256K1FieldElement secP256K1FieldElement2 = (SecP256K1FieldElement) this.f730y;
        SecP256K1FieldElement secP256K1FieldElement3 = (SecP256K1FieldElement) eCPoint.getXCoord();
        SecP256K1FieldElement secP256K1FieldElement4 = (SecP256K1FieldElement) eCPoint.getYCoord();
        SecP256K1FieldElement secP256K1FieldElement5 = (SecP256K1FieldElement) this.f731zs[0];
        SecP256K1FieldElement secP256K1FieldElement6 = (SecP256K1FieldElement) eCPoint.getZCoord(0);
        int[] createExt = Nat256.createExt();
        int[] create = Nat256.create();
        int[] create2 = Nat256.create();
        int[] create3 = Nat256.create();
        boolean isOne = secP256K1FieldElement5.isOne();
        if (isOne) {
            iArr2 = secP256K1FieldElement3.f788x;
            iArr = secP256K1FieldElement4.f788x;
        } else {
            SecP256K1Field.square(secP256K1FieldElement5.f788x, create2);
            SecP256K1Field.multiply(create2, secP256K1FieldElement3.f788x, create);
            SecP256K1Field.multiply(create2, secP256K1FieldElement5.f788x, create2);
            SecP256K1Field.multiply(create2, secP256K1FieldElement4.f788x, create2);
            iArr2 = create;
            iArr = create2;
        }
        boolean isOne2 = secP256K1FieldElement6.isOne();
        if (isOne2) {
            iArr4 = secP256K1FieldElement.f788x;
            iArr3 = secP256K1FieldElement2.f788x;
        } else {
            SecP256K1Field.square(secP256K1FieldElement6.f788x, create3);
            SecP256K1Field.multiply(create3, secP256K1FieldElement.f788x, createExt);
            SecP256K1Field.multiply(create3, secP256K1FieldElement6.f788x, create3);
            SecP256K1Field.multiply(create3, secP256K1FieldElement2.f788x, create3);
            iArr4 = createExt;
            iArr3 = create3;
        }
        int[] create4 = Nat256.create();
        SecP256K1Field.subtract(iArr4, iArr2, create4);
        SecP256K1Field.subtract(iArr3, iArr, create);
        if (Nat256.isZero(create4)) {
            return Nat256.isZero(create) ? twice() : curve.getInfinity();
        }
        SecP256K1Field.square(create4, create2);
        int[] create5 = Nat256.create();
        SecP256K1Field.multiply(create2, create4, create5);
        SecP256K1Field.multiply(create2, iArr4, create2);
        SecP256K1Field.negate(create5, create5);
        Nat256.mul(iArr3, create5, createExt);
        SecP256K1Field.reduce32(Nat256.addBothTo(create2, create2, create5), create5);
        SecP256K1FieldElement secP256K1FieldElement7 = new SecP256K1FieldElement(create3);
        SecP256K1Field.square(create, secP256K1FieldElement7.f788x);
        SecP256K1Field.subtract(secP256K1FieldElement7.f788x, create5, secP256K1FieldElement7.f788x);
        SecP256K1FieldElement secP256K1FieldElement8 = new SecP256K1FieldElement(create5);
        SecP256K1Field.subtract(create2, secP256K1FieldElement7.f788x, secP256K1FieldElement8.f788x);
        SecP256K1Field.multiplyAddToExt(secP256K1FieldElement8.f788x, create, createExt);
        SecP256K1Field.reduce(createExt, secP256K1FieldElement8.f788x);
        SecP256K1FieldElement secP256K1FieldElement9 = new SecP256K1FieldElement(create4);
        if (!isOne) {
            SecP256K1Field.multiply(secP256K1FieldElement9.f788x, secP256K1FieldElement5.f788x, secP256K1FieldElement9.f788x);
        }
        if (!isOne2) {
            SecP256K1Field.multiply(secP256K1FieldElement9.f788x, secP256K1FieldElement6.f788x, secP256K1FieldElement9.f788x);
        }
        return new SecP256K1Point(curve, secP256K1FieldElement7, secP256K1FieldElement8, new ECFieldElement[]{secP256K1FieldElement9});
    }

    /* access modifiers changed from: protected */
    public ECPoint detach() {
        return new SecP256K1Point((ECCurve) null, getAffineXCoord(), getAffineYCoord());
    }

    public ECPoint negate() {
        return isInfinity() ? this : new SecP256K1Point(this.curve, this.f729x, this.f730y.negate(), this.f731zs);
    }

    public ECPoint threeTimes() {
        return (isInfinity() || this.f730y.isZero()) ? this : twice().add(this);
    }

    public ECPoint twice() {
        if (isInfinity()) {
            return this;
        }
        ECCurve curve = getCurve();
        SecP256K1FieldElement secP256K1FieldElement = (SecP256K1FieldElement) this.f730y;
        if (secP256K1FieldElement.isZero()) {
            return curve.getInfinity();
        }
        SecP256K1FieldElement secP256K1FieldElement2 = (SecP256K1FieldElement) this.f729x;
        SecP256K1FieldElement secP256K1FieldElement3 = (SecP256K1FieldElement) this.f731zs[0];
        int[] create = Nat256.create();
        SecP256K1Field.square(secP256K1FieldElement.f788x, create);
        int[] create2 = Nat256.create();
        SecP256K1Field.square(create, create2);
        int[] create3 = Nat256.create();
        SecP256K1Field.square(secP256K1FieldElement2.f788x, create3);
        SecP256K1Field.reduce32(Nat256.addBothTo(create3, create3, create3), create3);
        SecP256K1Field.multiply(create, secP256K1FieldElement2.f788x, create);
        SecP256K1Field.reduce32(Nat.shiftUpBits(8, create, 2, 0), create);
        int[] create4 = Nat256.create();
        SecP256K1Field.reduce32(Nat.shiftUpBits(8, create2, 3, 0, create4), create4);
        SecP256K1FieldElement secP256K1FieldElement4 = new SecP256K1FieldElement(create2);
        SecP256K1Field.square(create3, secP256K1FieldElement4.f788x);
        SecP256K1Field.subtract(secP256K1FieldElement4.f788x, create, secP256K1FieldElement4.f788x);
        SecP256K1Field.subtract(secP256K1FieldElement4.f788x, create, secP256K1FieldElement4.f788x);
        SecP256K1FieldElement secP256K1FieldElement5 = new SecP256K1FieldElement(create);
        SecP256K1Field.subtract(create, secP256K1FieldElement4.f788x, secP256K1FieldElement5.f788x);
        SecP256K1Field.multiply(secP256K1FieldElement5.f788x, create3, secP256K1FieldElement5.f788x);
        SecP256K1Field.subtract(secP256K1FieldElement5.f788x, create4, secP256K1FieldElement5.f788x);
        SecP256K1FieldElement secP256K1FieldElement6 = new SecP256K1FieldElement(create3);
        SecP256K1Field.twice(secP256K1FieldElement.f788x, secP256K1FieldElement6.f788x);
        if (!secP256K1FieldElement3.isOne()) {
            SecP256K1Field.multiply(secP256K1FieldElement6.f788x, secP256K1FieldElement3.f788x, secP256K1FieldElement6.f788x);
        }
        return new SecP256K1Point(curve, secP256K1FieldElement4, secP256K1FieldElement5, new ECFieldElement[]{secP256K1FieldElement6});
    }

    public ECPoint twicePlus(ECPoint eCPoint) {
        return this == eCPoint ? threeTimes() : isInfinity() ? eCPoint : eCPoint.isInfinity() ? twice() : this.f730y.isZero() ? eCPoint : twice().add(eCPoint);
    }
}
