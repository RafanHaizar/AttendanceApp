package com.itextpdf.styledxmlparser.jsoup.nodes;

public class BooleanAttribute extends Attribute {
    public BooleanAttribute(String key) {
        super(key, "");
    }

    /* access modifiers changed from: protected */
    public boolean isBooleanAttribute() {
        return true;
    }
}
