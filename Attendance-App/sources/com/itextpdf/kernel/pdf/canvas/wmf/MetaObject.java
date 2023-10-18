package com.itextpdf.kernel.pdf.canvas.wmf;

public class MetaObject {
    public static final int META_BRUSH = 2;
    public static final int META_FONT = 3;
    public static final int META_NOT_SUPPORTED = 0;
    public static final int META_PEN = 1;
    private int type = 0;

    public MetaObject() {
    }

    public MetaObject(int type2) {
        this.type = type2;
    }

    public int getType() {
        return this.type;
    }
}
