package org.bouncycastle.pqc.math.linearalgebra;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Vector;

public class GF2nONBField extends GF2nField {
    private static final int MAXLONG = 64;
    private int mBit;
    private int mLength;
    int[][] mMult;
    private int mType;

    public GF2nONBField(int i, SecureRandom secureRandom) throws RuntimeException {
        super(secureRandom);
        if (i >= 3) {
            this.mDegree = i;
            this.mLength = this.mDegree / 64;
            int i2 = this.mDegree & 63;
            this.mBit = i2;
            if (i2 == 0) {
                this.mBit = 64;
            } else {
                this.mLength++;
            }
            computeType();
            if (this.mType < 3) {
                int i3 = this.mDegree;
                int[] iArr = new int[2];
                iArr[1] = 2;
                iArr[0] = i3;
                this.mMult = (int[][]) Array.newInstance(Integer.TYPE, iArr);
                for (int i4 = 0; i4 < this.mDegree; i4++) {
                    int[] iArr2 = this.mMult[i4];
                    iArr2[0] = -1;
                    iArr2[1] = -1;
                }
                computeMultMatrix();
                computeFieldPolynomial();
                this.fields = new Vector();
                this.matrices = new Vector();
                return;
            }
            throw new RuntimeException("\nThe type of this field is " + this.mType);
        }
        throw new IllegalArgumentException("k must be at least 3");
    }

    private void computeMultMatrix() {
        int i;
        int i2 = this.mType;
        if ((i2 & 7) != 0) {
            int i3 = (i2 * this.mDegree) + 1;
            int[] iArr = new int[i3];
            int i4 = this.mType;
            int elementOfOrder = i4 == 1 ? 1 : i4 == 2 ? i3 - 1 : elementOfOrder(i4, i3);
            int i5 = 0;
            int i6 = 1;
            while (true) {
                i = this.mType;
                if (i5 >= i) {
                    break;
                }
                int i7 = i6;
                for (int i8 = 0; i8 < this.mDegree; i8++) {
                    iArr[i7] = i8;
                    i7 = (i7 << 1) % i3;
                    if (i7 < 0) {
                        i7 += i3;
                    }
                }
                i6 = (i6 * elementOfOrder) % i3;
                if (i6 < 0) {
                    i6 += i3;
                }
                i5++;
            }
            if (i == 1) {
                int i9 = 1;
                while (i9 < i3 - 1) {
                    int i10 = i9 + 1;
                    int[] iArr2 = this.mMult[iArr[i10]];
                    int i11 = i3 - i9;
                    if (iArr2[0] == -1) {
                        iArr2[0] = iArr[i11];
                    } else {
                        iArr2[1] = iArr[i11];
                    }
                    i9 = i10;
                }
                int i12 = this.mDegree >> 1;
                for (int i13 = 1; i13 <= i12; i13++) {
                    int[][] iArr3 = this.mMult;
                    int i14 = i13 - 1;
                    int[] iArr4 = iArr3[i14];
                    if (iArr4[0] == -1) {
                        iArr4[0] = (i12 + i13) - 1;
                    } else {
                        iArr4[1] = (i12 + i13) - 1;
                    }
                    int[] iArr5 = iArr3[(i12 + i13) - 1];
                    if (iArr5[0] == -1) {
                        iArr5[0] = i14;
                    } else {
                        iArr5[1] = i14;
                    }
                }
            } else if (i == 2) {
                int i15 = 1;
                while (i15 < i3 - 1) {
                    int i16 = i15 + 1;
                    int[] iArr6 = this.mMult[iArr[i16]];
                    int i17 = i3 - i15;
                    if (iArr6[0] == -1) {
                        iArr6[0] = iArr[i17];
                    } else {
                        iArr6[1] = iArr[i17];
                    }
                    i15 = i16;
                }
            } else {
                throw new RuntimeException("only type 1 or type 2 implemented");
            }
        } else {
            throw new RuntimeException("bisher nur fuer Gausssche Normalbasen implementiert");
        }
    }

