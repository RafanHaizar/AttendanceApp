package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import com.google.android.material.C1087R;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.internal.DescendantOffsetUtils;
import java.util.List;

public class ExtendedFloatingActionButton extends MaterialButton implements CoordinatorLayout.AttachedBehavior {
    private static final int ANIM_STATE_HIDING = 1;
    private static final int ANIM_STATE_NONE = 0;
    private static final int ANIM_STATE_SHOWING = 2;
    private static final int DEF_STYLE_RES = C1087R.C1093style.Widget_MaterialComponents_ExtendedFloatingActionButton_Icon;
    private static final int EXTEND = 3;
    private static final int EXTEND_STRATEGY_AUTO = 0;
    private static final int EXTEND_STRATEGY_MATCH_PARENT = 2;
    private static final int EXTEND_STRATEGY_WRAP_CONTENT = 1;
    static final Property<View, Float> HEIGHT = new Property<View, Float>(Float.class, "height") {
        public void set(View object, Float value) {
            object.getLayoutParams().height = value.intValue();
            object.requestLayout();
        }

        public Float get(View object) {
            return Float.valueOf((float) object.getLayoutParams().height);
        }
    };
    private static final int HIDE = 1;
    static final Property<View, Float> PADDING_END = new Property<View, Float>(Float.class, "paddingEnd") {
        public void set(View object, Float value) {
            ViewCompat.setPaddingRelative(object, ViewCompat.getPaddingStart(object), object.getPaddingTop(), value.intValue(), object.getPaddingBottom());
        }

        public Float get(View object) {
            return Float.valueOf((float) ViewCompat.getPaddingEnd(object));
        }
    };
    static final Property<View, Float> PADDING_START = new Property<View, Float>(Float.class, "paddingStart") {
        public void set(View object, Float value) {
            ViewCompat.setPaddingRelative(object, value.intValue(), object.getPaddingTop(), ViewCompat.getPaddingEnd(object), object.getPaddingBottom());
        }

        public Float get(View object) {
            return Float.valueOf((float) ViewCompat.getPaddingStart(object));
        }
    };
    private static final int SHOW = 0;
    private static final int SHRINK = 2;
    static final Property<View, Float> WIDTH = new Property<View, Float>(Float.class, "width") {
        public void set(View object, Float value) {
            object.getLayoutParams().width = value.intValue();
            object.requestLayout();
        }

        public Float get(View object) {
            return Float.valueOf((float) object.getLayoutParams().width);
        }
    };
    /* access modifiers changed from: private */
    public int animState;
    private boolean animateShowBeforeLayout;
    private final CoordinatorLayout.Behavior<ExtendedFloatingActionButton> behavior;
    private final AnimatorTracker changeVisibilityTracker;
    private final int collapsedSize;
    private final MotionStrategy extendStrategy;
    private final int extendStrategyType;
    /* access modifiers changed from: private */
    public int extendedPaddingEnd;
    /* access modifiers changed from: private */
    public int extendedPaddingStart;
    private final MotionStrategy hideStrategy;
    /* access modifiers changed from: private */
    public boolean isExtended;
    /* access modifiers changed from: private */
    public boolean isTransforming;
    /* access modifiers changed from: private */
    public int originalHeight;
    protected ColorStateList originalTextCsl;
    /* access modifiers changed from: private */
    public int originalWidth;
    private final MotionStrategy showStrategy;
    private final MotionStrategy shrinkStrategy;

    interface Size {
        int getHeight();

        ViewGroup.LayoutParams getLayoutParams();

        int getPaddingEnd();

        int getPaddingStart();

        int getWidth();
    }

    public static abstract class OnChangedCallback {
        public void onShown(ExtendedFloatingActionButton extendedFab) {
        }

        public void onHidden(ExtendedFloatingActionButton extendedFab) {
        }

        public void onExtended(ExtendedFloatingActionButton extendedFab) {
        }

        public void onShrunken(ExtendedFloatingActionButton extendedFab) {
        }
    }

    public ExtendedFloatingActionButton(Context context) {
        this(context, (AttributeSet) null);
    }

