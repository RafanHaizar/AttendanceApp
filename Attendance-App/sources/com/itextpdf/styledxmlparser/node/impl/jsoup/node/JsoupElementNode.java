package com.itextpdf.styledxmlparser.node.impl.jsoup.node;

import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.node.IAttributes;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.INode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsoupElementNode extends JsoupNode implements IElementNode {
    private IAttributes attributes;
    private List<Map<String, String>> customDefaultStyles;
    private Element element;
    private Map<String, String> elementResolvedStyles;
    private String lang = null;

    public JsoupElementNode(Element element2) {
        super(element2);
        this.element = element2;
        this.attributes = new JsoupAttributes(element2.attributes());
        this.lang = getAttribute("lang");
    }

    public String name() {
        return this.element.nodeName();
    }

    public IAttributes getAttributes() {
        return this.attributes;
    }

    public String getAttribute(String key) {
        return this.attributes.getAttribute(key);
    }

    public void setStyles(Map<String, String> elementResolvedStyles2) {
        this.elementResolvedStyles = elementResolvedStyles2;
    }

    public Map<String, String> getStyles() {
        return this.elementResolvedStyles;
    }

    public List<Map<String, String>> getAdditionalHtmlStyles() {
        return this.customDefaultStyles;
    }

    public void addAdditionalHtmlStyles(Map<String, String> styles) {
        if (this.customDefaultStyles == null) {
            this.customDefaultStyles = new ArrayList();
        }
        this.customDefaultStyles.add(styles);
    }

    public String getLang() {
        String str = this.lang;
        if (str != null) {
            return str;
        }
        INode parent = this.parentNode;
        String lang2 = parent instanceof IElementNode ? ((IElementNode) parent).getLang() : null;
        this.lang = lang2;
        if (lang2 == null) {
            this.lang = "";
        }
        return this.lang;
    }

    public String text() {
        return this.element.text();
    }
}
