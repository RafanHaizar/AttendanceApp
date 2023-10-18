package com.itextpdf.p026io.font.otf;

import com.itextpdf.p026io.font.otf.OpenTableLookup;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* renamed from: com.itextpdf.io.font.otf.GposLookupType4 */
public class GposLookupType4 extends OpenTableLookup {
    private static final long serialVersionUID = 8820454200196341970L;
    private final List<MarkToBase> marksbases = new ArrayList();

    /* renamed from: com.itextpdf.io.font.otf.GposLookupType4$MarkToBase */
    public static class MarkToBase implements Serializable {
        private static final long serialVersionUID = 1518537209432079627L;
        public final Map<Integer, GposAnchor[]> bases = new HashMap();
        public final Map<Integer, OtfMarkRecord> marks = new HashMap();
    }

    public GposLookupType4(OpenTypeFontTableReader openReader, int lookupFlag, int[] subTableLocations) throws IOException {
        super(openReader, lookupFlag, subTableLocations);
        readSubTables();
    }

    public boolean transformOne(GlyphLine line) {
        GlyphLine glyphLine = line;
        if (glyphLine.idx >= glyphLine.end) {
            return false;
        }
        if (this.openReader.isSkip(glyphLine.get(glyphLine.idx).getCode(), this.lookupFlag)) {
            glyphLine.idx++;
            return false;
        }
        boolean changed = false;
        OpenTableLookup.GlyphIndexer gi = null;
        Iterator<MarkToBase> it = this.marksbases.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            MarkToBase mb = it.next();
            OtfMarkRecord omr = mb.marks.get(Integer.valueOf(glyphLine.get(glyphLine.idx).getCode()));
            if (omr != null) {
                if (gi == null) {
                    OpenTableLookup.GlyphIndexer gi2 = new OpenTableLookup.GlyphIndexer();
                    gi2.idx = glyphLine.idx;
                    gi2.line = glyphLine;
                    do {
                        gi2.previousGlyph(this.openReader, this.lookupFlag);
                        if (gi2.glyph == null || this.openReader.getGlyphClass(gi2.glyph.getCode()) != 3) {
                        }
                        gi2.previousGlyph(this.openReader, this.lookupFlag);
                        break;
                    } while (this.openReader.getGlyphClass(gi2.glyph.getCode()) != 3);
                    if (gi2.glyph == null) {
                        OpenTableLookup.GlyphIndexer glyphIndexer = gi2;
                        break;
                    }
                    gi = gi2;
                }
                GposAnchor[] gpas = mb.bases.get(Integer.valueOf(gi.glyph.getCode()));
                if (gpas != null) {
                    int xPlacement = 0;
                    int yPlacement = 0;
                    GposAnchor baseAnchor = gpas[omr.markClass];
                    if (baseAnchor != null) {
                        xPlacement = baseAnchor.XCoordinate;
                        yPlacement = baseAnchor.YCoordinate;
                    }
                    GposAnchor markAnchor = omr.anchor;
                    if (markAnchor != null) {
                        xPlacement -= markAnchor.XCoordinate;
                        yPlacement -= markAnchor.YCoordinate;
                    }
                    int i = glyphLine.idx;
                    Glyph glyph = r12;
                    Glyph glyph2 = new Glyph(glyphLine.get(glyphLine.idx), xPlacement, yPlacement, 0, 0, gi.idx - glyphLine.idx);
                    glyphLine.set(i, glyph);
                    changed = true;
                }
            }
        }
        glyphLine.idx++;
        return changed;
    }

    /* access modifiers changed from: protected */
    public void readSubTable(int subTableLocation) throws IOException {
        this.openReader.f1214rf.seek((long) subTableLocation);
        this.openReader.f1214rf.readUnsignedShort();
        int classCount = this.openReader.f1214rf.readUnsignedShort();
        int baseArrayLocation = this.openReader.f1214rf.readUnsignedShort() + subTableLocation;
        List<Integer> markCoverage = this.openReader.readCoverageFormat(this.openReader.f1214rf.readUnsignedShort() + subTableLocation);
        List<Integer> baseCoverage = this.openReader.readCoverageFormat(this.openReader.f1214rf.readUnsignedShort() + subTableLocation);
        List<OtfMarkRecord> markRecords = OtfReadCommon.readMarkArray(this.openReader, this.openReader.f1214rf.readUnsignedShort() + subTableLocation);
        MarkToBase markToBase = new MarkToBase();
        for (int k = 0; k < markCoverage.size(); k++) {
            markToBase.marks.put(markCoverage.get(k), markRecords.get(k));
        }
        List<GposAnchor[]> baseArray = OtfReadCommon.readBaseArray(this.openReader, classCount, baseArrayLocation);
        for (int k2 = 0; k2 < baseCoverage.size(); k2++) {
            markToBase.bases.put(baseCoverage.get(k2), baseArray.get(k2));
        }
        this.marksbases.add(markToBase);
    }
}
