package com.itextpdf.p026io.font;

import com.itextpdf.p026io.font.constants.FontWeights;
import java.io.Serializable;

/* renamed from: com.itextpdf.io.font.FontMetrics */
public class FontMetrics implements Serializable {
    private static final long serialVersionUID = -7113134666493365588L;
    private int advanceWidthMax;
    private int ascender;
    private int[] bbox = {-50, -200, 1000, 900};
    private int capHeight = 700;
    private int descender;
    private int[] glyphWidths;
    private boolean isFixedPitch;
    private float italicAngle = 0.0f;
    private int lineGap;
    protected float normalizationCoef = 1.0f;
    private int numOfGlyphs;
    private int stemH = 0;
    private int stemV = 80;
    private int strikeoutPosition;
    private int strikeoutSize;
    private int subscriptOffset;
    private int subscriptSize;
    private int superscriptOffset;
    private int superscriptSize;
    private int typoAscender = FontWeights.EXTRA_BOLD;
    private int typoDescender = -200;
    private int underlinePosition = -100;
    private int underlineThickness = 50;
    private int unitsPerEm = 1000;
    private int winAscender;
    private int winDescender;
    private int xHeight = 0;

    public int getUnitsPerEm() {
        return this.unitsPerEm;
    }

    public int getNumberOfGlyphs() {
        return this.numOfGlyphs;
    }

    public int[] getGlyphWidths() {
        return this.glyphWidths;
    }

    public int getTypoAscender() {
        return this.typoAscender;
    }

    public int getTypoDescender() {
        return this.typoDescender;
    }

    public int getCapHeight() {
        return this.capHeight;
    }

    public int getXHeight() {
        return this.xHeight;
    }

    public float getItalicAngle() {
        return this.italicAngle;
    }

    public int[] getBbox() {
        return this.bbox;
    }

    public void setBbox(int llx, int lly, int urx, int ury) {
        int[] iArr = this.bbox;
        iArr[0] = llx;
        iArr[1] = lly;
        iArr[2] = urx;
        iArr[3] = ury;
    }

    public int getAscender() {
        return this.ascender;
    }

    public int getDescender() {
        return this.descender;
    }

    public int getLineGap() {
        return this.lineGap;
    }

    public int getWinAscender() {
        return this.winAscender;
    }

    public int getWinDescender() {
        return this.winDescender;
    }

    public int getAdvanceWidthMax() {
        return this.advanceWidthMax;
    }

    public int getUnderlinePosition() {
        return this.underlinePosition - (this.underlineThickness / 2);
    }

    public int getUnderlineThickness() {
        return this.underlineThickness;
    }

    public int getStrikeoutPosition() {
        return this.strikeoutPosition;
    }

    public int getStrikeoutSize() {
        return this.strikeoutSize;
    }

    public int getSubscriptSize() {
        return this.subscriptSize;
    }

    public int getSubscriptOffset() {
        return this.subscriptOffset;
    }

    public int getSuperscriptSize() {
        return this.superscriptSize;
    }

    public int getSuperscriptOffset() {
        return this.superscriptOffset;
    }

    public int getStemV() {
        return this.stemV;
    }

    public int getStemH() {
        return this.stemH;
    }

    public boolean isFixedPitch() {
        return this.isFixedPitch;
    }

    /* access modifiers changed from: protected */
    public void setUnitsPerEm(int unitsPerEm2) {
        this.unitsPerEm = unitsPerEm2;
        this.normalizationCoef = 1000.0f / ((float) unitsPerEm2);
    }

    /* access modifiers changed from: protected */
    public void updateBbox(float llx, float lly, float urx, float ury) {
        int[] iArr = this.bbox;
        float f = this.normalizationCoef;
        iArr[0] = (int) (llx * f);
        iArr[1] = (int) (lly * f);
        iArr[2] = (int) (urx * f);
        iArr[3] = (int) (f * ury);
    }

    /* access modifiers changed from: protected */
    public void setNumberOfGlyphs(int numOfGlyphs2) {
        this.numOfGlyphs = numOfGlyphs2;
    }

