package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.ProductInfo;
import com.itextpdf.kernel.VersionInfo;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.source.ByteUtils;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;
import org.slf4j.LoggerFactory;

public class PdfXrefTable implements Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int INITIAL_CAPACITY = 32;
    private static final int MAX_GENERATION = 65535;
    private static final byte[] freeXRefEntry = ByteUtils.getIsoBytes("f \n");
    private static final byte[] inUseXRefEntry = ByteUtils.getIsoBytes("n \n");
    private static final long serialVersionUID = 4171655392492002944L;
    private int count;
    private final TreeMap<Integer, PdfIndirectReference> freeReferencesLinkedList;
    private boolean readingCompleted;
    private PdfIndirectReference[] xref;

    public PdfXrefTable() {
        this(32);
    }

    public PdfXrefTable(int capacity) {
        this.count = 0;
        this.xref = new PdfIndirectReference[(capacity < 1 ? 32 : capacity)];
        this.freeReferencesLinkedList = new TreeMap<>();
        add((PdfIndirectReference) new PdfIndirectReference((PdfDocument) null, 0, 65535, 0).setState(2));
    }

    public PdfIndirectReference add(PdfIndirectReference reference) {
        if (reference == null) {
            return null;
        }
        int objNr = reference.getObjNumber();
        this.count = Math.max(this.count, objNr);
        ensureCount(objNr);
        this.xref[objNr] = reference;
        return reference;
    }

    public int size() {
        return this.count + 1;
    }

    public int getCountOfIndirectObjects() {
        int countOfIndirectObjects = 0;
        for (PdfIndirectReference ref : this.xref) {
            if (ref != null && !ref.isFree()) {
                countOfIndirectObjects++;
            }
        }
        return countOfIndirectObjects;
    }

    public PdfIndirectReference get(int index) {
        if (index > this.count) {
            return null;
        }
        return this.xref[index];
    }

    /* access modifiers changed from: package-private */
    public void markReadingCompleted() {
        this.readingCompleted = true;
    }

    /* access modifiers changed from: package-private */
    public boolean isReadingCompleted() {
        return this.readingCompleted;
    }

    /* access modifiers changed from: package-private */
    public void initFreeReferencesList(PdfDocument pdfDocument) {
        this.freeReferencesLinkedList.clear();
        this.xref[0].setState(2);
        TreeSet<Integer> freeReferences = new TreeSet<>();
        for (int i = 1; i < size(); i++) {
            PdfIndirectReference ref = this.xref[i];
            if (ref == null || ref.isFree()) {
                freeReferences.add(Integer.valueOf(i));
            }
        }
        PdfIndirectReference prevFreeRef = this.xref[0];
        while (!freeReferences.isEmpty()) {
            int currFreeRefObjNr = -1;
            if (prevFreeRef.getOffset() <= 2147483647L) {
                currFreeRefObjNr = (int) prevFreeRef.getOffset();
            }
            if (!freeReferences.contains(Integer.valueOf(currFreeRefObjNr)) || this.xref[currFreeRefObjNr] == null) {
                break;
            }
            this.freeReferencesLinkedList.put(Integer.valueOf(currFreeRefObjNr), prevFreeRef);
            prevFreeRef = this.xref[currFreeRefObjNr];
            freeReferences.remove(Integer.valueOf(currFreeRefObjNr));
        }
        while (!freeReferences.isEmpty()) {
            int next = freeReferences.pollFirst().intValue();
            PdfIndirectReference pdfIndirectReference = this.xref[next];
            if (pdfIndirectReference == null) {
                if (!pdfDocument.properties.appendMode) {
                    this.xref[next] = (PdfIndirectReference) new PdfIndirectReference(pdfDocument, next, 0).setState(2).setState(8);
                }
            } else if (pdfIndirectReference.getGenNumber() == 65535 && this.xref[next].getOffset() == 0) {
            }
            if (prevFreeRef.getOffset() != ((long) next)) {
                ((PdfIndirectReference) prevFreeRef.setState(8)).setOffset((long) next);
            }
            this.freeReferencesLinkedList.put(Integer.valueOf(next), prevFreeRef);
            prevFreeRef = this.xref[next];
        }
        if (prevFreeRef.getOffset() != 0) {
            ((PdfIndirectReference) prevFreeRef.setState(8)).setOffset(0);
        }
        this.freeReferencesLinkedList.put(0, prevFreeRef);
    }

    /* access modifiers changed from: package-private */
    public PdfIndirectReference createNewIndirectReference(PdfDocument document) {
        int i = this.count + 1;
        this.count = i;
        PdfIndirectReference reference = new PdfIndirectReference(document, i);
        add(reference);
        return (PdfIndirectReference) reference.setState(8);
    }

    /* access modifiers changed from: protected */
    public PdfIndirectReference createNextIndirectReference(PdfDocument document) {
        int i = this.count + 1;
        this.count = i;
        PdfIndirectReference reference = new PdfIndirectReference(document, i);
        add(reference);
        return (PdfIndirectReference) reference.setState(8);
    }

    /* access modifiers changed from: protected */
    public void freeReference(PdfIndirectReference reference) {
        if (!reference.isFree()) {
            Class<PdfXrefTable> cls = PdfXrefTable.class;
            if (reference.checkState(32)) {
                LoggerFactory.getLogger((Class<?>) cls).error(LogMessageConstant.INDIRECT_REFERENCE_USED_IN_FLUSHED_OBJECT_MADE_FREE);
            } else if (reference.checkState(1)) {
                LoggerFactory.getLogger((Class<?>) cls).error(LogMessageConstant.ALREADY_FLUSHED_INDIRECT_OBJECT_MADE_FREE);
            } else {
                reference.setState(2).setState(8);
                appendNewRefToFreeList(reference);
                if (reference.getGenNumber() < 65535) {
                    reference.genNr++;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void setCapacity(int capacity) {
        if (capacity > this.xref.length) {
            extendXref(capacity);
        }
    }

    /* access modifiers changed from: protected */
    public void writeXrefTableAndTrailer(PdfDocument document, PdfObject fileId, PdfObject crypto) throws IOException {
        List<Integer> sections;
        long xRefStmPos;
        List<Integer> sections2;
        List<Integer> sections3;
        int first;
        PdfXrefTable xrefTable;
        int len;
        PdfDocument pdfDocument = document;
        PdfObject pdfObject = fileId;
        PdfObject pdfObject2 = crypto;
        PdfWriter writer = document.getWriter();
        if (!pdfDocument.properties.appendMode) {
            for (int i = this.count; i > 0; i--) {
                PdfIndirectReference lastRef = this.xref[i];
                if (lastRef != null && !lastRef.isFree()) {
                    break;
                }
                removeFreeRefFromList(i);
                this.count--;
            }
        }
        PdfStream xrefStream = null;
        if (writer.isFullCompression()) {
            xrefStream = new PdfStream();
            xrefStream.makeIndirect(pdfDocument);
        }
        List<Integer> sections4 = createSections(pdfDocument, false);
        boolean noModifiedObjects = sections4.size() == 0 || (xrefStream != null && sections4.size() == 2 && sections4.get(0).intValue() == this.count && sections4.get(1).intValue() == 1);
        if (!pdfDocument.properties.appendMode || !noModifiedObjects) {
            pdfDocument.checkIsoConformance(this, IsoKey.XREF_TABLE);
            long startxref = writer.getCurrentPos();
            if (xrefStream != null) {
                xrefStream.put(PdfName.Type, PdfName.XRef);
                xrefStream.put(PdfName.f1341ID, pdfObject);
                if (pdfObject2 != null) {
                    xrefStream.put(PdfName.Encrypt, pdfObject2);
                }
                xrefStream.put(PdfName.Size, new PdfNumber(size()));
                int offsetSize = getOffsetSize(Math.max(startxref, (long) size()));
                boolean z = noModifiedObjects;
                xrefStream.put(PdfName.f1409W, new PdfArray((List<? extends PdfObject>) Arrays.asList(new PdfObject[]{new PdfNumber(1), new PdfNumber(offsetSize), new PdfNumber(2)})));
                xrefStream.put(PdfName.Info, document.getDocumentInfo().getPdfObject());
                xrefStream.put(PdfName.Root, document.getCatalog().getPdfObject());
                PdfArray index = new PdfArray();
                for (Integer section : sections4) {
                    index.add(new PdfNumber(section.intValue()));
                }
                if (pdfDocument.properties.appendMode && !pdfDocument.reader.hybridXref) {
                    xrefStream.put(PdfName.Prev, new PdfNumber((double) pdfDocument.reader.getLastXref()));
                }
                xrefStream.put(PdfName.Index, index);
                xrefStream.getIndirectReference().setOffset(startxref);
                PdfXrefTable xrefTable2 = document.getXref();
                int k = 0;
                while (k < sections4.size()) {
                    int first2 = sections4.get(k).intValue();
                    int len2 = sections4.get(k + 1).intValue();
                    PdfArray index2 = index;
                    int i2 = first2;
                    while (true) {
                        sections3 = sections4;
                        if (i2 >= first2 + len2) {
                            break;
                        }
                        PdfIndirectReference reference = xrefTable2.get(i2);
                        if (reference.isFree()) {
                            xrefTable = xrefTable2;
                            first = first2;
                            xrefStream.getOutputStream().write(0);
                            len = len2;
                            xrefStream.getOutputStream().write(reference.getOffset(), offsetSize);
                            xrefStream.getOutputStream().write(reference.getGenNumber(), 2);
                        } else {
                            xrefTable = xrefTable2;
                            first = first2;
                            len = len2;
                            if (reference.getObjStreamNumber() == 0) {
                                xrefStream.getOutputStream().write(1);
                                xrefStream.getOutputStream().write(reference.getOffset(), offsetSize);
                                xrefStream.getOutputStream().write(reference.getGenNumber(), 2);
                            } else {
                                xrefStream.getOutputStream().write(2);
                                xrefStream.getOutputStream().write(reference.getObjStreamNumber(), offsetSize);
                                xrefStream.getOutputStream().write(reference.getIndex(), 2);
                            }
                        }
                        i2++;
                        len2 = len;
                        sections4 = sections3;
                        xrefTable2 = xrefTable;
                        first2 = first;
                    }
                    int i3 = first2;
                    int i4 = len2;
                    k += 2;
                    sections4 = sections3;
                    index = index2;
                }
                PdfXrefTable pdfXrefTable = xrefTable2;
                sections = sections4;
                xrefStream.flush();
                xRefStmPos = startxref;
            } else {
                sections = sections4;
                boolean z2 = noModifiedObjects;
                xRefStmPos = -1;
            }
            boolean needsRegularXref = !writer.isFullCompression() || (pdfDocument.properties.appendMode && pdfDocument.reader.hybridXref);
            if (needsRegularXref) {
                startxref = writer.getCurrentPos();
                writer.writeString("xref\n");
                PdfXrefTable xrefTable3 = document.getXref();
                if (xRefStmPos != -1) {
                    sections2 = createSections(pdfDocument, true);
                } else {
                    sections2 = sections;
                }
                int k2 = 0;
                while (k2 < sections2.size()) {
                    int first3 = sections2.get(k2).intValue();
                    int len3 = sections2.get(k2 + 1).intValue();
                    PdfStream xrefStream2 = xrefStream;
                    boolean needsRegularXref2 = needsRegularXref;
                    ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) writer.writeInteger(first3)).writeSpace()).writeInteger(len3)).writeByte((byte) 10);
                    int i5 = first3;
                    while (i5 < first3 + len3) {
                        PdfIndirectReference reference2 = xrefTable3.get(i5);
                        PdfXrefTable xrefTable4 = xrefTable3;
                        int first4 = first3;
                        int len4 = len3;
                        StringBuilder off = new StringBuilder("0000000000").append(reference2.getOffset());
                        StringBuilder gen = new StringBuilder("00000").append(reference2.getGenNumber());
                        List<Integer> sections5 = sections2;
                        StringBuilder sb = off;
                        ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) writer.writeString(off.substring(off.length() - 10, off.length()))).writeSpace()).writeString(gen.substring(gen.length() - 5, gen.length()))).writeSpace();
                        if (reference2.isFree()) {
                            writer.writeBytes(freeXRefEntry);
                        } else {
                            writer.writeBytes(inUseXRefEntry);
                        }
                        i5++;
                        first3 = first4;
                        xrefTable3 = xrefTable4;
                        len3 = len4;
                        sections2 = sections5;
                    }
                    int i6 = first3;
                    int i7 = len3;
                    List<Integer> list = sections2;
                    k2 += 2;
                    needsRegularXref = needsRegularXref2;
                    xrefStream = xrefStream2;
                }
                boolean z3 = needsRegularXref;
                PdfXrefTable pdfXrefTable2 = xrefTable3;
                List<Integer> sections6 = sections2;
                PdfDictionary trailer = document.getTrailer();
                trailer.remove(PdfName.f1409W);
                trailer.remove(PdfName.Index);
                trailer.remove(PdfName.Type);
                trailer.remove(PdfName.Length);
                trailer.put(PdfName.Size, new PdfNumber(size()));
                trailer.put(PdfName.f1341ID, pdfObject);
                if (xRefStmPos != -1) {
                    trailer.put(PdfName.XRefStm, new PdfNumber((double) xRefStmPos));
                }
                if (pdfObject2 != null) {
                    trailer.put(PdfName.Encrypt, pdfObject2);
                }
                writer.writeString("trailer\n");
                if (pdfDocument.properties.appendMode) {
                    trailer.put(PdfName.Prev, new PdfNumber((double) pdfDocument.reader.getLastXref()));
                }
                writer.write((PdfObject) document.getTrailer());
                writer.write(10);
                List<Integer> list2 = sections6;
            } else {
                boolean z4 = needsRegularXref;
                List<Integer> list3 = sections;
            }
            writeKeyInfo(document);
            ((PdfOutputStream) ((PdfOutputStream) writer.writeString("startxref\n")).writeLong(startxref)).writeString("\n%%EOF\n");
            this.xref = null;
            this.freeReferencesLinkedList.clear();
            return;
        }
        this.xref = null;
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        for (int i = 1; i <= this.count; i++) {
            PdfIndirectReference pdfIndirectReference = this.xref[i];
            if (pdfIndirectReference == null || !pdfIndirectReference.isFree()) {
                this.xref[i] = null;
            }
        }
        this.count = 1;
    }

    private List<Integer> createSections(PdfDocument document, boolean dropObjectsFromObjectStream) {
        List<Integer> sections = new ArrayList<>();
        int first = 0;
        int len = 0;
        for (int i = 0; i < size(); i++) {
            PdfIndirectReference reference = this.xref[i];
            if (document.properties.appendMode && reference != null && (!reference.checkState(8) || (dropObjectsFromObjectStream && reference.getObjStreamNumber() != 0))) {
                reference = null;
            }
            if (reference == null) {
                if (len > 0) {
                    sections.add(Integer.valueOf(first));
                    sections.add(Integer.valueOf(len));
                }
                len = 0;
            } else if (len > 0) {
                len++;
            } else {
                first = i;
                len = 1;
            }
        }
        if (len > 0) {
            sections.add(Integer.valueOf(first));
            sections.add(Integer.valueOf(len));
        }
        return sections;
    }

    private int getOffsetSize(long startxref) {
        if (startxref < 0 || startxref >= 1099511627776L) {
            throw new AssertionError();
        }
        int size = 5;
        long mask = 1095216660480L;
        while (size > 1 && (mask & startxref) == 0) {
            mask >>= 8;
            size--;
        }
        return size;
    }

    protected static void writeKeyInfo(PdfDocument document) {
        PdfWriter writer = document.getWriter();
        FingerPrint fingerPrint = document.getFingerPrint();
        VersionInfo versionInfo = document.getVersionInfo();
        String k = versionInfo.getKey();
        if (k == null) {
            k = "iText";
        }
        writer.writeString(MessageFormatUtil.format("%{0}-{1}{2}\n", k, versionInfo.getRelease(), ""));
        for (ProductInfo productInfo : fingerPrint.getProducts()) {
            writer.writeString(MessageFormatUtil.format("%{0}\n", productInfo));
        }
    }

    private void appendNewRefToFreeList(PdfIndirectReference reference) {
        reference.setOffset(0);
        if (!this.freeReferencesLinkedList.isEmpty()) {
            PdfIndirectReference lastFreeRef = this.freeReferencesLinkedList.get(0);
            ((PdfIndirectReference) lastFreeRef.setState(8)).setOffset((long) reference.getObjNumber());
            this.freeReferencesLinkedList.put(Integer.valueOf(reference.getObjNumber()), lastFreeRef);
            this.freeReferencesLinkedList.put(0, reference);
            return;
        }
        throw new AssertionError();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: java.lang.Integer} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.itextpdf.kernel.pdf.PdfIndirectReference removeFreeRefFromList(int r7) {
        /*
            r6 = this;
            java.util.TreeMap<java.lang.Integer, com.itextpdf.kernel.pdf.PdfIndirectReference> r0 = r6.freeReferencesLinkedList
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x0093
            r0 = 0
            if (r7 != 0) goto L_0x000c
            return r0
        L_0x000c:
            if (r7 >= 0) goto L_0x0058
            r1 = 0
            java.util.TreeMap<java.lang.Integer, com.itextpdf.kernel.pdf.PdfIndirectReference> r2 = r6.freeReferencesLinkedList
            java.util.Set r2 = r2.entrySet()
            java.util.Iterator r2 = r2.iterator()
        L_0x0019:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0051
            java.lang.Object r3 = r2.next()
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3
            java.lang.Object r4 = r3.getKey()
            java.lang.Integer r4 = (java.lang.Integer) r4
            int r4 = r4.intValue()
            if (r4 <= 0) goto L_0x0019
            com.itextpdf.kernel.pdf.PdfIndirectReference[] r4 = r6.xref
            java.lang.Object r5 = r3.getKey()
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            r4 = r4[r5]
            int r4 = r4.getGenNumber()
            r5 = 65535(0xffff, float:9.1834E-41)
            if (r4 < r5) goto L_0x0049
            goto L_0x0019
        L_0x0049:
            java.lang.Object r2 = r3.getKey()
            r1 = r2
            java.lang.Integer r1 = (java.lang.Integer) r1
        L_0x0051:
            if (r1 != 0) goto L_0x0054
            return r0
        L_0x0054:
            int r7 = r1.intValue()
        L_0x0058:
            com.itextpdf.kernel.pdf.PdfIndirectReference[] r1 = r6.xref
            r1 = r1[r7]
            boolean r2 = r1.isFree()
            if (r2 != 0) goto L_0x0063
            return r0
        L_0x0063:
            java.util.TreeMap<java.lang.Integer, com.itextpdf.kernel.pdf.PdfIndirectReference> r0 = r6.freeReferencesLinkedList
            int r2 = r1.getObjNumber()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            java.lang.Object r0 = r0.remove(r2)
            com.itextpdf.kernel.pdf.PdfIndirectReference r0 = (com.itextpdf.kernel.pdf.PdfIndirectReference) r0
            if (r0 == 0) goto L_0x0092
            java.util.TreeMap<java.lang.Integer, com.itextpdf.kernel.pdf.PdfIndirectReference> r2 = r6.freeReferencesLinkedList
            long r3 = r1.getOffset()
            int r4 = (int) r3
            java.lang.Integer r3 = java.lang.Integer.valueOf(r4)
            r2.put(r3, r0)
            r2 = 8
            com.itextpdf.kernel.pdf.PdfObject r2 = r0.setState(r2)
            com.itextpdf.kernel.pdf.PdfIndirectReference r2 = (com.itextpdf.kernel.pdf.PdfIndirectReference) r2
            long r3 = r1.getOffset()
            r2.setOffset(r3)
        L_0x0092:
            return r1
        L_0x0093:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            goto L_0x009a
        L_0x0099:
            throw r0
        L_0x009a:
            goto L_0x0099
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.pdf.PdfXrefTable.removeFreeRefFromList(int):com.itextpdf.kernel.pdf.PdfIndirectReference");
    }

    private void ensureCount(int count2) {
        if (count2 >= this.xref.length) {
            extendXref(count2 << 1);
        }
    }

    private void extendXref(int capacity) {
        PdfIndirectReference[] newXref = new PdfIndirectReference[capacity];
        PdfIndirectReference[] pdfIndirectReferenceArr = this.xref;
        System.arraycopy(pdfIndirectReferenceArr, 0, newXref, 0, pdfIndirectReferenceArr.length);
        this.xref = newXref;
    }
}
