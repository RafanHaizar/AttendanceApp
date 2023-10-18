package androidx.constraintlayout.core.motion.utils;

public class LinearCurveFit extends CurveFit {
    private static final String TAG = "LinearCurveFit";
    private boolean mExtrapolate = true;
    double[] mSlopeTemp;

    /* renamed from: mT */
    private double[] f994mT;
    private double mTotalLength = Double.NaN;

    /* renamed from: mY */
    private double[][] f995mY;

    public LinearCurveFit(double[] time, double[][] y) {
        double px;
        int dim;
        double[] dArr = time;
        double[][] dArr2 = y;
        int length = dArr.length;
        char c = 0;
        int dim2 = dArr2[0].length;
        this.mSlopeTemp = new double[dim2];
        this.f994mT = dArr;
        this.f995mY = dArr2;
        if (dim2 > 2) {
            double sum = 0.0d;
            double lastx = 0.0d;
            double lasty = 0.0d;
            int i = 0;
            while (i < dArr.length) {
                double px2 = dArr2[i][c];
                double py = dArr2[i][c];
                if (i > 0) {
                    dim = dim2;
                    px = px2;
                    sum += Math.hypot(px2 - lastx, py - lasty);
                } else {
                    dim = dim2;
                    px = px2;
                }
                lastx = px;
                lasty = py;
                i++;
                dim2 = dim;
                c = 0;
            }
            this.mTotalLength = 0.0d;
            return;
        }
    }

    private double getLength2D(double t) {
        if (Double.isNaN(this.mTotalLength)) {
            return 0.0d;
        }
        double[] dArr = this.f994mT;
        int n = dArr.length;
        if (t <= dArr[0]) {
            return 0.0d;
        }
        if (t >= dArr[n - 1]) {
            return this.mTotalLength;
        }
        double sum = 0.0d;
        double last_x = 0.0d;
        double last_y = 0.0d;
        for (int i = 0; i < n - 1; i++) {
            double[] dArr2 = this.f995mY[i];
            double px = dArr2[0];
            double py = dArr2[1];
            if (i > 0) {
                double d = last_x;
                sum += Math.hypot(px - last_x, py - last_y);
            }
            last_x = px;
            last_y = py;
            double[] dArr3 = this.f994mT;
            double d2 = dArr3[i];
            if (t == d2) {
                return sum;
            }
            if (t < dArr3[i + 1]) {
                double x = (t - d2) / (dArr3[i + 1] - d2);
                double[][] dArr4 = this.f995mY;
                double[] dArr5 = dArr4[i];
                double x1 = dArr5[0];
                double x2 = dArr4[i + 1][0];
                int i2 = n;
                return sum + Math.hypot(py - (((1.0d - x) * dArr5[1]) + (dArr4[i + 1][1] * x)), px - (((1.0d - x) * x1) + (x2 * x)));
            }
        }
        return 0.0d;
    }

    public void getPos(double t, double[] v) {
        double[] dArr = this.f994mT;
        int n = dArr.length;
        int dim = this.f995mY[0].length;
        if (this.mExtrapolate) {
            double d = dArr[0];
            if (t <= d) {
                getSlope(d, this.mSlopeTemp);
                for (int j = 0; j < dim; j++) {
                    v[j] = this.f995mY[0][j] + ((t - this.f994mT[0]) * this.mSlopeTemp[j]);
                }
                return;
            } else if (t >= dArr[n - 1]) {
                getSlope(dArr[n - 1], this.mSlopeTemp);
                for (int j2 = 0; j2 < dim; j2++) {
                    v[j2] = this.f995mY[n - 1][j2] + ((t - this.f994mT[n - 1]) * this.mSlopeTemp[j2]);
                }
                return;
            }
        } else if (t <= dArr[0]) {
            for (int j3 = 0; j3 < dim; j3++) {
                v[j3] = this.f995mY[0][j3];
            }
            return;
        } else if (t >= dArr[n - 1]) {
            for (int j4 = 0; j4 < dim; j4++) {
                v[j4] = this.f995mY[n - 1][j4];
            }
            return;
        }
        for (int i = 0; i < n - 1; i++) {
            if (t == this.f994mT[i]) {
                for (int j5 = 0; j5 < dim; j5++) {
                    v[j5] = this.f995mY[i][j5];
                }
            }
            double[] dArr2 = this.f994mT;
            if (t < dArr2[i + 1]) {
                double d2 = dArr2[i + 1];
                double d3 = dArr2[i];
                double x = (t - d3) / (d2 - d3);
                for (int j6 = 0; j6 < dim; j6++) {
                    double[][] dArr3 = this.f995mY;
                    v[j6] = ((1.0d - x) * dArr3[i][j6]) + (dArr3[i + 1][j6] * x);
                }
                return;
            }
        }
    }

