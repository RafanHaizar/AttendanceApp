package com.itextpdf.kernel.font;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfOutputStream;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.p026io.font.FontEncoding;
import com.itextpdf.p026io.font.FontMetrics;
import com.itextpdf.p026io.font.FontNames;
import com.itextpdf.p026io.font.FontProgram;
import com.itextpdf.p026io.font.cmap.CMapToUnicode;
import com.itextpdf.p026io.font.constants.FontDescriptorFlags;
import com.itextpdf.p026io.font.otf.Glyph;
import com.itextpdf.p026io.font.otf.GlyphLine;
import com.itextpdf.p026io.util.ArrayUtil;
import com.itextpdf.p026io.util.StreamUtil;
import com.itextpdf.p026io.util.TextUtil;
import java.util.ArrayList;
import java.util.List;
import kotlin.UByte;

public abstract class PdfSimpleFont<T extends FontProgram> extends PdfFont {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long serialVersionUID = -4942318223894676176L;
    protected FontEncoding fontEncoding;
    protected boolean forceWidthsOutput = false;
    protected byte[] shortTag = new byte[256];
    protected CMapToUnicode toUnicode;

    /* access modifiers changed from: protected */
    public abstract void addFontStream(PdfDictionary pdfDictionary);

    protected PdfSimpleFont(PdfDictionary fontDictionary) {
        super(fontDictionary);
        this.toUnicode = FontUtil.processToUnicode(fontDictionary.get(PdfName.ToUnicode));
    }

    protected PdfSimpleFont() {
    }

    public boolean isBuiltWith(String fontProgram, String encoding) {
        return getFontProgram().isBuiltWith(fontProgram) && this.fontEncoding.isBuiltWith(encoding);
    }

    public GlyphLine createGlyphLine(String content) {
        List<Glyph> glyphs = new ArrayList<>(content.length());
        if (this.fontEncoding.isFontSpecific()) {
            for (int i = 0; i < content.length(); i++) {
                Glyph glyph = this.fontProgram.getGlyphByCode(content.charAt(i));
                if (glyph != null) {
                    glyphs.add(glyph);
                }
            }
        } else {
            for (int i2 = 0; i2 < content.length(); i2++) {
                Glyph glyph2 = getGlyph(content.charAt(i2));
                if (glyph2 != null) {
                    glyphs.add(glyph2);
                }
            }
        }
        return new GlyphLine(glyphs);
    }

    public int appendGlyphs(String text, int from, int to, List<Glyph> glyphs) {
        int processed = 0;
        if (this.fontEncoding.isFontSpecific()) {
            for (int i = from; i <= to; i++) {
                Glyph glyph = this.fontProgram.getGlyphByCode(text.charAt(i) & 255);
                if (glyph == null) {
                    break;
                }
                glyphs.add(glyph);
                processed++;
            }
        } else {
            for (int i2 = from; i2 <= to; i2++) {
                Glyph glyph2 = getGlyph(text.charAt(i2));
                if (glyph2 == null || (!containsGlyph(glyph2.getUnicode()) && !isAppendableGlyph(glyph2))) {
                    if (glyph2 != null || !TextUtil.isWhitespaceOrNonPrintable(text.charAt(i2))) {
                        break;
                    }
                } else {
                    glyphs.add(glyph2);
                }
                processed++;
            }
        }
        return processed;
    }

    public int appendAnyGlyph(String text, int from, List<Glyph> glyphs) {
        Glyph glyph;
        if (this.fontEncoding.isFontSpecific()) {
            glyph = this.fontProgram.getGlyphByCode(text.charAt(from));
        } else {
            glyph = getGlyph(text.charAt(from));
        }
        if (glyph == null) {
            return 1;
        }
        glyphs.add(glyph);
        return 1;
    }

    private boolean isAppendableGlyph(Glyph glyph) {
        return glyph.getCode() > 0 || TextUtil.isWhitespaceOrNonPrintable(glyph.getUnicode());
    }

    public FontEncoding getFontEncoding() {
        return this.fontEncoding;
    }

    public byte[] convertToBytes(String text) {
        byte[] bytes = this.fontEncoding.convertToBytes(text);
        for (byte b : bytes) {
            this.shortTag[b & UByte.MAX_VALUE] = 1;
        }
        return bytes;
    }

