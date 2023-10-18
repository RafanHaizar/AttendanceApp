package com.itextpdf.p026io.font.otf.lookuptype6;

import com.itextpdf.p026io.font.otf.ContextualSubstRule;
import com.itextpdf.p026io.font.otf.OpenTypeFontTableReader;
import com.itextpdf.p026io.font.otf.OtfClass;
import com.itextpdf.p026io.font.otf.SubstLookupRecord;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/* renamed from: com.itextpdf.io.font.otf.lookuptype6.SubTableLookup6Format2 */
public class SubTableLookup6Format2 extends SubTableLookup6 {
    private static final long serialVersionUID = -4930056769443953242L;
    /* access modifiers changed from: private */
    public OtfClass backtrackClassDefinition;
    /* access modifiers changed from: private */
    public OtfClass inputClassDefinition;
    /* access modifiers changed from: private */
    public OtfClass lookaheadClassDefinition;
    private List<List<ContextualSubstRule>> subClassSets;
    private Set<Integer> substCoverageGlyphIds;

    public SubTableLookup6Format2(OpenTypeFontTableReader openReader, int lookupFlag, Set<Integer> substCoverageGlyphIds2, OtfClass backtrackClassDefinition2, OtfClass inputClassDefinition2, OtfClass lookaheadClassDefinition2) {
        super(openReader, lookupFlag);
        this.substCoverageGlyphIds = substCoverageGlyphIds2;
        this.backtrackClassDefinition = backtrackClassDefinition2;
        this.inputClassDefinition = inputClassDefinition2;
        this.lookaheadClassDefinition = lookaheadClassDefinition2;
    }

    public void setSubClassSets(List<List<ContextualSubstRule>> subClassSets2) {
        this.subClassSets = subClassSets2;
    }

    /* access modifiers changed from: protected */
    public List<ContextualSubstRule> getSetOfRulesForStartGlyph(int startId) {
        if (!this.substCoverageGlyphIds.contains(Integer.valueOf(startId)) || this.openReader.isSkip(startId, this.lookupFlag)) {
            return Collections.emptyList();
        }
        return this.subClassSets.get(this.inputClassDefinition.getOtfClass(startId));
    }

    /* renamed from: com.itextpdf.io.font.otf.lookuptype6.SubTableLookup6Format2$SubstRuleFormat2 */
    public static class SubstRuleFormat2 extends ContextualSubstRule {
        private static final long serialVersionUID = 5227942059467859541L;
        private int[] backtrackClassIds;
        private int[] inputClassIds;
        private int[] lookAheadClassIds;
        private SubTableLookup6Format2 subTable;
        private SubstLookupRecord[] substLookupRecords;

        public SubstRuleFormat2(SubTableLookup6Format2 subTable2, int[] backtrackClassIds2, int[] inputClassIds2, int[] lookAheadClassIds2, SubstLookupRecord[] substLookupRecords2) {
            this.subTable = subTable2;
            this.backtrackClassIds = backtrackClassIds2;
            this.inputClassIds = inputClassIds2;
            this.lookAheadClassIds = lookAheadClassIds2;
            this.substLookupRecords = substLookupRecords2;
        }

        public int getContextLength() {
            return this.inputClassIds.length + 1;
        }

        public int getLookaheadContextLength() {
            return this.lookAheadClassIds.length;
        }

        public int getBacktrackContextLength() {
            return this.backtrackClassIds.length;
        }

        public SubstLookupRecord[] getSubstLookupRecords() {
            return this.substLookupRecords;
        }

        public boolean isGlyphMatchesInput(int glyphId, int atIdx) {
            return this.subTable.inputClassDefinition.getOtfClass(glyphId) == this.inputClassIds[atIdx + -1];
        }

        public boolean isGlyphMatchesLookahead(int glyphId, int atIdx) {
            return this.subTable.lookaheadClassDefinition.getOtfClass(glyphId) == this.lookAheadClassIds[atIdx];
        }

        public boolean isGlyphMatchesBacktrack(int glyphId, int atIdx) {
            return this.subTable.backtrackClassDefinition.getOtfClass(glyphId) == this.backtrackClassIds[atIdx];
        }
    }
}
