package com.itextpdf.kernel.utils;

import com.itextpdf.kernel.pdf.PdfDocument;
import java.util.ArrayList;
import java.util.List;

public class PdfMerger {
    private boolean closeSrcDocuments;
    private boolean mergeOutlines;
    private boolean mergeTags;
    private PdfDocument pdfDocument;

    public PdfMerger(PdfDocument pdfDocument2) {
        this(pdfDocument2, true, true);
    }

    public PdfMerger(PdfDocument pdfDocument2, boolean mergeTags2, boolean mergeOutlines2) {
        this.pdfDocument = pdfDocument2;
        this.mergeTags = mergeTags2;
        this.mergeOutlines = mergeOutlines2;
    }

    public PdfMerger setCloseSourceDocuments(boolean closeSourceDocuments) {
        this.closeSrcDocuments = closeSourceDocuments;
        return this;
    }

    public PdfMerger merge(PdfDocument from, int fromPage, int toPage) {
        List<Integer> pages = new ArrayList<>(toPage - fromPage);
        for (int pageNum = fromPage; pageNum <= toPage; pageNum++) {
            pages.add(Integer.valueOf(pageNum));
        }
        return merge(from, pages);
    }

    public PdfMerger merge(PdfDocument from, List<Integer> pages) {
        if (this.mergeTags && from.isTagged()) {
            this.pdfDocument.setTagged();
        }
        if (this.mergeOutlines && from.hasOutlines()) {
            this.pdfDocument.initializeOutlines();
        }
        from.copyPagesTo(pages, this.pdfDocument);
        if (this.closeSrcDocuments) {
            from.close();
        }
        return this;
    }

    public void close() {
        this.pdfDocument.close();
    }
}
