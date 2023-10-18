package com.google.android.material.progressindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.google.android.material.C1087R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;

public abstract class BaseProgressIndicatorSpec {
    public int hideAnimationBehavior;
    public int[] indicatorColors = new int[0];
    public int showAnimationBehavior;
    public int trackColor;
    public int trackCornerRadius;
    public int trackThickness;

    /* access modifiers changed from: package-private */
    public abstract void validateSpec();

    protected BaseProgressIndicatorSpec(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        int defaultIndicatorSize = context.getResources().getDimensionPixelSize(C1087R.dimen.mtrl_progress_track_thickness);
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context, attrs, C1087R.styleable.BaseProgressIndicator, defStyleAttr, defStyleRes, new int[0]);
        this.trackThickness = MaterialResources.getDimensionPixelSize(context, a, C1087R.styleable.BaseProgressIndicator_trackThickness, defaultIndicatorSize);
        this.trackCornerRadius = Math.min(MaterialResources.getDimensionPixelSize(context, a, C1087R.styleable.BaseProgressIndicator_trackCornerRadius, 0), this.trackThickness / 2);
        this.showAnimationBehavior = a.getInt(C1087R.styleable.BaseProgressIndicator_showAnimationBehavior, 0);
        this.hideAnimationBehavior = a.getInt(C1087R.styleable.BaseProgressIndicator_hideAnimationBehavior, 0);
        loadIndicatorColors(context, a);
        loadTrackColor(context, a);
        a.recycle();
    }

    private void loadIndicatorColors(Context context, TypedArray typedArray) {
        if (!typedArray.hasValue(C1087R.styleable.BaseProgressIndicator_indicatorColor)) {
            this.indicatorColors = new int[]{MaterialColors.getColor(context, C1087R.attr.colorPrimary, -1)};
        } else if (typedArray.peekValue(C1087R.styleable.BaseProgressIndicator_indicatorColor).type != 1) {
            this.indicatorColors = new int[]{typedArray.getColor(C1087R.styleable.BaseProgressIndicator_indicatorColor, -1)};
        } else {
            int[] intArray = context.getResources().getIntArray(typedArray.getResourceId(C1087R.styleable.BaseProgressIndicator_indicatorColor, -1));
            this.indicatorColors = intArray;
            if (intArray.length == 0) {
                throw new IllegalArgumentException("indicatorColors cannot be empty when indicatorColor is not used.");
            }
        }
    }

    private void loadTrackColor(Context context, TypedArray typedArray) {
        if (typedArray.hasValue(C1087R.styleable.BaseProgressIndicator_trackColor)) {
            this.trackColor = typedArray.getColor(C1087R.styleable.BaseProgressIndicator_trackColor, -1);
            return;
        }
        this.trackColor = this.indicatorColors[0];
        TypedArray disabledAlphaArray = context.getTheme().obtainStyledAttributes(new int[]{16842803});
        float defaultOpacity = disabledAlphaArray.getFloat(0, 0.2f);
        disabledAlphaArray.recycle();
        this.trackColor = MaterialColors.compositeARGBWithAlpha(this.trackColor, (int) (255.0f * defaultOpacity));
    }

    public boolean isShowAnimationEnabled() {
        return this.showAnimationBehavior != 0;
    }

    public boolean isHideAnimationEnabled() {
        return this.hideAnimationBehavior != 0;
    }
}
