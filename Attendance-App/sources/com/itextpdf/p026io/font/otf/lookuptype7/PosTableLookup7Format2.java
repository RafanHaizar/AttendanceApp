package com.itextpdf.p026io.font.otf.lookuptype7;

import com.itextpdf.p026io.font.otf.ContextualPositionRule;
import com.itextpdf.p026io.font.otf.ContextualPositionTable;
import com.itextpdf.p026io.font.otf.OpenTypeFontTableReader;
import com.itextpdf.p026io.font.otf.OtfClass;
import com.itextpdf.p026io.font.otf.PosLookupRecord;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/* renamed from: com.itextpdf.io.font.otf.lookuptype7.PosTableLookup7Format2 */
public class PosTableLookup7Format2 extends ContextualPositionTable {
    private static final long serialVersionUID = 2542153457480614040L;
    /* access modifiers changed from: private */
    public OtfClass classDefinition;
    private Set<Integer> posCoverageGlyphIds;
    private List<List<ContextualPositionRule>> subClassSets;

    public PosTableLookup7Format2(OpenTypeFontTableReader openReader, int lookupFlag, Set<Integer> posCoverageGlyphIds2, OtfClass classDefinition2) {
        super(openReader, lookupFlag);
        this.posCoverageGlyphIds = posCoverageGlyphIds2;
        this.classDefinition = classDefinition2;
    }

    public void setPosClassSets(List<List<ContextualPositionRule>> subClassSets2) {
        this.subClassSets = subClassSets2;
    }

    /* access modifiers changed from: protected */
    public List<ContextualPositionRule> getSetOfRulesForStartGlyph(int startId) {
        if (!this.posCoverageGlyphIds.contains(Integer.valueOf(startId)) || this.openReader.isSkip(startId, this.lookupFlag)) {
            return Collections.emptyList();
        }
        return this.subClassSets.get(this.classDefinition.getOtfClass(startId));
    }

    /* renamed from: com.itextpdf.io.font.otf.lookuptype7.PosTableLookup7Format2$PosRuleFormat2 */
    public static class PosRuleFormat2 extends ContextualPositionRule {
        private static final long serialVersionUID = 652574134066355802L;
        private OtfClass classDefinition;
        private int[] inputClassIds;
        private PosLookupRecord[] posLookupRecords;

        public PosRuleFormat2(PosTableLookup7Format2 subTable, int[] inputClassIds2, PosLookupRecord[] posLookupRecords2) {
            this.inputClassIds = inputClassIds2;
            this.posLookupRecords = posLookupRecords2;
            this.classDefinition = subTable.classDefinition;
        }

        public int getContextLength() {
            return this.inputClassIds.length + 1;
        }

        public PosLookupRecord[] getPosLookupRecords() {
            return this.posLookupRecords;
        }

        public boolean isGlyphMatchesInput(int glyphId, int atIdx) {
            return this.classDefinition.getOtfClass(glyphId) == this.inputClassIds[atIdx + -1];
        }
    }
}
