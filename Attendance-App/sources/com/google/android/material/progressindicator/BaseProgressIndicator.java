package com.google.android.material.progressindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.ProgressBar;
import androidx.core.view.ViewCompat;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.google.android.material.C1087R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.progressindicator.BaseProgressIndicatorSpec;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

public abstract class BaseProgressIndicator<S extends BaseProgressIndicatorSpec> extends ProgressBar {
    static final float DEFAULT_OPACITY = 0.2f;
    static final int DEF_STYLE_RES = C1087R.C1093style.Widget_MaterialComponents_ProgressIndicator;
    public static final int HIDE_INWARD = 2;
    public static final int HIDE_NONE = 0;
    public static final int HIDE_OUTWARD = 1;
    static final int MAX_ALPHA = 255;
    static final int MAX_HIDE_DELAY = 1000;
    public static final int SHOW_INWARD = 2;
    public static final int SHOW_NONE = 0;
    public static final int SHOW_OUTWARD = 1;
    AnimatorDurationScaleProvider animatorDurationScaleProvider;
    private final Runnable delayedHide = new Runnable() {
        public void run() {
            BaseProgressIndicator.this.internalHide();
            long unused = BaseProgressIndicator.this.lastShowStartTime = -1;
        }
    };
    private final Runnable delayedShow = new Runnable() {
        public void run() {
            BaseProgressIndicator.this.internalShow();
        }
    };
    private final Animatable2Compat.AnimationCallback hideAnimationCallback = new Animatable2Compat.AnimationCallback() {
        public void onAnimationEnd(Drawable drawable) {
            super.onAnimationEnd(drawable);
            if (!BaseProgressIndicator.this.isIndeterminateModeChangeRequested) {
                BaseProgressIndicator baseProgressIndicator = BaseProgressIndicator.this;
                baseProgressIndicator.setVisibility(baseProgressIndicator.visibilityAfterHide);
            }
        }
    };
    /* access modifiers changed from: private */
    public boolean isIndeterminateModeChangeRequested = false;
    private boolean isParentDoneInitializing;
    /* access modifiers changed from: private */
    public long lastShowStartTime = -1;
    private final int minHideDelay;
    private final int showDelay;
    S spec;
    /* access modifiers changed from: private */
    public int storedProgress;
    /* access modifiers changed from: private */
    public boolean storedProgressAnimated;
    private final Animatable2Compat.AnimationCallback switchIndeterminateModeCallback = new Animatable2Compat.AnimationCallback() {
        public void onAnimationEnd(Drawable drawable) {
            BaseProgressIndicator.this.setIndeterminate(false);
            BaseProgressIndicator baseProgressIndicator = BaseProgressIndicator.this;
            baseProgressIndicator.setProgressCompat(baseProgressIndicator.storedProgress, BaseProgressIndicator.this.storedProgressAnimated);
        }
    };
    /* access modifiers changed from: private */
    public int visibilityAfterHide = 4;

    @Retention(RetentionPolicy.SOURCE)
    public @interface HideAnimationBehavior {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ShowAnimationBehavior {
    }

    /* access modifiers changed from: package-private */
    public abstract S createSpec(Context context, AttributeSet attributeSet);

