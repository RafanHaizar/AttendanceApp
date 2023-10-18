package com.itextpdf.p026io.font.otf;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.itextpdf.io.font.otf.GsubLookupType3 */
public class GsubLookupType3 extends OpenTableLookup {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long serialVersionUID = -5408042853790920298L;
    private Map<Integer, int[]> substMap = new HashMap();

    public GsubLookupType3(OpenTypeFontTableReader openReader, int lookupFlag, int[] subTableLocations) throws IOException {
        super(openReader, lookupFlag, subTableLocations);
        readSubTables();
    }

    public boolean transformOne(GlyphLine line) {
        int[] substCode;
        if (line.idx >= line.end) {
            return false;
        }
        Glyph g = line.get(line.idx);
        boolean changed = false;
        if (!(this.openReader.isSkip(g.getCode(), this.lookupFlag) || (substCode = this.substMap.get(Integer.valueOf(g.getCode()))) == null || substCode[0] == g.getCode())) {
            line.substituteOneToOne(this.openReader, substCode[0]);
            changed = true;
        }
        line.idx++;
        return changed;
    }

    /* access modifiers changed from: protected */
    public void readSubTable(int subTableLocation) throws IOException {
        this.openReader.f1214rf.seek((long) subTableLocation);
        if (this.openReader.f1214rf.readShort() == 1) {
            int coverage = this.openReader.f1214rf.readUnsignedShort();
            int alternateSetCount = this.openReader.f1214rf.readUnsignedShort();
            int[][] substitute = new int[alternateSetCount][];
            int[] alternateLocations = this.openReader.readUShortArray(alternateSetCount, subTableLocation);
            for (int k = 0; k < alternateSetCount; k++) {
                this.openReader.f1214rf.seek((long) alternateLocations[k]);
                substitute[k] = this.openReader.readUShortArray(this.openReader.f1214rf.readUnsignedShort());
            }
            List<Integer> coverageGlyphIds = this.openReader.readCoverageFormat(subTableLocation + coverage);
            for (int k2 = 0; k2 < alternateSetCount; k2++) {
                this.substMap.put(coverageGlyphIds.get(k2), substitute[k2]);
            }
            return;
        }
        throw new AssertionError();
    }

    public boolean hasSubstitution(int index) {
        return this.substMap.containsKey(Integer.valueOf(index));
    }
}
