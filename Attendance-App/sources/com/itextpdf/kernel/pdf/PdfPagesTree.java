package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class PdfPagesTree implements Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOGGER = LoggerFactory.getLogger((Class<?>) PdfPagesTree.class);
    private static final long serialVersionUID = 4189501363348296036L;
    private PdfDocument document;
    private boolean generated = false;
    private final int leafSize = 10;
    private List<PdfIndirectReference> pageRefs;
    private List<PdfPage> pages;
    private List<PdfPages> parents;
    private PdfPages root;

    public PdfPagesTree(PdfCatalog pdfCatalog) {
        this.document = pdfCatalog.getDocument();
        this.pageRefs = new ArrayList();
        this.parents = new ArrayList();
        this.pages = new ArrayList();
        if (((PdfDictionary) pdfCatalog.getPdfObject()).containsKey(PdfName.Pages)) {
            PdfDictionary pages2 = ((PdfDictionary) pdfCatalog.getPdfObject()).getAsDictionary(PdfName.Pages);
            if (pages2 != null) {
                PdfPages pdfPages = new PdfPages(0, Integer.MAX_VALUE, pages2, (PdfPages) null);
                this.root = pdfPages;
                this.parents.add(pdfPages);
                for (int i = 0; i < this.root.getCount(); i++) {
                    this.pageRefs.add((Object) null);
                    this.pages.add((Object) null);
                }
                return;
            }
            throw new PdfException(PdfException.InvalidPageStructurePagesPagesMustBePdfDictionary);
        }
        this.root = null;
        this.parents.add(new PdfPages(0, this.document));
    }

    public PdfPage getPage(int pageNum) {
        if (pageNum < 1 || pageNum > getNumberOfPages()) {
            throw new IndexOutOfBoundsException(MessageFormatUtil.format(PdfException.RequestedPageNumberIsOutOfBounds, Integer.valueOf(pageNum)));
        }
        int pageNum2 = pageNum - 1;
        PdfPage pdfPage = this.pages.get(pageNum2);
        if (pdfPage == null) {
            loadPage(pageNum2);
            if (this.pageRefs.get(pageNum2) != null) {
                int parentIndex = findPageParent(pageNum2);
                PdfObject pageObject = this.pageRefs.get(pageNum2).getRefersTo();
                if (pageObject instanceof PdfDictionary) {
                    pdfPage = this.document.getPageFactory().createPdfPage((PdfDictionary) pageObject);
                    pdfPage.parentPages = this.parents.get(parentIndex);
                } else {
                    LOGGER.error(MessageFormatUtil.format(LogMessageConstant.PAGE_TREE_IS_BROKEN_FAILED_TO_RETRIEVE_PAGE, Integer.valueOf(pageNum2 + 1)));
                }
            } else {
                LOGGER.error(MessageFormatUtil.format(LogMessageConstant.PAGE_TREE_IS_BROKEN_FAILED_TO_RETRIEVE_PAGE, Integer.valueOf(pageNum2 + 1)));
            }
            this.pages.set(pageNum2, pdfPage);
        }
        return pdfPage;
    }

    public PdfPage getPage(PdfDictionary pageDictionary) {
        int pageNum = getPageNumber(pageDictionary);
        if (pageNum > 0) {
            return getPage(pageNum);
        }
        return null;
    }

    public int getNumberOfPages() {
        return this.pageRefs.size();
    }

    public int getPageNumber(PdfPage page) {
        return this.pages.indexOf(page) + 1;
    }

    public int getPageNumber(PdfDictionary pageDictionary) {
        int pageNum = this.pageRefs.indexOf(pageDictionary.getIndirectReference());
        if (pageNum >= 0) {
            return pageNum + 1;
        }
        for (int i = 0; i < this.pageRefs.size(); i++) {
            if (this.pageRefs.get(i) == null) {
                loadPage(i);
            }
            if (this.pageRefs.get(i).equals(pageDictionary.getIndirectReference())) {
                return i + 1;
            }
        }
        return 0;
    }

    public void addPage(PdfPage pdfPage) {
        PdfPages pdfPages;
        if (this.root == null) {
            List<PdfPages> list = this.parents;
            pdfPages = list.get(list.size() - 1);
            if (pdfPages.getCount() % 10 == 0 && this.pageRefs.size() > 0) {
                pdfPages = new PdfPages(pdfPages.getFrom() + pdfPages.getCount(), this.document);
                this.parents.add(pdfPages);
            }
        } else if (this.pageRefs.size() == 0) {
            pdfPages = this.root;
        } else {
            loadPage(this.pageRefs.size() - 1);
            List<PdfPages> list2 = this.parents;
            pdfPages = list2.get(list2.size() - 1);
        }
        pdfPage.makeIndirect(this.document);
        pdfPages.addPage((PdfDictionary) pdfPage.getPdfObject());
        pdfPage.parentPages = pdfPages;
        this.pageRefs.add(((PdfDictionary) pdfPage.getPdfObject()).getIndirectReference());
        this.pages.add(pdfPage);
    }

    public void addPage(int index, PdfPage pdfPage) {
        int index2 = index - 1;
        if (index2 > this.pageRefs.size()) {
            throw new IndexOutOfBoundsException("index");
        } else if (index2 == this.pageRefs.size()) {
            addPage(pdfPage);
        } else {
            loadPage(index2);
            pdfPage.makeIndirect(this.document);
            int parentIndex = findPageParent(index2);
            PdfPages parentPages = this.parents.get(parentIndex);
            parentPages.addPage(index2, pdfPage);
            pdfPage.parentPages = parentPages;
            correctPdfPagesFromProperty(parentIndex + 1, 1);
            this.pageRefs.add(index2, ((PdfDictionary) pdfPage.getPdfObject()).getIndirectReference());
            this.pages.add(index2, pdfPage);
        }
    }

    public PdfPage removePage(int pageNum) {
        PdfPage pdfPage = getPage(pageNum);
        if (pdfPage.isFlushed()) {
            LOGGER.warn(LogMessageConstant.REMOVING_PAGE_HAS_ALREADY_BEEN_FLUSHED);
        }
        if (internalRemovePage(pageNum - 1)) {
            return pdfPage;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void releasePage(int pageNumber) {
        int pageNumber2 = pageNumber - 1;
        if (this.pageRefs.get(pageNumber2) != null && !this.pageRefs.get(pageNumber2).checkState(1) && !this.pageRefs.get(pageNumber2).checkState(8)) {
            if (this.pageRefs.get(pageNumber2).getOffset() > 0 || this.pageRefs.get(pageNumber2).getIndex() >= 0) {
                this.pages.set(pageNumber2, (Object) null);
            }
        }
    }

    /* access modifiers changed from: protected */
    public PdfObject generateTree() {
        if (this.pageRefs.size() == 0) {
            throw new PdfException(PdfException.DocumentHasNoPages);
        } else if (!this.generated) {
            if (this.root == null) {
                while (this.parents.size() != 1) {
                    List<PdfPages> nextParents = new ArrayList<>();
                    int dynamicLeafSize = 10;
                    PdfPages current = null;
                    int i = 0;
                    while (i < this.parents.size()) {
                        PdfPages pages2 = this.parents.get(i);
                        int pageCount = pages2.getCount();
                        if (i % dynamicLeafSize == 0) {
                            if (pageCount <= 1) {
                                dynamicLeafSize++;
                            } else {
                                current = new PdfPages(-1, this.document);
                                nextParents.add(current);
                                dynamicLeafSize = 10;
                            }
                        }
                        if (current != null) {
                            current.addPages(pages2);
                            i++;
                        } else {
                            throw new AssertionError();
                        }
                    }
                    this.parents = nextParents;
                }
                this.root = this.parents.get(0);
            }
            this.generated = true;
            return this.root.getPdfObject();
        } else {
            throw new PdfException(PdfException.PdfPagesTreeCouldBeGeneratedOnlyOnce);
        }
    }

    /* access modifiers changed from: protected */
    public void clearPageRefs() {
        this.pageRefs = null;
        this.pages = null;
    }

    /* access modifiers changed from: protected */
    public List<PdfPages> getParents() {
        return this.parents;
    }

    /* access modifiers changed from: protected */
    public PdfPages getRoot() {
        return this.root;
    }

    /* access modifiers changed from: protected */
    public PdfPages findPageParent(PdfPage pdfPage) {
        return this.parents.get(findPageParent(getPageNumber(pdfPage) - 1));
    }

    private void loadPage(int pageNum) {
        int from;
        if (this.pageRefs.get(pageNum) == null) {
            int parentIndex = findPageParent(pageNum);
            PdfPages parent = this.parents.get(parentIndex);
            PdfArray kids = parent.getKids();
            if (kids != null) {
                int kidsCount = parent.getCount();
                boolean findPdfPages = false;
                int i = 0;
                while (i < kids.size()) {
                    PdfDictionary page = kids.getAsDictionary(i);
                    if (page != null) {
                        PdfObject pageKids = page.get(PdfName.Kids);
                        if (pageKids != null) {
                            if (pageKids.isArray()) {
                                findPdfPages = true;
                            } else {
                                throw new PdfException(PdfException.InvalidPageStructure1).setMessageParams(Integer.valueOf(pageNum + 1));
                            }
                        }
                        if (this.document.getReader().isMemorySavingMode() && !findPdfPages && parent.getFrom() + i != pageNum) {
                            page.release();
                        }
                        i++;
                    } else {
                        throw new PdfException(PdfException.InvalidPageStructure1).setMessageParams(Integer.valueOf(pageNum + 1));
                    }
                }
                if (findPdfPages) {
                    List<PdfPages> newParents = new ArrayList<>(kids.size());
                    PdfPages lastPdfPages = null;
                    int i2 = 0;
                    while (i2 < kids.size() && kidsCount > 0) {
                        PdfDictionary pdfPagesObject = kids.getAsDictionary(i2);
                        if (pdfPagesObject.getAsArray(PdfName.Kids) == null) {
                            if (lastPdfPages == null) {
                                lastPdfPages = new PdfPages(parent.getFrom(), this.document, parent);
                                kids.set(i2, lastPdfPages.getPdfObject());
                                newParents.add(lastPdfPages);
                            } else {
                                kids.remove(i2);
                                i2--;
                            }
                            parent.decrementCount();
                            lastPdfPages.addPage(pdfPagesObject);
                            kidsCount--;
                        } else {
                            if (lastPdfPages == null) {
                                from = parent.getFrom();
                            } else {
                                from = lastPdfPages.getFrom() + lastPdfPages.getCount();
                            }
                            lastPdfPages = new PdfPages(from, kidsCount, pdfPagesObject, parent);
                            newParents.add(lastPdfPages);
                            kidsCount -= lastPdfPages.getCount();
                        }
                        i2++;
                    }
                    this.parents.remove(parentIndex);
                    for (int i3 = newParents.size() - 1; i3 >= 0; i3--) {
                        this.parents.add(parentIndex, newParents.get(i3));
                    }
                    loadPage(pageNum);
                    return;
                }
                int from2 = parent.getFrom();
                for (int i4 = 0; i4 < parent.getCount(); i4++) {
                    PdfObject kid = kids.get(i4, false);
                    if (kid instanceof PdfIndirectReference) {
                        this.pageRefs.set(from2 + i4, (PdfIndirectReference) kid);
                    } else {
                        this.pageRefs.set(from2 + i4, kid.getIndirectReference());
                    }
                }
                return;
            }
            throw new PdfException(PdfException.InvalidPageStructure1).setMessageParams(Integer.valueOf(pageNum + 1));
        }
    }

    private boolean internalRemovePage(int pageNum) {
        int parentIndex = findPageParent(pageNum);
        PdfPages pdfPages = this.parents.get(parentIndex);
        if (!pdfPages.removePage(pageNum)) {
            return false;
        }
        if (pdfPages.getCount() == 0) {
            this.parents.remove(parentIndex);
            pdfPages.removeFromParent();
            parentIndex--;
        }
        if (this.parents.size() == 0) {
            this.root = null;
            this.parents.add(new PdfPages(0, this.document));
        } else {
            correctPdfPagesFromProperty(parentIndex + 1, -1);
        }
        this.pageRefs.remove(pageNum);
        this.pages.remove(pageNum);
        return true;
    }

    private int findPageParent(int pageNum) {
        int low = 0;
        int high = this.parents.size() - 1;
        while (low != high) {
            int middle = ((low + high) + 1) / 2;
            if (this.parents.get(middle).compareTo(pageNum) > 0) {
                high = middle - 1;
            } else {
                low = middle;
            }
        }
        return low;
    }

    private void correctPdfPagesFromProperty(int index, int correction) {
        for (int i = index; i < this.parents.size(); i++) {
            if (this.parents.get(i) != null) {
                this.parents.get(i).correctFrom(correction);
            }
        }
    }
}
