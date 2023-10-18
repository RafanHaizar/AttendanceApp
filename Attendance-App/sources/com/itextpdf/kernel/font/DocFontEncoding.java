package com.itextpdf.kernel.font;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.font.AdobeGlyphList;
import com.itextpdf.p026io.font.FontEncoding;
import com.itextpdf.p026io.font.PdfEncodings;
import com.itextpdf.p026io.font.cmap.CMapToUnicode;
import com.itextpdf.p026io.util.IntHashtable;
import com.itextpdf.p026io.util.MessageFormatUtil;
import org.slf4j.LoggerFactory;

class DocFontEncoding extends FontEncoding {
    private static final long serialVersionUID = -4248206280861742148L;

    protected DocFontEncoding() {
    }

    public static FontEncoding createDocFontEncoding(PdfObject encoding, CMapToUnicode toUnicode) {
        if (encoding != null) {
            if (encoding.isName()) {
                return FontEncoding.createFontEncoding(((PdfName) encoding).getValue());
            }
            if (encoding.isDictionary()) {
                DocFontEncoding fontEncoding = new DocFontEncoding();
                fontEncoding.differences = new String[256];
                fillBaseEncoding(fontEncoding, ((PdfDictionary) encoding).getAsName(PdfName.BaseEncoding));
                fillDifferences(fontEncoding, ((PdfDictionary) encoding).getAsArray(PdfName.Differences), toUnicode);
                return fontEncoding;
            }
        }
        if (toUnicode == null) {
            return FontEncoding.createFontSpecificEncoding();
        }
        DocFontEncoding fontEncoding2 = new DocFontEncoding();
        fontEncoding2.differences = new String[256];
        fillDifferences(fontEncoding2, toUnicode);
        return fontEncoding2;
    }

    private static void fillBaseEncoding(DocFontEncoding fontEncoding, PdfName baseEncodingName) {
        if (baseEncodingName != null) {
            fontEncoding.baseEncoding = baseEncodingName.getValue();
        }
        if (PdfName.MacRomanEncoding.equals(baseEncodingName) || PdfName.WinAnsiEncoding.equals(baseEncodingName) || PdfName.Symbol.equals(baseEncodingName) || PdfName.ZapfDingbats.equals(baseEncodingName)) {
            String enc = "Cp1252";
            if (PdfName.MacRomanEncoding.equals(baseEncodingName)) {
                enc = PdfEncodings.MACROMAN;
            } else if (PdfName.Symbol.equals(baseEncodingName)) {
                enc = "Symbol";
            } else if (PdfName.ZapfDingbats.equals(baseEncodingName)) {
                enc = "ZapfDingbats";
            }
            fontEncoding.baseEncoding = enc;
            fontEncoding.fillNamedEncoding();
            return;
        }
        fontEncoding.fillStandardEncoding();
    }

    private static void fillDifferences(DocFontEncoding fontEncoding, PdfArray diffs, CMapToUnicode toUnicode) {
        IntHashtable byte2uni = toUnicode != null ? toUnicode.createDirectMapping() : new IntHashtable();
        if (diffs != null) {
            int currentNumber = 0;
            for (int k = 0; k < diffs.size(); k++) {
                PdfObject obj = diffs.get(k);
                if (obj.isNumber()) {
                    currentNumber = ((PdfNumber) obj).intValue();
                } else if (currentNumber > 255) {
                    LoggerFactory.getLogger((Class<?>) DocFontEncoding.class).warn(MessageFormatUtil.format(LogMessageConstant.DOCFONT_HAS_ILLEGAL_DIFFERENCES, ((PdfName) obj).getValue()));
                } else {
                    String glyphName = ((PdfName) obj).getValue();
                    int unicode = AdobeGlyphList.nameToUnicode(glyphName);
                    if (unicode != -1) {
                        fontEncoding.codeToUnicode[currentNumber] = unicode;
                        fontEncoding.unicodeToCode.put(unicode, currentNumber);
                        fontEncoding.differences[currentNumber] = glyphName;
                        fontEncoding.unicodeDifferences.put(unicode, unicode);
                    } else if (byte2uni.containsKey(currentNumber)) {
                        int unicode2 = byte2uni.get(currentNumber);
                        fontEncoding.codeToUnicode[currentNumber] = unicode2;
                        fontEncoding.unicodeToCode.put(unicode2, currentNumber);
                        fontEncoding.differences[currentNumber] = glyphName;
                        fontEncoding.unicodeDifferences.put(unicode2, unicode2);
                    }
                    currentNumber++;
                }
            }
        }
    }

    private static void fillDifferences(DocFontEncoding fontEncoding, CMapToUnicode toUnicode) {
        IntHashtable byte2uni = toUnicode.createDirectMapping();
        for (int valueOf : byte2uni.getKeys()) {
            Integer code = Integer.valueOf(valueOf);
            int unicode = byte2uni.get(code.intValue());
            String glyphName = AdobeGlyphList.unicodeToName(unicode);
            fontEncoding.codeToUnicode[code.intValue()] = unicode;
            fontEncoding.unicodeToCode.put(unicode, code.intValue());
            fontEncoding.differences[code.intValue()] = glyphName;
            fontEncoding.unicodeDifferences.put(unicode, unicode);
        }
    }
}
