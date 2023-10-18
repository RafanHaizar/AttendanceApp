package com.itextpdf.kernel.pdf.annot;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;

public class PdfPrinterMarkAnnotation extends PdfAnnotation {
    private static final long serialVersionUID = -7709626622860134020L;

    public PdfPrinterMarkAnnotation(Rectangle rect, PdfFormXObject appearanceStream) {
        super(rect);
        setNormalAppearance((PdfDictionary) appearanceStream.getPdfObject());
        setFlags(68);
    }

    protected PdfPrinterMarkAnnotation(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfName getSubtype() {
        return PdfName.PrinterMark;
    }

    public PdfMarkupAnnotation setArbitraryTypeName(PdfName arbitraryTypeName) {
        return (PdfMarkupAnnotation) put(PdfName.f1355MN, arbitraryTypeName);
    }

    public PdfName getArbitraryTypeName() {
        return ((PdfDictionary) getPdfObject()).getAsName(PdfName.f1355MN);
    }
}
