package com.google.android.material.slider;

import java.util.Locale;

public final class BasicLabelFormatter implements LabelFormatter {
    private static final int BILLION = 1000000000;
    private static final int MILLION = 1000000;
    private static final int THOUSAND = 1000;
    private static final long TRILLION = 1000000000000L;

    public String getFormattedValue(float value) {
        if (value >= 1.0E12f) {
            return String.format(Locale.US, "%.1fT", new Object[]{Float.valueOf(value / 1.0E12f)});
        } else if (value >= 1.0E9f) {
            return String.format(Locale.US, "%.1fB", new Object[]{Float.valueOf(value / 1.0E9f)});
        } else if (value >= 1000000.0f) {
            return String.format(Locale.US, "%.1fM", new Object[]{Float.valueOf(value / 1000000.0f)});
        } else if (value >= 1000.0f) {
            return String.format(Locale.US, "%.1fK", new Object[]{Float.valueOf(value / 1000.0f)});
        } else {
            return String.format(Locale.US, "%.0f", new Object[]{Float.valueOf(value)});
        }
    }
}
