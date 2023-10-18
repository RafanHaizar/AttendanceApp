package com.google.android.material.circularreveal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import com.google.android.material.circularreveal.CircularRevealWidget;

public final class CircularRevealCompat {
    private CircularRevealCompat() {
    }

    public static Animator createCircularReveal(CircularRevealWidget view, float centerX, float centerY, float endRadius) {
        Animator revealInfoAnimator = ObjectAnimator.ofObject(view, CircularRevealWidget.CircularRevealProperty.CIRCULAR_REVEAL, CircularRevealWidget.CircularRevealEvaluator.CIRCULAR_REVEAL, new CircularRevealWidget.RevealInfo[]{new CircularRevealWidget.RevealInfo(centerX, centerY, endRadius)});
        if (Build.VERSION.SDK_INT < 21) {
            return revealInfoAnimator;
        }
        CircularRevealWidget.RevealInfo revealInfo = view.getRevealInfo();
        if (revealInfo != null) {
            Animator circularRevealAnimator = ViewAnimationUtils.createCircularReveal((View) view, (int) centerX, (int) centerY, revealInfo.radius, endRadius);
            AnimatorSet set = new AnimatorSet();
            set.playTogether(new Animator[]{revealInfoAnimator, circularRevealAnimator});
            return set;
        }
        throw new IllegalStateException("Caller must set a non-null RevealInfo before calling this.");
    }

    public static Animator createCircularReveal(CircularRevealWidget view, float centerX, float centerY, float startRadius, float endRadius) {
        Animator revealInfoAnimator = ObjectAnimator.ofObject(view, CircularRevealWidget.CircularRevealProperty.CIRCULAR_REVEAL, CircularRevealWidget.CircularRevealEvaluator.CIRCULAR_REVEAL, new CircularRevealWidget.RevealInfo[]{new CircularRevealWidget.RevealInfo(centerX, centerY, startRadius), new CircularRevealWidget.RevealInfo(centerX, centerY, endRadius)});
        if (Build.VERSION.SDK_INT < 21) {
            return revealInfoAnimator;
        }
        Animator circularRevealAnimator = ViewAnimationUtils.createCircularReveal((View) view, (int) centerX, (int) centerY, startRadius, endRadius);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(new Animator[]{revealInfoAnimator, circularRevealAnimator});
        return set;
    }

    public static Animator.AnimatorListener createCircularRevealListener(final CircularRevealWidget view) {
        return new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animation) {
                CircularRevealWidget.this.buildCircularRevealCache();
            }

            public void onAnimationEnd(Animator animation) {
                CircularRevealWidget.this.destroyCircularRevealCache();
            }
        };
    }
}
