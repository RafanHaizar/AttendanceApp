package com.itextpdf.styledxmlparser.jsoup.nodes;

import com.itextpdf.forms.xfdf.XfdfConstants;
import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.CommonAttributeConstants;
import com.itextpdf.styledxmlparser.jsoup.helper.StringUtil;
import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.itextpdf.styledxmlparser.jsoup.parser.Parser;
import com.itextpdf.styledxmlparser.jsoup.parser.Tag;
import com.itextpdf.styledxmlparser.jsoup.select.Collector;
import com.itextpdf.styledxmlparser.jsoup.select.Elements;
import com.itextpdf.styledxmlparser.jsoup.select.Evaluator;
import com.itextpdf.styledxmlparser.jsoup.select.NodeTraversor;
import com.itextpdf.styledxmlparser.jsoup.select.NodeVisitor;
import com.itextpdf.styledxmlparser.jsoup.select.Selector;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import kotlin.text.Typography;

public class Element extends Node {
    private static final Pattern classSplit = Pattern.compile("\\s+");
    /* access modifiers changed from: private */
    public Tag tag;

    public Element(Tag tag2, String baseUri, Attributes attributes) {
        super(baseUri, attributes);
        Validate.notNull(tag2);
        this.tag = tag2;
    }

    public Element(Tag tag2, String baseUri) {
        this(tag2, baseUri, new Attributes());
    }

    public String nodeName() {
        return this.tag.getName();
    }

    public String tagName() {
        return this.tag.getName();
    }

    public Element tagName(String tagName) {
        Validate.notEmpty(tagName, "Tag name must not be empty.");
        this.tag = Tag.valueOf(tagName);
        return this;
    }

    public Tag tag() {
        return this.tag;
    }

    public boolean isBlock() {
        return this.tag.isBlock();
    }

    /* renamed from: id */
    public String mo31113id() {
        return this.attributes.get("id");
    }

    public Node attr(String attributeKey, String attributeValue) {
        super.attr(attributeKey, attributeValue);
        return this;
    }

    public Element attr(String attributeKey, boolean attributeValue) {
        this.attributes.put(attributeKey, attributeValue);
        return this;
    }

    public Map<String, String> dataset() {
        return this.attributes.dataset();
    }

    public final Node parent() {
        return this.parentNode;
    }

    public Elements parents() {
        Elements parents = new Elements();
        accumulateParents(this, parents);
        return parents;
    }

    private static void accumulateParents(Element el, Elements parents) {
        Element parent = (Element) el.parent();
        if (parent != null && !parent.tagName().equals("#root")) {
            parents.add(parent);
            accumulateParents(parent, parents);
        }
    }

    public Element child(int index) {
        return (Element) children().get(index);
    }

    public Elements children() {
        List<Element> elements = new ArrayList<>(this.childNodes.size());
        for (Node node : this.childNodes) {
            if (node instanceof Element) {
                elements.add((Element) node);
            }
        }
        return new Elements(elements);
    }

    public List<TextNode> textNodes() {
        List<TextNode> textNodes = new ArrayList<>();
        for (Node node : this.childNodes) {
            if (node instanceof TextNode) {
                textNodes.add((TextNode) node);
            }
        }
        return Collections.unmodifiableList(textNodes);
    }

    public List<DataNode> dataNodes() {
        List<DataNode> dataNodes = new ArrayList<>();
        for (Node node : this.childNodes) {
            if (node instanceof DataNode) {
                dataNodes.add((DataNode) node);
            }
        }
        return Collections.unmodifiableList(dataNodes);
    }

    public Elements select(String cssQuery) {
        return Selector.select(cssQuery, this);
    }

    public Element appendChild(Node child) {
        Validate.notNull(child);
        reparentChild(child);
        ensureChildNodes();
        this.childNodes.add(child);
        child.setSiblingIndex(this.childNodes.size() - 1);
        return this;
    }

    public Element prependChild(Node child) {
        return insertChild(0, child);
    }

    public Element insertChild(int index, Node child) {
        if (index == -1) {
            return appendChild(child);
        }
        Validate.notNull(child);
        addChildren(index, child);
        return this;
    }

    public Element insertChildren(int index, Collection<? extends Node> children) {
        Validate.notNull(children, "Children collection to be inserted must not be null.");
        int currentSize = childNodeSize();
        if (index < 0) {
            index += currentSize + 1;
        }
        Validate.isTrue(index >= 0 && index <= currentSize, "Insert position out of bounds.");
        ArrayList<Node> nodes = new ArrayList<>(children);
        addChildren(index, (Node[]) nodes.toArray(new Node[nodes.size()]));
        return this;
    }

