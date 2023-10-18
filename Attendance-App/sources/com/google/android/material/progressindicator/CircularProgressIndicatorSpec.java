package com.google.android.material.progressindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.google.android.material.C1087R;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;

public final class CircularProgressIndicatorSpec extends BaseProgressIndicatorSpec {
    public int indicatorDirection;
    public int indicatorInset;
    public int indicatorSize;

    public CircularProgressIndicatorSpec(Context context, AttributeSet attrs) {
        this(context, attrs, C1087R.attr.circularProgressIndicatorStyle);
    }

    public CircularProgressIndicatorSpec(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, CircularProgressIndicator.DEF_STYLE_RES);
    }

    public CircularProgressIndicatorSpec(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        int defaultIndicatorSize = context.getResources().getDimensionPixelSize(C1087R.dimen.mtrl_progress_circular_size_medium);
        int defaultIndicatorInset = context.getResources().getDimensionPixelSize(C1087R.dimen.mtrl_progress_circular_inset_medium);
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context, attrs, C1087R.styleable.CircularProgressIndicator, defStyleAttr, defStyleRes, new int[0]);
        this.indicatorSize = Math.max(MaterialResources.getDimensionPixelSize(context, a, C1087R.styleable.CircularProgressIndicator_indicatorSize, defaultIndicatorSize), this.trackThickness * 2);
        this.indicatorInset = MaterialResources.getDimensionPixelSize(context, a, C1087R.styleable.CircularProgressIndicator_indicatorInset, defaultIndicatorInset);
        this.indicatorDirection = a.getInt(C1087R.styleable.CircularProgressIndicator_indicatorDirectionCircular, 0);
        a.recycle();
        validateSpec();
    }

    /* access modifiers changed from: package-private */
    public void validateSpec() {
    }
}
