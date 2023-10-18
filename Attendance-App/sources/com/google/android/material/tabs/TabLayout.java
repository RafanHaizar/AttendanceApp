package com.google.android.material.tabs;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.util.Pools;
import androidx.core.view.GravityCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.PointerIconCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.C1087R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

@ViewPager.DecorView
public class TabLayout extends HorizontalScrollView {
    private static final int ANIMATION_DURATION = 300;
    static final int DEFAULT_GAP_TEXT_ICON = 8;
    private static final int DEFAULT_HEIGHT = 48;
    private static final int DEFAULT_HEIGHT_WITH_TEXT_ICON = 72;
    private static final int DEF_STYLE_RES = C1087R.C1093style.Widget_Design_TabLayout;
    static final int FIXED_WRAP_GUTTER_MIN = 16;
    public static final int GRAVITY_CENTER = 1;
    public static final int GRAVITY_FILL = 0;
    public static final int GRAVITY_START = 2;
    public static final int INDICATOR_ANIMATION_MODE_ELASTIC = 1;
    public static final int INDICATOR_ANIMATION_MODE_FADE = 2;
    public static final int INDICATOR_ANIMATION_MODE_LINEAR = 0;
    public static final int INDICATOR_GRAVITY_BOTTOM = 0;
    public static final int INDICATOR_GRAVITY_CENTER = 1;
    public static final int INDICATOR_GRAVITY_STRETCH = 3;
    public static final int INDICATOR_GRAVITY_TOP = 2;
    private static final int INVALID_WIDTH = -1;
    private static final String LOG_TAG = "TabLayout";
    public static final int MODE_AUTO = 2;
    public static final int MODE_FIXED = 1;
    public static final int MODE_SCROLLABLE = 0;
    private static final int SELECTED_INDICATOR_HEIGHT_DEFAULT = -1;
    public static final int TAB_LABEL_VISIBILITY_LABELED = 1;
    public static final int TAB_LABEL_VISIBILITY_UNLABELED = 0;
    private static final int TAB_MIN_WIDTH_MARGIN = 56;
    private static final Pools.Pool<Tab> tabPool = new Pools.SynchronizedPool(16);
    private AdapterChangeListener adapterChangeListener;
    private int contentInsetStart;
    private BaseOnTabSelectedListener currentVpSelectedListener;
    /* access modifiers changed from: private */
    public final int defaultTabTextAppearance;
    int indicatorPosition;
    boolean inlineLabel;
    int mode;
    private TabLayoutOnPageChangeListener pageChangeListener;
    private PagerAdapter pagerAdapter;
    private DataSetObserver pagerAdapterObserver;
    private final int requestedTabMaxWidth;
    private final int requestedTabMinWidth;
    private ValueAnimator scrollAnimator;
    private final int scrollableTabMinWidth;
    private BaseOnTabSelectedListener selectedListener;
    private final ArrayList<BaseOnTabSelectedListener> selectedListeners;
    private Tab selectedTab;
    /* access modifiers changed from: private */
    public int selectedTabTextAppearance;
    float selectedTabTextSize;
    private boolean setupViewPagerImplicitly;
    final SlidingTabIndicator slidingTabIndicator;
    final int tabBackgroundResId;
    int tabGravity;
    ColorStateList tabIconTint;
    PorterDuff.Mode tabIconTintMode;
    int tabIndicatorAnimationDuration;
    int tabIndicatorAnimationMode;
    boolean tabIndicatorFullWidth;
    int tabIndicatorGravity;
    int tabIndicatorHeight;
    /* access modifiers changed from: private */
    public TabIndicatorInterpolator tabIndicatorInterpolator;
    /* access modifiers changed from: private */
    public final TimeInterpolator tabIndicatorTimeInterpolator;
    int tabMaxWidth;
    int tabPaddingBottom;
    int tabPaddingEnd;
    int tabPaddingStart;
    int tabPaddingTop;
    ColorStateList tabRippleColorStateList;
    Drawable tabSelectedIndicator;
    private int tabSelectedIndicatorColor;
    /* access modifiers changed from: private */
    public final int tabTextAppearance;
    ColorStateList tabTextColors;
    float tabTextMultiLineSize;
    float tabTextSize;
    private final Pools.Pool<TabView> tabViewPool;
    private final ArrayList<Tab> tabs;
    boolean unboundedRipple;
    ViewPager viewPager;
    /* access modifiers changed from: private */
    public int viewPagerScrollState;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TabGravity {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TabIndicatorAnimationMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TabIndicatorGravity {
    }

    @Deprecated
    public interface BaseOnTabSelectedListener<T extends Tab> {
        void onTabReselected(T t);

        void onTabSelected(T t);

        void onTabUnselected(T t);
    }

    public @interface LabelVisibility {
    }

    public interface OnTabSelectedListener extends BaseOnTabSelectedListener<Tab> {
    }

    public TabLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public TabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, C1087R.attr.tabStyle);
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public TabLayout(android.content.Context r11, android.util.AttributeSet r12, int r13) {
        /*
            r10 = this;
            int r4 = DEF_STYLE_RES
            android.content.Context r0 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r11, r12, r13, r4)
            r10.<init>(r0, r12, r13)
            r6 = -1
            r10.indicatorPosition = r6
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r10.tabs = r0
            r10.selectedTabTextAppearance = r6
            r7 = 0
            r10.tabSelectedIndicatorColor = r7
            r0 = 2147483647(0x7fffffff, float:NaN)
            r10.tabMaxWidth = r0
            r10.tabIndicatorHeight = r6
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r10.selectedListeners = r0
            androidx.core.util.Pools$SimplePool r0 = new androidx.core.util.Pools$SimplePool
            r1 = 12
            r0.<init>(r1)
            r10.tabViewPool = r0
            android.content.Context r11 = r10.getContext()
            r10.setHorizontalScrollBarEnabled(r7)
            com.google.android.material.tabs.TabLayout$SlidingTabIndicator r8 = new com.google.android.material.tabs.TabLayout$SlidingTabIndicator
            r8.<init>(r11)
            r10.slidingTabIndicator = r8
            android.widget.FrameLayout$LayoutParams r0 = new android.widget.FrameLayout$LayoutParams
            r1 = -2
            r0.<init>(r1, r6)
            super.addView(r8, r7, r0)
            int[] r2 = com.google.android.material.C1087R.styleable.TabLayout
            r9 = 1
            int[] r5 = new int[r9]
            int r0 = com.google.android.material.C1087R.styleable.TabLayout_tabTextAppearance
            r5[r7] = r0
            r0 = r11
            r1 = r12
            r3 = r13
            android.content.res.TypedArray r0 = com.google.android.material.internal.ThemeEnforcement.obtainStyledAttributes(r0, r1, r2, r3, r4, r5)
            android.graphics.drawable.Drawable r1 = r10.getBackground()
            boolean r1 = r1 instanceof android.graphics.drawable.ColorDrawable
            if (r1 == 0) goto L_0x0081
            android.graphics.drawable.Drawable r1 = r10.getBackground()
            android.graphics.drawable.ColorDrawable r1 = (android.graphics.drawable.ColorDrawable) r1
            com.google.android.material.shape.MaterialShapeDrawable r2 = new com.google.android.material.shape.MaterialShapeDrawable
            r2.<init>()
            int r3 = r1.getColor()
            android.content.res.ColorStateList r3 = android.content.res.ColorStateList.valueOf(r3)
            r2.setFillColor(r3)
            r2.initializeElevationOverlay(r11)
            float r3 = androidx.core.view.ViewCompat.getElevation(r10)
            r2.setElevation(r3)
            androidx.core.view.ViewCompat.setBackground(r10, r2)
        L_0x0081:
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabIndicator
            android.graphics.drawable.Drawable r1 = com.google.android.material.resources.MaterialResources.getDrawable(r11, r0, r1)
            r10.setSelectedTabIndicator((android.graphics.drawable.Drawable) r1)
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabIndicatorColor
            int r1 = r0.getColor(r1, r7)
            r10.setSelectedTabIndicatorColor(r1)
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabIndicatorHeight
            int r1 = r0.getDimensionPixelSize(r1, r6)
            r8.setSelectedIndicatorHeight(r1)
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabIndicatorGravity
            int r1 = r0.getInt(r1, r7)
            r10.setSelectedTabIndicatorGravity(r1)
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabIndicatorAnimationMode
            int r1 = r0.getInt(r1, r7)
            r10.setTabIndicatorAnimationMode(r1)
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabIndicatorFullWidth
            boolean r1 = r0.getBoolean(r1, r9)
            r10.setTabIndicatorFullWidth(r1)
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabPadding
            int r1 = r0.getDimensionPixelSize(r1, r7)
            r10.tabPaddingBottom = r1
            r10.tabPaddingEnd = r1
            r10.tabPaddingTop = r1
            r10.tabPaddingStart = r1
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabPaddingStart
            int r2 = r10.tabPaddingStart
            int r1 = r0.getDimensionPixelSize(r1, r2)
            r10.tabPaddingStart = r1
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabPaddingTop
            int r2 = r10.tabPaddingTop
            int r1 = r0.getDimensionPixelSize(r1, r2)
            r10.tabPaddingTop = r1
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabPaddingEnd
            int r2 = r10.tabPaddingEnd
            int r1 = r0.getDimensionPixelSize(r1, r2)
            r10.tabPaddingEnd = r1
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabPaddingBottom
            int r2 = r10.tabPaddingBottom
            int r1 = r0.getDimensionPixelSize(r1, r2)
            r10.tabPaddingBottom = r1
            boolean r1 = com.google.android.material.internal.ThemeEnforcement.isMaterial3Theme(r11)
            if (r1 == 0) goto L_0x00f8
            int r1 = com.google.android.material.C1087R.attr.textAppearanceTitleSmall
            r10.defaultTabTextAppearance = r1
            goto L_0x00fc
        L_0x00f8:
            int r1 = com.google.android.material.C1087R.attr.textAppearanceButton
            r10.defaultTabTextAppearance = r1
        L_0x00fc:
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabTextAppearance
            int r2 = com.google.android.material.C1087R.C1093style.TextAppearance_Design_Tab
            int r1 = r0.getResourceId(r1, r2)
            r10.tabTextAppearance = r1
            int[] r2 = androidx.appcompat.C0503R.styleable.TextAppearance
            android.content.res.TypedArray r2 = r11.obtainStyledAttributes(r1, r2)
            int r3 = androidx.appcompat.C0503R.styleable.TextAppearance_android_textSize     // Catch:{ all -> 0x022a }
            int r3 = r2.getDimensionPixelSize(r3, r7)     // Catch:{ all -> 0x022a }
            float r3 = (float) r3     // Catch:{ all -> 0x022a }
            r10.tabTextSize = r3     // Catch:{ all -> 0x022a }
            int r3 = androidx.appcompat.C0503R.styleable.TextAppearance_android_textColor     // Catch:{ all -> 0x022a }
            android.content.res.ColorStateList r3 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r11, (android.content.res.TypedArray) r2, (int) r3)     // Catch:{ all -> 0x022a }
            r10.tabTextColors = r3     // Catch:{ all -> 0x022a }
            r2.recycle()
            int r3 = com.google.android.material.C1087R.styleable.TabLayout_tabSelectedTextAppearance
            boolean r3 = r0.hasValue(r3)
            if (r3 == 0) goto L_0x0131
            int r3 = com.google.android.material.C1087R.styleable.TabLayout_tabSelectedTextAppearance
            int r1 = r0.getResourceId(r3, r1)
            r10.selectedTabTextAppearance = r1
        L_0x0131:
            int r1 = r10.selectedTabTextAppearance
            if (r1 == r6) goto L_0x0173
            int[] r3 = androidx.appcompat.C0503R.styleable.TextAppearance
            android.content.res.TypedArray r1 = r11.obtainStyledAttributes(r1, r3)
            int r3 = androidx.appcompat.C0503R.styleable.TextAppearance_android_textSize     // Catch:{ all -> 0x016e }
            float r4 = r10.tabTextSize     // Catch:{ all -> 0x016e }
            int r4 = (int) r4     // Catch:{ all -> 0x016e }
            int r3 = r1.getDimensionPixelSize(r3, r4)     // Catch:{ all -> 0x016e }
            float r3 = (float) r3     // Catch:{ all -> 0x016e }
            r10.selectedTabTextSize = r3     // Catch:{ all -> 0x016e }
            int r3 = androidx.appcompat.C0503R.styleable.TextAppearance_android_textColor     // Catch:{ all -> 0x016e }
            android.content.res.ColorStateList r3 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r11, (android.content.res.TypedArray) r1, (int) r3)     // Catch:{ all -> 0x016e }
            if (r3 == 0) goto L_0x016a
            android.content.res.ColorStateList r4 = r10.tabTextColors     // Catch:{ all -> 0x016e }
            int r4 = r4.getDefaultColor()     // Catch:{ all -> 0x016e }
            int[] r5 = new int[r9]     // Catch:{ all -> 0x016e }
            r8 = 16842913(0x10100a1, float:2.369401E-38)
            r5[r7] = r8     // Catch:{ all -> 0x016e }
            int r8 = r3.getDefaultColor()     // Catch:{ all -> 0x016e }
            int r5 = r3.getColorForState(r5, r8)     // Catch:{ all -> 0x016e }
            android.content.res.ColorStateList r4 = createColorStateList(r4, r5)     // Catch:{ all -> 0x016e }
            r10.tabTextColors = r4     // Catch:{ all -> 0x016e }
        L_0x016a:
            r1.recycle()
            goto L_0x0173
        L_0x016e:
            r3 = move-exception
            r1.recycle()
            throw r3
        L_0x0173:
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabTextColor
            boolean r1 = r0.hasValue(r1)
            if (r1 == 0) goto L_0x0183
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabTextColor
            android.content.res.ColorStateList r1 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r11, (android.content.res.TypedArray) r0, (int) r1)
            r10.tabTextColors = r1
        L_0x0183:
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabSelectedTextColor
            boolean r1 = r0.hasValue(r1)
            if (r1 == 0) goto L_0x019d
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabSelectedTextColor
            int r1 = r0.getColor(r1, r7)
            android.content.res.ColorStateList r3 = r10.tabTextColors
            int r3 = r3.getDefaultColor()
            android.content.res.ColorStateList r3 = createColorStateList(r3, r1)
            r10.tabTextColors = r3
        L_0x019d:
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabIconTint
            android.content.res.ColorStateList r1 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r11, (android.content.res.TypedArray) r0, (int) r1)
            r10.tabIconTint = r1
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabIconTintMode
            int r1 = r0.getInt(r1, r6)
            r3 = 0
            android.graphics.PorterDuff$Mode r1 = com.google.android.material.internal.ViewUtils.parseTintMode(r1, r3)
            r10.tabIconTintMode = r1
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabRippleColor
            android.content.res.ColorStateList r1 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r11, (android.content.res.TypedArray) r0, (int) r1)
            r10.tabRippleColorStateList = r1
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabIndicatorAnimationDuration
            r3 = 300(0x12c, float:4.2E-43)
            int r1 = r0.getInt(r1, r3)
            r10.tabIndicatorAnimationDuration = r1
            int r1 = com.google.android.material.C1087R.attr.motionEasingEmphasizedInterpolator
            android.animation.TimeInterpolator r3 = com.google.android.material.animation.AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR
            android.animation.TimeInterpolator r1 = com.google.android.material.motion.MotionUtils.resolveThemeInterpolator(r11, r1, r3)
            r10.tabIndicatorTimeInterpolator = r1
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabMinWidth
            int r1 = r0.getDimensionPixelSize(r1, r6)
            r10.requestedTabMinWidth = r1
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabMaxWidth
            int r1 = r0.getDimensionPixelSize(r1, r6)
            r10.requestedTabMaxWidth = r1
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabBackground
            int r1 = r0.getResourceId(r1, r7)
            r10.tabBackgroundResId = r1
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabContentStart
            int r1 = r0.getDimensionPixelSize(r1, r7)
            r10.contentInsetStart = r1
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabMode
            int r1 = r0.getInt(r1, r9)
            r10.mode = r1
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabGravity
            int r1 = r0.getInt(r1, r7)
            r10.tabGravity = r1
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabInlineLabel
            boolean r1 = r0.getBoolean(r1, r7)
            r10.inlineLabel = r1
            int r1 = com.google.android.material.C1087R.styleable.TabLayout_tabUnboundedRipple
            boolean r1 = r0.getBoolean(r1, r7)
            r10.unboundedRipple = r1
            r0.recycle()
            android.content.res.Resources r1 = r10.getResources()
            int r3 = com.google.android.material.C1087R.dimen.design_tab_text_size_2line
            int r3 = r1.getDimensionPixelSize(r3)
            float r3 = (float) r3
            r10.tabTextMultiLineSize = r3
            int r3 = com.google.android.material.C1087R.dimen.design_tab_scrollable_min_width
            int r3 = r1.getDimensionPixelSize(r3)
            r10.scrollableTabMinWidth = r3
            r10.applyModeAndGravity()
            return
        L_0x022a:
            r1 = move-exception
            r2.recycle()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.tabs.TabLayout.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    public void setSelectedTabIndicatorColor(int color) {
        this.tabSelectedIndicatorColor = color;
        DrawableUtils.setTint(this.tabSelectedIndicator, color);
        updateTabViews(false);
    }

    @Deprecated
    public void setSelectedTabIndicatorHeight(int height) {
        this.tabIndicatorHeight = height;
        this.slidingTabIndicator.setSelectedIndicatorHeight(height);
    }

    public void setScrollPosition(int position, float positionOffset, boolean updateSelectedText) {
        setScrollPosition(position, positionOffset, updateSelectedText, true);
    }

    public void setScrollPosition(int position, float positionOffset, boolean updateSelectedText, boolean updateIndicatorPosition) {
        int roundedPosition = Math.round(((float) position) + positionOffset);
        if (roundedPosition >= 0 && roundedPosition < this.slidingTabIndicator.getChildCount()) {
            if (updateIndicatorPosition) {
                this.slidingTabIndicator.setIndicatorPositionFromTabPosition(position, positionOffset);
            }
            ValueAnimator valueAnimator = this.scrollAnimator;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.scrollAnimator.cancel();
            }
            scrollTo(position < 0 ? 0 : calculateScrollXForTab(position, positionOffset), 0);
            if (updateSelectedText) {
                setSelectedTabView(roundedPosition);
            }
        }
    }

    public void addTab(Tab tab) {
        addTab(tab, this.tabs.isEmpty());
    }

    public void addTab(Tab tab, int position) {
        addTab(tab, position, this.tabs.isEmpty());
    }

    public void addTab(Tab tab, boolean setSelected) {
        addTab(tab, this.tabs.size(), setSelected);
    }

    public void addTab(Tab tab, int position, boolean setSelected) {
        if (tab.parent == this) {
            configureTab(tab, position);
            addTabView(tab);
            if (setSelected) {
                tab.select();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Tab belongs to a different TabLayout.");
    }

    private void addTabFromItemView(TabItem item) {
        Tab tab = newTab();
        if (item.text != null) {
            tab.setText(item.text);
        }
        if (item.icon != null) {
            tab.setIcon(item.icon);
        }
        if (item.customLayout != 0) {
            tab.setCustomView(item.customLayout);
        }
        if (!TextUtils.isEmpty(item.getContentDescription())) {
            tab.setContentDescription(item.getContentDescription());
        }
        addTab(tab);
    }

    private boolean isScrollingEnabled() {
        return getTabMode() == 0 || getTabMode() == 2;
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        return isScrollingEnabled() && super.onInterceptTouchEvent(event);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() != 8 || isScrollingEnabled()) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    @Deprecated
    public void setOnTabSelectedListener(OnTabSelectedListener listener) {
        setOnTabSelectedListener((BaseOnTabSelectedListener) listener);
    }

    @Deprecated
    public void setOnTabSelectedListener(BaseOnTabSelectedListener listener) {
        BaseOnTabSelectedListener baseOnTabSelectedListener = this.selectedListener;
        if (baseOnTabSelectedListener != null) {
            removeOnTabSelectedListener(baseOnTabSelectedListener);
        }
        this.selectedListener = listener;
        if (listener != null) {
            addOnTabSelectedListener(listener);
        }
    }

    public void addOnTabSelectedListener(OnTabSelectedListener listener) {
        addOnTabSelectedListener((BaseOnTabSelectedListener) listener);
    }

    @Deprecated
    public void addOnTabSelectedListener(BaseOnTabSelectedListener listener) {
        if (!this.selectedListeners.contains(listener)) {
            this.selectedListeners.add(listener);
        }
    }

    public void removeOnTabSelectedListener(OnTabSelectedListener listener) {
        removeOnTabSelectedListener((BaseOnTabSelectedListener) listener);
    }

    @Deprecated
    public void removeOnTabSelectedListener(BaseOnTabSelectedListener listener) {
        this.selectedListeners.remove(listener);
    }

    public void clearOnTabSelectedListeners() {
        this.selectedListeners.clear();
    }

    public Tab newTab() {
        Tab tab = createTabFromPool();
        tab.parent = this;
        tab.view = createTabView(tab);
        if (tab.f1126id != -1) {
            tab.view.setId(tab.f1126id);
        }
        return tab;
    }

    /* access modifiers changed from: protected */
    public Tab createTabFromPool() {
        Tab tab = tabPool.acquire();
        if (tab == null) {
            return new Tab();
        }
        return tab;
    }

    /* access modifiers changed from: protected */
    public boolean releaseFromTabPool(Tab tab) {
        return tabPool.release(tab);
    }

    public int getTabCount() {
        return this.tabs.size();
    }

    public Tab getTabAt(int index) {
        if (index < 0 || index >= getTabCount()) {
            return null;
        }
        return this.tabs.get(index);
    }

    public int getSelectedTabPosition() {
        Tab tab = this.selectedTab;
        if (tab != null) {
            return tab.getPosition();
        }
        return -1;
    }

    public void removeTab(Tab tab) {
        if (tab.parent == this) {
            removeTabAt(tab.getPosition());
            return;
        }
        throw new IllegalArgumentException("Tab does not belong to this TabLayout.");
    }

    public void removeTabAt(int position) {
        Tab tab = this.selectedTab;
        int selectedTabPosition = tab != null ? tab.getPosition() : 0;
        removeTabViewAt(position);
        Tab removedTab = this.tabs.remove(position);
        if (removedTab != null) {
            removedTab.reset();
            releaseFromTabPool(removedTab);
        }
        int newTabCount = this.tabs.size();
        int newIndicatorPosition = -1;
        for (int i = position; i < newTabCount; i++) {
            if (this.tabs.get(i).getPosition() == this.indicatorPosition) {
                newIndicatorPosition = i;
            }
            this.tabs.get(i).setPosition(i);
        }
        this.indicatorPosition = newIndicatorPosition;
        if (selectedTabPosition == position) {
            selectTab(this.tabs.isEmpty() ? null : this.tabs.get(Math.max(0, position - 1)));
        }
    }

    public void removeAllTabs() {
        for (int i = this.slidingTabIndicator.getChildCount() - 1; i >= 0; i--) {
            removeTabViewAt(i);
        }
        Iterator<Tab> i2 = this.tabs.iterator();
        while (i2.hasNext()) {
            Tab tab = i2.next();
            i2.remove();
            tab.reset();
            releaseFromTabPool(tab);
        }
        this.selectedTab = null;
    }

    public void setTabMode(int mode2) {
        if (mode2 != this.mode) {
            this.mode = mode2;
            applyModeAndGravity();
        }
    }

    public int getTabMode() {
        return this.mode;
    }

    public void setTabGravity(int gravity) {
        if (this.tabGravity != gravity) {
            this.tabGravity = gravity;
            applyModeAndGravity();
        }
    }

    public int getTabGravity() {
        return this.tabGravity;
    }

    public void setSelectedTabIndicatorGravity(int indicatorGravity) {
        if (this.tabIndicatorGravity != indicatorGravity) {
            this.tabIndicatorGravity = indicatorGravity;
            ViewCompat.postInvalidateOnAnimation(this.slidingTabIndicator);
        }
    }

    public int getTabIndicatorGravity() {
        return this.tabIndicatorGravity;
    }

    public void setTabIndicatorAnimationMode(int tabIndicatorAnimationMode2) {
        this.tabIndicatorAnimationMode = tabIndicatorAnimationMode2;
        switch (tabIndicatorAnimationMode2) {
            case 0:
                this.tabIndicatorInterpolator = new TabIndicatorInterpolator();
                return;
            case 1:
                this.tabIndicatorInterpolator = new ElasticTabIndicatorInterpolator();
                return;
            case 2:
                this.tabIndicatorInterpolator = new FadeTabIndicatorInterpolator();
                return;
            default:
                throw new IllegalArgumentException(tabIndicatorAnimationMode2 + " is not a valid TabIndicatorAnimationMode");
        }
    }

    public int getTabIndicatorAnimationMode() {
        return this.tabIndicatorAnimationMode;
    }

    public void setTabIndicatorFullWidth(boolean tabIndicatorFullWidth2) {
        this.tabIndicatorFullWidth = tabIndicatorFullWidth2;
        this.slidingTabIndicator.jumpIndicatorToSelectedPosition();
        ViewCompat.postInvalidateOnAnimation(this.slidingTabIndicator);
    }

    public boolean isTabIndicatorFullWidth() {
        return this.tabIndicatorFullWidth;
    }

    public void setInlineLabel(boolean inline) {
        if (this.inlineLabel != inline) {
            this.inlineLabel = inline;
            for (int i = 0; i < this.slidingTabIndicator.getChildCount(); i++) {
                View child = this.slidingTabIndicator.getChildAt(i);
                if (child instanceof TabView) {
                    ((TabView) child).updateOrientation();
                }
            }
            applyModeAndGravity();
        }
    }

    public void setInlineLabelResource(int inlineResourceId) {
        setInlineLabel(getResources().getBoolean(inlineResourceId));
    }

    public boolean isInlineLabel() {
        return this.inlineLabel;
    }

    public void setUnboundedRipple(boolean unboundedRipple2) {
        if (this.unboundedRipple != unboundedRipple2) {
            this.unboundedRipple = unboundedRipple2;
            for (int i = 0; i < this.slidingTabIndicator.getChildCount(); i++) {
                View child = this.slidingTabIndicator.getChildAt(i);
                if (child instanceof TabView) {
                    ((TabView) child).updateBackgroundDrawable(getContext());
                }
            }
        }
    }

    public void setUnboundedRippleResource(int unboundedRippleResourceId) {
        setUnboundedRipple(getResources().getBoolean(unboundedRippleResourceId));
    }

    public boolean hasUnboundedRipple() {
        return this.unboundedRipple;
    }

    public void setTabTextColors(ColorStateList textColor) {
        if (this.tabTextColors != textColor) {
            this.tabTextColors = textColor;
            updateAllTabs();
        }
    }

    public ColorStateList getTabTextColors() {
        return this.tabTextColors;
    }

    public void setTabTextColors(int normalColor, int selectedColor) {
        setTabTextColors(createColorStateList(normalColor, selectedColor));
    }

    public void setTabIconTint(ColorStateList iconTint) {
        if (this.tabIconTint != iconTint) {
            this.tabIconTint = iconTint;
            updateAllTabs();
        }
    }

    public void setTabIconTintResource(int iconTintResourceId) {
        setTabIconTint(AppCompatResources.getColorStateList(getContext(), iconTintResourceId));
    }

    public ColorStateList getTabIconTint() {
        return this.tabIconTint;
    }

    public ColorStateList getTabRippleColor() {
        return this.tabRippleColorStateList;
    }

    public void setTabRippleColor(ColorStateList color) {
        if (this.tabRippleColorStateList != color) {
            this.tabRippleColorStateList = color;
            for (int i = 0; i < this.slidingTabIndicator.getChildCount(); i++) {
                View child = this.slidingTabIndicator.getChildAt(i);
                if (child instanceof TabView) {
                    ((TabView) child).updateBackgroundDrawable(getContext());
                }
            }
        }
    }

    public void setTabRippleColorResource(int tabRippleColorResourceId) {
        setTabRippleColor(AppCompatResources.getColorStateList(getContext(), tabRippleColorResourceId));
    }

    public Drawable getTabSelectedIndicator() {
        return this.tabSelectedIndicator;
    }

    public void setSelectedTabIndicator(Drawable tabSelectedIndicator2) {
        if (tabSelectedIndicator2 == null) {
            tabSelectedIndicator2 = new GradientDrawable();
        }
        Drawable mutate = DrawableCompat.wrap(tabSelectedIndicator2).mutate();
        this.tabSelectedIndicator = mutate;
        DrawableUtils.setTint(mutate, this.tabSelectedIndicatorColor);
        int indicatorHeight = this.tabIndicatorHeight;
        if (indicatorHeight == -1) {
            indicatorHeight = this.tabSelectedIndicator.getIntrinsicHeight();
        }
        this.slidingTabIndicator.setSelectedIndicatorHeight(indicatorHeight);
    }

    public void setSelectedTabIndicator(int tabSelectedIndicatorResourceId) {
        if (tabSelectedIndicatorResourceId != 0) {
            setSelectedTabIndicator(AppCompatResources.getDrawable(getContext(), tabSelectedIndicatorResourceId));
        } else {
            setSelectedTabIndicator((Drawable) null);
        }
    }

    public void setupWithViewPager(ViewPager viewPager2) {
        setupWithViewPager(viewPager2, true);
    }

    public void setupWithViewPager(ViewPager viewPager2, boolean autoRefresh) {
        setupWithViewPager(viewPager2, autoRefresh, false);
    }

    private void setupWithViewPager(ViewPager viewPager2, boolean autoRefresh, boolean implicitSetup) {
        ViewPager viewPager3 = this.viewPager;
        if (viewPager3 != null) {
            TabLayoutOnPageChangeListener tabLayoutOnPageChangeListener = this.pageChangeListener;
            if (tabLayoutOnPageChangeListener != null) {
                viewPager3.removeOnPageChangeListener(tabLayoutOnPageChangeListener);
            }
            AdapterChangeListener adapterChangeListener2 = this.adapterChangeListener;
            if (adapterChangeListener2 != null) {
                this.viewPager.removeOnAdapterChangeListener(adapterChangeListener2);
            }
        }
        BaseOnTabSelectedListener baseOnTabSelectedListener = this.currentVpSelectedListener;
        if (baseOnTabSelectedListener != null) {
            removeOnTabSelectedListener(baseOnTabSelectedListener);
            this.currentVpSelectedListener = null;
        }
        if (viewPager2 != null) {
            this.viewPager = viewPager2;
            if (this.pageChangeListener == null) {
                this.pageChangeListener = new TabLayoutOnPageChangeListener(this);
            }
            this.pageChangeListener.reset();
            viewPager2.addOnPageChangeListener(this.pageChangeListener);
            ViewPagerOnTabSelectedListener viewPagerOnTabSelectedListener = new ViewPagerOnTabSelectedListener(viewPager2);
            this.currentVpSelectedListener = viewPagerOnTabSelectedListener;
            addOnTabSelectedListener((BaseOnTabSelectedListener) viewPagerOnTabSelectedListener);
            PagerAdapter adapter = viewPager2.getAdapter();
            if (adapter != null) {
                setPagerAdapter(adapter, autoRefresh);
            }
            if (this.adapterChangeListener == null) {
                this.adapterChangeListener = new AdapterChangeListener();
            }
            this.adapterChangeListener.setAutoRefresh(autoRefresh);
            viewPager2.addOnAdapterChangeListener(this.adapterChangeListener);
            setScrollPosition(viewPager2.getCurrentItem(), 0.0f, true);
        } else {
            this.viewPager = null;
            setPagerAdapter((PagerAdapter) null, false);
        }
        this.setupViewPagerImplicitly = implicitSetup;
    }

    @Deprecated
    public void setTabsFromPagerAdapter(PagerAdapter adapter) {
        setPagerAdapter(adapter, false);
    }

    /* access modifiers changed from: package-private */
    public void updateViewPagerScrollState(int scrollState) {
        this.viewPagerScrollState = scrollState;
    }

    public boolean shouldDelayChildPressedState() {
        return getTabScrollRange() > 0;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this);
        if (this.viewPager == null) {
            ViewParent vp = getParent();
            if (vp instanceof ViewPager) {
                setupWithViewPager((ViewPager) vp, true, true);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.setupViewPagerImplicitly) {
            setupWithViewPager((ViewPager) null);
            this.setupViewPagerImplicitly = false;
        }
    }

    private int getTabScrollRange() {
        return Math.max(0, ((this.slidingTabIndicator.getWidth() - getWidth()) - getPaddingLeft()) - getPaddingRight());
    }

    /* access modifiers changed from: package-private */
    public void setPagerAdapter(PagerAdapter adapter, boolean addObserver) {
        DataSetObserver dataSetObserver;
        PagerAdapter pagerAdapter2 = this.pagerAdapter;
        if (!(pagerAdapter2 == null || (dataSetObserver = this.pagerAdapterObserver) == null)) {
            pagerAdapter2.unregisterDataSetObserver(dataSetObserver);
        }
        this.pagerAdapter = adapter;
        if (addObserver && adapter != null) {
            if (this.pagerAdapterObserver == null) {
                this.pagerAdapterObserver = new PagerAdapterObserver();
            }
            adapter.registerDataSetObserver(this.pagerAdapterObserver);
        }
        populateFromPagerAdapter();
    }

    /* access modifiers changed from: package-private */
    public void populateFromPagerAdapter() {
        int curItem;
        removeAllTabs();
        PagerAdapter pagerAdapter2 = this.pagerAdapter;
        if (pagerAdapter2 != null) {
            int adapterCount = pagerAdapter2.getCount();
            for (int i = 0; i < adapterCount; i++) {
                addTab(newTab().setText(this.pagerAdapter.getPageTitle(i)), false);
            }
            ViewPager viewPager2 = this.viewPager;
            if (viewPager2 != null && adapterCount > 0 && (curItem = viewPager2.getCurrentItem()) != getSelectedTabPosition() && curItem < getTabCount()) {
                selectTab(getTabAt(curItem));
            }
        }
    }

    private void updateAllTabs() {
        int z = this.tabs.size();
        for (int i = 0; i < z; i++) {
            this.tabs.get(i).updateView();
        }
    }

    private TabView createTabView(Tab tab) {
        Pools.Pool<TabView> pool = this.tabViewPool;
        TabView tabView = pool != null ? pool.acquire() : null;
        if (tabView == null) {
            tabView = new TabView(getContext());
        }
        tabView.setTab(tab);
        tabView.setFocusable(true);
        tabView.setMinimumWidth(getTabMinWidth());
        if (TextUtils.isEmpty(tab.contentDesc)) {
            tabView.setContentDescription(tab.text);
        } else {
            tabView.setContentDescription(tab.contentDesc);
        }
        return tabView;
    }

    private void configureTab(Tab tab, int position) {
        tab.setPosition(position);
        this.tabs.add(position, tab);
        int count = this.tabs.size();
        int newIndicatorPosition = -1;
        for (int i = position + 1; i < count; i++) {
            if (this.tabs.get(i).getPosition() == this.indicatorPosition) {
                newIndicatorPosition = i;
            }
            this.tabs.get(i).setPosition(i);
        }
        this.indicatorPosition = newIndicatorPosition;
    }

    private void addTabView(Tab tab) {
        TabView tabView = tab.view;
        tabView.setSelected(false);
        tabView.setActivated(false);
        this.slidingTabIndicator.addView(tabView, tab.getPosition(), createLayoutParamsForTabs());
    }

    public void addView(View child) {
        addViewInternal(child);
    }

    public void addView(View child, int index) {
        addViewInternal(child);
    }

    public void addView(View child, ViewGroup.LayoutParams params) {
        addViewInternal(child);
    }

    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        addViewInternal(child);
    }