    protected BaseProgressIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES), attrs, defStyleAttr);
        Context context2 = getContext();
        this.spec = createSpec(context2, attrs);
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context2, attrs, C1087R.styleable.BaseProgressIndicator, defStyleAttr, defStyleRes, new int[0]);
        this.showDelay = a.getInt(C1087R.styleable.BaseProgressIndicator_showDelay, -1);
        this.minHideDelay = Math.min(a.getInt(C1087R.styleable.BaseProgressIndicator_minHideDelay, -1), 1000);
        a.recycle();
        this.animatorDurationScaleProvider = new AnimatorDurationScaleProvider();
        this.isParentDoneInitializing = true;
    }

    private void registerAnimationCallbacks() {
        if (!(getProgressDrawable() == null || getIndeterminateDrawable() == null)) {
            getIndeterminateDrawable().getAnimatorDelegate().registerAnimatorsCompleteCallback(this.switchIndeterminateModeCallback);
        }
        if (getProgressDrawable() != null) {
            getProgressDrawable().registerAnimationCallback(this.hideAnimationCallback);
        }
        if (getIndeterminateDrawable() != null) {
            getIndeterminateDrawable().registerAnimationCallback(this.hideAnimationCallback);
        }
    }

    private void unregisterAnimationCallbacks() {
        if (getIndeterminateDrawable() != null) {
            getIndeterminateDrawable().unregisterAnimationCallback(this.hideAnimationCallback);
            getIndeterminateDrawable().getAnimatorDelegate().unregisterAnimatorsCompleteCallback();
        }
        if (getProgressDrawable() != null) {
            getProgressDrawable().unregisterAnimationCallback(this.hideAnimationCallback);
        }
    }

    public void show() {
        if (this.showDelay > 0) {
            removeCallbacks(this.delayedShow);
            postDelayed(this.delayedShow, (long) this.showDelay);
            return;
        }
        this.delayedShow.run();
    }

    /* access modifiers changed from: private */
    public void internalShow() {
        if (this.minHideDelay > 0) {
            this.lastShowStartTime = SystemClock.uptimeMillis();
        }
        setVisibility(0);
    }

    public void hide() {
        if (getVisibility() != 0) {
            removeCallbacks(this.delayedShow);
            return;
        }
        removeCallbacks(this.delayedHide);
        long timeElapsedSinceShowStart = SystemClock.uptimeMillis() - this.lastShowStartTime;
        int i = this.minHideDelay;
        if (timeElapsedSinceShowStart >= ((long) i)) {
            this.delayedHide.run();
        } else {
            postDelayed(this.delayedHide, ((long) i) - timeElapsedSinceShowStart);
        }
    }

    /* access modifiers changed from: private */
    public void internalHide() {
        ((DrawableWithAnimatedVisibilityChange) getCurrentDrawable()).setVisible(false, false, true);
        if (isNoLongerNeedToBeVisible()) {
            setVisibility(4);
        }
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        applyNewVisibility(visibility == 0);
    }

    /* access modifiers changed from: protected */
    public void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        applyNewVisibility(false);
    }

    /* access modifiers changed from: protected */
    public void applyNewVisibility(boolean animate) {
        if (this.isParentDoneInitializing) {
            ((DrawableWithAnimatedVisibilityChange) getCurrentDrawable()).setVisible(visibleToUser(), false, animate);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        registerAnimationCallbacks();
        if (visibleToUser()) {
            internalShow();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        removeCallbacks(this.delayedHide);
        removeCallbacks(this.delayedShow);
        ((DrawableWithAnimatedVisibilityChange) getCurrentDrawable()).hideNow();
        unregisterAnimationCallbacks();
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public synchronized void onDraw(Canvas canvas) {
        int saveCount = canvas.save();
        if (!(getPaddingLeft() == 0 && getPaddingTop() == 0)) {
            canvas.translate((float) getPaddingLeft(), (float) getPaddingTop());
        }
        if (!(getPaddingRight() == 0 && getPaddingBottom() == 0)) {
            canvas.clipRect(0, 0, getWidth() - (getPaddingLeft() + getPaddingRight()), getHeight() - (getPaddingTop() + getPaddingBottom()));
        }
        getCurrentDrawable().draw(canvas);
        canvas.restoreToCount(saveCount);
    }

    /* access modifiers changed from: protected */
    public synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth;
        int measuredHeight;
        DrawingDelegate<S> drawingDelegate = getCurrentDrawingDelegate();
        if (drawingDelegate != null) {
            if (drawingDelegate.getPreferredWidth() < 0) {
                measuredWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
            } else {
                measuredWidth = drawingDelegate.getPreferredWidth() + getPaddingLeft() + getPaddingRight();
            }
            if (drawingDelegate.getPreferredHeight() < 0) {
                measuredHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
            } else {
                measuredHeight = drawingDelegate.getPreferredHeight() + getPaddingTop() + getPaddingBottom();
            }
            setMeasuredDimension(measuredWidth, measuredHeight);
        }
    }

    public void invalidate() {
        super.invalidate();
        if (getCurrentDrawable() != null) {
            getCurrentDrawable().invalidateSelf();
        }
    }

    public Drawable getCurrentDrawable() {
        return isIndeterminate() ? getIndeterminateDrawable() : getProgressDrawable();
    }

    private DrawingDelegate<S> getCurrentDrawingDelegate() {
        if (isIndeterminate()) {
            if (getIndeterminateDrawable() == null) {
                return null;
            }
            return getIndeterminateDrawable().getDrawingDelegate();
        } else if (getProgressDrawable() == null) {
            return null;
        } else {
            return getProgressDrawable().getDrawingDelegate();
        }
    }

    public void setProgressDrawable(Drawable drawable) {
        if (drawable == null) {
            super.setProgressDrawable((Drawable) null);
        } else if (drawable instanceof DeterminateDrawable) {
            DeterminateDrawable<S> determinateDrawable = (DeterminateDrawable) drawable;
            determinateDrawable.hideNow();
            super.setProgressDrawable(determinateDrawable);
            determinateDrawable.setLevelByFraction(((float) getProgress()) / ((float) getMax()));
        } else {
            throw new IllegalArgumentException("Cannot set framework drawable as progress drawable.");
        }
    }

    public void setIndeterminateDrawable(Drawable drawable) {
        if (drawable == null) {
            super.setIndeterminateDrawable((Drawable) null);
        } else if (drawable instanceof IndeterminateDrawable) {
            ((DrawableWithAnimatedVisibilityChange) drawable).hideNow();
            super.setIndeterminateDrawable(drawable);
        } else {
            throw new IllegalArgumentException("Cannot set framework drawable as indeterminate drawable.");
        }
    }

    public DeterminateDrawable<S> getProgressDrawable() {
        return (DeterminateDrawable) super.getProgressDrawable();
    }

    public IndeterminateDrawable<S> getIndeterminateDrawable() {
        return (IndeterminateDrawable) super.getIndeterminateDrawable();
    }

    /* access modifiers changed from: package-private */
    public boolean visibleToUser() {
        return ViewCompat.isAttachedToWindow(this) && getWindowVisibility() == 0 && isEffectivelyVisible();
    }

    /* access modifiers changed from: package-private */
    public boolean isEffectivelyVisible() {
        View current = this;
        while (current.getVisibility() == 0) {
            ViewParent parent = current.getParent();
            if (parent == null) {
                if (getWindowVisibility() == 0) {
                    return true;
                }
                return false;
            } else if (!(parent instanceof View)) {
                return true;
            } else {
                current = (View) parent;
            }
        }
        return false;
    }

    private boolean isNoLongerNeedToBeVisible() {
        return (getProgressDrawable() == null || !getProgressDrawable().isVisible()) && (getIndeterminateDrawable() == null || !getIndeterminateDrawable().isVisible());
    }

    public synchronized void setIndeterminate(boolean indeterminate) {
        if (indeterminate != isIndeterminate()) {
            DrawableWithAnimatedVisibilityChange oldDrawable = (DrawableWithAnimatedVisibilityChange) getCurrentDrawable();
            if (oldDrawable != null) {
                oldDrawable.hideNow();
            }
            super.setIndeterminate(indeterminate);
            DrawableWithAnimatedVisibilityChange newDrawable = (DrawableWithAnimatedVisibilityChange) getCurrentDrawable();
            if (newDrawable != null) {
                newDrawable.setVisible(visibleToUser(), false, false);
            }
            if ((newDrawable instanceof IndeterminateDrawable) && visibleToUser()) {
                ((IndeterminateDrawable) newDrawable).getAnimatorDelegate().startAnimator();
            }
            this.isIndeterminateModeChangeRequested = false;
        }
    }

    public int getTrackThickness() {
        return this.spec.trackThickness;
    }

    public void setTrackThickness(int trackThickness) {
        if (this.spec.trackThickness != trackThickness) {
            this.spec.trackThickness = trackThickness;
            requestLayout();
        }
    }

    public int[] getIndicatorColor() {
        return this.spec.indicatorColors;
    }

    public void setIndicatorColor(int... indicatorColors) {
        if (indicatorColors.length == 0) {
            indicatorColors = new int[]{MaterialColors.getColor(getContext(), C1087R.attr.colorPrimary, -1)};
        }
        if (!Arrays.equals(getIndicatorColor(), indicatorColors)) {
            this.spec.indicatorColors = indicatorColors;
            getIndeterminateDrawable().getAnimatorDelegate().invalidateSpecValues();
            invalidate();
        }
    }

    public int getTrackColor() {
        return this.spec.trackColor;
    }

    public void setTrackColor(int trackColor) {
        if (this.spec.trackColor != trackColor) {
            this.spec.trackColor = trackColor;
            invalidate();
        }
    }

    public int getTrackCornerRadius() {
        return this.spec.trackCornerRadius;
    }

    public void setTrackCornerRadius(int trackCornerRadius) {
        if (this.spec.trackCornerRadius != trackCornerRadius) {
            S s = this.spec;
            s.trackCornerRadius = Math.min(trackCornerRadius, s.trackThickness / 2);
        }
    }

    public int getShowAnimationBehavior() {
        return this.spec.showAnimationBehavior;
    }

    public void setShowAnimationBehavior(int showAnimationBehavior) {
        this.spec.showAnimationBehavior = showAnimationBehavior;
        invalidate();
    }

    public int getHideAnimationBehavior() {
        return this.spec.hideAnimationBehavior;
    }

    public void setHideAnimationBehavior(int hideAnimationBehavior) {
        this.spec.hideAnimationBehavior = hideAnimationBehavior;
        invalidate();
    }

    public synchronized void setProgress(int progress) {
        if (!isIndeterminate()) {
            setProgressCompat(progress, false);
        }
    }

    public void setProgressCompat(int progress, boolean animated) {
        if (!isIndeterminate()) {
            super.setProgress(progress);
            if (getProgressDrawable() != null && !animated) {
                getProgressDrawable().jumpToCurrentState();
            }
        } else if (getProgressDrawable() != null) {
            this.storedProgress = progress;
            this.storedProgressAnimated = animated;
            this.isIndeterminateModeChangeRequested = true;
            if (!getIndeterminateDrawable().isVisible() || this.animatorDurationScaleProvider.getSystemAnimatorDurationScale(getContext().getContentResolver()) == 0.0f) {
                this.switchIndeterminateModeCallback.onAnimationEnd(getIndeterminateDrawable());
            } else {
                getIndeterminateDrawable().getAnimatorDelegate().requestCancelAnimatorAfterCurrentCycle();
            }
        }
    }

    public void setVisibilityAfterHide(int visibility) {
        if (visibility == 0 || visibility == 4 || visibility == 8) {
            this.visibilityAfterHide = visibility;
            return;
        }
        throw new IllegalArgumentException("The component's visibility must be one of VISIBLE, INVISIBLE, and GONE defined in View.");
    }

    public void setAnimatorDurationScaleProvider(AnimatorDurationScaleProvider animatorDurationScaleProvider2) {
        this.animatorDurationScaleProvider = animatorDurationScaleProvider2;
        if (getProgressDrawable() != null) {
            getProgressDrawable().animatorDurationScaleProvider = animatorDurationScaleProvider2;
        }
        if (getIndeterminateDrawable() != null) {
            getIndeterminateDrawable().animatorDurationScaleProvider = animatorDurationScaleProvider2;
        }
    }
}
