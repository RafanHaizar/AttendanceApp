package com.itextpdf.kernel.pdf.filters;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import java.io.ByteArrayOutputStream;

public class FlateDecodeStrictFilter extends FlateDecodeFilter {
    public byte[] decode(byte[] b, PdfName filterName, PdfObject decodeParams, PdfDictionary streamDictionary) {
        return decodePredictor(flateDecode(b, enableMemoryLimitsAwareHandler(streamDictionary)), decodeParams);
    }

    private static byte[] flateDecode(byte[] in, ByteArrayOutputStream out) {
        return flateDecodeInternal(in, true, out);
    }
}
