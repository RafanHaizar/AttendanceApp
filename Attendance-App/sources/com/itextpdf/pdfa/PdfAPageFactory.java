package com.itextpdf.pdfa;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.IPdfPageFactory;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;

class PdfAPageFactory implements IPdfPageFactory {
    PdfAPageFactory() {
    }

    public PdfPage createPdfPage(PdfDictionary pdfObject) {
        return new PdfAPage(pdfObject);
    }

    public PdfPage createPdfPage(PdfDocument pdfDocument, PageSize pageSize) {
        return new PdfAPage(pdfDocument, pageSize);
    }
}
