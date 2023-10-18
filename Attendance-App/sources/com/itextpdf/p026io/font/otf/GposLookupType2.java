package com.itextpdf.p026io.font.otf;

import com.itextpdf.p026io.font.otf.OpenTableLookup;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/* renamed from: com.itextpdf.io.font.otf.GposLookupType2 */
public class GposLookupType2 extends OpenTableLookup {
    private static final long serialVersionUID = 4781829862270887603L;
    private List<OpenTableLookup> listRules = new ArrayList();

    public GposLookupType2(OpenTypeFontTableReader openReader, int lookupFlag, int[] subTableLocations) throws IOException {
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
        for (OpenTableLookup lookup : this.listRules) {
            if (lookup.transformOne(line)) {
                return true;
            }
        }
        line.idx++;
        return false;
    }

    /* access modifiers changed from: protected */
    public void readSubTable(int subTableLocation) throws IOException {
        this.openReader.f1214rf.seek((long) subTableLocation);
        switch (this.openReader.f1214rf.readShort()) {
            case 1:
                this.listRules.add(new PairPosAdjustmentFormat1(this.openReader, this.lookupFlag, subTableLocation));
                return;
            case 2:
                this.listRules.add(new PairPosAdjustmentFormat2(this.openReader, this.lookupFlag, subTableLocation));
                return;
            default:
                return;
        }
    }

    /* renamed from: com.itextpdf.io.font.otf.GposLookupType2$PairPosAdjustmentFormat1 */
    private static class PairPosAdjustmentFormat1 extends OpenTableLookup {
        private static final long serialVersionUID = -5556528810086852702L;
        private Map<Integer, Map<Integer, PairValueFormat>> gposMap = new HashMap();

        public PairPosAdjustmentFormat1(OpenTypeFontTableReader openReader, int lookupFlag, int subtableLocation) throws IOException {
            super(openReader, lookupFlag, (int[]) null);
            readFormat(subtableLocation);
        }

        public boolean transformOne(GlyphLine line) {
            PairValueFormat pv;
            GlyphLine glyphLine = line;
            if (glyphLine.idx >= glyphLine.end || glyphLine.idx < glyphLine.start) {
                return false;
            }
            Glyph g1 = glyphLine.get(glyphLine.idx);
            Map<Integer, PairValueFormat> m = this.gposMap.get(Integer.valueOf(g1.getCode()));
            if (m == null) {
                return false;
            }
            OpenTableLookup.GlyphIndexer gi = new OpenTableLookup.GlyphIndexer();
            gi.line = glyphLine;
            gi.idx = glyphLine.idx;
            gi.nextGlyph(this.openReader, this.lookupFlag);
            if (gi.glyph == null || (pv = m.get(Integer.valueOf(gi.glyph.getCode()))) == null) {
                return false;
            }
            Glyph g2 = gi.glyph;
            int i = glyphLine.idx;
            Glyph glyph = r4;
            Glyph glyph2 = new Glyph(g1, 0, 0, pv.first.XAdvance, pv.first.YAdvance, 0);
            glyphLine.set(i, glyph);
            glyphLine.set(gi.idx, new Glyph(g2, 0, 0, pv.second.XAdvance, pv.second.YAdvance, 0));
            glyphLine.idx = gi.idx;
            return true;
        }

        /* access modifiers changed from: protected */
        public void readFormat(int subTableLocation) throws IOException {
            int valueFormat1 = this.openReader.f1214rf.readUnsignedShort();
            int valueFormat2 = this.openReader.f1214rf.readUnsignedShort();
            int pairSetCount = this.openReader.f1214rf.readUnsignedShort();
            int[] locationRule = this.openReader.readUShortArray(pairSetCount, subTableLocation);
            List<Integer> coverageList = this.openReader.readCoverageFormat(this.openReader.f1214rf.readUnsignedShort() + subTableLocation);
            for (int k = 0; k < pairSetCount; k++) {
                this.openReader.f1214rf.seek((long) locationRule[k]);
                Map<Integer, PairValueFormat> pairs = new HashMap<>();
                this.gposMap.put(coverageList.get(k), pairs);
                int pairValueCount = this.openReader.f1214rf.readUnsignedShort();
                for (int j = 0; j < pairValueCount; j++) {
                    int glyph2 = this.openReader.f1214rf.readUnsignedShort();
                    PairValueFormat pair = new PairValueFormat();
                    pair.first = OtfReadCommon.readGposValueRecord(this.openReader, valueFormat1);
                    pair.second = OtfReadCommon.readGposValueRecord(this.openReader, valueFormat2);
                    pairs.put(Integer.valueOf(glyph2), pair);
                }
            }
        }

