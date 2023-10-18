package com.google.android.material.transition.platform;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.C1087R;
import com.google.android.material.animation.AnimationUtils;

public final class MaterialFade extends MaterialVisibility<FadeProvider> {
    private static final float DEFAULT_FADE_END_THRESHOLD_ENTER = 0.3f;
    private static final float DEFAULT_START_SCALE = 0.8f;
    private static final int DEFAULT_THEMED_INCOMING_DURATION_ATTR = C1087R.attr.motionDurationMedium4;
    private static final int DEFAULT_THEMED_INCOMING_EASING_ATTR = C1087R.attr.motionEasingEmphasizedDecelerateInterpolator;
    private static final int DEFAULT_THEMED_OUTGOING_DURATION_ATTR = C1087R.attr.motionDurationShort3;
    private static final int DEFAULT_THEMED_OUTGOING_EASING_ATTR = C1087R.attr.motionEasingEmphasizedAccelerateInterpolator;

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

    public MaterialFade() {
        super(createPrimaryAnimatorProvider(), createSecondaryAnimatorProvider());
    }

    private static FadeProvider createPrimaryAnimatorProvider() {
        FadeProvider fadeProvider = new FadeProvider();
        fadeProvider.setIncomingEndThreshold(DEFAULT_FADE_END_THRESHOLD_ENTER);
        return fadeProvider;
    }

    private static VisibilityAnimatorProvider createSecondaryAnimatorProvider() {
        ScaleProvider scaleProvider = new ScaleProvider();
        scaleProvider.setScaleOnDisappear(false);
        scaleProvider.setIncomingStartScale(DEFAULT_START_SCALE);
        return scaleProvider;
    }

    /* access modifiers changed from: package-private */
    public int getDurationThemeAttrResId(boolean appearing) {
        if (appearing) {
            return DEFAULT_THEMED_INCOMING_DURATION_ATTR;
        }
        return DEFAULT_THEMED_OUTGOING_DURATION_ATTR;
    }

    /* access modifiers changed from: package-private */
    public int getEasingThemeAttrResId(boolean appearing) {
        if (appearing) {
            return DEFAULT_THEMED_INCOMING_EASING_ATTR;
        }
        return DEFAULT_THEMED_OUTGOING_EASING_ATTR;
    }

    /* access modifiers changed from: package-private */
    public TimeInterpolator getDefaultEasingInterpolator(boolean appearing) {
        return AnimationUtils.LINEAR_INTERPOLATOR;
    }
}
