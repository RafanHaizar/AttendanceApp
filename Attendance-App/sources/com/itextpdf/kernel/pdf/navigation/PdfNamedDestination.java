package com.itextpdf.kernel.pdf.navigation;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import java.util.Map;

public class PdfNamedDestination extends PdfDestination {
    private static final long serialVersionUID = 5285810255133676086L;

    public PdfNamedDestination(String name) {
        this(new PdfName(name));
    }

    public PdfNamedDestination(PdfName pdfObject) {
        super(pdfObject);
    }

    public PdfObject getDestinationPage(Map<String, PdfObject> names) {
        PdfArray array = (PdfArray) names.get(((PdfName) getPdfObject()).getValue());
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
