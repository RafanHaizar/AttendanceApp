package com.google.android.material.shape;

import android.graphics.RectF;
import java.util.Arrays;

public final class AbsoluteCornerSize implements CornerSize {
    private final float size;

    public AbsoluteCornerSize(float size2) {
        this.size = size2;
    }

    public float getCornerSize(RectF bounds) {
        return this.size;
    }

    public float getCornerSize() {
        return this.size;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o instanceof AbsoluteCornerSize) && this.size == ((AbsoluteCornerSize) o).size) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Float.valueOf(this.size)});
    }
}
