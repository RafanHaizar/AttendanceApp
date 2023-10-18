package com.itextpdf.styledxmlparser.jsoup.safety;

import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
import com.itextpdf.styledxmlparser.jsoup.nodes.Attribute;
import com.itextpdf.styledxmlparser.jsoup.nodes.Attributes;
import com.itextpdf.styledxmlparser.jsoup.nodes.DataNode;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.jsoup.nodes.Node;
import com.itextpdf.styledxmlparser.jsoup.nodes.TextNode;
import com.itextpdf.styledxmlparser.jsoup.parser.Tag;
import com.itextpdf.styledxmlparser.jsoup.select.NodeTraversor;
import com.itextpdf.styledxmlparser.jsoup.select.NodeVisitor;
import java.util.Iterator;

public class Cleaner {
    /* access modifiers changed from: private */
    public Whitelist whitelist;

    public Cleaner(Whitelist whitelist2) {
        Validate.notNull(whitelist2);
        this.whitelist = whitelist2;
    }

    public Document clean(Document dirtyDocument) {
        Validate.notNull(dirtyDocument);
        Document clean = Document.createShell(dirtyDocument.baseUri());
        if (dirtyDocument.body() != null) {
            copySafeNodes(dirtyDocument.body(), clean.body());
        }
        return clean;
    }

    public boolean isValid(Document dirtyDocument) {
        Validate.notNull(dirtyDocument);
        return copySafeNodes(dirtyDocument.body(), Document.createShell(dirtyDocument.baseUri()).body()) == 0;
    }

    private final class CleaningVisitor implements NodeVisitor {
        Element destination;
        int numDiscarded = 0;
        final Element root;

        CleaningVisitor(Element root2, Element destination2) {
            this.root = root2;
            this.destination = destination2;
        }

        public void head(Node source, int depth) {
            if (source instanceof Element) {
                Element sourceEl = (Element) source;
                if (Cleaner.this.whitelist.isSafeTag(sourceEl.tagName())) {
                    ElementMeta meta = Cleaner.this.createSafeElement(sourceEl);
                    Element destChild = meta.f1627el;
                    this.destination.appendChild(destChild);
                    this.numDiscarded += meta.numAttribsDiscarded;
                    this.destination = destChild;
                } else if (source != this.root) {
                    this.numDiscarded++;
                }
            } else if (source instanceof TextNode) {
                this.destination.appendChild(new TextNode(((TextNode) source).getWholeText(), source.baseUri()));
            } else if (!(source instanceof DataNode) || !Cleaner.this.whitelist.isSafeTag(source.parent().nodeName())) {
                this.numDiscarded++;
            } else {
                this.destination.appendChild(new DataNode(((DataNode) source).getWholeData(), source.baseUri()));
            }
        }

        public void tail(Node source, int depth) {
            if ((source instanceof Element) && Cleaner.this.whitelist.isSafeTag(source.nodeName())) {
                this.destination = (Element) this.destination.parent();
            }
        }
    }

    private int copySafeNodes(Element source, Element dest) {
        CleaningVisitor cleaningVisitor = new CleaningVisitor(source, dest);
        new NodeTraversor(cleaningVisitor).traverse(source);
        return cleaningVisitor.numDiscarded;
    }

    /* access modifiers changed from: private */
    public ElementMeta createSafeElement(Element sourceEl) {
        String sourceTag = sourceEl.tagName();
        Attributes destAttrs = new Attributes();
        Element dest = new Element(Tag.valueOf(sourceTag), sourceEl.baseUri(), destAttrs);
        int numDiscarded = 0;
        Iterator<Attribute> it = sourceEl.attributes().iterator();
        while (it.hasNext()) {
            Attribute sourceAttr = it.next();
            if (this.whitelist.isSafeAttribute(sourceTag, sourceEl, sourceAttr)) {
                destAttrs.put(sourceAttr);
            } else {
                numDiscarded++;
            }
        }
        destAttrs.addAll(this.whitelist.getEnforcedAttributes(sourceTag));
        return new ElementMeta(dest, numDiscarded);
    }

    private static class ElementMeta {

        /* renamed from: el */
        Element f1627el;
        int numAttribsDiscarded;

        ElementMeta(Element el, int numAttribsDiscarded2) {
            this.f1627el = el;
            this.numAttribsDiscarded = numAttribsDiscarded2;
        }
    }
}
