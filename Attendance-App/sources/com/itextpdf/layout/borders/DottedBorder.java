package com.itextpdf.layout.borders;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.borders.Border;

public class DottedBorder extends Border {
    private static final float GAP_MODIFIER = 1.5f;

    public DottedBorder(float width) {
        super(width);
    }

    public DottedBorder(Color color, float width) {
        super(color, width);
    }

    public DottedBorder(Color color, float width, float opacity) {
        super(color, width, opacity);
    }

    public int getType() {
        return 2;
    }

    public void draw(PdfCanvas canvas, float x1, float y1, float x2, float y2, Border.Side defaultSide, float borderWidthBefore, float borderWidthAfter) {
        float adjustedGap;
        PdfCanvas pdfCanvas = canvas;
        float dx = x2 - x1;
        float dy = y2 - y1;
        float adjustedGap2 = super.getDotsGap(Math.sqrt((double) ((dx * dx) + (dy * dy))), this.width + (this.width * GAP_MODIFIER));
        if (adjustedGap2 > this.width) {
            adjustedGap = adjustedGap2 - this.width;
        } else {
            adjustedGap = adjustedGap2;
        }
        float[] startingPoints = getStartingPointsForBorderSide(x1, y1, x2, y2, defaultSide);
        float x12 = startingPoints[0];
        float y12 = startingPoints[1];
        float x22 = startingPoints[2];
        float y22 = startingPoints[3];
        canvas.saveState().setLineWidth(this.width).setStrokeColor(this.transparentColor.getColor());
        this.transparentColor.applyStrokeTransparency(pdfCanvas);
        float[] fArr = startingPoints;
        float f = x12;
        float f2 = y12;
        pdfCanvas.setLineDash(this.width, adjustedGap, this.width + (adjustedGap / 2.0f)).moveTo((double) x12, (double) y12).lineTo((double) x22, (double) y22).stroke().restoreState();
    }

    public void draw(PdfCanvas canvas, float x1, float y1, float x2, float y2, float horizontalRadius1, float verticalRadius1, float horizontalRadius2, float verticalRadius2, Border.Side defaultSide, float borderWidthBefore, float borderWidthAfter) {
        float adjustedGap;
        PdfCanvas pdfCanvas = canvas;
        float f = x1;
        float f2 = y1;
        float initialGap = this.width * GAP_MODIFIER;
        float dx = x2 - f;
        float dy = y2 - f2;
        double borderLength = Math.sqrt((double) ((dx * dx) + (dy * dy)));
        float adjustedGap2 = super.getDotsGap(borderLength, initialGap);
        if (adjustedGap2 > this.width) {
            adjustedGap = adjustedGap2 - this.width;
        } else {
            adjustedGap = adjustedGap2;
        }
        canvas.saveState().setLineWidth(this.width).setStrokeColor(this.transparentColor.getColor());
        this.transparentColor.applyStrokeTransparency(pdfCanvas);
        pdfCanvas.setLineDash(this.width, adjustedGap, this.width + (adjustedGap / 2.0f));
        double d = borderLength;
        drawDiscontinuousBorders(canvas, new Rectangle(f, f2, x2 - f, y2 - f2), new float[]{horizontalRadius1, horizontalRadius2}, new float[]{verticalRadius1, verticalRadius2}, defaultSide, borderWidthBefore, borderWidthAfter);
    }

    public void drawCellBorder(PdfCanvas canvas, float x1, float y1, float x2, float y2, Border.Side defaultSide) {
        PdfCanvas pdfCanvas = canvas;
        float f = x1;
        float f2 = y1;
        float f3 = x2;
        float f4 = y2;
        float dx = f3 - f;
        float dy = f4 - f2;
        float adjustedGap = super.getDotsGap(Math.sqrt((double) ((dx * dx) + (dy * dy))), this.width + (this.width * GAP_MODIFIER));
        if (adjustedGap > this.width) {
            adjustedGap -= this.width;
        }
        canvas.saveState().setLineWidth(this.width).setStrokeColor(this.transparentColor.getColor());
        this.transparentColor.applyStrokeTransparency(pdfCanvas);
        pdfCanvas.setLineDash(this.width, adjustedGap, this.width + (adjustedGap / 2.0f)).moveTo((double) f, (double) f2).lineTo((double) f3, (double) f4).stroke().restoreState();
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public float getDotsGap(double distance, float initialGap) {
        return super.getDotsGap(distance, initialGap);
    }
}
