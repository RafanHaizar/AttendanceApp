package com.itextpdf.svg.renderers.path.impl;

import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

public class ClosePath extends LineTo {
    static final int ARGUMENT_SIZE = 0;

    public ClosePath() {
        this(false);
    }

    public ClosePath(boolean relative) {
        super(relative);
    }

    public void draw(PdfCanvas canvas) {
        canvas.closePath();
    }
}
