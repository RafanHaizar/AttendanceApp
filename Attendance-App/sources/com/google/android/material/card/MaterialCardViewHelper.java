package com.google.android.material.card;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.C1087R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.CornerTreatment;
import com.google.android.material.shape.CutCornerTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.RoundedCornerTreatment;
import com.google.android.material.shape.ShapeAppearanceModel;

class MaterialCardViewHelper {
    private static final float CARD_VIEW_SHADOW_MULTIPLIER = 1.5f;
    private static final int CHECKED_ICON_LAYER_INDEX = 2;
    private static final Drawable CHECKED_ICON_NONE = (Build.VERSION.SDK_INT <= 28 ? new ColorDrawable() : null);
    private static final double COS_45 = Math.cos(Math.toRadians(45.0d));
    public static final int DEFAULT_FADE_ANIM_DURATION = 300;
    private static final int DEFAULT_STROKE_VALUE = -1;
    private final MaterialShapeDrawable bgDrawable;
    private boolean checkable;
    private float checkedAnimationProgress = 0.0f;
    private Drawable checkedIcon;
    private int checkedIconGravity;
    private int checkedIconMargin;
    private int checkedIconSize;
    private ColorStateList checkedIconTint;
    private LayerDrawable clickableForegroundDrawable;
    private MaterialShapeDrawable compatRippleDrawable;
    private Drawable fgDrawable;
    private final MaterialShapeDrawable foregroundContentDrawable;
    private MaterialShapeDrawable foregroundShapeDrawable;
    private ValueAnimator iconAnimator;
    private final TimeInterpolator iconFadeAnimInterpolator;
    private final int iconFadeInAnimDuration;
    private final int iconFadeOutAnimDuration;
    private boolean isBackgroundOverwritten = false;
    private final MaterialCardView materialCardView;
    private ColorStateList rippleColor;
    private Drawable rippleDrawable;
    private ShapeAppearanceModel shapeAppearanceModel;
    private ColorStateList strokeColor;
    private int strokeWidth;
    private final Rect userContentPadding = new Rect();

    public MaterialCardViewHelper(MaterialCardView card, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this.materialCardView = card;
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(card.getContext(), attrs, defStyleAttr, defStyleRes);
        this.bgDrawable = materialShapeDrawable;
        materialShapeDrawable.initializeElevationOverlay(card.getContext());
        materialShapeDrawable.setShadowColor(-12303292);
        ShapeAppearanceModel.Builder shapeAppearanceModelBuilder = materialShapeDrawable.getShapeAppearanceModel().toBuilder();
        TypedArray cardViewAttributes = card.getContext().obtainStyledAttributes(attrs, C1087R.styleable.CardView, defStyleAttr, C1087R.C1093style.CardView);
        if (cardViewAttributes.hasValue(C1087R.styleable.CardView_cardCornerRadius)) {
            shapeAppearanceModelBuilder.setAllCornerSizes(cardViewAttributes.getDimension(C1087R.styleable.CardView_cardCornerRadius, 0.0f));
        }
        this.foregroundContentDrawable = new MaterialShapeDrawable();
        setShapeAppearanceModel(shapeAppearanceModelBuilder.build());
        this.iconFadeAnimInterpolator = MotionUtils.resolveThemeInterpolator(card.getContext(), C1087R.attr.motionEasingLinearInterpolator, AnimationUtils.LINEAR_INTERPOLATOR);
        this.iconFadeInAnimDuration = MotionUtils.resolveThemeDuration(card.getContext(), C1087R.attr.motionDurationShort2, 300);
        this.iconFadeOutAnimDuration = MotionUtils.resolveThemeDuration(card.getContext(), C1087R.attr.motionDurationShort1, 300);
        cardViewAttributes.recycle();
    }

