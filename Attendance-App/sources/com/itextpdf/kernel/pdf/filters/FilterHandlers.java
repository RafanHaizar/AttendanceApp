package com.itextpdf.kernel.pdf.filters;

import com.itextpdf.kernel.pdf.PdfName;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class FilterHandlers {
    private static final Map<PdfName, IFilterHandler> defaults;

    static {
        Map<PdfName, IFilterHandler> map = new HashMap<>();
        map.put(PdfName.FlateDecode, new FlateDecodeFilter());
        map.put(PdfName.f1329Fl, new FlateDecodeFilter());
        map.put(PdfName.ASCIIHexDecode, new ASCIIHexDecodeFilter());
        map.put(PdfName.AHx, new ASCIIHexDecodeFilter());
        map.put(PdfName.ASCII85Decode, new ASCII85DecodeFilter());
        map.put(PdfName.A85, new ASCII85DecodeFilter());
        map.put(PdfName.LZWDecode, new LZWDecodeFilter());
        map.put(PdfName.CCITTFaxDecode, new CCITTFaxDecodeFilter());
        map.put(PdfName.Crypt, new DoNothingFilter());
        map.put(PdfName.RunLengthDecode, new RunLengthDecodeFilter());
        map.put(PdfName.DCTDecode, new DctDecodeFilter());
        map.put(PdfName.JPXDecode, new JpxDecodeFilter());
        defaults = Collections.unmodifiableMap(map);
    }

    public static Map<PdfName, IFilterHandler> getDefaultFilterHandlers() {
        return defaults;
    }
}
