package com.google.android.material.shape;

import android.graphics.RectF;
import java.util.Arrays;

public final class RelativeCornerSize implements CornerSize {
    private final float percent;

    public static RelativeCornerSize createFromCornerSize(RectF bounds, CornerSize cornerSize) {
        if (cornerSize instanceof RelativeCornerSize) {
            return (RelativeCornerSize) cornerSize;
        }
        return new RelativeCornerSize(cornerSize.getCornerSize(bounds) / getMaxCornerSize(bounds));
    }

    private static float getMaxCornerSize(RectF bounds) {
        return Math.min(bounds.width(), bounds.height());
    }

    public RelativeCornerSize(float percent2) {
        this.percent = percent2;
    }

    public float getRelativePercent() {
        return this.percent;
    }

    public float getCornerSize(RectF bounds) {
        return this.percent * getMaxCornerSize(bounds);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o instanceof RelativeCornerSize) && this.percent == ((RelativeCornerSize) o).percent) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Float.valueOf(this.percent)});
    }
}
