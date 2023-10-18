package com.google.android.material.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class BaselineLayout extends ViewGroup {
    private int baseline = -1;

    public BaselineLayout(Context context) {
        super(context, (AttributeSet) null, 0);
    }

    public BaselineLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public BaselineLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        int maxWidth = 0;
        int maxHeight = 0;
        int maxChildBaseline = -1;
        int maxChildDescent = -1;
        int childState = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                int baseline2 = child.getBaseline();
                if (baseline2 != -1) {
                    maxChildBaseline = Math.max(maxChildBaseline, baseline2);
                    maxChildDescent = Math.max(maxChildDescent, child.getMeasuredHeight() - baseline2);
                }
                maxWidth = Math.max(maxWidth, child.getMeasuredWidth());
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
                childState = View.combineMeasuredStates(childState, child.getMeasuredState());
            }
        }
        if (maxChildBaseline != -1) {
            maxHeight = Math.max(maxHeight, maxChildBaseline + Math.max(maxChildDescent, getPaddingBottom()));
            this.baseline = maxChildBaseline;
        }
        setMeasuredDimension(View.resolveSizeAndState(Math.max(maxWidth, getSuggestedMinimumWidth()), widthMeasureSpec, childState), View.resolveSizeAndState(Math.max(maxHeight, getSuggestedMinimumHeight()), heightMeasureSpec, childState << 16));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childTop;
        int count = getChildCount();
        int parentLeft = getPaddingLeft();
        int parentContentWidth = ((right - left) - getPaddingRight()) - parentLeft;
        int parentTop = getPaddingTop();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                int childLeft = ((parentContentWidth - width) / 2) + parentLeft;
                if (this.baseline == -1 || child.getBaseline() == -1) {
                    childTop = parentTop;
                } else {
                    childTop = (this.baseline + parentTop) - child.getBaseline();
                }
                child.layout(childLeft, childTop, childLeft + width, childTop + height);
            }
        }
    }

    public int getBaseline() {
        return this.baseline;
    }
}
