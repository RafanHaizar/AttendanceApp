package com.itextpdf.svg.renderers.impl;

import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.SvgDrawContext;

public class GroupSvgNodeRenderer extends AbstractBranchSvgNodeRenderer {
    /* access modifiers changed from: protected */
    public void doDraw(SvgDrawContext context) {
        PdfCanvas currentCanvas = context.getCurrentCanvas();
        for (ISvgNodeRenderer child : getChildren()) {
            currentCanvas.saveState();
            child.draw(context);
            currentCanvas.restoreState();
        }
    }

    public ISvgNodeRenderer createDeepCopy() {
        GroupSvgNodeRenderer copy = new GroupSvgNodeRenderer();
        deepCopyAttributesAndStyles(copy);
        deepCopyChildren(copy);
        return copy;
    }
}
