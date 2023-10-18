package com.google.android.material.internal;

import android.animation.ValueAnimator;
import android.view.View;
import java.util.Collection;

public class MultiViewUpdateListener implements ValueAnimator.AnimatorUpdateListener {
    private final Listener listener;
    private final View[] views;

    interface Listener {
        void onAnimationUpdate(ValueAnimator valueAnimator, View view);
    }

    public MultiViewUpdateListener(Listener listener2, View... views2) {
        this.listener = listener2;
        this.views = views2;
    }

    public MultiViewUpdateListener(Listener listener2, Collection<View> views2) {
        this.listener = listener2;
        this.views = (View[]) views2.toArray(new View[0]);
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        for (View view : this.views) {
            this.listener.onAnimationUpdate(valueAnimator, view);
        }
    }

    public static MultiViewUpdateListener alphaListener(View... views2) {
        return new MultiViewUpdateListener((Listener) new MultiViewUpdateListener$$ExternalSyntheticLambda3(), views2);
    }

    public static MultiViewUpdateListener alphaListener(Collection<View> views2) {
        return new MultiViewUpdateListener((Listener) new MultiViewUpdateListener$$ExternalSyntheticLambda3(), views2);
    }

    /* access modifiers changed from: private */
    public static void setAlpha(ValueAnimator animator, View view) {
        view.setAlpha(((Float) animator.getAnimatedValue()).floatValue());
    }

    public static MultiViewUpdateListener scaleListener(View... views2) {
        return new MultiViewUpdateListener((Listener) new MultiViewUpdateListener$$ExternalSyntheticLambda1(), views2);
    }

    public static MultiViewUpdateListener scaleListener(Collection<View> views2) {
        return new MultiViewUpdateListener((Listener) new MultiViewUpdateListener$$ExternalSyntheticLambda1(), views2);
    }

    /* access modifiers changed from: private */
    public static void setScale(ValueAnimator animator, View view) {
        Float scale = (Float) animator.getAnimatedValue();
        view.setScaleX(scale.floatValue());
        view.setScaleY(scale.floatValue());
    }

    public static MultiViewUpdateListener translationXListener(View... views2) {
        return new MultiViewUpdateListener((Listener) new MultiViewUpdateListener$$ExternalSyntheticLambda0(), views2);
    }

    public static MultiViewUpdateListener translationXListener(Collection<View> views2) {
        return new MultiViewUpdateListener((Listener) new MultiViewUpdateListener$$ExternalSyntheticLambda0(), views2);
    }

    /* access modifiers changed from: private */
    public static void setTranslationX(ValueAnimator animator, View view) {
        view.setTranslationX(((Float) animator.getAnimatedValue()).floatValue());
    }

    public static MultiViewUpdateListener translationYListener(View... views2) {
        return new MultiViewUpdateListener((Listener) new MultiViewUpdateListener$$ExternalSyntheticLambda2(), views2);
    }

    public static MultiViewUpdateListener translationYListener(Collection<View> views2) {
        return new MultiViewUpdateListener((Listener) new MultiViewUpdateListener$$ExternalSyntheticLambda2(), views2);
    }

    /* access modifiers changed from: private */
    public static void setTranslationY(ValueAnimator animator, View view) {
        view.setTranslationY(((Float) animator.getAnimatedValue()).floatValue());
    }
}
