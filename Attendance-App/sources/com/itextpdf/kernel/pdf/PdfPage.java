package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.pdf.tagutils.TagTreePointer;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.kernel.xmp.XMPException;
import com.itextpdf.kernel.xmp.XMPMeta;
import com.itextpdf.kernel.xmp.XMPMetaFactory;
import com.itextpdf.kernel.xmp.options.SerializeOptions;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.LoggerFactory;

public class PdfPage extends PdfObjectWrapper<PdfDictionary> {
    private static final List<PdfName> PAGE_EXCLUDED_KEYS;
    private static final List<PdfName> XOBJECT_EXCLUDED_KEYS;
    private static final long serialVersionUID = -952395541908379500L;
    private boolean ignorePageRotationForContent;
    private int mcid;
    private boolean pageRotationInverseMatrixWritten;
    PdfPages parentPages;
    private PdfResources resources;

    static {
        ArrayList arrayList = new ArrayList(Arrays.asList(new PdfName[]{PdfName.Parent, PdfName.Annots, PdfName.StructParents, PdfName.f1293B}));
        PAGE_EXCLUDED_KEYS = arrayList;
        ArrayList arrayList2 = new ArrayList(Arrays.asList(new PdfName[]{PdfName.MediaBox, PdfName.CropBox, PdfName.TrimBox, PdfName.Contents}));
        XOBJECT_EXCLUDED_KEYS = arrayList2;
        arrayList2.addAll(arrayList);
    }

    protected PdfPage(PdfDictionary pdfObject) {
        super(pdfObject);
        this.resources = null;
        this.mcid = -1;
        this.ignorePageRotationForContent = false;
        this.pageRotationInverseMatrixWritten = false;
        setForbidRelease();
        ensureObjectIsAddedToDocument(pdfObject);
    }

    protected PdfPage(PdfDocument pdfDocument, PageSize pageSize) {
        this((PdfDictionary) new PdfDictionary().makeIndirect(pdfDocument));
        ((PdfDictionary) getPdfObject()).put(PdfName.Contents, (PdfStream) new PdfStream().makeIndirect(pdfDocument));
        ((PdfDictionary) getPdfObject()).put(PdfName.Type, PdfName.Page);
        ((PdfDictionary) getPdfObject()).put(PdfName.MediaBox, new PdfArray((Rectangle) pageSize));
        ((PdfDictionary) getPdfObject()).put(PdfName.TrimBox, new PdfArray((Rectangle) pageSize));
        if (pdfDocument.isTagged()) {
            setTabOrder(PdfName.f1385S);
        }
    }

    protected PdfPage(PdfDocument pdfDocument) {
        this(pdfDocument, pdfDocument.getDefaultPageSize());
    }

    public Rectangle getPageSize() {
        return getMediaBox();
    }

    public Rectangle getPageSizeWithRotation() {
        PageSize rect = new PageSize(getPageSize());
        for (int rotation = getRotation(); rotation > 0; rotation -= 90) {
            rect = rect.rotate();
        }
        return rect;
    }

    /* JADX WARNING: type inference failed for: r2v3, types: [com.itextpdf.kernel.pdf.PdfObject] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getRotation() {
        /*
            r4 = this;
            com.itextpdf.kernel.pdf.PdfObject r0 = r4.getPdfObject()
            com.itextpdf.kernel.pdf.PdfDictionary r0 = (com.itextpdf.kernel.pdf.PdfDictionary) r0
            com.itextpdf.kernel.pdf.PdfName r1 = com.itextpdf.kernel.pdf.PdfName.Rotate
            com.itextpdf.kernel.pdf.PdfNumber r0 = r0.getAsNumber(r1)
            r1 = 0
            if (r0 != 0) goto L_0x001a
            com.itextpdf.kernel.pdf.PdfName r2 = com.itextpdf.kernel.pdf.PdfName.Rotate
            r3 = 8
            com.itextpdf.kernel.pdf.PdfObject r2 = r4.getInheritedValue((com.itextpdf.kernel.pdf.PdfName) r2, (int) r3)
            r0 = r2
            com.itextpdf.kernel.pdf.PdfNumber r0 = (com.itextpdf.kernel.pdf.PdfNumber) r0
        L_0x001a:
            if (r0 == 0) goto L_0x0020
            int r1 = r0.intValue()
        L_0x0020:
            int r1 = r1 % 360
            if (r1 >= 0) goto L_0x0027
            int r2 = r1 + 360
            goto L_0x0028
        L_0x0027:
            r2 = r1
        L_0x0028:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.pdf.PdfPage.getRotation():int");
    }

    public PdfPage setRotation(int degAngle) {
        put(PdfName.Rotate, new PdfNumber(degAngle));
        return this;
    }

    public PdfStream getContentStream(int index) {
        int count = getContentStreamCount();
        if (index >= count || index < 0) {
            throw new IndexOutOfBoundsException(MessageFormatUtil.format("Index: {0}, Size: {1}", Integer.valueOf(index), Integer.valueOf(count)));
        }
        PdfObject contents = ((PdfDictionary) getPdfObject()).get(PdfName.Contents);
        if (contents instanceof PdfStream) {
            return (PdfStream) contents;
        }
        if (contents instanceof PdfArray) {
            return ((PdfArray) contents).getAsStream(index);
        }
        return null;
    }

    public int getContentStreamCount() {
        PdfObject contents = ((PdfDictionary) getPdfObject()).get(PdfName.Contents);
        if (contents instanceof PdfStream) {
            return 1;
        }
        if (contents instanceof PdfArray) {
            return ((PdfArray) contents).size();
        }
        return 0;
    }

    public PdfStream getFirstContentStream() {
        if (getContentStreamCount() > 0) {
            return getContentStream(0);
        }
        return null;
    }

    public PdfStream getLastContentStream() {
        int count = getContentStreamCount();
        if (count > 0) {
            return getContentStream(count - 1);
        }
        return null;
    }

    public PdfStream newContentStreamBefore() {
        return newContentStream(true);
    }

    public PdfStream newContentStreamAfter() {
        return newContentStream(false);
    }

    public PdfResources getResources() {
        return getResources(true);
    }

    /* access modifiers changed from: package-private */
    public PdfResources getResources(boolean initResourcesField) {
        if (this.resources == null && initResourcesField) {
            initResources(true);
        }
        return this.resources;
    }

