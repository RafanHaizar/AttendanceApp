package com.google.android.material.progressindicator;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.core.view.ViewCompat;
import com.google.android.material.C1087R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class LinearProgressIndicator extends BaseProgressIndicator<LinearProgressIndicatorSpec> {
    public static final int DEF_STYLE_RES = C1087R.C1093style.Widget_MaterialComponents_LinearProgressIndicator;
    public static final int INDETERMINATE_ANIMATION_TYPE_CONTIGUOUS = 0;
    public static final int INDETERMINATE_ANIMATION_TYPE_DISJOINT = 1;
    public static final int INDICATOR_DIRECTION_END_TO_START = 3;
    public static final int INDICATOR_DIRECTION_LEFT_TO_RIGHT = 0;
    public static final int INDICATOR_DIRECTION_RIGHT_TO_LEFT = 1;
    public static final int INDICATOR_DIRECTION_START_TO_END = 2;

    @Retention(RetentionPolicy.SOURCE)
    public @interface IndeterminateAnimationType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface IndicatorDirection {
    }

    public LinearProgressIndicator(Context context) {
        this(context, (AttributeSet) null);
    }

    public LinearProgressIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, C1087R.attr.linearProgressIndicatorStyle);
    }

    public LinearProgressIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, DEF_STYLE_RES);
        initializeDrawables();
    }

    /* access modifiers changed from: package-private */
    public LinearProgressIndicatorSpec createSpec(Context context, AttributeSet attrs) {
        return new LinearProgressIndicatorSpec(context, attrs);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        LinearProgressIndicatorSpec linearProgressIndicatorSpec = (LinearProgressIndicatorSpec) this.spec;
        boolean z = true;
        if (!(((LinearProgressIndicatorSpec) this.spec).indicatorDirection == 1 || ((ViewCompat.getLayoutDirection(this) == 1 && ((LinearProgressIndicatorSpec) this.spec).indicatorDirection == 2) || (ViewCompat.getLayoutDirection(this) == 0 && ((LinearProgressIndicatorSpec) this.spec).indicatorDirection == 3)))) {
            z = false;
        }
        linearProgressIndicatorSpec.drawHorizontallyInverse = z;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        int contentWidth = w - (getPaddingLeft() + getPaddingRight());
        int contentHeight = h - (getPaddingTop() + getPaddingBottom());
        Drawable drawable = getIndeterminateDrawable();
        if (drawable != null) {
            drawable.setBounds(0, 0, contentWidth, contentHeight);
        }
        Drawable drawable2 = getProgressDrawable();
        if (drawable2 != null) {
            drawable2.setBounds(0, 0, contentWidth, contentHeight);
        }
    }

    private void initializeDrawables() {
        setIndeterminateDrawable(IndeterminateDrawable.createLinearDrawable(getContext(), (LinearProgressIndicatorSpec) this.spec));
        setProgressDrawable(DeterminateDrawable.createLinearDrawable(getContext(), (LinearProgressIndicatorSpec) this.spec));
    }

    public void setIndicatorColor(int... indicatorColors) {
        super.setIndicatorColor(indicatorColors);
        ((LinearProgressIndicatorSpec) this.spec).validateSpec();
    }

    public void setTrackCornerRadius(int trackCornerRadius) {
        super.setTrackCornerRadius(trackCornerRadius);
        ((LinearProgressIndicatorSpec) this.spec).validateSpec();
        invalidate();
    }

    public int getIndeterminateAnimationType() {
        return ((LinearProgressIndicatorSpec) this.spec).indeterminateAnimationType;
    }

    public void setIndeterminateAnimationType(int indeterminateAnimationType) {
        if (((LinearProgressIndicatorSpec) this.spec).indeterminateAnimationType != indeterminateAnimationType) {
            if (!visibleToUser() || !isIndeterminate()) {
                ((LinearProgressIndicatorSpec) this.spec).indeterminateAnimationType = indeterminateAnimationType;
                ((LinearProgressIndicatorSpec) this.spec).validateSpec();
                if (indeterminateAnimationType == 0) {
                    getIndeterminateDrawable().setAnimatorDelegate(new LinearIndeterminateContiguousAnimatorDelegate((LinearProgressIndicatorSpec) this.spec));
                } else {
                    getIndeterminateDrawable().setAnimatorDelegate(new LinearIndeterminateDisjointAnimatorDelegate(getContext(), (LinearProgressIndicatorSpec) this.spec));
                }
                invalidate();
                return;
            }
            throw new IllegalStateException("Cannot change indeterminate animation type while the progress indicator is show in indeterminate mode.");
        }
    }

    public int getIndicatorDirection() {
        return ((LinearProgressIndicatorSpec) this.spec).indicatorDirection;
    }

    public void setIndicatorDirection(int indicatorDirection) {
        ((LinearProgressIndicatorSpec) this.spec).indicatorDirection = indicatorDirection;
        LinearProgressIndicatorSpec linearProgressIndicatorSpec = (LinearProgressIndicatorSpec) this.spec;
        boolean z = true;
        if (!(indicatorDirection == 1 || ((ViewCompat.getLayoutDirection(this) == 1 && ((LinearProgressIndicatorSpec) this.spec).indicatorDirection == 2) || (ViewCompat.getLayoutDirection(this) == 0 && indicatorDirection == 3)))) {
            z = false;
        }
        linearProgressIndicatorSpec.drawHorizontallyInverse = z;
        invalidate();
    }

    public void setProgressCompat(int progress, boolean animated) {
        if (this.spec == null || ((LinearProgressIndicatorSpec) this.spec).indeterminateAnimationType != 0 || !isIndeterminate()) {
            super.setProgressCompat(progress, animated);
        }
    }
}
