package com.itextpdf.kernel.pdf.annot;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfBoolean;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;

public class PdfPopupAnnotation extends PdfAnnotation {
    private static final long serialVersionUID = -8892617787951569855L;
    protected PdfAnnotation parent;

    public PdfPopupAnnotation(Rectangle rect) {
        super(rect);
    }

    protected PdfPopupAnnotation(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfName getSubtype() {
        return PdfName.Popup;
    }

    public PdfDictionary getParentObject() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.Parent);
    }

    public PdfAnnotation getParent() {
        if (this.parent == null) {
            this.parent = makeAnnotation(getParentObject());
        }
        return this.parent;
    }

    public PdfPopupAnnotation setParent(PdfAnnotation parent2) {
        this.parent = parent2;
        return (PdfPopupAnnotation) put(PdfName.Parent, parent2.getPdfObject());
    }

    public boolean getOpen() {
        return PdfBoolean.TRUE.equals(((PdfDictionary) getPdfObject()).getAsBoolean(PdfName.Open));
    }

    public PdfPopupAnnotation setOpen(boolean open) {
        return (PdfPopupAnnotation) put(PdfName.Open, PdfBoolean.valueOf(open));
    }
}
