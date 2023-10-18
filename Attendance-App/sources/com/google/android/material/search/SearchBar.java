package com.google.android.material.search;

import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityManagerCompat;
import androidx.core.widget.TextViewCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.C1087R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ToolbarUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;

public class SearchBar extends Toolbar {
    private static final int DEFAULT_SCROLL_FLAGS = 53;
    private static final int DEF_STYLE_RES = C1087R.C1093style.Widget_Material3_SearchBar;
    private static final String NAMESPACE_APP = "http://schemas.android.com/apk/res-auto";
    /* access modifiers changed from: private */
    public final AccessibilityManager accessibilityManager;
    private MaterialShapeDrawable backgroundShape;
    private View centerView;
    private final boolean defaultMarginsEnabled;
    private final Drawable defaultNavigationIcon;
    private boolean defaultScrollFlagsEnabled;
    private final boolean forceDefaultNavigationOnClickListener;
    private final boolean layoutInflated;
    private int menuResId;
    private Integer navigationIconTint;
    private Drawable originalNavigationIconBackground;
    private final SearchBarAnimationHelper searchBarAnimationHelper;
    private final TextView textView;
    private final boolean tintNavigationIcon;
    /* access modifiers changed from: private */
    public final AccessibilityManagerCompat.TouchExplorationStateChangeListener touchExplorationStateChangeListener;

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$com-google-android-material-search-SearchBar  reason: not valid java name */
    public /* synthetic */ void m1322lambda$new$0$comgoogleandroidmaterialsearchSearchBar(boolean enabled) {
        setFocusableInTouchMode(enabled);
    }

    public SearchBar(Context context) {
        this(context, (AttributeSet) null);
    }

