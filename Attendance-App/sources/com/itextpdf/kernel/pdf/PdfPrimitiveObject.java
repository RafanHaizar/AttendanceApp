package com.itextpdf.kernel.pdf;

import com.itextpdf.p026io.LogMessageConstant;
import java.util.Arrays;
import org.slf4j.LoggerFactory;

public abstract class PdfPrimitiveObject extends PdfObject {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long serialVersionUID = -1788064882121987538L;
    protected byte[] content;
    protected boolean directOnly;

    /* access modifiers changed from: protected */
    public abstract void generateContent();

    protected PdfPrimitiveObject() {
        this.content = null;
    }

    protected PdfPrimitiveObject(boolean directOnly2) {
        this.content = null;
        this.directOnly = directOnly2;
    }

    protected PdfPrimitiveObject(byte[] content2) {
        this();
        if (content2 != null) {
            this.content = content2;
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: protected */
    public final byte[] getInternalContent() {
        if (this.content == null) {
            generateContent();
        }
        return this.content;
    }

    /* access modifiers changed from: protected */
    public boolean hasContent() {
        return this.content != null;
    }

    public PdfObject makeIndirect(PdfDocument document, PdfIndirectReference reference) {
        if (!this.directOnly) {
            return super.makeIndirect(document, reference);
        }
        LoggerFactory.getLogger((Class<?>) PdfObject.class).warn(LogMessageConstant.DIRECTONLY_OBJECT_CANNOT_BE_INDIRECT);
        return this;
    }

    public PdfObject setIndirectReference(PdfIndirectReference indirectReference) {
        if (!this.directOnly) {
            super.setIndirectReference(indirectReference);
        } else {
            LoggerFactory.getLogger((Class<?>) PdfObject.class).warn(LogMessageConstant.DIRECTONLY_OBJECT_CANNOT_BE_INDIRECT);
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public void copyContent(PdfObject from, PdfDocument document) {
        super.copyContent(from, document);
        byte[] bArr = ((PdfPrimitiveObject) from).content;
        if (bArr != null) {
            this.content = Arrays.copyOf(bArr, bArr.length);
        }
    }

    /* access modifiers changed from: protected */
    public int compareContent(PdfPrimitiveObject o) {
        for (int i = 0; i < Math.min(this.content.length, o.content.length); i++) {
            byte b = this.content[i];
            byte b2 = o.content[i];
            if (b > b2) {
                return 1;
            }
            if (b < b2) {
                return -1;
            }
        }
        return Integer.compare(this.content.length, o.content.length);
    }
}
