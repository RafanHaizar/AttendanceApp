package com.itextpdf.kernel.pdf.collection;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfDate;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import com.itextpdf.kernel.pdf.PdfString;

public class PdfCollectionItem extends PdfObjectWrapper<PdfDictionary> {
    private static final long serialVersionUID = -6471103872805179766L;
    private PdfCollectionSchema schema;

    public PdfCollectionItem(PdfCollectionSchema schema2) {
        super(new PdfDictionary());
        this.schema = schema2;
    }

    public PdfCollectionItem addItem(String key, String value) {
        ((PdfDictionary) getPdfObject()).put(new PdfName(key), this.schema.getField(key).getValue(value));
        return this;
    }

    public void addItem(String key, PdfDate date) {
        if (this.schema.getField(key).subType == 1) {
            ((PdfDictionary) getPdfObject()).put(new PdfName(key), date.getPdfObject());
        }
    }

    public void addItem(String key, PdfNumber number) {
        if (this.schema.getField(key).subType == 2) {
            ((PdfDictionary) getPdfObject()).put(new PdfName(key), number);
        }
    }

    public PdfCollectionItem setPrefix(String key, String prefix) {
        PdfName fieldName = new PdfName(key);
        PdfObject obj = ((PdfDictionary) getPdfObject()).get(fieldName);
        if (obj != null) {
            PdfDictionary subItem = new PdfDictionary();
            subItem.put(PdfName.f1312D, obj);
            subItem.put(PdfName.f1367P, new PdfString(prefix));
            ((PdfDictionary) getPdfObject()).put(fieldName, subItem);
            return this;
        }
        throw new PdfException(PdfException.YouMustSetAValueBeforeAddingAPrefix);
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return false;
    }
}
