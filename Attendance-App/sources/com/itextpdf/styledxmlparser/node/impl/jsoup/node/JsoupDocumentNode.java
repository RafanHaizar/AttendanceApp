package com.itextpdf.styledxmlparser.node.impl.jsoup.node;

import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.itextpdf.styledxmlparser.node.IDocumentNode;

public class JsoupDocumentNode extends JsoupElementNode implements IDocumentNode {
    private Document document;

    public JsoupDocumentNode(Document document2) {
        super(document2);
        this.document = document2;
    }

    public Document getDocument() {
        return this.document;
    }
}
