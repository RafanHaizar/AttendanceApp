package com.google.android.material.button;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.CompoundButton;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.C1087R;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class MaterialButton extends AppCompatButton implements Checkable, Shapeable {
    private static final int[] CHECKABLE_STATE_SET = {16842911};
    private static final int[] CHECKED_STATE_SET = {16842912};
    private static final int DEF_STYLE_RES = C1087R.C1093style.Widget_MaterialComponents_Button;
    public static final int ICON_GRAVITY_END = 3;
    public static final int ICON_GRAVITY_START = 1;
    public static final int ICON_GRAVITY_TEXT_END = 4;
    public static final int ICON_GRAVITY_TEXT_START = 2;
    public static final int ICON_GRAVITY_TEXT_TOP = 32;
    public static final int ICON_GRAVITY_TOP = 16;
    private static final String LOG_TAG = "MaterialButton";
    private boolean broadcasting;
    private boolean checked;
    private Drawable icon;
    private int iconGravity;
    private int iconLeft;
    private int iconPadding;
    private int iconSize;
    private ColorStateList iconTint;
    private PorterDuff.Mode iconTintMode;
    private int iconTop;
    private final MaterialButtonHelper materialButtonHelper;
    private final LinkedHashSet<OnCheckedChangeListener> onCheckedChangeListeners;
    private OnPressedChangeListener onPressedChangeListenerInternal;

    @Retention(RetentionPolicy.SOURCE)
    public @interface IconGravity {
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(MaterialButton materialButton, boolean z);
    }

    interface OnPressedChangeListener {
        void onPressedChanged(MaterialButton materialButton, boolean z);
    }

    public MaterialButton(Context context) {
        this(context, (AttributeSet) null);
    }

    public MaterialButton(Context context, AttributeSet attrs) {
        this(context, attrs, C1087R.attr.materialButtonStyle);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MaterialButton(android.content.Context r9, android.util.AttributeSet r10, int r11) {
        /*
            r8 = this;
            int r6 = DEF_STYLE_RES
            android.content.Context r0 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r9, r10, r11, r6)
            r8.<init>(r0, r10, r11)
            java.util.LinkedHashSet r0 = new java.util.LinkedHashSet
            r0.<init>()
            r8.onCheckedChangeListeners = r0
            r7 = 0
            r8.checked = r7
            r8.broadcasting = r7
            android.content.Context r9 = r8.getContext()
            int[] r2 = com.google.android.material.C1087R.styleable.MaterialButton
            int[] r5 = new int[r7]
            r0 = r9
            r1 = r10
            r3 = r11
            r4 = r6
            android.content.res.TypedArray r0 = com.google.android.material.internal.ThemeEnforcement.obtainStyledAttributes(r0, r1, r2, r3, r4, r5)
            int r1 = com.google.android.material.C1087R.styleable.MaterialButton_iconPadding
            int r1 = r0.getDimensionPixelSize(r1, r7)
            r8.iconPadding = r1
            int r1 = com.google.android.material.C1087R.styleable.MaterialButton_iconTintMode
            r2 = -1
            int r1 = r0.getInt(r1, r2)
            android.graphics.PorterDuff$Mode r2 = android.graphics.PorterDuff.Mode.SRC_IN
            android.graphics.PorterDuff$Mode r1 = com.google.android.material.internal.ViewUtils.parseTintMode(r1, r2)
            r8.iconTintMode = r1
            android.content.Context r1 = r8.getContext()
            int r2 = com.google.android.material.C1087R.styleable.MaterialButton_iconTint
            android.content.res.ColorStateList r1 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r1, (android.content.res.TypedArray) r0, (int) r2)
            r8.iconTint = r1
            android.content.Context r1 = r8.getContext()
            int r2 = com.google.android.material.C1087R.styleable.MaterialButton_icon
            android.graphics.drawable.Drawable r1 = com.google.android.material.resources.MaterialResources.getDrawable(r1, r0, r2)
            r8.icon = r1
            int r1 = com.google.android.material.C1087R.styleable.MaterialButton_iconGravity
            r2 = 1
            int r1 = r0.getInteger(r1, r2)
            r8.iconGravity = r1
            int r1 = com.google.android.material.C1087R.styleable.MaterialButton_iconSize
            int r1 = r0.getDimensionPixelSize(r1, r7)
            r8.iconSize = r1
            com.google.android.material.shape.ShapeAppearanceModel$Builder r1 = com.google.android.material.shape.ShapeAppearanceModel.builder((android.content.Context) r9, (android.util.AttributeSet) r10, (int) r11, (int) r6)
            com.google.android.material.shape.ShapeAppearanceModel r1 = r1.build()
            com.google.android.material.button.MaterialButtonHelper r3 = new com.google.android.material.button.MaterialButtonHelper
            r3.<init>(r8, r1)
            r8.materialButtonHelper = r3
            r3.loadFromAttributes(r0)
            r0.recycle()
            int r3 = r8.iconPadding
            r8.setCompoundDrawablePadding(r3)
            android.graphics.drawable.Drawable r3 = r8.icon
            if (r3 == 0) goto L_0x0086
            r7 = 1
        L_0x0086:
            r8.updateIcon(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.button.MaterialButton.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    private String getA11yClassName() {
        return (isCheckable() ? CompoundButton.class : Button.class).getName();
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(getA11yClassName());
        info.setCheckable(isCheckable());
        info.setChecked(isChecked());
        info.setClickable(isClickable());
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(getA11yClassName());
        accessibilityEvent.setChecked(isChecked());
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.checked = this.checked;
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        setChecked(savedState.checked);
    }

    public void setSupportBackgroundTintList(ColorStateList tint) {
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.setSupportBackgroundTintList(tint);
        } else {
            super.setSupportBackgroundTintList(tint);
        }
    }

    public ColorStateList getSupportBackgroundTintList() {
        if (isUsingOriginalBackground()) {
            return this.materialButtonHelper.getSupportBackgroundTintList();
        }
        return super.getSupportBackgroundTintList();
    }

    public void setSupportBackgroundTintMode(PorterDuff.Mode tintMode) {
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.setSupportBackgroundTintMode(tintMode);
        } else {
            super.setSupportBackgroundTintMode(tintMode);
        }
    }

    public PorterDuff.Mode getSupportBackgroundTintMode() {
        if (isUsingOriginalBackground()) {
            return this.materialButtonHelper.getSupportBackgroundTintMode();
        }
        return super.getSupportBackgroundTintMode();
    }

    public void setBackgroundTintList(ColorStateList tintList) {
        setSupportBackgroundTintList(tintList);
    }

    public ColorStateList getBackgroundTintList() {
        return getSupportBackgroundTintList();
    }

    public void setBackgroundTintMode(PorterDuff.Mode tintMode) {
        setSupportBackgroundTintMode(tintMode);
    }

    public PorterDuff.Mode getBackgroundTintMode() {
        return getSupportBackgroundTintMode();
    }

    public void setBackgroundColor(int color) {
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.setBackgroundColor(color);
        } else {
            super.setBackgroundColor(color);
        }
    }

    public void setBackground(Drawable background) {
        setBackgroundDrawable(background);
    }

    public void setBackgroundResource(int backgroundResourceId) {
        Drawable background = null;
        if (backgroundResourceId != 0) {
            background = AppCompatResources.getDrawable(getContext(), backgroundResourceId);
        }
        setBackgroundDrawable(background);
    }

    public void setBackgroundDrawable(Drawable background) {
        if (!isUsingOriginalBackground()) {
            super.setBackgroundDrawable(background);
        } else if (background != getBackground()) {
            Log.w(LOG_TAG, "MaterialButton manages its own background to control elevation, shape, color and states. Consider using backgroundTint, shapeAppearance and other attributes where available. A custom background will ignore these attributes and you should consider handling interaction states such as pressed, focused and disabled");
            this.materialButtonHelper.setBackgroundOverwritten();
            super.setBackgroundDrawable(background);
        } else {
            getBackground().setState(background.getState());
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        MaterialButtonHelper materialButtonHelper2;
        super.onLayout(changed, left, top, right, bottom);
        if (Build.VERSION.SDK_INT == 21 && (materialButtonHelper2 = this.materialButtonHelper) != null) {
            materialButtonHelper2.updateMaskBounds(bottom - top, right - left);
        }
        updateIconPosition(getMeasuredWidth(), getMeasuredHeight());
    }

    /* access modifiers changed from: protected */
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        super.onTextChanged(charSequence, i, i1, i2);
        updateIconPosition(getMeasuredWidth(), getMeasuredHeight());
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isUsingOriginalBackground()) {
            MaterialShapeUtils.setParentAbsoluteElevation(this, this.materialButtonHelper.getMaterialShapeDrawable());
        }
    }

    public void setElevation(float elevation) {
        super.setElevation(elevation);
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.getMaterialShapeDrawable().setElevation(elevation);
        }
    }

    public void refreshDrawableState() {
        super.refreshDrawableState();
        if (this.icon != null) {
            if (this.icon.setState(getDrawableState())) {
                invalidate();
            }
        }
    }

    public void setTextAlignment(int textAlignment) {
        super.setTextAlignment(textAlignment);
        updateIconPosition(getMeasuredWidth(), getMeasuredHeight());
    }

    private Layout.Alignment getGravityTextAlignment() {
        switch (getGravity() & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK) {
            case 1:
                return Layout.Alignment.ALIGN_CENTER;
            case 5:
            case GravityCompat.END:
                return Layout.Alignment.ALIGN_OPPOSITE;
            default:
                return Layout.Alignment.ALIGN_NORMAL;
        }
    }

    private Layout.Alignment getActualTextAlignment() {
        switch (getTextAlignment()) {
            case 1:
                return getGravityTextAlignment();
            case 3:
            case 6:
                return Layout.Alignment.ALIGN_OPPOSITE;
            case 4:
                return Layout.Alignment.ALIGN_CENTER;
            default:
                return Layout.Alignment.ALIGN_NORMAL;
        }
    }

    private void updateIconPosition(int buttonWidth, int buttonHeight) {
        if (this.icon != null && getLayout() != null) {
            if (isIconStart() || isIconEnd()) {
                this.iconTop = 0;
                Layout.Alignment textAlignment = getActualTextAlignment();
                int i = this.iconGravity;
                boolean z = true;
                if (i == 1 || i == 3 || ((i == 2 && textAlignment == Layout.Alignment.ALIGN_NORMAL) || (this.iconGravity == 4 && textAlignment == Layout.Alignment.ALIGN_OPPOSITE))) {
                    this.iconLeft = 0;
                    updateIcon(false);
                    return;
                }
                int localIconSize = this.iconSize;
                if (localIconSize == 0) {
                    localIconSize = this.icon.getIntrinsicWidth();
                }
                int availableWidth = ((((buttonWidth - getTextLayoutWidth()) - ViewCompat.getPaddingEnd(this)) - localIconSize) - this.iconPadding) - ViewCompat.getPaddingStart(this);
                int newIconLeft = textAlignment == Layout.Alignment.ALIGN_CENTER ? availableWidth / 2 : availableWidth;
                boolean isLayoutRTL = isLayoutRTL();
                if (this.iconGravity != 4) {
                    z = false;
                }
                if (isLayoutRTL != z) {
                    newIconLeft = -newIconLeft;
                }
                if (this.iconLeft != newIconLeft) {
                    this.iconLeft = newIconLeft;
                    updateIcon(false);
                }
            } else if (isIconTop()) {
                this.iconLeft = 0;
                if (this.iconGravity == 16) {
                    this.iconTop = 0;
                    updateIcon(false);
                    return;
                }
                int localIconSize2 = this.iconSize;
                if (localIconSize2 == 0) {
                    localIconSize2 = this.icon.getIntrinsicHeight();
                }
                int newIconTop = Math.max(0, (((((buttonHeight - getTextHeight()) - getPaddingTop()) - localIconSize2) - this.iconPadding) - getPaddingBottom()) / 2);
                if (this.iconTop != newIconTop) {
                    this.iconTop = newIconTop;
                    updateIcon(false);
                }
            }
        }
    }

    private int getTextLayoutWidth() {
        float maxWidth = 0.0f;
        int lineCount = getLineCount();
        for (int line = 0; line < lineCount; line++) {
            maxWidth = Math.max(maxWidth, getLayout().getLineWidth(line));
        }
        return (int) Math.ceil((double) maxWidth);
    }

    private int getTextHeight() {
        if (getLineCount() > 1) {
            return getLayout().getHeight();
        }
        Paint textPaint = getPaint();
        String buttonText = getText().toString();
        if (getTransformationMethod() != null) {
            buttonText = getTransformationMethod().getTransformation(buttonText, this).toString();
        }
        Rect bounds = new Rect();
        textPaint.getTextBounds(buttonText, 0, buttonText.length(), bounds);
        return Math.min(bounds.height(), getLayout().getHeight());
    }

    private boolean isLayoutRTL() {
        return ViewCompat.getLayoutDirection(this) == 1;
    }

    /* access modifiers changed from: package-private */
    public void setInternalBackground(Drawable background) {
        super.setBackgroundDrawable(background);
    }

    public void setIconPadding(int iconPadding2) {
        if (this.iconPadding != iconPadding2) {
            this.iconPadding = iconPadding2;
            setCompoundDrawablePadding(iconPadding2);
        }
    }

    public int getIconPadding() {
        return this.iconPadding;
    }

    public void setIconSize(int iconSize2) {
        if (iconSize2 < 0) {
            throw new IllegalArgumentException("iconSize cannot be less than 0");
        } else if (this.iconSize != iconSize2) {
            this.iconSize = iconSize2;
            updateIcon(true);
        }
    }

    public int getIconSize() {
        return this.iconSize;
    }

    public void setIcon(Drawable icon2) {
        if (this.icon != icon2) {
            this.icon = icon2;
            updateIcon(true);
            updateIconPosition(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    public void setIconResource(int iconResourceId) {
        Drawable icon2 = null;
        if (iconResourceId != 0) {
            icon2 = AppCompatResources.getDrawable(getContext(), iconResourceId);
        }
        setIcon(icon2);
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public void setIconTint(ColorStateList iconTint2) {
        if (this.iconTint != iconTint2) {
            this.iconTint = iconTint2;
            updateIcon(false);
        }
    }

    public void setIconTintResource(int iconTintResourceId) {
        setIconTint(AppCompatResources.getColorStateList(getContext(), iconTintResourceId));
    }

    public ColorStateList getIconTint() {
        return this.iconTint;
    }

    public void setIconTintMode(PorterDuff.Mode iconTintMode2) {
        if (this.iconTintMode != iconTintMode2) {
            this.iconTintMode = iconTintMode2;
            updateIcon(false);
        }
    }

    public PorterDuff.Mode getIconTintMode() {
        return this.iconTintMode;
    }

    private void updateIcon(boolean needsIconReset) {
        Drawable drawable = this.icon;
        boolean hasIconChanged = true;
        if (drawable != null) {
            Drawable mutate = DrawableCompat.wrap(drawable).mutate();
            this.icon = mutate;
            DrawableCompat.setTintList(mutate, this.iconTint);
            PorterDuff.Mode mode = this.iconTintMode;
            if (mode != null) {
                DrawableCompat.setTintMode(this.icon, mode);
            }
            int width = this.iconSize;
            if (width == 0) {
                width = this.icon.getIntrinsicWidth();
            }
            int height = this.iconSize;
            if (height == 0) {
                height = this.icon.getIntrinsicHeight();
            }
            Drawable drawable2 = this.icon;
            int i = this.iconLeft;
            int i2 = this.iconTop;
            drawable2.setBounds(i, i2, i + width, i2 + height);
            this.icon.setVisible(true, needsIconReset);
        }
        if (needsIconReset) {
            resetIconDrawable();
            return;
        }
        Drawable[] existingDrawables = TextViewCompat.getCompoundDrawablesRelative(this);
        Drawable drawableStart = existingDrawables[0];
        Drawable drawableTop = existingDrawables[1];
        Drawable drawableEnd = existingDrawables[2];
        if ((!isIconStart() || drawableStart == this.icon) && ((!isIconEnd() || drawableEnd == this.icon) && (!isIconTop() || drawableTop == this.icon))) {
            hasIconChanged = false;
        }
        if (hasIconChanged) {
            resetIconDrawable();
        }
    }

    private void resetIconDrawable() {
        if (isIconStart()) {
            TextViewCompat.setCompoundDrawablesRelative(this, this.icon, (Drawable) null, (Drawable) null, (Drawable) null);
        } else if (isIconEnd()) {
            TextViewCompat.setCompoundDrawablesRelative(this, (Drawable) null, (Drawable) null, this.icon, (Drawable) null);
        } else if (isIconTop()) {
            TextViewCompat.setCompoundDrawablesRelative(this, (Drawable) null, this.icon, (Drawable) null, (Drawable) null);
        }
    }

    private boolean isIconStart() {
        int i = this.iconGravity;
        return i == 1 || i == 2;
    }

    private boolean isIconEnd() {
        int i = this.iconGravity;
        return i == 3 || i == 4;
    }

    private boolean isIconTop() {
        int i = this.iconGravity;
        return i == 16 || i == 32;
    }

    public void setRippleColor(ColorStateList rippleColor) {
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.setRippleColor(rippleColor);
        }
    }

    public void setRippleColorResource(int rippleColorResourceId) {
        if (isUsingOriginalBackground()) {
            setRippleColor(AppCompatResources.getColorStateList(getContext(), rippleColorResourceId));
        }
    }

    public ColorStateList getRippleColor() {
        if (isUsingOriginalBackground()) {
            return this.materialButtonHelper.getRippleColor();
        }
        return null;
    }

    public void setStrokeColor(ColorStateList strokeColor) {
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.setStrokeColor(strokeColor);
        }
    }

    public void setStrokeColorResource(int strokeColorResourceId) {
        if (isUsingOriginalBackground()) {
            setStrokeColor(AppCompatResources.getColorStateList(getContext(), strokeColorResourceId));
        }
    }

    public ColorStateList getStrokeColor() {
        if (isUsingOriginalBackground()) {
            return this.materialButtonHelper.getStrokeColor();
        }
        return null;
    }

    public void setStrokeWidth(int strokeWidth) {
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.setStrokeWidth(strokeWidth);
        }
    }

    public void setStrokeWidthResource(int strokeWidthResourceId) {
        if (isUsingOriginalBackground()) {
            setStrokeWidth(getResources().getDimensionPixelSize(strokeWidthResourceId));
        }
    }

    public int getStrokeWidth() {
        if (isUsingOriginalBackground()) {
            return this.materialButtonHelper.getStrokeWidth();
        }
        return 0;
    }

    public void setCornerRadius(int cornerRadius) {
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.setCornerRadius(cornerRadius);
        }
    }

    public void setCornerRadiusResource(int cornerRadiusResourceId) {
        if (isUsingOriginalBackground()) {
            setCornerRadius(getResources().getDimensionPixelSize(cornerRadiusResourceId));
        }
    }

    public int getCornerRadius() {
        if (isUsingOriginalBackground()) {
            return this.materialButtonHelper.getCornerRadius();
        }
        return 0;
    }

    public int getIconGravity() {
        return this.iconGravity;
    }

    public void setIconGravity(int iconGravity2) {
        if (this.iconGravity != iconGravity2) {
            this.iconGravity = iconGravity2;
            updateIconPosition(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    public void setInsetBottom(int insetBottom) {
        this.materialButtonHelper.setInsetBottom(insetBottom);
    }

    public int getInsetBottom() {
        return this.materialButtonHelper.getInsetBottom();
    }

    public void setInsetTop(int insetTop) {
        this.materialButtonHelper.setInsetTop(insetTop);
    }

    public int getInsetTop() {
        return this.materialButtonHelper.getInsetTop();
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int extraSpace) {
        int[] drawableState = super.onCreateDrawableState(extraSpace + 2);
        if (isCheckable()) {
            mergeDrawableStates(drawableState, CHECKABLE_STATE_SET);
        }
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    public void addOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.onCheckedChangeListeners.add(listener);
    }

    public void removeOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.onCheckedChangeListeners.remove(listener);
    }

    public void clearOnCheckedChangeListeners() {
        this.onCheckedChangeListeners.clear();
    }

    public void setChecked(boolean checked2) {
        if (isCheckable() && isEnabled() && this.checked != checked2) {
            this.checked = checked2;
            refreshDrawableState();
            if (getParent() instanceof MaterialButtonToggleGroup) {
                ((MaterialButtonToggleGroup) getParent()).onButtonCheckedStateChanged(this, this.checked);
            }
            if (!this.broadcasting) {
                this.broadcasting = true;
                Iterator it = this.onCheckedChangeListeners.iterator();
                while (it.hasNext()) {
                    ((OnCheckedChangeListener) it.next()).onCheckedChanged(this, this.checked);
                }
                this.broadcasting = false;
            }
        }
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void toggle() {
        setChecked(!this.checked);
    }

    public boolean performClick() {
        if (this.materialButtonHelper.isToggleCheckedStateOnClick()) {
            toggle();
        }
        return super.performClick();
    }

    public boolean isToggleCheckedStateOnClick() {
        return this.materialButtonHelper.isToggleCheckedStateOnClick();
    }

    public void setToggleCheckedStateOnClick(boolean toggleCheckedStateOnClick) {
        this.materialButtonHelper.setToggleCheckedStateOnClick(toggleCheckedStateOnClick);
    }

    public boolean isCheckable() {
        MaterialButtonHelper materialButtonHelper2 = this.materialButtonHelper;
        return materialButtonHelper2 != null && materialButtonHelper2.isCheckable();
    }

    public void setCheckable(boolean checkable) {
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.setCheckable(checkable);
        }
    }

    public void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.setShapeAppearanceModel(shapeAppearanceModel);
            return;
        }
        throw new IllegalStateException("Attempted to set ShapeAppearanceModel on a MaterialButton which has an overwritten background.");
    }

    public ShapeAppearanceModel getShapeAppearanceModel() {
        if (isUsingOriginalBackground()) {
            return this.materialButtonHelper.getShapeAppearanceModel();
        }
        throw new IllegalStateException("Attempted to get ShapeAppearanceModel from a MaterialButton which has an overwritten background.");
    }

    /* access modifiers changed from: package-private */
    public void setOnPressedChangeListenerInternal(OnPressedChangeListener listener) {
        this.onPressedChangeListenerInternal = listener;
    }

    public void setPressed(boolean pressed) {
        OnPressedChangeListener onPressedChangeListener = this.onPressedChangeListenerInternal;
        if (onPressedChangeListener != null) {
            onPressedChangeListener.onPressedChanged(this, pressed);
        }
        super.setPressed(pressed);
    }

    private boolean isUsingOriginalBackground() {
        MaterialButtonHelper materialButtonHelper2 = this.materialButtonHelper;
        return materialButtonHelper2 != null && !materialButtonHelper2.isBackgroundOverwritten();
    }

    /* access modifiers changed from: package-private */
    public void setShouldDrawSurfaceColorStroke(boolean shouldDrawSurfaceColorStroke) {
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.setShouldDrawSurfaceColorStroke(shouldDrawSurfaceColorStroke);
        }
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
        boolean checked;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel source, ClassLoader loader) {
            super(source, loader);
            if (loader == null) {
                ClassLoader loader2 = getClass().getClassLoader();
            }
            readFromParcel(source);
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.checked ? 1 : 0);
        }

        private void readFromParcel(Parcel in) {
            boolean z = true;
            if (in.readInt() != 1) {
                z = false;
            }
            this.checked = z;
        }
    }
}
