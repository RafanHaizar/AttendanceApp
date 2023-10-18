package com.itextpdf.kernel.pdf.annot;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;

class BorderStyleUtil {
    private BorderStyleUtil() {
    }

    public static final PdfDictionary setStyle(PdfDictionary bs, PdfName style) {
        if (bs == null) {
            bs = new PdfDictionary();
        }
        bs.put(PdfName.f1385S, style);
        return bs;
    }

    public static final PdfDictionary setDashPattern(PdfDictionary bs, PdfArray dashPattern) {
        if (bs == null) {
            bs = new PdfDictionary();
        }
        bs.put(PdfName.f1312D, dashPattern);
        return bs;
    }
}
