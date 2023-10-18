package org.bouncycastle.math.p018ec.custom.sec;

import java.math.BigInteger;
import org.bouncycastle.math.p018ec.ECFieldElement;
import org.bouncycastle.math.raw.Mod;
import org.bouncycastle.math.raw.Nat160;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;

/* renamed from: org.bouncycastle.math.ec.custom.sec.SecP160R1FieldElement */
public class SecP160R1FieldElement extends ECFieldElement.AbstractFp {

    /* renamed from: Q */
    public static final BigInteger f755Q = new BigInteger(1, Hex.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF7FFFFFFF"));

    /* renamed from: x */
    protected int[] f756x;

    public SecP160R1FieldElement() {
        this.f756x = Nat160.create();
    }

    public SecP160R1FieldElement(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(f755Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP160R1FieldElement");
        }
        this.f756x = SecP160R1Field.fromBigInteger(bigInteger);
    }

    protected SecP160R1FieldElement(int[] iArr) {
        this.f756x = iArr;
    }

    public ECFieldElement add(ECFieldElement eCFieldElement) {
        int[] create = Nat160.create();
        SecP160R1Field.add(this.f756x, ((SecP160R1FieldElement) eCFieldElement).f756x, create);
        return new SecP160R1FieldElement(create);
    }

    public ECFieldElement addOne() {
        int[] create = Nat160.create();
        SecP160R1Field.addOne(this.f756x, create);
        return new SecP160R1FieldElement(create);
    }

    public ECFieldElement divide(ECFieldElement eCFieldElement) {
        int[] create = Nat160.create();
        Mod.invert(SecP160R1Field.f753P, ((SecP160R1FieldElement) eCFieldElement).f756x, create);
        SecP160R1Field.multiply(create, this.f756x, create);
        return new SecP160R1FieldElement(create);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SecP160R1FieldElement)) {
            return false;
        }
        return Nat160.m132eq(this.f756x, ((SecP160R1FieldElement) obj).f756x);
    }

    public String getFieldName() {
        return "SecP160R1Field";
    }

    public int getFieldSize() {
        return f755Q.bitLength();
    }

    public int hashCode() {
        return f755Q.hashCode() ^ Arrays.hashCode(this.f756x, 0, 5);
    }

    public ECFieldElement invert() {
        int[] create = Nat160.create();
        Mod.invert(SecP160R1Field.f753P, this.f756x, create);
        return new SecP160R1FieldElement(create);
    }

    public boolean isOne() {
        return Nat160.isOne(this.f756x);
    }

    public boolean isZero() {
        return Nat160.isZero(this.f756x);
    }

    public ECFieldElement multiply(ECFieldElement eCFieldElement) {
        int[] create = Nat160.create();
        SecP160R1Field.multiply(this.f756x, ((SecP160R1FieldElement) eCFieldElement).f756x, create);
        return new SecP160R1FieldElement(create);
    }

    public ECFieldElement negate() {
        int[] create = Nat160.create();
        SecP160R1Field.negate(this.f756x, create);
        return new SecP160R1FieldElement(create);
    }

    public ECFieldElement sqrt() {
        int[] iArr = this.f756x;
        if (Nat160.isZero(iArr) || Nat160.isOne(iArr)) {
            return this;
        }
        int[] create = Nat160.create();
        SecP160R1Field.square(iArr, create);
        SecP160R1Field.multiply(create, iArr, create);
        int[] create2 = Nat160.create();
        SecP160R1Field.squareN(create, 2, create2);
        SecP160R1Field.multiply(create2, create, create2);
        SecP160R1Field.squareN(create2, 4, create);
        SecP160R1Field.multiply(create, create2, create);
        SecP160R1Field.squareN(create, 8, create2);
        SecP160R1Field.multiply(create2, create, create2);
        SecP160R1Field.squareN(create2, 16, create);
        SecP160R1Field.multiply(create, create2, create);
        SecP160R1Field.squareN(create, 32, create2);
        SecP160R1Field.multiply(create2, create, create2);
        SecP160R1Field.squareN(create2, 64, create);
        SecP160R1Field.multiply(create, create2, create);
        SecP160R1Field.square(create, create2);
        SecP160R1Field.multiply(create2, iArr, create2);
        SecP160R1Field.squareN(create2, 29, create2);
        SecP160R1Field.square(create2, create);
        if (Nat160.m132eq(iArr, create)) {
            return new SecP160R1FieldElement(create2);
        }
        return null;
    }

    public ECFieldElement square() {
        int[] create = Nat160.create();
        SecP160R1Field.square(this.f756x, create);
        return new SecP160R1FieldElement(create);
    }

    public ECFieldElement subtract(ECFieldElement eCFieldElement) {
        int[] create = Nat160.create();
        SecP160R1Field.subtract(this.f756x, ((SecP160R1FieldElement) eCFieldElement).f756x, create);
        return new SecP160R1FieldElement(create);
    }

    public boolean testBitZero() {
        return Nat160.getBit(this.f756x, 0) == 1;
    }

    public BigInteger toBigInteger() {
        return Nat160.toBigInteger(this.f756x);
    }
}
