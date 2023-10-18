package com.google.android.material.bottomnavigation;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;
import com.google.android.material.C1087R;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarMenuView;
import java.util.ArrayList;
import java.util.List;

public class BottomNavigationMenuView extends NavigationBarMenuView {
    private final int activeItemMaxWidth;
    private final int activeItemMinWidth;
    private final int inactiveItemMaxWidth;
    private final int inactiveItemMinWidth;
    private boolean itemHorizontalTranslationEnabled;
    private final List<Integer> tempChildWidths = new ArrayList();

    public BottomNavigationMenuView(Context context) {
        super(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-2, -2);
        params.gravity = 17;
        setLayoutParams(params);
        Resources res = getResources();
        this.inactiveItemMaxWidth = res.getDimensionPixelSize(C1087R.dimen.design_bottom_navigation_item_max_width);
        this.inactiveItemMinWidth = res.getDimensionPixelSize(C1087R.dimen.design_bottom_navigation_item_min_width);
        this.activeItemMaxWidth = res.getDimensionPixelSize(C1087R.dimen.design_bottom_navigation_active_item_max_width);
        this.activeItemMinWidth = res.getDimensionPixelSize(C1087R.dimen.design_bottom_navigation_active_item_min_width);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00fd  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r22, int r23) {
        /*
            r21 = this;
            r0 = r21
            androidx.appcompat.view.menu.MenuBuilder r1 = r21.getMenu()
            int r2 = android.view.View.MeasureSpec.getSize(r22)
            java.util.ArrayList r3 = r1.getVisibleItems()
            int r3 = r3.size()
            int r4 = r21.getChildCount()
            java.util.List<java.lang.Integer> r5 = r0.tempChildWidths
            r5.clear()
            int r5 = android.view.View.MeasureSpec.getSize(r23)
            r6 = 1073741824(0x40000000, float:2.0)
            int r7 = android.view.View.MeasureSpec.makeMeasureSpec(r5, r6)
            int r8 = r21.getLabelVisibilityMode()
            boolean r8 = r0.isShifting(r8, r3)
            r9 = 8
            r10 = 1
            if (r8 == 0) goto L_0x00c4
            boolean r8 = r21.isItemHorizontalTranslationEnabled()
            if (r8 == 0) goto L_0x00c1
            int r8 = r21.getSelectedItemPosition()
            android.view.View r8 = r0.getChildAt(r8)
            int r11 = r0.activeItemMinWidth
            int r12 = r8.getVisibility()
            if (r12 == r9) goto L_0x005b
            int r12 = r0.activeItemMaxWidth
            r13 = -2147483648(0xffffffff80000000, float:-0.0)
            int r12 = android.view.View.MeasureSpec.makeMeasureSpec(r12, r13)
            r8.measure(r12, r7)
            int r12 = r8.getMeasuredWidth()
            int r11 = java.lang.Math.max(r11, r12)
        L_0x005b:
            int r12 = r8.getVisibility()
            if (r12 == r9) goto L_0x0063
            r12 = 1
            goto L_0x0064
        L_0x0063:
            r12 = 0
        L_0x0064:
            int r12 = r3 - r12
            int r13 = r0.inactiveItemMinWidth
            int r13 = r13 * r12
            int r13 = r2 - r13
            int r14 = r0.activeItemMaxWidth
            int r14 = java.lang.Math.min(r11, r14)
            int r14 = java.lang.Math.min(r13, r14)
            int r15 = r2 - r14
            if (r12 != 0) goto L_0x007b
            goto L_0x007c
        L_0x007b:
            r10 = r12
        L_0x007c:
            int r15 = r15 / r10
            int r10 = r0.inactiveItemMaxWidth
            int r10 = java.lang.Math.min(r15, r10)
            int r16 = r2 - r14
            int r17 = r10 * r12
            int r16 = r16 - r17
            r17 = 0
            r6 = r17
        L_0x008d:
            if (r6 >= r4) goto L_0x00be
            r18 = 0
            android.view.View r19 = r0.getChildAt(r6)
            r20 = r1
            int r1 = r19.getVisibility()
            if (r1 == r9) goto L_0x00ae
            int r1 = r21.getSelectedItemPosition()
            if (r6 != r1) goto L_0x00a5
            r1 = r14
            goto L_0x00a6
        L_0x00a5:
            r1 = r10
        L_0x00a6:
            r18 = r1
            if (r16 <= 0) goto L_0x00ae
            int r18 = r18 + 1
            int r16 = r16 + -1
        L_0x00ae:
            java.util.List<java.lang.Integer> r1 = r0.tempChildWidths
            java.lang.Integer r9 = java.lang.Integer.valueOf(r18)
            r1.add(r9)
            int r6 = r6 + 1
            r1 = r20
            r9 = 8
            goto L_0x008d
        L_0x00be:
            r20 = r1
            goto L_0x00f9
        L_0x00c1:
            r20 = r1
            goto L_0x00c6
        L_0x00c4:
            r20 = r1
        L_0x00c6:
            if (r3 != 0) goto L_0x00c9
            goto L_0x00ca
        L_0x00c9:
            r10 = r3
        L_0x00ca:
            int r1 = r2 / r10
            int r6 = r0.activeItemMaxWidth
            int r6 = java.lang.Math.min(r1, r6)
            int r8 = r6 * r3
            int r8 = r2 - r8
            r9 = 0
        L_0x00d7:
            if (r9 >= r4) goto L_0x00f9
            r10 = 0
            android.view.View r11 = r0.getChildAt(r9)
            int r11 = r11.getVisibility()
            r12 = 8
            if (r11 == r12) goto L_0x00ed
            r10 = r6
            if (r8 <= 0) goto L_0x00ed
            int r10 = r10 + 1
            int r8 = r8 + -1
        L_0x00ed:
            java.util.List<java.lang.Integer> r11 = r0.tempChildWidths
            java.lang.Integer r12 = java.lang.Integer.valueOf(r10)
            r11.add(r12)
            int r9 = r9 + 1
            goto L_0x00d7
        L_0x00f9:
            r1 = 0
            r6 = 0
        L_0x00fb:
            if (r6 >= r4) goto L_0x0133
            android.view.View r8 = r0.getChildAt(r6)
            int r9 = r8.getVisibility()
            r10 = 8
            if (r9 != r10) goto L_0x010c
            r11 = 1073741824(0x40000000, float:2.0)
            goto L_0x0130
        L_0x010c:
            java.util.List<java.lang.Integer> r9 = r0.tempChildWidths
            java.lang.Object r9 = r9.get(r6)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            r11 = 1073741824(0x40000000, float:2.0)
            int r9 = android.view.View.MeasureSpec.makeMeasureSpec(r9, r11)
            r8.measure(r9, r7)
            android.view.ViewGroup$LayoutParams r9 = r8.getLayoutParams()
            int r12 = r8.getMeasuredWidth()
            r9.width = r12
            int r12 = r8.getMeasuredWidth()
            int r1 = r1 + r12
        L_0x0130:
            int r6 = r6 + 1
            goto L_0x00fb
        L_0x0133:
            r0.setMeasuredDimension(r1, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.bottomnavigation.BottomNavigationMenuView.onMeasure(int, int):void");
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int count = getChildCount();
        int width = right - left;
        int height = bottom - top;
        int used = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                if (ViewCompat.getLayoutDirection(this) == 1) {
                    child.layout((width - used) - child.getMeasuredWidth(), 0, width - used, height);
                } else {
                    child.layout(used, 0, child.getMeasuredWidth() + used, height);
                }
                used += child.getMeasuredWidth();
            }
        }
    }

    public void setItemHorizontalTranslationEnabled(boolean itemHorizontalTranslationEnabled2) {
        this.itemHorizontalTranslationEnabled = itemHorizontalTranslationEnabled2;
    }

    public boolean isItemHorizontalTranslationEnabled() {
        return this.itemHorizontalTranslationEnabled;
    }

    /* access modifiers changed from: protected */
    public NavigationBarItemView createNavigationBarItemView(Context context) {
        return new BottomNavigationItemView(context);
    }
}
