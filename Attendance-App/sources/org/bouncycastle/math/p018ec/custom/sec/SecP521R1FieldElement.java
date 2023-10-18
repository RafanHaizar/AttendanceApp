package org.bouncycastle.math.p018ec.custom.sec;

import com.itextpdf.p026io.codec.TIFFConstants;
import java.math.BigInteger;
import org.bouncycastle.math.p018ec.ECFieldElement;
import org.bouncycastle.math.raw.Mod;
import org.bouncycastle.math.raw.Nat;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;

/* renamed from: org.bouncycastle.math.ec.custom.sec.SecP521R1FieldElement */
public class SecP521R1FieldElement extends ECFieldElement.AbstractFp {

    /* renamed from: Q */
    public static final BigInteger f802Q = new BigInteger(1, Hex.decodeStrict("01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF"));

    /* renamed from: x */
    protected int[] f803x;

    public SecP521R1FieldElement() {
        this.f803x = Nat.create(17);
    }

    public SecP521R1FieldElement(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(f802Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP521R1FieldElement");
        }
        this.f803x = SecP521R1Field.fromBigInteger(bigInteger);
    }

    protected SecP521R1FieldElement(int[] iArr) {
        this.f803x = iArr;
    }

    public ECFieldElement add(ECFieldElement eCFieldElement) {
        int[] create = Nat.create(17);
        SecP521R1Field.add(this.f803x, ((SecP521R1FieldElement) eCFieldElement).f803x, create);
        return new SecP521R1FieldElement(create);
    }

    public ECFieldElement addOne() {
        int[] create = Nat.create(17);
        SecP521R1Field.addOne(this.f803x, create);
        return new SecP521R1FieldElement(create);
    }

    public ECFieldElement divide(ECFieldElement eCFieldElement) {
        int[] create = Nat.create(17);
        Mod.invert(SecP521R1Field.f801P, ((SecP521R1FieldElement) eCFieldElement).f803x, create);
        SecP521R1Field.multiply(create, this.f803x, create);
        return new SecP521R1FieldElement(create);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SecP521R1FieldElement)) {
            return false;
        }
        return Nat.m130eq(17, this.f803x, ((SecP521R1FieldElement) obj).f803x);
    }

    public String getFieldName() {
        return "SecP521R1Field";
    }

    public int getFieldSize() {
        return f802Q.bitLength();
    }

    public int hashCode() {
        return f802Q.hashCode() ^ Arrays.hashCode(this.f803x, 0, 17);
    }

    public ECFieldElement invert() {
        int[] create = Nat.create(17);
        Mod.invert(SecP521R1Field.f801P, this.f803x, create);
        return new SecP521R1FieldElement(create);
    }

    public boolean isOne() {
        return Nat.isOne(17, this.f803x);
    }

    public boolean isZero() {
        return Nat.isZero(17, this.f803x);
    }

    public ECFieldElement multiply(ECFieldElement eCFieldElement) {
        int[] create = Nat.create(17);
        SecP521R1Field.multiply(this.f803x, ((SecP521R1FieldElement) eCFieldElement).f803x, create);
        return new SecP521R1FieldElement(create);
    }

    public ECFieldElement negate() {
        int[] create = Nat.create(17);
        SecP521R1Field.negate(this.f803x, create);
        return new SecP521R1FieldElement(create);
    }

    public ECFieldElement sqrt() {
        int[] iArr = this.f803x;
        if (Nat.isZero(17, iArr) || Nat.isOne(17, iArr)) {
            return this;
        }
        int[] create = Nat.create(17);
        int[] create2 = Nat.create(17);
        SecP521R1Field.squareN(iArr, TIFFConstants.TIFFTAG_JPEGQTABLES, create);
        SecP521R1Field.square(create, create2);
        if (Nat.m130eq(17, iArr, create2)) {
            return new SecP521R1FieldElement(create);
        }
        return null;
    }

    public ECFieldElement square() {
        int[] create = Nat.create(17);
        SecP521R1Field.square(this.f803x, create);
        return new SecP521R1FieldElement(create);
    }

    public ECFieldElement subtract(ECFieldElement eCFieldElement) {
        int[] create = Nat.create(17);
        SecP521R1Field.subtract(this.f803x, ((SecP521R1FieldElement) eCFieldElement).f803x, create);
        return new SecP521R1FieldElement(create);
    }

    public boolean testBitZero() {
        return Nat.getBit(this.f803x, 0) == 1;
    }

    public BigInteger toBigInteger() {
        return Nat.toBigInteger(17, this.f803x);
    }
}
