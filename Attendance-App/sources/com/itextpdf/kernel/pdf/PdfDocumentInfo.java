package com.itextpdf.kernel.pdf;

import com.itextpdf.p026io.font.PdfEncodings;
import java.io.Serializable;
import java.util.Map;

public class PdfDocumentInfo implements Serializable {
    static final PdfName[] PDF20_DEPRECATED_KEYS = {PdfName.Title, PdfName.Author, PdfName.Subject, PdfName.Keywords, PdfName.Creator, PdfName.Producer, PdfName.Trapped};
    private static final long serialVersionUID = -21957940280527125L;
    private PdfDictionary infoDictionary;

    PdfDocumentInfo(PdfDictionary pdfObject, PdfDocument pdfDocument) {
        this.infoDictionary = pdfObject;
        if (pdfDocument.getWriter() != null) {
            this.infoDictionary.makeIndirect(pdfDocument);
        }
    }

    PdfDocumentInfo(PdfDocument pdfDocument) {
        this(new PdfDictionary(), pdfDocument);
    }

    public PdfDocumentInfo setTitle(String title) {
        return put(PdfName.Title, new PdfString(title, PdfEncodings.UNICODE_BIG));
    }

    public PdfDocumentInfo setAuthor(String author) {
        return put(PdfName.Author, new PdfString(author, PdfEncodings.UNICODE_BIG));
    }

    public PdfDocumentInfo setSubject(String subject) {
        return put(PdfName.Subject, new PdfString(subject, PdfEncodings.UNICODE_BIG));
    }

    public PdfDocumentInfo setKeywords(String keywords) {
        return put(PdfName.Keywords, new PdfString(keywords, PdfEncodings.UNICODE_BIG));
    }

    public PdfDocumentInfo setCreator(String creator) {
        return put(PdfName.Creator, new PdfString(creator, PdfEncodings.UNICODE_BIG));
    }

    public PdfDocumentInfo setTrapped(PdfName trapped) {
        return put(PdfName.Trapped, trapped);
    }

    public String getTitle() {
        return getStringValue(PdfName.Title);
    }

    public String getAuthor() {
        return getStringValue(PdfName.Author);
    }

    public String getSubject() {
        return getStringValue(PdfName.Subject);
    }

    public String getKeywords() {
        return getStringValue(PdfName.Keywords);
    }

    public String getCreator() {
        return getStringValue(PdfName.Creator);
    }

    public String getProducer() {
        return getStringValue(PdfName.Producer);
    }

    public PdfName getTrapped() {
        return this.infoDictionary.getAsName(PdfName.Trapped);
    }

    public PdfDocumentInfo addCreationDate() {
        return put(PdfName.CreationDate, new PdfDate().getPdfObject());
    }

    public PdfDocumentInfo addModDate() {
        return put(PdfName.ModDate, new PdfDate().getPdfObject());
    }

    public void setMoreInfo(Map<String, String> moreInfo) {
        if (moreInfo != null) {
            for (Map.Entry<String, String> entry : moreInfo.entrySet()) {
                setMoreInfo(entry.getKey(), entry.getValue());
            }
        }
    }

    public void setMoreInfo(String key, String value) {
        PdfName keyName = new PdfName(key);
        if (value == null) {
            this.infoDictionary.remove(keyName);
            this.infoDictionary.setModified();
            return;
        }
        put(keyName, new PdfString(value, PdfEncodings.UNICODE_BIG));
    }

    public String getMoreInfo(String key) {
        return getStringValue(new PdfName(key));
    }

    /* access modifiers changed from: package-private */
    public PdfDictionary getPdfObject() {
        return this.infoDictionary;
    }

    /* access modifiers changed from: package-private */
    public PdfDocumentInfo put(PdfName key, PdfObject value) {
        getPdfObject().put(key, value);
        getPdfObject().setModified();
        return this;
    }

    private String getStringValue(PdfName name) {
        PdfString pdfString = this.infoDictionary.getAsString(name);
        if (pdfString != null) {
            return pdfString.toUnicodeString();
        }
        return null;
    }
}
