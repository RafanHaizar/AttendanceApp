package org.bouncycastle.pqc.math.linearalgebra;

import java.lang.reflect.Array;
import java.security.SecureRandom;

public final class GoppaCode {

    public static class MaMaPe {

        /* renamed from: h */
        private GF2Matrix f959h;

        /* renamed from: p */
        private Permutation f960p;

        /* renamed from: s */
        private GF2Matrix f961s;

        public MaMaPe(GF2Matrix gF2Matrix, GF2Matrix gF2Matrix2, Permutation permutation) {
            this.f961s = gF2Matrix;
            this.f959h = gF2Matrix2;
            this.f960p = permutation;
        }

        public GF2Matrix getFirstMatrix() {
            return this.f961s;
        }

        public Permutation getPermutation() {
            return this.f960p;
        }

        public GF2Matrix getSecondMatrix() {
            return this.f959h;
        }
    }

    public static class MatrixSet {

        /* renamed from: g */
        private GF2Matrix f962g;
        private int[] setJ;

        public MatrixSet(GF2Matrix gF2Matrix, int[] iArr) {
            this.f962g = gF2Matrix;
            this.setJ = iArr;
        }

        public GF2Matrix getG() {
            return this.f962g;
        }

        public int[] getSetJ() {
            return this.setJ;
        }
    }

    private GoppaCode() {
    }

    public static MaMaPe computeSystematicForm(GF2Matrix gF2Matrix, SecureRandom secureRandom) {
        GF2Matrix gF2Matrix2;
        boolean z;
        int numColumns = gF2Matrix.getNumColumns();
        GF2Matrix gF2Matrix3 = null;
        while (true) {
            Permutation permutation = new Permutation(numColumns, secureRandom);
            GF2Matrix gF2Matrix4 = (GF2Matrix) gF2Matrix.rightMultiply(permutation);
            GF2Matrix leftSubMatrix = gF2Matrix4.getLeftSubMatrix();
            try {
                gF2Matrix2 = (GF2Matrix) leftSubMatrix.computeInverse();
                z = true;
            } catch (ArithmeticException e) {
                gF2Matrix2 = gF2Matrix3;
                z = false;
            }
            if (z) {
                return new MaMaPe(leftSubMatrix, ((GF2Matrix) gF2Matrix2.rightMultiply((Matrix) gF2Matrix4)).getRightSubMatrix(), permutation);
            }
            gF2Matrix3 = gF2Matrix2;
        }
    }

    public static GF2Matrix createCanonicalCheckMatrix(GF2mField gF2mField, PolynomialGF2mSmallM polynomialGF2mSmallM) {
        GF2mField gF2mField2 = gF2mField;
        PolynomialGF2mSmallM polynomialGF2mSmallM2 = polynomialGF2mSmallM;
        int degree = gF2mField.getDegree();
        int i = 1 << degree;
        int degree2 = polynomialGF2mSmallM.getDegree();
        int[] iArr = new int[2];
        iArr[1] = i;
        iArr[0] = degree2;
        int[][] iArr2 = (int[][]) Array.newInstance(Integer.TYPE, iArr);
        int[] iArr3 = new int[2];
        iArr3[1] = i;
        iArr3[0] = degree2;
        int[][] iArr4 = (int[][]) Array.newInstance(Integer.TYPE, iArr3);
        for (int i2 = 0; i2 < i; i2++) {
            iArr4[0][i2] = gF2mField2.inverse(polynomialGF2mSmallM2.evaluateAt(i2));
        }
        for (int i3 = 1; i3 < degree2; i3++) {
            for (int i4 = 0; i4 < i; i4++) {
                iArr4[i3][i4] = gF2mField2.mult(iArr4[i3 - 1][i4], i4);
            }
        }
        for (int i5 = 0; i5 < degree2; i5++) {
            for (int i6 = 0; i6 < i; i6++) {
                for (int i7 = 0; i7 <= i5; i7++) {
                    int[] iArr5 = iArr2[i5];
                    iArr5[i6] = gF2mField2.add(iArr5[i6], gF2mField2.mult(iArr4[i7][i6], polynomialGF2mSmallM2.getCoefficient((degree2 + i7) - i5)));
                }
            }
        }
        int[] iArr6 = new int[2];
        iArr6[1] = (i + 31) >>> 5;
        iArr6[0] = degree2 * degree;
        int[][] iArr7 = (int[][]) Array.newInstance(Integer.TYPE, iArr6);
        for (int i8 = 0; i8 < i; i8++) {
            int i9 = i8 >>> 5;
            int i10 = 1 << (i8 & 31);
            for (int i11 = 0; i11 < degree2; i11++) {
                int i12 = iArr2[i11][i8];
                for (int i13 = 0; i13 < degree; i13++) {
                    if (((i12 >>> i13) & 1) != 0) {
                        int[] iArr8 = iArr7[(((i11 + 1) * degree) - i13) - 1];
                        iArr8[i9] = iArr8[i9] ^ i10;
                    }
                }
            }
        }
        return new GF2Matrix(i, iArr7);
    }

    public static GF2Vector syndromeDecode(GF2Vector gF2Vector, GF2mField gF2mField, PolynomialGF2mSmallM polynomialGF2mSmallM, PolynomialGF2mSmallM[] polynomialGF2mSmallMArr) {
        int degree = 1 << gF2mField.getDegree();
        GF2Vector gF2Vector2 = new GF2Vector(degree);
        if (!gF2Vector.isZero()) {
            PolynomialGF2mSmallM[] modPolynomialToFracton = new PolynomialGF2mSmallM(gF2Vector.toExtensionFieldVector(gF2mField)).modInverse(polynomialGF2mSmallM).addMonomial(1).modSquareRootMatrix(polynomialGF2mSmallMArr).modPolynomialToFracton(polynomialGF2mSmallM);
            PolynomialGF2mSmallM polynomialGF2mSmallM2 = modPolynomialToFracton[0];
            PolynomialGF2mSmallM multiply = polynomialGF2mSmallM2.multiply(polynomialGF2mSmallM2);
            PolynomialGF2mSmallM polynomialGF2mSmallM3 = modPolynomialToFracton[1];
            PolynomialGF2mSmallM add = multiply.add(polynomialGF2mSmallM3.multiply(polynomialGF2mSmallM3).multWithMonomial(1));
            PolynomialGF2mSmallM multWithElement = add.multWithElement(gF2mField.inverse(add.getHeadCoefficient()));
            for (int i = 0; i < degree; i++) {
                if (multWithElement.evaluateAt(i) == 0) {
                    gF2Vector2.setBit(i);
                }
            }
        }
        return gF2Vector2;
    }
}
