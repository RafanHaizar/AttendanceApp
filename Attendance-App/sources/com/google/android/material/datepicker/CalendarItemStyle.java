package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.widget.TextView;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import com.google.android.material.C1087R;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;

final class CalendarItemStyle {
    private final ColorStateList backgroundColor;
    private final Rect insets;
    private final ShapeAppearanceModel itemShape;
    private final ColorStateList strokeColor;
    private final int strokeWidth;
    private final ColorStateList textColor;

    private CalendarItemStyle(ColorStateList backgroundColor2, ColorStateList textColor2, ColorStateList strokeColor2, int strokeWidth2, ShapeAppearanceModel itemShape2, Rect insets2) {
        Preconditions.checkArgumentNonnegative(insets2.left);
        Preconditions.checkArgumentNonnegative(insets2.top);
        Preconditions.checkArgumentNonnegative(insets2.right);
        Preconditions.checkArgumentNonnegative(insets2.bottom);
        this.insets = insets2;
        this.textColor = textColor2;
        this.backgroundColor = backgroundColor2;
        this.strokeColor = strokeColor2;
        this.strokeWidth = strokeWidth2;
        this.itemShape = itemShape2;
    }

    static CalendarItemStyle create(Context context, int materialCalendarItemStyle) {
        Context context2 = context;
        int i = materialCalendarItemStyle;
        Preconditions.checkArgument(i != 0, "Cannot create a CalendarItemStyle with a styleResId of 0");
        TypedArray styleableArray = context2.obtainStyledAttributes(i, C1087R.styleable.MaterialCalendarItem);
        Rect insets2 = new Rect(styleableArray.getDimensionPixelOffset(C1087R.styleable.MaterialCalendarItem_android_insetLeft, 0), styleableArray.getDimensionPixelOffset(C1087R.styleable.MaterialCalendarItem_android_insetTop, 0), styleableArray.getDimensionPixelOffset(C1087R.styleable.MaterialCalendarItem_android_insetRight, 0), styleableArray.getDimensionPixelOffset(C1087R.styleable.MaterialCalendarItem_android_insetBottom, 0));
        ColorStateList backgroundColor2 = MaterialResources.getColorStateList(context2, styleableArray, C1087R.styleable.MaterialCalendarItem_itemFillColor);
        ColorStateList textColor2 = MaterialResources.getColorStateList(context2, styleableArray, C1087R.styleable.MaterialCalendarItem_itemTextColor);
        ColorStateList strokeColor2 = MaterialResources.getColorStateList(context2, styleableArray, C1087R.styleable.MaterialCalendarItem_itemStrokeColor);
        int strokeWidth2 = styleableArray.getDimensionPixelSize(C1087R.styleable.MaterialCalendarItem_itemStrokeWidth, 0);
        int shapeAppearanceResId = styleableArray.getResourceId(C1087R.styleable.MaterialCalendarItem_itemShapeAppearance, 0);
        ShapeAppearanceModel itemShape2 = ShapeAppearanceModel.builder(context2, shapeAppearanceResId, styleableArray.getResourceId(C1087R.styleable.MaterialCalendarItem_itemShapeAppearanceOverlay, 0)).build();
        styleableArray.recycle();
        int i2 = shapeAppearanceResId;
        return new CalendarItemStyle(backgroundColor2, textColor2, strokeColor2, strokeWidth2, itemShape2, insets2);
    }

    /* access modifiers changed from: package-private */
    public void styleItem(TextView item) {
        styleItem(item, (ColorStateList) null);
    }

    /* access modifiers changed from: package-private */
    public void styleItem(TextView item, ColorStateList backgroundColorOverride) {
        Drawable d;
        MaterialShapeDrawable backgroundDrawable = new MaterialShapeDrawable();
        MaterialShapeDrawable shapeMask = new MaterialShapeDrawable();
        backgroundDrawable.setShapeAppearanceModel(this.itemShape);
        shapeMask.setShapeAppearanceModel(this.itemShape);
        backgroundDrawable.setFillColor(backgroundColorOverride != null ? backgroundColorOverride : this.backgroundColor);
        backgroundDrawable.setStroke((float) this.strokeWidth, this.strokeColor);
        item.setTextColor(this.textColor);
        if (Build.VERSION.SDK_INT >= 21) {
            d = new RippleDrawable(this.textColor.withAlpha(30), backgroundDrawable, shapeMask);
        } else {
            d = backgroundDrawable;
        }
        ViewCompat.setBackground(item, new InsetDrawable(d, this.insets.left, this.insets.top, this.insets.right, this.insets.bottom));
    }

    /* access modifiers changed from: package-private */
    public int getLeftInset() {
        return this.insets.left;
    }

    /* access modifiers changed from: package-private */
    public int getRightInset() {
        return this.insets.right;
    }

    /* access modifiers changed from: package-private */
    public int getTopInset() {
        return this.insets.top;
    }

    /* access modifiers changed from: package-private */
    public int getBottomInset() {
        return this.insets.bottom;
    }
}
