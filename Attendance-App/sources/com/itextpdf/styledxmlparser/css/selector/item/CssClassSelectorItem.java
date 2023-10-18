package com.itextpdf.styledxmlparser.css.selector.item;

import com.itextpdf.styledxmlparser.CommonAttributeConstants;
import com.itextpdf.styledxmlparser.node.ICustomElementNode;
import com.itextpdf.styledxmlparser.node.IDocumentNode;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.INode;

public class CssClassSelectorItem implements ICssSelectorItem {
    private String className;

    public CssClassSelectorItem(String className2) {
        this.className = className2;
    }

    public int getSpecificity() {
        return 1024;
    }

    public String toString() {
        return "." + this.className;
    }

    public boolean matches(INode node) {
        String classAttr;
        if ((node instanceof IElementNode) && !(node instanceof ICustomElementNode) && !(node instanceof IDocumentNode) && (classAttr = ((IElementNode) node).getAttribute(CommonAttributeConstants.CLASS)) != null && classAttr.length() > 0) {
            for (String currClassName : classAttr.split(" ")) {
                if (this.className.equals(currClassName.trim())) {
                    return true;
                }
            }
        }
        return false;
    }
}
