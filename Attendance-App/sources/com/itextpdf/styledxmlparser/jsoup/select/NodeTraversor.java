package com.itextpdf.styledxmlparser.jsoup.select;

import com.itextpdf.styledxmlparser.jsoup.nodes.Node;

public class NodeTraversor {
    private NodeVisitor visitor;

    public NodeTraversor(NodeVisitor visitor2) {
        this.visitor = visitor2;
    }

    public void traverse(Node root) {
        Node node = root;
        int depth = 0;
        while (node != null) {
            this.visitor.head(node, depth);
            if (node.childNodeSize() > 0) {
                node = node.childNode(0);
                depth++;
            } else {
                while (node.nextSibling() == null && depth > 0) {
                    this.visitor.tail(node, depth);
                    node = node.parentNode();
                    depth--;
                }
                this.visitor.tail(node, depth);
                if (node != root) {
                    node = node.nextSibling();
                } else {
                    return;
                }
            }
        }
    }
}
