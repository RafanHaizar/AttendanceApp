package com.itextpdf.svg.css.impl;

import com.itextpdf.styledxmlparser.LogMessageConstant;
import com.itextpdf.styledxmlparser.css.CssDeclaration;
import com.itextpdf.styledxmlparser.css.CssFontFaceRule;
import com.itextpdf.styledxmlparser.css.CssStatement;
import com.itextpdf.styledxmlparser.css.CssStyleSheet;
import com.itextpdf.styledxmlparser.css.ICssResolver;
import com.itextpdf.styledxmlparser.css.media.CssMediaRule;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.parse.CssRuleSetParser;
import com.itextpdf.styledxmlparser.css.parse.CssStyleSheetParser;
import com.itextpdf.styledxmlparser.css.resolve.AbstractCssContext;
import com.itextpdf.styledxmlparser.css.resolve.CssInheritance;
import com.itextpdf.styledxmlparser.css.resolve.IStyleInheritance;
import com.itextpdf.styledxmlparser.node.IAttribute;
import com.itextpdf.styledxmlparser.node.IDocumentNode;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.INode;
import com.itextpdf.styledxmlparser.node.IStylesContainer;
import com.itextpdf.styledxmlparser.resolver.resource.ResourceResolver;
import com.itextpdf.styledxmlparser.util.StyleUtil;
import com.itextpdf.svg.processors.impl.SvgConverterProperties;
import com.itextpdf.svg.processors.impl.SvgProcessorContext;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.LoggerFactory;

public class SvgStyleResolver implements ICssResolver {
    private static final String DEFAULT_CSS_PATH = "com/itextpdf/svg/default.css";
    private CssStyleSheet css;
    private MediaDeviceDescription deviceDescription;
    private List<CssFontFaceRule> fonts;
    private ResourceResolver resourceResolver;

    @Deprecated
    public SvgStyleResolver(InputStream defaultCssStream) throws IOException {
        this(defaultCssStream, new SvgProcessorContext(new SvgConverterProperties()));
    }

    @Deprecated
    public SvgStyleResolver() {
        this(new SvgProcessorContext(new SvgConverterProperties()));
    }

