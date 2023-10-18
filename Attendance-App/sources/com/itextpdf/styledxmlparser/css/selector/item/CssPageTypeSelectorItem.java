package com.itextpdf.styledxmlparser.css.selector.item;

import com.itextpdf.styledxmlparser.css.page.PageContextNode;
import com.itextpdf.styledxmlparser.node.INode;

public class CssPageTypeSelectorItem implements ICssSelectorItem {
    private String pageTypeName;

    public CssPageTypeSelectorItem(String pageTypeName2) {
        this.pageTypeName = pageTypeName2;
    }

    public int getSpecificity() {
        return 1048576;
    }

    public boolean matches(INode node) {
        if ((node instanceof PageContextNode) && !"auto".equals(this.pageTypeName.toLowerCase()) && this.pageTypeName.equals(((PageContextNode) node).getPageTypeName())) {
            return true;
        }
        return false;
    }
}
