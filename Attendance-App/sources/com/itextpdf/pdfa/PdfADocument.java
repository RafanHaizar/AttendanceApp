package com.itextpdf.pdfa;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.log.CounterManager;
import com.itextpdf.kernel.log.ICounter;
import com.itextpdf.kernel.pdf.DocumentProperties;
import com.itextpdf.kernel.pdf.IPdfPageFactory;
import com.itextpdf.kernel.pdf.IsoKey;
import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfOutputIntent;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfResources;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfXrefTable;
import com.itextpdf.kernel.pdf.StampingProperties;
import com.itextpdf.kernel.pdf.canvas.CanvasGraphicsState;
import com.itextpdf.kernel.pdf.tagutils.TagStructureContext;
import com.itextpdf.kernel.xmp.XMPConst;
import com.itextpdf.kernel.xmp.XMPException;
import com.itextpdf.kernel.xmp.XMPMeta;
import com.itextpdf.kernel.xmp.XMPMetaFactory;
import com.itextpdf.kernel.xmp.XMPUtils;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.pdfa.checker.PdfAChecker;
import java.io.IOException;
import java.util.List;
import org.slf4j.LoggerFactory;

public class PdfADocument extends PdfDocument {
    private static IPdfPageFactory pdfAPageFactory = new PdfAPageFactory();
    private static final long serialVersionUID = -5908390625367471894L;
    private boolean alreadyLoggedThatObjectFlushingWasNotPerformed;
    private boolean alreadyLoggedThatPageFlushingWasNotPerformed;
    protected PdfAChecker checker;

    public PdfADocument(PdfWriter writer, PdfAConformanceLevel conformanceLevel, PdfOutputIntent outputIntent) {
        this(writer, conformanceLevel, outputIntent, new DocumentProperties());
    }

    public PdfADocument(PdfWriter writer, PdfAConformanceLevel conformanceLevel, PdfOutputIntent outputIntent, DocumentProperties properties) {
        super(writer, properties);
        this.alreadyLoggedThatObjectFlushingWasNotPerformed = false;
        this.alreadyLoggedThatPageFlushingWasNotPerformed = false;
        setChecker(conformanceLevel);
        addOutputIntent(outputIntent);
    }

    public PdfADocument(PdfReader reader, PdfWriter writer) {
        this(reader, writer, new StampingProperties());
    }

    public PdfADocument(PdfReader reader, PdfWriter writer, StampingProperties properties) {
        super(reader, writer, properties);
        this.alreadyLoggedThatObjectFlushingWasNotPerformed = false;
        this.alreadyLoggedThatPageFlushingWasNotPerformed = false;
        byte[] existingXmpMetadata = getXmpMetadata();
        if (existingXmpMetadata != null) {
            try {
                PdfAConformanceLevel conformanceLevel = PdfAConformanceLevel.getConformanceLevel(XMPMetaFactory.parseFromBuffer(existingXmpMetadata));
                if (conformanceLevel != null) {
                    setChecker(conformanceLevel);
                    return;
                }
                throw new PdfAConformanceException(PdfAConformanceException.f1564x75e3db75);
            } catch (XMPException e) {
                throw new PdfAConformanceException(PdfAConformanceException.f1564x75e3db75);
            }
        } else {
            throw new PdfAConformanceException(PdfAConformanceException.f1564x75e3db75);
        }
    }

    public void checkIsoConformance(Object obj, IsoKey key) {
        checkIsoConformance(obj, key, (PdfResources) null, (PdfStream) null);
    }

    @Deprecated
    public void checkIsoConformance(Object obj, IsoKey key, PdfResources resources) {
        checkIsoConformance(obj, key, resources, (PdfStream) null);
    }

    public void checkIsoConformance(Object obj, IsoKey key, PdfResources resources, PdfStream contentStream) {
        PdfDictionary currentColorSpaces = null;
        if (resources != null) {
            currentColorSpaces = ((PdfDictionary) resources.getPdfObject()).getAsDictionary(PdfName.ColorSpace);
        }
        switch (C14791.$SwitchMap$com$itextpdf$kernel$pdf$IsoKey[key.ordinal()]) {
            case 1:
                this.checker.checkCanvasStack(((Character) obj).charValue());
                return;
            case 2:
                this.checker.checkPdfObject((PdfObject) obj);
                return;
            case 3:
                this.checker.checkRenderingIntent((PdfName) obj);
                return;
            case 4:
                this.checker.checkInlineImage((PdfStream) obj, currentColorSpaces);
                return;
            case 5:
                this.checker.checkExtGState((CanvasGraphicsState) obj, contentStream);
                return;
            case 6:
                this.checker.checkColor(((CanvasGraphicsState) obj).getFillColor(), currentColorSpaces, true, contentStream);
                return;
            case 7:
                this.checker.checkSinglePage((PdfPage) obj);
                return;
            case 8:
                this.checker.checkColor(((CanvasGraphicsState) obj).getStrokeColor(), currentColorSpaces, false, contentStream);
                return;
            case 9:
                this.checker.checkTagStructureElement((PdfObject) obj);
                return;
            case 10:
                this.checker.checkFontGlyphs(((CanvasGraphicsState) obj).getFont(), contentStream);
                return;
            case 11:
                this.checker.checkXrefTable((PdfXrefTable) obj);
                return;
            default:
                return;
        }
    }

