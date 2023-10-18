package com.itextpdf.layout.renderer;

import com.itextpdf.layout.minmaxwidth.MinMaxWidth;

public class MaxMaxWidthHandler extends AbstractWidthHandler {
    public MaxMaxWidthHandler(MinMaxWidth minMaxWidth) {
        super(minMaxWidth);
    }

    public void updateMinChildWidth(float childMinWidth) {
        this.minMaxWidth.setChildrenMinWidth(Math.max(this.minMaxWidth.getChildrenMinWidth(), childMinWidth));
    }

    public void updateMaxChildWidth(float childMaxWidth) {
        this.minMaxWidth.setChildrenMaxWidth(Math.max(this.minMaxWidth.getChildrenMaxWidth(), childMaxWidth));
    }
}
