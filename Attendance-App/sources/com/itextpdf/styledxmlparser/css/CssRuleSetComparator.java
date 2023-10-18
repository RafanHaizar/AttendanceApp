package com.itextpdf.styledxmlparser.css;

import com.itextpdf.styledxmlparser.css.selector.CssSelectorComparator;
import java.util.Comparator;

public class CssRuleSetComparator implements Comparator<CssRuleSet> {
    private CssSelectorComparator selectorComparator = new CssSelectorComparator();

    public int compare(CssRuleSet o1, CssRuleSet o2) {
        return this.selectorComparator.compare(o1.getSelector(), o2.getSelector());
    }
}
