package com.itextpdf.kernel.pdf;

class PdfPages extends PdfObjectWrapper<PdfDictionary> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long serialVersionUID = 404629033132277362L;
    private PdfNumber count;
    private int from;
    private final PdfArray kids;
    private final PdfPages parent;

    public PdfPages(int from2, PdfDocument pdfDocument, PdfPages parent2) {
        super(new PdfDictionary());
        if (pdfDocument.getWriter() != null) {
            ((PdfDictionary) getPdfObject()).makeIndirect(pdfDocument);
        }
        setForbidRelease();
        this.from = from2;
        this.count = new PdfNumber(0);
        PdfArray pdfArray = new PdfArray();
        this.kids = pdfArray;
        this.parent = parent2;
        ((PdfDictionary) getPdfObject()).put(PdfName.Type, PdfName.Pages);
        ((PdfDictionary) getPdfObject()).put(PdfName.Kids, pdfArray);
        ((PdfDictionary) getPdfObject()).put(PdfName.Count, this.count);
        if (parent2 != null) {
            ((PdfDictionary) getPdfObject()).put(PdfName.Parent, parent2.getPdfObject());
        }
    }

    public PdfPages(int from2, PdfDocument pdfDocument) {
        this(from2, pdfDocument, (PdfPages) null);
    }

    public PdfPages(int from2, int maxCount, PdfDictionary pdfObject, PdfPages parent2) {
        super(pdfObject);
        setForbidRelease();
        this.from = from2;
        PdfNumber asNumber = pdfObject.getAsNumber(PdfName.Count);
        this.count = asNumber;
        this.parent = parent2;
        if (asNumber == null) {
            this.count = new PdfNumber(1);
            pdfObject.put(PdfName.Count, this.count);
        } else if (maxCount < asNumber.intValue()) {
            this.count.setValue(maxCount);
        }
        this.kids = pdfObject.getAsArray(PdfName.Kids);
        pdfObject.put(PdfName.Type, PdfName.Pages);
    }

    public void addPage(PdfDictionary page) {
        this.kids.add(page);
        incrementCount();
        page.put(PdfName.Parent, getPdfObject());
        page.setModified();
    }

    public boolean addPage(int index, PdfPage pdfPage) {
        int i = this.from;
        if (index < i || index > i + getCount()) {
            return false;
        }
        this.kids.add(index - this.from, pdfPage.getPdfObject());
        ((PdfDictionary) pdfPage.getPdfObject()).put(PdfName.Parent, getPdfObject());
        pdfPage.setModified();
        incrementCount();
        return true;
    }

    public boolean removePage(int pageNum) {
        int i = this.from;
        if (pageNum < i || pageNum >= i + getCount()) {
            return false;
        }
        decrementCount();
        this.kids.remove(pageNum - this.from);
        return true;
    }

    public void addPages(PdfPages pdfPages) {
        this.kids.add(pdfPages.getPdfObject());
        PdfNumber pdfNumber = this.count;
        pdfNumber.setValue(pdfNumber.intValue() + pdfPages.getCount());
        ((PdfDictionary) pdfPages.getPdfObject()).put(PdfName.Parent, getPdfObject());
        pdfPages.setModified();
        setModified();
    }

    public void removeFromParent() {
        if (this.parent == null) {
            return;
        }
        if (getCount() == 0) {
            this.parent.kids.remove((PdfObject) ((PdfDictionary) getPdfObject()).getIndirectReference());
            if (this.parent.getCount() == 0) {
                this.parent.removeFromParent();
                return;
            }
            return;
        }
        throw new AssertionError();
    }

    public int getFrom() {
        return this.from;
    }

    public int getCount() {
        return this.count.intValue();
    }

    public void correctFrom(int correction) {
        this.from += correction;
    }

    public PdfArray getKids() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Kids);
    }

    public PdfPages getParent() {
        return this.parent;
    }

    public void incrementCount() {
        this.count.increment();
        setModified();
        PdfPages pdfPages = this.parent;
        if (pdfPages != null) {
            pdfPages.incrementCount();
        }
    }

    public void decrementCount() {
        this.count.decrement();
        setModified();
        PdfPages pdfPages = this.parent;
        if (pdfPages != null) {
            pdfPages.decrementCount();
        }
    }

    public int compareTo(int index) {
        int i = this.from;
        if (index < i) {
            return 1;
        }
        if (index >= i + getCount()) {
            return -1;
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return true;
    }
}
