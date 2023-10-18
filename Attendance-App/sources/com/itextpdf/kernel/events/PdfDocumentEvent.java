package com.itextpdf.kernel.events;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;

public class PdfDocumentEvent extends Event {
    public static final String END_PAGE = "EndPdfPage";
    public static final String INSERT_PAGE = "InsertPdfPage";
    public static final String REMOVE_PAGE = "RemovePdfPage";
    public static final String START_PAGE = "StartPdfPage";
    private PdfDocument document;
    protected PdfPage page;

    public PdfDocumentEvent(String type, PdfDocument document2) {
        super(type);
        this.document = document2;
    }

    public PdfDocumentEvent(String type, PdfPage page2) {
        super(type);
        this.page = page2;
        this.document = page2.getDocument();
    }

    public PdfDocument getDocument() {
        return this.document;
    }

    public PdfPage getPage() {
        return this.page;
    }
}
