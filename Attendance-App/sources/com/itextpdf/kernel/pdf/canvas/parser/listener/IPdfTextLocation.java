package com.itextpdf.kernel.pdf.canvas.parser.listener;

import com.itextpdf.kernel.geom.Rectangle;

public interface IPdfTextLocation {
    int getPageNumber();

    Rectangle getRectangle();

    String getText();
}