    public Element appendElement(String tagName) {
        Element child = new Element(Tag.valueOf(tagName), baseUri());
        appendChild(child);
        return child;
    }

    public Element prependElement(String tagName) {
        Element child = new Element(Tag.valueOf(tagName), baseUri());
        prependChild(child);
        return child;
    }

    public Element appendText(String text) {
        Validate.notNull(text);
        appendChild(new TextNode(text, baseUri()));
        return this;
    }

    public Element prependText(String text) {
        Validate.notNull(text);
        prependChild(new TextNode(text, baseUri()));
        return this;
    }

    public Element append(String html) {
        Validate.notNull(html);
        List<Node> nodes = Parser.parseFragment(html, this, baseUri());
        addChildren((Node[]) nodes.toArray(new Node[nodes.size()]));
        return this;
    }

    public Element prepend(String html) {
        Validate.notNull(html);
        List<Node> nodes = Parser.parseFragment(html, this, baseUri());
        addChildren(0, (Node[]) nodes.toArray(new Node[nodes.size()]));
        return this;
    }

    public Node before(String html) {
        return super.before(html);
    }

    public Node before(Node node) {
        return super.before(node);
    }

    public Node after(String html) {
        return (Element) super.after(html);
    }

    public Node after(Node node) {
        return (Element) super.after(node);
    }

    public Element empty() {
        this.childNodes.clear();
        return this;
    }

    public Node wrap(String html) {
        return super.wrap(html);
    }

    public String cssSelector() {
        if (mo31113id().length() > 0) {
            return "#" + mo31113id();
        }
        StringBuilder selector = new StringBuilder(tagName().replace(':', '|'));
        String classes = StringUtil.join((Collection) classNames(), ".");
        if (classes.length() > 0) {
            selector.append('.').append(classes);
        }
        if (parent() == null || (parent() instanceof Document)) {
            return selector.toString();
        }
        selector.insert(0, " > ");
        if (((Element) parent()).select(selector.toString()).size() > 1) {
            selector.append(MessageFormatUtil.format(":nth-child({0})", Integer.valueOf(elementSiblingIndex() + 1)));
        }
        return ((Element) parent()).cssSelector() + selector.toString();
    }

    public Elements siblingElements() {
        if (this.parentNode == null) {
            return new Elements(0);
        }
        List<Element> elements = ((Element) parent()).children();
        Elements siblings = new Elements(elements.size() - 1);
        for (Element el : elements) {
            if (el != this) {
                siblings.add(el);
            }
        }
        return siblings;
    }

    public Element nextElementSibling() {
        if (this.parentNode == null) {
            return null;
        }
        List<Element> siblings = ((Element) parent()).children();
        int index = indexInList(this, siblings);
        Validate.isTrue(index >= 0);
        if (siblings.size() > index + 1) {
            return siblings.get(index + 1);
        }
        return null;
    }

    public Element previousElementSibling() {
        if (this.parentNode == null) {
            return null;
        }
        List<Element> siblings = ((Element) parent()).children();
        int index = indexInList(this, siblings);
        Validate.isTrue(index >= 0);
        if (index > 0) {
            return siblings.get(index - 1);
        }
        return null;
    }

    public Element firstElementSibling() {
        List<Element> siblings = ((Element) parent()).children();
        if (siblings.size() > 1) {
            return siblings.get(0);
        }
        return null;
    }

    public int elementSiblingIndex() {
        if (parent() == null) {
            return 0;
        }
        return indexInList(this, ((Element) parent()).children());
    }

    public Element lastElementSibling() {
        List<Element> siblings = ((Element) parent()).children();
        if (siblings.size() > 1) {
            return siblings.get(siblings.size() - 1);
        }
        return null;
    }

    private static <E extends Element> int indexInList(Element search, List<E> elements) {
        Validate.notNull(search);
        Validate.notNull(elements);
        for (int i = 0; i < elements.size(); i++) {
            if (((Element) elements.get(i)) == search) {
                return i;
            }
        }
        return -1;
    }

    public Elements getElementsByTag(String tagName) {
        Validate.notEmpty(tagName);
        return Collector.collect(new Evaluator.Tag(tagName.toLowerCase().trim()), this);
    }

    public Element getElementById(String id) {
        Validate.notEmpty(id);
        Elements elements = Collector.collect(new Evaluator.C1589Id(id), this);
        if (elements.size() > 0) {
            return (Element) elements.get(0);
        }
        return null;
    }

    public Elements getElementsByClass(String className) {
        Validate.notEmpty(className);
        return Collector.collect(new Evaluator.Class(className), this);
    }

    public Elements getElementsByAttribute(String key) {
        Validate.notEmpty(key);
        return Collector.collect(new Evaluator.Attribute(key.trim().toLowerCase()), this);
    }

