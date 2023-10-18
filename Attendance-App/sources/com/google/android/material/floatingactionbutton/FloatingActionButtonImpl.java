package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import com.google.android.material.C1087R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.animation.ImageMatrixProperty;
import com.google.android.material.animation.MatrixEvaluator;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.internal.StateListAnimator;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.ripple.RippleDrawableCompat;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shadow.ShadowViewDelegate;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class FloatingActionButtonImpl {
    static final int ANIM_STATE_HIDING = 1;
    static final int ANIM_STATE_NONE = 0;
    static final int ANIM_STATE_SHOWING = 2;
    static final long ELEVATION_ANIM_DELAY = 100;
    static final long ELEVATION_ANIM_DURATION = 100;
    static final TimeInterpolator ELEVATION_ANIM_INTERPOLATOR = AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR;
    static final int[] EMPTY_STATE_SET = new int[0];
    static final int[] ENABLED_STATE_SET = {16842910};
    static final int[] FOCUSED_ENABLED_STATE_SET = {16842908, 16842910};
    private static final int HIDE_ANIM_DURATION_ATTR = C1087R.attr.motionDurationMedium1;
    private static final int HIDE_ANIM_EASING_ATTR = C1087R.attr.motionEasingEmphasizedAccelerateInterpolator;
    private static final float HIDE_ICON_SCALE = 0.4f;
    private static final float HIDE_OPACITY = 0.0f;
    private static final float HIDE_SCALE = 0.4f;
    static final int[] HOVERED_ENABLED_STATE_SET = {16843623, 16842910};
    static final int[] HOVERED_FOCUSED_ENABLED_STATE_SET = {16843623, 16842908, 16842910};
    static final int[] PRESSED_ENABLED_STATE_SET = {16842919, 16842910};
    static final float SHADOW_MULTIPLIER = 1.5f;
    private static final int SHOW_ANIM_DURATION_ATTR = C1087R.attr.motionDurationLong2;
    private static final int SHOW_ANIM_EASING_ATTR = C1087R.attr.motionEasingEmphasizedInterpolator;
    private static final float SHOW_ICON_SCALE = 1.0f;
    private static final float SHOW_OPACITY = 1.0f;
    private static final float SHOW_SCALE = 1.0f;
    private static final float SPEC_HIDE_ICON_SCALE = 0.0f;
    private static final float SPEC_HIDE_SCALE = 0.0f;
    /* access modifiers changed from: private */
    public int animState = 0;
    BorderDrawable borderDrawable;
    Drawable contentBackground;
    /* access modifiers changed from: private */
    public Animator currentAnimator;
    float elevation;
    boolean ensureMinTouchTargetSize;
    private ArrayList<Animator.AnimatorListener> hideListeners;
    private MotionSpec hideMotionSpec;
    float hoveredFocusedTranslationZ;
    /* access modifiers changed from: private */
    public float imageMatrixScale = 1.0f;
    private int maxImageSize;
    int minTouchTargetSize;
    private ViewTreeObserver.OnPreDrawListener preDrawListener;
    float pressedTranslationZ;
    Drawable rippleDrawable;
    private float rotation;
    boolean shadowPaddingEnabled = true;
    final ShadowViewDelegate shadowViewDelegate;
    ShapeAppearanceModel shapeAppearance;
    MaterialShapeDrawable shapeDrawable;
    private ArrayList<Animator.AnimatorListener> showListeners;
    private MotionSpec showMotionSpec;
    private final StateListAnimator stateListAnimator;
    private final Matrix tmpMatrix = new Matrix();
    private final Rect tmpRect = new Rect();
    private final RectF tmpRectF1 = new RectF();
    private final RectF tmpRectF2 = new RectF();
    private ArrayList<InternalTransformationCallback> transformationCallbacks;
    final FloatingActionButton view;

    interface InternalTransformationCallback {
        void onScaleChanged();

        void onTranslationChanged();
    }

    interface InternalVisibilityChangedListener {
        void onHidden();

        void onShown();
    }

    FloatingActionButtonImpl(FloatingActionButton view2, ShadowViewDelegate shadowViewDelegate2) {
        this.view = view2;
        this.shadowViewDelegate = shadowViewDelegate2;
        StateListAnimator stateListAnimator2 = new StateListAnimator();
        this.stateListAnimator = stateListAnimator2;
        stateListAnimator2.addState(PRESSED_ENABLED_STATE_SET, createElevationAnimator(new ElevateToPressedTranslationZAnimation()));
        stateListAnimator2.addState(HOVERED_FOCUSED_ENABLED_STATE_SET, createElevationAnimator(new ElevateToHoveredFocusedTranslationZAnimation()));
        stateListAnimator2.addState(FOCUSED_ENABLED_STATE_SET, createElevationAnimator(new ElevateToHoveredFocusedTranslationZAnimation()));
        stateListAnimator2.addState(HOVERED_ENABLED_STATE_SET, createElevationAnimator(new ElevateToHoveredFocusedTranslationZAnimation()));
        stateListAnimator2.addState(ENABLED_STATE_SET, createElevationAnimator(new ResetElevationAnimation()));
        stateListAnimator2.addState(EMPTY_STATE_SET, createElevationAnimator(new DisabledElevationAnimation()));
        this.rotation = view2.getRotation();
    }

    /* access modifiers changed from: package-private */
    public void initializeBackgroundDrawable(ColorStateList backgroundTint, PorterDuff.Mode backgroundTintMode, ColorStateList rippleColor, int borderWidth) {
        MaterialShapeDrawable createShapeDrawable = createShapeDrawable();
        this.shapeDrawable = createShapeDrawable;
        createShapeDrawable.setTintList(backgroundTint);
        if (backgroundTintMode != null) {
            this.shapeDrawable.setTintMode(backgroundTintMode);
        }
        this.shapeDrawable.setShadowColor(-12303292);
        this.shapeDrawable.initializeElevationOverlay(this.view.getContext());
        RippleDrawableCompat touchFeedbackShape = new RippleDrawableCompat(this.shapeDrawable.getShapeAppearanceModel());
        touchFeedbackShape.setTintList(RippleUtils.sanitizeRippleDrawableColor(rippleColor));
        this.rippleDrawable = touchFeedbackShape;
        this.contentBackground = new LayerDrawable(new Drawable[]{(Drawable) Preconditions.checkNotNull(this.shapeDrawable), touchFeedbackShape});
    }

    /* access modifiers changed from: package-private */
    public void setBackgroundTintList(ColorStateList tint) {
        MaterialShapeDrawable materialShapeDrawable = this.shapeDrawable;
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setTintList(tint);
        }
        BorderDrawable borderDrawable2 = this.borderDrawable;
        if (borderDrawable2 != null) {
            borderDrawable2.setBorderTint(tint);
        }
    }

    /* access modifiers changed from: package-private */
    public void setBackgroundTintMode(PorterDuff.Mode tintMode) {
        MaterialShapeDrawable materialShapeDrawable = this.shapeDrawable;
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setTintMode(tintMode);
        }
    }

    /* access modifiers changed from: package-private */
    public void setMinTouchTargetSize(int minTouchTargetSize2) {
        this.minTouchTargetSize = minTouchTargetSize2;
    }

    /* access modifiers changed from: package-private */
    public void setRippleColor(ColorStateList rippleColor) {
        Drawable drawable = this.rippleDrawable;
        if (drawable != null) {
            DrawableCompat.setTintList(drawable, RippleUtils.sanitizeRippleDrawableColor(rippleColor));
        }
    }

    /* access modifiers changed from: package-private */
    public final void setElevation(float elevation2) {
        if (this.elevation != elevation2) {
            this.elevation = elevation2;
            onElevationsChanged(elevation2, this.hoveredFocusedTranslationZ, this.pressedTranslationZ);
        }
    }

    /* access modifiers changed from: package-private */
    public float getElevation() {
        return this.elevation;
    }

    /* access modifiers changed from: package-private */
    public float getHoveredFocusedTranslationZ() {
        return this.hoveredFocusedTranslationZ;
    }

    /* access modifiers changed from: package-private */
    public float getPressedTranslationZ() {
        return this.pressedTranslationZ;
    }

    /* access modifiers changed from: package-private */
    public final void setHoveredFocusedTranslationZ(float translationZ) {
        if (this.hoveredFocusedTranslationZ != translationZ) {
            this.hoveredFocusedTranslationZ = translationZ;
            onElevationsChanged(this.elevation, translationZ, this.pressedTranslationZ);
        }
    }

    /* access modifiers changed from: package-private */
    public final void setPressedTranslationZ(float translationZ) {
        if (this.pressedTranslationZ != translationZ) {
            this.pressedTranslationZ = translationZ;
            onElevationsChanged(this.elevation, this.hoveredFocusedTranslationZ, translationZ);
        }
    }

    /* access modifiers changed from: package-private */
    public final void setMaxImageSize(int maxImageSize2) {
        if (this.maxImageSize != maxImageSize2) {
            this.maxImageSize = maxImageSize2;
            updateImageMatrixScale();
        }
    }

    /* access modifiers changed from: package-private */
    public final void updateImageMatrixScale() {
        setImageMatrixScale(this.imageMatrixScale);
    }

    /* access modifiers changed from: package-private */
    public final void setImageMatrixScale(float scale) {
        this.imageMatrixScale = scale;
        Matrix matrix = this.tmpMatrix;
        calculateImageMatrixFromScale(scale, matrix);
        this.view.setImageMatrix(matrix);
    }

    /* access modifiers changed from: private */
    public void calculateImageMatrixFromScale(float scale, Matrix matrix) {
        matrix.reset();
        Drawable drawable = this.view.getDrawable();
        if (drawable != null && this.maxImageSize != 0) {
            RectF drawableBounds = this.tmpRectF1;
            RectF imageBounds = this.tmpRectF2;
            drawableBounds.set(0.0f, 0.0f, (float) drawable.getIntrinsicWidth(), (float) drawable.getIntrinsicHeight());
            int i = this.maxImageSize;
            imageBounds.set(0.0f, 0.0f, (float) i, (float) i);
            matrix.setRectToRect(drawableBounds, imageBounds, Matrix.ScaleToFit.CENTER);
            int i2 = this.maxImageSize;
            matrix.postScale(scale, scale, ((float) i2) / 2.0f, ((float) i2) / 2.0f);
        }
    }

    /* access modifiers changed from: package-private */
    public final void setShapeAppearance(ShapeAppearanceModel shapeAppearance2) {
        this.shapeAppearance = shapeAppearance2;
        MaterialShapeDrawable materialShapeDrawable = this.shapeDrawable;
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setShapeAppearanceModel(shapeAppearance2);
        }
        Drawable drawable = this.rippleDrawable;
        if (drawable instanceof Shapeable) {
            ((Shapeable) drawable).setShapeAppearanceModel(shapeAppearance2);
        }
        BorderDrawable borderDrawable2 = this.borderDrawable;
        if (borderDrawable2 != null) {
            borderDrawable2.setShapeAppearanceModel(shapeAppearance2);
        }
    }

    /* access modifiers changed from: package-private */
    public final ShapeAppearanceModel getShapeAppearance() {
        return this.shapeAppearance;
    }

    /* access modifiers changed from: package-private */
    public final MotionSpec getShowMotionSpec() {
        return this.showMotionSpec;
    }

    /* access modifiers changed from: package-private */
    public final void setShowMotionSpec(MotionSpec spec) {
        this.showMotionSpec = spec;
    }

    /* access modifiers changed from: package-private */
    public final MotionSpec getHideMotionSpec() {
        return this.hideMotionSpec;
    }

    /* access modifiers changed from: package-private */
    public final void setHideMotionSpec(MotionSpec spec) {
        this.hideMotionSpec = spec;
    }

    /* access modifiers changed from: package-private */
    public final boolean shouldExpandBoundsForA11y() {
        return !this.ensureMinTouchTargetSize || this.view.getSizeDimension() >= this.minTouchTargetSize;
    }

    /* access modifiers changed from: package-private */
    public boolean getEnsureMinTouchTargetSize() {
        return this.ensureMinTouchTargetSize;
    }

    /* access modifiers changed from: package-private */
    public void setEnsureMinTouchTargetSize(boolean flag) {
        this.ensureMinTouchTargetSize = flag;
    }

    /* access modifiers changed from: package-private */
    public void setShadowPaddingEnabled(boolean shadowPaddingEnabled2) {
        this.shadowPaddingEnabled = shadowPaddingEnabled2;
        updatePadding();
    }

    /* access modifiers changed from: package-private */
    public void onElevationsChanged(float elevation2, float hoveredFocusedTranslationZ2, float pressedTranslationZ2) {
        updatePadding();
        updateShapeElevation(elevation2);
    }

    /* access modifiers changed from: package-private */
    public void updateShapeElevation(float elevation2) {
        MaterialShapeDrawable materialShapeDrawable = this.shapeDrawable;
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setElevation(elevation2);
        }
    }

    /* access modifiers changed from: package-private */
    public void onDrawableStateChanged(int[] state) {
        this.stateListAnimator.setState(state);
    }

    /* access modifiers changed from: package-private */
    public void jumpDrawableToCurrentState() {
        this.stateListAnimator.jumpToCurrentState();
    }

    /* access modifiers changed from: package-private */
    public void addOnShowAnimationListener(Animator.AnimatorListener listener) {
        if (this.showListeners == null) {
            this.showListeners = new ArrayList<>();
        }
        this.showListeners.add(listener);
    }

    /* access modifiers changed from: package-private */
    public void removeOnShowAnimationListener(Animator.AnimatorListener listener) {
        ArrayList<Animator.AnimatorListener> arrayList = this.showListeners;
        if (arrayList != null) {
            arrayList.remove(listener);
        }
    }

    public void addOnHideAnimationListener(Animator.AnimatorListener listener) {
        if (this.hideListeners == null) {
            this.hideListeners = new ArrayList<>();
        }
        this.hideListeners.add(listener);
    }

    public void removeOnHideAnimationListener(Animator.AnimatorListener listener) {
        ArrayList<Animator.AnimatorListener> arrayList = this.hideListeners;
        if (arrayList != null) {
            arrayList.remove(listener);
        }
    }

    /* access modifiers changed from: package-private */
    public void hide(final InternalVisibilityChangedListener listener, final boolean fromUser) {
        AnimatorSet set;
        if (!isOrWillBeHidden()) {
            Animator animator = this.currentAnimator;
            if (animator != null) {
                animator.cancel();
            }
            if (shouldAnimateVisibilityChange()) {
                MotionSpec motionSpec = this.hideMotionSpec;
                if (motionSpec != null) {
                    set = createAnimator(motionSpec, 0.0f, 0.0f, 0.0f);
                } else {
                    set = createDefaultAnimator(0.0f, 0.4f, 0.4f, HIDE_ANIM_DURATION_ATTR, HIDE_ANIM_EASING_ATTR);
                }
                set.addListener(new AnimatorListenerAdapter() {
                    private boolean cancelled;

                    public void onAnimationStart(Animator animation) {
                        FloatingActionButtonImpl.this.view.internalSetVisibility(0, fromUser);
                        int unused = FloatingActionButtonImpl.this.animState = 1;
                        Animator unused2 = FloatingActionButtonImpl.this.currentAnimator = animation;
                        this.cancelled = false;
                    }

                    public void onAnimationCancel(Animator animation) {
                        this.cancelled = true;
                    }

                    public void onAnimationEnd(Animator animation) {
                        int unused = FloatingActionButtonImpl.this.animState = 0;
                        Animator unused2 = FloatingActionButtonImpl.this.currentAnimator = null;
                        if (!this.cancelled) {
                            FloatingActionButton floatingActionButton = FloatingActionButtonImpl.this.view;
                            boolean z = fromUser;
                            floatingActionButton.internalSetVisibility(z ? 8 : 4, z);
                            InternalVisibilityChangedListener internalVisibilityChangedListener = listener;
                            if (internalVisibilityChangedListener != null) {
                                internalVisibilityChangedListener.onHidden();
                            }
                        }
                    }
                });
                ArrayList<Animator.AnimatorListener> arrayList = this.hideListeners;
                if (arrayList != null) {
                    Iterator<Animator.AnimatorListener> it = arrayList.iterator();
                    while (it.hasNext()) {
                        set.addListener(it.next());
                    }
                }
                set.start();
                return;
            }
            this.view.internalSetVisibility(fromUser ? 8 : 4, fromUser);
            if (listener != null) {
                listener.onHidden();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void show(final InternalVisibilityChangedListener listener, final boolean fromUser) {
        AnimatorSet set;
        if (!isOrWillBeShown()) {
            Animator animator = this.currentAnimator;
            if (animator != null) {
                animator.cancel();
            }
            boolean useDefaultAnimation = this.showMotionSpec == null;
            if (shouldAnimateVisibilityChange()) {
                if (this.view.getVisibility() != 0) {
                    float f = 0.0f;
                    this.view.setAlpha(0.0f);
                    this.view.setScaleY(useDefaultAnimation ? 0.4f : 0.0f);
                    this.view.setScaleX(useDefaultAnimation ? 0.4f : 0.0f);
                    if (useDefaultAnimation) {
                        f = 0.4f;
                    }
                    setImageMatrixScale(f);
                }
                MotionSpec motionSpec = this.showMotionSpec;
                if (motionSpec != null) {
                    set = createAnimator(motionSpec, 1.0f, 1.0f, 1.0f);
                } else {
                    set = createDefaultAnimator(1.0f, 1.0f, 1.0f, SHOW_ANIM_DURATION_ATTR, SHOW_ANIM_EASING_ATTR);
                }
                set.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationStart(Animator animation) {
                        FloatingActionButtonImpl.this.view.internalSetVisibility(0, fromUser);
                        int unused = FloatingActionButtonImpl.this.animState = 2;
                        Animator unused2 = FloatingActionButtonImpl.this.currentAnimator = animation;
                    }

                    public void onAnimationEnd(Animator animation) {
                        int unused = FloatingActionButtonImpl.this.animState = 0;
                        Animator unused2 = FloatingActionButtonImpl.this.currentAnimator = null;
                        InternalVisibilityChangedListener internalVisibilityChangedListener = listener;
                        if (internalVisibilityChangedListener != null) {
                            internalVisibilityChangedListener.onShown();
                        }
                    }
                });
                ArrayList<Animator.AnimatorListener> arrayList = this.showListeners;
                if (arrayList != null) {
                    Iterator<Animator.AnimatorListener> it = arrayList.iterator();
                    while (it.hasNext()) {
                        set.addListener(it.next());
                    }
                }
                set.start();
                return;
            }
            this.view.internalSetVisibility(0, fromUser);
            this.view.setAlpha(1.0f);
            this.view.setScaleY(1.0f);
            this.view.setScaleX(1.0f);
            setImageMatrixScale(1.0f);
            if (listener != null) {
                listener.onShown();
            }
        }
    }

    private AnimatorSet createAnimator(MotionSpec spec, float opacity, float scale, float iconScale) {
        List<Animator> animators = new ArrayList<>();
        ObjectAnimator animatorOpacity = ObjectAnimator.ofFloat(this.view, View.ALPHA, new float[]{opacity});
        spec.getTiming("opacity").apply(animatorOpacity);
        animators.add(animatorOpacity);
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(this.view, View.SCALE_X, new float[]{scale});
        spec.getTiming(CommonCssConstants.SCALE).apply(animatorScaleX);
        workAroundOreoBug(animatorScaleX);
        animators.add(animatorScaleX);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(this.view, View.SCALE_Y, new float[]{scale});
        spec.getTiming(CommonCssConstants.SCALE).apply(animatorScaleY);
        workAroundOreoBug(animatorScaleY);
        animators.add(animatorScaleY);
        calculateImageMatrixFromScale(iconScale, this.tmpMatrix);
        ObjectAnimator animatorIconScale = ObjectAnimator.ofObject(this.view, new ImageMatrixProperty(), new MatrixEvaluator() {
            public Matrix evaluate(float fraction, Matrix startValue, Matrix endValue) {
                float unused = FloatingActionButtonImpl.this.imageMatrixScale = fraction;
                return super.evaluate(fraction, startValue, endValue);
            }
        }, new Matrix[]{new Matrix(this.tmpMatrix)});
        spec.getTiming("iconScale").apply(animatorIconScale);
        animators.add(animatorIconScale);
        AnimatorSet set = new AnimatorSet();
        AnimatorSetCompat.playTogether(set, animators);
        return set;
    }

    private AnimatorSet createDefaultAnimator(float targetOpacity, float targetScale, float targetIconScale, int duration, int interpolator) {
        AnimatorSet set = new AnimatorSet();
        List<Animator> animators = new ArrayList<>();
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        float startAlpha = this.view.getAlpha();
        float startScaleX = this.view.getScaleX();
        float startScaleY = this.view.getScaleY();
        float startImageMatrixScale = this.imageMatrixScale;
        final Matrix matrix = new Matrix(this.tmpMatrix);
        final float f = startAlpha;
        final float f2 = targetOpacity;
        final float f3 = startScaleX;
        final float f4 = targetScale;
        final float f5 = startScaleY;
        float f6 = startAlpha;
        C12084 r14 = r0;
        final float f7 = startImageMatrixScale;
        float f8 = startImageMatrixScale;
        final float startImageMatrixScale2 = targetIconScale;
        C12084 r0 = new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = ((Float) animation.getAnimatedValue()).floatValue();
                FloatingActionButtonImpl.this.view.setAlpha(AnimationUtils.lerp(f, f2, 0.0f, 0.2f, progress));
                FloatingActionButtonImpl.this.view.setScaleX(AnimationUtils.lerp(f3, f4, progress));
                FloatingActionButtonImpl.this.view.setScaleY(AnimationUtils.lerp(f5, f4, progress));
                float unused = FloatingActionButtonImpl.this.imageMatrixScale = AnimationUtils.lerp(f7, startImageMatrixScale2, progress);
                FloatingActionButtonImpl.this.calculateImageMatrixFromScale(AnimationUtils.lerp(f7, startImageMatrixScale2, progress), matrix);
                FloatingActionButtonImpl.this.view.setImageMatrix(matrix);
            }
        };
        animator.addUpdateListener(r14);
        animators.add(animator);
        AnimatorSetCompat.playTogether(set, animators);
        set.setDuration((long) MotionUtils.resolveThemeDuration(this.view.getContext(), duration, this.view.getContext().getResources().getInteger(C1087R.integer.material_motion_duration_long_1)));
        set.setInterpolator(MotionUtils.resolveThemeInterpolator(this.view.getContext(), interpolator, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        return set;
    }

    private void workAroundOreoBug(ObjectAnimator animator) {
        if (Build.VERSION.SDK_INT == 26) {
            animator.setEvaluator(new TypeEvaluator<Float>() {
                FloatEvaluator floatEvaluator = new FloatEvaluator();

                public Float evaluate(float fraction, Float startValue, Float endValue) {
                    float evaluated = this.floatEvaluator.evaluate(fraction, startValue, endValue).floatValue();
                    return Float.valueOf(evaluated < 0.1f ? 0.0f : evaluated);
                }
            });
        }
    }

    /* access modifiers changed from: package-private */
    public void addTransformationCallback(InternalTransformationCallback listener) {
        if (this.transformationCallbacks == null) {
            this.transformationCallbacks = new ArrayList<>();
        }
        this.transformationCallbacks.add(listener);
    }

    /* access modifiers changed from: package-private */
    public void removeTransformationCallback(InternalTransformationCallback listener) {
        ArrayList<InternalTransformationCallback> arrayList = this.transformationCallbacks;
        if (arrayList != null) {
            arrayList.remove(listener);
        }
    }

    /* access modifiers changed from: package-private */
    public void onTranslationChanged() {
        ArrayList<InternalTransformationCallback> arrayList = this.transformationCallbacks;
        if (arrayList != null) {
            Iterator<InternalTransformationCallback> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().onTranslationChanged();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onScaleChanged() {
        ArrayList<InternalTransformationCallback> arrayList = this.transformationCallbacks;
        if (arrayList != null) {
            Iterator<InternalTransformationCallback> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().onScaleChanged();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final Drawable getContentBackground() {
        return this.contentBackground;
    }

    /* access modifiers changed from: package-private */
    public void onCompatShadowChanged() {
    }

    /* access modifiers changed from: package-private */
    public final void updatePadding() {
        Rect rect = this.tmpRect;
        getPadding(rect);
        onPaddingUpdated(rect);
        this.shadowViewDelegate.setShadowPadding(rect.left, rect.top, rect.right, rect.bottom);
    }

    /* access modifiers changed from: package-private */
    public void getPadding(Rect rect) {
        int minPadding;
        if (this.ensureMinTouchTargetSize) {
            minPadding = (this.minTouchTargetSize - this.view.getSizeDimension()) / 2;
        } else {
            minPadding = 0;
        }
        float maxShadowSize = this.shadowPaddingEnabled ? getElevation() + this.pressedTranslationZ : 0.0f;
        int hPadding = Math.max(minPadding, (int) Math.ceil((double) maxShadowSize));
        int vPadding = Math.max(minPadding, (int) Math.ceil((double) (SHADOW_MULTIPLIER * maxShadowSize)));
        rect.set(hPadding, vPadding, hPadding, vPadding);
    }

    /* access modifiers changed from: package-private */
    public void onPaddingUpdated(Rect padding) {
        Preconditions.checkNotNull(this.contentBackground, "Didn't initialize content background");
        if (shouldAddPadding()) {
            this.shadowViewDelegate.setBackgroundDrawable(new InsetDrawable(this.contentBackground, padding.left, padding.top, padding.right, padding.bottom));
            return;
        }
        this.shadowViewDelegate.setBackgroundDrawable(this.contentBackground);
    }

    /* access modifiers changed from: package-private */
    public boolean shouldAddPadding() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void onAttachedToWindow() {
        MaterialShapeDrawable materialShapeDrawable = this.shapeDrawable;
        if (materialShapeDrawable != null) {
            MaterialShapeUtils.setParentAbsoluteElevation(this.view, materialShapeDrawable);
        }
        if (requirePreDrawListener()) {
            this.view.getViewTreeObserver().addOnPreDrawListener(getOrCreatePreDrawListener());
        }
    }

    /* access modifiers changed from: package-private */
    public void onDetachedFromWindow() {
        ViewTreeObserver viewTreeObserver = this.view.getViewTreeObserver();
        ViewTreeObserver.OnPreDrawListener onPreDrawListener = this.preDrawListener;
        if (onPreDrawListener != null) {
            viewTreeObserver.removeOnPreDrawListener(onPreDrawListener);
            this.preDrawListener = null;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean requirePreDrawListener() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void onPreDraw() {
        float rotation2 = this.view.getRotation();
        if (this.rotation != rotation2) {
            this.rotation = rotation2;
            updateFromViewRotation();
        }
    }

    private ViewTreeObserver.OnPreDrawListener getOrCreatePreDrawListener() {
        if (this.preDrawListener == null) {
            this.preDrawListener = new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {
                    FloatingActionButtonImpl.this.onPreDraw();
                    return true;
                }
            };
        }
        return this.preDrawListener;
    }

    /* access modifiers changed from: package-private */
    public MaterialShapeDrawable createShapeDrawable() {
        return new MaterialShapeDrawable((ShapeAppearanceModel) Preconditions.checkNotNull(this.shapeAppearance));
    }

    /* access modifiers changed from: package-private */
    public boolean isOrWillBeShown() {
        if (this.view.getVisibility() != 0) {
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

    /* access modifiers changed from: package-private */
    public boolean isOrWillBeHidden() {
        if (this.view.getVisibility() == 0) {
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

    private ValueAnimator createElevationAnimator(ShadowAnimatorImpl impl) {
        ValueAnimator animator = new ValueAnimator();
        animator.setInterpolator(ELEVATION_ANIM_INTERPOLATOR);
        animator.setDuration(100);
        animator.addListener(impl);
        animator.addUpdateListener(impl);
        animator.setFloatValues(new float[]{0.0f, 1.0f});
        return animator;
    }

    private abstract class ShadowAnimatorImpl extends AnimatorListenerAdapter implements ValueAnimator.AnimatorUpdateListener {
        private float shadowSizeEnd;
        private float shadowSizeStart;
        private boolean validValues;

        /* access modifiers changed from: protected */
        public abstract float getTargetShadowSize();

        private ShadowAnimatorImpl() {
        }

        public void onAnimationUpdate(ValueAnimator animator) {
            if (!this.validValues) {
                this.shadowSizeStart = FloatingActionButtonImpl.this.shapeDrawable == null ? 0.0f : FloatingActionButtonImpl.this.shapeDrawable.getElevation();
                this.shadowSizeEnd = getTargetShadowSize();
                this.validValues = true;
            }
            FloatingActionButtonImpl floatingActionButtonImpl = FloatingActionButtonImpl.this;
            float f = this.shadowSizeStart;
            floatingActionButtonImpl.updateShapeElevation((float) ((int) (f + ((this.shadowSizeEnd - f) * animator.getAnimatedFraction()))));
        }

        public void onAnimationEnd(Animator animator) {
            FloatingActionButtonImpl.this.updateShapeElevation((float) ((int) this.shadowSizeEnd));
            this.validValues = false;
        }
    }

    private class ResetElevationAnimation extends ShadowAnimatorImpl {
        ResetElevationAnimation() {
            super();
        }

        /* access modifiers changed from: protected */
        public float getTargetShadowSize() {
            return FloatingActionButtonImpl.this.elevation;
        }
    }

    private class ElevateToHoveredFocusedTranslationZAnimation extends ShadowAnimatorImpl {
        ElevateToHoveredFocusedTranslationZAnimation() {
            super();
        }

        /* access modifiers changed from: protected */
        public float getTargetShadowSize() {
            return FloatingActionButtonImpl.this.elevation + FloatingActionButtonImpl.this.hoveredFocusedTranslationZ;
        }
    }

    private class ElevateToPressedTranslationZAnimation extends ShadowAnimatorImpl {
        ElevateToPressedTranslationZAnimation() {
            super();
        }

        /* access modifiers changed from: protected */
        public float getTargetShadowSize() {
            return FloatingActionButtonImpl.this.elevation + FloatingActionButtonImpl.this.pressedTranslationZ;
        }
    }

    private class DisabledElevationAnimation extends ShadowAnimatorImpl {
        DisabledElevationAnimation() {
            super();
        }

        /* access modifiers changed from: protected */
        public float getTargetShadowSize() {
            return 0.0f;
        }
    }

    private boolean shouldAnimateVisibilityChange() {
        return ViewCompat.isLaidOut(this.view) && !this.view.isInEditMode();
    }

    /* access modifiers changed from: package-private */
    public void updateFromViewRotation() {
        if (Build.VERSION.SDK_INT == 19) {
            if (this.rotation % 90.0f != 0.0f) {
                if (this.view.getLayerType() != 1) {
                    this.view.setLayerType(1, (Paint) null);
                }
            } else if (this.view.getLayerType() != 0) {
                this.view.setLayerType(0, (Paint) null);
            }
        }
        MaterialShapeDrawable materialShapeDrawable = this.shapeDrawable;
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setShadowCompatRotation((int) this.rotation);
        }
    }
}
