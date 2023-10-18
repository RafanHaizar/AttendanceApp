package com.itextpdf.layout.font;

import com.itextpdf.styledxmlparser.css.CommonCssConstants;

public final class FontCharacteristics {
    private short fontWeight;
    private boolean isBold;
    private boolean isItalic;
    private boolean isMonospace;
    private boolean undefined;

    public FontCharacteristics() {
        this.isItalic = false;
        this.isBold = false;
        this.fontWeight = 400;
        this.undefined = true;
        this.isMonospace = false;
    }

    public FontCharacteristics(FontCharacteristics other) {
        this();
        this.isItalic = other.isItalic;
        this.isBold = other.isBold;
        this.fontWeight = other.fontWeight;
        this.undefined = other.undefined;
    }

    public FontCharacteristics setFontWeight(short fw) {
        if (fw > 0) {
            this.fontWeight = FontCharacteristicsUtils.normalizeFontWeight(fw);
            modified();
        }
        return this;
    }

    public FontCharacteristics setFontWeight(String fw) {
        return setFontWeight(FontCharacteristicsUtils.parseFontWeight(fw));
    }

    public FontCharacteristics setBoldFlag(boolean isBold2) {
        this.isBold = isBold2;
        if (isBold2) {
            modified();
        }
        return this;
    }

    public FontCharacteristics setItalicFlag(boolean isItalic2) {
        this.isItalic = isItalic2;
        if (isItalic2) {
            modified();
        }
        return this;
    }

    public FontCharacteristics setMonospaceFlag(boolean isMonospace2) {
        this.isMonospace = isMonospace2;
        if (isMonospace2) {
            modified();
        }
        return this;
    }

    public FontCharacteristics setFontStyle(String fs) {
        if (fs != null && fs.length() > 0) {
            String fs2 = fs.trim().toLowerCase();
            if (CommonCssConstants.NORMAL.equals(fs2)) {
                this.isItalic = false;
            } else if ("italic".equals(fs2) || CommonCssConstants.OBLIQUE.equals(fs2)) {
                this.isItalic = true;
            }
        }
        if (this.isItalic) {
            modified();
        }
        return this;
    }

    public boolean isItalic() {
        return this.isItalic;
    }

    public boolean isBold() {
        return this.isBold || this.fontWeight > 500;
    }

    public boolean isMonospace() {
        return this.isMonospace;
    }

    public short getFontWeight() {
        return this.fontWeight;
    }

    public boolean isUndefined() {
        return this.undefined;
    }

    private void modified() {
        this.undefined = false;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FontCharacteristics that = (FontCharacteristics) o;
        if (this.isItalic == that.isItalic && this.isBold == that.isBold && this.fontWeight == that.fontWeight) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((int) this.isItalic) * true) + (this.isBold ? 1 : 0)) * 31) + this.fontWeight;
    }
}
