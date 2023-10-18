package com.itextpdf.kernel.colors;

import com.itextpdf.kernel.pdf.colorspace.PdfCieBasedCs;

public class CalGray extends Color {
    private static final long serialVersionUID = 2654434937251198951L;

    public CalGray(PdfCieBasedCs.CalGray cs) {
        this(cs, 0.0f);
    }

    public CalGray(PdfCieBasedCs.CalGray cs, float value) {
        super(cs, new float[]{value});
    }

    public CalGray(float[] whitePoint, float value) {
        super(new PdfCieBasedCs.CalGray(whitePoint), new float[]{value});
    }

    public CalGray(float[] whitePoint, float[] blackPoint, float gamma, float value) {
        this(new PdfCieBasedCs.CalGray(whitePoint, blackPoint, gamma), value);
    }
}
