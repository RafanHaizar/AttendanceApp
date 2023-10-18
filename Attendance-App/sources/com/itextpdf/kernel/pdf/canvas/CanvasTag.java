package com.itextpdf.kernel.pdf.canvas;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.tagging.PdfMcr;

public class CanvasTag {
    protected PdfDictionary properties;
    protected PdfName role;

    public CanvasTag(PdfName role2) {
        this.role = role2;
    }

    public CanvasTag(PdfName role2, int mcid) {
        this.role = role2;
        addProperty(PdfName.MCID, new PdfNumber(mcid));
    }

    public CanvasTag(PdfMcr mcr) {
        this(mcr.getRole(), mcr.getMcid());
    }

    public PdfName getRole() {
        return this.role;
    }

    public int getMcid() {
        int mcid = -1;
        PdfDictionary pdfDictionary = this.properties;
        if (pdfDictionary != null) {
            mcid = pdfDictionary.getAsInt(PdfName.MCID).intValue();
        }
        if (mcid != -1) {
            return mcid;
        }
        throw new IllegalStateException("CanvasTag has no MCID");
    }

    public boolean hasMcid() {
        PdfDictionary pdfDictionary = this.properties;
        return pdfDictionary != null && pdfDictionary.containsKey(PdfName.MCID);
    }

    public CanvasTag setProperties(PdfDictionary properties2) {
        this.properties = properties2;
        return this;
    }

    public CanvasTag addProperty(PdfName name, PdfObject value) {
        ensurePropertiesInit();
        this.properties.put(name, value);
        return this;
    }

    public CanvasTag removeProperty(PdfName name) {
        PdfDictionary pdfDictionary = this.properties;
        if (pdfDictionary != null) {
            pdfDictionary.remove(name);
        }
        return this;
    }

    public PdfObject getProperty(PdfName name) {
        PdfDictionary pdfDictionary = this.properties;
        if (pdfDictionary == null) {
            return null;
        }
        return pdfDictionary.get(name);
    }

    public PdfDictionary getProperties() {
        return this.properties;
    }

    public String getActualText() {
        return getPropertyAsString(PdfName.ActualText);
    }

    public String getExpansionText() {
        return getPropertyAsString(PdfName.f1320E);
    }

    private String getPropertyAsString(PdfName name) {
        PdfString text = this.properties.getAsString(name);
        if (text != null) {
            return text.toUnicodeString();
        }
        return null;
    }

    private void ensurePropertiesInit() {
        if (this.properties == null) {
            this.properties = new PdfDictionary();
        }
    }
}
