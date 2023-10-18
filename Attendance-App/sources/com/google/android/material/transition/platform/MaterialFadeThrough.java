package com.google.android.material.transition.platform;

import android.animation.Animator;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.C1087R;

public final class MaterialFadeThrough extends MaterialVisibility<FadeThroughProvider> {
    private static final float DEFAULT_START_SCALE = 0.92f;
    private static final int DEFAULT_THEMED_DURATION_ATTR = C1087R.attr.motionDurationLong1;
    private static final int DEFAULT_THEMED_EASING_ATTR = C1087R.attr.motionEasingEmphasizedInterpolator;

    public /* bridge */ /* synthetic */ void addAdditionalAnimatorProvider(VisibilityAnimatorProvider visibilityAnimatorProvider) {
        super.addAdditionalAnimatorProvider(visibilityAnimatorProvider);
    }

    public /* bridge */ /* synthetic */ void clearAdditionalAnimatorProvider() {
        super.clearAdditionalAnimatorProvider();
    }

    public /* bridge */ /* synthetic */ VisibilityAnimatorProvider getPrimaryAnimatorProvider() {
        return super.getPrimaryAnimatorProvider();
    }

    public /* bridge */ /* synthetic */ VisibilityAnimatorProvider getSecondaryAnimatorProvider() {
        return super.getSecondaryAnimatorProvider();
    }

    public /* bridge */ /* synthetic */ Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return super.onAppear(viewGroup, view, transitionValues, transitionValues2);
    }

    public /* bridge */ /* synthetic */ Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return super.onDisappear(viewGroup, view, transitionValues, transitionValues2);
    }

    public /* bridge */ /* synthetic */ boolean removeAdditionalAnimatorProvider(VisibilityAnimatorProvider visibilityAnimatorProvider) {
        return super.removeAdditionalAnimatorProvider(visibilityAnimatorProvider);
    }

    public /* bridge */ /* synthetic */ void setSecondaryAnimatorProvider(VisibilityAnimatorProvider visibilityAnimatorProvider) {
        super.setSecondaryAnimatorProvider(visibilityAnimatorProvider);
    }

    public MaterialFadeThrough() {
        super(createPrimaryAnimatorProvider(), createSecondaryAnimatorProvider());
    }

    private static FadeThroughProvider createPrimaryAnimatorProvider() {
        return new FadeThroughProvider();
    }

    private static VisibilityAnimatorProvider createSecondaryAnimatorProvider() {
        ScaleProvider scaleProvider = new ScaleProvider();
        scaleProvider.setScaleOnDisappear(false);
        scaleProvider.setIncomingStartScale(DEFAULT_START_SCALE);
        return scaleProvider;
    }

    /* access modifiers changed from: package-private */
    public int getDurationThemeAttrResId(boolean appearing) {
        return DEFAULT_THEMED_DURATION_ATTR;
    }

    /* access modifiers changed from: package-private */
    public int getEasingThemeAttrResId(boolean appearing) {
        return DEFAULT_THEMED_EASING_ATTR;
    }
}
