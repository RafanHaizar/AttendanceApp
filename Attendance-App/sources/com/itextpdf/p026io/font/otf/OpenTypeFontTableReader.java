package com.itextpdf.p026io.font.otf;

import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import com.itextpdf.p026io.util.IntHashtable;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* renamed from: com.itextpdf.io.font.otf.OpenTypeFontTableReader */
public abstract class OpenTypeFontTableReader implements Serializable {
    private static final long serialVersionUID = 4826484598227913292L;
    protected OpenTypeFeature featuresType;
    private final OpenTypeGdefTableReader gdef;
    private final Map<Integer, Glyph> indexGlyphMap;
    protected List<OpenTableLookup> lookupList;

    /* renamed from: rf */
    protected final RandomAccessFileOrArray f1214rf;
    protected OpenTypeScript scriptsType;
    protected final int tableLocation;
    private final int unitsPerEm;

    /* access modifiers changed from: protected */
    public abstract OpenTableLookup readLookupTable(int i, int i2, int[] iArr) throws IOException;

    protected OpenTypeFontTableReader(RandomAccessFileOrArray rf, int tableLocation2, OpenTypeGdefTableReader gdef2, Map<Integer, Glyph> indexGlyphMap2, int unitsPerEm2) throws IOException {
        this.f1214rf = rf;
        this.tableLocation = tableLocation2;
        this.indexGlyphMap = indexGlyphMap2;
        this.gdef = gdef2;
        this.unitsPerEm = unitsPerEm2;
    }

    public Glyph getGlyph(int index) {
        return this.indexGlyphMap.get(Integer.valueOf(index));
    }

    public OpenTableLookup getLookupTable(int idx) {
        if (idx < 0 || idx >= this.lookupList.size()) {
            return null;
        }
        return this.lookupList.get(idx);
    }

    public List<ScriptRecord> getScriptRecords() {
        return this.scriptsType.getScriptRecords();
    }

    public List<FeatureRecord> getFeatureRecords() {
        return this.featuresType.getRecords();
    }

    public List<FeatureRecord> getFeatures(String[] scripts, String language) {
        LanguageRecord rec = this.scriptsType.getLanguageRecord(scripts, language);
        if (rec == null) {
            return null;
        }
        List<FeatureRecord> ret = new ArrayList<>();
        for (int f : rec.features) {
            ret.add(this.featuresType.getRecord(f));
        }
        return ret;
    }

    public List<FeatureRecord> getSpecificFeatures(List<FeatureRecord> features, String[] specific) {
        if (specific == null) {
            return features;
        }
        Set<String> hs = new HashSet<>();
        for (String s : specific) {
            hs.add(s);
        }
        List<FeatureRecord> recs = new ArrayList<>();
        for (FeatureRecord rec : features) {
            if (hs.contains(rec.tag)) {
                recs.add(rec);
            }
        }
        return recs;
    }

    public FeatureRecord getRequiredFeature(String[] scripts, String language) {
        LanguageRecord rec = this.scriptsType.getLanguageRecord(scripts, language);
        if (rec == null) {
            return null;
        }
        return this.featuresType.getRecord(rec.featureRequired);
    }

    public List<OpenTableLookup> getLookups(FeatureRecord[] features) {
        IntHashtable hash = new IntHashtable();
        for (FeatureRecord rec : features) {
            for (int idx : rec.lookups) {
                hash.put(idx, 1);
            }
        }
        List<OpenTableLookup> ret = new ArrayList<>();
        for (int idx2 : hash.toOrderedKeys()) {
            ret.add(this.lookupList.get(idx2));
        }
        return ret;
    }

    public List<OpenTableLookup> getLookups(FeatureRecord feature) {
        List<OpenTableLookup> ret = new ArrayList<>(feature.lookups.length);
        for (int idx : feature.lookups) {
            ret.add(this.lookupList.get(idx));
        }
        return ret;
    }

    public boolean isSkip(int glyph, int flag) {
        return this.gdef.isSkip(glyph, flag);
    }

