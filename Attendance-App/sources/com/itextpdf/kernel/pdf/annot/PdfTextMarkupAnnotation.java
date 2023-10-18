package com.itextpdf.kernel.pdf.annot;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;

public class PdfTextMarkupAnnotation extends PdfMarkupAnnotation {
    public static final PdfName MarkupHighlight = PdfName.Highlight;
    public static final PdfName MarkupSquiggly = PdfName.Squiggly;
    public static final PdfName MarkupStrikeout = PdfName.StrikeOut;
    public static final PdfName MarkupUnderline = PdfName.Underline;
    private static final long serialVersionUID = 2189266742204503217L;

    public PdfTextMarkupAnnotation(Rectangle rect, PdfName subtype, float[] quadPoints) {
        super(rect);
        put(PdfName.Subtype, subtype);
        setQuadPoints(new PdfArray(quadPoints));
    }

    protected PdfTextMarkupAnnotation(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public static PdfTextMarkupAnnotation createHighLight(Rectangle rect, float[] quadPoints) {
        return new PdfTextMarkupAnnotation(rect, MarkupHighlight, quadPoints);
    }

    public static PdfTextMarkupAnnotation createUnderline(Rectangle rect, float[] quadPoints) {
        return new PdfTextMarkupAnnotation(rect, MarkupUnderline, quadPoints);
    }

    public static PdfTextMarkupAnnotation createStrikeout(Rectangle rect, float[] quadPoints) {
        return new PdfTextMarkupAnnotation(rect, MarkupStrikeout, quadPoints);
    }

    public static PdfTextMarkupAnnotation createSquiggly(Rectangle rect, float[] quadPoints) {
        return new PdfTextMarkupAnnotation(rect, MarkupSquiggly, quadPoints);
    }

    public PdfName getSubtype() {
        PdfName subType = ((PdfDictionary) getPdfObject()).getAsName(PdfName.Subtype);
        if (subType == null) {
            return PdfName.Underline;
        }
        return subType;
    }

    public PdfArray getQuadPoints() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.QuadPoints);
    }

    public PdfTextMarkupAnnotation setQuadPoints(PdfArray quadPoints) {
        return (PdfTextMarkupAnnotation) put(PdfName.QuadPoints, quadPoints);
    }
}
