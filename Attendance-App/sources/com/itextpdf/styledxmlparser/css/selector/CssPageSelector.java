package com.itextpdf.styledxmlparser.css.selector;

import com.itextpdf.styledxmlparser.css.page.PageContextNode;
import com.itextpdf.styledxmlparser.css.parse.CssPageSelectorParser;
import com.itextpdf.styledxmlparser.css.selector.item.ICssSelectorItem;
import com.itextpdf.styledxmlparser.node.INode;

public class CssPageSelector extends AbstractCssSelector {
    public CssPageSelector(String pageSelectorStr) {
        super(CssPageSelectorParser.parseSelectorItems(pageSelectorStr));
    }

    public boolean matches(INode node) {
        if (!(node instanceof PageContextNode)) {
            return false;
        }
        for (ICssSelectorItem selectorItem : this.selectorItems) {
            if (!selectorItem.matches(node)) {
                return false;
            }
        }
        return true;
    }
}
