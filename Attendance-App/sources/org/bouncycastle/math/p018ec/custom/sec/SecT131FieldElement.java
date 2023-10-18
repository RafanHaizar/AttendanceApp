package org.bouncycastle.math.p018ec.custom.sec;

import java.math.BigInteger;
import org.bouncycastle.math.p018ec.ECFieldElement;
import org.bouncycastle.math.raw.Nat;
import org.bouncycastle.math.raw.Nat192;
import org.bouncycastle.util.Arrays;

/* renamed from: org.bouncycastle.math.ec.custom.sec.SecT131FieldElement */
public class SecT131FieldElement extends ECFieldElement.AbstractF2m {

    /* renamed from: x */
    protected long[] f805x;

    public SecT131FieldElement() {
        this.f805x = Nat192.create64();
    }

    public SecT131FieldElement(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.bitLength() > 131) {
            throw new IllegalArgumentException("x value invalid for SecT131FieldElement");
        }
        this.f805x = SecT131Field.fromBigInteger(bigInteger);
    }

    protected SecT131FieldElement(long[] jArr) {
        this.f805x = jArr;
    }

    public ECFieldElement add(ECFieldElement eCFieldElement) {
        long[] create64 = Nat192.create64();
        SecT131Field.add(this.f805x, ((SecT131FieldElement) eCFieldElement).f805x, create64);
        return new SecT131FieldElement(create64);
    }

    public ECFieldElement addOne() {
        long[] create64 = Nat192.create64();
        SecT131Field.addOne(this.f805x, create64);
        return new SecT131FieldElement(create64);
    }

    public ECFieldElement divide(ECFieldElement eCFieldElement) {
        return multiply(eCFieldElement.invert());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SecT131FieldElement)) {
            return false;
        }
        return Nat192.eq64(this.f805x, ((SecT131FieldElement) obj).f805x);
    }

    public String getFieldName() {
        return "SecT131Field";
    }

    public int getFieldSize() {
        return 131;
    }

    public int getK1() {
        return 2;
    }

    public int getK2() {
        return 3;
    }

    public int getK3() {
        return 8;
    }

    public int getM() {
        return 131;
    }

    public int getRepresentation() {
        return 3;
    }

    public ECFieldElement halfTrace() {
        long[] create64 = Nat192.create64();
        SecT131Field.halfTrace(this.f805x, create64);
        return new SecT131FieldElement(create64);
    }

    public boolean hasFastTrace() {
        return true;
    }

    public int hashCode() {
        return Arrays.hashCode(this.f805x, 0, 3) ^ 131832;
    }

    public ECFieldElement invert() {
        long[] create64 = Nat192.create64();
        SecT131Field.invert(this.f805x, create64);
        return new SecT131FieldElement(create64);
    }

    public boolean isOne() {
        return Nat192.isOne64(this.f805x);
    }

    public boolean isZero() {
        return Nat192.isZero64(this.f805x);
    }

    public ECFieldElement multiply(ECFieldElement eCFieldElement) {
        long[] create64 = Nat192.create64();
        SecT131Field.multiply(this.f805x, ((SecT131FieldElement) eCFieldElement).f805x, create64);
        return new SecT131FieldElement(create64);
    }

    public ECFieldElement multiplyMinusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3) {
        return multiplyPlusProduct(eCFieldElement, eCFieldElement2, eCFieldElement3);
    }

    public ECFieldElement multiplyPlusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3) {
        long[] jArr = this.f805x;
        long[] jArr2 = ((SecT131FieldElement) eCFieldElement).f805x;
        long[] jArr3 = ((SecT131FieldElement) eCFieldElement2).f805x;
        long[] jArr4 = ((SecT131FieldElement) eCFieldElement3).f805x;
        long[] create64 = Nat.create64(5);
        SecT131Field.multiplyAddToExt(jArr, jArr2, create64);
        SecT131Field.multiplyAddToExt(jArr3, jArr4, create64);
        long[] create642 = Nat192.create64();
        SecT131Field.reduce(create64, create642);
        return new SecT131FieldElement(create642);
    }

    public ECFieldElement negate() {
        return this;
    }

    public ECFieldElement sqrt() {
        long[] create64 = Nat192.create64();
        SecT131Field.sqrt(this.f805x, create64);
        return new SecT131FieldElement(create64);
    }

    public ECFieldElement square() {
        long[] create64 = Nat192.create64();
        SecT131Field.square(this.f805x, create64);
        return new SecT131FieldElement(create64);
    }

    public ECFieldElement squareMinusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        return squarePlusProduct(eCFieldElement, eCFieldElement2);
    }

    public ECFieldElement squarePlusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        long[] jArr = this.f805x;
        long[] jArr2 = ((SecT131FieldElement) eCFieldElement).f805x;
        long[] jArr3 = ((SecT131FieldElement) eCFieldElement2).f805x;
        long[] create64 = Nat.create64(5);
        SecT131Field.squareAddToExt(jArr, create64);
        SecT131Field.multiplyAddToExt(jArr2, jArr3, create64);
        long[] create642 = Nat192.create64();
        SecT131Field.reduce(create64, create642);
        return new SecT131FieldElement(create642);
    }

    public ECFieldElement squarePow(int i) {
        if (i < 1) {
            return this;
        }
        long[] create64 = Nat192.create64();
        SecT131Field.squareN(this.f805x, i, create64);
        return new SecT131FieldElement(create64);
    }

    public ECFieldElement subtract(ECFieldElement eCFieldElement) {
        return add(eCFieldElement);
    }

    public boolean testBitZero() {
        return (this.f805x[0] & 1) != 0;
    }

    public BigInteger toBigInteger() {
        return Nat192.toBigInteger64(this.f805x);
    }

    public int trace() {
        return SecT131Field.trace(this.f805x);
    }
}
