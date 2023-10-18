package com.itextpdf.styledxmlparser.css.selector;

import com.itextpdf.styledxmlparser.css.parse.CssSelectorParser;
import com.itextpdf.styledxmlparser.css.pseudo.CssPseudoElementNode;
import com.itextpdf.styledxmlparser.css.selector.item.CssPseudoElementSelectorItem;
import com.itextpdf.styledxmlparser.css.selector.item.CssSeparatorSelectorItem;
import com.itextpdf.styledxmlparser.css.selector.item.ICssSelectorItem;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.INode;
import java.util.List;

public class CssSelector extends AbstractCssSelector {
    public CssSelector(List<ICssSelectorItem> selectorItems) {
        super(selectorItems);
    }

    public CssSelector(String selector) {
        this(CssSelectorParser.parseSelectorItems(selector));
    }

    public boolean matches(INode element) {
        return matches(element, this.selectorItems.size() - 1);
    }

    private boolean matches(INode element, int lastSelectorItemInd) {
        if (!(element instanceof IElementNode)) {
            return false;
        }
        if (lastSelectorItemInd < 0) {
            return true;
        }
        boolean isPseudoElement = element instanceof CssPseudoElementNode;
        int i = lastSelectorItemInd;
        while (i >= 0) {
            if (isPseudoElement && (this.selectorItems.get(lastSelectorItemInd) instanceof CssPseudoElementSelectorItem) && i < lastSelectorItemInd) {
                element = element.parentNode();
                isPseudoElement = false;
            }
            ICssSelectorItem currentItem = (ICssSelectorItem) this.selectorItems.get(i);
            if (currentItem instanceof CssSeparatorSelectorItem) {
                switch (((CssSeparatorSelectorItem) currentItem).getSeparator()) {
                    case ' ':
                        for (INode parent = element.parentNode(); parent != null; parent = parent.parentNode()) {
                            if (matches(parent, i - 1)) {
                                return true;
                            }
                        }
                        return false;
                    case '+':
                        INode parent2 = element.parentNode();
                        if (parent2 != null) {
                            int indexOfElement = parent2.childNodes().indexOf(element);
                            INode previousElement = null;
                            int j = indexOfElement - 1;
                            while (true) {
                                if (j >= 0) {
                                    if (parent2.childNodes().get(j) instanceof IElementNode) {
                                        previousElement = parent2.childNodes().get(j);
                                    } else {
                                        j--;
                                    }
                                }
                            }
                            if (previousElement == null || indexOfElement <= 0 || !matches(previousElement, i - 1)) {
                                return false;
                            }
                            return true;
                        }
                        return false;
                    case '>':
                        return matches(element.parentNode(), i - 1);
                    case '~':
                        INode parent3 = element.parentNode();
                        if (parent3 != null) {
                            for (int j2 = parent3.childNodes().indexOf(element) - 1; j2 >= 0; j2--) {
                                if (matches(parent3.childNodes().get(j2), i - 1)) {
                                    return true;
                                }
                            }
                        }
                        return false;
                    default:
                        return false;
                }
            } else if (currentItem.matches(element) == 0) {
                return false;
            } else {
                i--;
            }
        }
        return true;
    }
}
