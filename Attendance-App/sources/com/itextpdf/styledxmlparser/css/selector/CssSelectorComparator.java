package com.itextpdf.styledxmlparser.css.selector;

import java.util.Comparator;

public class CssSelectorComparator implements Comparator<ICssSelector> {
    public int compare(ICssSelector o1, ICssSelector o2) {
        return o1.calculateSpecificity() - o2.calculateSpecificity();
    }
}
