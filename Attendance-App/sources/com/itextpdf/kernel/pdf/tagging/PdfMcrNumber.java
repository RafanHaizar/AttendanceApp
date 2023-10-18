package com.itextpdf.kernel.pdf.tagging;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfPage;

public class PdfMcrNumber extends PdfMcr {
    private static final long serialVersionUID = -9039654592261202430L;

    public PdfMcrNumber(PdfNumber pdfObject, PdfStructElem parent) {
        super(pdfObject, parent);
    }

    public PdfMcrNumber(PdfPage page, PdfStructElem parent) {
        super(new PdfNumber(page.getNextMcid()), parent);
    }

    public int getMcid() {
        return ((PdfNumber) getPdfObject()).intValue();
    }

    public PdfDictionary getPageObject() {
        return super.getPageObject();
    }
}
