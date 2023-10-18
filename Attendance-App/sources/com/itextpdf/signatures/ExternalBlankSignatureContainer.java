package com.itextpdf.signatures;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import java.io.InputStream;
import java.security.GeneralSecurityException;

public class ExternalBlankSignatureContainer implements IExternalSignatureContainer {
    private PdfDictionary sigDic;

    public ExternalBlankSignatureContainer(PdfDictionary sigDic2) {
        this.sigDic = sigDic2;
    }

    public ExternalBlankSignatureContainer(PdfName filter, PdfName subFilter) {
        PdfDictionary pdfDictionary = new PdfDictionary();
        this.sigDic = pdfDictionary;
        pdfDictionary.put(PdfName.Filter, filter);
        this.sigDic.put(PdfName.SubFilter, subFilter);
    }

    public byte[] sign(InputStream data) throws GeneralSecurityException {
        return new byte[0];
    }

    public void modifySigningDictionary(PdfDictionary signDic) {
        signDic.putAll(this.sigDic);
    }
}