    public Elements getElementsByAttributeStarting(String keyPrefix) {
        Validate.notEmpty(keyPrefix);
        return Collector.collect(new Evaluator.AttributeStarting(keyPrefix.trim().toLowerCase()), this);
    }

    public Elements getElementsByAttributeValue(String key, String value) {
        return Collector.collect(new Evaluator.AttributeWithValue(key, value), this);
    }

    public Elements getElementsByAttributeValueNot(String key, String value) {
        return Collector.collect(new Evaluator.AttributeWithValueNot(key, value), this);
    }

    public Elements getElementsByAttributeValueStarting(String key, String valuePrefix) {
        return Collector.collect(new Evaluator.AttributeWithValueStarting(key, valuePrefix), this);
    }

    public Elements getElementsByAttributeValueEnding(String key, String valueSuffix) {
        return Collector.collect(new Evaluator.AttributeWithValueEnding(key, valueSuffix), this);
    }

    public Elements getElementsByAttributeValueContaining(String key, String match) {
        return Collector.collect(new Evaluator.AttributeWithValueContaining(key, match), this);
    }

    public Elements getElementsByAttributeValueMatching(String key, Pattern pattern) {
        return Collector.collect(new Evaluator.AttributeWithValueMatching(key, pattern), this);
    }

    public Elements getElementsByAttributeValueMatching(String key, String regex) {
        try {
            return getElementsByAttributeValueMatching(key, Pattern.compile(regex));
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Pattern syntax error: " + regex, e);
        }
    }

    public Elements getElementsByIndexLessThan(int index) {
        return Collector.collect(new Evaluator.IndexLessThan(index), this);
    }

    public Elements getElementsByIndexGreaterThan(int index) {
        return Collector.collect(new Evaluator.IndexGreaterThan(index), this);
    }

    public Elements getElementsByIndexEquals(int index) {
        return Collector.collect(new Evaluator.IndexEquals(index), this);
    }

    public Elements getElementsContainingText(String searchText) {
        return Collector.collect(new Evaluator.ContainsText(searchText), this);
    }

    public Elements getElementsContainingOwnText(String searchText) {
        return Collector.collect(new Evaluator.ContainsOwnText(searchText), this);
    }

    public Elements getElementsMatchingText(Pattern pattern) {
        return Collector.collect(new Evaluator.Matches(pattern), this);
    }

    public Elements getElementsMatchingText(String regex) {
        try {
            return getElementsMatchingText(Pattern.compile(regex));
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Pattern syntax error: " + regex, e);
        }
    }

    public Elements getElementsMatchingOwnText(Pattern pattern) {
        return Collector.collect(new Evaluator.MatchesOwn(pattern), this);
    }

    public Elements getElementsMatchingOwnText(String regex) {
        try {
            return getElementsMatchingOwnText(Pattern.compile(regex));
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Pattern syntax error: " + regex, e);
        }
    }

    public Elements getAllElements() {
        return Collector.collect(new Evaluator.AllElements(), this);
    }

    public String text() {
        final StringBuilder accum = new StringBuilder();
        new NodeTraversor(new NodeVisitor() {
            public void head(Node node, int depth) {
                if (node instanceof TextNode) {
                    Element.appendNormalisedText(accum, (TextNode) node);
                } else if (node instanceof Element) {
                    Element element = (Element) node;
                    if (accum.length() <= 0) {
                        return;
                    }
                    if ((element.isBlock() || element.tag.getName().equals("br")) && !TextNode.lastCharIsWhitespace(accum)) {
                        accum.append(" ");
                    }
                }
            }

            public void tail(Node node, int depth) {
            }
        }).traverse(this);
        return accum.toString().trim();
    }

    public String ownText() {
        StringBuilder sb = new StringBuilder();
        ownText(sb);
        return sb.toString().trim();
    }

