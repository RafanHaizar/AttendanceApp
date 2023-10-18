package com.itextpdf.styledxmlparser.resolver.font;

import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.font.FontSet;

public class BasicFontProvider extends FontProvider {
    private static final String DEFAULT_FONT_FAMILY = "Times";

    public BasicFontProvider() {
        this(true, false);
    }

    public BasicFontProvider(boolean registerStandardPdfFonts, boolean registerSystemFonts) {
        this(registerStandardPdfFonts, registerSystemFonts, "Times");
    }

    public BasicFontProvider(boolean registerStandardPdfFonts, boolean registerSystemFonts, String defaultFontFamily) {
        super(defaultFontFamily);
        if (registerStandardPdfFonts) {
            addStandardPdfFonts();
        }
        if (registerSystemFonts) {
            addSystemFonts();
        }
    }

    public BasicFontProvider(FontSet fontSet, String defaultFontFamily) {
        super(fontSet, defaultFontFamily);
    }
}
