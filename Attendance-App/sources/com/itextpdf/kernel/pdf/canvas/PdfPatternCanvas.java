package com.itextpdf.kernel.pdf.canvas;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfResources;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
import com.itextpdf.kernel.pdf.colorspace.PdfPattern;

public class PdfPatternCanvas extends PdfCanvas {
    private static final long serialVersionUID = -8325687042148621178L;
    private final PdfPattern.Tiling tilingPattern;

    public PdfPatternCanvas(PdfStream contentStream, PdfResources resources, PdfDocument document) {
        super(contentStream, resources, document);
        this.tilingPattern = new PdfPattern.Tiling(contentStream);
    }

    public PdfPatternCanvas(PdfPattern.Tiling pattern, PdfDocument document) {
        super((PdfStream) pattern.getPdfObject(), pattern.getResources(), document);
        this.tilingPattern = pattern;
    }

    public PdfCanvas setColor(PdfColorSpace colorSpace, float[] colorValue, PdfPattern pattern, boolean fill) {
        checkNoColor();
        return super.setColor(colorSpace, colorValue, pattern, fill);
    }

    private void checkNoColor() {
        if (!this.tilingPattern.isColored()) {
            throw new PdfException(PdfException.f1241xa6840a7b);
        }
    }
}