    public void getPos(double t, float[] v) {
        double[] dArr = this.f994mT;
        int n = dArr.length;
        int dim = this.f995mY[0].length;
        if (this.mExtrapolate) {
            double d = dArr[0];
            if (t <= d) {
                getSlope(d, this.mSlopeTemp);
                for (int j = 0; j < dim; j++) {
                    v[j] = (float) (this.f995mY[0][j] + ((t - this.f994mT[0]) * this.mSlopeTemp[j]));
                }
                return;
            } else if (t >= dArr[n - 1]) {
                getSlope(dArr[n - 1], this.mSlopeTemp);
                for (int j2 = 0; j2 < dim; j2++) {
                    v[j2] = (float) (this.f995mY[n - 1][j2] + ((t - this.f994mT[n - 1]) * this.mSlopeTemp[j2]));
                }
                return;
            }
        } else if (t <= dArr[0]) {
            for (int j3 = 0; j3 < dim; j3++) {
                v[j3] = (float) this.f995mY[0][j3];
            }
            return;
        } else if (t >= dArr[n - 1]) {
            for (int j4 = 0; j4 < dim; j4++) {
                v[j4] = (float) this.f995mY[n - 1][j4];
            }
            return;
        }
        for (int i = 0; i < n - 1; i++) {
            if (t == this.f994mT[i]) {
                for (int j5 = 0; j5 < dim; j5++) {
                    v[j5] = (float) this.f995mY[i][j5];
                }
            }
            double[] dArr2 = this.f994mT;
            if (t < dArr2[i + 1]) {
                double d2 = dArr2[i + 1];
                double d3 = dArr2[i];
                double x = (t - d3) / (d2 - d3);
                for (int j6 = 0; j6 < dim; j6++) {
                    double[][] dArr3 = this.f995mY;
                    v[j6] = (float) (((1.0d - x) * dArr3[i][j6]) + (dArr3[i + 1][j6] * x));
                }
                return;
            }
        }
    }

    public double getPos(double t, int j) {
        int i = j;
        double[] dArr = this.f994mT;
        int n = dArr.length;
        if (this.mExtrapolate) {
            double d = dArr[0];
            if (t <= d) {
                return this.f995mY[0][i] + ((t - d) * getSlope(d, i));
            }
            if (t >= dArr[n - 1]) {
                return this.f995mY[n - 1][i] + ((t - dArr[n - 1]) * getSlope(dArr[n - 1], i));
            }
        } else if (t <= dArr[0]) {
            return this.f995mY[0][i];
        } else {
            if (t >= dArr[n - 1]) {
                return this.f995mY[n - 1][i];
            }
        }
        for (int i2 = 0; i2 < n - 1; i2++) {
            double[] dArr2 = this.f994mT;
            double d2 = dArr2[i2];
            if (t == d2) {
                return this.f995mY[i2][i];
            }
            if (t < dArr2[i2 + 1]) {
                double x = (t - d2) / (dArr2[i2 + 1] - d2);
                double[][] dArr3 = this.f995mY;
                return ((1.0d - x) * dArr3[i2][i]) + (dArr3[i2 + 1][i] * x);
            }
        }
        return 0.0d;
    }

    public void getSlope(double t, double[] v) {
        double t2;
        double[] dArr = this.f994mT;
        int n = dArr.length;
        int dim = this.f995mY[0].length;
        if (t <= dArr[0]) {
            t2 = dArr[0];
        } else if (t >= dArr[n - 1]) {
            t2 = dArr[n - 1];
        } else {
            t2 = t;
        }
        for (int i = 0; i < n - 1; i++) {
            double[] dArr2 = this.f994mT;
            if (t2 <= dArr2[i + 1]) {
                double d = dArr2[i + 1];
                double d2 = dArr2[i];
                double h = d - d2;
                double d3 = (t2 - d2) / h;
                for (int j = 0; j < dim; j++) {
                    double[][] dArr3 = this.f995mY;
                    v[j] = (dArr3[i + 1][j] - dArr3[i][j]) / h;
                }
                return;
            }
        }
    }

    public double getSlope(double t, int j) {
        double t2;
        double[] dArr = this.f994mT;
        int n = dArr.length;
        if (t < dArr[0]) {
            t2 = dArr[0];
        } else if (t >= dArr[n - 1]) {
            t2 = dArr[n - 1];
        } else {
            t2 = t;
        }
        for (int i = 0; i < n - 1; i++) {
            double[] dArr2 = this.f994mT;
            if (t2 <= dArr2[i + 1]) {
                double d = dArr2[i + 1];
                double d2 = dArr2[i];
                double h = d - d2;
                double d3 = (t2 - d2) / h;
                double[][] dArr3 = this.f995mY;
                return (dArr3[i + 1][j] - dArr3[i][j]) / h;
            }
        }
        return 0.0d;
    }

    public double[] getTimePoints() {
        return this.f994mT;
    }
}
