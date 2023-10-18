package com.itextpdf.svg.renderers.impl;

import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.SvgDrawContext;

public class ClipPathSvgNodeRenderer extends AbstractBranchSvgNodeRenderer {
    private AbstractSvgNodeRenderer clippedRenderer;

    public ISvgNodeRenderer createDeepCopy() {
        AbstractBranchSvgNodeRenderer copy = new ClipPathSvgNodeRenderer();
        deepCopyAttributesAndStyles(copy);
        deepCopyChildren(copy);
        return copy;
    }

    /* access modifiers changed from: package-private */
    public void preDraw(SvgDrawContext context) {
    }

    /* access modifiers changed from: protected */
    public void doDraw(SvgDrawContext context) {
        PdfCanvas currentCanvas = context.getCurrentCanvas();
        for (ISvgNodeRenderer child : getChildren()) {
            currentCanvas.saveState();
            if (child instanceof AbstractSvgNodeRenderer) {
                ((AbstractSvgNodeRenderer) child).setPartOfClipPath(true);
            }
            child.draw(context);
            if (child instanceof AbstractSvgNodeRenderer) {
                ((AbstractSvgNodeRenderer) child).setPartOfClipPath(false);
            }
            AbstractSvgNodeRenderer abstractSvgNodeRenderer = this.clippedRenderer;
            if (abstractSvgNodeRenderer != null) {
                abstractSvgNodeRenderer.preDraw(context);
                this.clippedRenderer.doDraw(context);
                this.clippedRenderer.postDraw(context);
            }
            currentCanvas.restoreState();
        }
    }

    public void setClippedRenderer(AbstractSvgNodeRenderer clippedRenderer2) {
        this.clippedRenderer = clippedRenderer2;
    }
}