    public ExtendedFloatingActionButton(Context context, AttributeSet attrs) {
        this(context, attrs, C1087R.attr.extendedFloatingActionButtonStyle);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ExtendedFloatingActionButton(android.content.Context r17, android.util.AttributeSet r18, int r19) {
        /*
            r16 = this;
            r0 = r16
            r7 = r18
            r8 = r19
            int r9 = DEF_STYLE_RES
            r1 = r17
            android.content.Context r2 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r1, r7, r8, r9)
            r0.<init>(r2, r7, r8)
            r10 = 0
            r0.animState = r10
            com.google.android.material.floatingactionbutton.AnimatorTracker r2 = new com.google.android.material.floatingactionbutton.AnimatorTracker
            r2.<init>()
            r0.changeVisibilityTracker = r2
            com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$ShowStrategy r11 = new com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$ShowStrategy
            r11.<init>(r2)
            r0.showStrategy = r11
            com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$HideStrategy r12 = new com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$HideStrategy
            r12.<init>(r2)
            r0.hideStrategy = r12
            r13 = 1
            r0.isExtended = r13
            r0.isTransforming = r10
            r0.animateShowBeforeLayout = r10
            android.content.Context r14 = r16.getContext()
            com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$ExtendedFloatingActionButtonBehavior r1 = new com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$ExtendedFloatingActionButtonBehavior
            r1.<init>(r14, r7)
            r0.behavior = r1
            int[] r3 = com.google.android.material.C1087R.styleable.ExtendedFloatingActionButton
            int[] r6 = new int[r10]
            r1 = r14
            r2 = r18
            r4 = r19
            r5 = r9
            android.content.res.TypedArray r1 = com.google.android.material.internal.ThemeEnforcement.obtainStyledAttributes(r1, r2, r3, r4, r5, r6)
            int r2 = com.google.android.material.C1087R.styleable.ExtendedFloatingActionButton_showMotionSpec
            com.google.android.material.animation.MotionSpec r2 = com.google.android.material.animation.MotionSpec.createFromAttribute(r14, r1, r2)
            int r3 = com.google.android.material.C1087R.styleable.ExtendedFloatingActionButton_hideMotionSpec
            com.google.android.material.animation.MotionSpec r3 = com.google.android.material.animation.MotionSpec.createFromAttribute(r14, r1, r3)
            int r4 = com.google.android.material.C1087R.styleable.ExtendedFloatingActionButton_extendMotionSpec
            com.google.android.material.animation.MotionSpec r4 = com.google.android.material.animation.MotionSpec.createFromAttribute(r14, r1, r4)
            int r5 = com.google.android.material.C1087R.styleable.ExtendedFloatingActionButton_shrinkMotionSpec
            com.google.android.material.animation.MotionSpec r5 = com.google.android.material.animation.MotionSpec.createFromAttribute(r14, r1, r5)
            int r6 = com.google.android.material.C1087R.styleable.ExtendedFloatingActionButton_collapsedSize
            r15 = -1
            int r6 = r1.getDimensionPixelSize(r6, r15)
            r0.collapsedSize = r6
            int r6 = com.google.android.material.C1087R.styleable.ExtendedFloatingActionButton_extendStrategy
            int r6 = r1.getInt(r6, r13)
            r0.extendStrategyType = r6
            int r15 = androidx.core.view.ViewCompat.getPaddingStart(r16)
            r0.extendedPaddingStart = r15
            int r15 = androidx.core.view.ViewCompat.getPaddingEnd(r16)
            r0.extendedPaddingEnd = r15
            com.google.android.material.floatingactionbutton.AnimatorTracker r15 = new com.google.android.material.floatingactionbutton.AnimatorTracker
            r15.<init>()
            com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$ChangeSizeStrategy r10 = new com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$ChangeSizeStrategy
            com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$Size r6 = r0.getSizeFromExtendStrategyType(r6)
            r10.<init>(r15, r6, r13)
            r0.extendStrategy = r10
            com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$ChangeSizeStrategy r6 = new com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$ChangeSizeStrategy
            com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$1 r13 = new com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$1
            r13.<init>()
            r7 = 0
            r6.<init>(r15, r13, r7)
            r0.shrinkStrategy = r6
            r11.setMotionSpec(r2)
            r12.setMotionSpec(r3)
            r10.setMotionSpec(r4)
            r6.setMotionSpec(r5)
            r1.recycle()
            com.google.android.material.shape.CornerSize r6 = com.google.android.material.shape.ShapeAppearanceModel.PILL
            r7 = r18
            com.google.android.material.shape.ShapeAppearanceModel$Builder r6 = com.google.android.material.shape.ShapeAppearanceModel.builder((android.content.Context) r14, (android.util.AttributeSet) r7, (int) r8, (int) r9, (com.google.android.material.shape.CornerSize) r6)
            com.google.android.material.shape.ShapeAppearanceModel r6 = r6.build()
            r0.setShapeAppearanceModel(r6)
            r16.saveOriginalTextCsl()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    private Size getSizeFromExtendStrategyType(int extendStrategyType2) {
        final Size wrapContentSize = new Size() {
            public int getWidth() {
                return (ExtendedFloatingActionButton.this.getMeasuredWidth() - (ExtendedFloatingActionButton.this.getCollapsedPadding() * 2)) + ExtendedFloatingActionButton.this.extendedPaddingStart + ExtendedFloatingActionButton.this.extendedPaddingEnd;
            }

            public int getHeight() {
                return ExtendedFloatingActionButton.this.getMeasuredHeight();
            }

            public int getPaddingStart() {
                return ExtendedFloatingActionButton.this.extendedPaddingStart;
            }

            public int getPaddingEnd() {
                return ExtendedFloatingActionButton.this.extendedPaddingEnd;
            }

            public ViewGroup.LayoutParams getLayoutParams() {
                return new ViewGroup.LayoutParams(-2, -2);
            }
        };
        final Size matchParentSize = new Size() {
            public int getWidth() {
                ViewGroup.MarginLayoutParams layoutParams;
                int margins = 0;
                if (!(ExtendedFloatingActionButton.this.getParent() instanceof View)) {
                    return wrapContentSize.getWidth();
                }
                View parent = (View) ExtendedFloatingActionButton.this.getParent();
                ViewGroup.LayoutParams parentLayoutParams = parent.getLayoutParams();
                if (parentLayoutParams != null && parentLayoutParams.width == -2) {
                    return wrapContentSize.getWidth();
                }
                int padding = 0 + parent.getPaddingLeft() + parent.getPaddingRight();
                if ((ExtendedFloatingActionButton.this.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) && (layoutParams = (ViewGroup.MarginLayoutParams) ExtendedFloatingActionButton.this.getLayoutParams()) != null) {
                    margins = 0 + layoutParams.leftMargin + layoutParams.rightMargin;
                }
                return (parent.getWidth() - margins) - padding;
            }

            public int getHeight() {
                ViewGroup.MarginLayoutParams layoutParams;
                if (ExtendedFloatingActionButton.this.originalHeight == -1) {
                    if (!(ExtendedFloatingActionButton.this.getParent() instanceof View)) {
                        return wrapContentSize.getHeight();
                    }
                    int margins = 0;
                    View parent = (View) ExtendedFloatingActionButton.this.getParent();
                    ViewGroup.LayoutParams parentLayoutParams = parent.getLayoutParams();
                    if (parentLayoutParams != null && parentLayoutParams.height == -2) {
                        return wrapContentSize.getHeight();
                    }
                    int padding = 0 + parent.getPaddingTop() + parent.getPaddingBottom();
                    if ((ExtendedFloatingActionButton.this.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) && (layoutParams = (ViewGroup.MarginLayoutParams) ExtendedFloatingActionButton.this.getLayoutParams()) != null) {
                        margins = 0 + layoutParams.topMargin + layoutParams.bottomMargin;
                    }
                    return (parent.getHeight() - margins) - padding;
                } else if (ExtendedFloatingActionButton.this.originalHeight == 0 || ExtendedFloatingActionButton.this.originalHeight == -2) {
                    return wrapContentSize.getHeight();
                } else {
                    return ExtendedFloatingActionButton.this.originalHeight;
                }
            }

            public int getPaddingStart() {
                return ExtendedFloatingActionButton.this.extendedPaddingStart;
            }

            public int getPaddingEnd() {
                return ExtendedFloatingActionButton.this.extendedPaddingEnd;
            }

            public ViewGroup.LayoutParams getLayoutParams() {
                return new ViewGroup.LayoutParams(-1, ExtendedFloatingActionButton.this.originalHeight == 0 ? -2 : ExtendedFloatingActionButton.this.originalHeight);
            }
        };
        Size autoSize = new Size() {
            public int getWidth() {
                if (ExtendedFloatingActionButton.this.originalWidth == -1) {
                    return matchParentSize.getWidth();
                }
                if (ExtendedFloatingActionButton.this.originalWidth == 0 || ExtendedFloatingActionButton.this.originalWidth == -2) {
                    return wrapContentSize.getWidth();
                }
                return ExtendedFloatingActionButton.this.originalWidth;
            }

            public int getHeight() {
                if (ExtendedFloatingActionButton.this.originalHeight == -1) {
                    return matchParentSize.getHeight();
                }
                if (ExtendedFloatingActionButton.this.originalHeight == 0 || ExtendedFloatingActionButton.this.originalHeight == -2) {
                    return wrapContentSize.getHeight();
                }
                return ExtendedFloatingActionButton.this.originalHeight;
            }

            public int getPaddingStart() {
                return ExtendedFloatingActionButton.this.extendedPaddingStart;
            }

            public int getPaddingEnd() {
                return ExtendedFloatingActionButton.this.extendedPaddingEnd;
            }

            public ViewGroup.LayoutParams getLayoutParams() {
                int i = -2;
                int access$300 = ExtendedFloatingActionButton.this.originalWidth == 0 ? -2 : ExtendedFloatingActionButton.this.originalWidth;
                if (ExtendedFloatingActionButton.this.originalHeight != 0) {
                    i = ExtendedFloatingActionButton.this.originalHeight;
                }
                return new ViewGroup.LayoutParams(access$300, i);
            }
        };
        switch (extendStrategyType2) {
            case 1:
                return wrapContentSize;
            case 2:
                return matchParentSize;
            default:
                return autoSize;
        }
    }

    public void setTextColor(int color) {
        super.setTextColor(color);
        saveOriginalTextCsl();
    }

    public void setTextColor(ColorStateList colors) {
        super.setTextColor(colors);
        saveOriginalTextCsl();
    }

    private void saveOriginalTextCsl() {
        this.originalTextCsl = getTextColors();
    }

    /* access modifiers changed from: protected */
    public void silentlyUpdateTextColor(ColorStateList csl) {
        super.setTextColor(csl);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.isExtended && TextUtils.isEmpty(getText()) && getIcon() != null) {
            this.isExtended = false;
            this.shrinkStrategy.performNow();
        }
    }

    public CoordinatorLayout.Behavior<ExtendedFloatingActionButton> getBehavior() {
        return this.behavior;
    }

    public void setExtended(boolean extended) {
        if (this.isExtended != extended) {
            MotionStrategy motionStrategy = extended ? this.extendStrategy : this.shrinkStrategy;
            if (!motionStrategy.shouldCancel()) {
                motionStrategy.performNow();
            }
        }
    }

    public final boolean isExtended() {
        return this.isExtended;
    }

    public void setAnimateShowBeforeLayout(boolean animateShowBeforeLayout2) {
        this.animateShowBeforeLayout = animateShowBeforeLayout2;
    }

    public void setPaddingRelative(int start, int top, int end, int bottom) {
        super.setPaddingRelative(start, top, end, bottom);
        if (this.isExtended && !this.isTransforming) {
            this.extendedPaddingStart = start;
            this.extendedPaddingEnd = end;
        }
    }

    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        if (this.isExtended && !this.isTransforming) {
            this.extendedPaddingStart = ViewCompat.getPaddingStart(this);
            this.extendedPaddingEnd = ViewCompat.getPaddingEnd(this);
        }
    }

