package com.itextpdf.styledxmlparser.css.selector;

import com.itextpdf.styledxmlparser.css.selector.item.ICssSelectorItem;
import java.util.Collections;
import java.util.List;

public abstract class AbstractCssSelector implements ICssSelector {
    protected List<ICssSelectorItem> selectorItems;

    public AbstractCssSelector(List<ICssSelectorItem> selectorItems2) {
        this.selectorItems = selectorItems2;
    }

    public List<ICssSelectorItem> getSelectorItems() {
        return Collections.unmodifiableList(this.selectorItems);
    }

    public int calculateSpecificity() {
        int specificity = 0;
        for (ICssSelectorItem item : this.selectorItems) {
            specificity += item.getSpecificity();
        }
        return specificity;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ICssSelectorItem item : this.selectorItems) {
            sb.append(item.toString());
        }
        return sb.toString();
    }
}
