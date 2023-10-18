package com.itextpdf.styledxmlparser.jsoup.helper;

import java.io.InputStream;

public class KeyVal {
    private String key;
    private InputStream stream;
    private String value;

    public static KeyVal create(String key2, String value2) {
        return new KeyVal().key(key2).value(value2);
    }

    public static KeyVal create(String key2, String filename, InputStream stream2) {
        return new KeyVal().key(key2).value(filename).inputStream(stream2);
    }

    private KeyVal() {
    }

    public KeyVal key(String key2) {
        Validate.notEmpty(key2, "Data key must not be empty");
        this.key = key2;
        return this;
    }

    public String key() {
        return this.key;
    }

    public KeyVal value(String value2) {
        Validate.notNull(value2, "Data value must not be null");
        this.value = value2;
        return this;
    }

    public String value() {
        return this.value;
    }

    public KeyVal inputStream(InputStream inputStream) {
        Validate.notNull(this.value, "Data input stream must not be null");
        this.stream = inputStream;
        return this;
    }

    public InputStream inputStream() {
        return this.stream;
    }

    public boolean hasInputStream() {
        return this.stream != null;
    }

    public String toString() {
        return this.key + "=" + this.value;
    }
}
