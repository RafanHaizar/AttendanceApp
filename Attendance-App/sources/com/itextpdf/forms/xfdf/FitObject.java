package com.itextpdf.forms.xfdf;

import com.itextpdf.kernel.pdf.PdfObject;

public class FitObject {
    private float bottom;
    private float left;
    private PdfObject page;
    private float right;
    private float top;
    private float zoom;

    public FitObject(PdfObject page2) {
        if (page2 != null) {
            this.page = page2;
            return;
        }
        throw new XfdfException("Required Page attribute is missing.");
    }

    public PdfObject getPage() {
        return this.page;
    }

    public float getTop() {
        return this.top;
    }

    public FitObject setTop(float top2) {
        this.top = top2;
        return this;
    }

    public float getLeft() {
        return this.left;
    }

    public FitObject setLeft(float left2) {
        this.left = left2;
        return this;
    }

    public float getBottom() {
        return this.bottom;
    }

    public FitObject setBottom(float bottom2) {
        this.bottom = bottom2;
        return this;
    }

    public float getRight() {
        return this.right;
    }

    public FitObject setRight(float right2) {
        this.right = right2;
        return this;
    }

    public float getZoom() {
        return this.zoom;
    }

    public FitObject setZoom(float zoom2) {
        this.zoom = zoom2;
        return this;
    }
}
