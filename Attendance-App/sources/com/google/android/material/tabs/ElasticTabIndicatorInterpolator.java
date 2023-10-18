package com.google.android.material.tabs;

import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.google.android.material.animation.AnimationUtils;

class ElasticTabIndicatorInterpolator extends TabIndicatorInterpolator {
    ElasticTabIndicatorInterpolator() {
    }

    private static float decInterp(float fraction) {
        double d = (double) fraction;
        Double.isNaN(d);
        return (float) Math.sin((d * 3.141592653589793d) / 2.0d);
    }

    private static float accInterp(float fraction) {
        double d = (double) fraction;
        Double.isNaN(d);
        return (float) (1.0d - Math.cos((d * 3.141592653589793d) / 2.0d));
    }

    /* access modifiers changed from: package-private */
    public void updateIndicatorForOffset(TabLayout tabLayout, View startTitle, View endTitle, float offset, Drawable indicator) {
        float rightFraction;
        float leftFraction;
        RectF startIndicator = calculateIndicatorWidthForTab(tabLayout, startTitle);
        RectF endIndicator = calculateIndicatorWidthForTab(tabLayout, endTitle);
        if (startIndicator.left < endIndicator.left) {
            leftFraction = accInterp(offset);
            rightFraction = decInterp(offset);
        } else {
            leftFraction = decInterp(offset);
            rightFraction = accInterp(offset);
        }
        indicator.setBounds(AnimationUtils.lerp((int) startIndicator.left, (int) endIndicator.left, leftFraction), indicator.getBounds().top, AnimationUtils.lerp((int) startIndicator.right, (int) endIndicator.right, rightFraction), indicator.getBounds().bottom);
    }
}