    private void addViewInternal(View child) {
        if (child instanceof TabItem) {
            addTabFromItemView((TabItem) child);
            return;
        }
        throw new IllegalArgumentException("Only TabItem instances can be added to TabLayout");
    }

    private LinearLayout.LayoutParams createLayoutParamsForTabs() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -1);
        updateTabViewLayoutParams(lp);
        return lp;
    }

    private void updateTabViewLayoutParams(LinearLayout.LayoutParams lp) {
        if (this.mode == 1 && this.tabGravity == 0) {
            lp.width = 0;
            lp.weight = 1.0f;
            return;
        }
        lp.width = -2;
        lp.weight = 0.0f;
    }

    public void setElevation(float elevation) {
        super.setElevation(elevation);
        MaterialShapeUtils.setElevation(this, elevation);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        AccessibilityNodeInfoCompat.wrap(info).setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(1, getTabCount(), false, 1));
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        for (int i = 0; i < this.slidingTabIndicator.getChildCount(); i++) {
            View tabView = this.slidingTabIndicator.getChildAt(i);
            if (tabView instanceof TabView) {
                ((TabView) tabView).drawBackground(canvas);
            }
        }
        super.onDraw(canvas);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int idealHeight = Math.round(ViewUtils.dpToPx(getContext(), getDefaultHeight()));
        boolean z = false;
        switch (View.MeasureSpec.getMode(heightMeasureSpec)) {
            case Integer.MIN_VALUE:
                if (getChildCount() == 1 && View.MeasureSpec.getSize(heightMeasureSpec) >= idealHeight) {
                    getChildAt(0).setMinimumHeight(idealHeight);
                    break;
                }
            case 0:
                heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(getPaddingTop() + idealHeight + getPaddingBottom(), 1073741824);
                break;
        }
        int specWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        if (View.MeasureSpec.getMode(widthMeasureSpec) != 0) {
            int i = this.requestedTabMaxWidth;
            if (i <= 0) {
                i = (int) (((float) specWidth) - ViewUtils.dpToPx(getContext(), 56));
            }
            this.tabMaxWidth = i;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() == 1) {
            View child = getChildAt(0);
            boolean remeasure = false;
            switch (this.mode) {
                case 0:
                case 2:
                    if (child.getMeasuredWidth() < getMeasuredWidth()) {
                        z = true;
                    }
                    remeasure = z;
                    break;
                case 1:
                    if (child.getMeasuredWidth() != getMeasuredWidth()) {
                        z = true;
                    }
                    remeasure = z;
                    break;
            }
            if (remeasure) {
                child.measure(View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), getChildMeasureSpec(heightMeasureSpec, getPaddingTop() + getPaddingBottom(), child.getLayoutParams().height));
            }
        }
    }

    private void removeTabViewAt(int position) {
        TabView view = (TabView) this.slidingTabIndicator.getChildAt(position);
        this.slidingTabIndicator.removeViewAt(position);
        if (view != null) {
            view.reset();
            this.tabViewPool.release(view);
        }
        requestLayout();
    }

    private void animateToTab(int newPosition) {
        if (newPosition != -1) {
            if (getWindowToken() == null || !ViewCompat.isLaidOut(this) || this.slidingTabIndicator.childrenNeedLayout()) {
                setScrollPosition(newPosition, 0.0f, true);
                return;
            }
            int startScrollX = getScrollX();
            int targetScrollX = calculateScrollXForTab(newPosition, 0.0f);
            if (startScrollX != targetScrollX) {
                ensureScrollAnimator();
                this.scrollAnimator.setIntValues(new int[]{startScrollX, targetScrollX});
                this.scrollAnimator.start();
            }
            this.slidingTabIndicator.animateIndicatorToPosition(newPosition, this.tabIndicatorAnimationDuration);
        }
    }

    private void ensureScrollAnimator() {
        if (this.scrollAnimator == null) {
            ValueAnimator valueAnimator = new ValueAnimator();
            this.scrollAnimator = valueAnimator;
            valueAnimator.setInterpolator(this.tabIndicatorTimeInterpolator);
            this.scrollAnimator.setDuration((long) this.tabIndicatorAnimationDuration);
            this.scrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animator) {
                    TabLayout.this.scrollTo(((Integer) animator.getAnimatedValue()).intValue(), 0);
                }
            });
        }
    }

    /* access modifiers changed from: package-private */
    public void setScrollAnimatorListener(Animator.AnimatorListener listener) {
        ensureScrollAnimator();
        this.scrollAnimator.addListener(listener);
    }

    private void setSelectedTabView(int position) {
        int tabCount = this.slidingTabIndicator.getChildCount();
        if (position < tabCount) {
            int i = 0;
            while (i < tabCount) {
                View child = this.slidingTabIndicator.getChildAt(i);
                boolean z = false;
                if ((i != position || child.isSelected()) && (i == position || !child.isSelected())) {
                    child.setSelected(i == position);
                    if (i == position) {
                        z = true;
                    }
                    child.setActivated(z);
                } else {
                    child.setSelected(i == position);
                    if (i == position) {
                        z = true;
                    }
                    child.setActivated(z);
                    if (child instanceof TabView) {
                        ((TabView) child).updateTab();
                    }
                }
                i++;
            }
        }
    }

    public void selectTab(Tab tab) {
        selectTab(tab, true);
    }

    public void selectTab(Tab tab, boolean updateIndicator) {
        Tab currentTab = this.selectedTab;
        if (currentTab != tab) {
            int newPosition = tab != null ? tab.getPosition() : -1;
            if (updateIndicator) {
                if ((currentTab == null || currentTab.getPosition() == -1) && newPosition != -1) {
                    setScrollPosition(newPosition, 0.0f, true);
                } else {
                    animateToTab(newPosition);
                }
                if (newPosition != -1) {
                    setSelectedTabView(newPosition);
                }
            }
            this.selectedTab = tab;
            if (!(currentTab == null || currentTab.parent == null)) {
                dispatchTabUnselected(currentTab);
            }
            if (tab != null) {
                dispatchTabSelected(tab);
            }
        } else if (currentTab != null) {
            dispatchTabReselected(tab);
            animateToTab(tab.getPosition());
        }
    }

    private void dispatchTabSelected(Tab tab) {
        for (int i = this.selectedListeners.size() - 1; i >= 0; i--) {
            this.selectedListeners.get(i).onTabSelected(tab);
        }
    }

    private void dispatchTabUnselected(Tab tab) {
        for (int i = this.selectedListeners.size() - 1; i >= 0; i--) {
            this.selectedListeners.get(i).onTabUnselected(tab);
        }
    }

    private void dispatchTabReselected(Tab tab) {
        for (int i = this.selectedListeners.size() - 1; i >= 0; i--) {
            this.selectedListeners.get(i).onTabReselected(tab);
        }
    }

    private int calculateScrollXForTab(int position, float positionOffset) {
        View selectedChild;
        View nextChild;
        int i = this.mode;
        int nextWidth = 0;
        if ((i != 0 && i != 2) || (selectedChild = this.slidingTabIndicator.getChildAt(position)) == null) {
            return 0;
        }
        if (position + 1 < this.slidingTabIndicator.getChildCount()) {
            nextChild = this.slidingTabIndicator.getChildAt(position + 1);
        } else {
            nextChild = null;
        }
        int selectedWidth = selectedChild.getWidth();
        if (nextChild != null) {
            nextWidth = nextChild.getWidth();
        }
        int scrollBase = (selectedChild.getLeft() + (selectedWidth / 2)) - (getWidth() / 2);
        int scrollOffset = (int) (((float) (selectedWidth + nextWidth)) * 0.5f * positionOffset);
        if (ViewCompat.getLayoutDirection(this) == 0) {
            return scrollBase + scrollOffset;
        }
        return scrollBase - scrollOffset;
    }

    private void applyModeAndGravity() {
        int paddingStart = 0;
        int i = this.mode;
        if (i == 0 || i == 2) {
            paddingStart = Math.max(0, this.contentInsetStart - this.tabPaddingStart);
        }
        ViewCompat.setPaddingRelative(this.slidingTabIndicator, paddingStart, 0, 0, 0);
        switch (this.mode) {
            case 0:
                applyGravityForModeScrollable(this.tabGravity);
                break;
            case 1:
            case 2:
                if (this.tabGravity == 2) {
                    Log.w(LOG_TAG, "GRAVITY_START is not supported with the current tab mode, GRAVITY_CENTER will be used instead");
                }
                this.slidingTabIndicator.setGravity(1);
                break;
        }
        updateTabViews(true);
    }

    private void applyGravityForModeScrollable(int tabGravity2) {
        switch (tabGravity2) {
            case 0:
                Log.w(LOG_TAG, "MODE_SCROLLABLE + GRAVITY_FILL is not supported, GRAVITY_START will be used instead");
                break;
            case 1:
                this.slidingTabIndicator.setGravity(1);
                return;
            case 2:
                break;
            default:
                return;
        }
        this.slidingTabIndicator.setGravity(GravityCompat.START);
    }

    /* access modifiers changed from: package-private */
    public void updateTabViews(boolean requestLayout) {
        for (int i = 0; i < this.slidingTabIndicator.getChildCount(); i++) {
            View child = this.slidingTabIndicator.getChildAt(i);
            child.setMinimumWidth(getTabMinWidth());
            updateTabViewLayoutParams((LinearLayout.LayoutParams) child.getLayoutParams());
            if (requestLayout) {
                child.requestLayout();
            }
        }
    }

    public static class Tab {
        public static final int INVALID_POSITION = -1;
        /* access modifiers changed from: private */
        public CharSequence contentDesc;
        private View customView;
        private Drawable icon;
        /* access modifiers changed from: private */

        /* renamed from: id */
        public int f1126id = -1;
        /* access modifiers changed from: private */
        public int labelVisibilityMode = 1;
        public TabLayout parent;
        private int position = -1;
        private Object tag;
        /* access modifiers changed from: private */
        public CharSequence text;
        public TabView view;

        public Object getTag() {
            return this.tag;
        }

        public Tab setTag(Object tag2) {
            this.tag = tag2;
            return this;
        }

        public Tab setId(int id) {
            this.f1126id = id;
            TabView tabView = this.view;
            if (tabView != null) {
                tabView.setId(id);
            }
            return this;
        }

        public int getId() {
            return this.f1126id;
        }

        public View getCustomView() {
            return this.customView;
        }

        public Tab setCustomView(View view2) {
            this.customView = view2;
            updateView();
            return this;
        }

        public Tab setCustomView(int resId) {
            return setCustomView(LayoutInflater.from(this.view.getContext()).inflate(resId, this.view, false));
        }

        public Drawable getIcon() {
            return this.icon;
        }

        public int getPosition() {
            return this.position;
        }

        /* access modifiers changed from: package-private */
        public void setPosition(int position2) {
            this.position = position2;
        }

        public CharSequence getText() {
            return this.text;
        }

        public Tab setIcon(Drawable icon2) {
            this.icon = icon2;
            if (this.parent.tabGravity == 1 || this.parent.mode == 2) {
                this.parent.updateTabViews(true);
            }
            updateView();
            if (BadgeUtils.USE_COMPAT_PARENT && this.view.hasBadgeDrawable() && this.view.badgeDrawable.isVisible()) {
                this.view.invalidate();
            }
            return this;
        }

        public Tab setIcon(int resId) {
            TabLayout tabLayout = this.parent;
            if (tabLayout != null) {
                return setIcon(AppCompatResources.getDrawable(tabLayout.getContext(), resId));
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        public Tab setText(CharSequence text2) {
            if (TextUtils.isEmpty(this.contentDesc) && !TextUtils.isEmpty(text2)) {
                this.view.setContentDescription(text2);
            }
            this.text = text2;
            updateView();
            return this;
        }

        public Tab setText(int resId) {
            TabLayout tabLayout = this.parent;
            if (tabLayout != null) {
                return setText(tabLayout.getResources().getText(resId));
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        public BadgeDrawable getOrCreateBadge() {
            return this.view.getOrCreateBadge();
        }

        public void removeBadge() {
            this.view.removeBadge();
        }

        public BadgeDrawable getBadge() {
            return this.view.getBadge();
        }

        public Tab setTabLabelVisibility(int mode) {
            this.labelVisibilityMode = mode;
            if (this.parent.tabGravity == 1 || this.parent.mode == 2) {
                this.parent.updateTabViews(true);
            }
            updateView();
            if (BadgeUtils.USE_COMPAT_PARENT && this.view.hasBadgeDrawable() && this.view.badgeDrawable.isVisible()) {
                this.view.invalidate();
            }
            return this;
        }

        public int getTabLabelVisibility() {
            return this.labelVisibilityMode;
        }

        public void select() {
            TabLayout tabLayout = this.parent;
            if (tabLayout != null) {
                tabLayout.selectTab(this);
                return;
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        public boolean isSelected() {
            TabLayout tabLayout = this.parent;
            if (tabLayout != null) {
                int selectedPosition = tabLayout.getSelectedTabPosition();
                return selectedPosition != -1 && selectedPosition == this.position;
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        public Tab setContentDescription(int resId) {
            TabLayout tabLayout = this.parent;
            if (tabLayout != null) {
                return setContentDescription(tabLayout.getResources().getText(resId));
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        public Tab setContentDescription(CharSequence contentDesc2) {
            this.contentDesc = contentDesc2;
            updateView();
            return this;
        }

        public CharSequence getContentDescription() {
            TabView tabView = this.view;
            if (tabView == null) {
                return null;
            }
            return tabView.getContentDescription();
        }

        /* access modifiers changed from: package-private */
        public void updateView() {
            TabView tabView = this.view;
            if (tabView != null) {
                tabView.update();
            }
        }

        /* access modifiers changed from: package-private */
        public void reset() {
            this.parent = null;
            this.view = null;
            this.tag = null;
            this.icon = null;
            this.f1126id = -1;
            this.text = null;
            this.contentDesc = null;
            this.position = -1;
            this.customView = null;
        }
    }

    public final class TabView extends LinearLayout {
        private View badgeAnchorView;
        /* access modifiers changed from: private */
        public BadgeDrawable badgeDrawable;
        private Drawable baseBackgroundDrawable;
        private ImageView customIconView;
        private TextView customTextView;
        private View customView;
        private int defaultMaxLines = 2;
        private ImageView iconView;
        private Tab tab;
        private TextView textView;

        public TabView(Context context) {
            super(context);
            updateBackgroundDrawable(context);
            ViewCompat.setPaddingRelative(this, TabLayout.this.tabPaddingStart, TabLayout.this.tabPaddingTop, TabLayout.this.tabPaddingEnd, TabLayout.this.tabPaddingBottom);
            setGravity(17);
            setOrientation(TabLayout.this.inlineLabel ^ true ? 1 : 0);
            setClickable(true);
            ViewCompat.setPointerIcon(this, PointerIconCompat.getSystemIcon(getContext(), PointerIconCompat.TYPE_HAND));
        }

        /* access modifiers changed from: private */
        public void updateBackgroundDrawable(Context context) {
            Drawable background;
            GradientDrawable gradientDrawable = null;
            if (TabLayout.this.tabBackgroundResId != 0) {
                Drawable drawable = AppCompatResources.getDrawable(context, TabLayout.this.tabBackgroundResId);
                this.baseBackgroundDrawable = drawable;
                if (drawable != null && drawable.isStateful()) {
                    this.baseBackgroundDrawable.setState(getDrawableState());
                }
            } else {
                this.baseBackgroundDrawable = null;
            }
            Drawable contentDrawable = new GradientDrawable();
            ((GradientDrawable) contentDrawable).setColor(0);
            if (TabLayout.this.tabRippleColorStateList != null) {
                GradientDrawable maskDrawable = new GradientDrawable();
                maskDrawable.setCornerRadius(1.0E-5f);
                maskDrawable.setColor(-1);
                ColorStateList rippleColor = RippleUtils.convertToRippleDrawableColor(TabLayout.this.tabRippleColorStateList);
                if (Build.VERSION.SDK_INT >= 21) {
                    GradientDrawable gradientDrawable2 = TabLayout.this.unboundedRipple ? null : contentDrawable;
                    if (!TabLayout.this.unboundedRipple) {
                        gradientDrawable = maskDrawable;
                    }
                    background = new RippleDrawable(rippleColor, gradientDrawable2, gradientDrawable);
                } else {
                    Drawable rippleDrawable = DrawableCompat.wrap(maskDrawable);
                    DrawableCompat.setTintList(rippleDrawable, rippleColor);
                    background = new LayerDrawable(new Drawable[]{contentDrawable, rippleDrawable});
                }
            } else {
                background = contentDrawable;
            }
            ViewCompat.setBackground(this, background);
            TabLayout.this.invalidate();
        }

        /* access modifiers changed from: private */
        public void drawBackground(Canvas canvas) {
            Drawable drawable = this.baseBackgroundDrawable;
            if (drawable != null) {
                drawable.setBounds(getLeft(), getTop(), getRight(), getBottom());
                this.baseBackgroundDrawable.draw(canvas);
            }
        }

        /* access modifiers changed from: protected */
        public void drawableStateChanged() {
            super.drawableStateChanged();
            boolean changed = false;
            int[] state = getDrawableState();
            Drawable drawable = this.baseBackgroundDrawable;
            if (drawable != null && drawable.isStateful()) {
                changed = false | this.baseBackgroundDrawable.setState(state);
            }
            if (changed) {
                invalidate();
                TabLayout.this.invalidate();
            }
        }

        public boolean performClick() {
            boolean handled = super.performClick();
            if (this.tab == null) {
                return handled;
            }
            if (!handled) {
                playSoundEffect(0);
            }
            this.tab.select();
            return true;
        }

        public void setSelected(boolean selected) {
            if (isSelected() != selected) {
            }
            super.setSelected(selected);
            TextView textView2 = this.textView;
            if (textView2 != null) {
                textView2.setSelected(selected);
            }
            ImageView imageView = this.iconView;
            if (imageView != null) {
                imageView.setSelected(selected);
            }
            View view = this.customView;
            if (view != null) {
                view.setSelected(selected);
            }
        }

        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
            super.onInitializeAccessibilityNodeInfo(info);
            BadgeDrawable badgeDrawable2 = this.badgeDrawable;
            if (badgeDrawable2 != null && badgeDrawable2.isVisible()) {
                info.setContentDescription(getContentDescription() + ", " + this.badgeDrawable.getContentDescription());
            }
            AccessibilityNodeInfoCompat infoCompat = AccessibilityNodeInfoCompat.wrap(info);
            infoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(0, 1, this.tab.getPosition(), 1, false, isSelected()));
            if (isSelected()) {
                infoCompat.setClickable(false);
                infoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
            }
            infoCompat.setRoleDescription(getResources().getString(C1087R.string.item_view_role_description));
        }

        public void onMeasure(int origWidthMeasureSpec, int origHeightMeasureSpec) {
            int widthMeasureSpec;
            Layout layout;
            int specWidthSize = View.MeasureSpec.getSize(origWidthMeasureSpec);
            int specWidthMode = View.MeasureSpec.getMode(origWidthMeasureSpec);
            int maxWidth = TabLayout.this.getTabMaxWidth();
            int heightMeasureSpec = origHeightMeasureSpec;
            if (maxWidth <= 0 || (specWidthMode != 0 && specWidthSize <= maxWidth)) {
                widthMeasureSpec = origWidthMeasureSpec;
            } else {
                widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(TabLayout.this.tabMaxWidth, Integer.MIN_VALUE);
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            if (this.textView != null) {
                float textSize = TabLayout.this.tabTextSize;
                int maxLines = this.defaultMaxLines;
                ImageView imageView = this.iconView;
                if (imageView == null || imageView.getVisibility() != 0) {
                    TextView textView2 = this.textView;
                    if (textView2 != null && textView2.getLineCount() > 1) {
                        textSize = TabLayout.this.tabTextMultiLineSize;
                    }
                } else {
                    maxLines = 1;
                }
                float curTextSize = this.textView.getTextSize();
                int curLineCount = this.textView.getLineCount();
                int curMaxLines = TextViewCompat.getMaxLines(this.textView);
                if (textSize != curTextSize || (curMaxLines >= 0 && maxLines != curMaxLines)) {
                    boolean updateTextView = true;
                    if (TabLayout.this.mode == 1 && textSize > curTextSize && curLineCount == 1 && ((layout = this.textView.getLayout()) == null || approximateLineWidth(layout, 0, textSize) > ((float) ((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight())))) {
                        updateTextView = false;
                    }
                    if (updateTextView) {
                        this.textView.setTextSize(0, textSize);
                        this.textView.setMaxLines(maxLines);
                        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void setTab(Tab tab2) {
            if (tab2 != this.tab) {
                this.tab = tab2;
                update();
            }
        }

        /* access modifiers changed from: package-private */
        public void reset() {
            setTab((Tab) null);
            setSelected(false);
        }

        /* access modifiers changed from: package-private */
        public final void updateTab() {
            ViewParent customViewParent;
            Tab tab2 = this.tab;
            View custom = tab2 != null ? tab2.getCustomView() : null;
            if (custom != null) {
                ViewParent customParent = custom.getParent();
                if (customParent != this) {
                    if (customParent != null) {
                        ((ViewGroup) customParent).removeView(custom);
                    }
                    View view = this.customView;
                    if (!(view == null || (customViewParent = view.getParent()) == null)) {
                        ((ViewGroup) customViewParent).removeView(this.customView);
                    }
                    addView(custom);
                }
                this.customView = custom;
                TextView textView2 = this.textView;
                if (textView2 != null) {
                    textView2.setVisibility(8);
                }
                ImageView imageView = this.iconView;
                if (imageView != null) {
                    imageView.setVisibility(8);
                    this.iconView.setImageDrawable((Drawable) null);
                }
                TextView textView3 = (TextView) custom.findViewById(16908308);
                this.customTextView = textView3;
                if (textView3 != null) {
                    this.defaultMaxLines = TextViewCompat.getMaxLines(textView3);
                }
                this.customIconView = (ImageView) custom.findViewById(16908294);
            } else {
                View view2 = this.customView;
                if (view2 != null) {
                    removeView(view2);
                    this.customView = null;
                }
                this.customTextView = null;
                this.customIconView = null;
            }
            if (this.customView == null) {
                if (this.iconView == null) {
                    inflateAndAddDefaultIconView();
                }
                if (this.textView == null) {
                    inflateAndAddDefaultTextView();
                    this.defaultMaxLines = TextViewCompat.getMaxLines(this.textView);
                }
                TextViewCompat.setTextAppearance(this.textView, TabLayout.this.defaultTabTextAppearance);
                if (!isSelected() || TabLayout.this.selectedTabTextAppearance == -1) {
                    TextViewCompat.setTextAppearance(this.textView, TabLayout.this.tabTextAppearance);
                } else {
                    TextViewCompat.setTextAppearance(this.textView, TabLayout.this.selectedTabTextAppearance);
                }
                if (TabLayout.this.tabTextColors != null) {
                    this.textView.setTextColor(TabLayout.this.tabTextColors);
                }
                updateTextAndIcon(this.textView, this.iconView, true);
                tryUpdateBadgeAnchor();
                addOnLayoutChangeListener(this.iconView);
                addOnLayoutChangeListener(this.textView);
            } else {
                TextView textView4 = this.customTextView;
                if (!(textView4 == null && this.customIconView == null)) {
                    updateTextAndIcon(textView4, this.customIconView, false);
                }
            }
            if (tab2 != null && !TextUtils.isEmpty(tab2.contentDesc)) {
                setContentDescription(tab2.contentDesc);
            }
        }

        /* access modifiers changed from: package-private */
        public final void update() {
            updateTab();
            Tab tab2 = this.tab;
            setSelected(tab2 != null && tab2.isSelected());
        }

        private void inflateAndAddDefaultIconView() {
            ViewGroup iconViewParent = this;
            if (BadgeUtils.USE_COMPAT_PARENT) {
                iconViewParent = createPreApi18BadgeAnchorRoot();
                addView(iconViewParent, 0);
            }
            ImageView imageView = (ImageView) LayoutInflater.from(getContext()).inflate(C1087R.C1092layout.design_layout_tab_icon, iconViewParent, false);
            this.iconView = imageView;
            iconViewParent.addView(imageView, 0);
        }

        private void inflateAndAddDefaultTextView() {
            ViewGroup textViewParent = this;
            if (BadgeUtils.USE_COMPAT_PARENT) {
                textViewParent = createPreApi18BadgeAnchorRoot();
                addView(textViewParent);
            }
            TextView textView2 = (TextView) LayoutInflater.from(getContext()).inflate(C1087R.C1092layout.design_layout_tab_text, textViewParent, false);
            this.textView = textView2;
            textViewParent.addView(textView2);
        }

        private FrameLayout createPreApi18BadgeAnchorRoot() {
            FrameLayout frameLayout = new FrameLayout(getContext());
            frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
            return frameLayout;
        }

        /* access modifiers changed from: private */
        public BadgeDrawable getOrCreateBadge() {
            if (this.badgeDrawable == null) {
                this.badgeDrawable = BadgeDrawable.create(getContext());
            }
            tryUpdateBadgeAnchor();
            BadgeDrawable badgeDrawable2 = this.badgeDrawable;
            if (badgeDrawable2 != null) {
                return badgeDrawable2;
            }
            throw new IllegalStateException("Unable to create badge");
        }

        /* access modifiers changed from: private */
        public BadgeDrawable getBadge() {
            return this.badgeDrawable;
        }

        /* access modifiers changed from: private */
        public void removeBadge() {
            if (this.badgeAnchorView != null) {
                tryRemoveBadgeFromAnchor();
            }
            this.badgeDrawable = null;
        }

        private void addOnLayoutChangeListener(final View view) {
            if (view != null) {
                view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                        if (view.getVisibility() == 0) {
                            TabView.this.tryUpdateBadgeDrawableBounds(view);
                        }
                    }
                });
            }
        }

        private void tryUpdateBadgeAnchor() {
            Tab tab2;
            Tab tab3;
            if (hasBadgeDrawable()) {
                if (this.customView != null) {
                    tryRemoveBadgeFromAnchor();
                } else if (this.iconView != null && (tab3 = this.tab) != null && tab3.getIcon() != null) {
                    View view = this.badgeAnchorView;
                    ImageView imageView = this.iconView;
                    if (view != imageView) {
                        tryRemoveBadgeFromAnchor();
                        tryAttachBadgeToAnchor(this.iconView);
                        return;
                    }
                    tryUpdateBadgeDrawableBounds(imageView);
                } else if (this.textView == null || (tab2 = this.tab) == null || tab2.getTabLabelVisibility() != 1) {
                    tryRemoveBadgeFromAnchor();
                } else {
                    View view2 = this.badgeAnchorView;
                    TextView textView2 = this.textView;
                    if (view2 != textView2) {
                        tryRemoveBadgeFromAnchor();
                        tryAttachBadgeToAnchor(this.textView);
                        return;
                    }
                    tryUpdateBadgeDrawableBounds(textView2);
                }
            }
        }

        private void tryAttachBadgeToAnchor(View anchorView) {
            if (hasBadgeDrawable() && anchorView != null) {
                clipViewToPaddingForBadge(false);
                BadgeUtils.attachBadgeDrawable(this.badgeDrawable, anchorView, getCustomParentForBadge(anchorView));
                this.badgeAnchorView = anchorView;
            }
        }

        private void tryRemoveBadgeFromAnchor() {
            if (hasBadgeDrawable()) {
                clipViewToPaddingForBadge(true);
                View view = this.badgeAnchorView;
                if (view != null) {
                    BadgeUtils.detachBadgeDrawable(this.badgeDrawable, view);
                    this.badgeAnchorView = null;
                }
            }
        }

        private void clipViewToPaddingForBadge(boolean flag) {
            setClipChildren(flag);
            setClipToPadding(flag);
            ViewGroup parent = (ViewGroup) getParent();
            if (parent != null) {
                parent.setClipChildren(flag);
                parent.setClipToPadding(flag);
            }
        }

        /* access modifiers changed from: package-private */
        public final void updateOrientation() {
            setOrientation(TabLayout.this.inlineLabel ^ true ? 1 : 0);
            TextView textView2 = this.customTextView;
            if (textView2 == null && this.customIconView == null) {
                updateTextAndIcon(this.textView, this.iconView, true);
            } else {
                updateTextAndIcon(textView2, this.customIconView, false);
            }
        }

        private void updateTextAndIcon(TextView textView2, ImageView iconView2, boolean addDefaultMargins) {
            Drawable icon;
            Tab tab2 = this.tab;
            CharSequence contentDesc = null;
            if (tab2 == null || tab2.getIcon() == null) {
                icon = null;
            } else {
                icon = DrawableCompat.wrap(this.tab.getIcon()).mutate();
            }
            if (icon != null) {
                DrawableCompat.setTintList(icon, TabLayout.this.tabIconTint);
                if (TabLayout.this.tabIconTintMode != null) {
                    DrawableCompat.setTintMode(icon, TabLayout.this.tabIconTintMode);
                }
            }
            Tab tab3 = this.tab;
            CharSequence text = tab3 != null ? tab3.getText() : null;
            if (iconView2 != null) {
                if (icon != null) {
                    iconView2.setImageDrawable(icon);
                    iconView2.setVisibility(0);
                    setVisibility(0);
                } else {
                    iconView2.setVisibility(8);
                    iconView2.setImageDrawable((Drawable) null);
                }
            }
            boolean showingText = true;
            boolean hasText = !TextUtils.isEmpty(text);
            if (textView2 != null) {
                if (!hasText || this.tab.labelVisibilityMode != 1) {
                    showingText = false;
                }
                textView2.setText(hasText ? text : null);
                textView2.setVisibility(showingText ? 0 : 8);
                if (hasText) {
                    setVisibility(0);
                }
            } else {
                showingText = false;
            }
            if (addDefaultMargins && iconView2 != null) {
                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) iconView2.getLayoutParams();
                int iconMargin = 0;
                if (showingText && iconView2.getVisibility() == 0) {
                    iconMargin = (int) ViewUtils.dpToPx(getContext(), 8);
                }
                if (TabLayout.this.inlineLabel) {
                    if (iconMargin != MarginLayoutParamsCompat.getMarginEnd(lp)) {
                        MarginLayoutParamsCompat.setMarginEnd(lp, iconMargin);
                        lp.bottomMargin = 0;
                        iconView2.setLayoutParams(lp);
                        iconView2.requestLayout();
                    }
                } else if (iconMargin != lp.bottomMargin) {
                    lp.bottomMargin = iconMargin;
                    MarginLayoutParamsCompat.setMarginEnd(lp, 0);
                    iconView2.setLayoutParams(lp);
                    iconView2.requestLayout();
                }
            }
            Tab tab4 = this.tab;
            if (tab4 != null) {
                contentDesc = tab4.contentDesc;
            }
            if (Build.VERSION.SDK_INT < 21 || Build.VERSION.SDK_INT > 23) {
                TooltipCompat.setTooltipText(this, hasText ? text : contentDesc);
            }
        }

        /* access modifiers changed from: private */
        public void tryUpdateBadgeDrawableBounds(View anchor) {
            if (hasBadgeDrawable() && anchor == this.badgeAnchorView) {
                BadgeUtils.setBadgeDrawableBounds(this.badgeDrawable, anchor, getCustomParentForBadge(anchor));
            }
        }

        /* access modifiers changed from: private */
        public boolean hasBadgeDrawable() {
            return this.badgeDrawable != null;
        }

        private FrameLayout getCustomParentForBadge(View anchor) {
            if ((anchor == this.iconView || anchor == this.textView) && BadgeUtils.USE_COMPAT_PARENT) {
                return (FrameLayout) anchor.getParent();
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        public int getContentWidth() {
            boolean initialized = false;
            int left = 0;
            int right = 0;
            View[] viewArr = {this.textView, this.iconView, this.customView};
            for (int i = 0; i < 3; i++) {
                View view = viewArr[i];
                if (view != null && view.getVisibility() == 0) {
                    int left2 = view.getLeft();
                    if (initialized) {
                        left2 = Math.min(left, left2);
                    }
                    left = left2;
                    int right2 = view.getRight();
                    if (initialized) {
                        right2 = Math.max(right, right2);
                    }
                    right = right2;
                    initialized = true;
                }
            }
            return right - left;
        }

        /* access modifiers changed from: package-private */
        public int getContentHeight() {
            boolean initialized = false;
            int top = 0;
            int bottom = 0;
            View[] viewArr = {this.textView, this.iconView, this.customView};
            for (int i = 0; i < 3; i++) {
                View view = viewArr[i];
                if (view != null && view.getVisibility() == 0) {
                    int top2 = view.getTop();
                    if (initialized) {
                        top2 = Math.min(top, top2);
                    }
                    top = top2;
                    int bottom2 = view.getBottom();
                    if (initialized) {
                        bottom2 = Math.max(bottom, bottom2);
                    }
                    bottom = bottom2;
                    initialized = true;
                }
            }
            return bottom - top;
        }

        public Tab getTab() {
            return this.tab;
        }

        private float approximateLineWidth(Layout layout, int line, float textSize) {
            return layout.getLineWidth(line) * (textSize / layout.getPaint().getTextSize());
        }
    }

    class SlidingTabIndicator extends LinearLayout {
        ValueAnimator indicatorAnimator;
        private int layoutDirection = -1;

        SlidingTabIndicator(Context context) {
            super(context);
            setWillNotDraw(false);
        }

        /* access modifiers changed from: package-private */
        public void setSelectedIndicatorHeight(int height) {
            Rect bounds = TabLayout.this.tabSelectedIndicator.getBounds();
            TabLayout.this.tabSelectedIndicator.setBounds(bounds.left, 0, bounds.right, height);
            requestLayout();
        }

        /* access modifiers changed from: package-private */
        public boolean childrenNeedLayout() {
            int z = getChildCount();
            for (int i = 0; i < z; i++) {
                if (getChildAt(i).getWidth() <= 0) {
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: package-private */
        public void setIndicatorPositionFromTabPosition(int position, float positionOffset) {
            TabLayout.this.indicatorPosition = Math.round(((float) position) + positionOffset);
            ValueAnimator valueAnimator = this.indicatorAnimator;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.indicatorAnimator.cancel();
            }
            tweenIndicatorPosition(getChildAt(position), getChildAt(position + 1), positionOffset);
        }

        public void onRtlPropertiesChanged(int layoutDirection2) {
            super.onRtlPropertiesChanged(layoutDirection2);
            if (Build.VERSION.SDK_INT < 23 && this.layoutDirection != layoutDirection2) {
                requestLayout();
                this.layoutDirection = layoutDirection2;
            }
        }

        /* access modifiers changed from: protected */
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            if (View.MeasureSpec.getMode(widthMeasureSpec) == 1073741824) {
                if (TabLayout.this.tabGravity == 1 || TabLayout.this.mode == 2) {
                    int count = getChildCount();
                    int largestTabWidth = 0;
                    int z = count;
                    for (int i = 0; i < z; i++) {
                        View child = getChildAt(i);
                        if (child.getVisibility() == 0) {
                            largestTabWidth = Math.max(largestTabWidth, child.getMeasuredWidth());
                        }
                    }
                    if (largestTabWidth > 0) {
                        boolean remeasure = false;
                        if (largestTabWidth * count <= getMeasuredWidth() - (((int) ViewUtils.dpToPx(getContext(), 16)) * 2)) {
                            for (int i2 = 0; i2 < count; i2++) {
                                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) getChildAt(i2).getLayoutParams();
                                if (lp.width != largestTabWidth || lp.weight != 0.0f) {
                                    lp.width = largestTabWidth;
                                    lp.weight = 0.0f;
                                    remeasure = true;
                                }
                            }
                        } else {
                            TabLayout.this.tabGravity = 0;
                            TabLayout.this.updateTabViews(false);
                            remeasure = true;
                        }
                        if (remeasure) {
                            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                        }
                    }
                }
            }
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean changed, int l, int t, int r, int b) {
            super.onLayout(changed, l, t, r, b);
            ValueAnimator valueAnimator = this.indicatorAnimator;
            if (valueAnimator == null || !valueAnimator.isRunning()) {
                jumpIndicatorToIndicatorPosition();
            } else {
                updateOrRecreateIndicatorAnimation(false, TabLayout.this.getSelectedTabPosition(), -1);
            }
        }

        private void jumpIndicatorToPosition(int position) {
            if (TabLayout.this.viewPagerScrollState == 0) {
                View currentView = getChildAt(position);
                TabIndicatorInterpolator access$1700 = TabLayout.this.tabIndicatorInterpolator;
                TabLayout tabLayout = TabLayout.this;
                access$1700.setIndicatorBoundsForTab(tabLayout, currentView, tabLayout.tabSelectedIndicator);
                TabLayout.this.indicatorPosition = position;
            }
        }

        /* access modifiers changed from: private */
        public void jumpIndicatorToSelectedPosition() {
            jumpIndicatorToPosition(TabLayout.this.getSelectedTabPosition());
        }

        private void jumpIndicatorToIndicatorPosition() {
            if (TabLayout.this.indicatorPosition == -1) {
                TabLayout tabLayout = TabLayout.this;
                tabLayout.indicatorPosition = tabLayout.getSelectedTabPosition();
            }
            jumpIndicatorToPosition(TabLayout.this.indicatorPosition);
        }

        /* access modifiers changed from: private */
        public void tweenIndicatorPosition(View startTitle, View endTitle, float fraction) {
            if (startTitle != null && startTitle.getWidth() > 0) {
                TabIndicatorInterpolator access$1700 = TabLayout.this.tabIndicatorInterpolator;
                TabLayout tabLayout = TabLayout.this;
                access$1700.updateIndicatorForOffset(tabLayout, startTitle, endTitle, fraction, tabLayout.tabSelectedIndicator);
            } else {
                TabLayout.this.tabSelectedIndicator.setBounds(-1, TabLayout.this.tabSelectedIndicator.getBounds().top, -1, TabLayout.this.tabSelectedIndicator.getBounds().bottom);
            }
            ViewCompat.postInvalidateOnAnimation(this);
        }

        /* access modifiers changed from: package-private */
        public void animateIndicatorToPosition(int position, int duration) {
            ValueAnimator valueAnimator = this.indicatorAnimator;
            if (!(valueAnimator == null || !valueAnimator.isRunning() || TabLayout.this.indicatorPosition == position)) {
                this.indicatorAnimator.cancel();
            }
            updateOrRecreateIndicatorAnimation(true, position, duration);
        }

        private void updateOrRecreateIndicatorAnimation(boolean recreateAnimation, int position, int duration) {
            if (TabLayout.this.indicatorPosition != position) {
                final View currentView = getChildAt(TabLayout.this.getSelectedTabPosition());
                final View targetView = getChildAt(position);
                if (targetView == null) {
                    jumpIndicatorToSelectedPosition();
                    return;
                }
                TabLayout.this.indicatorPosition = position;
                ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        SlidingTabIndicator.this.tweenIndicatorPosition(currentView, targetView, valueAnimator.getAnimatedFraction());
                    }
                };
                if (recreateAnimation) {
                    ValueAnimator animator = new ValueAnimator();
                    this.indicatorAnimator = animator;
                    animator.setInterpolator(TabLayout.this.tabIndicatorTimeInterpolator);
                    animator.setDuration((long) duration);
                    animator.setFloatValues(new float[]{0.0f, 1.0f});
                    animator.addUpdateListener(updateListener);
                    animator.start();
                    return;
                }
                this.indicatorAnimator.removeAllUpdateListeners();
                this.indicatorAnimator.addUpdateListener(updateListener);
            }
        }

        public void draw(Canvas canvas) {
            int indicatorHeight = TabLayout.this.tabSelectedIndicator.getBounds().height();
            if (indicatorHeight < 0) {
                indicatorHeight = TabLayout.this.tabSelectedIndicator.getIntrinsicHeight();
            }
            int indicatorTop = 0;
            int indicatorBottom = 0;
            switch (TabLayout.this.tabIndicatorGravity) {
                case 0:
                    indicatorTop = getHeight() - indicatorHeight;
                    indicatorBottom = getHeight();
                    break;
                case 1:
                    indicatorTop = (getHeight() - indicatorHeight) / 2;
                    indicatorBottom = (getHeight() + indicatorHeight) / 2;
                    break;
                case 2:
                    indicatorTop = 0;
                    indicatorBottom = indicatorHeight;
                    break;
                case 3:
                    indicatorTop = 0;
                    indicatorBottom = getHeight();
                    break;
            }
            if (TabLayout.this.tabSelectedIndicator.getBounds().width() > 0) {
                Rect indicatorBounds = TabLayout.this.tabSelectedIndicator.getBounds();
                TabLayout.this.tabSelectedIndicator.setBounds(indicatorBounds.left, indicatorTop, indicatorBounds.right, indicatorBottom);
                TabLayout.this.tabSelectedIndicator.draw(canvas);
            }
            super.draw(canvas);
        }
    }

    private static ColorStateList createColorStateList(int defaultColor, int selectedColor) {
        int[][] states = new int[2][];
        int[] colors = new int[2];
        states[0] = SELECTED_STATE_SET;
        colors[0] = selectedColor;
        int i = 0 + 1;
        states[i] = EMPTY_STATE_SET;
        colors[i] = defaultColor;
        int i2 = i + 1;
        return new ColorStateList(states, colors);
    }

    private int getDefaultHeight() {
        boolean hasIconAndText = false;
        int i = 0;
        int count = this.tabs.size();
        while (true) {
            if (i < count) {
                Tab tab = this.tabs.get(i);
                if (tab != null && tab.getIcon() != null && !TextUtils.isEmpty(tab.getText())) {
                    hasIconAndText = true;
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        return (!hasIconAndText || this.inlineLabel) ? 48 : 72;
    }

    private int getTabMinWidth() {
        int i = this.requestedTabMinWidth;
        if (i != -1) {
            return i;
        }
        int i2 = this.mode;
        if (i2 == 0 || i2 == 2) {
            return this.scrollableTabMinWidth;
        }
        return 0;
    }

    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return generateDefaultLayoutParams();
    }

    /* access modifiers changed from: package-private */
    public int getTabMaxWidth() {
        return this.tabMaxWidth;
    }

    public static class TabLayoutOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private int previousScrollState;
        private int scrollState;
        private final WeakReference<TabLayout> tabLayoutRef;

        public TabLayoutOnPageChangeListener(TabLayout tabLayout) {
            this.tabLayoutRef = new WeakReference<>(tabLayout);
        }

        public void onPageScrollStateChanged(int state) {
            this.previousScrollState = this.scrollState;
            this.scrollState = state;
            TabLayout tabLayout = (TabLayout) this.tabLayoutRef.get();
            if (tabLayout != null) {
                tabLayout.updateViewPagerScrollState(this.scrollState);
            }
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            TabLayout tabLayout = (TabLayout) this.tabLayoutRef.get();
            if (tabLayout != null) {
                int i = this.scrollState;
                boolean updateIndicator = false;
                boolean updateText = i != 2 || this.previousScrollState == 1;
                if (!(i == 2 && this.previousScrollState == 0)) {
                    updateIndicator = true;
                }
                tabLayout.setScrollPosition(position, positionOffset, updateText, updateIndicator);
            }
        }

        public void onPageSelected(int position) {
            TabLayout tabLayout = (TabLayout) this.tabLayoutRef.get();
            if (tabLayout != null && tabLayout.getSelectedTabPosition() != position && position < tabLayout.getTabCount()) {
                int i = this.scrollState;
                tabLayout.selectTab(tabLayout.getTabAt(position), i == 0 || (i == 2 && this.previousScrollState == 0));
            }
        }

        /* access modifiers changed from: package-private */
        public void reset() {
            this.scrollState = 0;
            this.previousScrollState = 0;
        }
    }

    public static class ViewPagerOnTabSelectedListener implements OnTabSelectedListener {
        private final ViewPager viewPager;

        public ViewPagerOnTabSelectedListener(ViewPager viewPager2) {
            this.viewPager = viewPager2;
        }

        public void onTabSelected(Tab tab) {
            this.viewPager.setCurrentItem(tab.getPosition());
        }

        public void onTabUnselected(Tab tab) {
        }

        public void onTabReselected(Tab tab) {
        }
    }

    private class PagerAdapterObserver extends DataSetObserver {
        PagerAdapterObserver() {
        }

        public void onChanged() {
            TabLayout.this.populateFromPagerAdapter();
        }

        public void onInvalidated() {
            TabLayout.this.populateFromPagerAdapter();
        }
    }

    private class AdapterChangeListener implements ViewPager.OnAdapterChangeListener {
        private boolean autoRefresh;

        AdapterChangeListener() {
        }

        public void onAdapterChanged(ViewPager viewPager, PagerAdapter oldAdapter, PagerAdapter newAdapter) {
            if (TabLayout.this.viewPager == viewPager) {
                TabLayout.this.setPagerAdapter(newAdapter, this.autoRefresh);
            }
        }

        /* access modifiers changed from: package-private */
        public void setAutoRefresh(boolean autoRefresh2) {
            this.autoRefresh = autoRefresh2;
        }
    }
}
