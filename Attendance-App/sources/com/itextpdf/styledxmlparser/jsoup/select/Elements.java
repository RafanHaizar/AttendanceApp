package com.itextpdf.styledxmlparser.jsoup.select;

import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.jsoup.nodes.FormElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Elements extends ArrayList<Element> {
    public Elements() {
    }

    public Elements(int initialCapacity) {
        super(initialCapacity);
    }

    public Elements(Collection<Element> elements) {
        super(elements);
    }

    public Elements(List<Element> elements) {
        super(elements);
    }

    public Elements(Element... elements) {
        super(Arrays.asList(elements));
    }

    public Object clone() {
        Elements clone = new Elements(size());
        Iterator it = iterator();
        while (it.hasNext()) {
            clone.add((Element) ((Element) it.next()).clone());
        }
        return clone;
    }

    public String attr(String attributeKey) {
        Iterator it = iterator();
        while (it.hasNext()) {
            Element element = (Element) it.next();
            if (element.hasAttr(attributeKey)) {
                return element.attr(attributeKey);
            }
        }
        return "";
    }

    public boolean hasAttr(String attributeKey) {
        Iterator it = iterator();
        while (it.hasNext()) {
            if (((Element) it.next()).hasAttr(attributeKey)) {
                return true;
            }
        }
        return false;
    }

    public Elements attr(String attributeKey, String attributeValue) {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).attr(attributeKey, attributeValue);
        }
        return this;
    }

    public Elements removeAttr(String attributeKey) {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).removeAttr(attributeKey);
        }
        return this;
    }

    public Elements addClass(String className) {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).addClass(className);
        }
        return this;
    }

    public Elements removeClass(String className) {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).removeClass(className);
        }
        return this;
    }

    public Elements toggleClass(String className) {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).toggleClass(className);
        }
        return this;
    }

    public boolean hasClass(String className) {
        Iterator it = iterator();
        while (it.hasNext()) {
            if (((Element) it.next()).hasClass(className)) {
                return true;
            }
        }
        return false;
    }

    public String val() {
        if (size() > 0) {
            return first().val();
        }
        return "";
    }

    public Elements val(String value) {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).val(value);
        }
        return this;
    }

    public String text() {
        StringBuilder sb = new StringBuilder();
        Iterator it = iterator();
        while (it.hasNext()) {
            Element element = (Element) it.next();
            if (sb.length() != 0) {
                sb.append(" ");
            }
            sb.append(element.text());
        }
        return sb.toString();
    }

    public boolean hasText() {
        Iterator it = iterator();
        while (it.hasNext()) {
            if (((Element) it.next()).hasText()) {
                return true;
            }
        }
        return false;
    }

    public String html() {
        StringBuilder sb = new StringBuilder();
        Iterator it = iterator();
        while (it.hasNext()) {
            Element element = (Element) it.next();
            if (sb.length() != 0) {
                sb.append("\n");
            }
            sb.append(element.html());
        }
        return sb.toString();
    }

    public String outerHtml() {
        StringBuilder sb = new StringBuilder();
        Iterator it = iterator();
        while (it.hasNext()) {
            Element element = (Element) it.next();
            if (sb.length() != 0) {
                sb.append("\n");
            }
            sb.append(element.outerHtml());
        }
        return sb.toString();
    }

    public String toString() {
        return outerHtml();
    }

    public Elements tagName(String tagName) {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).tagName(tagName);
        }
        return this;
    }

    public Elements html(String html) {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).html(html);
        }
        return this;
    }

    public Elements prepend(String html) {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).prepend(html);
        }
        return this;
    }

    public Elements append(String html) {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).append(html);
        }
        return this;
    }

    public Elements before(String html) {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).before(html);
        }
        return this;
    }

    public Elements after(String html) {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).after(html);
        }
        return this;
    }

    public Elements wrap(String html) {
        Validate.notEmpty(html);
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).wrap(html);
        }
        return this;
    }

    public Elements unwrap() {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).unwrap();
        }
        return this;
    }

    public Elements empty() {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).empty();
        }
        return this;
    }

    public Elements remove() {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Element) it.next()).remove();
        }
        return this;
    }

    public Elements select(String query) {
        return Selector.select(query, (Iterable<Element>) this);
    }

    public Elements not(String query) {
        return Selector.filterOut(this, Selector.select(query, (Iterable<Element>) this));
    }

    /* renamed from: eq */
    public Elements mo31462eq(int index) {
        if (size() <= index) {
            return new Elements();
        }
        return new Elements((Element) get(index));
    }

    /* renamed from: is */
    public boolean mo31470is(String query) {
        return !select(query).isEmpty();
    }

    public Elements parents() {
        Set<Element> combo = new LinkedHashSet<>();
        Iterator it = iterator();
        while (it.hasNext()) {
            combo.addAll(((Element) it.next()).parents());
        }
        return new Elements((Collection<Element>) combo);
    }

    public Element first() {
        if (isEmpty()) {
            return null;
        }
        return (Element) get(0);
    }

    public Element last() {
        if (isEmpty()) {
            return null;
        }
        return (Element) get(size() - 1);
    }

    public Elements traverse(NodeVisitor nodeVisitor) {
        Validate.notNull(nodeVisitor);
        NodeTraversor traversor = new NodeTraversor(nodeVisitor);
        Iterator it = iterator();
        while (it.hasNext()) {
            traversor.traverse((Element) it.next());
        }
        return this;
    }

    public List<FormElement> forms() {
        ArrayList<FormElement> forms = new ArrayList<>();
        Iterator it = iterator();
        while (it.hasNext()) {
            Element el = (Element) it.next();
            if (el instanceof FormElement) {
                forms.add((FormElement) el);
            }
        }
        return forms;
    }
}