    public void addOnShowAnimationListener(Animator.AnimatorListener listener) {
        this.showStrategy.addAnimationListener(listener);
    }

    public void removeOnShowAnimationListener(Animator.AnimatorListener listener) {
        this.showStrategy.removeAnimationListener(listener);
    }

    public void addOnHideAnimationListener(Animator.AnimatorListener listener) {
        this.hideStrategy.addAnimationListener(listener);
    }

    public void removeOnHideAnimationListener(Animator.AnimatorListener listener) {
        this.hideStrategy.removeAnimationListener(listener);
    }

    public void addOnShrinkAnimationListener(Animator.AnimatorListener listener) {
        this.shrinkStrategy.addAnimationListener(listener);
    }

    public void removeOnShrinkAnimationListener(Animator.AnimatorListener listener) {
        this.shrinkStrategy.removeAnimationListener(listener);
    }

    public void addOnExtendAnimationListener(Animator.AnimatorListener listener) {
        this.extendStrategy.addAnimationListener(listener);
    }

    public void removeOnExtendAnimationListener(Animator.AnimatorListener listener) {
        this.extendStrategy.removeAnimationListener(listener);
    }

    public void hide() {
        performMotion(1, (OnChangedCallback) null);
    }

    public void hide(OnChangedCallback callback) {
        performMotion(1, callback);
    }

