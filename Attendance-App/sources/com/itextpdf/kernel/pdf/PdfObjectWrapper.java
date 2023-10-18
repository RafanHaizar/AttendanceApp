package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfObject;
import java.io.Serializable;

public abstract class PdfObjectWrapper<T extends PdfObject> implements Serializable {
    private static final long serialVersionUID = 3516473712028588356L;
    private T pdfObject = null;

    /* access modifiers changed from: protected */
    public abstract boolean isWrappedObjectMustBeIndirect();

    protected PdfObjectWrapper(T pdfObject2) {
        this.pdfObject = pdfObject2;
        if (isWrappedObjectMustBeIndirect()) {
            markObjectAsIndirect(this.pdfObject);
        }
    }

    public T getPdfObject() {
        return this.pdfObject;
    }

    public PdfObjectWrapper<T> makeIndirect(PdfDocument document, PdfIndirectReference reference) {
        getPdfObject().makeIndirect(document, reference);
        return this;
    }

    public PdfObjectWrapper<T> makeIndirect(PdfDocument document) {
        return makeIndirect(document, (PdfIndirectReference) null);
    }

    public PdfObjectWrapper<T> setModified() {
        this.pdfObject.setModified();
        return this;
    }

    public void flush() {
        this.pdfObject.flush();
    }

    public boolean isFlushed() {
        return this.pdfObject.isFlushed();
    }

    /* access modifiers changed from: protected */
    public void setPdfObject(T pdfObject2) {
        this.pdfObject = pdfObject2;
    }

    /* access modifiers changed from: protected */
    public void setForbidRelease() {
        T t = this.pdfObject;
        if (t != null) {
            t.setState(128);
        }
    }

    /* access modifiers changed from: protected */
    public void unsetForbidRelease() {
        T t = this.pdfObject;
        if (t != null) {
            t.clearState(128);
        }
    }

    /* access modifiers changed from: protected */
    public void ensureUnderlyingObjectHasIndirectReference() {
        if (getPdfObject().getIndirectReference() == null) {
            throw new PdfException(PdfException.ToFlushThisWrapperUnderlyingObjectMustBeAddedToDocument);
        }
    }

    protected static void markObjectAsIndirect(PdfObject pdfObject2) {
        if (pdfObject2.getIndirectReference() == null) {
            pdfObject2.setState(64);
        }
    }

    protected static void ensureObjectIsAddedToDocument(PdfObject object) {
        if (object.getIndirectReference() == null) {
            throw new PdfException(PdfException.ObjectMustBeIndirectToWorkWithThisWrapper);
        }
    }
}
