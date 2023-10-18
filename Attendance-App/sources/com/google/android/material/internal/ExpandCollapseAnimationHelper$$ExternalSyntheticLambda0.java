package com.google.android.material.internal;

import android.animation.ValueAnimator;
import android.graphics.Rect;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class ExpandCollapseAnimationHelper$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ ExpandCollapseAnimationHelper f$0;
    public final /* synthetic */ Rect f$1;

    public /* synthetic */ ExpandCollapseAnimationHelper$$ExternalSyntheticLambda0(ExpandCollapseAnimationHelper expandCollapseAnimationHelper, Rect rect) {
        this.f$0 = expandCollapseAnimationHelper;
        this.f$1 = rect;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.f$0.mo22785xeb41e2ac(this.f$1, valueAnimator);
    }
}
