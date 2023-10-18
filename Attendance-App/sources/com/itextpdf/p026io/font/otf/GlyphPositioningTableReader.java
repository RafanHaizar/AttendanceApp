package com.itextpdf.p026io.font.otf;

import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import java.io.IOException;
import java.util.Map;

/* renamed from: com.itextpdf.io.font.otf.GlyphPositioningTableReader */
public class GlyphPositioningTableReader extends OpenTypeFontTableReader {
    private static final long serialVersionUID = 7437245788115628787L;

    public GlyphPositioningTableReader(RandomAccessFileOrArray rf, int gposTableLocation, OpenTypeGdefTableReader gdef, Map<Integer, Glyph> indexGlyphMap, int unitsPerEm) throws IOException {
        super(rf, gposTableLocation, gdef, indexGlyphMap, unitsPerEm);
        startReadingTable();
    }

    /* access modifiers changed from: protected */
    public OpenTableLookup readLookupTable(int lookupType, int lookupFlag, int[] subTableLocations) throws IOException {
        if (lookupType == 9) {
            for (int k = 0; k < subTableLocations.length; k++) {
                int location = subTableLocations[k];
                this.f1214rf.seek((long) location);
                this.f1214rf.readUnsignedShort();
                lookupType = this.f1214rf.readUnsignedShort();
                subTableLocations[k] = location + this.f1214rf.readInt();
            }
        }
        switch (lookupType) {
            case 1:
                return new GposLookupType1(this, lookupFlag, subTableLocations);
            case 2:
                return new GposLookupType2(this, lookupFlag, subTableLocations);
            case 4:
                return new GposLookupType4(this, lookupFlag, subTableLocations);
            case 5:
                return new GposLookupType5(this, lookupFlag, subTableLocations);
            case 6:
                return new GposLookupType6(this, lookupFlag, subTableLocations);
            case 7:
                return new GposLookupType7(this, lookupFlag, subTableLocations);
            default:
                return null;
        }
    }
}
