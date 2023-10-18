package com.google.android.material.bottomnavigation;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.C1087R;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.navigation.NavigationBarMenuView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.shape.MaterialShapeDrawable;

public class BottomNavigationView extends NavigationBarView {
    private static final int MAX_ITEM_COUNT = 5;

    @Deprecated
    public interface OnNavigationItemReselectedListener extends NavigationBarView.OnItemReselectedListener {
    }

    @Deprecated
    public interface OnNavigationItemSelectedListener extends NavigationBarView.OnItemSelectedListener {
    }

    public BottomNavigationView(Context context) {
        this(context, (AttributeSet) null);
    }

    public BottomNavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, C1087R.attr.bottomNavigationStyle);
    }

    public BottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, C1087R.C1093style.Widget_Design_BottomNavigationView);
    }

    public BottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Context context2 = getContext();
        TintTypedArray attributes = ThemeEnforcement.obtainTintedStyledAttributes(context2, attrs, C1087R.styleable.BottomNavigationView, defStyleAttr, defStyleRes, new int[0]);
        setItemHorizontalTranslationEnabled(attributes.getBoolean(C1087R.styleable.BottomNavigationView_itemHorizontalTranslationEnabled, true));
        if (attributes.hasValue(C1087R.styleable.BottomNavigationView_android_minHeight)) {
            setMinimumHeight(attributes.getDimensionPixelSize(C1087R.styleable.BottomNavigationView_android_minHeight, 0));
        }
        if (attributes.getBoolean(C1087R.styleable.BottomNavigationView_compatShadowEnabled, true) && shouldDrawCompatibilityTopDivider()) {
            addCompatibilityTopDivider(context2);
        }
        attributes.recycle();
        applyWindowInsets();
    }

    private void applyWindowInsets() {
        ViewUtils.doOnApplyWindowInsets(this, new ViewUtils.OnApplyWindowInsetsListener() {
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets, ViewUtils.RelativePadding initialPadding) {
                initialPadding.bottom += insets.getSystemWindowInsetBottom();
                boolean z = true;
                if (ViewCompat.getLayoutDirection(view) != 1) {
                    z = false;
                }
                boolean isRtl = z;
                int systemWindowInsetLeft = insets.getSystemWindowInsetLeft();
                int systemWindowInsetRight = insets.getSystemWindowInsetRight();
                initialPadding.start += isRtl ? systemWindowInsetRight : systemWindowInsetLeft;
                initialPadding.end += isRtl ? systemWindowInsetLeft : systemWindowInsetRight;
                initialPadding.applyToView(view);
                return insets;
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, makeMinHeightSpec(heightMeasureSpec));
    }

    private int makeMinHeightSpec(int measureSpec) {
        int minHeight = getSuggestedMinimumHeight();
        if (View.MeasureSpec.getMode(measureSpec) == 1073741824 || minHeight <= 0) {
            return measureSpec;
        }
        return View.MeasureSpec.makeMeasureSpec(Math.min(View.MeasureSpec.getSize(measureSpec), minHeight + getPaddingTop() + getPaddingBottom()), 1073741824);
    }

    public void setItemHorizontalTranslationEnabled(boolean itemHorizontalTranslationEnabled) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) getMenuView();
        if (menuView.isItemHorizontalTranslationEnabled() != itemHorizontalTranslationEnabled) {
            menuView.setItemHorizontalTranslationEnabled(itemHorizontalTranslationEnabled);
            getPresenter().updateMenuView(false);
        }
    }

    public boolean isItemHorizontalTranslationEnabled() {
        return ((BottomNavigationMenuView) getMenuView()).isItemHorizontalTranslationEnabled();
    }

    public int getMaxItemCount() {
        return 5;
    }

    /* access modifiers changed from: protected */
    public NavigationBarMenuView createNavigationBarMenuView(Context context) {
        return new BottomNavigationMenuView(context);
    }

    private boolean shouldDrawCompatibilityTopDivider() {
        return Build.VERSION.SDK_INT < 21 && !(getBackground() instanceof MaterialShapeDrawable);
    }

    private void addCompatibilityTopDivider(Context context) {
        View divider = new View(context);
        divider.setBackgroundColor(ContextCompat.getColor(context, C1087R.C1088color.design_bottom_navigation_shadow_color));
        divider.setLayoutParams(new FrameLayout.LayoutParams(-1, getResources().getDimensionPixelSize(C1087R.dimen.design_bottom_navigation_shadow_height)));
        addView(divider);
    }

    @Deprecated
    public void setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener listener) {
        setOnItemSelectedListener(listener);
    }

    @Deprecated
    public void setOnNavigationItemReselectedListener(OnNavigationItemReselectedListener listener) {
        setOnItemReselectedListener(listener);
    }
}
