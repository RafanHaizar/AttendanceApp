package com.google.android.material.internal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.view.View;
import com.google.android.material.animation.AnimationUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ExpandCollapseAnimationHelper {
    private ValueAnimator.AnimatorUpdateListener additionalUpdateListener;
    private final View collapsedView;
    private int collapsedViewOffsetY;
    private long duration;
    private final List<View> endAnchoredViews = new ArrayList();
    /* access modifiers changed from: private */
    public final View expandedView;
    private int expandedViewOffsetY;
    private final List<AnimatorListenerAdapter> listeners = new ArrayList();

    public ExpandCollapseAnimationHelper(View collapsedView2, View expandedView2) {
        this.collapsedView = collapsedView2;
        this.expandedView = expandedView2;
    }

    public Animator getExpandAnimator() {
        Animator animator = getAnimatorSet(true);
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animation) {
                ExpandCollapseAnimationHelper.this.expandedView.setVisibility(0);
            }
        });
        addListeners(animator, this.listeners);
        return animator;
    }

    public Animator getCollapseAnimator() {
        Animator animator = getAnimatorSet(false);
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                ExpandCollapseAnimationHelper.this.expandedView.setVisibility(8);
            }
        });
        addListeners(animator, this.listeners);
        return animator;
    }

    public ExpandCollapseAnimationHelper setDuration(long duration2) {
        this.duration = duration2;
        return this;
    }

    public ExpandCollapseAnimationHelper addListener(AnimatorListenerAdapter listener) {
        this.listeners.add(listener);
        return this;
    }

    public ExpandCollapseAnimationHelper addEndAnchoredViews(View... views) {
        Collections.addAll(this.endAnchoredViews, views);
        return this;
    }

    public ExpandCollapseAnimationHelper addEndAnchoredViews(Collection<View> views) {
        this.endAnchoredViews.addAll(views);
        return this;
    }

    public ExpandCollapseAnimationHelper setAdditionalUpdateListener(ValueAnimator.AnimatorUpdateListener additionalUpdateListener2) {
        this.additionalUpdateListener = additionalUpdateListener2;
        return this;
    }

    public ExpandCollapseAnimationHelper setCollapsedViewOffsetY(int collapsedViewOffsetY2) {
        this.collapsedViewOffsetY = collapsedViewOffsetY2;
        return this;
    }

    public ExpandCollapseAnimationHelper setExpandedViewOffsetY(int expandedViewOffsetY2) {
        this.expandedViewOffsetY = expandedViewOffsetY2;
        return this;
    }

    private AnimatorSet getAnimatorSet(boolean expand) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{getExpandCollapseAnimator(expand), getExpandedViewChildrenAlphaAnimator(expand), getEndAnchoredViewsTranslateAnimator(expand)});
        return animatorSet;
    }

    private Animator getExpandCollapseAnimator(boolean expand) {
        Rect fromBounds = ViewUtils.calculateRectFromBounds(this.collapsedView, this.collapsedViewOffsetY);
        Rect toBounds = ViewUtils.calculateRectFromBounds(this.expandedView, this.expandedViewOffsetY);
        Rect bounds = new Rect(fromBounds);
        ValueAnimator animator = ValueAnimator.ofObject(new RectEvaluator(bounds), new Object[]{fromBounds, toBounds});
        animator.addUpdateListener(new ExpandCollapseAnimationHelper$$ExternalSyntheticLambda0(this, bounds));
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = this.additionalUpdateListener;
        if (animatorUpdateListener != null) {
            animator.addUpdateListener(animatorUpdateListener);
        }
        animator.setDuration(this.duration);
        animator.setInterpolator(ReversableAnimatedValueInterpolator.m232of(expand, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        return animator;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$getExpandCollapseAnimator$0$com-google-android-material-internal-ExpandCollapseAnimationHelper */
    public /* synthetic */ void mo22785xeb41e2ac(Rect bounds, ValueAnimator valueAnimator) {
        ViewUtils.setBoundsFromRect(this.expandedView, bounds);
    }

    private Animator getExpandedViewChildrenAlphaAnimator(boolean expand) {
        List<View> expandedViewChildren = ViewUtils.getChildren(this.expandedView);
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        animator.addUpdateListener(MultiViewUpdateListener.alphaListener((Collection<View>) expandedViewChildren));
        animator.setDuration(this.duration);
        animator.setInterpolator(ReversableAnimatedValueInterpolator.m232of(expand, AnimationUtils.LINEAR_INTERPOLATOR));
        return animator;
    }

    private Animator getEndAnchoredViewsTranslateAnimator(boolean expand) {
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{(float) ((this.expandedView.getLeft() - this.collapsedView.getLeft()) + (this.collapsedView.getRight() - this.expandedView.getRight())), 0.0f});
        animator.addUpdateListener(MultiViewUpdateListener.translationXListener((Collection<View>) this.endAnchoredViews));
        animator.setDuration(this.duration);
        animator.setInterpolator(ReversableAnimatedValueInterpolator.m232of(expand, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        return animator;
    }

    private void addListeners(Animator animator, List<AnimatorListenerAdapter> listeners2) {
        for (AnimatorListenerAdapter listener : listeners2) {
            animator.addListener(listener);
        }
    }
}
