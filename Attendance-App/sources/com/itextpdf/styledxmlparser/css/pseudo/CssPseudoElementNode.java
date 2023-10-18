package com.itextpdf.styledxmlparser.css.pseudo;

import com.itextpdf.styledxmlparser.css.CssContextNode;
import com.itextpdf.styledxmlparser.node.IAttribute;
import com.itextpdf.styledxmlparser.node.IAttributes;
import com.itextpdf.styledxmlparser.node.ICustomElementNode;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.INode;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CssPseudoElementNode extends CssContextNode implements IElementNode, ICustomElementNode {
    private String pseudoElementName;
    private String pseudoElementTagName;

    public CssPseudoElementNode(INode parentNode, String pseudoElementName2) {
        super(parentNode);
        this.pseudoElementName = pseudoElementName2;
        this.pseudoElementTagName = CssPseudoElementUtil.createPseudoElementTagName(pseudoElementName2);
    }

    public String getPseudoElementName() {
        return this.pseudoElementName;
    }

    public String name() {
        return this.pseudoElementTagName;
    }

    public IAttributes getAttributes() {
        return new AttributesStub();
    }

    public String getAttribute(String key) {
        return null;
    }

    public List<Map<String, String>> getAdditionalHtmlStyles() {
        return null;
    }

    public void addAdditionalHtmlStyles(Map<String, String> map) {
        throw new UnsupportedOperationException();
    }

    public String getLang() {
        return null;
    }

    private static class AttributesStub implements IAttributes {
        private AttributesStub() {
        }

        public String getAttribute(String key) {
            return null;
        }

        public void setAttribute(String key, String value) {
            throw new UnsupportedOperationException();
        }

        public int size() {
            return 0;
        }

        public Iterator<IAttribute> iterator() {
            return Collections.emptyIterator();
        }
    }
}
