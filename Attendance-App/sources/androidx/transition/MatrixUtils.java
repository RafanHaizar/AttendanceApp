package androidx.transition;

import android.graphics.Matrix;
import android.graphics.RectF;

class MatrixUtils {
    static final Matrix IDENTITY_MATRIX = new Matrix() {
        /* access modifiers changed from: package-private */
        public void oops() {
            throw new IllegalStateException("Matrix can not be modified");
        }

        public void set(Matrix src) {
            oops();
        }

        public void reset() {
            oops();
        }

        public void setTranslate(float dx, float dy) {
            oops();
        }

        public void setScale(float sx, float sy, float px, float py) {
            oops();
        }

        public void setScale(float sx, float sy) {
            oops();
        }

        public void setRotate(float degrees, float px, float py) {
            oops();
        }

        public void setRotate(float degrees) {
            oops();
        }

        public void setSinCos(float sinValue, float cosValue, float px, float py) {
            oops();
        }

        public void setSinCos(float sinValue, float cosValue) {
            oops();
        }

        public void setSkew(float kx, float ky, float px, float py) {
            oops();
        }

        public void setSkew(float kx, float ky) {
            oops();
        }

        public boolean setConcat(Matrix a, Matrix b) {
            oops();
            return false;
        }

        public boolean preTranslate(float dx, float dy) {
            oops();
            return false;
        }

        public boolean preScale(float sx, float sy, float px, float py) {
            oops();
            return false;
        }

        public boolean preScale(float sx, float sy) {
            oops();
            return false;
        }

        public boolean preRotate(float degrees, float px, float py) {
            oops();
            return false;
        }

        public boolean preRotate(float degrees) {
            oops();
            return false;
        }

        public boolean preSkew(float kx, float ky, float px, float py) {
            oops();
            return false;
        }

        public boolean preSkew(float kx, float ky) {
            oops();
            return false;
        }

        public boolean preConcat(Matrix other) {
            oops();
            return false;
        }

        public boolean postTranslate(float dx, float dy) {
            oops();
            return false;
        }

        public boolean postScale(float sx, float sy, float px, float py) {
            oops();
            return false;
        }

        public boolean postScale(float sx, float sy) {
            oops();
            return false;
        }

        public boolean postRotate(float degrees, float px, float py) {
            oops();
            return false;
        }

        public boolean postRotate(float degrees) {
            oops();
            return false;
        }

        public boolean postSkew(float kx, float ky, float px, float py) {
            oops();
            return false;
        }

        public boolean postSkew(float kx, float ky) {
            oops();
            return false;
        }

        public boolean postConcat(Matrix other) {
            oops();
            return false;
        }

        public boolean setRectToRect(RectF src, RectF dst, Matrix.ScaleToFit stf) {
            oops();
            return false;
        }

        public boolean setPolyToPoly(float[] src, int srcIndex, float[] dst, int dstIndex, int pointCount) {
            oops();
            return false;
        }

        public void setValues(float[] values) {
            oops();
        }
    };

    private MatrixUtils() {
    }
}
