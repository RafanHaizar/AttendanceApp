package org.bouncycastle.math.p018ec.custom.sec;

import com.itextpdf.p026io.codec.TIFFConstants;
import java.math.BigInteger;
import org.bouncycastle.math.p018ec.ECFieldElement;
import org.bouncycastle.math.raw.Nat;
import org.bouncycastle.math.raw.Nat320;
import org.bouncycastle.util.Arrays;

/* renamed from: org.bouncycastle.math.ec.custom.sec.SecT283FieldElement */
public class SecT283FieldElement extends ECFieldElement.AbstractF2m {

    /* renamed from: x */
    protected long[] f810x;

    public SecT283FieldElement() {
        this.f810x = Nat320.create64();
    }

    public SecT283FieldElement(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.bitLength() > 283) {
            throw new IllegalArgumentException("x value invalid for SecT283FieldElement");
        }
        this.f810x = SecT283Field.fromBigInteger(bigInteger);
    }

    protected SecT283FieldElement(long[] jArr) {
        this.f810x = jArr;
    }

    public ECFieldElement add(ECFieldElement eCFieldElement) {
        long[] create64 = Nat320.create64();
        SecT283Field.add(this.f810x, ((SecT283FieldElement) eCFieldElement).f810x, create64);
        return new SecT283FieldElement(create64);
    }

    public ECFieldElement addOne() {
        long[] create64 = Nat320.create64();
        SecT283Field.addOne(this.f810x, create64);
        return new SecT283FieldElement(create64);
    }

    public ECFieldElement divide(ECFieldElement eCFieldElement) {
        return multiply(eCFieldElement.invert());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SecT283FieldElement)) {
            return false;
        }
        return Nat320.eq64(this.f810x, ((SecT283FieldElement) obj).f810x);
    }

    public String getFieldName() {
        return "SecT283Field";
    }

    public int getFieldSize() {
        return TIFFConstants.TIFFTAG_YRESOLUTION;
    }

    public int getK1() {
        return 5;
    }

    public int getK2() {
        return 7;
    }

    public int getK3() {
        return 12;
    }

    public int getM() {
        return TIFFConstants.TIFFTAG_YRESOLUTION;
    }

    public int getRepresentation() {
        return 3;
    }

    public ECFieldElement halfTrace() {
        long[] create64 = Nat320.create64();
        SecT283Field.halfTrace(this.f810x, create64);
        return new SecT283FieldElement(create64);
    }

    public boolean hasFastTrace() {
        return true;
    }

    public int hashCode() {
        return Arrays.hashCode(this.f810x, 0, 5) ^ 2831275;
    }

    public ECFieldElement invert() {
        long[] create64 = Nat320.create64();
        SecT283Field.invert(this.f810x, create64);
        return new SecT283FieldElement(create64);
    }

    public boolean isOne() {
        return Nat320.isOne64(this.f810x);
    }

    public boolean isZero() {
        return Nat320.isZero64(this.f810x);
    }

    public ECFieldElement multiply(ECFieldElement eCFieldElement) {
        long[] create64 = Nat320.create64();
        SecT283Field.multiply(this.f810x, ((SecT283FieldElement) eCFieldElement).f810x, create64);
        return new SecT283FieldElement(create64);
    }

    public ECFieldElement multiplyMinusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3) {
        return multiplyPlusProduct(eCFieldElement, eCFieldElement2, eCFieldElement3);
    }

    public ECFieldElement multiplyPlusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3) {
        long[] jArr = this.f810x;
        long[] jArr2 = ((SecT283FieldElement) eCFieldElement).f810x;
        long[] jArr3 = ((SecT283FieldElement) eCFieldElement2).f810x;
        long[] jArr4 = ((SecT283FieldElement) eCFieldElement3).f810x;
        long[] create64 = Nat.create64(9);
        SecT283Field.multiplyAddToExt(jArr, jArr2, create64);
        SecT283Field.multiplyAddToExt(jArr3, jArr4, create64);
        long[] create642 = Nat320.create64();
        SecT283Field.reduce(create64, create642);
        return new SecT283FieldElement(create642);
    }

    public ECFieldElement negate() {
        return this;
    }

    public ECFieldElement sqrt() {
        long[] create64 = Nat320.create64();
        SecT283Field.sqrt(this.f810x, create64);
        return new SecT283FieldElement(create64);
    }

    public ECFieldElement square() {
        long[] create64 = Nat320.create64();
        SecT283Field.square(this.f810x, create64);
        return new SecT283FieldElement(create64);
    }

    public ECFieldElement squareMinusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        return squarePlusProduct(eCFieldElement, eCFieldElement2);
    }

    public ECFieldElement squarePlusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        long[] jArr = this.f810x;
        long[] jArr2 = ((SecT283FieldElement) eCFieldElement).f810x;
        long[] jArr3 = ((SecT283FieldElement) eCFieldElement2).f810x;
        long[] create64 = Nat.create64(9);
        SecT283Field.squareAddToExt(jArr, create64);
        SecT283Field.multiplyAddToExt(jArr2, jArr3, create64);
        long[] create642 = Nat320.create64();
        SecT283Field.reduce(create64, create642);
        return new SecT283FieldElement(create642);
    }

    public ECFieldElement squarePow(int i) {
        if (i < 1) {
            return this;
        }
        long[] create64 = Nat320.create64();
        SecT283Field.squareN(this.f810x, i, create64);
        return new SecT283FieldElement(create64);
    }

    public ECFieldElement subtract(ECFieldElement eCFieldElement) {
        return add(eCFieldElement);
    }

    public boolean testBitZero() {
        return (this.f810x[0] & 1) != 0;
    }

    public BigInteger toBigInteger() {
        return Nat320.toBigInteger64(this.f810x);
    }

    public int trace() {
        return SecT283Field.trace(this.f810x);
    }
}
