package com.itextpdf.p026io.font.otf;

import com.itextpdf.p026io.font.otf.OpenTableLookup;
import com.itextpdf.p026io.util.TextUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: com.itextpdf.io.font.otf.GlyphLine */
public class GlyphLine implements Serializable {
    private static final long serialVersionUID = 4689818013371677649L;
    protected List<ActualText> actualText;
    public int end;
    protected List<Glyph> glyphs;
    public int idx;
    public int start;

    /* renamed from: com.itextpdf.io.font.otf.GlyphLine$IGlyphLineFilter */
    public interface IGlyphLineFilter {
        boolean accept(Glyph glyph);
    }

    public GlyphLine() {
        this.glyphs = new ArrayList();
    }

    public GlyphLine(List<Glyph> glyphs2) {
        this.glyphs = glyphs2;
        this.start = 0;
        this.end = glyphs2.size();
    }

    public GlyphLine(List<Glyph> glyphs2, int start2, int end2) {
        this.glyphs = glyphs2;
        this.start = start2;
        this.end = end2;
    }

    protected GlyphLine(List<Glyph> glyphs2, List<ActualText> actualText2, int start2, int end2) {
        this(glyphs2, start2, end2);
        this.actualText = actualText2;
    }

    public GlyphLine(GlyphLine other) {
        this.glyphs = other.glyphs;
        this.actualText = other.actualText;
        this.start = other.start;
        this.end = other.end;
        this.idx = other.idx;
    }

    public GlyphLine(GlyphLine other, int start2, int end2) {
        this.glyphs = other.glyphs.subList(start2, end2);
        List<ActualText> list = other.actualText;
        if (list != null) {
            this.actualText = list.subList(start2, end2);
        }
        this.start = 0;
        this.end = end2 - start2;
        this.idx = other.idx - start2;
    }

    public String toUnicodeString(int start2, int end2) {
        ActualTextIterator iter = new ActualTextIterator(this, start2, end2);
        StringBuilder str = new StringBuilder();
        while (iter.hasNext()) {
            GlyphLinePart part = iter.next();
            if (part.actualText != null) {
                str.append(part.actualText);
            } else {
                for (int i = part.start; i < part.end; i++) {
                    str.append(this.glyphs.get(i).getUnicodeChars());
                }
            }
        }
        return str.toString();
    }

    public String toString() {
        return toUnicodeString(this.start, this.end);
    }

    public GlyphLine copy(int left, int right) {
        GlyphLine glyphLine = new GlyphLine();
        glyphLine.start = 0;
        glyphLine.end = right - left;
        glyphLine.glyphs = new ArrayList(this.glyphs.subList(left, right));
        glyphLine.actualText = this.actualText == null ? null : new ArrayList(this.actualText.subList(left, right));
        return glyphLine;
    }

    public Glyph get(int index) {
        return this.glyphs.get(index);
    }

    public Glyph set(int index, Glyph glyph) {
        return this.glyphs.set(index, glyph);
    }

    public void add(Glyph glyph) {
        this.glyphs.add(glyph);
        List<ActualText> list = this.actualText;
        if (list != null) {
            list.add((Object) null);
        }
    }

    public void add(int index, Glyph glyph) {
        this.glyphs.add(index, glyph);
        List<ActualText> list = this.actualText;
        if (list != null) {
            list.add(index, (Object) null);
        }
    }

    public void setGlyphs(List<Glyph> replacementGlyphs) {
        this.glyphs = new ArrayList(replacementGlyphs);
        this.start = 0;
        this.end = replacementGlyphs.size();
        this.actualText = null;
    }

    public void add(GlyphLine other) {
        if (other.actualText != null) {
            if (this.actualText == null) {
                this.actualText = new ArrayList(this.glyphs.size());
                for (int i = 0; i < this.glyphs.size(); i++) {
                    this.actualText.add((Object) null);
                }
            }
            this.actualText.addAll(other.actualText.subList(other.start, other.end));
        }
        this.glyphs.addAll(other.glyphs.subList(other.start, other.end));
        if (this.actualText != null) {
            while (this.actualText.size() < this.glyphs.size()) {
                this.actualText.add((Object) null);
            }
        }
    }

