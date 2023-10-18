package com.itextpdf.forms.xfdf;

public class IdsObject {
    private String modified;
    private String original;

    public String getOriginal() {
        return this.original;
    }

    public IdsObject setOriginal(String original2) {
        this.original = original2;
        return this;
    }

    public String getModified() {
        return this.modified;
    }

    public IdsObject setModified(String modified2) {
        this.modified = modified2;
        return this;
    }
}
