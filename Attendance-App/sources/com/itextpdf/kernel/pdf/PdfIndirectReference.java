package com.itextpdf.kernel.pdf;

import com.itextpdf.p026io.util.MessageFormatUtil;

public class PdfIndirectReference extends PdfObject implements Comparable<PdfIndirectReference> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int LENGTH_OF_INDIRECTS_CHAIN = 31;
    private static final long serialVersionUID = -8293603068792908601L;
    protected int genNr;
    protected final int objNr;
    protected int objectStreamNumber;
    protected long offsetOrIndex;
    protected PdfDocument pdfDocument;
    protected PdfObject refersTo;

    protected PdfIndirectReference(PdfDocument doc, int objNr2) {
        this(doc, objNr2, 0);
    }

    protected PdfIndirectReference(PdfDocument doc, int objNr2, int genNr2) {
        this.refersTo = null;
        this.objectStreamNumber = 0;
        this.offsetOrIndex = 0;
        this.pdfDocument = null;
        this.pdfDocument = doc;
        this.objNr = objNr2;
        this.genNr = genNr2;
    }

    protected PdfIndirectReference(PdfDocument doc, int objNr2, int genNr2, long offset) {
        this.refersTo = null;
        this.objectStreamNumber = 0;
        this.offsetOrIndex = 0;
        this.pdfDocument = null;
        this.pdfDocument = doc;
        this.objNr = objNr2;
        this.genNr = genNr2;
        this.offsetOrIndex = offset;
        if (offset < 0) {
            throw new AssertionError();
        }
    }

    public int getObjNumber() {
        return this.objNr;
    }

    public int getGenNumber() {
        return this.genNr;
    }

    public PdfObject getRefersTo() {
        return getRefersTo(true);
    }

    public PdfObject getRefersTo(boolean recursively) {
        if (!recursively) {
            if (this.refersTo == null && !checkState(1) && !checkState(8) && !checkState(2) && getReader() != null) {
                this.refersTo = getReader().readObject(this);
            }
            return this.refersTo;
        }
        PdfObject currentRefersTo = getRefersTo(false);
        for (int i = 0; i < 31 && (currentRefersTo instanceof PdfIndirectReference); i++) {
            currentRefersTo = ((PdfIndirectReference) currentRefersTo).getRefersTo(false);
        }
        return currentRefersTo;
    }

    /* access modifiers changed from: protected */
    public void setRefersTo(PdfObject refersTo2) {
        this.refersTo = refersTo2;
    }

    public int getObjStreamNumber() {
        return this.objectStreamNumber;
    }

    public long getOffset() {
        if (this.objectStreamNumber == 0) {
            return this.offsetOrIndex;
        }
        return -1;
    }

    public int getIndex() {
        if (this.objectStreamNumber == 0) {
            return -1;
        }
        return (int) this.offsetOrIndex;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PdfIndirectReference that = (PdfIndirectReference) o;
        if (this.objNr == that.objNr && this.genNr == that.genNr) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.objNr * 31) + this.genNr;
    }

    public int compareTo(PdfIndirectReference o) {
        int i = this.objNr;
        int i2 = o.objNr;
        if (i == i2) {
            int i3 = this.genNr;
            int i4 = o.genNr;
            if (i3 == i4) {
                return 0;
            }
            if (i3 > i4) {
                return 1;
            }
            return -1;
        } else if (i > i2) {
            return 1;
        } else {
            return -1;
        }
    }

    public byte getType() {
        return 5;
    }

    public PdfDocument getDocument() {
        return this.pdfDocument;
    }

    public void setFree() {
        getDocument().getXref().freeReference(this);
    }

    public boolean isFree() {
        return checkState(2);
    }

    public String toString() {
        StringBuilder states = new StringBuilder(" ");
        if (checkState(2)) {
            states.append("Free; ");
        }
        if (checkState(8)) {
            states.append("Modified; ");
        }
        if (checkState(32)) {
            states.append("MustBeFlushed; ");
        }
        if (checkState(4)) {
            states.append("Reading; ");
        }
        if (checkState(1)) {
            states.append("Flushed; ");
        }
        if (checkState(16)) {
            states.append("OriginalObjectStream; ");
        }
        if (checkState(128)) {
            states.append("ForbidRelease; ");
        }
        if (checkState(256)) {
            states.append("ReadOnly; ");
        }
        return MessageFormatUtil.format("{0} {1} R{2}", Integer.toString(getObjNumber()), Integer.toString(getGenNumber()), states.substring(0, states.length() - 1));
    }

    /* access modifiers changed from: protected */
    public PdfWriter getWriter() {
        if (getDocument() != null) {
            return getDocument().getWriter();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public PdfReader getReader() {
        if (getDocument() != null) {
            return getDocument().getReader();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public PdfObject newInstance() {
        return PdfNull.PDF_NULL;
    }

    /* access modifiers changed from: protected */
    public void copyContent(PdfObject from, PdfDocument document) {
    }

    /* access modifiers changed from: protected */
    public PdfObject setState(short state) {
        return super.setState(state);
    }

    /* access modifiers changed from: package-private */
    public void setObjStreamNumber(int objectStreamNumber2) {
        this.objectStreamNumber = objectStreamNumber2;
    }

    /* access modifiers changed from: package-private */
    public void setIndex(long index) {
        this.offsetOrIndex = index;
    }

    /* access modifiers changed from: package-private */
    public void setOffset(long offset) {
        this.offsetOrIndex = offset;
        this.objectStreamNumber = 0;
    }

    /* access modifiers changed from: package-private */
    public void fixOffset(long offset) {
        if (!isFree()) {
            this.offsetOrIndex = offset;
        }
    }
}
