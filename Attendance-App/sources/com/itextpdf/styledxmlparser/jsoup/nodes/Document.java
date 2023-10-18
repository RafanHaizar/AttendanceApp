package com.itextpdf.styledxmlparser.jsoup.nodes;

import com.inan.cmhs.attendance.BuildConfig;
import com.itextpdf.forms.xfdf.XfdfConstants;
import com.itextpdf.p026io.font.PdfEncodings;
import com.itextpdf.styledxmlparser.jsoup.helper.StringUtil;
import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
import com.itextpdf.styledxmlparser.jsoup.nodes.Entities;
import com.itextpdf.styledxmlparser.jsoup.parser.Tag;
import com.itextpdf.styledxmlparser.jsoup.select.Elements;
import com.itextpdf.svg.SvgConstants;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.List;

public class Document extends Element {
    private String location;
    private OutputSettings outputSettings = new OutputSettings();
    private QuirksMode quirksMode = QuirksMode.noQuirks;
    private boolean updateMetaCharset = false;

    public enum QuirksMode {
        noQuirks,
        quirks,
        limitedQuirks
    }

    public Document(String baseUri) {
        super(Tag.valueOf("#root"), baseUri);
        this.location = baseUri;
    }

    public static Document createShell(String baseUri) {
        Validate.notNull(baseUri);
        Document doc = new Document(baseUri);
        Element html = doc.appendElement("html");
        html.appendElement(XfdfConstants.HEAD);
        html.appendElement("body");
        return doc;
    }

    public String location() {
        return this.location;
    }

    public Element head() {
        return findFirstElementByTagName(XfdfConstants.HEAD, this);
    }

    public Element body() {
        return findFirstElementByTagName("body", this);
    }

    public String title() {
        Element titleEl = getElementsByTag("title").first();
        return titleEl != null ? StringUtil.normaliseWhitespace(titleEl.text()).trim() : "";
    }

    public void title(String title) {
        Validate.notNull(title);
        Element titleEl = getElementsByTag("title").first();
        if (titleEl == null) {
            head().appendElement("title").text(title);
        } else {
            titleEl.text(title);
        }
    }

    public Element createElement(String tagName) {
        return new Element(Tag.valueOf(tagName), baseUri());
    }

    public Document normalise() {
        Element htmlEl = findFirstElementByTagName("html", this);
        if (htmlEl == null) {
            htmlEl = appendElement("html");
        }
        if (head() == null) {
            htmlEl.prependElement(XfdfConstants.HEAD);
        }
        if (body() == null) {
            htmlEl.appendElement("body");
        }
        normaliseTextNodes(head());
        normaliseTextNodes(htmlEl);
        normaliseTextNodes(this);
        normaliseStructure(XfdfConstants.HEAD, htmlEl);
        normaliseStructure("body", htmlEl);
        ensureMetaCharsetElement();
        return this;
    }

    private void normaliseTextNodes(Element element) {
        List<Node> toMove = new ArrayList<>();
        for (Node node : element.childNodes) {
            if (node instanceof TextNode) {
                TextNode tn = (TextNode) node;
                if (!tn.isBlank()) {
                    toMove.add(tn);
                }
            }
        }
        for (int i = toMove.size() - 1; i >= 0; i--) {
            Node node2 = toMove.get(i);
            element.removeChild(node2);
            body().prependChild(new TextNode(" ", ""));
            body().prependChild(node2);
        }
    }

    private void normaliseStructure(String tag, Element htmlEl) {
        Elements elements = getElementsByTag(tag);
        Element master = elements.first();
        if (elements.size() > 1) {
            List<Node> toMove = new ArrayList<>();
            for (int i = 1; i < elements.size(); i++) {
                Node dupe = (Node) elements.get(i);
                for (Node node : dupe.childNodes) {
                    toMove.add(node);
                }
                dupe.remove();
            }
            for (Node dupe2 : toMove) {
                master.appendChild(dupe2);
            }
        }
        if (!master.parent().equals(htmlEl)) {
            htmlEl.appendChild(master);
        }
    }

