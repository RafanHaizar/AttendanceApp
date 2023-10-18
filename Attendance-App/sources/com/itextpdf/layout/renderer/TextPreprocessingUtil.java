package com.itextpdf.layout.renderer;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.p026io.font.otf.Glyph;
import com.itextpdf.p026io.font.otf.GlyphLine;

public final class TextPreprocessingUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private TextPreprocessingUtil() {
    }

    public static GlyphLine replaceSpecialWhitespaceGlyphs(GlyphLine line, PdfFont font) {
        if (line != null) {
            boolean isMonospaceFont = font.getFontProgram().getFontMetrics().isFixedPitch();
            Glyph space = font.getGlyph(32);
            int spaceWidth = space.getWidth();
            int lineSize = line.size();
            for (int i = 0; i < lineSize; i++) {
                Glyph glyph = line.get(i);
                int xAdvance = 0;
                boolean isSpecialWhitespaceGlyph = false;
                if (glyph.getCode() <= 0) {
                    int i2 = 0;
                    switch (glyph.getUnicode()) {
                        case 9:
                            xAdvance = spaceWidth * 3;
                            isSpecialWhitespaceGlyph = true;
                            break;
                        case 8194:
                            if (!isMonospaceFont) {
                                i2 = 500 - spaceWidth;
                            }
                            xAdvance = i2;
                            isSpecialWhitespaceGlyph = true;
                            break;
                        case 8195:
                            if (!isMonospaceFont) {
                                i2 = 1000 - spaceWidth;
                            }
                            xAdvance = i2;
                            isSpecialWhitespaceGlyph = true;
                            break;
                        case 8201:
                            if (!isMonospaceFont) {
                                i2 = 200 - spaceWidth;
                            }
                            xAdvance = i2;
                            isSpecialWhitespaceGlyph = true;
                            break;
                    }
                }
                if (isSpecialWhitespaceGlyph) {
                    Glyph newGlyph = new Glyph(space, glyph.getUnicode());
                    if (xAdvance > 32767 || xAdvance < -32768) {
                        throw new AssertionError();
                    }
                    newGlyph.setXAdvance((short) xAdvance);
                    line.set(i, newGlyph);
                }
            }
        }
        return line;
    }
}
