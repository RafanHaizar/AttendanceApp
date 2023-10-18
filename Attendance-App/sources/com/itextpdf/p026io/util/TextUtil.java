package com.itextpdf.p026io.util;

import com.itextpdf.p026io.font.otf.Glyph;
import com.itextpdf.p026io.font.otf.GlyphLine;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.itextpdf.io.util.TextUtil */
public final class TextUtil {
    private TextUtil() {
    }

    public static boolean isSurrogateHigh(char c) {
        return c >= 55296 && c <= 56319;
    }

    public static boolean isSurrogateLow(char c) {
        return c >= 56320 && c <= 57343;
    }

    public static char highSurrogate(int codePoint) {
        return (char) ((codePoint >>> 10) + 55232);
    }

    public static char lowSurrogate(int codePoint) {
        return (char) ((codePoint & 1023) + 56320);
    }

    public static boolean isSurrogatePair(String text, int idx) {
        return idx >= 0 && idx <= text.length() + -2 && isSurrogateHigh(text.charAt(idx)) && isSurrogateLow(text.charAt(idx + 1));
    }

    public static boolean isSurrogatePair(char[] text, int idx) {
        return idx >= 0 && idx <= text.length + -2 && isSurrogateHigh(text[idx]) && isSurrogateLow(text[idx + 1]);
    }

    public static int convertToUtf32(char highSurrogate, char lowSurrogate) {
        return ((((highSurrogate - 55296) * 1024) + lowSurrogate) - 56320) + 65536;
    }

    public static int convertToUtf32(char[] text, int idx) {
        return ((((text[idx] - 55296) * 1024) + text[idx + 1]) - 56320) + 65536;
    }

    public static int convertToUtf32(String text, int idx) {
        return ((((text.charAt(idx) - 55296) * 1024) + text.charAt(idx + 1)) - 56320) + 65536;
    }

    public static int[] convertToUtf32(String text) {
        if (text == null) {
            return null;
        }
        List<Integer> charCodes = new ArrayList<>(text.length());
        int pos = 0;
        while (pos < text.length()) {
            if (isSurrogatePair(text, pos)) {
                charCodes.add(Integer.valueOf(convertToUtf32(text, pos)));
                pos += 2;
            } else {
                charCodes.add(Integer.valueOf(text.charAt(pos)));
                pos++;
            }
        }
        return ArrayUtil.toIntArray(charCodes);
    }

    public static char[] convertFromUtf32(int codePoint) {
        if (codePoint < 65536) {
            return new char[]{(char) codePoint};
        }
        int codePoint2 = codePoint - 65536;
        return new char[]{(char) ((codePoint2 / 1024) + 55296), (char) ((codePoint2 % 1024) + 56320)};
    }

    public static String convertFromUtf32(int[] text, int startPos, int endPos) {
        StringBuilder sb = new StringBuilder();
        for (int i = startPos; i < endPos; i++) {
            sb.append(convertFromUtf32ToCharArray(text[i]));
        }
        return sb.toString();
    }

    public static char[] convertFromUtf32ToCharArray(int codePoint) {
        if (codePoint < 65536) {
            return new char[]{(char) codePoint};
        }
        int codePoint2 = codePoint - 65536;
        return new char[]{(char) ((codePoint2 / 1024) + 55296), (char) ((codePoint2 % 1024) + 56320)};
    }

    public static String charToString(char ch) {
        return String.valueOf(ch);
    }

    public static boolean isNewLine(Glyph glyph) {
        return isNewLine(glyph.getUnicode());
    }

    public static boolean isNewLine(char c) {
        return isNewLine((int) c);
    }

    public static boolean isNewLine(int unicode) {
        return unicode == 10 || unicode == 13;
    }

    public static boolean isCarriageReturnFollowedByLineFeed(GlyphLine glyphLine, int carriageReturnPosition) {
        if (glyphLine.size() <= 1 || carriageReturnPosition > glyphLine.size() - 2 || glyphLine.get(carriageReturnPosition).getUnicode() != 13 || glyphLine.get(carriageReturnPosition + 1).getUnicode() != 10) {
            return false;
        }
        return true;
    }

    public static boolean isSpaceOrWhitespace(Glyph glyph) {
        return Character.isSpaceChar((char) glyph.getUnicode()) || Character.isWhitespace((char) glyph.getUnicode());
    }

    public static boolean isWhitespace(Glyph glyph) {
        return Character.isWhitespace(glyph.getUnicode());
    }

    public static boolean isNonBreakingHyphen(Glyph glyph) {
        return 8209 == glyph.getUnicode();
    }

    public static boolean isSpace(Glyph glyph) {
        return Character.isSpaceChar((char) glyph.getUnicode());
    }

    public static boolean isUni0020(Glyph g) {
        return g.getUnicode() == 32;
    }

    public static boolean isNonPrintable(int c) {
        return Character.isIdentifierIgnorable(c) || c == 173;
    }

    public static boolean isWhitespaceOrNonPrintable(int code) {
        return Character.isWhitespace(code) || isNonPrintable(code);
    }

    public static boolean charsetIsSupported(String charsetName) {
        try {
            return Charset.isSupported(charsetName);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
