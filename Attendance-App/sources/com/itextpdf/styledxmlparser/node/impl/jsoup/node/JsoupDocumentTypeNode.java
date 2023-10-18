package com.itextpdf.styledxmlparser.node.impl.jsoup.node;

import com.itextpdf.styledxmlparser.jsoup.nodes.DocumentType;
import com.itextpdf.styledxmlparser.node.IDocumentTypeNode;

public class JsoupDocumentTypeNode extends JsoupNode implements IDocumentTypeNode {
    public JsoupDocumentTypeNode(DocumentType node) {
        super(node);
    }
}
