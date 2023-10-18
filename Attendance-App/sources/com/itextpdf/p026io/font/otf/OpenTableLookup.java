package com.itextpdf.p026io.font.otf;

import java.io.IOException;
import java.io.Serializable;

/* renamed from: com.itextpdf.io.font.otf.OpenTableLookup */
public abstract class OpenTableLookup implements Serializable {
    private static final long serialVersionUID = 8381791136767127636L;
    protected int lookupFlag;
    protected OpenTypeFontTableReader openReader;
    protected int[] subTableLocations;

    /* access modifiers changed from: protected */
    public abstract void readSubTable(int i) throws IOException;

    public abstract boolean transformOne(GlyphLine glyphLine);

    protected OpenTableLookup(OpenTypeFontTableReader openReader2, int lookupFlag2, int[] subTableLocations2) {
        this.lookupFlag = lookupFlag2;
        this.subTableLocations = subTableLocations2;
        this.openReader = openReader2;
    }

    public int getLookupFlag() {
        return this.lookupFlag;
    }

    public boolean transformLine(GlyphLine line) {
        boolean changed = false;
        line.idx = line.start;
        while (line.idx < line.end && line.idx >= line.start) {
            changed = transformOne(line) || changed;
        }
        return changed;
    }

    public boolean hasSubstitution(int index) {
        return false;
    }

    /* access modifiers changed from: protected */
    public void readSubTables() throws IOException {
        for (int subTableLocation : this.subTableLocations) {
            readSubTable(subTableLocation);
        }
    }

    /* renamed from: com.itextpdf.io.font.otf.OpenTableLookup$GlyphIndexer */
    public static class GlyphIndexer {
        public Glyph glyph;
        public int idx;
        public GlyphLine line;

        public void nextGlyph(OpenTypeFontTableReader openReader, int lookupFlag) {
            Glyph g;
            this.glyph = null;
            do {
                int i = this.idx + 1;
                this.idx = i;
                if (i < this.line.end) {
                    g = this.line.get(this.idx);
                } else {
                    return;
                }
            } while (openReader.isSkip(g.getCode(), lookupFlag));
            this.glyph = g;
        }

        public void previousGlyph(OpenTypeFontTableReader openReader, int lookupFlag) {
            Glyph g;
            this.glyph = null;
            do {
                int i = this.idx - 1;
                this.idx = i;
                if (i >= this.line.start) {
                    g = this.line.get(this.idx);
                } else {
                    return;
                }
            } while (openReader.isSkip(g.getCode(), lookupFlag));
            this.glyph = g;
        }
    }
}
