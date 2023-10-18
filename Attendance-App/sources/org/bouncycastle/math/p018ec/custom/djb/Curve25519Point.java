package org.bouncycastle.math.p018ec.custom.djb;

import org.bouncycastle.math.p018ec.ECCurve;
import org.bouncycastle.math.p018ec.ECFieldElement;
import org.bouncycastle.math.p018ec.ECPoint;
import org.bouncycastle.math.raw.Nat256;

/* renamed from: org.bouncycastle.math.ec.custom.djb.Curve25519Point */
public class Curve25519Point extends ECPoint.AbstractFp {
    Curve25519Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        super(eCCurve, eCFieldElement, eCFieldElement2);
    }

    Curve25519Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr) {
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
        Curve25519FieldElement curve25519FieldElement = (Curve25519FieldElement) this.f729x;
        Curve25519FieldElement curve25519FieldElement2 = (Curve25519FieldElement) this.f730y;
        Curve25519FieldElement curve25519FieldElement3 = (Curve25519FieldElement) this.f731zs[0];
        Curve25519FieldElement curve25519FieldElement4 = (Curve25519FieldElement) eCPoint.getXCoord();
        Curve25519FieldElement curve25519FieldElement5 = (Curve25519FieldElement) eCPoint.getYCoord();
        Curve25519FieldElement curve25519FieldElement6 = (Curve25519FieldElement) eCPoint.getZCoord(0);
        int[] createExt = Nat256.createExt();
        int[] create = Nat256.create();
        int[] create2 = Nat256.create();
        int[] create3 = Nat256.create();
        boolean isOne = curve25519FieldElement3.isOne();
        if (isOne) {
            iArr2 = curve25519FieldElement4.f739x;
            iArr = curve25519FieldElement5.f739x;
        } else {
            Curve25519Field.square(curve25519FieldElement3.f739x, create2);
            Curve25519Field.multiply(create2, curve25519FieldElement4.f739x, create);
            Curve25519Field.multiply(create2, curve25519FieldElement3.f739x, create2);
            Curve25519Field.multiply(create2, curve25519FieldElement5.f739x, create2);
            iArr2 = create;
            iArr = create2;
        }
        boolean isOne2 = curve25519FieldElement6.isOne();
        if (isOne2) {
            iArr4 = curve25519FieldElement.f739x;
            iArr3 = curve25519FieldElement2.f739x;
        } else {
            Curve25519Field.square(curve25519FieldElement6.f739x, create3);
            Curve25519Field.multiply(create3, curve25519FieldElement.f739x, createExt);
            Curve25519Field.multiply(create3, curve25519FieldElement6.f739x, create3);
            Curve25519Field.multiply(create3, curve25519FieldElement2.f739x, create3);
            iArr4 = createExt;
            iArr3 = create3;
        }
        int[] create4 = Nat256.create();
        Curve25519Field.subtract(iArr4, iArr2, create4);
        Curve25519Field.subtract(iArr3, iArr, create);
        if (Nat256.isZero(create4)) {
            return Nat256.isZero(create) ? twice() : curve.getInfinity();
        }
        int[] create5 = Nat256.create();
        Curve25519Field.square(create4, create5);
        int[] create6 = Nat256.create();
        Curve25519Field.multiply(create5, create4, create6);
        Curve25519Field.multiply(create5, iArr4, create2);
        Curve25519Field.negate(create6, create6);
        Nat256.mul(iArr3, create6, createExt);
        Curve25519Field.reduce27(Nat256.addBothTo(create2, create2, create6), create6);
        Curve25519FieldElement curve25519FieldElement7 = new Curve25519FieldElement(create3);
        Curve25519Field.square(create, curve25519FieldElement7.f739x);
        Curve25519Field.subtract(curve25519FieldElement7.f739x, create6, curve25519FieldElement7.f739x);
        Curve25519FieldElement curve25519FieldElement8 = new Curve25519FieldElement(create6);
        Curve25519Field.subtract(create2, curve25519FieldElement7.f739x, curve25519FieldElement8.f739x);
        Curve25519Field.multiplyAddToExt(curve25519FieldElement8.f739x, create, createExt);
        Curve25519Field.reduce(createExt, curve25519FieldElement8.f739x);
        Curve25519FieldElement curve25519FieldElement9 = new Curve25519FieldElement(create4);
        if (!isOne) {
            Curve25519Field.multiply(curve25519FieldElement9.f739x, curve25519FieldElement3.f739x, curve25519FieldElement9.f739x);
        }
        if (!isOne2) {
            Curve25519Field.multiply(curve25519FieldElement9.f739x, curve25519FieldElement6.f739x, curve25519FieldElement9.f739x);
        }
        if (!isOne || !isOne2) {
            create5 = null;
        }
        return new Curve25519Point(curve, curve25519FieldElement7, curve25519FieldElement8, new ECFieldElement[]{curve25519FieldElement9, calculateJacobianModifiedW(curve25519FieldElement9, create5)});
    }

    /* access modifiers changed from: protected */
    public Curve25519FieldElement calculateJacobianModifiedW(Curve25519FieldElement curve25519FieldElement, int[] iArr) {
        Curve25519FieldElement curve25519FieldElement2 = (Curve25519FieldElement) getCurve().getA();
        if (curve25519FieldElement.isOne()) {
            return curve25519FieldElement2;
        }
        Curve25519FieldElement curve25519FieldElement3 = new Curve25519FieldElement();
        if (iArr == null) {
            iArr = curve25519FieldElement3.f739x;
            Curve25519Field.square(curve25519FieldElement.f739x, iArr);
        }
        Curve25519Field.square(iArr, curve25519FieldElement3.f739x);
        Curve25519Field.multiply(curve25519FieldElement3.f739x, curve25519FieldElement2.f739x, curve25519FieldElement3.f739x);
        return curve25519FieldElement3;
    }

    /* access modifiers changed from: protected */
    public ECPoint detach() {
        return new Curve25519Point((ECCurve) null, getAffineXCoord(), getAffineYCoord());
    }

    /* access modifiers changed from: protected */
    public Curve25519FieldElement getJacobianModifiedW() {
        Curve25519FieldElement curve25519FieldElement = (Curve25519FieldElement) this.f731zs[1];
        if (curve25519FieldElement != null) {
            return curve25519FieldElement;
        }
        ECFieldElement[] eCFieldElementArr = this.f731zs;
        Curve25519FieldElement calculateJacobianModifiedW = calculateJacobianModifiedW((Curve25519FieldElement) this.f731zs[0], (int[]) null);
        eCFieldElementArr[1] = calculateJacobianModifiedW;
        return calculateJacobianModifiedW;
    }

    public ECFieldElement getZCoord(int i) {
        return i == 1 ? getJacobianModifiedW() : super.getZCoord(i);
    }

    public ECPoint negate() {
        return isInfinity() ? this : new Curve25519Point(getCurve(), this.f729x, this.f730y.negate(), this.f731zs);
    }

    public ECPoint threeTimes() {
        return (!isInfinity() && !this.f730y.isZero()) ? twiceJacobianModified(false).add(this) : this;
    }

    public ECPoint twice() {
        if (isInfinity()) {
            return this;
        }
        return this.f730y.isZero() ? getCurve().getInfinity() : twiceJacobianModified(true);
    }

    /* access modifiers changed from: protected */
    public Curve25519Point twiceJacobianModified(boolean z) {
        Curve25519FieldElement curve25519FieldElement;
        Curve25519FieldElement curve25519FieldElement2 = (Curve25519FieldElement) this.f729x;
        Curve25519FieldElement curve25519FieldElement3 = (Curve25519FieldElement) this.f730y;
        Curve25519FieldElement curve25519FieldElement4 = (Curve25519FieldElement) this.f731zs[0];
        Curve25519FieldElement jacobianModifiedW = getJacobianModifiedW();
        int[] create = Nat256.create();
        Curve25519Field.square(curve25519FieldElement2.f739x, create);
        Curve25519Field.reduce27(Nat256.addBothTo(create, create, create) + Nat256.addTo(jacobianModifiedW.f739x, create), create);
        int[] create2 = Nat256.create();
        Curve25519Field.twice(curve25519FieldElement3.f739x, create2);
        int[] create3 = Nat256.create();
        Curve25519Field.multiply(create2, curve25519FieldElement3.f739x, create3);
        int[] create4 = Nat256.create();
        Curve25519Field.multiply(create3, curve25519FieldElement2.f739x, create4);
        Curve25519Field.twice(create4, create4);
        int[] create5 = Nat256.create();
        Curve25519Field.square(create3, create5);
        Curve25519Field.twice(create5, create5);
        Curve25519FieldElement curve25519FieldElement5 = new Curve25519FieldElement(create3);
        Curve25519Field.square(create, curve25519FieldElement5.f739x);
        Curve25519Field.subtract(curve25519FieldElement5.f739x, create4, curve25519FieldElement5.f739x);
        Curve25519Field.subtract(curve25519FieldElement5.f739x, create4, curve25519FieldElement5.f739x);
        Curve25519FieldElement curve25519FieldElement6 = new Curve25519FieldElement(create4);
        Curve25519Field.subtract(create4, curve25519FieldElement5.f739x, curve25519FieldElement6.f739x);
        Curve25519Field.multiply(curve25519FieldElement6.f739x, create, curve25519FieldElement6.f739x);
        Curve25519Field.subtract(curve25519FieldElement6.f739x, create5, curve25519FieldElement6.f739x);
        Curve25519FieldElement curve25519FieldElement7 = new Curve25519FieldElement(create2);
        if (!Nat256.isOne(curve25519FieldElement4.f739x)) {
            Curve25519Field.multiply(curve25519FieldElement7.f739x, curve25519FieldElement4.f739x, curve25519FieldElement7.f739x);
        }
        if (z) {
            curve25519FieldElement = new Curve25519FieldElement(create5);
            Curve25519Field.multiply(curve25519FieldElement.f739x, jacobianModifiedW.f739x, curve25519FieldElement.f739x);
            Curve25519Field.twice(curve25519FieldElement.f739x, curve25519FieldElement.f739x);
        } else {
            curve25519FieldElement = null;
        }
        return new Curve25519Point(getCurve(), curve25519FieldElement5, curve25519FieldElement6, new ECFieldElement[]{curve25519FieldElement7, curve25519FieldElement});
    }

    public ECPoint twicePlus(ECPoint eCPoint) {
        return this == eCPoint ? threeTimes() : isInfinity() ? eCPoint : eCPoint.isInfinity() ? twice() : this.f730y.isZero() ? eCPoint : twiceJacobianModified(false).add(eCPoint);
    }
}
