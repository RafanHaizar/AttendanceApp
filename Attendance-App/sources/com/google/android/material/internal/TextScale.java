package com.google.android.material.internal;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.transition.Transition;
import androidx.transition.TransitionValues;
import java.util.Map;

public class TextScale extends Transition {
    private static final String PROPNAME_SCALE = "android:textscale:scale";

    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private void captureValues(TransitionValues transitionValues) {
        if (transitionValues.view instanceof TextView) {
            transitionValues.values.put(PROPNAME_SCALE, Float.valueOf(((TextView) transitionValues.view).getScaleX()));
        }
    }

    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null || endValues == null || !(startValues.view instanceof TextView) || !(endValues.view instanceof TextView)) {
            return null;
        }
        final TextView view = (TextView) endValues.view;
        Map<String, Object> startVals = startValues.values;
        Map<String, Object> endVals = endValues.values;
        float f = 1.0f;
        float startSize = startVals.get(PROPNAME_SCALE) != null ? ((Float) startVals.get(PROPNAME_SCALE)).floatValue() : 1.0f;
        if (endVals.get(PROPNAME_SCALE) != null) {
            f = ((Float) endVals.get(PROPNAME_SCALE)).floatValue();
        }
        float endSize = f;
        if (startSize == endSize) {
            return null;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{startSize, endSize});
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                view.setScaleX(animatedValue);
                view.setScaleY(animatedValue);
            }
        });
        return animator;
    }
}
