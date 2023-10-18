package com.itextpdf.styledxmlparser.css.selector;

import com.itextpdf.styledxmlparser.node.INode;

public interface ICssSelector {
    int calculateSpecificity();

    boolean matches(INode iNode);
}
