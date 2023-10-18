package com.google.android.material.textfield;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityManagerCompat;
import androidx.core.widget.TextViewCompat;
import com.google.android.material.C1087R;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Iterator;
import java.util.LinkedHashSet;

class EndCompoundLayout extends LinearLayout {
    private final AccessibilityManager accessibilityManager;
    /* access modifiers changed from: private */
    public EditText editText;
    /* access modifiers changed from: private */
    public final TextWatcher editTextWatcher = new TextWatcherAdapter() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            EndCompoundLayout.this.getEndIconDelegate().beforeEditTextChanged(s, start, count, after);
        }

        public void afterTextChanged(Editable s) {
            EndCompoundLayout.this.getEndIconDelegate().afterEditTextChanged(s);
        }
    };
    private final LinkedHashSet<TextInputLayout.OnEndIconChangedListener> endIconChangedListeners = new LinkedHashSet<>();
    private final EndIconDelegates endIconDelegates;
    private final FrameLayout endIconFrame;
    private int endIconMinSize;
    private int endIconMode = 0;
    private View.OnLongClickListener endIconOnLongClickListener;
    private ImageView.ScaleType endIconScaleType;
    private ColorStateList endIconTintList;
    private PorterDuff.Mode endIconTintMode;
    private final CheckableImageButton endIconView;
    private View.OnLongClickListener errorIconOnLongClickListener;
    private ColorStateList errorIconTintList;
    private PorterDuff.Mode errorIconTintMode;
    private final CheckableImageButton errorIconView;
    private boolean hintExpanded;
    private final TextInputLayout.OnEditTextAttachedListener onEditTextAttachedListener;
    private CharSequence suffixText;
    private final TextView suffixTextView;
    final TextInputLayout textInputLayout;
    private AccessibilityManagerCompat.TouchExplorationStateChangeListener touchExplorationStateChangeListener;

    EndCompoundLayout(TextInputLayout textInputLayout2, TintTypedArray a) {
        super(textInputLayout2.getContext());
        C13202 r1 = new TextInputLayout.OnEditTextAttachedListener() {
            public void onEditTextAttached(TextInputLayout textInputLayout) {
                if (EndCompoundLayout.this.editText != textInputLayout.getEditText()) {
                    if (EndCompoundLayout.this.editText != null) {
                        EndCompoundLayout.this.editText.removeTextChangedListener(EndCompoundLayout.this.editTextWatcher);
                        if (EndCompoundLayout.this.editText.getOnFocusChangeListener() == EndCompoundLayout.this.getEndIconDelegate().getOnEditTextFocusChangeListener()) {
                            EndCompoundLayout.this.editText.setOnFocusChangeListener((View.OnFocusChangeListener) null);
                        }
                    }
                    EditText unused = EndCompoundLayout.this.editText = textInputLayout.getEditText();
                    if (EndCompoundLayout.this.editText != null) {
                        EndCompoundLayout.this.editText.addTextChangedListener(EndCompoundLayout.this.editTextWatcher);
                    }
                    EndCompoundLayout.this.getEndIconDelegate().onEditTextAttached(EndCompoundLayout.this.editText);
                    EndCompoundLayout endCompoundLayout = EndCompoundLayout.this;
                    endCompoundLayout.setOnFocusChangeListenersIfNeeded(endCompoundLayout.getEndIconDelegate());
                }
            }
        };
        this.onEditTextAttachedListener = r1;
        this.accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        this.textInputLayout = textInputLayout2;
        setVisibility(8);
        setOrientation(0);
        setLayoutParams(new FrameLayout.LayoutParams(-2, -1, GravityCompat.END));
        FrameLayout frameLayout = new FrameLayout(getContext());
        this.endIconFrame = frameLayout;
        frameLayout.setVisibility(8);
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(-2, -1));
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        CheckableImageButton createIconView = createIconView(this, layoutInflater, C1087R.C1090id.text_input_error_icon);
        this.errorIconView = createIconView;
        CheckableImageButton createIconView2 = createIconView(frameLayout, layoutInflater, C1087R.C1090id.text_input_end_icon);
        this.endIconView = createIconView2;
        this.endIconDelegates = new EndIconDelegates(this, a);
        AppCompatTextView appCompatTextView = new AppCompatTextView(getContext());
        this.suffixTextView = appCompatTextView;
        initErrorIconView(a);
        initEndIconView(a);
        initSuffixTextView(a);
        frameLayout.addView(createIconView2);
        addView(appCompatTextView);
        addView(frameLayout);
        addView(createIconView);
        textInputLayout2.addOnEditTextAttachedListener(r1);
        addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            public void onViewAttachedToWindow(View ignored) {
                EndCompoundLayout.this.addTouchExplorationStateChangeListenerIfNeeded();
            }

            public void onViewDetachedFromWindow(View ignored) {
                EndCompoundLayout.this.removeTouchExplorationStateChangeListenerIfNeeded();
            }
        });
    }

    private CheckableImageButton createIconView(ViewGroup root, LayoutInflater inflater, int id) {
        CheckableImageButton iconView = (CheckableImageButton) inflater.inflate(C1087R.C1092layout.design_text_input_end_icon, root, false);
        iconView.setId(id);
        IconHelper.setCompatRippleBackgroundIfNeeded(iconView);
        if (MaterialResources.isFontScaleAtLeast1_3(getContext())) {
            MarginLayoutParamsCompat.setMarginStart((ViewGroup.MarginLayoutParams) iconView.getLayoutParams(), 0);
        }
        return iconView;
    }

    private void initErrorIconView(TintTypedArray a) {
        if (a.hasValue(C1087R.styleable.TextInputLayout_errorIconTint)) {
            this.errorIconTintList = MaterialResources.getColorStateList(getContext(), a, C1087R.styleable.TextInputLayout_errorIconTint);
        }
        if (a.hasValue(C1087R.styleable.TextInputLayout_errorIconTintMode)) {
            this.errorIconTintMode = ViewUtils.parseTintMode(a.getInt(C1087R.styleable.TextInputLayout_errorIconTintMode, -1), (PorterDuff.Mode) null);
        }
        if (a.hasValue(C1087R.styleable.TextInputLayout_errorIconDrawable)) {
            setErrorIconDrawable(a.getDrawable(C1087R.styleable.TextInputLayout_errorIconDrawable));
        }
        this.errorIconView.setContentDescription(getResources().getText(C1087R.string.error_icon_content_description));
        ViewCompat.setImportantForAccessibility(this.errorIconView, 2);
        this.errorIconView.setClickable(false);
        this.errorIconView.setPressable(false);
        this.errorIconView.setFocusable(false);
    }

    private void initEndIconView(TintTypedArray a) {
        if (!a.hasValue(C1087R.styleable.TextInputLayout_passwordToggleEnabled)) {
            if (a.hasValue(C1087R.styleable.TextInputLayout_endIconTint)) {
                this.endIconTintList = MaterialResources.getColorStateList(getContext(), a, C1087R.styleable.TextInputLayout_endIconTint);
            }
            if (a.hasValue(C1087R.styleable.TextInputLayout_endIconTintMode)) {
                this.endIconTintMode = ViewUtils.parseTintMode(a.getInt(C1087R.styleable.TextInputLayout_endIconTintMode, -1), (PorterDuff.Mode) null);
            }
        }
        if (a.hasValue(C1087R.styleable.TextInputLayout_endIconMode)) {
            setEndIconMode(a.getInt(C1087R.styleable.TextInputLayout_endIconMode, 0));
            if (a.hasValue(C1087R.styleable.TextInputLayout_endIconContentDescription)) {
                setEndIconContentDescription(a.getText(C1087R.styleable.TextInputLayout_endIconContentDescription));
            }
            setEndIconCheckable(a.getBoolean(C1087R.styleable.TextInputLayout_endIconCheckable, true));
        } else if (a.hasValue(C1087R.styleable.TextInputLayout_passwordToggleEnabled)) {
            if (a.hasValue(C1087R.styleable.TextInputLayout_passwordToggleTint)) {
                this.endIconTintList = MaterialResources.getColorStateList(getContext(), a, C1087R.styleable.TextInputLayout_passwordToggleTint);
            }
            if (a.hasValue(C1087R.styleable.TextInputLayout_passwordToggleTintMode)) {
                this.endIconTintMode = ViewUtils.parseTintMode(a.getInt(C1087R.styleable.TextInputLayout_passwordToggleTintMode, -1), (PorterDuff.Mode) null);
            }
            setEndIconMode(a.getBoolean(C1087R.styleable.TextInputLayout_passwordToggleEnabled, false));
            setEndIconContentDescription(a.getText(C1087R.styleable.TextInputLayout_passwordToggleContentDescription));
        }
        setEndIconMinSize(a.getDimensionPixelSize(C1087R.styleable.TextInputLayout_endIconMinSize, getResources().getDimensionPixelSize(C1087R.dimen.mtrl_min_touch_target_size)));
        if (a.hasValue(C1087R.styleable.TextInputLayout_endIconScaleType)) {
            setEndIconScaleType(IconHelper.convertScaleType(a.getInt(C1087R.styleable.TextInputLayout_endIconScaleType, -1)));
        }
    }

    private void initSuffixTextView(TintTypedArray a) {
        this.suffixTextView.setVisibility(8);
        this.suffixTextView.setId(C1087R.C1090id.textinput_suffix_text);
        this.suffixTextView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2, 80.0f));
        ViewCompat.setAccessibilityLiveRegion(this.suffixTextView, 1);
        setSuffixTextAppearance(a.getResourceId(C1087R.styleable.TextInputLayout_suffixTextAppearance, 0));
        if (a.hasValue(C1087R.styleable.TextInputLayout_suffixTextColor)) {
            setSuffixTextColor(a.getColorStateList(C1087R.styleable.TextInputLayout_suffixTextColor));
        }
        setSuffixText(a.getText(C1087R.styleable.TextInputLayout_suffixText));
    }

    /* access modifiers changed from: package-private */
    public void setErrorIconDrawable(int resId) {
        setErrorIconDrawable(resId != 0 ? AppCompatResources.getDrawable(getContext(), resId) : null);
        refreshErrorIconDrawableState();
    }

    /* access modifiers changed from: package-private */
    public void setErrorIconDrawable(Drawable errorIconDrawable) {
        this.errorIconView.setImageDrawable(errorIconDrawable);
        updateErrorIconVisibility();
        IconHelper.applyIconTint(this.textInputLayout, this.errorIconView, this.errorIconTintList, this.errorIconTintMode);
    }

    /* access modifiers changed from: package-private */
    public Drawable getErrorIconDrawable() {
        return this.errorIconView.getDrawable();
    }

    /* access modifiers changed from: package-private */
    public void setErrorIconTintList(ColorStateList errorIconTintList2) {
        if (this.errorIconTintList != errorIconTintList2) {
            this.errorIconTintList = errorIconTintList2;
            IconHelper.applyIconTint(this.textInputLayout, this.errorIconView, errorIconTintList2, this.errorIconTintMode);
        }
    }

    /* access modifiers changed from: package-private */
    public void setErrorIconTintMode(PorterDuff.Mode errorIconTintMode2) {
        if (this.errorIconTintMode != errorIconTintMode2) {
            this.errorIconTintMode = errorIconTintMode2;
            IconHelper.applyIconTint(this.textInputLayout, this.errorIconView, this.errorIconTintList, errorIconTintMode2);
        }
    }

    /* access modifiers changed from: package-private */
    public void setErrorIconOnClickListener(View.OnClickListener errorIconOnClickListener) {
        IconHelper.setIconOnClickListener(this.errorIconView, errorIconOnClickListener, this.errorIconOnLongClickListener);
    }

    /* access modifiers changed from: package-private */
    public CheckableImageButton getEndIconView() {
        return this.endIconView;
    }

    /* access modifiers changed from: package-private */
    public EndIconDelegate getEndIconDelegate() {
        return this.endIconDelegates.get(this.endIconMode);
    }

    /* access modifiers changed from: package-private */
    public int getEndIconMode() {
        return this.endIconMode;
    }

    /* access modifiers changed from: package-private */
    public void setEndIconMode(int endIconMode2) {
        if (this.endIconMode != endIconMode2) {
            tearDownDelegate(getEndIconDelegate());
            int previousEndIconMode = this.endIconMode;
            this.endIconMode = endIconMode2;
            dispatchOnEndIconChanged(previousEndIconMode);
            setEndIconVisible(endIconMode2 != 0);
            EndIconDelegate delegate = getEndIconDelegate();
            setEndIconDrawable(getIconResId(delegate));
            setEndIconContentDescription(delegate.getIconContentDescriptionResId());
            setEndIconCheckable(delegate.isIconCheckable());
            if (delegate.isBoxBackgroundModeSupported(this.textInputLayout.getBoxBackgroundMode())) {
                setUpDelegate(delegate);
                setEndIconOnClickListener(delegate.getOnIconClickListener());
                EditText editText2 = this.editText;
                if (editText2 != null) {
                    delegate.onEditTextAttached(editText2);
                    setOnFocusChangeListenersIfNeeded(delegate);
                }
                IconHelper.applyIconTint(this.textInputLayout, this.endIconView, this.endIconTintList, this.endIconTintMode);
                refreshIconState(true);
                return;
            }
            throw new IllegalStateException("The current box background mode " + this.textInputLayout.getBoxBackgroundMode() + " is not supported by the end icon mode " + endIconMode2);
        }
    }

    /* access modifiers changed from: package-private */
    public void refreshIconState(boolean force) {
        boolean wasActivated;
        boolean wasChecked;
        boolean stateChanged = false;
        EndIconDelegate delegate = getEndIconDelegate();
        if (delegate.isIconCheckable() && (wasChecked = this.endIconView.isChecked()) != delegate.isIconChecked()) {
            this.endIconView.setChecked(!wasChecked);
            stateChanged = true;
        }
        if (delegate.isIconActivable() && (wasActivated = this.endIconView.isActivated()) != delegate.isIconActivated()) {
            setEndIconActivated(!wasActivated);
            stateChanged = true;
        }
        if (force || stateChanged) {
            refreshEndIconDrawableState();
        }
    }

    private void setUpDelegate(EndIconDelegate delegate) {
        delegate.setUp();
        this.touchExplorationStateChangeListener = delegate.getTouchExplorationStateChangeListener();
        addTouchExplorationStateChangeListenerIfNeeded();
    }

    private void tearDownDelegate(EndIconDelegate delegate) {
        removeTouchExplorationStateChangeListenerIfNeeded();
        this.touchExplorationStateChangeListener = null;
        delegate.tearDown();
    }

    /* access modifiers changed from: private */
    public void addTouchExplorationStateChangeListenerIfNeeded() {
        if (this.touchExplorationStateChangeListener != null && this.accessibilityManager != null && ViewCompat.isAttachedToWindow(this)) {
            AccessibilityManagerCompat.addTouchExplorationStateChangeListener(this.accessibilityManager, this.touchExplorationStateChangeListener);
        }
    }

    /* access modifiers changed from: private */
    public void removeTouchExplorationStateChangeListenerIfNeeded() {
        AccessibilityManager accessibilityManager2;
        AccessibilityManagerCompat.TouchExplorationStateChangeListener touchExplorationStateChangeListener2 = this.touchExplorationStateChangeListener;
        if (touchExplorationStateChangeListener2 != null && (accessibilityManager2 = this.accessibilityManager) != null) {
            AccessibilityManagerCompat.removeTouchExplorationStateChangeListener(accessibilityManager2, touchExplorationStateChangeListener2);
        }
    }

    private int getIconResId(EndIconDelegate delegate) {
        int customIconResId = this.endIconDelegates.customEndIconDrawableId;
        return customIconResId == 0 ? delegate.getIconDrawableResId() : customIconResId;
    }

    /* access modifiers changed from: package-private */
    public void setEndIconOnClickListener(View.OnClickListener endIconOnClickListener) {
        IconHelper.setIconOnClickListener(this.endIconView, endIconOnClickListener, this.endIconOnLongClickListener);
    }

    /* access modifiers changed from: package-private */
    public void setEndIconOnLongClickListener(View.OnLongClickListener endIconOnLongClickListener2) {
        this.endIconOnLongClickListener = endIconOnLongClickListener2;
        IconHelper.setIconOnLongClickListener(this.endIconView, endIconOnLongClickListener2);
    }

    /* access modifiers changed from: package-private */
    public void setErrorIconOnLongClickListener(View.OnLongClickListener errorIconOnLongClickListener2) {
        this.errorIconOnLongClickListener = errorIconOnLongClickListener2;
        IconHelper.setIconOnLongClickListener(this.errorIconView, errorIconOnLongClickListener2);
    }

    /* access modifiers changed from: private */
    public void setOnFocusChangeListenersIfNeeded(EndIconDelegate delegate) {
        if (this.editText != null) {
            if (delegate.getOnEditTextFocusChangeListener() != null) {
                this.editText.setOnFocusChangeListener(delegate.getOnEditTextFocusChangeListener());
            }
            if (delegate.getOnIconViewFocusChangeListener() != null) {
                this.endIconView.setOnFocusChangeListener(delegate.getOnIconViewFocusChangeListener());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void refreshErrorIconDrawableState() {
        IconHelper.refreshIconDrawableState(this.textInputLayout, this.errorIconView, this.errorIconTintList);
    }

    /* access modifiers changed from: package-private */
    public void setEndIconVisible(boolean visible) {
        if (isEndIconVisible() != visible) {
            this.endIconView.setVisibility(visible ? 0 : 8);
            updateEndLayoutVisibility();
            updateSuffixTextViewPadding();
            this.textInputLayout.updateDummyDrawables();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isEndIconVisible() {
        return this.endIconFrame.getVisibility() == 0 && this.endIconView.getVisibility() == 0;
    }

    /* access modifiers changed from: package-private */
    public void setEndIconActivated(boolean endIconActivated) {
        this.endIconView.setActivated(endIconActivated);
    }

    /* access modifiers changed from: package-private */
    public void refreshEndIconDrawableState() {
        IconHelper.refreshIconDrawableState(this.textInputLayout, this.endIconView, this.endIconTintList);
    }

    /* access modifiers changed from: package-private */
    public void setEndIconCheckable(boolean endIconCheckable) {
        this.endIconView.setCheckable(endIconCheckable);
    }

    /* access modifiers changed from: package-private */
    public boolean isEndIconCheckable() {
        return this.endIconView.isCheckable();
    }

    /* access modifiers changed from: package-private */
    public boolean isEndIconChecked() {
        return hasEndIcon() && this.endIconView.isChecked();
    }

    /* access modifiers changed from: package-private */
    public void checkEndIcon() {
        this.endIconView.performClick();
        this.endIconView.jumpDrawablesToCurrentState();
    }

    /* access modifiers changed from: package-private */
    public void setEndIconDrawable(int resId) {
        setEndIconDrawable(resId != 0 ? AppCompatResources.getDrawable(getContext(), resId) : null);
    }

    /* access modifiers changed from: package-private */
    public void setEndIconDrawable(Drawable endIconDrawable) {
        this.endIconView.setImageDrawable(endIconDrawable);
        if (endIconDrawable != null) {
            IconHelper.applyIconTint(this.textInputLayout, this.endIconView, this.endIconTintList, this.endIconTintMode);
            refreshEndIconDrawableState();
        }
    }

    /* access modifiers changed from: package-private */
    public Drawable getEndIconDrawable() {
        return this.endIconView.getDrawable();
    }

    /* access modifiers changed from: package-private */
    public void setEndIconContentDescription(int resId) {
        setEndIconContentDescription(resId != 0 ? getResources().getText(resId) : null);
    }

    /* access modifiers changed from: package-private */
    public void setEndIconContentDescription(CharSequence endIconContentDescription) {
        if (getEndIconContentDescription() != endIconContentDescription) {
            this.endIconView.setContentDescription(endIconContentDescription);
        }
    }

    /* access modifiers changed from: package-private */
    public CharSequence getEndIconContentDescription() {
        return this.endIconView.getContentDescription();
    }

    /* access modifiers changed from: package-private */
    public void setEndIconTintList(ColorStateList endIconTintList2) {
        if (this.endIconTintList != endIconTintList2) {
            this.endIconTintList = endIconTintList2;
            IconHelper.applyIconTint(this.textInputLayout, this.endIconView, endIconTintList2, this.endIconTintMode);
        }
    }

    /* access modifiers changed from: package-private */
    public void setEndIconTintMode(PorterDuff.Mode endIconTintMode2) {
        if (this.endIconTintMode != endIconTintMode2) {
            this.endIconTintMode = endIconTintMode2;
            IconHelper.applyIconTint(this.textInputLayout, this.endIconView, this.endIconTintList, endIconTintMode2);
        }
    }

    /* access modifiers changed from: package-private */
    public void setEndIconMinSize(int iconSize) {
        if (iconSize < 0) {
            throw new IllegalArgumentException("endIconSize cannot be less than 0");
        } else if (iconSize != this.endIconMinSize) {
            this.endIconMinSize = iconSize;
            IconHelper.setIconMinSize(this.endIconView, iconSize);
            IconHelper.setIconMinSize(this.errorIconView, iconSize);
        }
    }

    /* access modifiers changed from: package-private */
    public int getEndIconMinSize() {
        return this.endIconMinSize;
    }

    /* access modifiers changed from: package-private */
    public void setEndIconScaleType(ImageView.ScaleType endIconScaleType2) {
        this.endIconScaleType = endIconScaleType2;
        IconHelper.setIconScaleType(this.endIconView, endIconScaleType2);
        IconHelper.setIconScaleType(this.errorIconView, endIconScaleType2);
    }

    /* access modifiers changed from: package-private */
    public ImageView.ScaleType getEndIconScaleType() {
        return this.endIconScaleType;
    }

    /* access modifiers changed from: package-private */
    public void addOnEndIconChangedListener(TextInputLayout.OnEndIconChangedListener listener) {
        this.endIconChangedListeners.add(listener);
    }

    /* access modifiers changed from: package-private */
    public void removeOnEndIconChangedListener(TextInputLayout.OnEndIconChangedListener listener) {
        this.endIconChangedListeners.remove(listener);
    }

    /* access modifiers changed from: package-private */
    public void clearOnEndIconChangedListeners() {
        this.endIconChangedListeners.clear();
    }

    /* access modifiers changed from: package-private */
    public boolean hasEndIcon() {
        return this.endIconMode != 0;
    }

    /* access modifiers changed from: package-private */
    public TextView getSuffixTextView() {
        return this.suffixTextView;
    }

    /* access modifiers changed from: package-private */
    public void setSuffixText(CharSequence suffixText2) {
        this.suffixText = TextUtils.isEmpty(suffixText2) ? null : suffixText2;
        this.suffixTextView.setText(suffixText2);
        updateSuffixTextVisibility();
    }

    /* access modifiers changed from: package-private */
    public CharSequence getSuffixText() {
        return this.suffixText;
    }

    /* access modifiers changed from: package-private */
    public void setSuffixTextAppearance(int suffixTextAppearance) {
        TextViewCompat.setTextAppearance(this.suffixTextView, suffixTextAppearance);
    }

    /* access modifiers changed from: package-private */
    public void setSuffixTextColor(ColorStateList suffixTextColor) {
        this.suffixTextView.setTextColor(suffixTextColor);
    }

    /* access modifiers changed from: package-private */
    public ColorStateList getSuffixTextColor() {
        return this.suffixTextView.getTextColors();
    }

    /* access modifiers changed from: package-private */
    public void setPasswordVisibilityToggleDrawable(int resId) {
        setPasswordVisibilityToggleDrawable(resId != 0 ? AppCompatResources.getDrawable(getContext(), resId) : null);
    }

    /* access modifiers changed from: package-private */
    public void setPasswordVisibilityToggleDrawable(Drawable icon) {
        this.endIconView.setImageDrawable(icon);
    }

    /* access modifiers changed from: package-private */
    public void setPasswordVisibilityToggleContentDescription(int resId) {
        setPasswordVisibilityToggleContentDescription(resId != 0 ? getResources().getText(resId) : null);
    }

    /* access modifiers changed from: package-private */
    public void setPasswordVisibilityToggleContentDescription(CharSequence description) {
        this.endIconView.setContentDescription(description);
    }

    /* access modifiers changed from: package-private */
    public Drawable getPasswordVisibilityToggleDrawable() {
        return this.endIconView.getDrawable();
    }

    /* access modifiers changed from: package-private */
    public CharSequence getPasswordVisibilityToggleContentDescription() {
        return this.endIconView.getContentDescription();
    }

    /* access modifiers changed from: package-private */
    public boolean isPasswordVisibilityToggleEnabled() {
        return this.endIconMode == 1;
    }

    /* access modifiers changed from: package-private */
    public void setPasswordVisibilityToggleEnabled(boolean enabled) {
        if (enabled && this.endIconMode != 1) {
            setEndIconMode(1);
        } else if (!enabled) {
            setEndIconMode(0);
        }
    }

    /* access modifiers changed from: package-private */
    public void setPasswordVisibilityToggleTintList(ColorStateList tintList) {
        this.endIconTintList = tintList;
        IconHelper.applyIconTint(this.textInputLayout, this.endIconView, tintList, this.endIconTintMode);
    }

    /* access modifiers changed from: package-private */
    public void setPasswordVisibilityToggleTintMode(PorterDuff.Mode mode) {
        this.endIconTintMode = mode;
        IconHelper.applyIconTint(this.textInputLayout, this.endIconView, this.endIconTintList, mode);
    }

    /* access modifiers changed from: package-private */
    public void togglePasswordVisibilityToggle(boolean shouldSkipAnimations) {
        if (this.endIconMode == 1) {
            this.endIconView.performClick();
            if (shouldSkipAnimations) {
                this.endIconView.jumpDrawablesToCurrentState();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onHintStateChanged(boolean hintExpanded2) {
        this.hintExpanded = hintExpanded2;
        updateSuffixTextVisibility();
    }

    /* access modifiers changed from: package-private */
    public void onTextInputBoxStateUpdated() {
        updateErrorIconVisibility();
        refreshErrorIconDrawableState();
        refreshEndIconDrawableState();
        if (getEndIconDelegate().shouldTintIconOnError()) {
            tintEndIconOnError(this.textInputLayout.shouldShowError());
        }
    }

    private void updateSuffixTextVisibility() {
        int oldVisibility = this.suffixTextView.getVisibility();
        boolean z = false;
        int newVisibility = (this.suffixText == null || this.hintExpanded) ? 8 : 0;
        if (oldVisibility != newVisibility) {
            EndIconDelegate endIconDelegate = getEndIconDelegate();
            if (newVisibility == 0) {
                z = true;
            }
            endIconDelegate.onSuffixVisibilityChanged(z);
        }
        updateEndLayoutVisibility();
        this.suffixTextView.setVisibility(newVisibility);
        this.textInputLayout.updateDummyDrawables();
    }

    /* access modifiers changed from: package-private */
    public void updateSuffixTextViewPadding() {
        if (this.textInputLayout.editText != null) {
            ViewCompat.setPaddingRelative(this.suffixTextView, getContext().getResources().getDimensionPixelSize(C1087R.dimen.material_input_text_to_prefix_suffix_padding), this.textInputLayout.editText.getPaddingTop(), (isEndIconVisible() || isErrorIconVisible()) ? 0 : ViewCompat.getPaddingEnd(this.textInputLayout.editText), this.textInputLayout.editText.getPaddingBottom());
        }
    }

    /* access modifiers changed from: package-private */
    public CheckableImageButton getCurrentEndIconView() {
        if (isErrorIconVisible()) {
            return this.errorIconView;
        }
        if (!hasEndIcon() || !isEndIconVisible()) {
            return null;
        }
        return this.endIconView;
    }

    /* access modifiers changed from: package-private */
    public boolean isErrorIconVisible() {
        return this.errorIconView.getVisibility() == 0;
    }

    private void updateErrorIconVisibility() {
        int i = 0;
        boolean visible = getErrorIconDrawable() != null && this.textInputLayout.isErrorEnabled() && this.textInputLayout.shouldShowError();
        CheckableImageButton checkableImageButton = this.errorIconView;
        if (!visible) {
            i = 8;
        }
        checkableImageButton.setVisibility(i);
        updateEndLayoutVisibility();
        updateSuffixTextViewPadding();
        if (!hasEndIcon()) {
            this.textInputLayout.updateDummyDrawables();
        }
    }

    private void updateEndLayoutVisibility() {
        int i = 8;
        this.endIconFrame.setVisibility((this.endIconView.getVisibility() != 0 || isErrorIconVisible()) ? 8 : 0);
        if (isEndIconVisible() || isErrorIconVisible() || ((this.suffixText == null || this.hintExpanded) ? 8 : 0) == 0) {
            i = 0;
        }
        setVisibility(i);
    }

    private void dispatchOnEndIconChanged(int previousIcon) {
        Iterator it = this.endIconChangedListeners.iterator();
        while (it.hasNext()) {
            ((TextInputLayout.OnEndIconChangedListener) it.next()).onEndIconChanged(this.textInputLayout, previousIcon);
        }
    }

    private void tintEndIconOnError(boolean tintEndIconOnError) {
        if (!tintEndIconOnError || getEndIconDrawable() == null) {
            IconHelper.applyIconTint(this.textInputLayout, this.endIconView, this.endIconTintList, this.endIconTintMode);
            return;
        }
        Drawable endIconDrawable = DrawableCompat.wrap(getEndIconDrawable()).mutate();
        DrawableCompat.setTint(endIconDrawable, this.textInputLayout.getErrorCurrentTextColors());
        this.endIconView.setImageDrawable(endIconDrawable);
    }

    private static class EndIconDelegates {
        /* access modifiers changed from: private */
        public final int customEndIconDrawableId;
        private final SparseArray<EndIconDelegate> delegates = new SparseArray<>();
        private final EndCompoundLayout endLayout;
        private final int passwordIconDrawableId;

        EndIconDelegates(EndCompoundLayout endLayout2, TintTypedArray a) {
            this.endLayout = endLayout2;
            this.customEndIconDrawableId = a.getResourceId(C1087R.styleable.TextInputLayout_endIconDrawable, 0);
            this.passwordIconDrawableId = a.getResourceId(C1087R.styleable.TextInputLayout_passwordToggleDrawable, 0);
        }

        /* access modifiers changed from: package-private */
        public EndIconDelegate get(int endIconMode) {
            EndIconDelegate delegate = this.delegates.get(endIconMode);
            if (delegate != null) {
                return delegate;
            }
            EndIconDelegate delegate2 = create(endIconMode);
            this.delegates.append(endIconMode, delegate2);
            return delegate2;
        }

        private EndIconDelegate create(int endIconMode) {
            switch (endIconMode) {
                case -1:
                    return new CustomEndIconDelegate(this.endLayout);
                case 0:
                    return new NoEndIconDelegate(this.endLayout);
                case 1:
                    return new PasswordToggleEndIconDelegate(this.endLayout, this.passwordIconDrawableId);
                case 2:
                    return new ClearTextEndIconDelegate(this.endLayout);
                case 3:
                    return new DropdownMenuEndIconDelegate(this.endLayout);
                default:
                    throw new IllegalArgumentException("Invalid end icon mode: " + endIconMode);
            }
        }
    }
}
