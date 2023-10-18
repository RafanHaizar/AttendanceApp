package com.itextpdf.p026io.util;

/* renamed from: com.itextpdf.io.util.EnumUtil */
public final class EnumUtil {
    private EnumUtil() {
    }

    public static <T extends Enum<T>> T throwIfNull(T enumInstance) {
        if (enumInstance != null) {
            return enumInstance;
        }
        throw new RuntimeException("Expected not null enum instance");
    }
}
