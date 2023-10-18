package com.itextpdf.svg.renderers.impl;

import com.itextpdf.svg.exceptions.SvgLogMessageConstant;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.SvgDrawContext;

@Deprecated
public class NoDrawOperationSvgNodeRenderer extends AbstractBranchSvgNodeRenderer {
    /* access modifiers changed from: protected */
    public void doDraw(SvgDrawContext context) {
        throw new UnsupportedOperationException(SvgLogMessageConstant.DRAW_NO_DRAW);
    }

    public ISvgNodeRenderer createDeepCopy() {
        NoDrawOperationSvgNodeRenderer copy = new NoDrawOperationSvgNodeRenderer();
        deepCopyAttributesAndStyles(copy);
        return copy;
    }
}
