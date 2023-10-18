package com.itextpdf.kernel.pdf.annot;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;

public class PdfSquareAnnotation extends PdfMarkupAnnotation {
    private static final long serialVersionUID = 5577194318058336359L;

    public PdfSquareAnnotation(Rectangle rect) {
        super(rect);
    }

    protected PdfSquareAnnotation(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfName getSubtype() {
        return PdfName.Square;
    }

    public PdfDictionary getBorderStyle() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1298BS);
    }

    public PdfSquareAnnotation setBorderStyle(PdfDictionary borderStyle) {
        return (PdfSquareAnnotation) put(PdfName.f1298BS, borderStyle);
    }

    public PdfSquareAnnotation setBorderStyle(PdfName style) {
        return setBorderStyle(BorderStyleUtil.setStyle(getBorderStyle(), style));
    }

    public PdfSquareAnnotation setDashPattern(PdfArray dashPattern) {
        return setBorderStyle(BorderStyleUtil.setDashPattern(getBorderStyle(), dashPattern));
    }

    public PdfArray getRectangleDifferences() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1379RD);
    }

    public PdfSquareAnnotation setRectangleDifferences(PdfArray rect) {
        return (PdfSquareAnnotation) put(PdfName.f1379RD, rect);
    }

    public PdfDictionary getBorderEffect() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1295BE);
    }

    public PdfSquareAnnotation setBorderEffect(PdfDictionary borderEffect) {
        return (PdfSquareAnnotation) put(PdfName.f1295BE, borderEffect);
    }

    public Color getInteriorColor() {
        return InteriorColorUtil.parseInteriorColor(((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1340IC));
    }

    public PdfSquareAnnotation setInteriorColor(PdfArray interiorColor) {
        return (PdfSquareAnnotation) put(PdfName.f1340IC, interiorColor);
    }

    public PdfSquareAnnotation setInteriorColor(float[] interiorColor) {
        return setInteriorColor(new PdfArray(interiorColor));
    }
}
