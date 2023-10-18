package com.itextpdf.layout.renderer;

import com.itextpdf.layout.minmaxwidth.MinMaxWidth;

class MaxSumWidthHandler extends AbstractWidthHandler {
    public MaxSumWidthHandler(MinMaxWidth minMaxWidth) {
        super(minMaxWidth);
    }

    public void updateMinChildWidth(float childMinWidth) {
        this.minMaxWidth.setChildrenMinWidth(Math.max(this.minMaxWidth.getChildrenMinWidth(), childMinWidth));
    }

    public void updateMaxChildWidth(float childMaxWidth) {
        this.minMaxWidth.setChildrenMaxWidth(this.minMaxWidth.getChildrenMaxWidth() + childMaxWidth);
    }
}
