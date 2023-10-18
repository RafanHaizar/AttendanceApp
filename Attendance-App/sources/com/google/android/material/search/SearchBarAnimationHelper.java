package com.google.android.material.search;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.ActionMenuView;
import androidx.core.view.ViewCompat;
import com.google.android.material.animation.AnimatableView;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.internal.ExpandCollapseAnimationHelper;
import com.google.android.material.internal.MultiViewUpdateListener;
import com.google.android.material.internal.ToolbarUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.search.SearchBar;
import com.google.android.material.shape.MaterialShapeDrawable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

class SearchBarAnimationHelper {
    private static final long COLLAPSE_DURATION_MS = 250;
    private static final long COLLAPSE_FADE_IN_CHILDREN_DURATION_MS = 100;
    private static final long EXPAND_DURATION_MS = 300;
    private static final long EXPAND_FADE_OUT_CHILDREN_DURATION_MS = 75;
    private static final long ON_LOAD_ANIM_CENTER_VIEW_DEFAULT_FADE_DURATION_MS = 250;
    private static final long ON_LOAD_ANIM_CENTER_VIEW_DEFAULT_FADE_IN_START_DELAY_MS = 500;
    private static final long ON_LOAD_ANIM_CENTER_VIEW_DEFAULT_FADE_OUT_START_DELAY_MS = 750;
    private static final long ON_LOAD_ANIM_SECONDARY_DURATION_MS = 250;
    private static final long ON_LOAD_ANIM_SECONDARY_START_DELAY_MS = 250;
    private final Set<AnimatorListenerAdapter> collapseAnimationListeners = new LinkedHashSet();
    /* access modifiers changed from: private */
    public boolean collapsing;
    private Animator defaultCenterViewAnimator;
    private final Set<AnimatorListenerAdapter> expandAnimationListeners = new LinkedHashSet();
    /* access modifiers changed from: private */
    public boolean expanding;
    private final Set<SearchBar.OnLoadAnimationCallback> onLoadAnimationCallbacks = new LinkedHashSet();
    private boolean onLoadAnimationFadeInEnabled = true;
    /* access modifiers changed from: private */
    public Animator runningExpandOrCollapseAnimator = null;
    private Animator secondaryViewAnimator;

    private interface OnLoadAnimationInvocation {
        void invoke(SearchBar.OnLoadAnimationCallback onLoadAnimationCallback);
    }

    SearchBarAnimationHelper() {
    }

