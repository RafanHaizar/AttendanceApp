package com.itextpdf.kernel.pdf.canvas.parser.listener;

import com.itextpdf.kernel.geom.Rectangle;

public class DefaultPdfTextLocation implements IPdfTextLocation {
    private int pageNr;
    private Rectangle rectangle;
    private String text;

    public DefaultPdfTextLocation(int pageNr2, Rectangle rect, String text2) {
        this.pageNr = pageNr2;
        this.rectangle = rect;
        this.text = text2;
    }

    public Rectangle getRectangle() {
        return this.rectangle;
    }

    public DefaultPdfTextLocation setRectangle(Rectangle rectangle2) {
        this.rectangle = rectangle2;
        return this;
    }

    public String getText() {
        return this.text;
    }

    public DefaultPdfTextLocation setText(String text2) {
        this.text = text2;
        return this;
    }

    public int getPageNumber() {
        return this.pageNr;
    }

    public DefaultPdfTextLocation setPageNr(int pageNr2) {
        this.pageNr = pageNr2;
        return this;
    }
}
