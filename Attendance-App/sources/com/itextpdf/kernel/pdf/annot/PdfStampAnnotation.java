package com.itextpdf.kernel.pdf.annot;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;

public class PdfStampAnnotation extends PdfMarkupAnnotation {
    private static final long serialVersionUID = -8834372239248292352L;

    public PdfStampAnnotation(Rectangle rect) {
        super(rect);
    }

    protected PdfStampAnnotation(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfName getSubtype() {
        return PdfName.Stamp;
    }

    public PdfStampAnnotation setStampName(PdfName name) {
        return (PdfStampAnnotation) put(PdfName.Name, name);
    }

    public PdfName getStampName() {
        return ((PdfDictionary) getPdfObject()).getAsName(PdfName.Name);
    }

    public PdfName getIconName() {
        return ((PdfDictionary) getPdfObject()).getAsName(PdfName.Name);
    }

    public PdfStampAnnotation setIconName(PdfName name) {
        return (PdfStampAnnotation) put(PdfName.Name, name);
    }
}
