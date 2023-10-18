package com.google.android.material.timepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.ViewCompat;
import com.google.android.material.C1087R;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.RelativeCornerSize;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class RadialViewGroup extends ConstraintLayout {
    static final int LEVEL_1 = 1;
    static final int LEVEL_2 = 2;
    static final float LEVEL_RADIUS_RATIO = 0.66f;
    private static final String SKIP_TAG = "skip";
    private MaterialShapeDrawable background;
    private int radius;
    private final Runnable updateLayoutParametersRunnable;

    public RadialViewGroup(Context context) {
        this(context, (AttributeSet) null);
    }

    public RadialViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadialViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(C1087R.C1092layout.material_radial_view_group, this);
        ViewCompat.setBackground(this, createBackground());
        TypedArray a = context.obtainStyledAttributes(attrs, C1087R.styleable.RadialViewGroup, defStyleAttr, 0);
        this.radius = a.getDimensionPixelSize(C1087R.styleable.RadialViewGroup_materialCircleRadius, 0);
        this.updateLayoutParametersRunnable = new RadialViewGroup$$ExternalSyntheticLambda0(this);
        a.recycle();
    }

    private Drawable createBackground() {
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
        this.background = materialShapeDrawable;
        materialShapeDrawable.setCornerSize((CornerSize) new RelativeCornerSize(0.5f));
        this.background.setFillColor(ColorStateList.valueOf(-1));
        return this.background;
    }

    public void setBackgroundColor(int color) {
        this.background.setFillColor(ColorStateList.valueOf(color));
    }

    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (child.getId() == -1) {
            child.setId(ViewCompat.generateViewId());
        }
        updateLayoutParamsAsync();
    }

    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        updateLayoutParamsAsync();
    }

    private void updateLayoutParamsAsync() {
        Handler handler = getHandler();
        if (handler != null) {
            handler.removeCallbacks(this.updateLayoutParametersRunnable);
            handler.post(this.updateLayoutParametersRunnable);
        }
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        updateLayoutParams();
    }

    /* access modifiers changed from: protected */
    public void updateLayoutParams() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) this);
        Map<Integer, List<View>> levels = new HashMap<>();
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getId() != C1087R.C1090id.circle_center && !shouldSkipView(childAt)) {
                int level = (Integer) childAt.getTag(C1087R.C1090id.material_clock_level);
                if (level == null) {
                    level = 1;
                }
                if (!levels.containsKey(level)) {
                    levels.put(level, new ArrayList());
                }
                levels.get(level).add(childAt);
            }
        }
        for (Map.Entry<Integer, List<View>> entry : levels.entrySet()) {
            addConstraints(entry.getValue(), constraintSet, getLeveledRadius(entry.getKey().intValue()));
        }
        constraintSet.applyTo(this);
    }

    private void addConstraints(List<View> views, ConstraintSet constraintSet, int leveledRadius) {
        float currentAngle = 0.0f;
        for (View view : views) {
            constraintSet.constrainCircle(view.getId(), C1087R.C1090id.circle_center, leveledRadius, currentAngle);
            currentAngle += 360.0f / ((float) views.size());
        }
    }

    public void setRadius(int radius2) {
        this.radius = radius2;
        updateLayoutParams();
    }

    public int getRadius() {
        return this.radius;
    }

    /* access modifiers changed from: package-private */
    public int getLeveledRadius(int level) {
        return level == 2 ? Math.round(((float) this.radius) * LEVEL_RADIUS_RATIO) : this.radius;
    }

    private static boolean shouldSkipView(View child) {
        return SKIP_TAG.equals(child.getTag());
    }
}
