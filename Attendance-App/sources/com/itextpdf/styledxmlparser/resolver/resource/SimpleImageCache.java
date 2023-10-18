package com.itextpdf.styledxmlparser.resolver.resource;

import com.itextpdf.kernel.pdf.xobject.PdfXObject;
import java.util.LinkedHashMap;
import java.util.Map;

class SimpleImageCache {
    private Map<String, PdfXObject> cache;
    private int capacity;
    private Map<String, Integer> imagesFrequency;

    SimpleImageCache() {
        this.cache = new LinkedHashMap();
        this.imagesFrequency = new LinkedHashMap();
        this.capacity = 100;
    }

    SimpleImageCache(int capacity2) {
        this.cache = new LinkedHashMap();
        this.imagesFrequency = new LinkedHashMap();
        if (capacity2 >= 1) {
            this.capacity = capacity2;
            return;
        }
        throw new IllegalArgumentException("capacity");
    }

    /* access modifiers changed from: package-private */
    public void putImage(String src, PdfXObject imageXObject) {
        if (!this.cache.containsKey(src)) {
            ensureCapacity();
            this.cache.put(src, imageXObject);
        }
    }

    /* access modifiers changed from: package-private */
    public PdfXObject getImage(String src) {
        Integer frequency = this.imagesFrequency.get(src);
        if (frequency != null) {
            this.imagesFrequency.put(src, Integer.valueOf(frequency.intValue() + 1));
        } else {
            this.imagesFrequency.put(src, 1);
        }
        return this.cache.get(src);
    }

    /* access modifiers changed from: package-private */
    public int size() {
        return this.cache.size();
    }

    /* access modifiers changed from: package-private */
    public void reset() {
        this.cache.clear();
        this.imagesFrequency.clear();
    }

    private void ensureCapacity() {
        if (this.cache.size() >= this.capacity) {
            String mostUnpopularImg = null;
            int minFrequency = Integer.MAX_VALUE;
            for (String imgSrc : this.cache.keySet()) {
                Integer imgFrequency = this.imagesFrequency.get(imgSrc);
                if (imgFrequency == null || imgFrequency.intValue() < minFrequency) {
                    mostUnpopularImg = imgSrc;
                    if (imgFrequency == null) {
                        break;
                    }
                    minFrequency = imgFrequency.intValue();
                }
            }
            this.cache.remove(mostUnpopularImg);
        }
    }
}
