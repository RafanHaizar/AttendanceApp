package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.geom.PageSize;

class PdfPageFactory implements IPdfPageFactory {
    PdfPageFactory() {
    }

    public PdfPage createPdfPage(PdfDictionary pdfObject) {
        return new PdfPage(pdfObject);
    }

    public PdfPage createPdfPage(PdfDocument pdfDocument, PageSize pageSize) {
        return new PdfPage(pdfDocument, pageSize);
    }
}
