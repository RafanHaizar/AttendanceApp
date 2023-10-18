package com.itextpdf.kernel.font;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfLiteral;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfOutputStream;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.font.CFFFontSubset;
import com.itextpdf.p026io.font.CMapEncoding;
import com.itextpdf.p026io.font.CidFont;
import com.itextpdf.p026io.font.CidFontProperties;
import com.itextpdf.p026io.font.FontProgramFactory;
import com.itextpdf.p026io.font.PdfEncodings;
import com.itextpdf.p026io.font.TrueTypeFont;
import com.itextpdf.p026io.font.cmap.CMapContentParser;
import com.itextpdf.p026io.font.cmap.CMapToUnicode;
import com.itextpdf.p026io.font.otf.Glyph;
import com.itextpdf.p026io.font.otf.GlyphLine;
import com.itextpdf.p026io.source.ByteArrayOutputStream;
import com.itextpdf.p026io.source.ByteBuffer;
import com.itextpdf.p026io.source.OutputStream;
import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.p026io.util.StreamUtil;
import com.itextpdf.p026io.util.TextUtil;
import com.itextpdf.svg.SvgConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;
import org.slf4j.LoggerFactory;

public class PdfType0Font extends PdfFont {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected static final int CID_FONT_TYPE_0 = 0;
    protected static final int CID_FONT_TYPE_2 = 2;
    private static final byte[] rotbits = {ByteCompanionObject.MIN_VALUE, 64, 32, Tnaf.POW_2_WIDTH, 8, 4, 2, 1};
    private static final long serialVersionUID = -8033620300884193397L;
    protected int cidFontType;
    protected CMapEncoding cmapEncoding;
    protected Set<Integer> longTag;
    protected char[] specificUnicodeDifferences;
    protected boolean vertical;

    PdfType0Font(TrueTypeFont ttf, String cmap) {
        if (!PdfEncodings.IDENTITY_H.equals(cmap) && !PdfEncodings.IDENTITY_V.equals(cmap)) {
            throw new PdfException(PdfException.OnlyIdentityCMapsSupportsWithTrueType);
        } else if (ttf.getFontNames().allowEmbedding()) {
            this.fontProgram = ttf;
            this.embedded = true;
            this.vertical = cmap.endsWith(SvgConstants.Attributes.PATH_DATA_LINE_TO_V);
            this.cmapEncoding = new CMapEncoding(cmap);
            this.longTag = new TreeSet();
            this.cidFontType = 2;
            if (ttf.isFontSpecific()) {
                this.specificUnicodeDifferences = new char[256];
                byte[] bytes = new byte[1];
                for (int k = 0; k < 256; k++) {
                    bytes[0] = (byte) k;
                    String s = PdfEncodings.convertToString(bytes, (String) null);
                    this.specificUnicodeDifferences[k] = s.length() > 0 ? s.charAt(0) : '?';
                }
            }
        } else {
            throw new PdfException(PdfException.CannotBeEmbeddedDueToLicensingRestrictions).setMessageParams(ttf.getFontNames().getFontName() + ttf.getFontNames().getStyle());
        }
    }

    PdfType0Font(CidFont font, String cmap) {
        if (CidFontProperties.isCidFont(font.getFontNames().getFontName(), cmap)) {
            this.fontProgram = font;
            this.vertical = cmap.endsWith(SvgConstants.Attributes.PATH_DATA_LINE_TO_V);
            this.cmapEncoding = new CMapEncoding(cmap, getCompatibleUniMap(this.fontProgram.getRegistry()));
            this.longTag = new TreeSet();
            this.cidFontType = 0;
            return;
        }
        throw new PdfException("Font {0} with {1} encoding is not a cjk font.").setMessageParams(font.getFontNames().getFontName(), cmap);
    }

