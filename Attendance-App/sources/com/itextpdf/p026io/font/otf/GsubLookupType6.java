package com.itextpdf.p026io.font.otf;

import com.itextpdf.p026io.font.otf.lookuptype6.SubTableLookup6Format1;
import com.itextpdf.p026io.font.otf.lookuptype6.SubTableLookup6Format2;
import com.itextpdf.p026io.font.otf.lookuptype6.SubTableLookup6Format3;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* renamed from: com.itextpdf.io.font.otf.GsubLookupType6 */
public class GsubLookupType6 extends GsubLookupType5 {
    private static final long serialVersionUID = 6205375104387477124L;

    protected GsubLookupType6(OpenTypeFontTableReader openReader, int lookupFlag, int[] subTableLocations) throws IOException {
        super(openReader, lookupFlag, subTableLocations);
    }

    /* access modifiers changed from: protected */
    public void readSubTableFormat1(int subTableLocation) throws IOException {
        int i = subTableLocation;
        Map<Integer, List<ContextualSubstRule>> substMap = new HashMap<>();
        int coverageOffset = this.openReader.f1214rf.readUnsignedShort();
        int chainSubRuleSetCount = this.openReader.f1214rf.readUnsignedShort();
        int[] chainSubRuleSetOffsets = this.openReader.readUShortArray(chainSubRuleSetCount, i);
        List<Integer> coverageGlyphIds = this.openReader.readCoverageFormat(i + coverageOffset);
        int i2 = 0;
        while (i2 < chainSubRuleSetCount) {
            this.openReader.f1214rf.seek((long) chainSubRuleSetOffsets[i2]);
            int chainSubRuleCount = this.openReader.f1214rf.readUnsignedShort();
            int[] chainSubRuleOffsets = this.openReader.readUShortArray(chainSubRuleCount, chainSubRuleSetOffsets[i2]);
            List<ContextualSubstRule> chainSubRuleSet = new ArrayList<>(chainSubRuleCount);
            int j = 0;
            while (j < chainSubRuleCount) {
                this.openReader.f1214rf.seek((long) chainSubRuleOffsets[j]);
                int[] backtrackGlyphIds = this.openReader.readUShortArray(this.openReader.f1214rf.readUnsignedShort());
                int[] inputGlyphIds = this.openReader.readUShortArray(this.openReader.f1214rf.readUnsignedShort() - 1);
                int coverageOffset2 = coverageOffset;
                int[] lookAheadGlyphIds = this.openReader.readUShortArray(this.openReader.f1214rf.readUnsignedShort());
                int chainSubRuleSetCount2 = chainSubRuleSetCount;
                int substCount = this.openReader.f1214rf.readUnsignedShort();
                int i3 = substCount;
                chainSubRuleSet.add(new SubTableLookup6Format1.SubstRuleFormat1(backtrackGlyphIds, inputGlyphIds, lookAheadGlyphIds, this.openReader.readSubstLookupRecords(substCount)));
                j++;
                int i4 = subTableLocation;
                coverageOffset = coverageOffset2;
                chainSubRuleSetCount = chainSubRuleSetCount2;
                chainSubRuleSetOffsets = chainSubRuleSetOffsets;
            }
            int i5 = chainSubRuleSetCount;
            int[] iArr = chainSubRuleSetOffsets;
            substMap.put(coverageGlyphIds.get(i2), chainSubRuleSet);
            i2++;
            int i6 = subTableLocation;
        }
        int i7 = chainSubRuleSetCount;
        int[] iArr2 = chainSubRuleSetOffsets;
        this.subTables.add(new SubTableLookup6Format1(this.openReader, this.lookupFlag, substMap));
    }

