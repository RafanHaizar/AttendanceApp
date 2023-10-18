package com.itextpdf.styledxmlparser.css.page;

import com.itextpdf.styledxmlparser.css.CssDeclaration;
import com.itextpdf.styledxmlparser.css.CssNestedAtRule;
import com.itextpdf.styledxmlparser.css.selector.CssPageMarginBoxSelector;
import com.itextpdf.styledxmlparser.css.selector.ICssSelector;
import java.util.ArrayList;
import java.util.List;

public class CssMarginRule extends CssNestedAtRule {
    private List<ICssSelector> pageSelectors;

    public CssMarginRule(String ruleName) {
        this(ruleName, "");
    }

    @Deprecated
    public CssMarginRule(String ruleName, String ruleParameters) {
        super(ruleName, ruleParameters);
    }

    public void addBodyCssDeclarations(List<CssDeclaration> cssDeclarations) {
        for (ICssSelector pageSelector : this.pageSelectors) {
            this.body.add(new CssNonStandardRuleSet(new CssPageMarginBoxSelector(getRuleName(), pageSelector), cssDeclarations));
        }
    }

    /* access modifiers changed from: package-private */
    public void setPageSelectors(List<ICssSelector> pageSelectors2) {
        this.pageSelectors = new ArrayList(pageSelectors2);
    }
}
