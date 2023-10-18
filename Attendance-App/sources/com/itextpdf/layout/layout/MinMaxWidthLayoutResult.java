package com.itextpdf.layout.layout;

import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
import com.itextpdf.layout.renderer.IRenderer;

public class MinMaxWidthLayoutResult extends LayoutResult {
    protected MinMaxWidth minMaxWidth = new MinMaxWidth();

    public MinMaxWidthLayoutResult(int status, LayoutArea occupiedArea, IRenderer splitRenderer, IRenderer overflowRenderer) {
        super(status, occupiedArea, splitRenderer, overflowRenderer);
    }

    public MinMaxWidthLayoutResult(int status, LayoutArea occupiedArea, IRenderer splitRenderer, IRenderer overflowRenderer, IRenderer cause) {
        super(status, occupiedArea, splitRenderer, overflowRenderer, cause);
    }

    public MinMaxWidth getMinMaxWidth() {
        return this.minMaxWidth;
    }

    public MinMaxWidthLayoutResult setMinMaxWidth(MinMaxWidth minMaxWidth2) {
        this.minMaxWidth = minMaxWidth2;
        return this;
    }
}
