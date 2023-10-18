package com.itextpdf.kernel.pdf;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

class PdfDictionaryValues extends AbstractCollection<PdfObject> {
    private final Collection<PdfObject> collection;

    PdfDictionaryValues(Collection<PdfObject> collection2) {
        this.collection = collection2;
    }

    public boolean add(PdfObject object) {
        return this.collection.add(object);
    }

    public boolean contains(Object o) {
        if (this.collection.contains(o)) {
            return true;
        }
        if (o == null) {
            return false;
        }
        Iterator<PdfObject> it = iterator();
        while (it.hasNext()) {
            if (PdfObject.equalContent((PdfObject) o, it.next())) {
                return true;
            }
        }
        return false;
    }

    public boolean remove(Object o) {
        if (this.collection.remove(o)) {
            return true;
        }
        if (o == null) {
            return false;
        }
        Iterator<PdfObject> it = iterator();
        while (it.hasNext()) {
            if (PdfObject.equalContent((PdfObject) o, it.next())) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public int size() {
        return this.collection.size();
    }

    public void clear() {
        this.collection.clear();
    }

    public Iterator<PdfObject> iterator() {
        return new DirectIterator(this.collection.iterator());
    }

    private static class DirectIterator implements Iterator<PdfObject> {
        Iterator<PdfObject> parentIterator;

        DirectIterator(Iterator<PdfObject> parentIterator2) {
            this.parentIterator = parentIterator2;
        }

        public boolean hasNext() {
            return this.parentIterator.hasNext();
        }

        public PdfObject next() {
            PdfObject obj = this.parentIterator.next();
            if (obj == null || !obj.isIndirectReference()) {
                return obj;
            }
            return ((PdfIndirectReference) obj).getRefersTo(true);
        }

        public void remove() {
            this.parentIterator.remove();
        }
    }
}
