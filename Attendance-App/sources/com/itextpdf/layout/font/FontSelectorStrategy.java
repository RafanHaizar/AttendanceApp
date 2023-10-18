package com.itextpdf.layout.font;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.p026io.font.otf.Glyph;
import java.util.List;

public abstract class FontSelectorStrategy {
    protected int index = 0;
    protected final FontProvider provider;
    @Deprecated
    protected final FontSet tempFonts;
    protected String text;

    public abstract PdfFont getCurrentFont();

    public abstract List<Glyph> nextGlyphs();

    protected FontSelectorStrategy(String text2, FontProvider provider2, FontSet additionalFonts) {
        this.text = text2;
        this.provider = provider2;
        this.tempFonts = additionalFonts;
    }

    public boolean endOfText() {
        String str = this.text;
        return str == null || this.index >= str.length();
    }

    /* access modifiers changed from: protected */
    public PdfFont getPdfFont(FontInfo fontInfo) {
        return this.provider.getPdfFont(fontInfo, this.tempFonts);
    }
}
