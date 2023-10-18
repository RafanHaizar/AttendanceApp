package com.google.android.material.internal;

import android.animation.ValueAnimator;
import android.view.View;
import com.google.android.material.internal.MultiViewUpdateListener;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class MultiViewUpdateListener$$ExternalSyntheticLambda0 implements MultiViewUpdateListener.Listener {
    public final void onAnimationUpdate(ValueAnimator valueAnimator, View view) {
        MultiViewUpdateListener.setTranslationX(valueAnimator, view);
    }
}
