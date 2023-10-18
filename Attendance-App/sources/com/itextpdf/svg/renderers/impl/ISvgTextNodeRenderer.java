package com.itextpdf.svg.renderers.impl;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;

public interface ISvgTextNodeRenderer extends ISvgNodeRenderer {
    boolean containsAbsolutePositionChange();

    boolean containsRelativeMove();

    float[][] getAbsolutePositionChanges();

    float[] getRelativeTranslation();

    float getTextContentLength(float f, PdfFont pdfFont);
}
