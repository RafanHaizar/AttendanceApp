package org.bouncycastle.math.p018ec.custom.sec;

import java.math.BigInteger;
import org.bouncycastle.math.p018ec.ECFieldElement;
import org.bouncycastle.math.raw.Nat128;
import org.bouncycastle.util.Arrays;

/* renamed from: org.bouncycastle.math.ec.custom.sec.SecT113FieldElement */
public class SecT113FieldElement extends ECFieldElement.AbstractF2m {

    /* renamed from: x */
    protected long[] f804x;

    public SecT113FieldElement() {
        this.f804x = Nat128.create64();
    }

    public SecT113FieldElement(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.bitLength() > 113) {
            throw new IllegalArgumentException("x value invalid for SecT113FieldElement");
        }
        this.f804x = SecT113Field.fromBigInteger(bigInteger);
    }

    protected SecT113FieldElement(long[] jArr) {
        this.f804x = jArr;
    }

    public ECFieldElement add(ECFieldElement eCFieldElement) {
        long[] create64 = Nat128.create64();
        SecT113Field.add(this.f804x, ((SecT113FieldElement) eCFieldElement).f804x, create64);
        return new SecT113FieldElement(create64);
    }

    public ECFieldElement addOne() {
        long[] create64 = Nat128.create64();
        SecT113Field.addOne(this.f804x, create64);
        return new SecT113FieldElement(create64);
    }

    public ECFieldElement divide(ECFieldElement eCFieldElement) {
        return multiply(eCFieldElement.invert());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SecT113FieldElement)) {
            return false;
        }
        return Nat128.eq64(this.f804x, ((SecT113FieldElement) obj).f804x);
    }

    public String getFieldName() {
        return "SecT113Field";
    }

    public int getFieldSize() {
        return 113;
    }

    public int getK1() {
        return 9;
    }

    public int getK2() {
        return 0;
    }

    public int getK3() {
        return 0;
    }

    public int getM() {
        return 113;
    }

    public int getRepresentation() {
        return 2;
    }

    public ECFieldElement halfTrace() {
        long[] create64 = Nat128.create64();
        SecT113Field.halfTrace(this.f804x, create64);
        return new SecT113FieldElement(create64);
    }

    public boolean hasFastTrace() {
        return true;
    }

    public int hashCode() {
        return Arrays.hashCode(this.f804x, 0, 2) ^ 113009;
    }

    public ECFieldElement invert() {
        long[] create64 = Nat128.create64();
        SecT113Field.invert(this.f804x, create64);
        return new SecT113FieldElement(create64);
    }

    public boolean isOne() {
        return Nat128.isOne64(this.f804x);
    }

    public boolean isZero() {
        return Nat128.isZero64(this.f804x);
    }

    public ECFieldElement multiply(ECFieldElement eCFieldElement) {
        long[] create64 = Nat128.create64();
        SecT113Field.multiply(this.f804x, ((SecT113FieldElement) eCFieldElement).f804x, create64);
        return new SecT113FieldElement(create64);
    }

    public ECFieldElement multiplyMinusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3) {
        return multiplyPlusProduct(eCFieldElement, eCFieldElement2, eCFieldElement3);
    }

    public ECFieldElement multiplyPlusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3) {
        long[] jArr = this.f804x;
        long[] jArr2 = ((SecT113FieldElement) eCFieldElement).f804x;
        long[] jArr3 = ((SecT113FieldElement) eCFieldElement2).f804x;
        long[] jArr4 = ((SecT113FieldElement) eCFieldElement3).f804x;
        long[] createExt64 = Nat128.createExt64();
        SecT113Field.multiplyAddToExt(jArr, jArr2, createExt64);
        SecT113Field.multiplyAddToExt(jArr3, jArr4, createExt64);
        long[] create64 = Nat128.create64();
        SecT113Field.reduce(createExt64, create64);
        return new SecT113FieldElement(create64);
    }

    public ECFieldElement negate() {
        return this;
    }

    public ECFieldElement sqrt() {
        long[] create64 = Nat128.create64();
        SecT113Field.sqrt(this.f804x, create64);
        return new SecT113FieldElement(create64);
    }

    public ECFieldElement square() {
        long[] create64 = Nat128.create64();
        SecT113Field.square(this.f804x, create64);
        return new SecT113FieldElement(create64);
    }

    public ECFieldElement squareMinusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        return squarePlusProduct(eCFieldElement, eCFieldElement2);
    }

    public ECFieldElement squarePlusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        long[] jArr = this.f804x;
        long[] jArr2 = ((SecT113FieldElement) eCFieldElement).f804x;
        long[] jArr3 = ((SecT113FieldElement) eCFieldElement2).f804x;
        long[] createExt64 = Nat128.createExt64();
        SecT113Field.squareAddToExt(jArr, createExt64);
        SecT113Field.multiplyAddToExt(jArr2, jArr3, createExt64);
        long[] create64 = Nat128.create64();
        SecT113Field.reduce(createExt64, create64);
        return new SecT113FieldElement(create64);
    }

    public ECFieldElement squarePow(int i) {
        if (i < 1) {
            return this;
        }
        long[] create64 = Nat128.create64();
        SecT113Field.squareN(this.f804x, i, create64);
        return new SecT113FieldElement(create64);
    }

    public ECFieldElement subtract(ECFieldElement eCFieldElement) {
        return add(eCFieldElement);
    }

    public boolean testBitZero() {
        return (this.f804x[0] & 1) != 0;
    }

    public BigInteger toBigInteger() {
        return Nat128.toBigInteger64(this.f804x);
    }

    public int trace() {
        return SecT113Field.trace(this.f804x);
    }
}
