package com.itextpdf.kernel.pdf.navigation;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import com.itextpdf.kernel.pdf.PdfString;
import java.util.Map;

public abstract class PdfDestination extends PdfObjectWrapper<PdfObject> {
    private static final long serialVersionUID = 8102903000978704308L;

    public abstract PdfObject getDestinationPage(Map<String, PdfObject> map);

    protected PdfDestination(PdfObject pdfObject) {
        super(pdfObject);
    }

    public static PdfDestination makeDestination(PdfObject pdfObject) {
        if (pdfObject.getType() == 10) {
            return new PdfStringDestination((PdfString) pdfObject);
        }
        if (pdfObject.getType() == 6) {
            return new PdfNamedDestination((PdfName) pdfObject);
        }
        if (pdfObject.getType() == 1) {
            PdfArray destArray = (PdfArray) pdfObject;
            if (destArray.size() != 0) {
                PdfObject firstObj = destArray.get(0);
                if (firstObj.isNumber()) {
                    return new PdfExplicitRemoteGoToDestination(destArray);
                }
                if (!firstObj.isDictionary() || !PdfName.Page.equals(((PdfDictionary) firstObj).getAsName(PdfName.Type))) {
                    return new PdfStructureDestination(destArray);
                }
                return new PdfExplicitDestination(destArray);
            }
            throw new IllegalArgumentException();
        }
        throw new UnsupportedOperationException();
    }
}
