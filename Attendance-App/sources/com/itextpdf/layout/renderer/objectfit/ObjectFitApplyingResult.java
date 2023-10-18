package com.itextpdf.layout.renderer.objectfit;

public class ObjectFitApplyingResult {
    private boolean imageCuttingRequired;
    private double renderedImageHeight;
    private double renderedImageWidth;

    public ObjectFitApplyingResult() {
    }

    public ObjectFitApplyingResult(double renderedImageWidth2, double renderedImageHeight2, boolean imageCuttingRequired2) {
        this.renderedImageWidth = renderedImageWidth2;
        this.renderedImageHeight = renderedImageHeight2;
        this.imageCuttingRequired = imageCuttingRequired2;
    }

    public double getRenderedImageWidth() {
        return this.renderedImageWidth;
    }

    public void setRenderedImageWidth(double renderedImageWidth2) {
        this.renderedImageWidth = renderedImageWidth2;
    }

    public double getRenderedImageHeight() {
        return this.renderedImageHeight;
    }

    public void setRenderedImageHeight(double renderedImageHeight2) {
        this.renderedImageHeight = renderedImageHeight2;
    }

    public boolean isImageCuttingRequired() {
        return this.imageCuttingRequired;
    }

    public void setImageCuttingRequired(boolean imageCuttingRequired2) {
        this.imageCuttingRequired = imageCuttingRequired2;
    }
}
