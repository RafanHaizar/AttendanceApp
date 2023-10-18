package com.google.android.material.search;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ClippableRoundedCornerLayout;
import com.google.android.material.internal.FadeThroughDrawable;
import com.google.android.material.internal.FadeThroughUpdateListener;
import com.google.android.material.internal.MultiViewUpdateListener;
import com.google.android.material.internal.RectEvaluator;
import com.google.android.material.internal.ReversableAnimatedValueInterpolator;
import com.google.android.material.internal.ToolbarUtils;
import com.google.android.material.internal.TouchObserverFrameLayout;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.search.SearchView;
import java.util.Objects;

class SearchViewAnimationHelper {
    private static final float CONTENT_FROM_SCALE = 0.95f;
    private static final long HIDE_CLEAR_BUTTON_ALPHA_DURATION_MS = 42;
    private static final long HIDE_CLEAR_BUTTON_ALPHA_START_DELAY_MS = 0;
    private static final long HIDE_CONTENT_ALPHA_DURATION_MS = 83;
    private static final long HIDE_CONTENT_ALPHA_START_DELAY_MS = 0;
    private static final long HIDE_CONTENT_SCALE_DURATION_MS = 250;
    private static final long HIDE_DURATION_MS = 250;
    private static final long HIDE_TRANSLATE_DURATION_MS = 300;
    private static final long SHOW_CLEAR_BUTTON_ALPHA_DURATION_MS = 50;
    private static final long SHOW_CLEAR_BUTTON_ALPHA_START_DELAY_MS = 250;
    private static final long SHOW_CONTENT_ALPHA_DURATION_MS = 150;
    private static final long SHOW_CONTENT_ALPHA_START_DELAY_MS = 75;
    private static final long SHOW_CONTENT_SCALE_DURATION_MS = 300;
    private static final long SHOW_DURATION_MS = 300;
    private static final long SHOW_TRANSLATE_DURATION_MS = 350;
    private static final long SHOW_TRANSLATE_KEYBOARD_START_DELAY_MS = 150;
    private final ImageButton clearButton;
    private final TouchObserverFrameLayout contentContainer;
    private final View divider;
    private final Toolbar dummyToolbar;
    private final EditText editText;
    private final FrameLayout headerContainer;
    /* access modifiers changed from: private */
    public final ClippableRoundedCornerLayout rootView;
    private final View scrim;
    /* access modifiers changed from: private */
    public SearchBar searchBar;
    private final TextView searchPrefix;
    /* access modifiers changed from: private */
    public final SearchView searchView;
    private final Toolbar toolbar;
    private final FrameLayout toolbarContainer;

    SearchViewAnimationHelper(SearchView searchView2) {
        this.searchView = searchView2;
        this.scrim = searchView2.scrim;
        this.rootView = searchView2.rootView;
        this.headerContainer = searchView2.headerContainer;
        this.toolbarContainer = searchView2.toolbarContainer;
        this.toolbar = searchView2.toolbar;
        this.dummyToolbar = searchView2.dummyToolbar;
        this.searchPrefix = searchView2.searchPrefix;
        this.editText = searchView2.editText;
        this.clearButton = searchView2.clearButton;
        this.divider = searchView2.divider;
        this.contentContainer = searchView2.contentContainer;
    }

    /* access modifiers changed from: package-private */
    public void setSearchBar(SearchBar searchBar2) {
        this.searchBar = searchBar2;
    }

    /* access modifiers changed from: package-private */
    public void show() {
        if (this.searchBar != null) {
            startShowAnimationExpand();
        } else {
            startShowAnimationTranslate();
        }
    }

    /* access modifiers changed from: package-private */
    public void hide() {
        if (this.searchBar != null) {
            startHideAnimationCollapse();
        } else {
            startHideAnimationTranslate();
        }
    }

