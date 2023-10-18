package com.itextpdf.kernel.font;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.p026io.font.FontEncoding;
import com.itextpdf.p026io.font.FontNames;
import com.itextpdf.p026io.font.FontProgramFactory;
import com.itextpdf.p026io.font.TrueTypeFont;
import com.itextpdf.p026io.font.Type1Font;
import com.itextpdf.p026io.font.constants.StandardFonts;
import com.itextpdf.p026io.font.otf.Glyph;
import java.io.IOException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.slf4j.LoggerFactory;

public class PdfTrueTypeFont extends PdfSimpleFont<TrueTypeFont> {
    private static final long serialVersionUID = -8152778382960290571L;

    PdfTrueTypeFont(TrueTypeFont ttf, String encoding, boolean embedded) {
        setFontProgram(ttf);
        this.embedded = embedded;
        FontNames fontNames = ttf.getFontNames();
        if (!embedded || fontNames.allowEmbedding()) {
            if ((encoding == null || encoding.length() == 0) && ttf.isFontSpecific()) {
                encoding = FontEncoding.FONT_SPECIFIC;
            }
            if (encoding == null || !FontEncoding.FONT_SPECIFIC.toLowerCase().equals(encoding.toLowerCase())) {
                this.fontEncoding = FontEncoding.createFontEncoding(encoding);
            } else {
                this.fontEncoding = FontEncoding.createFontSpecificEncoding();
            }
        } else {
            throw new PdfException(PdfException.CannotBeEmbeddedDueToLicensingRestrictions).setMessageParams(fontNames.getFontName());
        }
    }

    PdfTrueTypeFont(PdfDictionary fontDictionary) {
        super(fontDictionary);
        boolean z = false;
        this.newFont = false;
        this.subset = false;
        this.fontEncoding = DocFontEncoding.createDocFontEncoding(fontDictionary.get(PdfName.Encoding), this.toUnicode);
        PdfName baseFontName = fontDictionary.getAsName(PdfName.BaseFont);
        if (baseFontName == null || !StandardFonts.isStandardFont(baseFontName.getValue()) || fontDictionary.containsKey(PdfName.FontDescriptor) || fontDictionary.containsKey(PdfName.Widths)) {
            this.fontProgram = DocTrueTypeFont.createFontProgram(fontDictionary, this.fontEncoding, this.toUnicode);
        } else {
            try {
                this.fontProgram = FontProgramFactory.createFont(baseFontName.getValue(), true);
            } catch (IOException e) {
                throw new PdfException(PdfException.IoExceptionWhileCreatingFont, (Throwable) e);
            }
        }
        if ((this.fontProgram instanceof IDocFontProgram) && ((IDocFontProgram) this.fontProgram).getFontFile() != null) {
            z = true;
        }
        this.embedded = z;
    }

    public Glyph getGlyph(int unicode) {
        if (!this.fontEncoding.canEncode(unicode)) {
            return null;
        }
        Glyph glyph = getFontProgram().getGlyph(this.fontEncoding.getUnicodeDifference(unicode));
        if (glyph != null) {
            return glyph;
        }
        Glyph glyph2 = (Glyph) this.notdefGlyphs.get(Integer.valueOf(unicode));
        Glyph glyph3 = glyph2;
        if (glyph2 != null || getFontProgram().getGlyphByCode(0) == null) {
            return glyph3;
        }
        Glyph glyph4 = new Glyph(getFontProgram().getGlyphByCode(0), unicode);
        this.notdefGlyphs.put(Integer.valueOf(unicode), glyph4);
        return glyph4;
    }

    public boolean containsGlyph(int unicode) {
        if (this.fontEncoding.isFontSpecific()) {
            if (this.fontProgram.getGlyphByCode(unicode) != null) {
                return true;
            }
            return false;
        } else if (!this.fontEncoding.canEncode(unicode) || getFontProgram().getGlyph(this.fontEncoding.getUnicodeDifference(unicode)) == null) {
            return false;
        } else {
            return true;
        }
    }

