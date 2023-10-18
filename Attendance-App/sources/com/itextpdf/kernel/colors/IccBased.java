package com.itextpdf.kernel.colors;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.colorspace.PdfCieBasedCs;
import java.io.InputStream;

public class IccBased extends Color {
    private static final long serialVersionUID = -2204252409856288615L;

    public IccBased(PdfCieBasedCs.IccBased cs) {
        this(cs, new float[cs.getNumberOfComponents()]);
    }

    public IccBased(PdfCieBasedCs.IccBased cs, float[] value) {
        super(cs, value);
    }

    public IccBased(InputStream iccStream) {
        this(new PdfCieBasedCs.IccBased(iccStream), (float[]) null);
        this.colorValue = new float[getNumberOfComponents()];
        for (int i = 0; i < getNumberOfComponents(); i++) {
            this.colorValue[i] = 0.0f;
        }
    }

    public IccBased(InputStream iccStream, float[] value) {
        this(new PdfCieBasedCs.IccBased(iccStream), value);
    }

    public IccBased(InputStream iccStream, float[] range, float[] value) {
        this(new PdfCieBasedCs.IccBased(iccStream, range), value);
        if (getNumberOfComponents() * 2 != range.length) {
            throw new PdfException(PdfException.InvalidRangeArray, (Object) this);
        }
    }
}
