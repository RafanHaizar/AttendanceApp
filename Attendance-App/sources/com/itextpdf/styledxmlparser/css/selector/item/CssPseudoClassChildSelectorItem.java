package com.itextpdf.styledxmlparser.css.selector.item;

import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.INode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class CssPseudoClassChildSelectorItem extends CssPseudoClassSelectorItem {
    CssPseudoClassChildSelectorItem(String pseudoClass) {
        super(pseudoClass);
    }

    CssPseudoClassChildSelectorItem(String pseudoClass, String arguments) {
        super(pseudoClass, arguments);
    }

    /* access modifiers changed from: package-private */
    public List<INode> getAllSiblings(INode node) {
        INode parentElement = node.parentNode();
        if (parentElement == null) {
            return Collections.emptyList();
        }
        List<INode> childrenUnmodifiable = parentElement.childNodes();
        List<INode> children = new ArrayList<>(childrenUnmodifiable.size());
        for (INode iNode : childrenUnmodifiable) {
            if (iNode instanceof IElementNode) {
                children.add(iNode);
            }
        }
        return children;
    }

    /* access modifiers changed from: package-private */
    public List<INode> getAllSiblingsOfNodeType(INode node) {
        INode parentElement = node.parentNode();
        if (parentElement == null) {
            return Collections.emptyList();
        }
        List<INode> childrenUnmodifiable = parentElement.childNodes();
        List<INode> children = new ArrayList<>(childrenUnmodifiable.size());
        for (INode iNode : childrenUnmodifiable) {
            if ((iNode instanceof IElementNode) && ((IElementNode) iNode).name().equals(((IElementNode) node).name())) {
                children.add(iNode);
            }
        }
        return children;
    }
}
