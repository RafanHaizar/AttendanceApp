package com.google.android.material.chip;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.PointerIconCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.google.android.material.C1087R;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.internal.MaterialCheckable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.resources.TextAppearanceFontCallback;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import java.util.List;

public class Chip extends AppCompatCheckBox implements ChipDrawable.Delegate, Shapeable, MaterialCheckable<Chip> {
    private static final String BUTTON_ACCESSIBILITY_CLASS_NAME = "android.widget.Button";
    private static final int[] CHECKABLE_STATE_SET = {16842911};
    private static final int CHIP_BODY_VIRTUAL_ID = 0;
    private static final int CLOSE_ICON_VIRTUAL_ID = 1;
    private static final int DEF_STYLE_RES = C1087R.C1093style.Widget_MaterialComponents_Chip_Action;
    /* access modifiers changed from: private */
    public static final Rect EMPTY_BOUNDS = new Rect();
    private static final String GENERIC_VIEW_ACCESSIBILITY_CLASS_NAME = "android.view.View";
    private static final int MIN_TOUCH_TARGET_DP = 48;
    private static final String NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android";
    private static final String RADIO_BUTTON_ACCESSIBILITY_CLASS_NAME = "android.widget.RadioButton";
    private static final int[] SELECTED_STATE = {16842913};
    private static final String TAG = "Chip";
    private CharSequence accessibilityClassName;
    /* access modifiers changed from: private */
    public ChipDrawable chipDrawable;
    /* access modifiers changed from: private */
    public boolean closeIconFocused;
    private boolean closeIconHovered;
    private boolean closeIconPressed;
    private boolean deferredCheckedValue;
    private boolean ensureMinTouchTargetSize;
    private final TextAppearanceFontCallback fontCallback;
    private InsetDrawable insetBackgroundDrawable;
    private int lastLayoutDirection;
    private int minTouchTargetSize;
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
    private MaterialCheckable.OnCheckedChangeListener<Chip> onCheckedChangeListenerInternal;
    /* access modifiers changed from: private */
    public View.OnClickListener onCloseIconClickListener;
    private final Rect rect;
    private final RectF rectF;
    private RippleDrawable ripple;
    private final ChipTouchHelper touchHelper;
    private boolean touchHelperEnabled;

    public Chip(Context context) {
        this(context, (AttributeSet) null);
    }

    public Chip(Context context, AttributeSet attrs) {
        this(context, attrs, C1087R.attr.chipStyle);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public Chip(android.content.Context r8, android.util.AttributeSet r9, int r10) {
        /*
            r7 = this;
            int r4 = DEF_STYLE_RES
            android.content.Context r0 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r8, r9, r10, r4)
            r7.<init>(r0, r9, r10)
            android.graphics.Rect r0 = new android.graphics.Rect
            r0.<init>()
            r7.rect = r0
            android.graphics.RectF r0 = new android.graphics.RectF
            r0.<init>()
            r7.rectF = r0
            com.google.android.material.chip.Chip$1 r0 = new com.google.android.material.chip.Chip$1
            r0.<init>()
            r7.fontCallback = r0
            android.content.Context r8 = r7.getContext()
            r7.validateAttributes(r9)
            com.google.android.material.chip.ChipDrawable r6 = com.google.android.material.chip.ChipDrawable.createFromAttributes(r8, r9, r10, r4)
            r7.initMinTouchTarget(r8, r9, r10)
            r7.setChipDrawable(r6)
            float r0 = androidx.core.view.ViewCompat.getElevation(r7)
            r6.setElevation(r0)
            int[] r2 = com.google.android.material.C1087R.styleable.Chip
            r0 = 0
            int[] r5 = new int[r0]
            r0 = r8
            r1 = r9
            r3 = r10
            android.content.res.TypedArray r0 = com.google.android.material.internal.ThemeEnforcement.obtainStyledAttributes(r0, r1, r2, r3, r4, r5)
            int r1 = android.os.Build.VERSION.SDK_INT
            r2 = 23
            if (r1 >= r2) goto L_0x0052
            int r1 = com.google.android.material.C1087R.styleable.Chip_android_textColor
            android.content.res.ColorStateList r1 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r8, (android.content.res.TypedArray) r0, (int) r1)
            r7.setTextColor(r1)
        L_0x0052:
            int r1 = com.google.android.material.C1087R.styleable.Chip_shapeAppearance
            boolean r1 = r0.hasValue(r1)
            r0.recycle()
            com.google.android.material.chip.Chip$ChipTouchHelper r2 = new com.google.android.material.chip.Chip$ChipTouchHelper
            r2.<init>(r7)
            r7.touchHelper = r2
            r7.updateAccessibilityDelegate()
            if (r1 != 0) goto L_0x006a
            r7.initOutlineProvider()
        L_0x006a:
            boolean r2 = r7.deferredCheckedValue
            r7.setChecked(r2)
            java.lang.CharSequence r2 = r6.getText()
            r7.setText(r2)
            android.text.TextUtils$TruncateAt r2 = r6.getEllipsize()
            r7.setEllipsize(r2)
            r7.updateTextPaintDrawState()
            com.google.android.material.chip.ChipDrawable r2 = r7.chipDrawable
            boolean r2 = r2.shouldDrawText()
            if (r2 != 0) goto L_0x008f
            r2 = 1
            r7.setLines(r2)
            r7.setHorizontallyScrolling(r2)
        L_0x008f:
            r2 = 8388627(0x800013, float:1.175497E-38)
            r7.setGravity(r2)
            r7.updatePaddingInternal()
            boolean r2 = r7.shouldEnsureMinTouchTargetSize()
            if (r2 == 0) goto L_0x00a3
            int r2 = r7.minTouchTargetSize
            r7.setMinHeight(r2)
        L_0x00a3:
            int r2 = androidx.core.view.ViewCompat.getLayoutDirection(r7)
            r7.lastLayoutDirection = r2
            com.google.android.material.chip.Chip$$ExternalSyntheticLambda0 r2 = new com.google.android.material.chip.Chip$$ExternalSyntheticLambda0
            r2.<init>(r7)
            super.setOnCheckedChangeListener(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.chip.Chip.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$com-google-android-material-chip-Chip  reason: not valid java name */
    public /* synthetic */ void m1320lambda$new$0$comgoogleandroidmaterialchipChip(CompoundButton buttonView, boolean isChecked) {
        MaterialCheckable.OnCheckedChangeListener<Chip> onCheckedChangeListener2 = this.onCheckedChangeListenerInternal;
        if (onCheckedChangeListener2 != null) {
            onCheckedChangeListener2.onCheckedChanged(this, isChecked);
        }
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener3 = this.onCheckedChangeListener;
        if (onCheckedChangeListener3 != null) {
            onCheckedChangeListener3.onCheckedChanged(buttonView, isChecked);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this, this.chipDrawable);
    }

    public void setElevation(float elevation) {
        super.setElevation(elevation);
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setElevation(elevation);
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(getAccessibilityClassName());
        info.setCheckable(isCheckable());
        info.setClickable(isClickable());
        if (getParent() instanceof ChipGroup) {
            ChipGroup chipGroup = (ChipGroup) getParent();
            AccessibilityNodeInfoCompat.wrap(info).setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(chipGroup.getRowIndex(this), 1, chipGroup.isSingleLine() ? chipGroup.getIndexOfChip(this) : -1, 1, false, isChecked()));
        }
    }

    private void updateAccessibilityDelegate() {
        if (!hasCloseIcon() || !isCloseIconVisible() || this.onCloseIconClickListener == null) {
            ViewCompat.setAccessibilityDelegate(this, (AccessibilityDelegateCompat) null);
            this.touchHelperEnabled = false;
            return;
        }
        ViewCompat.setAccessibilityDelegate(this, this.touchHelper);
        this.touchHelperEnabled = true;
    }

    private void initMinTouchTarget(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context, attrs, C1087R.styleable.Chip, defStyleAttr, DEF_STYLE_RES, new int[0]);
        this.ensureMinTouchTargetSize = a.getBoolean(C1087R.styleable.Chip_ensureMinTouchTargetSize, false);
        this.minTouchTargetSize = (int) Math.ceil((double) a.getDimension(C1087R.styleable.Chip_chipMinTouchTargetSize, (float) Math.ceil((double) ViewUtils.dpToPx(getContext(), 48))));
        a.recycle();
    }

