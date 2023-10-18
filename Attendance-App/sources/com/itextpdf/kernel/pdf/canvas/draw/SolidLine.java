package com.itextpdf.kernel.pdf.canvas.draw;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

public class SolidLine implements ILineDrawer {
    private Color color = ColorConstants.BLACK;
    private float lineWidth = 1.0f;

    public SolidLine() {
    }

    public SolidLine(float lineWidth2) {
        this.lineWidth = lineWidth2;
    }

    public void draw(PdfCanvas canvas, Rectangle drawArea) {
        canvas.saveState().setStrokeColor(this.color).setLineWidth(this.lineWidth).moveTo((double) drawArea.getX(), (double) (drawArea.getY() + (this.lineWidth / 2.0f))).lineTo((double) (drawArea.getX() + drawArea.getWidth()), (double) (drawArea.getY() + (this.lineWidth / 2.0f))).stroke().restoreState();
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
