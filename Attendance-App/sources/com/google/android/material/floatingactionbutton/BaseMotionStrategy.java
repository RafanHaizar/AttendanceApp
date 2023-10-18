package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Property;
import android.view.View;
import androidx.core.util.Preconditions;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.animation.MotionSpec;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import java.util.ArrayList;
import java.util.List;

abstract class BaseMotionStrategy implements MotionStrategy {
    private final Context context;
    private MotionSpec defaultMotionSpec;
    /* access modifiers changed from: private */
    public final ExtendedFloatingActionButton fab;
    private final ArrayList<Animator.AnimatorListener> listeners = new ArrayList<>();
    private MotionSpec motionSpec;
    private final AnimatorTracker tracker;

    BaseMotionStrategy(ExtendedFloatingActionButton fab2, AnimatorTracker tracker2) {
        this.fab = fab2;
        this.context = fab2.getContext();
        this.tracker = tracker2;
    }

    public final void setMotionSpec(MotionSpec motionSpec2) {
        this.motionSpec = motionSpec2;
    }

    public final MotionSpec getCurrentMotionSpec() {
        MotionSpec motionSpec2 = this.motionSpec;
        if (motionSpec2 != null) {
            return motionSpec2;
        }
        if (this.defaultMotionSpec == null) {
            this.defaultMotionSpec = MotionSpec.createFromResource(this.context, getDefaultMotionSpecResource());
        }
        return (MotionSpec) Preconditions.checkNotNull(this.defaultMotionSpec);
    }

    public final void addAnimationListener(Animator.AnimatorListener listener) {
        this.listeners.add(listener);
    }

    public final void removeAnimationListener(Animator.AnimatorListener listener) {
        this.listeners.remove(listener);
    }

    public final List<Animator.AnimatorListener> getListeners() {
        return this.listeners;
    }

    public MotionSpec getMotionSpec() {
        return this.motionSpec;
    }

    public void onAnimationStart(Animator animator) {
        this.tracker.onNextAnimationStart(animator);
    }

    public void onAnimationEnd() {
        this.tracker.clear();
    }

    public void onAnimationCancel() {
        this.tracker.clear();
    }

    public AnimatorSet createAnimator() {
        return createAnimator(getCurrentMotionSpec());
    }

    /* access modifiers changed from: package-private */
    public AnimatorSet createAnimator(MotionSpec spec) {
        List<Animator> animators = new ArrayList<>();
        if (spec.hasPropertyValues("opacity")) {
            animators.add(spec.getAnimator("opacity", this.fab, View.ALPHA));
        }
        if (spec.hasPropertyValues(CommonCssConstants.SCALE)) {
            animators.add(spec.getAnimator(CommonCssConstants.SCALE, this.fab, View.SCALE_Y));
            animators.add(spec.getAnimator(CommonCssConstants.SCALE, this.fab, View.SCALE_X));
        }
        if (spec.hasPropertyValues("width")) {
            animators.add(spec.getAnimator("width", this.fab, ExtendedFloatingActionButton.WIDTH));
        }
        if (spec.hasPropertyValues("height")) {
            animators.add(spec.getAnimator("height", this.fab, ExtendedFloatingActionButton.HEIGHT));
        }
        if (spec.hasPropertyValues("paddingStart")) {
            animators.add(spec.getAnimator("paddingStart", this.fab, ExtendedFloatingActionButton.PADDING_START));
        }
        if (spec.hasPropertyValues("paddingEnd")) {
            animators.add(spec.getAnimator("paddingEnd", this.fab, ExtendedFloatingActionButton.PADDING_END));
        }
        if (spec.hasPropertyValues("labelOpacity")) {
            animators.add(spec.getAnimator("labelOpacity", this.fab, new Property<ExtendedFloatingActionButton, Float>(Float.class, "LABEL_OPACITY_PROPERTY") {
                public Float get(ExtendedFloatingActionButton object) {
                    return Float.valueOf(AnimationUtils.lerp(0.0f, 1.0f, (((float) Color.alpha(object.getCurrentTextColor())) / 255.0f) / ((float) Color.alpha(object.originalTextCsl.getColorForState(object.getDrawableState(), BaseMotionStrategy.this.fab.originalTextCsl.getDefaultColor())))));
                }

                public void set(ExtendedFloatingActionButton object, Float value) {
                    int originalColor = object.originalTextCsl.getColorForState(object.getDrawableState(), BaseMotionStrategy.this.fab.originalTextCsl.getDefaultColor());
                    ColorStateList csl = ColorStateList.valueOf(Color.argb((int) (255.0f * AnimationUtils.lerp(0.0f, ((float) Color.alpha(originalColor)) / 255.0f, value.floatValue())), Color.red(originalColor), Color.green(originalColor), Color.blue(originalColor)));
                    if (value.floatValue() == 1.0f) {
                        object.silentlyUpdateTextColor(object.originalTextCsl);
                    } else {
                        object.silentlyUpdateTextColor(csl);
                    }
                }
            }));
        }
        AnimatorSet set = new AnimatorSet();
        AnimatorSetCompat.playTogether(set, animators);
        return set;
    }
}
