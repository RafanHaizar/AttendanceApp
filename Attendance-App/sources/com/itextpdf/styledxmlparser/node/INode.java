package com.itextpdf.styledxmlparser.node;

import java.util.List;

public interface INode {
    void addChild(INode iNode);

    List<INode> childNodes();

    INode parentNode();
}
