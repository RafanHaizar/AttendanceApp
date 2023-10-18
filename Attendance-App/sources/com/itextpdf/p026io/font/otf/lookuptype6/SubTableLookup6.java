package com.itextpdf.p026io.font.otf.lookuptype6;

import com.itextpdf.p026io.font.otf.ContextualSubTable;
import com.itextpdf.p026io.font.otf.ContextualSubstRule;
import com.itextpdf.p026io.font.otf.GlyphLine;
import com.itextpdf.p026io.font.otf.OpenTableLookup;
import com.itextpdf.p026io.font.otf.OpenTypeFontTableReader;

/* renamed from: com.itextpdf.io.font.otf.lookuptype6.SubTableLookup6 */
public abstract class SubTableLookup6 extends ContextualSubTable {
    private static final long serialVersionUID = -7471613803606544198L;

    protected SubTableLookup6(OpenTypeFontTableReader openReader, int lookupFlag) {
        super(openReader, lookupFlag);
    }

    public ContextualSubstRule getMatchingContextRule(GlyphLine line) {
        if (line.idx >= line.end) {
            return null;
        }
        for (ContextualSubstRule rule : getSetOfRulesForStartGlyph(line.get(line.idx).getCode())) {
            int lastGlyphIndex = checkIfContextMatch(line, rule);
            if (lastGlyphIndex != -1 && checkIfLookaheadContextMatch(line, rule, lastGlyphIndex) && checkIfBacktrackContextMatch(line, rule)) {
                line.start = line.idx;
                line.end = lastGlyphIndex + 1;
                return rule;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public boolean checkIfLookaheadContextMatch(GlyphLine line, ContextualSubstRule rule, int startIdx) {
        OpenTableLookup.GlyphIndexer gidx = new OpenTableLookup.GlyphIndexer();
        gidx.line = line;
        gidx.idx = startIdx;
        int j = 0;
        while (j < rule.getLookaheadContextLength()) {
            gidx.nextGlyph(this.openReader, this.lookupFlag);
            if (gidx.glyph == null || !rule.isGlyphMatchesLookahead(gidx.glyph.getCode(), j)) {
                break;
            }
            j++;
        }
        return j == rule.getLookaheadContextLength();
    }

    /* access modifiers changed from: protected */
    public boolean checkIfBacktrackContextMatch(GlyphLine line, ContextualSubstRule rule) {
        OpenTableLookup.GlyphIndexer gidx = new OpenTableLookup.GlyphIndexer();
        gidx.line = line;
        gidx.idx = line.idx;
        int j = 0;
        while (j < rule.getBacktrackContextLength()) {
            gidx.previousGlyph(this.openReader, this.lookupFlag);
            if (gidx.glyph == null || !rule.isGlyphMatchesBacktrack(gidx.glyph.getCode(), j)) {
                break;
            }
            j++;
        }
        return j == rule.getBacktrackContextLength();
    }
}
