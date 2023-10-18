package com.itextpdf.signatures;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;

public class PdfSignatureBuildProperties extends PdfObjectWrapper<PdfDictionary> {
    public PdfSignatureBuildProperties() {
        super(new PdfDictionary());
    }

    public PdfSignatureBuildProperties(PdfDictionary dict) {
        super(dict);
    }

    public void setSignatureCreator(String name) {
        getPdfSignatureAppProperty().setSignatureCreator(name);
    }

    private PdfSignatureApp getPdfSignatureAppProperty() {
        PdfDictionary appPropDic = ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.App);
        if (appPropDic == null) {
            appPropDic = new PdfDictionary();
            ((PdfDictionary) getPdfObject()).put(PdfName.App, appPropDic);
        }
        return new PdfSignatureApp(appPropDic);
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return false;
    }
}