    /* access modifiers changed from: package-private */
    public void loadFromAttributes(TypedArray attributes) {
        ColorStateList colorStateList = MaterialResources.getColorStateList(this.materialCardView.getContext(), attributes, C1087R.styleable.MaterialCardView_strokeColor);
        this.strokeColor = colorStateList;
        if (colorStateList == null) {
            this.strokeColor = ColorStateList.valueOf(-1);
        }
        this.strokeWidth = attributes.getDimensionPixelSize(C1087R.styleable.MaterialCardView_strokeWidth, 0);
        boolean z = attributes.getBoolean(C1087R.styleable.MaterialCardView_android_checkable, false);
        this.checkable = z;
        this.materialCardView.setLongClickable(z);
        this.checkedIconTint = MaterialResources.getColorStateList(this.materialCardView.getContext(), attributes, C1087R.styleable.MaterialCardView_checkedIconTint);
        setCheckedIcon(MaterialResources.getDrawable(this.materialCardView.getContext(), attributes, C1087R.styleable.MaterialCardView_checkedIcon));
        setCheckedIconSize(attributes.getDimensionPixelSize(C1087R.styleable.MaterialCardView_checkedIconSize, 0));
        setCheckedIconMargin(attributes.getDimensionPixelSize(C1087R.styleable.MaterialCardView_checkedIconMargin, 0));
        this.checkedIconGravity = attributes.getInteger(C1087R.styleable.MaterialCardView_checkedIconGravity, 8388661);
        ColorStateList colorStateList2 = MaterialResources.getColorStateList(this.materialCardView.getContext(), attributes, C1087R.styleable.MaterialCardView_rippleColor);
        this.rippleColor = colorStateList2;
        if (colorStateList2 == null) {
            this.rippleColor = ColorStateList.valueOf(MaterialColors.getColor(this.materialCardView, C1087R.attr.colorControlHighlight));
        }
        setCardForegroundColor(MaterialResources.getColorStateList(this.materialCardView.getContext(), attributes, C1087R.styleable.MaterialCardView_cardForegroundColor));
        updateRippleColor();
        updateElevation();
        updateStroke();
        this.materialCardView.setBackgroundInternal(insetDrawable(this.bgDrawable));
        Drawable clickableForeground = this.materialCardView.isClickable() ? getClickableForeground() : this.foregroundContentDrawable;
        this.fgDrawable = clickableForeground;
        this.materialCardView.setForeground(insetDrawable(clickableForeground));
    }

    /* access modifiers changed from: package-private */
    public boolean isBackgroundOverwritten() {
        return this.isBackgroundOverwritten;
    }

    /* access modifiers changed from: package-private */
    public void setBackgroundOverwritten(boolean isBackgroundOverwritten2) {
        this.isBackgroundOverwritten = isBackgroundOverwritten2;
    }

    /* access modifiers changed from: package-private */
    public void setStrokeColor(ColorStateList strokeColor2) {
        if (this.strokeColor != strokeColor2) {
            this.strokeColor = strokeColor2;
            updateStroke();
        }
    }

    /* access modifiers changed from: package-private */
    public int getStrokeColor() {
        ColorStateList colorStateList = this.strokeColor;
        if (colorStateList == null) {
            return -1;
        }
        return colorStateList.getDefaultColor();
    }

    /* access modifiers changed from: package-private */
    public ColorStateList getStrokeColorStateList() {
        return this.strokeColor;
    }

    /* access modifiers changed from: package-private */
    public void setStrokeWidth(int strokeWidth2) {
        if (strokeWidth2 != this.strokeWidth) {
            this.strokeWidth = strokeWidth2;
            updateStroke();
        }
    }

    /* access modifiers changed from: package-private */
    public int getStrokeWidth() {
        return this.strokeWidth;
    }

    /* access modifiers changed from: package-private */
    public MaterialShapeDrawable getBackground() {
        return this.bgDrawable;
    }

    /* access modifiers changed from: package-private */
    public void setCardBackgroundColor(ColorStateList color) {
        this.bgDrawable.setFillColor(color);
    }

    /* access modifiers changed from: package-private */
    public ColorStateList getCardBackgroundColor() {
        return this.bgDrawable.getFillColor();
    }

    /* access modifiers changed from: package-private */
    public void setCardForegroundColor(ColorStateList foregroundColor) {
        this.foregroundContentDrawable.setFillColor(foregroundColor == null ? ColorStateList.valueOf(0) : foregroundColor);
    }

    /* access modifiers changed from: package-private */
    public ColorStateList getCardForegroundColor() {
        return this.foregroundContentDrawable.getFillColor();
    }

    /* access modifiers changed from: package-private */
    public void setUserContentPadding(int left, int top, int right, int bottom) {
        this.userContentPadding.set(left, top, right, bottom);
        updateContentPadding();
    }

    /* access modifiers changed from: package-private */
    public Rect getUserContentPadding() {
        return this.userContentPadding;
    }

