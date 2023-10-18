package com.itextpdf.styledxmlparser.node.impl.jsoup.node;

import com.itextpdf.styledxmlparser.jsoup.nodes.Attribute;
import com.itextpdf.styledxmlparser.jsoup.nodes.Attributes;
import com.itextpdf.styledxmlparser.node.IAttribute;
import com.itextpdf.styledxmlparser.node.IAttributes;
import java.util.Iterator;

public class JsoupAttributes implements IAttributes {
    private Attributes attributes;

    public JsoupAttributes(Attributes attributes2) {
        this.attributes = attributes2;
    }

    public String getAttribute(String key) {
        if (this.attributes.hasKey(key)) {
            return this.attributes.get(key);
        }
        return null;
    }

    public void setAttribute(String key, String value) {
        if (this.attributes.hasKey(key)) {
            this.attributes.remove(key);
        }
        this.attributes.put(key, value);
    }

    public int size() {
        return this.attributes.size();
    }

    public Iterator<IAttribute> iterator() {
        return new AttributeIterator(this.attributes.iterator());
    }

    private static class AttributeIterator implements Iterator<IAttribute> {
        private Iterator<Attribute> iterator;

        public AttributeIterator(Iterator<Attribute> iterator2) {
            this.iterator = iterator2;
        }

        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        public IAttribute next() {
            return new JsoupAttribute(this.iterator.next());
        }

        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }
}
