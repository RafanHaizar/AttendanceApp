package com.itextpdf.kernel.pdf.annot;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;

class PdfPolylineAnnotation extends PdfPolyGeomAnnotation {
    PdfPolylineAnnotation(Rectangle rect, float[] vertices) {
        super(rect, vertices);
    }

    PdfPolylineAnnotation(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfName getSubtype() {
        return PdfName.PolyLine;
    }
}
