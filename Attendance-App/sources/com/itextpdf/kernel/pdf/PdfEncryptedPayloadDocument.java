package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;

public class PdfEncryptedPayloadDocument extends PdfObjectWrapper<PdfStream> {
    private PdfFileSpec fileSpec;
    private String name;

    public PdfEncryptedPayloadDocument(PdfStream pdfObject, PdfFileSpec fileSpec2, String name2) {
        super(pdfObject);
        this.fileSpec = fileSpec2;
        this.name = name2;
    }

    public byte[] getDocumentBytes() {
        return ((PdfStream) getPdfObject()).getBytes();
    }

    public PdfFileSpec getFileSpec() {
        return this.fileSpec;
    }

    public String getName() {
        return this.name;
    }

    public PdfEncryptedPayload getEncryptedPayload() {
        return PdfEncryptedPayload.extractFrom(this.fileSpec);
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return true;
    }
}
