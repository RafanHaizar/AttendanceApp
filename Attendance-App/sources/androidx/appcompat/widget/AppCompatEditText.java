package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.DragEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.textclassifier.TextClassifier;
import android.widget.EditText;
import androidx.appcompat.C0503R;
import androidx.core.view.ContentInfoCompat;
import androidx.core.view.OnReceiveContentViewBehavior;
import androidx.core.view.TintableBackgroundView;
import androidx.core.view.ViewCompat;
import androidx.core.view.inputmethod.EditorInfoCompat;
import androidx.core.view.inputmethod.InputConnectionCompat;
import androidx.core.widget.TextViewCompat;
import androidx.core.widget.TextViewOnReceiveContentListener;
import androidx.core.widget.TintableCompoundDrawablesView;

public class AppCompatEditText extends EditText implements TintableBackgroundView, OnReceiveContentViewBehavior, EmojiCompatConfigurationView, TintableCompoundDrawablesView {
    private final AppCompatEmojiEditTextHelper mAppCompatEmojiEditTextHelper;
    private final AppCompatBackgroundHelper mBackgroundTintHelper;
    private final TextViewOnReceiveContentListener mDefaultOnReceiveContentListener;
    private SuperCaller mSuperCaller;
    private final AppCompatTextClassifierHelper mTextClassifierHelper;
    private final AppCompatTextHelper mTextHelper;

    public AppCompatEditText(Context context) {
        this(context, (AttributeSet) null);
    }

    public AppCompatEditText(Context context, AttributeSet attrs) {
        this(context, attrs, C0503R.attr.editTextStyle);
    }

