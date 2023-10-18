package com.itextpdf.kernel.font;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.p026io.font.CidFont;
import com.itextpdf.p026io.font.FontProgram;
import com.itextpdf.p026io.font.FontProgramFactory;
import com.itextpdf.p026io.font.PdfEncodings;
import com.itextpdf.p026io.font.TrueTypeFont;
import com.itextpdf.p026io.font.Type1Font;
import java.io.IOException;
import java.util.Set;

public final class PdfFontFactory {
    private static boolean DEFAULT_CACHED = true;
    private static boolean DEFAULT_EMBEDDING = false;
    private static String DEFAULT_ENCODING = "";

    public static PdfFont createFont() throws IOException {
        return createFont("Helvetica", DEFAULT_ENCODING);
    }

    public static PdfFont createFont(PdfDictionary fontDictionary) {
        if (checkFontDictionary(fontDictionary, PdfName.Type1, false)) {
            return new PdfType1Font(fontDictionary);
        }
        if (checkFontDictionary(fontDictionary, PdfName.Type0, false)) {
            return new PdfType0Font(fontDictionary);
        }
        if (checkFontDictionary(fontDictionary, PdfName.TrueType, false)) {
            return new PdfTrueTypeFont(fontDictionary);
        }
        if (checkFontDictionary(fontDictionary, PdfName.Type3, false)) {
            return new PdfType3Font(fontDictionary);
        }
        if (checkFontDictionary(fontDictionary, PdfName.MMType1, false)) {
            return new PdfType1Font(fontDictionary);
        }
        throw new PdfException(PdfException.DictionaryDoesntHaveSupportedFontData);
    }

    public static PdfFont createFont(String fontProgram, String encoding, PdfDocument cacheTo) throws IOException {
        PdfFont pdfFont;
        if (cacheTo != null && (pdfFont = cacheTo.findFont(fontProgram, encoding)) != null) {
            return pdfFont;
        }
        PdfFont pdfFont2 = createFont(fontProgram, encoding);
        if (cacheTo != null) {
            pdfFont2.makeIndirect(cacheTo);
        }
        return pdfFont2;
    }

    public static PdfFont createFont(String fontProgram) throws IOException {
        return createFont(fontProgram, DEFAULT_ENCODING);
    }

    public static PdfFont createFont(String fontProgram, String encoding) throws IOException {
        return createFont(fontProgram, encoding, DEFAULT_EMBEDDING);
    }

    public static PdfFont createTtcFont(byte[] ttc, int ttcIndex, String encoding, boolean embedded, boolean cached) throws IOException {
        return createFont(FontProgramFactory.createFont(ttc, ttcIndex, cached), encoding, embedded);
    }

    public static PdfFont createTtcFont(String ttc, int ttcIndex, String encoding, boolean embedded, boolean cached) throws IOException {
        return createFont(FontProgramFactory.createFont(ttc, ttcIndex, cached), encoding, embedded);
    }

    public static PdfFont createFont(String fontProgram, boolean embedded) throws IOException {
        return createFont(fontProgram, DEFAULT_ENCODING, embedded);
    }

    public static PdfFont createFont(String fontProgram, String encoding, boolean embedded) throws IOException {
        return createFont(fontProgram, encoding, embedded, DEFAULT_CACHED);
    }

    public static PdfFont createFont(String fontProgram, String encoding, boolean embedded, boolean cached) throws IOException {
        return createFont(FontProgramFactory.createFont(fontProgram, cached), encoding, embedded);
    }

    public static PdfFont createFont(FontProgram fontProgram, String encoding, boolean embedded) {
        if (fontProgram == null) {
            return null;
        }
        if (fontProgram instanceof Type1Font) {
            return new PdfType1Font((Type1Font) fontProgram, encoding, embedded);
        }
        if (fontProgram instanceof TrueTypeFont) {
            if (PdfEncodings.IDENTITY_H.equals(encoding) || PdfEncodings.IDENTITY_V.equals(encoding)) {
                return new PdfType0Font((TrueTypeFont) fontProgram, encoding);
            }
            return new PdfTrueTypeFont((TrueTypeFont) fontProgram, encoding, embedded);
        } else if (!(fontProgram instanceof CidFont) || !((CidFont) fontProgram).compatibleWith(encoding)) {
            return null;
        } else {
            return new PdfType0Font((CidFont) fontProgram, encoding);
        }
    }

