package com.itextpdf.kernel.pdf.canvas.draw;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

public class DashedLine implements ILineDrawer {
    private Color color = ColorConstants.BLACK;
    private float lineWidth = 1.0f;

    public DashedLine() {
    }

    public DashedLine(float lineWidth2) {
        this.lineWidth = lineWidth2;
    }

    public void draw(PdfCanvas canvas, Rectangle drawArea) {
        canvas.saveState().setLineWidth(this.lineWidth).setStrokeColor(this.color).setLineDash(2.0f, 2.0f).moveTo((double) drawArea.getX(), (double) (drawArea.getY() + (this.lineWidth / 2.0f))).lineTo((double) (drawArea.getX() + drawArea.getWidth()), (double) (drawArea.getY() + (this.lineWidth / 2.0f))).stroke().restoreState();
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
