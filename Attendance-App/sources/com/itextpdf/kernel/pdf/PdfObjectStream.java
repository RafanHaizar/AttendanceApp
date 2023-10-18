package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.p026io.source.ByteArrayOutputStream;
import java.io.OutputStream;

class PdfObjectStream extends PdfStream {
    public static final int MAX_OBJ_STREAM_SIZE = 200;
    private static final long serialVersionUID = -3513488307665597642L;
    protected PdfOutputStream indexStream;
    protected PdfNumber size;

    public PdfObjectStream(PdfDocument doc) {
        this(doc, new ByteArrayOutputStream());
        this.indexStream = new PdfOutputStream(new ByteArrayOutputStream());
    }

    PdfObjectStream(PdfObjectStream prev) {
        this(prev.getIndirectReference().getDocument(), prev.getOutputStream().getOutputStream());
        this.indexStream = new PdfOutputStream(prev.indexStream.getOutputStream());
        ((ByteArrayOutputStream) this.outputStream.getOutputStream()).reset();
        ((ByteArrayOutputStream) this.indexStream.getOutputStream()).reset();
        prev.releaseContent(true);
    }

    private PdfObjectStream(PdfDocument doc, OutputStream outputStream) {
        super(outputStream);
        this.size = new PdfNumber(0);
        makeIndirect(doc, doc.getXref().createNewIndirectReference(doc));
        getOutputStream().document = doc;
        put(PdfName.Type, PdfName.ObjStm);
        put(PdfName.f1357N, this.size);
        put(PdfName.First, new PdfNumber(0));
    }

    public void addObject(PdfObject object) {
        if (this.size.intValue() != 200) {
            PdfOutputStream outputStream = getOutputStream();
            ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) this.indexStream.writeInteger(object.getIndirectReference().getObjNumber())).writeSpace()).writeLong(outputStream.getCurrentPos())).writeSpace();
            outputStream.write(object);
            object.getIndirectReference().setObjStreamNumber(getIndirectReference().getObjNumber());
            object.getIndirectReference().setIndex((long) this.size.intValue());
            outputStream.writeSpace();
            this.size.increment();
            getAsNumber(PdfName.First).setValue((double) this.indexStream.getCurrentPos());
            return;
        }
        throw new PdfException(PdfException.PdfObjectStreamReachMaxSize);
    }

    public int getSize() {
        return this.size.intValue();
    }

    public PdfOutputStream getIndexStream() {
        return this.indexStream;
    }

    /* access modifiers changed from: protected */
    public void releaseContent() {
        releaseContent(false);
    }

    private void releaseContent(boolean close) {
        if (close) {
            this.outputStream = null;
            this.indexStream = null;
            super.releaseContent();
        }
    }
}
