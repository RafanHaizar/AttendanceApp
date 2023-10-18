package com.itextpdf.kernel.pdf.xobject;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfBoolean;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;

public class PdfTransparencyGroup extends PdfObjectWrapper<PdfDictionary> {
    private static final long serialVersionUID = 753843601750097627L;

    public PdfTransparencyGroup() {
        super(new PdfDictionary());
        ((PdfDictionary) getPdfObject()).put(PdfName.f1385S, PdfName.Transparency);
    }

    public void setIsolated(boolean isolated) {
        if (isolated) {
            ((PdfDictionary) getPdfObject()).put(PdfName.f1339I, PdfBoolean.TRUE);
        } else {
            ((PdfDictionary) getPdfObject()).remove(PdfName.f1339I);
        }
    }

    public void setKnockout(boolean knockout) {
        if (knockout) {
            ((PdfDictionary) getPdfObject()).put(PdfName.f1344K, PdfBoolean.TRUE);
        } else {
            ((PdfDictionary) getPdfObject()).remove(PdfName.f1344K);
        }
    }

    public void setColorSpace(PdfName colorSpace) {
        ((PdfDictionary) getPdfObject()).put(PdfName.f1309CS, colorSpace);
    }

    public void setColorSpace(PdfArray colorSpace) {
        ((PdfDictionary) getPdfObject()).put(PdfName.f1309CS, colorSpace);
    }

    public PdfTransparencyGroup put(PdfName key, PdfObject value) {
        ((PdfDictionary) getPdfObject()).put(key, value);
        return this;
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return false;
    }
}
