package com.itextpdf.kernel.pdf.annot;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;

@Deprecated
public class PdfRichMediaAnnotation extends PdfAnnotation {
    private static final long serialVersionUID = 5368329326723025646L;

    public PdfRichMediaAnnotation(Rectangle rect) {
        super(rect);
    }

    public PdfRichMediaAnnotation(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfName getSubtype() {
        return PdfName.RichMedia;
    }
}