    public void replaceContent(GlyphLine other) {
        this.glyphs.clear();
        this.glyphs.addAll(other.glyphs);
        if (other.actualText != null) {
            List<ActualText> list = this.actualText;
            if (list == null) {
                this.actualText = new ArrayList();
            } else {
                list.clear();
            }
            this.actualText.addAll(other.actualText);
        } else {
            this.actualText = null;
        }
        this.start = other.start;
        this.end = other.end;
    }

    public int size() {
        return this.glyphs.size();
    }

    public void substituteManyToOne(OpenTypeFontTableReader tableReader, int lookupFlag, int rightPartLen, int substitutionGlyphIndex) {
        OpenTableLookup.GlyphIndexer gidx = new OpenTableLookup.GlyphIndexer();
        gidx.line = this;
        gidx.idx = this.idx;
        StringBuilder chars = new StringBuilder();
        Glyph currentGlyph = this.glyphs.get(this.idx);
        if (currentGlyph.getChars() != null) {
            chars.append(currentGlyph.getChars());
        } else if (currentGlyph.hasValidUnicode()) {
            chars.append(TextUtil.convertFromUtf32(currentGlyph.getUnicode()));
        }
        for (int j = 0; j < rightPartLen; j++) {
            gidx.nextGlyph(tableReader, lookupFlag);
            Glyph currentGlyph2 = this.glyphs.get(gidx.idx);
            if (currentGlyph2.getChars() != null) {
                chars.append(currentGlyph2.getChars());
            } else if (currentGlyph2.hasValidUnicode()) {
                chars.append(TextUtil.convertFromUtf32(currentGlyph2.getUnicode()));
            }
            int i = gidx.idx;
            gidx.idx = i - 1;
            removeGlyph(i);
        }
        char[] newChars = new char[chars.length()];
        chars.getChars(0, chars.length(), newChars, 0);
        Glyph newGlyph = tableReader.getGlyph(substitutionGlyphIndex);
        newGlyph.setChars(newChars);
        this.glyphs.set(this.idx, newGlyph);
        this.end -= rightPartLen;
    }

    public void substituteOneToOne(OpenTypeFontTableReader tableReader, int substitutionGlyphIndex) {
        Glyph oldGlyph = this.glyphs.get(this.idx);
        Glyph newGlyph = tableReader.getGlyph(substitutionGlyphIndex);
        if (oldGlyph.getChars() != null) {
            newGlyph.setChars(oldGlyph.getChars());
        } else if (newGlyph.hasValidUnicode()) {
            newGlyph.setChars(TextUtil.convertFromUtf32(newGlyph.getUnicode()));
        } else if (oldGlyph.hasValidUnicode()) {
            newGlyph.setChars(TextUtil.convertFromUtf32(oldGlyph.getUnicode()));
        }
        this.glyphs.set(this.idx, newGlyph);
    }

    public void substituteOneToMany(OpenTypeFontTableReader tableReader, int[] substGlyphIds) {
        int substCode = substGlyphIds[0];
        Glyph oldGlyph = this.glyphs.get(this.idx);
        this.glyphs.set(this.idx, tableReader.getGlyph(substCode));
        if (substGlyphIds.length > 1) {
            List<Glyph> additionalGlyphs = new ArrayList<>(substGlyphIds.length - 1);
            for (int i = 1; i < substGlyphIds.length; i++) {
                additionalGlyphs.add(tableReader.getGlyph(substGlyphIds[i]));
            }
            addAllGlyphs(this.idx + 1, additionalGlyphs);
            List<ActualText> list = this.actualText;
            if (list != null) {
                if (list.get(this.idx) == null) {
                    this.actualText.set(this.idx, new ActualText(oldGlyph.getUnicodeString()));
                }
                for (int i2 = 0; i2 < additionalGlyphs.size(); i2++) {
                    List<ActualText> list2 = this.actualText;
                    int i3 = this.idx;
                    list2.set(i3 + 1 + i2, list2.get(i3));
                }
            }
            this.idx += substGlyphIds.length - 1;
            this.end += substGlyphIds.length - 1;
        }
    }

