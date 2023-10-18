package com.google.android.material.transition.platform;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.view.View;
import android.view.ViewGroup;

public final class Hold extends Visibility {
    public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        return ValueAnimator.ofFloat(new float[]{0.0f});
    }

    public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        return ValueAnimator.ofFloat(new float[]{0.0f});
    }
}