    /* access modifiers changed from: protected */
    public void setGlyphWidths(int[] glyphWidths2) {
        this.glyphWidths = glyphWidths2;
    }

    /* access modifiers changed from: protected */
    public void setTypoAscender(int typoAscender2) {
        this.typoAscender = (int) (((float) typoAscender2) * this.normalizationCoef);
    }

    /* access modifiers changed from: protected */
    public void setTypoDescender(int typoDesctender) {
        this.typoDescender = (int) (((float) typoDesctender) * this.normalizationCoef);
    }

    /* access modifiers changed from: protected */
    public void setCapHeight(int capHeight2) {
        this.capHeight = (int) (((float) capHeight2) * this.normalizationCoef);
    }

    /* access modifiers changed from: protected */
    public void setXHeight(int xHeight2) {
        this.xHeight = (int) (((float) xHeight2) * this.normalizationCoef);
    }

    /* access modifiers changed from: protected */
    public void setItalicAngle(float italicAngle2) {
        this.italicAngle = italicAngle2;
    }

    /* access modifiers changed from: protected */
    public void setAscender(int ascender2) {
        this.ascender = (int) (((float) ascender2) * this.normalizationCoef);
    }

    /* access modifiers changed from: protected */
    public void setDescender(int descender2) {
        this.descender = (int) (((float) descender2) * this.normalizationCoef);
    }

    /* access modifiers changed from: protected */
    public void setLineGap(int lineGap2) {
        this.lineGap = (int) (((float) lineGap2) * this.normalizationCoef);
    }

    /* access modifiers changed from: protected */
    public void setWinAscender(int winAscender2) {
        this.winAscender = (int) (((float) winAscender2) * this.normalizationCoef);
    }

    /* access modifiers changed from: protected */
    public void setWinDescender(int winDescender2) {
        this.winDescender = (int) (((float) winDescender2) * this.normalizationCoef);
    }

    /* access modifiers changed from: protected */
    public void setAdvanceWidthMax(int advanceWidthMax2) {
        this.advanceWidthMax = (int) (((float) advanceWidthMax2) * this.normalizationCoef);
    }

    /* access modifiers changed from: protected */
    public void setUnderlinePosition(int underlinePosition2) {
        this.underlinePosition = (int) (((float) underlinePosition2) * this.normalizationCoef);
    }

    /* access modifiers changed from: protected */
    public void setUnderlineThickness(int underineThickness) {
        this.underlineThickness = underineThickness;
    }

    /* access modifiers changed from: protected */
    public void setStrikeoutPosition(int strikeoutPosition2) {
        this.strikeoutPosition = (int) (((float) strikeoutPosition2) * this.normalizationCoef);
    }

    /* access modifiers changed from: protected */
    public void setStrikeoutSize(int strikeoutSize2) {
        this.strikeoutSize = (int) (((float) strikeoutSize2) * this.normalizationCoef);
    }

    /* access modifiers changed from: protected */
    public void setSubscriptSize(int subscriptSize2) {
        this.subscriptSize = (int) (((float) subscriptSize2) * this.normalizationCoef);
    }

    /* access modifiers changed from: protected */
    public void setSubscriptOffset(int subscriptOffset2) {
        this.subscriptOffset = (int) (((float) subscriptOffset2) * this.normalizationCoef);
    }

    /* access modifiers changed from: protected */
    public void setSuperscriptSize(int superscriptSize2) {
        this.superscriptSize = superscriptSize2;
    }

    /* access modifiers changed from: protected */
    public void setSuperscriptOffset(int superscriptOffset2) {
        this.superscriptOffset = (int) (((float) superscriptOffset2) * this.normalizationCoef);
    }

    public void setStemV(int stemV2) {
        this.stemV = stemV2;
    }

    /* access modifiers changed from: protected */
    public void setStemH(int stemH2) {
        this.stemH = stemH2;
    }

    /* access modifiers changed from: protected */
    public void setIsFixedPitch(boolean isFixedPitch2) {
        this.isFixedPitch = isFixedPitch2;
    }
}