    public int getGlyphClass(int glyphCode) {
        return this.gdef.getGlyphClassTable().getOtfClass(glyphCode);
    }

    public int getUnitsPerEm() {
        return this.unitsPerEm;
    }

    public LanguageRecord getLanguageRecord(String otfScriptTag) {
        if (otfScriptTag == null) {
            return null;
        }
        for (ScriptRecord record : getScriptRecords()) {
            if (otfScriptTag.equals(record.tag)) {
                return record.defaultLanguage;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public final OtfClass readClassDefinition(int classLocation) throws IOException {
        return OtfClass.create(this.f1214rf, classLocation);
    }

    /* access modifiers changed from: protected */
    public final int[] readUShortArray(int size, int location) throws IOException {
        return OtfReadCommon.readUShortArray(this.f1214rf, size, location);
    }

    /* access modifiers changed from: protected */
    public final int[] readUShortArray(int size) throws IOException {
        return OtfReadCommon.readUShortArray(this.f1214rf, size);
    }

    /* access modifiers changed from: protected */
    public void readCoverages(int[] locations, List<Set<Integer>> coverage) throws IOException {
        OtfReadCommon.readCoverages(this.f1214rf, locations, coverage);
    }

    /* access modifiers changed from: protected */
    public final List<Integer> readCoverageFormat(int coverageLocation) throws IOException {
        return OtfReadCommon.readCoverageFormat(this.f1214rf, coverageLocation);
    }

    /* access modifiers changed from: protected */
    public SubstLookupRecord[] readSubstLookupRecords(int substCount) throws IOException {
        return OtfReadCommon.readSubstLookupRecords(this.f1214rf, substCount);
    }

    /* access modifiers changed from: protected */
    public PosLookupRecord[] readPosLookupRecords(int substCount) throws IOException {
        return OtfReadCommon.readPosLookupRecords(this.f1214rf, substCount);
    }

    /* access modifiers changed from: protected */
    public TagAndLocation[] readTagAndLocations(int baseLocation) throws IOException {
        int count = this.f1214rf.readUnsignedShort();
        TagAndLocation[] tagslLocs = new TagAndLocation[count];
        for (int k = 0; k < count; k++) {
            TagAndLocation tl = new TagAndLocation();
            tl.tag = this.f1214rf.readString(4, "utf-8");
            tl.location = this.f1214rf.readUnsignedShort() + baseLocation;
            tagslLocs[k] = tl;
        }
        return tagslLocs;
    }

    /* access modifiers changed from: package-private */
    public final void startReadingTable() throws FontReadingException {
        try {
            this.f1214rf.seek((long) this.tableLocation);
            this.f1214rf.readInt();
            int scriptListOffset = this.f1214rf.readUnsignedShort();
            int featureListOffset = this.f1214rf.readUnsignedShort();
            int lookupListOffset = this.f1214rf.readUnsignedShort();
            this.scriptsType = new OpenTypeScript(this, this.tableLocation + scriptListOffset);
            this.featuresType = new OpenTypeFeature(this, this.tableLocation + featureListOffset);
            readLookupListTable(this.tableLocation + lookupListOffset);
        } catch (IOException e) {
            throw new FontReadingException("Error reading font file", e);
        }
    }

    private void readLookupListTable(int lookupListTableLocation) throws IOException {
        this.lookupList = new ArrayList();
        this.f1214rf.seek((long) lookupListTableLocation);
        for (int lookupLocation : readUShortArray(this.f1214rf.readUnsignedShort(), lookupListTableLocation)) {
            if (lookupLocation != 0) {
                readLookupTable(lookupLocation);
            }
        }
    }

    private void readLookupTable(int lookupTableLocation) throws IOException {
        this.f1214rf.seek((long) lookupTableLocation);
        this.lookupList.add(readLookupTable(this.f1214rf.readUnsignedShort(), this.f1214rf.readUnsignedShort(), readUShortArray(this.f1214rf.readUnsignedShort(), lookupTableLocation)));
    }
}
