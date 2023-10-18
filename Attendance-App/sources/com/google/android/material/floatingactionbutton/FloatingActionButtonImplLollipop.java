package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.core.util.Preconditions;
import com.google.android.material.C1087R;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shadow.ShadowViewDelegate;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.util.ArrayList;
import java.util.List;

class FloatingActionButtonImplLollipop extends FloatingActionButtonImpl {
    private StateListAnimator stateListAnimator;

    FloatingActionButtonImplLollipop(FloatingActionButton view, ShadowViewDelegate shadowViewDelegate) {
        super(view, shadowViewDelegate);
    }

    /* access modifiers changed from: package-private */
    public void initializeBackgroundDrawable(ColorStateList backgroundTint, PorterDuff.Mode backgroundTintMode, ColorStateList rippleColor, int borderWidth) {
        Drawable rippleContent;
        this.shapeDrawable = createShapeDrawable();
        this.shapeDrawable.setTintList(backgroundTint);
        if (backgroundTintMode != null) {
            this.shapeDrawable.setTintMode(backgroundTintMode);
        }
        this.shapeDrawable.initializeElevationOverlay(this.view.getContext());
        if (borderWidth > 0) {
            this.borderDrawable = createBorderDrawable(borderWidth, backgroundTint);
            rippleContent = new LayerDrawable(new Drawable[]{(Drawable) Preconditions.checkNotNull(this.borderDrawable), (Drawable) Preconditions.checkNotNull(this.shapeDrawable)});
        } else {
            this.borderDrawable = null;
            rippleContent = this.shapeDrawable;
        }
        this.rippleDrawable = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(rippleColor), rippleContent, (Drawable) null);
        this.contentBackground = this.rippleDrawable;
    }

    /* access modifiers changed from: package-private */
    public void setRippleColor(ColorStateList rippleColor) {
        if (this.rippleDrawable instanceof RippleDrawable) {
            ((RippleDrawable) this.rippleDrawable).setColor(RippleUtils.sanitizeRippleDrawableColor(rippleColor));
        } else {
            super.setRippleColor(rippleColor);
        }
    }

    /* access modifiers changed from: package-private */
    public void onElevationsChanged(float elevation, float hoveredFocusedTranslationZ, float pressedTranslationZ) {
        if (Build.VERSION.SDK_INT == 21) {
            this.view.refreshDrawableState();
        } else if (this.view.getStateListAnimator() == this.stateListAnimator) {
            this.stateListAnimator = createDefaultStateListAnimator(elevation, hoveredFocusedTranslationZ, pressedTranslationZ);
            this.view.setStateListAnimator(this.stateListAnimator);
        }
        if (shouldAddPadding()) {
            updatePadding();
        }
    }

    private StateListAnimator createDefaultStateListAnimator(float elevation, float hoveredFocusedTranslationZ, float pressedTranslationZ) {
        StateListAnimator stateListAnimator2 = new StateListAnimator();
        stateListAnimator2.addState(PRESSED_ENABLED_STATE_SET, createElevationAnimator(elevation, pressedTranslationZ));
        stateListAnimator2.addState(HOVERED_FOCUSED_ENABLED_STATE_SET, createElevationAnimator(elevation, hoveredFocusedTranslationZ));
        stateListAnimator2.addState(FOCUSED_ENABLED_STATE_SET, createElevationAnimator(elevation, hoveredFocusedTranslationZ));
        stateListAnimator2.addState(HOVERED_ENABLED_STATE_SET, createElevationAnimator(elevation, hoveredFocusedTranslationZ));
        AnimatorSet set = new AnimatorSet();
        List<Animator> animators = new ArrayList<>();
        animators.add(ObjectAnimator.ofFloat(this.view, "elevation", new float[]{elevation}).setDuration(0));
        if (Build.VERSION.SDK_INT >= 22 && Build.VERSION.SDK_INT <= 24) {
            animators.add(ObjectAnimator.ofFloat(this.view, View.TRANSLATION_Z, new float[]{this.view.getTranslationZ()}).setDuration(100));
        }
        animators.add(ObjectAnimator.ofFloat(this.view, View.TRANSLATION_Z, new float[]{0.0f}).setDuration(100));
        set.playSequentially((Animator[]) animators.toArray(new Animator[0]));
        set.setInterpolator(ELEVATION_ANIM_INTERPOLATOR);
        stateListAnimator2.addState(ENABLED_STATE_SET, set);
        stateListAnimator2.addState(EMPTY_STATE_SET, createElevationAnimator(0.0f, 0.0f));
        return stateListAnimator2;
    }

    private Animator createElevationAnimator(float elevation, float translationZ) {
        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(this.view, "elevation", new float[]{elevation}).setDuration(0)).with(ObjectAnimator.ofFloat(this.view, View.TRANSLATION_Z, new float[]{translationZ}).setDuration(100));
        set.setInterpolator(ELEVATION_ANIM_INTERPOLATOR);
        return set;
    }

    public float getElevation() {
        return this.view.getElevation();
    }

    /* access modifiers changed from: package-private */
    public void onCompatShadowChanged() {
        updatePadding();
    }

    /* access modifiers changed from: package-private */
    public boolean shouldAddPadding() {
        return this.shadowViewDelegate.isCompatPaddingEnabled() || !shouldExpandBoundsForA11y();
    }

    /* access modifiers changed from: package-private */
    public void onDrawableStateChanged(int[] state) {
        if (Build.VERSION.SDK_INT != 21) {
            return;
        }
        if (this.view.isEnabled()) {
            this.view.setElevation(this.elevation);
            if (this.view.isPressed()) {
                this.view.setTranslationZ(this.pressedTranslationZ);
            } else if (this.view.isFocused() || this.view.isHovered()) {
                this.view.setTranslationZ(this.hoveredFocusedTranslationZ);
            } else {
                this.view.setTranslationZ(0.0f);
            }
        } else {
            this.view.setElevation(0.0f);
            this.view.setTranslationZ(0.0f);
        }
    }

    /* access modifiers changed from: package-private */
    public void jumpDrawableToCurrentState() {
    }

    /* access modifiers changed from: package-private */
    public void updateFromViewRotation() {
    }

    /* access modifiers changed from: package-private */
    public boolean requirePreDrawListener() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public BorderDrawable createBorderDrawable(int borderWidth, ColorStateList backgroundTint) {
        Context context = this.view.getContext();
        BorderDrawable borderDrawable = new BorderDrawable((ShapeAppearanceModel) Preconditions.checkNotNull(this.shapeAppearance));
        borderDrawable.setGradientColors(ContextCompat.getColor(context, C1087R.C1088color.design_fab_stroke_top_outer_color), ContextCompat.getColor(context, C1087R.C1088color.design_fab_stroke_top_inner_color), ContextCompat.getColor(context, C1087R.C1088color.design_fab_stroke_end_inner_color), ContextCompat.getColor(context, C1087R.C1088color.design_fab_stroke_end_outer_color));
        borderDrawable.setBorderWidth((float) borderWidth);
        borderDrawable.setBorderTint(backgroundTint);
        return borderDrawable;
    }

    /* access modifiers changed from: package-private */
    public MaterialShapeDrawable createShapeDrawable() {
        return new AlwaysStatefulMaterialShapeDrawable((ShapeAppearanceModel) Preconditions.checkNotNull(this.shapeAppearance));
    }

    /* access modifiers changed from: package-private */
    public void getPadding(Rect rect) {
        if (this.shadowViewDelegate.isCompatPaddingEnabled()) {
            super.getPadding(rect);
        } else if (!shouldExpandBoundsForA11y()) {
            int minPadding = (this.minTouchTargetSize - this.view.getSizeDimension()) / 2;
            rect.set(minPadding, minPadding, minPadding, minPadding);
        } else {
            rect.set(0, 0, 0, 0);
        }
    }

    static class AlwaysStatefulMaterialShapeDrawable extends MaterialShapeDrawable {
        AlwaysStatefulMaterialShapeDrawable(ShapeAppearanceModel shapeAppearanceModel) {
            super(shapeAppearanceModel);
        }

        public boolean isStateful() {
            return true;
        }
    }
}
