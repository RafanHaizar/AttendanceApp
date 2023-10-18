package org.bouncycastle.math.p018ec.custom.djb;

import java.math.BigInteger;
import org.bouncycastle.math.p018ec.ECFieldElement;
import org.bouncycastle.math.raw.Mod;
import org.bouncycastle.math.raw.Nat256;
import org.bouncycastle.util.Arrays;

/* renamed from: org.bouncycastle.math.ec.custom.djb.Curve25519FieldElement */
public class Curve25519FieldElement extends ECFieldElement.AbstractFp {
    private static final int[] PRECOMP_POW2 = {1242472624, -991028441, -1389370248, 792926214, 1039914919, 726466713, 1338105611, 730014848};

    /* renamed from: Q */
    public static final BigInteger f738Q = Nat256.toBigInteger(Curve25519Field.f736P);

    /* renamed from: x */
    protected int[] f739x;

    public Curve25519FieldElement() {
        this.f739x = Nat256.create();
    }

    public Curve25519FieldElement(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(f738Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for Curve25519FieldElement");
        }
        this.f739x = Curve25519Field.fromBigInteger(bigInteger);
    }

    protected Curve25519FieldElement(int[] iArr) {
        this.f739x = iArr;
    }

    public ECFieldElement add(ECFieldElement eCFieldElement) {
        int[] create = Nat256.create();
        Curve25519Field.add(this.f739x, ((Curve25519FieldElement) eCFieldElement).f739x, create);
        return new Curve25519FieldElement(create);
    }

    public ECFieldElement addOne() {
        int[] create = Nat256.create();
        Curve25519Field.addOne(this.f739x, create);
        return new Curve25519FieldElement(create);
    }

    public ECFieldElement divide(ECFieldElement eCFieldElement) {
        int[] create = Nat256.create();
        Mod.invert(Curve25519Field.f736P, ((Curve25519FieldElement) eCFieldElement).f739x, create);
        Curve25519Field.multiply(create, this.f739x, create);
        return new Curve25519FieldElement(create);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Curve25519FieldElement)) {
            return false;
        }
        return Nat256.m135eq(this.f739x, ((Curve25519FieldElement) obj).f739x);
    }

    public String getFieldName() {
        return "Curve25519Field";
    }

    public int getFieldSize() {
        return f738Q.bitLength();
    }

    public int hashCode() {
        return f738Q.hashCode() ^ Arrays.hashCode(this.f739x, 0, 8);
    }

    public ECFieldElement invert() {
        int[] create = Nat256.create();
        Mod.invert(Curve25519Field.f736P, this.f739x, create);
        return new Curve25519FieldElement(create);
    }

    public boolean isOne() {
        return Nat256.isOne(this.f739x);
    }

    public boolean isZero() {
        return Nat256.isZero(this.f739x);
    }

    public ECFieldElement multiply(ECFieldElement eCFieldElement) {
        int[] create = Nat256.create();
        Curve25519Field.multiply(this.f739x, ((Curve25519FieldElement) eCFieldElement).f739x, create);
        return new Curve25519FieldElement(create);
    }

    public ECFieldElement negate() {
        int[] create = Nat256.create();
        Curve25519Field.negate(this.f739x, create);
        return new Curve25519FieldElement(create);
    }

    public ECFieldElement sqrt() {
        int[] iArr = this.f739x;
        if (Nat256.isZero(iArr) || Nat256.isOne(iArr)) {
            return this;
        }
        int[] create = Nat256.create();
        Curve25519Field.square(iArr, create);
        Curve25519Field.multiply(create, iArr, create);
        Curve25519Field.square(create, create);
        Curve25519Field.multiply(create, iArr, create);
        int[] create2 = Nat256.create();
        Curve25519Field.square(create, create2);
        Curve25519Field.multiply(create2, iArr, create2);
        int[] create3 = Nat256.create();
        Curve25519Field.squareN(create2, 3, create3);
        Curve25519Field.multiply(create3, create, create3);
        Curve25519Field.squareN(create3, 4, create);
        Curve25519Field.multiply(create, create2, create);
        Curve25519Field.squareN(create, 4, create3);
        Curve25519Field.multiply(create3, create2, create3);
        Curve25519Field.squareN(create3, 15, create2);
        Curve25519Field.multiply(create2, create3, create2);
        Curve25519Field.squareN(create2, 30, create3);
        Curve25519Field.multiply(create3, create2, create3);
        Curve25519Field.squareN(create3, 60, create2);
        Curve25519Field.multiply(create2, create3, create2);
        Curve25519Field.squareN(create2, 11, create3);
        Curve25519Field.multiply(create3, create, create3);
        Curve25519Field.squareN(create3, 120, create);
        Curve25519Field.multiply(create, create2, create);
        Curve25519Field.square(create, create);
        Curve25519Field.square(create, create2);
        if (Nat256.m135eq(iArr, create2)) {
            return new Curve25519FieldElement(create);
        }
        Curve25519Field.multiply(create, PRECOMP_POW2, create);
        Curve25519Field.square(create, create2);
        if (Nat256.m135eq(iArr, create2)) {
            return new Curve25519FieldElement(create);
        }
        return null;
    }

    public ECFieldElement square() {
        int[] create = Nat256.create();
        Curve25519Field.square(this.f739x, create);
        return new Curve25519FieldElement(create);
    }

    public ECFieldElement subtract(ECFieldElement eCFieldElement) {
        int[] create = Nat256.create();
        Curve25519Field.subtract(this.f739x, ((Curve25519FieldElement) eCFieldElement).f739x, create);
        return new Curve25519FieldElement(create);
    }

    public boolean testBitZero() {
        return Nat256.getBit(this.f739x, 0) == 1;
    }

    public BigInteger toBigInteger() {
        return Nat256.toBigInteger(this.f739x);
    }
}
