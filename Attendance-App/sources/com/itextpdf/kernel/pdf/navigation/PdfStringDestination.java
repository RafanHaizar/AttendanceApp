package com.itextpdf.kernel.pdf.navigation;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfString;
import java.util.Map;

public class PdfStringDestination extends PdfDestination {
    private static final long serialVersionUID = -5949596673571485743L;

    public PdfStringDestination(String string) {
        this(new PdfString(string));
    }

    public PdfStringDestination(PdfString pdfObject) {
        super(pdfObject);
    }

    public PdfObject getDestinationPage(Map<String, PdfObject> names) {
        PdfArray array = (PdfArray) names.get(((PdfString) getPdfObject()).toUnicodeString());
        if (array != null) {
            return array.get(0);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return false;
    }
}
