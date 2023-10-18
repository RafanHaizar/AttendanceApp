package com.itextpdf.kernel.geom;

import java.io.Serializable;
import java.util.Objects;

public class AffineTransform implements Cloneable, Serializable {
    public static final int TYPE_FLIP = 64;
    public static final int TYPE_GENERAL_ROTATION = 16;
    public static final int TYPE_GENERAL_SCALE = 4;
    public static final int TYPE_GENERAL_TRANSFORM = 32;
    public static final int TYPE_IDENTITY = 0;
    public static final int TYPE_MASK_ROTATION = 24;
    public static final int TYPE_MASK_SCALE = 6;
    public static final int TYPE_QUADRANT_ROTATION = 8;
    public static final int TYPE_TRANSLATION = 1;
    public static final int TYPE_UNIFORM_SCALE = 2;
    static final int TYPE_UNKNOWN = -1;
    static final double ZERO = 1.0E-10d;
    private static final long serialVersionUID = 1330973210523860834L;
    double m00;
    double m01;
    double m02;
    double m10;
    double m11;
    double m12;
    int type;

    public AffineTransform() {
        this.type = 0;
        this.m11 = 1.0d;
        this.m00 = 1.0d;
        this.m12 = 0.0d;
        this.m02 = 0.0d;
        this.m01 = 0.0d;
        this.m10 = 0.0d;
    }

    public AffineTransform(AffineTransform t) {
        this.type = t.type;
        this.m00 = t.m00;
        this.m10 = t.m10;
        this.m01 = t.m01;
        this.m11 = t.m11;
        this.m02 = t.m02;
        this.m12 = t.m12;
    }

    public AffineTransform(double m002, double m102, double m012, double m112, double m022, double m122) {
        this.type = -1;
        this.m00 = m002;
        this.m10 = m102;
        this.m01 = m012;
        this.m11 = m112;
        this.m02 = m022;
        this.m12 = m122;
    }

    public AffineTransform(float[] matrix) {
        this.type = -1;
        this.m00 = (double) matrix[0];
        this.m10 = (double) matrix[1];
        this.m01 = (double) matrix[2];
        this.m11 = (double) matrix[3];
        if (matrix.length > 4) {
            this.m02 = (double) matrix[4];
            this.m12 = (double) matrix[5];
        }
    }

    public AffineTransform(double[] matrix) {
        this.type = -1;
        this.m00 = matrix[0];
        this.m10 = matrix[1];
        this.m01 = matrix[2];
        this.m11 = matrix[3];
        if (matrix.length > 4) {
            this.m02 = matrix[4];
            this.m12 = matrix[5];
        }
    }

    public int getType() {
        int i = this.type;
        if (i != -1) {
            return i;
        }
        int type2 = 0;
        double d = this.m00;
        double d2 = this.m01;
        double d3 = this.m10;
        double d4 = this.m11;
        if ((d * d2) + (d3 * d4) != 0.0d) {
            return 0 | 32;
        }
        if (this.m02 != 0.0d || this.m12 != 0.0d) {
            type2 = 0 | 1;
        } else if (d == 1.0d && d4 == 1.0d && d2 == 0.0d && d3 == 0.0d) {
            return 0;
        }
        if ((d * d4) - (d2 * d3) < 0.0d) {
            type2 |= 64;
        }
        double dx = (d * d) + (d3 * d3);
        if (dx != (d2 * d2) + (d4 * d4)) {
            type2 |= 4;
        } else if (dx != 1.0d) {
            type2 |= 2;
        }
        if ((d == 0.0d && d4 == 0.0d) || (d3 == 0.0d && d2 == 0.0d && (d < 0.0d || d4 < 0.0d))) {
            return type2 | 8;
        }
        if (d2 == 0.0d && d3 == 0.0d) {
            return type2;
        }
        return type2 | 16;
    }

    public double getScaleX() {
        return this.m00;
    }

    public double getScaleY() {
        return this.m11;
    }

    public double getShearX() {
        return this.m01;
    }

    public double getShearY() {
        return this.m10;
    }

    public double getTranslateX() {
        return this.m02;
    }

    public double getTranslateY() {
        return this.m12;
    }

    public boolean isIdentity() {
        return getType() == 0;
    }

