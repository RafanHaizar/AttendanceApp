package com.itextpdf.kernel.pdf.annot;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.p026io.LogMessageConstant;
import org.slf4j.LoggerFactory;

public abstract class PdfMarkupAnnotation extends PdfAnnotation {
    private static final long serialVersionUID = 239280278775576458L;
    protected PdfAnnotation inReplyTo = null;
    protected PdfPopupAnnotation popup = null;

    protected PdfMarkupAnnotation(Rectangle rect) {
        super(rect);
    }

    protected PdfMarkupAnnotation(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfString getText() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.f1391T);
    }

    public PdfMarkupAnnotation setText(PdfString text) {
        return (PdfMarkupAnnotation) put(PdfName.f1391T, text);
    }

    public PdfNumber getOpacity() {
        return ((PdfDictionary) getPdfObject()).getAsNumber(PdfName.f1303CA);
    }

    public PdfMarkupAnnotation setOpacity(PdfNumber ca) {
        return (PdfMarkupAnnotation) put(PdfName.f1303CA, ca);
    }

    public PdfObject getRichText() {
        return ((PdfDictionary) getPdfObject()).get(PdfName.f1378RC);
    }

    public PdfMarkupAnnotation setRichText(PdfObject richText) {
        return (PdfMarkupAnnotation) put(PdfName.f1378RC, richText);
    }

    public PdfString getCreationDate() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.CreationDate);
    }

    public PdfMarkupAnnotation setCreationDate(PdfString creationDate) {
        return (PdfMarkupAnnotation) put(PdfName.CreationDate, creationDate);
    }

    public PdfDictionary getInReplyToObject() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.IRT);
    }

    public PdfAnnotation getInReplyTo() {
        if (this.inReplyTo == null) {
            this.inReplyTo = makeAnnotation(getInReplyToObject());
        }
        return this.inReplyTo;
    }

    public PdfMarkupAnnotation setInReplyTo(PdfAnnotation inReplyTo2) {
        this.inReplyTo = inReplyTo2;
        return (PdfMarkupAnnotation) put(PdfName.IRT, inReplyTo2.getPdfObject());
    }

    public PdfMarkupAnnotation setPopup(PdfPopupAnnotation popup2) {
        this.popup = popup2;
        popup2.setParent(this);
        return (PdfMarkupAnnotation) put(PdfName.Popup, popup2.getPdfObject());
    }

    public PdfDictionary getPopupObject() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.Popup);
    }

    public PdfPopupAnnotation getPopup() {
        PdfDictionary popupObject;
        if (this.popup == null && (popupObject = getPopupObject()) != null) {
            PdfAnnotation annotation = makeAnnotation(popupObject);
            if (!(annotation instanceof PdfPopupAnnotation)) {
                LoggerFactory.getLogger((Class<?>) PdfMarkupAnnotation.class).warn(LogMessageConstant.POPUP_ENTRY_IS_NOT_POPUP_ANNOTATION);
                return null;
            }
            this.popup = (PdfPopupAnnotation) annotation;
        }
        return this.popup;
    }

    public PdfString getSubject() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.Subj);
    }

    public PdfMarkupAnnotation setSubject(PdfString subject) {
        return (PdfMarkupAnnotation) put(PdfName.Subj, subject);
    }

    public PdfName getReplyType() {
        return ((PdfDictionary) getPdfObject()).getAsName(PdfName.f1383RT);
    }

    public PdfMarkupAnnotation setReplyType(PdfName replyType) {
        return (PdfMarkupAnnotation) put(PdfName.f1383RT, replyType);
    }

    public PdfName getIntent() {
        return ((PdfDictionary) getPdfObject()).getAsName(PdfName.f1342IT);
    }

    public PdfMarkupAnnotation setIntent(PdfName intent) {
        return (PdfMarkupAnnotation) put(PdfName.f1342IT, intent);
    }

    public PdfDictionary getExternalData() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.ExData);
    }

    @Deprecated
    public PdfMarkupAnnotation setExternalData(PdfName exData) {
        return (PdfMarkupAnnotation) put(PdfName.ExData, exData);
    }

    public PdfMarkupAnnotation setExternalData(PdfDictionary exData) {
        return (PdfMarkupAnnotation) put(PdfName.ExData, exData);
    }
}
