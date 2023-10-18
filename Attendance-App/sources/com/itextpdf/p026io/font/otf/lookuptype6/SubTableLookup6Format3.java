package com.itextpdf.p026io.font.otf.lookuptype6;

import com.itextpdf.p026io.font.otf.ContextualSubstRule;
import com.itextpdf.p026io.font.otf.OpenTypeFontTableReader;
import com.itextpdf.p026io.font.otf.SubstLookupRecord;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/* renamed from: com.itextpdf.io.font.otf.lookuptype6.SubTableLookup6Format3 */
public class SubTableLookup6Format3 extends SubTableLookup6 {
    private static final long serialVersionUID = 764166925472080146L;
    ContextualSubstRule substitutionRule;

    public SubTableLookup6Format3(OpenTypeFontTableReader openReader, int lookupFlag, SubstRuleFormat3 rule) {
        super(openReader, lookupFlag);
        this.substitutionRule = rule;
    }

    /* access modifiers changed from: protected */
    public List<ContextualSubstRule> getSetOfRulesForStartGlyph(int startId) {
        if (!((SubstRuleFormat3) this.substitutionRule).inputCoverages.get(0).contains(Integer.valueOf(startId)) || this.openReader.isSkip(startId, this.lookupFlag)) {
            return Collections.emptyList();
        }
        return Collections.singletonList(this.substitutionRule);
    }

    /* renamed from: com.itextpdf.io.font.otf.lookuptype6.SubTableLookup6Format3$SubstRuleFormat3 */
    public static class SubstRuleFormat3 extends ContextualSubstRule {
        private static final long serialVersionUID = -8817891790304481782L;
        List<Set<Integer>> backtrackCoverages;
        List<Set<Integer>> inputCoverages;
        List<Set<Integer>> lookaheadCoverages;
        SubstLookupRecord[] substLookupRecords;

        public SubstRuleFormat3(List<Set<Integer>> backtrackCoverages2, List<Set<Integer>> inputCoverages2, List<Set<Integer>> lookaheadCoverages2, SubstLookupRecord[] substLookupRecords2) {
            this.backtrackCoverages = backtrackCoverages2;
            this.inputCoverages = inputCoverages2;
            this.lookaheadCoverages = lookaheadCoverages2;
            this.substLookupRecords = substLookupRecords2;
        }

        public int getContextLength() {
            return this.inputCoverages.size();
        }

        public int getLookaheadContextLength() {
            return this.lookaheadCoverages.size();
        }

        public int getBacktrackContextLength() {
            return this.backtrackCoverages.size();
        }

        public SubstLookupRecord[] getSubstLookupRecords() {
            return this.substLookupRecords;
        }

        public boolean isGlyphMatchesInput(int glyphId, int atIdx) {
            return this.inputCoverages.get(atIdx).contains(Integer.valueOf(glyphId));
        }

        public boolean isGlyphMatchesLookahead(int glyphId, int atIdx) {
            return this.lookaheadCoverages.get(atIdx).contains(Integer.valueOf(glyphId));
        }

        public boolean isGlyphMatchesBacktrack(int glyphId, int atIdx) {
            return this.backtrackCoverages.get(atIdx).contains(Integer.valueOf(glyphId));
        }
    }
}
