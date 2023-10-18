package androidx.transition;

import android.animation.TypeEvaluator;

class FloatArrayEvaluator implements TypeEvaluator<float[]> {
    private float[] mArray;

    FloatArrayEvaluator(float[] reuseArray) {
        this.mArray = reuseArray;
    }

    public float[] evaluate(float fraction, float[] startValue, float[] endValue) {
        float[] array = this.mArray;
        if (array == null) {
            array = new float[startValue.length];
        }
        for (int i = 0; i < array.length; i++) {
            float start = startValue[i];
            array[i] = ((endValue[i] - start) * fraction) + start;
        }
        return array;
    }
}
