package com.google.android.material.timepicker;

import android.text.InputFilter;
import android.text.Spanned;

class MaxInputValidator implements InputFilter {
    private int max;

    public MaxInputValidator(int max2) {
        this.max = max2;
    }

    public void setMax(int max2) {
        this.max = max2;
    }

    public int getMax() {
        return this.max;
    }

    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            StringBuilder builder = new StringBuilder(dest);
            builder.replace(dstart, dend, source.subSequence(start, end).toString());
            if (Integer.parseInt(builder.toString()) <= this.max) {
                return null;
            }
            return "";
        } catch (NumberFormatException e) {
            return "";
        }
    }
}
