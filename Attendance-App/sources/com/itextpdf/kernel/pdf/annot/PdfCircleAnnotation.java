package com.itextpdf.kernel.pdf.annot;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;

public class PdfCircleAnnotation extends PdfMarkupAnnotation {
    private static final long serialVersionUID = -4123774794612333746L;

    public PdfCircleAnnotation(Rectangle rect) {
        super(rect);
    }

    protected PdfCircleAnnotation(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfName getSubtype() {
        return PdfName.Circle;
    }

    public PdfDictionary getBorderStyle() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1298BS);
    }

    public PdfCircleAnnotation setBorderStyle(PdfDictionary borderStyle) {
        return (PdfCircleAnnotation) put(PdfName.f1298BS, borderStyle);
    }

    public PdfCircleAnnotation setBorderStyle(PdfName style) {
        return setBorderStyle(BorderStyleUtil.setStyle(getBorderStyle(), style));
    }

    public PdfCircleAnnotation setDashPattern(PdfArray dashPattern) {
        return setBorderStyle(BorderStyleUtil.setDashPattern(getBorderStyle(), dashPattern));
    }

    public PdfArray getRectangleDifferences() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1379RD);
    }

    public PdfCircleAnnotation setRectangleDifferences(PdfArray rect) {
        return (PdfCircleAnnotation) put(PdfName.f1379RD, rect);
    }

    public PdfDictionary getBorderEffect() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1295BE);
    }

    public PdfCircleAnnotation setBorderEffect(PdfDictionary borderEffect) {
        return (PdfCircleAnnotation) put(PdfName.f1295BE, borderEffect);
    }

    public Color getInteriorColor() {
        return InteriorColorUtil.parseInteriorColor(((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1340IC));
    }

    public PdfCircleAnnotation setInteriorColor(PdfArray interiorColor) {
        return (PdfCircleAnnotation) put(PdfName.f1340IC, interiorColor);
    }

    public PdfCircleAnnotation setInteriorColor(float[] interiorColor) {
        return setInteriorColor(new PdfArray(interiorColor));
    }
}