    public void getMatrix(float[] matrix) {
        matrix[0] = (float) this.m00;
        matrix[1] = (float) this.m10;
        matrix[2] = (float) this.m01;
        matrix[3] = (float) this.m11;
        if (matrix.length > 4) {
            matrix[4] = (float) this.m02;
            matrix[5] = (float) this.m12;
        }
    }

    public void getMatrix(double[] matrix) {
        matrix[0] = this.m00;
        matrix[1] = this.m10;
        matrix[2] = this.m01;
        matrix[3] = this.m11;
        if (matrix.length > 4) {
            matrix[4] = this.m02;
            matrix[5] = this.m12;
        }
    }

    public double getDeterminant() {
        return (this.m00 * this.m11) - (this.m01 * this.m10);
    }

    public void setTransform(float m002, float m102, float m012, float m112, float m022, float m122) {
        this.type = -1;
        this.m00 = (double) m002;
        this.m10 = (double) m102;
        this.m01 = (double) m012;
        this.m11 = (double) m112;
        this.m02 = (double) m022;
        this.m12 = (double) m122;
    }

    public void setTransform(double m002, double m102, double m012, double m112, double m022, double m122) {
        this.type = -1;
        this.m00 = m002;
        this.m10 = m102;
        this.m01 = m012;
        this.m11 = m112;
        this.m02 = m022;
        this.m12 = m122;
    }

    public void setTransform(AffineTransform t) {
        this.type = t.type;
        setTransform(t.m00, t.m10, t.m01, t.m11, t.m02, t.m12);
    }

    public void setToIdentity() {
        this.type = 0;
        this.m11 = 1.0d;
        this.m00 = 1.0d;
        this.m12 = 0.0d;
        this.m02 = 0.0d;
        this.m01 = 0.0d;
        this.m10 = 0.0d;
    }

    public void setToTranslation(double mx, double my) {
        this.m11 = 1.0d;
        this.m00 = 1.0d;
        this.m10 = 0.0d;
        this.m01 = 0.0d;
        this.m02 = mx;
        this.m12 = my;
        if (mx == 0.0d && my == 0.0d) {
            this.type = 0;
        } else {
            this.type = 1;
        }
    }

    public void setToScale(double scx, double scy) {
        this.m00 = scx;
        this.m11 = scy;
        this.m12 = 0.0d;
        this.m02 = 0.0d;
        this.m01 = 0.0d;
        this.m10 = 0.0d;
        if (scx == 1.0d && scy == 1.0d) {
            this.type = 0;
        } else {
            this.type = -1;
        }
    }

    public void setToShear(double shx, double shy) {
        this.m11 = 1.0d;
        this.m00 = 1.0d;
        this.m12 = 0.0d;
        this.m02 = 0.0d;
        this.m01 = shx;
        this.m10 = shy;
        if (shx == 0.0d && shy == 0.0d) {
            this.type = 0;
        } else {
            this.type = -1;
        }
    }

    public void setToRotation(double angle) {
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double d = 1.0d;
        if (Math.abs(cos) < ZERO) {
            cos = 0.0d;
            if (sin <= 0.0d) {
                d = -1.0d;
            }
            sin = d;
        } else if (Math.abs(sin) < ZERO) {
            sin = 0.0d;
            if (cos <= 0.0d) {
                d = -1.0d;
            }
            cos = d;
        }
        double d2 = (double) ((float) cos);
        this.m11 = d2;
        this.m00 = d2;
        this.m01 = (double) ((float) (-sin));
        this.m10 = (double) ((float) sin);
        this.m12 = 0.0d;
        this.m02 = 0.0d;
        this.type = -1;
    }

    public void setToRotation(double angle, double px, double py) {
        setToRotation(angle);
        double d = this.m00;
        double d2 = this.m10;
        this.m02 = ((1.0d - d) * px) + (py * d2);
        this.m12 = (py * (1.0d - d)) - (px * d2);
        this.type = -1;
    }

    public static AffineTransform getTranslateInstance(double mx, double my) {
        AffineTransform t = new AffineTransform();
        t.setToTranslation(mx, my);
        return t;
    }

    public static AffineTransform getScaleInstance(double scx, double scY) {
        AffineTransform t = new AffineTransform();
        t.setToScale(scx, scY);
        return t;
    }

    public static AffineTransform getShearInstance(double shx, double shy) {
        AffineTransform m = new AffineTransform();
        m.setToShear(shx, shy);
        return m;
    }

