package com.google.android.material.search;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.TextViewCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.C1087R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.elevation.ElevationOverlayProvider;
import com.google.android.material.internal.ClippableRoundedCornerLayout;
import com.google.android.material.internal.ContextUtils;
import com.google.android.material.internal.FadeThroughDrawable;
import com.google.android.material.internal.ToolbarUtils;
import com.google.android.material.internal.TouchObserverFrameLayout;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.shape.MaterialShapeUtils;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class SearchView extends FrameLayout implements CoordinatorLayout.AttachedBehavior {
    private static final int DEF_STYLE_RES = C1087R.C1093style.Widget_Material3_SearchView;
    private static final long TALKBACK_FOCUS_CHANGE_DELAY_MS = 100;
    private boolean animatedMenuItems;
    private boolean animatedNavigationIcon;
    private boolean autoShowKeyboard;
    final View backgroundView;
    private Map<View, Integer> childImportantForAccessibilityMap;
    final ImageButton clearButton;
    final TouchObserverFrameLayout contentContainer;
    private TransitionState currentTransitionState;
    final View divider;
    final Toolbar dummyToolbar;
    final EditText editText;
    private final ElevationOverlayProvider elevationOverlayProvider;
    final FrameLayout headerContainer;
    private final boolean layoutInflated;
    final ClippableRoundedCornerLayout rootView;
    final View scrim;
    private SearchBar searchBar;
    final TextView searchPrefix;
    private final SearchViewAnimationHelper searchViewAnimationHelper;
    private int softInputMode;
    final View statusBarSpacer;
    private boolean statusBarSpacerEnabledOverride;
    final MaterialToolbar toolbar;
    final FrameLayout toolbarContainer;
    private final Set<TransitionListener> transitionListeners;
    private boolean useWindowInsetsController;

    public interface TransitionListener {
        void onStateChanged(SearchView searchView, TransitionState transitionState, TransitionState transitionState2);
    }

    public enum TransitionState {
        HIDING,
        HIDDEN,
        SHOWING,
        SHOWN
    }

    public SearchView(Context context) {
        this(context, (AttributeSet) null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        this(context, attrs, C1087R.attr.materialSearchViewStyle);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public SearchView(android.content.Context r12, android.util.AttributeSet r13, int r14) {
        /*
            r11 = this;
            int r4 = DEF_STYLE_RES
            android.content.Context r0 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r12, r13, r14, r4)
            r11.<init>(r0, r13, r14)
            java.util.LinkedHashSet r0 = new java.util.LinkedHashSet
            r0.<init>()
            r11.transitionListeners = r0
            r0 = 16
            r11.softInputMode = r0
            com.google.android.material.search.SearchView$TransitionState r0 = com.google.android.material.search.SearchView.TransitionState.HIDDEN
            r11.currentTransitionState = r0
            android.content.Context r12 = r11.getContext()
            int[] r2 = com.google.android.material.C1087R.styleable.SearchView
            r6 = 0
            int[] r5 = new int[r6]
            r0 = r12
            r1 = r13
            r3 = r14
            android.content.res.TypedArray r0 = com.google.android.material.internal.ThemeEnforcement.obtainStyledAttributes(r0, r1, r2, r3, r4, r5)
            int r1 = com.google.android.material.C1087R.styleable.SearchView_headerLayout
            r2 = -1
            int r1 = r0.getResourceId(r1, r2)
            int r3 = com.google.android.material.C1087R.styleable.SearchView_android_textAppearance
            int r2 = r0.getResourceId(r3, r2)
            int r3 = com.google.android.material.C1087R.styleable.SearchView_android_text
            java.lang.String r3 = r0.getString(r3)
            int r4 = com.google.android.material.C1087R.styleable.SearchView_android_hint
            java.lang.String r4 = r0.getString(r4)
            int r5 = com.google.android.material.C1087R.styleable.SearchView_searchPrefixText
            java.lang.String r5 = r0.getString(r5)
            int r7 = com.google.android.material.C1087R.styleable.SearchView_useDrawerArrowDrawable
            boolean r7 = r0.getBoolean(r7, r6)
            int r8 = com.google.android.material.C1087R.styleable.SearchView_animateNavigationIcon
            r9 = 1
            boolean r8 = r0.getBoolean(r8, r9)
            r11.animatedNavigationIcon = r8
            int r8 = com.google.android.material.C1087R.styleable.SearchView_animateMenuItems
            boolean r8 = r0.getBoolean(r8, r9)
            r11.animatedMenuItems = r8
            int r8 = com.google.android.material.C1087R.styleable.SearchView_hideNavigationIcon
            boolean r6 = r0.getBoolean(r8, r6)
            int r8 = com.google.android.material.C1087R.styleable.SearchView_autoShowKeyboard
            boolean r8 = r0.getBoolean(r8, r9)
            r11.autoShowKeyboard = r8
            r0.recycle()
            android.view.LayoutInflater r8 = android.view.LayoutInflater.from(r12)
            int r10 = com.google.android.material.C1087R.C1092layout.mtrl_search_view
            r8.inflate(r10, r11)
            r11.layoutInflated = r9
            int r8 = com.google.android.material.C1087R.C1090id.search_view_scrim
            android.view.View r8 = r11.findViewById(r8)
            r11.scrim = r8
            int r8 = com.google.android.material.C1087R.C1090id.search_view_root
            android.view.View r8 = r11.findViewById(r8)
            com.google.android.material.internal.ClippableRoundedCornerLayout r8 = (com.google.android.material.internal.ClippableRoundedCornerLayout) r8
            r11.rootView = r8
            int r8 = com.google.android.material.C1087R.C1090id.search_view_background
            android.view.View r8 = r11.findViewById(r8)
            r11.backgroundView = r8
            int r8 = com.google.android.material.C1087R.C1090id.search_view_status_bar_spacer
            android.view.View r8 = r11.findViewById(r8)
            r11.statusBarSpacer = r8
            int r8 = com.google.android.material.C1087R.C1090id.search_view_header_container
            android.view.View r8 = r11.findViewById(r8)
            android.widget.FrameLayout r8 = (android.widget.FrameLayout) r8
            r11.headerContainer = r8
            int r8 = com.google.android.material.C1087R.C1090id.search_view_toolbar_container
            android.view.View r8 = r11.findViewById(r8)
            android.widget.FrameLayout r8 = (android.widget.FrameLayout) r8
            r11.toolbarContainer = r8
            int r8 = com.google.android.material.C1087R.C1090id.search_view_toolbar
            android.view.View r8 = r11.findViewById(r8)
            com.google.android.material.appbar.MaterialToolbar r8 = (com.google.android.material.appbar.MaterialToolbar) r8
            r11.toolbar = r8
            int r8 = com.google.android.material.C1087R.C1090id.search_view_dummy_toolbar
            android.view.View r8 = r11.findViewById(r8)
            androidx.appcompat.widget.Toolbar r8 = (androidx.appcompat.widget.Toolbar) r8
            r11.dummyToolbar = r8
            int r8 = com.google.android.material.C1087R.C1090id.search_view_search_prefix
            android.view.View r8 = r11.findViewById(r8)
            android.widget.TextView r8 = (android.widget.TextView) r8
            r11.searchPrefix = r8
            int r8 = com.google.android.material.C1087R.C1090id.search_view_edit_text
            android.view.View r8 = r11.findViewById(r8)
            android.widget.EditText r8 = (android.widget.EditText) r8
            r11.editText = r8
            int r8 = com.google.android.material.C1087R.C1090id.search_view_clear_button
            android.view.View r8 = r11.findViewById(r8)
            android.widget.ImageButton r8 = (android.widget.ImageButton) r8
            r11.clearButton = r8
            int r8 = com.google.android.material.C1087R.C1090id.search_view_divider
            android.view.View r8 = r11.findViewById(r8)
            r11.divider = r8
            int r8 = com.google.android.material.C1087R.C1090id.search_view_content_container
            android.view.View r8 = r11.findViewById(r8)
            com.google.android.material.internal.TouchObserverFrameLayout r8 = (com.google.android.material.internal.TouchObserverFrameLayout) r8
            r11.contentContainer = r8
            com.google.android.material.search.SearchViewAnimationHelper r8 = new com.google.android.material.search.SearchViewAnimationHelper
            r8.<init>(r11)
            r11.searchViewAnimationHelper = r8
            com.google.android.material.elevation.ElevationOverlayProvider r8 = new com.google.android.material.elevation.ElevationOverlayProvider
            r8.<init>(r12)
            r11.elevationOverlayProvider = r8
            r11.setUpRootView()
            r11.setUpBackgroundViewElevationOverlay()
            r11.setUpHeaderLayout(r1)
            r11.setSearchPrefixText(r5)
            r11.setUpEditText(r2, r3, r4)
            r11.setUpBackButton(r7, r6)
            r11.setUpClearButton()
            r11.setUpContentOnTouchListener()
            r11.setUpInsetListeners()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.search.SearchView.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (this.layoutInflated) {
            this.contentContainer.addView(child, index, params);
        } else {
            super.addView(child, index, params);
        }
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        updateSoftInputMode();
    }

    public void setElevation(float elevation) {
        super.setElevation(elevation);
        setUpBackgroundViewElevationOverlay(elevation);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this);
    }

    public CoordinatorLayout.Behavior<SearchView> getBehavior() {
        return new Behavior();
    }

    private Window getActivityWindow() {
        Activity activity = ContextUtils.getActivity(getContext());
        if (activity == null) {
            return null;
        }
        return activity.getWindow();
    }

    static /* synthetic */ boolean lambda$setUpRootView$0(View v, MotionEvent event) {
        return true;
    }

    private void setUpRootView() {
        this.rootView.setOnTouchListener(new SearchView$$ExternalSyntheticLambda1());
    }

    private void setUpBackgroundViewElevationOverlay() {
        setUpBackgroundViewElevationOverlay(getOverlayElevation());
    }

    private void setUpBackgroundViewElevationOverlay(float elevation) {
        ElevationOverlayProvider elevationOverlayProvider2 = this.elevationOverlayProvider;
        if (elevationOverlayProvider2 != null && this.backgroundView != null) {
            this.backgroundView.setBackgroundColor(elevationOverlayProvider2.compositeOverlayWithThemeSurfaceColorIfNeeded(elevation));
        }
    }

    private float getOverlayElevation() {
        SearchBar searchBar2 = this.searchBar;
        if (searchBar2 != null) {
            return searchBar2.getCompatElevation();
        }
        return getResources().getDimension(C1087R.dimen.m3_searchview_elevation);
    }

    private void setUpHeaderLayout(int headerLayoutResId) {
        if (headerLayoutResId != -1) {
            addHeaderView(LayoutInflater.from(getContext()).inflate(headerLayoutResId, this.headerContainer, false));
        }
    }

    private void setUpEditText(int textAppearanceResId, String text, String hint) {
        if (textAppearanceResId != -1) {
            TextViewCompat.setTextAppearance(this.editText, textAppearanceResId);
        }
        this.editText.setText(text);
        this.editText.setHint(hint);
    }

    private void setUpBackButton(boolean useDrawerArrowDrawable, boolean hideNavigationIcon) {
        if (hideNavigationIcon) {
            this.toolbar.setNavigationIcon((Drawable) null);
            return;
        }
        this.toolbar.setNavigationOnClickListener(new SearchView$$ExternalSyntheticLambda2(this));
        if (useDrawerArrowDrawable) {
            DrawerArrowDrawable drawerArrowDrawable = new DrawerArrowDrawable(getContext());
            drawerArrowDrawable.setColor(MaterialColors.getColor(this, C1087R.attr.colorOnSurface));
            this.toolbar.setNavigationIcon(drawerArrowDrawable);
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$setUpBackButton$1$com-google-android-material-search-SearchView */
    public /* synthetic */ void mo23479x40e9b054(View v) {
        hide();
    }

    private void setUpClearButton() {
        this.clearButton.setOnClickListener(new SearchView$$ExternalSyntheticLambda7(this));
        this.editText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SearchView.this.clearButton.setVisibility(s.length() > 0 ? 0 : 8);
            }

            public void afterTextChanged(Editable s) {
            }
        });
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$setUpClearButton$2$com-google-android-material-search-SearchView */
    public /* synthetic */ void mo23480xf4a71c3b(View v) {
        clearText();
        requestFocusAndShowKeyboardIfNeeded();
    }

    private void setUpContentOnTouchListener() {
        this.contentContainer.setOnTouchListener(new SearchView$$ExternalSyntheticLambda0(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$setUpContentOnTouchListener$3$com-google-android-material-search-SearchView */
    public /* synthetic */ boolean mo23481x1cd2d198(View v, MotionEvent event) {
        if (!isAdjustNothingSoftInputMode()) {
            return false;
        }
        clearFocusAndHideKeyboard();
        return false;
    }

    private void setUpStatusBarSpacer(int height) {
        if (this.statusBarSpacer.getLayoutParams().height != height) {
            this.statusBarSpacer.getLayoutParams().height = height;
            this.statusBarSpacer.requestLayout();
        }
    }

    private int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    private void updateNavigationIconIfNeeded() {
        MaterialToolbar materialToolbar = this.toolbar;
        if (materialToolbar != null && !isNavigationIconDrawerArrowDrawable(materialToolbar)) {
            int navigationIcon = C1087R.C1089drawable.ic_arrow_back_black_24;
            if (this.searchBar == null) {
                this.toolbar.setNavigationIcon(navigationIcon);
                return;
            }
            Drawable navigationIconDrawable = DrawableCompat.wrap(AppCompatResources.getDrawable(getContext(), navigationIcon).mutate());
            if (this.toolbar.getNavigationIconTint() != null) {
                DrawableCompat.setTint(navigationIconDrawable, this.toolbar.getNavigationIconTint().intValue());
            }
            this.toolbar.setNavigationIcon(new FadeThroughDrawable(this.searchBar.getNavigationIcon(), navigationIconDrawable));
            updateNavigationIconProgressIfNeeded();
        }
    }

    private boolean isNavigationIconDrawerArrowDrawable(Toolbar toolbar2) {
        return DrawableCompat.unwrap(toolbar2.getNavigationIcon()) instanceof DrawerArrowDrawable;
    }

    private void setUpInsetListeners() {
        setUpToolbarInsetListener();
        setUpDividerInsetListener();
        setUpStatusBarSpacerInsetListener();
    }

    private void setUpToolbarInsetListener() {
        ViewUtils.doOnApplyWindowInsets(this.toolbar, new SearchView$$ExternalSyntheticLambda8(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$setUpToolbarInsetListener$4$com-google-android-material-search-SearchView */
    public /* synthetic */ WindowInsetsCompat mo23483x7371bf54(View view, WindowInsetsCompat insets, ViewUtils.RelativePadding initialPadding) {
        boolean isRtl = ViewUtils.isLayoutRtl(this.toolbar);
        this.toolbar.setPadding(insets.getSystemWindowInsetLeft() + (isRtl ? initialPadding.end : initialPadding.start), initialPadding.top, insets.getSystemWindowInsetRight() + (isRtl ? initialPadding.start : initialPadding.end), initialPadding.bottom);
        return insets;
    }

    private void setUpStatusBarSpacerInsetListener() {
        setUpStatusBarSpacer(getStatusBarHeight());
        ViewCompat.setOnApplyWindowInsetsListener(this.statusBarSpacer, new SearchView$$ExternalSyntheticLambda4(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$setUpStatusBarSpacerInsetListener$5$com-google-android-material-search-SearchView */
    public /* synthetic */ WindowInsetsCompat mo23482x941b8403(View v, WindowInsetsCompat insets) {
        int systemWindowInsetTop = insets.getSystemWindowInsetTop();
        setUpStatusBarSpacer(systemWindowInsetTop);
        if (!this.statusBarSpacerEnabledOverride) {
            setStatusBarSpacerEnabledInternal(systemWindowInsetTop > 0);
        }
        return insets;
    }

    private void setUpDividerInsetListener() {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) this.divider.getLayoutParams();
        ViewCompat.setOnApplyWindowInsetsListener(this.divider, new SearchView$$ExternalSyntheticLambda3(layoutParams, layoutParams.leftMargin, layoutParams.rightMargin));
    }

    static /* synthetic */ WindowInsetsCompat lambda$setUpDividerInsetListener$6(ViewGroup.MarginLayoutParams layoutParams, int leftMargin, int rightMargin, View v, WindowInsetsCompat insets) {
        layoutParams.leftMargin = insets.getSystemWindowInsetLeft() + leftMargin;
        layoutParams.rightMargin = insets.getSystemWindowInsetRight() + rightMargin;
        return insets;
    }

    public boolean isSetupWithSearchBar() {
        return this.searchBar != null;
    }

    public void setupWithSearchBar(SearchBar searchBar2) {
        this.searchBar = searchBar2;
        this.searchViewAnimationHelper.setSearchBar(searchBar2);
        if (searchBar2 != null) {
            searchBar2.setOnClickListener(new SearchView$$ExternalSyntheticLambda6(this));
        }
        updateNavigationIconIfNeeded();
        setUpBackgroundViewElevationOverlay();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$setupWithSearchBar$7$com-google-android-material-search-SearchView */
    public /* synthetic */ void mo23484x986696e6(View v) {
        show();
    }

    public void addHeaderView(View headerView) {
        this.headerContainer.addView(headerView);
        this.headerContainer.setVisibility(0);
    }

    public void removeHeaderView(View headerView) {
        this.headerContainer.removeView(headerView);
        if (this.headerContainer.getChildCount() == 0) {
            this.headerContainer.setVisibility(8);
        }
    }

    public void removeAllHeaderViews() {
        this.headerContainer.removeAllViews();
        this.headerContainer.setVisibility(8);
    }

    public void setAnimatedNavigationIcon(boolean animatedNavigationIcon2) {
        this.animatedNavigationIcon = animatedNavigationIcon2;
    }

    public boolean isAnimatedNavigationIcon() {
        return this.animatedNavigationIcon;
    }

    public void setMenuItemsAnimated(boolean menuItemsAnimated) {
        this.animatedMenuItems = menuItemsAnimated;
    }

    public boolean isMenuItemsAnimated() {
        return this.animatedMenuItems;
    }

    public void setAutoShowKeyboard(boolean autoShowKeyboard2) {
        this.autoShowKeyboard = autoShowKeyboard2;
    }

    public boolean isAutoShowKeyboard() {
        return this.autoShowKeyboard;
    }

    public void setUseWindowInsetsController(boolean useWindowInsetsController2) {
        this.useWindowInsetsController = useWindowInsetsController2;
    }

    public boolean isUseWindowInsetsController() {
        return this.useWindowInsetsController;
    }

    public void addTransitionListener(TransitionListener transitionListener) {
        this.transitionListeners.add(transitionListener);
    }

    public void removeTransitionListener(TransitionListener transitionListener) {
        this.transitionListeners.remove(transitionListener);
    }

    public void inflateMenu(int menuResId) {
        this.toolbar.inflateMenu(menuResId);
    }

    public void setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener onMenuItemClickListener) {
        this.toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
    }

    public TextView getSearchPrefix() {
        return this.searchPrefix;
    }

    public void setSearchPrefixText(CharSequence searchPrefixText) {
        this.searchPrefix.setText(searchPrefixText);
        this.searchPrefix.setVisibility(TextUtils.isEmpty(searchPrefixText) ? 8 : 0);
    }

    public CharSequence getSearchPrefixText() {
        return this.searchPrefix.getText();
    }

    public Toolbar getToolbar() {
        return this.toolbar;
    }

    public EditText getEditText() {
        return this.editText;
    }

    public Editable getText() {
        return this.editText.getText();
    }

    public void setText(CharSequence text) {
        this.editText.setText(text);
    }

    public void setText(int textResId) {
        this.editText.setText(textResId);
    }

    public void clearText() {
        this.editText.setText("");
    }

    public CharSequence getHint() {
        return this.editText.getHint();
    }

    public void setHint(CharSequence hint) {
        this.editText.setHint(hint);
    }

    public void setHint(int hintResId) {
        this.editText.setHint(hintResId);
    }

    public int getSoftInputMode() {
        return this.softInputMode;
    }

    public void updateSoftInputMode() {
        Window window = getActivityWindow();
        if (window != null) {
            this.softInputMode = window.getAttributes().softInputMode;
        }
    }

    public void setStatusBarSpacerEnabled(boolean enabled) {
        this.statusBarSpacerEnabledOverride = true;
        setStatusBarSpacerEnabledInternal(enabled);
    }

    private void setStatusBarSpacerEnabledInternal(boolean enabled) {
        this.statusBarSpacer.setVisibility(enabled ? 0 : 8);
    }

    public TransitionState getCurrentTransitionState() {
        return this.currentTransitionState;
    }

    /* access modifiers changed from: package-private */
    public void setTransitionState(TransitionState state) {
        if (!this.currentTransitionState.equals(state)) {
            TransitionState previousState = this.currentTransitionState;
            this.currentTransitionState = state;
            for (TransitionListener listener : new LinkedHashSet<>(this.transitionListeners)) {
                listener.onStateChanged(this, previousState, state);
            }
        }
    }

    public boolean isShowing() {
        return this.currentTransitionState.equals(TransitionState.SHOWN) || this.currentTransitionState.equals(TransitionState.SHOWING);
    }

    public void show() {
        if (!this.currentTransitionState.equals(TransitionState.SHOWN) && !this.currentTransitionState.equals(TransitionState.SHOWING)) {
            this.searchViewAnimationHelper.show();
            setModalForAccessibility(true);
        }
    }

    public void hide() {
        if (!this.currentTransitionState.equals(TransitionState.HIDDEN) && !this.currentTransitionState.equals(TransitionState.HIDING)) {
            this.searchViewAnimationHelper.hide();
            setModalForAccessibility(false);
        }
    }

    public void setVisible(boolean visible) {
        int i = 0;
        boolean wasVisible = this.rootView.getVisibility() == 0;
        ClippableRoundedCornerLayout clippableRoundedCornerLayout = this.rootView;
        if (!visible) {
            i = 8;
        }
        clippableRoundedCornerLayout.setVisibility(i);
        updateNavigationIconProgressIfNeeded();
        if (wasVisible != visible) {
            setModalForAccessibility(visible);
        }
        setTransitionState(visible ? TransitionState.SHOWN : TransitionState.HIDDEN);
    }

    private void updateNavigationIconProgressIfNeeded() {
        ImageButton backButton = ToolbarUtils.getNavigationIconButton(this.toolbar);
        if (backButton != null) {
            int progress = this.rootView.getVisibility() == 0 ? 1 : 0;
            Drawable drawable = DrawableCompat.unwrap(backButton.getDrawable());
            if (drawable instanceof DrawerArrowDrawable) {
                ((DrawerArrowDrawable) drawable).setProgress((float) progress);
            }
            if (drawable instanceof FadeThroughDrawable) {
                ((FadeThroughDrawable) drawable).setProgress((float) progress);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void requestFocusAndShowKeyboardIfNeeded() {
        if (this.autoShowKeyboard) {
            requestFocusAndShowKeyboard();
        }
    }

    public void requestFocusAndShowKeyboard() {
        this.editText.postDelayed(new SearchView$$ExternalSyntheticLambda5(this), TALKBACK_FOCUS_CHANGE_DELAY_MS);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$requestFocusAndShowKeyboard$8$com-google-android-material-search-SearchView */
    public /* synthetic */ void mo23478x2b2700d7() {
        if (this.editText.requestFocus()) {
            this.editText.sendAccessibilityEvent(8);
        }
        ViewUtils.showKeyboard(this.editText, this.useWindowInsetsController);
    }

    public void clearFocusAndHideKeyboard() {
        this.editText.post(new SearchView$$ExternalSyntheticLambda9(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$clearFocusAndHideKeyboard$9$com-google-android-material-search-SearchView */
    public /* synthetic */ void mo23477xff5aa7db() {
        this.editText.clearFocus();
        SearchBar searchBar2 = this.searchBar;
        if (searchBar2 != null) {
            searchBar2.requestFocus();
        }
        ViewUtils.hideKeyboard(this.editText, this.useWindowInsetsController);
    }

    /* access modifiers changed from: package-private */
    public boolean isAdjustNothingSoftInputMode() {
        return this.softInputMode == 48;
    }

    public void setModalForAccessibility(boolean isSearchViewModal) {
        ViewGroup rootView2 = (ViewGroup) getRootView();
        if (isSearchViewModal) {
            this.childImportantForAccessibilityMap = new HashMap(rootView2.getChildCount());
        }
        updateChildImportantForAccessibility(rootView2, isSearchViewModal);
        if (!isSearchViewModal) {
            this.childImportantForAccessibilityMap = null;
        }
    }

    public void setToolbarTouchscreenBlocksFocus(boolean touchscreenBlocksFocus) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.toolbar.setTouchscreenBlocksFocus(touchscreenBlocksFocus);
        }
    }

    private void updateChildImportantForAccessibility(ViewGroup parent, boolean isSearchViewModal) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if (child != this) {
                if (child.findViewById(this.rootView.getId()) != null) {
                    updateChildImportantForAccessibility((ViewGroup) child, isSearchViewModal);
                } else if (!isSearchViewModal) {
                    Map<View, Integer> map = this.childImportantForAccessibilityMap;
                    if (map != null && map.containsKey(child)) {
                        ViewCompat.setImportantForAccessibility(child, this.childImportantForAccessibilityMap.get(child).intValue());
                    }
                } else {
                    this.childImportantForAccessibilityMap.put(child, Integer.valueOf(child.getImportantForAccessibility()));
                    ViewCompat.setImportantForAccessibility(child, 4);
                }
            }
        }
    }

    public static class Behavior extends CoordinatorLayout.Behavior<SearchView> {
        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public boolean onDependentViewChanged(CoordinatorLayout parent, SearchView child, View dependency) {
            if (child.isSetupWithSearchBar() || !(dependency instanceof SearchBar)) {
                return false;
            }
            child.setupWithSearchBar((SearchBar) dependency);
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        CharSequence text = getText();
        savedState.text = text == null ? null : text.toString();
        savedState.visibility = this.rootView.getVisibility();
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
        setVisible(savedState.visibility == 0);
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
        int visibility;

        public SavedState(Parcel source) {
            this(source, (ClassLoader) null);
        }

        public SavedState(Parcel source, ClassLoader classLoader) {
            super(source, classLoader);
            this.text = source.readString();
            this.visibility = source.readInt();
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeString(this.text);
            dest.writeInt(this.visibility);
        }
    }
}
