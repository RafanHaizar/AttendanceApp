package com.itextpdf.pdfa;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;

class PdfAPage extends PdfPage {
    PdfAPage(PdfDocument pdfDocument, PageSize pageSize) {
        super(pdfDocument, pageSize);
    }

    PdfAPage(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public void flush(boolean flushResourcesContentStreams) {
        if (flushResourcesContentStreams || ((PdfADocument) getDocument()).isClosing() || ((PdfADocument) getDocument()).checker.objectIsChecked(getPdfObject())) {
            super.flush(flushResourcesContentStreams);
        } else {
            ((PdfADocument) getDocument()).logThatPdfAPageFlushingWasNotPerformed();
        }
    }
}
