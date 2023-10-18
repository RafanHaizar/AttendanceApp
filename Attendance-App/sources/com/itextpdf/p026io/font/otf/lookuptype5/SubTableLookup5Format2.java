package com.itextpdf.p026io.font.otf.lookuptype5;

import com.itextpdf.p026io.font.otf.ContextualSubTable;
import com.itextpdf.p026io.font.otf.ContextualSubstRule;
import com.itextpdf.p026io.font.otf.OpenTypeFontTableReader;
import com.itextpdf.p026io.font.otf.OtfClass;
import com.itextpdf.p026io.font.otf.SubstLookupRecord;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/* renamed from: com.itextpdf.io.font.otf.lookuptype5.SubTableLookup5Format2 */
public class SubTableLookup5Format2 extends ContextualSubTable {
    private static final long serialVersionUID = -2184080481143798249L;
    /* access modifiers changed from: private */
    public OtfClass classDefinition;
    private List<List<ContextualSubstRule>> subClassSets;
    private Set<Integer> substCoverageGlyphIds;

    public SubTableLookup5Format2(OpenTypeFontTableReader openReader, int lookupFlag, Set<Integer> substCoverageGlyphIds2, OtfClass classDefinition2) {
        super(openReader, lookupFlag);
        this.substCoverageGlyphIds = substCoverageGlyphIds2;
        this.classDefinition = classDefinition2;
    }

    public void setSubClassSets(List<List<ContextualSubstRule>> subClassSets2) {
        this.subClassSets = subClassSets2;
    }

    /* access modifiers changed from: protected */
    public List<ContextualSubstRule> getSetOfRulesForStartGlyph(int startId) {
        if (!this.substCoverageGlyphIds.contains(Integer.valueOf(startId)) || this.openReader.isSkip(startId, this.lookupFlag)) {
            return Collections.emptyList();
        }
        return this.subClassSets.get(this.classDefinition.getOtfClass(startId));
    }

    /* renamed from: com.itextpdf.io.font.otf.lookuptype5.SubTableLookup5Format2$SubstRuleFormat2 */
    public static class SubstRuleFormat2 extends ContextualSubstRule {
        private static final long serialVersionUID = 652574134066355802L;
        private OtfClass classDefinition;
        private int[] inputClassIds;
        private SubstLookupRecord[] substLookupRecords;

        public SubstRuleFormat2(SubTableLookup5Format2 subTable, int[] inputClassIds2, SubstLookupRecord[] substLookupRecords2) {
            this.inputClassIds = inputClassIds2;
            this.substLookupRecords = substLookupRecords2;
            this.classDefinition = subTable.classDefinition;
        }

        public int getContextLength() {
            return this.inputClassIds.length + 1;
        }

        public SubstLookupRecord[] getSubstLookupRecords() {
            return this.substLookupRecords;
        }

        public boolean isGlyphMatchesInput(int glyphId, int atIdx) {
            return this.classDefinition.getOtfClass(glyphId) == this.inputClassIds[atIdx + -1];
        }
    }
}
