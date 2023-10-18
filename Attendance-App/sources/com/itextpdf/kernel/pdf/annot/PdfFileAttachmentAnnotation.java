package com.itextpdf.kernel.pdf.annot;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;

public class PdfFileAttachmentAnnotation extends PdfMarkupAnnotation {
    private static final long serialVersionUID = -6190623972220405822L;

    public PdfFileAttachmentAnnotation(Rectangle rect) {
        super(rect);
    }

    public PdfFileAttachmentAnnotation(Rectangle rect, PdfFileSpec file) {
        this(rect);
        put(PdfName.f1326FS, file.getPdfObject());
    }

    protected PdfFileAttachmentAnnotation(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfName getSubtype() {
        return PdfName.FileAttachment;
    }

    public PdfObject getFileSpecObject() {
        return ((PdfDictionary) getPdfObject()).get(PdfName.f1326FS);
    }

    public PdfName getIconName() {
        return ((PdfDictionary) getPdfObject()).getAsName(PdfName.Name);
    }

    public PdfFileAttachmentAnnotation setIconName(PdfName name) {
        return (PdfFileAttachmentAnnotation) put(PdfName.Name, name);
    }
}
