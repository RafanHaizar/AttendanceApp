package com.itextpdf.layout.renderer;

import com.itextpdf.layout.minmaxwidth.MinMaxWidth;

public abstract class AbstractWidthHandler {
    MinMaxWidth minMaxWidth;

    public abstract void updateMaxChildWidth(float f);

    public abstract void updateMinChildWidth(float f);

    public AbstractWidthHandler(MinMaxWidth minMaxWidth2) {
        this.minMaxWidth = minMaxWidth2;
    }

    public void updateMinMaxWidth(MinMaxWidth minMaxWidth2) {
        if (minMaxWidth2 != null) {
            updateMaxChildWidth(minMaxWidth2.getMaxWidth());
            updateMinChildWidth(minMaxWidth2.getMinWidth());
        }
    }
}
