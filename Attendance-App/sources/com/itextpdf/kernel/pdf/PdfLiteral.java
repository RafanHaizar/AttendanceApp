package com.itextpdf.kernel.pdf;

import com.itextpdf.p026io.font.PdfEncodings;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class PdfLiteral extends PdfPrimitiveObject {
    private static final long serialVersionUID = -770215611509192403L;
    private long position;

    public PdfLiteral(byte[] content) {
        super(true);
        this.content = content;
    }

    public PdfLiteral(int size) {
        this(new byte[size]);
        Arrays.fill(this.content, (byte) 32);
    }

    public PdfLiteral(String content) {
        this(PdfEncodings.convertToBytes(content, (String) null));
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    private PdfLiteral() {
        this((byte[]) null);
        byte[] bArr = null;
    }

    public byte getType() {
        return 4;
    }

    public String toString() {
        if (this.content != null) {
            return new String(this.content, StandardCharsets.ISO_8859_1);
        }
        return "";
    }

    public long getPosition() {
        return this.position;
    }

    public void setPosition(long position2) {
        this.position = position2;
    }

    public int getBytesCount() {
        return this.content.length;
    }

    /* access modifiers changed from: protected */
    public void generateContent() {
    }

    public boolean equals(Object o) {
        return this == o || (o != null && getClass() == o.getClass() && Arrays.equals(this.content, ((PdfLiteral) o).content));
    }

    public int hashCode() {
        if (this.content == null) {
            return 0;
        }
        return Arrays.hashCode(this.content);
    }

    /* access modifiers changed from: protected */
    public PdfObject newInstance() {
        return new PdfLiteral();
    }

    /* access modifiers changed from: protected */
    public void copyContent(PdfObject from, PdfDocument document) {
        super.copyContent(from, document);
        this.content = ((PdfLiteral) from).getInternalContent();
    }
}
