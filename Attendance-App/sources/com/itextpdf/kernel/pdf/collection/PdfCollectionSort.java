package com.itextpdf.kernel.pdf.collection;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfBoolean;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import java.util.Arrays;
import java.util.List;

public class PdfCollectionSort extends PdfObjectWrapper<PdfDictionary> {
    private static final long serialVersionUID = -3871923275239410475L;

    public PdfCollectionSort(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfCollectionSort(String key) {
        this(new PdfDictionary());
        ((PdfDictionary) getPdfObject()).put(PdfName.f1385S, new PdfName(key));
    }

    public PdfCollectionSort(String[] keys) {
        this(new PdfDictionary());
        ((PdfDictionary) getPdfObject()).put(PdfName.f1385S, new PdfArray((List<String>) Arrays.asList(keys), true));
    }

    public PdfCollectionSort setSortOrder(boolean ascending) {
        if (((PdfDictionary) getPdfObject()).get(PdfName.f1385S).isName()) {
            ((PdfDictionary) getPdfObject()).put(PdfName.f1287A, PdfBoolean.valueOf(ascending));
            return this;
        }
        throw new PdfException(PdfException.YouHaveToDefineABooleanArrayForThisCollectionSortDictionary);
    }

    public PdfCollectionSort setSortOrder(boolean[] ascending) {
        PdfObject obj = ((PdfDictionary) getPdfObject()).get(PdfName.f1385S);
        if (!obj.isArray()) {
            throw new PdfException(PdfException.YouNeedASingleBooleanForThisCollectionSortDictionary);
        } else if (((PdfArray) obj).size() == ascending.length) {
            ((PdfDictionary) getPdfObject()).put(PdfName.f1287A, new PdfArray(ascending));
            return this;
        } else {
            throw new PdfException(PdfException.NumberOfBooleansInTheArrayDoesntCorrespondWithTheNumberOfFields);
        }
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return false;
    }
}
