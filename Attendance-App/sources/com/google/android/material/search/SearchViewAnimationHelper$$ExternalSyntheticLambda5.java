package com.google.android.material.search;

import android.animation.ValueAnimator;
import android.graphics.Rect;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class SearchViewAnimationHelper$$ExternalSyntheticLambda5 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ SearchViewAnimationHelper f$0;
    public final /* synthetic */ float f$1;
    public final /* synthetic */ Rect f$2;

    public /* synthetic */ SearchViewAnimationHelper$$ExternalSyntheticLambda5(SearchViewAnimationHelper searchViewAnimationHelper, float f, Rect rect) {
        this.f$0 = searchViewAnimationHelper;
        this.f$1 = f;
        this.f$2 = rect;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.f$0.mo23529xa183b80f(this.f$1, this.f$2, valueAnimator);
    }
}
