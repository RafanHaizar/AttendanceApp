package com.itextpdf.kernel.font;

import com.itextpdf.p026io.font.FontNames;
import com.itextpdf.p026io.font.FontProgram;
import com.itextpdf.p026io.font.otf.Glyph;
import java.util.HashMap;
import java.util.Map;

public class Type3Font extends FontProgram {
    private static final long serialVersionUID = 1027076515537536993L;
    private boolean colorized = false;
    private int flags = 0;
    private final Map<Integer, Type3Glyph> type3Glyphs = new HashMap();

    Type3Font(boolean colorized2) {
        this.colorized = colorized2;
        this.fontNames = new FontNames();
        getFontMetrics().setBbox(0, 0, 0, 0);
    }

    public Type3Glyph getType3Glyph(int unicode) {
        return this.type3Glyphs.get(Integer.valueOf(unicode));
    }

    public int getPdfFontFlags() {
        return this.flags;
    }

    public boolean isFontSpecific() {
        return false;
    }

    public boolean isColorized() {
        return this.colorized;
    }

    public int getKerning(Glyph glyph1, Glyph glyph2) {
        return 0;
    }

    public int getNumberOfGlyphs() {
        return this.type3Glyphs.size();
    }

    /* access modifiers changed from: protected */
    public void setFontName(String fontName) {
        super.setFontName(fontName);
    }

    /* access modifiers changed from: protected */
    public void setFontFamily(String fontFamily) {
        super.setFontFamily(fontFamily);
    }

    /* access modifiers changed from: protected */
    public void setFontWeight(int fontWeight) {
        super.setFontWeight(fontWeight);
    }

    /* access modifiers changed from: protected */
    public void setFontStretch(String fontWidth) {
        super.setFontStretch(fontWidth);
    }

    /* access modifiers changed from: protected */
    public void setItalicAngle(int italicAngle) {
        super.setItalicAngle(italicAngle);
    }

    /* access modifiers changed from: package-private */
    public void setPdfFontFlags(int flags2) {
        this.flags = flags2;
    }

    /* access modifiers changed from: package-private */
    public void addGlyph(int code, int unicode, int width, int[] bbox, Type3Glyph type3Glyph) {
        Glyph glyph = new Glyph(code, width, unicode, bbox);
        this.codeToGlyph.put(Integer.valueOf(code), glyph);
        this.unicodeToGlyph.put(Integer.valueOf(unicode), glyph);
        this.type3Glyphs.put(Integer.valueOf(unicode), type3Glyph);
        recalculateAverageWidth();
    }

    private void recalculateAverageWidth() {
        int widthSum = 0;
        for (Glyph glyph : this.codeToGlyph.values()) {
            widthSum += glyph.getWidth();
        }
        this.avgWidth = widthSum / this.codeToGlyph.size();
    }
}
