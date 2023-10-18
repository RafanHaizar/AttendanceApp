package com.itextpdf.p026io.font.otf;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.itextpdf.io.font.otf.GsubLookupType2 */
public class GsubLookupType2 extends OpenTableLookup {
    private static final long serialVersionUID = 48861238131801306L;
    private Map<Integer, int[]> substMap = new HashMap();

    public GsubLookupType2(OpenTypeFontTableReader openReader, int lookupFlag, int[] subTableLocations) throws IOException {
        super(openReader, lookupFlag, subTableLocations);
        readSubTables();
    }

    public boolean transformOne(GlyphLine line) {
        int[] substSequence;
        if (line.idx >= line.end) {
            return false;
        }
        Glyph g = line.get(line.idx);
        boolean changed = false;
        if (!this.openReader.isSkip(g.getCode(), this.lookupFlag) && (substSequence = this.substMap.get(Integer.valueOf(g.getCode()))) != null && substSequence.length > 0) {
            line.substituteOneToMany(this.openReader, substSequence);
            changed = true;
        }
        line.idx++;
        return changed;
    }

    /* access modifiers changed from: protected */
    public void readSubTable(int subTableLocation) throws IOException {
        this.openReader.f1214rf.seek((long) subTableLocation);
        int substFormat = this.openReader.f1214rf.readUnsignedShort();
        if (substFormat == 1) {
            int coverage = this.openReader.f1214rf.readUnsignedShort();
            int sequenceCount = this.openReader.f1214rf.readUnsignedShort();
            int[] sequenceLocations = this.openReader.readUShortArray(sequenceCount, subTableLocation);
            List<Integer> coverageGlyphIds = this.openReader.readCoverageFormat(subTableLocation + coverage);
            for (int i = 0; i < sequenceCount; i++) {
                this.openReader.f1214rf.seek((long) sequenceLocations[i]);
                this.substMap.put(coverageGlyphIds.get(i), this.openReader.readUShortArray(this.openReader.f1214rf.readUnsignedShort()));
            }
            return;
        }
        throw new IllegalArgumentException("Bad substFormat: " + substFormat);
    }

    public boolean hasSubstitution(int index) {
        return this.substMap.containsKey(Integer.valueOf(index));
    }
}
