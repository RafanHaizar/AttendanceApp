package com.google.android.material.internal;

import android.animation.TypeEvaluator;
import android.graphics.Rect;

public class RectEvaluator implements TypeEvaluator<Rect> {
    private final Rect rect;

    public RectEvaluator(Rect rect2) {
        this.rect = rect2;
    }

    public Rect evaluate(float fraction, Rect startValue, Rect endValue) {
        this.rect.set(startValue.left + ((int) (((float) (endValue.left - startValue.left)) * fraction)), startValue.top + ((int) (((float) (endValue.top - startValue.top)) * fraction)), startValue.right + ((int) (((float) (endValue.right - startValue.right)) * fraction)), startValue.bottom + ((int) (((float) (endValue.bottom - startValue.bottom)) * fraction)));
        return this.rect;
    }
}
