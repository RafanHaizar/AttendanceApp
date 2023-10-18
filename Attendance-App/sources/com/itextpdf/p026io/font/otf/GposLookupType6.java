package com.itextpdf.p026io.font.otf;

import com.itextpdf.p026io.font.otf.OpenTableLookup;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* renamed from: com.itextpdf.io.font.otf.GposLookupType6 */
public class GposLookupType6 extends OpenTableLookup {
    private static final long serialVersionUID = -2213669257401436260L;
    private final List<MarkToBaseMark> marksbases = new ArrayList();

    public GposLookupType6(OpenTypeFontTableReader openReader, int lookupFlag, int[] subTableLocations) throws IOException {
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
        Iterator<MarkToBaseMark> it = this.marksbases.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            MarkToBaseMark mb = it.next();
            OtfMarkRecord omr = mb.marks.get(Integer.valueOf(glyphLine.get(glyphLine.idx).getCode()));
            if (omr != null) {
                if (gi == null) {
                    OpenTableLookup.GlyphIndexer gi2 = new OpenTableLookup.GlyphIndexer();
                    gi2.idx = glyphLine.idx;
                    gi2.line = glyphLine;
                    while (true) {
                        int prev = gi2.idx;
                        boolean foundBaseGlyph = false;
                        gi2.previousGlyph(this.openReader, this.lookupFlag);
                        if (gi2.idx != -1) {
                            int i = gi2.idx;
                            while (true) {
                                if (i >= prev) {
                                    break;
                                } else if (this.openReader.getGlyphClass(glyphLine.get(i).getCode()) == 1) {
                                    foundBaseGlyph = true;
                                    break;
                                } else {
                                    i++;
                                }
                            }
                        }
                        if (!foundBaseGlyph) {
                            if (gi2.glyph != null) {
                                if (mb.baseMarks.containsKey(Integer.valueOf(gi2.glyph.getCode()))) {
                                    break;
                                }
                            } else {
                                break;
                            }
                        } else {
                            gi2.glyph = null;
                            break;
                        }
                    }
                    if (gi2.glyph == null) {
                        OpenTableLookup.GlyphIndexer glyphIndexer = gi2;
                        break;
                    }
                    gi = gi2;
                }
                GposAnchor[] gpas = mb.baseMarks.get(Integer.valueOf(gi.glyph.getCode()));
                if (gpas != null) {
                    GposAnchor baseAnchor = gpas[omr.markClass];
                    GposAnchor markAnchor = omr.anchor;
                    int i2 = glyphLine.idx;
                    Glyph glyph = r12;
                    Glyph glyph2 = new Glyph(glyphLine.get(glyphLine.idx), baseAnchor.XCoordinate + (-markAnchor.XCoordinate), baseAnchor.YCoordinate + (-markAnchor.YCoordinate), 0, 0, gi.idx - glyphLine.idx);
                    glyphLine.set(i2, glyph);
                    changed = true;
                    break;
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
        MarkToBaseMark markToBaseMark = new MarkToBaseMark();
        for (int k = 0; k < markCoverage.size(); k++) {
            markToBaseMark.marks.put(markCoverage.get(k), markRecords.get(k));
        }
        List<GposAnchor[]> baseArray = OtfReadCommon.readBaseArray(this.openReader, classCount, baseArrayLocation);
        for (int k2 = 0; k2 < baseCoverage.size(); k2++) {
            markToBaseMark.baseMarks.put(baseCoverage.get(k2), baseArray.get(k2));
        }
        this.marksbases.add(markToBaseMark);
    }

    /* renamed from: com.itextpdf.io.font.otf.GposLookupType6$MarkToBaseMark */
    private static class MarkToBaseMark implements Serializable {
        private static final long serialVersionUID = -2097614797893579206L;
        public final Map<Integer, GposAnchor[]> baseMarks;
        public final Map<Integer, OtfMarkRecord> marks;

        private MarkToBaseMark() {
            this.marks = new HashMap();
            this.baseMarks = new HashMap();
        }
    }
}
