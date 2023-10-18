package com.itextpdf.kernel.pdf.tagging;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfIndirectReference;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import java.util.List;

public abstract class PdfMcr extends PdfObjectWrapper<PdfObject> implements IStructureNode {
    private static final long serialVersionUID = -6453225665665080940L;
    protected PdfStructElem parent;

    public abstract int getMcid();

    protected PdfMcr(PdfObject pdfObject, PdfStructElem parent2) {
        super(pdfObject);
        this.parent = parent2;
    }

    public PdfDictionary getPageObject() {
        PdfObject pageObject = getPageIndirectReference().getRefersTo();
        if (pageObject instanceof PdfDictionary) {
            return (PdfDictionary) pageObject;
        }
        return null;
    }

    public PdfIndirectReference getPageIndirectReference() {
        PdfObject page = null;
        if (getPdfObject() instanceof PdfDictionary) {
            page = ((PdfDictionary) getPdfObject()).get(PdfName.f1374Pg, false);
        }
        if (page == null) {
            page = ((PdfDictionary) this.parent.getPdfObject()).get(PdfName.f1374Pg, false);
        }
        if (page instanceof PdfIndirectReference) {
            return (PdfIndirectReference) page;
        }
        if (page instanceof PdfDictionary) {
            return page.getIndirectReference();
        }
        return null;
    }

    public PdfName getRole() {
        return this.parent.getRole();
    }

    public IStructureNode getParent() {
        return this.parent;
    }

    public List<IStructureNode> getKids() {
        return null;
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return false;
    }
}
