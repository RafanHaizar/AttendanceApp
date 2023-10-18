package com.itextpdf.forms.xfdf;

public class FieldObject {
    private boolean containsRichText;
    private String name;
    private FieldObject parent;
    private String richTextValue;
    private String value;

    public FieldObject() {
    }

    public FieldObject(String name2, String value2, boolean containsRichText2) {
        this.name = name2;
        this.containsRichText = containsRichText2;
        if (containsRichText2) {
            this.richTextValue = value2;
        } else {
            this.value = value2;
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value2) {
        this.value = value2;
    }

    public String getRichTextValue() {
        return this.richTextValue;
    }

    public void setRichTextValue(String richTextValue2) {
        this.richTextValue = richTextValue2;
    }

    public boolean isContainsRichText() {
        return this.containsRichText;
    }

    public void setContainsRichText(boolean containsRichText2) {
        this.containsRichText = containsRichText2;
    }

    public FieldObject getParent() {
        return this.parent;
    }

    public void setParent(FieldObject parent2) {
        this.parent = parent2;
    }
}