    public SvgStyleResolver(InputStream defaultCssStream, SvgProcessorContext context) throws IOException {
        this.fonts = new ArrayList();
        this.css = CssStyleSheetParser.parse(defaultCssStream);
        this.resourceResolver = context.getResourceResolver();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001e, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001f, code lost:
        if (r0 != null) goto L_0x0021;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0029, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public SvgStyleResolver(com.itextpdf.svg.processors.impl.SvgProcessorContext r5) {
        /*
            r4 = this;
            r4.<init>()
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r4.fonts = r0
            java.lang.String r0 = "com/itextpdf/svg/default.css"
            java.io.InputStream r0 = com.itextpdf.p026io.util.ResourceUtil.getResourceStream(r0)     // Catch:{ IOException -> 0x002a }
            com.itextpdf.styledxmlparser.css.CssStyleSheet r1 = com.itextpdf.styledxmlparser.css.parse.CssStyleSheetParser.parse((java.io.InputStream) r0)     // Catch:{ all -> 0x001c }
            r4.css = r1     // Catch:{ all -> 0x001c }
            if (r0 == 0) goto L_0x001b
            r0.close()     // Catch:{ IOException -> 0x002a }
        L_0x001b:
            goto L_0x003f
        L_0x001c:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x001e }
        L_0x001e:
            r2 = move-exception
            if (r0 == 0) goto L_0x0029
            r0.close()     // Catch:{ all -> 0x0025 }
            goto L_0x0029
        L_0x0025:
            r3 = move-exception
            r1.addSuppressed(r3)     // Catch:{ IOException -> 0x002a }
        L_0x0029:
            throw r2     // Catch:{ IOException -> 0x002a }
        L_0x002a:
            r0 = move-exception
            java.lang.Class r1 = r4.getClass()
            org.slf4j.Logger r1 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r1)
            java.lang.String r2 = "Error loading the default CSS. Initializing an empty style sheet."
            r1.warn((java.lang.String) r2, (java.lang.Throwable) r0)
            com.itextpdf.styledxmlparser.css.CssStyleSheet r2 = new com.itextpdf.styledxmlparser.css.CssStyleSheet
            r2.<init>()
            r4.css = r2
        L_0x003f:
            com.itextpdf.styledxmlparser.resolver.resource.ResourceResolver r0 = r5.getResourceResolver()
            r4.resourceResolver = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.svg.css.impl.SvgStyleResolver.<init>(com.itextpdf.svg.processors.impl.SvgProcessorContext):void");
    }

    public SvgStyleResolver(INode rootNode, SvgProcessorContext context) {
        this.fonts = new ArrayList();
        this.deviceDescription = context.getDeviceDescription();
        ResourceResolver resourceResolver2 = context.getResourceResolver();
        this.resourceResolver = resourceResolver2;
        collectCssDeclarations(rootNode, resourceResolver2);
        collectFonts();
    }

    public Map<String, String> resolveStyles(INode node, AbstractCssContext context) {
        Map<String, String> styles = resolveNativeStyles(node, context);
        if (node.parentNode() instanceof IStylesContainer) {
            Map<String, String> parentStyles = ((IStylesContainer) node.parentNode()).getStyles();
            if (parentStyles == null && !(node.parentNode() instanceof IDocumentNode)) {
                LoggerFactory.getLogger((Class<?>) SvgStyleResolver.class).error(LogMessageConstant.ERROR_RESOLVING_PARENT_STYLES);
            }
            Set<IStyleInheritance> inheritanceRules = new HashSet<>();
            inheritanceRules.add(new CssInheritance());
            inheritanceRules.add(new SvgAttributeInheritance());
            if (parentStyles != null) {
                for (Map.Entry<String, String> entry : parentStyles.entrySet()) {
                    String parentFontSizeString = parentStyles.get("font-size");
                    if (parentFontSizeString == null) {
                        parentFontSizeString = "0";
                    }
                    styles = StyleUtil.mergeParentStyleDeclaration(styles, entry.getKey(), entry.getValue(), parentFontSizeString, inheritanceRules);
                }
            }
        }
        return styles;
    }

    public Map<String, String> resolveNativeStyles(INode node, AbstractCssContext cssContext) {
        Map<String, String> styles = new HashMap<>();
        for (CssDeclaration ssd : this.css.getCssDeclarations(node, MediaDeviceDescription.createDefault())) {
            styles.put(ssd.getProperty(), ssd.getExpression());
        }
        if (node instanceof IElementNode) {
            for (IAttribute attr : ((IElementNode) node).getAttributes()) {
                processAttribute(attr, styles);
            }
        }
        return styles;
    }

    private void processXLink(IAttribute attr, Map<String, String> attributesMap) {
        String xlinkValue = attr.getValue();
        if (!isStartedWithHash(xlinkValue)) {
            try {
                xlinkValue = this.resourceResolver.resolveAgainstBaseUri(attr.getValue()).toExternalForm();
            } catch (MalformedURLException mue) {
                LoggerFactory.getLogger((Class<?>) SvgStyleResolver.class).error(LogMessageConstant.UNABLE_TO_RESOLVE_IMAGE_URL, (Throwable) mue);
            }
        }
        attributesMap.put(attr.getKey(), xlinkValue);
    }

    private boolean isStartedWithHash(String s) {
        return s != null && s.startsWith("#");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00ae, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00af, code lost:
        if (r4 != null) goto L_0x00b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00b9, code lost:
        throw r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void collectCssDeclarations(com.itextpdf.styledxmlparser.node.INode r9, com.itextpdf.styledxmlparser.resolver.resource.ResourceResolver r10) {
        /*
            r8 = this;
            com.itextpdf.styledxmlparser.css.CssStyleSheet r0 = new com.itextpdf.styledxmlparser.css.CssStyleSheet
            r0.<init>()
            r8.css = r0
            java.util.LinkedList r0 = new java.util.LinkedList
            r0.<init>()
            if (r9 == 0) goto L_0x0011
            r0.add(r9)
        L_0x0011:
            boolean r1 = r0.isEmpty()
            if (r1 != 0) goto L_0x00ea
            java.lang.Object r1 = r0.pop()
            com.itextpdf.styledxmlparser.node.INode r1 = (com.itextpdf.styledxmlparser.node.INode) r1
            boolean r2 = r1 instanceof com.itextpdf.styledxmlparser.node.IElementNode
            if (r2 == 0) goto L_0x00cc
            r2 = r1
            com.itextpdf.styledxmlparser.node.IElementNode r2 = (com.itextpdf.styledxmlparser.node.IElementNode) r2
            java.lang.String r3 = "style"
            java.lang.String r4 = r2.name()
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0087
            java.util.List r3 = r1.childNodes()
            boolean r3 = r3.isEmpty()
            if (r3 != 0) goto L_0x00cc
            java.util.List r3 = r1.childNodes()
            r4 = 0
            java.lang.Object r3 = r3.get(r4)
            boolean r3 = r3 instanceof com.itextpdf.styledxmlparser.node.IDataNode
            if (r3 != 0) goto L_0x0054
            java.util.List r3 = r1.childNodes()
            java.lang.Object r3 = r3.get(r4)
            boolean r3 = r3 instanceof com.itextpdf.styledxmlparser.node.ITextNode
            if (r3 == 0) goto L_0x00cc
        L_0x0054:
            java.util.List r3 = r1.childNodes()
            java.lang.Object r3 = r3.get(r4)
            boolean r3 = r3 instanceof com.itextpdf.styledxmlparser.node.IDataNode
            if (r3 == 0) goto L_0x006f
            java.util.List r3 = r1.childNodes()
            java.lang.Object r3 = r3.get(r4)
            com.itextpdf.styledxmlparser.node.IDataNode r3 = (com.itextpdf.styledxmlparser.node.IDataNode) r3
            java.lang.String r3 = r3.getWholeData()
            goto L_0x007d
        L_0x006f:
            java.util.List r3 = r1.childNodes()
            java.lang.Object r3 = r3.get(r4)
            com.itextpdf.styledxmlparser.node.ITextNode r3 = (com.itextpdf.styledxmlparser.node.ITextNode) r3
            java.lang.String r3 = r3.wholeText()
        L_0x007d:
            com.itextpdf.styledxmlparser.css.CssStyleSheet r4 = com.itextpdf.styledxmlparser.css.parse.CssStyleSheetParser.parse((java.lang.String) r3)
            com.itextpdf.styledxmlparser.css.CssStyleSheet r5 = r8.css
            r5.appendCssStyleSheet(r4)
            goto L_0x00cc
        L_0x0087:
            boolean r3 = com.itextpdf.styledxmlparser.css.util.CssUtils.isStyleSheetLink(r2)
            if (r3 == 0) goto L_0x00cc
            java.lang.String r3 = "href"
            java.lang.String r3 = r2.getAttribute(r3)
            java.io.InputStream r4 = r10.retrieveResourceAsInputStream(r3)     // Catch:{ Exception -> 0x00c0 }
            if (r4 == 0) goto L_0x00ba
            java.net.URL r5 = r10.resolveAgainstBaseUri(r3)     // Catch:{ all -> 0x00ac }
            java.lang.String r5 = r5.toExternalForm()     // Catch:{ all -> 0x00ac }
            com.itextpdf.styledxmlparser.css.CssStyleSheet r5 = com.itextpdf.styledxmlparser.css.parse.CssStyleSheetParser.parse((java.io.InputStream) r4, (java.lang.String) r5)     // Catch:{ all -> 0x00ac }
            com.itextpdf.styledxmlparser.css.CssStyleSheet r6 = r8.css     // Catch:{ all -> 0x00ac }
            r6.appendCssStyleSheet(r5)     // Catch:{ all -> 0x00ac }
            goto L_0x00ba
        L_0x00ac:
            r5 = move-exception
            throw r5     // Catch:{ all -> 0x00ae }
        L_0x00ae:
            r6 = move-exception
            if (r4 == 0) goto L_0x00b9
            r4.close()     // Catch:{ all -> 0x00b5 }
            goto L_0x00b9
        L_0x00b5:
            r7 = move-exception
            r5.addSuppressed(r7)     // Catch:{ Exception -> 0x00c0 }
        L_0x00b9:
            throw r6     // Catch:{ Exception -> 0x00c0 }
        L_0x00ba:
            if (r4 == 0) goto L_0x00bf
            r4.close()     // Catch:{ Exception -> 0x00c0 }
        L_0x00bf:
            goto L_0x00cc
        L_0x00c0:
            r4 = move-exception
            java.lang.Class<com.itextpdf.svg.css.impl.SvgStyleResolver> r5 = com.itextpdf.svg.css.impl.SvgStyleResolver.class
            org.slf4j.Logger r5 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r5)
            java.lang.String r6 = "Unable to process external css file"
            r5.error((java.lang.String) r6, (java.lang.Throwable) r4)
        L_0x00cc:
            java.util.List r2 = r1.childNodes()
            java.util.Iterator r2 = r2.iterator()
        L_0x00d4:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x00e8
            java.lang.Object r3 = r2.next()
            com.itextpdf.styledxmlparser.node.INode r3 = (com.itextpdf.styledxmlparser.node.INode) r3
            boolean r4 = r3 instanceof com.itextpdf.styledxmlparser.node.IElementNode
            if (r4 == 0) goto L_0x00e7
            r0.add(r3)
        L_0x00e7:
            goto L_0x00d4
        L_0x00e8:
            goto L_0x0011
        L_0x00ea:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.svg.css.impl.SvgStyleResolver.collectCssDeclarations(com.itextpdf.styledxmlparser.node.INode, com.itextpdf.styledxmlparser.resolver.resource.ResourceResolver):void");
    }

    public List<CssFontFaceRule> getFonts() {
        return new ArrayList(this.fonts);
    }

    private void collectFonts() {
        for (CssStatement cssStatement : this.css.getStatements()) {
            collectFonts(cssStatement);
        }
    }

    private void collectFonts(CssStatement cssStatement) {
        if (cssStatement instanceof CssFontFaceRule) {
            this.fonts.add((CssFontFaceRule) cssStatement);
        } else if ((cssStatement instanceof CssMediaRule) && ((CssMediaRule) cssStatement).matchMediaDevice(this.deviceDescription)) {
            for (CssStatement cssSubStatement : ((CssMediaRule) cssStatement).getStatements()) {
                collectFonts(cssSubStatement);
            }
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void processAttribute(com.itextpdf.styledxmlparser.node.IAttribute r6, java.util.Map<java.lang.String, java.lang.String> r7) {
        /*
            r5 = this;
            java.lang.String r0 = r6.getKey()
            int r1 = r0.hashCode()
            switch(r1) {
                case 109780401: goto L_0x0017;
                case 529372467: goto L_0x000c;
                default: goto L_0x000b;
            }
        L_0x000b:
            goto L_0x0022
        L_0x000c:
            java.lang.String r1 = "xlink:href"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000b
            r0 = 1
            goto L_0x0023
        L_0x0017:
            java.lang.String r1 = "style"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000b
            r0 = 0
            goto L_0x0023
        L_0x0022:
            r0 = -1
        L_0x0023:
            switch(r0) {
                case 0: goto L_0x0036;
                case 1: goto L_0x0032;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.String r0 = r6.getKey()
            java.lang.String r1 = r6.getValue()
            r7.put(r0, r1)
            goto L_0x005f
        L_0x0032:
            r5.processXLink(r6, r7)
            goto L_0x005f
        L_0x0036:
            java.lang.String r0 = r6.getValue()
            java.util.Map r0 = r5.parseStylesFromStyleAttribute(r0)
            java.util.Set r1 = r0.entrySet()
            java.util.Iterator r1 = r1.iterator()
        L_0x0046:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x005e
            java.lang.Object r2 = r1.next()
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2
            java.lang.Object r3 = r2.getKey()
            java.lang.Object r4 = r2.getValue()
            r7.put(r3, r4)
            goto L_0x0046
        L_0x005e:
        L_0x005f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.svg.css.impl.SvgStyleResolver.processAttribute(com.itextpdf.styledxmlparser.node.IAttribute, java.util.Map):void");
    }

    private Map<String, String> parseStylesFromStyleAttribute(String style) {
        Map<String, String> parsed = new HashMap<>();
        for (CssDeclaration declaration : CssRuleSetParser.parsePropertyDeclarations(style)) {
            parsed.put(declaration.getProperty(), declaration.getExpression());
        }
        return parsed;
    }
}