    public static PdfFont createFont(FontProgram fontProgram, String encoding) {
        return createFont(fontProgram, encoding, DEFAULT_EMBEDDING);
    }

    public static PdfFont createFont(FontProgram fontProgram) {
        return createFont(fontProgram, DEFAULT_ENCODING);
    }

    public static PdfFont createFont(byte[] fontProgram, String encoding) throws IOException {
        return createFont(fontProgram, encoding, DEFAULT_EMBEDDING);
    }

    public static PdfFont createFont(byte[] fontProgram, boolean embedded) throws IOException {
        return createFont(fontProgram, (String) null, embedded);
    }

    public static PdfFont createFont(byte[] fontProgram, String encoding, boolean embedded) throws IOException {
        return createFont(fontProgram, encoding, embedded, DEFAULT_CACHED);
    }

    public static PdfFont createFont(byte[] fontProgram, String encoding, boolean embedded, boolean cached) throws IOException {
        return createFont(FontProgramFactory.createFont(fontProgram, cached), encoding, embedded);
    }

    public static PdfType3Font createType3Font(PdfDocument document, boolean colorized) {
        return new PdfType3Font(document, colorized);
    }

    public static PdfType3Font createType3Font(PdfDocument document, String fontName, String fontFamily, boolean colorized) {
        return new PdfType3Font(document, fontName, fontFamily, colorized);
    }

    public static PdfFont createRegisteredFont(String fontName, String encoding, boolean embedded, int style, boolean cached) throws IOException {
        return createFont(FontProgramFactory.createRegisteredFont(fontName, style, cached), encoding, embedded);
    }

    public static PdfFont createRegisteredFont(String fontName, String encoding, boolean embedded, boolean cached) throws IOException {
        return createRegisteredFont(fontName, encoding, embedded, -1, cached);
    }

    public static PdfFont createRegisteredFont(String fontName, String encoding, boolean embedded) throws IOException {
        return createRegisteredFont(fontName, encoding, embedded, -1);
    }

    public static PdfFont createRegisteredFont(String fontName, String encoding, boolean embedded, int style) throws IOException {
        return createRegisteredFont(fontName, encoding, embedded, style, DEFAULT_CACHED);
    }

    public static PdfFont createRegisteredFont(String fontName, String encoding) throws IOException {
        return createRegisteredFont(fontName, encoding, false, -1);
    }

    public static PdfFont createRegisteredFont(String fontName) throws IOException {
        return createRegisteredFont(fontName, (String) null, false, -1);
    }

    public static void registerFamily(String familyName, String fullName, String path) {
        FontProgramFactory.registerFontFamily(familyName, fullName, path);
    }

    public static void register(String path) {
        register(path, (String) null);
    }

    public static void register(String path, String alias) {
        FontProgramFactory.registerFont(path, alias);
    }

    public static int registerDirectory(String dirPath) {
        return FontProgramFactory.registerFontDirectory(dirPath);
    }

    public static int registerSystemDirectories() {
        return FontProgramFactory.registerSystemFontDirectories();
    }

    public static Set<String> getRegisteredFonts() {
        return FontProgramFactory.getRegisteredFonts();
    }

    public static Set<String> getRegisteredFamilies() {
        return FontProgramFactory.getRegisteredFontFamilies();
    }

    public static boolean isRegistered(String fontName) {
        return FontProgramFactory.isRegisteredFont(fontName);
    }

    private static boolean checkFontDictionary(PdfDictionary fontDic, PdfName fontType, boolean isException) {
        if (fontDic != null && fontDic.get(PdfName.Subtype) != null && fontDic.get(PdfName.Subtype).equals(fontType)) {
            return true;
        }
        if (!isException) {
            return false;
        }
        throw new PdfException(PdfException.DictionaryDoesntHave1FontData).setMessageParams(fontType.getValue());
    }
}
