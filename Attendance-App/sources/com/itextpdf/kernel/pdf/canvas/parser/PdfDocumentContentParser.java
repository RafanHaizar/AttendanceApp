package com.itextpdf.kernel.pdf.canvas.parser;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.parser.listener.IEventListener;
import java.util.HashMap;
import java.util.Map;

public class PdfDocumentContentParser {
    private final PdfDocument pdfDocument;

    public PdfDocumentContentParser(PdfDocument pdfDocument2) {
        this.pdfDocument = pdfDocument2;
    }

    public <E extends IEventListener> E processContent(int pageNumber, E renderListener, Map<String, IContentOperator> additionalContentOperators) {
        new PdfCanvasProcessor(renderListener, additionalContentOperators).processPageContent(this.pdfDocument.getPage(pageNumber));
        return renderListener;
    }

    public <E extends IEventListener> E processContent(int pageNumber, E renderListener) {
        return processContent(pageNumber, renderListener, new HashMap());
    }
}
