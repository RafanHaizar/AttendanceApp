package com.itextpdf.kernel.pdf.annot;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;

public class PdfWatermarkAnnotation extends PdfAnnotation {
    private static final long serialVersionUID = -4490286782196827176L;

    public PdfWatermarkAnnotation(Rectangle rect) {
        super(rect);
    }

    protected PdfWatermarkAnnotation(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfName getSubtype() {
        return PdfName.Watermark;
    }

    public PdfWatermarkAnnotation setFixedPrint(PdfFixedPrint fixedPrint) {
        return (PdfWatermarkAnnotation) put(PdfName.FixedPrint, fixedPrint.getPdfObject());
    }

    public PdfDictionary getFixedPrint() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.FixedPrint);
    }
}
