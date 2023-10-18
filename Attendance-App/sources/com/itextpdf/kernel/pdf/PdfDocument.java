package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.KernelLogMessageConstant;
import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.ProductInfo;
import com.itextpdf.kernel.Version;
import com.itextpdf.kernel.VersionInfo;
import com.itextpdf.kernel.counter.EventCounterHandler;
import com.itextpdf.kernel.counter.event.CoreEvent;
import com.itextpdf.kernel.crypto.BadPasswordException;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.EventDispatcher;
import com.itextpdf.kernel.events.IEventDispatcher;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.log.CounterManager;
import com.itextpdf.kernel.log.ICounter;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfWidgetAnnotation;
import com.itextpdf.kernel.pdf.canvas.CanvasGraphicsState;
import com.itextpdf.kernel.pdf.collection.PdfCollection;
import com.itextpdf.kernel.pdf.filespec.PdfEncryptedPayloadFileSpecFactory;
import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;
import com.itextpdf.kernel.pdf.navigation.PdfDestination;
import com.itextpdf.kernel.pdf.tagging.PdfStructTreeRoot;
import com.itextpdf.kernel.pdf.tagutils.TagStructureContext;
import com.itextpdf.kernel.xmp.PdfConst;
import com.itextpdf.kernel.xmp.XMPConst;
import com.itextpdf.kernel.xmp.XMPException;
import com.itextpdf.kernel.xmp.XMPMeta;
import com.itextpdf.kernel.xmp.XMPMetaFactory;
import com.itextpdf.kernel.xmp.options.PropertyOptions;
import com.itextpdf.kernel.xmp.options.SerializeOptions;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.source.ByteArrayOutputStream;
import com.itextpdf.p026io.source.ByteUtils;
import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.LoggerFactory;

