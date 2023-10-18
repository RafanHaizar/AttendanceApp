package com.itextpdf.p026io.font.otf;

import com.itextpdf.p026io.font.otf.OpenTableLookup;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* renamed from: com.itextpdf.io.font.otf.GposLookupType5 */
public class GposLookupType5 extends OpenTableLookup {
    private static final long serialVersionUID = 6409145706785333023L;
    private final List<MarkToLigature> marksligatures = new ArrayList();

    /* renamed from: com.itextpdf.io.font.otf.GposLookupType5$MarkToLigature */
    public static class MarkToLigature implements Serializable {
        private static final long serialVersionUID = 4249432630962669432L;
        public final Map<Integer, List<GposAnchor[]>> ligatures = new HashMap();
        public final Map<Integer, OtfMarkRecord> marks = new HashMap();
    }

    public GposLookupType5(OpenTypeFontTableReader openReader, int lookupFlag, int[] subTableLocations) throws IOException {
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
        OpenTableLookup.GlyphIndexer ligatureGlyphIndexer = null;
        Iterator<MarkToLigature> it = this.marksligatures.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            MarkToLigature mb = it.next();
            OtfMarkRecord omr = mb.marks.get(Integer.valueOf(glyphLine.get(glyphLine.idx).getCode()));
            if (omr != null) {
                if (ligatureGlyphIndexer == null) {
                    OpenTableLookup.GlyphIndexer ligatureGlyphIndexer2 = new OpenTableLookup.GlyphIndexer();
                    ligatureGlyphIndexer2.idx = glyphLine.idx;
                    ligatureGlyphIndexer2.line = glyphLine;
                    do {
                        ligatureGlyphIndexer2.previousGlyph(this.openReader, this.lookupFlag);
                        if (ligatureGlyphIndexer2.glyph == null || !mb.marks.containsKey(Integer.valueOf(ligatureGlyphIndexer2.glyph.getCode()))) {
                        }
                        ligatureGlyphIndexer2.previousGlyph(this.openReader, this.lookupFlag);
                        break;
                    } while (!mb.marks.containsKey(Integer.valueOf(ligatureGlyphIndexer2.glyph.getCode())));
                    if (ligatureGlyphIndexer2.glyph == null) {
                        OpenTableLookup.GlyphIndexer glyphIndexer = ligatureGlyphIndexer2;
                        break;
                    }
                    ligatureGlyphIndexer = ligatureGlyphIndexer2;
                }
                List<GposAnchor[]> componentAnchors = mb.ligatures.get(Integer.valueOf(ligatureGlyphIndexer.glyph.getCode()));
                if (componentAnchors != null) {
                    int markClass = omr.markClass;
                    int component = componentAnchors.size() - 1;
                    while (true) {
                        if (component < 0) {
                            break;
                        } else if (componentAnchors.get(component)[markClass] != null) {
                            GposAnchor baseAnchor = componentAnchors.get(component)[markClass];
                            GposAnchor markAnchor = omr.anchor;
                            int i = glyphLine.idx;
                            Glyph glyph = r12;
                            Glyph glyph2 = new Glyph(glyphLine.get(glyphLine.idx), baseAnchor.XCoordinate - markAnchor.XCoordinate, baseAnchor.YCoordinate - markAnchor.YCoordinate, 0, 0, ligatureGlyphIndexer.idx - glyphLine.idx);
                            glyphLine.set(i, glyph);
                            changed = true;
                            break;
                        } else {
                            component--;
                        }
                    }
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
        int ligatureArrayLocation = this.openReader.f1214rf.readUnsignedShort() + subTableLocation;
        List<Integer> markCoverage = this.openReader.readCoverageFormat(this.openReader.f1214rf.readUnsignedShort() + subTableLocation);
        List<Integer> ligatureCoverage = this.openReader.readCoverageFormat(this.openReader.f1214rf.readUnsignedShort() + subTableLocation);
        List<OtfMarkRecord> markRecords = OtfReadCommon.readMarkArray(this.openReader, this.openReader.f1214rf.readUnsignedShort() + subTableLocation);
        MarkToLigature markToLigature = new MarkToLigature();
        for (int k = 0; k < markCoverage.size(); k++) {
            markToLigature.marks.put(markCoverage.get(k), markRecords.get(k));
        }
        List<List<GposAnchor[]>> ligatureArray = OtfReadCommon.readLigatureArray(this.openReader, classCount, ligatureArrayLocation);
        for (int k2 = 0; k2 < ligatureCoverage.size(); k2++) {
            markToLigature.ligatures.put(ligatureCoverage.get(k2), ligatureArray.get(k2));
        }
        this.marksligatures.add(markToLigature);
    }
}
