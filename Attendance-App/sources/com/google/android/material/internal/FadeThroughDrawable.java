package com.google.android.material.internal;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

public class FadeThroughDrawable extends Drawable {
    private final float[] alphas = new float[2];
    private final Drawable fadeInDrawable;
    private final Drawable fadeOutDrawable;
    private float progress;

    public FadeThroughDrawable(Drawable fadeOutDrawable2, Drawable fadeInDrawable2) {
        this.fadeOutDrawable = fadeOutDrawable2.getConstantState().newDrawable().mutate();
        Drawable mutate = fadeInDrawable2.getConstantState().newDrawable().mutate();
        this.fadeInDrawable = mutate;
        mutate.setAlpha(0);
    }

    public void draw(Canvas canvas) {
        this.fadeOutDrawable.draw(canvas);
        this.fadeInDrawable.draw(canvas);
    }

    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        this.fadeOutDrawable.setBounds(left, top, right, bottom);
        this.fadeInDrawable.setBounds(left, top, right, bottom);
    }

    public int getIntrinsicWidth() {
        return Math.max(this.fadeOutDrawable.getIntrinsicWidth(), this.fadeInDrawable.getIntrinsicWidth());
    }

    public int getIntrinsicHeight() {
        return Math.max(this.fadeOutDrawable.getIntrinsicHeight(), this.fadeInDrawable.getIntrinsicHeight());
    }

    public int getMinimumWidth() {
        return Math.max(this.fadeOutDrawable.getMinimumWidth(), this.fadeInDrawable.getMinimumWidth());
    }

    public int getMinimumHeight() {
        return Math.max(this.fadeOutDrawable.getMinimumHeight(), this.fadeInDrawable.getMinimumHeight());
    }

    public void setAlpha(int alpha) {
        if (this.progress <= 0.5f) {
            this.fadeOutDrawable.setAlpha(alpha);
            this.fadeInDrawable.setAlpha(0);
        } else {
            this.fadeOutDrawable.setAlpha(0);
            this.fadeInDrawable.setAlpha(alpha);
        }
        invalidateSelf();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.fadeOutDrawable.setColorFilter(colorFilter);
        this.fadeInDrawable.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public int getOpacity() {
        return -3;
    }

    public boolean isStateful() {
        return this.fadeOutDrawable.isStateful() || this.fadeInDrawable.isStateful();
    }

    public boolean setState(int[] stateSet) {
        return this.fadeOutDrawable.setState(stateSet) || this.fadeInDrawable.setState(stateSet);
    }

    public void setProgress(float progress2) {
        if (this.progress != progress2) {
            this.progress = progress2;
            FadeThroughUtils.calculateFadeOutAndInAlphas(progress2, this.alphas);
            this.fadeOutDrawable.setAlpha((int) (this.alphas[0] * 255.0f));
            this.fadeInDrawable.setAlpha((int) (this.alphas[1] * 255.0f));
            invalidateSelf();
        }
    }
}