    /* access modifiers changed from: package-private */
    public void startOnLoadAnimation(SearchBar searchBar) {
        dispatchOnLoadAnimation(new SearchBarAnimationHelper$$ExternalSyntheticLambda3());
        TextView textView = searchBar.getTextView();
        final View centerView = searchBar.getCenterView();
        View secondaryActionMenuItemView = ToolbarUtils.getSecondaryActionMenuItemView(searchBar);
        final Animator secondaryViewAnimator2 = getSecondaryViewAnimator(textView, secondaryActionMenuItemView);
        secondaryViewAnimator2.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                SearchBarAnimationHelper.this.dispatchOnLoadAnimation(new SearchBarAnimationHelper$1$$ExternalSyntheticLambda0());
            }
        });
        this.secondaryViewAnimator = secondaryViewAnimator2;
        textView.setAlpha(0.0f);
        if (secondaryActionMenuItemView != null) {
            secondaryActionMenuItemView.setAlpha(0.0f);
        }
        if (centerView instanceof AnimatableView) {
            Objects.requireNonNull(secondaryViewAnimator2);
            ((AnimatableView) centerView).startAnimation(new SearchBarAnimationHelper$$ExternalSyntheticLambda4(secondaryViewAnimator2));
        } else if (centerView != null) {
            centerView.setAlpha(0.0f);
            centerView.setVisibility(0);
            Animator defaultCenterViewAnimator2 = getDefaultCenterViewAnimator(centerView);
            this.defaultCenterViewAnimator = defaultCenterViewAnimator2;
            defaultCenterViewAnimator2.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    centerView.setVisibility(8);
                    secondaryViewAnimator2.start();
                }
            });
            defaultCenterViewAnimator2.start();
        } else {
            secondaryViewAnimator2.start();
        }
    }

    /* access modifiers changed from: package-private */
    public void stopOnLoadAnimation(SearchBar searchBar) {
        Animator animator = this.secondaryViewAnimator;
        if (animator != null) {
            animator.end();
        }
        Animator animator2 = this.defaultCenterViewAnimator;
        if (animator2 != null) {
            animator2.end();
        }
        View centerView = searchBar.getCenterView();
        if (centerView instanceof AnimatableView) {
            ((AnimatableView) centerView).stopAnimation();
        }
        if (centerView != null) {
            centerView.setAlpha(0.0f);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isOnLoadAnimationFadeInEnabled() {
        return this.onLoadAnimationFadeInEnabled;
    }

    /* access modifiers changed from: package-private */
    public void setOnLoadAnimationFadeInEnabled(boolean onLoadAnimationFadeInEnabled2) {
        this.onLoadAnimationFadeInEnabled = onLoadAnimationFadeInEnabled2;
    }

    /* access modifiers changed from: package-private */
    public void addOnLoadAnimationCallback(SearchBar.OnLoadAnimationCallback onLoadAnimationCallback) {
        this.onLoadAnimationCallbacks.add(onLoadAnimationCallback);
    }

    /* access modifiers changed from: package-private */
    public boolean removeOnLoadAnimationCallback(SearchBar.OnLoadAnimationCallback onLoadAnimationCallback) {
        return this.onLoadAnimationCallbacks.remove(onLoadAnimationCallback);
    }

    /* access modifiers changed from: private */
    public void dispatchOnLoadAnimation(OnLoadAnimationInvocation invocation) {
        for (SearchBar.OnLoadAnimationCallback onLoadAnimationCallback : this.onLoadAnimationCallbacks) {
            invocation.invoke(onLoadAnimationCallback);
        }
    }

    private Animator getDefaultCenterViewAnimator(View centerView) {
        ValueAnimator fadeInAnimator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        fadeInAnimator.addUpdateListener(MultiViewUpdateListener.alphaListener(centerView));
        fadeInAnimator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
        long j = 0;
        fadeInAnimator.setDuration(this.onLoadAnimationFadeInEnabled ? 250 : 0);
        if (this.onLoadAnimationFadeInEnabled) {
            j = ON_LOAD_ANIM_CENTER_VIEW_DEFAULT_FADE_IN_START_DELAY_MS;
        }
        fadeInAnimator.setStartDelay(j);
        ValueAnimator fadeOutAnimator = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
        fadeOutAnimator.addUpdateListener(MultiViewUpdateListener.alphaListener(centerView));
        fadeOutAnimator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
        fadeOutAnimator.setDuration(250);
        fadeOutAnimator.setStartDelay(ON_LOAD_ANIM_CENTER_VIEW_DEFAULT_FADE_OUT_START_DELAY_MS);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(new Animator[]{fadeInAnimator, fadeOutAnimator});
        return animatorSet;
    }

    private Animator getSecondaryViewAnimator(TextView textView, View secondaryActionMenuItemView) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setStartDelay(250);
        animatorSet.play(getTextViewAnimator(textView));
        if (secondaryActionMenuItemView != null) {
            animatorSet.play(getSecondaryActionMenuItemAnimator(secondaryActionMenuItemView));
        }
        return animatorSet;
    }

    private Animator getTextViewAnimator(TextView textView) {
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        animator.addUpdateListener(MultiViewUpdateListener.alphaListener(textView));
        animator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
        animator.setDuration(250);
        return animator;
    }

    private Animator getSecondaryActionMenuItemAnimator(View secondaryActionMenuItemView) {
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        animator.addUpdateListener(MultiViewUpdateListener.alphaListener(secondaryActionMenuItemView));
        animator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
        animator.setDuration(250);
        return animator;
    }

    /* access modifiers changed from: package-private */
    public void startExpandAnimation(SearchBar searchBar, View expandedView, AppBarLayout appBarLayout, boolean skipAnimation) {
        Animator animator;
        if (isCollapsing() && (animator = this.runningExpandOrCollapseAnimator) != null) {
            animator.cancel();
        }
        this.expanding = true;
        expandedView.setVisibility(4);
        expandedView.post(new SearchBarAnimationHelper$$ExternalSyntheticLambda0(this, searchBar, expandedView, appBarLayout, skipAnimation));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$startExpandAnimation$0$com-google-android-material-search-SearchBarAnimationHelper */
    public /* synthetic */ void mo23434x1b96b119(SearchBar searchBar, View expandedView, AppBarLayout appBarLayout, boolean skipAnimation) {
        AnimatorSet fadeAndExpandAnimatorSet = new AnimatorSet();
        fadeAndExpandAnimatorSet.playSequentially(new Animator[]{getFadeOutChildrenAnimator(searchBar, expandedView), getExpandAnimator(searchBar, expandedView, appBarLayout)});
        fadeAndExpandAnimatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                Animator unused = SearchBarAnimationHelper.this.runningExpandOrCollapseAnimator = null;
            }
        });
        for (AnimatorListenerAdapter listener : this.expandAnimationListeners) {
            fadeAndExpandAnimatorSet.addListener(listener);
        }
        if (skipAnimation) {
            fadeAndExpandAnimatorSet.setDuration(0);
        }
        fadeAndExpandAnimatorSet.start();
        this.runningExpandOrCollapseAnimator = fadeAndExpandAnimatorSet;
    }

    private Animator getExpandAnimator(final SearchBar searchBar, View expandedView, AppBarLayout appBarLayout) {
        return getExpandCollapseAnimationHelper(searchBar, expandedView, appBarLayout).setDuration(EXPAND_DURATION_MS).addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animation) {
                searchBar.setVisibility(4);
            }

            public void onAnimationEnd(Animator animation) {
                boolean unused = SearchBarAnimationHelper.this.expanding = false;
            }
        }).getExpandAnimator();
    }

    /* access modifiers changed from: package-private */
    public boolean isExpanding() {
        return this.expanding;
    }

    /* access modifiers changed from: package-private */
    public void addExpandAnimationListener(AnimatorListenerAdapter listener) {
        this.expandAnimationListeners.add(listener);
    }

    /* access modifiers changed from: package-private */
    public boolean removeExpandAnimationListener(AnimatorListenerAdapter listener) {
        return this.expandAnimationListeners.remove(listener);
    }

    /* access modifiers changed from: package-private */
    public void startCollapseAnimation(SearchBar searchBar, View expandedView, AppBarLayout appBarLayout, boolean skipAnimation) {
        Animator animator;
        if (isExpanding() && (animator = this.runningExpandOrCollapseAnimator) != null) {
            animator.cancel();
        }
        this.collapsing = true;
        AnimatorSet collapseAndFadeAnimatorSet = new AnimatorSet();
        collapseAndFadeAnimatorSet.playSequentially(new Animator[]{getCollapseAnimator(searchBar, expandedView, appBarLayout), getFadeInChildrenAnimator(searchBar)});
        collapseAndFadeAnimatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                Animator unused = SearchBarAnimationHelper.this.runningExpandOrCollapseAnimator = null;
            }
        });
        for (AnimatorListenerAdapter listener : this.collapseAnimationListeners) {
            collapseAndFadeAnimatorSet.addListener(listener);
        }
        if (skipAnimation) {
            collapseAndFadeAnimatorSet.setDuration(0);
        }
        collapseAndFadeAnimatorSet.start();
        this.runningExpandOrCollapseAnimator = collapseAndFadeAnimatorSet;
    }

    private Animator getCollapseAnimator(final SearchBar searchBar, View expandedView, AppBarLayout appBarLayout) {
        return getExpandCollapseAnimationHelper(searchBar, expandedView, appBarLayout).setDuration(250).addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animation) {
                searchBar.stopOnLoadAnimation();
            }

            public void onAnimationEnd(Animator animation) {
                searchBar.setVisibility(0);
                boolean unused = SearchBarAnimationHelper.this.collapsing = false;
            }
        }).getCollapseAnimator();
    }

    /* access modifiers changed from: package-private */
    public boolean isCollapsing() {
        return this.collapsing;
    }

    /* access modifiers changed from: package-private */
    public void addCollapseAnimationListener(AnimatorListenerAdapter listener) {
        this.collapseAnimationListeners.add(listener);
    }

    /* access modifiers changed from: package-private */
    public boolean removeCollapseAnimationListener(AnimatorListenerAdapter listener) {
        return this.collapseAnimationListeners.remove(listener);
    }

    private ExpandCollapseAnimationHelper getExpandCollapseAnimationHelper(SearchBar searchBar, View expandedView, AppBarLayout appBarLayout) {
        return new ExpandCollapseAnimationHelper(searchBar, expandedView).setAdditionalUpdateListener(getExpandedViewBackgroundUpdateListener(searchBar, expandedView)).setCollapsedViewOffsetY(appBarLayout != null ? appBarLayout.getTop() : 0).addEndAnchoredViews((Collection<View>) getEndAnchoredViews(expandedView));
    }

    private ValueAnimator.AnimatorUpdateListener getExpandedViewBackgroundUpdateListener(SearchBar searchBar, View expandedView) {
        MaterialShapeDrawable expandedViewBackground = MaterialShapeDrawable.createWithElevationOverlay(expandedView.getContext());
        expandedViewBackground.setCornerSize(searchBar.getCornerSize());
        expandedViewBackground.setElevation(ViewCompat.getElevation(searchBar));
        return new SearchBarAnimationHelper$$ExternalSyntheticLambda1(expandedViewBackground, expandedView);
    }

    static /* synthetic */ void lambda$getExpandedViewBackgroundUpdateListener$1(MaterialShapeDrawable expandedViewBackground, View expandedView, ValueAnimator valueAnimator) {
        expandedViewBackground.setInterpolation(1.0f - valueAnimator.getAnimatedFraction());
        ViewCompat.setBackground(expandedView, expandedViewBackground);
        expandedView.setAlpha(1.0f);
    }

    private Animator getFadeOutChildrenAnimator(SearchBar searchBar, View expandedView) {
        List<View> children = getFadeChildren(searchBar);
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
        animator.addUpdateListener(MultiViewUpdateListener.alphaListener((Collection<View>) children));
        animator.addUpdateListener(new SearchBarAnimationHelper$$ExternalSyntheticLambda2(expandedView));
        animator.setDuration(75);
        animator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
        return animator;
    }

    private Animator getFadeInChildrenAnimator(SearchBar searchBar) {
        List<View> children = getFadeChildren(searchBar);
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        animator.addUpdateListener(MultiViewUpdateListener.alphaListener((Collection<View>) children));
        animator.setDuration(COLLAPSE_FADE_IN_CHILDREN_DURATION_MS);
        animator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
        return animator;
    }

    private List<View> getFadeChildren(SearchBar searchBar) {
        List<View> children = ViewUtils.getChildren(searchBar);
        if (searchBar.getCenterView() != null) {
            children.remove(searchBar.getCenterView());
        }
        return children;
    }

    private List<View> getEndAnchoredViews(View expandedView) {
        boolean isRtl = ViewUtils.isLayoutRtl(expandedView);
        List<View> endAnchoredViews = new ArrayList<>();
        if (expandedView instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) expandedView;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                if ((!isRtl && (child instanceof ActionMenuView)) || (isRtl && !(child instanceof ActionMenuView))) {
                    endAnchoredViews.add(child);
                }
            }
        }
        return endAnchoredViews;
    }
}
