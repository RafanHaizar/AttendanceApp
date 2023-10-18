package com.itextpdf.kernel.pdf.annot;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.annot.p027da.AnnotationDefaultAppearance;

public class PdfFreeTextAnnotation extends PdfMarkupAnnotation {
    public static final int CENTERED = 1;
    public static final int LEFT_JUSTIFIED = 0;
    public static final int RIGHT_JUSTIFIED = 2;
    private static final long serialVersionUID = -7835504102518915220L;

    public PdfFreeTextAnnotation(Rectangle rect, PdfString contents) {
        super(rect);
        setContents(contents);
    }

    protected PdfFreeTextAnnotation(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfName getSubtype() {
        return PdfName.FreeText;
    }

    public PdfString getDefaultStyleString() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.f1316DS);
    }

    public PdfFreeTextAnnotation setDefaultStyleString(PdfString defaultStyleString) {
        return (PdfFreeTextAnnotation) put(PdfName.f1316DS, defaultStyleString);
    }

    public PdfString getDefaultAppearance() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.f1313DA);
    }

    public PdfFreeTextAnnotation setDefaultAppearance(PdfString appearanceString) {
        return (PdfFreeTextAnnotation) put(PdfName.f1313DA, appearanceString);
    }

    public PdfFreeTextAnnotation setDefaultAppearance(AnnotationDefaultAppearance da) {
        return setDefaultAppearance(da.toPdfString());
    }

    public PdfArray getCalloutLine() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1306CL);
    }

    public PdfFreeTextAnnotation setCalloutLine(float[] calloutLine) {
        return setCalloutLine(new PdfArray(calloutLine));
    }

    public PdfFreeTextAnnotation setCalloutLine(PdfArray calloutLine) {
        return (PdfFreeTextAnnotation) put(PdfName.f1306CL, calloutLine);
    }

    public PdfName getLineEndingStyle() {
        return ((PdfDictionary) getPdfObject()).getAsName(PdfName.f1347LE);
    }

    public PdfFreeTextAnnotation setLineEndingStyle(PdfName lineEndingStyle) {
        return (PdfFreeTextAnnotation) put(PdfName.f1347LE, lineEndingStyle);
    }

    public int getJustification() {
        PdfNumber q = ((PdfDictionary) getPdfObject()).getAsNumber(PdfName.f1375Q);
        if (q == null) {
            return 0;
        }
        return q.intValue();
    }

    public PdfFreeTextAnnotation setJustification(int justification) {
        return (PdfFreeTextAnnotation) put(PdfName.f1375Q, new PdfNumber(justification));
    }

    public PdfDictionary getBorderStyle() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1298BS);
    }

    public PdfFreeTextAnnotation setBorderStyle(PdfDictionary borderStyle) {
        return (PdfFreeTextAnnotation) put(PdfName.f1298BS, borderStyle);
    }

    public PdfFreeTextAnnotation setBorderStyle(PdfName style) {
        return setBorderStyle(BorderStyleUtil.setStyle(getBorderStyle(), style));
    }

    public PdfFreeTextAnnotation setDashPattern(PdfArray dashPattern) {
        return setBorderStyle(BorderStyleUtil.setDashPattern(getBorderStyle(), dashPattern));
    }

    public PdfArray getRectangleDifferences() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1379RD);
    }

    public PdfFreeTextAnnotation setRectangleDifferences(PdfArray rect) {
        return (PdfFreeTextAnnotation) put(PdfName.f1379RD, rect);
    }

    public PdfDictionary getBorderEffect() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1295BE);
    }

    public PdfFreeTextAnnotation setBorderEffect(PdfDictionary borderEffect) {
        return (PdfFreeTextAnnotation) put(PdfName.f1295BE, borderEffect);
    }
}
