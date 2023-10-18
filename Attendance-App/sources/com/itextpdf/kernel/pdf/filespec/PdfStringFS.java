package com.itextpdf.kernel.pdf.filespec;

import com.itextpdf.kernel.pdf.PdfString;

public class PdfStringFS extends PdfFileSpec {
    private static final long serialVersionUID = 3440302276954369264L;

    public PdfStringFS(String string) {
        super(new PdfString(string));
    }

    public PdfStringFS(PdfString pdfObject) {
        super(pdfObject);
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return false;
    }
}
