package com.itextpdf.styledxmlparser.css;

import com.itextpdf.styledxmlparser.node.INode;
import com.itextpdf.styledxmlparser.node.IStylesContainer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class CssContextNode implements INode, IStylesContainer {
    private List<INode> childNodes = new ArrayList();
    private INode parentNode;
    private Map<String, String> styles;

    public CssContextNode(INode parentNode2) {
        this.parentNode = parentNode2;
    }

    public List<INode> childNodes() {
        return Collections.unmodifiableList(this.childNodes);
    }

    public void addChild(INode node) {
        this.childNodes.add(node);
    }

    public INode parentNode() {
        return this.parentNode;
    }

    public void setStyles(Map<String, String> stringStringMap) {
        this.styles = stringStringMap;
    }

    public Map<String, String> getStyles() {
        return this.styles;
    }
}