    private void ownText(StringBuilder accum) {
        for (Node child : this.childNodes) {
            if (child instanceof TextNode) {
                appendNormalisedText(accum, (TextNode) child);
            } else if (child instanceof Element) {
                appendWhitespaceIfBr((Element) child, accum);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void appendNormalisedText(StringBuilder accum, TextNode textNode) {
        String text = textNode.getWholeText();
        if (preserveWhitespace(textNode.parentNode)) {
            accum.append(text);
        } else {
            StringUtil.appendNormalisedWhitespace(accum, text, TextNode.lastCharIsWhitespace(accum));
        }
    }

    private static void appendWhitespaceIfBr(Element element, StringBuilder accum) {
        if (element.tag.getName().equals("br") && !TextNode.lastCharIsWhitespace(accum)) {
            accum.append(" ");
        }
    }

    static boolean preserveWhitespace(Node node) {
        if (node == null || !(node instanceof Element)) {
            return false;
        }
        Element element = (Element) node;
        if (element.tag.preserveWhitespace() || (element.parent() != null && ((Element) element.parent()).tag.preserveWhitespace())) {
            return true;
        }
        return false;
    }

    public Element text(String text) {
        Validate.notNull(text);
        empty();
        appendChild(new TextNode(text, this.baseUri));
        return this;
    }

    public boolean hasText() {
        for (Node child : this.childNodes) {
            if (child instanceof TextNode) {
                if (!((TextNode) child).isBlank()) {
                    return true;
                }
            } else if ((child instanceof Element) && ((Element) child).hasText()) {
                return true;
            }
        }
        return false;
    }

    public String data() {
        StringBuilder sb = new StringBuilder();
        for (Node childNode : this.childNodes) {
            if (childNode instanceof DataNode) {
                sb.append(((DataNode) childNode).getWholeData());
            } else if (childNode instanceof Element) {
                sb.append(((Element) childNode).data());
            }
        }
        return sb.toString();
    }

    public String className() {
        return attr(CommonAttributeConstants.CLASS).trim();
    }

    public Set<String> classNames() {
        Set<String> classNames = new LinkedHashSet<>(Arrays.asList(classSplit.split(className())));
        classNames.remove("");
        return classNames;
    }

    public Element classNames(Set<String> classNames) {
        Validate.notNull(classNames);
        this.attributes.put(CommonAttributeConstants.CLASS, StringUtil.join((Collection) classNames, " "));
        return this;
    }

    public boolean hasClass(String className) {
        String classAttr = this.attributes.get(CommonAttributeConstants.CLASS);
        if (classAttr.equals("") || classAttr.length() < className.length()) {
            return false;
        }
        for (String name : classSplit.split(classAttr)) {
            if (className.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public Element addClass(String className) {
        Validate.notNull(className);
        Set<String> classes = classNames();
        classes.add(className);
        classNames(classes);
        return this;
    }

    public Element removeClass(String className) {
        Validate.notNull(className);
        Set<String> classes = classNames();
        classes.remove(className);
        classNames(classes);
        return this;
    }

    public Element toggleClass(String className) {
        Validate.notNull(className);
        Set<String> classes = classNames();
        if (classes.contains(className)) {
            classes.remove(className);
        } else {
            classes.add(className);
        }
        classNames(classes);
        return this;
    }

    public String val() {
        if (tagName().equals("textarea")) {
            return text();
        }
        return attr(XfdfConstants.VALUE);
    }

    public Element val(String value) {
        if (tagName().equals("textarea")) {
            text(value);
        } else {
            attr(XfdfConstants.VALUE, value);
        }
        return this;
    }

    /* access modifiers changed from: package-private */
    public void outerHtmlHead(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
        if (out.prettyPrint() && (this.tag.formatAsBlock() || ((parent() != null && ((Element) parent()).tag().formatAsBlock()) || out.outline()))) {
            if (!(accum instanceof StringBuilder)) {
                indent(accum, depth, out);
            } else if (((StringBuilder) accum).length() > 0) {
                indent(accum, depth, out);
            }
        }
        accum.append("<").append(tagName());
        this.attributes.html(accum, out);
        if (!this.childNodes.isEmpty() || !this.tag.isSelfClosing()) {
            accum.append(">");
        } else if (out.syntax() != Document.OutputSettings.Syntax.html || !this.tag.isEmpty()) {
            accum.append(" />");
        } else {
            accum.append(Typography.greater);
        }
    }

    /* access modifiers changed from: package-private */
    public void outerHtmlTail(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
        if (!this.childNodes.isEmpty() || !this.tag.isSelfClosing()) {
            if (out.prettyPrint() && !this.childNodes.isEmpty() && (this.tag.formatAsBlock() || (out.outline() && (this.childNodes.size() > 1 || (this.childNodes.size() == 1 && !(this.childNodes.get(0) instanceof TextNode)))))) {
                indent(accum, depth, out);
            }
            accum.append("</").append(tagName()).append(">");
        }
    }

    public String html() {
        StringBuilder accum = new StringBuilder();
        html((Appendable) accum);
        return getOutputSettings().prettyPrint() ? accum.toString().trim() : accum.toString();
    }

    public Appendable html(Appendable appendable) {
        for (Node node : this.childNodes) {
            node.outerHtml(appendable);
        }
        return appendable;
    }

    public Element html(String html) {
        empty();
        append(html);
        return this;
    }

    public String toString() {
        return outerHtml();
    }

    public Object clone() {
        return super.clone();
    }
}
