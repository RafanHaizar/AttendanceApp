package com.itextpdf.styledxmlparser.jsoup.select;

import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.jsoup.helper.StringUtil;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

abstract class CombiningEvaluator extends Evaluator {
    final ArrayList<Evaluator> evaluators;
    int num;

    CombiningEvaluator() {
        this.num = 0;
        this.evaluators = new ArrayList<>();
    }

    CombiningEvaluator(Collection<Evaluator> evaluators2) {
        this();
        this.evaluators.addAll(evaluators2);
        updateNumEvaluators();
    }

    /* access modifiers changed from: package-private */
    public Evaluator rightMostEvaluator() {
        int i = this.num;
        if (i > 0) {
            return this.evaluators.get(i - 1);
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void replaceRightMostEvaluator(Evaluator replacement) {
        this.evaluators.set(this.num - 1, replacement);
    }

    /* access modifiers changed from: package-private */
    public void updateNumEvaluators() {
        this.num = this.evaluators.size();
    }

    static final class And extends CombiningEvaluator {
        And(Collection<Evaluator> evaluators) {
            super(evaluators);
        }

        And(Evaluator... evaluators) {
            this((Collection<Evaluator>) Arrays.asList(evaluators));
        }

        public boolean matches(Element root, Element node) {
            for (int i = 0; i < this.num; i++) {
                if (!((Evaluator) this.evaluators.get(i)).matches(root, node)) {
                    return false;
                }
            }
            return true;
        }

        public String toString() {
            return StringUtil.join((Collection) this.evaluators, " ");
        }
    }

    /* renamed from: com.itextpdf.styledxmlparser.jsoup.select.CombiningEvaluator$Or */
    static final class C1588Or extends CombiningEvaluator {
        C1588Or(Collection<Evaluator> evaluators) {
            if (this.num > 1) {
                this.evaluators.add(new And(evaluators));
            } else {
                this.evaluators.addAll(evaluators);
            }
            updateNumEvaluators();
        }

        C1588Or() {
        }

        public void add(Evaluator e) {
            this.evaluators.add(e);
            updateNumEvaluators();
        }

        public boolean matches(Element root, Element node) {
            for (int i = 0; i < this.num; i++) {
                if (((Evaluator) this.evaluators.get(i)).matches(root, node)) {
                    return true;
                }
            }
            return false;
        }

        public String toString() {
            return MessageFormatUtil.format(":or{0}", this.evaluators);
        }
    }
}
