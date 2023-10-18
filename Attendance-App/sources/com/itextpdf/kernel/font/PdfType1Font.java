package com.itextpdf.kernel.font;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.p026io.font.FontEncoding;
import com.itextpdf.p026io.font.Type1Font;
import com.itextpdf.p026io.font.otf.Glyph;

public class PdfType1Font extends PdfSimpleFont<Type1Font> {
    private static final long serialVersionUID = 7009919945291639441L;

    PdfType1Font(Type1Font type1Font, String encoding, boolean embedded) {
        setFontProgram(type1Font);
        this.embedded = embedded && !type1Font.isBuiltInFont();
        if ((encoding == null || encoding.length() == 0) && type1Font.isFontSpecific()) {
            encoding = FontEncoding.FONT_SPECIFIC;
        }
        if (encoding == null || !FontEncoding.FONT_SPECIFIC.toLowerCase().equals(encoding.toLowerCase())) {
            this.fontEncoding = FontEncoding.createFontEncoding(encoding);
        } else {
            this.fontEncoding = FontEncoding.createFontSpecificEncoding();
        }
    }

    PdfType1Font(Type1Font type1Font, String encoding) {
        this(type1Font, encoding, false);
    }

    PdfType1Font(PdfDictionary fontDictionary) {
        super(fontDictionary);
        this.newFont = false;
        this.fontEncoding = DocFontEncoding.createDocFontEncoding(fontDictionary.get(PdfName.Encoding), this.toUnicode);
        this.fontProgram = DocType1Font.createFontProgram(fontDictionary, this.fontEncoding, this.toUnicode);
        if (this.fontProgram instanceof IDocFontProgram) {
            this.embedded = ((IDocFontProgram) this.fontProgram).getFontFile() != null;
        }
        this.subset = false;
    }

    public boolean isSubset() {
        return this.subset;
    }

    public void setSubset(boolean subset) {
        this.subset = subset;
    }

    public void flush() {
        if (!isFlushed()) {
            ensureUnderlyingObjectHasIndirectReference();
            if (this.newFont) {
                flushFontData(this.fontProgram.getFontNames().getFontName(), PdfName.Type1);
            }
            super.flush();
        }
    }

    public Glyph getGlyph(int unicode) {
        if (!this.fontEncoding.canEncode(unicode)) {
            return null;
        }
        if (this.fontEncoding.isFontSpecific()) {
            return getFontProgram().getGlyphByCode(unicode);
        }
        Glyph glyph = getFontProgram().getGlyph(this.fontEncoding.getUnicodeDifference(unicode));
        if (glyph != null) {
            return glyph;
        }
        Glyph glyph2 = (Glyph) this.notdefGlyphs.get(Integer.valueOf(unicode));
        Glyph glyph3 = glyph2;
        if (glyph2 != null) {
            return glyph3;
        }
        Glyph glyph4 = new Glyph(-1, 0, unicode);
        this.notdefGlyphs.put(Integer.valueOf(unicode), glyph4);
        return glyph4;
    }

    public boolean containsGlyph(int unicode) {
        if (!this.fontEncoding.canEncode(unicode)) {
            return false;
        }
        if (this.fontEncoding.isFontSpecific()) {
            if (getFontProgram().getGlyphByCode(unicode) != null) {
                return true;
            }
            return false;
        } else if (getFontProgram().getGlyph(this.fontEncoding.getUnicodeDifference(unicode)) != null) {
            return true;
        } else {
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public boolean isBuiltInFont() {
        return ((Type1Font) getFontProgram()).isBuiltInFont();
    }

    /* access modifiers changed from: protected */
    public void addFontStream(PdfDictionary fontDescriptor) {
        if (!this.embedded) {
            return;
        }
        if (this.fontProgram instanceof IDocFontProgram) {
            IDocFontProgram docType1Font = (IDocFontProgram) this.fontProgram;
            fontDescriptor.put(docType1Font.getFontFileName(), docType1Font.getFontFile());
            docType1Font.getFontFile().flush();
            if (docType1Font.getSubtype() != null) {
                fontDescriptor.put(PdfName.Subtype, docType1Font.getSubtype());
                return;
            }
            return;
        }
        byte[] fontStreamBytes = ((Type1Font) getFontProgram()).getFontStreamBytes();
        if (fontStreamBytes != null) {
            PdfStream fontStream = new PdfStream(fontStreamBytes);
            int[] fontStreamLengths = ((Type1Font) getFontProgram()).getFontStreamLengths();
            for (int k = 0; k < fontStreamLengths.length; k++) {
                fontStream.put(new PdfName("Length" + (k + 1)), new PdfNumber(fontStreamLengths[k]));
            }
            fontDescriptor.put(PdfName.FontFile, fontStream);
            if (makeObjectIndirect(fontStream)) {
                fontStream.flush();
            }
        }
    }
}
