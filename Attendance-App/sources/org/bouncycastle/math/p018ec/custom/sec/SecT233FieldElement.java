package org.bouncycastle.math.p018ec.custom.sec;

import java.math.BigInteger;
import org.bouncycastle.math.p018ec.ECFieldElement;
import org.bouncycastle.math.raw.Nat256;
import org.bouncycastle.util.Arrays;

/* renamed from: org.bouncycastle.math.ec.custom.sec.SecT233FieldElement */
public class SecT233FieldElement extends ECFieldElement.AbstractF2m {

    /* renamed from: x */
    protected long[] f808x;

    public SecT233FieldElement() {
        this.f808x = Nat256.create64();
    }

    public SecT233FieldElement(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.bitLength() > 233) {
            throw new IllegalArgumentException("x value invalid for SecT233FieldElement");
        }
        this.f808x = SecT233Field.fromBigInteger(bigInteger);
    }

    protected SecT233FieldElement(long[] jArr) {
        this.f808x = jArr;
    }

    public ECFieldElement add(ECFieldElement eCFieldElement) {
        long[] create64 = Nat256.create64();
        SecT233Field.add(this.f808x, ((SecT233FieldElement) eCFieldElement).f808x, create64);
        return new SecT233FieldElement(create64);
    }

    public ECFieldElement addOne() {
        long[] create64 = Nat256.create64();
        SecT233Field.addOne(this.f808x, create64);
        return new SecT233FieldElement(create64);
    }

    public ECFieldElement divide(ECFieldElement eCFieldElement) {
        return multiply(eCFieldElement.invert());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SecT233FieldElement)) {
            return false;
        }
        return Nat256.eq64(this.f808x, ((SecT233FieldElement) obj).f808x);
    }

    public String getFieldName() {
        return "SecT233Field";
    }

    public int getFieldSize() {
        return 233;
    }

    public int getK1() {
        return 74;
    }

    public int getK2() {
        return 0;
    }

    public int getK3() {
        return 0;
    }

    public int getM() {
        return 233;
    }

    public int getRepresentation() {
        return 2;
    }

    public ECFieldElement halfTrace() {
        long[] create64 = Nat256.create64();
        SecT233Field.halfTrace(this.f808x, create64);
        return new SecT233FieldElement(create64);
    }

    public boolean hasFastTrace() {
        return true;
    }

    public int hashCode() {
        return Arrays.hashCode(this.f808x, 0, 4) ^ 2330074;
    }

    public ECFieldElement invert() {
        long[] create64 = Nat256.create64();
        SecT233Field.invert(this.f808x, create64);
        return new SecT233FieldElement(create64);
    }

    public boolean isOne() {
        return Nat256.isOne64(this.f808x);
    }

    public boolean isZero() {
        return Nat256.isZero64(this.f808x);
    }

    public ECFieldElement multiply(ECFieldElement eCFieldElement) {
        long[] create64 = Nat256.create64();
        SecT233Field.multiply(this.f808x, ((SecT233FieldElement) eCFieldElement).f808x, create64);
        return new SecT233FieldElement(create64);
    }

    public ECFieldElement multiplyMinusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3) {
        return multiplyPlusProduct(eCFieldElement, eCFieldElement2, eCFieldElement3);
    }

    public ECFieldElement multiplyPlusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3) {
        long[] jArr = this.f808x;
        long[] jArr2 = ((SecT233FieldElement) eCFieldElement).f808x;
        long[] jArr3 = ((SecT233FieldElement) eCFieldElement2).f808x;
        long[] jArr4 = ((SecT233FieldElement) eCFieldElement3).f808x;
        long[] createExt64 = Nat256.createExt64();
        SecT233Field.multiplyAddToExt(jArr, jArr2, createExt64);
        SecT233Field.multiplyAddToExt(jArr3, jArr4, createExt64);
        long[] create64 = Nat256.create64();
        SecT233Field.reduce(createExt64, create64);
        return new SecT233FieldElement(create64);
    }

    public ECFieldElement negate() {
        return this;
    }

    public ECFieldElement sqrt() {
        long[] create64 = Nat256.create64();
        SecT233Field.sqrt(this.f808x, create64);
        return new SecT233FieldElement(create64);
    }

    public ECFieldElement square() {
        long[] create64 = Nat256.create64();
        SecT233Field.square(this.f808x, create64);
        return new SecT233FieldElement(create64);
    }

    public ECFieldElement squareMinusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        return squarePlusProduct(eCFieldElement, eCFieldElement2);
    }

    public ECFieldElement squarePlusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        long[] jArr = this.f808x;
        long[] jArr2 = ((SecT233FieldElement) eCFieldElement).f808x;
        long[] jArr3 = ((SecT233FieldElement) eCFieldElement2).f808x;
        long[] createExt64 = Nat256.createExt64();
        SecT233Field.squareAddToExt(jArr, createExt64);
        SecT233Field.multiplyAddToExt(jArr2, jArr3, createExt64);
        long[] create64 = Nat256.create64();
        SecT233Field.reduce(createExt64, create64);
        return new SecT233FieldElement(create64);
    }

    public ECFieldElement squarePow(int i) {
        if (i < 1) {
            return this;
        }
        long[] create64 = Nat256.create64();
        SecT233Field.squareN(this.f808x, i, create64);
        return new SecT233FieldElement(create64);
    }

    public ECFieldElement subtract(ECFieldElement eCFieldElement) {
        return add(eCFieldElement);
    }

    public boolean testBitZero() {
        return (this.f808x[0] & 1) != 0;
    }

    public BigInteger toBigInteger() {
        return Nat256.toBigInteger64(this.f808x);
    }

    public int trace() {
        return SecT233Field.trace(this.f808x);
    }
}