    /* access modifiers changed from: package-private */
    public void updateClickable() {
        Drawable previousFgDrawable = this.fgDrawable;
        Drawable clickableForeground = this.materialCardView.isClickable() ? getClickableForeground() : this.foregroundContentDrawable;
        this.fgDrawable = clickableForeground;
        if (previousFgDrawable != clickableForeground) {
            updateInsetForeground(clickableForeground);
        }
    }

    public void animateCheckedIcon(boolean checked) {
        long j;
        float targetCheckedProgress = checked ? 1.0f : 0.0f;
        float delta = checked ? 1.0f - this.checkedAnimationProgress : this.checkedAnimationProgress;
        ValueAnimator valueAnimator = this.iconAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.iconAnimator = null;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{this.checkedAnimationProgress, targetCheckedProgress});
        this.iconAnimator = ofFloat;
        ofFloat.addUpdateListener(new MaterialCardViewHelper$$ExternalSyntheticLambda0(this));
        this.iconAnimator.setInterpolator(this.iconFadeAnimInterpolator);
        ValueAnimator valueAnimator2 = this.iconAnimator;
        if (checked) {
            j = (long) (((float) this.iconFadeInAnimDuration) * delta);
        } else {
            j = (long) (((float) this.iconFadeOutAnimDuration) * delta);
        }
        valueAnimator2.setDuration(j);
        this.iconAnimator.start();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$animateCheckedIcon$0$com-google-android-material-card-MaterialCardViewHelper */
    public /* synthetic */ void mo21496xa4d79c2b(ValueAnimator animation) {
        float progress = ((Float) animation.getAnimatedValue()).floatValue();
        this.checkedIcon.setAlpha((int) (255.0f * progress));
        this.checkedAnimationProgress = progress;
    }

    /* access modifiers changed from: package-private */
    public void setCornerRadius(float cornerRadius) {
        setShapeAppearanceModel(this.shapeAppearanceModel.withCornerSize(cornerRadius));
        this.fgDrawable.invalidateSelf();
        if (shouldAddCornerPaddingOutsideCardBackground() || shouldAddCornerPaddingInsideCardBackground()) {
            updateContentPadding();
        }
        if (shouldAddCornerPaddingOutsideCardBackground()) {
            updateInsets();
        }
    }

    /* access modifiers changed from: package-private */
    public float getCornerRadius() {
        return this.bgDrawable.getTopLeftCornerResolvedSize();
    }

