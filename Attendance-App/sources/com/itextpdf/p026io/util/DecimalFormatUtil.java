package com.itextpdf.p026io.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/* renamed from: com.itextpdf.io.util.DecimalFormatUtil */
public final class DecimalFormatUtil {
    private static final DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);

    private DecimalFormatUtil() {
    }

    public static String formatNumber(double d, String pattern) {
        return new DecimalFormat(pattern, dfs).format(d);
    }
}
