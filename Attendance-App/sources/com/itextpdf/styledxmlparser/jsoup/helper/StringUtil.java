package com.itextpdf.styledxmlparser.jsoup.helper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public final class StringUtil {
    private static final String[] padding = {"", " ", "  ", "   ", "    ", "     ", "      ", "       ", "        ", "         ", "          "};

    public static String join(Collection strings, String sep) {
        return join(strings.iterator(), sep);
    }

    public static String join(Iterator strings, String sep) {
        if (!strings.hasNext()) {
            return "";
        }
        String start = strings.next().toString();
        if (!strings.hasNext()) {
            return start;
        }
        StringBuilder sb = new StringBuilder(64).append(start);
        while (strings.hasNext()) {
            sb.append(sep);
            sb.append(strings.next());
        }
        return sb.toString();
    }

    public static String padding(int width) {
        if (width >= 0) {
            String[] strArr = padding;
            if (width < strArr.length) {
                return strArr[width];
            }
            char[] out = new char[width];
            for (int i = 0; i < width; i++) {
                out[i] = ' ';
            }
            return String.valueOf(out);
        }
        throw new IllegalArgumentException("width must be > 0");
    }

    public static boolean isBlank(String string) {
        if (string == null || string.length() == 0) {
            return true;
        }
        int l = string.length();
        for (int i = 0; i < l; i++) {
            if (!isWhitespace(string.codePointAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumeric(String string) {
        if (string == null || string.length() == 0) {
            return false;
        }
        int l = string.length();
        for (int i = 0; i < l; i++) {
            if (!Character.isDigit(string.codePointAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isWhitespace(int c) {
        return c == 32 || c == 9 || c == 10 || c == 12 || c == 13;
    }

    public static String normaliseWhitespace(String string) {
        StringBuilder sb = new StringBuilder(string.length());
        appendNormalisedWhitespace(sb, string, false);
        return sb.toString();
    }

    public static void appendNormalisedWhitespace(StringBuilder accum, String string, boolean stripLeading) {
        boolean lastWasWhite = false;
        boolean reachedNonWhite = false;
        int len = string.length();
        int i = 0;
        while (i < len) {
            int c = string.codePointAt(i);
            if (!isWhitespace(c)) {
                accum.appendCodePoint(c);
                lastWasWhite = false;
                reachedNonWhite = true;
            } else if ((!stripLeading || reachedNonWhite) && !lastWasWhite) {
                accum.append(' ');
                lastWasWhite = true;
            }
            i += Character.charCount(c);
        }
    }

    /* renamed from: in */
    public static boolean m276in(String needle, String... haystack) {
        for (String hay : haystack) {
            if (hay.equals(needle)) {
                return true;
            }
        }
        return false;
    }

    public static boolean inSorted(String needle, String[] haystack) {
        return Arrays.binarySearch(haystack, needle) >= 0;
    }

    public static URL resolve(URL base, String relUrl) throws MalformedURLException {
        if (relUrl.startsWith("?")) {
            relUrl = base.getPath() + relUrl;
        }
        if (relUrl.indexOf(46) == 0 && base.getFile().indexOf(47) != 0) {
            base = new URL(base.getProtocol(), base.getHost(), base.getPort(), "/" + base.getFile());
        }
        return new URL(base, relUrl);
    }

    public static String resolve(String baseUrl, String relUrl) {
        try {
            try {
                return resolve(new URL(baseUrl), relUrl).toExternalForm();
            } catch (MalformedURLException e) {
                return "";
            }
        } catch (MalformedURLException e2) {
            return new URL(relUrl).toExternalForm();
        }
    }
}
