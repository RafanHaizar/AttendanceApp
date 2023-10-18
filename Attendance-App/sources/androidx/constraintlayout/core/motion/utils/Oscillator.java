package androidx.constraintlayout.core.motion.utils;

import java.util.Arrays;

public class Oscillator {
    public static final int BOUNCE = 6;
    public static final int COS_WAVE = 5;
    public static final int CUSTOM = 7;
    public static final int REVERSE_SAW_WAVE = 4;
    public static final int SAW_WAVE = 3;
    public static final int SIN_WAVE = 0;
    public static final int SQUARE_WAVE = 1;
    public static String TAG = "Oscillator";
    public static final int TRIANGLE_WAVE = 2;
    double PI2 = 6.283185307179586d;
    double[] mArea;
    MonotonicCurveFit mCustomCurve;
    String mCustomType;
    private boolean mNormalized = false;
    float[] mPeriod = new float[0];
    double[] mPosition = new double[0];
    int mType;

    public String toString() {
        return "pos =" + Arrays.toString(this.mPosition) + " period=" + Arrays.toString(this.mPeriod);
    }

    public void setType(int type, String customType) {
        this.mType = type;
        this.mCustomType = customType;
        if (customType != null) {
            this.mCustomCurve = MonotonicCurveFit.buildWave(customType);
        }
    }

    public void addPoint(double position, float period) {
        int len = this.mPeriod.length + 1;
        int j = Arrays.binarySearch(this.mPosition, position);
        if (j < 0) {
            j = (-j) - 1;
        }
        this.mPosition = Arrays.copyOf(this.mPosition, len);
        this.mPeriod = Arrays.copyOf(this.mPeriod, len);
        this.mArea = new double[len];
        double[] dArr = this.mPosition;
        System.arraycopy(dArr, j, dArr, j + 1, (len - j) - 1);
        this.mPosition[j] = position;
        this.mPeriod[j] = period;
        this.mNormalized = false;
    }

    public void normalize() {
        double totalArea = 0.0d;
        double totalCount = 0.0d;
        int i = 0;
        while (true) {
            float[] fArr = this.mPeriod;
            if (i >= fArr.length) {
                break;
            }
            double d = (double) fArr[i];
            Double.isNaN(d);
            totalCount += d;
            i++;
        }
        int i2 = 1;
        while (true) {
            float[] fArr2 = this.mPeriod;
            if (i2 >= fArr2.length) {
                break;
            }
            double[] dArr = this.mPosition;
            double w = dArr[i2] - dArr[i2 - 1];
            double d2 = (double) ((fArr2[i2 - 1] + fArr2[i2]) / 2.0f);
            Double.isNaN(d2);
            totalArea += d2 * w;
            i2++;
        }
        int i3 = 0;
        while (true) {
            float[] fArr3 = this.mPeriod;
            if (i3 >= fArr3.length) {
                break;
            }
            double d3 = (double) fArr3[i3];
            Double.isNaN(d3);
            fArr3[i3] = (float) (d3 * (totalCount / totalArea));
            i3++;
        }
        this.mArea[0] = 0.0d;
        int i4 = 1;
        while (true) {
            float[] fArr4 = this.mPeriod;
            if (i4 < fArr4.length) {
                double[] dArr2 = this.mPosition;
                double w2 = dArr2[i4] - dArr2[i4 - 1];
                double[] dArr3 = this.mArea;
                double d4 = dArr3[i4 - 1];
                double d5 = (double) ((fArr4[i4 - 1] + fArr4[i4]) / 2.0f);
                Double.isNaN(d5);
                dArr3[i4] = d4 + (d5 * w2);
                i4++;
            } else {
                this.mNormalized = true;
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public double getP(double time) {
        double time2;
        if (time < 0.0d) {
            time2 = 0.0d;
        } else if (time > 1.0d) {
            time2 = 1.0d;
        } else {
            time2 = time;
        }
        int index = Arrays.binarySearch(this.mPosition, time2);
        if (index > 0) {
            double d = time2;
            return 1.0d;
        } else if (index != 0) {
            int index2 = (-index) - 1;
            double t = time2;
            float[] fArr = this.mPeriod;
            double d2 = (double) (fArr[index2] - fArr[index2 - 1]);
            double[] dArr = this.mPosition;
            Double.isNaN(d2);
            double m = d2 / (dArr[index2] - dArr[index2 - 1]);
            double d3 = this.mArea[index2 - 1];
            double d4 = time2;
            double d5 = (double) fArr[index2 - 1];
            Double.isNaN(d5);
            return d3 + ((d5 - (dArr[index2 - 1] * m)) * (t - dArr[index2 - 1])) + ((((t * t) - (dArr[index2 - 1] * dArr[index2 - 1])) * m) / 2.0d);
        } else {
            return 0.0d;
        }
    }

    public double getValue(double time, double phase) {
        double angle = getP(time) + phase;
        switch (this.mType) {
            case 1:
                return Math.signum(0.5d - (angle % 1.0d));
            case 2:
                return 1.0d - Math.abs((((angle * 4.0d) + 1.0d) % 4.0d) - 2.0d);
            case 3:
                return (((angle * 2.0d) + 1.0d) % 2.0d) - 1.0d;
            case 4:
                return 1.0d - (((angle * 2.0d) + 1.0d) % 2.0d);
            case 5:
                return Math.cos(this.PI2 * (phase + angle));
            case 6:
                double x = 1.0d - Math.abs(((angle * 4.0d) % 4.0d) - 2.0d);
                return 1.0d - (x * x);
            case 7:
                return this.mCustomCurve.getPos(angle % 1.0d, 0);
            default:
                return Math.sin(this.PI2 * angle);
        }
    }

    /* access modifiers changed from: package-private */
    public double getDP(double time) {
        double time2;
        if (time <= 0.0d) {
            time2 = 1.0E-5d;
        } else if (time >= 1.0d) {
            time2 = 0.999999d;
        } else {
            time2 = time;
        }
        int index = Arrays.binarySearch(this.mPosition, time2);
        if (index > 0 || index == 0) {
            return 0.0d;
        }
        int index2 = (-index) - 1;
        float[] fArr = this.mPeriod;
        double d = (double) (fArr[index2] - fArr[index2 - 1]);
        double[] dArr = this.mPosition;
        Double.isNaN(d);
        double m = d / (dArr[index2] - dArr[index2 - 1]);
        double d2 = (double) fArr[index2 - 1];
        Double.isNaN(d2);
        return (m * time2) + (d2 - (dArr[index2 - 1] * m));
    }

    public double getSlope(double time, double phase, double dphase) {
        double angle = phase + getP(time);
        double dangle_dtime = getDP(time) + dphase;
        switch (this.mType) {
            case 1:
                return 0.0d;
            case 2:
                return dangle_dtime * 4.0d * Math.signum((((angle * 4.0d) + 3.0d) % 4.0d) - 2.0d);
            case 3:
                return 2.0d * dangle_dtime;
            case 4:
                return (-dangle_dtime) * 2.0d;
            case 5:
                double d = this.PI2;
                return (-d) * dangle_dtime * Math.sin(d * angle);
            case 6:
                return dangle_dtime * 4.0d * ((((angle * 4.0d) + 2.0d) % 4.0d) - 2.0d);
            case 7:
                return this.mCustomCurve.getSlope(angle % 1.0d, 0);
            default:
                double d2 = this.PI2;
                return d2 * dangle_dtime * Math.cos(d2 * angle);
        }
    }
}
