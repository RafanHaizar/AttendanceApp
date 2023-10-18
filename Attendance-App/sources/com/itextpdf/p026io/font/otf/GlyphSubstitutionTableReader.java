package com.itextpdf.p026io.font.otf;

import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import java.io.IOException;
import java.util.Map;

/* renamed from: com.itextpdf.io.font.otf.GlyphSubstitutionTableReader */
public class GlyphSubstitutionTableReader extends OpenTypeFontTableReader {
    private static final long serialVersionUID = -6971081733980429442L;

    public GlyphSubstitutionTableReader(RandomAccessFileOrArray rf, int gsubTableLocation, OpenTypeGdefTableReader gdef, Map<Integer, Glyph> indexGlyphMap, int unitsPerEm) throws IOException {
        super(rf, gsubTableLocation, gdef, indexGlyphMap, unitsPerEm);
        startReadingTable();
    }

    /* access modifiers changed from: protected */
    public OpenTableLookup readLookupTable(int lookupType, int lookupFlag, int[] subTableLocations) throws IOException {
        if (lookupType == 7) {
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
                return new GsubLookupType1(this, lookupFlag, subTableLocations);
            case 2:
                return new GsubLookupType2(this, lookupFlag, subTableLocations);
            case 3:
                return new GsubLookupType3(this, lookupFlag, subTableLocations);
            case 4:
                return new GsubLookupType4(this, lookupFlag, subTableLocations);
            case 5:
                return new GsubLookupType5(this, lookupFlag, subTableLocations);
            case 6:
                return new GsubLookupType6(this, lookupFlag, subTableLocations);
            default:
                return null;
        }
    }
}
