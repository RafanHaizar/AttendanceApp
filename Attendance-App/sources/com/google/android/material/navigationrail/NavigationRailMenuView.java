package com.google.android.material.navigationrail;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarMenuView;

public class NavigationRailMenuView extends NavigationBarMenuView {
    private int itemMinimumHeight = -1;
    private final FrameLayout.LayoutParams layoutParams;

    public NavigationRailMenuView(Context context) {
        super(context);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -2);
        this.layoutParams = layoutParams2;
        layoutParams2.gravity = 49;
        setLayoutParams(layoutParams2);
        setItemActiveIndicatorResizeable(true);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredHeight;
        int maxHeight = View.MeasureSpec.getSize(heightMeasureSpec);
        int visibleCount = getMenu().getVisibleItems().size();
        if (visibleCount <= 1 || !isShifting(getLabelVisibilityMode(), visibleCount)) {
            measuredHeight = measureSharedChildHeights(widthMeasureSpec, maxHeight, visibleCount, (View) null);
        } else {
            measuredHeight = measureShiftingChildHeights(widthMeasureSpec, maxHeight, visibleCount);
        }
        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec), View.resolveSizeAndState(measuredHeight, heightMeasureSpec, 0));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int count = getChildCount();
        int width = right - left;
        int used = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                int childHeight = child.getMeasuredHeight();
                child.layout(0, used, width, childHeight + used);
                used += childHeight;
            }
        }
    }

    /* access modifiers changed from: protected */
    public NavigationBarItemView createNavigationBarItemView(Context context) {
        return new NavigationRailItemView(context);
    }

    private int makeSharedHeightSpec(int parentWidthSpec, int maxHeight, int shareCount) {
        int maxAvailable = maxHeight / Math.max(1, shareCount);
        int minHeight = this.itemMinimumHeight;
        if (minHeight == -1) {
            minHeight = View.MeasureSpec.getSize(parentWidthSpec);
        }
        return View.MeasureSpec.makeMeasureSpec(Math.min(minHeight, maxAvailable), 0);
    }

    private int measureShiftingChildHeights(int widthMeasureSpec, int maxHeight, int shareCount) {
        int selectedViewHeight = 0;
        View selectedView = getChildAt(getSelectedItemPosition());
        if (selectedView != null) {
            selectedViewHeight = measureChildHeight(selectedView, widthMeasureSpec, makeSharedHeightSpec(widthMeasureSpec, maxHeight, shareCount));
            maxHeight -= selectedViewHeight;
            shareCount--;
        }
        return measureSharedChildHeights(widthMeasureSpec, maxHeight, shareCount, selectedView) + selectedViewHeight;
    }

    private int measureSharedChildHeights(int widthMeasureSpec, int maxHeight, int shareCount, View selectedView) {
        int childHeightSpec;
        if (selectedView == null) {
            childHeightSpec = makeSharedHeightSpec(widthMeasureSpec, maxHeight, shareCount);
        } else {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(selectedView.getMeasuredHeight(), 0);
        }
        int childCount = getChildCount();
        int totalHeight = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child != selectedView) {
                totalHeight += measureChildHeight(child, widthMeasureSpec, childHeightSpec);
            }
        }
        return totalHeight;
    }

    private int measureChildHeight(View child, int widthMeasureSpec, int heightMeasureSpec) {
        if (child.getVisibility() == 8) {
            return 0;
        }
        child.measure(widthMeasureSpec, heightMeasureSpec);
        return child.getMeasuredHeight();
    }

    /* access modifiers changed from: package-private */
    public void setMenuGravity(int gravity) {
        if (this.layoutParams.gravity != gravity) {
            this.layoutParams.gravity = gravity;
            setLayoutParams(this.layoutParams);
        }
    }

    /* access modifiers changed from: package-private */
    public int getMenuGravity() {
        return this.layoutParams.gravity;
    }

    public void setItemMinimumHeight(int minHeight) {
        if (this.itemMinimumHeight != minHeight) {
            this.itemMinimumHeight = minHeight;
            requestLayout();
        }
    }

    public int getItemMinimumHeight() {
        return this.itemMinimumHeight;
    }

    /* access modifiers changed from: package-private */
    public boolean isTopGravity() {
        return (this.layoutParams.gravity & 112) == 48;
    }
}
