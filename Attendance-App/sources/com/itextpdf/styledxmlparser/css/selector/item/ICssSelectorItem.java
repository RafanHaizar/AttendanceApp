package com.itextpdf.styledxmlparser.css.selector.item;

import com.itextpdf.styledxmlparser.node.INode;

public interface ICssSelectorItem {
    int getSpecificity();

    boolean matches(INode iNode);
}
