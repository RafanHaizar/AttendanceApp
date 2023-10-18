package com.itextpdf.kernel.pdf.annot;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfBoolean;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;

public class PdfLineAnnotation extends PdfMarkupAnnotation {
    private static final long serialVersionUID = -6047928061827404283L;

    public PdfLineAnnotation(Rectangle rect, float[] line) {
        super(rect);
        put(PdfName.f1345L, new PdfArray(line));
    }

    protected PdfLineAnnotation(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfName getSubtype() {
        return PdfName.Line;
    }

    public PdfArray getLine() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1345L);
    }

    public PdfDictionary getBorderStyle() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1298BS);
    }

    public PdfLineAnnotation setBorderStyle(PdfDictionary borderStyle) {
        return (PdfLineAnnotation) put(PdfName.f1298BS, borderStyle);
    }

    public PdfLineAnnotation setBorderStyle(PdfName style) {
        return setBorderStyle(BorderStyleUtil.setStyle(getBorderStyle(), style));
    }

    public PdfLineAnnotation setDashPattern(PdfArray dashPattern) {
        return setBorderStyle(BorderStyleUtil.setDashPattern(getBorderStyle(), dashPattern));
    }

    public PdfArray getLineEndingStyles() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1347LE);
    }

    public PdfLineAnnotation setLineEndingStyles(PdfArray lineEndingStyles) {
        return (PdfLineAnnotation) put(PdfName.f1347LE, lineEndingStyles);
    }

    public Color getInteriorColor() {
        return InteriorColorUtil.parseInteriorColor(((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1340IC));
    }

    public PdfLineAnnotation setInteriorColor(PdfArray interiorColor) {
        return (PdfLineAnnotation) put(PdfName.f1340IC, interiorColor);
    }

    public PdfLineAnnotation setInteriorColor(float[] interiorColor) {
        return setInteriorColor(new PdfArray(interiorColor));
    }

    public float getLeaderLineLength() {
        PdfNumber n = ((PdfDictionary) getPdfObject()).getAsNumber(PdfName.f1350LL);
        if (n == null) {
            return 0.0f;
        }
        return n.floatValue();
    }

    public PdfLineAnnotation setLeaderLineLength(float leaderLineLength) {
        return (PdfLineAnnotation) put(PdfName.f1350LL, new PdfNumber((double) leaderLineLength));
    }

    public float getLeaderLineExtension() {
        PdfNumber n = ((PdfDictionary) getPdfObject()).getAsNumber(PdfName.LLE);
        if (n == null) {
            return 0.0f;
        }
        return n.floatValue();
    }

    public PdfLineAnnotation setLeaderLineExtension(float leaderLineExtension) {
        return (PdfLineAnnotation) put(PdfName.LLE, new PdfNumber((double) leaderLineExtension));
    }

    public float getLeaderLineOffset() {
        PdfNumber n = ((PdfDictionary) getPdfObject()).getAsNumber(PdfName.LLO);
        if (n == null) {
            return 0.0f;
        }
        return n.floatValue();
    }

    public PdfLineAnnotation setLeaderLineOffset(float leaderLineOffset) {
        return (PdfLineAnnotation) put(PdfName.LLO, new PdfNumber((double) leaderLineOffset));
    }

    public boolean getContentsAsCaption() {
        PdfBoolean b = ((PdfDictionary) getPdfObject()).getAsBoolean(PdfName.Cap);
        return b != null && b.getValue();
    }

    public PdfLineAnnotation setContentsAsCaption(boolean contentsAsCaption) {
        return (PdfLineAnnotation) put(PdfName.Cap, PdfBoolean.valueOf(contentsAsCaption));
    }

    public PdfName getCaptionPosition() {
        return ((PdfDictionary) getPdfObject()).getAsName(PdfName.f1308CP);
    }

    public PdfLineAnnotation setCaptionPosition(PdfName captionPosition) {
        return (PdfLineAnnotation) put(PdfName.f1308CP, captionPosition);
    }

    public PdfDictionary getMeasure() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.Measure);
    }

    public PdfLineAnnotation setMeasure(PdfDictionary measure) {
        return (PdfLineAnnotation) put(PdfName.Measure, measure);
    }

    public PdfArray getCaptionOffset() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1307CO);
    }

    public PdfLineAnnotation setCaptionOffset(PdfArray captionOffset) {
        return (PdfLineAnnotation) put(PdfName.f1307CO, captionOffset);
    }

    public PdfLineAnnotation setCaptionOffset(float[] captionOffset) {
        return setCaptionOffset(new PdfArray(captionOffset));
    }
}