    /* access modifiers changed from: protected */
    public void readSubTableFormat2(int subTableLocation) throws IOException {
        int coverageOffset;
        int i = subTableLocation;
        int coverageOffset2 = this.openReader.f1214rf.readUnsignedShort();
        int backtrackClassDefOffset = this.openReader.f1214rf.readUnsignedShort();
        int inputClassDefOffset = this.openReader.f1214rf.readUnsignedShort();
        int lookaheadClassDefOffset = this.openReader.f1214rf.readUnsignedShort();
        int chainSubClassSetCount = this.openReader.f1214rf.readUnsignedShort();
        int[] chainSubClassSetOffsets = this.openReader.readUShortArray(chainSubClassSetCount, i);
        SubTableLookup6Format2 t = new SubTableLookup6Format2(this.openReader, this.lookupFlag, new HashSet<>(this.openReader.readCoverageFormat(i + coverageOffset2)), this.openReader.readClassDefinition(i + backtrackClassDefOffset), this.openReader.readClassDefinition(i + inputClassDefOffset), this.openReader.readClassDefinition(i + lookaheadClassDefOffset));
        List<List<ContextualSubstRule>> subClassSets = new ArrayList<>(chainSubClassSetCount);
        int i2 = 0;
        while (i2 < chainSubClassSetCount) {
            List<ContextualSubstRule> subClassSet = null;
            if (chainSubClassSetOffsets[i2] != 0) {
                coverageOffset = coverageOffset2;
                this.openReader.f1214rf.seek((long) chainSubClassSetOffsets[i2]);
                int chainSubClassRuleCount = this.openReader.f1214rf.readUnsignedShort();
                int[] chainSubClassRuleOffsets = this.openReader.readUShortArray(chainSubClassRuleCount, chainSubClassSetOffsets[i2]);
                subClassSet = new ArrayList<>(chainSubClassRuleCount);
                int j = 0;
                while (j < chainSubClassRuleCount) {
                    int chainSubClassRuleCount2 = chainSubClassRuleCount;
                    int[] chainSubClassRuleOffsets2 = chainSubClassRuleOffsets;
                    this.openReader.f1214rf.seek((long) chainSubClassRuleOffsets[j]);
                    int backtrackClassCount = this.openReader.f1214rf.readUnsignedShort();
                    int[] backtrackClassIds = this.openReader.readUShortArray(backtrackClassCount);
                    int i3 = backtrackClassCount;
                    int backtrackClassDefOffset2 = backtrackClassDefOffset;
                    int[] inputClassIds = this.openReader.readUShortArray(this.openReader.f1214rf.readUnsignedShort() - 1);
                    int lookAheadClassCount = this.openReader.f1214rf.readUnsignedShort();
                    int inputClassDefOffset2 = inputClassDefOffset;
                    int i4 = lookAheadClassCount;
                    int[] iArr = inputClassIds;
                    subClassSet.add(new SubTableLookup6Format2.SubstRuleFormat2(t, backtrackClassIds, inputClassIds, this.openReader.readUShortArray(lookAheadClassCount), this.openReader.readSubstLookupRecords(this.openReader.f1214rf.readUnsignedShort())));
                    j++;
                    chainSubClassRuleCount = chainSubClassRuleCount2;
                    chainSubClassRuleOffsets = chainSubClassRuleOffsets2;
                    backtrackClassDefOffset = backtrackClassDefOffset2;
                    inputClassDefOffset = inputClassDefOffset2;
                    lookaheadClassDefOffset = lookaheadClassDefOffset;
                }
                int[] iArr2 = chainSubClassRuleOffsets;
            } else {
                coverageOffset = coverageOffset2;
            }
            subClassSets.add(subClassSet);
            i2++;
            int i5 = subTableLocation;
            coverageOffset2 = coverageOffset;
            backtrackClassDefOffset = backtrackClassDefOffset;
            inputClassDefOffset = inputClassDefOffset;
            lookaheadClassDefOffset = lookaheadClassDefOffset;
        }
        t.setSubClassSets(subClassSets);
        this.subTables.add(t);
    }

    /* access modifiers changed from: protected */
    public void readSubTableFormat3(int subTableLocation) throws IOException {
        int i = subTableLocation;
        int backtrackGlyphCount = this.openReader.f1214rf.readUnsignedShort();
        int[] backtrackCoverageOffsets = this.openReader.readUShortArray(backtrackGlyphCount, i);
        int inputGlyphCount = this.openReader.f1214rf.readUnsignedShort();
        int[] inputCoverageOffsets = this.openReader.readUShortArray(inputGlyphCount, i);
        int lookaheadGlyphCount = this.openReader.f1214rf.readUnsignedShort();
        int[] lookaheadCoverageOffsets = this.openReader.readUShortArray(lookaheadGlyphCount, i);
        SubstLookupRecord[] substLookupRecords = this.openReader.readSubstLookupRecords(this.openReader.f1214rf.readUnsignedShort());
        List<Set<Integer>> backtrackCoverages = new ArrayList<>(backtrackGlyphCount);
        this.openReader.readCoverages(backtrackCoverageOffsets, backtrackCoverages);
        List<Set<Integer>> inputCoverages = new ArrayList<>(inputGlyphCount);
        this.openReader.readCoverages(inputCoverageOffsets, inputCoverages);
        List<Set<Integer>> lookaheadCoverages = new ArrayList<>(lookaheadGlyphCount);
        this.openReader.readCoverages(lookaheadCoverageOffsets, lookaheadCoverages);
        int i2 = backtrackGlyphCount;
        this.subTables.add(new SubTableLookup6Format3(this.openReader, this.lookupFlag, new SubTableLookup6Format3.SubstRuleFormat3(backtrackCoverages, inputCoverages, lookaheadCoverages, substLookupRecords)));
    }
}
