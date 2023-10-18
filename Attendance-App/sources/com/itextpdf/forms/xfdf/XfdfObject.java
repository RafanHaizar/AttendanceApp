package com.itextpdf.forms.xfdf;

import com.itextpdf.kernel.pdf.PdfDocument;
import java.io.OutputStream;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class XfdfObject {
    private AnnotsObject annots;
    private List<AttributeObject> attributes;

    /* renamed from: f */
    private FObject f1186f;
    private FieldsObject fields;
    private IdsObject ids;

    public FObject getF() {
        return this.f1186f;
    }

    public void setF(FObject f) {
        this.f1186f = f;
    }

    public IdsObject getIds() {
        return this.ids;
    }

    public void setIds(IdsObject ids2) {
        this.ids = ids2;
    }

    public FieldsObject getFields() {
        return this.fields;
    }

    public void setFields(FieldsObject fields2) {
        this.fields = fields2;
    }

    public AnnotsObject getAnnots() {
        return this.annots;
    }

    public void setAnnots(AnnotsObject annots2) {
        this.annots = annots2;
    }

    public List<AttributeObject> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(List<AttributeObject> attributes2) {
        this.attributes = attributes2;
    }

    public void mergeToPdf(PdfDocument pdfDocument, String pdfDocumentName) {
        new XfdfReader().mergeXfdfIntoPdf(this, pdfDocument, pdfDocumentName);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0013, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0014, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0017, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000e, code lost:
        r2 = move-exception;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void writeToFile(java.lang.String r5) throws java.io.IOException, javax.xml.transform.TransformerException, javax.xml.parsers.ParserConfigurationException {
        /*
            r4 = this;
            java.io.FileOutputStream r0 = new java.io.FileOutputStream
            r0.<init>(r5)
            r4.writeToFile((java.io.OutputStream) r0)     // Catch:{ all -> 0x000c }
            r0.close()
            return
        L_0x000c:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x000e }
        L_0x000e:
            r2 = move-exception
            r0.close()     // Catch:{ all -> 0x0013 }
            goto L_0x0017
        L_0x0013:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x0017:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.forms.xfdf.XfdfObject.writeToFile(java.lang.String):void");
    }

    public void writeToFile(OutputStream os) throws TransformerException, ParserConfigurationException {
        new XfdfWriter(os).write(this);
    }
}
