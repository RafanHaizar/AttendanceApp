package com.itextpdf.p026io.font.otf.lookuptype5;

import com.itextpdf.p026io.font.otf.ContextualSubTable;
import com.itextpdf.p026io.font.otf.ContextualSubstRule;
import com.itextpdf.p026io.font.otf.OpenTypeFontTableReader;
import com.itextpdf.p026io.font.otf.SubstLookupRecord;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* renamed from: com.itextpdf.io.font.otf.lookuptype5.SubTableLookup5Format1 */
public class SubTableLookup5Format1 extends ContextualSubTable {
    private static final long serialVersionUID = -6061489236592337747L;
    private Map<Integer, List<ContextualSubstRule>> substMap;

    public SubTableLookup5Format1(OpenTypeFontTableReader openReader, int lookupFlag, Map<Integer, List<ContextualSubstRule>> substMap2) {
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

    /* renamed from: com.itextpdf.io.font.otf.lookuptype5.SubTableLookup5Format1$SubstRuleFormat1 */
    public static class SubstRuleFormat1 extends ContextualSubstRule {
        private static final long serialVersionUID = -540799242670887211L;
        private int[] inputGlyphIds;
        private SubstLookupRecord[] substLookupRecords;

        public SubstRuleFormat1(int[] inputGlyphIds2, SubstLookupRecord[] substLookupRecords2) {
            this.inputGlyphIds = inputGlyphIds2;
            this.substLookupRecords = substLookupRecords2;
        }

        public int getContextLength() {
            return this.inputGlyphIds.length + 1;
        }

        public SubstLookupRecord[] getSubstLookupRecords() {
            return this.substLookupRecords;
        }

        public boolean isGlyphMatchesInput(int glyphId, int atIdx) {
            return glyphId == this.inputGlyphIds[atIdx + -1];
        }
    }
}
