package com.itextpdf.kernel.pdf;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

class PdfDictionaryEntrySet extends AbstractSet<Map.Entry<PdfName, PdfObject>> {
    private final Set<Map.Entry<PdfName, PdfObject>> set;

    PdfDictionaryEntrySet(Set<Map.Entry<PdfName, PdfObject>> set2) {
        this.set = set2;
    }

    public boolean contains(Object o) {
        return this.set.contains(o) || super.contains(o);
    }

    public boolean remove(Object o) {
        return this.set.remove(o) || super.remove(o);
    }

    public Iterator<Map.Entry<PdfName, PdfObject>> iterator() {
        return new DirectIterator(this.set.iterator());
    }

    public int size() {
        return this.set.size();
    }

    public void clear() {
        this.set.clear();
    }

    private static class DirectIterator implements Iterator<Map.Entry<PdfName, PdfObject>> {
        Iterator<Map.Entry<PdfName, PdfObject>> parentIterator;

        public DirectIterator(Iterator<Map.Entry<PdfName, PdfObject>> parentIterator2) {
            this.parentIterator = parentIterator2;
        }

        public boolean hasNext() {
            return this.parentIterator.hasNext();
        }

        public Map.Entry<PdfName, PdfObject> next() {
            return new DirectEntry(this.parentIterator.next());
        }

        public void remove() {
            this.parentIterator.remove();
        }
    }

    private static class DirectEntry implements Map.Entry<PdfName, PdfObject> {
        Map.Entry<PdfName, PdfObject> entry;

        DirectEntry(Map.Entry<PdfName, PdfObject> entry2) {
            this.entry = entry2;
        }

        public PdfName getKey() {
            return this.entry.getKey();
        }

        public PdfObject getValue() {
            PdfObject obj = this.entry.getValue();
            if (obj == null || !obj.isIndirectReference()) {
                return obj;
            }
            return ((PdfIndirectReference) obj).getRefersTo(true);
        }

        public PdfObject setValue(PdfObject value) {
            return this.entry.setValue(value);
        }

        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry) o;
            Object k1 = getKey();
            Object k2 = e.getKey();
            if (k1 != null && k1.equals(k2)) {
                Object v1 = getValue();
                Object v2 = e.getValue();
                if (v1 == null || !v1.equals(v2)) {
                    return false;
                }
                return true;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue());
        }
    }
}
