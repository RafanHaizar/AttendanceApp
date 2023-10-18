package com.itextpdf.kernel.pdf.collection;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;

public class PdfCollectionSchema extends PdfObjectWrapper<PdfDictionary> {
    private static final long serialVersionUID = -4388183665435879535L;

    public PdfCollectionSchema(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfCollectionSchema() {
        this(new PdfDictionary());
    }

    public PdfCollectionSchema addField(String name, PdfCollectionField field) {
        ((PdfDictionary) getPdfObject()).put(new PdfName(name), field.getPdfObject());
        return this;
    }

    public PdfCollectionField getField(String name) {
        return new PdfCollectionField(((PdfDictionary) getPdfObject()).getAsDictionary(new PdfName(name)));
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return false;
    }
}
