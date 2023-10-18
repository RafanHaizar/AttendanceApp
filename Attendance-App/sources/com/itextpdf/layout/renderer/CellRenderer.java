package com.itextpdf.layout.renderer;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.Matrix;
import com.itextpdf.kernel.geom.NoninvertibleTransformException;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.property.BorderCollapsePropertyValue;
import com.itextpdf.layout.property.UnitValue;

public class CellRenderer extends BlockRenderer {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    public CellRenderer(Cell modelElement) {
        super(modelElement);
        if (modelElement != null) {
            setProperty(60, Integer.valueOf(modelElement.getRowspan()));
            setProperty(16, Integer.valueOf(modelElement.getColspan()));
            return;
        }
        throw new AssertionError();
    }

    public IPropertyContainer getModelElement() {
        return super.getModelElement();
    }

    /* access modifiers changed from: protected */
    public Float retrieveWidth(float parentBoxWidth) {
        return null;
    }

    /* access modifiers changed from: protected */
    public AbstractRenderer createSplitRenderer(int layoutResult) {
        CellRenderer splitRenderer = (CellRenderer) getNextRenderer();
        splitRenderer.parent = this.parent;
        splitRenderer.modelElement = this.modelElement;
        splitRenderer.occupiedArea = this.occupiedArea;
        splitRenderer.isLastRendererForModelElement = false;
        splitRenderer.addAllProperties(getOwnProperties());
        return splitRenderer;
    }

    /* access modifiers changed from: protected */
    public AbstractRenderer createOverflowRenderer(int layoutResult) {
        CellRenderer overflowRenderer = (CellRenderer) getNextRenderer();
        overflowRenderer.parent = this.parent;
        overflowRenderer.modelElement = this.modelElement;
        overflowRenderer.addAllProperties(getOwnProperties());
        return overflowRenderer;
    }

    public void drawBackground(DrawContext drawContext) {
        boolean avoidRotation;
        CellRenderer cellRenderer;
        PdfCanvas canvas = drawContext.getCanvas();
        Matrix ctm = canvas.getGraphicsState().getCtm();
        Float angle = getPropertyAsFloat(55);
        boolean avoidRotation2 = angle != null && hasProperty(6);
        boolean restoreRotation = hasOwnProperty(55);
        if (avoidRotation2) {
            avoidRotation = avoidRotation2;
            try {
                AffineTransform transform = new AffineTransform((double) ctm.get(0), (double) ctm.get(1), (double) ctm.get(3), (double) ctm.get(4), (double) ctm.get(6), (double) ctm.get(7)).createInverse();
                transform.concatenate(new AffineTransform());
                canvas.concatMatrix(transform);
                cellRenderer = this;
                cellRenderer.setProperty(55, (Object) null);
            } catch (NoninvertibleTransformException e) {
                throw new PdfException(PdfException.NoninvertibleMatrixCannotBeProcessed, (Throwable) e);
            }
        } else {
            cellRenderer = this;
            avoidRotation = avoidRotation2;
        }
        super.drawBackground(drawContext);
        if (avoidRotation) {
            if (restoreRotation) {
                cellRenderer.setProperty(55, angle);
            } else {
                cellRenderer.deleteOwnProperty(55);
            }
            Float f = angle;
            boolean z = restoreRotation;
            canvas.concatMatrix(new AffineTransform((double) ctm.get(0), (double) ctm.get(1), (double) ctm.get(3), (double) ctm.get(4), (double) ctm.get(6), (double) ctm.get(7)));
            return;
        }
        Float f2 = angle;
        boolean z2 = restoreRotation;
    }

    public void drawBorder(DrawContext drawContext) {
        if (BorderCollapsePropertyValue.SEPARATE.equals(this.parent.getProperty(114))) {
            super.drawBorder(drawContext);
        }
    }

    /* access modifiers changed from: protected */
    public Rectangle applyBorderBox(Rectangle rect, Border[] borders, boolean reverse) {
        if (BorderCollapsePropertyValue.SEPARATE.equals(this.parent.getProperty(114))) {
            super.applyBorderBox(rect, borders, reverse);
        }
        return rect;
    }

    /* access modifiers changed from: protected */
    public Rectangle applyMargins(Rectangle rect, UnitValue[] margins, boolean reverse) {
        if (BorderCollapsePropertyValue.SEPARATE.equals(this.parent.getProperty(114))) {
            applySpacings(rect, reverse);
        }
        return rect;
    }

    /* access modifiers changed from: protected */
    public Rectangle applySpacings(Rectangle rect, boolean reverse) {
        if (BorderCollapsePropertyValue.SEPARATE.equals(this.parent.getProperty(114))) {
            Float verticalBorderSpacing = (Float) this.parent.getProperty(116);
            Float horizontalBorderSpacing = (Float) this.parent.getProperty(115);
            float[] cellSpacings = new float[4];
            for (int i = 0; i < cellSpacings.length; i++) {
                float f = 0.0f;
                if (i % 2 == 0) {
                    if (verticalBorderSpacing != null) {
                        f = verticalBorderSpacing.floatValue();
                    }
                } else if (horizontalBorderSpacing != null) {
                    f = horizontalBorderSpacing.floatValue();
                }
                cellSpacings[i] = f;
            }
            applySpacings(rect, cellSpacings, reverse);
        }
        return rect;
    }

    /* access modifiers changed from: protected */
    public Rectangle applySpacings(Rectangle rect, float[] spacings, boolean reverse) {
        if (BorderCollapsePropertyValue.SEPARATE.equals(this.parent.getProperty(114))) {
            rect.applyMargins(spacings[0] / 2.0f, spacings[1] / 2.0f, spacings[2] / 2.0f, spacings[3] / 2.0f, reverse);
        }
        return rect;
    }

    public IRenderer getNextRenderer() {
        return new CellRenderer((Cell) getModelElement());
    }
}
