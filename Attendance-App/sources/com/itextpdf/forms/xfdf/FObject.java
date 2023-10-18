package com.itextpdf.forms.xfdf;

public class FObject {
    private String href;

    public FObject(String href2) {
        this.href = href2;
    }

    public String getHref() {
        return this.href;
    }

    public FObject setHref(String href2) {
        this.href = href2;
        return this;
    }
}