    public void flush() {
        String fontName;
        PdfName subtype;
        if (!isFlushed()) {
            ensureUnderlyingObjectHasIndirectReference();
            if (this.newFont) {
                if (((TrueTypeFont) getFontProgram()).isCff()) {
                    subtype = PdfName.Type1;
                    fontName = this.fontProgram.getFontNames().getFontName();
                } else {
                    subtype = PdfName.TrueType;
                    fontName = updateSubsetPrefix(this.fontProgram.getFontNames().getFontName(), this.subset, this.embedded);
                }
                flushFontData(fontName, subtype);
            }
            super.flush();
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void addRangeUni(Set<Integer> longTag) {
        ((TrueTypeFont) getFontProgram()).updateUsedGlyphs((SortedSet) longTag, this.subset, this.subsetRanges);
    }

    /* access modifiers changed from: protected */
    public void addFontStream(PdfDictionary fontDescriptor) {
        PdfStream fontStream;
        PdfName fontFileName;
        byte[] fontStreamBytes;
        if (this.embedded) {
            if (this.fontProgram instanceof IDocFontProgram) {
                fontFileName = ((IDocFontProgram) this.fontProgram).getFontFileName();
                fontStream = ((IDocFontProgram) this.fontProgram).getFontFile();
            } else {
                Class<PdfTrueTypeFont> cls = PdfTrueTypeFont.class;
                if (((TrueTypeFont) getFontProgram()).isCff()) {
                    fontFileName = PdfName.FontFile3;
                    try {
                        byte[] fontStreamBytes2 = ((TrueTypeFont) getFontProgram()).getFontStreamBytes();
                        fontStream = getPdfFontStream(fontStreamBytes2, new int[]{fontStreamBytes2.length});
                        fontStream.put(PdfName.Subtype, new PdfName("Type1C"));
                    } catch (PdfException e) {
                        LoggerFactory.getLogger((Class<?>) cls).error(e.getMessage());
                        fontStream = null;
                    }
                } else {
                    fontFileName = PdfName.FontFile2;
                    SortedSet<Integer> glyphs = new TreeSet<>();
                    for (int k = 0; k < this.shortTag.length; k++) {
                        if (this.shortTag[k] != 0) {
                            int uni = this.fontEncoding.getUnicode(k);
                            Glyph glyph = uni > -1 ? this.fontProgram.getGlyph(uni) : this.fontProgram.getGlyphByCode(k);
                            if (glyph != null) {
                                glyphs.add(Integer.valueOf(glyph.getCode()));
                            }
                        }
                    }
                    ((TrueTypeFont) getFontProgram()).updateUsedGlyphs(glyphs, this.subset, this.subsetRanges);
                    try {
                        if (!this.subset) {
                            if (((TrueTypeFont) getFontProgram()).getDirectoryOffset() <= 0) {
                                fontStreamBytes = ((TrueTypeFont) getFontProgram()).getFontStreamBytes();
                                fontStream = getPdfFontStream(fontStreamBytes, new int[]{fontStreamBytes.length});
                            }
                        }
                        fontStreamBytes = ((TrueTypeFont) getFontProgram()).getSubset(glyphs, this.subset);
                        fontStream = getPdfFontStream(fontStreamBytes, new int[]{fontStreamBytes.length});
                    } catch (PdfException e2) {
                        LoggerFactory.getLogger((Class<?>) cls).error(e2.getMessage());
                        fontStream = null;
                    }
                }
            }
            if (fontStream != null) {
                fontDescriptor.put(fontFileName, fontStream);
                if (fontStream.getIndirectReference() != null) {
                    fontStream.flush();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean isBuiltInFont() {
        return (this.fontProgram instanceof Type1Font) && ((Type1Font) this.fontProgram).isBuiltInFont();
    }
}
