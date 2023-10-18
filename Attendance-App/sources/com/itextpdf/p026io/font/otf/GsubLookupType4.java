package com.itextpdf.p026io.font.otf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.itextpdf.io.font.otf.GsubLookupType4 */
public class GsubLookupType4 extends OpenTableLookup {
    private static final long serialVersionUID = -8106254947137506056L;
    private Map<Integer, List<int[]>> ligatures = new HashMap();

    public GsubLookupType4(OpenTypeFontTableReader openReader, int lookupFlag, int[] subTableLocations) throws IOException {
        super(openReader, lookupFlag, subTableLocations);
        readSubTables();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0079, code lost:
        continue;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean transformOne(com.itextpdf.p026io.font.otf.GlyphLine r12) {
        /*
            r11 = this;
            int r0 = r12.idx
            int r1 = r12.end
            r2 = 0
            if (r0 < r1) goto L_0x0008
            return r2
        L_0x0008:
            r0 = 0
            int r1 = r12.idx
            com.itextpdf.io.font.otf.Glyph r1 = r12.get(r1)
            r3 = 0
            java.util.Map<java.lang.Integer, java.util.List<int[]>> r4 = r11.ligatures
            int r5 = r1.getCode()
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            boolean r4 = r4.containsKey(r5)
            if (r4 == 0) goto L_0x0089
            com.itextpdf.io.font.otf.OpenTypeFontTableReader r4 = r11.openReader
            int r5 = r1.getCode()
            int r6 = r11.lookupFlag
            boolean r4 = r4.isSkip(r5, r6)
            if (r4 != 0) goto L_0x0089
            com.itextpdf.io.font.otf.OpenTableLookup$GlyphIndexer r4 = new com.itextpdf.io.font.otf.OpenTableLookup$GlyphIndexer
            r4.<init>()
            r4.line = r12
            java.util.Map<java.lang.Integer, java.util.List<int[]>> r5 = r11.ligatures
            int r6 = r1.getCode()
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            java.lang.Object r5 = r5.get(r6)
            java.util.List r5 = (java.util.List) r5
            java.util.Iterator r6 = r5.iterator()
        L_0x0049:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L_0x0089
            java.lang.Object r7 = r6.next()
            int[] r7 = (int[]) r7
            r3 = 1
            int r8 = r12.idx
            r4.idx = r8
            r8 = 1
        L_0x005b:
            int r9 = r7.length
            if (r8 >= r9) goto L_0x0079
            com.itextpdf.io.font.otf.OpenTypeFontTableReader r9 = r11.openReader
            int r10 = r11.lookupFlag
            r4.nextGlyph(r9, r10)
            com.itextpdf.io.font.otf.Glyph r9 = r4.glyph
            if (r9 == 0) goto L_0x0077
            com.itextpdf.io.font.otf.Glyph r9 = r4.glyph
            int r9 = r9.getCode()
            r10 = r7[r8]
            if (r9 == r10) goto L_0x0074
            goto L_0x0077
        L_0x0074:
            int r8 = r8 + 1
            goto L_0x005b
        L_0x0077:
            r3 = 0
        L_0x0079:
            if (r3 == 0) goto L_0x0088
            com.itextpdf.io.font.otf.OpenTypeFontTableReader r6 = r11.openReader
            int r8 = r11.lookupFlag
            int r9 = r7.length
            int r9 = r9 + -1
            r2 = r7[r2]
            r12.substituteManyToOne(r6, r8, r9, r2)
            goto L_0x0089
        L_0x0088:
            goto L_0x0049
        L_0x0089:
            if (r3 == 0) goto L_0x008c
            r0 = 1
        L_0x008c:
            int r2 = r12.idx
            int r2 = r2 + 1
            r12.idx = r2
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.font.otf.GsubLookupType4.transformOne(com.itextpdf.io.font.otf.GlyphLine):boolean");
    }

    /* access modifiers changed from: protected */
    public void readSubTable(int subTableLocation) throws IOException {
        this.openReader.f1214rf.seek((long) subTableLocation);
        this.openReader.f1214rf.readShort();
        int coverage = this.openReader.f1214rf.readUnsignedShort() + subTableLocation;
        int ligSetCount = this.openReader.f1214rf.readUnsignedShort();
        int[] ligatureSet = new int[ligSetCount];
        for (int k = 0; k < ligSetCount; k++) {
            ligatureSet[k] = this.openReader.f1214rf.readUnsignedShort() + subTableLocation;
        }
        List<Integer> coverageGlyphIds = this.openReader.readCoverageFormat(coverage);
        for (int k2 = 0; k2 < ligSetCount; k2++) {
            this.openReader.f1214rf.seek((long) ligatureSet[k2]);
            int ligatureCount = this.openReader.f1214rf.readUnsignedShort();
            int[] ligature = new int[ligatureCount];
            for (int j = 0; j < ligatureCount; j++) {
                ligature[j] = this.openReader.f1214rf.readUnsignedShort() + ligatureSet[k2];
            }
            List<int[]> components = new ArrayList<>(ligatureCount);
            for (int j2 = 0; j2 < ligatureCount; j2++) {
                this.openReader.f1214rf.seek((long) ligature[j2]);
                int ligGlyph = this.openReader.f1214rf.readUnsignedShort();
                int compCount = this.openReader.f1214rf.readUnsignedShort();
                int[] component = new int[compCount];
                component[0] = ligGlyph;
                for (int i = 1; i < compCount; i++) {
                    component[i] = this.openReader.f1214rf.readUnsignedShort();
                }
                components.add(component);
            }
            this.ligatures.put(coverageGlyphIds.get(k2), components);
        }
    }
}