    private void computeType() throws RuntimeException {
        if ((this.mDegree & 7) != 0) {
            this.mType = 1;
            int i = 0;
            while (i != 1) {
                int i2 = (this.mType * this.mDegree) + 1;
                if (IntegerFunctions.isPrime(i2)) {
                    i = IntegerFunctions.gcd((this.mType * this.mDegree) / IntegerFunctions.order(2, i2), this.mDegree);
                }
                this.mType++;
            }
            int i3 = this.mType - 1;
            this.mType = i3;
            if (i3 == 1) {
                int i4 = (this.mDegree << 1) + 1;
                if (IntegerFunctions.isPrime(i4)) {
                    if (IntegerFunctions.gcd((this.mDegree << 1) / IntegerFunctions.order(2, i4), this.mDegree) == 1) {
                        this.mType++;
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        throw new RuntimeException("The extension degree is divisible by 8!");
    }

    private int elementOfOrder(int i, int i2) {
        int order;
        Random random = new Random();
        int i3 = 0;
        while (i3 == 0) {
            int i4 = i2 - 1;
            i3 = random.nextInt() % i4;
            if (i3 < 0) {
                i3 += i4;
            }
        }
        while (true) {
            order = IntegerFunctions.order(i3, i2);
            if (order % i == 0 && order != 0) {
                break;
            }
            while (i3 == 0) {
                int i5 = i2 - 1;
                int nextInt = random.nextInt() % i5;
                if (nextInt < 0) {
                    nextInt += i5;
                }
            }
        }
        int i6 = i3;
        for (int i7 = 2; i7 <= i / order; i7++) {
            i6 *= i3;
        }
        return i6;
    }

    /* access modifiers changed from: protected */
    public void computeCOBMatrix(GF2nField gF2nField) {
        GF2nElement randomRoot;
        if (this.mDegree == gF2nField.mDegree) {
            GF2Polynomial[] gF2PolynomialArr = new GF2Polynomial[this.mDegree];
            for (int i = 0; i < this.mDegree; i++) {
                gF2PolynomialArr[i] = new GF2Polynomial(this.mDegree);
            }
            do {
                randomRoot = gF2nField.getRandomRoot(this.fieldPolynomial);
            } while (randomRoot.isZero());
            GF2nElement[] gF2nElementArr = new GF2nPolynomialElement[this.mDegree];
            gF2nElementArr[0] = (GF2nElement) randomRoot.clone();
            for (int i2 = 1; i2 < this.mDegree; i2++) {
                gF2nElementArr[i2] = gF2nElementArr[i2 - 1].square();
            }
            for (int i3 = 0; i3 < this.mDegree; i3++) {
                for (int i4 = 0; i4 < this.mDegree; i4++) {
                    if (gF2nElementArr[i3].testBit(i4)) {
                        gF2PolynomialArr[(this.mDegree - i4) - 1].setBit((this.mDegree - i3) - 1);
                    }
                }
            }
            this.fields.addElement(gF2nField);
            this.matrices.addElement(gF2PolynomialArr);
            gF2nField.fields.addElement(this);
            gF2nField.matrices.addElement(invertMatrix(gF2PolynomialArr));
            return;
        }
        throw new IllegalArgumentException("GF2nField.computeCOBMatrix: B1 has a different degree and thus cannot be coverted to!");
    }

    /* access modifiers changed from: protected */
    public void computeFieldPolynomial() {
        GF2Polynomial gF2Polynomial;
        int i = this.mType;
        if (i == 1) {
            gF2Polynomial = new GF2Polynomial(this.mDegree + 1, "ALL");
        } else if (i == 2) {
            GF2Polynomial gF2Polynomial2 = new GF2Polynomial(this.mDegree + 1, "ONE");
            GF2Polynomial gF2Polynomial3 = new GF2Polynomial(this.mDegree + 1, "X");
            gF2Polynomial3.addToThis(gF2Polynomial2);
            GF2Polynomial gF2Polynomial4 = gF2Polynomial2;
            gF2Polynomial = gF2Polynomial3;
            int i2 = 1;
            while (i2 < this.mDegree) {
                GF2Polynomial shiftLeft = gF2Polynomial.shiftLeft();
                shiftLeft.addToThis(gF2Polynomial4);
                i2++;
                gF2Polynomial4 = gF2Polynomial;
                gF2Polynomial = shiftLeft;
            }
        } else {
            return;
        }
        this.fieldPolynomial = gF2Polynomial;
    }

    /* access modifiers changed from: package-private */
    public int getONBBit() {
        return this.mBit;
    }

    /* access modifiers changed from: package-private */
    public int getONBLength() {
        return this.mLength;
    }

    /* access modifiers changed from: protected */
    public GF2nElement getRandomRoot(GF2Polynomial gF2Polynomial) {
        GF2nPolynomial gcd;
        int degree;
        int degree2;
        GF2nPolynomial gF2nPolynomial = new GF2nPolynomial(gF2Polynomial, (GF2nField) this);
        while (gF2nPolynomial.getDegree() > 1) {
            while (true) {
                GF2nONBElement gF2nONBElement = new GF2nONBElement(this, this.random);
                GF2nPolynomial gF2nPolynomial2 = new GF2nPolynomial(2, (GF2nElement) GF2nONBElement.ZERO(this));
                gF2nPolynomial2.set(1, gF2nONBElement);
                GF2nPolynomial gF2nPolynomial3 = new GF2nPolynomial(gF2nPolynomial2);
                for (int i = 1; i <= this.mDegree - 1; i++) {
                    gF2nPolynomial3 = gF2nPolynomial3.multiplyAndReduce(gF2nPolynomial3, gF2nPolynomial).add(gF2nPolynomial2);
                }
                gcd = gF2nPolynomial3.gcd(gF2nPolynomial);
                degree = gcd.getDegree();
                degree2 = gF2nPolynomial.getDegree();
                if (degree != 0 && degree != degree2) {
                    break;
                }
            }
            gF2nPolynomial = (degree << 1) > degree2 ? gF2nPolynomial.quotient(gcd) : new GF2nPolynomial(gcd);
        }
        return gF2nPolynomial.mo8627at(0);
    }

    /* access modifiers changed from: package-private */
    public int[][] invMatrix(int[][] iArr) {
        int i = this.mDegree;
        int[] iArr2 = new int[2];
        iArr2[1] = this.mDegree;
        iArr2[0] = i;
        int[][] iArr3 = (int[][]) Array.newInstance(Integer.TYPE, iArr2);
        int i2 = this.mDegree;
        int[] iArr4 = new int[2];
        iArr4[1] = this.mDegree;
        iArr4[0] = i2;
        int[][] iArr5 = (int[][]) Array.newInstance(Integer.TYPE, iArr4);
        for (int i3 = 0; i3 < this.mDegree; i3++) {
            iArr5[i3][i3] = 1;
        }
        for (int i4 = 0; i4 < this.mDegree; i4++) {
            for (int i5 = i4; i5 < this.mDegree; i5++) {
                iArr[(this.mDegree - 1) - i4][i5] = iArr[i4][i4];
            }
        }
        int[][] iArr6 = null;
        return null;
    }
}
