package com.itextpdf.kernel.geom;

import java.util.Arrays;

public class Vector {

    /* renamed from: I1 */
    public static final int f1284I1 = 0;

    /* renamed from: I2 */
    public static final int f1285I2 = 1;

    /* renamed from: I3 */
    public static final int f1286I3 = 2;
    private final float[] vals;

    public Vector(float x, float y, float z) {
        float[] fArr = {0.0f, 0.0f, 0.0f};
        this.vals = fArr;
        fArr[0] = x;
        fArr[1] = y;
        fArr[2] = z;
    }

    public float get(int index) {
        return this.vals[index];
    }

    public Vector cross(Matrix by) {
        return new Vector((this.vals[0] * by.get(0)) + (this.vals[1] * by.get(3)) + (this.vals[2] * by.get(6)), (this.vals[0] * by.get(1)) + (this.vals[1] * by.get(4)) + (this.vals[2] * by.get(7)), (this.vals[0] * by.get(2)) + (this.vals[1] * by.get(5)) + (this.vals[2] * by.get(8)));
    }

    public Vector subtract(Vector v) {
        float[] fArr = this.vals;
        float f = fArr[0];
        float[] fArr2 = v.vals;
        return new Vector(f - fArr2[0], fArr[1] - fArr2[1], fArr[2] - fArr2[2]);
    }

    public Vector cross(Vector with) {
        float[] fArr = this.vals;
        float f = fArr[1];
        float[] fArr2 = with.vals;
        float f2 = fArr2[2];
        float f3 = fArr[2];
        float f4 = fArr2[1];
        float f5 = fArr2[0];
        float f6 = fArr[0];
        return new Vector((f * f2) - (f3 * f4), (f3 * f5) - (f2 * f6), (f6 * f4) - (f * f5));
    }

    public Vector normalize() {
        float l = length();
        float[] fArr = this.vals;
        return new Vector(fArr[0] / l, fArr[1] / l, fArr[2] / l);
    }

    public Vector multiply(float by) {
        float[] fArr = this.vals;
        return new Vector(fArr[0] * by, fArr[1] * by, fArr[2] * by);
    }

    public float dot(Vector with) {
        float[] fArr = this.vals;
        float f = fArr[0];
        float[] fArr2 = with.vals;
        return (f * fArr2[0]) + (fArr[1] * fArr2[1]) + (fArr[2] * fArr2[2]);
    }

    public float length() {
        return (float) Math.sqrt((double) lengthSquared());
    }

    public float lengthSquared() {
        float[] fArr = this.vals;
        float f = fArr[0];
        float f2 = fArr[1];
        float f3 = fArr[2];
        return (f * f) + (f2 * f2) + (f3 * f3);
    }

    public String toString() {
        return this.vals[0] + "," + this.vals[1] + "," + this.vals[2];
    }

    public int hashCode() {
        return (1 * 31) + Arrays.hashCode(this.vals);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass() && Arrays.equals(this.vals, ((Vector) obj).vals)) {
            return true;
        }
        return false;
    }
}