        /* access modifiers changed from: protected */
        public void readSubTable(int subTableLocation) throws IOException {
        }
    }

    /* renamed from: com.itextpdf.io.font.otf.GposLookupType2$PairPosAdjustmentFormat2 */
    private static class PairPosAdjustmentFormat2 extends OpenTableLookup {
        private static final long serialVersionUID = 3056620748845862393L;
        private OtfClass classDef1;
        private OtfClass classDef2;
        private HashSet<Integer> coverageSet;
        private Map<Integer, PairValueFormat[]> posSubs = new HashMap();

        public PairPosAdjustmentFormat2(OpenTypeFontTableReader openReader, int lookupFlag, int subtableLocation) throws IOException {
            super(openReader, lookupFlag, (int[]) null);
            readFormat(subtableLocation);
        }

        public boolean transformOne(GlyphLine line) {
            GlyphLine glyphLine = line;
            if (glyphLine.idx >= glyphLine.end || glyphLine.idx < glyphLine.start) {
                return false;
            }
            Glyph g1 = glyphLine.get(glyphLine.idx);
            if (!this.coverageSet.contains(Integer.valueOf(g1.getCode()))) {
                return false;
            }
            PairValueFormat[] pvs = this.posSubs.get(Integer.valueOf(this.classDef1.getOtfClass(g1.getCode())));
            if (pvs == null) {
                return false;
            }
            OpenTableLookup.GlyphIndexer gi = new OpenTableLookup.GlyphIndexer();
            gi.line = glyphLine;
            gi.idx = glyphLine.idx;
            gi.nextGlyph(this.openReader, this.lookupFlag);
            if (gi.glyph == null) {
                return false;
            }
            Glyph g2 = gi.glyph;
            int c2 = this.classDef2.getOtfClass(g2.getCode());
            if (c2 >= pvs.length) {
                return false;
            }
            PairValueFormat pv = pvs[c2];
            int i = glyphLine.idx;
            Glyph glyph = r5;
            Glyph glyph2 = new Glyph(g1, 0, 0, pv.first.XAdvance, pv.first.YAdvance, 0);
            glyphLine.set(i, glyph);
            int i2 = c2;
            Glyph glyph3 = g2;
            glyphLine.set(gi.idx, new Glyph(g2, 0, 0, pv.second.XAdvance, pv.second.YAdvance, 0));
            glyphLine.idx = gi.idx;
            return true;
        }

        /* access modifiers changed from: protected */
        public void readFormat(int subTableLocation) throws IOException {
            int coverage = this.openReader.f1214rf.readUnsignedShort() + subTableLocation;
            int valueFormat1 = this.openReader.f1214rf.readUnsignedShort();
            int valueFormat2 = this.openReader.f1214rf.readUnsignedShort();
            int locationClass1 = this.openReader.f1214rf.readUnsignedShort() + subTableLocation;
            int locationClass2 = this.openReader.f1214rf.readUnsignedShort() + subTableLocation;
            int class1Count = this.openReader.f1214rf.readUnsignedShort();
            int class2Count = this.openReader.f1214rf.readUnsignedShort();
            for (int k = 0; k < class1Count; k++) {
                PairValueFormat[] pairs = new PairValueFormat[class2Count];
                this.posSubs.put(Integer.valueOf(k), pairs);
                for (int j = 0; j < class2Count; j++) {
                    PairValueFormat pair = new PairValueFormat();
                    pair.first = OtfReadCommon.readGposValueRecord(this.openReader, valueFormat1);
                    pair.second = OtfReadCommon.readGposValueRecord(this.openReader, valueFormat2);
                    pairs[j] = pair;
                }
            }
            this.coverageSet = new HashSet<>(this.openReader.readCoverageFormat(coverage));
            this.classDef1 = this.openReader.readClassDefinition(locationClass1);
            this.classDef2 = this.openReader.readClassDefinition(locationClass2);
        }

        /* access modifiers changed from: protected */
        public void readSubTable(int subTableLocation) throws IOException {
        }
    }

    /* renamed from: com.itextpdf.io.font.otf.GposLookupType2$PairValueFormat */
    private static class PairValueFormat implements Serializable {
        private static final long serialVersionUID = -6442882035589529495L;
        public GposValueRecord first;
        public GposValueRecord second;

        private PairValueFormat() {
        }
    }
}
