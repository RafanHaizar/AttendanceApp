package com.itextpdf.p026io.font;

import com.itextpdf.p026io.IOException;
import com.itextpdf.p026io.font.cmap.CMapCidUni;
import com.itextpdf.p026io.font.otf.Glyph;
import com.itextpdf.p026io.util.IntHashtable;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;

/* renamed from: com.itextpdf.io.font.CidFont */
public class CidFont extends FontProgram {
    private static final long serialVersionUID = 5444988003799502179L;
    private Set<String> compatibleCmaps;
    private String fontName;
    private int pdfFontFlags;

    CidFont(String fontName2, Set<String> cmaps) {
        this.fontName = fontName2;
        this.compatibleCmaps = cmaps;
        this.fontNames = new FontNames();
        initializeCidFontNameAndStyle(fontName2);
        Map<String, Object> fontDesc = CidFontProperties.getAllFonts().get(this.fontNames.getFontName());
        if (fontDesc != null) {
            initializeCidFontProperties(fontDesc);
            return;
        }
        throw new IOException("There is no such predefined font: {0}").setMessageParams(fontName2);
    }

    CidFont(String fontName2, Set<String> cmaps, Map<String, Object> fontDescription) {
        initializeCidFontNameAndStyle(fontName2);
        initializeCidFontProperties(fontDescription);
        this.compatibleCmaps = cmaps;
    }

    public boolean compatibleWith(String cmap) {
        if (cmap.equals(PdfEncodings.IDENTITY_H) || cmap.equals(PdfEncodings.IDENTITY_V)) {
            return true;
        }
        Set<String> set = this.compatibleCmaps;
        if (set == null || !set.contains(cmap)) {
            return false;
        }
        return true;
    }

    public int getKerning(Glyph glyph1, Glyph glyph2) {
        return 0;
    }

    public int getPdfFontFlags() {
        return this.pdfFontFlags;
    }

    public boolean isFontSpecific() {
        return false;
    }

    public boolean isBuiltWith(String fontName2) {
        return Objects.equals(this.fontName, fontName2);
    }

    private void initializeCidFontNameAndStyle(String fontName2) {
        String nameBase = trimFontStyle(fontName2);
        if (nameBase.length() < fontName2.length()) {
            this.fontNames.setFontName(fontName2);
            this.fontNames.setStyle(fontName2.substring(nameBase.length()));
        } else {
            this.fontNames.setFontName(fontName2);
        }
        this.fontNames.setFullName(new String[][]{new String[]{"", "", "", this.fontNames.getFontName()}});
    }

    private void initializeCidFontProperties(Map<String, Object> fontDesc) {
        Map<String, Object> map = fontDesc;
        this.fontIdentification.setPanose((String) map.get("Panose"));
        this.fontMetrics.setItalicAngle((float) Integer.parseInt((String) map.get("ItalicAngle")));
        this.fontMetrics.setCapHeight(Integer.parseInt((String) map.get("CapHeight")));
        this.fontMetrics.setTypoAscender(Integer.parseInt((String) map.get("Ascent")));
        this.fontMetrics.setTypoDescender(Integer.parseInt((String) map.get("Descent")));
        this.fontMetrics.setStemV(Integer.parseInt((String) map.get("StemV")));
        this.pdfFontFlags = Integer.parseInt((String) map.get("Flags"));
        String fontBBox = (String) map.get("FontBBox");
        StringTokenizer tk = new StringTokenizer(fontBBox, " []\r\n\t\f");
        this.fontMetrics.updateBbox((float) Integer.parseInt(tk.nextToken()), (float) Integer.parseInt(tk.nextToken()), (float) Integer.parseInt(tk.nextToken()), (float) Integer.parseInt(tk.nextToken()));
        this.registry = (String) map.get("Registry");
        String uniMap = getCompatibleUniMap(this.registry);
        if (uniMap != null) {
            IntHashtable metrics = (IntHashtable) map.get("W");
            CMapCidUni cid2Uni = FontCache.getCid2UniCmap(uniMap);
            int i = 0;
            this.avgWidth = 0;
            int[] cids = cid2Uni.getCids();
            int length = cids.length;
            while (i < length) {
                int cid = cids[i];
                int uni = cid2Uni.lookup(cid);
                Glyph glyph = new Glyph(cid, metrics.containsKey(cid) ? metrics.get(cid) : 1000, uni);
                this.avgWidth += glyph.getWidth();
                this.codeToGlyph.put(Integer.valueOf(cid), glyph);
                this.unicodeToGlyph.put(Integer.valueOf(uni), glyph);
                i++;
                Map<String, Object> map2 = fontDesc;
                fontBBox = fontBBox;
                tk = tk;
            }
            StringTokenizer stringTokenizer = tk;
            fixSpaceIssue();
            if (this.codeToGlyph.size() != 0) {
                this.avgWidth /= this.codeToGlyph.size();
                return;
            }
            return;
        }
        StringTokenizer stringTokenizer2 = tk;
    }

    private static String getCompatibleUniMap(String registry) {
        String uniMap = "";
        for (String name : CidFontProperties.getRegistryNames().get(registry + "_Uni")) {
            uniMap = name;
            if (name.endsWith("H")) {
                break;
            }
        }
        return uniMap;
    }
}
