package com.itextpdf.styledxmlparser.jsoup.select;

import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.jsoup.nodes.Node;
import java.util.Iterator;

abstract class StructuralEvaluator extends Evaluator {
    Evaluator evaluator;

    StructuralEvaluator() {
    }

    static class Root extends Evaluator {
        Root() {
        }

        public boolean matches(Element root, Element element) {
            return root == element;
        }
    }

    static class Has extends StructuralEvaluator {
        public Has(Evaluator evaluator) {
            this.evaluator = evaluator;
        }

        public boolean matches(Element root, Element element) {
            Iterator it = element.getAllElements().iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                if (e != element && this.evaluator.matches(root, e)) {
                    return true;
                }
            }
            return false;
        }

        public String toString() {
            return MessageFormatUtil.format(":has({0})", this.evaluator);
        }
    }

    static class Not extends StructuralEvaluator {
        public Not(Evaluator evaluator) {
            this.evaluator = evaluator;
        }

        public boolean matches(Element root, Element node) {
            return !this.evaluator.matches(root, node);
        }

        public String toString() {
            return MessageFormatUtil.format(":not{0}", this.evaluator);
        }
    }

    static class Parent extends StructuralEvaluator {
        public Parent(Evaluator evaluator) {
            this.evaluator = evaluator;
        }

        public boolean matches(Element root, Element element) {
            if (root == element) {
                return false;
            }
            Node parent = element.parent();
            while (true) {
                Element parent2 = (Element) parent;
                if (this.evaluator.matches(root, parent2)) {
                    return true;
                }
                if (parent2 == root) {
                    return false;
                }
                parent = parent2.parent();
            }
        }

        public String toString() {
            return MessageFormatUtil.format(":parent{0}", this.evaluator);
        }
    }

    static class ImmediateParent extends StructuralEvaluator {
        public ImmediateParent(Evaluator evaluator) {
            this.evaluator = evaluator;
        }

        public boolean matches(Element root, Element element) {
            Element parent;
            if (root == element || (parent = (Element) element.parent()) == null || !this.evaluator.matches(root, parent)) {
                return false;
            }
            return true;
        }

        public String toString() {
            return MessageFormatUtil.format(":ImmediateParent{0}", this.evaluator);
        }
    }

    static class PreviousSibling extends StructuralEvaluator {
        public PreviousSibling(Evaluator evaluator) {
            this.evaluator = evaluator;
        }

        public boolean matches(Element root, Element element) {
            if (root == element) {
                return false;
            }
            for (Element prev = element.previousElementSibling(); prev != null; prev = prev.previousElementSibling()) {
                if (this.evaluator.matches(root, prev)) {
                    return true;
                }
            }
            return false;
        }

        public String toString() {
            return MessageFormatUtil.format(":prev*{0}", this.evaluator);
        }
    }

    static class ImmediatePreviousSibling extends StructuralEvaluator {
        public ImmediatePreviousSibling(Evaluator evaluator) {
            this.evaluator = evaluator;
        }

        public boolean matches(Element root, Element element) {
            Element prev;
            if (root == element || (prev = element.previousElementSibling()) == null || !this.evaluator.matches(root, prev)) {
                return false;
            }
            return true;
        }

        public String toString() {
            return MessageFormatUtil.format(":prev{0}", this.evaluator);
        }
    }
}
