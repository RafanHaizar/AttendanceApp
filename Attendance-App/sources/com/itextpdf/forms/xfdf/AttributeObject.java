package com.itextpdf.forms.xfdf;

public class AttributeObject {
    private String name;
    private String value;

    public AttributeObject(String name2, String value2) {
        if (name2 == null || value2 == null) {
            throw new XfdfException("Attribute name or value are missing");
        }
        this.name = name2;
        this.value = value2;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }
}
