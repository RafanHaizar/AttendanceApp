package com.itextpdf.p026io.font.otf;

import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.p026io.util.TextUtil;
import java.io.Serializable;
import java.util.Arrays;

/* renamed from: com.itextpdf.io.font.otf.Glyph */
public class Glyph implements Serializable {
    private static final char REPLACEMENT_CHARACTER = '�';
    private static final char[] REPLACEMENT_CHARACTERS = {REPLACEMENT_CHARACTER};
    private static final String REPLACEMENT_CHARACTER_STRING = String.valueOf(REPLACEMENT_CHARACTER);
    private static final long serialVersionUID = 1627806639423114471L;
    short anchorDelta;
    private int[] bbox;
    private char[] chars;
    private final int code;
    private final boolean isMark;
    private int unicode;
    private final int width;
    short xAdvance;
    short xPlacement;
    short yAdvance;
    short yPlacement;

    public Glyph(int code2, int width2, int unicode2) {
        this(code2, width2, unicode2, (char[]) null, false);
    }

    public Glyph(int code2, int width2, char[] chars2) {
        this(code2, width2, codePoint(chars2), chars2, false);
    }

    public Glyph(int code2, int width2, int unicode2, int[] bbox2) {
        this(code2, width2, unicode2, (char[]) null, false);
        this.bbox = bbox2;
    }

    public Glyph(int width2, int unicode2) {
        this(-1, width2, unicode2, getChars(unicode2), false);
    }

    public Glyph(int code2, int width2, int unicode2, char[] chars2, boolean IsMark) {
        this.bbox = null;
        this.xPlacement = 0;
        this.yPlacement = 0;
        this.xAdvance = 0;
        this.yAdvance = 0;
        this.anchorDelta = 0;
        this.code = code2;
        this.width = width2;
        this.unicode = unicode2;
        this.isMark = IsMark;
        this.chars = chars2 != null ? chars2 : getChars(unicode2);
    }

    public Glyph(Glyph glyph) {
        this.bbox = null;
        this.xPlacement = 0;
        this.yPlacement = 0;
        this.xAdvance = 0;
        this.yAdvance = 0;
        this.anchorDelta = 0;
        this.code = glyph.code;
        this.width = glyph.width;
        this.chars = glyph.chars;
        this.unicode = glyph.unicode;
        this.isMark = glyph.isMark;
        this.bbox = glyph.bbox;
        this.xPlacement = glyph.xPlacement;
        this.yPlacement = glyph.yPlacement;
        this.xAdvance = glyph.xAdvance;
        this.yAdvance = glyph.yAdvance;
        this.anchorDelta = glyph.anchorDelta;
    }

    public Glyph(Glyph glyph, int xPlacement2, int yPlacement2, int xAdvance2, int yAdvance2, int anchorDelta2) {
        this(glyph);
        this.xPlacement = (short) xPlacement2;
        this.yPlacement = (short) yPlacement2;
        this.xAdvance = (short) xAdvance2;
        this.yAdvance = (short) yAdvance2;
        this.anchorDelta = (short) anchorDelta2;
    }

    public Glyph(Glyph glyph, int unicode2) {
        this(glyph.code, glyph.width, unicode2, getChars(unicode2), glyph.isMark());
    }

    public int getCode() {
        return this.code;
    }

    public int getWidth() {
        return this.width;
    }

    public int[] getBbox() {
        return this.bbox;
    }

    public boolean hasValidUnicode() {
        return this.unicode > -1;
    }

    public int getUnicode() {
        return this.unicode;
    }

    public void setUnicode(int unicode2) {
        this.unicode = unicode2;
        this.chars = getChars(unicode2);
    }

    public char[] getChars() {
        return this.chars;
    }

    public void setChars(char[] chars2) {
        this.chars = chars2;
    }

    public boolean isMark() {
        return this.isMark;
    }

    public short getXPlacement() {
        return this.xPlacement;
    }

    public void setXPlacement(short xPlacement2) {
        this.xPlacement = xPlacement2;
    }

    public short getYPlacement() {
        return this.yPlacement;
    }

    public void setYPlacement(short yPlacement2) {
        this.yPlacement = yPlacement2;
    }

    public short getXAdvance() {
        return this.xAdvance;
    }

    public void setXAdvance(short xAdvance2) {
        this.xAdvance = xAdvance2;
    }

    public short getYAdvance() {
        return this.yAdvance;
    }

    public void setYAdvance(short yAdvance2) {
        this.yAdvance = yAdvance2;
    }

    public short getAnchorDelta() {
        return this.anchorDelta;
    }

    public void setAnchorDelta(short anchorDelta2) {
        this.anchorDelta = anchorDelta2;
    }

    public boolean hasOffsets() {
        return hasAdvance() || hasPlacement();
    }

    public boolean hasPlacement() {
        return this.anchorDelta != 0;
    }

    public boolean hasAdvance() {
        return (this.xAdvance == 0 && this.yAdvance == 0) ? false : true;
    }

    public int hashCode() {
        int i = 1 * 31;
        char[] cArr = this.chars;
        return ((((i + (cArr == null ? 0 : Arrays.hashCode(cArr))) * 31) + this.code) * 31) + this.width;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Glyph other = (Glyph) obj;
        if (Arrays.equals(this.chars, other.chars) && this.code == other.code && this.width == other.width) {
            return true;
        }
        return false;
    }

    public String getUnicodeString() {
        char[] cArr = this.chars;
        if (cArr != null) {
            return String.valueOf(cArr);
        }
        return REPLACEMENT_CHARACTER_STRING;
    }

    public char[] getUnicodeChars() {
        char[] cArr = this.chars;
        if (cArr != null) {
            return cArr;
        }
        return REPLACEMENT_CHARACTERS;
    }

    public String toString() {
        Object[] objArr = new Object[4];
        objArr[0] = toHex(this.code);
        char[] cArr = this.chars;
        objArr[1] = cArr != null ? Arrays.toString(cArr) : "null";
        objArr[2] = toHex(this.unicode);
        objArr[3] = Integer.valueOf(this.width);
        return MessageFormatUtil.format("[id={0}, chars={1}, uni={2}, width={3}]", objArr);
    }

    private static String toHex(int ch) {
        String s = "0000" + Integer.toHexString(ch);
        return s.substring(Math.min(4, s.length() - 4));
    }

    private static int codePoint(char[] a) {
        if (a == null) {
            return -1;
        }
        if (a.length == 1 && Character.isValidCodePoint(a[0])) {
            return a[0];
        }
        if (a.length != 2 || !Character.isHighSurrogate(a[0]) || !Character.isLowSurrogate(a[1])) {
            return -1;
        }
        return Character.toCodePoint(a[0], a[1]);
    }

    private static char[] getChars(int unicode2) {
        if (unicode2 > -1) {
            return TextUtil.convertFromUtf32(unicode2);
        }
        return null;
    }
}
