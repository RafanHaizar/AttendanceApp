package com.itextpdf.signatures;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;

public class PdfSignatureApp extends PdfObjectWrapper<PdfDictionary> {
    public PdfSignatureApp() {
        super(new PdfDictionary());
    }

    public PdfSignatureApp(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public void setSignatureCreator(String name) {
        ((PdfDictionary) getPdfObject()).put(PdfName.Name, new PdfName(name));
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return false;
    }
}
