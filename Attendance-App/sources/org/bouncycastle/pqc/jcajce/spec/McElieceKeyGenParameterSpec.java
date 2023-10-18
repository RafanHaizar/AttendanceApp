package org.bouncycastle.pqc.jcajce.spec;

import java.security.InvalidParameterException;
import java.security.spec.AlgorithmParameterSpec;
import org.bouncycastle.pqc.math.linearalgebra.PolynomialRingGF2;

public class McElieceKeyGenParameterSpec implements AlgorithmParameterSpec {
    public static final int DEFAULT_M = 11;
    public static final int DEFAULT_T = 50;
    private int fieldPoly;

    /* renamed from: m */
    private int f949m;

    /* renamed from: n */
    private int f950n;

    /* renamed from: t */
    private int f951t;

    public McElieceKeyGenParameterSpec() {
        this(11, 50);
    }

    public McElieceKeyGenParameterSpec(int i) {
        if (i >= 1) {
            this.f949m = 0;
            this.f950n = 1;
            while (true) {
                int i2 = this.f950n;
                if (i2 < i) {
                    this.f950n = i2 << 1;
                    this.f949m++;
                } else {
                    int i3 = i2 >>> 1;
                    this.f951t = i3;
                    int i4 = this.f949m;
                    this.f951t = i3 / i4;
                    this.fieldPoly = PolynomialRingGF2.getIrreduciblePolynomial(i4);
                    return;
                }
            }
        } else {
            throw new IllegalArgumentException("key size must be positive");
        }
    }

    public McElieceKeyGenParameterSpec(int i, int i2) throws InvalidParameterException {
        if (i < 1) {
            throw new IllegalArgumentException("m must be positive");
        } else if (i <= 32) {
            this.f949m = i;
            int i3 = 1 << i;
            this.f950n = i3;
            if (i2 < 0) {
                throw new IllegalArgumentException("t must be positive");
            } else if (i2 <= i3) {
                this.f951t = i2;
                this.fieldPoly = PolynomialRingGF2.getIrreduciblePolynomial(i);
            } else {
                throw new IllegalArgumentException("t must be less than n = 2^m");
            }
        } else {
            throw new IllegalArgumentException("m is too large");
        }
    }

    public McElieceKeyGenParameterSpec(int i, int i2, int i3) {
        this.f949m = i;
        if (i < 1) {
            throw new IllegalArgumentException("m must be positive");
        } else if (i <= 32) {
            int i4 = 1 << i;
            this.f950n = i4;
            this.f951t = i2;
            if (i2 < 0) {
                throw new IllegalArgumentException("t must be positive");
            } else if (i2 > i4) {
                throw new IllegalArgumentException("t must be less than n = 2^m");
            } else if (PolynomialRingGF2.degree(i3) != i || !PolynomialRingGF2.isIrreducible(i3)) {
                throw new IllegalArgumentException("polynomial is not a field polynomial for GF(2^m)");
            } else {
                this.fieldPoly = i3;
            }
        } else {
            throw new IllegalArgumentException(" m is too large");
        }
    }

    public int getFieldPoly() {
        return this.fieldPoly;
    }

    public int getM() {
        return this.f949m;
    }

    public int getN() {
        return this.f950n;
    }

    public int getT() {
        return this.f951t;
    }
}