    public void show() {
        performMotion(0, (OnChangedCallback) null);
    }

    public void show(OnChangedCallback callback) {
        performMotion(0, callback);
    }

    public void extend() {
        performMotion(3, (OnChangedCallback) null);
    }

    public void extend(OnChangedCallback callback) {
        performMotion(3, callback);
    }

    public void shrink() {
        performMotion(2, (OnChangedCallback) null);
    }

    public void shrink(OnChangedCallback callback) {
        performMotion(2, callback);
    }

    public MotionSpec getShowMotionSpec() {
        return this.showStrategy.getMotionSpec();
    }

    public void setShowMotionSpec(MotionSpec spec) {
        this.showStrategy.setMotionSpec(spec);
    }

    public void setShowMotionSpecResource(int id) {
        setShowMotionSpec(MotionSpec.createFromResource(getContext(), id));
    }

    public MotionSpec getHideMotionSpec() {
        return this.hideStrategy.getMotionSpec();
    }

    public void setHideMotionSpec(MotionSpec spec) {
        this.hideStrategy.setMotionSpec(spec);
    }

    public void setHideMotionSpecResource(int id) {
        setHideMotionSpec(MotionSpec.createFromResource(getContext(), id));
    }

    public MotionSpec getExtendMotionSpec() {
        return this.extendStrategy.getMotionSpec();
    }

