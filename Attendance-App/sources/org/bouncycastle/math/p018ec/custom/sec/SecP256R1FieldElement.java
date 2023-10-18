package org.bouncycastle.math.p018ec.custom.sec;

import java.math.BigInteger;
import org.bouncycastle.math.p018ec.ECFieldElement;
import org.bouncycastle.math.raw.Mod;
import org.bouncycastle.math.raw.Nat256;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;

/* renamed from: org.bouncycastle.math.ec.custom.sec.SecP256R1FieldElement */
public class SecP256R1FieldElement extends ECFieldElement.AbstractFp {

    /* renamed from: Q */
    public static final BigInteger f793Q = new BigInteger(1, Hex.decodeStrict("FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFF"));

    /* renamed from: x */
    protected int[] f794x;

    public SecP256R1FieldElement() {
        this.f794x = Nat256.create();
    }

    public SecP256R1FieldElement(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(f793Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP256R1FieldElement");
        }
        this.f794x = SecP256R1Field.fromBigInteger(bigInteger);
    }

    protected SecP256R1FieldElement(int[] iArr) {
        this.f794x = iArr;
    }

    public ECFieldElement add(ECFieldElement eCFieldElement) {
        int[] create = Nat256.create();
        SecP256R1Field.add(this.f794x, ((SecP256R1FieldElement) eCFieldElement).f794x, create);
        return new SecP256R1FieldElement(create);
    }

    public ECFieldElement addOne() {
        int[] create = Nat256.create();
        SecP256R1Field.addOne(this.f794x, create);
        return new SecP256R1FieldElement(create);
    }

    public ECFieldElement divide(ECFieldElement eCFieldElement) {
        int[] create = Nat256.create();
        Mod.invert(SecP256R1Field.f791P, ((SecP256R1FieldElement) eCFieldElement).f794x, create);
        SecP256R1Field.multiply(create, this.f794x, create);
        return new SecP256R1FieldElement(create);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SecP256R1FieldElement)) {
            return false;
        }
        return Nat256.m135eq(this.f794x, ((SecP256R1FieldElement) obj).f794x);
    }

    public String getFieldName() {
        return "SecP256R1Field";
    }

    public int getFieldSize() {
        return f793Q.bitLength();
    }

    public int hashCode() {
        return f793Q.hashCode() ^ Arrays.hashCode(this.f794x, 0, 8);
    }

    public ECFieldElement invert() {
        int[] create = Nat256.create();
        Mod.invert(SecP256R1Field.f791P, this.f794x, create);
        return new SecP256R1FieldElement(create);
    }

    public boolean isOne() {
        return Nat256.isOne(this.f794x);
    }

    public boolean isZero() {
        return Nat256.isZero(this.f794x);
    }

    public ECFieldElement multiply(ECFieldElement eCFieldElement) {
        int[] create = Nat256.create();
        SecP256R1Field.multiply(this.f794x, ((SecP256R1FieldElement) eCFieldElement).f794x, create);
        return new SecP256R1FieldElement(create);
    }

    public ECFieldElement negate() {
        int[] create = Nat256.create();
        SecP256R1Field.negate(this.f794x, create);
        return new SecP256R1FieldElement(create);
    }

    public ECFieldElement sqrt() {
        int[] iArr = this.f794x;
        if (Nat256.isZero(iArr) || Nat256.isOne(iArr)) {
            return this;
        }
        int[] create = Nat256.create();
        int[] create2 = Nat256.create();
        SecP256R1Field.square(iArr, create);
        SecP256R1Field.multiply(create, iArr, create);
        SecP256R1Field.squareN(create, 2, create2);
        SecP256R1Field.multiply(create2, create, create2);
        SecP256R1Field.squareN(create2, 4, create);
        SecP256R1Field.multiply(create, create2, create);
        SecP256R1Field.squareN(create, 8, create2);
        SecP256R1Field.multiply(create2, create, create2);
        SecP256R1Field.squareN(create2, 16, create);
        SecP256R1Field.multiply(create, create2, create);
        SecP256R1Field.squareN(create, 32, create);
        SecP256R1Field.multiply(create, iArr, create);
        SecP256R1Field.squareN(create, 96, create);
        SecP256R1Field.multiply(create, iArr, create);
        SecP256R1Field.squareN(create, 94, create);
        SecP256R1Field.square(create, create2);
        if (Nat256.m135eq(iArr, create2)) {
            return new SecP256R1FieldElement(create);
        }
        return null;
    }

    public ECFieldElement square() {
        int[] create = Nat256.create();
        SecP256R1Field.square(this.f794x, create);
        return new SecP256R1FieldElement(create);
    }

    public ECFieldElement subtract(ECFieldElement eCFieldElement) {
        int[] create = Nat256.create();
        SecP256R1Field.subtract(this.f794x, ((SecP256R1FieldElement) eCFieldElement).f794x, create);
        return new SecP256R1FieldElement(create);
    }

    public boolean testBitZero() {
        return Nat256.getBit(this.f794x, 0) == 1;
    }

    public BigInteger toBigInteger() {
        return Nat256.toBigInteger(this.f794x);
    }
}
