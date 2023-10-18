package com.itextpdf.styledxmlparser.css.page;

import com.itextpdf.styledxmlparser.css.CssContextNode;
import com.itextpdf.styledxmlparser.node.INode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PageContextNode extends CssContextNode {
    private List<String> pageClasses;
    private String pageTypeName;

    public PageContextNode() {
        this((INode) null);
    }

    public PageContextNode(INode parentNode) {
        super(parentNode);
        this.pageClasses = new ArrayList();
    }

    public PageContextNode addPageClass(String pageClass) {
        this.pageClasses.add(pageClass.toLowerCase());
        return this;
    }

    public String getPageTypeName() {
        return this.pageTypeName;
    }

    public PageContextNode setPageTypeName(String pageTypeName2) {
        this.pageTypeName = pageTypeName2;
        return this;
    }

    public List<String> getPageClasses() {
        return Collections.unmodifiableList(this.pageClasses);
    }
}
