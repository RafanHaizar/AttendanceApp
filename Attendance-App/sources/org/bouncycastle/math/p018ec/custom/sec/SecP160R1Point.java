package org.bouncycastle.math.p018ec.custom.sec;

import org.bouncycastle.math.p018ec.ECCurve;
import org.bouncycastle.math.p018ec.ECFieldElement;
import org.bouncycastle.math.p018ec.ECPoint;
import org.bouncycastle.math.raw.Nat;
import org.bouncycastle.math.raw.Nat160;

/* renamed from: org.bouncycastle.math.ec.custom.sec.SecP160R1Point */
public class SecP160R1Point extends ECPoint.AbstractFp {
    SecP160R1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        super(eCCurve, eCFieldElement, eCFieldElement2);
    }

    SecP160R1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr) {
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
        SecP160R1FieldElement secP160R1FieldElement = (SecP160R1FieldElement) this.f729x;
        SecP160R1FieldElement secP160R1FieldElement2 = (SecP160R1FieldElement) this.f730y;
        SecP160R1FieldElement secP160R1FieldElement3 = (SecP160R1FieldElement) eCPoint.getXCoord();
        SecP160R1FieldElement secP160R1FieldElement4 = (SecP160R1FieldElement) eCPoint.getYCoord();
        SecP160R1FieldElement secP160R1FieldElement5 = (SecP160R1FieldElement) this.f731zs[0];
        SecP160R1FieldElement secP160R1FieldElement6 = (SecP160R1FieldElement) eCPoint.getZCoord(0);
        int[] createExt = Nat160.createExt();
        int[] create = Nat160.create();
        int[] create2 = Nat160.create();
        int[] create3 = Nat160.create();
        boolean isOne = secP160R1FieldElement5.isOne();
        if (isOne) {
            iArr2 = secP160R1FieldElement3.f756x;
            iArr = secP160R1FieldElement4.f756x;
        } else {
            SecP160R1Field.square(secP160R1FieldElement5.f756x, create2);
            SecP160R1Field.multiply(create2, secP160R1FieldElement3.f756x, create);
            SecP160R1Field.multiply(create2, secP160R1FieldElement5.f756x, create2);
            SecP160R1Field.multiply(create2, secP160R1FieldElement4.f756x, create2);
            iArr2 = create;
            iArr = create2;
        }
        boolean isOne2 = secP160R1FieldElement6.isOne();
        if (isOne2) {
            iArr4 = secP160R1FieldElement.f756x;
            iArr3 = secP160R1FieldElement2.f756x;
        } else {
            SecP160R1Field.square(secP160R1FieldElement6.f756x, create3);
            SecP160R1Field.multiply(create3, secP160R1FieldElement.f756x, createExt);
            SecP160R1Field.multiply(create3, secP160R1FieldElement6.f756x, create3);
            SecP160R1Field.multiply(create3, secP160R1FieldElement2.f756x, create3);
            iArr4 = createExt;
            iArr3 = create3;
        }
        int[] create4 = Nat160.create();
        SecP160R1Field.subtract(iArr4, iArr2, create4);
        SecP160R1Field.subtract(iArr3, iArr, create);
        if (Nat160.isZero(create4)) {
            return Nat160.isZero(create) ? twice() : curve.getInfinity();
        }
        SecP160R1Field.square(create4, create2);
        int[] create5 = Nat160.create();
        SecP160R1Field.multiply(create2, create4, create5);
        SecP160R1Field.multiply(create2, iArr4, create2);
        SecP160R1Field.negate(create5, create5);
        Nat160.mul(iArr3, create5, createExt);
        SecP160R1Field.reduce32(Nat160.addBothTo(create2, create2, create5), create5);
        SecP160R1FieldElement secP160R1FieldElement7 = new SecP160R1FieldElement(create3);
        SecP160R1Field.square(create, secP160R1FieldElement7.f756x);
        SecP160R1Field.subtract(secP160R1FieldElement7.f756x, create5, secP160R1FieldElement7.f756x);
        SecP160R1FieldElement secP160R1FieldElement8 = new SecP160R1FieldElement(create5);
        SecP160R1Field.subtract(create2, secP160R1FieldElement7.f756x, secP160R1FieldElement8.f756x);
        SecP160R1Field.multiplyAddToExt(secP160R1FieldElement8.f756x, create, createExt);
        SecP160R1Field.reduce(createExt, secP160R1FieldElement8.f756x);
        SecP160R1FieldElement secP160R1FieldElement9 = new SecP160R1FieldElement(create4);
        if (!isOne) {
            SecP160R1Field.multiply(secP160R1FieldElement9.f756x, secP160R1FieldElement5.f756x, secP160R1FieldElement9.f756x);
        }
        if (!isOne2) {
            SecP160R1Field.multiply(secP160R1FieldElement9.f756x, secP160R1FieldElement6.f756x, secP160R1FieldElement9.f756x);
        }
        return new SecP160R1Point(curve, secP160R1FieldElement7, secP160R1FieldElement8, new ECFieldElement[]{secP160R1FieldElement9});
    }

    /* access modifiers changed from: protected */
    public ECPoint detach() {
        return new SecP160R1Point((ECCurve) null, getAffineXCoord(), getAffineYCoord());
    }

    public ECPoint negate() {
        return isInfinity() ? this : new SecP160R1Point(this.curve, this.f729x, this.f730y.negate(), this.f731zs);
    }

    public ECPoint threeTimes() {
        return (isInfinity() || this.f730y.isZero()) ? this : twice().add(this);
    }

    public ECPoint twice() {
        if (isInfinity()) {
            return this;
        }
        ECCurve curve = getCurve();
        SecP160R1FieldElement secP160R1FieldElement = (SecP160R1FieldElement) this.f730y;
        if (secP160R1FieldElement.isZero()) {
            return curve.getInfinity();
        }
        SecP160R1FieldElement secP160R1FieldElement2 = (SecP160R1FieldElement) this.f729x;
        SecP160R1FieldElement secP160R1FieldElement3 = (SecP160R1FieldElement) this.f731zs[0];
        int[] create = Nat160.create();
        int[] create2 = Nat160.create();
        int[] create3 = Nat160.create();
        SecP160R1Field.square(secP160R1FieldElement.f756x, create3);
        int[] create4 = Nat160.create();
        SecP160R1Field.square(create3, create4);
        boolean isOne = secP160R1FieldElement3.isOne();
        int[] iArr = secP160R1FieldElement3.f756x;
        if (!isOne) {
            SecP160R1Field.square(secP160R1FieldElement3.f756x, create2);
            iArr = create2;
        }
        SecP160R1Field.subtract(secP160R1FieldElement2.f756x, iArr, create);
        SecP160R1Field.add(secP160R1FieldElement2.f756x, iArr, create2);
        SecP160R1Field.multiply(create2, create, create2);
        SecP160R1Field.reduce32(Nat160.addBothTo(create2, create2, create2), create2);
        SecP160R1Field.multiply(create3, secP160R1FieldElement2.f756x, create3);
        SecP160R1Field.reduce32(Nat.shiftUpBits(5, create3, 2, 0), create3);
        SecP160R1Field.reduce32(Nat.shiftUpBits(5, create4, 3, 0, create), create);
        SecP160R1FieldElement secP160R1FieldElement4 = new SecP160R1FieldElement(create4);
        SecP160R1Field.square(create2, secP160R1FieldElement4.f756x);
        SecP160R1Field.subtract(secP160R1FieldElement4.f756x, create3, secP160R1FieldElement4.f756x);
        SecP160R1Field.subtract(secP160R1FieldElement4.f756x, create3, secP160R1FieldElement4.f756x);
        SecP160R1FieldElement secP160R1FieldElement5 = new SecP160R1FieldElement(create3);
        SecP160R1Field.subtract(create3, secP160R1FieldElement4.f756x, secP160R1FieldElement5.f756x);
        SecP160R1Field.multiply(secP160R1FieldElement5.f756x, create2, secP160R1FieldElement5.f756x);
        SecP160R1Field.subtract(secP160R1FieldElement5.f756x, create, secP160R1FieldElement5.f756x);
        SecP160R1FieldElement secP160R1FieldElement6 = new SecP160R1FieldElement(create2);
        SecP160R1Field.twice(secP160R1FieldElement.f756x, secP160R1FieldElement6.f756x);
        if (!isOne) {
            SecP160R1Field.multiply(secP160R1FieldElement6.f756x, secP160R1FieldElement3.f756x, secP160R1FieldElement6.f756x);
        }
        return new SecP160R1Point(curve, secP160R1FieldElement4, secP160R1FieldElement5, new ECFieldElement[]{secP160R1FieldElement6});
    }

    public ECPoint twicePlus(ECPoint eCPoint) {
        return this == eCPoint ? threeTimes() : isInfinity() ? eCPoint : eCPoint.isInfinity() ? twice() : this.f730y.isZero() ? eCPoint : twice().add(eCPoint);
    }
}