    public static AffineTransform getRotateInstance(double angle) {
        AffineTransform t = new AffineTransform();
        t.setToRotation(angle);
        return t;
    }

    public static AffineTransform getRotateInstance(double angle, double x, double y) {
        AffineTransform t = new AffineTransform();
        t.setToRotation(angle, x, y);
        return t;
    }

    public void translate(double mx, double my) {
        concatenate(getTranslateInstance(mx, my));
    }

    public void scale(double scx, double scy) {
        concatenate(getScaleInstance(scx, scy));
    }

    public void shear(double shx, double shy) {
        concatenate(getShearInstance(shx, shy));
    }

    public void rotate(double angle) {
        concatenate(getRotateInstance(angle));
    }

    public void rotate(double angle, double px, double py) {
        concatenate(getRotateInstance(angle, px, py));
    }

    /* access modifiers changed from: package-private */
    public AffineTransform multiply(AffineTransform t1, AffineTransform t2) {
        AffineTransform affineTransform = t1;
        AffineTransform affineTransform2 = t2;
        double d = affineTransform.m00;
        double d2 = affineTransform2.m00;
        double d3 = affineTransform.m10;
        double d4 = affineTransform2.m01;
        double d5 = affineTransform2.m10;
        double d6 = (d * d2) + (d3 * d4);
        double d7 = affineTransform2.m11;
        double d8 = (d3 * d7) + (d * d5);
        double d9 = affineTransform.m01;
        double d10 = d8;
        double d11 = affineTransform.m11;
        double d12 = (d9 * d2) + (d11 * d4);
        double d13 = (d9 * d5) + (d11 * d7);
        double d14 = affineTransform.m02;
        double d15 = affineTransform.m12;
        return new AffineTransform(d6, d10, d12, d13, (d2 * d14) + (d4 * d15) + affineTransform2.m02, (d14 * d5) + (d15 * d7) + affineTransform2.m12);
    }

    public void concatenate(AffineTransform t) {
        setTransform(multiply(t, this));
    }

    public void preConcatenate(AffineTransform t) {
        setTransform(multiply(this, t));
    }

    public AffineTransform createInverse() throws NoninvertibleTransformException {
        double det = getDeterminant();
        if (Math.abs(det) >= ZERO) {
            double d = this.m11;
            double d2 = this.m10;
            double d3 = this.m01;
            double d4 = (-d2) / det;
            double d5 = (-d3) / det;
            double d6 = this.m00;
            double d7 = d6 / det;
            double d8 = this.m12;
            double d9 = d3 * d8;
            double d10 = d8;
            double d11 = this.m02;
            return new AffineTransform(d / det, d4, d5, d7, (d9 - (d * d11)) / det, ((d2 * d11) - (d6 * d10)) / det);
        }
        throw new NoninvertibleTransformException(NoninvertibleTransformException.DETERMINANT_IS_ZERO_CANNOT_INVERT_TRANSFORMATION);
    }

    public Point transform(Point src, Point dst) {
        if (dst == null) {
            dst = new Point();
        }
        double x = src.getX();
        double y = src.getY();
        dst.setLocation((this.m00 * x) + (this.m01 * y) + this.m02, (this.m10 * x) + (this.m11 * y) + this.m12);
        return dst;
    }

    public void transform(Point[] src, int srcOff, Point[] dst, int dstOff, int length) {
        int srcOff2 = srcOff;
        int dstOff2 = dstOff;
        int length2 = length;
        while (true) {
            length2--;
            if (length2 >= 0) {
                int srcOff3 = srcOff2 + 1;
                Point srcPoint = src[srcOff2];
                double x = srcPoint.getX();
                double y = srcPoint.getY();
                Point dstPoint = dst[dstOff2];
                if (dstPoint == null) {
                    dstPoint = new Point();
                }
                dstPoint.setLocation((this.m00 * x) + (this.m01 * y) + this.m02, (this.m10 * x) + (this.m11 * y) + this.m12);
                dst[dstOff2] = dstPoint;
                srcOff2 = srcOff3;
                dstOff2++;
            } else {
                return;
            }
        }
    }

