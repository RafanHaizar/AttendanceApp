package com.google.android.material.shape;

import android.graphics.RectF;
import java.util.Arrays;

public final class AdjustedCornerSize implements CornerSize {
    private final float adjustment;
    private final CornerSize other;

    public AdjustedCornerSize(float adjustment2, CornerSize other2) {
        while (other2 instanceof AdjustedCornerSize) {
            other2 = ((AdjustedCornerSize) other2).other;
            adjustment2 += ((AdjustedCornerSize) other2).adjustment;
        }
        this.other = other2;
        this.adjustment = adjustment2;
    }

    public float getCornerSize(RectF bounds) {
        return Math.max(0.0f, this.other.getCornerSize(bounds) + this.adjustment);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdjustedCornerSize)) {
            return false;
        }
        AdjustedCornerSize that = (AdjustedCornerSize) o;
        if (!this.other.equals(that.other) || this.adjustment != that.adjustment) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.other, Float.valueOf(this.adjustment)});
    }
}