public class PdfDocument implements IEventDispatcher, Closeable, Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final AtomicLong lastDocumentId = new AtomicLong();
    private static IPdfPageFactory pdfPageFactory = new PdfPageFactory();
    private static final long serialVersionUID = -7041578979319799646L;
    protected PdfCatalog catalog;
    protected boolean closeReader;
    protected boolean closeWriter;
    protected boolean closed;
    @Deprecated
    protected PdfPage currentPage;
    private PdfFont defaultFont;
    protected PageSize defaultPageSize;
    private Map<PdfIndirectReference, PdfFont> documentFonts;
    private long documentId;
    protected transient EventDispatcher eventDispatcher;
    protected FingerPrint fingerPrint;
    protected boolean flushUnusedObjects;
    protected PdfDocumentInfo info;
    protected boolean isClosing;
    private LinkedHashMap<PdfPage, List<PdfLinkAnnotation>> linkAnnotations;
    MemoryLimitsAwareHandler memoryLimitsAwareHandler;
    private PdfString modifiedDocumentId;
    private PdfString originalDocumentId;
    protected PdfVersion pdfVersion;
    protected final StampingProperties properties;
    protected PdfReader reader;
    Map<PdfIndirectReference, byte[]> serializedObjectsCache;
    protected int structParentIndex;
    protected PdfStructTreeRoot structTreeRoot;
    protected transient TagStructureContext tagStructureContext;
    protected PdfDictionary trailer;
    private VersionInfo versionInfo;
    protected PdfWriter writer;
    protected byte[] xmpMetadata;
    final PdfXrefTable xref;

    public PdfDocument(PdfReader reader2) {
        this(reader2, new DocumentProperties());
    }

    public PdfDocument(PdfReader reader2, DocumentProperties properties2) {
        this.currentPage = null;
        this.defaultPageSize = PageSize.Default;
        this.eventDispatcher = new EventDispatcher();
        this.writer = null;
        this.reader = null;
        this.xmpMetadata = null;
        this.catalog = null;
        this.trailer = null;
        this.info = null;
        this.pdfVersion = PdfVersion.PDF_1_7;
        this.xref = new PdfXrefTable();
        this.structParentIndex = -1;
        this.closeReader = true;
        this.closeWriter = true;
        this.isClosing = false;
        this.closed = false;
        this.flushUnusedObjects = false;
        this.documentFonts = new HashMap();
        this.defaultFont = null;
        this.versionInfo = Version.getInstance().getInfo();
        this.linkAnnotations = new LinkedHashMap<>();
        this.serializedObjectsCache = new HashMap();
        this.memoryLimitsAwareHandler = null;
        if (reader2 != null) {
            this.documentId = lastDocumentId.incrementAndGet();
            this.reader = reader2;
            StampingProperties stampingProperties = new StampingProperties();
            this.properties = stampingProperties;
            stampingProperties.setEventCountingMetaInfo(properties2.metaInfo);
            open((PdfVersion) null);
            return;
        }
        throw new IllegalArgumentException("The reader in PdfDocument constructor can not be null.");
    }

    public PdfDocument(PdfWriter writer2) {
        this(writer2, new DocumentProperties());
    }

    public PdfDocument(PdfWriter writer2, DocumentProperties properties2) {
        this.currentPage = null;
        this.defaultPageSize = PageSize.Default;
        this.eventDispatcher = new EventDispatcher();
        this.writer = null;
        this.reader = null;
        this.xmpMetadata = null;
        this.catalog = null;
        this.trailer = null;
        this.info = null;
        this.pdfVersion = PdfVersion.PDF_1_7;
        this.xref = new PdfXrefTable();
        this.structParentIndex = -1;
        this.closeReader = true;
        this.closeWriter = true;
        this.isClosing = false;
        this.closed = false;
        this.flushUnusedObjects = false;
        this.documentFonts = new HashMap();
        this.defaultFont = null;
        this.versionInfo = Version.getInstance().getInfo();
        this.linkAnnotations = new LinkedHashMap<>();
        this.serializedObjectsCache = new HashMap();
        this.memoryLimitsAwareHandler = null;
        if (writer2 != null) {
            this.documentId = lastDocumentId.incrementAndGet();
            this.writer = writer2;
            StampingProperties stampingProperties = new StampingProperties();
            this.properties = stampingProperties;
            stampingProperties.setEventCountingMetaInfo(properties2.metaInfo);
            open(writer2.properties.pdfVersion);
            return;
        }
        throw new IllegalArgumentException("The writer in PdfDocument constructor can not be null.");
    }

    public PdfDocument(PdfReader reader2, PdfWriter writer2) {
        this(reader2, writer2, new StampingProperties());
    }

    public PdfDocument(PdfReader reader2, PdfWriter writer2, StampingProperties properties2) {
        this.currentPage = null;
        this.defaultPageSize = PageSize.Default;
        this.eventDispatcher = new EventDispatcher();
        this.writer = null;
        this.reader = null;
        this.xmpMetadata = null;
        this.catalog = null;
        this.trailer = null;
        this.info = null;
        this.pdfVersion = PdfVersion.PDF_1_7;
        this.xref = new PdfXrefTable();
        this.structParentIndex = -1;
        this.closeReader = true;
        this.closeWriter = true;
        this.isClosing = false;
        this.closed = false;
        this.flushUnusedObjects = false;
        this.documentFonts = new HashMap();
        this.defaultFont = null;
        this.versionInfo = Version.getInstance().getInfo();
        this.linkAnnotations = new LinkedHashMap<>();
        this.serializedObjectsCache = new HashMap();
        this.memoryLimitsAwareHandler = null;
        if (reader2 == null) {
            throw new IllegalArgumentException("The reader in PdfDocument constructor can not be null.");
        } else if (writer2 != null) {
            this.documentId = lastDocumentId.incrementAndGet();
            this.reader = reader2;
            this.writer = writer2;
            this.properties = properties2;
            boolean writerHasEncryption = writerHasEncryption();
            Class<PdfDocument> cls = PdfDocument.class;
            if (properties2.appendMode && writerHasEncryption) {
                LoggerFactory.getLogger((Class<?>) cls).warn(LogMessageConstant.WRITER_ENCRYPTION_IS_IGNORED_APPEND);
            }
            if (properties2.preserveEncryption && writerHasEncryption) {
                LoggerFactory.getLogger((Class<?>) cls).warn(LogMessageConstant.WRITER_ENCRYPTION_IS_IGNORED_PRESERVE);
            }
            open(writer2.properties.pdfVersion);
        } else {
            throw new IllegalArgumentException("The writer in PdfDocument constructor can not be null.");
        }
    }

    /* access modifiers changed from: protected */
    public void setXmpMetadata(byte[] xmpMetadata2) {
        this.xmpMetadata = xmpMetadata2;
    }

    public void setXmpMetadata(XMPMeta xmpMeta, SerializeOptions serializeOptions) throws XMPException {
        setXmpMetadata(XMPMetaFactory.serializeToBuffer(xmpMeta, serializeOptions));
    }

    public void setXmpMetadata(XMPMeta xmpMeta) throws XMPException {
        SerializeOptions serializeOptions = new SerializeOptions();
        serializeOptions.setPadding(2000);
        setXmpMetadata(xmpMeta, serializeOptions);
    }

    public byte[] getXmpMetadata() {
        return getXmpMetadata(false);
    }

    public byte[] getXmpMetadata(boolean createNew) {
        if (this.xmpMetadata == null && createNew) {
            XMPMeta xmpMeta = XMPMetaFactory.create();
            xmpMeta.setObjectName(XMPConst.TAG_XMPMETA);
            xmpMeta.setObjectName("");
            addCustomMetadataExtensions(xmpMeta);
            try {
                xmpMeta.setProperty(XMPConst.NS_DC, PdfConst.Format, "application/pdf");
                xmpMeta.setProperty(XMPConst.NS_PDF, PdfConst.Producer, this.versionInfo.getVersion());
                setXmpMetadata(xmpMeta);
            } catch (XMPException e) {
            }
        }
        return this.xmpMetadata;
    }

    public PdfObject getPdfObject(int objNum) {
        checkClosingStatus();
        PdfIndirectReference reference = this.xref.get(objNum);
        if (reference == null) {
            return null;
        }
        return reference.getRefersTo();
    }

    public int getNumberOfPdfObjects() {
        return this.xref.size();
    }

    public PdfPage getPage(int pageNum) {
        checkClosingStatus();
        return this.catalog.getPageTree().getPage(pageNum);
    }

    public PdfPage getPage(PdfDictionary pageDictionary) {
        checkClosingStatus();
        return this.catalog.getPageTree().getPage(pageDictionary);
    }

    public PdfPage getFirstPage() {
        checkClosingStatus();
        return getPage(1);
    }

    public PdfPage getLastPage() {
        return getPage(getNumberOfPages());
    }

    public PdfPage addNewPage() {
        return addNewPage(getDefaultPageSize());
    }

    public PdfPage addNewPage(PageSize pageSize) {
        checkClosingStatus();
        PdfPage page = getPageFactory().createPdfPage(this, pageSize);
        checkAndAddPage(page);
        dispatchEvent(new PdfDocumentEvent(PdfDocumentEvent.START_PAGE, page));
        dispatchEvent(new PdfDocumentEvent(PdfDocumentEvent.INSERT_PAGE, page));
        return page;
    }

    public PdfPage addNewPage(int index) {
        return addNewPage(index, getDefaultPageSize());
    }

    public PdfPage addNewPage(int index, PageSize pageSize) {
        checkClosingStatus();
        PdfPage page = getPageFactory().createPdfPage(this, pageSize);
        checkAndAddPage(index, page);
        this.currentPage = page;
        dispatchEvent(new PdfDocumentEvent(PdfDocumentEvent.START_PAGE, page));
        dispatchEvent(new PdfDocumentEvent(PdfDocumentEvent.INSERT_PAGE, page));
        return page;
    }

    public PdfPage addPage(PdfPage page) {
        checkClosingStatus();
        checkAndAddPage(page);
        dispatchEvent(new PdfDocumentEvent(PdfDocumentEvent.INSERT_PAGE, page));
        return page;
    }

    public PdfPage addPage(int index, PdfPage page) {
        checkClosingStatus();
        checkAndAddPage(index, page);
        this.currentPage = page;
        dispatchEvent(new PdfDocumentEvent(PdfDocumentEvent.INSERT_PAGE, page));
        return page;
    }

    public int getNumberOfPages() {
        checkClosingStatus();
        return this.catalog.getPageTree().getNumberOfPages();
    }

    public int getPageNumber(PdfPage page) {
        checkClosingStatus();
        return this.catalog.getPageTree().getPageNumber(page);
    }

    public int getPageNumber(PdfDictionary pageDictionary) {
        return this.catalog.getPageTree().getPageNumber(pageDictionary);
    }

    public boolean movePage(PdfPage page, int insertBefore) {
        checkClosingStatus();
        int pageNum = getPageNumber(page);
        if (pageNum <= 0) {
            return false;
        }
        movePage(pageNum, insertBefore);
        return true;
    }

    public void movePage(int pageNumber, int insertBefore) {
        checkClosingStatus();
        if (insertBefore < 1 || insertBefore > getNumberOfPages() + 1) {
            throw new IndexOutOfBoundsException(MessageFormatUtil.format(PdfException.RequestedPageNumberIsOutOfBounds, Integer.valueOf(insertBefore)));
        }
        PdfPage page = getPage(pageNumber);
        if (isTagged()) {
            getStructTreeRoot().move(page, insertBefore);
            getTagStructureContext().normalizeDocumentRootTag();
        }
        PdfPage removedPage = this.catalog.getPageTree().removePage(pageNumber);
        if (insertBefore > pageNumber) {
            insertBefore--;
        }
        this.catalog.getPageTree().addPage(insertBefore, removedPage);
    }

    public boolean removePage(PdfPage page) {
        checkClosingStatus();
        int pageNum = getPageNumber(page);
        if (pageNum < 1) {
            return false;
        }
        removePage(pageNum);
        return true;
    }

    public void removePage(int pageNum) {
        checkClosingStatus();
        PdfPage removedPage = getPage(pageNum);
        if (removedPage == null || !removedPage.isFlushed() || (!isTagged() && !hasAcroForm())) {
            this.catalog.getPageTree().removePage(pageNum);
            if (removedPage != null) {
                this.catalog.removeOutlines(removedPage);
                removeUnusedWidgetsFromFields(removedPage);
                if (isTagged()) {
                    getTagStructureContext().removePageTags(removedPage);
                }
                if (!removedPage.isFlushed()) {
                    ((PdfDictionary) removedPage.getPdfObject()).remove(PdfName.Parent);
                    ((PdfDictionary) removedPage.getPdfObject()).getIndirectReference().setFree();
                }
                dispatchEvent(new PdfDocumentEvent(PdfDocumentEvent.REMOVE_PAGE, removedPage));
                return;
            }
            return;
        }
        throw new PdfException(PdfException.FLUSHED_PAGE_CANNOT_BE_REMOVED);
    }

    public PdfDocumentInfo getDocumentInfo() {
        checkClosingStatus();
        return this.info;
    }

    public PdfString getOriginalDocumentId() {
        return this.originalDocumentId;
    }

    public PdfString getModifiedDocumentId() {
        return this.modifiedDocumentId;
    }

    public PageSize getDefaultPageSize() {
        return this.defaultPageSize;
    }

    public void setDefaultPageSize(PageSize pageSize) {
        this.defaultPageSize = pageSize;
    }

    public void addEventHandler(String type, IEventHandler handler) {
        this.eventDispatcher.addEventHandler(type, handler);
    }

    public void dispatchEvent(Event event) {
        this.eventDispatcher.dispatchEvent(event);
    }

    public void dispatchEvent(Event event, boolean delayed) {
        this.eventDispatcher.dispatchEvent(event, delayed);
    }

    public boolean hasEventHandler(String type) {
        return this.eventDispatcher.hasEventHandler(type);
    }

    public void removeEventHandler(String type, IEventHandler handler) {
        this.eventDispatcher.removeEventHandler(type, handler);
    }

    public void removeAllHandlers() {
        this.eventDispatcher.removeAllHandlers();
    }

    public PdfWriter getWriter() {
        checkClosingStatus();
        return this.writer;
    }

    public PdfReader getReader() {
        checkClosingStatus();
        return this.reader;
    }

    public boolean isAppendMode() {
        checkClosingStatus();
        return this.properties.appendMode;
    }

    public PdfIndirectReference createNextIndirectReference() {
        checkClosingStatus();
        return this.xref.createNextIndirectReference(this);
    }

    public PdfVersion getPdfVersion() {
        return this.pdfVersion;
    }

    public PdfCatalog getCatalog() {
        checkClosingStatus();
        return this.catalog;
    }

    public void close() {
        Class<PdfDocument> cls = PdfDocument.class;
        if (!this.closed) {
            this.isClosing = true;
            try {
                if (this.writer != null) {
                    if (!this.catalog.isFlushed()) {
                        updateProducerInInfoDictionary();
                        updateXmpMetadata();
                        if (this.pdfVersion.compareTo(PdfVersion.PDF_2_0) >= 0) {
                            for (PdfName deprecatedKey : PdfDocumentInfo.PDF20_DEPRECATED_KEYS) {
                                this.info.getPdfObject().remove(deprecatedKey);
                            }
                        }
                        if (getXmpMetadata() != null) {
                            PdfStream xmp = ((PdfDictionary) this.catalog.getPdfObject()).getAsStream(PdfName.Metadata);
                            if (!isAppendMode() || xmp == null || xmp.isFlushed() || xmp.getIndirectReference() == null) {
                                xmp = (PdfStream) new PdfStream().makeIndirect(this);
                                xmp.getOutputStream().write(this.xmpMetadata);
                                ((PdfDictionary) this.catalog.getPdfObject()).put(PdfName.Metadata, xmp);
                                this.catalog.setModified();
                            } else {
                                xmp.setData(this.xmpMetadata);
                                xmp.setModified();
                            }
                            xmp.put(PdfName.Type, PdfName.Metadata);
                            xmp.put(PdfName.Subtype, PdfName.XML);
                            if (this.writer.crypto != null && !this.writer.crypto.isMetadataEncrypted()) {
                                PdfArray ar = new PdfArray();
                                ar.add(PdfName.Crypt);
                                xmp.put(PdfName.Filter, ar);
                            }
                        }
                        checkIsoConformance();
                        PdfObject crypto = null;
                        Set<PdfIndirectReference> forbiddenToFlush = new HashSet<>();
                        if (this.properties.appendMode) {
                            if (this.structTreeRoot != null) {
                                tryFlushTagStructure(true);
                            }
                            if (this.catalog.isOCPropertiesMayHaveChanged() && ((PdfDictionary) this.catalog.getOCProperties(false).getPdfObject()).isModified()) {
                                this.catalog.getOCProperties(false).flush();
                            }
                            if (this.catalog.pageLabels != null) {
                                this.catalog.put(PdfName.PageLabels, this.catalog.pageLabels.buildTree());
                            }
                            for (Map.Entry<PdfName, PdfNameTree> entry : this.catalog.nameTrees.entrySet()) {
                                PdfNameTree tree = entry.getValue();
                                if (tree.isModified()) {
                                    ensureTreeRootAddedToNames(tree.buildTree().makeIndirect(this), entry.getKey());
                                }
                            }
                            PdfObject pageRoot = this.catalog.getPageTree().generateTree();
                            if (((PdfDictionary) this.catalog.getPdfObject()).isModified() || pageRoot.isModified()) {
                                this.catalog.put(PdfName.Pages, pageRoot);
                                ((PdfDictionary) this.catalog.getPdfObject()).flush(false);
                            }
                            if (this.info.getPdfObject().isModified()) {
                                this.info.getPdfObject().flush(false);
                            }
                            flushFonts();
                            if (this.writer.crypto != null) {
                                if (this.reader.decrypt.getPdfObject() == this.writer.crypto.getPdfObject()) {
                                    crypto = this.reader.decrypt.getPdfObject();
                                    if (crypto.getIndirectReference() != null) {
                                        forbiddenToFlush.add(crypto.getIndirectReference());
                                    }
                                } else {
                                    throw new AssertionError("Conflict with source encryption");
                                }
                            }
                            this.writer.flushModifiedWaitingObjects(forbiddenToFlush);
                            for (int i = 0; i < this.xref.size(); i++) {
                                PdfIndirectReference indirectReference = this.xref.get(i);
                                if (indirectReference != null && !indirectReference.isFree() && indirectReference.checkState(8) && !indirectReference.checkState(1) && !forbiddenToFlush.contains(indirectReference)) {
                                    indirectReference.setFree();
                                }
                            }
                        } else {
                            if (this.catalog.isOCPropertiesMayHaveChanged()) {
                                ((PdfDictionary) this.catalog.getPdfObject()).put(PdfName.OCProperties, this.catalog.getOCProperties(false).getPdfObject());
                                this.catalog.getOCProperties(false).flush();
                            }
                            if (this.catalog.pageLabels != null) {
                                this.catalog.put(PdfName.PageLabels, this.catalog.pageLabels.buildTree());
                            }
                            ((PdfDictionary) this.catalog.getPdfObject()).put(PdfName.Pages, this.catalog.getPageTree().generateTree());
                            for (Map.Entry<PdfName, PdfNameTree> entry2 : this.catalog.nameTrees.entrySet()) {
                                PdfNameTree tree2 = entry2.getValue();
                                if (tree2.isModified()) {
                                    ensureTreeRootAddedToNames(tree2.buildTree().makeIndirect(this), entry2.getKey());
                                }
                            }
                            for (int pageNum = 1; pageNum <= getNumberOfPages(); pageNum++) {
                                getPage(pageNum).flush();
                            }
                            if (this.structTreeRoot != null) {
                                tryFlushTagStructure(false);
                            }
                            ((PdfDictionary) this.catalog.getPdfObject()).flush(false);
                            this.info.getPdfObject().flush(false);
                            flushFonts();
                            if (this.writer.crypto != null) {
                                crypto = this.writer.crypto.getPdfObject();
                                crypto.makeIndirect(this);
                                forbiddenToFlush.add(crypto.getIndirectReference());
                            }
                            this.writer.flushWaitingObjects(forbiddenToFlush);
                            for (int i2 = 0; i2 < this.xref.size(); i2++) {
                                PdfIndirectReference indirectReference2 = this.xref.get(i2);
                                if (indirectReference2 != null && !indirectReference2.isFree() && !indirectReference2.checkState(1) && !forbiddenToFlush.contains(indirectReference2)) {
                                    if (isFlushUnusedObjects() && !indirectReference2.checkState(16)) {
                                        PdfObject refersTo = indirectReference2.getRefersTo(false);
                                        PdfObject object = refersTo;
                                        if (refersTo != null) {
                                            object.flush();
                                        }
                                    }
                                    indirectReference2.setFree();
                                }
                            }
                        }
                        this.writer.crypto = null;
                        if (!this.properties.appendMode && crypto != null) {
                            crypto.flush(false);
                        }
                        this.trailer.put(PdfName.Root, this.catalog.getPdfObject());
                        this.trailer.put(PdfName.Info, this.info.getPdfObject());
                        this.xref.writeXrefTableAndTrailer(this, PdfEncryption.createInfoId(ByteUtils.getIsoBytes(this.originalDocumentId.getValue()), ByteUtils.getIsoBytes(this.modifiedDocumentId.getValue())), crypto);
                        this.writer.flush();
                        for (ICounter counter : getCounters()) {
                            counter.onDocumentWritten(this.writer.getCurrentPos());
                        }
                    } else {
                        throw new PdfException(PdfException.CannotCloseDocumentWithAlreadyFlushedPdfCatalog);
                    }
                }
                this.catalog.getPageTree().clearPageRefs();
                removeAllHandlers();
                if (this.writer != null && isCloseWriter()) {
                    try {
                        this.writer.close();
                    } catch (Exception e) {
                        LoggerFactory.getLogger((Class<?>) cls).error(LogMessageConstant.PDF_WRITER_CLOSING_FAILED, (Throwable) e);
                    }
                }
                if (this.reader != null && isCloseReader()) {
                    try {
                        this.reader.close();
                    } catch (Exception e2) {
                        LoggerFactory.getLogger((Class<?>) cls).error(LogMessageConstant.PDF_READER_CLOSING_FAILED, (Throwable) e2);
                    }
                }
                this.closed = true;
            } catch (IOException e3) {
                throw new PdfException(PdfException.CannotCloseDocument, e3, this);
            } catch (Throwable th) {
                if (this.writer != null && isCloseWriter()) {
                    try {
                        this.writer.close();
                    } catch (Exception e4) {
                        LoggerFactory.getLogger((Class<?>) cls).error(LogMessageConstant.PDF_WRITER_CLOSING_FAILED, (Throwable) e4);
                    }
                }
                if (this.reader != null && isCloseReader()) {
                    try {
                        this.reader.close();
                    } catch (Exception e5) {
                        LoggerFactory.getLogger((Class<?>) cls).error(LogMessageConstant.PDF_READER_CLOSING_FAILED, (Throwable) e5);
                    }
                }
                throw th;
            }
        }
    }

    public boolean isClosed() {
        return this.closed;
    }

    public boolean isTagged() {
        return this.structTreeRoot != null;
    }

    public PdfDocument setTagged() {
        checkClosingStatus();
        if (this.structTreeRoot == null) {
            this.structTreeRoot = new PdfStructTreeRoot(this);
            ((PdfDictionary) this.catalog.getPdfObject()).put(PdfName.StructTreeRoot, this.structTreeRoot.getPdfObject());
            updateValueInMarkInfoDict(PdfName.Marked, PdfBoolean.TRUE);
            this.structParentIndex = 0;
        }
        return this;
    }

    public PdfStructTreeRoot getStructTreeRoot() {
        return this.structTreeRoot;
    }

    public int getNextStructParentIndex() {
        int i = this.structParentIndex;
        if (i < 0) {
            return -1;
        }
        this.structParentIndex = i + 1;
        return i;
    }

    public TagStructureContext getTagStructureContext() {
        checkClosingStatus();
        if (this.tagStructureContext == null) {
            if (isTagged()) {
                initTagStructureContext();
            } else {
                throw new PdfException(PdfException.MustBeATaggedDocument);
            }
        }
        return this.tagStructureContext;
    }

    public List<PdfPage> copyPagesTo(int pageFrom, int pageTo, PdfDocument toDocument, int insertBeforePage) {
        return copyPagesTo(pageFrom, pageTo, toDocument, insertBeforePage, (IPdfPageExtraCopier) null);
    }

    public List<PdfPage> copyPagesTo(int pageFrom, int pageTo, PdfDocument toDocument, int insertBeforePage, IPdfPageExtraCopier copier) {
        List<Integer> pages = new ArrayList<>();
        for (int i = pageFrom; i <= pageTo; i++) {
            pages.add(Integer.valueOf(i));
        }
        return copyPagesTo(pages, toDocument, insertBeforePage, copier);
    }

    public List<PdfPage> copyPagesTo(int pageFrom, int pageTo, PdfDocument toDocument) {
        return copyPagesTo(pageFrom, pageTo, toDocument, (IPdfPageExtraCopier) null);
    }

    public List<PdfPage> copyPagesTo(int pageFrom, int pageTo, PdfDocument toDocument, IPdfPageExtraCopier copier) {
        return copyPagesTo(pageFrom, pageTo, toDocument, toDocument.getNumberOfPages() + 1, copier);
    }

    public List<PdfPage> copyPagesTo(List<Integer> pagesToCopy, PdfDocument toDocument, int insertBeforePage) {
        return copyPagesTo(pagesToCopy, toDocument, insertBeforePage, (IPdfPageExtraCopier) null);
    }

    public List<PdfPage> copyPagesTo(List<Integer> pagesToCopy, PdfDocument toDocument, int insertBeforePage, IPdfPageExtraCopier copier) {
        PdfDocument pdfDocument = toDocument;
        if (pagesToCopy.isEmpty()) {
            return Collections.emptyList();
        }
        checkClosingStatus();
        List<PdfPage> copiedPages = new ArrayList<>();
        Map<PdfPage, PdfPage> page2page = new LinkedHashMap<>();
        Set<PdfOutline> outlinesToCopy = new HashSet<>();
        List<Map<PdfPage, PdfPage>> rangesOfPagesWithIncreasingNumbers = new ArrayList<>();
        int lastCopiedPageNum = pagesToCopy.get(0).intValue();
        int pageInsertIndex = insertBeforePage;
        int insertBeforePage2 = insertBeforePage;
        boolean insertInBetween = insertBeforePage2 < toDocument.getNumberOfPages() + 1;
        for (Integer pageNum : pagesToCopy) {
            PdfPage page = getPage(pageNum.intValue());
            PdfPage newPage = page.copyTo(pdfDocument, copier);
            copiedPages.add(newPage);
            page2page.put(page, newPage);
            if (lastCopiedPageNum >= pageNum.intValue()) {
                rangesOfPagesWithIncreasingNumbers.add(new HashMap());
            }
            int lastRangeInd = rangesOfPagesWithIncreasingNumbers.size() - 1;
            int i = lastRangeInd;
            rangesOfPagesWithIncreasingNumbers.get(lastRangeInd).put(page, newPage);
            if (insertInBetween) {
                pdfDocument.addPage(pageInsertIndex, newPage);
            } else {
                pdfDocument.addPage(newPage);
            }
            pageInsertIndex++;
            if (toDocument.hasOutlines()) {
                PdfPage pdfPage = newPage;
                List<PdfOutline> pageOutlines = page.getOutlines(false);
                if (pageOutlines != null) {
                    outlinesToCopy.addAll(pageOutlines);
                }
            }
            lastCopiedPageNum = pageNum.intValue();
            List<Integer> list = pagesToCopy;
        }
        IPdfPageExtraCopier iPdfPageExtraCopier = copier;
        copyLinkAnnotations(pdfDocument, page2page);
        if (!(getCatalog() == null || ((PdfDictionary) getCatalog().getPdfObject()).getAsDictionary(PdfName.OCProperties) == null)) {
            OcgPropertiesCopier.copyOCGProperties(this, pdfDocument, page2page);
        }
        if (toDocument.isTagged()) {
            if (isTagged()) {
                try {
                    for (Map<PdfPage, PdfPage> increasingPagesRange : rangesOfPagesWithIncreasingNumbers) {
                        try {
                            if (insertInBetween) {
                                getStructTreeRoot().copyTo(pdfDocument, insertBeforePage2, increasingPagesRange);
                            } else {
                                getStructTreeRoot().copyTo(pdfDocument, increasingPagesRange);
                            }
                            insertBeforePage2 += increasingPagesRange.size();
                        } catch (Exception e) {
                            ex = e;
                            throw new PdfException(PdfException.TagStructureCopyingFailedItMightBeCorruptedInOneOfTheDocuments, (Throwable) ex);
                        }
                    }
                    toDocument.getTagStructureContext().normalizeDocumentRootTag();
                } catch (Exception e2) {
                    ex = e2;
                    throw new PdfException(PdfException.TagStructureCopyingFailedItMightBeCorruptedInOneOfTheDocuments, (Throwable) ex);
                }
            } else {
                LoggerFactory.getLogger((Class<?>) PdfDocument.class).warn(LogMessageConstant.NOT_TAGGED_PAGES_IN_TAGGED_DOCUMENT);
            }
        }
        if (this.catalog.isOutlineMode()) {
            copyOutlines(outlinesToCopy, pdfDocument, page2page);
        }
        return copiedPages;
    }

    public List<PdfPage> copyPagesTo(List<Integer> pagesToCopy, PdfDocument toDocument) {
        return copyPagesTo(pagesToCopy, toDocument, (IPdfPageExtraCopier) null);
    }

    public List<PdfPage> copyPagesTo(List<Integer> pagesToCopy, PdfDocument toDocument, IPdfPageExtraCopier copier) {
        return copyPagesTo(pagesToCopy, toDocument, toDocument.getNumberOfPages() + 1, copier);
    }

    public void flushCopiedObjects(PdfDocument sourceDoc) {
        if (getWriter() != null) {
            getWriter().flushCopiedObjects(sourceDoc.getDocumentId());
        }
    }

    public boolean isCloseReader() {
        return this.closeReader;
    }

    public void setCloseReader(boolean closeReader2) {
        checkClosingStatus();
        this.closeReader = closeReader2;
    }

    public boolean isCloseWriter() {
        return this.closeWriter;
    }

    public void setCloseWriter(boolean closeWriter2) {
        checkClosingStatus();
        this.closeWriter = closeWriter2;
    }

    public boolean isFlushUnusedObjects() {
        return this.flushUnusedObjects;
    }

    public void setFlushUnusedObjects(boolean flushUnusedObjects2) {
        checkClosingStatus();
        this.flushUnusedObjects = flushUnusedObjects2;
    }

    public PdfOutline getOutlines(boolean updateOutlines) {
        checkClosingStatus();
        return this.catalog.getOutlines(updateOutlines);
    }

    public void initializeOutlines() {
        checkClosingStatus();
        getOutlines(false);
    }

    public void addNamedDestination(String key, PdfObject value) {
        checkClosingStatus();
        if (value.isArray() && ((PdfArray) value).get(0).isNumber()) {
            LoggerFactory.getLogger((Class<?>) PdfDocument.class).warn(LogMessageConstant.INVALID_DESTINATION_TYPE);
        }
        this.catalog.addNamedDestination(key, value);
    }

    public List<PdfIndirectReference> listIndirectReferences() {
        checkClosingStatus();
        List<PdfIndirectReference> indRefs = new ArrayList<>(this.xref.size());
        for (int i = 0; i < this.xref.size(); i++) {
            PdfIndirectReference indref = this.xref.get(i);
            if (indref != null) {
                indRefs.add(indref);
            }
        }
        return indRefs;
    }

    public PdfDictionary getTrailer() {
        checkClosingStatus();
        return this.trailer;
    }

    public void addOutputIntent(PdfOutputIntent outputIntent) {
        checkClosingStatus();
        if (outputIntent != null) {
            PdfArray outputIntents = ((PdfDictionary) this.catalog.getPdfObject()).getAsArray(PdfName.OutputIntents);
            if (outputIntents == null) {
                outputIntents = new PdfArray();
                this.catalog.put(PdfName.OutputIntents, outputIntents);
            }
            outputIntents.add(outputIntent.getPdfObject());
        }
    }

    public void checkIsoConformance(Object obj, IsoKey key) {
    }

    @Deprecated
    public void checkIsoConformance(Object obj, IsoKey key, PdfResources resources) {
    }

    public void checkIsoConformance(Object obj, IsoKey key, PdfResources resources, PdfStream contentStream) {
    }

    public void checkShowTextIsoConformance(CanvasGraphicsState gState, PdfResources resources) {
    }

    public void addFileAttachment(String key, PdfFileSpec fs) {
        checkClosingStatus();
        this.catalog.addNameToNameTree(key, fs.getPdfObject(), PdfName.EmbeddedFiles);
    }

    /* JADX WARNING: type inference failed for: r1v4, types: [com.itextpdf.kernel.pdf.PdfObject] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addAssociatedFile(java.lang.String r4, com.itextpdf.kernel.pdf.filespec.PdfFileSpec r5) {
        /*
            r3 = this;
            com.itextpdf.kernel.pdf.PdfObject r0 = r5.getPdfObject()
            com.itextpdf.kernel.pdf.PdfDictionary r0 = (com.itextpdf.kernel.pdf.PdfDictionary) r0
            com.itextpdf.kernel.pdf.PdfName r1 = com.itextpdf.kernel.pdf.PdfName.AFRelationship
            com.itextpdf.kernel.pdf.PdfObject r0 = r0.get(r1)
            if (r0 != 0) goto L_0x0019
            java.lang.Class<com.itextpdf.kernel.pdf.PdfDocument> r0 = com.itextpdf.kernel.pdf.PdfDocument.class
            org.slf4j.Logger r0 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r0)
            java.lang.String r1 = "For associated files their associated file specification dictionaries shall include the AFRelationship key."
            r0.error(r1)
        L_0x0019:
            com.itextpdf.kernel.pdf.PdfCatalog r0 = r3.catalog
            com.itextpdf.kernel.pdf.PdfObject r0 = r0.getPdfObject()
            com.itextpdf.kernel.pdf.PdfDictionary r0 = (com.itextpdf.kernel.pdf.PdfDictionary) r0
            com.itextpdf.kernel.pdf.PdfName r1 = com.itextpdf.kernel.pdf.PdfName.f1289AF
            com.itextpdf.kernel.pdf.PdfArray r0 = r0.getAsArray(r1)
            if (r0 != 0) goto L_0x003c
            com.itextpdf.kernel.pdf.PdfArray r1 = new com.itextpdf.kernel.pdf.PdfArray
            r1.<init>()
            com.itextpdf.kernel.pdf.PdfObject r1 = r1.makeIndirect(r3)
            r0 = r1
            com.itextpdf.kernel.pdf.PdfArray r0 = (com.itextpdf.kernel.pdf.PdfArray) r0
            com.itextpdf.kernel.pdf.PdfCatalog r1 = r3.catalog
            com.itextpdf.kernel.pdf.PdfName r2 = com.itextpdf.kernel.pdf.PdfName.f1289AF
            r1.put(r2, r0)
        L_0x003c:
            com.itextpdf.kernel.pdf.PdfObject r1 = r5.getPdfObject()
            r0.add(r1)
            r3.addFileAttachment(r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.pdf.PdfDocument.addAssociatedFile(java.lang.String, com.itextpdf.kernel.pdf.filespec.PdfFileSpec):void");
    }

    public PdfArray getAssociatedFiles() {
        checkClosingStatus();
        return ((PdfDictionary) this.catalog.getPdfObject()).getAsArray(PdfName.f1289AF);
    }

    public PdfEncryptedPayloadDocument getEncryptedPayloadDocument() {
        PdfCollection collection;
        if ((getReader() == null || !getReader().isEncrypted()) && (collection = getCatalog().getCollection()) != null && collection.isViewHidden()) {
            PdfString documentName = collection.getInitialDocument();
            PdfNameTree embeddedFiles = getCatalog().getNameTree(PdfName.EmbeddedFiles);
            String documentNameUnicode = documentName.toUnicodeString();
            PdfObject fileSpecObject = embeddedFiles.getNames().get(documentNameUnicode);
            if (fileSpecObject != null && fileSpecObject.isDictionary()) {
                try {
                    PdfFileSpec fileSpec = PdfEncryptedPayloadFileSpecFactory.wrap((PdfDictionary) fileSpecObject);
                    if (fileSpec != null) {
                        PdfDictionary embeddedDictionary = ((PdfDictionary) fileSpec.getPdfObject()).getAsDictionary(PdfName.f1321EF);
                        PdfStream stream = embeddedDictionary.getAsStream(PdfName.f1405UF);
                        if (stream == null) {
                            stream = embeddedDictionary.getAsStream(PdfName.f1324F);
                        }
                        if (stream != null) {
                            return new PdfEncryptedPayloadDocument(stream, fileSpec, documentNameUnicode);
                        }
                    }
                } catch (PdfException e) {
                    LoggerFactory.getLogger(getClass()).error(e.getMessage());
                }
            }
        }
        return null;
    }

    public void setEncryptedPayload(PdfFileSpec fs) {
        if (getWriter() == null) {
            throw new PdfException(PdfException.CannotSetEncryptedPayloadToDocumentOpenedInReadingMode);
        } else if (!writerHasEncryption()) {
            if (!PdfName.EncryptedPayload.equals(((PdfDictionary) fs.getPdfObject()).get(PdfName.AFRelationship))) {
                LoggerFactory.getLogger(getClass()).error(LogMessageConstant.f1188xfdc6407c);
            }
            PdfEncryptedPayload encryptedPayload = PdfEncryptedPayload.extractFrom(fs);
            if (encryptedPayload != null) {
                PdfCollection collection = getCatalog().getCollection();
                if (collection != null) {
                    LoggerFactory.getLogger(getClass()).warn(LogMessageConstant.COLLECTION_DICTIONARY_ALREADY_EXISTS_IT_WILL_BE_MODIFIED);
                } else {
                    collection = new PdfCollection();
                    getCatalog().setCollection(collection);
                }
                collection.setView(2);
                String displayName = PdfEncryptedPayloadFileSpecFactory.generateFileDisplay(encryptedPayload);
                collection.setInitialDocument(displayName);
                addAssociatedFile(displayName, fs);
                return;
            }
            throw new PdfException(PdfException.EncryptedPayloadFileSpecDoesntHaveEncryptedPayloadDictionary);
        } else {
            throw new PdfException(PdfException.CannotSetEncryptedPayloadToEncryptedDocument);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String[] getPageLabels() {
        /*
            r11 = this;
            com.itextpdf.kernel.pdf.PdfCatalog r0 = r11.catalog
            r1 = 0
            com.itextpdf.kernel.pdf.PdfNumTree r0 = r0.getPageLabelsTree(r1)
            r2 = 0
            if (r0 != 0) goto L_0x000b
            return r2
        L_0x000b:
            com.itextpdf.kernel.pdf.PdfCatalog r0 = r11.catalog
            com.itextpdf.kernel.pdf.PdfNumTree r0 = r0.getPageLabelsTree(r1)
            java.util.Map r0 = r0.getNumbers()
            int r3 = r0.size()
            if (r3 != 0) goto L_0x001c
            return r2
        L_0x001c:
            int r2 = r11.getNumberOfPages()
            java.lang.String[] r2 = new java.lang.String[r2]
            r3 = 1
            java.lang.String r4 = ""
            java.lang.String r5 = "D"
            r6 = 0
        L_0x0028:
            int r7 = r11.getNumberOfPages()
            if (r6 >= r7) goto L_0x012a
            java.lang.Integer r7 = java.lang.Integer.valueOf(r6)
            boolean r7 = r0.containsKey(r7)
            if (r7 == 0) goto L_0x006e
            java.lang.Integer r7 = java.lang.Integer.valueOf(r6)
            java.lang.Object r7 = r0.get(r7)
            com.itextpdf.kernel.pdf.PdfDictionary r7 = (com.itextpdf.kernel.pdf.PdfDictionary) r7
            com.itextpdf.kernel.pdf.PdfName r8 = com.itextpdf.kernel.pdf.PdfName.f1389St
            com.itextpdf.kernel.pdf.PdfNumber r8 = r7.getAsNumber(r8)
            if (r8 == 0) goto L_0x004f
            int r3 = r8.intValue()
            goto L_0x0050
        L_0x004f:
            r3 = 1
        L_0x0050:
            com.itextpdf.kernel.pdf.PdfName r9 = com.itextpdf.kernel.pdf.PdfName.f1367P
            com.itextpdf.kernel.pdf.PdfString r9 = r7.getAsString(r9)
            if (r9 == 0) goto L_0x005d
            java.lang.String r4 = r9.toUnicodeString()
            goto L_0x005f
        L_0x005d:
            java.lang.String r4 = ""
        L_0x005f:
            com.itextpdf.kernel.pdf.PdfName r10 = com.itextpdf.kernel.pdf.PdfName.f1385S
            com.itextpdf.kernel.pdf.PdfName r10 = r7.getAsName(r10)
            if (r10 == 0) goto L_0x006c
            java.lang.String r5 = r10.getValue()
            goto L_0x006e
        L_0x006c:
            java.lang.String r5 = "e"
        L_0x006e:
            int r7 = r5.hashCode()
            switch(r7) {
                case 65: goto L_0x009f;
                case 82: goto L_0x0095;
                case 97: goto L_0x008b;
                case 101: goto L_0x0081;
                case 114: goto L_0x0076;
                default: goto L_0x0075;
            }
        L_0x0075:
            goto L_0x00a9
        L_0x0076:
            java.lang.String r7 = "r"
            boolean r7 = r5.equals(r7)
            if (r7 == 0) goto L_0x0075
            r7 = 1
            goto L_0x00aa
        L_0x0081:
            java.lang.String r7 = "e"
            boolean r7 = r5.equals(r7)
            if (r7 == 0) goto L_0x0075
            r7 = 4
            goto L_0x00aa
        L_0x008b:
            java.lang.String r7 = "a"
            boolean r7 = r5.equals(r7)
            if (r7 == 0) goto L_0x0075
            r7 = 3
            goto L_0x00aa
        L_0x0095:
            java.lang.String r7 = "R"
            boolean r7 = r5.equals(r7)
            if (r7 == 0) goto L_0x0075
            r7 = 0
            goto L_0x00aa
        L_0x009f:
            java.lang.String r7 = "A"
            boolean r7 = r5.equals(r7)
            if (r7 == 0) goto L_0x0075
            r7 = 2
            goto L_0x00aa
        L_0x00a9:
            r7 = -1
        L_0x00aa:
            switch(r7) {
                case 0: goto L_0x010c;
                case 1: goto L_0x00f4;
                case 2: goto L_0x00dc;
                case 3: goto L_0x00c4;
                case 4: goto L_0x00c1;
                default: goto L_0x00ad;
            }
        L_0x00ad:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.StringBuilder r7 = r7.append(r4)
            java.lang.StringBuilder r7 = r7.append(r3)
            java.lang.String r7 = r7.toString()
            r2[r6] = r7
            goto L_0x0124
        L_0x00c1:
            r2[r6] = r4
            goto L_0x0124
        L_0x00c4:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.StringBuilder r7 = r7.append(r4)
            java.lang.String r8 = com.itextpdf.kernel.numbering.EnglishAlphabetNumbering.toLatinAlphabetNumberLowerCase(r3)
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            r2[r6] = r7
            goto L_0x0124
        L_0x00dc:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.StringBuilder r7 = r7.append(r4)
            java.lang.String r8 = com.itextpdf.kernel.numbering.EnglishAlphabetNumbering.toLatinAlphabetNumberUpperCase(r3)
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            r2[r6] = r7
            goto L_0x0124
        L_0x00f4:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.StringBuilder r7 = r7.append(r4)
            java.lang.String r8 = com.itextpdf.kernel.numbering.RomanNumbering.toRomanLowerCase(r3)
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            r2[r6] = r7
            goto L_0x0124
        L_0x010c:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.StringBuilder r7 = r7.append(r4)
            java.lang.String r8 = com.itextpdf.kernel.numbering.RomanNumbering.toRomanUpperCase(r3)
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            r2[r6] = r7
        L_0x0124:
            int r3 = r3 + 1
            int r6 = r6 + 1
            goto L_0x0028
        L_0x012a:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.pdf.PdfDocument.getPageLabels():java.lang.String[]");
    }

    public boolean hasOutlines() {
        return this.catalog.hasOutlines();
    }

    public void setUserProperties(boolean userProperties) {
        updateValueInMarkInfoDict(PdfName.UserProperties, userProperties ? PdfBoolean.TRUE : PdfBoolean.FALSE);
    }

    public PdfFont getFont(PdfDictionary dictionary) {
        if (dictionary.getIndirectReference() == null) {
            throw new AssertionError();
        } else if (this.documentFonts.containsKey(dictionary.getIndirectReference())) {
            return this.documentFonts.get(dictionary.getIndirectReference());
        } else {
            return addFont(PdfFontFactory.createFont(dictionary));
        }
    }

    public PdfFont getDefaultFont() {
        if (this.defaultFont == null) {
            try {
                PdfFont createFont = PdfFontFactory.createFont();
                this.defaultFont = createFont;
                if (this.writer != null) {
                    createFont.makeIndirect(this);
                }
            } catch (IOException e) {
                LoggerFactory.getLogger((Class<?>) PdfDocument.class).error(LogMessageConstant.EXCEPTION_WHILE_CREATING_DEFAULT_FONT, (Throwable) e);
                this.defaultFont = null;
            }
        }
        return this.defaultFont;
    }

    public PdfFont addFont(PdfFont font) {
        font.makeIndirect(this);
        font.setForbidRelease();
        this.documentFonts.put(((PdfDictionary) font.getPdfObject()).getIndirectReference(), font);
        return font;
    }

    public boolean registerProduct(ProductInfo productInfo) {
        return this.fingerPrint.registerProduct(productInfo);
    }

    public FingerPrint getFingerPrint() {
        return this.fingerPrint;
    }

    public PdfFont findFont(String fontProgram, String encoding) {
        for (PdfFont font : this.documentFonts.values()) {
            if (!font.isFlushed() && font.isBuiltWith(fontProgram, encoding)) {
                return font;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public PdfXrefTable getXref() {
        return this.xref;
    }

    /* access modifiers changed from: package-private */
    public boolean isDocumentFont(PdfIndirectReference indRef) {
        return indRef != null && this.documentFonts.containsKey(indRef);
    }

    /* access modifiers changed from: protected */
    public void initTagStructureContext() {
        this.tagStructureContext = new TagStructureContext(this);
    }

    /* access modifiers changed from: protected */
    public void storeLinkAnnotation(PdfPage page, PdfLinkAnnotation annotation) {
        List<PdfLinkAnnotation> pageAnnotations = this.linkAnnotations.get(page);
        if (pageAnnotations == null) {
            pageAnnotations = new ArrayList<>();
            this.linkAnnotations.put(page, pageAnnotations);
        }
        pageAnnotations.add(annotation);
    }

    /* access modifiers changed from: protected */
    public void checkIsoConformance() {
    }

    /* access modifiers changed from: protected */
    public void markObjectAsMustBeFlushed(PdfObject pdfObject) {
        if (pdfObject.getIndirectReference() != null) {
            pdfObject.getIndirectReference().setState(32);
        }
    }

    /* access modifiers changed from: protected */
    public void flushObject(PdfObject pdfObject, boolean canBeInObjStm) throws IOException {
        this.writer.flushObject(pdfObject, canBeInObjStm);
    }

    /* access modifiers changed from: protected */
    public void open(PdfVersion newPdfVersion) {
        PdfNumber r;
        PdfString pdfString;
        PdfReader pdfReader;
        this.fingerPrint = new FingerPrint();
        try {
            EventCounterHandler.getInstance().onEvent(CoreEvent.PROCESS, this.properties.metaInfo, getClass());
            PdfReader pdfReader2 = this.reader;
            if (pdfReader2 != null) {
                if (pdfReader2.pdfDocument == null) {
                    this.reader.pdfDocument = this;
                    MemoryLimitsAwareHandler memoryLimitsAwareHandler2 = this.reader.properties.memoryLimitsAwareHandler;
                    this.memoryLimitsAwareHandler = memoryLimitsAwareHandler2;
                    if (memoryLimitsAwareHandler2 == null) {
                        this.memoryLimitsAwareHandler = new MemoryLimitsAwareHandler(this.reader.tokens.getSafeFile().length());
                    }
                    this.reader.readPdf();
                    for (ICounter counter : getCounters()) {
                        counter.onDocumentRead(this.reader.getFileLength());
                    }
                    this.pdfVersion = this.reader.headerPdfVersion;
                    this.trailer = new PdfDictionary(this.reader.trailer);
                    PdfArray id = this.reader.trailer.getAsArray(PdfName.f1341ID);
                    if (id != null) {
                        if (id.size() == 2) {
                            this.originalDocumentId = id.getAsString(0);
                            this.modifiedDocumentId = id.getAsString(1);
                        }
                        if (this.originalDocumentId == null || this.modifiedDocumentId == null) {
                            LoggerFactory.getLogger((Class<?>) PdfDocument.class).error(LogMessageConstant.DOCUMENT_IDS_ARE_CORRUPTED);
                        }
                    }
                    PdfCatalog pdfCatalog = new PdfCatalog((PdfDictionary) this.trailer.get(PdfName.Root, true));
                    this.catalog = pdfCatalog;
                    if (((PdfDictionary) pdfCatalog.getPdfObject()).containsKey(PdfName.Version)) {
                        PdfVersion catalogVersion = PdfVersion.fromPdfName(((PdfDictionary) this.catalog.getPdfObject()).getAsName(PdfName.Version));
                        if (catalogVersion.compareTo(this.pdfVersion) > 0) {
                            this.pdfVersion = catalogVersion;
                        }
                    }
                    PdfStream xmpMetadataStream = ((PdfDictionary) this.catalog.getPdfObject()).getAsStream(PdfName.Metadata);
                    if (xmpMetadataStream != null) {
                        byte[] bytes = xmpMetadataStream.getBytes();
                        this.xmpMetadata = bytes;
                        try {
                            this.reader.pdfAConformanceLevel = PdfAConformanceLevel.getConformanceLevel(XMPMetaFactory.parseFromBuffer(bytes));
                        } catch (XMPException e) {
                        }
                    }
                    PdfObject infoDict = this.trailer.get(PdfName.Info);
                    PdfDocumentInfo pdfDocumentInfo = new PdfDocumentInfo(infoDict instanceof PdfDictionary ? (PdfDictionary) infoDict : new PdfDictionary(), this);
                    this.info = pdfDocumentInfo;
                    XmpMetaInfoConverter.appendMetadataToInfo(this.xmpMetadata, pdfDocumentInfo);
                    PdfDictionary str = ((PdfDictionary) this.catalog.getPdfObject()).getAsDictionary(PdfName.StructTreeRoot);
                    if (str != null) {
                        tryInitTagStructure(str);
                    }
                    if (this.properties.appendMode) {
                        if (this.reader.hasRebuiltXref() || this.reader.hasFixedXref()) {
                            throw new PdfException(PdfException.f1238x22deddac);
                        }
                    }
                } else {
                    throw new PdfException(PdfException.PdfReaderHasBeenAlreadyUtilized);
                }
            }
            this.xref.initFreeReferencesList(this);
            if (this.writer != null) {
                PdfReader pdfReader3 = this.reader;
                if (pdfReader3 != null && pdfReader3.hasXrefStm() && this.writer.properties.isFullCompression == null) {
                    this.writer.properties.isFullCompression = true;
                }
                PdfReader pdfReader4 = this.reader;
                if (pdfReader4 != null) {
                    if (!pdfReader4.isOpenedWithFullPermission()) {
                        throw new BadPasswordException(BadPasswordException.PdfReaderNotOpenedWithOwnerPassword);
                    }
                }
                if (this.reader != null && this.properties.preserveEncryption) {
                    this.writer.crypto = this.reader.decrypt;
                }
                this.writer.document = this;
                if (this.reader == null) {
                    this.catalog = new PdfCatalog(this);
                    this.info = new PdfDocumentInfo(this).addCreationDate();
                }
                updateProducerInInfoDictionary();
                this.info.addModDate();
                PdfDictionary pdfDictionary = new PdfDictionary();
                this.trailer = pdfDictionary;
                pdfDictionary.put(PdfName.Root, ((PdfDictionary) this.catalog.getPdfObject()).getIndirectReference());
                this.trailer.put(PdfName.Info, this.info.getPdfObject().getIndirectReference());
                PdfReader pdfReader5 = this.reader;
                if (pdfReader5 != null && pdfReader5.trailer.containsKey(PdfName.f1341ID)) {
                    this.trailer.put(PdfName.f1341ID, this.reader.trailer.get(PdfName.f1341ID));
                }
                if (this.writer.properties != null) {
                    PdfString readerModifiedId = this.modifiedDocumentId;
                    if (this.writer.properties.initialDocumentId != null && ((pdfReader = this.reader) == null || pdfReader.decrypt == null || (!this.properties.appendMode && !this.properties.preserveEncryption))) {
                        this.originalDocumentId = this.writer.properties.initialDocumentId;
                    }
                    if (this.writer.properties.modifiedDocumentId != null) {
                        this.modifiedDocumentId = this.writer.properties.modifiedDocumentId;
                    }
                    if (this.originalDocumentId == null && (pdfString = this.modifiedDocumentId) != null) {
                        this.originalDocumentId = pdfString;
                    }
                    if (this.modifiedDocumentId == null) {
                        if (this.originalDocumentId == null) {
                            this.originalDocumentId = new PdfString(PdfEncryption.generateNewDocumentId());
                        }
                        this.modifiedDocumentId = this.originalDocumentId;
                    }
                    if (this.writer.properties.modifiedDocumentId == null && this.modifiedDocumentId.equals(readerModifiedId)) {
                        this.modifiedDocumentId = new PdfString(PdfEncryption.generateNewDocumentId());
                    }
                }
                if (this.originalDocumentId == null) {
                    throw new AssertionError();
                } else if (this.modifiedDocumentId == null) {
                    throw new AssertionError();
                }
            }
            if (this.properties.appendMode) {
                PdfReader pdfReader6 = this.reader;
                if (pdfReader6 != null) {
                    RandomAccessFileOrArray file = pdfReader6.tokens.getSafeFile();
                    byte[] buffer = new byte[8192];
                    while (true) {
                        int read = file.read(buffer);
                        int n = read;
                        if (read <= 0) {
                            break;
                        }
                        this.writer.write(buffer, 0, n);
                    }
                    file.close();
                    this.writer.write(10);
                    overrideFullCompressionInWriterProperties(this.writer.properties, this.reader.hasXrefStm());
                    this.writer.crypto = this.reader.decrypt;
                    if (newPdfVersion != null && this.pdfVersion.compareTo(PdfVersion.PDF_1_4) >= 0 && newPdfVersion.compareTo(this.reader.headerPdfVersion) > 0) {
                        this.catalog.put(PdfName.Version, newPdfVersion.toPdfName());
                        this.catalog.setModified();
                        this.pdfVersion = newPdfVersion;
                        return;
                    }
                    return;
                }
                throw new AssertionError();
            }
            PdfWriter pdfWriter = this.writer;
            if (pdfWriter != null) {
                if (newPdfVersion != null) {
                    this.pdfVersion = newPdfVersion;
                }
                pdfWriter.writeHeader();
                if (this.writer.crypto == null) {
                    this.writer.initCryptoIfSpecified(this.pdfVersion);
                }
                if (this.writer.crypto == null) {
                    return;
                }
                if (this.writer.crypto.getCryptoMode() < 3) {
                    VersionConforming.validatePdfVersionForDeprecatedFeatureLogWarn(this, PdfVersion.PDF_2_0, VersionConforming.DEPRECATED_ENCRYPTION_ALGORITHMS);
                } else if (this.writer.crypto.getCryptoMode() == 3 && (r = ((PdfDictionary) this.writer.crypto.getPdfObject()).getAsNumber(PdfName.f1376R)) != null && r.intValue() == 5) {
                    VersionConforming.validatePdfVersionForDeprecatedFeatureLogWarn(this, PdfVersion.PDF_2_0, VersionConforming.DEPRECATED_AES256_REVISION);
                }
            }
        } catch (IOException e2) {
            throw new PdfException(PdfException.CannotOpenDocument, e2, this);
        }
    }

    /* access modifiers changed from: protected */
    public void addCustomMetadataExtensions(XMPMeta xmpMeta) {
    }

    /* access modifiers changed from: protected */
    public void updateXmpMetadata() {
        try {
            if (this.xmpMetadata != null || this.writer.properties.addXmpMetadata || this.pdfVersion.compareTo(PdfVersion.PDF_2_0) >= 0) {
                setXmpMetadata(updateDefaultXmpMetadata());
            }
        } catch (XMPException e) {
            LoggerFactory.getLogger((Class<?>) PdfDocument.class).error(LogMessageConstant.EXCEPTION_WHILE_UPDATING_XMPMETADATA, (Throwable) e);
        }
    }

    /* access modifiers changed from: protected */
    public XMPMeta updateDefaultXmpMetadata() throws XMPException {
        XMPMeta xmpMeta = XMPMetaFactory.parseFromBuffer(getXmpMetadata(true));
        XmpMetaInfoConverter.appendDocumentInfoToMetadata(this.info, xmpMeta);
        if (isTagged() && this.writer.properties.addUAXmpMetadata && !isXmpMetaHasProperty(xmpMeta, XMPConst.NS_PDFUA_ID, "part")) {
            xmpMeta.setPropertyInteger(XMPConst.NS_PDFUA_ID, "part", 1, new PropertyOptions(1073741824));
        }
        return xmpMeta;
    }

    /* access modifiers changed from: protected */
    public Collection<PdfFont> getDocumentFonts() {
        return this.documentFonts.values();
    }

    /* access modifiers changed from: protected */
    public void flushFonts() {
        if (this.properties.appendMode) {
            for (PdfFont font : getDocumentFonts()) {
                if (((PdfDictionary) font.getPdfObject()).checkState(64) || ((PdfDictionary) font.getPdfObject()).getIndirectReference().checkState(8)) {
                    font.flush();
                }
            }
            return;
        }
        for (PdfFont font2 : getDocumentFonts()) {
            font2.flush();
        }
    }

    /* access modifiers changed from: protected */
    public void checkAndAddPage(int index, PdfPage page) {
        if (page.isFlushed()) {
            throw new PdfException(PdfException.FlushedPageCannotBeAddedOrInserted, (Object) page);
        } else if (page.getDocument() == null || this == page.getDocument()) {
            this.catalog.getPageTree().addPage(index, page);
        } else {
            throw new PdfException(PdfException.Page1CannotBeAddedToDocument2BecauseItBelongsToDocument3).setMessageParams(page, this, page.getDocument());
        }
    }

    /* access modifiers changed from: protected */
    public void checkAndAddPage(PdfPage page) {
        if (page.isFlushed()) {
            throw new PdfException(PdfException.FlushedPageCannotBeAddedOrInserted, (Object) page);
        } else if (page.getDocument() == null || this == page.getDocument()) {
            this.catalog.getPageTree().addPage(page);
        } else {
            throw new PdfException(PdfException.Page1CannotBeAddedToDocument2BecauseItBelongsToDocument3).setMessageParams(page, this, page.getDocument());
        }
    }

    /* access modifiers changed from: protected */
    public void checkClosingStatus() {
        if (this.closed) {
            throw new PdfException(PdfException.DocumentClosedItIsImpossibleToExecuteAction);
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public List<ICounter> getCounters() {
        return CounterManager.getInstance().getCounters(PdfDocument.class);
    }

    /* access modifiers changed from: protected */
    public IPdfPageFactory getPageFactory() {
        return pdfPageFactory;
    }

    /* access modifiers changed from: package-private */
    public final VersionInfo getVersionInfo() {
        return this.versionInfo;
    }

    /* access modifiers changed from: package-private */
    public boolean hasAcroForm() {
        return ((PdfDictionary) getCatalog().getPdfObject()).containsKey(PdfName.AcroForm);
    }

    private void updateProducerInInfoDictionary() {
        String producer;
        String producer2 = null;
        if (this.reader == null) {
            producer = this.versionInfo.getVersion();
        } else {
            if (this.info.getPdfObject().containsKey(PdfName.Producer)) {
                producer2 = this.info.getPdfObject().getAsString(PdfName.Producer).toUnicodeString();
            }
            producer = addModifiedPostfix(producer2);
        }
        this.info.getPdfObject().put(PdfName.Producer, new PdfString(producer));
    }

    /* access modifiers changed from: protected */
    public void tryInitTagStructure(PdfDictionary str) {
        try {
            this.structTreeRoot = new PdfStructTreeRoot(str, this);
            this.structParentIndex = getStructTreeRoot().getParentTreeNextKey();
        } catch (Exception ex) {
            this.structTreeRoot = null;
            this.structParentIndex = -1;
            LoggerFactory.getLogger((Class<?>) PdfDocument.class).error(LogMessageConstant.TAG_STRUCTURE_INIT_FAILED, (Throwable) ex);
        }
    }

    private void tryFlushTagStructure(boolean isAppendMode) {
        try {
            TagStructureContext tagStructureContext2 = this.tagStructureContext;
            if (tagStructureContext2 != null) {
                tagStructureContext2.prepareToDocumentClosing();
            }
            if (!isAppendMode || ((PdfDictionary) this.structTreeRoot.getPdfObject()).isModified()) {
                this.structTreeRoot.flush();
            }
        } catch (Exception ex) {
            throw new PdfException(PdfException.TagStructureFlushingFailedItMightBeCorrupted, (Throwable) ex);
        }
    }

    private void updateValueInMarkInfoDict(PdfName key, PdfObject value) {
        PdfDictionary markInfo = ((PdfDictionary) this.catalog.getPdfObject()).getAsDictionary(PdfName.MarkInfo);
        if (markInfo == null) {
            markInfo = new PdfDictionary();
            ((PdfDictionary) this.catalog.getPdfObject()).put(PdfName.MarkInfo, markInfo);
        }
        markInfo.put(key, value);
    }

    private void removeUnusedWidgetsFromFields(PdfPage page) {
        if (!page.isFlushed()) {
            for (PdfAnnotation annot : page.getAnnotations()) {
                if (annot.getSubtype().equals(PdfName.Widget)) {
                    ((PdfWidgetAnnotation) annot).releaseFormFieldFromWidgetAnnotation();
                }
            }
        }
    }

    /* JADX WARNING: type inference failed for: r13v6, types: [com.itextpdf.kernel.pdf.PdfObject] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void copyLinkAnnotations(com.itextpdf.kernel.pdf.PdfDocument r17, java.util.Map<com.itextpdf.kernel.pdf.PdfPage, com.itextpdf.kernel.pdf.PdfPage> r18) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r2 = r18
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            com.itextpdf.kernel.pdf.PdfName r4 = com.itextpdf.kernel.pdf.PdfName.Dest
            r3.add(r4)
            com.itextpdf.kernel.pdf.PdfName r4 = com.itextpdf.kernel.pdf.PdfName.f1287A
            r3.add(r4)
            java.util.LinkedHashMap<com.itextpdf.kernel.pdf.PdfPage, java.util.List<com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation>> r4 = r0.linkAnnotations
            java.util.Set r4 = r4.entrySet()
            java.util.Iterator r4 = r4.iterator()
        L_0x001f:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x00cd
            java.lang.Object r5 = r4.next()
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5
            java.lang.Object r6 = r5.getValue()
            java.util.List r6 = (java.util.List) r6
            java.util.Iterator r6 = r6.iterator()
        L_0x0035:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L_0x00cb
            java.lang.Object r7 = r6.next()
            com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation r7 = (com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation) r7
            r8 = 1
            r9 = 0
            r10 = 0
            com.itextpdf.kernel.pdf.PdfObject r11 = r7.getDestinationObject()
            r12 = 1
            if (r11 == 0) goto L_0x005a
            com.itextpdf.kernel.pdf.PdfCatalog r14 = r16.getCatalog()
            com.itextpdf.kernel.pdf.navigation.PdfDestination r9 = r14.copyDestination(r11, r2, r1)
            if (r9 == 0) goto L_0x0057
            r14 = 1
            goto L_0x0058
        L_0x0057:
            r14 = 0
        L_0x0058:
            r8 = r14
            goto L_0x00a1
        L_0x005a:
            com.itextpdf.kernel.pdf.PdfDictionary r14 = r7.getAction()
            if (r14 == 0) goto L_0x00a1
            com.itextpdf.kernel.pdf.PdfName r15 = com.itextpdf.kernel.pdf.PdfName.GoTo
            com.itextpdf.kernel.pdf.PdfName r13 = com.itextpdf.kernel.pdf.PdfName.f1385S
            com.itextpdf.kernel.pdf.PdfObject r13 = r14.get(r13)
            boolean r13 = r15.equals(r13)
            if (r13 == 0) goto L_0x0099
            com.itextpdf.kernel.pdf.PdfName[] r13 = new com.itextpdf.kernel.pdf.PdfName[r12]
            com.itextpdf.kernel.pdf.PdfName r15 = com.itextpdf.kernel.pdf.PdfName.f1312D
            r12 = 0
            r13[r12] = r15
            java.util.List r13 = java.util.Arrays.asList(r13)
            com.itextpdf.kernel.pdf.PdfDictionary r10 = r14.copyTo(r1, r13, r12)
            com.itextpdf.kernel.pdf.PdfCatalog r12 = r16.getCatalog()
            com.itextpdf.kernel.pdf.PdfName r13 = com.itextpdf.kernel.pdf.PdfName.f1312D
            com.itextpdf.kernel.pdf.PdfObject r13 = r14.get(r13)
            com.itextpdf.kernel.pdf.navigation.PdfDestination r12 = r12.copyDestination(r13, r2, r1)
            if (r12 == 0) goto L_0x0097
            com.itextpdf.kernel.pdf.PdfName r13 = com.itextpdf.kernel.pdf.PdfName.f1312D
            com.itextpdf.kernel.pdf.PdfObject r15 = r12.getPdfObject()
            r10.put(r13, r15)
            goto L_0x0098
        L_0x0097:
            r8 = 0
        L_0x0098:
            goto L_0x00a1
        L_0x0099:
            r12 = 0
            com.itextpdf.kernel.pdf.PdfObject r13 = r14.copyTo(r1, r12)
            r10 = r13
            com.itextpdf.kernel.pdf.PdfDictionary r10 = (com.itextpdf.kernel.pdf.PdfDictionary) r10
        L_0x00a1:
            if (r8 == 0) goto L_0x00c9
            com.itextpdf.kernel.pdf.PdfObject r12 = r7.getPdfObject()
            com.itextpdf.kernel.pdf.PdfDictionary r12 = (com.itextpdf.kernel.pdf.PdfDictionary) r12
            r13 = 1
            com.itextpdf.kernel.pdf.PdfDictionary r12 = r12.copyTo(r1, r3, r13)
            com.itextpdf.kernel.pdf.annot.PdfAnnotation r12 = com.itextpdf.kernel.pdf.annot.PdfAnnotation.makeAnnotation(r12)
            com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation r12 = (com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation) r12
            if (r9 == 0) goto L_0x00b9
            r12.setDestination((com.itextpdf.kernel.pdf.navigation.PdfDestination) r9)
        L_0x00b9:
            if (r10 == 0) goto L_0x00be
            r12.setAction((com.itextpdf.kernel.pdf.PdfDictionary) r10)
        L_0x00be:
            java.lang.Object r13 = r5.getKey()
            com.itextpdf.kernel.pdf.PdfPage r13 = (com.itextpdf.kernel.pdf.PdfPage) r13
            r14 = -1
            r15 = 0
            r13.addAnnotation(r14, r12, r15)
        L_0x00c9:
            goto L_0x0035
        L_0x00cb:
            goto L_0x001f
        L_0x00cd:
            java.util.LinkedHashMap<com.itextpdf.kernel.pdf.PdfPage, java.util.List<com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation>> r4 = r0.linkAnnotations
            r4.clear()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.pdf.PdfDocument.copyLinkAnnotations(com.itextpdf.kernel.pdf.PdfDocument, java.util.Map):void");
    }

    private void copyOutlines(Set<PdfOutline> outlines, PdfDocument toDocument, Map<PdfPage, PdfPage> page2page) {
        PdfOutline rootOutline;
        HashSet hashSet = new HashSet();
        hashSet.addAll(outlines);
        for (PdfOutline outline : outlines) {
            getAllOutlinesToCopy(outline, hashSet);
        }
        PdfOutline rootOutline2 = toDocument.getOutlines(false);
        if (rootOutline2 == null) {
            PdfOutline rootOutline3 = new PdfOutline(toDocument);
            rootOutline3.setTitle("Outlines");
            rootOutline = rootOutline3;
        } else {
            rootOutline = rootOutline2;
        }
        cloneOutlines(hashSet, rootOutline, getOutlines(false), page2page, toDocument);
    }

    private void getAllOutlinesToCopy(PdfOutline outline, Set<PdfOutline> outlinesToCopy) {
        PdfOutline parent = outline.getParent();
        if (!"Outlines".equals(parent.getTitle()) && !outlinesToCopy.contains(parent)) {
            outlinesToCopy.add(parent);
            getAllOutlinesToCopy(parent, outlinesToCopy);
        }
    }

    private void cloneOutlines(Set<PdfOutline> outlinesToCopy, PdfOutline newParent, PdfOutline oldParent, Map<PdfPage, PdfPage> page2page, PdfDocument toDocument) {
        PdfDestination copiedDest;
        if (oldParent != null) {
            for (PdfOutline outline : oldParent.getAllChildren()) {
                if (outlinesToCopy.contains(outline)) {
                    if (outline.getDestination() != null) {
                        copiedDest = getCatalog().copyDestination(outline.getDestination().getPdfObject(), page2page, toDocument);
                    } else {
                        copiedDest = null;
                    }
                    PdfOutline child = newParent.addOutline(outline.getTitle());
                    if (copiedDest != null) {
                        child.addDestination(copiedDest);
                    }
                    cloneOutlines(outlinesToCopy, child, outline, page2page, toDocument);
                }
            }
        }
    }

    private void ensureTreeRootAddedToNames(PdfObject treeRoot, PdfName treeType) {
        PdfDictionary names = ((PdfDictionary) this.catalog.getPdfObject()).getAsDictionary(PdfName.Names);
        if (names == null) {
            names = new PdfDictionary();
            this.catalog.put(PdfName.Names, names);
            names.makeIndirect(this);
        }
        names.put(treeType, treeRoot);
        names.setModified();
    }

    private byte[] getSerializedBytes() {
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
            ObjectOutputStream oos2 = new ObjectOutputStream(bos2);
            oos2.writeObject(this);
            oos2.flush();
            byte[] byteArray = bos2.toByteArray();
            try {
                oos2.close();
            } catch (IOException e) {
            }
            try {
                bos2.close();
            } catch (IOException e2) {
            }
            return byteArray;
        } catch (Exception e3) {
            LoggerFactory.getLogger((Class<?>) PdfDocument.class).warn(LogMessageConstant.DOCUMENT_SERIALIZATION_EXCEPTION_RAISED, (Throwable) e3);
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e4) {
                }
            }
            if (bos == null) {
                return null;
            }
            try {
                bos.close();
                return null;
            } catch (IOException e5) {
                return null;
            }
        } catch (Throwable th) {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e6) {
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e7) {
                }
            }
            throw th;
        }
    }

    /* access modifiers changed from: private */
    public long getDocumentId() {
        return this.documentId;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        if (this.tagStructureContext != null) {
            LoggerFactory.getLogger(getClass()).warn(LogMessageConstant.TAG_STRUCTURE_CONTEXT_WILL_BE_REINITIALIZED_ON_SERIALIZATION);
        }
        out.defaultWriteObject();
    }

    private boolean writerHasEncryption() {
        return this.writer.properties.isStandardEncryptionUsed() || this.writer.properties.isPublicKeyEncryptionUsed();
    }

    static class IndirectRefDescription {
        final long docId;
        final int genNr;
        final int objNr;

        IndirectRefDescription(PdfIndirectReference reference) {
            this.docId = reference.getDocument().getDocumentId();
            this.objNr = reference.getObjNumber();
            this.genNr = reference.getGenNumber();
        }

        public int hashCode() {
            return (((((int) this.docId) * 31) + this.objNr) * 31) + this.genNr;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            IndirectRefDescription that = (IndirectRefDescription) o;
            if (this.docId == that.docId && this.objNr == that.objNr && this.genNr == that.genNr) {
                return true;
            }
            return false;
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        if (this.versionInfo == null) {
            this.versionInfo = Version.getInstance().getInfo();
        }
        this.eventDispatcher = new EventDispatcher();
    }

    private String addModifiedPostfix(String producer) {
        StringBuilder buf;
        if (producer == null || !this.versionInfo.getVersion().contains(this.versionInfo.getProduct())) {
            return this.versionInfo.getVersion();
        }
        int idx = producer.indexOf("; modified using");
        if (idx == -1) {
            buf = new StringBuilder(producer);
        } else {
            buf = new StringBuilder(producer.substring(0, idx));
        }
        buf.append("; modified using ");
        buf.append(this.versionInfo.getVersion());
        return buf.toString();
    }

    private static void overrideFullCompressionInWriterProperties(WriterProperties properties2, boolean readerHasXrefStream) {
        Class<PdfDocument> cls = PdfDocument.class;
        if (Boolean.TRUE == properties2.isFullCompression && !readerHasXrefStream) {
            LoggerFactory.getLogger((Class<?>) cls).warn(KernelLogMessageConstant.FULL_COMPRESSION_APPEND_MODE_XREF_TABLE_INCONSISTENCY);
        } else if (Boolean.FALSE == properties2.isFullCompression && readerHasXrefStream) {
            LoggerFactory.getLogger((Class<?>) cls).warn(KernelLogMessageConstant.FULL_COMPRESSION_APPEND_MODE_XREF_STREAM_INCONSISTENCY);
        }
        properties2.isFullCompression = Boolean.valueOf(readerHasXrefStream);
    }

    private static boolean isXmpMetaHasProperty(XMPMeta xmpMeta, String schemaNS, String propName) throws XMPException {
        return xmpMeta.getProperty(schemaNS, propName) != null;
    }
}
