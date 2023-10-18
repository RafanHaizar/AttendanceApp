package com.itextpdf.styledxmlparser.jsoup.select;

import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.jsoup.nodes.Node;

public class Collector {
    private Collector() {
    }

    public static Elements collect(Evaluator eval, Element root) {
        Elements elements = new Elements();
        new NodeTraversor(new Accumulator(root, elements, eval)).traverse(root);
        return elements;
    }

    private static class Accumulator implements NodeVisitor {
        private final Elements elements;
        private final Evaluator eval;
        private final Element root;

        Accumulator(Element root2, Elements elements2, Evaluator eval2) {
            this.root = root2;
            this.elements = elements2;
            this.eval = eval2;
        }

        public void head(Node node, int depth) {
            if (node instanceof Element) {
                Element el = (Element) node;
                if (this.eval.matches(this.root, el)) {
                    this.elements.add(el);
                }
            }
        }

        public void tail(Node node, int depth) {
        }
    }
}
