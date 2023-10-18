package com.itextpdf.p026io.font.constants;

/* renamed from: com.itextpdf.io.font.constants.FontStretches */
public final class FontStretches {
    public static final String CONDENSED = "Condensed";
    public static final String EXPANDED = "Expanded";
    public static final String EXTRA_CONDENSED = "ExtraCondensed";
    public static final String EXTRA_EXPANDED = "ExtraExpanded";
    private static final int FWIDTH_CONDENSED = 3;
    private static final int FWIDTH_EXPANDED = 7;
    private static final int FWIDTH_EXTRA_CONDENSED = 2;
    private static final int FWIDTH_EXTRA_EXPANDED = 8;
    private static final int FWIDTH_NORMAL = 5;
    private static final int FWIDTH_SEMI_CONDENSED = 4;
    private static final int FWIDTH_SEMI_EXPANDED = 6;
    private static final int FWIDTH_ULTRA_CONDENSED = 1;
    private static final int FWIDTH_ULTRA_EXPANDED = 9;
    public static final String NORMAL = "Normal";
    public static final String SEMI_CONDENSED = "SemiCondensed";
    public static final String SEMI_EXPANDED = "SemiExpanded";
    public static final String ULTRA_CONDENSED = "UltraCondensed";
    public static final String ULTRA_EXPANDED = "UltraExpanded";

    private FontStretches() {
    }

    public static String fromOpenTypeWidthClass(int fontWidth) {
        switch (fontWidth) {
            case 1:
                return ULTRA_CONDENSED;
            case 2:
                return EXTRA_CONDENSED;
            case 3:
                return CONDENSED;
            case 4:
                return SEMI_CONDENSED;
            case 5:
                return NORMAL;
            case 6:
                return SEMI_EXPANDED;
            case 7:
                return EXPANDED;
            case 8:
                return EXTRA_EXPANDED;
            case 9:
                return ULTRA_EXPANDED;
            default:
                return NORMAL;
        }
    }
}
