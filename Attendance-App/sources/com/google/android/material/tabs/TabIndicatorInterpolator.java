package com.google.android.material.tabs;

import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.tabs.TabLayout;

class TabIndicatorInterpolator {
    private static final int MIN_INDICATOR_WIDTH = 24;

    TabIndicatorInterpolator() {
    }

    static RectF calculateTabViewContentBounds(TabLayout.TabView tabView, int minWidth) {
        int tabViewContentWidth = tabView.getContentWidth();
        int tabViewContentHeight = tabView.getContentHeight();
        int minWidthPx = (int) ViewUtils.dpToPx(tabView.getContext(), minWidth);
        if (tabViewContentWidth < minWidthPx) {
            tabViewContentWidth = minWidthPx;
        }
        int tabViewCenterX = (tabView.getLeft() + tabView.getRight()) / 2;
        int tabViewCenterY = (tabView.getTop() + tabView.getBottom()) / 2;
        return new RectF((float) (tabViewCenterX - (tabViewContentWidth / 2)), (float) (tabViewCenterY - (tabViewContentHeight / 2)), (float) ((tabViewContentWidth / 2) + tabViewCenterX), (float) ((tabViewCenterX / 2) + tabViewCenterY));
    }

    static RectF calculateIndicatorWidthForTab(TabLayout tabLayout, View tab) {
        if (tab == null) {
            return new RectF();
        }
        if (tabLayout.isTabIndicatorFullWidth() || !(tab instanceof TabLayout.TabView)) {
            return new RectF((float) tab.getLeft(), (float) tab.getTop(), (float) tab.getRight(), (float) tab.getBottom());
        }
        return calculateTabViewContentBounds((TabLayout.TabView) tab, 24);
    }

    /* access modifiers changed from: package-private */
    public void setIndicatorBoundsForTab(TabLayout tabLayout, View tab, Drawable indicator) {
        RectF startIndicator = calculateIndicatorWidthForTab(tabLayout, tab);
        indicator.setBounds((int) startIndicator.left, indicator.getBounds().top, (int) startIndicator.right, indicator.getBounds().bottom);
    }

    /* access modifiers changed from: package-private */
    public void updateIndicatorForOffset(TabLayout tabLayout, View startTitle, View endTitle, float offset, Drawable indicator) {
        RectF startIndicator = calculateIndicatorWidthForTab(tabLayout, startTitle);
        RectF endIndicator = calculateIndicatorWidthForTab(tabLayout, endTitle);
        indicator.setBounds(AnimationUtils.lerp((int) startIndicator.left, (int) endIndicator.left, offset), indicator.getBounds().top, AnimationUtils.lerp((int) startIndicator.right, (int) endIndicator.right, offset), indicator.getBounds().bottom);
    }
}
