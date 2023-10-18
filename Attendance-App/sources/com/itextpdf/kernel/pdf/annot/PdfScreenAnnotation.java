package com.itextpdf.kernel.pdf.annot;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.action.PdfAction;

public class PdfScreenAnnotation extends PdfAnnotation {
    private static final long serialVersionUID = 1334399136151450493L;

    public PdfScreenAnnotation(Rectangle rect) {
        super(rect);
    }

    protected PdfScreenAnnotation(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfName getSubtype() {
        return PdfName.Screen;
    }

    public PdfDictionary getAction() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1287A);
    }

    public PdfScreenAnnotation setAction(PdfAction action) {
        return (PdfScreenAnnotation) put(PdfName.f1287A, action.getPdfObject());
    }

    public PdfDictionary getAdditionalAction() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1288AA);
    }

    public PdfScreenAnnotation setAdditionalAction(PdfName key, PdfAction action) {
        PdfAction.setAdditionalAction(this, key, action);
        return this;
    }

    public PdfDictionary getAppearanceCharacteristics() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1353MK);
    }

    public PdfScreenAnnotation setAppearanceCharacteristics(PdfDictionary characteristics) {
        return (PdfScreenAnnotation) put(PdfName.f1353MK, characteristics);
    }
}
