package com.google.android.material.timepicker;

import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;

class TimePickerTextInputKeyController implements TextView.OnEditorActionListener, View.OnKeyListener {
    private final ChipTextInputComboView hourLayoutComboView;
    private boolean keyListenerRunning = false;
    private final ChipTextInputComboView minuteLayoutComboView;
    private final TimeModel time;

    TimePickerTextInputKeyController(ChipTextInputComboView hourLayoutComboView2, ChipTextInputComboView minuteLayoutComboView2, TimeModel time2) {
        this.hourLayoutComboView = hourLayoutComboView2;
        this.minuteLayoutComboView = minuteLayoutComboView2;
        this.time = time2;
    }

    public void bind() {
        TextInputLayout hourLayout = this.hourLayoutComboView.getTextInput();
        TextInputLayout minuteLayout = this.minuteLayoutComboView.getTextInput();
        EditText hourEditText = hourLayout.getEditText();
        EditText minuteEditText = minuteLayout.getEditText();
        hourEditText.setImeOptions(268435461);
        minuteEditText.setImeOptions(268435462);
        hourEditText.setOnEditorActionListener(this);
        hourEditText.setOnKeyListener(this);
        minuteEditText.setOnKeyListener(this);
    }

    private void moveSelection(int selection) {
        boolean z = true;
        this.minuteLayoutComboView.setChecked(selection == 12);
        ChipTextInputComboView chipTextInputComboView = this.hourLayoutComboView;
        if (selection != 10) {
            z = false;
        }
        chipTextInputComboView.setChecked(z);
        this.time.selection = selection;
    }

    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean actionNext = actionId == 5;
        if (actionNext) {
            moveSelection(12);
        }
        return actionNext;
    }

    public boolean onKey(View view, int keyCode, KeyEvent event) {
        boolean ret;
        if (this.keyListenerRunning) {
            return false;
        }
        this.keyListenerRunning = true;
        EditText editText = (EditText) view;
        if (this.time.selection == 12) {
            ret = onMinuteKeyPress(keyCode, event, editText);
        } else {
            ret = onHourKeyPress(keyCode, event, editText);
        }
        this.keyListenerRunning = false;
        return ret;
    }

    private boolean onMinuteKeyPress(int keyCode, KeyEvent event, EditText editText) {
        if (keyCode == 67 && event.getAction() == 0 && TextUtils.isEmpty(editText.getText())) {
            moveSelection(10);
            return true;
        }
        clearPrefilledText(editText);
        return false;
    }

    private boolean onHourKeyPress(int keyCode, KeyEvent event, EditText editText) {
        Editable text = editText.getText();
        if (text == null) {
            return false;
        }
        if (keyCode >= 7 && keyCode <= 16 && event.getAction() == 1 && editText.getSelectionStart() == 2 && text.length() == 2) {
            moveSelection(12);
            return true;
        }
        clearPrefilledText(editText);
        return false;
    }

    private void clearPrefilledText(EditText editText) {
        if (editText.getSelectionStart() == 0 && editText.length() == 2) {
            editText.getText().clear();
        }
    }
}
