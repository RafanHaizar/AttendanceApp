package com.itextpdf.styledxmlparser.css.selector.item;

import com.itextpdf.styledxmlparser.node.ICustomElementNode;
import com.itextpdf.styledxmlparser.node.IDocumentNode;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.INode;

public class CssIdSelectorItem implements ICssSelectorItem {

    /* renamed from: id */
    private String f1622id;

    public CssIdSelectorItem(String id) {
        this.f1622id = id;
    }

    public int getSpecificity() {
        return 1048576;
    }

    public boolean matches(INode node) {
        if (!(node instanceof IElementNode) || (node instanceof ICustomElementNode) || (node instanceof IDocumentNode)) {
            return false;
        }
        return this.f1622id.equals(((IElementNode) node).getAttribute("id"));
    }

    public String toString() {
        return "#" + this.f1622id;
    }
}
