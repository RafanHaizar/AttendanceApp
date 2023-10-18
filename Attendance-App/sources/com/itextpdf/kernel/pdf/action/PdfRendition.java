package com.itextpdf.kernel.pdf.action;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;
import com.itextpdf.p026io.util.MessageFormatUtil;

public class PdfRendition extends PdfObjectWrapper<PdfDictionary> {
    private static final long serialVersionUID = -726500192326824100L;

    public PdfRendition(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfRendition(String file, PdfFileSpec fs, String mimeType) {
        this(new PdfDictionary());
        ((PdfDictionary) getPdfObject()).put(PdfName.f1385S, PdfName.f1356MR);
        ((PdfDictionary) getPdfObject()).put(PdfName.f1357N, new PdfString(MessageFormatUtil.format("Rendition for {0}", file)));
        ((PdfDictionary) getPdfObject()).put(PdfName.f1300C, new PdfMediaClipData(file, fs, mimeType).getPdfObject());
    }

    public void flush() {
        super.flush();
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return true;
    }
}
