package com.itextpdf.kernel.pdf;

import java.util.Iterator;
import java.util.List;

class PdfArrayDirectIterator implements Iterator<PdfObject> {
    Iterator<PdfObject> array;

    PdfArrayDirectIterator(List<PdfObject> array2) {
        this.array = array2.iterator();
    }

    public boolean hasNext() {
        return this.array.hasNext();
    }

    public PdfObject next() {
        PdfObject obj = this.array.next();
        if (obj.isIndirectReference()) {
            return ((PdfIndirectReference) obj).getRefersTo(true);
        }
        return obj;
    }

    public void remove() {
        this.array.remove();
    }
}
