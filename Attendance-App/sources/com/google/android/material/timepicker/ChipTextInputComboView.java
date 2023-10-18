package com.google.android.material.timepicker;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.C1087R;
import com.google.android.material.chip.Chip;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Arrays;

class ChipTextInputComboView extends FrameLayout implements Checkable {
    /* access modifiers changed from: private */
    public final Chip chip;
    private final EditText editText;
    private TextView label;
    private final TextInputLayout textInputLayout;
    private TextWatcher watcher;

    public ChipTextInputComboView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ChipTextInputComboView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChipTextInputComboView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = LayoutInflater.from(context);
        Chip chip2 = (Chip) inflater.inflate(C1087R.C1092layout.material_time_chip, this, false);
        this.chip = chip2;
        chip2.setAccessibilityClassName("android.view.View");
        TextInputLayout textInputLayout2 = (TextInputLayout) inflater.inflate(C1087R.C1092layout.material_time_input, this, false);
        this.textInputLayout = textInputLayout2;
        EditText editText2 = textInputLayout2.getEditText();
        this.editText = editText2;
        editText2.setVisibility(4);
        TextFormatter textFormatter = new TextFormatter();
        this.watcher = textFormatter;
        editText2.addTextChangedListener(textFormatter);
        updateHintLocales();
        addView(chip2);
        addView(textInputLayout2);
        this.label = (TextView) findViewById(C1087R.C1090id.material_label);
        editText2.setId(ViewCompat.generateViewId());
        ViewCompat.setLabelFor(this.label, editText2.getId());
        editText2.setSaveEnabled(false);
        editText2.setLongClickable(false);
    }

    private void updateHintLocales() {
        if (Build.VERSION.SDK_INT >= 24) {
            this.editText.setImeHintLocales(getContext().getResources().getConfiguration().getLocales());
        }
    }

    public boolean isChecked() {
        return this.chip.isChecked();
    }

    public void setChecked(boolean checked) {
        this.chip.setChecked(checked);
        int i = 0;
        this.editText.setVisibility(checked ? 0 : 4);
        Chip chip2 = this.chip;
        if (checked) {
            i = 8;
        }
        chip2.setVisibility(i);
        if (isChecked()) {
            ViewUtils.requestFocusAndShowKeyboard(this.editText);
        }
    }

    public void toggle() {
        this.chip.toggle();
    }

    public void setText(CharSequence text) {
        String formattedText = formatText(text);
        this.chip.setText(formattedText);
        if (!TextUtils.isEmpty(formattedText)) {
            this.editText.removeTextChangedListener(this.watcher);
            this.editText.setText(formattedText);
            this.editText.addTextChangedListener(this.watcher);
        }
    }

    /* access modifiers changed from: package-private */
    public CharSequence getChipText() {
        return this.chip.getText();
    }

    /* access modifiers changed from: private */
    public String formatText(CharSequence text) {
        return TimeModel.formatText(getResources(), text);
    }

    public void setOnClickListener(View.OnClickListener l) {
        this.chip.setOnClickListener(l);
    }

    public void setTag(int key, Object tag) {
        this.chip.setTag(key, tag);
    }

    public void setHelperText(CharSequence helperText) {
        this.label.setText(helperText);
    }

    public void setCursorVisible(boolean visible) {
        this.editText.setCursorVisible(visible);
    }

    public void addInputFilter(InputFilter filter) {
        InputFilter[] current = this.editText.getFilters();
        InputFilter[] arr = (InputFilter[]) Arrays.copyOf(current, current.length + 1);
        arr[current.length] = filter;
        this.editText.setFilters(arr);
    }

    public TextInputLayout getTextInput() {
        return this.textInputLayout;
    }

    public void setChipDelegate(AccessibilityDelegateCompat clickActionDelegate) {
        ViewCompat.setAccessibilityDelegate(this.chip, clickActionDelegate);
    }

    private class TextFormatter extends TextWatcherAdapter {
        private static final String DEFAULT_TEXT = "00";

        private TextFormatter() {
        }

        public void afterTextChanged(Editable editable) {
            if (TextUtils.isEmpty(editable)) {
                ChipTextInputComboView.this.chip.setText(ChipTextInputComboView.this.formatText(DEFAULT_TEXT));
                return;
            }
            String formattedText = ChipTextInputComboView.this.formatText(editable);
            ChipTextInputComboView.this.chip.setText(TextUtils.isEmpty(formattedText) ? ChipTextInputComboView.this.formatText(DEFAULT_TEXT) : formattedText);
        }
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateHintLocales();
    }
}
