package com.itextpdf.layout;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.renderer.CanvasRenderer;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.RootRenderer;
import com.itextpdf.p026io.LogMessageConstant;
import org.slf4j.LoggerFactory;

public class Canvas extends RootElement<Canvas> {
    private boolean isCanvasOfPage;
    protected PdfPage page;
    protected PdfCanvas pdfCanvas;
    protected Rectangle rootArea;

    public Canvas(PdfPage page2, Rectangle rootArea2) {
        this(initPdfCanvasOrThrowIfPageIsFlushed(page2), rootArea2);
        enableAutoTagging(page2);
        this.isCanvasOfPage = true;
    }

    public Canvas(PdfCanvas pdfCanvas2, Rectangle rootArea2) {
        this.pdfDocument = pdfCanvas2.getDocument();
        this.pdfCanvas = pdfCanvas2;
        this.rootArea = rootArea2;
    }

    @Deprecated
    public Canvas(PdfCanvas pdfCanvas2, PdfDocument pdfDocument, Rectangle rootArea2) {
        this.pdfDocument = pdfDocument;
        this.pdfCanvas = pdfCanvas2;
        this.rootArea = rootArea2;
    }

    public Canvas(PdfCanvas pdfCanvas2, Rectangle rootArea2, boolean immediateFlush) {
        this(pdfCanvas2, rootArea2);
        this.immediateFlush = immediateFlush;
    }

    @Deprecated
    public Canvas(PdfCanvas pdfCanvas2, PdfDocument pdfDocument, Rectangle rootArea2, boolean immediateFlush) {
        this(pdfCanvas2, rootArea2);
        this.immediateFlush = immediateFlush;
    }

    public Canvas(PdfFormXObject formXObject, PdfDocument pdfDocument) {
        this(new PdfCanvas(formXObject, pdfDocument), formXObject.getBBox().toRectangle());
    }

    public PdfDocument getPdfDocument() {
        return this.pdfDocument;
    }

    public Rectangle getRootArea() {
        return this.rootArea;
    }

    public PdfCanvas getPdfCanvas() {
        return this.pdfCanvas;
    }

    public void setRenderer(CanvasRenderer canvasRenderer) {
        this.rootRenderer = canvasRenderer;
    }

    public PdfPage getPage() {
        return this.page;
    }

    public void enableAutoTagging(PdfPage page2) {
        if (isCanvasOfPage() && this.page != page2) {
            LoggerFactory.getLogger((Class<?>) Canvas.class).error(LogMessageConstant.PASSED_PAGE_SHALL_BE_ON_WHICH_CANVAS_WILL_BE_RENDERED);
        }
        this.page = page2;
    }

    public boolean isAutoTaggingEnabled() {
        return this.page != null;
    }

    public boolean isCanvasOfPage() {
        return this.isCanvasOfPage;
    }

    public void relayout() {
        if (!this.immediateFlush) {
            IRenderer nextRelayoutRenderer = this.rootRenderer != null ? this.rootRenderer.getNextRenderer() : null;
            if (nextRelayoutRenderer == null || !(nextRelayoutRenderer instanceof RootRenderer)) {
                nextRelayoutRenderer = new CanvasRenderer(this, this.immediateFlush);
            }
            this.rootRenderer = (RootRenderer) nextRelayoutRenderer;
            for (IElement element : this.childElements) {
                createAndAddRendererSubTree(element);
            }
            return;
        }
        throw new IllegalStateException("Operation not supported with immediate flush");
    }

    public void flush() {
        this.rootRenderer.flush();
    }

    public void close() {
        if (this.rootRenderer != null) {
            this.rootRenderer.close();
        }
    }

    /* access modifiers changed from: protected */
    public RootRenderer ensureRootRendererNotNull() {
        if (this.rootRenderer == null) {
            this.rootRenderer = new CanvasRenderer(this, this.immediateFlush);
        }
        return this.rootRenderer;
    }

    private static PdfCanvas initPdfCanvasOrThrowIfPageIsFlushed(PdfPage page2) {
        if (!page2.isFlushed()) {
            return new PdfCanvas(page2);
        }
        throw new PdfException(PdfException.CannotDrawElementsOnAlreadyFlushedPages);
    }
}
