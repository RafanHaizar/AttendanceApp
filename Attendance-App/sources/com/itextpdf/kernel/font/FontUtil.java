package com.itextpdf.kernel.font;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.font.FontCache;
import com.itextpdf.p026io.font.PdfEncodings;
import com.itextpdf.p026io.font.cmap.CMapLocationFromBytes;
import com.itextpdf.p026io.font.cmap.CMapParser;
import com.itextpdf.p026io.font.cmap.CMapToUnicode;
import com.itextpdf.p026io.font.cmap.CMapUniCid;
import com.itextpdf.p026io.font.cmap.ICMapLocation;
import com.itextpdf.p026io.util.IntHashtable;
import java.util.HashMap;
import org.slf4j.LoggerFactory;

class FontUtil {
    private static final HashMap<String, CMapToUnicode> uniMaps = new HashMap<>();

    FontUtil() {
    }

    static CMapToUnicode processToUnicode(PdfObject toUnicode) {
        if (toUnicode instanceof PdfStream) {
            try {
                ICMapLocation lb = new CMapLocationFromBytes(((PdfStream) toUnicode).getBytes());
                CMapToUnicode cMapToUnicode = new CMapToUnicode();
                CMapParser.parseCid("", cMapToUnicode, lb);
                return cMapToUnicode;
            } catch (Exception e) {
                LoggerFactory.getLogger((Class<?>) CMapToUnicode.class).error(LogMessageConstant.UNKNOWN_ERROR_WHILE_PROCESSING_CMAP);
                return CMapToUnicode.EmptyCMapToUnicodeMap;
            }
        } else if (PdfName.IdentityH.equals(toUnicode)) {
            return CMapToUnicode.getIdentity();
        } else {
            return null;
        }
    }

    static CMapToUnicode getToUnicodeFromUniMap(String uniMap) {
        CMapToUnicode toUnicode;
        if (uniMap == null) {
            return null;
        }
        HashMap<String, CMapToUnicode> hashMap = uniMaps;
        synchronized (hashMap) {
            if (hashMap.containsKey(uniMap)) {
                CMapToUnicode cMapToUnicode = hashMap.get(uniMap);
                return cMapToUnicode;
            }
            if (PdfEncodings.IDENTITY_H.equals(uniMap)) {
                toUnicode = CMapToUnicode.getIdentity();
            } else {
                CMapUniCid uni = FontCache.getUni2CidCmap(uniMap);
                if (uni == null) {
                    return null;
                }
                toUnicode = uni.exportToUnicode();
            }
            hashMap.put(uniMap, toUnicode);
            return toUnicode;
        }
    }

    static String createRandomFontName() {
        StringBuilder s = new StringBuilder("");
        for (int k = 0; k < 7; k++) {
            s.append((char) ((int) ((Math.random() * 26.0d) + 65.0d)));
        }
        return s.toString();
    }

    static int[] convertSimpleWidthsArray(PdfArray widthsArray, int first, int missingWidth) {
        int[] res = new int[256];
        for (int i = 0; i < res.length; i++) {
            res[i] = missingWidth;
        }
        if (widthsArray == null) {
            LoggerFactory.getLogger((Class<?>) FontUtil.class).warn(LogMessageConstant.FONT_DICTIONARY_WITH_NO_WIDTHS);
            return res;
        }
        int i2 = 0;
        while (i2 < widthsArray.size() && first + i2 < 256) {
            PdfNumber number = widthsArray.getAsNumber(i2);
            res[first + i2] = number != null ? number.intValue() : missingWidth;
            i2++;
        }
        return res;
    }

    static IntHashtable convertCompositeWidthsArray(PdfArray widthsArray) {
        IntHashtable res = new IntHashtable();
        if (widthsArray == null) {
            return res;
        }
        int k = 0;
        while (k < widthsArray.size()) {
            int c1 = widthsArray.getAsNumber(k).intValue();
            int k2 = k + 1;
            PdfObject obj = widthsArray.get(k2);
            if (obj.isArray()) {
                PdfArray subWidths = (PdfArray) obj;
                int j = 0;
                while (j < subWidths.size()) {
                    res.put(c1, subWidths.getAsNumber(j).intValue());
                    j++;
                    c1++;
                }
            } else {
                int c2 = ((PdfNumber) obj).intValue();
                k2++;
                int w = widthsArray.getAsNumber(k2).intValue();
                while (c1 <= c2) {
                    res.put(c1, w);
                    c1++;
                }
            }
            k = k2 + 1;
        }
        return res;
    }
}