    /* JADX WARNING: type inference failed for: r2v6, types: [com.itextpdf.kernel.pdf.PdfObject] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.kernel.pdf.PdfDictionary initResources(boolean r5) {
        /*
            r4 = this;
            r0 = 0
            com.itextpdf.kernel.pdf.PdfObject r1 = r4.getPdfObject()
            com.itextpdf.kernel.pdf.PdfDictionary r1 = (com.itextpdf.kernel.pdf.PdfDictionary) r1
            com.itextpdf.kernel.pdf.PdfName r2 = com.itextpdf.kernel.pdf.PdfName.Resources
            com.itextpdf.kernel.pdf.PdfDictionary r1 = r1.getAsDictionary(r2)
            if (r1 != 0) goto L_0x001c
            com.itextpdf.kernel.pdf.PdfName r2 = com.itextpdf.kernel.pdf.PdfName.Resources
            r3 = 3
            com.itextpdf.kernel.pdf.PdfObject r2 = r4.getInheritedValue((com.itextpdf.kernel.pdf.PdfName) r2, (int) r3)
            r1 = r2
            com.itextpdf.kernel.pdf.PdfDictionary r1 = (com.itextpdf.kernel.pdf.PdfDictionary) r1
            if (r1 == 0) goto L_0x001c
            r0 = 1
        L_0x001c:
            if (r1 != 0) goto L_0x002f
            com.itextpdf.kernel.pdf.PdfDictionary r2 = new com.itextpdf.kernel.pdf.PdfDictionary
            r2.<init>()
            r1 = r2
            com.itextpdf.kernel.pdf.PdfObject r2 = r4.getPdfObject()
            com.itextpdf.kernel.pdf.PdfDictionary r2 = (com.itextpdf.kernel.pdf.PdfDictionary) r2
            com.itextpdf.kernel.pdf.PdfName r3 = com.itextpdf.kernel.pdf.PdfName.Resources
            r2.put(r3, r1)
        L_0x002f:
            if (r5 == 0) goto L_0x003b
            com.itextpdf.kernel.pdf.PdfResources r2 = new com.itextpdf.kernel.pdf.PdfResources
            r2.<init>(r1)
            r4.resources = r2
            r2.setReadOnly(r0)
        L_0x003b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.pdf.PdfPage.initResources(boolean):com.itextpdf.kernel.pdf.PdfDictionary");
    }

    public PdfPage setResources(PdfResources pdfResources) {
        put(PdfName.Resources, pdfResources.getPdfObject());
        this.resources = pdfResources;
        return this;
    }

    public PdfPage setXmpMetadata(byte[] xmpMetadata) throws IOException {
        PdfStream xmp = (PdfStream) new PdfStream().makeIndirect(getDocument());
        xmp.getOutputStream().write(xmpMetadata);
        xmp.put(PdfName.Type, PdfName.Metadata);
        xmp.put(PdfName.Subtype, PdfName.XML);
        put(PdfName.Metadata, xmp);
        return this;
    }

    public PdfPage setXmpMetadata(XMPMeta xmpMeta, SerializeOptions serializeOptions) throws XMPException, IOException {
        return setXmpMetadata(XMPMetaFactory.serializeToBuffer(xmpMeta, serializeOptions));
    }

    public PdfPage setXmpMetadata(XMPMeta xmpMeta) throws XMPException, IOException {
        SerializeOptions serializeOptions = new SerializeOptions();
        serializeOptions.setPadding(2000);
        return setXmpMetadata(xmpMeta, serializeOptions);
    }

    public PdfStream getXmpMetadata() {
        return ((PdfDictionary) getPdfObject()).getAsStream(PdfName.Metadata);
    }

    public PdfPage copyTo(PdfDocument toDocument) {
        return copyTo(toDocument, (IPdfPageExtraCopier) null);
    }

    public PdfPage copyTo(PdfDocument toDocument, IPdfPageExtraCopier copier) {
        PdfPage page = getDocument().getPageFactory().createPdfPage(((PdfDictionary) getPdfObject()).copyTo(toDocument, PAGE_EXCLUDED_KEYS, true));
        copyInheritedProperties(page, toDocument);
        for (PdfAnnotation annot : getAnnotations()) {
            if (annot.getSubtype().equals(PdfName.Link)) {
                getDocument().storeLinkAnnotation(page, (PdfLinkAnnotation) annot);
            } else {
                PdfAnnotation newAnnot = PdfAnnotation.makeAnnotation(((PdfDictionary) annot.getPdfObject()).copyTo(toDocument, Arrays.asList(new PdfName[]{PdfName.f1367P, PdfName.Parent}), true));
                if (PdfName.Widget.equals(annot.getSubtype())) {
                    rebuildFormFieldParent((PdfDictionary) annot.getPdfObject(), (PdfDictionary) newAnnot.getPdfObject(), toDocument);
                }
                page.addAnnotation(-1, newAnnot, false);
            }
        }
        if (copier != null) {
            copier.copy(this, page);
        } else if (!toDocument.getWriter().isUserWarnedAboutAcroFormCopying && getDocument().hasAcroForm()) {
            LoggerFactory.getLogger((Class<?>) PdfPage.class).warn(LogMessageConstant.SOURCE_DOCUMENT_HAS_ACROFORM_DICTIONARY);
            toDocument.getWriter().isUserWarnedAboutAcroFormCopying = true;
        }
        return page;
    }

    public PdfFormXObject copyAsFormXObject(PdfDocument toDocument) throws IOException {
        PdfFormXObject xObject = new PdfFormXObject(getCropBox());
        for (PdfName key : ((PdfDictionary) getPdfObject()).keySet()) {
            if (!XOBJECT_EXCLUDED_KEYS.contains(key)) {
                PdfObject obj = ((PdfDictionary) getPdfObject()).get(key);
                if (!((PdfStream) xObject.getPdfObject()).containsKey(key)) {
                    ((PdfStream) xObject.getPdfObject()).put(key, obj.copyTo(toDocument, false));
                }
            }
        }
        ((PdfStream) xObject.getPdfObject()).getOutputStream().write(getContentBytes());
        if (!((PdfStream) xObject.getPdfObject()).containsKey(PdfName.Resources)) {
            ((PdfStream) xObject.getPdfObject()).put(PdfName.Resources, ((PdfDictionary) getResources().getPdfObject()).copyTo(toDocument, true));
        }
        return xObject;
    }

    public PdfDocument getDocument() {
        if (((PdfDictionary) getPdfObject()).getIndirectReference() != null) {
            return ((PdfDictionary) getPdfObject()).getIndirectReference().getDocument();
        }
        return null;
    }

    public void flush() {
        flush(false);
    }

    public void flush(boolean flushResourcesContentStreams) {
        if (!isFlushed()) {
            getDocument().dispatchEvent(new PdfDocumentEvent(PdfDocumentEvent.END_PAGE, this));
            if (getDocument().isTagged() && !getDocument().getStructTreeRoot().isFlushed()) {
                tryFlushPageTags();
            }
            PdfResources pdfResources = this.resources;
            if (pdfResources == null) {
                initResources(false);
            } else if (pdfResources.isModified() && !this.resources.isReadOnly()) {
                put(PdfName.Resources, this.resources.getPdfObject());
            }
            if (flushResourcesContentStreams) {
                getDocument().checkIsoConformance(this, IsoKey.PAGE);
                flushResourcesContentStreams();
            }
            PdfArray annots = getAnnots(false);
            if (annots != null && !annots.isFlushed()) {
                for (int i = 0; i < annots.size(); i++) {
                    PdfObject a = annots.get(i);
                    if (a != null) {
                        a.makeIndirect(getDocument()).flush();
                    }
                }
            }
            PdfStream thumb = ((PdfDictionary) getPdfObject()).getAsStream(PdfName.Thumb);
            if (thumb != null) {
                thumb.flush();
            }
            PdfObject contentsObj = ((PdfDictionary) getPdfObject()).get(PdfName.Contents);
            if (contentsObj != null && !contentsObj.isFlushed()) {
                int contentStreamCount = getContentStreamCount();
                for (int i2 = 0; i2 < contentStreamCount; i2++) {
                    PdfStream contentStream = getContentStream(i2);
                    if (contentStream != null) {
                        contentStream.flush(false);
                    }
                }
            }
            releaseInstanceFields();
            super.flush();
        }
    }

    /* JADX WARNING: type inference failed for: r2v7, types: [com.itextpdf.kernel.pdf.PdfObject] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.kernel.geom.Rectangle getMediaBox() {
        /*
            r12 = this;
            com.itextpdf.kernel.pdf.PdfObject r0 = r12.getPdfObject()
            com.itextpdf.kernel.pdf.PdfDictionary r0 = (com.itextpdf.kernel.pdf.PdfDictionary) r0
            com.itextpdf.kernel.pdf.PdfName r1 = com.itextpdf.kernel.pdf.PdfName.MediaBox
            com.itextpdf.kernel.pdf.PdfArray r0 = r0.getAsArray(r1)
            r1 = 1
            if (r0 != 0) goto L_0x0018
            com.itextpdf.kernel.pdf.PdfName r2 = com.itextpdf.kernel.pdf.PdfName.MediaBox
            com.itextpdf.kernel.pdf.PdfObject r2 = r12.getInheritedValue((com.itextpdf.kernel.pdf.PdfName) r2, (int) r1)
            r0 = r2
            com.itextpdf.kernel.pdf.PdfArray r0 = (com.itextpdf.kernel.pdf.PdfArray) r0
        L_0x0018:
            if (r0 == 0) goto L_0x00b7
            int r2 = r0.size()
            r3 = r2
            r4 = 0
            r5 = 4
            if (r2 == r5) goto L_0x005d
            if (r3 <= r5) goto L_0x0042
            java.lang.Class<com.itextpdf.kernel.pdf.PdfPage> r2 = com.itextpdf.kernel.pdf.PdfPage.class
            org.slf4j.Logger r2 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r2)
            boolean r6 = r2.isErrorEnabled()
            if (r6 == 0) goto L_0x0042
            java.lang.Object[] r6 = new java.lang.Object[r1]
            java.lang.Integer r7 = java.lang.Integer.valueOf(r3)
            r6[r4] = r7
            java.lang.String r7 = "Wrong media box size: {0}. The arguments beyond the 4th will be ignored"
            java.lang.String r6 = com.itextpdf.p026io.util.MessageFormatUtil.format(r7, r6)
            r2.error(r6)
        L_0x0042:
            if (r3 < r5) goto L_0x0045
            goto L_0x005d
        L_0x0045:
            com.itextpdf.kernel.PdfException r2 = new com.itextpdf.kernel.PdfException
            java.lang.String r5 = "Wrong media box size: {0}. Need at least 4 arguments"
            r2.<init>((java.lang.String) r5)
            java.lang.Object[] r1 = new java.lang.Object[r1]
            int r5 = r0.size()
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r1[r4] = r5
            com.itextpdf.kernel.PdfException r1 = r2.setMessageParams(r1)
            throw r1
        L_0x005d:
            com.itextpdf.kernel.pdf.PdfNumber r2 = r0.getAsNumber(r4)
            com.itextpdf.kernel.pdf.PdfNumber r1 = r0.getAsNumber(r1)
            r4 = 2
            com.itextpdf.kernel.pdf.PdfNumber r4 = r0.getAsNumber(r4)
            r5 = 3
            com.itextpdf.kernel.pdf.PdfNumber r5 = r0.getAsNumber(r5)
            if (r2 == 0) goto L_0x00af
            if (r1 == 0) goto L_0x00af
            if (r4 == 0) goto L_0x00af
            if (r5 == 0) goto L_0x00af
            com.itextpdf.kernel.geom.Rectangle r6 = new com.itextpdf.kernel.geom.Rectangle
            float r7 = r2.floatValue()
            float r8 = r4.floatValue()
            float r7 = java.lang.Math.min(r7, r8)
            float r8 = r1.floatValue()
            float r9 = r5.floatValue()
            float r8 = java.lang.Math.min(r8, r9)
            float r9 = r4.floatValue()
            float r10 = r2.floatValue()
            float r9 = r9 - r10
            float r9 = java.lang.Math.abs(r9)
            float r10 = r5.floatValue()
            float r11 = r1.floatValue()
            float r10 = r10 - r11
            float r10 = java.lang.Math.abs(r10)
            r6.<init>(r7, r8, r9, r10)
            return r6
        L_0x00af:
            com.itextpdf.kernel.PdfException r6 = new com.itextpdf.kernel.PdfException
            java.lang.String r7 = "Tne media box object has incorrect values."
            r6.<init>((java.lang.String) r7)
            throw r6
        L_0x00b7:
            com.itextpdf.kernel.PdfException r1 = new com.itextpdf.kernel.PdfException
            java.lang.String r2 = "Invalid PDF. There is no media box attribute for page or its parents."
            r1.<init>((java.lang.String) r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.pdf.PdfPage.getMediaBox():com.itextpdf.kernel.geom.Rectangle");
    }

    public PdfPage setMediaBox(Rectangle rectangle) {
        put(PdfName.MediaBox, new PdfArray(rectangle));
        return this;
    }

    /* JADX WARNING: type inference failed for: r1v3, types: [com.itextpdf.kernel.pdf.PdfObject] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.kernel.geom.Rectangle getCropBox() {
        /*
            r3 = this;
            com.itextpdf.kernel.pdf.PdfObject r0 = r3.getPdfObject()
            com.itextpdf.kernel.pdf.PdfDictionary r0 = (com.itextpdf.kernel.pdf.PdfDictionary) r0
            com.itextpdf.kernel.pdf.PdfName r1 = com.itextpdf.kernel.pdf.PdfName.CropBox
            com.itextpdf.kernel.pdf.PdfArray r0 = r0.getAsArray(r1)
            if (r0 != 0) goto L_0x001f
            com.itextpdf.kernel.pdf.PdfName r1 = com.itextpdf.kernel.pdf.PdfName.CropBox
            r2 = 1
            com.itextpdf.kernel.pdf.PdfObject r1 = r3.getInheritedValue((com.itextpdf.kernel.pdf.PdfName) r1, (int) r2)
            r0 = r1
            com.itextpdf.kernel.pdf.PdfArray r0 = (com.itextpdf.kernel.pdf.PdfArray) r0
            if (r0 != 0) goto L_0x001f
            com.itextpdf.kernel.geom.Rectangle r1 = r3.getMediaBox()
            return r1
        L_0x001f:
            com.itextpdf.kernel.geom.Rectangle r1 = r0.toRectangle()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.pdf.PdfPage.getCropBox():com.itextpdf.kernel.geom.Rectangle");
    }

    public PdfPage setCropBox(Rectangle rectangle) {
        put(PdfName.CropBox, new PdfArray(rectangle));
        return this;
    }

    public PdfPage setBleedBox(Rectangle rectangle) {
        put(PdfName.BleedBox, new PdfArray(rectangle));
        return this;
    }

    public Rectangle getBleedBox() {
        Rectangle bleedBox = ((PdfDictionary) getPdfObject()).getAsRectangle(PdfName.BleedBox);
        return bleedBox == null ? getCropBox() : bleedBox;
    }

    public PdfPage setArtBox(Rectangle rectangle) {
        if (((PdfDictionary) getPdfObject()).getAsRectangle(PdfName.TrimBox) != null) {
            ((PdfDictionary) getPdfObject()).remove(PdfName.TrimBox);
            LoggerFactory.getLogger((Class<?>) PdfPage.class).warn(LogMessageConstant.ONLY_ONE_OF_ARTBOX_OR_TRIMBOX_CAN_EXIST_IN_THE_PAGE);
        }
        put(PdfName.ArtBox, new PdfArray(rectangle));
        return this;
    }

    public Rectangle getArtBox() {
        Rectangle artBox = ((PdfDictionary) getPdfObject()).getAsRectangle(PdfName.ArtBox);
        return artBox == null ? getCropBox() : artBox;
    }

    public PdfPage setTrimBox(Rectangle rectangle) {
        if (((PdfDictionary) getPdfObject()).getAsRectangle(PdfName.ArtBox) != null) {
            ((PdfDictionary) getPdfObject()).remove(PdfName.ArtBox);
            LoggerFactory.getLogger((Class<?>) PdfPage.class).warn(LogMessageConstant.ONLY_ONE_OF_ARTBOX_OR_TRIMBOX_CAN_EXIST_IN_THE_PAGE);
        }
        put(PdfName.TrimBox, new PdfArray(rectangle));
        return this;
    }

    public Rectangle getTrimBox() {
        Rectangle trimBox = ((PdfDictionary) getPdfObject()).getAsRectangle(PdfName.TrimBox);
        return trimBox == null ? getCropBox() : trimBox;
    }

    public byte[] getContentBytes() {
        try {
            MemoryLimitsAwareHandler handler = getDocument().memoryLimitsAwareHandler;
            long usedMemory = handler == null ? -1 : handler.getAllMemoryUsedForDecompression();
            MemoryLimitsAwareOutputStream baos = new MemoryLimitsAwareOutputStream();
            int streamCount = getContentStreamCount();
            for (int i = 0; i < streamCount; i++) {
                byte[] streamBytes = getStreamBytes(i);
                if (handler != null && usedMemory < handler.getAllMemoryUsedForDecompression()) {
                    baos.setMaxStreamSize(handler.getMaxSizeOfSingleDecompressedPdfStream());
                }
                baos.write(streamBytes);
                if (streamBytes.length != 0 && !Character.isWhitespace((char) streamBytes[streamBytes.length - 1])) {
                    baos.write(10);
                }
            }
            return baos.toByteArray();
        } catch (IOException ioe) {
            throw new PdfException(PdfException.CannotGetContentBytes, ioe, this);
        }
    }

    public byte[] getStreamBytes(int index) {
        return getContentStream(index).getBytes();
    }

    public int getNextMcid() {
        if (getDocument().isTagged()) {
            if (this.mcid == -1) {
                this.mcid = getDocument().getStructTreeRoot().getNextMcidForPage(this);
            }
            int i = this.mcid;
            this.mcid = i + 1;
            return i;
        }
        throw new PdfException(PdfException.MustBeATaggedDocument);
    }

    public int getStructParentIndex() {
        if (((PdfDictionary) getPdfObject()).getAsNumber(PdfName.StructParents) != null) {
            return ((PdfDictionary) getPdfObject()).getAsNumber(PdfName.StructParents).intValue();
        }
        return -1;
    }

    public PdfPage setAdditionalAction(PdfName key, PdfAction action) {
        PdfAction.setAdditionalAction(this, key, action);
        return this;
    }

    public List<PdfAnnotation> getAnnotations() {
        PdfAnnotation annotation;
        List<PdfAnnotation> annotations = new ArrayList<>();
        PdfArray annots = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Annots);
        if (annots != null) {
            for (int i = 0; i < annots.size(); i++) {
                PdfDictionary annot = annots.getAsDictionary(i);
                if (!(annot == null || (annotation = PdfAnnotation.makeAnnotation(annot)) == null)) {
                    boolean hasBeenNotModified = annot.getIndirectReference() != null && !annot.getIndirectReference().checkState(8);
                    annotations.add(annotation.setPage(this));
                    if (hasBeenNotModified) {
                        annot.getIndirectReference().clearState(8);
                        annot.clearState(128);
                    }
                }
            }
        }
        return annotations;
    }

    public boolean containsAnnotation(PdfAnnotation annotation) {
        for (PdfAnnotation a : getAnnotations()) {
            if (((PdfDictionary) a.getPdfObject()).equals(annotation.getPdfObject())) {
                return true;
            }
        }
        return false;
    }

    public PdfPage addAnnotation(PdfAnnotation annotation) {
        return addAnnotation(-1, annotation, true);
    }

    public PdfPage addAnnotation(int index, PdfAnnotation annotation, boolean tagAnnotation) {
        if (getDocument().isTagged()) {
            if (tagAnnotation) {
                TagTreePointer tagPointer = getDocument().getTagStructureContext().getAutoTaggingPointer();
                PdfPage prevPage = tagPointer.getCurrentPage();
                tagPointer.setPageForTagging(this).addAnnotationTag(annotation);
                if (prevPage != null) {
                    tagPointer.setPageForTagging(prevPage);
                }
            }
            if (getTabOrder() == null) {
                setTabOrder(PdfName.f1385S);
            }
        }
        PdfArray annots = getAnnots(true);
        if (index == -1) {
            annots.add(annotation.setPage(this).getPdfObject());
        } else {
            annots.add(index, annotation.setPage(this).getPdfObject());
        }
        if (annots.getIndirectReference() == null) {
            setModified();
        } else {
            annots.setModified();
        }
        return this;
    }

    public PdfPage removeAnnotation(PdfAnnotation annotation) {
        TagTreePointer tagPointer;
        boolean standardAnnotTagRole = false;
        PdfArray annots = getAnnots(false);
        if (annots != null) {
            annots.remove(annotation.getPdfObject());
            if (annots.isEmpty()) {
                ((PdfDictionary) getPdfObject()).remove(PdfName.Annots);
                setModified();
            } else if (annots.getIndirectReference() == null) {
                setModified();
            }
        }
        if (getDocument().isTagged() && (tagPointer = getDocument().getTagStructureContext().removeAnnotationTag(annotation)) != null) {
            if (StandardRoles.ANNOT.equals(tagPointer.getRole()) || StandardRoles.FORM.equals(tagPointer.getRole())) {
                standardAnnotTagRole = true;
            }
            if (tagPointer.getKidsRoles().size() == 0 && standardAnnotTagRole) {
                tagPointer.removeTag();
            }
        }
        return this;
    }

    public int getAnnotsSize() {
        PdfArray annots = getAnnots(false);
        if (annots == null) {
            return 0;
        }
        return annots.size();
    }

    public List<PdfOutline> getOutlines(boolean updateOutlines) {
        getDocument().getOutlines(updateOutlines);
        return getDocument().getCatalog().getPagesWithOutlines().get(getPdfObject());
    }

    public boolean isIgnorePageRotationForContent() {
        return this.ignorePageRotationForContent;
    }

    public PdfPage setIgnorePageRotationForContent(boolean ignorePageRotationForContent2) {
        this.ignorePageRotationForContent = ignorePageRotationForContent2;
        return this;
    }

    public PdfPage setPageLabel(PageLabelNumberingStyle numberingStyle, String labelPrefix) {
        return setPageLabel(numberingStyle, labelPrefix, 1);
    }

    public PdfPage setPageLabel(PageLabelNumberingStyle numberingStyle, String labelPrefix, int firstPage) {
        if (firstPage >= 1) {
            PdfDictionary pageLabel = new PdfDictionary();
            if (numberingStyle != null) {
                switch (C14271.$SwitchMap$com$itextpdf$kernel$pdf$PageLabelNumberingStyle[numberingStyle.ordinal()]) {
                    case 1:
                        pageLabel.put(PdfName.f1385S, PdfName.f1312D);
                        break;
                    case 2:
                        pageLabel.put(PdfName.f1385S, PdfName.f1376R);
                        break;
                    case 3:
                        pageLabel.put(PdfName.f1385S, PdfName.f1419r);
                        break;
                    case 4:
                        pageLabel.put(PdfName.f1385S, PdfName.f1287A);
                        break;
                    case 5:
                        pageLabel.put(PdfName.f1385S, PdfName.f1416a);
                        break;
                }
            }
            if (labelPrefix != null) {
                pageLabel.put(PdfName.f1367P, new PdfString(labelPrefix));
            }
            if (firstPage != 1) {
                pageLabel.put(PdfName.f1389St, new PdfNumber(firstPage));
            }
            getDocument().getCatalog().getPageLabelsTree(true).addEntry(getDocument().getPageNumber(this) - 1, pageLabel);
            return this;
        }
        throw new PdfException(PdfException.InAPageLabelThePageNumbersMustBeGreaterOrEqualTo1);
    }

    /* renamed from: com.itextpdf.kernel.pdf.PdfPage$1 */
    static /* synthetic */ class C14271 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$kernel$pdf$PageLabelNumberingStyle;

