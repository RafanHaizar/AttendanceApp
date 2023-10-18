package com.google.android.material.divider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.C1087R;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;

public class MaterialDividerItemDecoration extends RecyclerView.ItemDecoration {
    private static final int DEF_STYLE_RES = C1087R.C1093style.Widget_MaterialComponents_MaterialDivider;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private int color;
    private Drawable dividerDrawable;
    private int insetEnd;
    private int insetStart;
    private boolean lastItemDecorated;
    private int orientation;
    private final Rect tempRect;
    private int thickness;

    public MaterialDividerItemDecoration(Context context, int orientation2) {
        this(context, (AttributeSet) null, orientation2);
    }

    public MaterialDividerItemDecoration(Context context, AttributeSet attrs, int orientation2) {
        this(context, attrs, C1087R.attr.materialDividerStyle, orientation2);
    }

    public MaterialDividerItemDecoration(Context context, AttributeSet attrs, int defStyleAttr, int orientation2) {
        this.tempRect = new Rect();
        TypedArray attributes = ThemeEnforcement.obtainStyledAttributes(context, attrs, C1087R.styleable.MaterialDivider, defStyleAttr, DEF_STYLE_RES, new int[0]);
        this.color = MaterialResources.getColorStateList(context, attributes, C1087R.styleable.MaterialDivider_dividerColor).getDefaultColor();
        this.thickness = attributes.getDimensionPixelSize(C1087R.styleable.MaterialDivider_dividerThickness, context.getResources().getDimensionPixelSize(C1087R.dimen.material_divider_thickness));
        this.insetStart = attributes.getDimensionPixelOffset(C1087R.styleable.MaterialDivider_dividerInsetStart, 0);
        this.insetEnd = attributes.getDimensionPixelOffset(C1087R.styleable.MaterialDivider_dividerInsetEnd, 0);
        this.lastItemDecorated = attributes.getBoolean(C1087R.styleable.MaterialDivider_lastItemDecorated, true);
        attributes.recycle();
        this.dividerDrawable = new ShapeDrawable();
        setDividerColor(this.color);
        setOrientation(orientation2);
    }

    public void setOrientation(int orientation2) {
        if (orientation2 == 0 || orientation2 == 1) {
            this.orientation = orientation2;
            return;
        }
        throw new IllegalArgumentException("Invalid orientation: " + orientation2 + ". It should be either HORIZONTAL or VERTICAL");
    }

    public int getOrientation() {
        return this.orientation;
    }

    public void setDividerThickness(int thickness2) {
        this.thickness = thickness2;
    }

    public void setDividerThicknessResource(Context context, int thicknessId) {
        setDividerThickness(context.getResources().getDimensionPixelSize(thicknessId));
    }

    public int getDividerThickness() {
        return this.thickness;
    }

    public void setDividerColor(int color2) {
        this.color = color2;
        Drawable wrap = DrawableCompat.wrap(this.dividerDrawable);
        this.dividerDrawable = wrap;
        DrawableCompat.setTint(wrap, color2);
    }

    public void setDividerColorResource(Context context, int colorId) {
        setDividerColor(ContextCompat.getColor(context, colorId));
    }

    public int getDividerColor() {
        return this.color;
    }

    public void setDividerInsetStart(int insetStart2) {
        this.insetStart = insetStart2;
    }

    public void setDividerInsetStartResource(Context context, int insetStartId) {
        setDividerInsetStart(context.getResources().getDimensionPixelOffset(insetStartId));
    }

    public int getDividerInsetStart() {
        return this.insetStart;
    }

    public void setDividerInsetEnd(int insetEnd2) {
        this.insetEnd = insetEnd2;
    }

    public void setDividerInsetEndResource(Context context, int insetEndId) {
        setDividerInsetEnd(context.getResources().getDimensionPixelOffset(insetEndId));
    }

    public int getDividerInsetEnd() {
        return this.insetEnd;
    }

    public void setLastItemDecorated(boolean lastItemDecorated2) {
        this.lastItemDecorated = lastItemDecorated2;
    }

    public boolean isLastItemDecorated() {
        return this.lastItemDecorated;
    }

    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() != null) {
            if (this.orientation == 1) {
                drawForVerticalOrientation(canvas, parent);
            } else {
                drawForHorizontalOrientation(canvas, parent);
            }
        }
    }

    private void drawForVerticalOrientation(Canvas canvas, RecyclerView parent) {
        int right;
        int left;
        canvas.save();
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right, parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }
        boolean z = true;
        if (ViewCompat.getLayoutDirection(parent) != 1) {
            z = false;
        }
        boolean isRtl = z;
        int left2 = left + (isRtl ? this.insetEnd : this.insetStart);
        int right2 = right - (isRtl ? this.insetStart : this.insetEnd);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (shouldDrawDivider(parent, child)) {
                parent.getDecoratedBoundsWithMargins(child, this.tempRect);
                int bottom = this.tempRect.bottom + Math.round(child.getTranslationY());
                this.dividerDrawable.setBounds(left2, bottom - this.thickness, right2, bottom);
                this.dividerDrawable.draw(canvas);
            }
        }
        canvas.restore();
    }

    private void drawForHorizontalOrientation(Canvas canvas, RecyclerView parent) {
        int bottom;
        int top;
        canvas.save();
        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), top, parent.getWidth() - parent.getPaddingRight(), bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }
        int top2 = top + this.insetStart;
        int bottom2 = bottom - this.insetEnd;
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (shouldDrawDivider(parent, child)) {
                parent.getDecoratedBoundsWithMargins(child, this.tempRect);
                int right = this.tempRect.right + Math.round(child.getTranslationX());
                this.dividerDrawable.setBounds(right - this.thickness, top2, right, bottom2);
                this.dividerDrawable.draw(canvas);
            }
        }
        canvas.restore();
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, 0);
        if (!shouldDrawDivider(parent, view)) {
            return;
        }
        if (this.orientation == 1) {
            outRect.bottom = this.thickness;
        } else {
            outRect.right = this.thickness;
        }
    }

    private boolean shouldDrawDivider(RecyclerView parent, View child) {
        int position = parent.getChildAdapterPosition(child);
        RecyclerView.Adapter<?> adapter = parent.getAdapter();
        boolean isLastItem = adapter != null && position == adapter.getItemCount() - 1;
        if (position == -1) {
            return false;
        }
        if ((!isLastItem || this.lastItemDecorated) && shouldDrawDivider(position, adapter)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean shouldDrawDivider(int position, RecyclerView.Adapter<?> adapter) {
        return true;
    }
}
