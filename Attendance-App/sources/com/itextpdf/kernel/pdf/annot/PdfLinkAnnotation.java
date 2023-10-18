package com.itextpdf.kernel.pdf.annot;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.navigation.PdfDestination;
import com.itextpdf.p026io.LogMessageConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PdfLinkAnnotation extends PdfAnnotation {
    public static final PdfName Invert = PdfName.f1339I;
    public static final PdfName None = PdfName.f1357N;
    public static final PdfName Outline = PdfName.f1361O;
    public static final PdfName Push = PdfName.f1367P;
    private static final Logger logger = LoggerFactory.getLogger((Class<?>) PdfLinkAnnotation.class);
    private static final long serialVersionUID = 5795613340575331536L;

    protected PdfLinkAnnotation(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfLinkAnnotation(Rectangle rect) {
        super(rect);
    }

    public PdfName getSubtype() {
        return PdfName.Link;
    }

    public PdfObject getDestinationObject() {
        return ((PdfDictionary) getPdfObject()).get(PdfName.Dest);
    }

    public PdfLinkAnnotation setDestination(PdfObject destination) {
        if (((PdfDictionary) getPdfObject()).containsKey(PdfName.f1287A)) {
            ((PdfDictionary) getPdfObject()).remove(PdfName.f1287A);
            logger.warn(LogMessageConstant.DESTINATION_NOT_PERMITTED_WHEN_ACTION_IS_SET);
        }
        if (destination.isArray() && ((PdfArray) destination).get(0).isNumber()) {
            LoggerFactory.getLogger((Class<?>) PdfLinkAnnotation.class).warn(LogMessageConstant.INVALID_DESTINATION_TYPE);
        }
        return (PdfLinkAnnotation) put(PdfName.Dest, destination);
    }

    public PdfLinkAnnotation setDestination(PdfDestination destination) {
        return setDestination(destination.getPdfObject());
    }

    public PdfLinkAnnotation removeDestination() {
        ((PdfDictionary) getPdfObject()).remove(PdfName.Dest);
        return this;
    }

    public PdfDictionary getAction() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1287A);
    }

    public PdfLinkAnnotation setAction(PdfDictionary action) {
        return (PdfLinkAnnotation) put(PdfName.f1287A, action);
    }

    public PdfLinkAnnotation setAction(PdfAction action) {
        if (getDestinationObject() != null) {
            removeDestination();
            logger.warn(LogMessageConstant.ACTION_WAS_SET_TO_LINK_ANNOTATION_WITH_DESTINATION);
        }
        return (PdfLinkAnnotation) put(PdfName.f1287A, action.getPdfObject());
    }

    public PdfLinkAnnotation removeAction() {
        ((PdfDictionary) getPdfObject()).remove(PdfName.f1287A);
        return this;
    }

    public PdfName getHighlightMode() {
        return ((PdfDictionary) getPdfObject()).getAsName(PdfName.f1331H);
    }

    public PdfLinkAnnotation setHighlightMode(PdfName hlMode) {
        return (PdfLinkAnnotation) put(PdfName.f1331H, hlMode);
    }

    public PdfDictionary getUriActionObject() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1368PA);
    }

    public PdfLinkAnnotation setUriAction(PdfDictionary action) {
        return (PdfLinkAnnotation) put(PdfName.f1368PA, action);
    }

    public PdfLinkAnnotation setUriAction(PdfAction action) {
        return (PdfLinkAnnotation) put(PdfName.f1368PA, action.getPdfObject());
    }

    public PdfArray getQuadPoints() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.QuadPoints);
    }

    public PdfLinkAnnotation setQuadPoints(PdfArray quadPoints) {
        return (PdfLinkAnnotation) put(PdfName.QuadPoints, quadPoints);
    }

    public PdfDictionary getBorderStyle() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.f1298BS);
    }

    public PdfLinkAnnotation setBorderStyle(PdfDictionary borderStyle) {
        return (PdfLinkAnnotation) put(PdfName.f1298BS, borderStyle);
    }

    public PdfLinkAnnotation setBorderStyle(PdfName style) {
        return setBorderStyle(BorderStyleUtil.setStyle(getBorderStyle(), style));
    }

    public PdfLinkAnnotation setDashPattern(PdfArray dashPattern) {
        return setBorderStyle(BorderStyleUtil.setDashPattern(getBorderStyle(), dashPattern));
    }
}
