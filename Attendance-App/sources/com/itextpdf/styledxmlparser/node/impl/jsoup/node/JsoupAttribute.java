package com.itextpdf.styledxmlparser.node.impl.jsoup.node;

import com.itextpdf.styledxmlparser.jsoup.nodes.Attribute;
import com.itextpdf.styledxmlparser.node.IAttribute;

public class JsoupAttribute implements IAttribute {
    private Attribute attribute;

    public JsoupAttribute(Attribute attribute2) {
        this.attribute = attribute2;
    }

    public String getKey() {
        return this.attribute.getKey();
    }

    public String getValue() {
        return this.attribute.getValue();
    }
}
