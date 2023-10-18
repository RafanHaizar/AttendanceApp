package com.itextpdf.svg.renderers.impl;

import com.itextpdf.svg.renderers.ISvgNodeRenderer;

public class SvgTagSvgNodeRenderer extends AbstractContainerSvgNodeRenderer {
    public ISvgNodeRenderer createDeepCopy() {
        SvgTagSvgNodeRenderer copy = new SvgTagSvgNodeRenderer();
        deepCopyAttributesAndStyles(copy);
        deepCopyChildren(copy);
        return copy;
    }
}
