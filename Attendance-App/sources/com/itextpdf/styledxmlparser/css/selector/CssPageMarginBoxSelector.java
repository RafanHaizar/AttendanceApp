package com.itextpdf.styledxmlparser.css.selector;

import com.itextpdf.styledxmlparser.css.page.PageMarginBoxContextNode;
import com.itextpdf.styledxmlparser.node.INode;

public class CssPageMarginBoxSelector implements ICssSelector {
    private String pageMarginBoxName;
    private ICssSelector pageSelector;

    public CssPageMarginBoxSelector(String pageMarginBoxName2, ICssSelector pageSelector2) {
        this.pageMarginBoxName = pageMarginBoxName2;
        this.pageSelector = pageSelector2;
    }

    public int calculateSpecificity() {
        return this.pageSelector.calculateSpecificity();
    }

    public boolean matches(INode node) {
        if (!(node instanceof PageMarginBoxContextNode) || !this.pageMarginBoxName.equals(((PageMarginBoxContextNode) node).getMarginBoxName())) {
            return false;
        }
        return this.pageSelector.matches(node.parentNode());
    }
}