    public void setExtendMotionSpec(MotionSpec spec) {
        this.extendStrategy.setMotionSpec(spec);
    }

    public void setExtendMotionSpecResource(int id) {
        setExtendMotionSpec(MotionSpec.createFromResource(getContext(), id));
    }

    public MotionSpec getShrinkMotionSpec() {
        return this.shrinkStrategy.getMotionSpec();
    }

    public void setShrinkMotionSpec(MotionSpec spec) {
        this.shrinkStrategy.setMotionSpec(spec);
    }

    public void setShrinkMotionSpecResource(int id) {
        setShrinkMotionSpec(MotionSpec.createFromResource(getContext(), id));
    }

    /* access modifiers changed from: private */
    public void performMotion(int strategyType, final OnChangedCallback callback) {
        final MotionStrategy strategy;
        switch (strategyType) {
            case 0:
                strategy = this.showStrategy;
                break;
            case 1:
                strategy = this.hideStrategy;
                break;
            case 2:
                strategy = this.shrinkStrategy;
                break;
            case 3:
                strategy = this.extendStrategy;
                break;
            default:
                throw new IllegalStateException("Unknown strategy type: " + strategyType);
        }
        if (!strategy.shouldCancel()) {
            if (!shouldAnimateVisibilityChange()) {
                strategy.performNow();
                strategy.onChange(callback);
                return;
            }
            if (strategyType == 2) {
                ViewGroup.LayoutParams lp = getLayoutParams();
                if (lp != null) {
                    this.originalWidth = lp.width;
                    this.originalHeight = lp.height;
                } else {
                    this.originalWidth = getWidth();
                    this.originalHeight = getHeight();
                }
            }
            measure(0, 0);
            Animator animator = strategy.createAnimator();
            animator.addListener(new AnimatorListenerAdapter() {
                private boolean cancelled;

                public void onAnimationStart(Animator animation) {
                    strategy.onAnimationStart(animation);
                    this.cancelled = false;
                }

                public void onAnimationCancel(Animator animation) {
                    this.cancelled = true;
                    strategy.onAnimationCancel();
                }

                public void onAnimationEnd(Animator animation) {
                    strategy.onAnimationEnd();
                    if (!this.cancelled) {
                        strategy.onChange(callback);
                    }
                }
            });
            for (Animator.AnimatorListener l : strategy.getListeners()) {
                animator.addListener(l);
            }
            animator.start();
        }
    }

    /* access modifiers changed from: private */
    public boolean isOrWillBeShown() {
        if (getVisibility() != 0) {
            if (this.animState == 2) {
                return true;
            }
            return false;
        } else if (this.animState != 1) {
            return true;
        } else {
            return false;
        }
    }

    /* access modifiers changed from: private */
    public boolean isOrWillBeHidden() {
        if (getVisibility() == 0) {
            if (this.animState == 1) {
                return true;
            }
            return false;
        } else if (this.animState != 2) {
            return true;
        } else {
            return false;
        }
    }

    private boolean shouldAnimateVisibilityChange() {
        return (ViewCompat.isLaidOut(this) || (!isOrWillBeShown() && this.animateShowBeforeLayout)) && !isInEditMode();
    }

    /* access modifiers changed from: package-private */
    public int getCollapsedSize() {
        int i = this.collapsedSize;
        if (i < 0) {
            return (Math.min(ViewCompat.getPaddingStart(this), ViewCompat.getPaddingEnd(this)) * 2) + getIconSize();
        }
        return i;
    }

    /* access modifiers changed from: package-private */
    public int getCollapsedPadding() {
        return (getCollapsedSize() - getIconSize()) / 2;
    }

