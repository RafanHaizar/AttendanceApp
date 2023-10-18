package org.bouncycastle.math.p018ec.custom.sec;

import org.bouncycastle.math.p018ec.ECCurve;
import org.bouncycastle.math.p018ec.ECFieldElement;
import org.bouncycastle.math.p018ec.ECPoint;
import org.bouncycastle.math.raw.Nat;

/* renamed from: org.bouncycastle.math.ec.custom.sec.SecP384R1Point */
public class SecP384R1Point extends ECPoint.AbstractFp {
    SecP384R1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        super(eCCurve, eCFieldElement, eCFieldElement2);
    }

    SecP384R1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr) {
        super(eCCurve, eCFieldElement, eCFieldElement2, eCFieldElementArr);
    }

    /* JADX WARNING: type inference failed for: r17v0, types: [org.bouncycastle.math.ec.ECPoint] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.bouncycastle.math.p018ec.ECPoint add(org.bouncycastle.math.p018ec.ECPoint r17) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            boolean r2 = r16.isInfinity()
            if (r2 == 0) goto L_0x000b
            return r1
        L_0x000b:
            boolean r2 = r17.isInfinity()
            if (r2 == 0) goto L_0x0012
            return r0
        L_0x0012:
            if (r0 != r1) goto L_0x0019
            org.bouncycastle.math.ec.ECPoint r1 = r16.twice()
            return r1
        L_0x0019:
            org.bouncycastle.math.ec.ECCurve r2 = r16.getCurve()
            org.bouncycastle.math.ec.ECFieldElement r3 = r0.f729x
            org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement r3 = (org.bouncycastle.math.p018ec.custom.sec.SecP384R1FieldElement) r3
            org.bouncycastle.math.ec.ECFieldElement r4 = r0.f730y
            org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement r4 = (org.bouncycastle.math.p018ec.custom.sec.SecP384R1FieldElement) r4
            org.bouncycastle.math.ec.ECFieldElement r5 = r17.getXCoord()
            org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement r5 = (org.bouncycastle.math.p018ec.custom.sec.SecP384R1FieldElement) r5
            org.bouncycastle.math.ec.ECFieldElement r6 = r17.getYCoord()
            org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement r6 = (org.bouncycastle.math.p018ec.custom.sec.SecP384R1FieldElement) r6
            org.bouncycastle.math.ec.ECFieldElement[] r7 = r0.f731zs
            r8 = 0
            r7 = r7[r8]
            org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement r7 = (org.bouncycastle.math.p018ec.custom.sec.SecP384R1FieldElement) r7
            org.bouncycastle.math.ec.ECFieldElement r1 = r1.getZCoord(r8)
            org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement r1 = (org.bouncycastle.math.p018ec.custom.sec.SecP384R1FieldElement) r1
            r9 = 24
            int[] r10 = org.bouncycastle.math.raw.Nat.create(r9)
            int[] r9 = org.bouncycastle.math.raw.Nat.create(r9)
            r11 = 12
            int[] r12 = org.bouncycastle.math.raw.Nat.create(r11)
            int[] r13 = org.bouncycastle.math.raw.Nat.create(r11)
            boolean r14 = r7.isOne()
            if (r14 == 0) goto L_0x005d
            int[] r5 = r5.f799x
            int[] r6 = r6.f799x
            goto L_0x0073
        L_0x005d:
            int[] r15 = r7.f799x
            org.bouncycastle.math.p018ec.custom.sec.SecP384R1Field.square(r15, r12)
            int[] r5 = r5.f799x
            org.bouncycastle.math.p018ec.custom.sec.SecP384R1Field.multiply(r12, r5, r9)
            int[] r5 = r7.f799x
            org.bouncycastle.math.p018ec.custom.sec.SecP384R1Field.multiply(r12, r5, r12)
            int[] r5 = r6.f799x
            org.bouncycastle.math.p018ec.custom.sec.SecP384R1Field.multiply(r12, r5, r12)
            r5 = r9
            r6 = r12
        L_0x0073:
            boolean r15 = r1.isOne()
            if (r15 == 0) goto L_0x007e
            int[] r3 = r3.f799x
            int[] r4 = r4.f799x
            goto L_0x0094
        L_0x007e:
            int[] r8 = r1.f799x
            org.bouncycastle.math.p018ec.custom.sec.SecP384R1Field.square(r8, r13)
            int[] r3 = r3.f799x
            org.bouncycastle.math.p018ec.custom.sec.SecP384R1Field.multiply(r13, r3, r10)
            int[] r3 = r1.f799x
            org.bouncycastle.math.p018ec.custom.sec.SecP384R1Field.multiply(r13, r3, r13)
            int[] r3 = r4.f799x
            org.bouncycastle.math.p018ec.custom.sec.SecP384R1Field.multiply(r13, r3, r13)
            r3 = r10
            r4 = r13
        L_0x0094:
            int[] r8 = org.bouncycastle.math.raw.Nat.create(r11)
            org.bouncycastle.math.p018ec.custom.sec.SecP384R1Field.subtract(r3, r5, r8)
            int[] r5 = org.bouncycastle.math.raw.Nat.create(r11)
            org.bouncycastle.math.p018ec.custom.sec.SecP384R1Field.subtract(r4, r6, r5)
            boolean r6 = org.bouncycastle.math.raw.Nat.isZero(r11, r8)
            if (r6 == 0) goto L_0x00b8
            boolean r1 = org.bouncycastle.math.raw.Nat.isZero(r11, r5)
            if (r1 == 0) goto L_0x00b3
            org.bouncycastle.math.ec.ECPoint r1 = r16.twice()
            return r1
        L_0x00b3:
            org.bouncycastle.math.ec.ECPoint r1 = r2.getInfinity()
            return r1
        L_0x00b8:
            org.bouncycastle.math.p018ec.custom.sec.SecP384R1Field.square(r8, r12)
            int[] r6 = org.bouncycastle.math.raw.Nat.create(r11)
            org.bouncycastle.math.p018ec.custom.sec.SecP384R1Field.multiply(r12, r8, r6)
            org.bouncycastle.math.p018ec.custom.sec.SecP384R1Field.multiply(r12, r3, r12)
            org.bouncycastle.math.p018ec.custom.sec.SecP384R1Field.negate(r6, r6)
            org.bouncycastle.math.raw.Nat384.mul(r4, r6, r10)
            int r3 = org.bouncycastle.math.raw.Nat.addBothTo(r11, r12, r12, r6)
            org.bouncycastle.math.p018ec.custom.sec.SecP384R1Field.reduce32(r3, r6)
            org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement r3 = new org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement
            r3.<init>((int[]) r13)
            int[] r4 = r3.f799x
            org.bouncycastle.math.p018ec.custom.sec.SecP384R1Field.square(r5, r4)
            int[] r4 = r3.f799x
            int[] r11 = r3.f799x
            org.bouncycastle.math.p018ec.custom.sec.SecP384R1Field.subtract(r4, r6, r11)
            org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement r4 = new org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement
            r4.<init>((int[]) r6)
            int[] r6 = r3.f799x
            int[] r11 = r4.f799x
            org.bouncycastle.math.p018ec.custom.sec.SecP384R1Field.subtract(r12, r6, r11)
            int[] r6 = r4.f799x
            org.bouncycastle.math.raw.Nat384.mul(r6, r5, r9)
            org.bouncycastle.math.p018ec.custom.sec.SecP384R1Field.addExt(r10, r9, r10)
            int[] r5 = r4.f799x
            org.bouncycastle.math.p018ec.custom.sec.SecP384R1Field.reduce(r10, r5)
            org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement r5 = new org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement
            r5.<init>((int[]) r8)
            if (r14 != 0) goto L_0x010c
            int[] r6 = r5.f799x
            int[] r7 = r7.f799x
            int[] r8 = r5.f799x
            org.bouncycastle.math.p018ec.custom.sec.SecP384R1Field.multiply(r6, r7, r8)
        L_0x010c:
            if (r15 != 0) goto L_0x0117
            int[] r6 = r5.f799x
            int[] r1 = r1.f799x
            int[] r7 = r5.f799x
            org.bouncycastle.math.p018ec.custom.sec.SecP384R1Field.multiply(r6, r1, r7)
        L_0x0117:
            r1 = 1
            org.bouncycastle.math.ec.ECFieldElement[] r1 = new org.bouncycastle.math.p018ec.ECFieldElement[r1]
            r6 = 0
            r1[r6] = r5
            org.bouncycastle.math.ec.custom.sec.SecP384R1Point r5 = new org.bouncycastle.math.ec.custom.sec.SecP384R1Point
            r5.<init>(r2, r3, r4, r1)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.math.p018ec.custom.sec.SecP384R1Point.add(org.bouncycastle.math.ec.ECPoint):org.bouncycastle.math.ec.ECPoint");
    }

    /* access modifiers changed from: protected */
    public ECPoint detach() {
        return new SecP384R1Point((ECCurve) null, getAffineXCoord(), getAffineYCoord());
    }

    public ECPoint negate() {
        return isInfinity() ? this : new SecP384R1Point(this.curve, this.f729x, this.f730y.negate(), this.f731zs);
    }

    public ECPoint threeTimes() {
        return (isInfinity() || this.f730y.isZero()) ? this : twice().add(this);
    }

    public ECPoint twice() {
        if (isInfinity()) {
            return this;
        }
        ECCurve curve = getCurve();
        SecP384R1FieldElement secP384R1FieldElement = (SecP384R1FieldElement) this.f730y;
        if (secP384R1FieldElement.isZero()) {
            return curve.getInfinity();
        }
        SecP384R1FieldElement secP384R1FieldElement2 = (SecP384R1FieldElement) this.f729x;
        SecP384R1FieldElement secP384R1FieldElement3 = (SecP384R1FieldElement) this.f731zs[0];
        int[] create = Nat.create(12);
        int[] create2 = Nat.create(12);
        int[] create3 = Nat.create(12);
        SecP384R1Field.square(secP384R1FieldElement.f799x, create3);
        int[] create4 = Nat.create(12);
        SecP384R1Field.square(create3, create4);
        boolean isOne = secP384R1FieldElement3.isOne();
        int[] iArr = secP384R1FieldElement3.f799x;
        if (!isOne) {
            SecP384R1Field.square(secP384R1FieldElement3.f799x, create2);
            iArr = create2;
        }
        SecP384R1Field.subtract(secP384R1FieldElement2.f799x, iArr, create);
        SecP384R1Field.add(secP384R1FieldElement2.f799x, iArr, create2);
        SecP384R1Field.multiply(create2, create, create2);
        SecP384R1Field.reduce32(Nat.addBothTo(12, create2, create2, create2), create2);
        SecP384R1Field.multiply(create3, secP384R1FieldElement2.f799x, create3);
        SecP384R1Field.reduce32(Nat.shiftUpBits(12, create3, 2, 0), create3);
        SecP384R1Field.reduce32(Nat.shiftUpBits(12, create4, 3, 0, create), create);
        SecP384R1FieldElement secP384R1FieldElement4 = new SecP384R1FieldElement(create4);
        SecP384R1Field.square(create2, secP384R1FieldElement4.f799x);
        SecP384R1Field.subtract(secP384R1FieldElement4.f799x, create3, secP384R1FieldElement4.f799x);
        SecP384R1Field.subtract(secP384R1FieldElement4.f799x, create3, secP384R1FieldElement4.f799x);
        SecP384R1FieldElement secP384R1FieldElement5 = new SecP384R1FieldElement(create3);
        SecP384R1Field.subtract(create3, secP384R1FieldElement4.f799x, secP384R1FieldElement5.f799x);
        SecP384R1Field.multiply(secP384R1FieldElement5.f799x, create2, secP384R1FieldElement5.f799x);
        SecP384R1Field.subtract(secP384R1FieldElement5.f799x, create, secP384R1FieldElement5.f799x);
        SecP384R1FieldElement secP384R1FieldElement6 = new SecP384R1FieldElement(create2);
        SecP384R1Field.twice(secP384R1FieldElement.f799x, secP384R1FieldElement6.f799x);
        if (!isOne) {
            SecP384R1Field.multiply(secP384R1FieldElement6.f799x, secP384R1FieldElement3.f799x, secP384R1FieldElement6.f799x);
        }
        return new SecP384R1Point(curve, secP384R1FieldElement4, secP384R1FieldElement5, new ECFieldElement[]{secP384R1FieldElement6});
    }

    public ECPoint twicePlus(ECPoint eCPoint) {
        return this == eCPoint ? threeTimes() : isInfinity() ? eCPoint : eCPoint.isInfinity() ? twice() : this.f730y.isZero() ? eCPoint : twice().add(eCPoint);
    }
}
