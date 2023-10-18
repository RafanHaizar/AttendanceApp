package com.itextpdf.kernel.utils;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.counter.event.IMetaInfo;
import com.itextpdf.kernel.pdf.DocumentProperties;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfOutline;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.p026io.source.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PdfSplitter {
    private IMetaInfo metaInfo;
    private PdfDocument pdfDocument;
    private boolean preserveOutlines;
    private boolean preserveTagged;

    public interface IDocumentReadyListener {
        void documentReady(PdfDocument pdfDocument, PageRange pageRange);
    }

    public PdfSplitter(PdfDocument pdfDocument2) {
        if (pdfDocument2.getWriter() == null) {
            this.pdfDocument = pdfDocument2;
            this.preserveTagged = true;
            this.preserveOutlines = true;
            return;
        }
        throw new PdfException(PdfException.CannotSplitDocumentThatIsBeingWritten);
    }

    public void setEventCountingMetaInfo(IMetaInfo metaInfo2) {
        this.metaInfo = metaInfo2;
    }

    public void setPreserveTagged(boolean preserveTagged2) {
        this.preserveTagged = preserveTagged2;
    }

    public void setPreserveOutlines(boolean preserveOutlines2) {
        this.preserveOutlines = preserveOutlines2;
    }

    public List<PdfDocument> splitBySize(long size) {
        List<PageRange> splitRanges = new ArrayList<>();
        int currentPage = 1;
        int numOfPages = this.pdfDocument.getNumberOfPages();
        while (currentPage <= numOfPages) {
            PageRange nextRange = getNextRange(currentPage, numOfPages, size);
            splitRanges.add(nextRange);
            List<Integer> allPages = nextRange.getQualifyingPageNums(numOfPages);
            currentPage = allPages.get(allPages.size() - 1).intValue() + 1;
        }
        return extractPageRanges(splitRanges);
    }

    public void splitByPageNumbers(List<Integer> pageNumbers, IDocumentReadyListener documentReady) {
        int currentPageNumber = 1;
        int ind = 0;
        while (ind <= pageNumbers.size()) {
            int nextPageNumber = ind == pageNumbers.size() ? this.pdfDocument.getNumberOfPages() + 1 : pageNumbers.get(ind).intValue();
            if (ind != 0 || nextPageNumber != 1) {
                PageRange currentPageRange = new PageRange().addPageSequence(currentPageNumber, nextPageNumber - 1);
                PdfDocument currentDocument = createPdfDocument(currentPageRange);
                this.pdfDocument.copyPagesTo(currentPageNumber, nextPageNumber - 1, currentDocument);
                documentReady.documentReady(currentDocument, currentPageRange);
                currentPageNumber = nextPageNumber;
            }
            ind++;
        }
    }

    public List<PdfDocument> splitByPageNumbers(List<Integer> pageNumbers) {
        final List<PdfDocument> splitDocuments = new ArrayList<>();
        splitByPageNumbers(pageNumbers, new IDocumentReadyListener() {
            public void documentReady(PdfDocument pdfDocument, PageRange pageRange) {
                splitDocuments.add(pdfDocument);
            }
        });
        return splitDocuments;
    }

    public void splitByPageCount(int pageCount, IDocumentReadyListener documentReady) {
        int startPage = 1;
        while (startPage <= this.pdfDocument.getNumberOfPages()) {
            int endPage = Math.min((startPage + pageCount) - 1, this.pdfDocument.getNumberOfPages());
            PageRange currentPageRange = new PageRange().addPageSequence(startPage, endPage);
            PdfDocument currentDocument = createPdfDocument(currentPageRange);
            this.pdfDocument.copyPagesTo(startPage, endPage, currentDocument);
            documentReady.documentReady(currentDocument, currentPageRange);
            startPage += pageCount;
        }
    }

    public List<PdfDocument> splitByPageCount(int pageCount) {
        final List<PdfDocument> splitDocuments = new ArrayList<>();
        splitByPageCount(pageCount, new IDocumentReadyListener() {
            public void documentReady(PdfDocument pdfDocument, PageRange pageRange) {
                splitDocuments.add(pdfDocument);
            }
        });
        return splitDocuments;
    }

    public List<PdfDocument> extractPageRanges(List<PageRange> pageRanges) {
        List<PdfDocument> splitDocuments = new ArrayList<>();
        for (PageRange currentPageRange : pageRanges) {
            PdfDocument currentPdfDocument = createPdfDocument(currentPageRange);
            splitDocuments.add(currentPdfDocument);
            PdfDocument pdfDocument2 = this.pdfDocument;
            pdfDocument2.copyPagesTo(currentPageRange.getQualifyingPageNums(pdfDocument2.getNumberOfPages()), currentPdfDocument);
        }
        return splitDocuments;
    }

    public PdfDocument extractPageRange(PageRange pageRange) {
        return extractPageRanges(Collections.singletonList(pageRange)).get(0);
    }

    public PdfDocument getPdfDocument() {
        return this.pdfDocument;
    }

    /* access modifiers changed from: protected */
    public PdfWriter getNextPdfWriter(PageRange documentPageRange) {
        return new PdfWriter((OutputStream) new ByteArrayOutputStream());
    }

    private PdfDocument createPdfDocument(PageRange currentPageRange) {
        PdfDocument newDocument = new PdfDocument(getNextPdfWriter(currentPageRange), new DocumentProperties().setEventCountingMetaInfo(this.metaInfo));
        if (this.pdfDocument.isTagged() && this.preserveTagged) {
            newDocument.setTagged();
        }
        if (this.pdfDocument.hasOutlines() && this.preserveOutlines) {
            newDocument.initializeOutlines();
        }
        return newDocument;
    }

    public List<PdfDocument> splitByOutlines(List<String> outlineTitles) {
        if (outlineTitles == null || outlineTitles.size() == 0) {
            return Collections.emptyList();
        }
        List<PdfDocument> documentList = new ArrayList<>(outlineTitles.size());
        for (String title : outlineTitles) {
            PdfDocument document = splitByOutline(title);
            if (document != null) {
                documentList.add(document);
            }
        }
        return documentList;
    }

    private PdfDocument splitByOutline(String outlineTitle) {
        int endPage;
        int startPage = -1;
        int endPage2 = -1;
        PdfDocument toDocument = createPdfDocument((PageRange) null);
        int size = this.pdfDocument.getNumberOfPages();
        for (int i = 1; i <= size; i++) {
            PdfPage pdfPage = this.pdfDocument.getPage(i);
            List<PdfOutline> outlineList = pdfPage.getOutlines(false);
            if (outlineList != null) {
                Iterator<PdfOutline> it = outlineList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    PdfOutline pdfOutline = it.next();
                    if (pdfOutline.getTitle().equals(outlineTitle)) {
                        startPage = this.pdfDocument.getPageNumber(pdfPage);
                        PdfOutline nextOutLine = getAbsoluteTreeNextOutline(pdfOutline);
                        if (nextOutLine != null) {
                            endPage = this.pdfDocument.getPageNumber(getPageByOutline(i, nextOutLine)) - 1;
                        } else {
                            endPage = size;
                        }
                        if (startPage - endPage == 1) {
                            endPage2 = startPage;
                        } else {
                            endPage2 = endPage;
                        }
                    }
                }
            }
        }
        if (startPage == -1 || endPage2 == -1) {
            return null;
        }
        this.pdfDocument.copyPagesTo(startPage, endPage2, toDocument);
        return toDocument;
    }

    private PdfPage getPageByOutline(int fromPage, PdfOutline outline) {
        int size = this.pdfDocument.getNumberOfPages();
        for (int i = fromPage; i <= size; i++) {
            PdfPage pdfPage = this.pdfDocument.getPage(i);
            List<PdfOutline> outlineList = pdfPage.getOutlines(false);
            if (outlineList != null) {
                for (PdfOutline pdfOutline : outlineList) {
                    if (pdfOutline.equals(outline)) {
                        return pdfPage;
                    }
                }
                continue;
            }
        }
        return null;
    }

    private PdfOutline getAbsoluteTreeNextOutline(PdfOutline outline) {
        PdfObject nextPdfObject = outline.getContent().get(PdfName.Next);
        PdfOutline nextPdfOutline = null;
        if (outline.getParent() != null && nextPdfObject != null) {
            Iterator<PdfOutline> it = outline.getParent().getAllChildren().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                PdfOutline pdfOutline = it.next();
                if (pdfOutline.getContent().getIndirectReference().equals(nextPdfObject.getIndirectReference())) {
                    nextPdfOutline = pdfOutline;
                    break;
                }
            }
        }
        if (nextPdfOutline != null || outline.getParent() == null) {
            return nextPdfOutline;
        }
        return getAbsoluteTreeNextOutline(outline.getParent());
    }

    private PageRange getNextRange(int startPage, int endPage, long size) {
        int currentPage;
        PdfResourceCounter counter = new PdfResourceCounter(this.pdfDocument.getTrailer());
        Map<Integer, PdfObject> resources = counter.getResources();
        long lengthWithoutXref = counter.getLength((Map<Integer, PdfObject>) null);
        int currentPage2 = startPage;
        boolean oversized = false;
        while (true) {
            currentPage = currentPage2 + 1;
            PdfResourceCounter counter2 = new PdfResourceCounter(this.pdfDocument.getPage(currentPage2).getPdfObject());
            lengthWithoutXref += counter2.getLength(resources);
            resources.putAll(counter2.getResources());
            if (xrefLength(resources.size()) + lengthWithoutXref > size) {
                oversized = true;
            }
            if (currentPage <= endPage && !oversized) {
                currentPage2 = currentPage;
            } else if (oversized && currentPage - 1 != startPage) {
                currentPage--;
            }
        }
        currentPage--;
        return new PageRange().addPageSequence(startPage, currentPage - 1);
    }

    private long xrefLength(int size) {
        return ((long) (size + 1)) * 20;
    }
}
