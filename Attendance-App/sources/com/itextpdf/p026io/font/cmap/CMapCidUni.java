package com.itextpdf.p026io.font.cmap;

import com.itextpdf.p026io.util.IntHashtable;
import com.itextpdf.p026io.util.TextUtil;

/* renamed from: com.itextpdf.io.font.cmap.CMapCidUni */
public class CMapCidUni extends AbstractCMap {
    private static final long serialVersionUID = 6879167385978230141L;
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
            this.map.put(((Integer) code.getValue()).intValue(), codePoint);
        }
    }

    public int lookup(int character) {
        return this.map.get(character);
    }

    public int[] getCids() {
        return this.map.getKeys();
    }
}