    /* access modifiers changed from: package-private */
    public void setProgress(float progress) {
        this.bgDrawable.setInterpolation(progress);
        MaterialShapeDrawable materialShapeDrawable = this.foregroundContentDrawable;
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setInterpolation(progress);
        }
        MaterialShapeDrawable materialShapeDrawable2 = this.foregroundShapeDrawable;
        if (materialShapeDrawable2 != null) {
            materialShapeDrawable2.setInterpolation(progress);
        }
    }

    /* access modifiers changed from: package-private */
    public float getProgress() {
        return this.bgDrawable.getInterpolation();
    }

    /* access modifiers changed from: package-private */
    public void updateElevation() {
        this.bgDrawable.setElevation(this.materialCardView.getCardElevation());
    }

    /* access modifiers changed from: package-private */
    public void updateInsets() {
        if (!isBackgroundOverwritten()) {
            this.materialCardView.setBackgroundInternal(insetDrawable(this.bgDrawable));
        }
        this.materialCardView.setForeground(insetDrawable(this.fgDrawable));
    }

    /* access modifiers changed from: package-private */
    public void updateStroke() {
        this.foregroundContentDrawable.setStroke((float) this.strokeWidth, this.strokeColor);
    }

    /* access modifiers changed from: package-private */
    public void updateContentPadding() {
        int contentPaddingOffset = (int) ((shouldAddCornerPaddingInsideCardBackground() || shouldAddCornerPaddingOutsideCardBackground() ? calculateActualCornerPadding() : 0.0f) - getParentCardViewCalculatedCornerPadding());
        this.materialCardView.setAncestorContentPadding(this.userContentPadding.left + contentPaddingOffset, this.userContentPadding.top + contentPaddingOffset, this.userContentPadding.right + contentPaddingOffset, this.userContentPadding.bottom + contentPaddingOffset);
    }

    /* access modifiers changed from: package-private */
    public void setCheckable(boolean checkable2) {
        this.checkable = checkable2;
    }

    /* access modifiers changed from: package-private */
    public boolean isCheckable() {
        return this.checkable;
    }

    /* access modifiers changed from: package-private */
    public void setRippleColor(ColorStateList rippleColor2) {
        this.rippleColor = rippleColor2;
        updateRippleColor();
    }

    /* access modifiers changed from: package-private */
    public void setCheckedIconTint(ColorStateList checkedIconTint2) {
        this.checkedIconTint = checkedIconTint2;
        Drawable drawable = this.checkedIcon;
        if (drawable != null) {
            DrawableCompat.setTintList(drawable, checkedIconTint2);
        }
    }

    /* access modifiers changed from: package-private */
    public ColorStateList getCheckedIconTint() {
        return this.checkedIconTint;
    }

    /* access modifiers changed from: package-private */
    public ColorStateList getRippleColor() {
        return this.rippleColor;
    }

    /* access modifiers changed from: package-private */
    public Drawable getCheckedIcon() {
        return this.checkedIcon;
    }

    /* access modifiers changed from: package-private */
    public void setCheckedIcon(Drawable checkedIcon2) {
        if (checkedIcon2 != null) {
            Drawable mutate = DrawableCompat.wrap(checkedIcon2).mutate();
            this.checkedIcon = mutate;
            DrawableCompat.setTintList(mutate, this.checkedIconTint);
            setChecked(this.materialCardView.isChecked());
        } else {
            this.checkedIcon = CHECKED_ICON_NONE;
        }
        LayerDrawable layerDrawable = this.clickableForegroundDrawable;
        if (layerDrawable != null) {
            layerDrawable.setDrawableByLayerId(C1087R.C1090id.mtrl_card_checked_layer_id, this.checkedIcon);
        }
    }

    /* access modifiers changed from: package-private */
    public int getCheckedIconSize() {
        return this.checkedIconSize;
    }

    /* access modifiers changed from: package-private */
    public void setCheckedIconSize(int checkedIconSize2) {
        this.checkedIconSize = checkedIconSize2;
    }

    /* access modifiers changed from: package-private */
    public int getCheckedIconMargin() {
        return this.checkedIconMargin;
    }

    /* access modifiers changed from: package-private */
    public void setCheckedIconMargin(int checkedIconMargin2) {
        this.checkedIconMargin = checkedIconMargin2;
    }

    /* access modifiers changed from: package-private */
    public void recalculateCheckedIconPosition(int measuredWidth, int measuredHeight) {
        int left;
        int bottom;
        int right;
        int top;
        if (this.clickableForegroundDrawable != null) {
            int verticalPaddingAdjustment = 0;
            int horizontalPaddingAdjustment = 0;
            if ((Build.VERSION.SDK_INT < 21) || this.materialCardView.getUseCompatPadding()) {
                verticalPaddingAdjustment = (int) Math.ceil((double) (calculateVerticalBackgroundPadding() * 2.0f));
                horizontalPaddingAdjustment = (int) Math.ceil((double) (calculateHorizontalBackgroundPadding() * 2.0f));
            }
            if (isCheckedIconEnd()) {
                left = ((measuredWidth - this.checkedIconMargin) - this.checkedIconSize) - horizontalPaddingAdjustment;
            } else {
                left = this.checkedIconMargin;
            }
            if (isCheckedIconBottom()) {
                bottom = this.checkedIconMargin;
            } else {
                bottom = ((measuredHeight - this.checkedIconMargin) - this.checkedIconSize) - verticalPaddingAdjustment;
            }
            if (isCheckedIconEnd()) {
                right = this.checkedIconMargin;
            } else {
                right = ((measuredWidth - this.checkedIconMargin) - this.checkedIconSize) - horizontalPaddingAdjustment;
            }
            if (isCheckedIconBottom()) {
                top = ((measuredHeight - this.checkedIconMargin) - this.checkedIconSize) - verticalPaddingAdjustment;
            } else {
                top = this.checkedIconMargin;
            }
            if (ViewCompat.getLayoutDirection(this.materialCardView) == 1) {
                int tmp = right;
                right = left;
                left = tmp;
            }
            this.clickableForegroundDrawable.setLayerInset(2, left, top, right, bottom);
        }
    }

    /* access modifiers changed from: package-private */
    public void forceRippleRedraw() {
        Drawable drawable = this.rippleDrawable;
        if (drawable != null) {
            Rect bounds = drawable.getBounds();
            int bottom = bounds.bottom;
            this.rippleDrawable.setBounds(bounds.left, bounds.top, bounds.right, bottom - 1);
            this.rippleDrawable.setBounds(bounds.left, bounds.top, bounds.right, bottom);
        }
    }

    /* access modifiers changed from: package-private */
    public void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel2) {
        this.shapeAppearanceModel = shapeAppearanceModel2;
        this.bgDrawable.setShapeAppearanceModel(shapeAppearanceModel2);
        MaterialShapeDrawable materialShapeDrawable = this.bgDrawable;
        materialShapeDrawable.setShadowBitmapDrawingEnable(!materialShapeDrawable.isRoundRect());
        MaterialShapeDrawable materialShapeDrawable2 = this.foregroundContentDrawable;
        if (materialShapeDrawable2 != null) {
            materialShapeDrawable2.setShapeAppearanceModel(shapeAppearanceModel2);
        }
        MaterialShapeDrawable materialShapeDrawable3 = this.foregroundShapeDrawable;
        if (materialShapeDrawable3 != null) {
            materialShapeDrawable3.setShapeAppearanceModel(shapeAppearanceModel2);
        }
        MaterialShapeDrawable materialShapeDrawable4 = this.compatRippleDrawable;
        if (materialShapeDrawable4 != null) {
            materialShapeDrawable4.setShapeAppearanceModel(shapeAppearanceModel2);
        }
    }

    /* access modifiers changed from: package-private */
    public ShapeAppearanceModel getShapeAppearanceModel() {
        return this.shapeAppearanceModel;
    }

    private void updateInsetForeground(Drawable insetForeground) {
        if (Build.VERSION.SDK_INT < 23 || !(this.materialCardView.getForeground() instanceof InsetDrawable)) {
            this.materialCardView.setForeground(insetDrawable(insetForeground));
        } else {
            ((InsetDrawable) this.materialCardView.getForeground()).setDrawable(insetForeground);
        }
    }

    private Drawable insetDrawable(Drawable originalDrawable) {
        int insetVertical = 0;
        int insetHorizontal = 0;
        if ((Build.VERSION.SDK_INT < 21) || this.materialCardView.getUseCompatPadding()) {
            insetVertical = (int) Math.ceil((double) calculateVerticalBackgroundPadding());
            insetHorizontal = (int) Math.ceil((double) calculateHorizontalBackgroundPadding());
        }
        return new InsetDrawable(originalDrawable, insetHorizontal, insetVertical, insetHorizontal, insetVertical) {
            public boolean getPadding(Rect padding) {
                return false;
            }

            public int getMinimumWidth() {
                return -1;
            }

            public int getMinimumHeight() {
                return -1;
            }
        };
    }

    private float calculateVerticalBackgroundPadding() {
        return (this.materialCardView.getMaxCardElevation() * CARD_VIEW_SHADOW_MULTIPLIER) + (shouldAddCornerPaddingOutsideCardBackground() ? calculateActualCornerPadding() : 0.0f);
    }

    private float calculateHorizontalBackgroundPadding() {
        return this.materialCardView.getMaxCardElevation() + (shouldAddCornerPaddingOutsideCardBackground() ? calculateActualCornerPadding() : 0.0f);
    }

    private boolean canClipToOutline() {
        return Build.VERSION.SDK_INT >= 21 && this.bgDrawable.isRoundRect();
    }

    private float getParentCardViewCalculatedCornerPadding() {
        if (!this.materialCardView.getPreventCornerOverlap()) {
            return 0.0f;
        }
        if (Build.VERSION.SDK_INT >= 21 && !this.materialCardView.getUseCompatPadding()) {
            return 0.0f;
        }
        double d = 1.0d - COS_45;
        double cardViewRadius = (double) this.materialCardView.getCardViewRadius();
        Double.isNaN(cardViewRadius);
        return (float) (d * cardViewRadius);
    }

    private boolean shouldAddCornerPaddingInsideCardBackground() {
        return this.materialCardView.getPreventCornerOverlap() && !canClipToOutline();
    }

    private boolean shouldAddCornerPaddingOutsideCardBackground() {
        return this.materialCardView.getPreventCornerOverlap() && canClipToOutline() && this.materialCardView.getUseCompatPadding();
    }

    private float calculateActualCornerPadding() {
        return Math.max(Math.max(calculateCornerPaddingForCornerTreatment(this.shapeAppearanceModel.getTopLeftCorner(), this.bgDrawable.getTopLeftCornerResolvedSize()), calculateCornerPaddingForCornerTreatment(this.shapeAppearanceModel.getTopRightCorner(), this.bgDrawable.getTopRightCornerResolvedSize())), Math.max(calculateCornerPaddingForCornerTreatment(this.shapeAppearanceModel.getBottomRightCorner(), this.bgDrawable.getBottomRightCornerResolvedSize()), calculateCornerPaddingForCornerTreatment(this.shapeAppearanceModel.getBottomLeftCorner(), this.bgDrawable.getBottomLeftCornerResolvedSize())));
    }

    private float calculateCornerPaddingForCornerTreatment(CornerTreatment treatment, float size) {
        if (treatment instanceof RoundedCornerTreatment) {
            double d = 1.0d - COS_45;
            double d2 = (double) size;
            Double.isNaN(d2);
            return (float) (d * d2);
        } else if (treatment instanceof CutCornerTreatment) {
            return size / 2.0f;
        } else {
            return 0.0f;
        }
    }

    private Drawable getClickableForeground() {
        if (this.rippleDrawable == null) {
            this.rippleDrawable = createForegroundRippleDrawable();
        }
        if (this.clickableForegroundDrawable == null) {
            LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{this.rippleDrawable, this.foregroundContentDrawable, this.checkedIcon});
            this.clickableForegroundDrawable = layerDrawable;
            layerDrawable.setId(2, C1087R.C1090id.mtrl_card_checked_layer_id);
        }
        return this.clickableForegroundDrawable;
    }

    private Drawable createForegroundRippleDrawable() {
        if (!RippleUtils.USE_FRAMEWORK_RIPPLE) {
            return createCompatRippleDrawable();
        }
        this.foregroundShapeDrawable = createForegroundShapeDrawable();
        return new RippleDrawable(this.rippleColor, (Drawable) null, this.foregroundShapeDrawable);
    }

    private Drawable createCompatRippleDrawable() {
        StateListDrawable rippleDrawable2 = new StateListDrawable();
        MaterialShapeDrawable createForegroundShapeDrawable = createForegroundShapeDrawable();
        this.compatRippleDrawable = createForegroundShapeDrawable;
        createForegroundShapeDrawable.setFillColor(this.rippleColor);
        rippleDrawable2.addState(new int[]{16842919}, this.compatRippleDrawable);
        return rippleDrawable2;
    }

    private void updateRippleColor() {
        Drawable drawable;
        if (!RippleUtils.USE_FRAMEWORK_RIPPLE || (drawable = this.rippleDrawable) == null) {
            MaterialShapeDrawable materialShapeDrawable = this.compatRippleDrawable;
            if (materialShapeDrawable != null) {
                materialShapeDrawable.setFillColor(this.rippleColor);
                return;
            }
            return;
        }
        ((RippleDrawable) drawable).setColor(this.rippleColor);
    }

    private MaterialShapeDrawable createForegroundShapeDrawable() {
        return new MaterialShapeDrawable(this.shapeAppearanceModel);
    }

    public void setChecked(boolean checked) {
        setChecked(checked, false);
    }

    public void setChecked(boolean checked, boolean animate) {
        Drawable drawable = this.checkedIcon;
        if (drawable == null) {
            return;
        }
        if (animate) {
            animateCheckedIcon(checked);
            return;
        }
        drawable.setAlpha(checked ? 255 : 0);
        this.checkedAnimationProgress = checked ? 1.0f : 0.0f;
    }

    /* access modifiers changed from: package-private */
    public int getCheckedIconGravity() {
        return this.checkedIconGravity;
    }

    /* access modifiers changed from: package-private */
    public void setCheckedIconGravity(int checkedIconGravity2) {
        this.checkedIconGravity = checkedIconGravity2;
        recalculateCheckedIconPosition(this.materialCardView.getMeasuredWidth(), this.materialCardView.getMeasuredHeight());
    }

    private boolean isCheckedIconEnd() {
        return (this.checkedIconGravity & GravityCompat.END) == 8388613;
    }

    private boolean isCheckedIconBottom() {
        return (this.checkedIconGravity & 80) == 80;
    }
}
