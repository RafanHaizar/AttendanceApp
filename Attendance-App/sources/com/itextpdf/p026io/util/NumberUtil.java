package com.itextpdf.p026io.util;

/* renamed from: com.itextpdf.io.util.NumberUtil */
public class NumberUtil {
    private NumberUtil() {
    }

    public static Float asFloat(Object obj) {
        Number value = (Number) obj;
        if (value != null) {
            return Float.valueOf(value.floatValue());
        }
        return null;
    }

    public static Integer asInteger(Object obj) {
        Number value = (Number) obj;
        if (value != null) {
            return Integer.valueOf(value.intValue());
        }
        return null;
    }
}
