package androidx.constraintlayout.core.motion.utils;

import androidx.constraintlayout.core.motion.CustomAttribute;
import androidx.constraintlayout.core.motion.CustomVariable;
import androidx.constraintlayout.core.motion.MotionWidget;
import androidx.constraintlayout.core.motion.utils.KeyFrameArray;
import java.lang.reflect.Array;
import java.text.DecimalFormat;

public abstract class TimeCycleSplineSet {
    protected static final int CURVE_OFFSET = 2;
    protected static final int CURVE_PERIOD = 1;
    protected static final int CURVE_VALUE = 0;
    private static final String TAG = "SplineSet";
    protected static float VAL_2PI = 6.2831855f;
    protected int count;
    protected float last_cycle = Float.NaN;
    protected long last_time;
    protected float[] mCache = new float[3];
    protected boolean mContinue = false;
    protected CurveFit mCurveFit;
    protected int[] mTimePoints = new int[10];
    protected String mType;
    protected float[][] mValues = ((float[][]) Array.newInstance(Float.TYPE, new int[]{10, 3}));
    protected int mWaveShape = 0;

    public String toString() {
        String str = this.mType;
        DecimalFormat df = new DecimalFormat("##.##");
        for (int i = 0; i < this.count; i++) {
            str = str + "[" + this.mTimePoints[i] + " , " + df.format(this.mValues[i]) + "] ";
        }
        return str;
    }

    public void setType(String type) {
        this.mType = type;
    }

    /* access modifiers changed from: protected */
    public float calcWave(float period) {
        float p = period;
        switch (this.mWaveShape) {
            case 1:
                return Math.signum(VAL_2PI * p);
            case 2:
                return 1.0f - Math.abs(p);
            case 3:
                return (((p * 2.0f) + 1.0f) % 2.0f) - 1.0f;
            case 4:
                return 1.0f - (((p * 2.0f) + 1.0f) % 2.0f);
            case 5:
                return (float) Math.cos((double) (VAL_2PI * p));
            case 6:
                float x = 1.0f - Math.abs(((p * 4.0f) % 4.0f) - 2.0f);
                return 1.0f - (x * x);
            default:
                return (float) Math.sin((double) (VAL_2PI * p));
        }
    }

    public CurveFit getCurveFit() {
        return this.mCurveFit;
    }

    /* access modifiers changed from: protected */
    public void setStartTime(long currentTime) {
        this.last_time = currentTime;
    }

    public void setPoint(int position, float value, float period, int shape, float offset) {
        int[] iArr = this.mTimePoints;
        int i = this.count;
        iArr[i] = position;
        float[] fArr = this.mValues[i];
        fArr[0] = value;
        fArr[1] = period;
        fArr[2] = offset;
        this.mWaveShape = Math.max(this.mWaveShape, shape);
        this.count++;
    }

    public static class CustomSet extends TimeCycleSplineSet {
        String mAttributeName;
        float[] mCache;
        KeyFrameArray.CustomArray mConstraintAttributeList;
        float[] mTempValues;
        KeyFrameArray.FloatArray mWaveProperties = new KeyFrameArray.FloatArray();

        public CustomSet(String attribute, KeyFrameArray.CustomArray attrList) {
            this.mAttributeName = attribute.split(",")[1];
            this.mConstraintAttributeList = attrList;
        }

        public void setup(int curveType) {
            int size = this.mConstraintAttributeList.size();
            int dimensionality = this.mConstraintAttributeList.valueAt(0).numberOfInterpolatedValues();
            double[] time = new double[size];
            this.mTempValues = new float[(dimensionality + 2)];
            this.mCache = new float[dimensionality];
            int[] iArr = new int[2];
            iArr[1] = dimensionality + 2;
            iArr[0] = size;
            double[][] values = (double[][]) Array.newInstance(Double.TYPE, iArr);
            for (int i = 0; i < size; i++) {
                int key = this.mConstraintAttributeList.keyAt(i);
                CustomAttribute ca = this.mConstraintAttributeList.valueAt(i);
                float[] waveProp = this.mWaveProperties.valueAt(i);
                double d = (double) key;
                Double.isNaN(d);
                time[i] = d * 0.01d;
                ca.getValuesToInterpolate(this.mTempValues);
                int k = 0;
                while (true) {
                    float[] fArr = this.mTempValues;
                    if (k >= fArr.length) {
                        break;
                    }
                    values[i][k] = (double) fArr[k];
                    k++;
                }
                values[i][dimensionality] = (double) waveProp[0];
                values[i][dimensionality + 1] = (double) waveProp[1];
            }
            this.mCurveFit = CurveFit.get(curveType, time, values);
        }

