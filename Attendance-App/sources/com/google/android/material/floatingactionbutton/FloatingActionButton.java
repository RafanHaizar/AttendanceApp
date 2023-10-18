package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.AppCompatImageHelper;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.TintableBackgroundView;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TintableImageSourceView;
import com.google.android.material.C1087R;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.animation.TransformationCallback;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.expandable.ExpandableTransformationWidget;
import com.google.android.material.expandable.ExpandableWidgetHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButtonImpl;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.VisibilityAwareImageButton;
import com.google.android.material.shadow.ShadowViewDelegate;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.stateful.ExtendableSavedState;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public class FloatingActionButton extends VisibilityAwareImageButton implements TintableBackgroundView, TintableImageSourceView, ExpandableTransformationWidget, Shapeable, CoordinatorLayout.AttachedBehavior {
    private static final int AUTO_MINI_LARGEST_SCREEN_WIDTH = 470;
    private static final int DEF_STYLE_RES = C1087R.C1093style.Widget_Design_FloatingActionButton;
    private static final String EXPANDABLE_WIDGET_HELPER_KEY = "expandableWidgetHelper";
    private static final String LOG_TAG = "FloatingActionButton";
    public static final int NO_CUSTOM_SIZE = 0;
    public static final int SIZE_AUTO = -1;
    public static final int SIZE_MINI = 1;
    public static final int SIZE_NORMAL = 0;
    private ColorStateList backgroundTint;
    private PorterDuff.Mode backgroundTintMode;
    private int borderWidth;
    boolean compatPadding;
    private int customSize;
    private final ExpandableWidgetHelper expandableWidgetHelper;
    private final AppCompatImageHelper imageHelper;
    private PorterDuff.Mode imageMode;
    /* access modifiers changed from: private */
    public int imagePadding;
    private ColorStateList imageTint;
    private FloatingActionButtonImpl impl;
    private int maxImageSize;
    private ColorStateList rippleColor;
    final Rect shadowPadding;
    private int size;
    private final Rect touchArea;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Size {
    }

    public static abstract class OnVisibilityChangedListener {
        public void onShown(FloatingActionButton fab) {
        }

        public void onHidden(FloatingActionButton fab) {
        }
    }

    public FloatingActionButton(Context context) {
        this(context, (AttributeSet) null);
    }

    public FloatingActionButton(Context context, AttributeSet attrs) {
        this(context, attrs, C1087R.attr.floatingActionButtonStyle);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public FloatingActionButton(android.content.Context r17, android.util.AttributeSet r18, int r19) {
        /*
            r16 = this;
            r0 = r16
            r7 = r18
            r8 = r19
            int r9 = DEF_STYLE_RES
            r1 = r17
            android.content.Context r2 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r1, r7, r8, r9)
            r0.<init>(r2, r7, r8)
            android.graphics.Rect r2 = new android.graphics.Rect
            r2.<init>()
            r0.shadowPadding = r2
            android.graphics.Rect r2 = new android.graphics.Rect
            r2.<init>()
            r0.touchArea = r2
            android.content.Context r10 = r16.getContext()
            int[] r3 = com.google.android.material.C1087R.styleable.FloatingActionButton
            r11 = 0
            int[] r6 = new int[r11]
            r1 = r10
            r2 = r18
            r4 = r19
            r5 = r9
            android.content.res.TypedArray r1 = com.google.android.material.internal.ThemeEnforcement.obtainStyledAttributes(r1, r2, r3, r4, r5, r6)
            int r2 = com.google.android.material.C1087R.styleable.FloatingActionButton_backgroundTint
            android.content.res.ColorStateList r2 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r10, (android.content.res.TypedArray) r1, (int) r2)
            r0.backgroundTint = r2
            int r2 = com.google.android.material.C1087R.styleable.FloatingActionButton_backgroundTintMode
            r3 = -1
            int r2 = r1.getInt(r2, r3)
            r4 = 0
            android.graphics.PorterDuff$Mode r2 = com.google.android.material.internal.ViewUtils.parseTintMode(r2, r4)
            r0.backgroundTintMode = r2
            int r2 = com.google.android.material.C1087R.styleable.FloatingActionButton_rippleColor
            android.content.res.ColorStateList r2 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r10, (android.content.res.TypedArray) r1, (int) r2)
            r0.rippleColor = r2
            int r2 = com.google.android.material.C1087R.styleable.FloatingActionButton_fabSize
            int r2 = r1.getInt(r2, r3)
            r0.size = r2
            int r2 = com.google.android.material.C1087R.styleable.FloatingActionButton_fabCustomSize
            int r2 = r1.getDimensionPixelSize(r2, r11)
            r0.customSize = r2
            int r2 = com.google.android.material.C1087R.styleable.FloatingActionButton_borderWidth
            int r2 = r1.getDimensionPixelSize(r2, r11)
            r0.borderWidth = r2
            int r2 = com.google.android.material.C1087R.styleable.FloatingActionButton_elevation
            r3 = 0
            float r2 = r1.getDimension(r2, r3)
            int r4 = com.google.android.material.C1087R.styleable.FloatingActionButton_hoveredFocusedTranslationZ
            float r4 = r1.getDimension(r4, r3)
            int r5 = com.google.android.material.C1087R.styleable.FloatingActionButton_pressedTranslationZ
            float r3 = r1.getDimension(r5, r3)
            int r5 = com.google.android.material.C1087R.styleable.FloatingActionButton_useCompatPadding
            boolean r5 = r1.getBoolean(r5, r11)
            r0.compatPadding = r5
            android.content.res.Resources r5 = r16.getResources()
            int r6 = com.google.android.material.C1087R.dimen.mtrl_fab_min_touch_target
            int r5 = r5.getDimensionPixelSize(r6)
            int r6 = com.google.android.material.C1087R.styleable.FloatingActionButton_maxImageSize
            int r6 = r1.getDimensionPixelSize(r6, r11)
            r0.setMaxImageSize(r6)
            int r6 = com.google.android.material.C1087R.styleable.FloatingActionButton_showMotionSpec
            com.google.android.material.animation.MotionSpec r6 = com.google.android.material.animation.MotionSpec.createFromAttribute(r10, r1, r6)
            int r12 = com.google.android.material.C1087R.styleable.FloatingActionButton_hideMotionSpec
            com.google.android.material.animation.MotionSpec r12 = com.google.android.material.animation.MotionSpec.createFromAttribute(r10, r1, r12)
            com.google.android.material.shape.CornerSize r13 = com.google.android.material.shape.ShapeAppearanceModel.PILL
            com.google.android.material.shape.ShapeAppearanceModel$Builder r9 = com.google.android.material.shape.ShapeAppearanceModel.builder((android.content.Context) r10, (android.util.AttributeSet) r7, (int) r8, (int) r9, (com.google.android.material.shape.CornerSize) r13)
            com.google.android.material.shape.ShapeAppearanceModel r9 = r9.build()
            int r13 = com.google.android.material.C1087R.styleable.FloatingActionButton_ensureMinTouchTargetSize
            boolean r11 = r1.getBoolean(r13, r11)
            int r13 = com.google.android.material.C1087R.styleable.FloatingActionButton_android_enabled
            r14 = 1
            boolean r13 = r1.getBoolean(r13, r14)
            r0.setEnabled(r13)
            r1.recycle()
            androidx.appcompat.widget.AppCompatImageHelper r13 = new androidx.appcompat.widget.AppCompatImageHelper
            r13.<init>(r0)
            r0.imageHelper = r13
            r13.loadFromAttributes(r7, r8)
            com.google.android.material.expandable.ExpandableWidgetHelper r13 = new com.google.android.material.expandable.ExpandableWidgetHelper
            r13.<init>(r0)
            r0.expandableWidgetHelper = r13
            com.google.android.material.floatingactionbutton.FloatingActionButtonImpl r13 = r16.getImpl()
            r13.setShapeAppearance(r9)
            com.google.android.material.floatingactionbutton.FloatingActionButtonImpl r13 = r16.getImpl()
            android.content.res.ColorStateList r14 = r0.backgroundTint
            android.graphics.PorterDuff$Mode r15 = r0.backgroundTintMode
            r17 = r1
            android.content.res.ColorStateList r1 = r0.rippleColor
            int r7 = r0.borderWidth
            r13.initializeBackgroundDrawable(r14, r15, r1, r7)
            com.google.android.material.floatingactionbutton.FloatingActionButtonImpl r1 = r16.getImpl()
            r1.setMinTouchTargetSize(r5)
            com.google.android.material.floatingactionbutton.FloatingActionButtonImpl r1 = r16.getImpl()
            r1.setElevation(r2)
            com.google.android.material.floatingactionbutton.FloatingActionButtonImpl r1 = r16.getImpl()
            r1.setHoveredFocusedTranslationZ(r4)
            com.google.android.material.floatingactionbutton.FloatingActionButtonImpl r1 = r16.getImpl()
            r1.setPressedTranslationZ(r3)
            com.google.android.material.floatingactionbutton.FloatingActionButtonImpl r1 = r16.getImpl()
            r1.setShowMotionSpec(r6)
            com.google.android.material.floatingactionbutton.FloatingActionButtonImpl r1 = r16.getImpl()
            r1.setHideMotionSpec(r12)
            com.google.android.material.floatingactionbutton.FloatingActionButtonImpl r1 = r16.getImpl()
            r1.setEnsureMinTouchTargetSize(r11)
            android.widget.ImageView$ScaleType r1 = android.widget.ImageView.ScaleType.MATRIX
            r0.setScaleType(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.floatingactionbutton.FloatingActionButton.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int preferredSize = getSizeDimension();
        this.imagePadding = (preferredSize - this.maxImageSize) / 2;
        getImpl().updatePadding();
        int d = Math.min(View.resolveSize(preferredSize, widthMeasureSpec), View.resolveSize(preferredSize, heightMeasureSpec));
        setMeasuredDimension(this.shadowPadding.left + d + this.shadowPadding.right, this.shadowPadding.top + d + this.shadowPadding.bottom);
    }

    @Deprecated
    public int getRippleColor() {
        ColorStateList colorStateList = this.rippleColor;
        if (colorStateList != null) {
            return colorStateList.getDefaultColor();
        }
        return 0;
    }

    public ColorStateList getRippleColorStateList() {
        return this.rippleColor;
    }

    public void setRippleColor(int color) {
        setRippleColor(ColorStateList.valueOf(color));
    }

    public void setRippleColor(ColorStateList color) {
        if (this.rippleColor != color) {
            this.rippleColor = color;
            getImpl().setRippleColor(this.rippleColor);
        }
    }

    public CoordinatorLayout.Behavior<FloatingActionButton> getBehavior() {
        return new Behavior();
    }

    public ColorStateList getBackgroundTintList() {
        return this.backgroundTint;
    }

    public void setBackgroundTintList(ColorStateList tint) {
        if (this.backgroundTint != tint) {
            this.backgroundTint = tint;
            getImpl().setBackgroundTintList(tint);
        }
    }

    public PorterDuff.Mode getBackgroundTintMode() {
        return this.backgroundTintMode;
    }

    public void setBackgroundTintMode(PorterDuff.Mode tintMode) {
        if (this.backgroundTintMode != tintMode) {
            this.backgroundTintMode = tintMode;
            getImpl().setBackgroundTintMode(tintMode);
        }
    }

    public void setSupportBackgroundTintList(ColorStateList tint) {
        setBackgroundTintList(tint);
    }

    public ColorStateList getSupportBackgroundTintList() {
        return getBackgroundTintList();
    }

    public void setSupportBackgroundTintMode(PorterDuff.Mode tintMode) {
        setBackgroundTintMode(tintMode);
    }

    public PorterDuff.Mode getSupportBackgroundTintMode() {
        return getBackgroundTintMode();
    }

    public void setSupportImageTintList(ColorStateList tint) {
        if (this.imageTint != tint) {
            this.imageTint = tint;
            onApplySupportImageTint();
        }
    }

    public ColorStateList getSupportImageTintList() {
        return this.imageTint;
    }

    public void setSupportImageTintMode(PorterDuff.Mode tintMode) {
        if (this.imageMode != tintMode) {
            this.imageMode = tintMode;
            onApplySupportImageTint();
        }
    }

    public PorterDuff.Mode getSupportImageTintMode() {
        return this.imageMode;
    }

    private void onApplySupportImageTint() {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            ColorStateList colorStateList = this.imageTint;
            if (colorStateList == null) {
                DrawableCompat.clearColorFilter(drawable);
                return;
            }
            int color = colorStateList.getColorForState(getDrawableState(), 0);
            PorterDuff.Mode mode = this.imageMode;
            if (mode == null) {
                mode = PorterDuff.Mode.SRC_IN;
            }
            drawable.mutate().setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(color, mode));
        }
    }

    public void setBackgroundDrawable(Drawable background) {
        Log.i(LOG_TAG, "Setting a custom background is not supported.");
    }

    public void setBackgroundResource(int resid) {
        Log.i(LOG_TAG, "Setting a custom background is not supported.");
    }

    public void setBackgroundColor(int color) {
        Log.i(LOG_TAG, "Setting a custom background is not supported.");
    }

    public void setImageResource(int resId) {
        this.imageHelper.setImageResource(resId);
        onApplySupportImageTint();
    }

    public void setImageDrawable(Drawable drawable) {
        if (getDrawable() != drawable) {
            super.setImageDrawable(drawable);
            getImpl().updateImageMatrixScale();
            if (this.imageTint != null) {
                onApplySupportImageTint();
            }
        }
    }

    public void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearance) {
        getImpl().setShapeAppearance(shapeAppearance);
    }

    public ShapeAppearanceModel getShapeAppearanceModel() {
        return (ShapeAppearanceModel) Preconditions.checkNotNull(getImpl().getShapeAppearance());
    }

    public boolean shouldEnsureMinTouchTargetSize() {
        return getImpl().getEnsureMinTouchTargetSize();
    }

    public void setEnsureMinTouchTargetSize(boolean flag) {
        if (flag != getImpl().getEnsureMinTouchTargetSize()) {
            getImpl().setEnsureMinTouchTargetSize(flag);
            requestLayout();
        }
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
    }

    public void setMaxImageSize(int imageSize) {
        this.maxImageSize = imageSize;
        getImpl().setMaxImageSize(imageSize);
    }

    public void show() {
        show((OnVisibilityChangedListener) null);
    }

    public void show(OnVisibilityChangedListener listener) {
        show(listener, true);
    }

    /* access modifiers changed from: package-private */
    public void show(OnVisibilityChangedListener listener, boolean fromUser) {
        getImpl().show(wrapOnVisibilityChangedListener(listener), fromUser);
    }

    public void addOnShowAnimationListener(Animator.AnimatorListener listener) {
        getImpl().addOnShowAnimationListener(listener);
    }

    public void removeOnShowAnimationListener(Animator.AnimatorListener listener) {
        getImpl().removeOnShowAnimationListener(listener);
    }

    public void hide() {
        hide((OnVisibilityChangedListener) null);
    }

    public void hide(OnVisibilityChangedListener listener) {
        hide(listener, true);
    }

    /* access modifiers changed from: package-private */
    public void hide(OnVisibilityChangedListener listener, boolean fromUser) {
        getImpl().hide(wrapOnVisibilityChangedListener(listener), fromUser);
    }

    public void addOnHideAnimationListener(Animator.AnimatorListener listener) {
        getImpl().addOnHideAnimationListener(listener);
    }

    public void removeOnHideAnimationListener(Animator.AnimatorListener listener) {
        getImpl().removeOnHideAnimationListener(listener);
    }

    public boolean setExpanded(boolean expanded) {
        return this.expandableWidgetHelper.setExpanded(expanded);
    }

    public boolean isExpanded() {
        return this.expandableWidgetHelper.isExpanded();
    }

    public void setExpandedComponentIdHint(int expandedComponentIdHint) {
        this.expandableWidgetHelper.setExpandedComponentIdHint(expandedComponentIdHint);
    }

    public int getExpandedComponentIdHint() {
        return this.expandableWidgetHelper.getExpandedComponentIdHint();
    }

    public void setUseCompatPadding(boolean useCompatPadding) {
        if (this.compatPadding != useCompatPadding) {
            this.compatPadding = useCompatPadding;
            getImpl().onCompatShadowChanged();
        }
    }

    public boolean getUseCompatPadding() {
        return this.compatPadding;
    }

    public void setSize(int size2) {
        this.customSize = 0;
        if (size2 != this.size) {
            this.size = size2;
            requestLayout();
        }
    }

    public int getSize() {
        return this.size;
    }

    private FloatingActionButtonImpl.InternalVisibilityChangedListener wrapOnVisibilityChangedListener(final OnVisibilityChangedListener listener) {
        if (listener == null) {
            return null;
        }
        return new FloatingActionButtonImpl.InternalVisibilityChangedListener() {
            public void onShown() {
                listener.onShown(FloatingActionButton.this);
            }

            public void onHidden() {
                listener.onHidden(FloatingActionButton.this);
            }
        };
    }

    public boolean isOrWillBeHidden() {
        return getImpl().isOrWillBeHidden();
    }

    public boolean isOrWillBeShown() {
        return getImpl().isOrWillBeShown();
    }

    public void setCustomSize(int size2) {
        if (size2 < 0) {
            throw new IllegalArgumentException("Custom size must be non-negative");
        } else if (size2 != this.customSize) {
            this.customSize = size2;
            requestLayout();
        }
    }

    public int getCustomSize() {
        return this.customSize;
    }

    public void clearCustomSize() {
        setCustomSize(0);
    }

    /* access modifiers changed from: package-private */
    public int getSizeDimension() {
        return getSizeDimension(this.size);
    }

    private int getSizeDimension(int size2) {
        int i = this.customSize;
        if (i != 0) {
            return i;
        }
        Resources res = getResources();
        switch (size2) {
            case -1:
                if (Math.max(res.getConfiguration().screenWidthDp, res.getConfiguration().screenHeightDp) < AUTO_MINI_LARGEST_SCREEN_WIDTH) {
                    return getSizeDimension(1);
                }
                return getSizeDimension(0);
            case 1:
                return res.getDimensionPixelSize(C1087R.dimen.design_fab_size_mini);
            default:
                return res.getDimensionPixelSize(C1087R.dimen.design_fab_size_normal);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getImpl().onAttachedToWindow();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getImpl().onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        getImpl().onDrawableStateChanged(getDrawableState());
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        getImpl().jumpDrawableToCurrentState();
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        if (superState == null) {
            superState = new Bundle();
        }
        ExtendableSavedState state = new ExtendableSavedState(superState);
        state.extendableStates.put(EXPANDABLE_WIDGET_HELPER_KEY, this.expandableWidgetHelper.onSaveInstanceState());
        return state;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof ExtendableSavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        ExtendableSavedState ess = (ExtendableSavedState) state;
        super.onRestoreInstanceState(ess.getSuperState());
        this.expandableWidgetHelper.onRestoreInstanceState((Bundle) Preconditions.checkNotNull(ess.extendableStates.get(EXPANDABLE_WIDGET_HELPER_KEY)));
    }

    @Deprecated
    public boolean getContentRect(Rect rect) {
        if (!ViewCompat.isLaidOut(this)) {
            return false;
        }
        rect.set(0, 0, getWidth(), getHeight());
        offsetRectWithShadow(rect);
        return true;
    }

    public void getMeasuredContentRect(Rect rect) {
        rect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
        offsetRectWithShadow(rect);
    }

    private void offsetRectWithShadow(Rect rect) {
        rect.left += this.shadowPadding.left;
        rect.top += this.shadowPadding.top;
        rect.right -= this.shadowPadding.right;
        rect.bottom -= this.shadowPadding.bottom;
    }

    public Drawable getContentBackground() {
        return getImpl().getContentBackground();
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() != 0 || !getContentRect(this.touchArea) || this.touchArea.contains((int) ev.getX(), (int) ev.getY())) {
            return super.onTouchEvent(ev);
        }
        return false;
    }

    public static class Behavior extends BaseBehavior<FloatingActionButton> {
        public /* bridge */ /* synthetic */ boolean getInsetDodgeRect(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, Rect rect) {
            return super.getInsetDodgeRect(coordinatorLayout, floatingActionButton, rect);
        }

        public /* bridge */ /* synthetic */ boolean isAutoHideEnabled() {
            return super.isAutoHideEnabled();
        }

        public /* bridge */ /* synthetic */ void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams layoutParams) {
            super.onAttachedToLayoutParams(layoutParams);
        }

        public /* bridge */ /* synthetic */ boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, View view) {
            return super.onDependentViewChanged(coordinatorLayout, floatingActionButton, view);
        }

        public /* bridge */ /* synthetic */ boolean onLayoutChild(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, int i) {
            return super.onLayoutChild(coordinatorLayout, floatingActionButton, i);
        }

        public /* bridge */ /* synthetic */ void setAutoHideEnabled(boolean z) {
            super.setAutoHideEnabled(z);
        }

        public /* bridge */ /* synthetic */ void setInternalAutoHideListener(OnVisibilityChangedListener onVisibilityChangedListener) {
            super.setInternalAutoHideListener(onVisibilityChangedListener);
        }

        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
    }

    protected static class BaseBehavior<T extends FloatingActionButton> extends CoordinatorLayout.Behavior<T> {
        private static final boolean AUTO_HIDE_DEFAULT = true;
        private boolean autoHideEnabled;
        private OnVisibilityChangedListener internalAutoHideListener;
        private Rect tmpRect;

        public BaseBehavior() {
            this.autoHideEnabled = AUTO_HIDE_DEFAULT;
        }

        public BaseBehavior(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray a = context.obtainStyledAttributes(attrs, C1087R.styleable.FloatingActionButton_Behavior_Layout);
            this.autoHideEnabled = a.getBoolean(C1087R.styleable.FloatingActionButton_Behavior_Layout_behavior_autoHide, AUTO_HIDE_DEFAULT);
            a.recycle();
        }

        public void setAutoHideEnabled(boolean autoHide) {
            this.autoHideEnabled = autoHide;
        }

        public boolean isAutoHideEnabled() {
            return this.autoHideEnabled;
        }

        public void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams lp) {
            if (lp.dodgeInsetEdges == 0) {
                lp.dodgeInsetEdges = 80;
            }
        }

        public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
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

        public void setInternalAutoHideListener(OnVisibilityChangedListener listener) {
            this.internalAutoHideListener = listener;
        }

        private boolean shouldUpdateVisibility(View dependency, FloatingActionButton child) {
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            if (this.autoHideEnabled && lp.getAnchorId() == dependency.getId() && child.getUserSetVisibility() == 0) {
                return AUTO_HIDE_DEFAULT;
            }
            return false;
        }

        private boolean updateFabVisibilityForAppBarLayout(CoordinatorLayout parent, AppBarLayout appBarLayout, FloatingActionButton child) {
            if (!shouldUpdateVisibility(appBarLayout, child)) {
                return false;
            }
            if (this.tmpRect == null) {
                this.tmpRect = new Rect();
            }
            Rect rect = this.tmpRect;
            DescendantOffsetUtils.getDescendantRect(parent, appBarLayout, rect);
            if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
                child.hide(this.internalAutoHideListener, false);
                return AUTO_HIDE_DEFAULT;
            }
            child.show(this.internalAutoHideListener, false);
            return AUTO_HIDE_DEFAULT;
        }

        private boolean updateFabVisibilityForBottomSheet(View bottomSheet, FloatingActionButton child) {
            if (!shouldUpdateVisibility(bottomSheet, child)) {
                return false;
            }
            if (bottomSheet.getTop() < (child.getHeight() / 2) + ((CoordinatorLayout.LayoutParams) child.getLayoutParams()).topMargin) {
                child.hide(this.internalAutoHideListener, false);
                return AUTO_HIDE_DEFAULT;
            }
            child.show(this.internalAutoHideListener, false);
            return AUTO_HIDE_DEFAULT;
        }

        public boolean onLayoutChild(CoordinatorLayout parent, FloatingActionButton child, int layoutDirection) {
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
            offsetIfNeeded(parent, child);
            return AUTO_HIDE_DEFAULT;
        }

        public boolean getInsetDodgeRect(CoordinatorLayout parent, FloatingActionButton child, Rect rect) {
            Rect shadowPadding = child.shadowPadding;
            rect.set(child.getLeft() + shadowPadding.left, child.getTop() + shadowPadding.top, child.getRight() - shadowPadding.right, child.getBottom() - shadowPadding.bottom);
            return AUTO_HIDE_DEFAULT;
        }

        private void offsetIfNeeded(CoordinatorLayout parent, FloatingActionButton fab) {
            Rect padding = fab.shadowPadding;
            if (padding != null && padding.centerX() > 0 && padding.centerY() > 0) {
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
                int offsetTB = 0;
                int offsetLR = 0;
                if (fab.getRight() >= parent.getWidth() - lp.rightMargin) {
                    offsetLR = padding.right;
                } else if (fab.getLeft() <= lp.leftMargin) {
                    offsetLR = -padding.left;
                }
                if (fab.getBottom() >= parent.getHeight() - lp.bottomMargin) {
                    offsetTB = padding.bottom;
                } else if (fab.getTop() <= lp.topMargin) {
                    offsetTB = -padding.top;
                }
                if (offsetTB != 0) {
                    ViewCompat.offsetTopAndBottom(fab, offsetTB);
                }
                if (offsetLR != 0) {
                    ViewCompat.offsetLeftAndRight(fab, offsetLR);
                }
            }
        }
    }

    public void setElevation(float elevation) {
        super.setElevation(elevation);
        getImpl().updateShapeElevation(elevation);
    }

    public float getCompatElevation() {
        return getImpl().getElevation();
    }

    public void setCompatElevation(float elevation) {
        getImpl().setElevation(elevation);
    }

    public void setCompatElevationResource(int id) {
        setCompatElevation(getResources().getDimension(id));
    }

    public float getCompatHoveredFocusedTranslationZ() {
        return getImpl().getHoveredFocusedTranslationZ();
    }

    public void setCompatHoveredFocusedTranslationZ(float translationZ) {
        getImpl().setHoveredFocusedTranslationZ(translationZ);
    }

    public void setCompatHoveredFocusedTranslationZResource(int id) {
        setCompatHoveredFocusedTranslationZ(getResources().getDimension(id));
    }

    public float getCompatPressedTranslationZ() {
        return getImpl().getPressedTranslationZ();
    }

    public void setCompatPressedTranslationZ(float translationZ) {
        getImpl().setPressedTranslationZ(translationZ);
    }

    public void setCompatPressedTranslationZResource(int id) {
        setCompatPressedTranslationZ(getResources().getDimension(id));
    }

    public MotionSpec getShowMotionSpec() {
        return getImpl().getShowMotionSpec();
    }

    public void setShowMotionSpec(MotionSpec spec) {
        getImpl().setShowMotionSpec(spec);
    }

    public void setShowMotionSpecResource(int id) {
        setShowMotionSpec(MotionSpec.createFromResource(getContext(), id));
    }

    public MotionSpec getHideMotionSpec() {
        return getImpl().getHideMotionSpec();
    }

    public void setHideMotionSpec(MotionSpec spec) {
        getImpl().setHideMotionSpec(spec);
    }

    public void setHideMotionSpecResource(int id) {
        setHideMotionSpec(MotionSpec.createFromResource(getContext(), id));
    }

    public void addTransformationCallback(TransformationCallback<? extends FloatingActionButton> listener) {
        getImpl().addTransformationCallback(new TransformationCallbackWrapper(listener));
    }

    public void removeTransformationCallback(TransformationCallback<? extends FloatingActionButton> listener) {
        getImpl().removeTransformationCallback(new TransformationCallbackWrapper(listener));
    }

    class TransformationCallbackWrapper<T extends FloatingActionButton> implements FloatingActionButtonImpl.InternalTransformationCallback {
        private final TransformationCallback<T> listener;

        TransformationCallbackWrapper(TransformationCallback<T> listener2) {
            this.listener = listener2;
        }

        public void onTranslationChanged() {
            this.listener.onTranslationChanged(FloatingActionButton.this);
        }

        public void onScaleChanged() {
            this.listener.onScaleChanged(FloatingActionButton.this);
        }

        public boolean equals(Object obj) {
            return (obj instanceof TransformationCallbackWrapper) && ((TransformationCallbackWrapper) obj).listener.equals(this.listener);
        }

        public int hashCode() {
            return this.listener.hashCode();
        }
    }

    public void setTranslationX(float translationX) {
        super.setTranslationX(translationX);
        getImpl().onTranslationChanged();
    }

    public void setTranslationY(float translationY) {
        super.setTranslationY(translationY);
        getImpl().onTranslationChanged();
    }

    public void setTranslationZ(float translationZ) {
        super.setTranslationZ(translationZ);
        getImpl().onTranslationChanged();
    }

    public void setScaleX(float scaleX) {
        super.setScaleX(scaleX);
        getImpl().onScaleChanged();
    }

    public void setScaleY(float scaleY) {
        super.setScaleY(scaleY);
        getImpl().onScaleChanged();
    }

    public void setShadowPaddingEnabled(boolean shadowPaddingEnabled) {
        getImpl().setShadowPaddingEnabled(shadowPaddingEnabled);
    }

    private FloatingActionButtonImpl getImpl() {
        if (this.impl == null) {
            this.impl = createImpl();
        }
        return this.impl;
    }

    private FloatingActionButtonImpl createImpl() {
        if (Build.VERSION.SDK_INT >= 21) {
            return new FloatingActionButtonImplLollipop(this, new ShadowDelegateImpl());
        }
        return new FloatingActionButtonImpl(this, new ShadowDelegateImpl());
    }

    private class ShadowDelegateImpl implements ShadowViewDelegate {
        ShadowDelegateImpl() {
        }

        public float getRadius() {
            return ((float) FloatingActionButton.this.getSizeDimension()) / 2.0f;
        }

        public void setShadowPadding(int left, int top, int right, int bottom) {
            FloatingActionButton.this.shadowPadding.set(left, top, right, bottom);
            FloatingActionButton floatingActionButton = FloatingActionButton.this;
            floatingActionButton.setPadding(floatingActionButton.imagePadding + left, FloatingActionButton.this.imagePadding + top, FloatingActionButton.this.imagePadding + right, FloatingActionButton.this.imagePadding + bottom);
        }

        public void setBackgroundDrawable(Drawable background) {
            if (background != null) {
                FloatingActionButton.super.setBackgroundDrawable(background);
            }
        }

        public boolean isCompatPaddingEnabled() {
            return FloatingActionButton.this.compatPadding;
        }
    }
}
