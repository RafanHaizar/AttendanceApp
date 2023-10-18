package com.itextpdf.styledxmlparser.node.impl.jsoup.node;

import com.itextpdf.styledxmlparser.jsoup.nodes.TextNode;
import com.itextpdf.styledxmlparser.node.ITextNode;

public class JsoupTextNode extends JsoupNode implements ITextNode {
    private TextNode textNode;

    public JsoupTextNode(TextNode textNode2) {
        super(textNode2);
        this.textNode = textNode2;
    }

    public String wholeText() {
        return this.textNode.getWholeText();
    }
}
