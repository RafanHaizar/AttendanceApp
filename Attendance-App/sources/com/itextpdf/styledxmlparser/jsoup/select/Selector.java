package com.itextpdf.styledxmlparser.jsoup.select;

import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;

public class Selector {
    private final Evaluator evaluator;
    private final Element root;

    private Selector(String query, Element root2) {
        Validate.notNull(query);
        String query2 = query.trim();
        Validate.notEmpty(query2);
        Validate.notNull(root2);
        this.evaluator = QueryParser.parse(query2);
        this.root = root2;
    }

    private Selector(Evaluator evaluator2, Element root2) {
        Validate.notNull(evaluator2);
        Validate.notNull(root2);
        this.evaluator = evaluator2;
        this.root = root2;
    }

    public static Elements select(String query, Element root2) {
        return new Selector(query, root2).select();
    }

    public static Elements select(Evaluator evaluator2, Element root2) {
        return new Selector(evaluator2, root2).select();
    }

    public static Elements select(String query, Iterable<Element> roots) {
        Validate.notEmpty(query);
        Validate.notNull(roots);
        Evaluator evaluator2 = QueryParser.parse(query);
        ArrayList<Element> elements = new ArrayList<>();
        IdentityHashMap<Element, Boolean> seenElements = new IdentityHashMap<>();
        for (Element root2 : roots) {
            Iterator it = select(evaluator2, root2).iterator();
            while (it.hasNext()) {
                Element el = (Element) it.next();
                if (!seenElements.containsKey(el)) {
                    elements.add(el);
                    seenElements.put(el, Boolean.TRUE);
                }
            }
        }
        return new Elements((List<Element>) elements);
    }

    private Elements select() {
        return Collector.collect(this.evaluator, this.root);
    }

    static Elements filterOut(Collection<Element> elements, Collection<Element> outs) {
        Elements output = new Elements();
        for (Element el : elements) {
            boolean found = false;
            Iterator<Element> it = outs.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (el.equals(it.next())) {
                        found = true;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (!found) {
                output.add(el);
            }
        }
        return output;
    }

    public static class SelectorParseException extends IllegalStateException {
        public SelectorParseException(String msg, Object... params) {
            super(MessageFormatUtil.format(msg, params));
        }
    }
}
