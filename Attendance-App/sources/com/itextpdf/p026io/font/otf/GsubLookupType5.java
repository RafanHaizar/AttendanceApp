package com.itextpdf.p026io.font.otf;

import com.itextpdf.p026io.font.otf.OpenTableLookup;
import com.itextpdf.p026io.font.otf.lookuptype5.SubTableLookup5Format1;
import com.itextpdf.p026io.font.otf.lookuptype5.SubTableLookup5Format2;
import com.itextpdf.p026io.font.otf.lookuptype5.SubTableLookup5Format3;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* renamed from: com.itextpdf.io.font.otf.GsubLookupType5 */
public class GsubLookupType5 extends OpenTableLookup {
    private static final long serialVersionUID = 1499367592878919320L;
    protected List<ContextualSubTable> subTables = new ArrayList();

    protected GsubLookupType5(OpenTypeFontTableReader openReader, int lookupFlag, int[] subTableLocations) throws IOException {
        super(openReader, lookupFlag, subTableLocations);
        readSubTables();
    }

    public boolean transformOne(GlyphLine line) {
        GlyphLine glyphLine = line;
        boolean changed = false;
        int oldLineStart = glyphLine.start;
        int oldLineEnd = glyphLine.end;
        int initialLineIndex = glyphLine.idx;
        for (ContextualSubTable subTable : this.subTables) {
            ContextualSubstRule contextRule = subTable.getMatchingContextRule(glyphLine);
            if (contextRule != null) {
                int lineEndBeforeSubstitutions = glyphLine.end;
                SubstLookupRecord[] substLookupRecords = contextRule.getSubstLookupRecords();
                OpenTableLookup.GlyphIndexer gidx = new OpenTableLookup.GlyphIndexer();
                gidx.line = glyphLine;
                int length = substLookupRecords.length;
                int i = 0;
                while (i < length) {
                    SubstLookupRecord substRecord = substLookupRecords[i];
                    gidx.idx = initialLineIndex;
                    int i2 = 0;
                    while (i2 < substRecord.sequenceIndex) {
                        gidx.nextGlyph(this.openReader, this.lookupFlag);
                        i2++;
                        initialLineIndex = initialLineIndex;
                    }
                    int initialLineIndex2 = initialLineIndex;
                    glyphLine.idx = gidx.idx;
                    changed = this.openReader.getLookupTable(substRecord.lookupListIndex).transformOne(glyphLine) || changed;
                    i++;
                    initialLineIndex = initialLineIndex2;
                }
                glyphLine.idx = glyphLine.end;
                glyphLine.start = oldLineStart;
                glyphLine.end = oldLineEnd - (lineEndBeforeSubstitutions - glyphLine.end);
                return changed;
            }
        }
        glyphLine.idx++;
        return false;
    }

    /* access modifiers changed from: protected */
    public void readSubTable(int subTableLocation) throws IOException {
        this.openReader.f1214rf.seek((long) subTableLocation);
        int substFormat = this.openReader.f1214rf.readShort();
        if (substFormat == 1) {
            readSubTableFormat1(subTableLocation);
        } else if (substFormat == 2) {
            readSubTableFormat2(subTableLocation);
        } else if (substFormat == 3) {
            readSubTableFormat3(subTableLocation);
        } else {
            throw new IllegalArgumentException("Bad substFormat: " + substFormat);
        }
    }

