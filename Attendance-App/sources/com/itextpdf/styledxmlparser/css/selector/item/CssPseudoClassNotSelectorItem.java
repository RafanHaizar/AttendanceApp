package com.itextpdf.styledxmlparser.css.selector.item;

import com.itextpdf.styledxmlparser.css.parse.CssSelectorParser;
import com.itextpdf.styledxmlparser.css.selector.ICssSelector;
import com.itextpdf.styledxmlparser.node.ICustomElementNode;
import com.itextpdf.styledxmlparser.node.IDocumentNode;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.INode;
import java.util.List;

class CssPseudoClassNotSelectorItem extends CssPseudoClassSelectorItem {
    private ICssSelector argumentsSelector;

    CssPseudoClassNotSelectorItem(ICssSelector argumentsSelector2) {
        super("not", argumentsSelector2.toString());
        this.argumentsSelector = argumentsSelector2;
    }

    public List<ICssSelectorItem> getArgumentsSelector() {
        return CssSelectorParser.parseSelectorItems(this.arguments);
    }

    public boolean matches(INode node) {
        if (!(node instanceof IElementNode) || (node instanceof ICustomElementNode) || (node instanceof IDocumentNode)) {
            return false;
        }
        return !this.argumentsSelector.matches(node);
    }
}
