package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.Property;
import android.view.animation.Interpolator;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimationUtilsCompat;
import com.google.android.material.C1087R;
import com.google.android.material.color.MaterialColors;
import com.itextpdf.p026io.codec.TIFFConstants;
import java.util.Arrays;

final class LinearIndeterminateDisjointAnimatorDelegate extends IndeterminateAnimatorDelegate<ObjectAnimator> {
    private static final Property<LinearIndeterminateDisjointAnimatorDelegate, Float> ANIMATION_FRACTION = new Property<LinearIndeterminateDisjointAnimatorDelegate, Float>(Float.class, "animationFraction") {
        public Float get(LinearIndeterminateDisjointAnimatorDelegate delegate) {
            return Float.valueOf(delegate.getAnimationFraction());
        }

        public void set(LinearIndeterminateDisjointAnimatorDelegate delegate, Float value) {
            delegate.setAnimationFraction(value.floatValue());
        }
    };
    private static final int[] DELAY_TO_MOVE_SEGMENT_ENDS = {1267, 1000, TIFFConstants.TIFFTAG_INKNAMES, 0};
    private static final int[] DURATION_TO_MOVE_SEGMENT_ENDS = {533, 567, 850, 750};
    private static final int TOTAL_DURATION_IN_MS = 1800;
    private float animationFraction;
    private ObjectAnimator animator;
    Animatable2Compat.AnimationCallback animatorCompleteCallback = null;
    /* access modifiers changed from: private */
    public final BaseProgressIndicatorSpec baseSpec;
    private ObjectAnimator completeEndAnimator;
    /* access modifiers changed from: private */
    public boolean dirtyColors;
    /* access modifiers changed from: private */
    public int indicatorColorIndex = 0;
    private final Interpolator[] interpolatorArray;

    public LinearIndeterminateDisjointAnimatorDelegate(Context context, LinearProgressIndicatorSpec spec) {
        super(2);
        this.baseSpec = spec;
        this.interpolatorArray = new Interpolator[]{AnimationUtilsCompat.loadInterpolator(context, C1087R.anim.linear_indeterminate_line1_head_interpolator), AnimationUtilsCompat.loadInterpolator(context, C1087R.anim.linear_indeterminate_line1_tail_interpolator), AnimationUtilsCompat.loadInterpolator(context, C1087R.anim.linear_indeterminate_line2_head_interpolator), AnimationUtilsCompat.loadInterpolator(context, C1087R.anim.linear_indeterminate_line2_tail_interpolator)};
    }

    public void startAnimator() {
        maybeInitializeAnimators();
        resetPropertiesForNewStart();
        this.animator.start();
    }

    private void maybeInitializeAnimators() {
        if (this.animator == null) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, ANIMATION_FRACTION, new float[]{0.0f, 1.0f});
            this.animator = ofFloat;
            ofFloat.setDuration(1800);
            this.animator.setInterpolator((TimeInterpolator) null);
            this.animator.setRepeatCount(-1);
            this.animator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationRepeat(Animator animation) {
                    super.onAnimationRepeat(animation);
                    LinearIndeterminateDisjointAnimatorDelegate linearIndeterminateDisjointAnimatorDelegate = LinearIndeterminateDisjointAnimatorDelegate.this;
                    int unused = linearIndeterminateDisjointAnimatorDelegate.indicatorColorIndex = (linearIndeterminateDisjointAnimatorDelegate.indicatorColorIndex + 1) % LinearIndeterminateDisjointAnimatorDelegate.this.baseSpec.indicatorColors.length;
                    boolean unused2 = LinearIndeterminateDisjointAnimatorDelegate.this.dirtyColors = true;
                }
            });
        }
        if (this.completeEndAnimator == null) {
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, ANIMATION_FRACTION, new float[]{1.0f});
            this.completeEndAnimator = ofFloat2;
            ofFloat2.setDuration(1800);
            this.completeEndAnimator.setInterpolator((TimeInterpolator) null);
            this.completeEndAnimator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    LinearIndeterminateDisjointAnimatorDelegate.this.cancelAnimatorImmediately();
                    if (LinearIndeterminateDisjointAnimatorDelegate.this.animatorCompleteCallback != null) {
                        LinearIndeterminateDisjointAnimatorDelegate.this.animatorCompleteCallback.onAnimationEnd(LinearIndeterminateDisjointAnimatorDelegate.this.drawable);
                    }
                }
            });
        }
    }

    public void cancelAnimatorImmediately() {
        ObjectAnimator objectAnimator = this.animator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    public void requestCancelAnimatorAfterCurrentCycle() {
        ObjectAnimator objectAnimator = this.completeEndAnimator;
        if (objectAnimator != null && !objectAnimator.isRunning()) {
            cancelAnimatorImmediately();
            if (this.drawable.isVisible()) {
                this.completeEndAnimator.setFloatValues(new float[]{this.animationFraction, 1.0f});
                this.completeEndAnimator.setDuration((long) ((1.0f - this.animationFraction) * 1800.0f));
                this.completeEndAnimator.start();
            }
        }
    }

    public void invalidateSpecValues() {
        resetPropertiesForNewStart();
    }

    public void registerAnimatorsCompleteCallback(Animatable2Compat.AnimationCallback callback) {
        this.animatorCompleteCallback = callback;
    }

    public void unregisterAnimatorsCompleteCallback() {
        this.animatorCompleteCallback = null;
    }

    private void updateSegmentPositions(int playtime) {
        for (int i = 0; i < 4; i++) {
            this.segmentPositions[i] = Math.max(0.0f, Math.min(1.0f, this.interpolatorArray[i].getInterpolation(getFractionInRange(playtime, DELAY_TO_MOVE_SEGMENT_ENDS[i], DURATION_TO_MOVE_SEGMENT_ENDS[i]))));
        }
    }

    private void maybeUpdateSegmentColors() {
        if (this.dirtyColors) {
            Arrays.fill(this.segmentColors, MaterialColors.compositeARGBWithAlpha(this.baseSpec.indicatorColors[this.indicatorColorIndex], this.drawable.getAlpha()));
            this.dirtyColors = false;
        }
    }

    /* access modifiers changed from: package-private */
    public void resetPropertiesForNewStart() {
        this.indicatorColorIndex = 0;
        int indicatorColor = MaterialColors.compositeARGBWithAlpha(this.baseSpec.indicatorColors[0], this.drawable.getAlpha());
        this.segmentColors[0] = indicatorColor;
        this.segmentColors[1] = indicatorColor;
    }

    /* access modifiers changed from: private */
    public float getAnimationFraction() {
        return this.animationFraction;
    }

    /* access modifiers changed from: package-private */
    public void setAnimationFraction(float fraction) {
        this.animationFraction = fraction;
        updateSegmentPositions((int) (1800.0f * fraction));
        maybeUpdateSegmentColors();
        this.drawable.invalidateSelf();
    }
}
