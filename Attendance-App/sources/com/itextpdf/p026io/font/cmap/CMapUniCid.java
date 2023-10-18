package com.itextpdf.p026io.font.cmap;

import com.itextpdf.p026io.util.IntHashtable;
import com.itextpdf.p026io.util.TextUtil;

/* renamed from: com.itextpdf.io.font.cmap.CMapUniCid */
public class CMapUniCid extends AbstractCMap {
    private static final long serialVersionUID = -6111821751136011584L;
    private IntHashtable map = new IntHashtable(65537);

    /* access modifiers changed from: package-private */
    public void addChar(String mark, CMapObject code) {
        int codePoint;
        if (code.isNumber()) {
            String s = toUnicodeString(mark, true);
            if (TextUtil.isSurrogatePair(s, 0)) {
                codePoint = TextUtil.convertToUtf32(s, 0);
            } else {
                codePoint = s.charAt(0);
            }
            this.map.put(codePoint, ((Integer) code.getValue()).intValue());
        }
    }

    public int lookup(int character) {
        return this.map.get(character);
    }

    public CMapToUnicode exportToUnicode() {
        CMapToUnicode uni = new CMapToUnicode();
        for (int key : this.map.toOrderedKeys()) {
            uni.addChar(this.map.get(key), TextUtil.convertFromUtf32(key));
        }
        int spaceCid = lookup(32);
        if (spaceCid != 0) {
            uni.addChar(spaceCid, TextUtil.convertFromUtf32(32));
        }
        return uni;
    }
}
