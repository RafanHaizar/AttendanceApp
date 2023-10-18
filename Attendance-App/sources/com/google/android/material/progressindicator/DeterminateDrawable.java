package com.google.android.material.progressindicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.progressindicator.BaseProgressIndicatorSpec;

public final class DeterminateDrawable<S extends BaseProgressIndicatorSpec> extends DrawableWithAnimatedVisibilityChange {
    private static final FloatPropertyCompat<DeterminateDrawable> INDICATOR_LENGTH_IN_LEVEL = new FloatPropertyCompat<DeterminateDrawable>("indicatorLevel") {
        public float getValue(DeterminateDrawable drawable) {
            return drawable.getIndicatorFraction() * 10000.0f;
        }

        public void setValue(DeterminateDrawable drawable, float value) {
            drawable.setIndicatorFraction(value / 10000.0f);
        }
    };
    private static final int MAX_DRAWABLE_LEVEL = 10000;
    private static final float SPRING_FORCE_STIFFNESS = 50.0f;
    private DrawingDelegate<S> drawingDelegate;
    private float indicatorFraction;
    private boolean skipAnimationOnLevelChange = false;
    private final SpringAnimation springAnimation;
    private final SpringForce springForce;

    public /* bridge */ /* synthetic */ void clearAnimationCallbacks() {
        super.clearAnimationCallbacks();
    }

    public /* bridge */ /* synthetic */ int getAlpha() {
        return super.getAlpha();
    }

    public /* bridge */ /* synthetic */ int getOpacity() {
        return super.getOpacity();
    }

    public /* bridge */ /* synthetic */ boolean hideNow() {
        return super.hideNow();
    }

    public /* bridge */ /* synthetic */ boolean isHiding() {
        return super.isHiding();
    }

    public /* bridge */ /* synthetic */ boolean isRunning() {
        return super.isRunning();
    }

    public /* bridge */ /* synthetic */ boolean isShowing() {
        return super.isShowing();
    }

    public /* bridge */ /* synthetic */ void registerAnimationCallback(Animatable2Compat.AnimationCallback animationCallback) {
        super.registerAnimationCallback(animationCallback);
    }

    public /* bridge */ /* synthetic */ void setAlpha(int i) {
        super.setAlpha(i);
    }

    public /* bridge */ /* synthetic */ void setColorFilter(ColorFilter colorFilter) {
        super.setColorFilter(colorFilter);
    }

    public /* bridge */ /* synthetic */ boolean setVisible(boolean z, boolean z2) {
        return super.setVisible(z, z2);
    }

    public /* bridge */ /* synthetic */ boolean setVisible(boolean z, boolean z2, boolean z3) {
        return super.setVisible(z, z2, z3);
    }

    public /* bridge */ /* synthetic */ void start() {
        super.start();
    }

    public /* bridge */ /* synthetic */ void stop() {
        super.stop();
    }

    public /* bridge */ /* synthetic */ boolean unregisterAnimationCallback(Animatable2Compat.AnimationCallback animationCallback) {
        return super.unregisterAnimationCallback(animationCallback);
    }

    DeterminateDrawable(Context context, BaseProgressIndicatorSpec baseSpec, DrawingDelegate<S> drawingDelegate2) {
        super(context, baseSpec);
        setDrawingDelegate(drawingDelegate2);
        SpringForce springForce2 = new SpringForce();
        this.springForce = springForce2;
        springForce2.setDampingRatio(1.0f);
        springForce2.setStiffness(50.0f);
        SpringAnimation springAnimation2 = new SpringAnimation(this, INDICATOR_LENGTH_IN_LEVEL);
        this.springAnimation = springAnimation2;
        springAnimation2.setSpring(springForce2);
        setGrowFraction(1.0f);
    }

    public static DeterminateDrawable<LinearProgressIndicatorSpec> createLinearDrawable(Context context, LinearProgressIndicatorSpec spec) {
        return new DeterminateDrawable<>(context, spec, new LinearDrawingDelegate(spec));
    }

    public static DeterminateDrawable<CircularProgressIndicatorSpec> createCircularDrawable(Context context, CircularProgressIndicatorSpec spec) {
        return new DeterminateDrawable<>(context, spec, new CircularDrawingDelegate(spec));
    }

    public void addSpringAnimationEndListener(DynamicAnimation.OnAnimationEndListener listener) {
        this.springAnimation.addEndListener(listener);
    }

    public void removeSpringAnimationEndListener(DynamicAnimation.OnAnimationEndListener listener) {
        this.springAnimation.removeEndListener(listener);
    }

    /* access modifiers changed from: package-private */
    public boolean setVisibleInternal(boolean visible, boolean restart, boolean animate) {
        boolean changed = super.setVisibleInternal(visible, restart, animate);
        float systemAnimatorDurationScale = this.animatorDurationScaleProvider.getSystemAnimatorDurationScale(this.context.getContentResolver());
        if (systemAnimatorDurationScale == 0.0f) {
            this.skipAnimationOnLevelChange = true;
        } else {
            this.skipAnimationOnLevelChange = false;
            this.springForce.setStiffness(50.0f / systemAnimatorDurationScale);
        }
        return changed;
    }

    public void jumpToCurrentState() {
        this.springAnimation.skipToEnd();
        setIndicatorFraction(((float) getLevel()) / 10000.0f);
    }

    /* access modifiers changed from: protected */
    public boolean onLevelChange(int level) {
        if (this.skipAnimationOnLevelChange) {
            this.springAnimation.skipToEnd();
            setIndicatorFraction(((float) level) / 10000.0f);
            return true;
        }
        this.springAnimation.setStartValue(getIndicatorFraction() * 10000.0f);
        this.springAnimation.animateToFinalPosition((float) level);
        return true;
    }

    public int getIntrinsicWidth() {
        return this.drawingDelegate.getPreferredWidth();
    }

    public int getIntrinsicHeight() {
        return this.drawingDelegate.getPreferredHeight();
    }

    /* access modifiers changed from: package-private */
    public void setLevelByFraction(float fraction) {
        setLevel((int) (10000.0f * fraction));
    }

    public void draw(Canvas canvas) {
        Rect clipBounds = new Rect();
        if (!getBounds().isEmpty() && isVisible() && canvas.getClipBounds(clipBounds)) {
            canvas.save();
            this.drawingDelegate.validateSpecAndAdjustCanvas(canvas, getBounds(), getGrowFraction());
            this.drawingDelegate.fillTrack(canvas, this.paint);
            Canvas canvas2 = canvas;
            this.drawingDelegate.fillIndicator(canvas2, this.paint, 0.0f, getIndicatorFraction(), MaterialColors.compositeARGBWithAlpha(this.baseSpec.indicatorColors[0], getAlpha()));
            canvas.restore();
        }
    }

    /* access modifiers changed from: private */
    public float getIndicatorFraction() {
        return this.indicatorFraction;
    }

    /* access modifiers changed from: private */
    public void setIndicatorFraction(float indicatorFraction2) {
        this.indicatorFraction = indicatorFraction2;
        invalidateSelf();
    }

    /* access modifiers changed from: package-private */
    public DrawingDelegate<S> getDrawingDelegate() {
        return this.drawingDelegate;
    }

    /* access modifiers changed from: package-private */
    public void setDrawingDelegate(DrawingDelegate<S> drawingDelegate2) {
        this.drawingDelegate = drawingDelegate2;
        drawingDelegate2.registerDrawable(this);
    }
}