    protected static class ExtendedFloatingActionButtonBehavior<T extends ExtendedFloatingActionButton> extends CoordinatorLayout.Behavior<T> {
        private static final boolean AUTO_HIDE_DEFAULT = false;
        private static final boolean AUTO_SHRINK_DEFAULT = true;
        private boolean autoHideEnabled;
        private boolean autoShrinkEnabled;
        private OnChangedCallback internalAutoHideCallback;
        private OnChangedCallback internalAutoShrinkCallback;
        private Rect tmpRect;

        public ExtendedFloatingActionButtonBehavior() {
            this.autoHideEnabled = false;
            this.autoShrinkEnabled = AUTO_SHRINK_DEFAULT;
        }

        public ExtendedFloatingActionButtonBehavior(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray a = context.obtainStyledAttributes(attrs, C1087R.styleable.ExtendedFloatingActionButton_Behavior_Layout);
            this.autoHideEnabled = a.getBoolean(C1087R.styleable.ExtendedFloatingActionButton_Behavior_Layout_behavior_autoHide, false);
            this.autoShrinkEnabled = a.getBoolean(C1087R.styleable.ExtendedFloatingActionButton_Behavior_Layout_behavior_autoShrink, AUTO_SHRINK_DEFAULT);
            a.recycle();
        }

        public void setAutoHideEnabled(boolean autoHide) {
            this.autoHideEnabled = autoHide;
        }

        public boolean isAutoHideEnabled() {
            return this.autoHideEnabled;
        }

        public void setAutoShrinkEnabled(boolean autoShrink) {
            this.autoShrinkEnabled = autoShrink;
        }

        public boolean isAutoShrinkEnabled() {
            return this.autoShrinkEnabled;
        }

        public boolean getInsetDodgeRect(CoordinatorLayout parent, ExtendedFloatingActionButton child, Rect rect) {
            return super.getInsetDodgeRect(parent, child, rect);
        }

        public void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams lp) {
            if (lp.dodgeInsetEdges == 0) {
                lp.dodgeInsetEdges = 80;
            }
        }

        public boolean onDependentViewChanged(CoordinatorLayout parent, ExtendedFloatingActionButton child, View dependency) {
            if (dependency instanceof AppBarLayout) {
                updateFabVisibilityForAppBarLayout(parent, (AppBarLayout) dependency, child);
                return false;
            } else if (!isBottomSheet(dependency)) {
                return false;
            } else {
                updateFabVisibilityForBottomSheet(dependency, child);
                return false;
            }
        }

        private static boolean isBottomSheet(View view) {
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (lp instanceof CoordinatorLayout.LayoutParams) {
                return ((CoordinatorLayout.LayoutParams) lp).getBehavior() instanceof BottomSheetBehavior;
            }
            return false;
        }

        /* access modifiers changed from: package-private */
        public void setInternalAutoHideCallback(OnChangedCallback callback) {
            this.internalAutoHideCallback = callback;
        }

        /* access modifiers changed from: package-private */
        public void setInternalAutoShrinkCallback(OnChangedCallback callback) {
            this.internalAutoShrinkCallback = callback;
        }

        private boolean shouldUpdateVisibility(View dependency, ExtendedFloatingActionButton child) {
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            if ((this.autoHideEnabled || this.autoShrinkEnabled) && lp.getAnchorId() == dependency.getId()) {
                return AUTO_SHRINK_DEFAULT;
            }
            return false;
        }

