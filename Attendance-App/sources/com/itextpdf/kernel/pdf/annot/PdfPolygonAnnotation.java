package com.itextpdf.kernel.pdf.annot;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;

class PdfPolygonAnnotation extends PdfPolyGeomAnnotation {
    PdfPolygonAnnotation(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    PdfPolygonAnnotation(Rectangle rect, float[] vertices) {
        super(rect, vertices);
    }

    public PdfName getSubtype() {
        return PdfName.Polygon;
    }
}
