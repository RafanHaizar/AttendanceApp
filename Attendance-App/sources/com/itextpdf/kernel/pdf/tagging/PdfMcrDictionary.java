package com.itextpdf.kernel.pdf.tagging;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfPage;

public class PdfMcrDictionary extends PdfMcr {
    private static final long serialVersionUID = 3562443854685749324L;

    public PdfMcrDictionary(PdfDictionary pdfObject, PdfStructElem parent) {
        super(pdfObject, parent);
    }

    public PdfMcrDictionary(PdfPage page, PdfStructElem parent) {
        super(new PdfDictionary(), parent);
        PdfDictionary dict = (PdfDictionary) getPdfObject();
        dict.put(PdfName.Type, PdfName.MCR);
        dict.put(PdfName.f1374Pg, ((PdfDictionary) page.getPdfObject()).getIndirectReference());
        dict.put(PdfName.MCID, new PdfNumber(page.getNextMcid()));
    }

    public int getMcid() {
        PdfNumber mcidNumber = ((PdfDictionary) getPdfObject()).getAsNumber(PdfName.MCID);
        if (mcidNumber != null) {
            return mcidNumber.intValue();
        }
        return -1;
    }

    public PdfDictionary getPageObject() {
        return super.getPageObject();
    }
}
