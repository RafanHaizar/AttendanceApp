package com.itextpdf.svg.renderers.impl;

import com.itextpdf.svg.exceptions.SvgLogMessageConstant;
import com.itextpdf.svg.renderers.INoDrawSvgNodeRenderer;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.SvgDrawContext;

public class DefsSvgNodeRenderer extends AbstractBranchSvgNodeRenderer implements INoDrawSvgNodeRenderer {
    /* access modifiers changed from: protected */
    public void doDraw(SvgDrawContext context) {
        throw new UnsupportedOperationException(SvgLogMessageConstant.DRAW_NO_DRAW);
    }

    public ISvgNodeRenderer createDeepCopy() {
        DefsSvgNodeRenderer copy = new DefsSvgNodeRenderer();
        deepCopyAttributesAndStyles(copy);
        return copy;
    }
}
