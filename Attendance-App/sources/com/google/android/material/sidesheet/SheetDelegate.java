package com.google.android.material.sidesheet;

import android.view.View;
import android.view.ViewGroup;

abstract class SheetDelegate {
    /* access modifiers changed from: package-private */
    public abstract float calculateSlideOffsetBasedOnOutwardEdge(int i);

    /* access modifiers changed from: package-private */
    public abstract int calculateTargetStateOnViewReleased(View view, float f, float f2);

    /* access modifiers changed from: package-private */
    public abstract int getExpandedOffset();

    /* access modifiers changed from: package-private */
    public abstract int getHiddenOffset();

    /* access modifiers changed from: package-private */
    public abstract <V extends View> int getOutwardEdge(V v);

    /* access modifiers changed from: package-private */
    public abstract int getSheetEdge();

    /* access modifiers changed from: package-private */
    public abstract boolean isSettling(View view, int i, boolean z);

    /* access modifiers changed from: package-private */
    public abstract boolean shouldHide(View view, float f);

    /* access modifiers changed from: package-private */
    public abstract void updateCoplanarSiblingLayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams, int i, int i2);

    SheetDelegate() {
    }
}
