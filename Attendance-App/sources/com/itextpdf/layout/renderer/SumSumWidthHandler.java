package com.itextpdf.layout.renderer;

import com.itextpdf.layout.minmaxwidth.MinMaxWidth;

class SumSumWidthHandler extends AbstractWidthHandler {
    public SumSumWidthHandler(MinMaxWidth minMaxWidth) {
        super(minMaxWidth);
    }

    public void updateMinChildWidth(float childMinWidth) {
        this.minMaxWidth.setChildrenMinWidth(this.minMaxWidth.getChildrenMinWidth() + childMinWidth);
    }

    public void updateMaxChildWidth(float childMaxWidth) {
        this.minMaxWidth.setChildrenMaxWidth(this.minMaxWidth.getChildrenMaxWidth() + childMaxWidth);
    }
}
