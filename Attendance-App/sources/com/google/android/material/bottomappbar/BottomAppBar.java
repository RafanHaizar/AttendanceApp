package com.google.android.material.bottomappbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.C1087R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.TransformationCallback;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BottomAppBar extends Toolbar implements CoordinatorLayout.AttachedBehavior {
    private static final int DEF_STYLE_RES = C1087R.C1093style.Widget_MaterialComponents_BottomAppBar;
    private static final int FAB_ALIGNMENT_ANIM_DURATION_ATTR = C1087R.attr.motionDurationLong2;
    private static final int FAB_ALIGNMENT_ANIM_DURATION_DEFAULT = 300;
    private static final int FAB_ALIGNMENT_ANIM_EASING_ATTR = C1087R.attr.motionEasingEmphasizedInterpolator;
    private static final float FAB_ALIGNMENT_ANIM_EASING_MIDPOINT = 0.2f;
    public static final int FAB_ALIGNMENT_MODE_CENTER = 0;
    public static final int FAB_ALIGNMENT_MODE_END = 1;
    public static final int FAB_ANCHOR_MODE_CRADLE = 1;
    public static final int FAB_ANCHOR_MODE_EMBED = 0;
    public static final int FAB_ANIMATION_MODE_SCALE = 0;
    public static final int FAB_ANIMATION_MODE_SLIDE = 1;
    public static final int MENU_ALIGNMENT_MODE_AUTO = 0;
    public static final int MENU_ALIGNMENT_MODE_START = 1;
    private static final int NO_FAB_END_MARGIN = -1;
    private static final int NO_MENU_RES_ID = 0;
    private int animatingModeChangeCounter;
    private ArrayList<AnimationListener> animationListeners;
    private Behavior behavior;
    /* access modifiers changed from: private */
    public int bottomInset;
    /* access modifiers changed from: private */
    public int fabAlignmentMode;
    private int fabAlignmentModeEndMargin;
    /* access modifiers changed from: private */
    public int fabAnchorMode;
    AnimatorListenerAdapter fabAnimationListener;
    private int fabAnimationMode;
    /* access modifiers changed from: private */
    public boolean fabAttached;
    /* access modifiers changed from: private */
    public final int fabOffsetEndMode;
    TransformationCallback<FloatingActionButton> fabTransformationCallback;
    private boolean hideOnScroll;
    /* access modifiers changed from: private */
    public int leftInset;
    /* access modifiers changed from: private */
    public final MaterialShapeDrawable materialShapeDrawable;
    private int menuAlignmentMode;
    /* access modifiers changed from: private */
    public boolean menuAnimatingWithFabAlignmentMode;
    /* access modifiers changed from: private */
    public Animator menuAnimator;
    /* access modifiers changed from: private */
    public Animator modeAnimator;
    private Integer navigationIconTint;
    /* access modifiers changed from: private */
    public final boolean paddingBottomSystemWindowInsets;
    /* access modifiers changed from: private */
    public final boolean paddingLeftSystemWindowInsets;
    /* access modifiers changed from: private */
    public final boolean paddingRightSystemWindowInsets;
    /* access modifiers changed from: private */
    public int pendingMenuResId;
    /* access modifiers changed from: private */
    public final boolean removeEmbeddedFabElevation;
    /* access modifiers changed from: private */
    public int rightInset;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FabAlignmentMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FabAnchorMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FabAnimationMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MenuAlignmentMode {
    }

    interface AnimationListener {
        void onAnimationEnd(BottomAppBar bottomAppBar);

        void onAnimationStart(BottomAppBar bottomAppBar);
    }

    public BottomAppBar(Context context) {
        this(context, (AttributeSet) null);
    }

    public BottomAppBar(Context context, AttributeSet attrs) {
        this(context, attrs, C1087R.attr.bottomAppBarStyle);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public BottomAppBar(android.content.Context r17, android.util.AttributeSet r18, int r19) {
        /*
            r16 = this;
            r0 = r16
            r7 = r18
            r8 = r19
            int r9 = DEF_STYLE_RES
            r1 = r17
            android.content.Context r2 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r1, r7, r8, r9)
            r0.<init>(r2, r7, r8)
            com.google.android.material.shape.MaterialShapeDrawable r10 = new com.google.android.material.shape.MaterialShapeDrawable
            r10.<init>()
            r0.materialShapeDrawable = r10
            r11 = 0
            r0.animatingModeChangeCounter = r11
            r0.pendingMenuResId = r11
            r0.menuAnimatingWithFabAlignmentMode = r11
            r12 = 1
            r0.fabAttached = r12
            com.google.android.material.bottomappbar.BottomAppBar$1 r2 = new com.google.android.material.bottomappbar.BottomAppBar$1
            r2.<init>()
            r0.fabAnimationListener = r2
            com.google.android.material.bottomappbar.BottomAppBar$2 r2 = new com.google.android.material.bottomappbar.BottomAppBar$2
            r2.<init>()
            r0.fabTransformationCallback = r2
            android.content.Context r13 = r16.getContext()
            int[] r3 = com.google.android.material.C1087R.styleable.BottomAppBar
            int[] r6 = new int[r11]
            r1 = r13
            r2 = r18
            r4 = r19
            r5 = r9
            android.content.res.TypedArray r1 = com.google.android.material.internal.ThemeEnforcement.obtainStyledAttributes(r1, r2, r3, r4, r5, r6)
            int r2 = com.google.android.material.C1087R.styleable.BottomAppBar_backgroundTint
            android.content.res.ColorStateList r2 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r13, (android.content.res.TypedArray) r1, (int) r2)
            int r3 = com.google.android.material.C1087R.styleable.BottomAppBar_navigationIconTint
            boolean r3 = r1.hasValue(r3)
            r4 = -1
            if (r3 == 0) goto L_0x005a
            int r3 = com.google.android.material.C1087R.styleable.BottomAppBar_navigationIconTint
            int r3 = r1.getColor(r3, r4)
            r0.setNavigationIconTint(r3)
        L_0x005a:
            int r3 = com.google.android.material.C1087R.styleable.BottomAppBar_elevation
            int r3 = r1.getDimensionPixelSize(r3, r11)
            int r5 = com.google.android.material.C1087R.styleable.BottomAppBar_fabCradleMargin
            int r5 = r1.getDimensionPixelOffset(r5, r11)
            float r5 = (float) r5
            int r6 = com.google.android.material.C1087R.styleable.BottomAppBar_fabCradleRoundedCornerRadius
            int r6 = r1.getDimensionPixelOffset(r6, r11)
            float r6 = (float) r6
            int r14 = com.google.android.material.C1087R.styleable.BottomAppBar_fabCradleVerticalOffset
            int r14 = r1.getDimensionPixelOffset(r14, r11)
            float r14 = (float) r14
            int r15 = com.google.android.material.C1087R.styleable.BottomAppBar_fabAlignmentMode
            int r15 = r1.getInt(r15, r11)
            r0.fabAlignmentMode = r15
            int r15 = com.google.android.material.C1087R.styleable.BottomAppBar_fabAnimationMode
            int r15 = r1.getInt(r15, r11)
            r0.fabAnimationMode = r15
            int r15 = com.google.android.material.C1087R.styleable.BottomAppBar_fabAnchorMode
            int r15 = r1.getInt(r15, r12)
            r0.fabAnchorMode = r15
            int r15 = com.google.android.material.C1087R.styleable.BottomAppBar_removeEmbeddedFabElevation
            boolean r15 = r1.getBoolean(r15, r12)
            r0.removeEmbeddedFabElevation = r15
            int r15 = com.google.android.material.C1087R.styleable.BottomAppBar_menuAlignmentMode
            int r15 = r1.getInt(r15, r11)
            r0.menuAlignmentMode = r15
            int r15 = com.google.android.material.C1087R.styleable.BottomAppBar_hideOnScroll
            boolean r15 = r1.getBoolean(r15, r11)
            r0.hideOnScroll = r15
            int r15 = com.google.android.material.C1087R.styleable.BottomAppBar_paddingBottomSystemWindowInsets
            boolean r15 = r1.getBoolean(r15, r11)
            r0.paddingBottomSystemWindowInsets = r15
            int r15 = com.google.android.material.C1087R.styleable.BottomAppBar_paddingLeftSystemWindowInsets
            boolean r15 = r1.getBoolean(r15, r11)
            r0.paddingLeftSystemWindowInsets = r15
            int r15 = com.google.android.material.C1087R.styleable.BottomAppBar_paddingRightSystemWindowInsets
            boolean r15 = r1.getBoolean(r15, r11)
            r0.paddingRightSystemWindowInsets = r15
            int r15 = com.google.android.material.C1087R.styleable.BottomAppBar_fabAlignmentModeEndMargin
            int r4 = r1.getDimensionPixelOffset(r15, r4)
            r0.fabAlignmentModeEndMargin = r4
            int r4 = com.google.android.material.C1087R.styleable.BottomAppBar_addElevationShadow
            boolean r4 = r1.getBoolean(r4, r12)
            r1.recycle()
            android.content.res.Resources r15 = r16.getResources()
            int r11 = com.google.android.material.C1087R.dimen.mtrl_bottomappbar_fabOffsetEndMode
            int r11 = r15.getDimensionPixelOffset(r11)
            r0.fabOffsetEndMode = r11
            com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment r11 = new com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment
            r11.<init>(r5, r6, r14)
            com.google.android.material.shape.ShapeAppearanceModel$Builder r15 = com.google.android.material.shape.ShapeAppearanceModel.builder()
            com.google.android.material.shape.ShapeAppearanceModel$Builder r15 = r15.setTopEdge(r11)
            com.google.android.material.shape.ShapeAppearanceModel r15 = r15.build()
            r10.setShapeAppearanceModel(r15)
            if (r4 == 0) goto L_0x00f8
            r12 = 2
            r10.setShadowCompatibilityMode(r12)
            r17 = r1
            goto L_0x010a
        L_0x00f8:
            r10.setShadowCompatibilityMode(r12)
            int r12 = android.os.Build.VERSION.SDK_INT
            r17 = r1
            r1 = 28
            if (r12 < r1) goto L_0x010a
            r1 = 0
            r0.setOutlineAmbientShadowColor(r1)
            r0.setOutlineSpotShadowColor(r1)
        L_0x010a:
            android.graphics.Paint$Style r1 = android.graphics.Paint.Style.FILL
            r10.setPaintStyle(r1)
            r10.initializeElevationOverlay(r13)
            float r1 = (float) r3
            r0.setElevation(r1)
            androidx.core.graphics.drawable.DrawableCompat.setTintList(r10, r2)
            androidx.core.view.ViewCompat.setBackground(r0, r10)
            com.google.android.material.bottomappbar.BottomAppBar$3 r1 = new com.google.android.material.bottomappbar.BottomAppBar$3
            r1.<init>()
            com.google.android.material.internal.ViewUtils.doOnApplyWindowInsets(r0, r7, r8, r9, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.bottomappbar.BottomAppBar.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    public void setNavigationIcon(Drawable drawable) {
        super.setNavigationIcon(maybeTintNavigationIcon(drawable));
    }

    public void setNavigationIconTint(int navigationIconTint2) {
        this.navigationIconTint = Integer.valueOf(navigationIconTint2);
        Drawable navigationIcon = getNavigationIcon();
        if (navigationIcon != null) {
            setNavigationIcon(navigationIcon);
        }
    }

    public int getFabAlignmentMode() {
        return this.fabAlignmentMode;
    }

    public void setFabAlignmentMode(int fabAlignmentMode2) {
        setFabAlignmentModeAndReplaceMenu(fabAlignmentMode2, 0);
    }

    public void setFabAlignmentModeAndReplaceMenu(int fabAlignmentMode2, int newMenu) {
        this.pendingMenuResId = newMenu;
        this.menuAnimatingWithFabAlignmentMode = true;
        maybeAnimateMenuView(fabAlignmentMode2, this.fabAttached);
        maybeAnimateModeChange(fabAlignmentMode2);
        this.fabAlignmentMode = fabAlignmentMode2;
    }

    public int getFabAnchorMode() {
        return this.fabAnchorMode;
    }

    public void setFabAnchorMode(int fabAnchorMode2) {
        this.fabAnchorMode = fabAnchorMode2;
        setCutoutStateAndTranslateFab();
        View fab = findDependentView();
        if (fab != null) {
            updateFabAnchorGravity(this, fab);
            fab.requestLayout();
            this.materialShapeDrawable.invalidateSelf();
        }
    }

    /* access modifiers changed from: private */
    public static void updateFabAnchorGravity(BottomAppBar bar, View fab) {
        CoordinatorLayout.LayoutParams fabLayoutParams = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        fabLayoutParams.anchorGravity = 17;
        if (bar.fabAnchorMode == 1) {
            fabLayoutParams.anchorGravity |= 48;
        }
        if (bar.fabAnchorMode == 0) {
            fabLayoutParams.anchorGravity |= 80;
        }
    }

    public int getFabAnimationMode() {
        return this.fabAnimationMode;
    }

    public void setFabAnimationMode(int fabAnimationMode2) {
        this.fabAnimationMode = fabAnimationMode2;
    }

    public void setMenuAlignmentMode(int menuAlignmentMode2) {
        if (this.menuAlignmentMode != menuAlignmentMode2) {
            this.menuAlignmentMode = menuAlignmentMode2;
            ActionMenuView menu = getActionMenuView();
            if (menu != null) {
                translateActionMenuView(menu, this.fabAlignmentMode, isFabVisibleOrWillBeShown());
            }
        }
    }

    public int getMenuAlignmentMode() {
        return this.menuAlignmentMode;
    }

    public void setBackgroundTint(ColorStateList backgroundTint) {
        DrawableCompat.setTintList(this.materialShapeDrawable, backgroundTint);
    }

    public ColorStateList getBackgroundTint() {
        return this.materialShapeDrawable.getTintList();
    }

    public float getFabCradleMargin() {
        return getTopEdgeTreatment().getFabCradleMargin();
    }

    public void setFabCradleMargin(float cradleMargin) {
        if (cradleMargin != getFabCradleMargin()) {
            getTopEdgeTreatment().setFabCradleMargin(cradleMargin);
            this.materialShapeDrawable.invalidateSelf();
        }
    }

    public float getFabCradleRoundedCornerRadius() {
        return getTopEdgeTreatment().getFabCradleRoundedCornerRadius();
    }

    public void setFabCradleRoundedCornerRadius(float roundedCornerRadius) {
        if (roundedCornerRadius != getFabCradleRoundedCornerRadius()) {
            getTopEdgeTreatment().setFabCradleRoundedCornerRadius(roundedCornerRadius);
            this.materialShapeDrawable.invalidateSelf();
        }
    }

    public float getCradleVerticalOffset() {
        return getTopEdgeTreatment().getCradleVerticalOffset();
    }

    public void setCradleVerticalOffset(float verticalOffset) {
        if (verticalOffset != getCradleVerticalOffset()) {
            getTopEdgeTreatment().setCradleVerticalOffset(verticalOffset);
            this.materialShapeDrawable.invalidateSelf();
            setCutoutStateAndTranslateFab();
        }
    }

    public int getFabAlignmentModeEndMargin() {
        return this.fabAlignmentModeEndMargin;
    }

    public void setFabAlignmentModeEndMargin(int margin) {
        if (this.fabAlignmentModeEndMargin != margin) {
            this.fabAlignmentModeEndMargin = margin;
            setCutoutStateAndTranslateFab();
        }
    }

    public boolean getHideOnScroll() {
        return this.hideOnScroll;
    }

    public void setHideOnScroll(boolean hide) {
        this.hideOnScroll = hide;
    }

    public void performHide() {
        performHide(true);
    }

    public void performHide(boolean animate) {
        getBehavior().slideDown(this, animate);
    }

    public void performShow() {
        performShow(true);
    }

    public void performShow(boolean animate) {
        getBehavior().slideUp(this, animate);
    }

    public boolean isScrolledDown() {
        return getBehavior().isScrolledDown();
    }

    public boolean isScrolledUp() {
        return getBehavior().isScrolledUp();
    }

    public void addOnScrollStateChangedListener(HideBottomViewOnScrollBehavior.OnScrollStateChangedListener listener) {
        getBehavior().addOnScrollStateChangedListener(listener);
    }

    public void removeOnScrollStateChangedListener(HideBottomViewOnScrollBehavior.OnScrollStateChangedListener listener) {
        getBehavior().removeOnScrollStateChangedListener(listener);
    }

    public void clearOnScrollStateChangedListeners() {
        getBehavior().clearOnScrollStateChangedListeners();
    }

    public void setElevation(float elevation) {
        this.materialShapeDrawable.setElevation(elevation);
        getBehavior().setAdditionalHiddenOffsetY(this, this.materialShapeDrawable.getShadowRadius() - this.materialShapeDrawable.getShadowOffsetY());
    }

    public void replaceMenu(int newMenu) {
        if (newMenu != 0) {
            this.pendingMenuResId = 0;
            getMenu().clear();
            inflateMenu(newMenu);
        }
    }

    /* access modifiers changed from: package-private */
    public void addAnimationListener(AnimationListener listener) {
        if (this.animationListeners == null) {
            this.animationListeners = new ArrayList<>();
        }
        this.animationListeners.add(listener);
    }

    /* access modifiers changed from: package-private */
    public void removeAnimationListener(AnimationListener listener) {
        ArrayList<AnimationListener> arrayList = this.animationListeners;
        if (arrayList != null) {
            arrayList.remove(listener);
        }
    }

    /* access modifiers changed from: private */
    public void dispatchAnimationStart() {
        ArrayList<AnimationListener> arrayList;
        int i = this.animatingModeChangeCounter;
        this.animatingModeChangeCounter = i + 1;
        if (i == 0 && (arrayList = this.animationListeners) != null) {
            Iterator<AnimationListener> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().onAnimationStart(this);
            }
        }
    }

    /* access modifiers changed from: private */
    public void dispatchAnimationEnd() {
        ArrayList<AnimationListener> arrayList;
        int i = this.animatingModeChangeCounter - 1;
        this.animatingModeChangeCounter = i;
        if (i == 0 && (arrayList = this.animationListeners) != null) {
            Iterator<AnimationListener> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().onAnimationEnd(this);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean setFabDiameter(int diameter) {
        if (((float) diameter) == getTopEdgeTreatment().getFabDiameter()) {
            return false;
        }
        getTopEdgeTreatment().setFabDiameter((float) diameter);
        this.materialShapeDrawable.invalidateSelf();
        return true;
    }

    /* access modifiers changed from: package-private */
    public void setFabCornerSize(float radius) {
        if (radius != getTopEdgeTreatment().getFabCornerRadius()) {
            getTopEdgeTreatment().setFabCornerSize(radius);
            this.materialShapeDrawable.invalidateSelf();
        }
    }

    private void maybeAnimateModeChange(int targetMode) {
        if (this.fabAlignmentMode != targetMode && ViewCompat.isLaidOut(this)) {
            Animator animator = this.modeAnimator;
            if (animator != null) {
                animator.cancel();
            }
            List<Animator> animators = new ArrayList<>();
            if (this.fabAnimationMode == 1) {
                createFabTranslationXAnimation(targetMode, animators);
            } else {
                createFabDefaultXAnimation(targetMode, animators);
            }
            AnimatorSet set = new AnimatorSet();
            set.playTogether(animators);
            set.setInterpolator(MotionUtils.resolveThemeInterpolator(getContext(), FAB_ALIGNMENT_ANIM_EASING_ATTR, AnimationUtils.LINEAR_INTERPOLATOR));
            this.modeAnimator = set;
            set.addListener(new AnimatorListenerAdapter() {
                public void onAnimationStart(Animator animation) {
                    BottomAppBar.this.dispatchAnimationStart();
                }

                public void onAnimationEnd(Animator animation) {
                    BottomAppBar.this.dispatchAnimationEnd();
                    Animator unused = BottomAppBar.this.modeAnimator = null;
                }
            });
            this.modeAnimator.start();
        }
    }

    /* access modifiers changed from: private */
    public FloatingActionButton findDependentFab() {
        View view = findDependentView();
        if (view instanceof FloatingActionButton) {
            return (FloatingActionButton) view;
        }
        return null;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:6:0x001e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View findDependentView() {
        /*
            r5 = this;
            android.view.ViewParent r0 = r5.getParent()
            boolean r0 = r0 instanceof androidx.coordinatorlayout.widget.CoordinatorLayout
            r1 = 0
            if (r0 != 0) goto L_0x000a
            return r1
        L_0x000a:
            android.view.ViewParent r0 = r5.getParent()
            androidx.coordinatorlayout.widget.CoordinatorLayout r0 = (androidx.coordinatorlayout.widget.CoordinatorLayout) r0
            java.util.List r0 = r0.getDependents(r5)
            java.util.Iterator r2 = r0.iterator()
        L_0x0018:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x002f
            java.lang.Object r3 = r2.next()
            android.view.View r3 = (android.view.View) r3
            boolean r4 = r3 instanceof com.google.android.material.floatingactionbutton.FloatingActionButton
            if (r4 != 0) goto L_0x002e
            boolean r4 = r3 instanceof com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
            if (r4 == 0) goto L_0x002d
            goto L_0x002e
        L_0x002d:
            goto L_0x0018
        L_0x002e:
            return r3
        L_0x002f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.bottomappbar.BottomAppBar.findDependentView():android.view.View");
    }

    private boolean isFabVisibleOrWillBeShown() {
        FloatingActionButton fab = findDependentFab();
        return fab != null && fab.isOrWillBeShown();
    }

    /* access modifiers changed from: protected */
    public void createFabDefaultXAnimation(final int targetMode, List<Animator> list) {
        FloatingActionButton fab = findDependentFab();
        if (fab != null && !fab.isOrWillBeHidden()) {
            dispatchAnimationStart();
            fab.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                public void onHidden(FloatingActionButton fab) {
                    fab.setTranslationX(BottomAppBar.this.getFabTranslationX(targetMode));
                    fab.show(new FloatingActionButton.OnVisibilityChangedListener() {
                        public void onShown(FloatingActionButton fab) {
                            BottomAppBar.this.dispatchAnimationEnd();
                        }
                    });
                }
            });
        }
    }

    private void createFabTranslationXAnimation(int targetMode, List<Animator> animators) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(findDependentFab(), "translationX", new float[]{getFabTranslationX(targetMode)});
        animator.setDuration((long) getFabAlignmentAnimationDuration());
        animators.add(animator);
    }

    private int getFabAlignmentAnimationDuration() {
        return MotionUtils.resolveThemeDuration(getContext(), FAB_ALIGNMENT_ANIM_DURATION_ATTR, 300);
    }

    private Drawable maybeTintNavigationIcon(Drawable navigationIcon) {
        if (navigationIcon == null || this.navigationIconTint == null) {
            return navigationIcon;
        }
        Drawable wrappedNavigationIcon = DrawableCompat.wrap(navigationIcon.mutate());
        DrawableCompat.setTint(wrappedNavigationIcon, this.navigationIconTint.intValue());
        return wrappedNavigationIcon;
    }

    /* access modifiers changed from: private */
    public void maybeAnimateMenuView(int targetMode, boolean newFabAttached) {
        if (!ViewCompat.isLaidOut(this)) {
            this.menuAnimatingWithFabAlignmentMode = false;
            replaceMenu(this.pendingMenuResId);
            return;
        }
        Animator animator = this.menuAnimator;
        if (animator != null) {
            animator.cancel();
        }
        List<Animator> animators = new ArrayList<>();
        if (!isFabVisibleOrWillBeShown()) {
            targetMode = 0;
            newFabAttached = false;
        }
        createMenuViewTranslationAnimation(targetMode, newFabAttached, animators);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animators);
        this.menuAnimator = set;
        set.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animation) {
                BottomAppBar.this.dispatchAnimationStart();
            }

            public void onAnimationEnd(Animator animation) {
                BottomAppBar.this.dispatchAnimationEnd();
                boolean unused = BottomAppBar.this.menuAnimatingWithFabAlignmentMode = false;
                Animator unused2 = BottomAppBar.this.menuAnimator = null;
            }
        });
        this.menuAnimator.start();
    }

    private void createMenuViewTranslationAnimation(final int targetMode, final boolean targetAttached, List<Animator> animators) {
        final ActionMenuView actionMenuView = getActionMenuView();
        if (actionMenuView != null) {
            float animationDuration = (float) getFabAlignmentAnimationDuration();
            Animator fadeIn = ObjectAnimator.ofFloat(actionMenuView, "alpha", new float[]{1.0f});
            fadeIn.setDuration((long) (0.8f * animationDuration));
            if (Math.abs(actionMenuView.getTranslationX() - ((float) getActionMenuViewTranslationX(actionMenuView, targetMode, targetAttached))) > 1.0f) {
                Animator fadeOut = ObjectAnimator.ofFloat(actionMenuView, "alpha", new float[]{0.0f});
                fadeOut.setDuration((long) (0.2f * animationDuration));
                fadeOut.addListener(new AnimatorListenerAdapter() {
                    public boolean cancelled;

                    public void onAnimationCancel(Animator animation) {
                        this.cancelled = true;
                    }

                    public void onAnimationEnd(Animator animation) {
                        if (!this.cancelled) {
                            boolean replaced = BottomAppBar.this.pendingMenuResId != 0;
                            BottomAppBar bottomAppBar = BottomAppBar.this;
                            bottomAppBar.replaceMenu(bottomAppBar.pendingMenuResId);
                            BottomAppBar.this.translateActionMenuView(actionMenuView, targetMode, targetAttached, replaced);
                        }
                    }
                });
                AnimatorSet set = new AnimatorSet();
                set.playSequentially(new Animator[]{fadeOut, fadeIn});
                animators.add(set);
            } else if (actionMenuView.getAlpha() < 1.0f) {
                animators.add(fadeIn);
            }
        }
    }

    private float getFabTranslationY() {
        if (this.fabAnchorMode == 1) {
            return -getTopEdgeTreatment().getCradleVerticalOffset();
        }
        return 0.0f;
    }

    /* access modifiers changed from: private */
    public float getFabTranslationX(int fabAlignmentMode2) {
        int totalEndInset;
        boolean isRtl = ViewUtils.isLayoutRtl(this);
        int i = 1;
        if (fabAlignmentMode2 != 1) {
            return 0.0f;
        }
        View fab = findDependentView();
        int totalEndInset2 = isRtl ? this.leftInset : this.rightInset;
        if (this.fabAlignmentModeEndMargin == -1 || fab == null) {
            totalEndInset = totalEndInset2 + this.fabOffsetEndMode;
        } else {
            totalEndInset = totalEndInset2 + (fab.getMeasuredWidth() / 2) + this.fabAlignmentModeEndMargin;
        }
        int measuredWidth = (getMeasuredWidth() / 2) - totalEndInset;
        if (isRtl) {
            i = -1;
        }
        return (float) (measuredWidth * i);
    }

    /* access modifiers changed from: private */
    public float getFabTranslationX() {
        return getFabTranslationX(this.fabAlignmentMode);
    }

    private ActionMenuView getActionMenuView() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof ActionMenuView) {
                return (ActionMenuView) view;
            }
        }
        return null;
    }

    private void translateActionMenuView(ActionMenuView actionMenuView, int fabAlignmentMode2, boolean fabAttached2) {
        translateActionMenuView(actionMenuView, fabAlignmentMode2, fabAttached2, false);
    }

    /* access modifiers changed from: private */
    public void translateActionMenuView(final ActionMenuView actionMenuView, final int fabAlignmentMode2, final boolean fabAttached2, boolean shouldWaitForMenuReplacement) {
        Runnable runnable = new Runnable() {
            public void run() {
                ActionMenuView actionMenuView = actionMenuView;
                actionMenuView.setTranslationX((float) BottomAppBar.this.getActionMenuViewTranslationX(actionMenuView, fabAlignmentMode2, fabAttached2));
            }
        };
        if (shouldWaitForMenuReplacement) {
            actionMenuView.post(runnable);
        } else {
            runnable.run();
        }
    }

    /* access modifiers changed from: protected */
    public int getActionMenuViewTranslationX(ActionMenuView actionMenuView, int fabAlignmentMode2, boolean fabAttached2) {
        int i;
        if (this.menuAlignmentMode != 1 && (fabAlignmentMode2 != 1 || !fabAttached2)) {
            return 0;
        }
        boolean isRtl = ViewUtils.isLayoutRtl(this);
        int toolbarLeftContentEnd = isRtl ? getMeasuredWidth() : 0;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View view = getChildAt(i2);
            if ((view.getLayoutParams() instanceof Toolbar.LayoutParams) && (((Toolbar.LayoutParams) view.getLayoutParams()).gravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK) == 8388611) {
                if (isRtl) {
                    i = Math.min(toolbarLeftContentEnd, view.getLeft());
                } else {
                    i = Math.max(toolbarLeftContentEnd, view.getRight());
                }
                toolbarLeftContentEnd = i;
            }
        }
        int actionMenuViewStart = isRtl ? actionMenuView.getRight() : actionMenuView.getLeft();
        int systemStartInset = isRtl ? this.rightInset : -this.leftInset;
        int marginStart = 0;
        if (getNavigationIcon() == null) {
            int horizontalMargin = getResources().getDimensionPixelOffset(C1087R.dimen.m3_bottomappbar_horizontal_padding);
            marginStart = isRtl ? horizontalMargin : -horizontalMargin;
        }
        return toolbarLeftContentEnd - ((actionMenuViewStart + systemStartInset) + marginStart);
    }

    /* access modifiers changed from: private */
    public void cancelAnimations() {
        Animator animator = this.menuAnimator;
        if (animator != null) {
            animator.cancel();
        }
        Animator animator2 = this.modeAnimator;
        if (animator2 != null) {
            animator2.cancel();
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            cancelAnimations();
            setCutoutStateAndTranslateFab();
            View dependentView = findDependentView();
            if (dependentView != null && ViewCompat.isLaidOut(dependentView)) {
                dependentView.post(new BottomAppBar$$ExternalSyntheticLambda0(dependentView));
            }
        }
        setActionMenuViewPosition();
    }

    /* access modifiers changed from: private */
    public BottomAppBarTopEdgeTreatment getTopEdgeTreatment() {
        return (BottomAppBarTopEdgeTreatment) this.materialShapeDrawable.getShapeAppearanceModel().getTopEdge();
    }

    /* access modifiers changed from: private */
    public void setCutoutStateAndTranslateFab() {
        float f;
        getTopEdgeTreatment().setHorizontalOffset(getFabTranslationX());
        MaterialShapeDrawable materialShapeDrawable2 = this.materialShapeDrawable;
        if (!this.fabAttached || !isFabVisibleOrWillBeShown() || this.fabAnchorMode != 1) {
            f = 0.0f;
        } else {
            f = 1.0f;
        }
        materialShapeDrawable2.setInterpolation(f);
        View fab = findDependentView();
        if (fab != null) {
            fab.setTranslationY(getFabTranslationY());
            fab.setTranslationX(getFabTranslationX());
        }
    }

    /* access modifiers changed from: private */
    public void setActionMenuViewPosition() {
        ActionMenuView actionMenuView = getActionMenuView();
        if (actionMenuView != null && this.menuAnimator == null) {
            actionMenuView.setAlpha(1.0f);
            if (!isFabVisibleOrWillBeShown()) {
                translateActionMenuView(actionMenuView, 0, false);
            } else {
                translateActionMenuView(actionMenuView, this.fabAlignmentMode, this.fabAttached);
            }
        }
    }

    /* access modifiers changed from: private */
    public void addFabAnimationListeners(FloatingActionButton fab) {
        fab.addOnHideAnimationListener(this.fabAnimationListener);
        fab.addOnShowAnimationListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animation) {
                BottomAppBar.this.fabAnimationListener.onAnimationStart(animation);
                FloatingActionButton fab = BottomAppBar.this.findDependentFab();
                if (fab != null) {
                    fab.setTranslationX(BottomAppBar.this.getFabTranslationX());
                }
            }
        });
        fab.addTransformationCallback(this.fabTransformationCallback);
    }

    /* access modifiers changed from: private */
    public int getBottomInset() {
        return this.bottomInset;
    }

    /* access modifiers changed from: private */
    public int getRightInset() {
        return this.rightInset;
    }

    /* access modifiers changed from: private */
    public int getLeftInset() {
        return this.leftInset;
    }

    public void setTitle(CharSequence title) {
    }

    public void setSubtitle(CharSequence subtitle) {
    }

    public Behavior getBehavior() {
        if (this.behavior == null) {
            this.behavior = new Behavior();
        }
        return this.behavior;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this, this.materialShapeDrawable);
        if (getParent() instanceof ViewGroup) {
            ((ViewGroup) getParent()).setClipChildren(false);
        }
    }

    public static class Behavior extends HideBottomViewOnScrollBehavior<BottomAppBar> {
        /* access modifiers changed from: private */
        public final Rect fabContentRect = new Rect();
        private final View.OnLayoutChangeListener fabLayoutListener = new View.OnLayoutChangeListener() {
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                View view = v;
                BottomAppBar child = (BottomAppBar) Behavior.this.viewRef.get();
                if (child == null || (!(view instanceof FloatingActionButton) && !(view instanceof ExtendedFloatingActionButton))) {
                    v.removeOnLayoutChangeListener(this);
                    return;
                }
                int height = v.getHeight();
                if (view instanceof FloatingActionButton) {
                    FloatingActionButton fab = (FloatingActionButton) view;
                    fab.getMeasuredContentRect(Behavior.this.fabContentRect);
                    height = Behavior.this.fabContentRect.height();
                    child.setFabDiameter(height);
                    child.setFabCornerSize(fab.getShapeAppearanceModel().getTopLeftCornerSize().getCornerSize(new RectF(Behavior.this.fabContentRect)));
                }
                CoordinatorLayout.LayoutParams fabLayoutParams = (CoordinatorLayout.LayoutParams) v.getLayoutParams();
                if (Behavior.this.originalBottomMargin == 0) {
                    int bottomShadowPadding = (v.getMeasuredHeight() - height) / 2;
                    if (child.fabAnchorMode == 1) {
                        fabLayoutParams.bottomMargin = child.getBottomInset() + (child.getResources().getDimensionPixelOffset(C1087R.dimen.mtrl_bottomappbar_fab_bottom_margin) - bottomShadowPadding);
                    } else if (child.fabAnchorMode == 0) {
                        fabLayoutParams.bottomMargin = ((child.getMeasuredHeight() + child.getBottomInset()) - v.getMeasuredHeight()) / 2;
                    }
                    fabLayoutParams.leftMargin = child.getLeftInset();
                    fabLayoutParams.rightMargin = child.getRightInset();
                    if (ViewUtils.isLayoutRtl(v)) {
                        fabLayoutParams.leftMargin += child.fabOffsetEndMode;
                    } else {
                        fabLayoutParams.rightMargin += child.fabOffsetEndMode;
                    }
                }
            }
        };
        /* access modifiers changed from: private */
        public int originalBottomMargin;
        /* access modifiers changed from: private */
        public WeakReference<BottomAppBar> viewRef;

        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public boolean onLayoutChild(CoordinatorLayout parent, BottomAppBar child, int layoutDirection) {
            this.viewRef = new WeakReference<>(child);
            View dependentView = child.findDependentView();
            if (dependentView != null && !ViewCompat.isLaidOut(dependentView)) {
                BottomAppBar.updateFabAnchorGravity(child, dependentView);
                this.originalBottomMargin = ((CoordinatorLayout.LayoutParams) dependentView.getLayoutParams()).bottomMargin;
                if (dependentView instanceof FloatingActionButton) {
                    FloatingActionButton fab = (FloatingActionButton) dependentView;
                    if (child.fabAnchorMode == 0 && child.removeEmbeddedFabElevation) {
                        ViewCompat.setElevation(fab, 0.0f);
                        fab.setCompatElevation(0.0f);
                    }
                    if (fab.getShowMotionSpec() == null) {
                        fab.setShowMotionSpecResource(C1087R.animator.mtrl_fab_show_motion_spec);
                    }
                    if (fab.getHideMotionSpec() == null) {
                        fab.setHideMotionSpecResource(C1087R.animator.mtrl_fab_hide_motion_spec);
                    }
                    child.addFabAnimationListeners(fab);
                }
                dependentView.addOnLayoutChangeListener(this.fabLayoutListener);
                child.setCutoutStateAndTranslateFab();
            }
            parent.onLayoutChild(child, layoutDirection);
            return super.onLayoutChild(parent, child, layoutDirection);
        }

        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, BottomAppBar child, View directTargetChild, View target, int axes, int type) {
            return child.getHideOnScroll() && super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.fabAlignmentMode = this.fabAlignmentMode;
        savedState.fabAttached = this.fabAttached;
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
        this.fabAlignmentMode = savedState.fabAlignmentMode;
        this.fabAttached = savedState.fabAttached;
    }

    static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() {
            public SavedState createFromParcel(Parcel in, ClassLoader loader) {
                return new SavedState(in, loader);
            }

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in, (ClassLoader) null);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        int fabAlignmentMode;
        boolean fabAttached;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel in, ClassLoader loader) {
            super(in, loader);
            this.fabAlignmentMode = in.readInt();
            this.fabAttached = in.readInt() != 0;
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.fabAlignmentMode);
            out.writeInt(this.fabAttached ? 1 : 0);
        }
    }
}
