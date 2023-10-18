package com.google.android.material.transition;

import android.animation.Animator;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.GravityCompat;
import androidx.transition.TransitionValues;
import com.google.android.material.C1087R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class MaterialSharedAxis extends MaterialVisibility<VisibilityAnimatorProvider> {
    private static final int DEFAULT_THEMED_DURATION_ATTR = C1087R.attr.motionDurationLong1;
    private static final int DEFAULT_THEMED_EASING_ATTR = C1087R.attr.motionEasingEmphasizedInterpolator;

    /* renamed from: X */
    public static final int f1128X = 0;

    /* renamed from: Y */
    public static final int f1129Y = 1;

    /* renamed from: Z */
    public static final int f1130Z = 2;
    private final int axis;
    private final boolean forward;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Axis {
    }

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

    public MaterialSharedAxis(int axis2, boolean forward2) {
        super(createPrimaryAnimatorProvider(axis2, forward2), createSecondaryAnimatorProvider());
        this.axis = axis2;
        this.forward = forward2;
    }

    public int getAxis() {
        return this.axis;
    }

    public boolean isForward() {
        return this.forward;
    }

    private static VisibilityAnimatorProvider createPrimaryAnimatorProvider(int axis2, boolean forward2) {
        switch (axis2) {
            case 0:
                return new SlideDistanceProvider(forward2 ? GravityCompat.END : GravityCompat.START);
            case 1:
                return new SlideDistanceProvider(forward2 ? 80 : 48);
            case 2:
                return new ScaleProvider(forward2);
            default:
                throw new IllegalArgumentException("Invalid axis: " + axis2);
        }
    }

    private static VisibilityAnimatorProvider createSecondaryAnimatorProvider() {
        return new FadeThroughProvider();
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
