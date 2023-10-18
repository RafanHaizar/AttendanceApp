package com.itextpdf.kernel.font;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.font.AdobeGlyphList;
import com.itextpdf.p026io.font.FontEncoding;
import com.itextpdf.p026io.font.FontMetrics;
import com.itextpdf.p026io.font.FontNames;
import com.itextpdf.p026io.font.constants.FontDescriptorFlags;
import com.itextpdf.p026io.font.otf.Glyph;
import java.util.Iterator;
import java.util.Map;
import org.slf4j.LoggerFactory;

public class PdfType3Font extends PdfSimpleFont<Type3Font> {
    private static final long serialVersionUID = 4940119184993066859L;
    private double dimensionsMultiplier;
    private double[] fontMatrix;

    PdfType3Font(PdfDocument document, boolean colorized) {
        this.fontMatrix = DEFAULT_FONT_MATRIX;
        makeIndirect(document);
        this.subset = true;
        this.embedded = true;
        this.fontProgram = new Type3Font(colorized);
        this.fontEncoding = FontEncoding.createEmptyFontEncoding();
        this.dimensionsMultiplier = 1.0d;
    }

    PdfType3Font(PdfDocument document, String fontName, String fontFamily, boolean colorized) {
        this(document, colorized);
        ((Type3Font) this.fontProgram).setFontName(fontName);
        ((Type3Font) this.fontProgram).setFontFamily(fontFamily);
        this.dimensionsMultiplier = 1.0d;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    PdfType3Font(PdfDictionary fontDictionary) {
        super(fontDictionary);
        int code;
        PdfDictionary charProcsDic;
        Iterator<PdfName> it;
        int firstChar;
        PdfArray fontMatrixArray;
        PdfDictionary pdfDictionary = fontDictionary;
        this.fontMatrix = DEFAULT_FONT_MATRIX;
        this.subset = true;
        this.embedded = true;
        this.fontProgram = new Type3Font(false);
        this.fontEncoding = DocFontEncoding.createDocFontEncoding(pdfDictionary.get(PdfName.Encoding), this.toUnicode);
        PdfDictionary charProcsDic2 = ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.CharProcs);
        PdfArray fontMatrixArray2 = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.FontMatrix);
        double[] fontMatrix2 = new double[6];
        for (int i = 0; i < fontMatrixArray2.size(); i++) {
            fontMatrix2[i] = ((PdfNumber) fontMatrixArray2.get(i)).getValue();
        }
        setDimensionsMultiplier(fontMatrix2[0] * 1000.0d);
        for (int i2 = 0; i2 < 6; i2++) {
            fontMatrix2[i2] = fontMatrix2[i2] / getDimensionsMultiplier();
        }
        setFontMatrix(fontMatrix2);
        if (((PdfDictionary) getPdfObject()).containsKey(PdfName.FontBBox)) {
            PdfArray fontBBox = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.FontBBox);
            this.fontProgram.getFontMetrics().setBbox((int) (fontBBox.getAsNumber(0).doubleValue() * getDimensionsMultiplier()), (int) (fontBBox.getAsNumber(1).doubleValue() * getDimensionsMultiplier()), (int) (fontBBox.getAsNumber(2).doubleValue() * getDimensionsMultiplier()), (int) (fontBBox.getAsNumber(3).doubleValue() * getDimensionsMultiplier()));
        } else {
            this.fontProgram.getFontMetrics().setBbox(0, 0, 0, 0);
        }
        int firstChar2 = normalizeFirstLastChar(pdfDictionary.getAsNumber(PdfName.FirstChar), 0);
        int i3 = 255;
        int lastChar = normalizeFirstLastChar(pdfDictionary.getAsNumber(PdfName.LastChar), 255);
        for (int i4 = firstChar2; i4 <= lastChar; i4++) {
            this.shortTag[i4] = 1;
        }
        PdfArray pdfWidths = pdfDictionary.getAsArray(PdfName.Widths);
        double[] multipliedWidths = new double[pdfWidths.size()];
        for (int i5 = 0; i5 < pdfWidths.size(); i5++) {
            multipliedWidths[i5] = pdfWidths.getAsNumber(i5).doubleValue() * getDimensionsMultiplier();
        }
        int[] widths = FontUtil.convertSimpleWidthsArray(new PdfArray(multipliedWidths), firstChar2, 0);
        int i6 = -1;
        if (this.toUnicode == null || !this.toUnicode.hasByteMappings() || !this.fontEncoding.hasDifferences()) {
            int i7 = firstChar2;
        } else {
            int i8 = 0;
            while (i8 <= i3) {
                int unicode = this.fontEncoding.getUnicode(i8);
                PdfName glyphName = new PdfName(this.fontEncoding.getDifference(i8));
                if (unicode == i6) {
                    fontMatrixArray = fontMatrixArray2;
                    firstChar = firstChar2;
                    int i9 = unicode;
                } else if (FontEncoding.NOTDEF.equals(glyphName.getValue())) {
                    fontMatrixArray = fontMatrixArray2;
                    firstChar = firstChar2;
                    int i10 = unicode;
                } else if (charProcsDic2.containsKey(glyphName)) {
                    fontMatrixArray = fontMatrixArray2;
                    firstChar = firstChar2;
                    ((Type3Font) getFontProgram()).addGlyph(i8, unicode, widths[i8], (int[]) null, new Type3Glyph(charProcsDic2.getAsStream(glyphName), getDocument()));
                } else {
                    fontMatrixArray = fontMatrixArray2;
                    firstChar = firstChar2;
                    int i11 = unicode;
                }
                i8++;
                fontMatrixArray2 = fontMatrixArray;
                firstChar2 = firstChar;
                i3 = 255;
                i6 = -1;
            }
            int i12 = firstChar2;
        }
        Map<Integer, Integer> unicodeToCode = null;
        if (this.toUnicode != null) {
            try {
                unicodeToCode = this.toUnicode.createReverseMapping();
            } catch (Exception e) {
            }
        }
        Iterator<PdfName> it2 = charProcsDic2.keySet().iterator();
        while (it2.hasNext()) {
            PdfName glyphName2 = it2.next();
            int unicode2 = AdobeGlyphList.nameToUnicode(glyphName2.getValue());
            if (this.fontEncoding.canEncode(unicode2)) {
                code = this.fontEncoding.convertToByte(unicode2);
            } else if (unicodeToCode == null || !unicodeToCode.containsKey(Integer.valueOf(unicode2))) {
                code = -1;
            } else {
                code = unicodeToCode.get(Integer.valueOf(unicode2)).intValue();
            }
            if (code == -1 || getFontProgram().getGlyphByCode(code) != null) {
                it = it2;
                charProcsDic = charProcsDic2;
                int i13 = code;
            } else {
                it = it2;
                charProcsDic = charProcsDic2;
                int i14 = code;
                ((Type3Font) getFontProgram()).addGlyph(code, unicode2, widths[code], (int[]) null, new Type3Glyph(charProcsDic2.getAsStream(glyphName2), getDocument()));
            }
            it2 = it;
            charProcsDic2 = charProcsDic;
        }
        fillFontDescriptor(pdfDictionary.getAsDictionary(PdfName.FontDescriptor));
    }

    public void setFontName(String fontName) {
        ((Type3Font) this.fontProgram).setFontName(fontName);
    }

    public void setFontFamily(String fontFamily) {
        ((Type3Font) this.fontProgram).setFontFamily(fontFamily);
    }

    public void setFontWeight(int fontWeight) {
        ((Type3Font) this.fontProgram).setFontWeight(fontWeight);
    }

    public void setItalicAngle(int italicAngle) {
        ((Type3Font) this.fontProgram).setItalicAngle(italicAngle);
    }

    public void setFontStretch(String fontWidth) {
        ((Type3Font) this.fontProgram).setFontStretch(fontWidth);
    }

    public void setPdfFontFlags(int flags) {
        ((Type3Font) this.fontProgram).setPdfFontFlags(flags);
    }

    public Type3Glyph getType3Glyph(int unicode) {
        return ((Type3Font) getFontProgram()).getType3Glyph(unicode);
    }

    public boolean isSubset() {
        return true;
    }

    public boolean isEmbedded() {
        return true;
    }

    public double[] getFontMatrix() {
        return this.fontMatrix;
    }

    public void setFontMatrix(double[] fontMatrix2) {
        this.fontMatrix = fontMatrix2;
    }

    public int getNumberOfGlyphs() {
        return ((Type3Font) getFontProgram()).getNumberOfGlyphs();
    }

    public Type3Glyph addGlyph(char c, int wx, int llx, int lly, int urx, int ury) {
        int i = llx;
        int i2 = lly;
        int i3 = urx;
        int i4 = ury;
        Type3Glyph glyph = getType3Glyph(c);
        if (glyph != null) {
            return glyph;
        }
        int code = getFirstEmptyCode();
        Type3Glyph glyph2 = new Type3Glyph(getDocument(), (float) wx, (float) i, (float) i2, (float) i3, (float) i4, ((Type3Font) getFontProgram()).isColorized());
        char c2 = c;
        ((Type3Font) getFontProgram()).addGlyph(code, c2, wx, new int[]{i, i2, i3, i4}, glyph2);
        this.fontEncoding.addSymbol((byte) code, c2);
        if (!((Type3Font) getFontProgram()).isColorized()) {
            if (this.fontProgram.countOfGlyphs() == 0) {
                this.fontProgram.getFontMetrics().setBbox(i, i2, i3, i4);
            } else {
                int[] bbox = this.fontProgram.getFontMetrics().getBbox();
                this.fontProgram.getFontMetrics().setBbox(Math.min(bbox[0], i), Math.min(bbox[1], i2), Math.max(bbox[2], i3), Math.max(bbox[3], i4));
            }
        }
        return glyph2;
    }

    public Glyph getGlyph(int unicode) {
        if (!this.fontEncoding.canEncode(unicode) && unicode >= 33) {
            return null;
        }
        Glyph glyph = getFontProgram().getGlyph(this.fontEncoding.getUnicodeDifference(unicode));
        if (glyph != null) {
            return glyph;
        }
        Glyph glyph2 = (Glyph) this.notdefGlyphs.get(Integer.valueOf(unicode));
        Glyph glyph3 = glyph2;
        if (glyph2 != null) {
            return glyph3;
        }
        Glyph glyph4 = new Glyph(-1, 0, unicode);
        this.notdefGlyphs.put(Integer.valueOf(unicode), glyph4);
        return glyph4;
    }

    public boolean containsGlyph(int unicode) {
        return (this.fontEncoding.canEncode(unicode) || unicode < 33) && getFontProgram().getGlyph(this.fontEncoding.getUnicodeDifference(unicode)) != null;
    }

    public void flush() {
        Type3Glyph glyph;
        if (!isFlushed()) {
            ensureUnderlyingObjectHasIndirectReference();
            if (((Type3Font) getFontProgram()).getNumberOfGlyphs() >= 1) {
                PdfDictionary charProcs = new PdfDictionary();
                for (int i = 0; i <= 255; i++) {
                    if (this.fontEncoding.canDecode(i) && (glyph = getType3Glyph(this.fontEncoding.getUnicode(i))) != null) {
                        charProcs.put(new PdfName(this.fontEncoding.getDifference(i)), glyph.getContentStream());
                        glyph.getContentStream().flush();
                    }
                }
                ((PdfDictionary) getPdfObject()).put(PdfName.CharProcs, charProcs);
                int i2 = 0;
                while (true) {
                    double[] dArr = this.fontMatrix;
                    if (i2 < dArr.length) {
                        dArr[i2] = dArr[i2] * getDimensionsMultiplier();
                        i2++;
                    } else {
                        ((PdfDictionary) getPdfObject()).put(PdfName.FontMatrix, new PdfArray(getFontMatrix()));
                        ((PdfDictionary) getPdfObject()).put(PdfName.FontBBox, normalizeBBox(this.fontProgram.getFontMetrics().getBbox()));
                        super.flushFontData(this.fontProgram.getFontNames().getFontName(), PdfName.Type3);
                        ((PdfDictionary) getPdfObject()).remove(PdfName.BaseFont);
                        super.flush();
                        return;
                    }
                }
            } else {
                throw new PdfException("No glyphs defined for type3 font.");
            }
        }
    }

    /* access modifiers changed from: protected */
    public PdfDictionary getFontDescriptor(String fontName) {
        if (fontName != null && fontName.length() > 0) {
            PdfDictionary fontDescriptor = new PdfDictionary();
            makeObjectIndirect(fontDescriptor);
            fontDescriptor.put(PdfName.Type, PdfName.FontDescriptor);
            FontMetrics fontMetrics = this.fontProgram.getFontMetrics();
            fontDescriptor.put(PdfName.CapHeight, new PdfNumber(fontMetrics.getCapHeight()));
            fontDescriptor.put(PdfName.ItalicAngle, new PdfNumber((double) fontMetrics.getItalicAngle()));
            FontNames fontNames = this.fontProgram.getFontNames();
            fontDescriptor.put(PdfName.FontWeight, new PdfNumber(fontNames.getFontWeight()));
            fontDescriptor.put(PdfName.FontName, new PdfName(fontName));
            if (fontNames.getFamilyName() != null && fontNames.getFamilyName().length > 0 && fontNames.getFamilyName()[0].length >= 4) {
                fontDescriptor.put(PdfName.FontFamily, new PdfString(fontNames.getFamilyName()[0][3]));
            }
            fontDescriptor.put(PdfName.Flags, new PdfNumber((this.fontProgram.getPdfFontFlags() & ((FontDescriptorFlags.Symbolic | FontDescriptorFlags.Nonsymbolic) ^ -1)) | (this.fontEncoding.isFontSpecific() ? FontDescriptorFlags.Symbolic : FontDescriptorFlags.Nonsymbolic)));
            return fontDescriptor;
        } else if (((PdfDictionary) getPdfObject()).getIndirectReference() == null || !((PdfDictionary) getPdfObject()).getIndirectReference().getDocument().isTagged()) {
            return null;
        } else {
            LoggerFactory.getLogger((Class<?>) PdfType3Font.class).warn(LogMessageConstant.TYPE3_FONT_ISSUE_TAGGED_PDF);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public void addFontStream(PdfDictionary fontDescriptor) {
    }

    /* access modifiers changed from: protected */
    public PdfDocument getDocument() {
        return ((PdfDictionary) getPdfObject()).getIndirectReference().getDocument();
    }

    /* access modifiers changed from: protected */
    public double getGlyphWidth(Glyph glyph) {
        if (glyph == null) {
            return 0.0d;
        }
        double width = (double) glyph.getWidth();
        double dimensionsMultiplier2 = getDimensionsMultiplier();
        Double.isNaN(width);
        return width / dimensionsMultiplier2;
    }

    /* access modifiers changed from: package-private */
    public double getDimensionsMultiplier() {
        return this.dimensionsMultiplier;
    }

    /* access modifiers changed from: package-private */
    public void setDimensionsMultiplier(double dimensionsMultiplier2) {
        this.dimensionsMultiplier = dimensionsMultiplier2;
    }

    private int getFirstEmptyCode() {
        for (int i = 1; i <= 255; i++) {
            if (!this.fontEncoding.canDecode(i)) {
                return i;
            }
        }
        return -1;
    }

    private void fillFontDescriptor(PdfDictionary fontDesc) {
        if (fontDesc != null) {
            PdfNumber v = fontDesc.getAsNumber(PdfName.ItalicAngle);
            if (v != null) {
                setItalicAngle(v.intValue());
            }
            PdfNumber v2 = fontDesc.getAsNumber(PdfName.FontWeight);
            if (v2 != null) {
                setFontWeight(v2.intValue());
            }
            PdfName fontStretch = fontDesc.getAsName(PdfName.FontStretch);
            if (fontStretch != null) {
                setFontStretch(fontStretch.getValue());
            }
            PdfName fontName = fontDesc.getAsName(PdfName.FontName);
            if (fontName != null) {
                setFontName(fontName.getValue());
            }
            PdfString fontFamily = fontDesc.getAsString(PdfName.FontFamily);
            if (fontFamily != null) {
                setFontFamily(fontFamily.getValue());
            }
        }
    }

    private int normalizeFirstLastChar(PdfNumber firstLast, int defaultValue) {
        if (firstLast == null) {
            return defaultValue;
        }
        int result = firstLast.intValue();
        return (result < 0 || result > 255) ? defaultValue : result;
    }

    private PdfArray normalizeBBox(int[] bBox) {
        double[] normalizedBBox = new double[4];
        for (int i = 0; i < 4; i++) {
            double d = (double) bBox[i];
            double dimensionsMultiplier2 = getDimensionsMultiplier();
            Double.isNaN(d);
            normalizedBBox[i] = d / dimensionsMultiplier2;
        }
        return new PdfArray(normalizedBBox);
    }
}
