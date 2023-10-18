package com.itextpdf.layout.renderer;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.CanvasArtifact;
import com.itextpdf.kernel.pdf.canvas.CanvasTag;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;

public class LineSeparatorRenderer extends BlockRenderer {
    public LineSeparatorRenderer(LineSeparator lineSeparator) {
        super(lineSeparator);
    }

    public LayoutResult layout(LayoutContext layoutContext) {
        Rectangle parentBBox = layoutContext.getArea().getBBox().clone();
        if (getProperty(55) != null) {
            parentBBox.moveDown(1000000.0f - parentBBox.getHeight()).setHeight(1000000.0f);
        }
        ILineDrawer lineDrawer = (ILineDrawer) getProperty(35);
        float height = lineDrawer != null ? lineDrawer.getLineWidth() : 0.0f;
        this.occupiedArea = new LayoutArea(layoutContext.getArea().getPageNumber(), parentBBox.clone());
        applyMargins(this.occupiedArea.getBBox(), false);
        Float calculatedWidth = retrieveWidth(layoutContext.getArea().getBBox().getWidth());
        if (calculatedWidth == null) {
            calculatedWidth = Float.valueOf(this.occupiedArea.getBBox().getWidth());
        }
        if ((this.occupiedArea.getBBox().getHeight() < height || this.occupiedArea.getBBox().getWidth() < calculatedWidth.floatValue()) && !hasOwnProperty(26)) {
            return new LayoutResult(3, (LayoutArea) null, (IRenderer) null, this, this);
        }
        this.occupiedArea.getBBox().setWidth(calculatedWidth.floatValue()).moveUp(this.occupiedArea.getBBox().getHeight() - height).setHeight(height);
        applyMargins(this.occupiedArea.getBBox(), true);
        if (getProperty(55) != null) {
            applyRotationLayout(layoutContext.getArea().getBBox().clone());
            if (isNotFittingLayoutArea(layoutContext.getArea()) && !Boolean.TRUE.equals(getPropertyAsBoolean(26))) {
                return new LayoutResult(3, (LayoutArea) null, (IRenderer) null, this, this);
            }
        }
        return new LayoutResult(1, this.occupiedArea, this, (IRenderer) null);
    }

    public IRenderer getNextRenderer() {
        return new LineSeparatorRenderer((LineSeparator) this.modelElement);
    }

    public void drawChildren(DrawContext drawContext) {
        ILineDrawer lineDrawer = (ILineDrawer) getProperty(35);
        if (lineDrawer != null) {
            PdfCanvas canvas = drawContext.getCanvas();
            boolean isTagged = drawContext.isTaggingEnabled();
            if (isTagged) {
                canvas.openTag((CanvasTag) new CanvasArtifact());
            }
            Rectangle area = getOccupiedAreaBBox();
            applyMargins(area, false);
            lineDrawer.draw(canvas, area);
            if (isTagged) {
                canvas.closeTag();
            }
        }
    }
}
