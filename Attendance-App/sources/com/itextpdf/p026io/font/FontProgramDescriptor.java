package com.itextpdf.p026io.font;

import java.util.HashSet;
import java.util.Set;

/* renamed from: com.itextpdf.io.font.FontProgramDescriptor */
public class FontProgramDescriptor {
    private static final String[] TT_FAMILY_ORDER = {"3", "1", "1033", "3", "0", "1033", "1", "0", "0", "0", "3", "0"};
    private final String familyNameEnglishOpenType;
    private final String familyNameLowerCase;
    private final String fontName;
    private final String fontNameLowerCase;
    private final String fullNameLowerCase;
    private final Set<String> fullNamesAllLangs;
    private final Set<String> fullNamesEnglishOpenType;
    private final boolean isMonospace;
    private final float italicAngle;
    private final int macStyle;
    private final String style;
    private final int weight;

    FontProgramDescriptor(FontNames fontNames, float italicAngle2, boolean isMonospace2) {
        String fontName2 = fontNames.getFontName();
        this.fontName = fontName2;
        this.fontNameLowerCase = fontName2.toLowerCase();
        this.fullNameLowerCase = fontNames.getFullName()[0][3].toLowerCase();
        this.familyNameLowerCase = (fontNames.getFamilyName() == null || fontNames.getFamilyName()[0][3] == null) ? null : fontNames.getFamilyName()[0][3].toLowerCase();
        this.style = fontNames.getStyle();
        this.weight = fontNames.getFontWeight();
        this.macStyle = fontNames.getMacStyle();
        this.italicAngle = italicAngle2;
        this.isMonospace = isMonospace2;
        this.familyNameEnglishOpenType = extractFamilyNameEnglishOpenType(fontNames);
        this.fullNamesAllLangs = extractFullFontNames(fontNames);
        this.fullNamesEnglishOpenType = extractFullNamesEnglishOpenType(fontNames);
    }

    FontProgramDescriptor(FontNames fontNames, FontMetrics fontMetrics) {
        this(fontNames, fontMetrics.getItalicAngle(), fontMetrics.isFixedPitch());
    }

    public String getFontName() {
        return this.fontName;
    }

    public String getStyle() {
        return this.style;
    }

    public int getFontWeight() {
        return this.weight;
    }

    public float getItalicAngle() {
        return this.italicAngle;
    }

    public boolean isMonospace() {
        return this.isMonospace;
    }

    public boolean isBold() {
        return (this.macStyle & 1) != 0;
    }

    public boolean isItalic() {
        return (this.macStyle & 2) != 0;
    }

    public String getFullNameLowerCase() {
        return this.fullNameLowerCase;
    }

    public String getFontNameLowerCase() {
        return this.fontNameLowerCase;
    }

    public String getFamilyNameLowerCase() {
        return this.familyNameLowerCase;
    }

    public Set<String> getFullNameAllLangs() {
        return this.fullNamesAllLangs;
    }

    public Set<String> getFullNamesEnglishOpenType() {
        return this.fullNamesEnglishOpenType;
    }

    /* access modifiers changed from: package-private */
    public String getFamilyNameEnglishOpenType() {
        return this.familyNameEnglishOpenType;
    }

    private Set<String> extractFullFontNames(FontNames fontNames) {
        Set<String> uniqueFullNames = new HashSet<>();
        for (String[] fullName : fontNames.getFullName()) {
            uniqueFullNames.add(fullName[3].toLowerCase());
        }
        return uniqueFullNames;
    }

    private String extractFamilyNameEnglishOpenType(FontNames fontNames) {
        if (fontNames.getFamilyName() == null) {
            return null;
        }
        for (int k = 0; k < TT_FAMILY_ORDER.length; k += 3) {
            for (String[] name : fontNames.getFamilyName()) {
                String[] strArr = TT_FAMILY_ORDER;
                if (strArr[k].equals(name[0]) && strArr[k + 1].equals(name[1]) && strArr[k + 2].equals(name[2])) {
                    return name[3].toLowerCase();
                }
            }
        }
        return null;
    }

    private Set<String> extractFullNamesEnglishOpenType(FontNames fontNames) {
        if (this.familyNameEnglishOpenType == null) {
            return new HashSet();
        }
        Set<String> uniqueTtfSuitableFullNames = new HashSet<>();
        for (String[] name : fontNames.getFullName()) {
            int k = 0;
            while (true) {
                String[] strArr = TT_FAMILY_ORDER;
                if (k < strArr.length) {
                    if (strArr[k].equals(name[0]) && strArr[k + 1].equals(name[1]) && strArr[k + 2].equals(name[2])) {
                        uniqueTtfSuitableFullNames.add(name[3]);
                        break;
                    }
                    k += 3;
                } else {
                    break;
                }
            }
        }
        return uniqueTtfSuitableFullNames;
    }
}
