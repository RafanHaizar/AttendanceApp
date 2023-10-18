package com.google.android.material.transformation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

@Deprecated
public abstract class ExpandableTransformationBehavior extends ExpandableBehavior {
    /* access modifiers changed from: private */
    public AnimatorSet currentAnimation;

    /* access modifiers changed from: protected */
    public abstract AnimatorSet onCreateExpandedStateChangeAnimation(View view, View view2, boolean z, boolean z2);

    public ExpandableTransformationBehavior() {
    }

    public ExpandableTransformationBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /* access modifiers changed from: protected */
    public boolean onExpandedStateChange(View dependency, View child, boolean expanded, boolean animated) {
        AnimatorSet animatorSet = this.currentAnimation;
        boolean currentlyAnimating = animatorSet != null;
        if (currentlyAnimating) {
            animatorSet.cancel();
        }
        AnimatorSet onCreateExpandedStateChangeAnimation = onCreateExpandedStateChangeAnimation(dependency, child, expanded, currentlyAnimating);
        this.currentAnimation = onCreateExpandedStateChangeAnimation;
        onCreateExpandedStateChangeAnimation.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                AnimatorSet unused = ExpandableTransformationBehavior.this.currentAnimation = null;
            }
        });
        this.currentAnimation.start();
        if (!animated) {
            this.currentAnimation.end();
        }
        return true;
    }
}
