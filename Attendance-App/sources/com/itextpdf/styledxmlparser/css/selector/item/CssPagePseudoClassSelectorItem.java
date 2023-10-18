package com.itextpdf.styledxmlparser.css.selector.item;

import com.itextpdf.styledxmlparser.css.page.PageContextNode;
import com.itextpdf.styledxmlparser.node.INode;

public class CssPagePseudoClassSelectorItem implements ICssSelectorItem {
    private boolean isSpreadPseudoClass;
    private String pagePseudoClass;

    public CssPagePseudoClassSelectorItem(String pagePseudoClass2) {
        this.isSpreadPseudoClass = pagePseudoClass2.equals("left") || pagePseudoClass2.equals("right");
        this.pagePseudoClass = pagePseudoClass2;
    }

    public int getSpecificity() {
        return this.isSpreadPseudoClass ? 1 : 1024;
    }

    public boolean matches(INode node) {
        if (!(node instanceof PageContextNode)) {
            return false;
        }
        return ((PageContextNode) node).getPageClasses().contains(this.pagePseudoClass);
    }
}
