package androidx.appcompat.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.SpinnerAdapter;
import androidx.appcompat.C0503R;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.ActionBarPolicy;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.ViewPropertyAnimatorCompatSet;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.appcompat.widget.ActionBarContainer;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.appcompat.widget.DecorToolbar;
import androidx.appcompat.widget.ScrollingTabContainerView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.core.view.ViewPropertyAnimatorUpdateListener;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class WindowDecorActionBar extends ActionBar implements ActionBarOverlayLayout.ActionBarVisibilityCallback {
    private static final long FADE_IN_DURATION_MS = 200;
    private static final long FADE_OUT_DURATION_MS = 100;
    private static final int INVALID_POSITION = -1;
    private static final String TAG = "WindowDecorActionBar";
    private static final Interpolator sHideInterpolator = new AccelerateInterpolator();
    private static final Interpolator sShowInterpolator = new DecelerateInterpolator();
    ActionModeImpl mActionMode;
    private Activity mActivity;
    ActionBarContainer mContainerView;
    boolean mContentAnimations = true;
    View mContentView;
    Context mContext;
    ActionBarContextView mContextView;
    private int mCurWindowVisibility = 0;
    ViewPropertyAnimatorCompatSet mCurrentShowAnim;
    DecorToolbar mDecorToolbar;
    ActionMode mDeferredDestroyActionMode;
    ActionMode.Callback mDeferredModeDestroyCallback;
    private boolean mDisplayHomeAsUpSet;
    private boolean mHasEmbeddedTabs;
    boolean mHiddenByApp;
    boolean mHiddenBySystem;
    final ViewPropertyAnimatorListener mHideListener = new ViewPropertyAnimatorListenerAdapter() {
        public void onAnimationEnd(View view) {
            if (WindowDecorActionBar.this.mContentAnimations && WindowDecorActionBar.this.mContentView != null) {
                WindowDecorActionBar.this.mContentView.setTranslationY(0.0f);
                WindowDecorActionBar.this.mContainerView.setTranslationY(0.0f);
            }
            WindowDecorActionBar.this.mContainerView.setVisibility(8);
            WindowDecorActionBar.this.mContainerView.setTransitioning(false);
            WindowDecorActionBar.this.mCurrentShowAnim = null;
            WindowDecorActionBar.this.completeDeferredDestroyActionMode();
            if (WindowDecorActionBar.this.mOverlayLayout != null) {
                ViewCompat.requestApplyInsets(WindowDecorActionBar.this.mOverlayLayout);
            }
        }
    };
    boolean mHideOnContentScroll;
    private boolean mLastMenuVisibility;
    private ArrayList<ActionBar.OnMenuVisibilityListener> mMenuVisibilityListeners = new ArrayList<>();
    private boolean mNowShowing = true;
    ActionBarOverlayLayout mOverlayLayout;
    private int mSavedTabPosition = -1;
    private TabImpl mSelectedTab;
    private boolean mShowHideAnimationEnabled;
    final ViewPropertyAnimatorListener mShowListener = new ViewPropertyAnimatorListenerAdapter() {
        public void onAnimationEnd(View view) {
            WindowDecorActionBar.this.mCurrentShowAnim = null;
            WindowDecorActionBar.this.mContainerView.requestLayout();
        }
    };
    private boolean mShowingForMode;
    ScrollingTabContainerView mTabScrollView;
    private ArrayList<TabImpl> mTabs = new ArrayList<>();
    private Context mThemedContext;
    final ViewPropertyAnimatorUpdateListener mUpdateListener = new ViewPropertyAnimatorUpdateListener() {
        public void onAnimationUpdate(View view) {
            ((View) WindowDecorActionBar.this.mContainerView.getParent()).invalidate();
        }
    };

    public WindowDecorActionBar(Activity activity, boolean overlayMode) {
        this.mActivity = activity;
        View decor = activity.getWindow().getDecorView();
        init(decor);
        if (!overlayMode) {
            this.mContentView = decor.findViewById(16908290);
        }
    }

    public WindowDecorActionBar(Dialog dialog) {
        init(dialog.getWindow().getDecorView());
    }

    public WindowDecorActionBar(View layout) {
        init(layout);
    }

    private void init(View decor) {
        ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) decor.findViewById(C0503R.C0506id.decor_content_parent);
        this.mOverlayLayout = actionBarOverlayLayout;
        if (actionBarOverlayLayout != null) {
            actionBarOverlayLayout.setActionBarVisibilityCallback(this);
        }
        this.mDecorToolbar = getDecorToolbar(decor.findViewById(C0503R.C0506id.action_bar));
        this.mContextView = (ActionBarContextView) decor.findViewById(C0503R.C0506id.action_context_bar);
        ActionBarContainer actionBarContainer = (ActionBarContainer) decor.findViewById(C0503R.C0506id.action_bar_container);
        this.mContainerView = actionBarContainer;
        DecorToolbar decorToolbar = this.mDecorToolbar;
        if (decorToolbar == null || this.mContextView == null || actionBarContainer == null) {
            throw new IllegalStateException(getClass().getSimpleName() + " can only be used with a compatible window decor layout");
        }
        this.mContext = decorToolbar.getContext();
        boolean homeAsUp = (this.mDecorToolbar.getDisplayOptions() & 4) != 0;
        if (homeAsUp) {
            this.mDisplayHomeAsUpSet = true;
        }
        ActionBarPolicy abp = ActionBarPolicy.get(this.mContext);
        setHomeButtonEnabled(abp.enableHomeButtonByDefault() || homeAsUp);
        setHasEmbeddedTabs(abp.hasEmbeddedTabs());
        TypedArray a = this.mContext.obtainStyledAttributes((AttributeSet) null, C0503R.styleable.ActionBar, C0503R.attr.actionBarStyle, 0);
        if (a.getBoolean(C0503R.styleable.ActionBar_hideOnContentScroll, false)) {
            setHideOnContentScrollEnabled(true);
        }
        int elevation = a.getDimensionPixelSize(C0503R.styleable.ActionBar_elevation, 0);
        if (elevation != 0) {
            setElevation((float) elevation);
        }
        a.recycle();
    }

    private DecorToolbar getDecorToolbar(View view) {
        if (view instanceof DecorToolbar) {
            return (DecorToolbar) view;
        }
        if (view instanceof Toolbar) {
            return ((Toolbar) view).getWrapper();
        }
        throw new IllegalStateException("Can't make a decor toolbar out of " + (view != null ? view.getClass().getSimpleName() : "null"));
    }

    public void setElevation(float elevation) {
        ViewCompat.setElevation(this.mContainerView, elevation);
    }

    public float getElevation() {
        return ViewCompat.getElevation(this.mContainerView);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        setHasEmbeddedTabs(ActionBarPolicy.get(this.mContext).hasEmbeddedTabs());
    }

    private void setHasEmbeddedTabs(boolean hasEmbeddedTabs) {
        this.mHasEmbeddedTabs = hasEmbeddedTabs;
        if (!hasEmbeddedTabs) {
            this.mDecorToolbar.setEmbeddedTabView((ScrollingTabContainerView) null);
            this.mContainerView.setTabContainer(this.mTabScrollView);
        } else {
            this.mContainerView.setTabContainer((ScrollingTabContainerView) null);
            this.mDecorToolbar.setEmbeddedTabView(this.mTabScrollView);
        }
        boolean z = true;
        boolean isInTabMode = getNavigationMode() == 2;
        ScrollingTabContainerView scrollingTabContainerView = this.mTabScrollView;
        if (scrollingTabContainerView != null) {
            if (isInTabMode) {
                scrollingTabContainerView.setVisibility(0);
                ActionBarOverlayLayout actionBarOverlayLayout = this.mOverlayLayout;
                if (actionBarOverlayLayout != null) {
                    ViewCompat.requestApplyInsets(actionBarOverlayLayout);
                }
            } else {
                scrollingTabContainerView.setVisibility(8);
            }
        }
        this.mDecorToolbar.setCollapsible(!this.mHasEmbeddedTabs && isInTabMode);
        ActionBarOverlayLayout actionBarOverlayLayout2 = this.mOverlayLayout;
        if (this.mHasEmbeddedTabs || !isInTabMode) {
            z = false;
        }
        actionBarOverlayLayout2.setHasNonEmbeddedTabs(z);
    }

    private void ensureTabsExist() {
        if (this.mTabScrollView == null) {
            ScrollingTabContainerView tabScroller = new ScrollingTabContainerView(this.mContext);
            if (this.mHasEmbeddedTabs) {
                tabScroller.setVisibility(0);
                this.mDecorToolbar.setEmbeddedTabView(tabScroller);
            } else {
                if (getNavigationMode() == 2) {
                    tabScroller.setVisibility(0);
                    ActionBarOverlayLayout actionBarOverlayLayout = this.mOverlayLayout;
                    if (actionBarOverlayLayout != null) {
                        ViewCompat.requestApplyInsets(actionBarOverlayLayout);
                    }
                } else {
                    tabScroller.setVisibility(8);
                }
                this.mContainerView.setTabContainer(tabScroller);
            }
            this.mTabScrollView = tabScroller;
        }
    }

    /* access modifiers changed from: package-private */
    public void completeDeferredDestroyActionMode() {
        ActionMode.Callback callback = this.mDeferredModeDestroyCallback;
        if (callback != null) {
            callback.onDestroyActionMode(this.mDeferredDestroyActionMode);
            this.mDeferredDestroyActionMode = null;
            this.mDeferredModeDestroyCallback = null;
        }
    }

    public void onWindowVisibilityChanged(int visibility) {
        this.mCurWindowVisibility = visibility;
    }

    public void setShowHideAnimationEnabled(boolean enabled) {
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet;
        this.mShowHideAnimationEnabled = enabled;
        if (!enabled && (viewPropertyAnimatorCompatSet = this.mCurrentShowAnim) != null) {
            viewPropertyAnimatorCompatSet.cancel();
        }
    }

    public void addOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener listener) {
        this.mMenuVisibilityListeners.add(listener);
    }

    public void removeOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener listener) {
        this.mMenuVisibilityListeners.remove(listener);
    }

    public void dispatchMenuVisibilityChanged(boolean isVisible) {
        if (isVisible != this.mLastMenuVisibility) {
            this.mLastMenuVisibility = isVisible;
            int count = this.mMenuVisibilityListeners.size();
            for (int i = 0; i < count; i++) {
                this.mMenuVisibilityListeners.get(i).onMenuVisibilityChanged(isVisible);
            }
        }
    }

    public void setCustomView(int resId) {
        setCustomView(LayoutInflater.from(getThemedContext()).inflate(resId, this.mDecorToolbar.getViewGroup(), false));
    }

    public void setDisplayUseLogoEnabled(boolean useLogo) {
        setDisplayOptions(useLogo, 1);
    }

    public void setDisplayShowHomeEnabled(boolean showHome) {
        setDisplayOptions(showHome ? 2 : 0, 2);
    }

    public void setDisplayHomeAsUpEnabled(boolean showHomeAsUp) {
        setDisplayOptions(showHomeAsUp ? 4 : 0, 4);
    }

    public void setDisplayShowTitleEnabled(boolean showTitle) {
        setDisplayOptions(showTitle ? 8 : 0, 8);
    }

    public void setDisplayShowCustomEnabled(boolean showCustom) {
        setDisplayOptions(showCustom ? 16 : 0, 16);
    }

    public void setHomeButtonEnabled(boolean enable) {
        this.mDecorToolbar.setHomeButtonEnabled(enable);
    }

    public void setTitle(int resId) {
        setTitle((CharSequence) this.mContext.getString(resId));
    }

    public void setSubtitle(int resId) {
        setSubtitle((CharSequence) this.mContext.getString(resId));
    }

    public void setSelectedNavigationItem(int position) {
        switch (this.mDecorToolbar.getNavigationMode()) {
            case 1:
                this.mDecorToolbar.setDropdownSelectedPosition(position);
                return;
            case 2:
                selectTab(this.mTabs.get(position));
                return;
            default:
                throw new IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode");
        }
    }

    public void removeAllTabs() {
        cleanupTabs();
    }

    private void cleanupTabs() {
        if (this.mSelectedTab != null) {
            selectTab((ActionBar.Tab) null);
        }
        this.mTabs.clear();
        ScrollingTabContainerView scrollingTabContainerView = this.mTabScrollView;
        if (scrollingTabContainerView != null) {
            scrollingTabContainerView.removeAllTabs();
        }
        this.mSavedTabPosition = -1;
    }

    public void setTitle(CharSequence title) {
        this.mDecorToolbar.setTitle(title);
    }

    public void setWindowTitle(CharSequence title) {
        this.mDecorToolbar.setWindowTitle(title);
    }

    public boolean requestFocus() {
        ViewGroup viewGroup = this.mDecorToolbar.getViewGroup();
        if (viewGroup == null || viewGroup.hasFocus()) {
            return false;
        }
        viewGroup.requestFocus();
        return true;
    }

    public void setSubtitle(CharSequence subtitle) {
        this.mDecorToolbar.setSubtitle(subtitle);
    }

    public void setDisplayOptions(int options) {
        if ((options & 4) != 0) {
            this.mDisplayHomeAsUpSet = true;
        }
        this.mDecorToolbar.setDisplayOptions(options);
    }

    public void setDisplayOptions(int options, int mask) {
        int current = this.mDecorToolbar.getDisplayOptions();
        if ((mask & 4) != 0) {
            this.mDisplayHomeAsUpSet = true;
        }
        this.mDecorToolbar.setDisplayOptions((options & mask) | ((mask ^ -1) & current));
    }

    public void setBackgroundDrawable(Drawable d) {
        this.mContainerView.setPrimaryBackground(d);
    }

    public void setStackedBackgroundDrawable(Drawable d) {
        this.mContainerView.setStackedBackground(d);
    }

    public void setSplitBackgroundDrawable(Drawable d) {
    }

    public View getCustomView() {
        return this.mDecorToolbar.getCustomView();
    }

    public CharSequence getTitle() {
        return this.mDecorToolbar.getTitle();
    }

    public CharSequence getSubtitle() {
        return this.mDecorToolbar.getSubtitle();
    }

    public int getNavigationMode() {
        return this.mDecorToolbar.getNavigationMode();
    }

    public int getDisplayOptions() {
        return this.mDecorToolbar.getDisplayOptions();
    }

    public ActionMode startActionMode(ActionMode.Callback callback) {
        ActionModeImpl actionModeImpl = this.mActionMode;
        if (actionModeImpl != null) {
            actionModeImpl.finish();
        }
        this.mOverlayLayout.setHideOnContentScrollEnabled(false);
        this.mContextView.killMode();
        ActionModeImpl mode = new ActionModeImpl(this.mContextView.getContext(), callback);
        if (!mode.dispatchOnCreate()) {
            return null;
        }
        this.mActionMode = mode;
        mode.invalidate();
        this.mContextView.initForMode(mode);
        animateToMode(true);
        return mode;
    }

    private void configureTab(ActionBar.Tab tab, int position) {
        TabImpl tabi = (TabImpl) tab;
        if (tabi.getCallback() != null) {
            tabi.setPosition(position);
            this.mTabs.add(position, tabi);
            int count = this.mTabs.size();
            for (int i = position + 1; i < count; i++) {
                this.mTabs.get(i).setPosition(i);
            }
            return;
        }
        throw new IllegalStateException("Action Bar Tab must have a Callback");
    }

    public void addTab(ActionBar.Tab tab) {
        addTab(tab, this.mTabs.isEmpty());
    }

    public void addTab(ActionBar.Tab tab, int position) {
        addTab(tab, position, this.mTabs.isEmpty());
    }

    public void addTab(ActionBar.Tab tab, boolean setSelected) {
        ensureTabsExist();
        this.mTabScrollView.addTab(tab, setSelected);
        configureTab(tab, this.mTabs.size());
        if (setSelected) {
            selectTab(tab);
        }
    }

    public void addTab(ActionBar.Tab tab, int position, boolean setSelected) {
        ensureTabsExist();
        this.mTabScrollView.addTab(tab, position, setSelected);
        configureTab(tab, position);
        if (setSelected) {
            selectTab(tab);
        }
    }

    public ActionBar.Tab newTab() {
        return new TabImpl();
    }

    public void removeTab(ActionBar.Tab tab) {
        removeTabAt(tab.getPosition());
    }

    public void removeTabAt(int position) {
        if (this.mTabScrollView != null) {
            TabImpl tabImpl = this.mSelectedTab;
            int selectedTabPosition = tabImpl != null ? tabImpl.getPosition() : this.mSavedTabPosition;
            this.mTabScrollView.removeTabAt(position);
            TabImpl removedTab = this.mTabs.remove(position);
            if (removedTab != null) {
                removedTab.setPosition(-1);
            }
            int newTabCount = this.mTabs.size();
            for (int i = position; i < newTabCount; i++) {
                this.mTabs.get(i).setPosition(i);
            }
            if (selectedTabPosition == position) {
                selectTab(this.mTabs.isEmpty() ? null : this.mTabs.get(Math.max(0, position - 1)));
            }
        }
    }

    public void selectTab(ActionBar.Tab tab) {
        FragmentTransaction trans;
        int i = -1;
        if (getNavigationMode() != 2) {
            if (tab != null) {
                i = tab.getPosition();
            }
            this.mSavedTabPosition = i;
            return;
        }
        if (!(this.mActivity instanceof FragmentActivity) || this.mDecorToolbar.getViewGroup().isInEditMode()) {
            trans = null;
        } else {
            trans = ((FragmentActivity) this.mActivity).getSupportFragmentManager().beginTransaction().disallowAddToBackStack();
        }
        TabImpl tabImpl = this.mSelectedTab;
        if (tabImpl != tab) {
            ScrollingTabContainerView scrollingTabContainerView = this.mTabScrollView;
            if (tab != null) {
                i = tab.getPosition();
            }
            scrollingTabContainerView.setTabSelected(i);
            TabImpl tabImpl2 = this.mSelectedTab;
            if (tabImpl2 != null) {
                tabImpl2.getCallback().onTabUnselected(this.mSelectedTab, trans);
            }
            TabImpl tabImpl3 = (TabImpl) tab;
            this.mSelectedTab = tabImpl3;
            if (tabImpl3 != null) {
                tabImpl3.getCallback().onTabSelected(this.mSelectedTab, trans);
            }
        } else if (tabImpl != null) {
            tabImpl.getCallback().onTabReselected(this.mSelectedTab, trans);
            this.mTabScrollView.animateToTab(tab.getPosition());
        }
        if (trans != null && !trans.isEmpty()) {
            trans.commit();
        }
    }

    public ActionBar.Tab getSelectedTab() {
        return this.mSelectedTab;
    }

    public int getHeight() {
        return this.mContainerView.getHeight();
    }

    public void enableContentAnimations(boolean enabled) {
        this.mContentAnimations = enabled;
    }

    public void show() {
        if (this.mHiddenByApp) {
            this.mHiddenByApp = false;
            updateVisibility(false);
        }
    }

    private void showForActionMode() {
        if (!this.mShowingForMode) {
            this.mShowingForMode = true;
            ActionBarOverlayLayout actionBarOverlayLayout = this.mOverlayLayout;
            if (actionBarOverlayLayout != null) {
                actionBarOverlayLayout.setShowingForActionMode(true);
            }
            updateVisibility(false);
        }
    }

    public void showForSystem() {
        if (this.mHiddenBySystem) {
            this.mHiddenBySystem = false;
            updateVisibility(true);
        }
    }

    public void hide() {
        if (!this.mHiddenByApp) {
            this.mHiddenByApp = true;
            updateVisibility(false);
        }
    }

    private void hideForActionMode() {
        if (this.mShowingForMode) {
            this.mShowingForMode = false;
            ActionBarOverlayLayout actionBarOverlayLayout = this.mOverlayLayout;
            if (actionBarOverlayLayout != null) {
                actionBarOverlayLayout.setShowingForActionMode(false);
            }
            updateVisibility(false);
        }
    }

    public void hideForSystem() {
        if (!this.mHiddenBySystem) {
            this.mHiddenBySystem = true;
            updateVisibility(true);
        }
    }

    public void setHideOnContentScrollEnabled(boolean hideOnContentScroll) {
        if (!hideOnContentScroll || this.mOverlayLayout.isInOverlayMode()) {
            this.mHideOnContentScroll = hideOnContentScroll;
            this.mOverlayLayout.setHideOnContentScrollEnabled(hideOnContentScroll);
            return;
        }
        throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll");
    }

    public boolean isHideOnContentScrollEnabled() {
        return this.mOverlayLayout.isHideOnContentScrollEnabled();
    }

    public int getHideOffset() {
        return this.mOverlayLayout.getActionBarHideOffset();
    }

    public void setHideOffset(int offset) {
        if (offset == 0 || this.mOverlayLayout.isInOverlayMode()) {
            this.mOverlayLayout.setActionBarHideOffset(offset);
            return;
        }
        throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to set a non-zero hide offset");
    }

    static boolean checkShowingFlags(boolean hiddenByApp, boolean hiddenBySystem, boolean showingForMode) {
        if (showingForMode) {
            return true;
        }
        if (hiddenByApp || hiddenBySystem) {
            return false;
        }
        return true;
    }

    private void updateVisibility(boolean fromSystem) {
        if (checkShowingFlags(this.mHiddenByApp, this.mHiddenBySystem, this.mShowingForMode)) {
            if (!this.mNowShowing) {
                this.mNowShowing = true;
                doShow(fromSystem);
            }
        } else if (this.mNowShowing) {
            this.mNowShowing = false;
            doHide(fromSystem);
        }
    }

    public void doShow(boolean fromSystem) {
        View view;
        View view2;
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = this.mCurrentShowAnim;
        if (viewPropertyAnimatorCompatSet != null) {
            viewPropertyAnimatorCompatSet.cancel();
        }
        this.mContainerView.setVisibility(0);
        if (this.mCurWindowVisibility != 0 || (!this.mShowHideAnimationEnabled && !fromSystem)) {
            this.mContainerView.setAlpha(1.0f);
            this.mContainerView.setTranslationY(0.0f);
            if (this.mContentAnimations && (view = this.mContentView) != null) {
                view.setTranslationY(0.0f);
            }
            this.mShowListener.onAnimationEnd((View) null);
        } else {
            this.mContainerView.setTranslationY(0.0f);
            float startingY = (float) (-this.mContainerView.getHeight());
            if (fromSystem) {
                int[] topLeft = {0, 0};
                this.mContainerView.getLocationInWindow(topLeft);
                startingY -= (float) topLeft[1];
            }
            this.mContainerView.setTranslationY(startingY);
            ViewPropertyAnimatorCompatSet anim = new ViewPropertyAnimatorCompatSet();
            ViewPropertyAnimatorCompat a = ViewCompat.animate(this.mContainerView).translationY(0.0f);
            a.setUpdateListener(this.mUpdateListener);
            anim.play(a);
            if (this.mContentAnimations && (view2 = this.mContentView) != null) {
                view2.setTranslationY(startingY);
                anim.play(ViewCompat.animate(this.mContentView).translationY(0.0f));
            }
            anim.setInterpolator(sShowInterpolator);
            anim.setDuration(250);
            anim.setListener(this.mShowListener);
            this.mCurrentShowAnim = anim;
            anim.start();
        }
        ActionBarOverlayLayout actionBarOverlayLayout = this.mOverlayLayout;
        if (actionBarOverlayLayout != null) {
            ViewCompat.requestApplyInsets(actionBarOverlayLayout);
        }
    }

    public void doHide(boolean fromSystem) {
        View view;
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = this.mCurrentShowAnim;
        if (viewPropertyAnimatorCompatSet != null) {
            viewPropertyAnimatorCompatSet.cancel();
        }
        if (this.mCurWindowVisibility != 0 || (!this.mShowHideAnimationEnabled && !fromSystem)) {
            this.mHideListener.onAnimationEnd((View) null);
            return;
        }
        this.mContainerView.setAlpha(1.0f);
        this.mContainerView.setTransitioning(true);
        ViewPropertyAnimatorCompatSet anim = new ViewPropertyAnimatorCompatSet();
        float endingY = (float) (-this.mContainerView.getHeight());
        if (fromSystem) {
            int[] topLeft = {0, 0};
            this.mContainerView.getLocationInWindow(topLeft);
            endingY -= (float) topLeft[1];
        }
        ViewPropertyAnimatorCompat a = ViewCompat.animate(this.mContainerView).translationY(endingY);
        a.setUpdateListener(this.mUpdateListener);
        anim.play(a);
        if (this.mContentAnimations && (view = this.mContentView) != null) {
            anim.play(ViewCompat.animate(view).translationY(endingY));
        }
        anim.setInterpolator(sHideInterpolator);
        anim.setDuration(250);
        anim.setListener(this.mHideListener);
        this.mCurrentShowAnim = anim;
        anim.start();
    }

    public boolean isShowing() {
        int height = getHeight();
        return this.mNowShowing && (height == 0 || getHideOffset() < height);
    }

    public void animateToMode(boolean toActionMode) {
        ViewPropertyAnimatorCompat fadeIn;
        ViewPropertyAnimatorCompat fadeOut;
        if (toActionMode) {
            showForActionMode();
        } else {
            hideForActionMode();
        }
        if (shouldAnimateContextView()) {
            if (toActionMode) {
                fadeOut = this.mDecorToolbar.setupAnimatorToVisibility(4, FADE_OUT_DURATION_MS);
                fadeIn = this.mContextView.setupAnimatorToVisibility(0, FADE_IN_DURATION_MS);
            } else {
                fadeIn = this.mDecorToolbar.setupAnimatorToVisibility(0, FADE_IN_DURATION_MS);
                fadeOut = this.mContextView.setupAnimatorToVisibility(8, FADE_OUT_DURATION_MS);
            }
            ViewPropertyAnimatorCompatSet set = new ViewPropertyAnimatorCompatSet();
            set.playSequentially(fadeOut, fadeIn);
            set.start();
        } else if (toActionMode) {
            this.mDecorToolbar.setVisibility(4);
            this.mContextView.setVisibility(0);
        } else {
            this.mDecorToolbar.setVisibility(0);
            this.mContextView.setVisibility(8);
        }
    }

    private boolean shouldAnimateContextView() {
        return ViewCompat.isLaidOut(this.mContainerView);
    }

    public Context getThemedContext() {
        if (this.mThemedContext == null) {
            TypedValue outValue = new TypedValue();
            this.mContext.getTheme().resolveAttribute(C0503R.attr.actionBarWidgetTheme, outValue, true);
            int targetThemeRes = outValue.resourceId;
            if (targetThemeRes != 0) {
                this.mThemedContext = new ContextThemeWrapper(this.mContext, targetThemeRes);
            } else {
                this.mThemedContext = this.mContext;
            }
        }
        return this.mThemedContext;
    }

    public boolean isTitleTruncated() {
        DecorToolbar decorToolbar = this.mDecorToolbar;
        return decorToolbar != null && decorToolbar.isTitleTruncated();
    }

    public void setHomeAsUpIndicator(Drawable indicator) {
        this.mDecorToolbar.setNavigationIcon(indicator);
    }

    public void setHomeAsUpIndicator(int resId) {
        this.mDecorToolbar.setNavigationIcon(resId);
    }

    public void setHomeActionContentDescription(CharSequence description) {
        this.mDecorToolbar.setNavigationContentDescription(description);
    }

    public void setHomeActionContentDescription(int resId) {
        this.mDecorToolbar.setNavigationContentDescription(resId);
    }

    public void onContentScrollStarted() {
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = this.mCurrentShowAnim;
        if (viewPropertyAnimatorCompatSet != null) {
            viewPropertyAnimatorCompatSet.cancel();
            this.mCurrentShowAnim = null;
        }
    }

    public void onContentScrollStopped() {
    }

    public boolean collapseActionView() {
        DecorToolbar decorToolbar = this.mDecorToolbar;
        if (decorToolbar == null || !decorToolbar.hasExpandedActionView()) {
            return false;
        }
        this.mDecorToolbar.collapseActionView();
        return true;
    }

    public class ActionModeImpl extends ActionMode implements MenuBuilder.Callback {
        private final Context mActionModeContext;
        private ActionMode.Callback mCallback;
        private WeakReference<View> mCustomView;
        private final MenuBuilder mMenu;

        public ActionModeImpl(Context context, ActionMode.Callback callback) {
            this.mActionModeContext = context;
            this.mCallback = callback;
            MenuBuilder defaultShowAsAction = new MenuBuilder(context).setDefaultShowAsAction(1);
            this.mMenu = defaultShowAsAction;
            defaultShowAsAction.setCallback(this);
        }

        public MenuInflater getMenuInflater() {
            return new SupportMenuInflater(this.mActionModeContext);
        }

        public Menu getMenu() {
            return this.mMenu;
        }

        public void finish() {
            if (WindowDecorActionBar.this.mActionMode == this) {
                if (!WindowDecorActionBar.checkShowingFlags(WindowDecorActionBar.this.mHiddenByApp, WindowDecorActionBar.this.mHiddenBySystem, false)) {
                    WindowDecorActionBar.this.mDeferredDestroyActionMode = this;
                    WindowDecorActionBar.this.mDeferredModeDestroyCallback = this.mCallback;
                } else {
                    this.mCallback.onDestroyActionMode(this);
                }
                this.mCallback = null;
                WindowDecorActionBar.this.animateToMode(false);
                WindowDecorActionBar.this.mContextView.closeMode();
                WindowDecorActionBar.this.mOverlayLayout.setHideOnContentScrollEnabled(WindowDecorActionBar.this.mHideOnContentScroll);
                WindowDecorActionBar.this.mActionMode = null;
            }
        }

        public void invalidate() {
            if (WindowDecorActionBar.this.mActionMode == this) {
                this.mMenu.stopDispatchingItemsChanged();
                try {
                    this.mCallback.onPrepareActionMode(this, this.mMenu);
                } finally {
                    this.mMenu.startDispatchingItemsChanged();
                }
            }
        }

        public boolean dispatchOnCreate() {
            this.mMenu.stopDispatchingItemsChanged();
            try {
                return this.mCallback.onCreateActionMode(this, this.mMenu);
            } finally {
                this.mMenu.startDispatchingItemsChanged();
            }
        }

        public void setCustomView(View view) {
            WindowDecorActionBar.this.mContextView.setCustomView(view);
            this.mCustomView = new WeakReference<>(view);
        }

        public void setSubtitle(CharSequence subtitle) {
            WindowDecorActionBar.this.mContextView.setSubtitle(subtitle);
        }

        public void setTitle(CharSequence title) {
            WindowDecorActionBar.this.mContextView.setTitle(title);
        }

        public void setTitle(int resId) {
            setTitle((CharSequence) WindowDecorActionBar.this.mContext.getResources().getString(resId));
        }

        public void setSubtitle(int resId) {
            setSubtitle((CharSequence) WindowDecorActionBar.this.mContext.getResources().getString(resId));
        }

        public CharSequence getTitle() {
            return WindowDecorActionBar.this.mContextView.getTitle();
        }

        public CharSequence getSubtitle() {
            return WindowDecorActionBar.this.mContextView.getSubtitle();
        }

        public void setTitleOptionalHint(boolean titleOptional) {
            super.setTitleOptionalHint(titleOptional);
            WindowDecorActionBar.this.mContextView.setTitleOptional(titleOptional);
        }

        public boolean isTitleOptional() {
            return WindowDecorActionBar.this.mContextView.isTitleOptional();
        }

        public View getCustomView() {
            WeakReference<View> weakReference = this.mCustomView;
            if (weakReference != null) {
                return (View) weakReference.get();
            }
            return null;
        }

        public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
            ActionMode.Callback callback = this.mCallback;
            if (callback != null) {
                return callback.onActionItemClicked(this, item);
            }
            return false;
        }

        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
        }

        public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
            if (this.mCallback == null) {
                return false;
            }
            if (!subMenu.hasVisibleItems()) {
                return true;
            }
            new MenuPopupHelper(WindowDecorActionBar.this.getThemedContext(), subMenu).show();
            return true;
        }

        public void onCloseSubMenu(SubMenuBuilder menu) {
        }

        public void onMenuModeChange(MenuBuilder menu) {
            if (this.mCallback != null) {
                invalidate();
                WindowDecorActionBar.this.mContextView.showOverflowMenu();
            }
        }
    }

    public class TabImpl extends ActionBar.Tab {
        private ActionBar.TabListener mCallback;
        private CharSequence mContentDesc;
        private View mCustomView;
        private Drawable mIcon;
        private int mPosition = -1;
        private Object mTag;
        private CharSequence mText;

        public TabImpl() {
        }

        public Object getTag() {
            return this.mTag;
        }

        public ActionBar.Tab setTag(Object tag) {
            this.mTag = tag;
            return this;
        }

        public ActionBar.TabListener getCallback() {
            return this.mCallback;
        }

        public ActionBar.Tab setTabListener(ActionBar.TabListener callback) {
            this.mCallback = callback;
            return this;
        }

        public View getCustomView() {
            return this.mCustomView;
        }

        public ActionBar.Tab setCustomView(View view) {
            this.mCustomView = view;
            if (this.mPosition >= 0) {
                WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
            }
            return this;
        }

        public ActionBar.Tab setCustomView(int layoutResId) {
            return setCustomView(LayoutInflater.from(WindowDecorActionBar.this.getThemedContext()).inflate(layoutResId, (ViewGroup) null));
        }

        public Drawable getIcon() {
            return this.mIcon;
        }

        public int getPosition() {
            return this.mPosition;
        }

        public void setPosition(int position) {
            this.mPosition = position;
        }

        public CharSequence getText() {
            return this.mText;
        }

        public ActionBar.Tab setIcon(Drawable icon) {
            this.mIcon = icon;
            if (this.mPosition >= 0) {
                WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
            }
            return this;
        }

        public ActionBar.Tab setIcon(int resId) {
            return setIcon(AppCompatResources.getDrawable(WindowDecorActionBar.this.mContext, resId));
        }

        public ActionBar.Tab setText(CharSequence text) {
            this.mText = text;
            if (this.mPosition >= 0) {
                WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
            }
            return this;
        }

        public ActionBar.Tab setText(int resId) {
            return setText(WindowDecorActionBar.this.mContext.getResources().getText(resId));
        }

        public void select() {
            WindowDecorActionBar.this.selectTab(this);
        }

        public ActionBar.Tab setContentDescription(int resId) {
            return setContentDescription(WindowDecorActionBar.this.mContext.getResources().getText(resId));
        }

        public ActionBar.Tab setContentDescription(CharSequence contentDesc) {
            this.mContentDesc = contentDesc;
            if (this.mPosition >= 0) {
                WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
            }
            return this;
        }

        public CharSequence getContentDescription() {
            return this.mContentDesc;
        }
    }

    public void setCustomView(View view) {
        this.mDecorToolbar.setCustomView(view);
    }

    public void setCustomView(View view, ActionBar.LayoutParams layoutParams) {
        view.setLayoutParams(layoutParams);
        this.mDecorToolbar.setCustomView(view);
    }

    public void setListNavigationCallbacks(SpinnerAdapter adapter, ActionBar.OnNavigationListener callback) {
        this.mDecorToolbar.setDropdownParams(adapter, new NavItemSelectedListener(callback));
    }

    public int getSelectedNavigationIndex() {
        switch (this.mDecorToolbar.getNavigationMode()) {
            case 1:
                return this.mDecorToolbar.getDropdownSelectedPosition();
            case 2:
                TabImpl tabImpl = this.mSelectedTab;
                if (tabImpl != null) {
                    return tabImpl.getPosition();
                }
                return -1;
            default:
                return -1;
        }
    }

    public int getNavigationItemCount() {
        switch (this.mDecorToolbar.getNavigationMode()) {
            case 1:
                return this.mDecorToolbar.getDropdownItemCount();
            case 2:
                return this.mTabs.size();
            default:
                return 0;
        }
    }

    public int getTabCount() {
        return this.mTabs.size();
    }

    public void setNavigationMode(int mode) {
        ActionBarOverlayLayout actionBarOverlayLayout;
        int oldMode = this.mDecorToolbar.getNavigationMode();
        switch (oldMode) {
            case 2:
                this.mSavedTabPosition = getSelectedNavigationIndex();
                selectTab((ActionBar.Tab) null);
                this.mTabScrollView.setVisibility(8);
                break;
        }
        if (!(oldMode == mode || this.mHasEmbeddedTabs || (actionBarOverlayLayout = this.mOverlayLayout) == null)) {
            ViewCompat.requestApplyInsets(actionBarOverlayLayout);
        }
        this.mDecorToolbar.setNavigationMode(mode);
        boolean z = false;
        switch (mode) {
            case 2:
                ensureTabsExist();
                this.mTabScrollView.setVisibility(0);
                int i = this.mSavedTabPosition;
                if (i != -1) {
                    setSelectedNavigationItem(i);
                    this.mSavedTabPosition = -1;
                    break;
                }
                break;
        }
        this.mDecorToolbar.setCollapsible(mode == 2 && !this.mHasEmbeddedTabs);
        ActionBarOverlayLayout actionBarOverlayLayout2 = this.mOverlayLayout;
        if (mode == 2 && !this.mHasEmbeddedTabs) {
            z = true;
        }
        actionBarOverlayLayout2.setHasNonEmbeddedTabs(z);
    }

    public ActionBar.Tab getTabAt(int index) {
        return this.mTabs.get(index);
    }

    public void setIcon(int resId) {
        this.mDecorToolbar.setIcon(resId);
    }

    public void setIcon(Drawable icon) {
        this.mDecorToolbar.setIcon(icon);
    }

    public boolean hasIcon() {
        return this.mDecorToolbar.hasIcon();
    }

    public void setLogo(int resId) {
        this.mDecorToolbar.setLogo(resId);
    }

    public void setLogo(Drawable logo) {
        this.mDecorToolbar.setLogo(logo);
    }

    public boolean hasLogo() {
        return this.mDecorToolbar.hasLogo();
    }

    public void setDefaultDisplayHomeAsUpEnabled(boolean enable) {
        if (!this.mDisplayHomeAsUpSet) {
            setDisplayHomeAsUpEnabled(enable);
        }
    }

    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        Menu menu;
        ActionModeImpl actionModeImpl = this.mActionMode;
        if (actionModeImpl == null || (menu = actionModeImpl.getMenu()) == null) {
            return false;
        }
        boolean z = true;
        if (KeyCharacterMap.load(event != null ? event.getDeviceId() : -1).getKeyboardType() == 1) {
            z = false;
        }
        menu.setQwertyMode(z);
        return menu.performShortcut(keyCode, event, 0);
    }
}
