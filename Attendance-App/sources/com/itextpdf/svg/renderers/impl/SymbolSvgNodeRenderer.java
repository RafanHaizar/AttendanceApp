package com.itextpdf.svg.renderers.impl;

import com.itextpdf.svg.renderers.INoDrawSvgNodeRenderer;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;

public class SymbolSvgNodeRenderer extends AbstractContainerSvgNodeRenderer implements INoDrawSvgNodeRenderer {
    public ISvgNodeRenderer createDeepCopy() {
        SymbolSvgNodeRenderer copy = new SymbolSvgNodeRenderer();
        deepCopyAttributesAndStyles(copy);
        deepCopyChildren(copy);
        return copy;
    }
}