    /* renamed from: com.itextpdf.pdfa.PdfADocument$1 */
    static /* synthetic */ class C14791 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$kernel$pdf$IsoKey;

        static {
            int[] iArr = new int[IsoKey.values().length];
            $SwitchMap$com$itextpdf$kernel$pdf$IsoKey = iArr;
            try {
                iArr[IsoKey.CANVAS_STACK.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$itextpdf$kernel$pdf$IsoKey[IsoKey.PDF_OBJECT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$itextpdf$kernel$pdf$IsoKey[IsoKey.RENDERING_INTENT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$itextpdf$kernel$pdf$IsoKey[IsoKey.INLINE_IMAGE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$itextpdf$kernel$pdf$IsoKey[IsoKey.EXTENDED_GRAPHICS_STATE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$itextpdf$kernel$pdf$IsoKey[IsoKey.FILL_COLOR.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$itextpdf$kernel$pdf$IsoKey[IsoKey.PAGE.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$itextpdf$kernel$pdf$IsoKey[IsoKey.STROKE_COLOR.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$itextpdf$kernel$pdf$IsoKey[IsoKey.TAG_STRUCTURE_ELEMENT.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$itextpdf$kernel$pdf$IsoKey[IsoKey.FONT_GLYPHS.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$itextpdf$kernel$pdf$IsoKey[IsoKey.XREF_TABLE.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
        }
    }

    public PdfAConformanceLevel getConformanceLevel() {
        return this.checker.getConformanceLevel();
    }

    /* access modifiers changed from: package-private */
    public void logThatPdfAPageFlushingWasNotPerformed() {
        if (!this.alreadyLoggedThatPageFlushingWasNotPerformed) {
            this.alreadyLoggedThatPageFlushingWasNotPerformed = true;
            LoggerFactory.getLogger((Class<?>) PdfADocument.class).warn(PdfALogMessageConstant.PDFA_PAGE_FLUSHING_WAS_NOT_PERFORMED);
        }
    }

    /* access modifiers changed from: protected */
    public void addCustomMetadataExtensions(XMPMeta xmpMeta) {
        if (isTagged()) {
            try {
                if (xmpMeta.getPropertyInteger(XMPConst.NS_PDFUA_ID, "part") != null) {
                    XMPUtils.appendProperties(XMPMetaFactory.parseFromString(PdfAXMPUtil.PDF_UA_EXTENSION), xmpMeta, true, false);
                }
            } catch (XMPException exc) {
                LoggerFactory.getLogger((Class<?>) PdfADocument.class).error(LogMessageConstant.EXCEPTION_WHILE_UPDATING_XMPMETADATA, (Throwable) exc);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void updateXmpMetadata() {
        try {
            XMPMeta xmpMeta = updateDefaultXmpMetadata();
            xmpMeta.setProperty(XMPConst.NS_PDFA_ID, "part", this.checker.getConformanceLevel().getPart());
            xmpMeta.setProperty(XMPConst.NS_PDFA_ID, XMPConst.CONFORMANCE, this.checker.getConformanceLevel().getConformance());
            addCustomMetadataExtensions(xmpMeta);
            setXmpMetadata(xmpMeta);
        } catch (XMPException e) {
            LoggerFactory.getLogger((Class<?>) PdfADocument.class).error(LogMessageConstant.EXCEPTION_WHILE_UPDATING_XMPMETADATA, (Throwable) e);
        }
    }

    /* access modifiers changed from: protected */
    public void checkIsoConformance() {
        this.checker.checkDocument(this.catalog);
    }

    /* access modifiers changed from: protected */
    public void flushObject(PdfObject pdfObject, boolean canBeInObjStm) throws IOException {
        markObjectAsMustBeFlushed(pdfObject);
        if (this.isClosing || this.checker.objectIsChecked(pdfObject)) {
            super.flushObject(pdfObject, canBeInObjStm);
        } else if (!this.alreadyLoggedThatObjectFlushingWasNotPerformed) {
            this.alreadyLoggedThatObjectFlushingWasNotPerformed = true;
            LoggerFactory.getLogger((Class<?>) PdfADocument.class).warn(PdfALogMessageConstant.PDFA_OBJECT_FLUSHING_WAS_NOT_PERFORMED);
        }
    }

    /* access modifiers changed from: protected */
    public void flushFonts() {
        for (PdfFont pdfFont : getDocumentFonts()) {
            this.checker.checkFont(pdfFont);
        }
        super.flushFonts();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setChecker(com.itextpdf.kernel.pdf.PdfAConformanceLevel r3) {
        /*
            r2 = this;
            java.lang.String r0 = r3.getPart()
            int r1 = r0.hashCode()
            switch(r1) {
                case 49: goto L_0x0020;
                case 50: goto L_0x0016;
                case 51: goto L_0x000c;
                default: goto L_0x000b;
            }
        L_0x000b:
            goto L_0x002a
        L_0x000c:
            java.lang.String r1 = "3"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000b
            r0 = 2
            goto L_0x002b
        L_0x0016:
            java.lang.String r1 = "2"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000b
            r0 = 1
            goto L_0x002b
        L_0x0020:
            java.lang.String r1 = "1"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000b
            r0 = 0
            goto L_0x002b
        L_0x002a:
            r0 = -1
        L_0x002b:
            switch(r0) {
                case 0: goto L_0x003f;
                case 1: goto L_0x0037;
                case 2: goto L_0x002f;
                default: goto L_0x002e;
            }
        L_0x002e:
            goto L_0x0047
        L_0x002f:
            com.itextpdf.pdfa.checker.PdfA3Checker r0 = new com.itextpdf.pdfa.checker.PdfA3Checker
            r0.<init>(r3)
            r2.checker = r0
            goto L_0x0047
        L_0x0037:
            com.itextpdf.pdfa.checker.PdfA2Checker r0 = new com.itextpdf.pdfa.checker.PdfA2Checker
            r0.<init>(r3)
            r2.checker = r0
            goto L_0x0047
        L_0x003f:
            com.itextpdf.pdfa.checker.PdfA1Checker r0 = new com.itextpdf.pdfa.checker.PdfA1Checker
            r0.<init>(r3)
            r2.checker = r0
        L_0x0047:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.pdfa.PdfADocument.setChecker(com.itextpdf.kernel.pdf.PdfAConformanceLevel):void");
    }

    /* access modifiers changed from: protected */
    public void initTagStructureContext() {
        this.tagStructureContext = new TagStructureContext(this, getPdfVersionForPdfA(this.checker.getConformanceLevel()));
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public List<ICounter> getCounters() {
        return CounterManager.getInstance().getCounters(PdfADocument.class);
    }

    /* access modifiers changed from: protected */
    public IPdfPageFactory getPageFactory() {
        return pdfAPageFactory;
    }

    /* access modifiers changed from: package-private */
    public boolean isClosing() {
        return this.isClosing;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.itextpdf.kernel.pdf.PdfVersion getPdfVersionForPdfA(com.itextpdf.kernel.pdf.PdfAConformanceLevel r2) {
        /*
            java.lang.String r0 = r2.getPart()
            int r1 = r0.hashCode()
            switch(r1) {
                case 49: goto L_0x0020;
                case 50: goto L_0x0016;
                case 51: goto L_0x000c;
                default: goto L_0x000b;
            }
        L_0x000b:
            goto L_0x002a
        L_0x000c:
            java.lang.String r1 = "3"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000b
            r0 = 2
            goto L_0x002b
        L_0x0016:
            java.lang.String r1 = "2"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000b
            r0 = 1
            goto L_0x002b
        L_0x0020:
            java.lang.String r1 = "1"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000b
            r0 = 0
            goto L_0x002b
        L_0x002a:
            r0 = -1
        L_0x002b:
            switch(r0) {
                case 0: goto L_0x0037;
                case 1: goto L_0x0034;
                case 2: goto L_0x0031;
                default: goto L_0x002e;
            }
        L_0x002e:
            com.itextpdf.kernel.pdf.PdfVersion r0 = com.itextpdf.kernel.pdf.PdfVersion.PDF_1_4
            goto L_0x003a
        L_0x0031:
            com.itextpdf.kernel.pdf.PdfVersion r0 = com.itextpdf.kernel.pdf.PdfVersion.PDF_1_7
            goto L_0x003a
        L_0x0034:
            com.itextpdf.kernel.pdf.PdfVersion r0 = com.itextpdf.kernel.pdf.PdfVersion.PDF_1_7
            goto L_0x003a
        L_0x0037:
            com.itextpdf.kernel.pdf.PdfVersion r0 = com.itextpdf.kernel.pdf.PdfVersion.PDF_1_4
        L_0x003a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.pdfa.PdfADocument.getPdfVersionForPdfA(com.itextpdf.kernel.pdf.PdfAConformanceLevel):com.itextpdf.kernel.pdf.PdfVersion");
    }
}
