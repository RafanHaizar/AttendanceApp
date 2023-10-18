package org.bouncycastle.math.p018ec.custom.sec;

import java.math.BigInteger;
import org.bouncycastle.math.p018ec.ECFieldElement;
import org.bouncycastle.math.raw.Mod;
import org.bouncycastle.math.raw.Nat192;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;

/* renamed from: org.bouncycastle.math.ec.custom.sec.SecP192R1FieldElement */
public class SecP192R1FieldElement extends ECFieldElement.AbstractFp {

    /* renamed from: Q */
    public static final BigInteger f771Q = new BigInteger(1, Hex.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFF"));

    /* renamed from: x */
    protected int[] f772x;

    public SecP192R1FieldElement() {
        this.f772x = Nat192.create();
    }

    public SecP192R1FieldElement(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(f771Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP192R1FieldElement");
        }
        this.f772x = SecP192R1Field.fromBigInteger(bigInteger);
    }

    protected SecP192R1FieldElement(int[] iArr) {
        this.f772x = iArr;
    }

    public ECFieldElement add(ECFieldElement eCFieldElement) {
        int[] create = Nat192.create();
        SecP192R1Field.add(this.f772x, ((SecP192R1FieldElement) eCFieldElement).f772x, create);
        return new SecP192R1FieldElement(create);
    }

    public ECFieldElement addOne() {
        int[] create = Nat192.create();
        SecP192R1Field.addOne(this.f772x, create);
        return new SecP192R1FieldElement(create);
    }

    public ECFieldElement divide(ECFieldElement eCFieldElement) {
        int[] create = Nat192.create();
        Mod.invert(SecP192R1Field.f769P, ((SecP192R1FieldElement) eCFieldElement).f772x, create);
        SecP192R1Field.multiply(create, this.f772x, create);
        return new SecP192R1FieldElement(create);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SecP192R1FieldElement)) {
            return false;
        }
        return Nat192.m133eq(this.f772x, ((SecP192R1FieldElement) obj).f772x);
    }

    public String getFieldName() {
        return "SecP192R1Field";
    }

    public int getFieldSize() {
        return f771Q.bitLength();
    }

    public int hashCode() {
        return f771Q.hashCode() ^ Arrays.hashCode(this.f772x, 0, 6);
    }

    public ECFieldElement invert() {
        int[] create = Nat192.create();
        Mod.invert(SecP192R1Field.f769P, this.f772x, create);
        return new SecP192R1FieldElement(create);
    }

    public boolean isOne() {
        return Nat192.isOne(this.f772x);
    }

    public boolean isZero() {
        return Nat192.isZero(this.f772x);
    }

    public ECFieldElement multiply(ECFieldElement eCFieldElement) {
        int[] create = Nat192.create();
        SecP192R1Field.multiply(this.f772x, ((SecP192R1FieldElement) eCFieldElement).f772x, create);
        return new SecP192R1FieldElement(create);
    }

    public ECFieldElement negate() {
        int[] create = Nat192.create();
        SecP192R1Field.negate(this.f772x, create);
        return new SecP192R1FieldElement(create);
    }

    public ECFieldElement sqrt() {
        int[] iArr = this.f772x;
        if (Nat192.isZero(iArr) || Nat192.isOne(iArr)) {
            return this;
        }
        int[] create = Nat192.create();
        int[] create2 = Nat192.create();
        SecP192R1Field.square(iArr, create);
        SecP192R1Field.multiply(create, iArr, create);
        SecP192R1Field.squareN(create, 2, create2);
        SecP192R1Field.multiply(create2, create, create2);
        SecP192R1Field.squareN(create2, 4, create);
        SecP192R1Field.multiply(create, create2, create);
        SecP192R1Field.squareN(create, 8, create2);
        SecP192R1Field.multiply(create2, create, create2);
        SecP192R1Field.squareN(create2, 16, create);
        SecP192R1Field.multiply(create, create2, create);
        SecP192R1Field.squareN(create, 32, create2);
        SecP192R1Field.multiply(create2, create, create2);
        SecP192R1Field.squareN(create2, 64, create);
        SecP192R1Field.multiply(create, create2, create);
        SecP192R1Field.squareN(create, 62, create);
        SecP192R1Field.square(create, create2);
        if (Nat192.m133eq(iArr, create2)) {
            return new SecP192R1FieldElement(create);
        }
        return null;
    }

    public ECFieldElement square() {
        int[] create = Nat192.create();
        SecP192R1Field.square(this.f772x, create);
        return new SecP192R1FieldElement(create);
    }

    public ECFieldElement subtract(ECFieldElement eCFieldElement) {
        int[] create = Nat192.create();
        SecP192R1Field.subtract(this.f772x, ((SecP192R1FieldElement) eCFieldElement).f772x, create);
        return new SecP192R1FieldElement(create);
    }

    public boolean testBitZero() {
        return Nat192.getBit(this.f772x, 0) == 1;
    }

    public BigInteger toBigInteger() {
        return Nat192.toBigInteger(this.f772x);
    }
}
