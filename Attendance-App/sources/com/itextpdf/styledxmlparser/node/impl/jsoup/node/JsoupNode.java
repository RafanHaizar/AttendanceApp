package com.itextpdf.styledxmlparser.node.impl.jsoup.node;

import com.itextpdf.styledxmlparser.LogMessageConstant;
import com.itextpdf.styledxmlparser.jsoup.nodes.Node;
import com.itextpdf.styledxmlparser.node.INode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.LoggerFactory;

public class JsoupNode implements INode {
    private List<INode> childNodes = new ArrayList();
    private Node node;
    INode parentNode;

    public JsoupNode(Node node2) {
        this.node = node2;
    }

    public List<INode> childNodes() {
        return Collections.unmodifiableList(this.childNodes);
    }

    public void addChild(INode node2) {
        if (node2 instanceof JsoupNode) {
            this.childNodes.add(node2);
            ((JsoupNode) node2).parentNode = this;
            return;
        }
        LoggerFactory.getLogger((Class<?>) JsoupNode.class).error(LogMessageConstant.ERROR_ADDING_CHILD_NODE);
    }

    public INode parentNode() {
        return this.parentNode;
    }
}
