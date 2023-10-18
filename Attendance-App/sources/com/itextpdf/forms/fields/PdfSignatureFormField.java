package com.itextpdf.forms.fields;

import com.itextpdf.forms.PdfSigFieldLock;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.annot.PdfWidgetAnnotation;

public class PdfSignatureFormField extends PdfFormField {
    protected PdfSignatureFormField(PdfDocument pdfDocument) {
        super(pdfDocument);
    }

    protected PdfSignatureFormField(PdfWidgetAnnotation widget, PdfDocument pdfDocument) {
        super(widget, pdfDocument);
    }

    protected PdfSignatureFormField(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfName getFormType() {
        return PdfName.Sig;
    }

    public PdfSignatureFormField setValue(PdfObject value) {
        return (PdfSignatureFormField) put(PdfName.f1406V, value);
    }

    public PdfSigFieldLock getSigFieldLockDictionary() {
        PdfDictionary sigLockDict = (PdfDictionary) ((PdfDictionary) getPdfObject()).get(PdfName.Lock);
        if (sigLockDict == null) {
            return null;
        }
        return new PdfSigFieldLock(sigLockDict);
    }
}