    PdfType0Font(PdfDictionary fontDictionary) {
        super(fontDictionary);
        String uniMap;
        this.newFont = false;
        PdfDictionary cidFont = fontDictionary.getAsArray(PdfName.DescendantFonts).getAsDictionary(0);
        PdfObject cmap = fontDictionary.get(PdfName.Encoding);
        CMapToUnicode toUnicodeCMap = FontUtil.processToUnicode(fontDictionary.get(PdfName.ToUnicode));
        boolean z = true;
        if (!cmap.isName() || (!PdfEncodings.IDENTITY_H.equals(((PdfName) cmap).getValue()) && !PdfEncodings.IDENTITY_V.equals(((PdfName) cmap).getValue()))) {
            String cidFontName = cidFont.getAsName(PdfName.BaseFont).getValue();
            String uniMap2 = getUniMapFromOrdering(getOrdering(cidFont));
            if (uniMap2 == null || !uniMap2.startsWith("Uni") || !CidFontProperties.isCidFont(cidFontName, uniMap2)) {
                toUnicodeCMap = toUnicodeCMap == null ? FontUtil.getToUnicodeFromUniMap(uniMap2) : toUnicodeCMap;
                if (toUnicodeCMap != null) {
                    this.fontProgram = DocTrueTypeFont.createFontProgram(cidFont, toUnicodeCMap);
                    this.cmapEncoding = createCMap(cmap, uniMap2);
                }
            } else {
                try {
                    this.fontProgram = FontProgramFactory.createFont(cidFontName);
                    this.cmapEncoding = createCMap(cmap, uniMap2);
                    this.embedded = false;
                } catch (IOException e) {
                    this.fontProgram = null;
                    this.cmapEncoding = null;
                }
            }
            if (this.fontProgram == null) {
                throw new PdfException(MessageFormatUtil.format(PdfException.CannotRecogniseDocumentFontWithEncoding, cidFontName, cmap));
            }
        } else {
            if (toUnicodeCMap == null && (toUnicodeCMap = FontUtil.getToUnicodeFromUniMap(uniMap)) == null) {
                toUnicodeCMap = FontUtil.getToUnicodeFromUniMap(PdfEncodings.IDENTITY_H);
                LoggerFactory.getLogger((Class<?>) PdfType0Font.class).error(MessageFormatUtil.format(LogMessageConstant.UNKNOWN_CMAP, (uniMap = getUniMapFromOrdering(getOrdering(cidFont)))));
            }
            this.fontProgram = DocTrueTypeFont.createFontProgram(cidFont, toUnicodeCMap);
            this.cmapEncoding = createCMap(cmap, (String) null);
            if (this.fontProgram instanceof IDocFontProgram) {
                this.embedded = ((IDocFontProgram) this.fontProgram).getFontFile() == null ? false : z;
            } else {
                throw new AssertionError();
            }
        }
        PdfName subtype = fontDictionary.getAsArray(PdfName.DescendantFonts).getAsDictionary(0).getAsName(PdfName.Subtype);
        if (PdfName.CIDFontType0.equals(subtype)) {
            this.cidFontType = 0;
        } else if (PdfName.CIDFontType2.equals(subtype)) {
            this.cidFontType = 2;
        } else {
            LoggerFactory.getLogger(getClass()).error(LogMessageConstant.FAILED_TO_DETERMINE_CID_FONT_SUBTYPE);
        }
        this.longTag = new TreeSet();
        this.subset = false;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getUniMapFromOrdering(java.lang.String r1) {
        /*
            int r0 = r1.hashCode()
            switch(r0) {
                case -2083395317: goto L_0x0030;
                case -2041773849: goto L_0x0026;
                case -71117602: goto L_0x001c;
                case 70326: goto L_0x0012;
                case 2073577: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x003a
        L_0x0008:
            java.lang.String r0 = "CNS1"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 0
            goto L_0x003b
        L_0x0012:
            java.lang.String r0 = "GB1"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 3
            goto L_0x003b
        L_0x001c:
            java.lang.String r0 = "Identity"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 4
            goto L_0x003b
        L_0x0026:
            java.lang.String r0 = "Korea1"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 2
            goto L_0x003b
        L_0x0030:
            java.lang.String r0 = "Japan1"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 1
            goto L_0x003b
        L_0x003a:
            r0 = -1
        L_0x003b:
            switch(r0) {
                case 0: goto L_0x004c;
                case 1: goto L_0x0049;
                case 2: goto L_0x0046;
                case 3: goto L_0x0043;
                case 4: goto L_0x0040;
                default: goto L_0x003e;
            }
        L_0x003e:
            r0 = 0
            return r0
        L_0x0040:
            java.lang.String r0 = "Identity-H"
            return r0
        L_0x0043:
            java.lang.String r0 = "UniGB-UTF16-H"
            return r0
        L_0x0046:
            java.lang.String r0 = "UniKS-UTF16-H"
            return r0
        L_0x0049:
            java.lang.String r0 = "UniJIS-UTF16-H"
            return r0
        L_0x004c:
            java.lang.String r0 = "UniCNS-UTF16-H"
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.font.PdfType0Font.getUniMapFromOrdering(java.lang.String):java.lang.String");
    }

    public Glyph getGlyph(int unicode) {
        Glyph glyph = getFontProgram().getGlyph(unicode);
        if (glyph == null) {
            Glyph glyph2 = (Glyph) this.notdefGlyphs.get(Integer.valueOf(unicode));
            glyph = glyph2;
            if (glyph2 == null) {
                Glyph notdef = getFontProgram().getGlyphByCode(0);
                if (notdef != null) {
                    glyph = new Glyph(notdef, unicode);
                } else {
                    glyph = new Glyph(-1, 0, unicode);
                }
                this.notdefGlyphs.put(Integer.valueOf(unicode), glyph);
            }
        }
        return glyph;
    }

    public boolean containsGlyph(int unicode) {
        int i = this.cidFontType;
        if (i == 0) {
            if (this.cmapEncoding.isDirect()) {
                if (this.fontProgram.getGlyphByCode(unicode) != null) {
                    return true;
                }
                return false;
            } else if (getFontProgram().getGlyph(unicode) != null) {
                return true;
            } else {
                return false;
            }
        } else if (i != 2) {
            throw new PdfException("Invalid CID font type: " + this.cidFontType);
        } else if (this.fontProgram.isFontSpecific()) {
            byte[] b = PdfEncodings.convertToBytes((char) unicode, "symboltt");
            if (b.length <= 0 || this.fontProgram.getGlyph(b[0] & UByte.MAX_VALUE) == null) {
                return false;
            }
            return true;
        } else if (getFontProgram().getGlyph(unicode) != null) {
            return true;
        } else {
            return false;
        }
    }

    public byte[] convertToBytes(String text) {
        int val;
        int len = text.length();
        ByteBuffer buffer = new ByteBuffer();
        if (this.fontProgram.isFontSpecific()) {
            for (byte b : PdfEncodings.convertToBytes(text, "symboltt")) {
                Glyph glyph = this.fontProgram.getGlyph(b & UByte.MAX_VALUE);
                if (glyph != null) {
                    convertToBytes(glyph, buffer);
                }
            }
        } else {
            int k = 0;
            while (k < len) {
                if (TextUtil.isSurrogatePair(text, k)) {
                    val = TextUtil.convertToUtf32(text, k);
                    k++;
                } else {
                    val = text.charAt(k);
                }
                Glyph glyph2 = getGlyph(val);
                if (glyph2.getCode() > 0) {
                    convertToBytes(glyph2, buffer);
                } else {
                    int nullCode = this.cmapEncoding.getCmapCode(0);
                    buffer.append(nullCode >> 8);
                    buffer.append(nullCode);
                }
                k++;
            }
        }
        return buffer.toByteArray();
    }

    public byte[] convertToBytes(GlyphLine glyphLine) {
        if (glyphLine == null) {
            return null;
        }
        int totalByteCount = 0;
        for (int i = glyphLine.start; i < glyphLine.end; i++) {
            totalByteCount += this.cmapEncoding.getCmapBytesLength(glyphLine.get(i).getCode());
        }
        byte[] bytes = new byte[totalByteCount];
        int offset = 0;
        for (int i2 = glyphLine.start; i2 < glyphLine.end; i2++) {
            this.longTag.add(Integer.valueOf(glyphLine.get(i2).getCode()));
            offset = this.cmapEncoding.fillCmapBytes(glyphLine.get(i2).getCode(), bytes, offset);
        }
        return bytes;
    }

    public byte[] convertToBytes(Glyph glyph) {
        this.longTag.add(Integer.valueOf(glyph.getCode()));
        return this.cmapEncoding.getCmapBytes(glyph.getCode());
    }

    public void writeText(GlyphLine text, int from, int to, PdfOutputStream stream) {
        if ((to - from) + 1 > 0) {
            StreamUtil.writeHexedString(stream, convertToBytes(new GlyphLine(text, from, to + 1)));
        }
    }

    public void writeText(String text, PdfOutputStream stream) {
        StreamUtil.writeHexedString(stream, convertToBytes(text));
    }

    public GlyphLine createGlyphLine(String content) {
        int val;
        int ch;
        List<Glyph> glyphs = new ArrayList<>();
        int i = this.cidFontType;
        if (i == 0) {
            int len = content.length();
            if (this.cmapEncoding.isDirect()) {
                for (int k = 0; k < len; k++) {
                    Glyph glyph = this.fontProgram.getGlyphByCode(content.charAt(k));
                    if (glyph != null) {
                        glyphs.add(glyph);
                    }
                }
            } else {
                int k2 = 0;
                while (k2 < len) {
                    if (TextUtil.isSurrogatePair(content, k2)) {
                        ch = TextUtil.convertToUtf32(content, k2);
                        k2++;
                    } else {
                        ch = content.charAt(k2);
                    }
                    glyphs.add(getGlyph(ch));
                    k2++;
                }
            }
        } else if (i == 2) {
            int len2 = content.length();
            if (this.fontProgram.isFontSpecific()) {
                for (byte b : PdfEncodings.convertToBytes(content, "symboltt")) {
                    Glyph glyph2 = this.fontProgram.getGlyph(b & UByte.MAX_VALUE);
                    if (glyph2 != null) {
                        glyphs.add(glyph2);
                    }
                }
            } else {
                int k3 = 0;
                while (k3 < len2) {
                    if (TextUtil.isSurrogatePair(content, k3)) {
                        val = TextUtil.convertToUtf32(content, k3);
                        k3++;
                    } else {
                        val = content.charAt(k3);
                    }
                    glyphs.add(getGlyph(val));
                    k3++;
                }
            }
        } else {
            throw new PdfException("Font has no suitable cmap.");
        }
        return new GlyphLine(glyphs);
    }

    public int appendGlyphs(String text, int from, int to, List<Glyph> glyphs) {
        int i = this.cidFontType;
        if (i == 0) {
            if (!this.cmapEncoding.isDirect()) {
                return appendUniGlyphs(text, from, to, glyphs);
            }
            int processed = 0;
            for (int k = from; k <= to; k++) {
                Glyph glyph = this.fontProgram.getGlyphByCode(text.charAt(k));
                if (glyph == null || !isAppendableGlyph(glyph)) {
                    break;
                }
                glyphs.add(glyph);
                processed++;
            }
            return processed;
        } else if (i != 2) {
            throw new PdfException("Font has no suitable cmap.");
        } else if (!this.fontProgram.isFontSpecific()) {
            return appendUniGlyphs(text, from, to, glyphs);
        } else {
            int processed2 = 0;
            for (int k2 = from; k2 <= to; k2++) {
                Glyph glyph2 = this.fontProgram.getGlyph(text.charAt(k2) & 255);
                if (glyph2 == null || !isAppendableGlyph(glyph2)) {
                    break;
                }
                glyphs.add(glyph2);
                processed2++;
            }
            return processed2;
        }
    }

    private int appendUniGlyphs(String text, int from, int to, List<Glyph> glyphs) {
        int val;
        int processed = 0;
        for (int k = from; k <= to; k++) {
            int currentlyProcessed = processed;
            if (TextUtil.isSurrogatePair(text, k)) {
                val = TextUtil.convertToUtf32(text, k);
                processed += 2;
            } else {
                val = text.charAt(k);
                processed++;
            }
            Glyph glyph = getGlyph(val);
            if (!isAppendableGlyph(glyph)) {
                return currentlyProcessed;
            }
            glyphs.add(glyph);
        }
        return processed;
    }

    public int appendAnyGlyph(String text, int from, List<Glyph> glyphs) {
        int ch;
        Glyph glyph;
        int ch2;
        int process = 1;
        int i = this.cidFontType;
        if (i == 0) {
            if (this.cmapEncoding.isDirect()) {
                Glyph glyph2 = this.fontProgram.getGlyphByCode(text.charAt(from));
                if (glyph2 != null) {
                    glyphs.add(glyph2);
                }
            } else {
                if (TextUtil.isSurrogatePair(text, from)) {
                    ch2 = TextUtil.convertToUtf32(text, from);
                    process = 2;
                } else {
                    ch2 = text.charAt(from);
                }
                glyphs.add(getGlyph(ch2));
            }
        } else if (i != 2) {
            throw new PdfException("Font has no suitable cmap.");
        } else if (((TrueTypeFont) this.fontProgram).isFontSpecific()) {
            byte[] b = PdfEncodings.convertToBytes(text, "symboltt");
            if (b.length > 0 && (glyph = this.fontProgram.getGlyph(b[0] & UByte.MAX_VALUE)) != null) {
                glyphs.add(glyph);
            }
        } else {
            if (TextUtil.isSurrogatePair(text, from)) {
                ch = TextUtil.convertToUtf32(text, from);
                process = 2;
            } else {
                ch = text.charAt(from);
            }
            glyphs.add(getGlyph(ch));
        }
        return process;
    }

    private boolean isAppendableGlyph(Glyph glyph) {
        return glyph.getCode() > 0 || TextUtil.isWhitespaceOrNonPrintable(glyph.getUnicode());
    }

    public String decode(PdfString content) {
        return decodeIntoGlyphLine(content).toString();
    }

    public GlyphLine decodeIntoGlyphLine(PdfString content) {
        String cids = content.getValue();
        List<Glyph> glyphs = new ArrayList<>();
        int i = 0;
        while (i < cids.length()) {
            int code = 0;
            Glyph glyph = null;
            int codeSpaceMatchedLength = 1;
            int codeLength = 1;
            while (true) {
                if (codeLength > 4 || i + codeLength > cids.length()) {
                    break;
                }
                code = (code << 8) + cids.charAt((i + codeLength) - 1);
                if (this.cmapEncoding.containsCodeInCodeSpaceRange(code, codeLength)) {
                    codeSpaceMatchedLength = codeLength;
                    glyph = this.fontProgram.getGlyphByCode(this.cmapEncoding.getCidCode(code));
                    if (glyph != null) {
                        i += codeLength - 1;
                        break;
                    }
                }
                codeLength++;
            }
            if (glyph == null) {
                StringBuilder failedCodes = new StringBuilder();
                int codeLength2 = 1;
                while (codeLength2 <= 4 && i + codeLength2 <= cids.length()) {
                    failedCodes.append(cids.charAt((i + codeLength2) - 1)).append(" ");
                    codeLength2++;
                }
                LoggerFactory.getLogger((Class<?>) PdfType0Font.class).warn(MessageFormatUtil.format(LogMessageConstant.COULD_NOT_FIND_GLYPH_WITH_CODE, failedCodes.toString()));
                i += codeSpaceMatchedLength - 1;
            }
            if (glyph == null || glyph.getChars() == null) {
                glyphs.add(new Glyph(0, this.fontProgram.getGlyphByCode(0).getWidth(), -1));
            } else {
                glyphs.add(glyph);
            }
            i++;
        }
        return new GlyphLine(glyphs);
    }

    public float getContentWidth(PdfString content) {
        float width = 0.0f;
        GlyphLine glyphLine = decodeIntoGlyphLine(content);
        for (int i = glyphLine.start; i < glyphLine.end; i++) {
            width += (float) glyphLine.get(i).getWidth();
        }
        return width;
    }

    public boolean isBuiltWith(String fontProgram, String encoding) {
        return getFontProgram().isBuiltWith(fontProgram) && this.cmapEncoding.isBuiltWith(encoding);
    }

    public void flush() {
        if (!isFlushed()) {
            ensureUnderlyingObjectHasIndirectReference();
            if (this.newFont) {
                flushFontData();
            }
            super.flush();
        }
    }

    public CMapEncoding getCmap() {
        return this.cmapEncoding;
    }

    @Deprecated
    public PdfStream getToUnicode(Object[] metrics) {
        return getToUnicode();
    }

    /* access modifiers changed from: protected */
    public PdfDictionary getFontDescriptor(String fontName) {
        PdfDictionary fontDescriptor = new PdfDictionary();
        makeObjectIndirect(fontDescriptor);
        fontDescriptor.put(PdfName.Type, PdfName.FontDescriptor);
        fontDescriptor.put(PdfName.FontName, new PdfName(fontName));
        fontDescriptor.put(PdfName.FontBBox, new PdfArray(getFontProgram().getFontMetrics().getBbox()));
        fontDescriptor.put(PdfName.Ascent, new PdfNumber(getFontProgram().getFontMetrics().getTypoAscender()));
        fontDescriptor.put(PdfName.Descent, new PdfNumber(getFontProgram().getFontMetrics().getTypoDescender()));
        fontDescriptor.put(PdfName.CapHeight, new PdfNumber(getFontProgram().getFontMetrics().getCapHeight()));
        fontDescriptor.put(PdfName.ItalicAngle, new PdfNumber((double) getFontProgram().getFontMetrics().getItalicAngle()));
        fontDescriptor.put(PdfName.StemV, new PdfNumber(getFontProgram().getFontMetrics().getStemV()));
        fontDescriptor.put(PdfName.Flags, new PdfNumber(getFontProgram().getPdfFontFlags()));
        if (this.fontProgram.getFontIdentification().getPanose() != null) {
            PdfDictionary styleDictionary = new PdfDictionary();
            styleDictionary.put(PdfName.Panose, new PdfString(this.fontProgram.getFontIdentification().getPanose()).setHexWriting(true));
            fontDescriptor.put(PdfName.Style, styleDictionary);
        }
        return fontDescriptor;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public PdfDictionary getCidFontType2(TrueTypeFont ttf, PdfDictionary fontDescriptor, String fontName, int[][] metrics) {
        return getCidFont(fontDescriptor, fontName, ttf != null && !ttf.isCff());
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void addRangeUni(TrueTypeFont ttf, Map<Integer, int[]> longTag2, boolean includeMetrics) {
        addRangeUni(ttf, longTag2.keySet());
    }

    private void convertToBytes(Glyph glyph, ByteBuffer result) {
        int code = glyph.getCode();
        this.longTag.add(Integer.valueOf(code));
        this.cmapEncoding.fillCmapBytes(code, result);
    }

    private static String getOrdering(PdfDictionary cidFont) {
        PdfDictionary cidinfo = cidFont.getAsDictionary(PdfName.CIDSystemInfo);
        if (cidinfo != null && cidinfo.containsKey(PdfName.Ordering)) {
            return cidinfo.get(PdfName.Ordering).toString();
        }
        return null;
    }

    private void flushFontData() {
        PdfStream fontStream;
        byte[] cffBytes;
        int i = this.cidFontType;
        if (i == 0) {
            ((PdfDictionary) getPdfObject()).put(PdfName.Type, PdfName.Font);
            ((PdfDictionary) getPdfObject()).put(PdfName.Subtype, PdfName.Type0);
            String name = this.fontProgram.getFontNames().getFontName();
            String style = this.fontProgram.getFontNames().getStyle();
            if (style.length() > 0) {
                name = name + "-" + style;
            }
            ((PdfDictionary) getPdfObject()).put(PdfName.BaseFont, new PdfName(MessageFormatUtil.format("{0}-{1}", name, this.cmapEncoding.getCmapName())));
            ((PdfDictionary) getPdfObject()).put(PdfName.Encoding, new PdfName(this.cmapEncoding.getCmapName()));
            PdfDictionary fontDescriptor = getFontDescriptor(name);
            PdfDictionary cidFont = getCidFont(fontDescriptor, this.fontProgram.getFontNames().getFontName(), false);
            ((PdfDictionary) getPdfObject()).put(PdfName.DescendantFonts, new PdfArray((PdfObject) cidFont));
            fontDescriptor.flush();
            cidFont.flush();
        } else if (i == 2) {
            TrueTypeFont ttf = (TrueTypeFont) getFontProgram();
            String fontName = updateSubsetPrefix(ttf.getFontNames().getFontName(), this.subset, this.embedded);
            PdfDictionary fontDescriptor2 = getFontDescriptor(fontName);
            ttf.updateUsedGlyphs((SortedSet) this.longTag, this.subset, this.subsetRanges);
            if (ttf.isCff()) {
                if (this.subset) {
                    cffBytes = new CFFFontSubset(ttf.getFontStreamBytes(), this.longTag).Process();
                } else {
                    cffBytes = ttf.getFontStreamBytes();
                }
                fontStream = getPdfFontStream(cffBytes, new int[]{cffBytes.length});
                fontStream.put(PdfName.Subtype, new PdfName("CIDFontType0C"));
                ((PdfDictionary) getPdfObject()).put(PdfName.BaseFont, new PdfName(MessageFormatUtil.format("{0}-{1}", fontName, this.cmapEncoding.getCmapName())));
                fontDescriptor2.put(PdfName.FontFile3, fontStream);
            } else {
                byte[] ttfBytes = null;
                if (this.subset || ttf.getDirectoryOffset() > 0) {
                    try {
                        ttfBytes = ttf.getSubset(this.longTag, this.subset);
                    } catch (com.itextpdf.p026io.IOException e) {
                        LoggerFactory.getLogger((Class<?>) PdfType0Font.class).warn(LogMessageConstant.FONT_SUBSET_ISSUE);
                        ttfBytes = null;
                    }
                }
                if (ttfBytes == null) {
                    ttfBytes = ttf.getFontStreamBytes();
                }
                fontStream = getPdfFontStream(ttfBytes, new int[]{ttfBytes.length});
                ((PdfDictionary) getPdfObject()).put(PdfName.BaseFont, new PdfName(fontName));
                fontDescriptor2.put(PdfName.FontFile2, fontStream);
            }
            int numOfGlyphs = ttf.getFontMetrics().getNumberOfGlyphs();
            byte[] cidSetBytes = new byte[((ttf.getFontMetrics().getNumberOfGlyphs() / 8) + 1)];
            for (int i2 = 0; i2 < numOfGlyphs / 8; i2++) {
                cidSetBytes[i2] = (byte) (cidSetBytes[i2] | UByte.MAX_VALUE);
            }
            for (int i3 = 0; i3 < numOfGlyphs % 8; i3++) {
                int length = cidSetBytes.length - 1;
                cidSetBytes[length] = (byte) (cidSetBytes[length] | rotbits[i3]);
            }
            fontDescriptor2.put(PdfName.CIDSet, new PdfStream(cidSetBytes));
            PdfDictionary cidFont2 = getCidFont(fontDescriptor2, fontName, !ttf.isCff());
            ((PdfDictionary) getPdfObject()).put(PdfName.Type, PdfName.Font);
            ((PdfDictionary) getPdfObject()).put(PdfName.Subtype, PdfName.Type0);
            ((PdfDictionary) getPdfObject()).put(PdfName.Encoding, new PdfName(this.cmapEncoding.getCmapName()));
            ((PdfDictionary) getPdfObject()).put(PdfName.DescendantFonts, new PdfArray((PdfObject) cidFont2));
            PdfStream toUnicode = getToUnicode();
            if (toUnicode != null) {
                ((PdfDictionary) getPdfObject()).put(PdfName.ToUnicode, toUnicode);
                if (toUnicode.getIndirectReference() != null) {
                    toUnicode.flush();
                }
            }
            if (((PdfDictionary) getPdfObject()).getIndirectReference().getDocument().getPdfVersion().compareTo(PdfVersion.PDF_2_0) >= 0) {
                fontDescriptor2.remove(PdfName.CIDSet);
            }
            fontDescriptor2.flush();
            cidFont2.flush();
            fontStream.flush();
        } else {
            throw new IllegalStateException("Unsupported CID Font");
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public PdfDictionary getCidFontType2(TrueTypeFont ttf, PdfDictionary fontDescriptor, String fontName, int[] glyphIds) {
        return getCidFont(fontDescriptor, fontName, ttf != null && !ttf.isCff());
    }

    /* access modifiers changed from: protected */
    public PdfDictionary getCidFont(PdfDictionary fontDescriptor, String fontName, boolean isType2) {
        PdfDictionary cidFont = new PdfDictionary();
        markObjectAsIndirect(cidFont);
        cidFont.put(PdfName.Type, PdfName.Font);
        cidFont.put(PdfName.FontDescriptor, fontDescriptor);
        if (isType2) {
            cidFont.put(PdfName.Subtype, PdfName.CIDFontType2);
            cidFont.put(PdfName.CIDToGIDMap, PdfName.Identity);
        } else {
            cidFont.put(PdfName.Subtype, PdfName.CIDFontType0);
        }
        cidFont.put(PdfName.BaseFont, new PdfName(fontName));
        PdfDictionary cidInfo = new PdfDictionary();
        cidInfo.put(PdfName.Registry, new PdfString(this.cmapEncoding.getRegistry()));
        cidInfo.put(PdfName.Ordering, new PdfString(this.cmapEncoding.getOrdering()));
        cidInfo.put(PdfName.Supplement, new PdfNumber(this.cmapEncoding.getSupplement()));
        cidFont.put(PdfName.CIDSystemInfo, cidInfo);
        if (!this.vertical) {
            cidFont.put(PdfName.f1318DW, new PdfNumber(1000));
            PdfObject widthsArray = generateWidthsArray();
            if (widthsArray != null) {
                cidFont.put(PdfName.f1409W, widthsArray);
            }
        } else {
            LoggerFactory.getLogger((Class<?>) PdfType0Font.class).warn("Vertical writing has not been implemented yet.");
        }
        return cidFont;
    }

    private PdfObject generateWidthsArray() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        OutputStream<ByteArrayOutputStream> stream = new OutputStream<>(bytes);
        stream.writeByte(91);
        int lastNumber = -10;
        boolean firstTime = true;
        for (Integer intValue : this.longTag) {
            Glyph glyph = this.fontProgram.getGlyphByCode(intValue.intValue());
            if (glyph.getWidth() != 1000) {
                if (glyph.getCode() == lastNumber + 1) {
                    stream.writeByte(32);
                } else {
                    if (!firstTime) {
                        stream.writeByte(93);
                    }
                    firstTime = false;
                    stream.writeInteger(glyph.getCode());
                    stream.writeByte(91);
                }
                stream.writeInteger(glyph.getWidth());
                lastNumber = glyph.getCode();
            }
        }
        if (stream.getCurrentPos() <= 1) {
            return null;
        }
        stream.writeString("]]");
        return new PdfLiteral(bytes.toByteArray());
    }

    public PdfStream getToUnicode() {
        OutputStream<ByteArrayOutputStream> stream = new OutputStream<>(new ByteArrayOutputStream());
        stream.writeString("/CIDInit /ProcSet findresource begin\n12 dict begin\nbegincmap\n/CIDSystemInfo\n<< /Registry (Adobe)\n/Ordering (UCS)\n/Supplement 0\n>> def\n/CMapName /Adobe-Identity-UCS def\n/CMapType 2 def\n1 begincodespacerange\n<0000><FFFF>\nendcodespacerange\n");
        ArrayList<Glyph> glyphGroup = new ArrayList<>(100);
        int bfranges = 0;
        for (Integer glyphId : this.longTag) {
            Glyph glyph = this.fontProgram.getGlyphByCode(glyphId.intValue());
            if (glyph.getChars() != null) {
                glyphGroup.add(glyph);
                if (glyphGroup.size() == 100) {
                    bfranges += writeBfrange(stream, glyphGroup);
                }
            }
        }
        if (bfranges + writeBfrange(stream, glyphGroup) == 0) {
            return null;
        }
        stream.writeString("endcmap\nCMapName currentdict /CMap defineresource pop\nend end\n");
        return new PdfStream(((ByteArrayOutputStream) stream.getOutputStream()).toByteArray());
    }

    private int writeBfrange(OutputStream<ByteArrayOutputStream> stream, List<Glyph> range) {
        if (range.isEmpty()) {
            return 0;
        }
        stream.writeInteger(range.size());
        stream.writeString(" beginbfrange\n");
        for (Glyph glyph : range) {
            String fromTo = CMapContentParser.toHex(glyph.getCode());
            stream.writeString(fromTo);
            stream.writeString(fromTo);
            stream.writeByte(60);
            for (char ch : glyph.getChars()) {
                stream.writeString(toHex4(ch));
            }
            stream.writeByte(62);
            stream.writeByte(10);
        }
        stream.writeString("endbfrange\n");
        range.clear();
        return 1;
    }

    private static String toHex4(char ch) {
        String s = "0000" + Integer.toHexString(ch);
        return s.substring(s.length() - 4);
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void addRangeUni(TrueTypeFont ttf, Set<Integer> longTag2) {
        ttf.updateUsedGlyphs((SortedSet) longTag2, this.subset, this.subsetRanges);
    }

    private String getCompatibleUniMap(String registry) {
        String uniMap = "";
        for (String name : CidFontProperties.getRegistryNames().get(registry + "_Uni")) {
            uniMap = name;
            if ((name.endsWith(SvgConstants.Attributes.PATH_DATA_LINE_TO_V) && this.vertical) || (!name.endsWith(SvgConstants.Attributes.PATH_DATA_LINE_TO_V) && !this.vertical)) {
                break;
            }
        }
        return uniMap;
    }

    private static CMapEncoding createCMap(PdfObject cmap, String uniMap) {
        if (cmap.isStream()) {
            PdfStream cmapStream = (PdfStream) cmap;
            return new CMapEncoding(cmapStream.getAsName(PdfName.CMapName).getValue(), cmapStream.getBytes());
        }
        String cmapName = ((PdfName) cmap).getValue();
        if (PdfEncodings.IDENTITY_H.equals(cmapName) || PdfEncodings.IDENTITY_V.equals(cmapName)) {
            return new CMapEncoding(cmapName);
        }
        return new CMapEncoding(cmapName, uniMap);
    }
}
