package com.itextpdf.kernel.pdf.annot;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;

public class PdfFixedPrint extends PdfObjectWrapper<PdfDictionary> {
    private static final long serialVersionUID = 4253232541458560135L;

    public PdfFixedPrint() {
        this(new PdfDictionary());
    }

    public PdfFixedPrint(PdfDictionary pdfObject) {
        super(pdfObject);
        pdfObject.put(PdfName.Type, PdfName.FixedPrint);
    }

    public PdfFixedPrint setMatrix(PdfArray matrix) {
        ((PdfDictionary) getPdfObject()).put(PdfName.Matrix, matrix);
        return this;
    }

    public PdfFixedPrint setMatrix(float[] matrix) {
        ((PdfDictionary) getPdfObject()).put(PdfName.Matrix, new PdfArray(matrix));
        return this;
    }

    public PdfFixedPrint setHorizontalTranslation(float horizontal) {
        ((PdfDictionary) getPdfObject()).put(PdfName.f1331H, new PdfNumber((double) horizontal));
        return this;
    }

    public PdfFixedPrint setVerticalTranslation(float vertical) {
        ((PdfDictionary) getPdfObject()).put(PdfName.f1406V, new PdfNumber((double) vertical));
        return this;
    }

    public PdfArray getMatrix() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Matrix);
    }

    public PdfNumber getHorizontalTranslation() {
        return ((PdfDictionary) getPdfObject()).getAsNumber(PdfName.f1331H);
    }

    public PdfNumber getVerticalTranslation() {
        return ((PdfDictionary) getPdfObject()).getAsNumber(PdfName.f1406V);
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return true;
    }
}
