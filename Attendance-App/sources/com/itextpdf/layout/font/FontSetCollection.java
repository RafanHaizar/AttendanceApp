package com.itextpdf.layout.font;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

class FontSetCollection extends AbstractCollection<FontInfo> {
    /* access modifiers changed from: private */
    public final Collection<FontInfo> additional;
    /* access modifiers changed from: private */
    public final Collection<FontInfo> primary;

    FontSetCollection(Collection<FontInfo> primary2, Collection<FontInfo> additional2) {
        this.primary = primary2;
        this.additional = additional2;
    }

    public int size() {
        int size = this.primary.size();
        Collection<FontInfo> collection = this.additional;
        return size + (collection != null ? collection.size() : 0);
    }

    public Iterator<FontInfo> iterator() {
        return new Iterator<FontInfo>() {

            /* renamed from: i */
            private Iterator<FontInfo> f1524i;
            boolean isPrimary = true;

            {
                this.f1524i = FontSetCollection.this.primary.iterator();
            }

            public boolean hasNext() {
                boolean hasNext = this.f1524i.hasNext();
                if (hasNext || !this.isPrimary || FontSetCollection.this.additional == null) {
                    return hasNext;
                }
                Iterator<FontInfo> it = FontSetCollection.this.additional.iterator();
                this.f1524i = it;
                this.isPrimary = false;
                return it.hasNext();
            }

            public FontInfo next() {
                return this.f1524i.next();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }
}
