package com.itextpdf.kernel.font;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.font.FontEncoding;
import com.itextpdf.p026io.font.TrueTypeFont;
import com.itextpdf.p026io.font.cmap.CMapToUnicode;
import com.itextpdf.p026io.font.otf.Glyph;
import com.itextpdf.p026io.util.IntHashtable;
import org.slf4j.LoggerFactory;

class DocTrueTypeFont extends TrueTypeFont implements IDocFontProgram {
    private static final long serialVersionUID = 4611535787920619829L;
    private PdfStream fontFile;
    private PdfName fontFileName;
    private int missingWidth = 0;
    private PdfName subtype;

    private DocTrueTypeFont(PdfDictionary fontDictionary) {
        PdfName baseFontName = fontDictionary.getAsName(PdfName.BaseFont);
        if (baseFontName != null) {
            setFontName(baseFontName.getValue());
        } else {
            setFontName(FontUtil.createRandomFontName());
        }
        this.subtype = fontDictionary.getAsName(PdfName.Subtype);
    }

    static TrueTypeFont createFontProgram(PdfDictionary fontDictionary, FontEncoding fontEncoding, CMapToUnicode toUnicode) {
        DocTrueTypeFont fontProgram = new DocTrueTypeFont(fontDictionary);
        fillFontDescriptor(fontProgram, fontDictionary.getAsDictionary(PdfName.FontDescriptor));
        PdfNumber firstCharNumber = fontDictionary.getAsNumber(PdfName.FirstChar);
        int[] widths = FontUtil.convertSimpleWidthsArray(fontDictionary.getAsArray(PdfName.Widths), firstCharNumber != null ? Math.max(firstCharNumber.intValue(), 0) : 0, fontProgram.getMissingWidth());
        fontProgram.avgWidth = 0;
        int glyphsWithWidths = 0;
        for (int i = 0; i < 256; i++) {
            Glyph glyph = new Glyph(i, widths[i], fontEncoding.getUnicode(i));
            fontProgram.codeToGlyph.put(Integer.valueOf(i), glyph);
            if (glyph.hasValidUnicode() && fontEncoding.convertToByte(glyph.getUnicode()) == i) {
                fontProgram.unicodeToGlyph.put(Integer.valueOf(glyph.getUnicode()), glyph);
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

    static TrueTypeFont createFontProgram(PdfDictionary fontDictionary, CMapToUnicode toUnicode) {
        int dw;
        DocTrueTypeFont fontProgram = new DocTrueTypeFont(fontDictionary);
        PdfDictionary fontDescriptor = fontDictionary.getAsDictionary(PdfName.FontDescriptor);
        fillFontDescriptor(fontProgram, fontDescriptor);
        if (fontDescriptor != null && fontDescriptor.containsKey(PdfName.f1318DW)) {
            dw = fontDescriptor.getAsInt(PdfName.f1318DW).intValue();
        } else if (fontDictionary.containsKey(PdfName.f1318DW)) {
            dw = fontDictionary.getAsInt(PdfName.f1318DW).intValue();
        } else {
            dw = 1000;
        }
        IntHashtable widths = null;
        if (toUnicode != null) {
            widths = FontUtil.convertCompositeWidthsArray(fontDictionary.getAsArray(PdfName.f1409W));
            fontProgram.avgWidth = 0;
            for (Integer intValue : toUnicode.getCodes()) {
                int cid = intValue.intValue();
                int width = widths.containsKey(cid) ? widths.get(cid) : dw;
                Glyph glyph = new Glyph(cid, width, toUnicode.lookup(cid));
                if (glyph.hasValidUnicode()) {
                    fontProgram.unicodeToGlyph.put(Integer.valueOf(glyph.getUnicode()), glyph);
                }
                fontProgram.codeToGlyph.put(Integer.valueOf(cid), glyph);
                fontProgram.avgWidth += width;
            }
            if (fontProgram.codeToGlyph.size() != 0) {
                fontProgram.avgWidth /= fontProgram.codeToGlyph.size();
            }
        }
        if (fontProgram.codeToGlyph.get(0) == null) {
            fontProgram.codeToGlyph.put(0, new Glyph(0, (widths == null || !widths.containsKey(0)) ? dw : widths.get(0), -1));
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

    static void fillFontDescriptor(DocTrueTypeFont font, PdfDictionary fontDesc) {
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
