package com.itextpdf.kernel.pdf.collection;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import com.itextpdf.kernel.pdf.PdfString;

public class PdfCollection extends PdfObjectWrapper<PdfDictionary> {
    public static final int DETAILS = 0;
    public static final int HIDDEN = 2;
    public static final int TILE = 1;
    private static final long serialVersionUID = 5184499156015360355L;

    public PdfCollection(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfCollection() {
        this(new PdfDictionary());
    }

    public PdfCollection setSchema(PdfCollectionSchema schema) {
        ((PdfDictionary) getPdfObject()).put(PdfName.Schema, schema.getPdfObject());
        return this;
    }

    public PdfCollectionSchema getSchema() {
        return new PdfCollectionSchema(((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.Schema));
    }

    public PdfCollection setInitialDocument(String documentName) {
        ((PdfDictionary) getPdfObject()).put(PdfName.f1312D, new PdfString(documentName));
        return this;
    }

    public PdfString getInitialDocument() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.f1312D);
    }

    public PdfCollection setView(int viewType) {
        switch (viewType) {
            case 1:
                ((PdfDictionary) getPdfObject()).put(PdfName.View, PdfName.f1391T);
                break;
            case 2:
                ((PdfDictionary) getPdfObject()).put(PdfName.View, PdfName.f1331H);
                break;
            default:
                ((PdfDictionary) getPdfObject()).put(PdfName.View, PdfName.f1312D);
                break;
        }
        return this;
    }

    @Deprecated
    public PdfNumber getView() {
        return ((PdfDictionary) getPdfObject()).getAsNumber(PdfName.View);
    }

    public boolean isViewDetails() {
        PdfName view = ((PdfDictionary) getPdfObject()).getAsName(PdfName.View);
        return view == null || view.equals(PdfName.f1312D);
    }

    public boolean isViewTile() {
        return PdfName.f1391T.equals(((PdfDictionary) getPdfObject()).getAsName(PdfName.View));
    }

    public boolean isViewHidden() {
        return PdfName.f1331H.equals(((PdfDictionary) getPdfObject()).getAsName(PdfName.View));
    }

    public PdfCollection setSort(PdfCollectionSort sort) {
        ((PdfDictionary) getPdfObject()).put(PdfName.Sort, sort.getPdfObject());
        return this;
    }

    public PdfCollectionSort getSort() {
        return new PdfCollectionSort(((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.Sort));
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return false;
    }
}
