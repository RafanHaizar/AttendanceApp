package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.geom.Rectangle;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class PdfDictionary extends PdfObject {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long serialVersionUID = -1122075818690871644L;
    private Map<PdfName, PdfObject> map;

    public PdfDictionary() {
        this.map = new TreeMap();
    }

    public PdfDictionary(Map<PdfName, PdfObject> map2) {
        TreeMap treeMap = new TreeMap();
        this.map = treeMap;
        treeMap.putAll(map2);
    }

    public PdfDictionary(Set<Map.Entry<PdfName, PdfObject>> entrySet) {
        this.map = new TreeMap();
        for (Map.Entry<PdfName, PdfObject> entry : entrySet) {
            this.map.put(entry.getKey(), entry.getValue());
        }
    }

    public PdfDictionary(PdfDictionary dictionary) {
        TreeMap treeMap = new TreeMap();
        this.map = treeMap;
        treeMap.putAll(dictionary.map);
    }

    public int size() {
        return this.map.size();
    }

    public boolean isEmpty() {
        return this.map.size() == 0;
    }

    public boolean containsKey(PdfName key) {
        return this.map.containsKey(key);
    }

    public boolean containsValue(PdfObject value) {
        return this.map.values().contains(value);
    }

    public PdfObject get(PdfName key) {
        return get(key, true);
    }

    public PdfArray getAsArray(PdfName key) {
        PdfObject direct = get(key, true);
        if (direct == null || direct.getType() != 1) {
            return null;
        }
        return (PdfArray) direct;
    }

    public PdfDictionary getAsDictionary(PdfName key) {
        PdfObject direct = get(key, true);
        if (direct == null || direct.getType() != 3) {
            return null;
        }
        return (PdfDictionary) direct;
    }

    public PdfStream getAsStream(PdfName key) {
        PdfObject direct = get(key, true);
        if (direct == null || direct.getType() != 9) {
            return null;
        }
        return (PdfStream) direct;
    }

    public PdfNumber getAsNumber(PdfName key) {
        PdfObject direct = get(key, true);
        if (direct == null || direct.getType() != 8) {
            return null;
        }
        return (PdfNumber) direct;
    }

    public PdfName getAsName(PdfName key) {
        PdfObject direct = get(key, true);
        if (direct == null || direct.getType() != 6) {
            return null;
        }
        return (PdfName) direct;
    }

    public PdfString getAsString(PdfName key) {
        PdfObject direct = get(key, true);
        if (direct == null || direct.getType() != 10) {
            return null;
        }
        return (PdfString) direct;
    }

    public PdfBoolean getAsBoolean(PdfName key) {
        PdfObject direct = get(key, true);
        if (direct == null || direct.getType() != 2) {
            return null;
        }
        return (PdfBoolean) direct;
    }

    public Rectangle getAsRectangle(PdfName key) {
        PdfArray a = getAsArray(key);
        if (a == null) {
            return null;
        }
        return a.toRectangle();
    }

    public Float getAsFloat(PdfName key) {
        PdfNumber number = getAsNumber(key);
        if (number != null) {
            return Float.valueOf(number.floatValue());
        }
        return null;
    }

    public Integer getAsInt(PdfName key) {
        PdfNumber number = getAsNumber(key);
        if (number != null) {
            return Integer.valueOf(number.intValue());
        }
        return null;
    }

    public Boolean getAsBool(PdfName key) {
        PdfBoolean b = getAsBoolean(key);
        if (b != null) {
            return Boolean.valueOf(b.getValue());
        }
        return null;
    }

    public PdfObject put(PdfName key, PdfObject value) {
        if (value != null) {
            return this.map.put(key, value);
        }
        throw new AssertionError();
    }

    public PdfObject remove(PdfName key) {
        return this.map.remove(key);
    }

    public void putAll(PdfDictionary d) {
        this.map.putAll(d.map);
    }

    public void clear() {
        this.map.clear();
    }

    public Set<PdfName> keySet() {
        return this.map.keySet();
    }

    public Collection<PdfObject> values(boolean asDirects) {
        if (asDirects) {
            return values();
        }
        return this.map.values();
    }

    public Collection<PdfObject> values() {
        return new PdfDictionaryValues(this.map.values());
    }

    public Set<Map.Entry<PdfName, PdfObject>> entrySet() {
        return new PdfDictionaryEntrySet(this.map.entrySet());
    }

    public byte getType() {
        return 3;
    }

    public String toString() {
        if (isFlushed()) {
            return this.indirectReference.toString();
        }
        String string = "<<";
        for (Map.Entry<PdfName, PdfObject> entry : this.map.entrySet()) {
            PdfIndirectReference indirectReference = entry.getValue().getIndirectReference();
            string = string + entry.getKey().toString() + " " + (indirectReference == null ? entry.getValue().toString() : indirectReference.toString()) + " ";
        }
        return string + ">>";
    }

    public PdfDictionary clone(List<PdfName> excludeKeys) {
        Map<PdfName, PdfObject> excluded = new TreeMap<>();
        for (PdfName key : excludeKeys) {
            if (this.map.get(key) != null) {
                excluded.put(key, this.map.remove(key));
            }
        }
        PdfDictionary dictionary = (PdfDictionary) clone();
        this.map.putAll(excluded);
        return dictionary;
    }

    public PdfDictionary copyTo(PdfDocument document, List<PdfName> excludeKeys, boolean allowDuplicating) {
        Map<PdfName, PdfObject> excluded = new TreeMap<>();
        for (PdfName key : excludeKeys) {
            if (this.map.get(key) != null) {
                excluded.put(key, this.map.remove(key));
            }
        }
        PdfDictionary dictionary = (PdfDictionary) copyTo(document, allowDuplicating);
        this.map.putAll(excluded);
        return dictionary;
    }

    public PdfObject get(PdfName key, boolean asDirect) {
        if (!asDirect) {
            return this.map.get(key);
        }
        PdfObject obj = this.map.get(key);
        if (obj == null || obj.getType() != 5) {
            return obj;
        }
        return ((PdfIndirectReference) obj).getRefersTo(true);
    }

    public void mergeDifferent(PdfDictionary other) {
        for (PdfName key : other.keySet()) {
            if (!containsKey(key)) {
                put(key, other.get(key));
            }
        }
    }

    /* access modifiers changed from: protected */
    public PdfObject newInstance() {
        return new PdfDictionary();
    }

    /* access modifiers changed from: protected */
    public void copyContent(PdfObject from, PdfDocument document) {
        super.copyContent(from, document);
        for (Map.Entry<PdfName, PdfObject> entry : ((PdfDictionary) from).map.entrySet()) {
            this.map.put(entry.getKey(), entry.getValue().processCopying(document, false));
        }
    }

    /* access modifiers changed from: protected */
    public void releaseContent() {
        this.map = null;
    }
}
