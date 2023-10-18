package com.itextpdf.p026io.font.otf;

import com.itextpdf.p026io.font.otf.ContextualRule;
import com.itextpdf.p026io.font.otf.OpenTableLookup;
import java.io.Serializable;
import java.util.List;

/* renamed from: com.itextpdf.io.font.otf.ContextualTable */
public abstract class ContextualTable<T extends ContextualRule> implements Serializable {
    private static final long serialVersionUID = 6482616632143439036L;
    protected int lookupFlag;
    protected OpenTypeFontTableReader openReader;

    /* access modifiers changed from: protected */
    public abstract List<T> getSetOfRulesForStartGlyph(int i);

    protected ContextualTable(OpenTypeFontTableReader openReader2, int lookupFlag2) {
        this.openReader = openReader2;
        this.lookupFlag = lookupFlag2;
    }

    public T getMatchingContextRule(GlyphLine line) {
        if (line.idx >= line.end) {
            return null;
        }
        for (T rule : getSetOfRulesForStartGlyph(line.get(line.idx).getCode())) {
            int lastGlyphIndex = checkIfContextMatch(line, rule);
            if (lastGlyphIndex != -1) {
                line.start = line.idx;
                line.end = lastGlyphIndex + 1;
                return rule;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public int checkIfContextMatch(GlyphLine line, T rule) {
        OpenTableLookup.GlyphIndexer gidx = new OpenTableLookup.GlyphIndexer();
        gidx.line = line;
        gidx.idx = line.idx;
        int j = 1;
        while (j < rule.getContextLength()) {
            gidx.nextGlyph(this.openReader, this.lookupFlag);
            if (gidx.glyph == null || !rule.isGlyphMatchesInput(gidx.glyph.getCode(), j)) {
                break;
            }
            j++;
        }
        if (j == rule.getContextLength()) {
            return gidx.idx;
        }
        return -1;
    }
}
