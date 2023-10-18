package com.itextpdf.p026io.font.otf;

import com.itextpdf.p026io.util.IntHashtable;
import java.io.IOException;
import java.util.List;

/* renamed from: com.itextpdf.io.font.otf.GsubLookupType1 */
public class GsubLookupType1 extends OpenTableLookup {
    private static final long serialVersionUID = 1047931810962199937L;
    private IntHashtable substMap = new IntHashtable();

    public GsubLookupType1(OpenTypeFontTableReader openReader, int lookupFlag, int[] subTableLocations) throws IOException {
        super(openReader, lookupFlag, subTableLocations);
        readSubTables();
    }

    public boolean transformOne(GlyphLine line) {
        int substCode;
        if (line.idx >= line.end) {
            return false;
        }
        Glyph g = line.get(line.idx);
        boolean changed = false;
        if (!(this.openReader.isSkip(g.getCode(), this.lookupFlag) || (substCode = this.substMap.get(g.getCode())) == 0 || substCode == g.getCode())) {
            line.substituteOneToOne(this.openReader, substCode);
            changed = true;
        }
        line.idx++;
        return changed;
    }

    /* access modifiers changed from: protected */
    public void readSubTable(int subTableLocation) throws IOException {
        this.openReader.f1214rf.seek((long) subTableLocation);
        int substFormat = this.openReader.f1214rf.readShort();
        if (substFormat == 1) {
            int coverage = this.openReader.f1214rf.readUnsignedShort();
            int deltaGlyphID = this.openReader.f1214rf.readShort();
            for (Integer intValue : this.openReader.readCoverageFormat(subTableLocation + coverage)) {
                int coverageGlyphId = intValue.intValue();
                this.substMap.put(coverageGlyphId, coverageGlyphId + deltaGlyphID);
            }
        } else if (substFormat == 2) {
            int coverage2 = this.openReader.f1214rf.readUnsignedShort();
            int glyphCount = this.openReader.f1214rf.readUnsignedShort();
            int[] substitute = new int[glyphCount];
            for (int k = 0; k < glyphCount; k++) {
                substitute[k] = this.openReader.f1214rf.readUnsignedShort();
            }
            List<Integer> coverageGlyphIds = this.openReader.readCoverageFormat(subTableLocation + coverage2);
            for (int k2 = 0; k2 < glyphCount; k2++) {
                this.substMap.put(coverageGlyphIds.get(k2).intValue(), substitute[k2]);
            }
        } else {
            throw new IllegalArgumentException("Bad substFormat: " + substFormat);
        }
    }

    public boolean hasSubstitution(int index) {
        return this.substMap.containsKey(index);
    }
}
