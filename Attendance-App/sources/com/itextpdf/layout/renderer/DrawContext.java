package com.itextpdf.layout.renderer;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

public class DrawContext {
    private PdfCanvas canvas;
    private PdfDocument document;
    private boolean taggingEnabled;

    public DrawContext(PdfDocument document2, PdfCanvas canvas2) {
        this(document2, canvas2, false);
    }

    public DrawContext(PdfDocument document2, PdfCanvas canvas2, boolean enableTagging) {
        this.document = document2;
        this.canvas = canvas2;
        this.taggingEnabled = enableTagging;
    }

    public PdfDocument getDocument() {
        return this.document;
    }

    public PdfCanvas getCanvas() {
        return this.canvas;
    }

    public boolean isTaggingEnabled() {
        return this.taggingEnabled;
    }

    public void setTaggingEnabled(boolean taggingEnabled2) {
        this.taggingEnabled = taggingEnabled2;
    }
}
