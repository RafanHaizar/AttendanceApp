package com.itextpdf.styledxmlparser.jsoup.nodes;

import com.itextpdf.styledxmlparser.jsoup.SerializationException;
import com.itextpdf.styledxmlparser.jsoup.helper.StringUtil;
import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.itextpdf.styledxmlparser.jsoup.parser.Parser;
import com.itextpdf.styledxmlparser.jsoup.select.NodeTraversor;
import com.itextpdf.styledxmlparser.jsoup.select.NodeVisitor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class Node implements Cloneable {
    private static final List<Node> EMPTY_NODES = Collections.emptyList();
    Attributes attributes;
    String baseUri;
    List<Node> childNodes;
    Node parentNode;
    int siblingIndex;

    public abstract String nodeName();

    /* access modifiers changed from: package-private */
    public abstract void outerHtmlHead(Appendable appendable, int i, Document.OutputSettings outputSettings) throws IOException;

    /* access modifiers changed from: package-private */
    public abstract void outerHtmlTail(Appendable appendable, int i, Document.OutputSettings outputSettings) throws IOException;

    protected Node(String baseUri2, Attributes attributes2) {
        Validate.notNull(baseUri2);
        Validate.notNull(attributes2);
        this.childNodes = EMPTY_NODES;
        this.baseUri = baseUri2.trim();
        this.attributes = attributes2;
    }

    protected Node(String baseUri2) {
        this(baseUri2, new Attributes());
    }

    protected Node() {
        this.childNodes = EMPTY_NODES;
        this.attributes = null;
    }

    public String attr(String attributeKey) {
        Validate.notNull(attributeKey);
        if (this.attributes.hasKey(attributeKey)) {
            return this.attributes.get(attributeKey);
        }
        if (attributeKey.toLowerCase().startsWith("abs:")) {
            return absUrl(attributeKey.substring("abs:".length()));
        }
        return "";
    }

    public Attributes attributes() {
        return this.attributes;
    }

    public Node attr(String attributeKey, String attributeValue) {
        this.attributes.put(attributeKey, attributeValue);
        return this;
    }

    public boolean hasAttr(String attributeKey) {
        Validate.notNull(attributeKey);
        if (attributeKey.startsWith("abs:")) {
            String key = attributeKey.substring("abs:".length());
            if (this.attributes.hasKey(key) && !absUrl(key).equals("")) {
                return true;
            }
        }
        return this.attributes.hasKey(attributeKey);
    }

    public Node removeAttr(String attributeKey) {
        Validate.notNull(attributeKey);
        this.attributes.remove(attributeKey);
        return this;
    }

    public String baseUri() {
        return this.baseUri;
    }

    public void setBaseUri(final String baseUri2) {
        Validate.notNull(baseUri2);
        traverse(new NodeVisitor() {
            public void head(Node node, int depth) {
                node.baseUri = baseUri2;
            }

            public void tail(Node node, int depth) {
            }
        });
    }

    public String absUrl(String attributeKey) {
        Validate.notEmpty(attributeKey);
        if (!hasAttr(attributeKey)) {
            return "";
        }
        return StringUtil.resolve(this.baseUri, attr(attributeKey));
    }

    public Node childNode(int index) {
        return this.childNodes.get(index);
    }

    public List<Node> childNodes() {
        return Collections.unmodifiableList(this.childNodes);
    }

    public List<Node> childNodesCopy() {
        List<Node> children = new ArrayList<>(this.childNodes.size());
        for (Node node : this.childNodes) {
            children.add((Node) node.clone());
        }
        return children;
    }

    public final int childNodeSize() {
        return this.childNodes.size();
    }

    /* access modifiers changed from: protected */
    public Node[] childNodesAsArray() {
        return (Node[]) this.childNodes.toArray(new Node[childNodeSize()]);
    }

    public Node parent() {
        return this.parentNode;
    }

    public final Node parentNode() {
        return this.parentNode;
    }

    public Document ownerDocument() {
        if (this instanceof Document) {
            return (Document) this;
        }
        Node node = this.parentNode;
        if (node == null) {
            return null;
        }
        return node.ownerDocument();
    }

    public void remove() {
        Validate.notNull(this.parentNode);
        this.parentNode.removeChild(this);
    }

    public Node before(String html) {
        addSiblingHtml(this.siblingIndex, html);
        return this;
    }

    public Node before(Node node) {
        Validate.notNull(node);
        Validate.notNull(this.parentNode);
        this.parentNode.addChildren(this.siblingIndex, node);
        return this;
    }

    public Node after(String html) {
        addSiblingHtml(this.siblingIndex + 1, html);
        return this;
    }

    public Node after(Node node) {
        Validate.notNull(node);
        Validate.notNull(this.parentNode);
        this.parentNode.addChildren(this.siblingIndex + 1, node);
        return this;
    }

    private void addSiblingHtml(int index, String html) {
        Validate.notNull(html);
        Validate.notNull(this.parentNode);
        List<Node> nodes = Parser.parseFragment(html, parent() instanceof Element ? (Element) parent() : null, baseUri());
        this.parentNode.addChildren(index, (Node[]) nodes.toArray(new Node[nodes.size()]));
    }

    public Node wrap(String html) {
        Validate.notEmpty(html);
        List<Node> wrapChildren = Parser.parseFragment(html, parent() instanceof Element ? (Element) parent() : null, baseUri());
        Node wrapNode = wrapChildren.get(0);
        if (wrapNode == null || !(wrapNode instanceof Element)) {
            return null;
        }
        Element wrap = (Element) wrapNode;
        Element deepest = getDeepChild(wrap);
        this.parentNode.replaceChild(this, wrap);
        deepest.addChildren(this);
        if (wrapChildren.size() > 0) {
            for (int i = 0; i < wrapChildren.size(); i++) {
                Node remainder = wrapChildren.get(i);
                remainder.parentNode.removeChild(remainder);
                wrap.appendChild(remainder);
            }
        }
        return this;
    }

    public Node unwrap() {
        Validate.notNull(this.parentNode);
        Node firstChild = this.childNodes.size() > 0 ? this.childNodes.get(0) : null;
        this.parentNode.addChildren(this.siblingIndex, childNodesAsArray());
        remove();
        return firstChild;
    }

    private Element getDeepChild(Element el) {
        List<Element> children = el.children();
        if (children.size() > 0) {
            return getDeepChild(children.get(0));
        }
        return el;
    }

    public void replaceWith(Node in) {
        Validate.notNull(in);
        Validate.notNull(this.parentNode);
        this.parentNode.replaceChild(this, in);
    }

    /* access modifiers changed from: protected */
    public void setParentNode(Node parentNode2) {
        Node node = this.parentNode;
        if (node != null) {
            node.removeChild(this);
        }
        this.parentNode = parentNode2;
    }

    /* access modifiers changed from: protected */
    public void replaceChild(Node out, Node in) {
        Validate.isTrue(out.parentNode == this);
        Validate.notNull(in);
        Node node = in.parentNode;
        if (node != null) {
            node.removeChild(in);
        }
        int index = out.siblingIndex;
        this.childNodes.set(index, in);
        in.parentNode = this;
        in.setSiblingIndex(index);
        out.parentNode = null;
    }

    /* access modifiers changed from: protected */
    public void removeChild(Node out) {
        Validate.isTrue(out.parentNode == this);
        int index = out.siblingIndex;
        this.childNodes.remove(index);
        reindexChildren(index);
        out.parentNode = null;
    }

    /* access modifiers changed from: protected */
    public void addChildren(Node... children) {
        for (Node child : children) {
            reparentChild(child);
            ensureChildNodes();
            this.childNodes.add(child);
            child.setSiblingIndex(this.childNodes.size() - 1);
        }
    }

    /* access modifiers changed from: protected */
    public void addChildren(int index, Node... children) {
        Validate.noNullElements(children);
        ensureChildNodes();
        for (int i = children.length - 1; i >= 0; i--) {
            Node in = children[i];
            reparentChild(in);
            this.childNodes.add(index, in);
            reindexChildren(index);
        }
    }

    /* access modifiers changed from: protected */
    public void ensureChildNodes() {
        if (this.childNodes == EMPTY_NODES) {
            this.childNodes = new ArrayList(4);
        }
    }

    /* access modifiers changed from: protected */
    public void reparentChild(Node child) {
        Node node = child.parentNode;
        if (node != null) {
            node.removeChild(child);
        }
        child.setParentNode(this);
    }

    private void reindexChildren(int start) {
        for (int i = start; i < this.childNodes.size(); i++) {
            this.childNodes.get(i).setSiblingIndex(i);
        }
    }

    public List<Node> siblingNodes() {
        Node node = this.parentNode;
        if (node == null) {
            return Collections.emptyList();
        }
        List<Node> nodes = node.childNodes;
        List<Node> siblings = new ArrayList<>(nodes.size() - 1);
        for (Node node2 : nodes) {
            if (node2 != this) {
                siblings.add(node2);
            }
        }
        return siblings;
    }

    public Node nextSibling() {
        Node node = this.parentNode;
        if (node == null) {
            return null;
        }
        List<Node> siblings = node.childNodes;
        int index = this.siblingIndex + 1;
        if (siblings.size() > index) {
            return siblings.get(index);
        }
        return null;
    }

    public Node previousSibling() {
        int i;
        Node node = this.parentNode;
        if (node != null && (i = this.siblingIndex) > 0) {
            return node.childNodes.get(i - 1);
        }
        return null;
    }

    public int siblingIndex() {
        return this.siblingIndex;
    }

    /* access modifiers changed from: protected */
    public void setSiblingIndex(int siblingIndex2) {
        this.siblingIndex = siblingIndex2;
    }

    public Node traverse(NodeVisitor nodeVisitor) {
        Validate.notNull(nodeVisitor);
        new NodeTraversor(nodeVisitor).traverse(this);
        return this;
    }

    public String outerHtml() {
        StringBuilder accum = new StringBuilder(128);
        outerHtml(accum);
        return accum.toString();
    }

    /* access modifiers changed from: protected */
    public void outerHtml(Appendable accum) {
        new NodeTraversor(new OuterHtmlVisitor(accum, getOutputSettings())).traverse(this);
    }

    /* access modifiers changed from: package-private */
    public Document.OutputSettings getOutputSettings() {
        return (ownerDocument() != null ? ownerDocument() : new Document("")).outputSettings();
    }

    public Appendable html(Appendable appendable) {
        outerHtml(appendable);
        return appendable;
    }

    public String toString() {
        return outerHtml();
    }

    /* access modifiers changed from: protected */
    public void indent(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
        accum.append("\n").append(StringUtil.padding(out.indentAmount() * depth));
    }

    public boolean equals(Object o) {
        return this == o;
    }

    public boolean hasSameValue(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return outerHtml().equals(((Node) o).outerHtml());
    }

    public Object clone() {
        Node thisClone = doClone((Node) null);
        LinkedList<Node> nodesToProcess = new LinkedList<>();
        nodesToProcess.add(thisClone);
        while (!nodesToProcess.isEmpty()) {
            Node currParent = nodesToProcess.remove();
            for (int i = 0; i < currParent.childNodes.size(); i++) {
                Node childClone = currParent.childNodes.get(i).doClone(currParent);
                currParent.childNodes.set(i, childClone);
                nodesToProcess.add(childClone);
            }
        }
        return thisClone;
    }

    /* access modifiers changed from: protected */
    public Node doClone(Node parent) {
        Node clone = (Node) partialClone();
        clone.parentNode = parent;
        clone.siblingIndex = parent == null ? 0 : this.siblingIndex;
        Attributes attributes2 = this.attributes;
        clone.attributes = attributes2 != null ? (Attributes) attributes2.clone() : null;
        clone.baseUri = this.baseUri;
        clone.childNodes = new ArrayList(this.childNodes.size());
        for (Node child : this.childNodes) {
            clone.childNodes.add(child);
        }
        return clone;
    }

    private Object partialClone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    private static class OuterHtmlVisitor implements NodeVisitor {
        private Appendable accum;
        private Document.OutputSettings out;

        OuterHtmlVisitor(Appendable accum2, Document.OutputSettings out2) {
            this.accum = accum2;
            this.out = out2;
        }

        public void head(Node node, int depth) {
            try {
                node.outerHtmlHead(this.accum, depth, this.out);
            } catch (IOException exception) {
                throw new SerializationException((Throwable) exception);
            }
        }

        public void tail(Node node, int depth) {
            if (!node.nodeName().equals("#text")) {
                try {
                    node.outerHtmlTail(this.accum, depth, this.out);
                } catch (IOException exception) {
                    throw new SerializationException((Throwable) exception);
                }
            }
        }
    }
}
