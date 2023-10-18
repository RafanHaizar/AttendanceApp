package androidx.interpolator.view.animation;

import android.view.animation.Interpolator;

abstract class LookupTableInterpolator implements Interpolator {
    private final float mStepSize;
    private final float[] mValues;

    protected LookupTableInterpolator(float[] values) {
        this.mValues = values;
        this.mStepSize = 1.0f / ((float) (values.length - 1));
    }

    public float getInterpolation(float input) {
        if (input >= 1.0f) {
            return 1.0f;
        }
        if (input <= 0.0f) {
            return 0.0f;
        }
        float[] fArr = this.mValues;
        int position = Math.min((int) (((float) (fArr.length - 1)) * input), fArr.length - 2);
        float f = this.mStepSize;
        float[] fArr2 = this.mValues;
        float f2 = fArr2[position];
        return f2 + ((fArr2[position + 1] - f2) * ((input - (((float) position) * f)) / f));
    }
}
