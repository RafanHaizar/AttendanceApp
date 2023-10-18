package com.itextpdf.styledxmlparser.jsoup.nodes;

import com.itextpdf.styledxmlparser.jsoup.SerializationException;
import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Attributes implements Iterable<Attribute>, Cloneable {
    protected static final String dataPrefix = "data-";
    /* access modifiers changed from: private */
    public LinkedHashMap<String, Attribute> attributes = null;

    public String get(String key) {
        Attribute attr;
        Validate.notEmpty(key);
        LinkedHashMap<String, Attribute> linkedHashMap = this.attributes;
        if (linkedHashMap == null || (attr = linkedHashMap.get(key.toLowerCase())) == null) {
            return "";
        }
        return attr.getValue();
    }

    public void put(String key, String value) {
        put(new Attribute(key, value));
    }

    public void put(String key, boolean value) {
        if (value) {
            put(new BooleanAttribute(key));
        } else {
            remove(key);
        }
    }

    public void put(Attribute attribute) {
        Validate.notNull(attribute);
        if (this.attributes == null) {
            this.attributes = new LinkedHashMap<>(2);
        }
        this.attributes.put(attribute.getKey(), attribute);
    }

    public void remove(String key) {
        Validate.notEmpty(key);
        LinkedHashMap<String, Attribute> linkedHashMap = this.attributes;
        if (linkedHashMap != null) {
            linkedHashMap.remove(key.toLowerCase());
        }
    }

    public boolean hasKey(String key) {
        LinkedHashMap<String, Attribute> linkedHashMap = this.attributes;
        return linkedHashMap != null && linkedHashMap.containsKey(key.toLowerCase());
    }

    public int size() {
        LinkedHashMap<String, Attribute> linkedHashMap = this.attributes;
        if (linkedHashMap == null) {
            return 0;
        }
        return linkedHashMap.size();
    }

    public void addAll(Attributes incoming) {
        if (incoming.size() != 0) {
            if (this.attributes == null) {
                this.attributes = new LinkedHashMap<>(incoming.size());
            }
            this.attributes.putAll(incoming.attributes);
        }
    }

    public Iterator<Attribute> iterator() {
        return asList().iterator();
    }

    public List<Attribute> asList() {
        if (this.attributes == null) {
            return Collections.emptyList();
        }
        List<Attribute> list = new ArrayList<>(this.attributes.size());
        for (Map.Entry<String, Attribute> entry : this.attributes.entrySet()) {
            list.add(entry.getValue());
        }
        return Collections.unmodifiableList(list);
    }

    public Map<String, String> dataset() {
        return new Dataset();
    }

    public String html() {
        StringBuilder accum = new StringBuilder();
        try {
            html(accum, new Document("").outputSettings());
            return accum.toString();
        } catch (IOException e) {
            throw new SerializationException((Throwable) e);
        }
    }

    /* access modifiers changed from: package-private */
    public void html(Appendable accum, Document.OutputSettings out) throws IOException {
        LinkedHashMap<String, Attribute> linkedHashMap = this.attributes;
        if (linkedHashMap != null) {
            for (Map.Entry<String, Attribute> entry : linkedHashMap.entrySet()) {
                accum.append(" ");
                entry.getValue().html(accum, out);
            }
        }
    }

    public String toString() {
        return html();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attributes)) {
            return false;
        }
        Attributes that = (Attributes) o;
        LinkedHashMap<String, Attribute> linkedHashMap = this.attributes;
        if (linkedHashMap != null) {
            if (!linkedHashMap.equals(that.attributes)) {
                return false;
            }
            return true;
        } else if (that.attributes == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        LinkedHashMap<String, Attribute> linkedHashMap = this.attributes;
        if (linkedHashMap != null) {
            return linkedHashMap.hashCode();
        }
        return 0;
    }

    public Object clone() {
        if (this.attributes == null) {
            return new Attributes();
        }
        try {
            Attributes clone = (Attributes) super.clone();
            clone.attributes = new LinkedHashMap<>(this.attributes.size());
            Iterator<Attribute> it = iterator();
            while (it.hasNext()) {
                Attribute attribute = it.next();
                clone.attributes.put(attribute.getKey(), (Attribute) attribute.clone());
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    private class Dataset extends AbstractMap<String, String> {
        private Dataset() {
            if (Attributes.this.attributes == null) {
                LinkedHashMap unused = Attributes.this.attributes = new LinkedHashMap(2);
            }
        }

        public Set<Map.Entry<String, String>> entrySet() {
            return new EntrySet();
        }

        public String put(String key, String value) {
            String dataKey = Attributes.dataKey(key);
            String oldValue = Attributes.this.hasKey(dataKey) ? ((Attribute) Attributes.this.attributes.get(dataKey)).getValue() : null;
            Attributes.this.attributes.put(dataKey, new Attribute(dataKey, value));
            return oldValue;
        }

        private class EntrySet extends AbstractSet<Map.Entry<String, String>> {
            private EntrySet() {
            }

            public Iterator<Map.Entry<String, String>> iterator() {
                return new DatasetIterator();
            }

            public int size() {
                int count = 0;
                while (new DatasetIterator().hasNext()) {
                    count++;
                }
                return count;
            }
        }

        private class DatasetIterator implements Iterator<Map.Entry<String, String>> {
            private Attribute attr;
            private Iterator<Attribute> attrIter;

            private DatasetIterator() {
                this.attrIter = Attributes.this.attributes.values().iterator();
            }

            public boolean hasNext() {
                while (this.attrIter.hasNext()) {
                    Attribute next = this.attrIter.next();
                    this.attr = next;
                    if (next.isDataAttribute()) {
                        return true;
                    }
                }
                return false;
            }

            public Map.Entry<String, String> next() {
                return new Attribute(this.attr.getKey().substring(Attributes.dataPrefix.length()), this.attr.getValue());
            }

            public void remove() {
                Attributes.this.attributes.remove(this.attr.getKey());
            }
        }
    }

    /* access modifiers changed from: private */
    public static String dataKey(String key) {
        return dataPrefix + key;
    }
}