    public byte[] convertToBytes(GlyphLine glyphLine) {
        if (glyphLine == null) {
            return EMPTY_BYTES;
        }
        byte[] bytes = new byte[glyphLine.size()];
        int ptr = 0;
        if (this.fontEncoding.isFontSpecific()) {
            int i = 0;
            while (i < glyphLine.size()) {
                bytes[ptr] = (byte) glyphLine.get(i).getCode();
                i++;
                ptr++;
            }
        } else {
            for (int i2 = 0; i2 < glyphLine.size(); i2++) {
                if (this.fontEncoding.canEncode(glyphLine.get(i2).getUnicode())) {
                    bytes[ptr] = (byte) this.fontEncoding.convertToByte(glyphLine.get(i2).getUnicode());
                    ptr++;
                }
            }
        }
        byte[] bytes2 = ArrayUtil.shortenArray(bytes, ptr);
        for (byte b : bytes2) {
            this.shortTag[b & UByte.MAX_VALUE] = 1;
        }
        return bytes2;
    }

    public byte[] convertToBytes(Glyph glyph) {
        byte[] bytes = new byte[1];
        if (this.fontEncoding.isFontSpecific()) {
            bytes[0] = (byte) glyph.getCode();
        } else if (!this.fontEncoding.canEncode(glyph.getUnicode())) {
            return EMPTY_BYTES;
        } else {
            bytes[0] = (byte) this.fontEncoding.convertToByte(glyph.getUnicode());
        }
        this.shortTag[bytes[0] & UByte.MAX_VALUE] = 1;
        return bytes;
    }

    public void writeText(GlyphLine text, int from, int to, PdfOutputStream stream) {
        byte[] bytes = new byte[((to - from) + 1)];
        int ptr = 0;
        if (this.fontEncoding.isFontSpecific()) {
            int i = from;
            while (i <= to) {
                bytes[ptr] = (byte) text.get(i).getCode();
                i++;
                ptr++;
            }
        } else {
            for (int i2 = from; i2 <= to; i2++) {
                if (this.fontEncoding.canEncode(text.get(i2).getUnicode())) {
                    bytes[ptr] = (byte) this.fontEncoding.convertToByte(text.get(i2).getUnicode());
                    ptr++;
                }
            }
        }
        byte[] bytes2 = ArrayUtil.shortenArray(bytes, ptr);
        for (byte b : bytes2) {
            this.shortTag[b & UByte.MAX_VALUE] = 1;
        }
        StreamUtil.writeEscapedString(stream, bytes2);
    }

    public void writeText(String text, PdfOutputStream stream) {
        StreamUtil.writeEscapedString(stream, convertToBytes(text));
    }

