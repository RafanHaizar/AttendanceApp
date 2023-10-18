package com.itextpdf.layout.font;

import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FontSelector {
    private static final int EXPECTED_FONT_IS_BOLD_AWARD = 5;
    private static final int EXPECTED_FONT_IS_ITALIC_AWARD = 5;
    private static final int EXPECTED_FONT_IS_MONOSPACED_AWARD = 5;
    private static final int EXPECTED_FONT_IS_NOT_BOLD_AWARD = 3;
    private static final int EXPECTED_FONT_IS_NOT_ITALIC_AWARD = 3;
    private static final int EXPECTED_FONT_IS_NOT_MONOSPACED_AWARD = 1;
    private static final int FONT_FAMILY_EQUALS_AWARD = 13;
    protected List<FontInfo> fonts;

    public FontSelector(Collection<FontInfo> allFonts, List<String> fontFamilies, FontCharacteristics fc) {
        ArrayList arrayList = new ArrayList(allFonts);
        this.fonts = arrayList;
        Collections.sort(arrayList, getComparator(fontFamilies, fc));
    }

    public final FontInfo bestMatch() {
        return this.fonts.get(0);
    }

    public final Iterable<FontInfo> getFonts() {
        return this.fonts;
    }

    /* access modifiers changed from: protected */
    public Comparator<FontInfo> getComparator(List<String> fontFamilies, FontCharacteristics fc) {
        return new PdfFontComparator(fontFamilies, fc);
    }

    private static class PdfFontComparator implements Comparator<FontInfo> {
        List<String> fontFamilies = new ArrayList();
        List<FontCharacteristics> fontStyles = new ArrayList();

        PdfFontComparator(List<String> fontFamilies2, FontCharacteristics fc) {
            if (fontFamilies2 == null || fontFamilies2.size() <= 0) {
                this.fontStyles.add(fc);
                return;
            }
            for (String fontFamily : fontFamilies2) {
                String lowercaseFontFamily = fontFamily.toLowerCase();
                this.fontFamilies.add(lowercaseFontFamily);
                this.fontStyles.add(parseFontStyle(lowercaseFontFamily, fc));
            }
        }

        public int compare(FontInfo o1, FontInfo o2) {
            int res = 0;
            for (int i = 0; i < this.fontFamilies.size() && res == 0; i++) {
                FontCharacteristics fc = this.fontStyles.get(i);
                String fontFamily = this.fontFamilies.get(i);
                boolean z = true;
                if ("monospace".equalsIgnoreCase(fontFamily)) {
                    fc.setMonospaceFlag(true);
                }
                if (i != this.fontFamilies.size() - 1) {
                    z = false;
                }
                boolean isLastFontFamilyToBeProcessed = z;
                res = characteristicsSimilarity(fontFamily, fc, o2, isLastFontFamilyToBeProcessed) - characteristicsSimilarity(fontFamily, fc, o1, isLastFontFamilyToBeProcessed);
            }
            return res;
        }

        private static FontCharacteristics parseFontStyle(String fontFamily, FontCharacteristics fc) {
            if (fc == null) {
                fc = new FontCharacteristics();
            }
            if (fc.isUndefined()) {
                if (fontFamily.contains("bold")) {
                    fc.setBoldFlag(true);
                }
                if (fontFamily.contains("italic") || fontFamily.contains(CommonCssConstants.OBLIQUE)) {
                    fc.setItalicFlag(true);
                }
            }
            return fc;
        }

        private static int characteristicsSimilarity(String fontFamily, FontCharacteristics fc, FontInfo fontInfo, boolean isLastFontFamilyToBeProcessed) {
            boolean isFontItalic = false;
            boolean isFontBold = fontInfo.getDescriptor().isBold() || fontInfo.getDescriptor().getFontWeight() > 500;
            if (fontInfo.getDescriptor().isItalic() || fontInfo.getDescriptor().getItalicAngle() < 0.0f) {
                isFontItalic = true;
            }
            boolean isFontMonospace = fontInfo.getDescriptor().isMonospace();
            int score = 0;
            boolean fontFamilySetByCharacteristics = false;
            if (fc.isMonospace()) {
                fontFamilySetByCharacteristics = true;
                if (isFontMonospace) {
                    score = 0 + 5;
                } else {
                    score = 0 - 5;
                }
            } else if (isFontMonospace) {
                score = 0 - 1;
            }
            if (!fontFamilySetByCharacteristics) {
                if (!"".equals(fontFamily) && ((fontInfo.getAlias() == null && fontInfo.getDescriptor().getFamilyNameLowerCase() != null && fontInfo.getDescriptor().getFamilyNameLowerCase().equals(fontFamily)) || (fontInfo.getAlias() != null && fontInfo.getAlias().toLowerCase().equals(fontFamily)))) {
                    score += 13;
                } else if (!isLastFontFamilyToBeProcessed) {
                    return score;
                }
            }
            if (fc.isBold()) {
                if (isFontBold) {
                    score += 5;
                } else {
                    score -= 5;
                }
            } else if (isFontBold) {
                score -= 3;
            }
            if (fc.isItalic()) {
                if (isFontItalic) {
                    return score + 5;
                }
                return score - 5;
            } else if (isFontItalic) {
                return score - 3;
            } else {
                return score;
            }
        }
    }
}
