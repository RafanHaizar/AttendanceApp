package com.itextpdf.kernel.pdf.annot;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfString;

public class PdfCaretAnnotation extends PdfMarkupAnnotation {
    private static final long serialVersionUID = 1542932123958535397L;

    public PdfCaretAnnotation(Rectangle rect) {
        super(rect);
    }

    protected PdfCaretAnnotation(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfName getSubtype() {
        return PdfName.Caret;
    }

    public PdfCaretAnnotation setSymbol(PdfString symbol) {
        return (PdfCaretAnnotation) put(PdfName.f1390Sy, symbol);
    }

    public PdfString getSymbol() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.f1390Sy);
    }

    public PdfArray getRectangleDifferences() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1379RD);
    }

    public PdfCaretAnnotation setRectangleDifferences(PdfArray rect) {
        return (PdfCaretAnnotation) put(PdfName.f1379RD, rect);
    }
}