        static {
            int[] iArr = new int[PageLabelNumberingStyle.values().length];
            $SwitchMap$com$itextpdf$kernel$pdf$PageLabelNumberingStyle = iArr;
            try {
                iArr[PageLabelNumberingStyle.DECIMAL_ARABIC_NUMERALS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$itextpdf$kernel$pdf$PageLabelNumberingStyle[PageLabelNumberingStyle.UPPERCASE_ROMAN_NUMERALS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$itextpdf$kernel$pdf$PageLabelNumberingStyle[PageLabelNumberingStyle.LOWERCASE_ROMAN_NUMERALS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$itextpdf$kernel$pdf$PageLabelNumberingStyle[PageLabelNumberingStyle.UPPERCASE_LETTERS.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$itextpdf$kernel$pdf$PageLabelNumberingStyle[PageLabelNumberingStyle.LOWERCASE_LETTERS.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public PdfPage setTabOrder(PdfName tabOrder) {
        put(PdfName.Tabs, tabOrder);
        return this;
    }

    public PdfName getTabOrder() {
        return ((PdfDictionary) getPdfObject()).getAsName(PdfName.Tabs);
    }

    public PdfPage setThumbnailImage(PdfImageXObject thumb) {
        return put(PdfName.Thumb, thumb.getPdfObject());
    }

    public PdfImageXObject getThumbnailImage() {
        PdfStream thumbStream = ((PdfDictionary) getPdfObject()).getAsStream(PdfName.Thumb);
        if (thumbStream != null) {
            return new PdfImageXObject(thumbStream);
        }
        return null;
    }

    public PdfPage addOutputIntent(PdfOutputIntent outputIntent) {
        if (outputIntent == null) {
            return this;
        }
        PdfArray outputIntents = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.OutputIntents);
        if (outputIntents == null) {
            outputIntents = new PdfArray();
            put(PdfName.OutputIntents, outputIntents);
        }
        outputIntents.add(outputIntent.getPdfObject());
        return this;
    }

    public PdfPage put(PdfName key, PdfObject value) {
        ((PdfDictionary) getPdfObject()).put(key, value);
        setModified();
        return this;
    }

    public boolean isPageRotationInverseMatrixWritten() {
        return this.pageRotationInverseMatrixWritten;
    }

    public void setPageRotationInverseMatrixWritten() {
        this.pageRotationInverseMatrixWritten = true;
    }

    public void addAssociatedFile(String description, PdfFileSpec fs) {
        if (((PdfDictionary) fs.getPdfObject()).get(PdfName.AFRelationship) == null) {
            LoggerFactory.getLogger((Class<?>) PdfPage.class).error(LogMessageConstant.ASSOCIATED_FILE_SPEC_SHALL_INCLUDE_AFRELATIONSHIP);
        }
        if (description != null) {
            getDocument().getCatalog().addNameToNameTree(description, fs.getPdfObject(), PdfName.EmbeddedFiles);
        }
        PdfArray afArray = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1289AF);
        if (afArray == null) {
            afArray = new PdfArray();
            put(PdfName.f1289AF, afArray);
        }
        afArray.add(fs.getPdfObject());
    }

    public void addAssociatedFile(PdfFileSpec fs) {
        addAssociatedFile((String) null, fs);
    }

    public PdfArray getAssociatedFiles(boolean create) {
        PdfArray afArray = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.f1289AF);
        if (afArray != null || !create) {
            return afArray;
        }
        PdfArray afArray2 = new PdfArray();
        put(PdfName.f1289AF, afArray2);
        return afArray2;
    }

    /* access modifiers changed from: package-private */
    public void tryFlushPageTags() {
        try {
            if (!getDocument().isClosing) {
                getDocument().getTagStructureContext().flushPageTags(this);
            }
            getDocument().getStructTreeRoot().savePageStructParentIndexIfNeeded(this);
        } catch (Exception ex) {
            throw new PdfException(PdfException.TagStructureFlushingFailedItMightBeCorrupted, (Throwable) ex);
        }
    }

    /* access modifiers changed from: package-private */
    public void releaseInstanceFields() {
        this.resources = null;
        this.parentPages = null;
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return true;
    }

    private PdfArray getAnnots(boolean create) {
        PdfArray annots = ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Annots);
        if (annots != null || !create) {
            return annots;
        }
        PdfArray annots2 = new PdfArray();
        put(PdfName.Annots, annots2);
        return annots2;
    }

    private PdfObject getInheritedValue(PdfName pdfName, int type) {
        if (this.parentPages == null) {
            this.parentPages = getDocument().getCatalog().getPageTree().findPageParent(this);
        }
        PdfObject val = getInheritedValue(this.parentPages, pdfName);
        if (val == null || val.getType() != type) {
            return null;
        }
        return val;
    }

    private static PdfObject getInheritedValue(PdfPages parentPages2, PdfName pdfName) {
        if (parentPages2 == null) {
            return null;
        }
        PdfObject value = ((PdfDictionary) parentPages2.getPdfObject()).get(pdfName);
        if (value != null) {
            return value;
        }
        return getInheritedValue(parentPages2.getParent(), pdfName);
    }

    private PdfStream newContentStream(boolean before) {
        PdfArray array;
        PdfObject contents = ((PdfDictionary) getPdfObject()).get(PdfName.Contents);
        if (contents instanceof PdfStream) {
            array = new PdfArray();
            if (contents.getIndirectReference() != null) {
                array.add(contents.getIndirectReference());
            } else {
                array.add(contents);
            }
            put(PdfName.Contents, array);
        } else if (contents instanceof PdfArray) {
            array = (PdfArray) contents;
        } else {
            array = null;
        }
        PdfStream contentStream = (PdfStream) new PdfStream().makeIndirect(getDocument());
        if (array != null) {
            if (before) {
                array.add(0, contentStream);
            } else {
                array.add(contentStream);
            }
            if (array.getIndirectReference() != null) {
                array.setModified();
            } else {
                setModified();
            }
        } else {
            put(PdfName.Contents, contentStream);
        }
        return contentStream;
    }

    private void flushResourcesContentStreams() {
        flushResourcesContentStreams((PdfDictionary) getResources().getPdfObject());
        PdfArray annots = getAnnots(false);
        if (annots != null && !annots.isFlushed()) {
            for (int i = 0; i < annots.size(); i++) {
                PdfDictionary apDict = annots.getAsDictionary(i).getAsDictionary(PdfName.f1291AP);
                if (apDict != null) {
                    flushAppearanceStreams(apDict);
                }
            }
        }
    }

    private void flushResourcesContentStreams(PdfDictionary resources2) {
        if (resources2 != null && !resources2.isFlushed()) {
            flushWithResources(resources2.getAsDictionary(PdfName.XObject));
            flushWithResources(resources2.getAsDictionary(PdfName.Pattern));
            flushWithResources(resources2.getAsDictionary(PdfName.Shading));
        }
    }

    private void flushWithResources(PdfDictionary objsCollection) {
        if (objsCollection != null && !objsCollection.isFlushed()) {
            for (PdfObject obj : objsCollection.values()) {
                if (!obj.isFlushed()) {
                    flushResourcesContentStreams(((PdfDictionary) obj).getAsDictionary(PdfName.Resources));
                    flushMustBeIndirectObject(obj);
                }
            }
        }
    }

    private void flushAppearanceStreams(PdfDictionary appearanceStreamsDict) {
        if (!appearanceStreamsDict.isFlushed()) {
            for (PdfObject val : appearanceStreamsDict.values()) {
                if (val instanceof PdfDictionary) {
                    PdfDictionary ap = (PdfDictionary) val;
                    if (ap.isDictionary()) {
                        flushAppearanceStreams(ap);
                    } else if (ap.isStream()) {
                        flushMustBeIndirectObject(ap);
                    }
                }
            }
        }
    }

    private void flushMustBeIndirectObject(PdfObject obj) {
        obj.makeIndirect(getDocument()).flush();
    }

    private void copyInheritedProperties(PdfPage copyPdfPage, PdfDocument pdfDocument) {
        PdfNumber rotate;
        PdfArray cropBox;
        if (((PdfDictionary) copyPdfPage.getPdfObject()).get(PdfName.Resources) == null) {
            ((PdfDictionary) copyPdfPage.getPdfObject()).put(PdfName.Resources, pdfDocument.getWriter().copyObject(getResources().getPdfObject(), pdfDocument, false));
        }
        if (((PdfDictionary) copyPdfPage.getPdfObject()).get(PdfName.MediaBox) == null) {
            copyPdfPage.setMediaBox(getMediaBox());
        }
        if (((PdfDictionary) copyPdfPage.getPdfObject()).get(PdfName.CropBox) == null && (cropBox = (PdfArray) getInheritedValue(PdfName.CropBox, 1)) != null) {
            copyPdfPage.put(PdfName.CropBox, cropBox.copyTo(pdfDocument));
        }
        if (((PdfDictionary) copyPdfPage.getPdfObject()).get(PdfName.Rotate) == null && (rotate = (PdfNumber) getInheritedValue(PdfName.Rotate, 8)) != null) {
            copyPdfPage.put(PdfName.Rotate, rotate.copyTo(pdfDocument));
        }
    }

    private void rebuildFormFieldParent(PdfDictionary field, PdfDictionary newField, PdfDocument toDocument) {
        PdfDictionary oldParent;
        if (!newField.containsKey(PdfName.Parent) && (oldParent = field.getAsDictionary(PdfName.Parent)) != null) {
            PdfDictionary newParent = oldParent.copyTo(toDocument, Arrays.asList(new PdfName[]{PdfName.f1367P, PdfName.Kids, PdfName.Parent}), false);
            if (newParent.isFlushed()) {
                newParent = oldParent.copyTo(toDocument, Arrays.asList(new PdfName[]{PdfName.f1367P, PdfName.Kids, PdfName.Parent}), true);
            }
            rebuildFormFieldParent(oldParent, newParent, toDocument);
            if (newParent.getAsArray(PdfName.Kids) == null) {
                newParent.put(PdfName.Kids, new PdfArray());
            }
            newField.put(PdfName.Parent, newParent);
        }
    }
}
