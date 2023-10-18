package com.itextpdf.p026io.font.otf;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.itextpdf.io.font.otf.OpenTypeFeature */
public class OpenTypeFeature implements Serializable {
    private static final long serialVersionUID = 1484564408822091202L;
    private OpenTypeFontTableReader openTypeReader;
    private List<FeatureRecord> records = new ArrayList();

    public OpenTypeFeature(OpenTypeFontTableReader openTypeReader2, int locationFeatureTable) throws IOException {
        this.openTypeReader = openTypeReader2;
        openTypeReader2.f1214rf.seek((long) locationFeatureTable);
        for (TagAndLocation tagLoc : openTypeReader2.readTagAndLocations(locationFeatureTable)) {
            openTypeReader2.f1214rf.seek(((long) tagLoc.location) + 2);
            int lookupCount = openTypeReader2.f1214rf.readUnsignedShort();
            FeatureRecord rec = new FeatureRecord();
            rec.tag = tagLoc.tag;
            rec.lookups = openTypeReader2.readUShortArray(lookupCount);
            this.records.add(rec);
        }
    }

    public List<FeatureRecord> getRecords() {
        return this.records;
    }

    public FeatureRecord getRecord(int idx) {
        if (idx < 0 || idx >= this.records.size()) {
            return null;
        }
        return this.records.get(idx);
    }
}
