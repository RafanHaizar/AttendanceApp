package com.itextpdf.kernel.pdf.canvas.parser.util;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.filters.DoNothingFilter;
import com.itextpdf.kernel.pdf.filters.FilterHandlers;
import com.itextpdf.kernel.pdf.filters.FlateDecodeStrictFilter;
import com.itextpdf.kernel.pdf.filters.IFilterHandler;
import com.itextpdf.p026io.source.PdfTokenizer;
import com.itextpdf.pdfa.checker.PdfAChecker;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class InlineImageParsingUtils {

    /* renamed from: EI */
    private static final byte[] f1496EI = {69, 73};
    private static final Map<PdfName, PdfName> inlineImageColorSpaceAbbreviationMap;
    private static final Map<PdfName, PdfName> inlineImageEntryAbbreviationMap;
    private static final Map<PdfName, PdfName> inlineImageFilterAbbreviationMap;

    static {
        HashMap hashMap = new HashMap();
        inlineImageEntryAbbreviationMap = hashMap;
        hashMap.put(PdfName.BitsPerComponent, PdfName.BitsPerComponent);
        hashMap.put(PdfName.ColorSpace, PdfName.ColorSpace);
        hashMap.put(PdfName.Decode, PdfName.Decode);
        hashMap.put(PdfName.DecodeParms, PdfName.DecodeParms);
        hashMap.put(PdfName.Filter, PdfName.Filter);
        hashMap.put(PdfName.Height, PdfName.Height);
        hashMap.put(PdfName.ImageMask, PdfName.ImageMask);
        hashMap.put(PdfName.Intent, PdfName.Intent);
        hashMap.put(PdfName.Interpolate, PdfName.Interpolate);
        hashMap.put(PdfName.Width, PdfName.Width);
        hashMap.put(new PdfName("BPC"), PdfName.BitsPerComponent);
        hashMap.put(new PdfName("CS"), PdfName.ColorSpace);
        hashMap.put(new PdfName("D"), PdfName.Decode);
        hashMap.put(new PdfName("DP"), PdfName.DecodeParms);
        hashMap.put(new PdfName("F"), PdfName.Filter);
        hashMap.put(new PdfName("H"), PdfName.Height);
        hashMap.put(new PdfName("IM"), PdfName.ImageMask);
        hashMap.put(new PdfName("I"), PdfName.Interpolate);
        hashMap.put(new PdfName("W"), PdfName.Width);
        HashMap hashMap2 = new HashMap();
        inlineImageColorSpaceAbbreviationMap = hashMap2;
        hashMap2.put(new PdfName("G"), PdfName.DeviceGray);
        hashMap2.put(new PdfName("RGB"), PdfName.DeviceRGB);
        hashMap2.put(new PdfName(PdfAChecker.ICC_COLOR_SPACE_CMYK), PdfName.DeviceCMYK);
        hashMap2.put(new PdfName("I"), PdfName.Indexed);
        HashMap hashMap3 = new HashMap();
        inlineImageFilterAbbreviationMap = hashMap3;
        hashMap3.put(new PdfName("AHx"), PdfName.ASCIIHexDecode);
        hashMap3.put(new PdfName("A85"), PdfName.ASCII85Decode);
        hashMap3.put(new PdfName("LZW"), PdfName.LZWDecode);
        hashMap3.put(new PdfName("Fl"), PdfName.FlateDecode);
        hashMap3.put(new PdfName("RL"), PdfName.RunLengthDecode);
        hashMap3.put(new PdfName("CCF"), PdfName.CCITTFaxDecode);
        hashMap3.put(new PdfName("DCT"), PdfName.DCTDecode);
    }

    private InlineImageParsingUtils() {
    }

    public static class InlineImageParseException extends PdfException implements Serializable {
        private static final long serialVersionUID = 233760879000268548L;

        public InlineImageParseException(String message) {
            super(message);
        }
    }

    public static PdfStream parse(PdfCanvasParser ps, PdfDictionary colorSpaceDic) throws IOException {
        PdfDictionary inlineImageDict = parseDictionary(ps);
        PdfStream inlineImageAsStreamObject = new PdfStream(parseSamples(inlineImageDict, colorSpaceDic, ps));
        inlineImageAsStreamObject.putAll(inlineImageDict);
        return inlineImageAsStreamObject;
    }

    private static PdfDictionary parseDictionary(PdfCanvasParser ps) throws IOException {
        PdfDictionary dict = new PdfDictionary();
        PdfObject key = ps.readObject();
        while (key != null && !"ID".equals(key.toString())) {
            PdfObject value = ps.readObject();
            PdfName resolvedKey = inlineImageEntryAbbreviationMap.get((PdfName) key);
            if (resolvedKey == null) {
                resolvedKey = (PdfName) key;
            }
            dict.put(resolvedKey, getAlternateValue(resolvedKey, value));
            key = ps.readObject();
        }
        int ch = ps.getTokeniser().read();
        if (PdfTokenizer.isWhitespace(ch)) {
            return dict;
        }
        throw new InlineImageParseException(PdfException.UnexpectedCharacter1FoundAfterIDInInlineImage).setMessageParams(Integer.valueOf(ch));
    }

    private static PdfObject getAlternateValue(PdfName key, PdfObject value) {
        PdfName altValue;
        if (key == PdfName.Filter) {
            if (value instanceof PdfName) {
                PdfName altValue2 = inlineImageFilterAbbreviationMap.get((PdfName) value);
                if (altValue2 != null) {
                    return altValue2;
                }
            } else if (value instanceof PdfArray) {
                PdfArray array = (PdfArray) value;
                PdfArray altArray = new PdfArray();
                int count = array.size();
                for (int i = 0; i < count; i++) {
                    altArray.add(getAlternateValue(key, array.get(i)));
                }
                return altArray;
            }
        } else if (key != PdfName.ColorSpace || !(value instanceof PdfName) || (altValue = inlineImageColorSpaceAbbreviationMap.get((PdfName) value)) == null) {
            return value;
        } else {
            return altValue;
        }
        return value;
    }

    private static int getComponentsPerPixel(PdfName colorSpaceName, PdfDictionary colorSpaceDic) {
        if (colorSpaceName == null || colorSpaceName.equals(PdfName.DeviceGray)) {
            return 1;
        }
        if (colorSpaceName.equals(PdfName.DeviceRGB)) {
            return 3;
        }
        if (colorSpaceName.equals(PdfName.DeviceCMYK)) {
            return 4;
        }
        if (colorSpaceDic != null) {
            PdfArray colorSpace = colorSpaceDic.getAsArray(colorSpaceName);
            if (colorSpace == null) {
                PdfName tempName = colorSpaceDic.getAsName(colorSpaceName);
                if (tempName != null) {
                    return getComponentsPerPixel(tempName, colorSpaceDic);
                }
            } else if (PdfName.Indexed.equals(colorSpace.getAsName(0))) {
                return 1;
            }
        }
        throw new InlineImageParseException(PdfException.UnexpectedColorSpace1).setMessageParams(colorSpaceName);
    }

    private static int computeBytesPerRow(PdfDictionary imageDictionary, PdfDictionary colorSpaceDic) {
        PdfNumber wObj = imageDictionary.getAsNumber(PdfName.Width);
        PdfNumber bpcObj = imageDictionary.getAsNumber(PdfName.BitsPerComponent);
        return (((wObj.intValue() * (bpcObj != null ? bpcObj.intValue() : 1)) * getComponentsPerPixel(imageDictionary.getAsName(PdfName.ColorSpace), colorSpaceDic)) + 7) / 8;
    }

    private static byte[] parseUnfilteredSamples(PdfDictionary imageDictionary, PdfDictionary colorSpaceDic, PdfCanvasParser ps) throws IOException {
        if (!imageDictionary.containsKey(PdfName.Filter)) {
            int bytesToRead = computeBytesPerRow(imageDictionary, colorSpaceDic) * imageDictionary.getAsNumber(PdfName.Height).intValue();
            byte[] bytes = new byte[bytesToRead];
            PdfTokenizer tokeniser = ps.getTokeniser();
            int shouldBeWhiteSpace = tokeniser.read();
            int startIndex = 0;
            if (!PdfTokenizer.isWhitespace(shouldBeWhiteSpace) || shouldBeWhiteSpace == 0) {
                bytes[0] = (byte) shouldBeWhiteSpace;
                startIndex = 0 + 1;
            }
            int i = startIndex;
            while (i < bytesToRead) {
                int ch = tokeniser.read();
                if (ch != -1) {
                    bytes[i] = (byte) ch;
                    i++;
                } else {
                    throw new InlineImageParseException(PdfException.EndOfContentStreamReachedBeforeEndOfImageData);
                }
            }
            if ("EI".equals(ps.readObject().toString()) || "EI".equals(ps.readObject().toString())) {
                return bytes;
            }
            throw new InlineImageParseException(PdfException.OperatorEINotFoundAfterEndOfImageData);
        }
        throw new IllegalArgumentException("Dictionary contains filters");
    }

    private static byte[] parseSamples(PdfDictionary imageDictionary, PdfDictionary colorSpaceDic, PdfCanvasParser ps) throws IOException {
        if (!imageDictionary.containsKey(PdfName.Filter) && imageColorSpaceIsKnown(imageDictionary, colorSpaceDic)) {
            return parseUnfilteredSamples(imageDictionary, colorSpaceDic, ps);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int found = 0;
        PdfTokenizer tokeniser = ps.getTokeniser();
        while (true) {
            int read = tokeniser.read();
            int ch = read;
            if (read == -1) {
                throw new InlineImageParseException(PdfException.CannotFindImageDataOrEI);
            } else if (ch == 69) {
                baos.write(f1496EI, 0, found);
                found = 1;
            } else if (found == 1 && ch == 73) {
                found = 2;
            } else {
                if (found == 2 && PdfTokenizer.isWhitespace(ch)) {
                    byte[] tmp = baos.toByteArray();
                    if (inlineImageStreamBytesAreComplete(tmp, imageDictionary)) {
                        return tmp;
                    }
                }
                baos.write(f1496EI, 0, found);
                baos.write(ch);
                found = 0;
            }
        }
    }

    private static boolean imageColorSpaceIsKnown(PdfDictionary imageDictionary, PdfDictionary colorSpaceDic) {
        PdfName cs = imageDictionary.getAsName(PdfName.ColorSpace);
        if (cs == null || cs.equals(PdfName.DeviceGray) || cs.equals(PdfName.DeviceRGB) || cs.equals(PdfName.DeviceCMYK)) {
            return true;
        }
        if (colorSpaceDic == null || !colorSpaceDic.containsKey(cs)) {
            return false;
        }
        return true;
    }

    private static boolean inlineImageStreamBytesAreComplete(byte[] samples, PdfDictionary imageDictionary) {
        try {
            Map<PdfName, IFilterHandler> filters = new HashMap<>(FilterHandlers.getDefaultFilterHandlers());
            filters.put(PdfName.JBIG2Decode, new DoNothingFilter());
            filters.put(PdfName.FlateDecode, new FlateDecodeStrictFilter());
            PdfReader.decodeBytes(samples, imageDictionary, filters);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
