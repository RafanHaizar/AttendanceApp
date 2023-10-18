package com.google.android.material.transition.platform;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

public final class FadeThroughProvider implements VisibilityAnimatorProvider {
    static final float FADE_THROUGH_THRESHOLD = 0.35f;
    private float progressThreshold = FADE_THROUGH_THRESHOLD;

    public float getProgressThreshold() {
        return this.progressThreshold;
    }

    public void setProgressThreshold(float progressThreshold2) {
        this.progressThreshold = progressThreshold2;
    }

    public Animator createAppear(ViewGroup sceneRoot, View view) {
        float originalAlpha = view.getAlpha() == 0.0f ? 1.0f : view.getAlpha();
        return createFadeThroughAnimator(view, 0.0f, originalAlpha, this.progressThreshold, 1.0f, originalAlpha);
    }

    public Animator createDisappear(ViewGroup sceneRoot, View view) {
        float originalAlpha = view.getAlpha() == 0.0f ? 1.0f : view.getAlpha();
        return createFadeThroughAnimator(view, originalAlpha, 0.0f, 0.0f, this.progressThreshold, originalAlpha);
    }

    private static Animator createFadeThroughAnimator(final View view, float startValue, float endValue, float startFraction, float endFraction, final float originalAlpha) {
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        final View view2 = view;
        final float f = startValue;
        final float f2 = endValue;
        final float f3 = startFraction;
        final float f4 = endFraction;
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                view2.setAlpha(TransitionUtils.lerp(f, f2, f3, f4, ((Float) animation.getAnimatedValue()).floatValue()));
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                view.setAlpha(originalAlpha);
            }
        });
        return animator;
    }
}