        private boolean updateFabVisibilityForAppBarLayout(CoordinatorLayout parent, AppBarLayout appBarLayout, ExtendedFloatingActionButton child) {
            if (!shouldUpdateVisibility(appBarLayout, child)) {
                return false;
            }
            if (this.tmpRect == null) {
                this.tmpRect = new Rect();
            }
            Rect rect = this.tmpRect;
            DescendantOffsetUtils.getDescendantRect(parent, appBarLayout, rect);
            if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
                shrinkOrHide(child);
                return AUTO_SHRINK_DEFAULT;
            }
            extendOrShow(child);
            return AUTO_SHRINK_DEFAULT;
        }

        private boolean updateFabVisibilityForBottomSheet(View bottomSheet, ExtendedFloatingActionButton child) {
            if (!shouldUpdateVisibility(bottomSheet, child)) {
                return false;
            }
            if (bottomSheet.getTop() < (child.getHeight() / 2) + ((CoordinatorLayout.LayoutParams) child.getLayoutParams()).topMargin) {
                shrinkOrHide(child);
                return AUTO_SHRINK_DEFAULT;
            }
            extendOrShow(child);
            return AUTO_SHRINK_DEFAULT;
        }

        /* access modifiers changed from: protected */
        public void shrinkOrHide(ExtendedFloatingActionButton fab) {
            OnChangedCallback callback;
            boolean z = this.autoShrinkEnabled;
            if (z) {
                callback = this.internalAutoShrinkCallback;
            } else {
                callback = this.internalAutoHideCallback;
            }
            fab.performMotion(z ? 2 : 1, callback);
        }

        /* access modifiers changed from: protected */
        public void extendOrShow(ExtendedFloatingActionButton fab) {
            OnChangedCallback callback;
            boolean z = this.autoShrinkEnabled;
            if (z) {
                callback = this.internalAutoShrinkCallback;
            } else {
                callback = this.internalAutoHideCallback;
            }
            fab.performMotion(z ? 3 : 0, callback);
        }

        public boolean onLayoutChild(CoordinatorLayout parent, ExtendedFloatingActionButton child, int layoutDirection) {
            List<View> dependencies = parent.getDependencies(child);
            int count = dependencies.size();
            for (int i = 0; i < count; i++) {
                View dependency = dependencies.get(i);
                if (!(dependency instanceof AppBarLayout)) {
                    if (isBottomSheet(dependency) && updateFabVisibilityForBottomSheet(dependency, child)) {
                        break;
                    }
                } else if (updateFabVisibilityForAppBarLayout(parent, (AppBarLayout) dependency, child)) {
                    break;
                }
            }
            parent.onLayoutChild(child, layoutDirection);
            return AUTO_SHRINK_DEFAULT;
        }
    }

    class ChangeSizeStrategy extends BaseMotionStrategy {
        private final boolean extending;
        private final Size size;

        ChangeSizeStrategy(AnimatorTracker animatorTracker, Size size2, boolean extending2) {
            super(ExtendedFloatingActionButton.this, animatorTracker);
            this.size = size2;
            this.extending = extending2;
        }

        public void performNow() {
            boolean unused = ExtendedFloatingActionButton.this.isExtended = this.extending;
            ViewGroup.LayoutParams layoutParams = ExtendedFloatingActionButton.this.getLayoutParams();
            if (layoutParams != null) {
                if (!this.extending) {
                    int unused2 = ExtendedFloatingActionButton.this.originalWidth = layoutParams.width;
                    int unused3 = ExtendedFloatingActionButton.this.originalHeight = layoutParams.height;
                }
                layoutParams.width = this.size.getLayoutParams().width;
                layoutParams.height = this.size.getLayoutParams().height;
                ViewCompat.setPaddingRelative(ExtendedFloatingActionButton.this, this.size.getPaddingStart(), ExtendedFloatingActionButton.this.getPaddingTop(), this.size.getPaddingEnd(), ExtendedFloatingActionButton.this.getPaddingBottom());
                ExtendedFloatingActionButton.this.requestLayout();
            }
        }

        public void onChange(OnChangedCallback callback) {
            if (callback != null) {
                if (this.extending) {
                    callback.onExtended(ExtendedFloatingActionButton.this);
                } else {
                    callback.onShrunken(ExtendedFloatingActionButton.this);
                }
            }
        }

        public int getDefaultMotionSpecResource() {
            if (this.extending) {
                return C1087R.animator.mtrl_extended_fab_change_size_expand_motion_spec;
            }
            return C1087R.animator.mtrl_extended_fab_change_size_collapse_motion_spec;
        }

        public AnimatorSet createAnimator() {
            MotionSpec spec = getCurrentMotionSpec();
            if (spec.hasPropertyValues("width")) {
                PropertyValuesHolder[] widthValues = spec.getPropertyValues("width");
                widthValues[0].setFloatValues(new float[]{(float) ExtendedFloatingActionButton.this.getWidth(), (float) this.size.getWidth()});
                spec.setPropertyValues("width", widthValues);
            }
            if (spec.hasPropertyValues("height")) {
                PropertyValuesHolder[] heightValues = spec.getPropertyValues("height");
                heightValues[0].setFloatValues(new float[]{(float) ExtendedFloatingActionButton.this.getHeight(), (float) this.size.getHeight()});
                spec.setPropertyValues("height", heightValues);
            }
            if (spec.hasPropertyValues("paddingStart")) {
                PropertyValuesHolder[] paddingValues = spec.getPropertyValues("paddingStart");
                paddingValues[0].setFloatValues(new float[]{(float) ViewCompat.getPaddingStart(ExtendedFloatingActionButton.this), (float) this.size.getPaddingStart()});
                spec.setPropertyValues("paddingStart", paddingValues);
            }
            if (spec.hasPropertyValues("paddingEnd")) {
                PropertyValuesHolder[] paddingValues2 = spec.getPropertyValues("paddingEnd");
                paddingValues2[0].setFloatValues(new float[]{(float) ViewCompat.getPaddingEnd(ExtendedFloatingActionButton.this), (float) this.size.getPaddingEnd()});
                spec.setPropertyValues("paddingEnd", paddingValues2);
            }
            if (spec.hasPropertyValues("labelOpacity")) {
                PropertyValuesHolder[] labelOpacityValues = spec.getPropertyValues("labelOpacity");
                boolean z = this.extending;
                float endValue = 0.0f;
                float startValue = z ? 0.0f : 1.0f;
                if (z) {
                    endValue = 1.0f;
                }
                labelOpacityValues[0].setFloatValues(new float[]{startValue, endValue});
                spec.setPropertyValues("labelOpacity", labelOpacityValues);
            }
            return super.createAnimator(spec);
        }

        public void onAnimationStart(Animator animator) {
            super.onAnimationStart(animator);
            boolean unused = ExtendedFloatingActionButton.this.isExtended = this.extending;
            boolean unused2 = ExtendedFloatingActionButton.this.isTransforming = true;
            ExtendedFloatingActionButton.this.setHorizontallyScrolling(true);
        }

        public void onAnimationEnd() {
            super.onAnimationEnd();
            boolean unused = ExtendedFloatingActionButton.this.isTransforming = false;
            ExtendedFloatingActionButton.this.setHorizontallyScrolling(false);
            ViewGroup.LayoutParams layoutParams = ExtendedFloatingActionButton.this.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.width = this.size.getLayoutParams().width;
                layoutParams.height = this.size.getLayoutParams().height;
            }
        }

        public boolean shouldCancel() {
            return this.extending == ExtendedFloatingActionButton.this.isExtended || ExtendedFloatingActionButton.this.getIcon() == null || TextUtils.isEmpty(ExtendedFloatingActionButton.this.getText());
        }
    }

    class ShowStrategy extends BaseMotionStrategy {
        public ShowStrategy(AnimatorTracker animatorTracker) {
            super(ExtendedFloatingActionButton.this, animatorTracker);
        }

        public void performNow() {
            ExtendedFloatingActionButton.this.setVisibility(0);
            ExtendedFloatingActionButton.this.setAlpha(1.0f);
            ExtendedFloatingActionButton.this.setScaleY(1.0f);
            ExtendedFloatingActionButton.this.setScaleX(1.0f);
        }

        public void onChange(OnChangedCallback callback) {
            if (callback != null) {
                callback.onShown(ExtendedFloatingActionButton.this);
            }
        }

        public int getDefaultMotionSpecResource() {
            return C1087R.animator.mtrl_extended_fab_show_motion_spec;
        }

        public void onAnimationStart(Animator animation) {
            super.onAnimationStart(animation);
            ExtendedFloatingActionButton.this.setVisibility(0);
            int unused = ExtendedFloatingActionButton.this.animState = 2;
        }

        public void onAnimationEnd() {
            super.onAnimationEnd();
            int unused = ExtendedFloatingActionButton.this.animState = 0;
        }

        public boolean shouldCancel() {
            return ExtendedFloatingActionButton.this.isOrWillBeShown();
        }
    }

    class HideStrategy extends BaseMotionStrategy {
        private boolean isCancelled;

        public HideStrategy(AnimatorTracker animatorTracker) {
            super(ExtendedFloatingActionButton.this, animatorTracker);
        }

        public void performNow() {
            ExtendedFloatingActionButton.this.setVisibility(8);
        }

        public void onChange(OnChangedCallback callback) {
            if (callback != null) {
                callback.onHidden(ExtendedFloatingActionButton.this);
            }
        }

        public boolean shouldCancel() {
            return ExtendedFloatingActionButton.this.isOrWillBeHidden();
        }

        public int getDefaultMotionSpecResource() {
            return C1087R.animator.mtrl_extended_fab_hide_motion_spec;
        }

        public void onAnimationStart(Animator animator) {
            super.onAnimationStart(animator);
            this.isCancelled = false;
            ExtendedFloatingActionButton.this.setVisibility(0);
            int unused = ExtendedFloatingActionButton.this.animState = 1;
        }

        public void onAnimationCancel() {
            super.onAnimationCancel();
            this.isCancelled = true;
        }

        public void onAnimationEnd() {
            super.onAnimationEnd();
            int unused = ExtendedFloatingActionButton.this.animState = 0;
            if (!this.isCancelled) {
                ExtendedFloatingActionButton.this.setVisibility(8);
            }
        }
    }
}
