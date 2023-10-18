package com.google.android.material.checkbox;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.autofill.AutofillManager;
import android.widget.CompoundButton;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.CompoundButtonCompat;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import com.google.android.material.C1087R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.ViewUtils;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class MaterialCheckBox extends AppCompatCheckBox {
    private static final int[][] CHECKBOX_STATES = {new int[]{16842910, C1087R.attr.state_error}, new int[]{16842910, 16842912}, new int[]{16842910, -16842912}, new int[]{-16842910, 16842912}, new int[]{-16842910, -16842912}};
    private static final int DEF_STYLE_RES = C1087R.C1093style.Widget_MaterialComponents_CompoundButton_CheckBox;
    private static final int[] ERROR_STATE_SET = {C1087R.attr.state_error};
    private static final int FRAMEWORK_BUTTON_DRAWABLE_RES_ID = Resources.getSystem().getIdentifier("btn_check_material_anim", "drawable", "android");
    private static final int[] INDETERMINATE_STATE_SET = {C1087R.attr.state_indeterminate};
    public static final int STATE_CHECKED = 1;
    public static final int STATE_INDETERMINATE = 2;
    public static final int STATE_UNCHECKED = 0;
    private boolean broadcasting;
    private Drawable buttonDrawable;
    private Drawable buttonIconDrawable;
    ColorStateList buttonIconTintList;
    private PorterDuff.Mode buttonIconTintMode;
    ColorStateList buttonTintList;
    private boolean centerIfNoTextEnabled;
    private int checkedState;
    /* access modifiers changed from: private */
    public int[] currentStateChecked;
    private CharSequence customStateDescription;
    private CharSequence errorAccessibilityLabel;
    private boolean errorShown;
    private ColorStateList materialThemeColorsTintList;
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
    private final LinkedHashSet<OnCheckedStateChangedListener> onCheckedStateChangedListeners;
    private final LinkedHashSet<OnErrorChangedListener> onErrorChangedListeners;
    private final AnimatedVectorDrawableCompat transitionToUnchecked;
    private final Animatable2Compat.AnimationCallback transitionToUncheckedCallback;
    private boolean useMaterialThemeColors;
    private boolean usingMaterialButtonDrawable;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CheckedState {
    }

    public interface OnCheckedStateChangedListener {
        void onCheckedStateChangedListener(MaterialCheckBox materialCheckBox, int i);
    }

    public interface OnErrorChangedListener {
        void onErrorChanged(MaterialCheckBox materialCheckBox, boolean z);
    }

    public MaterialCheckBox(Context context) {
        this(context, (AttributeSet) null);
    }

    public MaterialCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, C1087R.attr.checkboxStyle);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MaterialCheckBox(android.content.Context r9, android.util.AttributeSet r10, int r11) {
        /*
            r8 = this;
            int r4 = DEF_STYLE_RES
            android.content.Context r0 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r9, r10, r11, r4)
            r8.<init>(r0, r10, r11)
            java.util.LinkedHashSet r0 = new java.util.LinkedHashSet
            r0.<init>()
            r8.onErrorChangedListeners = r0
            java.util.LinkedHashSet r0 = new java.util.LinkedHashSet
            r0.<init>()
            r8.onCheckedStateChangedListeners = r0
            android.content.Context r0 = r8.getContext()
            int r1 = com.google.android.material.C1087R.C1089drawable.mtrl_checkbox_button_checked_unchecked
            androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat r0 = androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat.create(r0, r1)
            r8.transitionToUnchecked = r0
            com.google.android.material.checkbox.MaterialCheckBox$1 r0 = new com.google.android.material.checkbox.MaterialCheckBox$1
            r0.<init>()
            r8.transitionToUncheckedCallback = r0
            android.content.Context r9 = r8.getContext()
            android.graphics.drawable.Drawable r0 = androidx.core.widget.CompoundButtonCompat.getButtonDrawable(r8)
            r8.buttonDrawable = r0
            android.content.res.ColorStateList r0 = r8.getSuperButtonTintList()
            r8.buttonTintList = r0
            r6 = 0
            r8.setSupportButtonTintList(r6)
            int[] r2 = com.google.android.material.C1087R.styleable.MaterialCheckBox
            r7 = 0
            int[] r5 = new int[r7]
            r0 = r9
            r1 = r10
            r3 = r11
            androidx.appcompat.widget.TintTypedArray r0 = com.google.android.material.internal.ThemeEnforcement.obtainTintedStyledAttributes(r0, r1, r2, r3, r4, r5)
            int r1 = com.google.android.material.C1087R.styleable.MaterialCheckBox_buttonIcon
            android.graphics.drawable.Drawable r1 = r0.getDrawable(r1)
            r8.buttonIconDrawable = r1
            android.graphics.drawable.Drawable r1 = r8.buttonDrawable
            r2 = 1
            if (r1 == 0) goto L_0x007d
            boolean r1 = com.google.android.material.internal.ThemeEnforcement.isMaterial3Theme(r9)
            if (r1 == 0) goto L_0x007d
            boolean r1 = r8.isButtonDrawableLegacy(r0)
            if (r1 == 0) goto L_0x007d
            super.setButtonDrawable((android.graphics.drawable.Drawable) r6)
            int r1 = com.google.android.material.C1087R.C1089drawable.mtrl_checkbox_button
            android.graphics.drawable.Drawable r1 = androidx.appcompat.content.res.AppCompatResources.getDrawable(r9, r1)
            r8.buttonDrawable = r1
            r8.usingMaterialButtonDrawable = r2
            android.graphics.drawable.Drawable r1 = r8.buttonIconDrawable
            if (r1 != 0) goto L_0x007d
            int r1 = com.google.android.material.C1087R.C1089drawable.mtrl_checkbox_button_icon
            android.graphics.drawable.Drawable r1 = androidx.appcompat.content.res.AppCompatResources.getDrawable(r9, r1)
            r8.buttonIconDrawable = r1
        L_0x007d:
            int r1 = com.google.android.material.C1087R.styleable.MaterialCheckBox_buttonIconTint
            android.content.res.ColorStateList r1 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r9, (androidx.appcompat.widget.TintTypedArray) r0, (int) r1)
            r8.buttonIconTintList = r1
            int r1 = com.google.android.material.C1087R.styleable.MaterialCheckBox_buttonIconTintMode
            r3 = -1
            int r1 = r0.getInt(r1, r3)
            android.graphics.PorterDuff$Mode r3 = android.graphics.PorterDuff.Mode.SRC_IN
            android.graphics.PorterDuff$Mode r1 = com.google.android.material.internal.ViewUtils.parseTintMode(r1, r3)
            r8.buttonIconTintMode = r1
            int r1 = com.google.android.material.C1087R.styleable.MaterialCheckBox_useMaterialThemeColors
            boolean r1 = r0.getBoolean(r1, r7)
            r8.useMaterialThemeColors = r1
            int r1 = com.google.android.material.C1087R.styleable.MaterialCheckBox_centerIfNoTextEnabled
            boolean r1 = r0.getBoolean(r1, r2)
            r8.centerIfNoTextEnabled = r1
            int r1 = com.google.android.material.C1087R.styleable.MaterialCheckBox_errorShown
            boolean r1 = r0.getBoolean(r1, r7)
            r8.errorShown = r1
            int r1 = com.google.android.material.C1087R.styleable.MaterialCheckBox_errorAccessibilityLabel
            java.lang.CharSequence r1 = r0.getText(r1)
            r8.errorAccessibilityLabel = r1
            int r1 = com.google.android.material.C1087R.styleable.MaterialCheckBox_checkedState
            boolean r1 = r0.hasValue(r1)
            if (r1 == 0) goto L_0x00c5
            int r1 = com.google.android.material.C1087R.styleable.MaterialCheckBox_checkedState
            int r1 = r0.getInt(r1, r7)
            r8.setCheckedState(r1)
        L_0x00c5:
            r0.recycle()
            r8.refreshButtonDrawable()
            int r1 = android.os.Build.VERSION.SDK_INT
            r2 = 21
            if (r1 >= r2) goto L_0x00dd
            android.graphics.drawable.Drawable r1 = r8.buttonIconDrawable
            if (r1 == 0) goto L_0x00dd
            com.google.android.material.checkbox.MaterialCheckBox$$ExternalSyntheticLambda0 r1 = new com.google.android.material.checkbox.MaterialCheckBox$$ExternalSyntheticLambda0
            r1.<init>(r8)
            r8.post(r1)
        L_0x00dd:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.checkbox.MaterialCheckBox.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$com-google-android-material-checkbox-MaterialCheckBox */
    public /* synthetic */ void mo21541xdf87d0bf() {
        this.buttonIconDrawable.jumpToCurrentState();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        Drawable drawable;
        if (!this.centerIfNoTextEnabled || !TextUtils.isEmpty(getText()) || (drawable = CompoundButtonCompat.getButtonDrawable(this)) == null) {
            super.onDraw(canvas);
            return;
        }
        int dx = ((getWidth() - drawable.getIntrinsicWidth()) / 2) * (ViewUtils.isLayoutRtl(this) ? -1 : 1);
        int saveCount = canvas.save();
        canvas.translate((float) dx, 0.0f);
        super.onDraw(canvas);
        canvas.restoreToCount(saveCount);
        if (getBackground() != null) {
            Rect bounds = drawable.getBounds();
            DrawableCompat.setHotspotBounds(getBackground(), bounds.left + dx, bounds.top, bounds.right + dx, bounds.bottom);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.useMaterialThemeColors && this.buttonTintList == null && this.buttonIconTintList == null) {
            setUseMaterialThemeColors(true);
        }
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int extraSpace) {
        int[] drawableStates = super.onCreateDrawableState(extraSpace + 2);
        if (getCheckedState() == 2) {
            mergeDrawableStates(drawableStates, INDETERMINATE_STATE_SET);
        }
        if (isErrorShown()) {
            mergeDrawableStates(drawableStates, ERROR_STATE_SET);
        }
        this.currentStateChecked = DrawableUtils.getCheckedState(drawableStates);
        updateIconTintIfNeeded();
        return drawableStates;
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        updateIconTintIfNeeded();
    }

    public void setChecked(boolean checked) {
        setCheckedState(checked);
    }

    public boolean isChecked() {
        return this.checkedState == 1;
    }

    public void toggle() {
        setChecked(!isChecked());
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        this.onCheckedChangeListener = listener;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        if (info != null && isErrorShown()) {
            info.setText(info.getText() + ", " + this.errorAccessibilityLabel);
        }
    }

    public void setCheckedState(int checkedState2) {
        AutofillManager autofillManager;
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener2;
        if (this.checkedState != checkedState2) {
            this.checkedState = checkedState2;
            super.setChecked(checkedState2 == 1);
            refreshDrawableState();
            setDefaultStateDescription();
            if (!this.broadcasting) {
                this.broadcasting = true;
                LinkedHashSet<OnCheckedStateChangedListener> linkedHashSet = this.onCheckedStateChangedListeners;
                if (linkedHashSet != null) {
                    Iterator it = linkedHashSet.iterator();
                    while (it.hasNext()) {
                        ((OnCheckedStateChangedListener) it.next()).onCheckedStateChangedListener(this, this.checkedState);
                    }
                }
                if (!(this.checkedState == 2 || (onCheckedChangeListener2 = this.onCheckedChangeListener) == null)) {
                    onCheckedChangeListener2.onCheckedChanged(this, isChecked());
                }
                if (Build.VERSION.SDK_INT >= 26 && (autofillManager = (AutofillManager) getContext().getSystemService(AutofillManager.class)) != null) {
                    autofillManager.notifyValueChanged(this);
                }
                this.broadcasting = false;
                if (Build.VERSION.SDK_INT < 21 && this.buttonIconDrawable != null) {
                    refreshDrawableState();
                }
            }
        }
    }

    public int getCheckedState() {
        return this.checkedState;
    }

    public void addOnCheckedStateChangedListener(OnCheckedStateChangedListener listener) {
        this.onCheckedStateChangedListeners.add(listener);
    }

    public void removeOnCheckedStateChangedListener(OnCheckedStateChangedListener listener) {
        this.onCheckedStateChangedListeners.remove(listener);
    }

    public void clearOnCheckedStateChangedListeners() {
        this.onCheckedStateChangedListeners.clear();
    }

    public void setErrorShown(boolean errorShown2) {
        if (this.errorShown != errorShown2) {
            this.errorShown = errorShown2;
            refreshDrawableState();
            Iterator it = this.onErrorChangedListeners.iterator();
            while (it.hasNext()) {
                ((OnErrorChangedListener) it.next()).onErrorChanged(this, this.errorShown);
            }
        }
    }

    public boolean isErrorShown() {
        return this.errorShown;
    }

    public void setErrorAccessibilityLabelResource(int resId) {
        setErrorAccessibilityLabel(resId != 0 ? getResources().getText(resId) : null);
    }

    public void setErrorAccessibilityLabel(CharSequence errorAccessibilityLabel2) {
        this.errorAccessibilityLabel = errorAccessibilityLabel2;
    }

    public CharSequence getErrorAccessibilityLabel() {
        return this.errorAccessibilityLabel;
    }

    public void addOnErrorChangedListener(OnErrorChangedListener listener) {
        this.onErrorChangedListeners.add(listener);
    }

    public void removeOnErrorChangedListener(OnErrorChangedListener listener) {
        this.onErrorChangedListeners.remove(listener);
    }

    public void clearOnErrorChangedListeners() {
        this.onErrorChangedListeners.clear();
    }

    public void setButtonDrawable(int resId) {
        setButtonDrawable(AppCompatResources.getDrawable(getContext(), resId));
    }

    public void setButtonDrawable(Drawable drawable) {
        this.buttonDrawable = drawable;
        this.usingMaterialButtonDrawable = false;
        refreshButtonDrawable();
    }

    public Drawable getButtonDrawable() {
        return this.buttonDrawable;
    }

    public void setButtonTintList(ColorStateList tintList) {
        if (this.buttonTintList != tintList) {
            this.buttonTintList = tintList;
            refreshButtonDrawable();
        }
    }

    public ColorStateList getButtonTintList() {
        return this.buttonTintList;
    }

    public void setButtonTintMode(PorterDuff.Mode tintMode) {
        setSupportButtonTintMode(tintMode);
        refreshButtonDrawable();
    }

    public void setButtonIconDrawableResource(int resId) {
        setButtonIconDrawable(AppCompatResources.getDrawable(getContext(), resId));
    }

    public void setButtonIconDrawable(Drawable drawable) {
        this.buttonIconDrawable = drawable;
        refreshButtonDrawable();
    }

    public Drawable getButtonIconDrawable() {
        return this.buttonIconDrawable;
    }

    public void setButtonIconTintList(ColorStateList tintList) {
        if (this.buttonIconTintList != tintList) {
            this.buttonIconTintList = tintList;
            refreshButtonDrawable();
        }
    }

    public ColorStateList getButtonIconTintList() {
        return this.buttonIconTintList;
    }

    public void setButtonIconTintMode(PorterDuff.Mode tintMode) {
        if (this.buttonIconTintMode != tintMode) {
            this.buttonIconTintMode = tintMode;
            refreshButtonDrawable();
        }
    }

    public PorterDuff.Mode getButtonIconTintMode() {
        return this.buttonIconTintMode;
    }

    public void setUseMaterialThemeColors(boolean useMaterialThemeColors2) {
        this.useMaterialThemeColors = useMaterialThemeColors2;
        if (useMaterialThemeColors2) {
            CompoundButtonCompat.setButtonTintList(this, getMaterialThemeColorsTintList());
        } else {
            CompoundButtonCompat.setButtonTintList(this, (ColorStateList) null);
        }
    }

    public boolean isUseMaterialThemeColors() {
        return this.useMaterialThemeColors;
    }

    public void setCenterIfNoTextEnabled(boolean centerIfNoTextEnabled2) {
        this.centerIfNoTextEnabled = centerIfNoTextEnabled2;
    }

    public boolean isCenterIfNoTextEnabled() {
        return this.centerIfNoTextEnabled;
    }

    private void refreshButtonDrawable() {
        this.buttonDrawable = DrawableUtils.createTintableMutatedDrawableIfNeeded(this.buttonDrawable, this.buttonTintList, CompoundButtonCompat.getButtonTintMode(this));
        this.buttonIconDrawable = DrawableUtils.createTintableMutatedDrawableIfNeeded(this.buttonIconDrawable, this.buttonIconTintList, this.buttonIconTintMode);
        setUpDefaultButtonDrawableAnimationIfNeeded();
        updateButtonTints();
        super.setButtonDrawable(DrawableUtils.compositeTwoLayeredDrawable(this.buttonDrawable, this.buttonIconDrawable));
        refreshDrawableState();
    }

    private void setUpDefaultButtonDrawableAnimationIfNeeded() {
        if (this.usingMaterialButtonDrawable) {
            AnimatedVectorDrawableCompat animatedVectorDrawableCompat = this.transitionToUnchecked;
            if (animatedVectorDrawableCompat != null) {
                animatedVectorDrawableCompat.unregisterAnimationCallback(this.transitionToUncheckedCallback);
                this.transitionToUnchecked.registerAnimationCallback(this.transitionToUncheckedCallback);
            }
            if (Build.VERSION.SDK_INT >= 24) {
                Drawable drawable = this.buttonDrawable;
                if ((drawable instanceof AnimatedStateListDrawable) && this.transitionToUnchecked != null) {
                    ((AnimatedStateListDrawable) drawable).addTransition(C1087R.C1090id.checked, C1087R.C1090id.unchecked, this.transitionToUnchecked, false);
                    ((AnimatedStateListDrawable) this.buttonDrawable).addTransition(C1087R.C1090id.indeterminate, C1087R.C1090id.unchecked, this.transitionToUnchecked, false);
                }
            }
        }
    }

    private void updateButtonTints() {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        Drawable drawable = this.buttonDrawable;
        if (!(drawable == null || (colorStateList2 = this.buttonTintList) == null)) {
            DrawableCompat.setTintList(drawable, colorStateList2);
        }
        Drawable drawable2 = this.buttonIconDrawable;
        if (drawable2 != null && (colorStateList = this.buttonIconTintList) != null) {
            DrawableCompat.setTintList(drawable2, colorStateList);
        }
    }

    private void updateIconTintIfNeeded() {
        Drawable drawable;
        ColorStateList colorStateList;
        if (Build.VERSION.SDK_INT < 21 && (drawable = this.buttonIconDrawable) != null && (colorStateList = this.buttonIconTintList) != null) {
            drawable.setColorFilter(DrawableUtils.updateTintFilter(drawable, colorStateList, this.buttonIconTintMode));
        }
    }

    public void setStateDescription(CharSequence stateDescription) {
        this.customStateDescription = stateDescription;
        if (stateDescription == null) {
            setDefaultStateDescription();
        } else {
            super.setStateDescription(stateDescription);
        }
    }

    private void setDefaultStateDescription() {
        if (Build.VERSION.SDK_INT >= 30 && this.customStateDescription == null) {
            super.setStateDescription(getButtonStateDescription());
        }
    }

    private String getButtonStateDescription() {
        int i = this.checkedState;
        if (i == 1) {
            return getResources().getString(C1087R.string.mtrl_checkbox_state_description_checked);
        }
        if (i == 0) {
            return getResources().getString(C1087R.string.mtrl_checkbox_state_description_unchecked);
        }
        return getResources().getString(C1087R.string.mtrl_checkbox_state_description_indeterminate);
    }

    private ColorStateList getSuperButtonTintList() {
        ColorStateList colorStateList = this.buttonTintList;
        if (colorStateList != null) {
            return colorStateList;
        }
        if (Build.VERSION.SDK_INT < 21 || super.getButtonTintList() == null) {
            return getSupportButtonTintList();
        }
        return super.getButtonTintList();
    }

    private boolean isButtonDrawableLegacy(TintTypedArray attributes) {
        int buttonResourceId = attributes.getResourceId(C1087R.styleable.MaterialCheckBox_android_button, 0);
        int buttonCompatResourceId = attributes.getResourceId(C1087R.styleable.MaterialCheckBox_buttonCompat, 0);
        if (Build.VERSION.SDK_INT < 21) {
            if (buttonResourceId == C1087R.C1089drawable.abc_btn_check_material && buttonCompatResourceId == C1087R.C1089drawable.abc_btn_check_material_anim) {
                return true;
            }
            return false;
        } else if (buttonResourceId == FRAMEWORK_BUTTON_DRAWABLE_RES_ID && buttonCompatResourceId == 0) {
            return true;
        } else {
            return false;
        }
    }

    private ColorStateList getMaterialThemeColorsTintList() {
        if (this.materialThemeColorsTintList == null) {
            int[][] iArr = CHECKBOX_STATES;
            int[] checkBoxColorsList = new int[iArr.length];
            int colorControlActivated = MaterialColors.getColor(this, C1087R.attr.colorControlActivated);
            int colorError = MaterialColors.getColor(this, C1087R.attr.colorError);
            int colorSurface = MaterialColors.getColor(this, C1087R.attr.colorSurface);
            int colorOnSurface = MaterialColors.getColor(this, C1087R.attr.colorOnSurface);
            checkBoxColorsList[0] = MaterialColors.layer(colorSurface, colorError, 1.0f);
            checkBoxColorsList[1] = MaterialColors.layer(colorSurface, colorControlActivated, 1.0f);
            checkBoxColorsList[2] = MaterialColors.layer(colorSurface, colorOnSurface, 0.54f);
            checkBoxColorsList[3] = MaterialColors.layer(colorSurface, colorOnSurface, 0.38f);
            checkBoxColorsList[4] = MaterialColors.layer(colorSurface, colorOnSurface, 0.38f);
            this.materialThemeColorsTintList = new ColorStateList(iArr, checkBoxColorsList);
        }
        return this.materialThemeColorsTintList;
    }

    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.checkedState = getCheckedState();
        return ss;
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setCheckedState(ss.checkedState);
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        int checkedState;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.checkedState = ((Integer) in.readValue(getClass().getClassLoader())).intValue();
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeValue(Integer.valueOf(this.checkedState));
        }

        public String toString() {
            return "MaterialCheckBox.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " CheckedState=" + getCheckedStateString() + "}";
        }

        private String getCheckedStateString() {
            switch (this.checkedState) {
                case 1:
                    return CommonCssConstants.CHECKED;
                case 2:
                    return "indeterminate";
                default:
                    return "unchecked";
            }
        }
    }
}
