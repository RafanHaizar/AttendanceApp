package com.itextpdf.kernel.font;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.font.FontEncoding;
import com.itextpdf.p026io.font.FontProgramFactory;
import com.itextpdf.p026io.font.Type1Font;
import com.itextpdf.p026io.font.cmap.CMapToUnicode;
import com.itextpdf.p026io.font.otf.Glyph;
import org.slf4j.LoggerFactory;

class DocType1Font extends Type1Font implements IDocFontProgram {
    private static final long serialVersionUID = 6260280563455951912L;
    private PdfStream fontFile;
    private PdfName fontFileName;
    private int missingWidth = 0;
    private PdfName subtype;

    private DocType1Font(String fontName) {
        super(fontName);
    }

    static Type1Font createFontProgram(PdfDictionary fontDictionary, FontEncoding fontEncoding, CMapToUnicode toUnicode) {
        String baseFont;
        Type1Font type1StdFont;
        PdfName baseFontName = fontDictionary.getAsName(PdfName.BaseFont);
        if (baseFontName != null) {
            baseFont = baseFontName.getValue();
        } else {
            baseFont = FontUtil.createRandomFontName();
        }
        if (!fontDictionary.containsKey(PdfName.FontDescriptor)) {
            try {
                type1StdFont = (Type1Font) FontProgramFactory.createFont(baseFont, true);
            } catch (Exception e) {
                type1StdFont = null;
            }
            if (type1StdFont != null) {
                return type1StdFont;
            }
        }
        DocType1Font fontProgram = new DocType1Font(baseFont);
        PdfDictionary fontDesc = fontDictionary.getAsDictionary(PdfName.FontDescriptor);
        fontProgram.subtype = fontDesc != null ? fontDesc.getAsName(PdfName.Subtype) : null;
        fillFontDescriptor(fontProgram, fontDesc);
        PdfNumber firstCharNumber = fontDictionary.getAsNumber(PdfName.FirstChar);
        int[] widths = FontUtil.convertSimpleWidthsArray(fontDictionary.getAsArray(PdfName.Widths), firstCharNumber != null ? Math.max(firstCharNumber.intValue(), 0) : 0, fontProgram.getMissingWidth());
        fontProgram.avgWidth = 0;
        int glyphsWithWidths = 0;
        for (int i = 0; i < 256; i++) {
            Glyph glyph = new Glyph(i, widths[i], fontEncoding.getUnicode(i));
            fontProgram.codeToGlyph.put(Integer.valueOf(i), glyph);
            if (glyph.hasValidUnicode()) {
                if (fontEncoding.convertToByte(glyph.getUnicode()) == i) {
                    fontProgram.unicodeToGlyph.put(Integer.valueOf(glyph.getUnicode()), glyph);
                }
            } else if (toUnicode != null) {
                glyph.setChars(toUnicode.lookup(i));
            }
            if (widths[i] > 0) {
                glyphsWithWidths++;
                fontProgram.avgWidth += widths[i];
            }
        }
        if (glyphsWithWidths != 0) {
            fontProgram.avgWidth /= glyphsWithWidths;
        }
        return fontProgram;
    }

    public PdfStream getFontFile() {
        return this.fontFile;
    }

    public PdfName getFontFileName() {
        return this.fontFileName;
    }

    public PdfName getSubtype() {
        return this.subtype;
    }

    public boolean isBuiltWith(String fontName) {
        return false;
    }

    public int getMissingWidth() {
        return this.missingWidth;
    }

    static void fillFontDescriptor(DocType1Font font, PdfDictionary fontDesc) {
        if (fontDesc == null) {
            LoggerFactory.getLogger((Class<?>) FontUtil.class).warn(LogMessageConstant.FONT_DICTIONARY_WITH_NO_FONT_DESCRIPTOR);
            return;
        }
        PdfNumber v = fontDesc.getAsNumber(PdfName.Ascent);
        if (v != null) {
            font.setTypoAscender(v.intValue());
        }
        PdfNumber v2 = fontDesc.getAsNumber(PdfName.Descent);
        if (v2 != null) {
            font.setTypoDescender(v2.intValue());
        }
        PdfNumber v3 = fontDesc.getAsNumber(PdfName.CapHeight);
        if (v3 != null) {
            font.setCapHeight(v3.intValue());
        }
        PdfNumber v4 = fontDesc.getAsNumber(PdfName.XHeight);
        if (v4 != null) {
            font.setXHeight(v4.intValue());
        }
        PdfNumber v5 = fontDesc.getAsNumber(PdfName.ItalicAngle);
        if (v5 != null) {
            font.setItalicAngle(v5.intValue());
        }
        PdfNumber v6 = fontDesc.getAsNumber(PdfName.StemV);
        if (v6 != null) {
            font.setStemV(v6.intValue());
        }
        PdfNumber v7 = fontDesc.getAsNumber(PdfName.StemH);
        if (v7 != null) {
            font.setStemH(v7.intValue());
        }
        PdfNumber v8 = fontDesc.getAsNumber(PdfName.FontWeight);
        if (v8 != null) {
            font.setFontWeight(v8.intValue());
        }
        PdfNumber v9 = fontDesc.getAsNumber(PdfName.MissingWidth);
        if (v9 != null) {
            font.missingWidth = v9.intValue();
        }
        PdfName fontStretch = fontDesc.getAsName(PdfName.FontStretch);
        if (fontStretch != null) {
            font.setFontStretch(fontStretch.getValue());
        }
        PdfArray bboxValue = fontDesc.getAsArray(PdfName.FontBBox);
        if (bboxValue != null) {
            int[] bbox = {bboxValue.getAsNumber(0).intValue(), bboxValue.getAsNumber(1).intValue(), bboxValue.getAsNumber(2).intValue(), bboxValue.getAsNumber(3).intValue()};
            if (bbox[0] > bbox[2]) {
                int t = bbox[0];
                bbox[0] = bbox[2];
                bbox[2] = t;
            }
            if (bbox[1] > bbox[3]) {
                int t2 = bbox[1];
                bbox[1] = bbox[3];
                bbox[3] = t2;
            }
            font.setBbox(bbox);
            if (font.getFontMetrics().getTypoAscender() == 0 && font.getFontMetrics().getTypoDescender() == 0) {
                float maxAscent = (float) Math.max(bbox[3], font.getFontMetrics().getTypoAscender());
                float minDescent = (float) Math.min(bbox[1], font.getFontMetrics().getTypoDescender());
                font.setTypoAscender((int) ((maxAscent * 1000.0f) / (maxAscent - minDescent)));
                font.setTypoDescender((int) ((1000.0f * minDescent) / (maxAscent - minDescent)));
            }
        }
        PdfString fontFamily = fontDesc.getAsString(PdfName.FontFamily);
        if (fontFamily != null) {
            font.setFontFamily(fontFamily.getValue());
        }
        PdfNumber flagsValue = fontDesc.getAsNumber(PdfName.Flags);
        if (flagsValue != null) {
            int flags = flagsValue.intValue();
            if ((flags & 1) != 0) {
                font.setFixedPitch(true);
            }
            if ((262144 & flags) != 0) {
                font.setBold(true);
            }
        }
        for (PdfName fontFile2 : new PdfName[]{PdfName.FontFile, PdfName.FontFile2, PdfName.FontFile3}) {
            if (fontDesc.containsKey(fontFile2)) {
                font.fontFileName = fontFile2;
                font.fontFile = fontDesc.getAsStream(fontFile2);
                return;
            }
        }
    }
}
