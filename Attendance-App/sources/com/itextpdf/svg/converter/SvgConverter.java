package com.itextpdf.svg.converter;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.styledxmlparser.node.INode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.JsoupXmlParser;
import com.itextpdf.styledxmlparser.resolver.resource.IResourceRetriever;
import com.itextpdf.styledxmlparser.resolver.resource.ResourceResolver;
import com.itextpdf.svg.SvgConstants;
import com.itextpdf.svg.exceptions.SvgLogMessageConstant;
import com.itextpdf.svg.exceptions.SvgProcessingException;
import com.itextpdf.svg.processors.ISvgConverterProperties;
import com.itextpdf.svg.processors.ISvgProcessorResult;
import com.itextpdf.svg.processors.impl.DefaultSvgProcessor;
import com.itextpdf.svg.processors.impl.SvgConverterProperties;
import com.itextpdf.svg.processors.impl.SvgProcessorContext;
import com.itextpdf.svg.processors.impl.SvgProcessorResult;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.SvgDrawContext;
import com.itextpdf.svg.renderers.impl.PdfRootSvgNodeRenderer;
import com.itextpdf.svg.utils.SvgCssUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SvgConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger((Class<?>) SvgConverter.class);

    private SvgConverter() {
    }

    private static void checkNull(Object o) {
        if (o == null) {
            throw new SvgProcessingException(SvgLogMessageConstant.PARAMETER_CANNOT_BE_NULL);
        }
    }

    public static void drawOnDocument(String content, PdfDocument document, int pageNo) {
        drawOnDocument(content, document, pageNo, 0.0f, 0.0f);
    }

    public static void drawOnDocument(String content, PdfDocument document, int pageNo, float x, float y) {
        checkNull(document);
        drawOnPage(content, document.getPage(pageNo), x, y);
    }

    public static void drawOnDocument(String content, PdfDocument document, int pageNo, ISvgConverterProperties props) {
        drawOnDocument(content, document, pageNo, 0.0f, 0.0f, props);
    }

    public static void drawOnDocument(String content, PdfDocument document, int pageNo, float x, float y, ISvgConverterProperties props) {
        checkNull(document);
        drawOnPage(content, document.getPage(pageNo), x, y, props);
    }

    public static void drawOnDocument(InputStream stream, PdfDocument document, int pageNo) throws IOException {
        drawOnDocument(stream, document, pageNo, 0.0f, 0.0f);
    }

    public static void drawOnDocument(InputStream stream, PdfDocument document, int pageNo, float x, float y) throws IOException {
        checkNull(document);
        drawOnPage(stream, document.getPage(pageNo), x, y);
    }

    public static void drawOnDocument(InputStream stream, PdfDocument document, int pageNo, ISvgConverterProperties props) throws IOException {
        drawOnDocument(stream, document, pageNo, 0.0f, 0.0f, props);
    }

    public static void drawOnDocument(InputStream stream, PdfDocument document, int pageNo, float x, float y, ISvgConverterProperties props) throws IOException {
        checkNull(document);
        drawOnPage(stream, document.getPage(pageNo), x, y, props);
    }

    public static void drawOnPage(String content, PdfPage page) {
        drawOnPage(content, page, 0.0f, 0.0f);
    }

    public static void drawOnPage(String content, PdfPage page, float x, float y) {
        checkNull(page);
        drawOnCanvas(content, new PdfCanvas(page), x, y);
    }

    public static void drawOnPage(String content, PdfPage page, ISvgConverterProperties props) {
        drawOnPage(content, page, 0.0f, 0.0f, props);
    }

    public static void drawOnPage(String content, PdfPage page, float x, float y, ISvgConverterProperties props) {
        checkNull(page);
        drawOnCanvas(content, new PdfCanvas(page), x, y, props);
    }

    public static void drawOnPage(InputStream stream, PdfPage page) throws IOException {
        drawOnPage(stream, page, 0.0f, 0.0f);
    }

    public static void drawOnPage(InputStream stream, PdfPage page, float x, float y) throws IOException {
        checkNull(page);
        drawOnCanvas(stream, new PdfCanvas(page), x, y);
    }

    public static void drawOnPage(InputStream stream, PdfPage page, ISvgConverterProperties props) throws IOException {
        drawOnPage(stream, page, 0.0f, 0.0f, props);
    }

    public static void drawOnPage(InputStream stream, PdfPage page, float x, float y, ISvgConverterProperties props) throws IOException {
        checkNull(page);
        drawOnCanvas(stream, new PdfCanvas(page), x, y, props);
    }

    public static void drawOnCanvas(String content, PdfCanvas canvas) {
        drawOnCanvas(content, canvas, 0.0f, 0.0f);
    }

    public static void drawOnCanvas(String content, PdfCanvas canvas, float x, float y) {
        checkNull(canvas);
        draw(convertToXObject(content, canvas.getDocument()), canvas, x, y);
    }

    public static void drawOnCanvas(String content, PdfCanvas canvas, ISvgConverterProperties props) {
        drawOnCanvas(content, canvas, 0.0f, 0.0f, props);
    }

    public static void drawOnCanvas(String content, PdfCanvas canvas, float x, float y, ISvgConverterProperties props) {
        checkNull(canvas);
        draw(convertToXObject(content, canvas.getDocument(), props), canvas, x, y);
    }

    public static void drawOnCanvas(InputStream stream, PdfCanvas canvas) throws IOException {
        drawOnCanvas(stream, canvas, 0.0f, 0.0f);
    }

    public static void drawOnCanvas(InputStream stream, PdfCanvas canvas, float x, float y) throws IOException {
        checkNull(canvas);
        draw(convertToXObject(stream, canvas.getDocument()), canvas, x, y);
    }

    public static void drawOnCanvas(InputStream stream, PdfCanvas canvas, ISvgConverterProperties props) throws IOException {
        drawOnCanvas(stream, canvas, 0.0f, 0.0f, props);
    }

    public static void drawOnCanvas(InputStream stream, PdfCanvas canvas, float x, float y, ISvgConverterProperties props) throws IOException {
        checkNull(canvas);
        draw(convertToXObject(stream, canvas.getDocument(), props), canvas, x, y);
    }

    public static void createPdf(File svgFile, File pdfFile) throws IOException {
        createPdf(svgFile, pdfFile, (ISvgConverterProperties) null, (WriterProperties) null);
    }

    public static void createPdf(File svgFile, File pdfFile, ISvgConverterProperties props) throws IOException {
        createPdf(svgFile, pdfFile, props, (WriterProperties) null);
    }

    public static void createPdf(File svgFile, File pdfFile, WriterProperties writerProps) throws IOException {
        createPdf(svgFile, pdfFile, (ISvgConverterProperties) null, writerProps);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0047, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x004c, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        r2.addSuppressed(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0050, code lost:
        throw r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0053, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0058, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0059, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x005c, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void createPdf(java.io.File r5, java.io.File r6, com.itextpdf.svg.processors.ISvgConverterProperties r7, com.itextpdf.kernel.pdf.WriterProperties r8) throws java.io.IOException {
        /*
            if (r7 != 0) goto L_0x0010
            com.itextpdf.svg.processors.impl.SvgConverterProperties r0 = new com.itextpdf.svg.processors.impl.SvgConverterProperties
            r0.<init>()
            java.lang.String r1 = com.itextpdf.p026io.util.FileUtil.getParentDirectory((java.io.File) r5)
            com.itextpdf.svg.processors.impl.SvgConverterProperties r7 = r0.setBaseUri(r1)
            goto L_0x0028
        L_0x0010:
            java.lang.String r0 = r7.getBaseUri()
            if (r0 == 0) goto L_0x0020
            java.lang.String r0 = r7.getBaseUri()
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L_0x0028
        L_0x0020:
            java.lang.String r0 = com.itextpdf.p026io.util.FileUtil.getParentDirectory((java.io.File) r5)
            com.itextpdf.svg.processors.impl.SvgConverterProperties r7 = convertToSvgConverterProps(r7, r0)
        L_0x0028:
            java.io.FileInputStream r0 = new java.io.FileInputStream
            java.lang.String r1 = r5.getAbsolutePath()
            r0.<init>(r1)
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ all -> 0x0051 }
            java.lang.String r2 = r6.getAbsolutePath()     // Catch:{ all -> 0x0051 }
            r1.<init>(r2)     // Catch:{ all -> 0x0051 }
            createPdf((java.io.InputStream) r0, (java.io.OutputStream) r1, (com.itextpdf.svg.processors.ISvgConverterProperties) r7, (com.itextpdf.kernel.pdf.WriterProperties) r8)     // Catch:{ all -> 0x0045 }
            r1.close()     // Catch:{ all -> 0x0051 }
            r0.close()
            return
        L_0x0045:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x0047 }
        L_0x0047:
            r3 = move-exception
            r1.close()     // Catch:{ all -> 0x004c }
            goto L_0x0050
        L_0x004c:
            r4 = move-exception
            r2.addSuppressed(r4)     // Catch:{ all -> 0x0051 }
        L_0x0050:
            throw r3     // Catch:{ all -> 0x0051 }
        L_0x0051:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0053 }
        L_0x0053:
            r2 = move-exception
            r0.close()     // Catch:{ all -> 0x0058 }
            goto L_0x005c
        L_0x0058:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x005c:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.svg.converter.SvgConverter.createPdf(java.io.File, java.io.File, com.itextpdf.svg.processors.ISvgConverterProperties, com.itextpdf.kernel.pdf.WriterProperties):void");
    }

    private static SvgConverterProperties convertToSvgConverterProps(ISvgConverterProperties props, String baseUri) {
        return new SvgConverterProperties().setBaseUri(baseUri).setMediaDeviceDescription(props.getMediaDeviceDescription()).setFontProvider(props.getFontProvider()).setCharset(props.getCharset()).setRendererFactory(props.getRendererFactory());
    }

    public static void createPdf(InputStream svgStream, OutputStream pdfDest) throws IOException {
        createPdf(svgStream, pdfDest, (ISvgConverterProperties) null, (WriterProperties) null);
    }

    public static void createPdf(InputStream svgStream, OutputStream pdfDest, WriterProperties writerprops) throws IOException {
        createPdf(svgStream, pdfDest, (ISvgConverterProperties) null, writerprops);
    }

    public static void createPdf(InputStream svgStream, OutputStream pdfDest, ISvgConverterProperties props) throws IOException {
        createPdf(svgStream, pdfDest, props, (WriterProperties) null);
    }

    public static void createPdf(InputStream svgStream, OutputStream pdfDest, ISvgConverterProperties props, WriterProperties writerProps) throws IOException {
        if (writerProps == null) {
            writerProps = new WriterProperties();
        }
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(pdfDest, writerProps));
        ISvgProcessorResult processorResult = process(parse(svgStream, props), props);
        ISvgNodeRenderer topSvgRenderer = processorResult.getRootRenderer();
        SvgDrawContext drawContext = new SvgDrawContext(getResourceResolver(processorResult, props), processorResult.getFontProvider(), processorResult.getRootRenderer());
        drawContext.addNamedObjects(processorResult.getNamedObjects());
        drawContext.setTempFonts(processorResult.getTempFonts());
        checkNull(topSvgRenderer);
        checkNull(pdfDocument);
        float[] wh = extractWidthAndHeight(topSvgRenderer);
        pdfDocument.setDefaultPageSize(new PageSize(wh[0], wh[1]));
        draw(convertToXObject(topSvgRenderer, pdfDocument, drawContext), new PdfCanvas(pdfDocument.addNewPage()));
        pdfDocument.close();
    }

    public static PdfFormXObject convertToXObject(String content, PdfDocument document) {
        return convertToXObject(content, document, (ISvgConverterProperties) null);
    }

    public static PdfFormXObject convertToXObject(String content, PdfDocument document, ISvgConverterProperties props) {
        checkNull(content);
        checkNull(document);
        return convertToXObject(process(parse(content), props), document, props);
    }

    public static PdfFormXObject convertToXObject(InputStream stream, PdfDocument document, ISvgConverterProperties props) throws IOException {
        checkNull(stream);
        checkNull(document);
        return convertToXObject(process(parse(stream, props), props), document, props);
    }

    private static PdfFormXObject convertToXObject(ISvgProcessorResult processorResult, PdfDocument document, ISvgConverterProperties props) {
        SvgDrawContext drawContext = new SvgDrawContext(getResourceResolver(processorResult, props), processorResult.getFontProvider(), processorResult.getRootRenderer());
        drawContext.setTempFonts(processorResult.getTempFonts());
        drawContext.addNamedObjects(processorResult.getNamedObjects());
        return convertToXObject(processorResult.getRootRenderer(), document, drawContext);
    }

    public static PdfFormXObject convertToXObject(InputStream stream, PdfDocument document) throws IOException {
        return convertToXObject(stream, document, (ISvgConverterProperties) null);
    }

    public static Image convertToImage(InputStream stream, PdfDocument document) throws IOException {
        return new Image(convertToXObject(stream, document));
    }

    public static Image convertToImage(InputStream stream, PdfDocument document, ISvgConverterProperties props) throws IOException {
        return new Image(convertToXObject(stream, document, props));
    }

    private static void draw(PdfFormXObject pdfForm, PdfCanvas canvas) {
        canvas.addXObject(pdfForm, 0.0f, 0.0f);
    }

    private static void draw(PdfFormXObject pdfForm, PdfCanvas canvas, float x, float y) {
        canvas.addXObject(pdfForm, x, y);
    }

    public static PdfFormXObject convertToXObject(ISvgNodeRenderer topSvgRenderer, PdfDocument document) {
        return convertToXObject(topSvgRenderer, document, new SvgDrawContext((ResourceResolver) null, (FontProvider) null));
    }

    private static PdfFormXObject convertToXObject(ISvgNodeRenderer topSvgRenderer, PdfDocument document, SvgDrawContext context) {
        checkNull(topSvgRenderer);
        checkNull(document);
        checkNull(context);
        float[] wh = extractWidthAndHeight(topSvgRenderer);
        PdfFormXObject pdfForm = new PdfFormXObject(new Rectangle(0.0f, 0.0f, wh[0], wh[1]));
        context.pushCanvas(new PdfCanvas(pdfForm, document));
        new PdfRootSvgNodeRenderer(topSvgRenderer).draw(context);
        return pdfForm;
    }

    public static ISvgProcessorResult parseAndProcess(InputStream svgStream) throws IOException {
        return parseAndProcess(svgStream, (ISvgConverterProperties) null);
    }

    public static ISvgProcessorResult parseAndProcess(InputStream svgStream, ISvgConverterProperties props) throws IOException {
        try {
            return new DefaultSvgProcessor().process(new JsoupXmlParser().parse(svgStream, tryToExtractCharset(props)), props);
        } catch (Exception e) {
            throw new SvgProcessingException(SvgLogMessageConstant.FAILED_TO_PARSE_INPUTSTREAM, e);
        }
    }

    @Deprecated
    public static ISvgProcessorResult process(INode root) {
        return process(root, (ISvgConverterProperties) null);
    }

    public static ISvgProcessorResult process(INode root, ISvgConverterProperties props) {
        checkNull(root);
        return new DefaultSvgProcessor().process(root, props);
    }

    public static INode parse(String content) {
        checkNull(content);
        return new JsoupXmlParser().parse(content);
    }

    public static INode parse(InputStream stream) throws IOException {
        checkNull(stream);
        return parse(stream, (ISvgConverterProperties) null);
    }

    public static INode parse(InputStream stream, ISvgConverterProperties props) throws IOException {
        checkNull(stream);
        return new JsoupXmlParser().parse(stream, tryToExtractCharset(props));
    }

    public static float[] extractWidthAndHeight(ISvgNodeRenderer topSvgRenderer) {
        float width;
        float height;
        float[] res = new float[2];
        boolean viewBoxPresent = false;
        String vbString = topSvgRenderer.getAttribute(SvgConstants.Attributes.VIEWBOX);
        float[] values = {0.0f, 0.0f, 0.0f, 0.0f};
        if (vbString != null) {
            List<String> valueStrings = SvgCssUtils.splitValueList(vbString);
            values = new float[valueStrings.size()];
            for (int i = 0; i < values.length; i++) {
                values[i] = CssUtils.parseAbsoluteLength(valueStrings.get(i));
            }
            viewBoxPresent = true;
        }
        String wString = topSvgRenderer.getAttribute("width");
        if (wString != null) {
            width = CssUtils.parseAbsoluteLength(wString);
        } else if (viewBoxPresent) {
            width = values[2];
        } else {
            LOGGER.warn(SvgLogMessageConstant.MISSING_WIDTH);
            width = CssUtils.parseAbsoluteLength("300px");
        }
        String hString = topSvgRenderer.getAttribute("height");
        if (hString != null) {
            height = CssUtils.parseAbsoluteLength(hString);
        } else if (viewBoxPresent) {
            height = values[3];
        } else {
            LOGGER.warn(SvgLogMessageConstant.MISSING_HEIGHT);
            height = CssUtils.parseAbsoluteLength("150px");
        }
        res[0] = width;
        res[1] = height;
        return res;
    }

    static ResourceResolver getResourceResolver(ISvgProcessorResult processorResult, ISvgConverterProperties props) {
        SvgProcessorContext context;
        ResourceResolver resourceResolver = null;
        if ((processorResult instanceof SvgProcessorResult) && (context = ((SvgProcessorResult) processorResult).getContext()) != null) {
            resourceResolver = context.getResourceResolver();
        }
        if (resourceResolver == null) {
            return createResourceResolver(props);
        }
        return resourceResolver;
    }

    private static String tryToExtractCharset(ISvgConverterProperties props) {
        if (props != null) {
            return props.getCharset();
        }
        return null;
    }

    private static ResourceResolver createResourceResolver(ISvgConverterProperties props) {
        if (props == null) {
            return new ResourceResolver((String) null);
        }
        if (props instanceof SvgConverterProperties) {
            return new ResourceResolver(props.getBaseUri(), ((SvgConverterProperties) props).getResourceRetriever());
        }
        return new ResourceResolver(props.getBaseUri(), (IResourceRetriever) null);
    }
}