    public GlyphLine filter(IGlyphLineFilter filter) {
        boolean anythingFiltered = false;
        List<Glyph> filteredGlyphs = new ArrayList<>(this.end - this.start);
        List<ActualText> filteredActualText = this.actualText != null ? new ArrayList<>(this.end - this.start) : null;
        for (int i = this.start; i < this.end; i++) {
            if (filter.accept(this.glyphs.get(i))) {
                filteredGlyphs.add(this.glyphs.get(i));
                if (filteredActualText != null) {
                    filteredActualText.add(this.actualText.get(i));
                }
            } else {
                anythingFiltered = true;
            }
        }
        if (anythingFiltered) {
            return new GlyphLine(filteredGlyphs, filteredActualText, 0, filteredGlyphs.size());
        }
        return this;
    }

    public void setActualText(int left, int right, String text) {
        if (this.actualText == null) {
            this.actualText = new ArrayList(this.glyphs.size());
            for (int i = 0; i < this.glyphs.size(); i++) {
                this.actualText.add((Object) null);
            }
        }
        ActualText actualText2 = new ActualText(text);
        for (int i2 = left; i2 < right; i2++) {
            this.actualText.set(i2, actualText2);
        }
    }

    public Iterator<GlyphLinePart> iterator() {
        return new ActualTextIterator(this);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        GlyphLine other = (GlyphLine) obj;
        if (this.end - this.start != other.end - other.start) {
            return false;
        }
        List<ActualText> list = this.actualText;
        if ((list == null && other.actualText != null) || (list != null && other.actualText == null)) {
            return false;
        }
        for (int i = this.start; i < this.end; i++) {
            int otherPos = (other.start + i) - this.start;
            Glyph myGlyph = get(i);
            Glyph otherGlyph = other.get(otherPos);
            if ((myGlyph == null && otherGlyph != null) || (myGlyph != null && !myGlyph.equals(otherGlyph))) {
                return false;
            }
            List<ActualText> list2 = this.actualText;
            ActualText otherAT = null;
            ActualText myAT = list2 == null ? null : list2.get(i);
            List<ActualText> list3 = other.actualText;
            if (list3 != null) {
                otherAT = list3.get(otherPos);
            }
            if ((myAT == null && otherAT != null) || (myAT != null && !myAT.equals(otherAT))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int result;
        int result2 = (((0 * 31) + this.start) * 31) + this.end;
        for (int i = this.start; i < this.end; i++) {
            result2 = (result * 31) + this.glyphs.get(i).hashCode();
        }
        if (this.actualText != null) {
            for (int i2 = this.start; i2 < this.end; i2++) {
                result *= 31;
                if (this.actualText.get(i2) != null) {
                    result += this.actualText.get(i2).hashCode();
                }
            }
        }
        return result;
    }

    private void removeGlyph(int index) {
        this.glyphs.remove(index);
        List<ActualText> list = this.actualText;
        if (list != null) {
            list.remove(index);
        }
    }

    private void addAllGlyphs(int index, List<Glyph> additionalGlyphs) {
        this.glyphs.addAll(index, additionalGlyphs);
        if (this.actualText != null) {
            for (int i = 0; i < additionalGlyphs.size(); i++) {
                this.actualText.add(index, (Object) null);
            }
        }
    }

    /* renamed from: com.itextpdf.io.font.otf.GlyphLine$GlyphLinePart */
    public static class GlyphLinePart {
        public String actualText;
        public int end;
        public boolean reversed;
        public int start;

        public GlyphLinePart(int start2, int end2) {
            this(start2, end2, (String) null);
        }

        public GlyphLinePart(int start2, int end2, String actualText2) {
            this.start = start2;
            this.end = end2;
            this.actualText = actualText2;
        }

        public GlyphLinePart setReversed(boolean reversed2) {
            this.reversed = reversed2;
            return this;
        }
    }

    /* renamed from: com.itextpdf.io.font.otf.GlyphLine$ActualText */
    protected static class ActualText implements Serializable {
        private static final long serialVersionUID = 5109920013485372966L;
        public String value;

        public ActualText(String value2) {
            this.value = value2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ActualText other = (ActualText) obj;
            String str = this.value;
            if ((str != null || other.value != null) && !str.equals(other.value)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return this.value.hashCode() * 31;
        }
    }
}