    private Element findFirstElementByTagName(String tag, Node node) {
        if (node.nodeName().equals(tag)) {
            return (Element) node;
        }
        for (Node child : node.childNodes) {
            Element found = findFirstElementByTagName(tag, child);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    public String outerHtml() {
        return super.html();
    }

    public Element text(String text) {
        body().text(text);
        return this;
    }

    public String nodeName() {
        return "#document";
    }

    public void charset(Charset charset) {
        updateMetaCharsetElement(true);
        this.outputSettings.charset(charset);
        ensureMetaCharsetElement();
    }

    public Charset charset() {
        return this.outputSettings.charset();
    }

    public void updateMetaCharsetElement(boolean update) {
        this.updateMetaCharset = update;
    }

    public boolean updateMetaCharsetElement() {
        return this.updateMetaCharset;
    }

    public Object clone() {
        Document clone = (Document) super.clone();
        clone.outputSettings = (OutputSettings) this.outputSettings.clone();
        return clone;
    }

    private void ensureMetaCharsetElement() {
        if (this.updateMetaCharset) {
            OutputSettings.Syntax syntax = outputSettings().syntax();
            if (syntax == OutputSettings.Syntax.html) {
                Element metaCharset = select("meta[charset]").first();
                if (metaCharset != null) {
                    metaCharset.attr("charset", charset().displayName());
                } else {
                    Element head = head();
                    if (head != null) {
                        head.appendElement("meta").attr("charset", charset().displayName());
                    }
                }
                select("meta[name=charset]").remove();
            } else if (syntax == OutputSettings.Syntax.xml) {
                Node node = childNodes().get(0);
                if (node instanceof XmlDeclaration) {
                    XmlDeclaration decl = (XmlDeclaration) node;
                    if (decl.name().equals("xml")) {
                        decl.attr("encoding", charset().displayName());
                        if (decl.attr(SvgConstants.Attributes.VERSION) != null) {
                            decl.attr(SvgConstants.Attributes.VERSION, BuildConfig.VERSION_NAME);
                            return;
                        }
                        return;
                    }
                    XmlDeclaration decl2 = new XmlDeclaration("xml", this.baseUri, false);
                    decl2.attr(SvgConstants.Attributes.VERSION, BuildConfig.VERSION_NAME);
                    decl2.attr("encoding", charset().displayName());
                    prependChild(decl2);
                    return;
                }
                XmlDeclaration decl3 = new XmlDeclaration("xml", this.baseUri, false);
                decl3.attr(SvgConstants.Attributes.VERSION, BuildConfig.VERSION_NAME);
                decl3.attr("encoding", charset().displayName());
                prependChild(decl3);
            }
        }
    }

    public static class OutputSettings implements Cloneable {
        private Charset charset = Charset.forName(PdfEncodings.UTF8);
        private CharsetEncoder charsetEncoder = this.charset.newEncoder();
        private Entities.EscapeMode escapeMode = Entities.EscapeMode.base;
        private int indentAmount = 1;
        private boolean outline = false;
        private boolean prettyPrint = true;
        private Syntax syntax = Syntax.html;

        public enum Syntax {
            html,
            xml
        }

        public Entities.EscapeMode escapeMode() {
            return this.escapeMode;
        }

        public OutputSettings escapeMode(Entities.EscapeMode escapeMode2) {
            this.escapeMode = escapeMode2;
            return this;
        }

        public Charset charset() {
            return this.charset;
        }

        public OutputSettings charset(Charset charset2) {
            this.charset = charset2;
            this.charsetEncoder = charset2.newEncoder();
            return this;
        }

        public OutputSettings charset(String charset2) {
            charset(Charset.forName(charset2));
            return this;
        }

        /* access modifiers changed from: package-private */
        public CharsetEncoder encoder() {
            return this.charsetEncoder;
        }

        public Syntax syntax() {
            return this.syntax;
        }

        public OutputSettings syntax(Syntax syntax2) {
            this.syntax = syntax2;
            return this;
        }

        public boolean prettyPrint() {
            return this.prettyPrint;
        }

        public OutputSettings prettyPrint(boolean pretty) {
            this.prettyPrint = pretty;
            return this;
        }

        public boolean outline() {
            return this.outline;
        }

        public OutputSettings outline(boolean outlineMode) {
            this.outline = outlineMode;
            return this;
        }

        public int indentAmount() {
            return this.indentAmount;
        }

        public OutputSettings indentAmount(int indentAmount2) {
            Validate.isTrue(indentAmount2 >= 0);
            this.indentAmount = indentAmount2;
            return this;
        }

        public Object clone() {
            OutputSettings clone = (OutputSettings) partialClone();
            clone.charset(this.charset.name());
            clone.escapeMode = Entities.EscapeMode.valueOf(this.escapeMode.name());
            return clone;
        }

        private Object partialClone() {
            try {
                return super.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public OutputSettings outputSettings() {
        return this.outputSettings;
    }

    public Document outputSettings(OutputSettings outputSettings2) {
        Validate.notNull(outputSettings2);
        this.outputSettings = outputSettings2;
        return this;
    }

    public QuirksMode quirksMode() {
        return this.quirksMode;
    }

    public Document quirksMode(QuirksMode quirksMode2) {
        this.quirksMode = quirksMode2;
        return this;
    }
}