    public void transform(double[] src, int srcOff, double[] dst, int dstOff, int length) {
        int step = 2;
        if (src == dst && srcOff < dstOff && dstOff < (length * 2) + srcOff) {
            srcOff = ((length * 2) + srcOff) - 2;
            dstOff = ((length * 2) + dstOff) - 2;
            step = -2;
        }
        while (true) {
            length--;
            if (length >= 0) {
                double x = src[srcOff + 0];
                double y = src[srcOff + 1];
                dst[dstOff + 0] = (this.m00 * x) + (this.m01 * y) + this.m02;
                dst[dstOff + 1] = (this.m10 * x) + (this.m11 * y) + this.m12;
                srcOff += step;
                dstOff += step;
            } else {
                return;
            }
        }
    }

    public void transform(float[] src, int srcOff, float[] dst, int dstOff, int length) {
        int step = 2;
        if (src == dst && srcOff < dstOff && dstOff < (length * 2) + srcOff) {
            srcOff = ((length * 2) + srcOff) - 2;
            dstOff = ((length * 2) + dstOff) - 2;
            step = -2;
        }
        while (true) {
            length--;
            if (length >= 0) {
                float x = src[srcOff + 0];
                float y = src[srcOff + 1];
                double d = (double) x;
                double d2 = this.m00;
                Double.isNaN(d);
                double d3 = d * d2;
                double d4 = (double) y;
                double d5 = this.m01;
                Double.isNaN(d4);
                dst[dstOff + 0] = (float) (d3 + (d4 * d5) + this.m02);
                double d6 = (double) x;
                double d7 = this.m10;
                Double.isNaN(d6);
                double d8 = d6 * d7;
                double d9 = (double) y;
                double d10 = this.m11;
                Double.isNaN(d9);
                dst[dstOff + 1] = (float) (d8 + (d9 * d10) + this.m12);
                srcOff += step;
                dstOff += step;
            } else {
                return;
            }
        }
    }

    public void transform(float[] src, int srcOff, double[] dst, int dstOff, int length) {
        while (true) {
            length--;
            if (length >= 0) {
                int srcOff2 = srcOff + 1;
                int srcOff3 = src[srcOff];
                int srcOff4 = srcOff2 + 1;
                int srcOff5 = src[srcOff2];
                int dstOff2 = dstOff + 1;
                double d = (double) srcOff3;
                double d2 = this.m00;
                Double.isNaN(d);
                double d3 = d * d2;
                double d4 = (double) srcOff5;
                double d5 = this.m01;
                Double.isNaN(d4);
                dst[dstOff] = d3 + (d4 * d5) + this.m02;
                dstOff = dstOff2 + 1;
                double d6 = (double) srcOff3;
                double d7 = this.m10;
                Double.isNaN(d6);
                double d8 = d6 * d7;
                double d9 = (double) srcOff5;
                double d10 = this.m11;
                Double.isNaN(d9);
                dst[dstOff2] = d8 + (d9 * d10) + this.m12;
                srcOff = srcOff4;
            } else {
                return;
            }
        }
    }

    public void transform(double[] src, int srcOff, float[] dst, int dstOff, int length) {
        while (true) {
            length--;
            if (length >= 0) {
                int srcOff2 = srcOff + 1;
                double x = src[srcOff];
                srcOff = srcOff2 + 1;
                double y = src[srcOff2];
                int dstOff2 = dstOff + 1;
                dst[dstOff] = (float) ((this.m00 * x) + (this.m01 * y) + this.m02);
                dstOff = dstOff2 + 1;
                dst[dstOff2] = (float) ((this.m10 * x) + (this.m11 * y) + this.m12);
            } else {
                return;
            }
        }
    }

    public Point deltaTransform(Point src, Point dst) {
        if (dst == null) {
            dst = new Point();
        }
        double x = src.getX();
        double y = src.getY();
        dst.setLocation((this.m00 * x) + (this.m01 * y), (this.m10 * x) + (this.m11 * y));
        return dst;
    }

    public void deltaTransform(double[] src, int srcOff, double[] dst, int dstOff, int length) {
        while (true) {
            length--;
            if (length >= 0) {
                int srcOff2 = srcOff + 1;
                double x = src[srcOff];
                srcOff = srcOff2 + 1;
                double y = src[srcOff2];
                int dstOff2 = dstOff + 1;
                dst[dstOff] = (this.m00 * x) + (this.m01 * y);
                dstOff = dstOff2 + 1;
                dst[dstOff2] = (this.m10 * x) + (this.m11 * y);
            } else {
                return;
            }
        }
    }

