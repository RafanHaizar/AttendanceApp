package com.itextpdf.kernel.pdf;

import com.itextpdf.p026io.source.ByteUtils;

public class PdfNull extends PdfPrimitiveObject {
    private static final byte[] NullContent = ByteUtils.getIsoBytes("null");
    public static final PdfNull PDF_NULL = new PdfNull(true);
    private static final long serialVersionUID = 7789114018630038033L;

    public PdfNull() {
    }

    private PdfNull(boolean directOnly) {
        super(directOnly);
    }

    public byte getType() {
        return 7;
    }

    public String toString() {
        return "null";
    }

    /* access modifiers changed from: protected */
    public void generateContent() {
        this.content = NullContent;
    }

    /* access modifiers changed from: protected */
    public PdfObject newInstance() {
        return new PdfNull();
    }

    /* access modifiers changed from: protected */
    public void copyContent(PdfObject from, PdfDocument document) {
    }

    public boolean equals(Object obj) {
        return this == obj || (obj != null && getClass() == obj.getClass());
    }

    public int hashCode() {
        return 0;
    }
}
