package com.itextpdf.kernel.pdf.action;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import com.itextpdf.kernel.pdf.PdfString;

public class PdfWin extends PdfObjectWrapper<PdfDictionary> {
    private static final long serialVersionUID = -3057526285278565800L;

    public PdfWin(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfWin(PdfString f) {
        this(new PdfDictionary());
        ((PdfDictionary) getPdfObject()).put(PdfName.f1324F, f);
    }

    public PdfWin(PdfString f, PdfString d, PdfString o, PdfString p) {
        this(new PdfDictionary());
        ((PdfDictionary) getPdfObject()).put(PdfName.f1324F, f);
        ((PdfDictionary) getPdfObject()).put(PdfName.f1312D, d);
        ((PdfDictionary) getPdfObject()).put(PdfName.f1361O, o);
        ((PdfDictionary) getPdfObject()).put(PdfName.f1367P, p);
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return false;
    }
}
