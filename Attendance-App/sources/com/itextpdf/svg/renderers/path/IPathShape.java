package com.itextpdf.svg.renderers.path;

import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

public interface IPathShape {
    void draw(PdfCanvas pdfCanvas);

    Point getEndingPoint();

    boolean isRelative();

    void setCoordinates(String[] strArr, Point point);
}
