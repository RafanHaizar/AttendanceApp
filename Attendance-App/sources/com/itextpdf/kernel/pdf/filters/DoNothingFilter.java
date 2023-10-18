package com.itextpdf.kernel.pdf.filters;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;

public class DoNothingFilter implements IFilterHandler {
    private PdfName lastFilterName;

    public byte[] decode(byte[] b, PdfName filterName, PdfObject decodeParams, PdfDictionary streamDictionary) {
        this.lastFilterName = filterName;
        return b;
    }

    @Deprecated
    public PdfName getLastFilterName() {
        return this.lastFilterName;
    }
}
