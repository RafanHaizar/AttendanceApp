package com.itextpdf.kernel.pdf.action;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;
import com.itextpdf.p026io.util.MessageFormatUtil;

public class PdfMediaClipData extends PdfObjectWrapper<PdfDictionary> {
    private static final PdfString TEMPACCESS = new PdfString("TEMPACCESS");
    private static final long serialVersionUID = -7030377585169961523L;

    public PdfMediaClipData(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfMediaClipData(String file, PdfFileSpec fs, String mimeType) {
        this(new PdfDictionary());
        PdfDictionary dic = new PdfDictionary();
        markObjectAsIndirect(dic);
        dic.put(PdfName.f1394TF, TEMPACCESS);
        ((PdfDictionary) getPdfObject()).put(PdfName.Type, PdfName.MediaClip);
        ((PdfDictionary) getPdfObject()).put(PdfName.f1385S, PdfName.MCD);
        ((PdfDictionary) getPdfObject()).put(PdfName.f1357N, new PdfString(MessageFormatUtil.format("Media clip for {0}", file)));
        ((PdfDictionary) getPdfObject()).put(PdfName.f1310CT, new PdfString(mimeType));
        ((PdfDictionary) getPdfObject()).put(PdfName.f1367P, dic);
        ((PdfDictionary) getPdfObject()).put(PdfName.f1312D, fs.getPdfObject());
    }

    public void flush() {
        super.flush();
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return true;
    }
}
