package com.itextpdf.kernel.pdf.tagging;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfIndirectReference;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;

public class PdfObjRef extends PdfMcr {
    private static final long serialVersionUID = 344098256404114906L;

    public PdfObjRef(PdfDictionary pdfObject, PdfStructElem parent) {
        super(pdfObject, parent);
    }

    public PdfObjRef(PdfAnnotation annot, PdfStructElem parent, int nextStructParentIndex) {
        super(new PdfDictionary(), parent);
        ((PdfDictionary) annot.getPdfObject()).put(PdfName.StructParent, new PdfNumber(nextStructParentIndex));
        annot.setModified();
        PdfDictionary dict = (PdfDictionary) getPdfObject();
        dict.put(PdfName.Type, PdfName.OBJR);
        dict.put(PdfName.Obj, annot.getPdfObject());
    }

    public int getMcid() {
        return -1;
    }

    public PdfDictionary getPageObject() {
        return super.getPageObject();
    }

    public PdfDictionary getReferencedObject() {
        return ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.Obj);
    }

    private static PdfDocument getDocEnsureIndirect(PdfStructElem structElem) {
        PdfIndirectReference indRef = ((PdfDictionary) structElem.getPdfObject()).getIndirectReference();
        if (indRef != null) {
            return indRef.getDocument();
        }
        throw new PdfException(PdfException.f1244x971f4981);
    }
}
