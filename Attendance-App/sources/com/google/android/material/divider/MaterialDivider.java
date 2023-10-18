package com.google.android.material.divider;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.C1087R;
import com.google.android.material.shape.MaterialShapeDrawable;

public class MaterialDivider extends View {
    private static final int DEF_STYLE_RES = C1087R.C1093style.Widget_MaterialComponents_MaterialDivider;
    private int color;
    private final MaterialShapeDrawable dividerDrawable;
    private int insetEnd;
    private int insetStart;
    private int thickness;

    public MaterialDivider(Context context) {
        this(context, (AttributeSet) null);
    }

    public MaterialDivider(Context context, AttributeSet attrs) {
        this(context, attrs, C1087R.attr.materialDividerStyle);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MaterialDivider(android.content.Context r8, android.util.AttributeSet r9, int r10) {
        /*
            r7 = this;
            int r4 = DEF_STYLE_RES
            android.content.Context r0 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r8, r9, r10, r4)
            r7.<init>(r0, r9, r10)
            android.content.Context r8 = r7.getContext()
            com.google.android.material.shape.MaterialShapeDrawable r0 = new com.google.android.material.shape.MaterialShapeDrawable
            r0.<init>()
            r7.dividerDrawable = r0
            int[] r2 = com.google.android.material.C1087R.styleable.MaterialDivider
            r6 = 0
            int[] r5 = new int[r6]
            r0 = r8
            r1 = r9
            r3 = r10
            android.content.res.TypedArray r0 = com.google.android.material.internal.ThemeEnforcement.obtainStyledAttributes(r0, r1, r2, r3, r4, r5)
            int r1 = com.google.android.material.C1087R.styleable.MaterialDivider_dividerThickness
            android.content.res.Resources r2 = r7.getResources()
            int r3 = com.google.android.material.C1087R.dimen.material_divider_thickness
            int r2 = r2.getDimensionPixelSize(r3)
            int r1 = r0.getDimensionPixelSize(r1, r2)
            r7.thickness = r1
            int r1 = com.google.android.material.C1087R.styleable.MaterialDivider_dividerInsetStart
            int r1 = r0.getDimensionPixelOffset(r1, r6)
            r7.insetStart = r1
            int r1 = com.google.android.material.C1087R.styleable.MaterialDivider_dividerInsetEnd
            int r1 = r0.getDimensionPixelOffset(r1, r6)
            r7.insetEnd = r1
            int r1 = com.google.android.material.C1087R.styleable.MaterialDivider_dividerColor
            android.content.res.ColorStateList r1 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r8, (android.content.res.TypedArray) r0, (int) r1)
            int r1 = r1.getDefaultColor()
            r7.setDividerColor(r1)
            r0.recycle()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.divider.MaterialDivider.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int newThickness = getMeasuredHeight();
        if (heightMode == Integer.MIN_VALUE || heightMode == 0) {
            int i = this.thickness;
            if (i > 0 && newThickness != i) {
                newThickness = this.thickness;
            }
            setMeasuredDimension(getMeasuredWidth(), newThickness);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        boolean z = true;
        if (ViewCompat.getLayoutDirection(this) != 1) {
            z = false;
        }
        boolean isRtl = z;
        this.dividerDrawable.setBounds(isRtl ? this.insetEnd : this.insetStart, 0, getWidth() - (isRtl ? this.insetStart : this.insetEnd), getBottom() - getTop());
        this.dividerDrawable.draw(canvas);
    }

    public void setDividerThickness(int thickness2) {
        if (this.thickness != thickness2) {
            this.thickness = thickness2;
            requestLayout();
        }
    }

    public void setDividerThicknessResource(int thicknessId) {
        setDividerThickness(getContext().getResources().getDimensionPixelSize(thicknessId));
    }

    public int getDividerThickness() {
        return this.thickness;
    }

    public void setDividerInsetStart(int insetStart2) {
        this.insetStart = insetStart2;
    }

    public void setDividerInsetStartResource(int insetStartId) {
        setDividerInsetStart(getContext().getResources().getDimensionPixelOffset(insetStartId));
    }

    public int getDividerInsetStart() {
        return this.insetStart;
    }

    public void setDividerInsetEnd(int insetEnd2) {
        this.insetEnd = insetEnd2;
    }

    public void setDividerInsetEndResource(int insetEndId) {
        setDividerInsetEnd(getContext().getResources().getDimensionPixelOffset(insetEndId));
    }

    public int getDividerInsetEnd() {
        return this.insetEnd;
    }

    public void setDividerColor(int color2) {
        if (this.color != color2) {
            this.color = color2;
            this.dividerDrawable.setFillColor(ColorStateList.valueOf(color2));
            invalidate();
        }
    }

    public void setDividerColorResource(int colorId) {
        setDividerColor(ContextCompat.getColor(getContext(), colorId));
    }

    public int getDividerColor() {
        return this.color;
    }
}