    public SearchBar(Context context, AttributeSet attrs) {
        this(context, attrs, C1087R.attr.materialSearchBarStyle);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public SearchBar(android.content.Context r13, android.util.AttributeSet r14, int r15) {
        /*
            r12 = this;
            int r6 = DEF_STYLE_RES
            android.content.Context r0 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r13, r14, r15, r6)
            r12.<init>(r0, r14, r15)
            r7 = -1
            r12.menuResId = r7
            com.google.android.material.search.SearchBar$$ExternalSyntheticLambda0 r0 = new com.google.android.material.search.SearchBar$$ExternalSyntheticLambda0
            r0.<init>(r12)
            r12.touchExplorationStateChangeListener = r0
            android.content.Context r13 = r12.getContext()
            r12.validateAttributes(r14)
            int r0 = com.google.android.material.C1087R.C1089drawable.ic_search_black_24
            android.graphics.drawable.Drawable r0 = androidx.appcompat.content.res.AppCompatResources.getDrawable(r13, r0)
            r12.defaultNavigationIcon = r0
            com.google.android.material.search.SearchBarAnimationHelper r0 = new com.google.android.material.search.SearchBarAnimationHelper
            r0.<init>()
            r12.searchBarAnimationHelper = r0
            int[] r2 = com.google.android.material.C1087R.styleable.SearchBar
            r8 = 0
            int[] r5 = new int[r8]
            r0 = r13
            r1 = r14
            r3 = r15
            r4 = r6
            android.content.res.TypedArray r0 = com.google.android.material.internal.ThemeEnforcement.obtainStyledAttributes(r0, r1, r2, r3, r4, r5)
            com.google.android.material.shape.ShapeAppearanceModel$Builder r1 = com.google.android.material.shape.ShapeAppearanceModel.builder((android.content.Context) r13, (android.util.AttributeSet) r14, (int) r15, (int) r6)
            com.google.android.material.shape.ShapeAppearanceModel r1 = r1.build()
            int r2 = com.google.android.material.C1087R.styleable.SearchBar_elevation
            r3 = 0
            float r2 = r0.getDimension(r2, r3)
            int r3 = com.google.android.material.C1087R.styleable.SearchBar_defaultMarginsEnabled
            r4 = 1
            boolean r3 = r0.getBoolean(r3, r4)
            r12.defaultMarginsEnabled = r3
            int r3 = com.google.android.material.C1087R.styleable.SearchBar_defaultScrollFlagsEnabled
            boolean r3 = r0.getBoolean(r3, r4)
            r12.defaultScrollFlagsEnabled = r3
            int r3 = com.google.android.material.C1087R.styleable.SearchBar_hideNavigationIcon
            boolean r3 = r0.getBoolean(r3, r8)
            int r5 = com.google.android.material.C1087R.styleable.SearchBar_forceDefaultNavigationOnClickListener
            boolean r5 = r0.getBoolean(r5, r8)
            r12.forceDefaultNavigationOnClickListener = r5
            int r5 = com.google.android.material.C1087R.styleable.SearchBar_tintNavigationIcon
            boolean r5 = r0.getBoolean(r5, r4)
            r12.tintNavigationIcon = r5
            int r5 = com.google.android.material.C1087R.styleable.SearchBar_navigationIconTint
            boolean r5 = r0.hasValue(r5)
            if (r5 == 0) goto L_0x0081
            int r5 = com.google.android.material.C1087R.styleable.SearchBar_navigationIconTint
            int r5 = r0.getColor(r5, r7)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r12.navigationIconTint = r5
        L_0x0081:
            int r5 = com.google.android.material.C1087R.styleable.SearchBar_android_textAppearance
            int r5 = r0.getResourceId(r5, r7)
            int r6 = com.google.android.material.C1087R.styleable.SearchBar_android_text
            java.lang.String r6 = r0.getString(r6)
            int r7 = com.google.android.material.C1087R.styleable.SearchBar_android_hint
            java.lang.String r7 = r0.getString(r7)
            int r9 = com.google.android.material.C1087R.styleable.SearchBar_strokeWidth
            r10 = -1082130432(0xffffffffbf800000, float:-1.0)
            float r9 = r0.getDimension(r9, r10)
            int r10 = com.google.android.material.C1087R.styleable.SearchBar_strokeColor
            int r8 = r0.getColor(r10, r8)
            r0.recycle()
            if (r3 != 0) goto L_0x00a9
            r12.initNavigationIcon()
        L_0x00a9:
            r12.setClickable(r4)
            r12.setFocusable(r4)
            android.view.LayoutInflater r10 = android.view.LayoutInflater.from(r13)
            int r11 = com.google.android.material.C1087R.C1092layout.mtrl_search_bar
            r10.inflate(r11, r12)
            r12.layoutInflated = r4
            int r4 = com.google.android.material.C1087R.C1090id.search_bar_text_view
            android.view.View r4 = r12.findViewById(r4)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r12.textView = r4
            androidx.core.view.ViewCompat.setElevation(r12, r2)
            r12.initTextView(r5, r6, r7)
            r12.initBackground(r1, r2, r9, r8)
            android.content.Context r4 = r12.getContext()
            java.lang.String r10 = "accessibility"
            java.lang.Object r4 = r4.getSystemService(r10)
            android.view.accessibility.AccessibilityManager r4 = (android.view.accessibility.AccessibilityManager) r4
            r12.accessibilityManager = r4
            r12.setupTouchExplorationStateChangeListener()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.search.SearchBar.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    private void setupTouchExplorationStateChangeListener() {
        AccessibilityManager accessibilityManager2 = this.accessibilityManager;
        if (accessibilityManager2 != null) {
            if (accessibilityManager2.isEnabled() && this.accessibilityManager.isTouchExplorationEnabled()) {
                setFocusableInTouchMode(true);
            }
            addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                public void onViewAttachedToWindow(View ignored) {
                    AccessibilityManagerCompat.addTouchExplorationStateChangeListener(SearchBar.this.accessibilityManager, SearchBar.this.touchExplorationStateChangeListener);
                }

                public void onViewDetachedFromWindow(View ignored) {
                    AccessibilityManagerCompat.removeTouchExplorationStateChangeListener(SearchBar.this.accessibilityManager, SearchBar.this.touchExplorationStateChangeListener);
                }
            });
        }
    }

    private void validateAttributes(AttributeSet attributeSet) {
        if (attributeSet != null) {
            if (attributeSet.getAttributeValue(NAMESPACE_APP, "title") != null) {
                throw new UnsupportedOperationException("SearchBar does not support title. Use hint or text instead.");
            } else if (attributeSet.getAttributeValue(NAMESPACE_APP, "subtitle") != null) {
                throw new UnsupportedOperationException("SearchBar does not support subtitle. Use hint or text instead.");
            }
        }
    }

    private void initNavigationIcon() {
        setNavigationIcon(getNavigationIcon() == null ? this.defaultNavigationIcon : getNavigationIcon());
        setNavigationIconDecorative(true);
    }

    private void initTextView(int textAppearanceResId, String text, String hint) {
        if (textAppearanceResId != -1) {
            TextViewCompat.setTextAppearance(this.textView, textAppearanceResId);
        }
        setText((CharSequence) text);
        setHint((CharSequence) hint);
        if (getNavigationIcon() == null) {
            MarginLayoutParamsCompat.setMarginStart((ViewGroup.MarginLayoutParams) this.textView.getLayoutParams(), getResources().getDimensionPixelSize(C1087R.dimen.m3_searchbar_text_margin_start_no_navigation_icon));
        }
    }

    private void initBackground(ShapeAppearanceModel shapeAppearance, float elevation, float strokeWidth, int strokeColor) {
        Drawable background;
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(shapeAppearance);
        this.backgroundShape = materialShapeDrawable;
        materialShapeDrawable.initializeElevationOverlay(getContext());
        this.backgroundShape.setElevation(elevation);
        if (strokeWidth >= 0.0f) {
            this.backgroundShape.setStroke(strokeWidth, strokeColor);
        }
        int backgroundColor = MaterialColors.getColor(this, C1087R.attr.colorSurface);
        int rippleColor = MaterialColors.getColor(this, C1087R.attr.colorControlHighlight);
        if (Build.VERSION.SDK_INT >= 21) {
            this.backgroundShape.setFillColor(ColorStateList.valueOf(backgroundColor));
            ColorStateList valueOf = ColorStateList.valueOf(rippleColor);
            MaterialShapeDrawable materialShapeDrawable2 = this.backgroundShape;
            background = new RippleDrawable(valueOf, materialShapeDrawable2, materialShapeDrawable2);
        } else {
            this.backgroundShape.setFillColor(getCompatBackgroundColorStateList(backgroundColor, rippleColor));
            background = this.backgroundShape;
        }
        ViewCompat.setBackground(this, background);
    }

    private ColorStateList getCompatBackgroundColorStateList(int backgroundColor, int rippleColor) {
        int[][] states = {new int[]{16842919}, new int[]{16842908}, new int[0]};
        int pressedBackgroundColor = MaterialColors.layer(backgroundColor, rippleColor);
        return new ColorStateList(states, new int[]{pressedBackgroundColor, pressedBackgroundColor, backgroundColor});
    }

    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (this.layoutInflated && this.centerView == null && !(child instanceof ActionMenuView)) {
            this.centerView = child;
            child.setAlpha(0.0f);
        }
        super.addView(child, index, params);
    }

    public void setElevation(float elevation) {
        super.setElevation(elevation);
        MaterialShapeDrawable materialShapeDrawable = this.backgroundShape;
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setElevation(elevation);
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(EditText.class.getCanonicalName());
        CharSequence text = getText();
        boolean isTextEmpty = TextUtils.isEmpty(text);
        if (Build.VERSION.SDK_INT >= 26) {
            info.setHintText(getHint());
            info.setShowingHintText(isTextEmpty);
        }
        if (isTextEmpty) {
            text = getHint();
        }
        info.setText(text);
    }

    public void setNavigationOnClickListener(View.OnClickListener listener) {
        if (!this.forceDefaultNavigationOnClickListener) {
            super.setNavigationOnClickListener(listener);
            setNavigationIconDecorative(listener == null);
        }
    }

    public void setNavigationIcon(Drawable navigationIcon) {
        super.setNavigationIcon(maybeTintNavigationIcon(navigationIcon));
    }

    private Drawable maybeTintNavigationIcon(Drawable navigationIcon) {
        int navigationIconColorAttr;
        int navigationIconColorAttr2;
        if (!this.tintNavigationIcon || navigationIcon == null) {
            return navigationIcon;
        }
        Integer num = this.navigationIconTint;
        if (num != null) {
            navigationIconColorAttr = num.intValue();
        } else {
            if (navigationIcon == this.defaultNavigationIcon) {
                navigationIconColorAttr2 = C1087R.attr.colorOnSurfaceVariant;
            } else {
                navigationIconColorAttr2 = C1087R.attr.colorOnSurface;
            }
            navigationIconColorAttr = MaterialColors.getColor(this, navigationIconColorAttr2);
        }
        Drawable wrappedNavigationIcon = DrawableCompat.wrap(navigationIcon.mutate());
        DrawableCompat.setTint(wrappedNavigationIcon, navigationIconColorAttr);
        return wrappedNavigationIcon;
    }

    private void setNavigationIconDecorative(boolean decorative) {
        ImageButton navigationIconButton = ToolbarUtils.getNavigationIconButton(this);
        if (navigationIconButton != null) {
            navigationIconButton.setClickable(!decorative);
            navigationIconButton.setFocusable(!decorative);
            Drawable navigationIconBackground = navigationIconButton.getBackground();
            if (navigationIconBackground != null) {
                this.originalNavigationIconBackground = navigationIconBackground;
            }
            navigationIconButton.setBackgroundDrawable(decorative ? null : this.originalNavigationIconBackground);
        }
    }

    public void inflateMenu(int resId) {
        super.inflateMenu(resId);
        this.menuResId = resId;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureCenterView(widthMeasureSpec, heightMeasureSpec);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        layoutCenterView();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this, this.backgroundShape);
        setDefaultMargins();
        setOrClearDefaultScrollFlags();
    }

    public void setTitle(CharSequence title) {
    }

    public void setSubtitle(CharSequence subtitle) {
    }

    private void setDefaultMargins() {
        if (this.defaultMarginsEnabled && (getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
            Resources resources = getResources();
            int marginHorizontal = resources.getDimensionPixelSize(C1087R.dimen.m3_searchbar_margin_horizontal);
            int marginVertical = resources.getDimensionPixelSize(C1087R.dimen.m3_searchbar_margin_vertical);
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) getLayoutParams();
            lp.leftMargin = defaultIfZero(lp.leftMargin, marginHorizontal);
            lp.topMargin = defaultIfZero(lp.topMargin, marginVertical);
            lp.rightMargin = defaultIfZero(lp.rightMargin, marginHorizontal);
            lp.bottomMargin = defaultIfZero(lp.bottomMargin, marginVertical);
        }
    }

    private int defaultIfZero(int value, int defValue) {
        return value == 0 ? defValue : value;
    }

    private void setOrClearDefaultScrollFlags() {
        if (getLayoutParams() instanceof AppBarLayout.LayoutParams) {
            AppBarLayout.LayoutParams lp = (AppBarLayout.LayoutParams) getLayoutParams();
            if (this.defaultScrollFlagsEnabled) {
                if (lp.getScrollFlags() == 0) {
                    lp.setScrollFlags(53);
                }
            } else if (lp.getScrollFlags() == 53) {
                lp.setScrollFlags(0);
            }
        }
    }

    private void measureCenterView(int widthMeasureSpec, int heightMeasureSpec) {
        View view = this.centerView;
        if (view != null) {
            view.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void layoutCenterView() {
        View view = this.centerView;
        if (view != null) {
            int centerViewWidth = view.getMeasuredWidth();
            int left = (getMeasuredWidth() / 2) - (centerViewWidth / 2);
            int centerViewHeight = this.centerView.getMeasuredHeight();
            int top = (getMeasuredHeight() / 2) - (centerViewHeight / 2);
            layoutChild(this.centerView, left, top, left + centerViewWidth, top + centerViewHeight);
        }
    }

    private void layoutChild(View child, int left, int top, int right, int bottom) {
        if (ViewCompat.getLayoutDirection(this) == 1) {
            child.layout(getMeasuredWidth() - right, top, getMeasuredWidth() - left, bottom);
        } else {
            child.layout(left, top, right, bottom);
        }
    }

    public View getCenterView() {
        return this.centerView;
    }

    public void setCenterView(View view) {
        View view2 = this.centerView;
        if (view2 != null) {
            removeView(view2);
            this.centerView = null;
        }
        if (view != null) {
            addView(view);
        }
    }

    public TextView getTextView() {
        return this.textView;
    }

    public CharSequence getText() {
        return this.textView.getText();
    }

    public void setText(CharSequence text) {
        this.textView.setText(text);
    }

    public void setText(int textResId) {
        this.textView.setText(textResId);
    }

    public void clearText() {
        this.textView.setText("");
    }

    public CharSequence getHint() {
        return this.textView.getHint();
    }

    public void setHint(CharSequence hint) {
        this.textView.setHint(hint);
    }

    public void setHint(int hintResId) {
        this.textView.setHint(hintResId);
    }

    public int getStrokeColor() {
        return this.backgroundShape.getStrokeColor().getDefaultColor();
    }

    public void setStrokeColor(int strokeColor) {
        if (getStrokeColor() != strokeColor) {
            this.backgroundShape.setStrokeColor(ColorStateList.valueOf(strokeColor));
        }
    }

    public float getStrokeWidth() {
        return this.backgroundShape.getStrokeWidth();
    }

    public void setStrokeWidth(float strokeWidth) {
        if (getStrokeWidth() != strokeWidth) {
            this.backgroundShape.setStrokeWidth(strokeWidth);
        }
    }

    public float getCornerSize() {
        return this.backgroundShape.getTopLeftCornerResolvedSize();
    }

    public boolean isDefaultScrollFlagsEnabled() {
        return this.defaultScrollFlagsEnabled;
    }

    public void setDefaultScrollFlagsEnabled(boolean defaultScrollFlagsEnabled2) {
        this.defaultScrollFlagsEnabled = defaultScrollFlagsEnabled2;
        setOrClearDefaultScrollFlags();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$startOnLoadAnimation$1$com-google-android-material-search-SearchBar */
    public /* synthetic */ void mo23403x58362b63() {
        this.searchBarAnimationHelper.startOnLoadAnimation(this);
    }

    public void startOnLoadAnimation() {
        post(new SearchBar$$ExternalSyntheticLambda1(this));
    }

    public void stopOnLoadAnimation() {
        this.searchBarAnimationHelper.stopOnLoadAnimation(this);
    }

    public boolean isOnLoadAnimationFadeInEnabled() {
        return this.searchBarAnimationHelper.isOnLoadAnimationFadeInEnabled();
    }

    public void setOnLoadAnimationFadeInEnabled(boolean onLoadAnimationFadeInEnabled) {
        this.searchBarAnimationHelper.setOnLoadAnimationFadeInEnabled(onLoadAnimationFadeInEnabled);
    }

    public void addOnLoadAnimationCallback(OnLoadAnimationCallback onLoadAnimationCallback) {
        this.searchBarAnimationHelper.addOnLoadAnimationCallback(onLoadAnimationCallback);
    }

    public boolean removeOnLoadAnimationCallback(OnLoadAnimationCallback onLoadAnimationCallback) {
        return this.searchBarAnimationHelper.removeOnLoadAnimationCallback(onLoadAnimationCallback);
    }

    public boolean isExpanding() {
        return this.searchBarAnimationHelper.isExpanding();
    }

    public boolean expand(View expandedView) {
        return expand(expandedView, (AppBarLayout) null);
    }

    public boolean expand(View expandedView, AppBarLayout appBarLayout) {
        return expand(expandedView, appBarLayout, false);
    }

    public boolean expand(View expandedView, AppBarLayout appBarLayout, boolean skipAnimation) {
        if ((expandedView.getVisibility() == 0 || isExpanding()) && !isCollapsing()) {
            return false;
        }
        this.searchBarAnimationHelper.startExpandAnimation(this, expandedView, appBarLayout, skipAnimation);
        return true;
    }

    public void addExpandAnimationListener(AnimatorListenerAdapter listener) {
        this.searchBarAnimationHelper.addExpandAnimationListener(listener);
    }

    public boolean removeExpandAnimationListener(AnimatorListenerAdapter listener) {
        return this.searchBarAnimationHelper.removeExpandAnimationListener(listener);
    }

    public boolean isCollapsing() {
        return this.searchBarAnimationHelper.isCollapsing();
    }

    public boolean collapse(View expandedView) {
        return collapse(expandedView, (AppBarLayout) null);
    }

    public boolean collapse(View expandedView, AppBarLayout appBarLayout) {
        return collapse(expandedView, appBarLayout, false);
    }

    public boolean collapse(View expandedView, AppBarLayout appBarLayout, boolean skipAnimation) {
        if ((expandedView.getVisibility() != 0 || isCollapsing()) && !isExpanding()) {
            return false;
        }
        this.searchBarAnimationHelper.startCollapseAnimation(this, expandedView, appBarLayout, skipAnimation);
        return true;
    }

    public void addCollapseAnimationListener(AnimatorListenerAdapter listener) {
        this.searchBarAnimationHelper.addCollapseAnimationListener(listener);
    }

    public boolean removeCollapseAnimationListener(AnimatorListenerAdapter listener) {
        return this.searchBarAnimationHelper.removeCollapseAnimationListener(listener);
    }

    /* access modifiers changed from: package-private */
    public int getMenuResId() {
        return this.menuResId;
    }

    /* access modifiers changed from: package-private */
    public float getCompatElevation() {
        MaterialShapeDrawable materialShapeDrawable = this.backgroundShape;
        return materialShapeDrawable != null ? materialShapeDrawable.getElevation() : ViewCompat.getElevation(this);
    }

    public static class ScrollingViewBehavior extends AppBarLayout.ScrollingViewBehavior {
        private boolean initialized = false;

        public ScrollingViewBehavior() {
        }

        public ScrollingViewBehavior(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
            boolean changed = super.onDependentViewChanged(parent, child, dependency);
            if (!this.initialized && (dependency instanceof AppBarLayout)) {
                this.initialized = true;
                setAppBarLayoutTransparent((AppBarLayout) dependency);
            }
            return changed;
        }

        private void setAppBarLayoutTransparent(AppBarLayout appBarLayout) {
            appBarLayout.setBackgroundColor(0);
            if (Build.VERSION.SDK_INT == 21) {
                appBarLayout.setOutlineProvider((ViewOutlineProvider) null);
            } else {
                appBarLayout.setTargetElevation(0.0f);
            }
        }

        /* access modifiers changed from: protected */
        public boolean shouldHeaderOverlapScrollingChild() {
            return true;
        }
    }

    public static abstract class OnLoadAnimationCallback {
        public void onAnimationStart() {
        }

        public void onAnimationEnd() {
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        CharSequence text = getText();
        savedState.text = text == null ? null : text.toString();
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        setText((CharSequence) savedState.text);
    }

    static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() {
            public SavedState createFromParcel(Parcel source, ClassLoader loader) {
                return new SavedState(source, loader);
            }

            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        String text;

        public SavedState(Parcel source) {
            this(source, (ClassLoader) null);
        }

        public SavedState(Parcel source, ClassLoader classLoader) {
            super(source, classLoader);
            this.text = source.readString();
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeString(this.text);
        }
    }
}
