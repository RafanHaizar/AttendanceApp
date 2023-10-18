package com.itextpdf.kernel.pdf.filters;

import com.itextpdf.kernel.pdf.MemoryLimitsAwareFilter;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import java.io.ByteArrayOutputStream;

public class LZWDecodeFilter extends MemoryLimitsAwareFilter {
    public static byte[] LZWDecode(byte[] in) {
        return LZWDecodeInternal(in, new ByteArrayOutputStream());
    }

    public byte[] decode(byte[] b, PdfName filterName, PdfObject decodeParams, PdfDictionary streamDictionary) {
        return FlateDecodeFilter.decodePredictor(LZWDecodeInternal(b, enableMemoryLimitsAwareHandler(streamDictionary)), decodeParams);
    }

    private static byte[] LZWDecodeInternal(byte[] in, ByteArrayOutputStream out) {
        new LZWDecoder().decode(in, out);
        return out.toByteArray();
    }
}
