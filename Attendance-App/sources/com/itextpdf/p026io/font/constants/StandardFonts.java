package com.itextpdf.p026io.font.constants;

import java.util.HashSet;
import java.util.Set;

/* renamed from: com.itextpdf.io.font.constants.StandardFonts */
public final class StandardFonts {
    private static final Set<String> BUILTIN_FONTS;
    public static final String COURIER = "Courier";
    public static final String COURIER_BOLD = "Courier-Bold";
    public static final String COURIER_BOLDOBLIQUE = "Courier-BoldOblique";
    public static final String COURIER_OBLIQUE = "Courier-Oblique";
    public static final String HELVETICA = "Helvetica";
    public static final String HELVETICA_BOLD = "Helvetica-Bold";
    public static final String HELVETICA_BOLDOBLIQUE = "Helvetica-BoldOblique";
    public static final String HELVETICA_OBLIQUE = "Helvetica-Oblique";
    public static final String SYMBOL = "Symbol";
    public static final String TIMES_BOLD = "Times-Bold";
    public static final String TIMES_BOLDITALIC = "Times-BoldItalic";
    public static final String TIMES_ITALIC = "Times-Italic";
    public static final String TIMES_ROMAN = "Times-Roman";
    public static final String ZAPFDINGBATS = "ZapfDingbats";

    private StandardFonts() {
    }

    static {
        HashSet hashSet = new HashSet();
        BUILTIN_FONTS = hashSet;
        hashSet.add("Courier");
        hashSet.add("Courier-Bold");
        hashSet.add("Courier-BoldOblique");
        hashSet.add("Courier-Oblique");
        hashSet.add("Helvetica");
        hashSet.add("Helvetica-Bold");
        hashSet.add("Helvetica-BoldOblique");
        hashSet.add("Helvetica-Oblique");
        hashSet.add("Symbol");
        hashSet.add("Times-Roman");
        hashSet.add("Times-Bold");
        hashSet.add("Times-BoldItalic");
        hashSet.add("Times-Italic");
        hashSet.add("ZapfDingbats");
    }

    public static boolean isStandardFont(String fontName) {
        return BUILTIN_FONTS.contains(fontName);
    }
}
