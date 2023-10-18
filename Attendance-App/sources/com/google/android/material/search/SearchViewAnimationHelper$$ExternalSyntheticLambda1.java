package com.google.android.material.search;

import android.animation.ValueAnimator;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class SearchViewAnimationHelper$$ExternalSyntheticLambda1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ DrawerArrowDrawable f$0;

    public /* synthetic */ SearchViewAnimationHelper$$ExternalSyntheticLambda1(DrawerArrowDrawable drawerArrowDrawable) {
        this.f$0 = drawerArrowDrawable;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.f$0.setProgress(valueAnimator.getAnimatedFraction());
    }
}
