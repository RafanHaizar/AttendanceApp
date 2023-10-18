package com.google.android.material.progressindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.google.android.material.C1087R;
import com.google.android.material.internal.ThemeEnforcement;

public final class LinearProgressIndicatorSpec extends BaseProgressIndicatorSpec {
    boolean drawHorizontallyInverse;
    public int indeterminateAnimationType;
    public int indicatorDirection;

    public LinearProgressIndicatorSpec(Context context, AttributeSet attrs) {
        this(context, attrs, C1087R.attr.linearProgressIndicatorStyle);
    }

    public LinearProgressIndicatorSpec(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, LinearProgressIndicator.DEF_STYLE_RES);
    }

    public LinearProgressIndicatorSpec(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        boolean z = false;
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context, attrs, C1087R.styleable.LinearProgressIndicator, C1087R.attr.linearProgressIndicatorStyle, LinearProgressIndicator.DEF_STYLE_RES, new int[0]);
        this.indeterminateAnimationType = a.getInt(C1087R.styleable.LinearProgressIndicator_indeterminateAnimationType, 1);
        this.indicatorDirection = a.getInt(C1087R.styleable.LinearProgressIndicator_indicatorDirectionLinear, 0);
        a.recycle();
        validateSpec();
        this.drawHorizontallyInverse = this.indicatorDirection == 1 ? true : z;
    }

    /* access modifiers changed from: package-private */
    public void validateSpec() {
        if (this.indeterminateAnimationType != 0) {
            return;
        }
        if (this.trackCornerRadius > 0) {
            throw new IllegalArgumentException("Rounded corners are not supported in contiguous indeterminate animation.");
        } else if (this.indicatorColors.length < 3) {
            throw new IllegalArgumentException("Contiguous indeterminate animation must be used with 3 or more indicator colors.");
        }
    }
}
