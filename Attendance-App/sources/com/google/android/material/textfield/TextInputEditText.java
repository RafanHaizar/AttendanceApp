package com.google.android.material.textfield;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import androidx.appcompat.widget.AppCompatEditText;
import com.google.android.material.C1087R;
import com.google.android.material.internal.ManufacturerUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class TextInputEditText extends AppCompatEditText {
    private final Rect parentRect;
    private boolean textInputLayoutFocusedRectEnabled;

    public TextInputEditText(Context context) {
        this(context, (AttributeSet) null);
    }

    public TextInputEditText(Context context, AttributeSet attrs) {
        this(context, attrs, C1087R.attr.editTextStyle);
    }

    public TextInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, 0), attrs, defStyleAttr);
        this.parentRect = new Rect();
        TypedArray attributes = ThemeEnforcement.obtainStyledAttributes(context, attrs, C1087R.styleable.TextInputEditText, defStyleAttr, C1087R.C1093style.Widget_Design_TextInputEditText, new int[0]);
        setTextInputLayoutFocusedRectEnabled(attributes.getBoolean(C1087R.styleable.TextInputEditText_textInputLayoutFocusedRectEnabled, false));
        attributes.recycle();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        TextInputLayout layout = getTextInputLayout();
        if (layout != null && layout.isProvidingHint() && super.getHint() == null && ManufacturerUtils.isMeizuDevice()) {
            setHint("");
        }
    }

    public CharSequence getHint() {
        TextInputLayout layout = getTextInputLayout();
        if (layout == null || !layout.isProvidingHint()) {
            return super.getHint();
        }
        return layout.getHint();
    }

    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        InputConnection ic = super.onCreateInputConnection(outAttrs);
        if (ic != null && outAttrs.hintText == null) {
            outAttrs.hintText = getHintFromLayout();
        }
        return ic;
    }

    private TextInputLayout getTextInputLayout() {
        for (ViewParent parent = getParent(); parent instanceof View; parent = parent.getParent()) {
            if (parent instanceof TextInputLayout) {
                return (TextInputLayout) parent;
            }
        }
        return null;
    }

    private CharSequence getHintFromLayout() {
        TextInputLayout layout = getTextInputLayout();
        if (layout != null) {
            return layout.getHint();
        }
        return null;
    }

    public void setTextInputLayoutFocusedRectEnabled(boolean textInputLayoutFocusedRectEnabled2) {
        this.textInputLayoutFocusedRectEnabled = textInputLayoutFocusedRectEnabled2;
    }

    public boolean isTextInputLayoutFocusedRectEnabled() {
        return this.textInputLayoutFocusedRectEnabled;
    }

    private boolean shouldUseTextInputLayoutFocusedRect(TextInputLayout textInputLayout) {
        return textInputLayout != null && this.textInputLayoutFocusedRectEnabled;
    }

    public void getFocusedRect(Rect r) {
        super.getFocusedRect(r);
        TextInputLayout textInputLayout = getTextInputLayout();
        if (shouldUseTextInputLayoutFocusedRect(textInputLayout) && r != null) {
            textInputLayout.getFocusedRect(this.parentRect);
            r.bottom = this.parentRect.bottom;
        }
    }

    public boolean getGlobalVisibleRect(Rect r, Point globalOffset) {
        TextInputLayout textInputLayout = getTextInputLayout();
        if (shouldUseTextInputLayoutFocusedRect(textInputLayout)) {
            return textInputLayout.getGlobalVisibleRect(r, globalOffset);
        }
        return super.getGlobalVisibleRect(r, globalOffset);
    }

    public boolean requestRectangleOnScreen(Rect rectangle) {
        TextInputLayout textInputLayout = getTextInputLayout();
        if (!shouldUseTextInputLayoutFocusedRect(textInputLayout) || rectangle == null) {
            return super.requestRectangleOnScreen(rectangle);
        }
        this.parentRect.set(rectangle.left, rectangle.top, rectangle.right, rectangle.bottom + (textInputLayout.getHeight() - getHeight()));
        return super.requestRectangleOnScreen(this.parentRect);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        TextInputLayout layout = getTextInputLayout();
        if (Build.VERSION.SDK_INT < 23 && layout != null) {
            info.setText(getAccessibilityNodeInfoText(layout));
        }
    }

    private String getAccessibilityNodeInfoText(TextInputLayout layout) {
        CharSequence inputText = getText();
        CharSequence hintText = layout.getHint();
        boolean showingText = !TextUtils.isEmpty(inputText);
        String str = "";
        String hint = TextUtils.isEmpty(hintText) ^ true ? hintText.toString() : str;
        if (showingText) {
            StringBuilder append = new StringBuilder().append(inputText);
            if (!TextUtils.isEmpty(hint)) {
                str = ", " + hint;
            }
            return append.append(str).toString();
        } else if (!TextUtils.isEmpty(hint)) {
            return hint;
        } else {
            return str;
        }
    }
}
