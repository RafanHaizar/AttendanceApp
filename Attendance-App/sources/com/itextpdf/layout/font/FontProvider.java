package com.itextpdf.layout.font;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.p026io.font.FontProgram;
import com.itextpdf.p026io.font.FontProgramFactory;
import com.itextpdf.p026io.font.PdfEncodings;
import com.itextpdf.p026io.font.Type1Font;
import com.itextpdf.p026io.util.FileUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FontProvider {
    private static final String DEFAULT_FONT_FAMILY = "Helvetica";
    protected final String defaultFontFamily;
    private final FontSelectorCache fontSelectorCache;
    private final FontSet fontSet;
    protected final Map<FontInfo, PdfFont> pdfFonts;

    public FontProvider(FontSet fontSet2) {
        this(fontSet2, "Helvetica");
    }

    public FontProvider() {
        this(new FontSet());
    }

    public FontProvider(String defaultFontFamily2) {
        this(new FontSet(), defaultFontFamily2);
    }

    public FontProvider(FontSet fontSet2, String defaultFontFamily2) {
        FontSet fontSet3 = fontSet2 != null ? fontSet2 : new FontSet();
        this.fontSet = fontSet3;
        this.pdfFonts = new HashMap();
        this.fontSelectorCache = new FontSelectorCache(fontSet3);
        this.defaultFontFamily = defaultFontFamily2;
    }

    public boolean addFont(FontProgram fontProgram, String encoding, Range unicodeRange) {
        return this.fontSet.addFont(fontProgram, encoding, (String) null, unicodeRange);
    }

    public boolean addFont(FontProgram fontProgram, String encoding) {
        return addFont(fontProgram, encoding, (Range) null);
    }

    public boolean addFont(FontProgram fontProgram) {
        return addFont(fontProgram, getDefaultEncoding(fontProgram));
    }

    public boolean addFont(String fontPath, String encoding, Range unicodeRange) {
        return this.fontSet.addFont(fontPath, encoding, (String) null, unicodeRange);
    }

    public boolean addFont(String fontPath, String encoding) {
        return addFont(fontPath, encoding, (Range) null);
    }

    public boolean addFont(String fontPath) {
        return addFont(fontPath, (String) null);
    }

    public boolean addFont(byte[] fontData, String encoding, Range unicodeRange) {
        return this.fontSet.addFont(fontData, encoding, (String) null, unicodeRange);
    }

    public boolean addFont(byte[] fontData, String encoding) {
        return addFont(fontData, encoding, (Range) null);
    }

    public boolean addFont(byte[] fontData) {
        return addFont(fontData, (String) null);
    }

    public int addDirectory(String dir) {
        return this.fontSet.addDirectory(dir);
    }

    public int addSystemFonts() {
        int count = 0;
        for (String directory : new String[]{FileUtil.getFontsDir(), "/usr/share/X11/fonts", "/usr/X/lib/X11/fonts", "/usr/openwin/lib/X11/fonts", "/usr/share/fonts", "/usr/X11R6/lib/X11/fonts"}) {
            count += this.fontSet.addDirectory(directory, true);
        }
        for (String directory2 : new String[]{"/Library/Fonts", "/System/Library/Fonts"}) {
            count += this.fontSet.addDirectory(directory2, false);
        }
        return count;
    }

    public int addStandardPdfFonts() {
        addFont("Courier");
        addFont("Courier-Bold");
        addFont("Courier-BoldOblique");
        addFont("Courier-Oblique");
        addFont("Helvetica");
        addFont("Helvetica-Bold");
        addFont("Helvetica-BoldOblique");
        addFont("Helvetica-Oblique");
        addFont("Symbol");
        addFont("Times-Roman");
        addFont("Times-Bold");
        addFont("Times-BoldItalic");
        addFont("Times-Italic");
        addFont("ZapfDingbats");
        return 14;
    }

    public FontSet getFontSet() {
        return this.fontSet;
    }

    public String getDefaultFontFamily() {
        return this.defaultFontFamily;
    }

    public String getDefaultEncoding(FontProgram fontProgram) {
        if (fontProgram instanceof Type1Font) {
            return "Cp1252";
        }
        return PdfEncodings.IDENTITY_H;
    }

    public boolean getDefaultCacheFlag() {
        return true;
    }

    public boolean getDefaultEmbeddingFlag() {
        return true;
    }

    public FontSelectorStrategy getStrategy(String text, List<String> fontFamilies, FontCharacteristics fc, FontSet additionalFonts) {
        return new ComplexFontSelectorStrategy(text, getFontSelector(fontFamilies, fc, additionalFonts), this, additionalFonts);
    }

    public FontSelectorStrategy getStrategy(String text, List<String> fontFamilies, FontCharacteristics fc) {
        return getStrategy(text, fontFamilies, fc, (FontSet) null);
    }

    public FontSelectorStrategy getStrategy(String text, List<String> fontFamilies) {
        return getStrategy(text, fontFamilies, (FontCharacteristics) null);
    }

    public final FontSelector getFontSelector(List<String> fontFamilies, FontCharacteristics fc) {
        FontSelectorKey key = new FontSelectorKey(fontFamilies, fc);
        FontSelector fontSelector = this.fontSelectorCache.get(key);
        if (fontSelector != null) {
            return fontSelector;
        }
        FontSelector fontSelector2 = createFontSelector(this.fontSet.getFonts(), fontFamilies, fc);
        this.fontSelectorCache.put(key, fontSelector2);
        return fontSelector2;
    }

    public final FontSelector getFontSelector(List<String> fontFamilies, FontCharacteristics fc, FontSet additionalFonts) {
        FontSelectorKey key = new FontSelectorKey(fontFamilies, fc);
        FontSelector fontSelector = this.fontSelectorCache.get(key, additionalFonts);
        if (fontSelector != null) {
            return fontSelector;
        }
        FontSelector fontSelector2 = createFontSelector(this.fontSet.getFonts(additionalFonts), fontFamilies, fc);
        this.fontSelectorCache.put(key, fontSelector2, additionalFonts);
        return fontSelector2;
    }

    /* access modifiers changed from: protected */
    public FontSelector createFontSelector(Collection<FontInfo> fonts, List<String> fontFamilies, FontCharacteristics fc) {
        List<String> fontFamiliesToBeProcessed = new ArrayList<>(fontFamilies);
        fontFamiliesToBeProcessed.add(this.defaultFontFamily);
        return new FontSelector(fonts, fontFamiliesToBeProcessed, fc);
    }

    public PdfFont getPdfFont(FontInfo fontInfo) {
        return getPdfFont(fontInfo, (FontSet) null);
    }

    public PdfFont getPdfFont(FontInfo fontInfo, FontSet additionalFonts) {
        if (this.pdfFonts.containsKey(fontInfo)) {
            return this.pdfFonts.get(fontInfo);
        }
        FontProgram fontProgram = null;
        if (additionalFonts != null) {
            fontProgram = additionalFonts.getFontProgram(fontInfo);
        }
        if (fontProgram == null) {
            fontProgram = this.fontSet.getFontProgram(fontInfo);
        }
        if (fontProgram == null) {
            try {
                if (fontInfo.getFontData() != null) {
                    fontProgram = FontProgramFactory.createFont(fontInfo.getFontData(), getDefaultCacheFlag());
                } else {
                    fontProgram = FontProgramFactory.createFont(fontInfo.getFontName(), getDefaultCacheFlag());
                }
            } catch (IOException e) {
                throw new PdfException(PdfException.IoExceptionWhileCreatingFont, (Throwable) e);
            }
        }
        String encoding = fontInfo.getEncoding();
        if (encoding == null || encoding.length() == 0) {
            encoding = getDefaultEncoding(fontProgram);
        }
        PdfFont pdfFont = PdfFontFactory.createFont(fontProgram, encoding, getDefaultEmbeddingFlag());
        this.pdfFonts.put(fontInfo, pdfFont);
        return pdfFont;
    }

    public void reset() {
        this.pdfFonts.clear();
    }
}
