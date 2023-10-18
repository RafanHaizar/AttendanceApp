package com.itextpdf.layout.font;

import java.util.ArrayList;
import java.util.List;

final class FontSelectorKey {

    /* renamed from: fc */
    private FontCharacteristics f1522fc;
    private List<String> fontFamilies;

    FontSelectorKey(List<String> fontFamilies2, FontCharacteristics fc) {
        this.fontFamilies = new ArrayList(fontFamilies2);
        this.f1522fc = fc;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FontSelectorKey that = (FontSelectorKey) o;
        if (this.fontFamilies.equals(that.fontFamilies)) {
            FontCharacteristics fontCharacteristics = this.f1522fc;
            if (fontCharacteristics != null) {
                if (fontCharacteristics.equals(that.f1522fc)) {
                    return true;
                }
            } else if (that.f1522fc == null) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        List<String> list = this.fontFamilies;
        int i = 0;
        int hashCode = (list != null ? list.hashCode() : 0) * 31;
        FontCharacteristics fontCharacteristics = this.f1522fc;
        if (fontCharacteristics != null) {
            i = fontCharacteristics.hashCode();
        }
        return hashCode + i;
    }
}
