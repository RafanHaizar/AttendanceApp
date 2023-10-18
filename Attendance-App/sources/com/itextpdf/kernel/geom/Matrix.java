package com.itextpdf.kernel.geom;

import java.io.Serializable;
import java.util.Arrays;

public class Matrix implements Serializable {
    public static final int I11 = 0;
    public static final int I12 = 1;
    public static final int I13 = 2;
    public static final int I21 = 3;
    public static final int I22 = 4;
    public static final int I23 = 5;
    public static final int I31 = 6;
    public static final int I32 = 7;
    public static final int I33 = 8;
    private static final long serialVersionUID = 7434885566068528477L;
    private final float[] vals;

    public Matrix() {
        this.vals = new float[]{1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    }

    public Matrix(float tx, float ty) {
        float[] fArr = {1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
        this.vals = fArr;
        fArr[6] = tx;
        fArr[7] = ty;
    }

    public Matrix(float e11, float e12, float e13, float e21, float e22, float e23, float e31, float e32, float e33) {
        float[] fArr = {1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
        this.vals = fArr;
        fArr[0] = e11;
        fArr[1] = e12;
        fArr[2] = e13;
        fArr[3] = e21;
        fArr[4] = e22;
        fArr[5] = e23;
        fArr[6] = e31;
        fArr[7] = e32;
        fArr[8] = e33;
    }

    public Matrix(float a, float b, float c, float d, float e, float f) {
        float[] fArr = {1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
        this.vals = fArr;
        fArr[0] = a;
        fArr[1] = b;
        fArr[2] = 0.0f;
        fArr[3] = c;
        fArr[4] = d;
        fArr[5] = 0.0f;
        fArr[6] = e;
        fArr[7] = f;
        fArr[8] = 1.0f;
    }

    public float get(int index) {
        return this.vals[index];
    }

    public Matrix multiply(Matrix by) {
        Matrix rslt = new Matrix();
        float[] a = this.vals;
        float[] b = by.vals;
        float[] c = rslt.vals;
        c[0] = (a[0] * b[0]) + (a[1] * b[3]) + (a[2] * b[6]);
        c[1] = (a[0] * b[1]) + (a[1] * b[4]) + (a[2] * b[7]);
        c[2] = (a[0] * b[2]) + (a[1] * b[5]) + (a[2] * b[8]);
        c[3] = (a[3] * b[0]) + (a[4] * b[3]) + (a[5] * b[6]);
        c[4] = (a[3] * b[1]) + (a[4] * b[4]) + (a[5] * b[7]);
        c[5] = (a[3] * b[2]) + (a[4] * b[5]) + (a[5] * b[8]);
        c[6] = (a[6] * b[0]) + (a[7] * b[3]) + (a[8] * b[6]);
        c[7] = (a[6] * b[1]) + (a[7] * b[4]) + (a[8] * b[7]);
        c[8] = (a[6] * b[2]) + (a[7] * b[5]) + (a[8] * b[8]);
        return rslt;
    }

    public Matrix add(Matrix arg) {
        Matrix rslt = new Matrix();
        float[] a = this.vals;
        float[] b = arg.vals;
        float[] c = rslt.vals;
        c[0] = a[0] + b[0];
        c[1] = a[1] + b[1];
        c[2] = a[2] + b[2];
        c[3] = a[3] + b[3];
        c[4] = a[4] + b[4];
        c[5] = a[5] + b[5];
        c[6] = a[6] + b[6];
        c[7] = a[7] + b[7];
        c[8] = a[8] + b[8];
        return rslt;
    }

    public Matrix subtract(Matrix arg) {
        Matrix rslt = new Matrix();
        float[] a = this.vals;
        float[] b = arg.vals;
        float[] c = rslt.vals;
        c[0] = a[0] - b[0];
        c[1] = a[1] - b[1];
        c[2] = a[2] - b[2];
        c[3] = a[3] - b[3];
        c[4] = a[4] - b[4];
        c[5] = a[5] - b[5];
        c[6] = a[6] - b[6];
        c[7] = a[7] - b[7];
        c[8] = a[8] - b[8];
        return rslt;
    }

    public float getDeterminant() {
        float[] fArr = this.vals;
        float f = fArr[0];
        float f2 = fArr[4];
        float f3 = fArr[8];
        float f4 = fArr[1];
        float f5 = fArr[5];
        float f6 = fArr[6];
        float f7 = (f * f2 * f3) + (f4 * f5 * f6);
        float f8 = fArr[2];
        float f9 = fArr[3];
        float f10 = fArr[7];
        return (((f7 + ((f8 * f9) * f10)) - ((f * f5) * f10)) - ((f4 * f9) * f3)) - ((f8 * f2) * f6);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Matrix)) {
            return false;
        }
        return Arrays.equals(this.vals, ((Matrix) obj).vals);
    }

    public int hashCode() {
        return Arrays.hashCode(this.vals);
    }

    public String toString() {
        return this.vals[0] + "\t" + this.vals[1] + "\t" + this.vals[2] + "\n" + this.vals[3] + "\t" + this.vals[4] + "\t" + this.vals[5] + "\n" + this.vals[6] + "\t" + this.vals[7] + "\t" + this.vals[8];
    }
}
