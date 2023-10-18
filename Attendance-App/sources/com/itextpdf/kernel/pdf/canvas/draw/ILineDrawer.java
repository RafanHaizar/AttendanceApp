package com.itextpdf.kernel.pdf.canvas.draw;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

public interface ILineDrawer {
    void draw(PdfCanvas pdfCanvas, Rectangle rectangle);

    Color getColor();

    float getLineWidth();

    void setColor(Color color);

    void setLineWidth(float f);
}