    private void startShowAnimationExpand() {
        if (this.searchView.isAdjustNothingSoftInputMode()) {
            this.searchView.requestFocusAndShowKeyboardIfNeeded();
        }
        this.searchView.setTransitionState(SearchView.TransitionState.SHOWING);
        setUpDummyToolbarIfNeeded();
        this.editText.setText(this.searchBar.getText());
        EditText editText2 = this.editText;
        editText2.setSelection(editText2.getText().length());
        this.rootView.setVisibility(4);
        this.rootView.post(new SearchViewAnimationHelper$$ExternalSyntheticLambda2(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$startShowAnimationExpand$0$com-google-android-material-search-SearchViewAnimationHelper */
    public /* synthetic */ void mo23530x94743afc() {
        AnimatorSet animatorSet = getExpandCollapseAnimatorSet(true);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animation) {
                SearchViewAnimationHelper.this.rootView.setVisibility(0);
                SearchViewAnimationHelper.this.searchBar.stopOnLoadAnimation();
            }

            public void onAnimationEnd(Animator animation) {
                if (!SearchViewAnimationHelper.this.searchView.isAdjustNothingSoftInputMode()) {
                    SearchViewAnimationHelper.this.searchView.requestFocusAndShowKeyboardIfNeeded();
                }
                SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.SHOWN);
            }
        });
        animatorSet.start();
    }

    private void startHideAnimationCollapse() {
        if (this.searchView.isAdjustNothingSoftInputMode()) {
            this.searchView.clearFocusAndHideKeyboard();
        }
        AnimatorSet animatorSet = getExpandCollapseAnimatorSet(false);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animation) {
                SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.HIDING);
            }

            public void onAnimationEnd(Animator animation) {
                SearchViewAnimationHelper.this.rootView.setVisibility(8);
                if (!SearchViewAnimationHelper.this.searchView.isAdjustNothingSoftInputMode()) {
                    SearchViewAnimationHelper.this.searchView.clearFocusAndHideKeyboard();
                }
                SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.HIDDEN);
            }
        });
        animatorSet.start();
    }

    private void startShowAnimationTranslate() {
        if (this.searchView.isAdjustNothingSoftInputMode()) {
            SearchView searchView2 = this.searchView;
            Objects.requireNonNull(searchView2);
            searchView2.postDelayed(new SearchViewAnimationHelper$$ExternalSyntheticLambda3(searchView2), 150);
        }
        this.rootView.setVisibility(4);
        this.rootView.post(new SearchViewAnimationHelper$$ExternalSyntheticLambda4(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$startShowAnimationTranslate$1$com-google-android-material-search-SearchViewAnimationHelper */
    public /* synthetic */ void mo23531x4df249eb() {
        ClippableRoundedCornerLayout clippableRoundedCornerLayout = this.rootView;
        clippableRoundedCornerLayout.setTranslationY((float) clippableRoundedCornerLayout.getHeight());
        AnimatorSet animatorSet = getTranslateAnimatorSet(true);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animation) {
                SearchViewAnimationHelper.this.rootView.setVisibility(0);
                SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.SHOWING);
            }

            public void onAnimationEnd(Animator animation) {
                if (!SearchViewAnimationHelper.this.searchView.isAdjustNothingSoftInputMode()) {
                    SearchViewAnimationHelper.this.searchView.requestFocusAndShowKeyboardIfNeeded();
                }
                SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.SHOWN);
            }
        });
        animatorSet.start();
    }

    private void startHideAnimationTranslate() {
        if (this.searchView.isAdjustNothingSoftInputMode()) {
            this.searchView.clearFocusAndHideKeyboard();
        }
        AnimatorSet animatorSet = getTranslateAnimatorSet(false);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animation) {
                SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.HIDING);
            }

            public void onAnimationEnd(Animator animation) {
                SearchViewAnimationHelper.this.rootView.setVisibility(8);
                if (!SearchViewAnimationHelper.this.searchView.isAdjustNothingSoftInputMode()) {
                    SearchViewAnimationHelper.this.searchView.clearFocusAndHideKeyboard();
                }
                SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.HIDDEN);
            }
        });
        animatorSet.start();
    }

    private AnimatorSet getTranslateAnimatorSet(boolean show) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{getTranslationYAnimator()});
        addBackButtonProgressAnimatorIfNeeded(animatorSet);
        animatorSet.setInterpolator(ReversableAnimatedValueInterpolator.m232of(show, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        animatorSet.setDuration(show ? SHOW_TRANSLATE_DURATION_MS : 300);
        return animatorSet;
    }

    private Animator getTranslationYAnimator() {
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{(float) this.rootView.getHeight(), 0.0f});
        animator.addUpdateListener(MultiViewUpdateListener.translationYListener(this.rootView));
        return animator;
    }

    private AnimatorSet getExpandCollapseAnimatorSet(final boolean show) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{getScrimAlphaAnimator(show), getRootViewAnimator(show), getClearButtonAnimator(show), getContentAnimator(show), getButtonsAnimator(show), getHeaderContainerAnimator(show), getDummyToolbarAnimator(show), getActionMenuViewsAlphaAnimator(show), getEditTextAnimator(show), getSearchPrefixAnimator(show)});
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animation) {
                SearchViewAnimationHelper.this.setContentViewsAlpha(show ? 0.0f : 1.0f);
            }

            public void onAnimationEnd(Animator animation) {
                SearchViewAnimationHelper.this.setContentViewsAlpha(show ? 1.0f : 0.0f);
                if (show) {
                    SearchViewAnimationHelper.this.rootView.resetClipBoundsAndCornerRadius();
                }
            }
        });
        return animatorSet;
    }

    /* access modifiers changed from: private */
    public void setContentViewsAlpha(float alpha) {
        this.clearButton.setAlpha(alpha);
        this.divider.setAlpha(alpha);
        this.contentContainer.setAlpha(alpha);
        setActionMenuViewAlphaIfNeeded(alpha);
    }

    private void setActionMenuViewAlphaIfNeeded(float alpha) {
        ActionMenuView actionMenuView;
        if (this.searchView.isMenuItemsAnimated() && (actionMenuView = ToolbarUtils.getActionMenuView(this.toolbar)) != null) {
            actionMenuView.setAlpha(alpha);
        }
    }

    private Animator getScrimAlphaAnimator(boolean show) {
        TimeInterpolator interpolator = show ? AnimationUtils.LINEAR_INTERPOLATOR : AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR;
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        animator.setDuration(show ? 300 : 250);
        animator.setInterpolator(ReversableAnimatedValueInterpolator.m232of(show, interpolator));
        animator.addUpdateListener(MultiViewUpdateListener.alphaListener(this.scrim));
        return animator;
    }

    private Animator getRootViewAnimator(boolean show) {
        Rect toClipBounds = ViewUtils.calculateRectFromBounds(this.searchView);
        Rect fromClipBounds = calculateFromClipBounds();
        Rect clipBounds = new Rect(fromClipBounds);
        float initialCornerRadius = this.searchBar.getCornerSize();
        ValueAnimator animator = ValueAnimator.ofObject(new RectEvaluator(clipBounds), new Object[]{fromClipBounds, toClipBounds});
        animator.addUpdateListener(new SearchViewAnimationHelper$$ExternalSyntheticLambda5(this, initialCornerRadius, clipBounds));
        animator.setDuration(show ? 300 : 250);
        animator.setInterpolator(ReversableAnimatedValueInterpolator.m232of(show, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        return animator;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$getRootViewAnimator$2$com-google-android-material-search-SearchViewAnimationHelper */
    public /* synthetic */ void mo23529xa183b80f(float initialCornerRadius, Rect clipBounds, ValueAnimator valueAnimator) {
        this.rootView.updateClipBoundsAndCornerRadius(clipBounds, (1.0f - valueAnimator.getAnimatedFraction()) * initialCornerRadius);
    }

    private Rect calculateFromClipBounds() {
        int[] searchBarAbsolutePosition = new int[2];
        this.searchBar.getLocationOnScreen(searchBarAbsolutePosition);
        int searchBarAbsoluteLeft = searchBarAbsolutePosition[0];
        int searchBarAbsoluteTop = searchBarAbsolutePosition[1];
        int[] searchViewAbsolutePosition = new int[2];
        this.rootView.getLocationOnScreen(searchViewAbsolutePosition);
        int fromLeft = searchBarAbsoluteLeft - searchViewAbsolutePosition[0];
        int fromTop = searchBarAbsoluteTop - searchViewAbsolutePosition[1];
        return new Rect(fromLeft, fromTop, this.searchBar.getWidth() + fromLeft, this.searchBar.getHeight() + fromTop);
    }

    private Animator getClearButtonAnimator(boolean show) {
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        animator.setDuration(show ? SHOW_CLEAR_BUTTON_ALPHA_DURATION_MS : HIDE_CLEAR_BUTTON_ALPHA_DURATION_MS);
        animator.setStartDelay(show ? 250 : 0);
        animator.setInterpolator(ReversableAnimatedValueInterpolator.m232of(show, AnimationUtils.LINEAR_INTERPOLATOR));
        animator.addUpdateListener(MultiViewUpdateListener.alphaListener(this.clearButton));
        return animator;
    }

    private Animator getButtonsAnimator(boolean show) {
        AnimatorSet animatorSet = new AnimatorSet();
        addBackButtonTranslationAnimatorIfNeeded(animatorSet);
        addBackButtonProgressAnimatorIfNeeded(animatorSet);
        addActionMenuViewAnimatorIfNeeded(animatorSet);
        animatorSet.setDuration(show ? 300 : 250);
        animatorSet.setInterpolator(ReversableAnimatedValueInterpolator.m232of(show, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        return animatorSet;
    }

    private void addBackButtonTranslationAnimatorIfNeeded(AnimatorSet animatorSet) {
        ImageButton backButton = ToolbarUtils.getNavigationIconButton(this.toolbar);
        if (backButton != null) {
            ValueAnimator backButtonAnimatorX = ValueAnimator.ofFloat(new float[]{(float) getFromTranslationXStart(backButton), 0.0f});
            backButtonAnimatorX.addUpdateListener(MultiViewUpdateListener.translationXListener(backButton));
            ValueAnimator backButtonAnimatorY = ValueAnimator.ofFloat(new float[]{(float) getFromTranslationY(), 0.0f});
            backButtonAnimatorY.addUpdateListener(MultiViewUpdateListener.translationYListener(backButton));
            animatorSet.playTogether(new Animator[]{backButtonAnimatorX, backButtonAnimatorY});
        }
    }

    private void addBackButtonProgressAnimatorIfNeeded(AnimatorSet animatorSet) {
        ImageButton backButton = ToolbarUtils.getNavigationIconButton(this.toolbar);
        if (backButton != null) {
            Drawable drawable = DrawableCompat.unwrap(backButton.getDrawable());
            if (this.searchView.isAnimatedNavigationIcon()) {
                addDrawerArrowDrawableAnimatorIfNeeded(animatorSet, drawable);
                addFadeThroughDrawableAnimatorIfNeeded(animatorSet, drawable);
                return;
            }
            setFullDrawableProgressIfNeeded(drawable);
        }
    }

    private void addDrawerArrowDrawableAnimatorIfNeeded(AnimatorSet animatorSet, Drawable drawable) {
        if (drawable instanceof DrawerArrowDrawable) {
            ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            animator.addUpdateListener(new SearchViewAnimationHelper$$ExternalSyntheticLambda1((DrawerArrowDrawable) drawable));
            animatorSet.playTogether(new Animator[]{animator});
        }
    }

    private void addFadeThroughDrawableAnimatorIfNeeded(AnimatorSet animatorSet, Drawable drawable) {
        if (drawable instanceof FadeThroughDrawable) {
            ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            animator.addUpdateListener(new SearchViewAnimationHelper$$ExternalSyntheticLambda0((FadeThroughDrawable) drawable));
            animatorSet.playTogether(new Animator[]{animator});
        }
    }

    private void setFullDrawableProgressIfNeeded(Drawable drawable) {
        if (drawable instanceof DrawerArrowDrawable) {
            ((DrawerArrowDrawable) drawable).setProgress(1.0f);
        }
        if (drawable instanceof FadeThroughDrawable) {
            ((FadeThroughDrawable) drawable).setProgress(1.0f);
        }
    }

    private void addActionMenuViewAnimatorIfNeeded(AnimatorSet animatorSet) {
        ActionMenuView actionMenuView = ToolbarUtils.getActionMenuView(this.toolbar);
        if (actionMenuView != null) {
            ValueAnimator actionMenuViewAnimatorX = ValueAnimator.ofFloat(new float[]{(float) getFromTranslationXEnd(actionMenuView), 0.0f});
            actionMenuViewAnimatorX.addUpdateListener(MultiViewUpdateListener.translationXListener(actionMenuView));
            ValueAnimator actionMenuViewAnimatorY = ValueAnimator.ofFloat(new float[]{(float) getFromTranslationY(), 0.0f});
            actionMenuViewAnimatorY.addUpdateListener(MultiViewUpdateListener.translationYListener(actionMenuView));
            animatorSet.playTogether(new Animator[]{actionMenuViewAnimatorX, actionMenuViewAnimatorY});
        }
    }

    private Animator getDummyToolbarAnimator(boolean show) {
        return getTranslationAnimator(show, false, this.dummyToolbar);
    }

    private Animator getHeaderContainerAnimator(boolean show) {
        return getTranslationAnimator(show, false, this.headerContainer);
    }

    private Animator getActionMenuViewsAlphaAnimator(boolean show) {
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        animator.setDuration(show ? 300 : 250);
        animator.setInterpolator(ReversableAnimatedValueInterpolator.m232of(show, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        if (this.searchView.isMenuItemsAnimated()) {
            animator.addUpdateListener(new FadeThroughUpdateListener(ToolbarUtils.getActionMenuView(this.dummyToolbar), ToolbarUtils.getActionMenuView(this.toolbar)));
        }
        return animator;
    }

    private Animator getSearchPrefixAnimator(boolean show) {
        return getTranslationAnimator(show, true, this.searchPrefix);
    }

    private Animator getEditTextAnimator(boolean show) {
        return getTranslationAnimator(show, true, this.editText);
    }

    private Animator getContentAnimator(boolean show) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{getContentAlphaAnimator(show), getDividerAnimator(show), getContentScaleAnimator(show)});
        return animatorSet;
    }

    private Animator getContentAlphaAnimator(boolean show) {
        ValueAnimator animatorAlpha = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        animatorAlpha.setDuration(show ? 150 : HIDE_CONTENT_ALPHA_DURATION_MS);
        animatorAlpha.setStartDelay(show ? 75 : 0);
        animatorAlpha.setInterpolator(ReversableAnimatedValueInterpolator.m232of(show, AnimationUtils.LINEAR_INTERPOLATOR));
        animatorAlpha.addUpdateListener(MultiViewUpdateListener.alphaListener(this.divider, this.contentContainer));
        return animatorAlpha;
    }

    private Animator getDividerAnimator(boolean show) {
        ValueAnimator animatorDivider = ValueAnimator.ofFloat(new float[]{(((float) this.contentContainer.getHeight()) * 0.050000012f) / 2.0f, 0.0f});
        animatorDivider.setDuration(show ? 300 : 250);
        animatorDivider.setInterpolator(ReversableAnimatedValueInterpolator.m232of(show, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        animatorDivider.addUpdateListener(MultiViewUpdateListener.translationYListener(this.divider));
        return animatorDivider;
    }

    private Animator getContentScaleAnimator(boolean show) {
        ValueAnimator animatorScale = ValueAnimator.ofFloat(new float[]{CONTENT_FROM_SCALE, 1.0f});
        animatorScale.setDuration(show ? 300 : 250);
        animatorScale.setInterpolator(ReversableAnimatedValueInterpolator.m232of(show, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        animatorScale.addUpdateListener(MultiViewUpdateListener.scaleListener(this.contentContainer));
        return animatorScale;
    }

    private Animator getTranslationAnimator(boolean show, boolean anchoredToStart, View view) {
        ValueAnimator animatorX = ValueAnimator.ofFloat(new float[]{(float) (anchoredToStart ? getFromTranslationXStart(view) : getFromTranslationXEnd(view)), 0.0f});
        animatorX.addUpdateListener(MultiViewUpdateListener.translationXListener(view));
        ValueAnimator animatorY = ValueAnimator.ofFloat(new float[]{(float) getFromTranslationY(), 0.0f});
        animatorY.addUpdateListener(MultiViewUpdateListener.translationYListener(view));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{animatorX, animatorY});
        animatorSet.setDuration(show ? 300 : 250);
        animatorSet.setInterpolator(ReversableAnimatedValueInterpolator.m232of(show, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        return animatorSet;
    }

    private int getFromTranslationXStart(View view) {
        int marginStart = MarginLayoutParamsCompat.getMarginStart((ViewGroup.MarginLayoutParams) view.getLayoutParams());
        int paddingStart = ViewCompat.getPaddingStart(this.searchBar);
        if (ViewUtils.isLayoutRtl(this.searchBar)) {
            return ((this.searchBar.getWidth() - this.searchBar.getRight()) + marginStart) - paddingStart;
        }
        return (this.searchBar.getLeft() - marginStart) + paddingStart;
    }

    private int getFromTranslationXEnd(View view) {
        int marginEnd = MarginLayoutParamsCompat.getMarginEnd((ViewGroup.MarginLayoutParams) view.getLayoutParams());
        if (ViewUtils.isLayoutRtl(this.searchBar)) {
            return this.searchBar.getLeft() - marginEnd;
        }
        return (this.searchBar.getRight() - this.searchView.getWidth()) + marginEnd;
    }

    private int getFromTranslationY() {
        return ((this.searchBar.getTop() + this.searchBar.getBottom()) / 2) - ((this.toolbarContainer.getTop() + this.toolbarContainer.getBottom()) / 2);
    }

    private void setUpDummyToolbarIfNeeded() {
        Menu menu = this.dummyToolbar.getMenu();
        if (menu != null) {
            menu.clear();
        }
        if (this.searchBar.getMenuResId() == -1 || !this.searchView.isMenuItemsAnimated()) {
            this.dummyToolbar.setVisibility(8);
            return;
        }
        this.dummyToolbar.inflateMenu(this.searchBar.getMenuResId());
        setMenuItemsNotClickable(this.dummyToolbar);
        this.dummyToolbar.setVisibility(0);
    }

    private void setMenuItemsNotClickable(Toolbar toolbar2) {
        ActionMenuView actionMenuView = ToolbarUtils.getActionMenuView(toolbar2);
        if (actionMenuView != null) {
            for (int i = 0; i < actionMenuView.getChildCount(); i++) {
                View menuItem = actionMenuView.getChildAt(i);
                menuItem.setClickable(false);
                menuItem.setFocusable(false);
                menuItem.setFocusableInTouchMode(false);
            }
        }
    }
}
