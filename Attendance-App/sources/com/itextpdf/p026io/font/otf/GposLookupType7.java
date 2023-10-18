package com.itextpdf.p026io.font.otf;

import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.font.otf.OpenTableLookup;
import com.itextpdf.p026io.font.otf.lookuptype7.PosTableLookup7Format2;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* renamed from: com.itextpdf.io.font.otf.GposLookupType7 */
public class GposLookupType7 extends OpenTableLookup {
    private static final Logger LOGGER = LoggerFactory.getLogger((Class<?>) GposLookupType7.class);
    private static final long serialVersionUID = 4596977183462695970L;
    private List<ContextualPositionTable> subTables = new ArrayList();

    public GposLookupType7(OpenTypeFontTableReader openReader, int lookupFlag, int[] subTableLocations) throws IOException {
        super(openReader, lookupFlag, subTableLocations);
        readSubTables();
    }

    public boolean transformOne(GlyphLine line) {
        GlyphLine glyphLine = line;
        boolean changed = false;
        int oldLineStart = glyphLine.start;
        int oldLineEnd = glyphLine.end;
        int initialLineIndex = glyphLine.idx;
        for (ContextualPositionTable subTable : this.subTables) {
            ContextualPositionRule contextRule = (ContextualPositionRule) subTable.getMatchingContextRule(glyphLine);
            if (contextRule != null) {
                int lineEndBeforeTransformations = glyphLine.end;
                PosLookupRecord[] posLookupRecords = contextRule.getPosLookupRecords();
                OpenTableLookup.GlyphIndexer gidx = new OpenTableLookup.GlyphIndexer();
                gidx.line = glyphLine;
                int length = posLookupRecords.length;
                int i = 0;
                while (i < length) {
                    PosLookupRecord posRecord = posLookupRecords[i];
                    gidx.idx = initialLineIndex;
                    int i2 = 0;
                    while (i2 < posRecord.sequenceIndex) {
                        gidx.nextGlyph(this.openReader, this.lookupFlag);
                        i2++;
                        initialLineIndex = initialLineIndex;
                    }
                    int initialLineIndex2 = initialLineIndex;
                    glyphLine.idx = gidx.idx;
                    changed = this.openReader.getLookupTable(posRecord.lookupListIndex).transformOne(glyphLine) || changed;
                    i++;
                    initialLineIndex = initialLineIndex2;
                }
                glyphLine.idx = glyphLine.end;
                glyphLine.start = oldLineStart;
                glyphLine.end = oldLineEnd - (lineEndBeforeTransformations - glyphLine.end);
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
        switch (substFormat) {
            case 1:
            case 3:
                LOGGER.warn(MessageFormatUtil.format(LogMessageConstant.GPOS_LOOKUP_SUBTABLE_FORMAT_NOT_SUPPORTED, Integer.valueOf(substFormat), 7));
                return;
            case 2:
                readSubTableFormat2(subTableLocation);
                return;
            default:
                throw new IllegalArgumentException("Bad subtable format identifier: " + substFormat);
        }
    }

    /* access modifiers changed from: protected */
    public void readSubTableFormat2(int subTableLocation) throws IOException {
        int i = subTableLocation;
        int coverageOffset = this.openReader.f1214rf.readUnsignedShort();
        int classDefOffset = this.openReader.f1214rf.readUnsignedShort();
        int posClassSetCount = this.openReader.f1214rf.readUnsignedShort();
        int[] posClassSetOffsets = this.openReader.readUShortArray(posClassSetCount, i);
        PosTableLookup7Format2 t = new PosTableLookup7Format2(this.openReader, this.lookupFlag, new HashSet<>(this.openReader.readCoverageFormat(i + coverageOffset)), this.openReader.readClassDefinition(i + classDefOffset));
        List<List<ContextualPositionRule>> subClassSets = new ArrayList<>(posClassSetCount);
        int i2 = 0;
        while (i2 < posClassSetCount) {
            List<ContextualPositionRule> subClassSet = null;
            if (posClassSetOffsets[i2] != 0) {
                this.openReader.f1214rf.seek((long) posClassSetOffsets[i2]);
                int posClassRuleCount = this.openReader.f1214rf.readUnsignedShort();
                int[] posClassRuleOffsets = this.openReader.readUShortArray(posClassRuleCount, posClassSetOffsets[i2]);
                subClassSet = new ArrayList<>(posClassRuleCount);
                int j = 0;
                while (j < posClassRuleCount) {
                    int coverageOffset2 = coverageOffset;
                    this.openReader.f1214rf.seek((long) posClassRuleOffsets[j]);
                    int glyphCount = this.openReader.f1214rf.readUnsignedShort();
                    int i3 = glyphCount;
                    subClassSet.add(new PosTableLookup7Format2.PosRuleFormat2(t, this.openReader.readUShortArray(glyphCount - 1), this.openReader.readPosLookupRecords(this.openReader.f1214rf.readUnsignedShort())));
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
        t.setPosClassSets(subClassSets);
        this.subTables.add(t);
    }
}
