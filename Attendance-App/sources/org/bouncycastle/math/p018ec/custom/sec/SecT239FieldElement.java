package org.bouncycastle.math.p018ec.custom.sec;

import java.math.BigInteger;
import org.bouncycastle.crypto.tls.CipherSuite;
import org.bouncycastle.math.p018ec.ECFieldElement;
import org.bouncycastle.math.raw.Nat256;
import org.bouncycastle.util.Arrays;

/* renamed from: org.bouncycastle.math.ec.custom.sec.SecT239FieldElement */
public class SecT239FieldElement extends ECFieldElement.AbstractF2m {

    /* renamed from: x */
    protected long[] f809x;

    public SecT239FieldElement() {
        this.f809x = Nat256.create64();
    }

    public SecT239FieldElement(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.bitLength() > 239) {
            throw new IllegalArgumentException("x value invalid for SecT239FieldElement");
        }
        this.f809x = SecT239Field.fromBigInteger(bigInteger);
    }

    protected SecT239FieldElement(long[] jArr) {
        this.f809x = jArr;
    }

    public ECFieldElement add(ECFieldElement eCFieldElement) {
        long[] create64 = Nat256.create64();
        SecT239Field.add(this.f809x, ((SecT239FieldElement) eCFieldElement).f809x, create64);
        return new SecT239FieldElement(create64);
    }

    public ECFieldElement addOne() {
        long[] create64 = Nat256.create64();
        SecT239Field.addOne(this.f809x, create64);
        return new SecT239FieldElement(create64);
    }

    public ECFieldElement divide(ECFieldElement eCFieldElement) {
        return multiply(eCFieldElement.invert());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SecT239FieldElement)) {
            return false;
        }
        return Nat256.eq64(this.f809x, ((SecT239FieldElement) obj).f809x);
    }

    public String getFieldName() {
        return "SecT239Field";
    }

    public int getFieldSize() {
        return 239;
    }

    public int getK1() {
        return CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256;
    }

    public int getK2() {
        return 0;
    }

    public int getK3() {
        return 0;
    }

    public int getM() {
        return 239;
    }

    public int getRepresentation() {
        return 2;
    }

    public ECFieldElement halfTrace() {
        long[] create64 = Nat256.create64();
        SecT239Field.halfTrace(this.f809x, create64);
        return new SecT239FieldElement(create64);
    }

    public boolean hasFastTrace() {
        return true;
    }

    public int hashCode() {
        return Arrays.hashCode(this.f809x, 0, 4) ^ 23900158;
    }

    public ECFieldElement invert() {
        long[] create64 = Nat256.create64();
        SecT239Field.invert(this.f809x, create64);
        return new SecT239FieldElement(create64);
    }

    public boolean isOne() {
        return Nat256.isOne64(this.f809x);
    }

    public boolean isZero() {
        return Nat256.isZero64(this.f809x);
    }

    public ECFieldElement multiply(ECFieldElement eCFieldElement) {
        long[] create64 = Nat256.create64();
        SecT239Field.multiply(this.f809x, ((SecT239FieldElement) eCFieldElement).f809x, create64);
        return new SecT239FieldElement(create64);
    }

    public ECFieldElement multiplyMinusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3) {
        return multiplyPlusProduct(eCFieldElement, eCFieldElement2, eCFieldElement3);
    }

    public ECFieldElement multiplyPlusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3) {
        long[] jArr = this.f809x;
        long[] jArr2 = ((SecT239FieldElement) eCFieldElement).f809x;
        long[] jArr3 = ((SecT239FieldElement) eCFieldElement2).f809x;
        long[] jArr4 = ((SecT239FieldElement) eCFieldElement3).f809x;
        long[] createExt64 = Nat256.createExt64();
        SecT239Field.multiplyAddToExt(jArr, jArr2, createExt64);
        SecT239Field.multiplyAddToExt(jArr3, jArr4, createExt64);
        long[] create64 = Nat256.create64();
        SecT239Field.reduce(createExt64, create64);
        return new SecT239FieldElement(create64);
    }

    public ECFieldElement negate() {
        return this;
    }

    public ECFieldElement sqrt() {
        long[] create64 = Nat256.create64();
        SecT239Field.sqrt(this.f809x, create64);
        return new SecT239FieldElement(create64);
    }

    public ECFieldElement square() {
        long[] create64 = Nat256.create64();
        SecT239Field.square(this.f809x, create64);
        return new SecT239FieldElement(create64);
    }

    public ECFieldElement squareMinusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        return squarePlusProduct(eCFieldElement, eCFieldElement2);
    }

    public ECFieldElement squarePlusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        long[] jArr = this.f809x;
        long[] jArr2 = ((SecT239FieldElement) eCFieldElement).f809x;
        long[] jArr3 = ((SecT239FieldElement) eCFieldElement2).f809x;
        long[] createExt64 = Nat256.createExt64();
        SecT239Field.squareAddToExt(jArr, createExt64);
        SecT239Field.multiplyAddToExt(jArr2, jArr3, createExt64);
        long[] create64 = Nat256.create64();
        SecT239Field.reduce(createExt64, create64);
        return new SecT239FieldElement(create64);
    }

    public ECFieldElement squarePow(int i) {
        if (i < 1) {
            return this;
        }
        long[] create64 = Nat256.create64();
        SecT239Field.squareN(this.f809x, i, create64);
        return new SecT239FieldElement(create64);
    }

    public ECFieldElement subtract(ECFieldElement eCFieldElement) {
        return add(eCFieldElement);
    }

    public boolean testBitZero() {
        return (this.f809x[0] & 1) != 0;
    }

    public BigInteger toBigInteger() {
        return Nat256.toBigInteger64(this.f809x);
    }

    public int trace() {
        return SecT239Field.trace(this.f809x);
    }
}
