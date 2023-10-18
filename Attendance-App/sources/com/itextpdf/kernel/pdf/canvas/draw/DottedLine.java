package com.itextpdf.kernel.pdf.canvas.draw;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

public class DottedLine implements ILineDrawer {
    private Color color = ColorConstants.BLACK;
    protected float gap = 4.0f;
    private float lineWidth = 1.0f;

    public DottedLine() {
    }

    public DottedLine(float lineWidth2, float gap2) {
        this.lineWidth = lineWidth2;
        this.gap = gap2;
    }

    public DottedLine(float lineWidth2) {
        this.lineWidth = lineWidth2;
    }

    public void draw(PdfCanvas canvas, Rectangle drawArea) {
        PdfCanvas strokeColor = canvas.saveState().setLineWidth(this.lineWidth).setStrokeColor(this.color);
        float f = this.gap;
        strokeColor.setLineDash(0.0f, f, f / 2.0f).setLineCapStyle(1).moveTo((double) drawArea.getX(), (double) (drawArea.getY() + (this.lineWidth / 2.0f))).lineTo((double) (drawArea.getX() + drawArea.getWidth()), (double) (drawArea.getY() + (this.lineWidth / 2.0f))).stroke().restoreState();
    }

    public float getGap() {
        return this.gap;
    }

    public void setGap(float gap2) {
        this.gap = gap2;
    }

    public float getLineWidth() {
        return this.lineWidth;
    }

    public void setLineWidth(float lineWidth2) {
        this.lineWidth = lineWidth2;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color2) {
        this.color = color2;
    }
}
