package com.itextpdf.p026io.font.otf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.itextpdf.io.font.otf.GposLookupType1 */
public class GposLookupType1 extends OpenTableLookup {
    private static final long serialVersionUID = 4562279115440679363L;
    private Map<Integer, GposValueRecord> valueRecordMap = new HashMap();

    public GposLookupType1(OpenTypeFontTableReader openReader, int lookupFlag, int[] subTableLocations) throws IOException {
        super(openReader, lookupFlag, subTableLocations);
        readSubTables();
    }

    public boolean transformOne(GlyphLine line) {
        if (line.idx >= line.end) {
            return false;
        }
        if (this.openReader.isSkip(line.get(line.idx).getCode(), this.lookupFlag)) {
            line.idx++;
            return false;
        }
        boolean positionApplied = false;
        GposValueRecord valueRecord = this.valueRecordMap.get(Integer.valueOf(line.get(line.idx).getCode()));
        if (valueRecord != null) {
            Glyph newGlyph = new Glyph(line.get(line.idx));
            newGlyph.xAdvance = (short) (newGlyph.xAdvance + ((short) valueRecord.XAdvance));
            newGlyph.yAdvance = (short) (newGlyph.yAdvance + ((short) valueRecord.YAdvance));
            line.set(line.idx, newGlyph);
            positionApplied = true;
        }
        line.idx++;
        return positionApplied;
    }

    /* access modifiers changed from: protected */
    public void readSubTable(int subTableLocation) throws IOException {
        this.openReader.f1214rf.seek((long) subTableLocation);
        this.openReader.f1214rf.readShort();
        int coverage = this.openReader.f1214rf.readUnsignedShort();
        GposValueRecord valueRecord = OtfReadCommon.readGposValueRecord(this.openReader, this.openReader.f1214rf.readUnsignedShort());
        for (Integer glyphId : this.openReader.readCoverageFormat(subTableLocation + coverage)) {
            this.valueRecordMap.put(Integer.valueOf(glyphId.intValue()), valueRecord);
        }
    }
}
