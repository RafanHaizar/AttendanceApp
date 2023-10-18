package com.itextpdf.p026io.util;

/* renamed from: com.itextpdf.io.util.ExceptionUtil */
public final class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static boolean isOutOfRange(Exception e) {
        return e instanceof IndexOutOfBoundsException;
    }
}
