package com.itextpdf.kernel.pdf;

import com.itextpdf.p026io.source.ByteUtils;

public class PdfBoolean extends PdfPrimitiveObject {
    public static final PdfBoolean FALSE = new PdfBoolean(false, true);
    private static final byte[] False = ByteUtils.getIsoBytes("false");
    public static final PdfBoolean TRUE = new PdfBoolean(true, true);
    private static final byte[] True = ByteUtils.getIsoBytes("true");
    private static final long serialVersionUID = -1363839858135046832L;
    private boolean value;

    public PdfBoolean(boolean value2) {
        this(value2, false);
    }

    private PdfBoolean(boolean value2, boolean directOnly) {
        super(directOnly);
        this.value = value2;
    }

    private PdfBoolean() {
    }

    public boolean getValue() {
        return this.value;
    }

    public byte getType() {
        return 2;
    }

    public String toString() {
        return this.value ? "true" : "false";
    }

    /* access modifiers changed from: protected */
    public void generateContent() {
        this.content = this.value ? True : False;
    }

    /* access modifiers changed from: protected */
    public PdfObject newInstance() {
        return new PdfBoolean();
    }

    /* access modifiers changed from: protected */
    public void copyContent(PdfObject from, PdfDocument document) {
        super.copyContent(from, document);
        this.value = ((PdfBoolean) from).value;
    }

    public boolean equals(Object obj) {
        return this == obj || (obj != null && getClass() == obj.getClass() && this.value == ((PdfBoolean) obj).value);
    }

    public int hashCode() {
        return this.value ? 1 : 0;
    }

    public static PdfBoolean valueOf(boolean value2) {
        return value2 ? TRUE : FALSE;
    }
}
