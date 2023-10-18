package com.itextpdf.layout.borders;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.borders.Border;

public class RoundDotsBorder extends Border {
    private static final float GAP_MODIFIER = 2.5f;

    public RoundDotsBorder(float width) {
        super(width);
    }

    public RoundDotsBorder(Color color, float width) {
        super(color, width);
    }

    public RoundDotsBorder(Color color, float width, float opacity) {
        super(color, width, opacity);
    }

    public int getType() {
        return 4;
    }

    public void draw(PdfCanvas canvas, float x1, float y1, float x2, float y2, Border.Side defaultSide, float borderWidthBefore, float borderWidthAfter) {
        PdfCanvas pdfCanvas = canvas;
        float dx = x2 - x1;
        float dy = y2 - y1;
        float adjustedGap = super.getDotsGap(Math.sqrt((double) ((dx * dx) + (dy * dy))), this.width * GAP_MODIFIER);
        float[] startingPoints = getStartingPointsForBorderSide(x1, y1, x2, y2, defaultSide);
        float x12 = startingPoints[0];
        float y12 = startingPoints[1];
        float x22 = startingPoints[2];
        float y22 = startingPoints[3];
        canvas.saveState().setStrokeColor(this.transparentColor.getColor()).setLineWidth(this.width).setLineCapStyle(1);
        this.transparentColor.applyStrokeTransparency(pdfCanvas);
        float[] fArr = startingPoints;
        float f = x12;
        pdfCanvas.setLineDash(0.0f, adjustedGap, adjustedGap / 2.0f).moveTo((double) x12, (double) y12).lineTo((double) x22, (double) y22).stroke().restoreState();
    }

    public void drawCellBorder(PdfCanvas canvas, float x1, float y1, float x2, float y2, Border.Side defaultSide) {
        float x22;
        PdfCanvas pdfCanvas = canvas;
        float f = x1;
        float f2 = y1;
        float f3 = y2;
        float dx = x2 - f;
        float dy = f3 - f2;
        float adjustedGap = super.getDotsGap(Math.sqrt((double) ((dx * dx) + (dy * dy))), this.width * GAP_MODIFIER);
        boolean isHorizontal = false;
        if (Math.abs(f3 - f2) < 5.0E-4f) {
            isHorizontal = true;
        }
        if (isHorizontal) {
            x22 = x2 - this.width;
        } else {
            x22 = x2;
        }
        canvas.saveState();
        pdfCanvas.setStrokeColor(this.transparentColor.getColor());
        this.transparentColor.applyStrokeTransparency(pdfCanvas);
        pdfCanvas.setLineWidth(this.width);
        pdfCanvas.setLineCapStyle(1);
        pdfCanvas.setLineDash(0.0f, adjustedGap, adjustedGap / 2.0f).moveTo((double) f, (double) f2).lineTo((double) x22, (double) f3).stroke();
        canvas.restoreState();
    }

    public void draw(PdfCanvas canvas, float x1, float y1, float x2, float y2, float horizontalRadius1, float verticalRadius1, float horizontalRadius2, float verticalRadius2, Border.Side defaultSide, float borderWidthBefore, float borderWidthAfter) {
        PdfCanvas pdfCanvas = canvas;
        float f = x1;
        float f2 = y1;
        float initialGap = this.width * GAP_MODIFIER;
        float dx = x2 - f;
        float dy = y2 - f2;
        double borderLength = Math.sqrt((double) ((dx * dx) + (dy * dy)));
        float adjustedGap = super.getDotsGap(borderLength, initialGap);
        canvas.saveState().setStrokeColor(this.transparentColor.getColor());
        this.transparentColor.applyStrokeTransparency(pdfCanvas);
        pdfCanvas.setLineWidth(this.width).setLineCapStyle(1).setLineDash(0.0f, adjustedGap, adjustedGap / 2.0f);
        double d = borderLength;
        drawDiscontinuousBorders(canvas, new Rectangle(f, f2, x2 - f, y2 - f2), new float[]{horizontalRadius1, horizontalRadius2}, new float[]{verticalRadius1, verticalRadius2}, defaultSide, borderWidthBefore, borderWidthAfter);
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public float getDotsGap(double distance, float initialGap) {
        return super.getDotsGap(distance, initialGap);
    }
}