    public Point inverseTransform(Point src, Point dst) throws NoninvertibleTransformException {
        double det = getDeterminant();
        if (Math.abs(det) >= ZERO) {
            if (dst == null) {
                dst = new Point();
            }
            double x = src.getX() - this.m02;
            double y = src.getY() - this.m12;
            dst.setLocation(((this.m11 * x) - (this.m01 * y)) / det, ((this.m00 * y) - (this.m10 * x)) / det);
            return dst;
        }
        throw new NoninvertibleTransformException(NoninvertibleTransformException.DETERMINANT_IS_ZERO_CANNOT_INVERT_TRANSFORMATION);
    }

    public void inverseTransform(double[] src, int srcOff, double[] dst, int dstOff, int length) throws NoninvertibleTransformException {
        double det = getDeterminant();
        if (Math.abs(det) >= ZERO) {
            int srcOff2 = srcOff;
            int dstOff2 = dstOff;
            int length2 = length;
            while (true) {
                length2--;
                if (length2 >= 0) {
                    int srcOff3 = srcOff2 + 1;
                    double x = src[srcOff2] - this.m02;
                    srcOff2 = srcOff3 + 1;
                    double y = src[srcOff3] - this.m12;
                    int dstOff3 = dstOff2 + 1;
                    dst[dstOff2] = ((this.m11 * x) - (this.m01 * y)) / det;
                    dstOff2 = dstOff3 + 1;
                    dst[dstOff3] = ((this.m00 * y) - (this.m10 * x)) / det;
                } else {
                    return;
                }
            }
        } else {
            throw new NoninvertibleTransformException(NoninvertibleTransformException.DETERMINANT_IS_ZERO_CANNOT_INVERT_TRANSFORMATION);
        }
    }

    public void inverseTransform(float[] src, int srcOff, float[] dst, int dstOff, int length) throws NoninvertibleTransformException {
        float det = (float) getDeterminant();
        if (((double) Math.abs(det)) >= ZERO) {
            while (true) {
                length--;
                if (length >= 0) {
                    int srcOff2 = srcOff + 1;
                    double d = (double) src[srcOff];
                    double d2 = this.m02;
                    Double.isNaN(d);
                    float x = (float) (d - d2);
                    int srcOff3 = srcOff2 + 1;
                    double d3 = (double) src[srcOff2];
                    double d4 = this.m12;
                    Double.isNaN(d3);
                    float y = (float) (d3 - d4);
                    int dstOff2 = dstOff + 1;
                    double d5 = (double) x;
                    double d6 = this.m11;
                    Double.isNaN(d5);
                    double d7 = d5 * d6;
                    double d8 = (double) y;
                    double d9 = this.m01;
                    Double.isNaN(d8);
                    double d10 = d7 - (d8 * d9);
                    double d11 = (double) det;
                    Double.isNaN(d11);
                    dst[dstOff] = (float) (d10 / d11);
                    dstOff = dstOff2 + 1;
                    double d12 = (double) y;
                    double d13 = this.m00;
                    Double.isNaN(d12);
                    double d14 = d12 * d13;
                    double d15 = (double) x;
                    double d16 = this.m10;
                    Double.isNaN(d15);
                    double d17 = d14 - (d15 * d16);
                    double d18 = (double) det;
                    Double.isNaN(d18);
                    dst[dstOff2] = (float) (d17 / d18);
                    srcOff = srcOff3;
                } else {
                    return;
                }
            }
        } else {
            throw new NoninvertibleTransformException(NoninvertibleTransformException.DETERMINANT_IS_ZERO_CANNOT_INVERT_TRANSFORMATION);
        }
    }

    public AffineTransform clone() throws CloneNotSupportedException {
        return (AffineTransform) super.clone();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AffineTransform that = (AffineTransform) o;
        if (Double.compare(that.m00, this.m00) == 0 && Double.compare(that.m10, this.m10) == 0 && Double.compare(that.m01, this.m01) == 0 && Double.compare(that.m11, this.m11) == 0 && Double.compare(that.m02, this.m02) == 0 && Double.compare(that.m12, this.m12) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{Double.valueOf(this.m00), Double.valueOf(this.m10), Double.valueOf(this.m01), Double.valueOf(this.m11), Double.valueOf(this.m02), Double.valueOf(this.m12)});
    }
}
