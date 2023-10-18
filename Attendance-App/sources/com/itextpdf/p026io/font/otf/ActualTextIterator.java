package com.itextpdf.p026io.font.otf;

import com.itextpdf.p026io.font.otf.GlyphLine;
import com.itextpdf.p026io.util.TextUtil;
import java.util.Iterator;

/* renamed from: com.itextpdf.io.font.otf.ActualTextIterator */
public class ActualTextIterator implements Iterator<GlyphLine.GlyphLinePart> {
    private GlyphLine glyphLine;
    private int pos;

    public ActualTextIterator(GlyphLine glyphLine2) {
        this.glyphLine = glyphLine2;
        this.pos = glyphLine2.start;
    }

    public ActualTextIterator(GlyphLine glyphLine2, int start, int end) {
        this(new GlyphLine(glyphLine2.glyphs, glyphLine2.actualText, start, end));
    }

    public boolean hasNext() {
        return this.pos < this.glyphLine.end;
    }

    public GlyphLine.GlyphLinePart next() {
        GlyphLine.GlyphLinePart nextResult;
        if (this.glyphLine.actualText == null) {
            GlyphLine.GlyphLinePart result = new GlyphLine.GlyphLinePart(this.pos, this.glyphLine.end, (String) null);
            this.pos = this.glyphLine.end;
            return result;
        }
        GlyphLine.GlyphLinePart currentResult = nextGlyphLinePart(this.pos);
        if (currentResult == null) {
            return null;
        }
        this.pos = currentResult.end;
        if (!glyphLinePartNeedsActualText(currentResult)) {
            currentResult.actualText = null;
            while (this.pos < this.glyphLine.end && (nextResult = nextGlyphLinePart(this.pos)) != null && !glyphLinePartNeedsActualText(nextResult)) {
                currentResult.end = nextResult.end;
                this.pos = nextResult.end;
            }
        }
        return currentResult;
    }

    public void remove() {
        throw new IllegalStateException("Operation not supported");
    }

    private GlyphLine.GlyphLinePart nextGlyphLinePart(int pos2) {
        String str = null;
        if (pos2 >= this.glyphLine.end) {
            return null;
        }
        int startPos = pos2;
        GlyphLine.ActualText startActualText = this.glyphLine.actualText.get(pos2);
        while (pos2 < this.glyphLine.end && this.glyphLine.actualText.get(pos2) == startActualText) {
            pos2++;
        }
        if (startActualText != null) {
            str = startActualText.value;
        }
        return new GlyphLine.GlyphLinePart(startPos, pos2, str);
    }

    private boolean glyphLinePartNeedsActualText(GlyphLine.GlyphLinePart glyphLinePart) {
        if (glyphLinePart.actualText == null) {
            return false;
        }
        boolean needsActualText = false;
        StringBuilder toUnicodeMapResult = new StringBuilder();
        int i = glyphLinePart.start;
        while (true) {
            if (i >= glyphLinePart.end) {
                break;
            }
            Glyph currentGlyph = this.glyphLine.glyphs.get(i);
            if (!currentGlyph.hasValidUnicode()) {
                needsActualText = true;
                break;
            }
            toUnicodeMapResult.append(TextUtil.convertFromUtf32(currentGlyph.getUnicode()));
            i++;
        }
        if (needsActualText || !toUnicodeMapResult.toString().equals(glyphLinePart.actualText)) {
            return true;
        }
        return false;
    }
}
