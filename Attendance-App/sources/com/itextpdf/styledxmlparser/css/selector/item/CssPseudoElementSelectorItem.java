package com.itextpdf.styledxmlparser.css.selector.item;

import com.itextpdf.styledxmlparser.css.pseudo.CssPseudoElementNode;
import com.itextpdf.styledxmlparser.node.INode;

public class CssPseudoElementSelectorItem implements ICssSelectorItem {
    private String pseudoElementName;

    public CssPseudoElementSelectorItem(String pseudoElementName2) {
        this.pseudoElementName = pseudoElementName2;
    }

    public int getSpecificity() {
        return 1;
    }

    public boolean matches(INode node) {
        return (node instanceof CssPseudoElementNode) && ((CssPseudoElementNode) node).getPseudoElementName().equals(this.pseudoElementName);
    }

    public String toString() {
        return "::" + this.pseudoElementName;
    }
}
