package com.itextpdf.kernel.colors;

import com.itextpdf.kernel.pdf.colorspace.PdfCieBasedCs;

public class Lab extends Color {
    private static final long serialVersionUID = -103738025280259190L;

    public Lab(PdfCieBasedCs.Lab cs) {
        this(cs, new float[cs.getNumberOfComponents()]);
    }

    public Lab(PdfCieBasedCs.Lab cs, float[] value) {
        super(cs, value);
    }

    public Lab(float[] whitePoint, float[] value) {
        super(new PdfCieBasedCs.Lab(whitePoint), value);
    }

    public Lab(float[] whitePoint, float[] blackPoint, float[] range, float[] value) {
        this(new PdfCieBasedCs.Lab(whitePoint, blackPoint, range), value);
    }
}
