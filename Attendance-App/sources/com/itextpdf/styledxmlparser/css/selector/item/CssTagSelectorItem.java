package com.itextpdf.styledxmlparser.css.selector.item;

import com.itextpdf.styledxmlparser.node.ICustomElementNode;
import com.itextpdf.styledxmlparser.node.IDocumentNode;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.INode;
import org.slf4j.Marker;

public class CssTagSelectorItem implements ICssSelectorItem {
    private boolean isUniversal;
    private String tagName;

    public CssTagSelectorItem(String tagName2) {
        this.tagName = tagName2.toLowerCase();
        this.isUniversal = Marker.ANY_MARKER.equals(tagName2);
    }

    public int getSpecificity() {
        return this.isUniversal ^ true ? 1 : 0;
    }

    public boolean matches(INode node) {
        if (!(node instanceof IElementNode) || (node instanceof ICustomElementNode) || (node instanceof IDocumentNode)) {
            return false;
        }
        IElementNode element = (IElementNode) node;
        if (this.isUniversal || this.tagName.equals(element.name())) {
            return true;
        }
        return false;
    }

    public String toString() {
        return this.tagName;
    }
}
