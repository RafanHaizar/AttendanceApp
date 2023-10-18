package com.itextpdf.layout.font;

import java.util.HashMap;
import java.util.Map;

class FontSelectorCache {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Map<Long, FontSetSelectors> caches = new HashMap();
    private final FontSet defaultFontSet;
    private final FontSetSelectors defaultSelectors;

    FontSelectorCache(FontSet defaultFontSet2) {
        if (defaultFontSet2 != null) {
            FontSetSelectors fontSetSelectors = new FontSetSelectors();
            this.defaultSelectors = fontSetSelectors;
            fontSetSelectors.update(defaultFontSet2);
            this.defaultFontSet = defaultFontSet2;
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: package-private */
    public FontSelector get(FontSelectorKey key) {
        if (update((FontSetSelectors) null, (FontSet) null)) {
            return null;
        }
        return this.defaultSelectors.map.get(key);
    }

    /* access modifiers changed from: package-private */
    public FontSelector get(FontSelectorKey key, FontSet additionalFonts) {
        if (additionalFonts == null) {
            return get(key);
        }
        FontSetSelectors selectors = this.caches.get(Long.valueOf(additionalFonts.getId()));
        if (selectors == null) {
            Map<Long, FontSetSelectors> map = this.caches;
            Long valueOf = Long.valueOf(additionalFonts.getId());
            FontSetSelectors fontSetSelectors = new FontSetSelectors();
            selectors = fontSetSelectors;
            map.put(valueOf, fontSetSelectors);
        }
        if (update(selectors, additionalFonts)) {
            return null;
        }
        return selectors.map.get(key);
    }

    /* access modifiers changed from: package-private */
    public void put(FontSelectorKey key, FontSelector fontSelector) {
        update((FontSetSelectors) null, (FontSet) null);
        this.defaultSelectors.map.put(key, fontSelector);
    }

    /* access modifiers changed from: package-private */
    public void put(FontSelectorKey key, FontSelector fontSelector, FontSet fontSet) {
        if (fontSet == null) {
            put(key, fontSelector);
            return;
        }
        FontSetSelectors selectors = this.caches.get(Long.valueOf(fontSet.getId()));
        if (selectors == null) {
            Map<Long, FontSetSelectors> map = this.caches;
            Long valueOf = Long.valueOf(fontSet.getId());
            FontSetSelectors fontSetSelectors = new FontSetSelectors();
            selectors = fontSetSelectors;
            map.put(valueOf, fontSetSelectors);
        }
        update(selectors, fontSet);
        selectors.map.put(key, fontSelector);
    }

    private boolean update(FontSetSelectors selectors, FontSet fontSet) {
        boolean updated = false;
        if (this.defaultSelectors.update(this.defaultFontSet)) {
            updated = true;
        }
        if (selectors == null || !selectors.update(fontSet)) {
            return updated;
        }
        return true;
    }

    private static class FontSetSelectors {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private int fontSetSize;
        final Map<FontSelectorKey, FontSelector> map;

        static {
            Class<FontSelectorCache> cls = FontSelectorCache.class;
        }

        private FontSetSelectors() {
            this.map = new HashMap();
            this.fontSetSize = -1;
        }

        /* access modifiers changed from: package-private */
        public boolean update(FontSet fontSet) {
            if (fontSet == null) {
                throw new AssertionError();
            } else if (this.fontSetSize == fontSet.size()) {
                return false;
            } else {
                this.map.clear();
                this.fontSetSize = fontSet.size();
                return true;
            }
        }
    }
}
