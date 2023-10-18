package com.itextpdf.kernel.utils;

import com.itextpdf.kernel.counter.event.IMetaInfo;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.DocumentProperties;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfBoolean;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.kernel.pdf.PdfIndirectReference;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.ReaderProperties;
import com.itextpdf.kernel.pdf.StampingProperties;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.xmp.PdfConst;
import com.itextpdf.kernel.xmp.XMPConst;
import com.itextpdf.kernel.xmp.XMPMeta;
import com.itextpdf.kernel.xmp.XMPMetaFactory;
import com.itextpdf.kernel.xmp.XMPUtils;
import com.itextpdf.kernel.xmp.options.ParseOptions;
import com.itextpdf.kernel.xmp.options.SerializeOptions;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.font.PdfEncodings;
import com.itextpdf.p026io.util.FileUtil;
import com.itextpdf.p026io.util.GhostscriptHelper;
import com.itextpdf.p026io.util.ImageMagickHelper;
import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.p026io.util.UrlUtil;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.svg.SvgConstants;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class CompareTool {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String COPYRIGHT_REGEXP = "©\\d+-\\d+ iText Group NV";
    private static final String COPYRIGHT_REPLACEMENT = "©<copyright years> iText Group NV";
    private static final String DIFFERENT_PAGES = "File file:///<filename> differs on page <pagenumber>.";
    private static final String IGNORED_AREAS_PREFIX = "ignored_areas_";
    private static final String NEW_LINES = "\\r|\\n";
    private static final String UNEXPECTED_NUMBER_OF_PAGES = "Unexpected number of pages for <filename>.";
    private static final String VERSION_REGEXP = "(iText®( pdfX(FA|fa)| DITO)?|iTextSharp™) (\\d+\\.)+\\d+(-SNAPSHOT)?";
    private static final String VERSION_REPLACEMENT = "iText® <version>";
    private String cmpImage;
    private List<PdfIndirectReference> cmpPagesRef;
    private String cmpPdf;
    /* access modifiers changed from: private */
    public String cmpPdfName;
    private ReaderProperties cmpProps;
    private int compareByContentErrorsLimit = 1000;
    private String compareExec;
    private boolean encryptionCompareEnabled = false;
    private boolean generateCompareByContentXmlReport = false;
    private String gsExec;
    private IMetaInfo metaInfo;
    private String outImage;
    private List<PdfIndirectReference> outPagesRef;
    private String outPdf;
    /* access modifiers changed from: private */
    public String outPdfName;
    private ReaderProperties outProps;
    private boolean useCachedPagesForComparison = true;

    public CompareTool() {
    }

    CompareTool(String gsExec2, String compareExec2) {
        this.gsExec = gsExec2;
        this.compareExec = compareExec2;
    }

    public CompareResult compareByCatalog(PdfDocument outDocument, PdfDocument cmpDocument) throws IOException {
        List<PdfIndirectReference> list;
        CompareResult compareResult = new CompareResult(this.compareByContentErrorsLimit);
        ObjectPath catalogPath = new ObjectPath(((PdfDictionary) cmpDocument.getCatalog().getPdfObject()).getIndirectReference(), ((PdfDictionary) outDocument.getCatalog().getPdfObject()).getIndirectReference());
        compareDictionariesExtended((PdfDictionary) outDocument.getCatalog().getPdfObject(), (PdfDictionary) cmpDocument.getCatalog().getPdfObject(), catalogPath, compareResult, new LinkedHashSet<>(Arrays.asList(new PdfName[]{PdfName.Metadata})));
        if (this.cmpPagesRef == null || (list = this.outPagesRef) == null) {
            return compareResult;
        }
        if (list.size() != this.cmpPagesRef.size() && !compareResult.isMessageLimitReached()) {
            compareResult.addError(catalogPath, "Documents have different numbers of pages.");
        }
        for (int i = 0; i < Math.min(this.cmpPagesRef.size(), this.outPagesRef.size()) && !compareResult.isMessageLimitReached(); i++) {
            compareDictionariesExtended((PdfDictionary) this.outPagesRef.get(i).getRefersTo(), (PdfDictionary) this.cmpPagesRef.get(i).getRefersTo(), new ObjectPath(this.cmpPagesRef.get(i), this.outPagesRef.get(i)), compareResult);
        }
        return compareResult;
    }

    public CompareTool disableCachedPagesComparison() {
        this.useCachedPagesForComparison = false;
        return this;
    }

    public CompareTool setCompareByContentErrorsLimit(int compareByContentMaxErrorCount) {
        this.compareByContentErrorsLimit = compareByContentMaxErrorCount;
        return this;
    }

    public CompareTool setGenerateCompareByContentXmlReport(boolean generateCompareByContentXmlReport2) {
        this.generateCompareByContentXmlReport = generateCompareByContentXmlReport2;
        return this;
    }

    public void setEventCountingMetaInfo(IMetaInfo metaInfo2) {
        this.metaInfo = metaInfo2;
    }

    public CompareTool enableEncryptionCompare() {
        this.encryptionCompareEnabled = true;
        return this;
    }

    public ReaderProperties getOutReaderProperties() {
        if (this.outProps == null) {
            this.outProps = new ReaderProperties();
        }
        return this.outProps;
    }

    public ReaderProperties getCmpReaderProperties() {
        if (this.cmpProps == null) {
            this.cmpProps = new ReaderProperties();
        }
        return this.cmpProps;
    }

    public String compareVisually(String outPdf2, String cmpPdf2, String outPath, String differenceImagePrefix) throws InterruptedException, IOException {
        return compareVisually(outPdf2, cmpPdf2, outPath, differenceImagePrefix, (Map<Integer, List<Rectangle>>) null);
    }

    public String compareVisually(String outPdf2, String cmpPdf2, String outPath, String differenceImagePrefix, Map<Integer, List<Rectangle>> ignoredAreas) throws InterruptedException, IOException {
        init(outPdf2, cmpPdf2);
        System.out.println("Out pdf: " + UrlUtil.getNormalizedFileUriString(outPdf2));
        System.out.println("Cmp pdf: " + UrlUtil.getNormalizedFileUriString(cmpPdf2) + "\n");
        return compareVisually(outPath, differenceImagePrefix, ignoredAreas);
    }

    public String compareByContent(String outPdf2, String cmpPdf2, String outPath) throws InterruptedException, IOException {
        return compareByContent(outPdf2, cmpPdf2, outPath, (String) null, (Map<Integer, List<Rectangle>>) null, (byte[]) null, (byte[]) null);
    }

    public String compareByContent(String outPdf2, String cmpPdf2, String outPath, String differenceImagePrefix) throws InterruptedException, IOException {
        return compareByContent(outPdf2, cmpPdf2, outPath, differenceImagePrefix, (Map<Integer, List<Rectangle>>) null, (byte[]) null, (byte[]) null);
    }

    public String compareByContent(String outPdf2, String cmpPdf2, String outPath, String differenceImagePrefix, byte[] outPass, byte[] cmpPass) throws InterruptedException, IOException {
        return compareByContent(outPdf2, cmpPdf2, outPath, differenceImagePrefix, (Map<Integer, List<Rectangle>>) null, outPass, cmpPass);
    }

    public String compareByContent(String outPdf2, String cmpPdf2, String outPath, String differenceImagePrefix, Map<Integer, List<Rectangle>> ignoredAreas) throws InterruptedException, IOException {
        return compareByContent(outPdf2, cmpPdf2, outPath, differenceImagePrefix, ignoredAreas, (byte[]) null, (byte[]) null);
    }

    public String compareByContent(String outPdf2, String cmpPdf2, String outPath, String differenceImagePrefix, Map<Integer, List<Rectangle>> ignoredAreas, byte[] outPass, byte[] cmpPass) throws InterruptedException, IOException {
        init(outPdf2, cmpPdf2);
        System.out.println("Out pdf: " + UrlUtil.getNormalizedFileUriString(outPdf2));
        System.out.println("Cmp pdf: " + UrlUtil.getNormalizedFileUriString(cmpPdf2) + "\n");
        setPassword(outPass, cmpPass);
        return compareByContent(outPath, differenceImagePrefix, ignoredAreas);
    }

    public boolean compareDictionaries(PdfDictionary outDict, PdfDictionary cmpDict) throws IOException {
        return compareDictionariesExtended(outDict, cmpDict, (ObjectPath) null, (CompareResult) null);
    }

    public CompareResult compareDictionariesStructure(PdfDictionary outDict, PdfDictionary cmpDict) {
        return compareDictionariesStructure(outDict, cmpDict, (Set<PdfName>) null);
    }

    public CompareResult compareDictionariesStructure(PdfDictionary outDict, PdfDictionary cmpDict, Set<PdfName> excludedKeys) {
        if (outDict.getIndirectReference() == null || cmpDict.getIndirectReference() == null) {
            throw new IllegalArgumentException("The 'outDict' and 'cmpDict' objects shall have indirect references.");
        }
        CompareResult compareResult = new CompareResult(this.compareByContentErrorsLimit);
        if (!compareDictionariesExtended(outDict, cmpDict, new ObjectPath(cmpDict.getIndirectReference(), outDict.getIndirectReference()), compareResult, excludedKeys)) {
            if (!compareResult.isOk()) {
                System.out.println(compareResult.getReport());
                return compareResult;
            }
            throw new AssertionError();
        } else if (compareResult.isOk()) {
            return null;
        } else {
            throw new AssertionError();
        }
    }

    public CompareResult compareStreamsStructure(PdfStream outStream, PdfStream cmpStream) {
        CompareResult compareResult = new CompareResult(this.compareByContentErrorsLimit);
        if (!compareStreamsExtended(outStream, cmpStream, new ObjectPath(cmpStream.getIndirectReference(), outStream.getIndirectReference()), compareResult)) {
            if (!compareResult.isOk()) {
                System.out.println(compareResult.getReport());
                return compareResult;
            }
            throw new AssertionError();
        } else if (compareResult.isOk()) {
            return null;
        } else {
            throw new AssertionError();
        }
    }

    public boolean compareStreams(PdfStream outStream, PdfStream cmpStream) throws IOException {
        return compareStreamsExtended(outStream, cmpStream, (ObjectPath) null, (CompareResult) null);
    }

    public boolean compareArrays(PdfArray outArray, PdfArray cmpArray) throws IOException {
        return compareArraysExtended(outArray, cmpArray, (ObjectPath) null, (CompareResult) null);
    }

    public boolean compareNames(PdfName outName, PdfName cmpName) {
        return cmpName.equals(outName);
    }

    public boolean compareNumbers(PdfNumber outNumber, PdfNumber cmpNumber) {
        return cmpNumber.getValue() == outNumber.getValue();
    }

    public boolean compareStrings(PdfString outString, PdfString cmpString) {
        return cmpString.getValue().equals(outString.getValue());
    }

    public boolean compareBooleans(PdfBoolean outBoolean, PdfBoolean cmpBoolean) {
        return cmpBoolean.getValue() == outBoolean.getValue();
    }

    public String compareXmp(String outPdf2, String cmpPdf2) {
        return compareXmp(outPdf2, cmpPdf2, false);
    }

    public String compareXmp(String outPdf2, String cmpPdf2, boolean ignoreDateAndProducerProperties) {
        init(outPdf2, cmpPdf2);
        PdfDocument cmpDocument = null;
        PdfDocument outDocument = null;
        try {
            PdfDocument cmpDocument2 = new PdfDocument(new PdfReader(this.cmpPdf), new DocumentProperties().setEventCountingMetaInfo(this.metaInfo));
            PdfDocument outDocument2 = new PdfDocument(new PdfReader(this.outPdf), new DocumentProperties().setEventCountingMetaInfo(this.metaInfo));
            byte[] cmpBytes = cmpDocument2.getXmpMetadata();
            byte[] outBytes = outDocument2.getXmpMetadata();
            if (ignoreDateAndProducerProperties) {
                XMPMeta xmpMeta = XMPMetaFactory.parseFromBuffer(cmpBytes, new ParseOptions().setOmitNormalization(true));
                XMPUtils.removeProperties(xmpMeta, XMPConst.NS_XMP, PdfConst.CreateDate, true, true);
                XMPUtils.removeProperties(xmpMeta, XMPConst.NS_XMP, PdfConst.ModifyDate, true, true);
                XMPUtils.removeProperties(xmpMeta, XMPConst.NS_XMP, PdfConst.MetadataDate, true, true);
                XMPUtils.removeProperties(xmpMeta, XMPConst.NS_PDF, PdfConst.Producer, true, true);
                cmpBytes = XMPMetaFactory.serializeToBuffer(xmpMeta, new SerializeOptions(8192));
                XMPMeta xmpMeta2 = XMPMetaFactory.parseFromBuffer(outBytes, new ParseOptions().setOmitNormalization(true));
                XMPUtils.removeProperties(xmpMeta2, XMPConst.NS_XMP, PdfConst.CreateDate, true, true);
                XMPUtils.removeProperties(xmpMeta2, XMPConst.NS_XMP, PdfConst.ModifyDate, true, true);
                XMPUtils.removeProperties(xmpMeta2, XMPConst.NS_XMP, PdfConst.MetadataDate, true, true);
                XMPUtils.removeProperties(xmpMeta2, XMPConst.NS_PDF, PdfConst.Producer, true, true);
                outBytes = XMPMetaFactory.serializeToBuffer(xmpMeta2, new SerializeOptions(8192));
            }
            if (!compareXmls(cmpBytes, outBytes)) {
                cmpDocument2.close();
                outDocument2.close();
                return "The XMP packages different!";
            }
            cmpDocument2.close();
            outDocument2.close();
            return null;
        } catch (Exception e) {
            if (cmpDocument != null) {
                cmpDocument.close();
            }
            if (outDocument != null) {
                outDocument.close();
            }
            return "XMP parsing failure!";
        }
    }

    public boolean compareXmls(byte[] xml1, byte[] xml2) throws ParserConfigurationException, SAXException, IOException {
        return XmlUtils.compareXmls(new ByteArrayInputStream(xml1), new ByteArrayInputStream(xml2));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0058, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0059, code lost:
        if (r1 != null) goto L_0x005b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x005f, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r2.addSuppressed(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0063, code lost:
        throw r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0066, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0067, code lost:
        if (r0 != null) goto L_0x0069;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x006d, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x006e, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0071, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean compareXmls(java.lang.String r6, java.lang.String r7) throws javax.xml.parsers.ParserConfigurationException, org.xml.sax.SAXException, java.io.IOException {
        /*
            r5 = this;
            java.io.PrintStream r0 = java.lang.System.out
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Out xml: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = com.itextpdf.p026io.util.UrlUtil.getNormalizedFileUriString(r6)
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.println(r1)
            java.io.PrintStream r0 = java.lang.System.out
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Cmp xml: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = com.itextpdf.p026io.util.UrlUtil.getNormalizedFileUriString(r7)
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = "\n"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.println(r1)
            java.io.InputStream r0 = com.itextpdf.p026io.util.FileUtil.getInputStreamForFile(r6)
            java.io.InputStream r1 = com.itextpdf.p026io.util.FileUtil.getInputStreamForFile(r7)     // Catch:{ all -> 0x0064 }
            boolean r2 = com.itextpdf.kernel.utils.XmlUtils.compareXmls(r0, r1)     // Catch:{ all -> 0x0056 }
            if (r1 == 0) goto L_0x0050
            r1.close()     // Catch:{ all -> 0x0064 }
        L_0x0050:
            if (r0 == 0) goto L_0x0055
            r0.close()
        L_0x0055:
            return r2
        L_0x0056:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x0058 }
        L_0x0058:
            r3 = move-exception
            if (r1 == 0) goto L_0x0063
            r1.close()     // Catch:{ all -> 0x005f }
            goto L_0x0063
        L_0x005f:
            r4 = move-exception
            r2.addSuppressed(r4)     // Catch:{ all -> 0x0064 }
        L_0x0063:
            throw r3     // Catch:{ all -> 0x0064 }
        L_0x0064:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0066 }
        L_0x0066:
            r2 = move-exception
            if (r0 == 0) goto L_0x0071
            r0.close()     // Catch:{ all -> 0x006d }
            goto L_0x0071
        L_0x006d:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x0071:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.utils.CompareTool.compareXmls(java.lang.String, java.lang.String):boolean");
    }

    public String compareDocumentInfo(String outPdf2, String cmpPdf2, byte[] outPass, byte[] cmpPass) throws IOException {
        System.out.print("[itext] INFO  Comparing document info.......");
        String message = null;
        setPassword(outPass, cmpPass);
        PdfDocument outDocument = new PdfDocument(new PdfReader(outPdf2, getOutReaderProperties()), new DocumentProperties().setEventCountingMetaInfo(this.metaInfo));
        PdfDocument cmpDocument = new PdfDocument(new PdfReader(cmpPdf2, getCmpReaderProperties()), new DocumentProperties().setEventCountingMetaInfo(this.metaInfo));
        String[] cmpInfo = convertInfo(cmpDocument.getDocumentInfo());
        String[] outInfo = convertInfo(outDocument.getDocumentInfo());
        int i = 0;
        while (true) {
            if (i >= cmpInfo.length) {
                break;
            } else if (!cmpInfo[i].equals(outInfo[i])) {
                message = MessageFormatUtil.format("Document info fail. Expected: \"{0}\", actual: \"{1}\"", cmpInfo[i], outInfo[i]);
                break;
            } else {
                i++;
            }
        }
        outDocument.close();
        cmpDocument.close();
        if (message == null) {
            System.out.println("OK");
        } else {
            System.out.println("Fail");
        }
        System.out.flush();
        return message;
    }

    public String compareDocumentInfo(String outPdf2, String cmpPdf2) throws IOException {
        return compareDocumentInfo(outPdf2, cmpPdf2, (byte[]) null, (byte[]) null);
    }

    public String compareLinkAnnotations(String outPdf2, String cmpPdf2) throws IOException {
        System.out.print("[itext] INFO  Comparing link annotations....");
        String message = null;
        PdfDocument outDocument = new PdfDocument(new PdfReader(outPdf2), new DocumentProperties().setEventCountingMetaInfo(this.metaInfo));
        PdfDocument cmpDocument = new PdfDocument(new PdfReader(cmpPdf2), new DocumentProperties().setEventCountingMetaInfo(this.metaInfo));
        int i = 0;
        while (true) {
            if (i >= outDocument.getNumberOfPages() || i >= cmpDocument.getNumberOfPages()) {
                break;
            }
            List<PdfLinkAnnotation> outLinks = getLinkAnnotations(i + 1, outDocument);
            List<PdfLinkAnnotation> cmpLinks = getLinkAnnotations(i + 1, cmpDocument);
            if (cmpLinks.size() != outLinks.size()) {
                message = MessageFormatUtil.format("Different number of links on page {0}.", Integer.valueOf(i + 1));
                break;
            }
            int j = 0;
            while (true) {
                if (j >= cmpLinks.size()) {
                    break;
                } else if (!compareLinkAnnotations(cmpLinks.get(j), outLinks.get(j), cmpDocument, outDocument)) {
                    message = MessageFormatUtil.format("Different links on page {0}.\n{1}\n{2}", Integer.valueOf(i + 1), cmpLinks.get(j).toString(), outLinks.get(j).toString());
                    break;
                } else {
                    j++;
                }
            }
            i++;
        }
        outDocument.close();
        cmpDocument.close();
        if (message == null) {
            System.out.println("OK");
        } else {
            System.out.println("Fail");
        }
        System.out.flush();
        return message;
    }

    public String compareTagStructures(String outPdf2, String cmpPdf2) throws IOException, ParserConfigurationException, SAXException {
        System.out.print("[itext] INFO  Comparing tag structures......");
        String outXmlPath = outPdf2.replace(".pdf", ".xml");
        String cmpXmlPath = outPdf2.replace(".pdf", ".cmp.xml");
        String message = null;
        PdfDocument docOut = new PdfDocument(new PdfReader(outPdf2), new DocumentProperties().setEventCountingMetaInfo(this.metaInfo));
        FileOutputStream xmlOut = new FileOutputStream(outXmlPath);
        new TaggedPdfReaderTool(docOut).setRootTag(CommonCssConstants.ROOT).convertToXml(xmlOut);
        docOut.close();
        xmlOut.close();
        PdfDocument docCmp = new PdfDocument(new PdfReader(cmpPdf2), new DocumentProperties().setEventCountingMetaInfo(this.metaInfo));
        FileOutputStream xmlCmp = new FileOutputStream(cmpXmlPath);
        new TaggedPdfReaderTool(docCmp).setRootTag(CommonCssConstants.ROOT).convertToXml(xmlCmp);
        docCmp.close();
        xmlCmp.close();
        if (!compareXmls(outXmlPath, cmpXmlPath)) {
            message = "The tag structures are different.";
        }
        if (message == null) {
            System.out.println("OK");
        } else {
            System.out.println("Fail");
        }
        System.out.flush();
        return message;
    }

    /* access modifiers changed from: package-private */
    public String[] convertInfo(PdfDocumentInfo info) {
        String[] convertedInfo = {"", "", "", "", ""};
        String infoValue = info.getTitle();
        if (infoValue != null) {
            convertedInfo[0] = infoValue;
        }
        String infoValue2 = info.getAuthor();
        if (infoValue2 != null) {
            convertedInfo[1] = infoValue2;
        }
        String infoValue3 = info.getSubject();
        if (infoValue3 != null) {
            convertedInfo[2] = infoValue3;
        }
        String infoValue4 = info.getKeywords();
        if (infoValue4 != null) {
            convertedInfo[3] = infoValue4;
        }
        String infoValue5 = info.getProducer();
        if (infoValue5 != null) {
            convertedInfo[4] = convertProducerLine(infoValue5);
        }
        return convertedInfo;
    }

    /* access modifiers changed from: package-private */
    public String convertProducerLine(String producer) {
        return producer.replaceAll(VERSION_REGEXP, VERSION_REPLACEMENT).replaceAll(COPYRIGHT_REGEXP, COPYRIGHT_REPLACEMENT);
    }

    private void init(String outPdf2, String cmpPdf2) {
        this.outPdf = outPdf2;
        this.cmpPdf = cmpPdf2;
        this.outPdfName = new File(outPdf2).getName();
        this.cmpPdfName = new File(cmpPdf2).getName();
        this.outImage = this.outPdfName + "-%03d.png";
        if (this.cmpPdfName.startsWith("cmp_")) {
            this.cmpImage = this.cmpPdfName + "-%03d.png";
        } else {
            this.cmpImage = "cmp_" + this.cmpPdfName + "-%03d.png";
        }
    }

    private void setPassword(byte[] outPass, byte[] cmpPass) {
        if (outPass != null) {
            getOutReaderProperties().setPassword(outPass);
        }
        if (cmpPass != null) {
            getCmpReaderProperties().setPassword(outPass);
        }
    }

    private String compareVisually(String outPath, String differenceImagePrefix, Map<Integer, List<Rectangle>> ignoredAreas) throws InterruptedException, IOException {
        return compareVisually(outPath, differenceImagePrefix, ignoredAreas, (List<Integer>) null);
    }

    private String compareVisually(String outPath, String differenceImagePrefix, Map<Integer, List<Rectangle>> ignoredAreas, List<Integer> equalPages) throws IOException, InterruptedException {
        if (!outPath.endsWith("/")) {
            outPath = outPath + "/";
        }
        if (differenceImagePrefix == null) {
            String fileBasedPrefix = "";
            if (this.outPdfName != null) {
                fileBasedPrefix = this.outPdfName + "_";
            }
            differenceImagePrefix = "diff_" + fileBasedPrefix;
        }
        prepareOutputDirs(outPath, differenceImagePrefix);
        System.out.println("Comparing visually..........");
        if (ignoredAreas != null && !ignoredAreas.isEmpty()) {
            createIgnoredAreasPdfs(outPath, ignoredAreas);
        }
        try {
            GhostscriptHelper ghostscriptHelper = new GhostscriptHelper(this.gsExec);
            ghostscriptHelper.runGhostScriptImageGeneration(this.outPdf, outPath, this.outImage);
            ghostscriptHelper.runGhostScriptImageGeneration(this.cmpPdf, outPath, this.cmpImage);
            return compareImagesOfPdfs(outPath, differenceImagePrefix, equalPages);
        } catch (IllegalArgumentException e) {
            throw new CompareToolExecutionException(e.getMessage());
        }
    }

    private String compareImagesOfPdfs(String outPath, String differenceImagePrefix, List<Integer> equalPages) throws IOException, InterruptedException {
        boolean bUnexpectedNumberOfPages;
        boolean compareExecIsOk;
        int cnt;
        String str = outPath;
        List<Integer> list = equalPages;
        File[] imageFiles = FileUtil.listFilesInDirectoryByFilter(str, new PngFileFilter());
        File[] cmpImageFiles = FileUtil.listFilesInDirectoryByFilter(str, new CmpPngFileFilter());
        if (imageFiles.length != cmpImageFiles.length) {
            bUnexpectedNumberOfPages = true;
        } else {
            bUnexpectedNumberOfPages = false;
        }
        int cnt2 = Math.min(imageFiles.length, cmpImageFiles.length);
        if (cnt2 >= 1) {
            Arrays.sort(imageFiles, new ImageNameComparator());
            Arrays.sort(cmpImageFiles, new ImageNameComparator());
            String imageMagickInitError = null;
            ImageMagickHelper imageMagickHelper = null;
            try {
                imageMagickHelper = new ImageMagickHelper(this.compareExec);
                compareExecIsOk = true;
            } catch (IllegalArgumentException e) {
                imageMagickInitError = e.getMessage();
                LoggerFactory.getLogger((Class<?>) CompareTool.class).warn(e.getMessage());
                compareExecIsOk = false;
            }
            List<Integer> diffPages = new ArrayList<>();
            String differentPagesFail = null;
            int i = 0;
            while (i < cnt2) {
                if (list == null || !list.contains(Integer.valueOf(i))) {
                    cnt = cnt2;
                    System.out.println("Comparing page " + Integer.toString(i + 1) + ": " + UrlUtil.getNormalizedFileUriString(imageFiles[i].getName()) + " ...");
                    System.out.println("Comparing page " + Integer.toString(i + 1) + ": " + UrlUtil.getNormalizedFileUriString(imageFiles[i].getName()) + " ...");
                    FileInputStream is1 = new FileInputStream(imageFiles[i].getAbsolutePath());
                    FileInputStream is2 = new FileInputStream(cmpImageFiles[i].getAbsolutePath());
                    boolean cmpResult = compareStreams((InputStream) is1, (InputStream) is2);
                    is1.close();
                    is2.close();
                    if (!cmpResult) {
                        differentPagesFail = "Page is different!";
                        diffPages.add(Integer.valueOf(i + 1));
                        if (compareExecIsOk) {
                            String diffName = str + differenceImagePrefix + Integer.toString(i + 1) + ".png";
                            FileInputStream fileInputStream = is1;
                            if (!imageMagickHelper.runImageMagickImageCompare(imageFiles[i].getAbsolutePath(), cmpImageFiles[i].getAbsolutePath(), diffName)) {
                                String str2 = diffName;
                                differentPagesFail = differentPagesFail + "\nPlease, examine file:///" + UrlUtil.toNormalizedURI(new File(diffName)).getPath() + " for more details.";
                            }
                        } else {
                            String str3 = differenceImagePrefix;
                            FileInputStream fileInputStream2 = is1;
                        }
                        System.out.println(differentPagesFail);
                    } else {
                        String str4 = differenceImagePrefix;
                        FileInputStream fileInputStream3 = is1;
                        System.out.println(" done.");
                    }
                } else {
                    String str5 = differenceImagePrefix;
                    cnt = cnt2;
                }
                i++;
                str = outPath;
                list = equalPages;
                cnt2 = cnt;
            }
            String str6 = differenceImagePrefix;
            int i2 = cnt2;
            if (differentPagesFail != null) {
                String errorMessage = DIFFERENT_PAGES.replace("<filename>", UrlUtil.toNormalizedURI(this.outPdf).getPath()).replace("<pagenumber>", listDiffPagesAsString(diffPages));
                if (!compareExecIsOk) {
                    return errorMessage + "\n" + imageMagickInitError;
                }
                return errorMessage;
            } else if (bUnexpectedNumberOfPages) {
                return UNEXPECTED_NUMBER_OF_PAGES.replace("<filename>", this.outPdf);
            } else {
                return null;
            }
        } else {
            throw new CompareToolExecutionException("No files for comparing. The result or sample pdf file is not processed by GhostScript.");
        }
    }

    private String listDiffPagesAsString(List<Integer> diffPages) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < diffPages.size(); i++) {
            sb.append(diffPages.get(i));
            if (i < diffPages.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private void createIgnoredAreasPdfs(String outPath, Map<Integer, List<Rectangle>> ignoredAreas) throws IOException {
        String str = outPath;
        PdfWriter outWriter = new PdfWriter(str + IGNORED_AREAS_PREFIX + this.outPdfName);
        PdfWriter cmpWriter = new PdfWriter(str + IGNORED_AREAS_PREFIX + this.cmpPdfName);
        StampingProperties properties = new StampingProperties();
        properties.setEventCountingMetaInfo(this.metaInfo);
        PdfDocument pdfOutDoc = new PdfDocument(new PdfReader(this.outPdf), outWriter, properties);
        PdfDocument pdfCmpDoc = new PdfDocument(new PdfReader(this.cmpPdf), cmpWriter, properties);
        for (Map.Entry<Integer, List<Rectangle>> entry : ignoredAreas.entrySet()) {
            int pageNumber = entry.getKey().intValue();
            List<Rectangle> rectangles = entry.getValue();
            if (rectangles != null && !rectangles.isEmpty()) {
                PdfCanvas outCanvas = new PdfCanvas(pdfOutDoc.getPage(pageNumber));
                PdfCanvas cmpCanvas = new PdfCanvas(pdfCmpDoc.getPage(pageNumber));
                outCanvas.saveState();
                cmpCanvas.saveState();
                for (Rectangle rect : rectangles) {
                    outCanvas.rectangle(rect).fill();
                    cmpCanvas.rectangle(rect).fill();
                }
                outCanvas.restoreState();
                cmpCanvas.restoreState();
            }
        }
        pdfOutDoc.close();
        pdfCmpDoc.close();
        init(str + IGNORED_AREAS_PREFIX + this.outPdfName, str + IGNORED_AREAS_PREFIX + this.cmpPdfName);
    }

    private void prepareOutputDirs(String outPath, String differenceImagePrefix) {
        if (!FileUtil.directoryExists(outPath)) {
            FileUtil.createDirectories(outPath);
            return;
        }
        for (File file : FileUtil.listFilesInDirectoryByFilter(outPath, new PngFileFilter())) {
            file.delete();
        }
        for (File file2 : FileUtil.listFilesInDirectoryByFilter(outPath, new CmpPngFileFilter())) {
            file2.delete();
        }
        for (File file3 : FileUtil.listFilesInDirectoryByFilter(outPath, new DiffPngFileFilter(differenceImagePrefix))) {
            file3.delete();
        }
    }

    private void printOutCmpDirectories() {
        System.out.println("Out file folder: file://" + UrlUtil.toNormalizedURI(new File(this.outPdf).getParentFile()).getPath());
        System.out.println("Cmp file folder: file://" + UrlUtil.toNormalizedURI(new File(this.cmpPdf).getParentFile()).getPath());
    }

    private String compareByContent(String outPath, String differenceImagePrefix, Map<Integer, List<Rectangle>> ignoredAreas) throws InterruptedException, IOException {
        printOutCmpDirectories();
        System.out.print("Comparing by content..........");
        try {
            PdfDocument outDocument = new PdfDocument(new PdfReader(this.outPdf, getOutReaderProperties()), new DocumentProperties().setEventCountingMetaInfo(this.metaInfo));
            List<PdfDictionary> outPages = new ArrayList<>();
            ArrayList arrayList = new ArrayList();
            this.outPagesRef = arrayList;
            loadPagesFromReader(outDocument, outPages, arrayList);
            try {
                PdfDocument cmpDocument = new PdfDocument(new PdfReader(this.cmpPdf, getCmpReaderProperties()), new DocumentProperties().setEventCountingMetaInfo(this.metaInfo));
                ArrayList arrayList2 = new ArrayList();
                ArrayList arrayList3 = new ArrayList();
                this.cmpPagesRef = arrayList3;
                loadPagesFromReader(cmpDocument, arrayList2, arrayList3);
                if (outPages.size() != arrayList2.size()) {
                    return compareVisuallyAndCombineReports("Documents have different numbers of pages.", outPath, differenceImagePrefix, ignoredAreas, (List<Integer>) null);
                }
                CompareResult compareResult = new CompareResult(this.compareByContentErrorsLimit);
                List<Integer> equalPages = new ArrayList<>(arrayList2.size());
                for (int i = 0; i < arrayList2.size(); i++) {
                    if (compareDictionariesExtended(outPages.get(i), (PdfDictionary) arrayList2.get(i), new ObjectPath(this.cmpPagesRef.get(i), this.outPagesRef.get(i)), compareResult)) {
                        equalPages.add(Integer.valueOf(i));
                    }
                }
                compareDictionariesExtended((PdfDictionary) outDocument.getCatalog().getPdfObject(), (PdfDictionary) cmpDocument.getCatalog().getPdfObject(), new ObjectPath(((PdfDictionary) cmpDocument.getCatalog().getPdfObject()).getIndirectReference(), ((PdfDictionary) outDocument.getCatalog().getPdfObject()).getIndirectReference()), compareResult, new LinkedHashSet<>(Arrays.asList(new PdfName[]{PdfName.Pages, PdfName.Metadata})));
                if (this.encryptionCompareEnabled) {
                    compareDocumentsEncryption(outDocument, cmpDocument, compareResult);
                }
                outDocument.close();
                cmpDocument.close();
                if (this.generateCompareByContentXmlReport) {
                    String outPdfName2 = new File(this.outPdf).getName();
                    FileOutputStream xml = new FileOutputStream(outPath + "/" + outPdfName2.substring(0, outPdfName2.length() - 3) + "report.xml");
                    try {
                        compareResult.writeReportToXml(xml);
                        xml.close();
                    } catch (Exception e) {
                        Exception e2 = e;
                        throw new RuntimeException(e2.getMessage(), e2);
                    } catch (Throwable th) {
                        xml.close();
                        throw th;
                    }
                } else {
                    String str = outPath;
                }
                if (equalPages.size() != arrayList2.size() || !compareResult.isOk()) {
                    CompareResult compareResult2 = compareResult;
                    ArrayList arrayList4 = arrayList2;
                    PdfDocument pdfDocument = cmpDocument;
                    return compareVisuallyAndCombineReports(compareResult.getReport(), outPath, differenceImagePrefix, ignoredAreas, equalPages);
                }
                System.out.println("OK");
                System.out.flush();
                return null;
            } catch (IOException e3) {
                String str2 = outPath;
                throw new IOException("File \"" + this.cmpPdf + "\" not found", e3);
            }
        } catch (IOException e4) {
            String str3 = outPath;
            throw new IOException("File \"" + this.outPdf + "\" not found", e4);
        }
    }

    private String compareVisuallyAndCombineReports(String compareByFailContentReason, String outPath, String differenceImagePrefix, Map<Integer, List<Rectangle>> ignoredAreas, List<Integer> equalPages) throws IOException, InterruptedException {
        System.out.println("Fail");
        System.out.flush();
        System.out.println("Compare by content report:\n" + compareByFailContentReason);
        System.out.flush();
        String message = compareVisually(outPath, differenceImagePrefix, ignoredAreas, equalPages);
        if (message == null || message.length() == 0) {
            return "Compare by content fails. No visual differences";
        }
        return message;
    }

    private void loadPagesFromReader(PdfDocument doc, List<PdfDictionary> pages, List<PdfIndirectReference> pagesRef) {
        int numOfPages = doc.getNumberOfPages();
        for (int i = 0; i < numOfPages; i++) {
            pages.add(doc.getPage(i + 1).getPdfObject());
            pagesRef.add(pages.get(i).getIndirectReference());
        }
    }

    private void compareDocumentsEncryption(PdfDocument outDocument, PdfDocument cmpDocument, CompareResult compareResult) {
        CompareResult compareResult2 = compareResult;
        PdfDictionary outEncrypt = outDocument.getTrailer().getAsDictionary(PdfName.Encrypt);
        PdfDictionary cmpEncrypt = cmpDocument.getTrailer().getAsDictionary(PdfName.Encrypt);
        if (outEncrypt != null || cmpEncrypt != null) {
            TrailerPath trailerPath = new TrailerPath(cmpDocument, outDocument);
            if (outEncrypt == null) {
                compareResult2.addError(trailerPath, "Expected encrypted document.");
            } else if (cmpEncrypt == null) {
                compareResult2.addError(trailerPath, "Expected not encrypted document.");
            } else {
                int i = 1;
                Set<PdfName> ignoredEncryptEntries = new LinkedHashSet<>(Arrays.asList(new PdfName[]{PdfName.f1361O, PdfName.f1403U, PdfName.f1363OE, PdfName.f1404UE, PdfName.Perms, PdfName.f1304CF, PdfName.Recipients}));
                ObjectPath objectPath = new ObjectPath(outEncrypt.getIndirectReference(), cmpEncrypt.getIndirectReference());
                compareDictionariesExtended(outEncrypt, cmpEncrypt, objectPath, compareResult, ignoredEncryptEntries);
                PdfDictionary outCfDict = outEncrypt.getAsDictionary(PdfName.f1304CF);
                PdfDictionary cmpCfDict = cmpEncrypt.getAsDictionary(PdfName.f1304CF);
                if (cmpCfDict == null && outCfDict == null) {
                    ObjectPath objectPath2 = objectPath;
                    TrailerPath trailerPath2 = trailerPath;
                } else if ((cmpCfDict == null || outCfDict != null) && cmpCfDict != null) {
                    Set<PdfName> treeSet = new TreeSet<>(outCfDict.keySet());
                    treeSet.addAll(cmpCfDict.keySet());
                    for (PdfName key : treeSet) {
                        objectPath.pushDictItemToPath(key);
                        Set<PdfName> mergedKeys = treeSet;
                        PdfName[] pdfNameArr = new PdfName[i];
                        pdfNameArr[0] = PdfName.Recipients;
                        LinkedHashSet<PdfName> excludedKeys = new LinkedHashSet<>(Arrays.asList(pdfNameArr));
                        ObjectPath objectPath3 = objectPath;
                        PdfDictionary asDictionary = outCfDict.getAsDictionary(key);
                        compareDictionariesExtended(asDictionary, cmpCfDict.getAsDictionary(key), objectPath3, compareResult, excludedKeys);
                        objectPath3.pop();
                        PdfDocument pdfDocument = outDocument;
                        PdfDocument pdfDocument2 = cmpDocument;
                        trailerPath = trailerPath;
                        treeSet = mergedKeys;
                        objectPath = objectPath3;
                        i = 1;
                    }
                    Set<PdfName> mergedKeys2 = treeSet;
                    ObjectPath objectPath4 = objectPath;
                    TrailerPath trailerPath3 = trailerPath;
                } else {
                    compareResult2.addError(objectPath, "One of the dictionaries is null, the other is not.");
                    ObjectPath objectPath5 = objectPath;
                    TrailerPath trailerPath4 = trailerPath;
                }
            }
        }
    }

    private boolean compareStreams(InputStream is1, InputStream is2) throws IOException {
        int len1;
        byte[] buffer1 = new byte[65536];
        byte[] buffer2 = new byte[65536];
        do {
            len1 = is1.read(buffer1);
            if (len1 != is2.read(buffer2) || !Arrays.equals(buffer1, buffer2)) {
                return false;
            }
        } while (len1 != -1);
        return true;
    }

    private boolean compareDictionariesExtended(PdfDictionary outDict, PdfDictionary cmpDict, ObjectPath currentPath, CompareResult compareResult) {
        return compareDictionariesExtended(outDict, cmpDict, currentPath, compareResult, (Set<PdfName>) null);
    }

    private boolean compareDictionariesExtended(PdfDictionary outDict, PdfDictionary cmpDict, ObjectPath currentPath, CompareResult compareResult, Set<PdfName> excludedKeys) {
        PdfDictionary pdfDictionary = outDict;
        PdfDictionary pdfDictionary2 = cmpDict;
        ObjectPath objectPath = currentPath;
        CompareResult compareResult2 = compareResult;
        Set<PdfName> set = excludedKeys;
        boolean z = false;
        if ((pdfDictionary2 == null || pdfDictionary != null) && (pdfDictionary == null || pdfDictionary2 != null)) {
            boolean dictsAreSame = true;
            Set<PdfName> mergedKeys = new TreeSet<>(cmpDict.keySet());
            mergedKeys.addAll(outDict.keySet());
            for (PdfName key : mergedKeys) {
                if (!dictsAreSame && (objectPath == null || compareResult2 == null || compareResult.isMessageLimitReached())) {
                    return z;
                }
                if ((set == null || !set.contains(key)) && !key.equals(PdfName.Parent) && !key.equals(PdfName.f1367P) && !key.equals(PdfName.ModDate) && (!outDict.isStream() || !cmpDict.isStream() || (!key.equals(PdfName.Filter) && !key.equals(PdfName.Length)))) {
                    if (key.equals(PdfName.BaseFont) || key.equals(PdfName.FontName)) {
                        PdfObject cmpObj = pdfDictionary2.get(key);
                        if (cmpObj != null && cmpObj.isName() && cmpObj.toString().indexOf(43) > 0) {
                            PdfObject outObj = pdfDictionary.get(key);
                            if (!outObj.isName() || outObj.toString().indexOf(43) == -1) {
                                if (!(compareResult2 == null || objectPath == null)) {
                                    compareResult2.addError(objectPath, MessageFormatUtil.format("PdfDictionary {0} entry: Expected: {1}. Found: {2}", key.toString(), cmpObj.toString(), outObj.toString()));
                                }
                                dictsAreSame = false;
                                set = excludedKeys;
                                z = false;
                            } else {
                                if (!cmpObj.toString().substring(cmpObj.toString().indexOf(43)).equals(outObj.toString().substring(outObj.toString().indexOf(43)))) {
                                    if (!(compareResult2 == null || objectPath == null)) {
                                        compareResult2.addError(objectPath, MessageFormatUtil.format("PdfDictionary {0} entry: Expected: {1}. Found: {2}", key.toString(), cmpObj.toString(), outObj.toString()));
                                    }
                                    dictsAreSame = false;
                                }
                                set = excludedKeys;
                                z = false;
                            }
                        }
                    }
                    if (!key.equals(PdfName.ParentTree)) {
                        if (!key.equals(PdfName.PageLabels)) {
                            if (objectPath != null) {
                                objectPath.pushDictItemToPath(key);
                            }
                            dictsAreSame = compareObjects(pdfDictionary.get(key, false), pdfDictionary2.get(key, false), objectPath, compareResult2) && dictsAreSame;
                            if (objectPath != null) {
                                currentPath.pop();
                            }
                            set = excludedKeys;
                            z = false;
                        }
                    }
                    if (objectPath != null) {
                        objectPath.pushDictItemToPath(key);
                    }
                    PdfDictionary outNumTree = pdfDictionary.getAsDictionary(key);
                    PdfDictionary cmpNumTree = pdfDictionary2.getAsDictionary(key);
                    LinkedList<PdfObject> outItems = new LinkedList<>();
                    LinkedList<PdfObject> cmpItems = new LinkedList<>();
                    PdfNumber outLeftover = flattenNumTree(outNumTree, (PdfNumber) null, outItems);
                    PdfNumber cmpLeftover = flattenNumTree(cmpNumTree, (PdfNumber) null, cmpItems);
                    Class<CompareTool> cls = CompareTool.class;
                    if (outLeftover != null) {
                        LoggerFactory.getLogger((Class<?>) cls).warn(LogMessageConstant.NUM_TREE_SHALL_NOT_END_WITH_KEY);
                        if (cmpLeftover == null) {
                            if (!(compareResult2 == null || objectPath == null)) {
                                compareResult2.addError(objectPath, "Number tree unexpectedly ends with a key");
                            }
                            dictsAreSame = false;
                        }
                    }
                    if (cmpLeftover != null) {
                        LoggerFactory.getLogger((Class<?>) cls).warn(LogMessageConstant.NUM_TREE_SHALL_NOT_END_WITH_KEY);
                        if (outLeftover == null) {
                            if (!(compareResult2 == null || objectPath == null)) {
                                compareResult2.addError(objectPath, "Number tree was expected to end with a key (although it is invalid according to the specification), but ended with a value");
                            }
                            dictsAreSame = false;
                        }
                    }
                    if (!(outLeftover == null || cmpLeftover == null || compareNumbers(outLeftover, cmpLeftover))) {
                        if (!(compareResult2 == null || objectPath == null)) {
                            compareResult2.addError(objectPath, "Number tree was expected to end with a different key (although it is invalid according to the specification)");
                        }
                        dictsAreSame = false;
                    }
                    if (!compareArraysExtended(new PdfArray((Iterable<? extends PdfObject>) outItems, outItems.size()), new PdfArray((Iterable<? extends PdfObject>) cmpItems, cmpItems.size()), objectPath, compareResult2)) {
                        if (!(compareResult2 == null || objectPath == null)) {
                            compareResult2.addError(objectPath, "Number trees were flattened, compared and found to be different.");
                        }
                        dictsAreSame = false;
                    }
                    if (objectPath != null) {
                        currentPath.pop();
                    }
                }
                pdfDictionary = outDict;
                pdfDictionary2 = cmpDict;
                set = excludedKeys;
                z = false;
            }
            return dictsAreSame;
        }
        compareResult2.addError(objectPath, "One of the dictionaries is null, the other is not.");
        return false;
    }

    private PdfNumber flattenNumTree(PdfDictionary dictionary, PdfNumber leftOver, LinkedList<PdfObject> items) {
        PdfNumber number;
        PdfArray nums = dictionary.getAsArray(PdfName.Nums);
        if (nums != null) {
            int k = 0;
            while (k < nums.size()) {
                if (leftOver == null) {
                    int i = k + 1;
                    number = nums.getAsNumber(k);
                    k = i;
                } else {
                    number = leftOver;
                    leftOver = null;
                }
                if (k >= nums.size()) {
                    return number;
                }
                items.addLast(number);
                items.addLast(nums.get(k, false));
                k++;
            }
            return null;
        }
        PdfArray asArray = dictionary.getAsArray(PdfName.Kids);
        PdfArray nums2 = asArray;
        if (asArray == null) {
            return null;
        }
        for (int k2 = 0; k2 < nums2.size(); k2++) {
            leftOver = flattenNumTree(nums2.getAsDictionary(k2), leftOver, items);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public boolean compareObjects(PdfObject outObj, PdfObject cmpObj, ObjectPath currentPath, CompareResult compareResult) {
        PdfObject outDirectObj = null;
        PdfObject cmpDirectObj = null;
        if (outObj != null) {
            outDirectObj = outObj.isIndirectReference() ? ((PdfIndirectReference) outObj).getRefersTo(false) : outObj;
        }
        if (cmpObj != null) {
            cmpDirectObj = cmpObj.isIndirectReference() ? ((PdfIndirectReference) cmpObj).getRefersTo(false) : cmpObj;
        }
        if (cmpDirectObj == null && outDirectObj == null) {
            return true;
        }
        if (outDirectObj == null) {
            compareResult.addError(currentPath, "Expected object was not found.");
            return false;
        } else if (cmpDirectObj == null) {
            compareResult.addError(currentPath, "Found object which was not expected to be found.");
            return false;
        } else if (cmpDirectObj.getType() != outDirectObj.getType()) {
            compareResult.addError(currentPath, MessageFormatUtil.format("Types do not match. Expected: {0}. Found: {1}.", cmpDirectObj.getClass().getSimpleName(), outDirectObj.getClass().getSimpleName()));
            return false;
        } else if (cmpObj.isIndirectReference() && !outObj.isIndirectReference()) {
            compareResult.addError(currentPath, "Expected indirect object.");
            return false;
        } else if (cmpObj.isIndirectReference() || !outObj.isIndirectReference()) {
            if (currentPath != null && cmpObj.isIndirectReference() && outObj.isIndirectReference()) {
                if (currentPath.isComparing((PdfIndirectReference) cmpObj, (PdfIndirectReference) outObj)) {
                    return true;
                }
                currentPath = currentPath.resetDirectPath((PdfIndirectReference) cmpObj, (PdfIndirectReference) outObj);
            }
            if (cmpDirectObj.isDictionary() && PdfName.Page.equals(((PdfDictionary) cmpDirectObj).getAsName(PdfName.Type)) && this.useCachedPagesForComparison) {
                if (!outDirectObj.isDictionary() || !PdfName.Page.equals(((PdfDictionary) outDirectObj).getAsName(PdfName.Type))) {
                    if (!(compareResult == null || currentPath == null)) {
                        compareResult.addError(currentPath, "Expected a page. Found not a page.");
                    }
                    return false;
                }
                PdfIndirectReference cmpRefKey = cmpObj.isIndirectReference() ? (PdfIndirectReference) cmpObj : cmpObj.getIndirectReference();
                PdfIndirectReference outRefKey = outObj.isIndirectReference() ? (PdfIndirectReference) outObj : outObj.getIndirectReference();
                if (this.cmpPagesRef == null) {
                    this.cmpPagesRef = new ArrayList();
                    for (int i = 1; i <= cmpRefKey.getDocument().getNumberOfPages(); i++) {
                        this.cmpPagesRef.add(((PdfDictionary) cmpRefKey.getDocument().getPage(i).getPdfObject()).getIndirectReference());
                    }
                }
                if (this.outPagesRef == null) {
                    this.outPagesRef = new ArrayList();
                    for (int i2 = 1; i2 <= outRefKey.getDocument().getNumberOfPages(); i2++) {
                        this.outPagesRef.add(((PdfDictionary) outRefKey.getDocument().getPage(i2).getPdfObject()).getIndirectReference());
                    }
                }
                if (this.cmpPagesRef.contains(cmpRefKey) || this.outPagesRef.contains(outRefKey)) {
                    if (this.cmpPagesRef.contains(cmpRefKey) && this.cmpPagesRef.indexOf(cmpRefKey) == this.outPagesRef.indexOf(outRefKey)) {
                        return true;
                    }
                    if (!(compareResult == null || currentPath == null)) {
                        compareResult.addError(currentPath, MessageFormatUtil.format("The dictionaries refer to different pages. Expected page number: {0}. Found: {1}", Integer.valueOf(this.cmpPagesRef.indexOf(cmpRefKey) + 1), Integer.valueOf(this.outPagesRef.indexOf(outRefKey) + 1)));
                    }
                    return false;
                }
            }
            if (cmpDirectObj.isDictionary()) {
                return compareDictionariesExtended((PdfDictionary) outDirectObj, (PdfDictionary) cmpDirectObj, currentPath, compareResult);
            }
            if (cmpDirectObj.isStream()) {
                return compareStreamsExtended((PdfStream) outDirectObj, (PdfStream) cmpDirectObj, currentPath, compareResult);
            }
            if (cmpDirectObj.isArray()) {
                return compareArraysExtended((PdfArray) outDirectObj, (PdfArray) cmpDirectObj, currentPath, compareResult);
            }
            if (cmpDirectObj.isName()) {
                return compareNamesExtended((PdfName) outDirectObj, (PdfName) cmpDirectObj, currentPath, compareResult);
            }
            if (cmpDirectObj.isNumber()) {
                return compareNumbersExtended((PdfNumber) outDirectObj, (PdfNumber) cmpDirectObj, currentPath, compareResult);
            }
            if (cmpDirectObj.isString()) {
                return compareStringsExtended((PdfString) outDirectObj, (PdfString) cmpDirectObj, currentPath, compareResult);
            }
            if (cmpDirectObj.isBoolean()) {
                return compareBooleansExtended((PdfBoolean) outDirectObj, (PdfBoolean) cmpDirectObj, currentPath, compareResult);
            }
            if (outDirectObj.isNull() && cmpDirectObj.isNull()) {
                return true;
            }
            throw new UnsupportedOperationException();
        } else {
            compareResult.addError(currentPath, "Expected direct object.");
            return false;
        }
    }

    private boolean compareStreamsExtended(PdfStream outStream, PdfStream cmpStream, ObjectPath currentPath, CompareResult compareResult) {
        boolean toDecode = PdfName.FlateDecode.equals(outStream.get(PdfName.Filter));
        byte[] outStreamBytes = outStream.getBytes(toDecode);
        byte[] cmpStreamBytes = cmpStream.getBytes(toDecode);
        if (Arrays.equals(outStreamBytes, cmpStreamBytes)) {
            return compareDictionariesExtended(outStream, cmpStream, currentPath, compareResult);
        }
        StringBuilder errorMessage = new StringBuilder();
        if (cmpStreamBytes.length != outStreamBytes.length) {
            errorMessage.append(MessageFormatUtil.format("PdfStream. Lengths are different. Expected: {0}. Found: {1}\n", Integer.valueOf(cmpStreamBytes.length), Integer.valueOf(outStreamBytes.length)));
        } else {
            errorMessage.append("PdfStream. Bytes are different.\n");
        }
        int firstDifferenceOffset = findBytesDifference(outStreamBytes, cmpStreamBytes, errorMessage);
        if (!(compareResult == null || currentPath == null)) {
            currentPath.pushOffsetToPath(firstDifferenceOffset);
            compareResult.addError(currentPath, errorMessage.toString());
            currentPath.pop();
        }
        return false;
    }

    private int findBytesDifference(byte[] outStreamBytes, byte[] cmpStreamBytes, StringBuilder errorMessage) {
        String outByte;
        byte[] bArr = outStreamBytes;
        byte[] bArr2 = cmpStreamBytes;
        int numberOfDifferentBytes = 0;
        int firstDifferenceOffset = 0;
        int minLength = Math.min(bArr2.length, bArr.length);
        for (int i = 0; i < minLength; i++) {
            if (bArr2[i] != bArr[i] && (numberOfDifferentBytes = numberOfDifferentBytes + 1) == 1) {
                firstDifferenceOffset = i;
            }
        }
        if (numberOfDifferentBytes > 0) {
            int lCmp = Math.max(0, firstDifferenceOffset - 10);
            int rCmp = Math.min(bArr2.length, firstDifferenceOffset + 10);
            int lOut = Math.max(0, firstDifferenceOffset - 10);
            int rOut = Math.min(bArr.length, firstDifferenceOffset + 10);
            outByte = MessageFormatUtil.format("First bytes difference is encountered at index {0}. Expected: {1} ({2}). Found: {3} ({4}). Total number of different bytes: {5}", Integer.valueOf(firstDifferenceOffset).toString(), new String(new byte[]{bArr2[firstDifferenceOffset]}, StandardCharsets.ISO_8859_1), new String(bArr2, lCmp, rCmp - lCmp, StandardCharsets.ISO_8859_1).replaceAll(NEW_LINES, " "), new String(new byte[]{bArr[firstDifferenceOffset]}, StandardCharsets.ISO_8859_1), new String(bArr, lOut, rOut - lOut, StandardCharsets.ISO_8859_1).replaceAll(NEW_LINES, " "), Integer.valueOf(numberOfDifferentBytes));
        } else {
            firstDifferenceOffset = minLength;
            outByte = MessageFormatUtil.format("Bytes of the shorter array are the same as the first {0} bytes of the longer one.", Integer.valueOf(minLength));
        }
        errorMessage.append(outByte);
        return firstDifferenceOffset;
    }

    private boolean compareArraysExtended(PdfArray outArray, PdfArray cmpArray, ObjectPath currentPath, CompareResult compareResult) {
        if (outArray == null) {
            if (!(compareResult == null || currentPath == null)) {
                compareResult.addError(currentPath, "Found null. Expected PdfArray.");
            }
            return false;
        } else if (outArray.size() != cmpArray.size()) {
            if (!(compareResult == null || currentPath == null)) {
                compareResult.addError(currentPath, MessageFormatUtil.format("PdfArrays. Lengths are different. Expected: {0}. Found: {1}.", Integer.valueOf(cmpArray.size()), Integer.valueOf(outArray.size())));
            }
            return false;
        } else {
            boolean arraysAreEqual = true;
            for (int i = 0; i < cmpArray.size(); i++) {
                if (currentPath != null) {
                    currentPath.pushArrayItemToPath(i);
                }
                arraysAreEqual = compareObjects(outArray.get(i, false), cmpArray.get(i, false), currentPath, compareResult) && arraysAreEqual;
                if (currentPath != null) {
                    currentPath.pop();
                }
                if (!arraysAreEqual && (currentPath == null || compareResult == null || compareResult.isMessageLimitReached())) {
                    return false;
                }
            }
            return arraysAreEqual;
        }
    }

    private boolean compareNamesExtended(PdfName outName, PdfName cmpName, ObjectPath currentPath, CompareResult compareResult) {
        if (cmpName.equals(outName)) {
            return true;
        }
        if (!(compareResult == null || currentPath == null)) {
            compareResult.addError(currentPath, MessageFormatUtil.format("PdfName. Expected: {0}. Found: {1}", cmpName.toString(), outName.toString()));
        }
        return false;
    }

    private boolean compareNumbersExtended(PdfNumber outNumber, PdfNumber cmpNumber, ObjectPath currentPath, CompareResult compareResult) {
        if (cmpNumber.getValue() == outNumber.getValue()) {
            return true;
        }
        if (!(compareResult == null || currentPath == null)) {
            compareResult.addError(currentPath, MessageFormatUtil.format("PdfNumber. Expected: {0}. Found: {1}", cmpNumber, outNumber));
        }
        return false;
    }

    private boolean compareStringsExtended(PdfString outString, PdfString cmpString, ObjectPath currentPath, CompareResult compareResult) {
        if (Arrays.equals(convertPdfStringToBytes(cmpString), convertPdfStringToBytes(outString))) {
            return true;
        }
        String cmpStr = cmpString.toUnicodeString();
        String outStr = outString.toUnicodeString();
        StringBuilder errorMessage = new StringBuilder();
        if (cmpStr.length() != outStr.length()) {
            errorMessage.append(MessageFormatUtil.format("PdfString. Lengths are different. Expected: {0}. Found: {1}\n", Integer.valueOf(cmpStr.length()), Integer.valueOf(outStr.length())));
        } else {
            errorMessage.append("PdfString. Characters are different.\n");
        }
        int firstDifferenceOffset = findStringDifference(outStr, cmpStr, errorMessage);
        if (!(compareResult == null || currentPath == null)) {
            currentPath.pushOffsetToPath(firstDifferenceOffset);
            compareResult.addError(currentPath, errorMessage.toString());
            currentPath.pop();
        }
        return false;
    }

    private int findStringDifference(String outString, String cmpString, StringBuilder errorMessage) {
        String outBytesNeighbours;
        String str = outString;
        String str2 = cmpString;
        int numberOfDifferentChars = 0;
        int firstDifferenceOffset = 0;
        int minLength = Math.min(cmpString.length(), outString.length());
        for (int i = 0; i < minLength; i++) {
            if (str2.charAt(i) != str.charAt(i) && (numberOfDifferentChars = numberOfDifferentChars + 1) == 1) {
                firstDifferenceOffset = i;
            }
        }
        if (numberOfDifferentChars > 0) {
            int lCmp = Math.max(0, firstDifferenceOffset - 15);
            int rCmp = Math.min(cmpString.length(), firstDifferenceOffset + 15);
            int lOut = Math.max(0, firstDifferenceOffset - 15);
            int rOut = Math.min(outString.length(), firstDifferenceOffset + 15);
            outBytesNeighbours = MessageFormatUtil.format("First characters difference is encountered at index {0}.\nExpected: {1} ({2}).\nFound: {3} ({4}).\nTotal number of different characters: {5}", Integer.valueOf(firstDifferenceOffset).toString(), String.valueOf(str2.charAt(firstDifferenceOffset)), str2.substring(lCmp, rCmp).replaceAll(NEW_LINES, " "), String.valueOf(str.charAt(firstDifferenceOffset)), str.substring(lOut, rOut).replaceAll(NEW_LINES, " "), Integer.valueOf(numberOfDifferentChars));
        } else {
            firstDifferenceOffset = minLength;
            outBytesNeighbours = MessageFormatUtil.format("All characters of the shorter string are the same as the first {0} characters of the longer one.", Integer.valueOf(minLength));
        }
        errorMessage.append(outBytesNeighbours);
        return firstDifferenceOffset;
    }

    private byte[] convertPdfStringToBytes(PdfString pdfString) {
        String value = pdfString.getValue();
        String encoding = pdfString.getEncoding();
        if (encoding == null || !PdfEncodings.UNICODE_BIG.equals(encoding) || !PdfEncodings.isPdfDocEncoding(value)) {
            return PdfEncodings.convertToBytes(value, encoding);
        }
        return PdfEncodings.convertToBytes(value, PdfEncodings.PDF_DOC_ENCODING);
    }

    private boolean compareBooleansExtended(PdfBoolean outBoolean, PdfBoolean cmpBoolean, ObjectPath currentPath, CompareResult compareResult) {
        if (cmpBoolean.getValue() == outBoolean.getValue()) {
            return true;
        }
        if (!(compareResult == null || currentPath == null)) {
            compareResult.addError(currentPath, MessageFormatUtil.format("PdfBoolean. Expected: {0}. Found: {1}.", Boolean.valueOf(cmpBoolean.getValue()), Boolean.valueOf(outBoolean.getValue())));
        }
        return false;
    }

    private List<PdfLinkAnnotation> getLinkAnnotations(int pageNum, PdfDocument document) {
        List<PdfLinkAnnotation> linkAnnotations = new ArrayList<>();
        for (PdfAnnotation annotation : document.getPage(pageNum).getAnnotations()) {
            if (PdfName.Link.equals(annotation.getSubtype())) {
                linkAnnotations.add((PdfLinkAnnotation) annotation);
            }
        }
        return linkAnnotations;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v16, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: com.itextpdf.kernel.pdf.PdfArray} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v20, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v7, resolved type: com.itextpdf.kernel.pdf.PdfArray} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v24, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v9, resolved type: com.itextpdf.kernel.pdf.PdfArray} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v28, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v9, resolved type: com.itextpdf.kernel.pdf.PdfArray} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean compareLinkAnnotations(com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation r15, com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation r16, com.itextpdf.kernel.pdf.PdfDocument r17, com.itextpdf.kernel.pdf.PdfDocument r18) {
        /*
            r14 = this;
            r0 = r14
            com.itextpdf.kernel.pdf.PdfObject r1 = r15.getDestinationObject()
            com.itextpdf.kernel.pdf.PdfObject r2 = r16.getDestinationObject()
            r3 = 0
            if (r1 == 0) goto L_0x008b
            if (r2 == 0) goto L_0x008b
            byte r4 = r1.getType()
            byte r5 = r2.getType()
            if (r4 == r5) goto L_0x0019
            return r3
        L_0x0019:
            r4 = 0
            r5 = 0
            com.itextpdf.kernel.pdf.PdfCatalog r6 = r17.getCatalog()
            com.itextpdf.kernel.pdf.PdfName r7 = com.itextpdf.kernel.pdf.PdfName.Dests
            com.itextpdf.kernel.pdf.PdfNameTree r6 = r6.getNameTree(r7)
            java.util.Map r6 = r6.getNames()
            com.itextpdf.kernel.pdf.PdfCatalog r7 = r18.getCatalog()
            com.itextpdf.kernel.pdf.PdfName r8 = com.itextpdf.kernel.pdf.PdfName.Dests
            com.itextpdf.kernel.pdf.PdfNameTree r7 = r7.getNameTree(r8)
            java.util.Map r7 = r7.getNames()
            byte r8 = r1.getType()
            switch(r8) {
                case 1: goto L_0x0079;
                case 6: goto L_0x005c;
                case 10: goto L_0x003f;
                default: goto L_0x003e;
            }
        L_0x003e:
            goto L_0x0080
        L_0x003f:
            r8 = r1
            com.itextpdf.kernel.pdf.PdfString r8 = (com.itextpdf.kernel.pdf.PdfString) r8
            java.lang.String r8 = r8.toUnicodeString()
            java.lang.Object r8 = r6.get(r8)
            r4 = r8
            com.itextpdf.kernel.pdf.PdfArray r4 = (com.itextpdf.kernel.pdf.PdfArray) r4
            r8 = r2
            com.itextpdf.kernel.pdf.PdfString r8 = (com.itextpdf.kernel.pdf.PdfString) r8
            java.lang.String r8 = r8.toUnicodeString()
            java.lang.Object r8 = r7.get(r8)
            r5 = r8
            com.itextpdf.kernel.pdf.PdfArray r5 = (com.itextpdf.kernel.pdf.PdfArray) r5
            goto L_0x0080
        L_0x005c:
            r8 = r1
            com.itextpdf.kernel.pdf.PdfName r8 = (com.itextpdf.kernel.pdf.PdfName) r8
            java.lang.String r8 = r8.getValue()
            java.lang.Object r8 = r6.get(r8)
            r4 = r8
            com.itextpdf.kernel.pdf.PdfArray r4 = (com.itextpdf.kernel.pdf.PdfArray) r4
            r8 = r2
            com.itextpdf.kernel.pdf.PdfName r8 = (com.itextpdf.kernel.pdf.PdfName) r8
            java.lang.String r8 = r8.getValue()
            java.lang.Object r8 = r7.get(r8)
            r5 = r8
            com.itextpdf.kernel.pdf.PdfArray r5 = (com.itextpdf.kernel.pdf.PdfArray) r5
            goto L_0x0080
        L_0x0079:
            r4 = r1
            com.itextpdf.kernel.pdf.PdfArray r4 = (com.itextpdf.kernel.pdf.PdfArray) r4
            r5 = r2
            com.itextpdf.kernel.pdf.PdfArray r5 = (com.itextpdf.kernel.pdf.PdfArray) r5
        L_0x0080:
            int r8 = r14.getExplicitDestinationPageNum(r4)
            int r9 = r14.getExplicitDestinationPageNum(r5)
            if (r8 == r9) goto L_0x008b
            return r3
        L_0x008b:
            com.itextpdf.kernel.pdf.PdfObject r4 = r15.getPdfObject()
            com.itextpdf.kernel.pdf.PdfDictionary r4 = (com.itextpdf.kernel.pdf.PdfDictionary) r4
            com.itextpdf.kernel.pdf.PdfObject r5 = r16.getPdfObject()
            com.itextpdf.kernel.pdf.PdfDictionary r5 = (com.itextpdf.kernel.pdf.PdfDictionary) r5
            int r6 = r4.size()
            int r7 = r5.size()
            if (r6 == r7) goto L_0x00a2
            return r3
        L_0x00a2:
            com.itextpdf.kernel.pdf.PdfName r6 = com.itextpdf.kernel.pdf.PdfName.Rect
            com.itextpdf.kernel.geom.Rectangle r6 = r4.getAsRectangle(r6)
            com.itextpdf.kernel.pdf.PdfName r7 = com.itextpdf.kernel.pdf.PdfName.Rect
            com.itextpdf.kernel.geom.Rectangle r7 = r5.getAsRectangle(r7)
            float r8 = r6.getHeight()
            float r9 = r7.getHeight()
            int r8 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
            if (r8 != 0) goto L_0x0135
            float r8 = r6.getWidth()
            float r9 = r7.getWidth()
            int r8 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
            if (r8 != 0) goto L_0x0135
            float r8 = r6.getX()
            float r9 = r7.getX()
            int r8 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
            if (r8 != 0) goto L_0x0135
            float r8 = r6.getY()
            float r9 = r7.getY()
            int r8 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
            if (r8 == 0) goto L_0x00df
            goto L_0x0135
        L_0x00df:
            java.util.Set r8 = r4.entrySet()
            java.util.Iterator r8 = r8.iterator()
        L_0x00e7:
            boolean r9 = r8.hasNext()
            if (r9 == 0) goto L_0x0133
            java.lang.Object r9 = r8.next()
            java.util.Map$Entry r9 = (java.util.Map.Entry) r9
            java.lang.Object r10 = r9.getValue()
            com.itextpdf.kernel.pdf.PdfObject r10 = (com.itextpdf.kernel.pdf.PdfObject) r10
            java.lang.Object r11 = r9.getKey()
            com.itextpdf.kernel.pdf.PdfName r11 = (com.itextpdf.kernel.pdf.PdfName) r11
            boolean r11 = r5.containsKey(r11)
            if (r11 != 0) goto L_0x0106
            return r3
        L_0x0106:
            java.lang.Object r11 = r9.getKey()
            com.itextpdf.kernel.pdf.PdfName r11 = (com.itextpdf.kernel.pdf.PdfName) r11
            com.itextpdf.kernel.pdf.PdfObject r11 = r5.get(r11)
            byte r12 = r10.getType()
            byte r13 = r11.getType()
            if (r12 == r13) goto L_0x011b
            return r3
        L_0x011b:
            byte r12 = r10.getType()
            switch(r12) {
                case 2: goto L_0x0123;
                case 3: goto L_0x0122;
                case 4: goto L_0x0122;
                case 5: goto L_0x0122;
                case 6: goto L_0x0123;
                case 7: goto L_0x0123;
                case 8: goto L_0x0123;
                case 9: goto L_0x0122;
                case 10: goto L_0x0123;
                default: goto L_0x0122;
            }
        L_0x0122:
            goto L_0x0132
        L_0x0123:
            java.lang.String r12 = r10.toString()
            java.lang.String r13 = r11.toString()
            boolean r12 = r12.equals(r13)
            if (r12 != 0) goto L_0x0132
            return r3
        L_0x0132:
            goto L_0x00e7
        L_0x0133:
            r3 = 1
            return r3
        L_0x0135:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.utils.CompareTool.compareLinkAnnotations(com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation, com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation, com.itextpdf.kernel.pdf.PdfDocument, com.itextpdf.kernel.pdf.PdfDocument):boolean");
    }

    private int getExplicitDestinationPageNum(PdfArray explicitDest) {
        PdfIndirectReference pageReference = (PdfIndirectReference) explicitDest.get(0, false);
        PdfDocument doc = pageReference.getDocument();
        for (int i = 1; i <= doc.getNumberOfPages(); i++) {
            if (((PdfDictionary) doc.getPage(i).getPdfObject()).getIndirectReference().equals(pageReference)) {
                return i;
            }
        }
        throw new IllegalArgumentException("PdfLinkAnnotation comparison: Page not found.");
    }

    private class PngFileFilter implements FileFilter {
        private PngFileFilter() {
        }

        public boolean accept(File pathname) {
            String ap = pathname.getName();
            return ap.endsWith(".png") && !ap.contains("cmp_") && ap.contains(CompareTool.this.outPdfName);
        }
    }

    private class CmpPngFileFilter implements FileFilter {
        private CmpPngFileFilter() {
        }

        public boolean accept(File pathname) {
            String ap = pathname.getName();
            return ap.endsWith(".png") && ap.contains("cmp_") && ap.contains(CompareTool.this.cmpPdfName);
        }
    }

    private class DiffPngFileFilter implements FileFilter {
        private String differenceImagePrefix;

        public DiffPngFileFilter(String differenceImagePrefix2) {
            this.differenceImagePrefix = differenceImagePrefix2;
        }

        public boolean accept(File pathname) {
            String ap = pathname.getName();
            return ap.endsWith(".png") && ap.startsWith(this.differenceImagePrefix);
        }
    }

    private class ImageNameComparator implements Comparator<File> {
        private ImageNameComparator() {
        }

        public int compare(File f1, File f2) {
            return f1.getName().compareTo(f2.getName());
        }
    }

    public class CompareResult {
        protected Map<ObjectPath, String> differences = new LinkedHashMap();
        protected int messageLimit = 1;

        public CompareResult(int messageLimit2) {
            this.messageLimit = messageLimit2;
        }

        public boolean isOk() {
            return this.differences.size() == 0;
        }

        public int getErrorCount() {
            return this.differences.size();
        }

        public String getReport() {
            StringBuilder sb = new StringBuilder();
            boolean firstEntry = true;
            for (Map.Entry<ObjectPath, String> entry : this.differences.entrySet()) {
                if (!firstEntry) {
                    sb.append("-----------------------------").append("\n");
                }
                sb.append(entry.getValue()).append("\n").append(entry.getKey().toString()).append("\n");
                firstEntry = false;
            }
            return sb.toString();
        }

        public Map<ObjectPath, String> getDifferences() {
            return this.differences;
        }

        public void writeReportToXml(OutputStream stream) throws ParserConfigurationException, TransformerException {
            Document xmlReport = XmlUtils.initNewXmlDocument();
            Element root = xmlReport.createElement("report");
            Element errors = xmlReport.createElement("errors");
            errors.setAttribute("count", String.valueOf(this.differences.size()));
            root.appendChild(errors);
            for (Map.Entry<ObjectPath, String> entry : this.differences.entrySet()) {
                Node errorNode = xmlReport.createElement("error");
                Node message = xmlReport.createElement("message");
                message.appendChild(xmlReport.createTextNode(entry.getValue()));
                Node path = entry.getKey().toXmlNode(xmlReport);
                errorNode.appendChild(message);
                errorNode.appendChild(path);
                errors.appendChild(errorNode);
            }
            xmlReport.appendChild(root);
            XmlUtils.writeXmlDocToStream(xmlReport, stream);
        }

        /* access modifiers changed from: protected */
        public boolean isMessageLimitReached() {
            return this.differences.size() >= this.messageLimit;
        }

        /* access modifiers changed from: protected */
        public void addError(ObjectPath path, String message) {
            if (this.differences.size() < this.messageLimit) {
                this.differences.put((ObjectPath) path.clone(), message);
            }
        }
    }

    public class ObjectPath {
        protected PdfIndirectReference baseCmpObject;
        protected PdfIndirectReference baseOutObject;
        protected Stack<IndirectPathItem> indirects;
        protected Stack<LocalPathItem> path;

        public ObjectPath() {
            this.path = new Stack<>();
            this.indirects = new Stack<>();
        }

        public ObjectPath(PdfIndirectReference baseCmpObject2, PdfIndirectReference baseOutObject2) {
            this.path = new Stack<>();
            Stack<IndirectPathItem> stack = new Stack<>();
            this.indirects = stack;
            this.baseCmpObject = baseCmpObject2;
            this.baseOutObject = baseOutObject2;
            stack.push(new IndirectPathItem(baseCmpObject2, baseOutObject2));
        }

        public ObjectPath(PdfIndirectReference baseCmpObject2, PdfIndirectReference baseOutObject2, Stack<LocalPathItem> path2, Stack<IndirectPathItem> indirects2) {
            this.path = new Stack<>();
            this.indirects = new Stack<>();
            this.baseCmpObject = baseCmpObject2;
            this.baseOutObject = baseOutObject2;
            this.path = path2;
            this.indirects = indirects2;
        }

        public ObjectPath resetDirectPath(PdfIndirectReference baseCmpObject2, PdfIndirectReference baseOutObject2) {
            ObjectPath newPath = new ObjectPath(baseCmpObject2, baseOutObject2, new Stack(), (Stack) this.indirects.clone());
            newPath.indirects.push(new IndirectPathItem(baseCmpObject2, baseOutObject2));
            return newPath;
        }

        public boolean isComparing(PdfIndirectReference cmpObject, PdfIndirectReference outObject) {
            return this.indirects.contains(new IndirectPathItem(cmpObject, outObject));
        }

        public void pushArrayItemToPath(int index) {
            this.path.push(new ArrayPathItem(index));
        }

        public void pushDictItemToPath(PdfName key) {
            this.path.push(new DictPathItem(key));
        }

        public void pushOffsetToPath(int offset) {
            this.path.push(new OffsetPathItem(offset));
        }

        public void pop() {
            this.path.pop();
        }

        public Stack<LocalPathItem> getLocalPath() {
            return this.path;
        }

        public Stack<IndirectPathItem> getIndirectPath() {
            return this.indirects;
        }

        public PdfIndirectReference getBaseCmpObject() {
            return this.baseCmpObject;
        }

        public PdfIndirectReference getBaseOutObject() {
            return this.baseOutObject;
        }

        public Node toXmlNode(Document document) {
            Element element = document.createElement(SvgConstants.Tags.PATH);
            Element baseNode = document.createElement("base");
            baseNode.setAttribute("cmp", MessageFormatUtil.format("{0} {1} obj", Integer.valueOf(this.baseCmpObject.getObjNumber()), Integer.valueOf(this.baseCmpObject.getGenNumber())));
            baseNode.setAttribute("out", MessageFormatUtil.format("{0} {1} obj", Integer.valueOf(this.baseOutObject.getObjNumber()), Integer.valueOf(this.baseOutObject.getGenNumber())));
            element.appendChild(baseNode);
            Stack<LocalPathItem> pathClone = (Stack) this.path.clone();
            List<LocalPathItem> localPathItems = new ArrayList<>(this.path.size());
            for (int i = 0; i < this.path.size(); i++) {
                localPathItems.add(pathClone.pop());
            }
            for (int i2 = localPathItems.size() - 1; i2 >= 0; i2--) {
                element.appendChild(localPathItems.get(i2).toXmlNode(document));
            }
            return element;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(MessageFormatUtil.format("Base cmp object: {0} obj. Base out object: {1} obj", this.baseCmpObject, this.baseOutObject));
            Stack<LocalPathItem> pathClone = (Stack) this.path.clone();
            List<LocalPathItem> localPathItems = new ArrayList<>(this.path.size());
            for (int i = 0; i < this.path.size(); i++) {
                localPathItems.add(pathClone.pop());
            }
            for (int i2 = localPathItems.size() - 1; i2 >= 0; i2--) {
                sb.append("\n");
                sb.append(localPathItems.get(i2).toString());
            }
            return sb.toString();
        }

        public int hashCode() {
            PdfIndirectReference pdfIndirectReference = this.baseCmpObject;
            int i = 0;
            int hashCode = (pdfIndirectReference != null ? pdfIndirectReference.hashCode() : 0) * 31;
            PdfIndirectReference pdfIndirectReference2 = this.baseOutObject;
            if (pdfIndirectReference2 != null) {
                i = pdfIndirectReference2.hashCode();
            }
            int hashCode2 = hashCode + i;
            Iterator it = this.path.iterator();
            while (it.hasNext()) {
                hashCode2 = (hashCode2 * 31) + ((LocalPathItem) it.next()).hashCode();
            }
            return hashCode2;
        }

        public boolean equals(Object obj) {
            return obj.getClass() == getClass() && this.baseCmpObject.equals(((ObjectPath) obj).baseCmpObject) && this.baseOutObject.equals(((ObjectPath) obj).baseOutObject) && this.path.equals(((ObjectPath) obj).path);
        }

        /* access modifiers changed from: protected */
        public Object clone() {
            return new ObjectPath(this.baseCmpObject, this.baseOutObject, (Stack) this.path.clone(), (Stack) this.indirects.clone());
        }

        public class IndirectPathItem {
            private PdfIndirectReference cmpObject;
            private PdfIndirectReference outObject;

            public IndirectPathItem(PdfIndirectReference cmpObject2, PdfIndirectReference outObject2) {
                this.cmpObject = cmpObject2;
                this.outObject = outObject2;
            }

            public PdfIndirectReference getCmpObject() {
                return this.cmpObject;
            }

            public PdfIndirectReference getOutObject() {
                return this.outObject;
            }

            public int hashCode() {
                return (this.cmpObject.hashCode() * 31) + this.outObject.hashCode();
            }

            public boolean equals(Object obj) {
                return obj.getClass() == getClass() && this.cmpObject.equals(((IndirectPathItem) obj).cmpObject) && this.outObject.equals(((IndirectPathItem) obj).outObject);
            }
        }

        public abstract class LocalPathItem {
            /* access modifiers changed from: protected */
            public abstract Node toXmlNode(Document document);

            public LocalPathItem() {
            }
        }

        public class DictPathItem extends LocalPathItem {
            PdfName key;

            public DictPathItem(PdfName key2) {
                super();
                this.key = key2;
            }

            public String toString() {
                return "Dict key: " + this.key;
            }

            public int hashCode() {
                return this.key.hashCode();
            }

            public boolean equals(Object obj) {
                return obj.getClass() == getClass() && this.key.equals(((DictPathItem) obj).key);
            }

            public PdfName getKey() {
                return this.key;
            }

            /* access modifiers changed from: protected */
            public Node toXmlNode(Document document) {
                Element element = document.createElement("dictKey");
                element.appendChild(document.createTextNode(this.key.toString()));
                return element;
            }
        }

        public class ArrayPathItem extends LocalPathItem {
            int index;

            public ArrayPathItem(int index2) {
                super();
                this.index = index2;
            }

            public String toString() {
                return "Array index: " + String.valueOf(this.index);
            }

            public int hashCode() {
                return this.index;
            }

            public boolean equals(Object obj) {
                return obj.getClass() == getClass() && this.index == ((ArrayPathItem) obj).index;
            }

            public int getIndex() {
                return this.index;
            }

            /* access modifiers changed from: protected */
            public Node toXmlNode(Document document) {
                Element element = document.createElement("arrayIndex");
                element.appendChild(document.createTextNode(String.valueOf(this.index)));
                return element;
            }
        }

        public class OffsetPathItem extends LocalPathItem {
            int offset;

            public OffsetPathItem(int offset2) {
                super();
                this.offset = offset2;
            }

            public int getOffset() {
                return this.offset;
            }

            public String toString() {
                return "Offset: " + String.valueOf(this.offset);
            }

            public int hashCode() {
                return this.offset;
            }

            public boolean equals(Object obj) {
                return obj.getClass() == getClass() && this.offset == ((OffsetPathItem) obj).offset;
            }

            /* access modifiers changed from: protected */
            public Node toXmlNode(Document document) {
                Element element = document.createElement("offset");
                element.appendChild(document.createTextNode(String.valueOf(this.offset)));
                return element;
            }
        }
    }

    private class TrailerPath extends ObjectPath {
        private PdfDocument cmpDocument;
        private PdfDocument outDocument;

        public TrailerPath(PdfDocument cmpDoc, PdfDocument outDoc) {
            super();
            this.outDocument = outDoc;
            this.cmpDocument = cmpDoc;
        }

        public TrailerPath(PdfDocument cmpDoc, PdfDocument outDoc, Stack<ObjectPath.LocalPathItem> path) {
            super();
            this.outDocument = outDoc;
            this.cmpDocument = cmpDoc;
            this.path = path;
        }

        public Node toXmlNode(Document document) {
            Element element = document.createElement(SvgConstants.Tags.PATH);
            Element baseNode = document.createElement("base");
            baseNode.setAttribute("cmp", "trailer");
            baseNode.setAttribute("out", "trailer");
            element.appendChild(baseNode);
            Iterator it = this.path.iterator();
            while (it.hasNext()) {
                element.appendChild(((ObjectPath.LocalPathItem) it.next()).toXmlNode(document));
            }
            return element;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Base cmp object: trailer. Base out object: trailer");
            Iterator it = this.path.iterator();
            while (it.hasNext()) {
                sb.append("\n");
                sb.append(((ObjectPath.LocalPathItem) it.next()).toString());
            }
            return sb.toString();
        }

        public int hashCode() {
            int hashCode = (this.outDocument.hashCode() * 31) + this.cmpDocument.hashCode();
            Iterator it = this.path.iterator();
            while (it.hasNext()) {
                hashCode = (hashCode * 31) + ((ObjectPath.LocalPathItem) it.next()).hashCode();
            }
            return hashCode;
        }

        public boolean equals(Object obj) {
            return obj.getClass() == getClass() && this.outDocument.equals(((TrailerPath) obj).outDocument) && this.cmpDocument.equals(((TrailerPath) obj).cmpDocument) && this.path.equals(((ObjectPath) obj).path);
        }

        /* access modifiers changed from: protected */
        public Object clone() {
            return new TrailerPath(this.cmpDocument, this.outDocument, (Stack) this.path.clone());
        }
    }

    public class CompareToolExecutionException extends RuntimeException {
        public CompareToolExecutionException(String msg) {
            super(msg);
        }
    }
}
