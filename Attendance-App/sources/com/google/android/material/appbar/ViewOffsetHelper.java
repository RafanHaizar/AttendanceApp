package com.google.android.material.appbar;

import android.view.View;
import androidx.core.view.ViewCompat;

class ViewOffsetHelper {
    private boolean horizontalOffsetEnabled = true;
    private int layoutLeft;
    private int layoutTop;
    private int offsetLeft;
    private int offsetTop;
    private boolean verticalOffsetEnabled = true;
    private final View view;

    public ViewOffsetHelper(View view2) {
        this.view = view2;
    }

    /* access modifiers changed from: package-private */
    public void onViewLayout() {
        this.layoutTop = this.view.getTop();
        this.layoutLeft = this.view.getLeft();
    }

    /* access modifiers changed from: package-private */
    public void applyOffsets() {
        View view2 = this.view;
        ViewCompat.offsetTopAndBottom(view2, this.offsetTop - (view2.getTop() - this.layoutTop));
        View view3 = this.view;
        ViewCompat.offsetLeftAndRight(view3, this.offsetLeft - (view3.getLeft() - this.layoutLeft));
    }

    public boolean setTopAndBottomOffset(int offset) {
        if (!this.verticalOffsetEnabled || this.offsetTop == offset) {
            return false;
        }
        this.offsetTop = offset;
        applyOffsets();
        return true;
    }

    public boolean setLeftAndRightOffset(int offset) {
        if (!this.horizontalOffsetEnabled || this.offsetLeft == offset) {
            return false;
        }
        this.offsetLeft = offset;
        applyOffsets();
        return true;
    }

    public int getTopAndBottomOffset() {
        return this.offsetTop;
    }

    public int getLeftAndRightOffset() {
        return this.offsetLeft;
    }

    public int getLayoutTop() {
        return this.layoutTop;
    }

    public int getLayoutLeft() {
        return this.layoutLeft;
    }

    public void setVerticalOffsetEnabled(boolean verticalOffsetEnabled2) {
        this.verticalOffsetEnabled = verticalOffsetEnabled2;
    }

    public boolean isVerticalOffsetEnabled() {
        return this.verticalOffsetEnabled;
    }

    public void setHorizontalOffsetEnabled(boolean horizontalOffsetEnabled2) {
        this.horizontalOffsetEnabled = horizontalOffsetEnabled2;
    }

    public boolean isHorizontalOffsetEnabled() {
        return this.horizontalOffsetEnabled;
    }
}