        public void setPoint(int position, float value, float period, int shape, float offset) {
            throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute,...)");
        }

        public void setPoint(int position, CustomAttribute value, float period, int shape, float offset) {
            this.mConstraintAttributeList.append(position, value);
            this.mWaveProperties.append(position, new float[]{period, offset});
            this.mWaveShape = Math.max(this.mWaveShape, shape);
        }

        public boolean setProperty(MotionWidget view, float t, long time, KeyCache cache) {
            MotionWidget motionWidget = view;
            long j = time;
            this.mCurveFit.getPos((double) t, this.mTempValues);
            float[] fArr = this.mTempValues;
            float period = fArr[fArr.length - 2];
            float offset = fArr[fArr.length - 1];
            long delta_time = j - this.last_time;
            if (Float.isNaN(this.last_cycle)) {
                this.last_cycle = cache.getFloatValue(motionWidget, this.mAttributeName, 0);
                if (Float.isNaN(this.last_cycle)) {
                    this.last_cycle = 0.0f;
                }
            } else {
                KeyCache keyCache = cache;
            }
            double d = (double) this.last_cycle;
            double d2 = (double) delta_time;
            Double.isNaN(d2);
            double d3 = (double) period;
            Double.isNaN(d3);
            Double.isNaN(d);
            this.last_cycle = (float) ((d + ((d2 * 1.0E-9d) * d3)) % 1.0d);
            this.last_time = j;
            float wave = calcWave(this.last_cycle);
            this.mContinue = false;
            for (int i = 0; i < this.mCache.length; i++) {
                this.mContinue |= ((double) this.mTempValues[i]) != 0.0d;
                this.mCache[i] = (this.mTempValues[i] * wave) + offset;
            }
            motionWidget.setInterpolatedValue(this.mConstraintAttributeList.valueAt(0), this.mCache);
            if (period != 0.0f) {
                this.mContinue = true;
            }
            return this.mContinue;
        }
    }

    public void setup(int curveType) {
        int i = this.count;
        if (i == 0) {
            System.err.println("Error no points added to " + this.mType);
            return;
        }
        Sort.doubleQuickSort(this.mTimePoints, this.mValues, 0, i - 1);
        int unique = 0;
        int i2 = 1;
        while (true) {
            int[] iArr = this.mTimePoints;
            if (i2 >= iArr.length) {
                break;
            }
            if (iArr[i2] != iArr[i2 - 1]) {
                unique++;
            }
            i2++;
        }
        if (unique == 0) {
            unique = 1;
        }
        double[] time = new double[unique];
        int[] iArr2 = new int[2];
        iArr2[1] = 3;
        iArr2[0] = unique;
        double[][] values = (double[][]) Array.newInstance(Double.TYPE, iArr2);
        int k = 0;
        for (int i3 = 0; i3 < this.count; i3++) {
            if (i3 > 0) {
                int[] iArr3 = this.mTimePoints;
                if (iArr3[i3] == iArr3[i3 - 1]) {
                }
            }
            double d = (double) this.mTimePoints[i3];
            Double.isNaN(d);
            time[k] = d * 0.01d;
            double[] dArr = values[k];
            float[] fArr = this.mValues[i3];
            dArr[0] = (double) fArr[0];
            values[k][1] = (double) fArr[1];
            values[k][2] = (double) fArr[2];
            k++;
        }
        this.mCurveFit = CurveFit.get(curveType, time, values);
    }

    protected static class Sort {
        protected Sort() {
        }

        static void doubleQuickSort(int[] key, float[][] value, int low, int hi) {
            int[] stack = new int[(key.length + 10)];
            int count = 0 + 1;
            stack[0] = hi;
            int count2 = count + 1;
            stack[count] = low;
            while (count2 > 0) {
                int count3 = count2 - 1;
                int low2 = stack[count3];
                count2 = count3 - 1;
                int hi2 = stack[count2];
                if (low2 < hi2) {
                    int p = partition(key, value, low2, hi2);
                    int count4 = count2 + 1;
                    stack[count2] = p - 1;
                    int count5 = count4 + 1;
                    stack[count4] = low2;
                    int count6 = count5 + 1;
                    stack[count5] = hi2;
                    count2 = count6 + 1;
                    stack[count6] = p + 1;
                }
            }
        }

        private static int partition(int[] array, float[][] value, int low, int hi) {
            int pivot = array[hi];
            int i = low;
            for (int j = low; j < hi; j++) {
                if (array[j] <= pivot) {
                    swap(array, value, i, j);
                    i++;
                }
            }
            swap(array, value, i, hi);
            return i;
        }

        private static void swap(int[] array, float[][] value, int a, int b) {
            int tmp = array[a];
            array[a] = array[b];
            array[b] = tmp;
            float[] tmpv = value[a];
            value[a] = value[b];
            value[b] = tmpv;
        }
    }

    public static class CustomVarSet extends TimeCycleSplineSet {
        String mAttributeName;
        float[] mCache;
        KeyFrameArray.CustomVar mConstraintAttributeList;
        float[] mTempValues;
        KeyFrameArray.FloatArray mWaveProperties = new KeyFrameArray.FloatArray();

        public CustomVarSet(String attribute, KeyFrameArray.CustomVar attrList) {
            this.mAttributeName = attribute.split(",")[1];
            this.mConstraintAttributeList = attrList;
        }

        public void setup(int curveType) {
            int size = this.mConstraintAttributeList.size();
            int dimensionality = this.mConstraintAttributeList.valueAt(0).numberOfInterpolatedValues();
            double[] time = new double[size];
            this.mTempValues = new float[(dimensionality + 2)];
            this.mCache = new float[dimensionality];
            int[] iArr = new int[2];
            iArr[1] = dimensionality + 2;
            iArr[0] = size;
            double[][] values = (double[][]) Array.newInstance(Double.TYPE, iArr);
            for (int i = 0; i < size; i++) {
                int key = this.mConstraintAttributeList.keyAt(i);
                CustomVariable ca = this.mConstraintAttributeList.valueAt(i);
                float[] waveProp = this.mWaveProperties.valueAt(i);
                double d = (double) key;
                Double.isNaN(d);
                time[i] = d * 0.01d;
                ca.getValuesToInterpolate(this.mTempValues);
                int k = 0;
                while (true) {
                    float[] fArr = this.mTempValues;
                    if (k >= fArr.length) {
                        break;
                    }
                    values[i][k] = (double) fArr[k];
                    k++;
                }
                values[i][dimensionality] = (double) waveProp[0];
                values[i][dimensionality + 1] = (double) waveProp[1];
            }
            this.mCurveFit = CurveFit.get(curveType, time, values);
        }

        public void setPoint(int position, float value, float period, int shape, float offset) {
            throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute,...)");
        }

        public void setPoint(int position, CustomVariable value, float period, int shape, float offset) {
            this.mConstraintAttributeList.append(position, value);
            this.mWaveProperties.append(position, new float[]{period, offset});
            this.mWaveShape = Math.max(this.mWaveShape, shape);
        }

        public boolean setProperty(MotionWidget view, float t, long time, KeyCache cache) {
            MotionWidget motionWidget = view;
            long j = time;
            this.mCurveFit.getPos((double) t, this.mTempValues);
            float[] fArr = this.mTempValues;
            float period = fArr[fArr.length - 2];
            float offset = fArr[fArr.length - 1];
            long delta_time = j - this.last_time;
            if (Float.isNaN(this.last_cycle)) {
                this.last_cycle = cache.getFloatValue(motionWidget, this.mAttributeName, 0);
                if (Float.isNaN(this.last_cycle)) {
                    this.last_cycle = 0.0f;
                }
            } else {
                KeyCache keyCache = cache;
            }
            double d = (double) this.last_cycle;
            double d2 = (double) delta_time;
            Double.isNaN(d2);
            double d3 = (double) period;
            Double.isNaN(d3);
            Double.isNaN(d);
            this.last_cycle = (float) ((d + ((d2 * 1.0E-9d) * d3)) % 1.0d);
            this.last_time = j;
            float wave = calcWave(this.last_cycle);
            this.mContinue = false;
            for (int i = 0; i < this.mCache.length; i++) {
                this.mContinue |= ((double) this.mTempValues[i]) != 0.0d;
                this.mCache[i] = (this.mTempValues[i] * wave) + offset;
            }
            this.mConstraintAttributeList.valueAt(0).setInterpolatedValue(motionWidget, this.mCache);
            if (period != 0.0f) {
                this.mContinue = true;
            }
            return this.mContinue;
        }
    }
}
