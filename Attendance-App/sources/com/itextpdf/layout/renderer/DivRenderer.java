package com.itextpdf.layout.renderer;

import com.itextpdf.layout.element.Div;

public class DivRenderer extends BlockRenderer {
    public DivRenderer(Div modelElement) {
        super(modelElement);
    }

    public IRenderer getNextRenderer() {
        return new DivRenderer((Div) this.modelElement);
    }
}