    public AppCompatEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(TintContextWrapper.wrap(context), attrs, defStyleAttr);
        ThemeUtils.checkAppCompatTheme(this, getContext());
        AppCompatBackgroundHelper appCompatBackgroundHelper = new AppCompatBackgroundHelper(this);
        this.mBackgroundTintHelper = appCompatBackgroundHelper;
        appCompatBackgroundHelper.loadFromAttributes(attrs, defStyleAttr);
        AppCompatTextHelper appCompatTextHelper = new AppCompatTextHelper(this);
        this.mTextHelper = appCompatTextHelper;
        appCompatTextHelper.loadFromAttributes(attrs, defStyleAttr);
        appCompatTextHelper.applyCompoundDrawablesTints();
        this.mTextClassifierHelper = new AppCompatTextClassifierHelper(this);
        this.mDefaultOnReceiveContentListener = new TextViewOnReceiveContentListener();
        AppCompatEmojiEditTextHelper appCompatEmojiEditTextHelper = new AppCompatEmojiEditTextHelper(this);
        this.mAppCompatEmojiEditTextHelper = appCompatEmojiEditTextHelper;
        appCompatEmojiEditTextHelper.loadFromAttributes(attrs, defStyleAttr);
        initEmojiKeyListener(appCompatEmojiEditTextHelper);
    }

    /* access modifiers changed from: package-private */
    public void initEmojiKeyListener(AppCompatEmojiEditTextHelper appCompatEmojiEditTextHelper) {
        KeyListener currentKeyListener = getKeyListener();
        if (appCompatEmojiEditTextHelper.isEmojiCapableKeyListener(currentKeyListener)) {
            boolean wasFocusable = super.isFocusable();
            boolean wasClickable = super.isClickable();
            boolean wasLongClickable = super.isLongClickable();
            int inputType = super.getInputType();
            KeyListener wrappedKeyListener = appCompatEmojiEditTextHelper.getKeyListener(currentKeyListener);
            if (wrappedKeyListener != currentKeyListener) {
                super.setKeyListener(wrappedKeyListener);
                super.setRawInputType(inputType);
                super.setFocusable(wasFocusable);
                super.setClickable(wasClickable);
                super.setLongClickable(wasLongClickable);
            }
        }
    }

    public Editable getText() {
        if (Build.VERSION.SDK_INT >= 28) {
            return super.getText();
        }
        return super.getEditableText();
    }

    public void setBackgroundResource(int resId) {
        super.setBackgroundResource(resId);
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            appCompatBackgroundHelper.onSetBackgroundResource(resId);
        }
    }

    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            appCompatBackgroundHelper.onSetBackgroundDrawable(background);
        }
    }

    public void setSupportBackgroundTintList(ColorStateList tint) {
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            appCompatBackgroundHelper.setSupportBackgroundTintList(tint);
        }
    }

    public ColorStateList getSupportBackgroundTintList() {
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            return appCompatBackgroundHelper.getSupportBackgroundTintList();
        }
        return null;
    }

    public void setSupportBackgroundTintMode(PorterDuff.Mode tintMode) {
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            appCompatBackgroundHelper.setSupportBackgroundTintMode(tintMode);
        }
    }

    public PorterDuff.Mode getSupportBackgroundTintMode() {
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            return appCompatBackgroundHelper.getSupportBackgroundTintMode();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            appCompatBackgroundHelper.applySupportBackgroundTint();
        }
        AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
        if (appCompatTextHelper != null) {
            appCompatTextHelper.applyCompoundDrawablesTints();
        }
    }

    public void setTextAppearance(Context context, int resId) {
        super.setTextAppearance(context, resId);
        AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
        if (appCompatTextHelper != null) {
            appCompatTextHelper.onSetTextAppearance(context, resId);
        }
    }

    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        String[] mimeTypes;
        InputConnection ic = super.onCreateInputConnection(outAttrs);
        this.mTextHelper.populateSurroundingTextIfNeeded(this, ic, outAttrs);
        InputConnection ic2 = AppCompatHintHelper.onCreateInputConnection(ic, outAttrs, this);
        if (!(ic2 == null || Build.VERSION.SDK_INT > 30 || (mimeTypes = ViewCompat.getOnReceiveContentMimeTypes(this)) == null)) {
            EditorInfoCompat.setContentMimeTypes(outAttrs, mimeTypes);
            ic2 = InputConnectionCompat.createWrapper((View) this, ic2, outAttrs);
        }
        return this.mAppCompatEmojiEditTextHelper.onCreateInputConnection(ic2, outAttrs);
    }

    public void setCustomSelectionActionModeCallback(ActionMode.Callback actionModeCallback) {
        super.setCustomSelectionActionModeCallback(TextViewCompat.wrapCustomSelectionActionModeCallback(this, actionModeCallback));
    }

    public ActionMode.Callback getCustomSelectionActionModeCallback() {
        return TextViewCompat.unwrapCustomSelectionActionModeCallback(super.getCustomSelectionActionModeCallback());
    }

    private SuperCaller getSuperCaller() {
        if (this.mSuperCaller == null) {
            this.mSuperCaller = new SuperCaller();
        }
        return this.mSuperCaller;
    }

    public void setTextClassifier(TextClassifier textClassifier) {
        AppCompatTextClassifierHelper appCompatTextClassifierHelper;
        if (Build.VERSION.SDK_INT >= 28 || (appCompatTextClassifierHelper = this.mTextClassifierHelper) == null) {
            getSuperCaller().setTextClassifier(textClassifier);
        } else {
            appCompatTextClassifierHelper.setTextClassifier(textClassifier);
        }
    }

    public TextClassifier getTextClassifier() {
        AppCompatTextClassifierHelper appCompatTextClassifierHelper;
        if (Build.VERSION.SDK_INT >= 28 || (appCompatTextClassifierHelper = this.mTextClassifierHelper) == null) {
            return getSuperCaller().getTextClassifier();
        }
        return appCompatTextClassifierHelper.getTextClassifier();
    }

    public boolean onDragEvent(DragEvent event) {
        if (AppCompatReceiveContentHelper.maybeHandleDragEventViaPerformReceiveContent(this, event)) {
            return true;
        }
        return super.onDragEvent(event);
    }

    public boolean onTextContextMenuItem(int id) {
        if (AppCompatReceiveContentHelper.maybeHandleMenuActionViaPerformReceiveContent(this, id)) {
            return true;
        }
        return super.onTextContextMenuItem(id);
    }

    public ContentInfoCompat onReceiveContent(ContentInfoCompat payload) {
        return this.mDefaultOnReceiveContentListener.onReceiveContent(this, payload);
    }

    public void setKeyListener(KeyListener keyListener) {
        super.setKeyListener(this.mAppCompatEmojiEditTextHelper.getKeyListener(keyListener));
    }

    public void setEmojiCompatEnabled(boolean enabled) {
        this.mAppCompatEmojiEditTextHelper.setEnabled(enabled);
    }

    public boolean isEmojiCompatEnabled() {
        return this.mAppCompatEmojiEditTextHelper.isEnabled();
    }

    public void setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        super.setCompoundDrawables(left, top, right, bottom);
        AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
        if (appCompatTextHelper != null) {
            appCompatTextHelper.onSetCompoundDrawables();
        }
    }

    public void setCompoundDrawablesRelative(Drawable start, Drawable top, Drawable end, Drawable bottom) {
        super.setCompoundDrawablesRelative(start, top, end, bottom);
        AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
        if (appCompatTextHelper != null) {
            appCompatTextHelper.onSetCompoundDrawables();
        }
    }

    public ColorStateList getSupportCompoundDrawablesTintList() {
        return this.mTextHelper.getCompoundDrawableTintList();
    }

    public void setSupportCompoundDrawablesTintList(ColorStateList tintList) {
        this.mTextHelper.setCompoundDrawableTintList(tintList);
        this.mTextHelper.applyCompoundDrawablesTints();
    }

    public PorterDuff.Mode getSupportCompoundDrawablesTintMode() {
        return this.mTextHelper.getCompoundDrawableTintMode();
    }

    public void setSupportCompoundDrawablesTintMode(PorterDuff.Mode tintMode) {
        this.mTextHelper.setCompoundDrawableTintMode(tintMode);
        this.mTextHelper.applyCompoundDrawablesTints();
    }

    class SuperCaller {
        SuperCaller() {
        }

        public TextClassifier getTextClassifier() {
            return AppCompatEditText.super.getTextClassifier();
        }

        public void setTextClassifier(TextClassifier textClassifier) {
            AppCompatEditText.super.setTextClassifier(textClassifier);
        }
    }
}
