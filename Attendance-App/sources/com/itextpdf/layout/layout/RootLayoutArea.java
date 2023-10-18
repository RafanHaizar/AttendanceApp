package com.itextpdf.layout.layout;

import com.itextpdf.kernel.geom.Rectangle;

public class RootLayoutArea extends LayoutArea implements Cloneable {
    protected boolean emptyArea = true;

    public RootLayoutArea(int pageNumber, Rectangle bBox) {
        super(pageNumber, bBox);
    }

    public boolean isEmptyArea() {
        return this.emptyArea;
    }

    public void setEmptyArea(boolean emptyArea2) {
        this.emptyArea = emptyArea2;
    }

    public LayoutArea clone() {
        RootLayoutArea area = (RootLayoutArea) super.clone();
        area.setEmptyArea(this.emptyArea);
        return area;
    }
}
