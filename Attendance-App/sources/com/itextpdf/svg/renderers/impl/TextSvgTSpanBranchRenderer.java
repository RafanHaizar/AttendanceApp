package com.itextpdf.svg.renderers.impl;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.svg.renderers.SvgDrawContext;

public class TextSvgTSpanBranchRenderer extends TextSvgBranchRenderer {
    public TextSvgTSpanBranchRenderer() {
        this.performRootTransformations = false;
    }

    /* access modifiers changed from: protected */
    public Rectangle getObjectBoundingBox(SvgDrawContext context) {
        if (getParent() instanceof AbstractSvgNodeRenderer) {
            return ((AbstractSvgNodeRenderer) getParent()).getObjectBoundingBox(context);
        }
        return null;
    }
}
