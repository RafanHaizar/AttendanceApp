package com.itextpdf.styledxmlparser.jsoup.select;

import com.itextpdf.styledxmlparser.jsoup.nodes.Node;

public interface NodeVisitor {
    void head(Node node, int i);

    void tail(Node node, int i);
}
