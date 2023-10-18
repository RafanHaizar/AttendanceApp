package com.itextpdf.kernel.pdf.annot;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;

public class PdfInkAnnotation extends PdfMarkupAnnotation {
    private static final long serialVersionUID = 9110158515957708155L;

    public PdfInkAnnotation(Rectangle rect) {
        super(rect);
    }

    public PdfInkAnnotation(Rectangle rect, PdfArray inkList) {
        this(rect);
        put(PdfName.InkList, inkList);
    }

    protected PdfInkAnnotation(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfName getSubtype() {
        return PdfName.Ink;
    }

    public PdfDictionary getBorderStyle() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1298BS);
    }

    public PdfInkAnnotation setBorderStyle(PdfDictionary borderStyle) {
        return (PdfInkAnnotation) put(PdfName.f1298BS, borderStyle);
    }

    public PdfInkAnnotation setBorderStyle(PdfName style) {
        return setBorderStyle(BorderStyleUtil.setStyle(getBorderStyle(), style));
    }

    public PdfInkAnnotation setDashPattern(PdfArray dashPattern) {
        return setBorderStyle(BorderStyleUtil.setDashPattern(getBorderStyle(), dashPattern));
    }
}
