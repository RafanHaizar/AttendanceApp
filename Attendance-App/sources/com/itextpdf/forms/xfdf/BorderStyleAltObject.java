package com.itextpdf.forms.xfdf;

public class BorderStyleAltObject {
    private String content;
    private float[] dashPattern;
    private float hCornerRadius;
    private float vCornerRadius;
    private float width;

    public BorderStyleAltObject(float hCornerRadius2, float vCornerRadius2, float width2) {
        this.hCornerRadius = hCornerRadius2;
        this.vCornerRadius = vCornerRadius2;
        this.width = width2;
    }

    public float getHCornerRadius() {
        return this.hCornerRadius;
    }

    public float getVCornerRadius() {
        return this.vCornerRadius;
    }

    public float getWidth() {
        return this.width;
    }

    public float[] getDashPattern() {
        return this.dashPattern;
    }

    public BorderStyleAltObject setDashPattern(float[] dashPattern2) {
        this.dashPattern = dashPattern2;
        return this;
    }

    public String getContent() {
        return this.content;
    }

    public BorderStyleAltObject setContent(String content2) {
        this.content = content2;
        return this;
    }
}
