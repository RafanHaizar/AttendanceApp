package com.itextpdf.p026io.font;

import com.itextpdf.p026io.font.otf.Glyph;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.itextpdf.io.font.FontProgram */
public abstract class FontProgram implements Serializable {
    public static final int DEFAULT_WIDTH = 1000;
    public static final int UNITS_NORMALIZATION = 1000;
    private static final long serialVersionUID = -3488910249070253659L;
    protected int avgWidth;
    protected Map<Integer, Glyph> codeToGlyph = new HashMap();
    protected String encodingScheme = FontEncoding.FONT_SPECIFIC;
    protected FontIdentification fontIdentification = new FontIdentification();
    protected FontMetrics fontMetrics = new FontMetrics();
    protected FontNames fontNames;
    protected boolean isFontSpecific;
    protected String registry;
    protected Map<Integer, Glyph> unicodeToGlyph = new HashMap();

    public abstract int getKerning(Glyph glyph, Glyph glyph2);

    public abstract int getPdfFontFlags();

    public int countOfGlyphs() {
        return Math.max(this.codeToGlyph.size(), this.unicodeToGlyph.size());
    }

    public FontNames getFontNames() {
        return this.fontNames;
    }

    public FontMetrics getFontMetrics() {
        return this.fontMetrics;
    }

    public FontIdentification getFontIdentification() {
        return this.fontIdentification;
    }

    public String getRegistry() {
        return this.registry;
    }

    public boolean isFontSpecific() {
        return this.isFontSpecific;
    }

    public int getWidth(int unicode) {
        Glyph glyph = getGlyph(unicode);
        if (glyph != null) {
            return glyph.getWidth();
        }
        return 0;
    }

    public int getAvgWidth() {
        return this.avgWidth;
    }

    public int[] getCharBBox(int unicode) {
        Glyph glyph = getGlyph(unicode);
        if (glyph != null) {
            return glyph.getBbox();
        }
        return null;
    }

    public Glyph getGlyph(int unicode) {
        return this.unicodeToGlyph.get(Integer.valueOf(unicode));
    }

    public Glyph getGlyphByCode(int charCode) {
        return this.codeToGlyph.get(Integer.valueOf(charCode));
    }

    public boolean hasKernPairs() {
        return false;
    }

    public int getKerning(int first, int second) {
        return getKerning(this.unicodeToGlyph.get(Integer.valueOf(first)), this.unicodeToGlyph.get(Integer.valueOf(second)));
    }

    public boolean isBuiltWith(String fontName) {
        return false;
    }

    /* access modifiers changed from: protected */
    public void setRegistry(String registry2) {
        this.registry = registry2;
    }

    static String trimFontStyle(String name) {
        if (name == null) {
            return null;
        }
        if (name.endsWith(",Bold")) {
            return name.substring(0, name.length() - 5);
        }
        if (name.endsWith(",Italic")) {
            return name.substring(0, name.length() - 7);
        }
        if (name.endsWith(",BoldItalic")) {
            return name.substring(0, name.length() - 11);
        }
        return name;
    }

    /* access modifiers changed from: protected */
    public void setTypoAscender(int ascender) {
        this.fontMetrics.setTypoAscender(ascender);
    }

    /* access modifiers changed from: protected */
    public void setTypoDescender(int descender) {
        this.fontMetrics.setTypoDescender(descender);
    }

    /* access modifiers changed from: protected */
    public void setCapHeight(int capHeight) {
        this.fontMetrics.setCapHeight(capHeight);
    }

    /* access modifiers changed from: protected */
    public void setXHeight(int xHeight) {
        this.fontMetrics.setXHeight(xHeight);
    }

    /* access modifiers changed from: protected */
    public void setItalicAngle(int italicAngle) {
        this.fontMetrics.setItalicAngle((float) italicAngle);
    }

    /* access modifiers changed from: protected */
    public void setStemV(int stemV) {
        this.fontMetrics.setStemV(stemV);
    }

    /* access modifiers changed from: protected */
    public void setStemH(int stemH) {
        this.fontMetrics.setStemH(stemH);
    }

    /* access modifiers changed from: protected */
    public void setFontWeight(int fontWeight) {
        this.fontNames.setFontWeight(fontWeight);
    }

    /* access modifiers changed from: protected */
    public void setFontStretch(String fontWidth) {
        this.fontNames.setFontStretch(fontWidth);
    }

    /* access modifiers changed from: protected */
    public void setFixedPitch(boolean isFixedPitch) {
        this.fontMetrics.setIsFixedPitch(isFixedPitch);
    }

    /* access modifiers changed from: protected */
    public void setBold(boolean isBold) {
        if (isBold) {
            FontNames fontNames2 = this.fontNames;
            fontNames2.setMacStyle(fontNames2.getMacStyle() | 1);
            return;
        }
        FontNames fontNames3 = this.fontNames;
        fontNames3.setMacStyle(fontNames3.getMacStyle() & -2);
    }

    /* access modifiers changed from: protected */
    public void setBbox(int[] bbox) {
        this.fontMetrics.setBbox(bbox[0], bbox[1], bbox[2], bbox[3]);
    }

    /* access modifiers changed from: protected */
    public void setFontFamily(String fontFamily) {
        this.fontNames.setFamilyName(fontFamily);
    }

    /* access modifiers changed from: protected */
    public void setFontName(String fontName) {
        this.fontNames.setFontName(fontName);
        if (this.fontNames.getFullName() == null) {
            this.fontNames.setFullName(fontName);
        }
    }

    /* access modifiers changed from: protected */
    public void fixSpaceIssue() {
        Glyph space = this.unicodeToGlyph.get(32);
        if (space != null) {
            this.codeToGlyph.put(Integer.valueOf(space.getCode()), space);
        }
    }

    public String toString() {
        String name = getFontNames().getFontName();
        return name.length() > 0 ? name : super.toString();
    }
}
