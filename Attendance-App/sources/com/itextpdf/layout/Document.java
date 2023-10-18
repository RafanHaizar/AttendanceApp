package com.itextpdf.layout;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.ILargeElement;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.RootRenderer;

public class Document extends RootElement<Document> {
    @Deprecated
    protected float bottomMargin;
    @Deprecated
    protected float leftMargin;
    @Deprecated
    protected float rightMargin;
    @Deprecated
    protected float topMargin;

    public Document(PdfDocument pdfDoc) {
        this(pdfDoc, pdfDoc.getDefaultPageSize());
    }

    public Document(PdfDocument pdfDoc, PageSize pageSize) {
        this(pdfDoc, pageSize, true);
    }

    public Document(PdfDocument pdfDoc, PageSize pageSize, boolean immediateFlush) {
        this.leftMargin = 36.0f;
        this.rightMargin = 36.0f;
        this.topMargin = 36.0f;
        this.bottomMargin = 36.0f;
        this.pdfDocument = pdfDoc;
        this.pdfDocument.setDefaultPageSize(pageSize);
        this.immediateFlush = immediateFlush;
    }

    public void close() {
        if (this.rootRenderer != null) {
            this.rootRenderer.close();
        }
        this.pdfDocument.close();
    }

    public Document add(AreaBreak areaBreak) {
        checkClosingStatus();
        this.childElements.add(areaBreak);
        ensureRootRendererNotNull().addChild(areaBreak.createRendererSubTree());
        if (this.immediateFlush) {
            this.childElements.remove(this.childElements.size() - 1);
        }
        return this;
    }

    public Document add(IBlockElement element) {
        checkClosingStatus();
        super.add(element);
        if (element instanceof ILargeElement) {
            ((ILargeElement) element).setDocument(this);
            ((ILargeElement) element).flushContent();
        }
        return this;
    }

    public PdfDocument getPdfDocument() {
        return this.pdfDocument;
    }

    public void setRenderer(DocumentRenderer documentRenderer) {
        this.rootRenderer = documentRenderer;
    }

    public void flush() {
        this.rootRenderer.flush();
    }

    public void relayout() {
        if (!this.immediateFlush) {
            IRenderer nextRelayoutRenderer = this.rootRenderer != null ? this.rootRenderer.getNextRenderer() : null;
            if (nextRelayoutRenderer == null || !(nextRelayoutRenderer instanceof RootRenderer)) {
                nextRelayoutRenderer = new DocumentRenderer(this, this.immediateFlush);
            }
            while (this.pdfDocument.getNumberOfPages() > 0) {
                this.pdfDocument.removePage(this.pdfDocument.getNumberOfPages());
            }
            this.rootRenderer = (RootRenderer) nextRelayoutRenderer;
            for (IElement element : this.childElements) {
                createAndAddRendererSubTree(element);
            }
            return;
        }
        throw new IllegalStateException("Operation not supported with immediate flush");
    }

    public float getLeftMargin() {
        Float property = (Float) getProperty(44);
        return (property != null ? property : (Float) getDefaultProperty(44)).floatValue();
    }

    public void setLeftMargin(float leftMargin2) {
        setProperty(44, Float.valueOf(leftMargin2));
        this.leftMargin = leftMargin2;
    }

    public float getRightMargin() {
        Float property = (Float) getProperty(45);
        return (property != null ? property : (Float) getDefaultProperty(45)).floatValue();
    }

    public void setRightMargin(float rightMargin2) {
        setProperty(45, Float.valueOf(rightMargin2));
        this.rightMargin = rightMargin2;
    }

    public float getTopMargin() {
        Float property = (Float) getProperty(46);
        return (property != null ? property : (Float) getDefaultProperty(46)).floatValue();
    }

    public void setTopMargin(float topMargin2) {
        setProperty(46, Float.valueOf(topMargin2));
        this.topMargin = topMargin2;
    }

    public float getBottomMargin() {
        Float property = (Float) getProperty(43);
        return (property != null ? property : (Float) getDefaultProperty(43)).floatValue();
    }

    public void setBottomMargin(float bottomMargin2) {
        setProperty(43, Float.valueOf(bottomMargin2));
        this.bottomMargin = bottomMargin2;
    }

    public void setMargins(float topMargin2, float rightMargin2, float bottomMargin2, float leftMargin2) {
        setTopMargin(topMargin2);
        setRightMargin(rightMargin2);
        setBottomMargin(bottomMargin2);
        setLeftMargin(leftMargin2);
    }

    public Rectangle getPageEffectiveArea(PageSize pageSize) {
        return new Rectangle(pageSize.getLeft() + getLeftMargin(), pageSize.getBottom() + getBottomMargin(), (pageSize.getWidth() - getLeftMargin()) - getRightMargin(), (pageSize.getHeight() - getBottomMargin()) - getTopMargin());
    }

    public <T1> T1 getDefaultProperty(int property) {
        switch (property) {
            case 43:
            case 44:
            case 45:
            case 46:
                return Float.valueOf(36.0f);
            default:
                return super.getDefaultProperty(property);
        }
    }

    /* access modifiers changed from: protected */
    public RootRenderer ensureRootRendererNotNull() {
        if (this.rootRenderer == null) {
            this.rootRenderer = new DocumentRenderer(this, this.immediateFlush);
        }
        return this.rootRenderer;
    }

    /* access modifiers changed from: protected */
    public void checkClosingStatus() {
        if (getPdfDocument().isClosed()) {
            throw new PdfException(PdfException.DocumentClosedItIsImpossibleToExecuteAction);
        }
    }
}
