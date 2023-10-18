package com.google.android.material.theme;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.app.AppCompatViewInflater;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textview.MaterialTextView;

public class MaterialComponentsViewInflater extends AppCompatViewInflater {
    /* access modifiers changed from: protected */
    public AppCompatButton createButton(Context context, AttributeSet attrs) {
        return new MaterialButton(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatCheckBox createCheckBox(Context context, AttributeSet attrs) {
        return new MaterialCheckBox(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatRadioButton createRadioButton(Context context, AttributeSet attrs) {
        return new MaterialRadioButton(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatTextView createTextView(Context context, AttributeSet attrs) {
        return new MaterialTextView(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatAutoCompleteTextView createAutoCompleteTextView(Context context, AttributeSet attrs) {
        return new MaterialAutoCompleteTextView(context, attrs);
    }
}