    public String decode(PdfString content) {
        return decodeIntoGlyphLine(content).toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0064  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0067 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.p026io.font.otf.GlyphLine decodeIntoGlyphLine(com.itextpdf.kernel.pdf.PdfString r10) {
        /*
            r9 = this;
            byte[] r0 = r10.getValueBytes()
            java.util.ArrayList r1 = new java.util.ArrayList
            int r2 = r0.length
            r1.<init>(r2)
            int r2 = r0.length
            r3 = 0
        L_0x000c:
            if (r3 >= r2) goto L_0x006a
            byte r4 = r0[r3]
            r5 = r4 & 255(0xff, float:3.57E-43)
            r6 = 0
            com.itextpdf.io.font.cmap.CMapToUnicode r7 = r9.toUnicode
            if (r7 == 0) goto L_0x0046
            char[] r7 = r7.lookup((int) r5)
            if (r7 == 0) goto L_0x0046
            com.itextpdf.io.font.FontProgram r7 = r9.fontProgram
            com.itextpdf.io.font.otf.Glyph r7 = r7.getGlyphByCode(r5)
            r6 = r7
            if (r7 == 0) goto L_0x0046
            com.itextpdf.io.font.cmap.CMapToUnicode r7 = r9.toUnicode
            char[] r7 = r7.lookup((int) r5)
            char[] r8 = r6.getChars()
            boolean r7 = java.util.Arrays.equals(r7, r8)
            if (r7 != 0) goto L_0x0062
            com.itextpdf.io.font.otf.Glyph r7 = new com.itextpdf.io.font.otf.Glyph
            r7.<init>(r6)
            r6 = r7
            com.itextpdf.io.font.cmap.CMapToUnicode r7 = r9.toUnicode
            char[] r7 = r7.lookup((int) r5)
            r6.setChars(r7)
            goto L_0x0062
        L_0x0046:
            com.itextpdf.io.font.FontEncoding r7 = r9.fontEncoding
            int r7 = r7.getUnicode(r5)
            r8 = -1
            if (r7 <= r8) goto L_0x0054
            com.itextpdf.io.font.otf.Glyph r6 = r9.getGlyph(r7)
            goto L_0x0062
        L_0x0054:
            com.itextpdf.io.font.FontEncoding r8 = r9.fontEncoding
            java.lang.String r8 = r8.getBaseEncoding()
            if (r8 != 0) goto L_0x0062
            com.itextpdf.io.font.FontProgram r8 = r9.fontProgram
            com.itextpdf.io.font.otf.Glyph r6 = r8.getGlyphByCode(r5)
        L_0x0062:
            if (r6 == 0) goto L_0x0067
            r1.add(r6)
        L_0x0067:
            int r3 = r3 + 1
            goto L_0x000c
        L_0x006a:
            com.itextpdf.io.font.otf.GlyphLine r2 = new com.itextpdf.io.font.otf.GlyphLine
            r2.<init>((java.util.List<com.itextpdf.p026io.font.otf.Glyph>) r1)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.font.PdfSimpleFont.decodeIntoGlyphLine(com.itextpdf.kernel.pdf.PdfString):com.itextpdf.io.font.otf.GlyphLine");
    }

    public float getContentWidth(PdfString content) {
        float width = 0.0f;
        GlyphLine glyphLine = decodeIntoGlyphLine(content);
        for (int i = glyphLine.start; i < glyphLine.end; i++) {
            width += (float) glyphLine.get(i).getWidth();
        }
        return width;
    }

    public boolean isForceWidthsOutput() {
        return this.forceWidthsOutput;
    }

    public void setForceWidthsOutput(boolean forceWidthsOutput2) {
        this.forceWidthsOutput = forceWidthsOutput2;
    }

    /* access modifiers changed from: protected */
    public void flushFontData(String fontName, PdfName subtype) {
        ((PdfDictionary) getPdfObject()).put(PdfName.Subtype, subtype);
        if (fontName != null && fontName.length() > 0) {
            ((PdfDictionary) getPdfObject()).put(PdfName.BaseFont, new PdfName(fontName));
        }
        int firstChar = 0;
        while (firstChar <= 255 && this.shortTag[firstChar] == 0) {
            firstChar++;
        }
        int lastChar = 255;
        while (lastChar >= firstChar && this.shortTag[lastChar] == 0) {
            lastChar--;
        }
        if (firstChar > 255) {
            firstChar = 255;
            lastChar = 255;
        }
        if (!isSubset() || !isEmbedded()) {
            firstChar = 0;
            lastChar = this.shortTag.length - 1;
            for (int k = 0; k < this.shortTag.length; k++) {
                if (this.fontEncoding.canDecode(k)) {
                    this.shortTag[k] = 1;
                } else if (this.fontEncoding.hasDifferences() || this.fontProgram.getGlyphByCode(k) == null) {
                    this.shortTag[k] = 0;
                } else {
                    this.shortTag[k] = 1;
                }
            }
        }
        if (this.fontEncoding.hasDifferences()) {
            int k2 = firstChar;
            while (true) {
                if (k2 > lastChar) {
                    break;
                } else if (!FontEncoding.NOTDEF.equals(this.fontEncoding.getDifference(k2))) {
                    firstChar = k2;
                    break;
                } else {
                    k2++;
                }
            }
            int k3 = lastChar;
            while (true) {
                if (k3 < firstChar) {
                    break;
                } else if (!FontEncoding.NOTDEF.equals(this.fontEncoding.getDifference(k3))) {
                    lastChar = k3;
                    break;
                } else {
                    k3--;
                }
            }
            PdfDictionary enc = new PdfDictionary();
            enc.put(PdfName.Type, PdfName.Encoding);
            PdfArray diff = new PdfArray();
            boolean gap = true;
            for (int k4 = firstChar; k4 <= lastChar; k4++) {
                if (this.shortTag[k4] != 0) {
                    if (gap) {
                        diff.add(new PdfNumber(k4));
                        gap = false;
                    }
                    diff.add(new PdfName(this.fontEncoding.getDifference(k4)));
                } else {
                    gap = true;
                }
            }
            enc.put(PdfName.Differences, diff);
            ((PdfDictionary) getPdfObject()).put(PdfName.Encoding, enc);
        } else if (!this.fontEncoding.isFontSpecific()) {
            ((PdfDictionary) getPdfObject()).put(PdfName.Encoding, "Cp1252".equals(this.fontEncoding.getBaseEncoding()) ? PdfName.WinAnsiEncoding : PdfName.MacRomanEncoding);
        }
        if (isForceWidthsOutput() || !isBuiltInFont() || this.fontEncoding.hasDifferences()) {
            ((PdfDictionary) getPdfObject()).put(PdfName.FirstChar, new PdfNumber(firstChar));
            ((PdfDictionary) getPdfObject()).put(PdfName.LastChar, new PdfNumber(lastChar));
            PdfArray wd = new PdfArray();
            for (int k5 = firstChar; k5 <= lastChar; k5++) {
                if (this.shortTag[k5] == 0) {
                    wd.add(new PdfNumber(0));
                } else {
                    int uni = this.fontEncoding.getUnicode(k5);
                    wd.add(new PdfNumber(getGlyphWidth(uni > -1 ? getGlyph(uni) : this.fontProgram.getGlyphByCode(k5))));
                }
            }
            ((PdfDictionary) getPdfObject()).put(PdfName.Widths, wd);
        }
        PdfDictionary fontDescriptor = !isBuiltInFont() ? getFontDescriptor(fontName) : null;
        if (fontDescriptor != null) {
            ((PdfDictionary) getPdfObject()).put(PdfName.FontDescriptor, fontDescriptor);
            if (fontDescriptor.getIndirectReference() != null) {
                fontDescriptor.flush();
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean isBuiltInFont() {
        return false;
    }

    /* access modifiers changed from: protected */
    public PdfDictionary getFontDescriptor(String fontName) {
        if (fontName == null || fontName.length() <= 0) {
            throw new AssertionError();
        }
        FontMetrics fontMetrics = this.fontProgram.getFontMetrics();
        FontNames fontNames = this.fontProgram.getFontNames();
        PdfDictionary fontDescriptor = new PdfDictionary();
        makeObjectIndirect(fontDescriptor);
        fontDescriptor.put(PdfName.Type, PdfName.FontDescriptor);
        fontDescriptor.put(PdfName.FontName, new PdfName(fontName));
        fontDescriptor.put(PdfName.Ascent, new PdfNumber(fontMetrics.getTypoAscender()));
        fontDescriptor.put(PdfName.CapHeight, new PdfNumber(fontMetrics.getCapHeight()));
        fontDescriptor.put(PdfName.Descent, new PdfNumber(fontMetrics.getTypoDescender()));
        fontDescriptor.put(PdfName.FontBBox, new PdfArray(ArrayUtil.cloneArray(fontMetrics.getBbox())));
        fontDescriptor.put(PdfName.ItalicAngle, new PdfNumber((double) fontMetrics.getItalicAngle()));
        fontDescriptor.put(PdfName.StemV, new PdfNumber(fontMetrics.getStemV()));
        if (fontMetrics.getXHeight() > 0) {
            fontDescriptor.put(PdfName.XHeight, new PdfNumber(fontMetrics.getXHeight()));
        }
        if (fontMetrics.getStemH() > 0) {
            fontDescriptor.put(PdfName.StemH, new PdfNumber(fontMetrics.getStemH()));
        }
        if (fontNames.getFontWeight() > 0) {
            fontDescriptor.put(PdfName.FontWeight, new PdfNumber(fontNames.getFontWeight()));
        }
        if (fontNames.getFamilyName() != null && fontNames.getFamilyName().length > 0 && fontNames.getFamilyName()[0].length >= 4) {
            fontDescriptor.put(PdfName.FontFamily, new PdfString(fontNames.getFamilyName()[0][3]));
        }
        addFontStream(fontDescriptor);
        fontDescriptor.put(PdfName.Flags, new PdfNumber((this.fontProgram.getPdfFontFlags() & ((FontDescriptorFlags.Symbolic | FontDescriptorFlags.Nonsymbolic) ^ -1)) | (this.fontEncoding.isFontSpecific() ? FontDescriptorFlags.Symbolic : FontDescriptorFlags.Nonsymbolic)));
        return fontDescriptor;
    }

    /* access modifiers changed from: protected */
    public void setFontProgram(T fontProgram) {
        this.fontProgram = fontProgram;
    }

    /* access modifiers changed from: protected */
    public double getGlyphWidth(Glyph glyph) {
        if (glyph != null) {
            return (double) glyph.getWidth();
        }
        return 0.0d;
    }
}
