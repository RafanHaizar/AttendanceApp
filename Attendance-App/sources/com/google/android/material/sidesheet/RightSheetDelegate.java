package com.google.android.material.sidesheet;

import android.view.View;
import android.view.ViewGroup;
import androidx.customview.widget.ViewDragHelper;

final class RightSheetDelegate extends SheetDelegate {
    final SideSheetBehavior<? extends View> sheetBehavior;

    RightSheetDelegate(SideSheetBehavior<? extends View> sheetBehavior2) {
        this.sheetBehavior = sheetBehavior2;
    }

    /* access modifiers changed from: package-private */
    public int getSheetEdge() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public int getHiddenOffset() {
        return this.sheetBehavior.getParentWidth();
    }

    /* access modifiers changed from: package-private */
    public int getExpandedOffset() {
        return Math.max(0, getHiddenOffset() - this.sheetBehavior.getChildWidth());
    }

    private boolean isReleasedCloseToOriginEdge(View releasedChild) {
        return releasedChild.getLeft() > (getHiddenOffset() - getExpandedOffset()) / 2;
    }

    /* access modifiers changed from: package-private */
    public int calculateTargetStateOnViewReleased(View releasedChild, float xVelocity, float yVelocity) {
        if (xVelocity < 0.0f) {
            return 3;
        }
        if (shouldHide(releasedChild, xVelocity)) {
            if (isSwipeSignificant(xVelocity, yVelocity) || isReleasedCloseToOriginEdge(releasedChild)) {
                return 5;
            }
            return 3;
        } else if (xVelocity != 0.0f && SheetUtils.isSwipeMostlyHorizontal(xVelocity, yVelocity)) {
            return 5;
        } else {
            int currentLeft = releasedChild.getLeft();
            if (Math.abs(currentLeft - getExpandedOffset()) < Math.abs(currentLeft - getHiddenOffset())) {
                return 3;
            }
            return 5;
        }
    }

    private boolean isSwipeSignificant(float xVelocity, float yVelocity) {
        return SheetUtils.isSwipeMostlyHorizontal(xVelocity, yVelocity) && yVelocity > ((float) this.sheetBehavior.getSignificantVelocityThreshold());
    }

    /* access modifiers changed from: package-private */
    public boolean shouldHide(View child, float velocity) {
        return Math.abs(((float) child.getRight()) + (this.sheetBehavior.getHideFriction() * velocity)) > this.sheetBehavior.getHideThreshold();
    }

    /* access modifiers changed from: package-private */
    public boolean isSettling(View child, int state, boolean isReleasingView) {
        int left = this.sheetBehavior.getOutwardEdgeOffsetForState(state);
        ViewDragHelper viewDragHelper = this.sheetBehavior.getViewDragHelper();
        return viewDragHelper != null && (!isReleasingView ? viewDragHelper.smoothSlideViewTo(child, left, child.getTop()) : viewDragHelper.settleCapturedViewAt(left, child.getTop()));
    }

    /* access modifiers changed from: package-private */
    public <V extends View> int getOutwardEdge(V child) {
        return child.getLeft();
    }

    /* access modifiers changed from: package-private */
    public float calculateSlideOffsetBasedOnOutwardEdge(int left) {
        float hiddenOffset = (float) getHiddenOffset();
        return (hiddenOffset - ((float) left)) / (hiddenOffset - ((float) getExpandedOffset()));
    }

    /* access modifiers changed from: package-private */
    public void updateCoplanarSiblingLayoutParams(ViewGroup.MarginLayoutParams coplanarSiblingLayoutParams, int sheetLeft, int sheetRight) {
        int parentWidth = this.sheetBehavior.getParentWidth();
        if (sheetLeft <= parentWidth) {
            coplanarSiblingLayoutParams.rightMargin = parentWidth - sheetLeft;
        }
    }
}
