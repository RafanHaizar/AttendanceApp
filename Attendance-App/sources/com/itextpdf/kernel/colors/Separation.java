package com.itextpdf.kernel.colors;

import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
import com.itextpdf.kernel.pdf.colorspace.PdfSpecialCs;
import com.itextpdf.kernel.pdf.function.PdfFunction;

public class Separation extends Color {
    private static final long serialVersionUID = 5995354549050682283L;

    public Separation(PdfSpecialCs.Separation cs) {
        this(cs, 1.0f);
    }

    public Separation(PdfSpecialCs.Separation cs, float value) {
        super(cs, new float[]{value});
    }

    public Separation(String name, PdfColorSpace alternateCs, PdfFunction tintTransform, float value) {
        this(new PdfSpecialCs.Separation(name, alternateCs, tintTransform), value);
    }
}
