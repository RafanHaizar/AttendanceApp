package com.itextpdf.styledxmlparser.jsoup.nodes;

import com.itextpdf.styledxmlparser.jsoup.helper.StringUtil;
import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import java.io.IOException;

public class TextNode extends Node {
    private static final String TEXT_KEY = "text";
    String text;

    public TextNode(String text2, String baseUri) {
        this.baseUri = baseUri;
        this.text = text2;
    }

    public String nodeName() {
        return "#text";
    }

    public String text() {
        return normaliseWhitespace(getWholeText());
    }

    public TextNode text(String text2) {
        this.text = text2;
        if (this.attributes != null) {
            this.attributes.put("text", text2);
        }
        return this;
    }

    public String getWholeText() {
        return this.attributes == null ? this.text : this.attributes.get("text");
    }

    public boolean isBlank() {
        return StringUtil.isBlank(getWholeText());
    }

    public TextNode splitText(int offset) {
        Validate.isTrue(offset >= 0, "Split offset must be not be negative");
        Validate.isTrue(offset < this.text.length(), "Split offset must not be greater than current text length");
        String head = getWholeText().substring(0, offset);
        String tail = getWholeText().substring(offset);
        text(head);
        TextNode tailNode = new TextNode(tail, baseUri());
        if (parent() != null) {
            parent().addChildren(siblingIndex() + 1, tailNode);
        }
        return tailNode;
    }

    /* access modifiers changed from: package-private */
    public void outerHtmlHead(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
        if (out.prettyPrint() && ((siblingIndex() == 0 && (this.parentNode instanceof Element) && ((Element) this.parentNode).tag().formatAsBlock() && !isBlank()) || (out.outline() && siblingNodes().size() > 0 && !isBlank()))) {
            indent(accum, depth, out);
        }
        Entities.escape(accum, getWholeText(), out, false, out.prettyPrint() && (parent() instanceof Element) && !Element.preserveWhitespace(parent()), false);
    }

    /* access modifiers changed from: package-private */
    public void outerHtmlTail(Appendable accum, int depth, Document.OutputSettings out) {
    }

    public String toString() {
        return outerHtml();
    }

    public static TextNode createFromEncoded(String encodedText, String baseUri) {
        return new TextNode(Entities.unescape(encodedText), baseUri);
    }

    static String normaliseWhitespace(String text2) {
        return StringUtil.normaliseWhitespace(text2);
    }

    static String stripLeadingWhitespace(String text2) {
        return text2.replaceFirst("^\\s+", "");
    }

    static boolean lastCharIsWhitespace(StringBuilder sb) {
        return sb.length() != 0 && sb.charAt(sb.length() - 1) == ' ';
    }

    private void ensureAttributes() {
        if (this.attributes == null) {
            this.attributes = new Attributes();
            this.attributes.put("text", this.text);
        }
    }

    public String attr(String attributeKey) {
        ensureAttributes();
        return super.attr(attributeKey);
    }

    public Attributes attributes() {
        ensureAttributes();
        return super.attributes();
    }

    public Node attr(String attributeKey, String attributeValue) {
        ensureAttributes();
        return super.attr(attributeKey, attributeValue);
    }

    public boolean hasAttr(String attributeKey) {
        ensureAttributes();
        return super.hasAttr(attributeKey);
    }

    public Node removeAttr(String attributeKey) {
        ensureAttributes();
        return super.removeAttr(attributeKey);
    }

    public String absUrl(String attributeKey) {
        ensureAttributes();
        return super.absUrl(attributeKey);
    }
}
