package com.itextpdf.kernel.pdf.tagutils;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.tagging.PdfStructElem;

public class TagReference {
    protected int insertIndex;
    protected PdfDictionary properties;
    protected PdfStructElem referencedTag;
    protected PdfName role;
    protected TagTreePointer tagPointer;

    protected TagReference(PdfStructElem referencedTag2, TagTreePointer tagPointer2, int insertIndex2) {
        this.role = referencedTag2.getRole();
        this.referencedTag = referencedTag2;
        this.tagPointer = tagPointer2;
        this.insertIndex = insertIndex2;
    }

    public PdfName getRole() {
        return this.role;
    }

    public int createNextMcid() {
        return this.tagPointer.createNextMcidForStructElem(this.referencedTag, this.insertIndex);
    }

    public TagReference addProperty(PdfName name, PdfObject value) {
        if (this.properties == null) {
            this.properties = new PdfDictionary();
        }
        this.properties.put(name, value);
        return this;
    }

    public TagReference removeProperty(PdfName name) {
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
}