    private void updatePaddingInternal() {
        ChipDrawable chipDrawable2;
        if (!TextUtils.isEmpty(getText()) && (chipDrawable2 = this.chipDrawable) != null) {
            int paddingEnd = (int) (chipDrawable2.getChipEndPadding() + this.chipDrawable.getTextEndPadding() + this.chipDrawable.calculateCloseIconWidth());
            int paddingStart = (int) (this.chipDrawable.getChipStartPadding() + this.chipDrawable.getTextStartPadding() + this.chipDrawable.calculateChipIconWidth());
            if (this.insetBackgroundDrawable != null) {
                Rect padding = new Rect();
                this.insetBackgroundDrawable.getPadding(padding);
                paddingStart += padding.left;
                paddingEnd += padding.right;
            }
            ViewCompat.setPaddingRelative(this, paddingStart, getPaddingTop(), paddingEnd, getPaddingBottom());
        }
    }

    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        if (this.lastLayoutDirection != layoutDirection) {
            this.lastLayoutDirection = layoutDirection;
            updatePaddingInternal();
        }
    }

    private void validateAttributes(AttributeSet attributeSet) {
        if (attributeSet != null) {
            if (attributeSet.getAttributeValue(NAMESPACE_ANDROID, CommonCssConstants.BACKGROUND) != null) {
                Log.w(TAG, "Do not set the background; Chip manages its own background drawable.");
            }
            if (attributeSet.getAttributeValue(NAMESPACE_ANDROID, "drawableLeft") != null) {
                throw new UnsupportedOperationException("Please set left drawable using R.attr#chipIcon.");
            } else if (attributeSet.getAttributeValue(NAMESPACE_ANDROID, "drawableStart") != null) {
                throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
            } else if (attributeSet.getAttributeValue(NAMESPACE_ANDROID, "drawableEnd") != null) {
                throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
            } else if (attributeSet.getAttributeValue(NAMESPACE_ANDROID, "drawableRight") != null) {
                throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
            } else if (!attributeSet.getAttributeBooleanValue(NAMESPACE_ANDROID, "singleLine", true) || attributeSet.getAttributeIntValue(NAMESPACE_ANDROID, "lines", 1) != 1 || attributeSet.getAttributeIntValue(NAMESPACE_ANDROID, "minLines", 1) != 1 || attributeSet.getAttributeIntValue(NAMESPACE_ANDROID, "maxLines", 1) != 1) {
                throw new UnsupportedOperationException("Chip does not support multi-line text");
            } else if (attributeSet.getAttributeIntValue(NAMESPACE_ANDROID, "gravity", 8388627) != 8388627) {
                Log.w(TAG, "Chip text must be vertically center and start aligned");
            }
        }
    }

    private void initOutlineProvider() {
        if (Build.VERSION.SDK_INT >= 21) {
            setOutlineProvider(new ViewOutlineProvider() {
                public void getOutline(View view, Outline outline) {
                    if (Chip.this.chipDrawable != null) {
                        Chip.this.chipDrawable.getOutline(outline);
                    } else {
                        outline.setAlpha(0.0f);
                    }
                }
            });
        }
    }

    public Drawable getChipDrawable() {
        return this.chipDrawable;
    }

    public void setChipDrawable(ChipDrawable drawable) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != drawable) {
            unapplyChipDrawable(chipDrawable2);
            this.chipDrawable = drawable;
            drawable.setShouldDrawText(false);
            applyChipDrawable(this.chipDrawable);
            ensureAccessibleTouchTarget(this.minTouchTargetSize);
        }
    }

    private void updateBackgroundDrawable() {
        if (RippleUtils.USE_FRAMEWORK_RIPPLE) {
            updateFrameworkRippleBackground();
            return;
        }
        this.chipDrawable.setUseCompatRipple(true);
        ViewCompat.setBackground(this, getBackgroundDrawable());
        updatePaddingInternal();
        ensureChipDrawableHasCallback();
    }

    private void ensureChipDrawableHasCallback() {
        if (getBackgroundDrawable() == this.insetBackgroundDrawable && this.chipDrawable.getCallback() == null) {
            this.chipDrawable.setCallback(this.insetBackgroundDrawable);
        }
    }

    public Drawable getBackgroundDrawable() {
        InsetDrawable insetDrawable = this.insetBackgroundDrawable;
        if (insetDrawable == null) {
            return this.chipDrawable;
        }
        return insetDrawable;
    }

    private void updateFrameworkRippleBackground() {
        this.ripple = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(this.chipDrawable.getRippleColor()), getBackgroundDrawable(), (Drawable) null);
        this.chipDrawable.setUseCompatRipple(false);
        ViewCompat.setBackground(this, this.ripple);
        updatePaddingInternal();
    }

    private void unapplyChipDrawable(ChipDrawable chipDrawable2) {
        if (chipDrawable2 != null) {
            chipDrawable2.setDelegate((ChipDrawable.Delegate) null);
        }
    }

    private void applyChipDrawable(ChipDrawable chipDrawable2) {
        chipDrawable2.setDelegate(this);
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int extraSpace) {
        int[] state = super.onCreateDrawableState(extraSpace + 2);
        if (isChecked()) {
            mergeDrawableStates(state, SELECTED_STATE);
        }
        if (isCheckable()) {
            mergeDrawableStates(state, CHECKABLE_STATE_SET);
        }
        return state;
    }

    public void setGravity(int gravity) {
        if (gravity != 8388627) {
            Log.w(TAG, "Chip text must be vertically center and start aligned");
        } else {
            super.setGravity(gravity);
        }
    }

    public void setBackgroundTintList(ColorStateList tint) {
        Log.w(TAG, "Do not set the background tint list; Chip manages its own background drawable.");
    }

    public void setBackgroundTintMode(PorterDuff.Mode tintMode) {
        Log.w(TAG, "Do not set the background tint mode; Chip manages its own background drawable.");
    }

    public void setBackgroundColor(int color) {
        Log.w(TAG, "Do not set the background color; Chip manages its own background drawable.");
    }

    public void setBackgroundResource(int resid) {
        Log.w(TAG, "Do not set the background resource; Chip manages its own background drawable.");
    }

    public void setBackground(Drawable background) {
        if (background == getBackgroundDrawable() || background == this.ripple) {
            super.setBackground(background);
        } else {
            Log.w(TAG, "Do not set the background; Chip manages its own background drawable.");
        }
    }

    public void setBackgroundDrawable(Drawable background) {
        if (background == getBackgroundDrawable() || background == this.ripple) {
            super.setBackgroundDrawable(background);
        } else {
            Log.w(TAG, "Do not set the background drawable; Chip manages its own background drawable.");
        }
    }

    public void setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        if (left != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        } else if (right == null) {
            super.setCompoundDrawables(left, top, right, bottom);
        } else {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
    }

    public void setCompoundDrawablesWithIntrinsicBounds(int left, int top, int right, int bottom) {
        if (left != 0) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        } else if (right == 0) {
            super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        } else {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
    }

    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        if (left != null) {
            throw new UnsupportedOperationException("Please set left drawable using R.attr#chipIcon.");
        } else if (right == null) {
            super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        } else {
            throw new UnsupportedOperationException("Please set right drawable using R.attr#closeIcon.");
        }
    }

    public void setCompoundDrawablesRelative(Drawable start, Drawable top, Drawable end, Drawable bottom) {
        if (start != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        } else if (end == null) {
            super.setCompoundDrawablesRelative(start, top, end, bottom);
        } else {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
    }

    public void setCompoundDrawablesRelativeWithIntrinsicBounds(int start, int top, int end, int bottom) {
        if (start != 0) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        } else if (end == 0) {
            super.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
        } else {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
    }

    public void setCompoundDrawablesRelativeWithIntrinsicBounds(Drawable start, Drawable top, Drawable end, Drawable bottom) {
        if (start != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        } else if (end == null) {
            super.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
        } else {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
    }

    public TextUtils.TruncateAt getEllipsize() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            return chipDrawable2.getEllipsize();
        }
        return null;
    }

    public void setEllipsize(TextUtils.TruncateAt where) {
        if (this.chipDrawable != null) {
            if (where != TextUtils.TruncateAt.MARQUEE) {
                super.setEllipsize(where);
                ChipDrawable chipDrawable2 = this.chipDrawable;
                if (chipDrawable2 != null) {
                    chipDrawable2.setEllipsize(where);
                    return;
                }
                return;
            }
            throw new UnsupportedOperationException("Text within a chip are not allowed to scroll.");
        }
    }

    public void setSingleLine(boolean singleLine) {
        if (singleLine) {
            super.setSingleLine(singleLine);
            return;
        }
        throw new UnsupportedOperationException("Chip does not support multi-line text");
    }

    public void setLines(int lines) {
        if (lines <= 1) {
            super.setLines(lines);
            return;
        }
        throw new UnsupportedOperationException("Chip does not support multi-line text");
    }

    public void setMinLines(int minLines) {
        if (minLines <= 1) {
            super.setMinLines(minLines);
            return;
        }
        throw new UnsupportedOperationException("Chip does not support multi-line text");
    }

    public void setMaxLines(int maxLines) {
        if (maxLines <= 1) {
            super.setMaxLines(maxLines);
            return;
        }
        throw new UnsupportedOperationException("Chip does not support multi-line text");
    }

    public void setMaxWidth(int maxWidth) {
        super.setMaxWidth(maxWidth);
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setMaxWidth(maxWidth);
        }
    }

    public void onChipDrawableSizeChange() {
        ensureAccessibleTouchTarget(this.minTouchTargetSize);
        requestLayout();
        if (Build.VERSION.SDK_INT >= 21) {
            invalidateOutline();
        }
    }

    public void setChecked(boolean checked) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 == null) {
            this.deferredCheckedValue = checked;
        } else if (chipDrawable2.isCheckable()) {
            super.setChecked(checked);
        }
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        this.onCheckedChangeListener = listener;
    }

    public void setOnCloseIconClickListener(View.OnClickListener listener) {
        this.onCloseIconClickListener = listener;
        updateAccessibilityDelegate();
    }

    public boolean performCloseIconClick() {
        boolean result;
        playSoundEffect(0);
        View.OnClickListener onClickListener = this.onCloseIconClickListener;
        if (onClickListener != null) {
            onClickListener.onClick(this);
            result = true;
        } else {
            result = false;
        }
        if (this.touchHelperEnabled) {
            this.touchHelper.sendEventForVirtualView(1, 1);
        }
        return result;
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = false;
        int action = event.getActionMasked();
        boolean eventInCloseIcon = getCloseIconTouchBounds().contains(event.getX(), event.getY());
        switch (action) {
            case 0:
                if (eventInCloseIcon) {
                    setCloseIconPressed(true);
                    handled = true;
                    break;
                }
                break;
            case 1:
                if (this.closeIconPressed) {
                    performCloseIconClick();
                    handled = true;
                    break;
                }
                break;
            case 2:
                if (this.closeIconPressed) {
                    if (!eventInCloseIcon) {
                        setCloseIconPressed(false);
                    }
                    handled = true;
                    break;
                }
                break;
            case 3:
                break;
        }
        setCloseIconPressed(false);
        if (handled || super.onTouchEvent(event)) {
            return true;
        }
        return false;
    }

    public boolean onHoverEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case 7:
                setCloseIconHovered(getCloseIconTouchBounds().contains(event.getX(), event.getY()));
                break;
            case 10:
                setCloseIconHovered(false);
                break;
        }
        return super.onHoverEvent(event);
    }

    /* access modifiers changed from: protected */
    public boolean dispatchHoverEvent(MotionEvent event) {
        if (!this.touchHelperEnabled) {
            return super.dispatchHoverEvent(event);
        }
        return this.touchHelper.dispatchHoverEvent(event) || super.dispatchHoverEvent(event);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (!this.touchHelperEnabled) {
            return super.dispatchKeyEvent(event);
        }
        if (!this.touchHelper.dispatchKeyEvent(event) || this.touchHelper.getKeyboardFocusedVirtualViewId() == Integer.MIN_VALUE) {
            return super.dispatchKeyEvent(event);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (this.touchHelperEnabled) {
            this.touchHelper.onFocusChanged(focused, direction, previouslyFocusedRect);
        }
    }

    public void getFocusedRect(Rect r) {
        if (!this.touchHelperEnabled || !(this.touchHelper.getKeyboardFocusedVirtualViewId() == 1 || this.touchHelper.getAccessibilityFocusedVirtualViewId() == 1)) {
            super.getFocusedRect(r);
        } else {
            r.set(getCloseIconTouchBoundsInt());
        }
    }

    private void setCloseIconPressed(boolean pressed) {
        if (this.closeIconPressed != pressed) {
            this.closeIconPressed = pressed;
            refreshDrawableState();
        }
    }

    private void setCloseIconHovered(boolean hovered) {
        if (this.closeIconHovered != hovered) {
            this.closeIconHovered = hovered;
            refreshDrawableState();
        }
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        boolean changed = false;
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null && chipDrawable2.isCloseIconStateful()) {
            changed = this.chipDrawable.setCloseIconState(createCloseIconDrawableState());
        }
        if (changed) {
            invalidate();
        }
    }

    private int[] createCloseIconDrawableState() {
        int count = 0;
        if (isEnabled()) {
            count = 0 + 1;
        }
        if (this.closeIconFocused) {
            count++;
        }
        if (this.closeIconHovered) {
            count++;
        }
        if (this.closeIconPressed) {
            count++;
        }
        if (isChecked()) {
            count++;
        }
        int[] stateSet = new int[count];
        int i = 0;
        if (isEnabled()) {
            stateSet[0] = 16842910;
            i = 0 + 1;
        }
        if (this.closeIconFocused) {
            stateSet[i] = 16842908;
            i++;
        }
        if (this.closeIconHovered) {
            stateSet[i] = 16843623;
            i++;
        }
        if (this.closeIconPressed) {
            stateSet[i] = 16842919;
            i++;
        }
        if (isChecked()) {
            stateSet[i] = 16842913;
            int i2 = i + 1;
        }
        return stateSet;
    }

    /* access modifiers changed from: private */
    public boolean hasCloseIcon() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        return (chipDrawable2 == null || chipDrawable2.getCloseIcon() == null) ? false : true;
    }

    /* access modifiers changed from: private */
    public RectF getCloseIconTouchBounds() {
        this.rectF.setEmpty();
        if (hasCloseIcon() && this.onCloseIconClickListener != null) {
            this.chipDrawable.getCloseIconTouchBounds(this.rectF);
        }
        return this.rectF;
    }

    /* access modifiers changed from: private */
    public Rect getCloseIconTouchBoundsInt() {
        RectF bounds = getCloseIconTouchBounds();
        this.rect.set((int) bounds.left, (int) bounds.top, (int) bounds.right, (int) bounds.bottom);
        return this.rect;
    }

    public PointerIcon onResolvePointerIcon(MotionEvent event, int pointerIndex) {
        if (!getCloseIconTouchBounds().contains(event.getX(), event.getY()) || !isEnabled()) {
            return null;
        }
        return PointerIcon.getSystemIcon(getContext(), PointerIconCompat.TYPE_HAND);
    }

    public void setInternalOnCheckedChangeListener(MaterialCheckable.OnCheckedChangeListener<Chip> listener) {
        this.onCheckedChangeListenerInternal = listener;
    }

    private class ChipTouchHelper extends ExploreByTouchHelper {
        ChipTouchHelper(Chip view) {
            super(view);
        }

        /* access modifiers changed from: protected */
        public int getVirtualViewAt(float x, float y) {
            if (!Chip.this.hasCloseIcon() || !Chip.this.getCloseIconTouchBounds().contains(x, y)) {
                return 0;
            }
            return 1;
        }

        /* access modifiers changed from: protected */
        public void getVisibleVirtualViews(List<Integer> virtualViewIds) {
            virtualViewIds.add(0);
            if (Chip.this.hasCloseIcon() && Chip.this.isCloseIconVisible() && Chip.this.onCloseIconClickListener != null) {
                virtualViewIds.add(1);
            }
        }

        /* access modifiers changed from: protected */
        public void onVirtualViewKeyboardFocusChanged(int virtualViewId, boolean hasFocus) {
            if (virtualViewId == 1) {
                boolean unused = Chip.this.closeIconFocused = hasFocus;
                Chip.this.refreshDrawableState();
            }
        }

        /* access modifiers changed from: protected */
        public void onPopulateNodeForVirtualView(int virtualViewId, AccessibilityNodeInfoCompat node) {
            CharSequence charSequence = "";
            if (virtualViewId == 1) {
                CharSequence closeIconContentDescription = Chip.this.getCloseIconContentDescription();
                if (closeIconContentDescription != null) {
                    node.setContentDescription(closeIconContentDescription);
                } else {
                    CharSequence chipText = Chip.this.getText();
                    Context context = Chip.this.getContext();
                    int i = C1087R.string.mtrl_chip_close_icon_content_description;
                    Object[] objArr = new Object[1];
                    if (!TextUtils.isEmpty(chipText)) {
                        charSequence = chipText;
                    }
                    objArr[0] = charSequence;
                    node.setContentDescription(context.getString(i, objArr).trim());
                }
                node.setBoundsInParent(Chip.this.getCloseIconTouchBoundsInt());
                node.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
                node.setEnabled(Chip.this.isEnabled());
                return;
            }
            node.setContentDescription(charSequence);
            node.setBoundsInParent(Chip.EMPTY_BOUNDS);
        }

        /* access modifiers changed from: protected */
        public void onPopulateNodeForHost(AccessibilityNodeInfoCompat node) {
            node.setCheckable(Chip.this.isCheckable());
            node.setClickable(Chip.this.isClickable());
            node.setClassName(Chip.this.getAccessibilityClassName());
            CharSequence chipText = Chip.this.getText();
            if (Build.VERSION.SDK_INT >= 23) {
                node.setText(chipText);
            } else {
                node.setContentDescription(chipText);
            }
        }

        /* access modifiers changed from: protected */
        public boolean onPerformActionForVirtualView(int virtualViewId, int action, Bundle arguments) {
            if (action != 16) {
                return false;
            }
            if (virtualViewId == 0) {
                return Chip.this.performClick();
            }
            if (virtualViewId == 1) {
                return Chip.this.performCloseIconClick();
            }
            return false;
        }
    }

    public ColorStateList getChipBackgroundColor() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            return chipDrawable2.getChipBackgroundColor();
        }
        return null;
    }

    public void setChipBackgroundColorResource(int id) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setChipBackgroundColorResource(id);
        }
    }

    public void setChipBackgroundColor(ColorStateList chipBackgroundColor) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setChipBackgroundColor(chipBackgroundColor);
        }
    }

    public float getChipMinHeight() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            return chipDrawable2.getChipMinHeight();
        }
        return 0.0f;
    }

    public void setChipMinHeightResource(int id) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setChipMinHeightResource(id);
        }
    }

    public void setChipMinHeight(float minHeight) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setChipMinHeight(minHeight);
        }
    }

    public float getChipCornerRadius() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            return Math.max(0.0f, chipDrawable2.getChipCornerRadius());
        }
        return 0.0f;
    }

    @Deprecated
    public void setChipCornerRadiusResource(int id) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setChipCornerRadiusResource(id);
        }
    }

    public void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        this.chipDrawable.setShapeAppearanceModel(shapeAppearanceModel);
    }

    public ShapeAppearanceModel getShapeAppearanceModel() {
        return this.chipDrawable.getShapeAppearanceModel();
    }

    @Deprecated
    public void setChipCornerRadius(float chipCornerRadius) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setChipCornerRadius(chipCornerRadius);
        }
    }

    public ColorStateList getChipStrokeColor() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            return chipDrawable2.getChipStrokeColor();
        }
        return null;
    }

    public void setChipStrokeColorResource(int id) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setChipStrokeColorResource(id);
        }
    }

    public void setChipStrokeColor(ColorStateList chipStrokeColor) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setChipStrokeColor(chipStrokeColor);
        }
    }

    public float getChipStrokeWidth() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            return chipDrawable2.getChipStrokeWidth();
        }
        return 0.0f;
    }

    public void setChipStrokeWidthResource(int id) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setChipStrokeWidthResource(id);
        }
    }

    public void setChipStrokeWidth(float chipStrokeWidth) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setChipStrokeWidth(chipStrokeWidth);
        }
    }

    public ColorStateList getRippleColor() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            return chipDrawable2.getRippleColor();
        }
        return null;
    }

    public void setRippleColorResource(int id) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setRippleColorResource(id);
            if (!this.chipDrawable.getUseCompatRipple()) {
                updateFrameworkRippleBackground();
            }
        }
    }

    public void setRippleColor(ColorStateList rippleColor) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setRippleColor(rippleColor);
        }
        if (!this.chipDrawable.getUseCompatRipple()) {
            updateFrameworkRippleBackground();
        }
    }

    @Deprecated
    public CharSequence getChipText() {
        return getText();
    }

    public void setLayoutDirection(int layoutDirection) {
        if (this.chipDrawable != null) {
            super.setLayoutDirection(layoutDirection);
        }
    }

    public void setText(CharSequence text, TextView.BufferType type) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            if (text == null) {
                text = "";
            }
            super.setText(chipDrawable2.shouldDrawText() ? null : text, type);
            ChipDrawable chipDrawable3 = this.chipDrawable;
            if (chipDrawable3 != null) {
                chipDrawable3.setText(text);
            }
        }
    }

    @Deprecated
    public void setChipTextResource(int id) {
        setText(getResources().getString(id));
    }

    @Deprecated
    public void setChipText(CharSequence chipText) {
        setText(chipText);
    }

    public void setTextAppearanceResource(int id) {
        setTextAppearance(getContext(), id);
    }

    public void setTextAppearance(TextAppearance textAppearance) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setTextAppearance(textAppearance);
        }
        updateTextPaintDrawState();
    }

    public void setTextAppearance(Context context, int resId) {
        super.setTextAppearance(context, resId);
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setTextAppearanceResource(resId);
        }
        updateTextPaintDrawState();
    }

    public void setTextAppearance(int resId) {
        super.setTextAppearance(resId);
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setTextAppearanceResource(resId);
        }
        updateTextPaintDrawState();
    }

    public void setTextSize(int unit, float size) {
        super.setTextSize(unit, size);
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setTextSize(TypedValue.applyDimension(unit, size, getResources().getDisplayMetrics()));
        }
        updateTextPaintDrawState();
    }

    private void updateTextPaintDrawState() {
        TextPaint textPaint = getPaint();
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            textPaint.drawableState = chipDrawable2.getState();
        }
        TextAppearance textAppearance = getTextAppearance();
        if (textAppearance != null) {
            textAppearance.updateDrawState(getContext(), textPaint, this.fontCallback);
        }
    }

    private TextAppearance getTextAppearance() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            return chipDrawable2.getTextAppearance();
        }
        return null;
    }

    public boolean isChipIconVisible() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        return chipDrawable2 != null && chipDrawable2.isChipIconVisible();
    }

    @Deprecated
    public boolean isChipIconEnabled() {
        return isChipIconVisible();
    }

    public void setChipIconVisible(int id) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setChipIconVisible(id);
        }
    }

    public void setChipIconVisible(boolean chipIconVisible) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setChipIconVisible(chipIconVisible);
        }
    }

    @Deprecated
    public void setChipIconEnabledResource(int id) {
        setChipIconVisible(id);
    }

    @Deprecated
    public void setChipIconEnabled(boolean chipIconEnabled) {
        setChipIconVisible(chipIconEnabled);
    }

    public Drawable getChipIcon() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            return chipDrawable2.getChipIcon();
        }
        return null;
    }

    public void setChipIconResource(int id) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setChipIconResource(id);
        }
    }

    public void setChipIcon(Drawable chipIcon) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setChipIcon(chipIcon);
        }
    }

    public ColorStateList getChipIconTint() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            return chipDrawable2.getChipIconTint();
        }
        return null;
    }

    public void setChipIconTintResource(int id) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setChipIconTintResource(id);
        }
    }

    public void setChipIconTint(ColorStateList chipIconTint) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setChipIconTint(chipIconTint);
        }
    }

    public float getChipIconSize() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            return chipDrawable2.getChipIconSize();
        }
        return 0.0f;
    }

    public void setChipIconSizeResource(int id) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setChipIconSizeResource(id);
        }
    }

    public void setChipIconSize(float chipIconSize) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setChipIconSize(chipIconSize);
        }
    }

    public boolean isCloseIconVisible() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        return chipDrawable2 != null && chipDrawable2.isCloseIconVisible();
    }

    @Deprecated
    public boolean isCloseIconEnabled() {
        return isCloseIconVisible();
    }

    public void setCloseIconVisible(int id) {
        setCloseIconVisible(getResources().getBoolean(id));
    }

    public void setCloseIconVisible(boolean closeIconVisible) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setCloseIconVisible(closeIconVisible);
        }
        updateAccessibilityDelegate();
    }

    @Deprecated
    public void setCloseIconEnabledResource(int id) {
        setCloseIconVisible(id);
    }

    @Deprecated
    public void setCloseIconEnabled(boolean closeIconEnabled) {
        setCloseIconVisible(closeIconEnabled);
    }

    public Drawable getCloseIcon() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            return chipDrawable2.getCloseIcon();
        }
        return null;
    }

    public void setCloseIconResource(int id) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setCloseIconResource(id);
        }
        updateAccessibilityDelegate();
    }

    public void setCloseIcon(Drawable closeIcon) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setCloseIcon(closeIcon);
        }
        updateAccessibilityDelegate();
    }

    public ColorStateList getCloseIconTint() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            return chipDrawable2.getCloseIconTint();
        }
        return null;
    }

    public void setCloseIconTintResource(int id) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setCloseIconTintResource(id);
        }
    }

    public void setCloseIconTint(ColorStateList closeIconTint) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setCloseIconTint(closeIconTint);
        }
    }

    public float getCloseIconSize() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            return chipDrawable2.getCloseIconSize();
        }
        return 0.0f;
    }

    public void setCloseIconSizeResource(int id) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setCloseIconSizeResource(id);
        }
    }

    public void setCloseIconSize(float closeIconSize) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setCloseIconSize(closeIconSize);
        }
    }

    public void setCloseIconContentDescription(CharSequence closeIconContentDescription) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setCloseIconContentDescription(closeIconContentDescription);
        }
    }

    public CharSequence getCloseIconContentDescription() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            return chipDrawable2.getCloseIconContentDescription();
        }
        return null;
    }

    public boolean isCheckable() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        return chipDrawable2 != null && chipDrawable2.isCheckable();
    }

    public void setCheckableResource(int id) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setCheckableResource(id);
        }
    }

    public void setCheckable(boolean checkable) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setCheckable(checkable);
        }
    }

    public boolean isCheckedIconVisible() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        return chipDrawable2 != null && chipDrawable2.isCheckedIconVisible();
    }

    @Deprecated
    public boolean isCheckedIconEnabled() {
        return isCheckedIconVisible();
    }

    public void setCheckedIconVisible(int id) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setCheckedIconVisible(id);
        }
    }

    public void setCheckedIconVisible(boolean checkedIconVisible) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setCheckedIconVisible(checkedIconVisible);
        }
    }

    @Deprecated
    public void setCheckedIconEnabledResource(int id) {
        setCheckedIconVisible(id);
    }

    @Deprecated
    public void setCheckedIconEnabled(boolean checkedIconEnabled) {
        setCheckedIconVisible(checkedIconEnabled);
    }

    public Drawable getCheckedIcon() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            return chipDrawable2.getCheckedIcon();
        }
        return null;
    }

    public void setCheckedIconResource(int id) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setCheckedIconResource(id);
        }
    }

    public void setCheckedIcon(Drawable checkedIcon) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setCheckedIcon(checkedIcon);
        }
    }

    public ColorStateList getCheckedIconTint() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            return chipDrawable2.getCheckedIconTint();
        }
        return null;
    }

    public void setCheckedIconTintResource(int id) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setCheckedIconTintResource(id);
        }
    }

    public void setCheckedIconTint(ColorStateList checkedIconTint) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setCheckedIconTint(checkedIconTint);
        }
    }

    public MotionSpec getShowMotionSpec() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            return chipDrawable2.getShowMotionSpec();
        }
        return null;
    }

    public void setShowMotionSpecResource(int id) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setShowMotionSpecResource(id);
        }
    }

    public void setShowMotionSpec(MotionSpec showMotionSpec) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setShowMotionSpec(showMotionSpec);
        }
    }

    public MotionSpec getHideMotionSpec() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            return chipDrawable2.getHideMotionSpec();
        }
        return null;
    }

    public void setHideMotionSpecResource(int id) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setHideMotionSpecResource(id);
        }
    }

    public void setHideMotionSpec(MotionSpec hideMotionSpec) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setHideMotionSpec(hideMotionSpec);
        }
    }

    public float getChipStartPadding() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            return chipDrawable2.getChipStartPadding();
        }
        return 0.0f;
    }

    public void setChipStartPaddingResource(int id) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setChipStartPaddingResource(id);
        }
    }

    public void setChipStartPadding(float chipStartPadding) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setChipStartPadding(chipStartPadding);
        }
    }

    public float getIconStartPadding() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            return chipDrawable2.getIconStartPadding();
        }
        return 0.0f;
    }

    public void setIconStartPaddingResource(int id) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setIconStartPaddingResource(id);
        }
    }

    public void setIconStartPadding(float iconStartPadding) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setIconStartPadding(iconStartPadding);
        }
    }

    public float getIconEndPadding() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            return chipDrawable2.getIconEndPadding();
        }
        return 0.0f;
    }

    public void setIconEndPaddingResource(int id) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setIconEndPaddingResource(id);
        }
    }

    public void setIconEndPadding(float iconEndPadding) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setIconEndPadding(iconEndPadding);
        }
    }

    public float getTextStartPadding() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            return chipDrawable2.getTextStartPadding();
        }
        return 0.0f;
    }

    public void setTextStartPaddingResource(int id) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setTextStartPaddingResource(id);
        }
    }

    public void setTextStartPadding(float textStartPadding) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setTextStartPadding(textStartPadding);
        }
    }

    public float getTextEndPadding() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            return chipDrawable2.getTextEndPadding();
        }
        return 0.0f;
    }

    public void setTextEndPaddingResource(int id) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setTextEndPaddingResource(id);
        }
    }

    public void setTextEndPadding(float textEndPadding) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setTextEndPadding(textEndPadding);
        }
    }

    public float getCloseIconStartPadding() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            return chipDrawable2.getCloseIconStartPadding();
        }
        return 0.0f;
    }

    public void setCloseIconStartPaddingResource(int id) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setCloseIconStartPaddingResource(id);
        }
    }

    public void setCloseIconStartPadding(float closeIconStartPadding) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setCloseIconStartPadding(closeIconStartPadding);
        }
    }

    public float getCloseIconEndPadding() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            return chipDrawable2.getCloseIconEndPadding();
        }
        return 0.0f;
    }

    public void setCloseIconEndPaddingResource(int id) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setCloseIconEndPaddingResource(id);
        }
    }

    public void setCloseIconEndPadding(float closeIconEndPadding) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setCloseIconEndPadding(closeIconEndPadding);
        }
    }

    public float getChipEndPadding() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            return chipDrawable2.getChipEndPadding();
        }
        return 0.0f;
    }

    public void setChipEndPaddingResource(int id) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setChipEndPaddingResource(id);
        }
    }

    public void setChipEndPadding(float chipEndPadding) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setChipEndPadding(chipEndPadding);
        }
    }

    public boolean shouldEnsureMinTouchTargetSize() {
        return this.ensureMinTouchTargetSize;
    }

    public void setEnsureMinTouchTargetSize(boolean flag) {
        this.ensureMinTouchTargetSize = flag;
        ensureAccessibleTouchTarget(this.minTouchTargetSize);
    }

    public boolean ensureAccessibleTouchTarget(int minTargetPx) {
        this.minTouchTargetSize = minTargetPx;
        int deltaY = 0;
        if (!shouldEnsureMinTouchTargetSize()) {
            if (this.insetBackgroundDrawable != null) {
                removeBackgroundInset();
            } else {
                updateBackgroundDrawable();
            }
            return false;
        }
        int deltaHeight = Math.max(0, minTargetPx - this.chipDrawable.getIntrinsicHeight());
        int deltaWidth = Math.max(0, minTargetPx - this.chipDrawable.getIntrinsicWidth());
        if (deltaWidth > 0 || deltaHeight > 0) {
            int deltaX = deltaWidth > 0 ? deltaWidth / 2 : 0;
            if (deltaHeight > 0) {
                deltaY = deltaHeight / 2;
            }
            if (this.insetBackgroundDrawable != null) {
                Rect padding = new Rect();
                this.insetBackgroundDrawable.getPadding(padding);
                if (padding.top == deltaY && padding.bottom == deltaY && padding.left == deltaX && padding.right == deltaX) {
                    updateBackgroundDrawable();
                    return true;
                }
            }
            if (getMinHeight() != minTargetPx) {
                setMinHeight(minTargetPx);
            }
            if (getMinWidth() != minTargetPx) {
                setMinWidth(minTargetPx);
            }
            insetChipBackgroundDrawable(deltaX, deltaY, deltaX, deltaY);
            updateBackgroundDrawable();
            return true;
        }
        if (this.insetBackgroundDrawable != null) {
            removeBackgroundInset();
        } else {
            updateBackgroundDrawable();
        }
        return false;
    }

    public void setAccessibilityClassName(CharSequence className) {
        this.accessibilityClassName = className;
    }

    public CharSequence getAccessibilityClassName() {
        if (!TextUtils.isEmpty(this.accessibilityClassName)) {
            return this.accessibilityClassName;
        }
        if (isCheckable()) {
            ViewParent parent = getParent();
            if (!(parent instanceof ChipGroup) || !((ChipGroup) parent).isSingleSelection()) {
                return BUTTON_ACCESSIBILITY_CLASS_NAME;
            }
            return RADIO_BUTTON_ACCESSIBILITY_CLASS_NAME;
        } else if (isClickable()) {
            return BUTTON_ACCESSIBILITY_CLASS_NAME;
        } else {
            return GENERIC_VIEW_ACCESSIBILITY_CLASS_NAME;
        }
    }

    private void removeBackgroundInset() {
        if (this.insetBackgroundDrawable != null) {
            this.insetBackgroundDrawable = null;
            setMinWidth(0);
            setMinHeight((int) getChipMinHeight());
            updateBackgroundDrawable();
        }
    }

    private void insetChipBackgroundDrawable(int insetLeft, int insetTop, int insetRight, int insetBottom) {
        this.insetBackgroundDrawable = new InsetDrawable(this.chipDrawable, insetLeft, insetTop, insetRight, insetBottom);
    }
}
