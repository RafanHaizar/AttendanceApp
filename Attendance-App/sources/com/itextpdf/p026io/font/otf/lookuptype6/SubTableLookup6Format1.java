package com.itextpdf.p026io.font.otf.lookuptype6;

import com.itextpdf.p026io.font.otf.ContextualSubstRule;
import com.itextpdf.p026io.font.otf.OpenTypeFontTableReader;
import com.itextpdf.p026io.font.otf.SubstLookupRecord;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* renamed from: com.itextpdf.io.font.otf.lookuptype6.SubTableLookup6Format1 */
public class SubTableLookup6Format1 extends SubTableLookup6 {
    private static final long serialVersionUID = 4252117327329368679L;
    private Map<Integer, List<ContextualSubstRule>> substMap;

    public SubTableLookup6Format1(OpenTypeFontTableReader openReader, int lookupFlag, Map<Integer, List<ContextualSubstRule>> substMap2) {
        super(openReader, lookupFlag);
        this.substMap = substMap2;
    }

    /* access modifiers changed from: protected */
    public List<ContextualSubstRule> getSetOfRulesForStartGlyph(int startGlyphId) {
        if (!this.substMap.containsKey(Integer.valueOf(startGlyphId)) || this.openReader.isSkip(startGlyphId, this.lookupFlag)) {
            return Collections.emptyList();
        }
        return this.substMap.get(Integer.valueOf(startGlyphId));
    }

    /* renamed from: com.itextpdf.io.font.otf.lookuptype6.SubTableLookup6Format1$SubstRuleFormat1 */
    public static class SubstRuleFormat1 extends ContextualSubstRule {
        private static final long serialVersionUID = 6962160437871819250L;
        private int[] backtrackGlyphIds;
        private int[] inputGlyphIds;
        private int[] lookAheadGlyphIds;
        private SubstLookupRecord[] substLookupRecords;

        public SubstRuleFormat1(int[] backtrackGlyphIds2, int[] inputGlyphIds2, int[] lookAheadGlyphIds2, SubstLookupRecord[] substLookupRecords2) {
            this.backtrackGlyphIds = backtrackGlyphIds2;
            this.inputGlyphIds = inputGlyphIds2;
            this.lookAheadGlyphIds = lookAheadGlyphIds2;
            this.substLookupRecords = substLookupRecords2;
        }

        public int getContextLength() {
            return this.inputGlyphIds.length + 1;
        }

        public int getLookaheadContextLength() {
            return this.lookAheadGlyphIds.length;
        }

        public int getBacktrackContextLength() {
            return this.backtrackGlyphIds.length;
        }

        public SubstLookupRecord[] getSubstLookupRecords() {
            return this.substLookupRecords;
        }

        public boolean isGlyphMatchesInput(int glyphId, int atIdx) {
            return glyphId == this.inputGlyphIds[atIdx + -1];
        }

        public boolean isGlyphMatchesLookahead(int glyphId, int atIdx) {
            return glyphId == this.lookAheadGlyphIds[atIdx];
        }

        public boolean isGlyphMatchesBacktrack(int glyphId, int atIdx) {
            return glyphId == this.backtrackGlyphIds[atIdx];
        }
    }
}
