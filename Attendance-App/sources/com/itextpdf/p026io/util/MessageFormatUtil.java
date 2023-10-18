package com.itextpdf.p026io.util;

import java.text.MessageFormat;
import java.util.Locale;

/* renamed from: com.itextpdf.io.util.MessageFormatUtil */
public class MessageFormatUtil {
    public static String format(String pattern, Object... arguments) {
        return new MessageFormat(pattern, Locale.ROOT).format(arguments);
    }
}