    /* access modifiers changed from: protected */
    public void readSubTableFormat1(int subTableLocation) throws IOException {
        int i = subTableLocation;
        Map<Integer, List<ContextualSubstRule>> substMap = new HashMap<>();
        int coverageOffset = this.openReader.f1214rf.readUnsignedShort();
        int subRuleSetCount = this.openReader.f1214rf.readUnsignedShort();
        int[] subRuleSetOffsets = this.openReader.readUShortArray(subRuleSetCount, i);
        List<Integer> coverageGlyphIds = this.openReader.readCoverageFormat(i + coverageOffset);
        int i2 = 0;
        while (i2 < subRuleSetCount) {
            this.openReader.f1214rf.seek((long) subRuleSetOffsets[i2]);
            int subRuleCount = this.openReader.f1214rf.readUnsignedShort();
            int[] subRuleOffsets = this.openReader.readUShortArray(subRuleCount, subRuleSetOffsets[i2]);
            List<ContextualSubstRule> subRuleSet = new ArrayList<>(subRuleCount);
            int j = 0;
            while (j < subRuleCount) {
                this.openReader.f1214rf.seek((long) subRuleOffsets[j]);
                subRuleSet.add(new SubTableLookup5Format1.SubstRuleFormat1(this.openReader.readUShortArray(this.openReader.f1214rf.readUnsignedShort() - 1), this.openReader.readSubstLookupRecords(this.openReader.f1214rf.readUnsignedShort())));
                j++;
                int i3 = subTableLocation;
            }
            substMap.put(coverageGlyphIds.get(i2), subRuleSet);
            i2++;
            int i4 = subTableLocation;
        }
        this.subTables.add(new SubTableLookup5Format1(this.openReader, this.lookupFlag, substMap));
    }

    /* access modifiers changed from: protected */
    public void readSubTableFormat2(int subTableLocation) throws IOException {
        int i = subTableLocation;
        int coverageOffset = this.openReader.f1214rf.readUnsignedShort();
        int classDefOffset = this.openReader.f1214rf.readUnsignedShort();
        int subClassSetCount = this.openReader.f1214rf.readUnsignedShort();
        int[] subClassSetOffsets = this.openReader.readUShortArray(subClassSetCount, i);
        SubTableLookup5Format2 t = new SubTableLookup5Format2(this.openReader, this.lookupFlag, new HashSet<>(this.openReader.readCoverageFormat(i + coverageOffset)), this.openReader.readClassDefinition(i + classDefOffset));
        List<List<ContextualSubstRule>> subClassSets = new ArrayList<>(subClassSetCount);
        int i2 = 0;
        while (i2 < subClassSetCount) {
            List<ContextualSubstRule> subClassSet = null;
            if (subClassSetOffsets[i2] != 0) {
                this.openReader.f1214rf.seek((long) subClassSetOffsets[i2]);
                int subClassRuleCount = this.openReader.f1214rf.readUnsignedShort();
                int[] subClassRuleOffsets = this.openReader.readUShortArray(subClassRuleCount, subClassSetOffsets[i2]);
                subClassSet = new ArrayList<>(subClassRuleCount);
                int j = 0;
                while (j < subClassRuleCount) {
                    int coverageOffset2 = coverageOffset;
                    this.openReader.f1214rf.seek((long) subClassRuleOffsets[j]);
                    int glyphCount = this.openReader.f1214rf.readUnsignedShort();
                    int i3 = glyphCount;
                    subClassSet.add(new SubTableLookup5Format2.SubstRuleFormat2(t, this.openReader.readUShortArray(glyphCount - 1), this.openReader.readSubstLookupRecords(this.openReader.f1214rf.readUnsignedShort())));
                    j++;
                    int i4 = subTableLocation;
                    coverageOffset = coverageOffset2;
                    classDefOffset = classDefOffset;
                }
            }
            subClassSets.add(subClassSet);
            i2++;
            int i5 = subTableLocation;
            coverageOffset = coverageOffset;
            classDefOffset = classDefOffset;
        }
        t.setSubClassSets(subClassSets);
        this.subTables.add(t);
    }

    /* access modifiers changed from: protected */
    public void readSubTableFormat3(int subTableLocation) throws IOException {
        int glyphCount = this.openReader.f1214rf.readUnsignedShort();
        int substCount = this.openReader.f1214rf.readUnsignedShort();
        int[] coverageOffsets = this.openReader.readUShortArray(glyphCount, subTableLocation);
        SubstLookupRecord[] substLookupRecords = this.openReader.readSubstLookupRecords(substCount);
        List<Set<Integer>> coverages = new ArrayList<>(glyphCount);
        this.openReader.readCoverages(coverageOffsets, coverages);
        this.subTables.add(new SubTableLookup5Format3(this.openReader, this.lookupFlag, new SubTableLookup5Format3.SubstRuleFormat3(coverages, substLookupRecords)));
    }
}
