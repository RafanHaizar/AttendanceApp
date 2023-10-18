package com.itextpdf.p026io.font.otf.lookuptype5;

import com.itextpdf.p026io.font.otf.ContextualSubTable;
import com.itextpdf.p026io.font.otf.ContextualSubstRule;
import com.itextpdf.p026io.font.otf.OpenTypeFontTableReader;
import com.itextpdf.p026io.font.otf.SubstLookupRecord;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/* renamed from: com.itextpdf.io.font.otf.lookuptype5.SubTableLookup5Format3 */
public class SubTableLookup5Format3 extends ContextualSubTable {
    private static final long serialVersionUID = -9142690964201749548L;
    ContextualSubstRule substitutionRule;

    public SubTableLookup5Format3(OpenTypeFontTableReader openReader, int lookupFlag, SubstRuleFormat3 rule) {
        super(openReader, lookupFlag);
        this.substitutionRule = rule;
    }

    /* access modifiers changed from: protected */
    public List<ContextualSubstRule> getSetOfRulesForStartGlyph(int startId) {
        if (!((SubstRuleFormat3) this.substitutionRule).coverages.get(0).contains(Integer.valueOf(startId)) || this.openReader.isSkip(startId, this.lookupFlag)) {
            return Collections.emptyList();
        }
        return Collections.singletonList(this.substitutionRule);
    }

    /* renamed from: com.itextpdf.io.font.otf.lookuptype5.SubTableLookup5Format3$SubstRuleFormat3 */
    public static class SubstRuleFormat3 extends ContextualSubstRule {
        private static final long serialVersionUID = -1840126702536353850L;
        List<Set<Integer>> coverages;
        SubstLookupRecord[] substLookupRecords;

        public SubstRuleFormat3(List<Set<Integer>> coverages2, SubstLookupRecord[] substLookupRecords2) {
            this.coverages = coverages2;
            this.substLookupRecords = substLookupRecords2;
        }

        public int getContextLength() {
            return this.coverages.size();
        }

        public SubstLookupRecord[] getSubstLookupRecords() {
            return this.substLookupRecords;
        }

        public boolean isGlyphMatchesInput(int glyphId, int atIdx) {
            return this.coverages.get(atIdx).contains(Integer.valueOf(glyphId));
        }
    }
}
