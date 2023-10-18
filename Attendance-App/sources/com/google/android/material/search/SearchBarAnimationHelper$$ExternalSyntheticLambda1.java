package com.google.android.material.search;

import android.animation.ValueAnimator;
import android.view.View;
import com.google.android.material.shape.MaterialShapeDrawable;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class SearchBarAnimationHelper$$ExternalSyntheticLambda1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ MaterialShapeDrawable f$0;
    public final /* synthetic */ View f$1;

    public /* synthetic */ SearchBarAnimationHelper$$ExternalSyntheticLambda1(MaterialShapeDrawable materialShapeDrawable, View view) {
        this.f$0 = materialShapeDrawable;
        this.f$1 = view;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        SearchBarAnimationHelper.lambda$getExpandedViewBackgroundUpdateListener$1(this.f$0, this.f$1, valueAnimator);
    }
}
