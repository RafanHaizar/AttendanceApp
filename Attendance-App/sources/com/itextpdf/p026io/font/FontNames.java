package com.itextpdf.p026io.font;

import com.itextpdf.p026io.font.constants.FontStretches;
import com.itextpdf.p026io.font.constants.FontWeights;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/* renamed from: com.itextpdf.io.font.FontNames */
public class FontNames implements Serializable {
    private static final long serialVersionUID = 1005168842463622025L;
    protected Map<Integer, List<String[]>> allNames;
    private boolean allowEmbedding;
    private String cidFontName;
    private String[][] familyName;
    private String fontName;
    private String fontStretch = FontStretches.NORMAL;
    private String[][] fullName;
    private int macStyle;
    private String style = "";
    private String[][] subfamily;
    private int weight = FontWeights.NORMAL;

    public String[][] getNames(int id) {
        List<String[]> names = this.allNames.get(Integer.valueOf(id));
        if (names != null && names.size() > 0) {
            return listToArray(names);
        }
        String[][] strArr = null;
        return null;
    }

    public String[][] getFullName() {
        return this.fullName;
    }

    public String getFontName() {
        return this.fontName;
    }

    public String getCidFontName() {
        return this.cidFontName;
    }

    public String[][] getFamilyName() {
        return this.familyName;
    }

    public String getStyle() {
        return this.style;
    }

    public String getSubfamily() {
        String[][] strArr = this.subfamily;
        return strArr != null ? strArr[0][3] : "";
    }

    public int getFontWeight() {
        return this.weight;
    }

    /* access modifiers changed from: protected */
    public void setFontWeight(int weight2) {
        this.weight = FontWeights.normalizeFontWeight(weight2);
    }

    public String getFontStretch() {
        return this.fontStretch;
    }

    /* access modifiers changed from: protected */
    public void setFontStretch(String fontStretch2) {
        this.fontStretch = fontStretch2;
    }

    public boolean allowEmbedding() {
        return this.allowEmbedding;
    }

    public boolean isBold() {
        return (this.macStyle & 1) != 0;
    }

    public boolean isItalic() {
        return (this.macStyle & 2) != 0;
    }

    public boolean isUnderline() {
        return (this.macStyle & 4) != 0;
    }

    public boolean isOutline() {
        return (this.macStyle & 8) != 0;
    }

    public boolean isShadow() {
        return (this.macStyle & 16) != 0;
    }

    public boolean isCondensed() {
        return (this.macStyle & 32) != 0;
    }

    public boolean isExtended() {
        return (this.macStyle & 64) != 0;
    }

    /* access modifiers changed from: protected */
    public void setAllNames(Map<Integer, List<String[]>> allNames2) {
        this.allNames = allNames2;
    }

    /* access modifiers changed from: protected */
    public void setFullName(String[][] fullName2) {
        this.fullName = fullName2;
    }

    /* access modifiers changed from: protected */
    public void setFullName(String fullName2) {
        this.fullName = new String[][]{new String[]{"", "", "", fullName2}};
    }

    /* access modifiers changed from: protected */
    public void setFontName(String psFontName) {
        this.fontName = psFontName;
    }

    /* access modifiers changed from: protected */
    public void setCidFontName(String cidFontName2) {
        this.cidFontName = cidFontName2;
    }

    /* access modifiers changed from: protected */
    public void setFamilyName(String[][] familyName2) {
        this.familyName = familyName2;
    }

    /* access modifiers changed from: protected */
    public void setFamilyName(String familyName2) {
        this.familyName = new String[][]{new String[]{"", "", "", familyName2}};
    }

    /* access modifiers changed from: protected */
    public void setStyle(String style2) {
        this.style = style2;
    }

    /* access modifiers changed from: protected */
    public void setSubfamily(String subfamily2) {
        this.subfamily = new String[][]{new String[]{"", "", "", subfamily2}};
    }

    /* access modifiers changed from: protected */
    public void setSubfamily(String[][] subfamily2) {
        this.subfamily = subfamily2;
    }

    /* access modifiers changed from: protected */
    public void setMacStyle(int macStyle2) {
        this.macStyle = macStyle2;
    }

    /* access modifiers changed from: protected */
    public int getMacStyle() {
        return this.macStyle;
    }

    /* access modifiers changed from: protected */
    public void setAllowEmbedding(boolean allowEmbedding2) {
        this.allowEmbedding = allowEmbedding2;
    }

    private String[][] listToArray(List<String[]> list) {
        String[][] array = new String[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    public String toString() {
        String name = getFontName();
        return name.length() > 0 ? name : super.toString();
    }
}
