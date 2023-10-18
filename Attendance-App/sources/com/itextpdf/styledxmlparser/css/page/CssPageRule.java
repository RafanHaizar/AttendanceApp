package com.itextpdf.styledxmlparser.css.page;

import com.itextpdf.styledxmlparser.css.CssDeclaration;
import com.itextpdf.styledxmlparser.css.CssNestedAtRule;
import com.itextpdf.styledxmlparser.css.CssStatement;
import com.itextpdf.styledxmlparser.css.selector.CssPageSelector;
import com.itextpdf.styledxmlparser.css.selector.ICssSelector;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CssPageRule extends CssNestedAtRule {
    private List<ICssSelector> pageSelectors = new ArrayList();

    public CssPageRule(String ruleParameters) {
        super("page", ruleParameters);
        String[] selectors = ruleParameters.split(",");
        for (int i = 0; i < selectors.length; i++) {
            selectors[i] = CssUtils.removeDoubleSpacesAndTrim(selectors[i]);
        }
        for (String currentSelectorStr : selectors) {
            this.pageSelectors.add(new CssPageSelector(currentSelectorStr));
        }
    }

    public void addBodyCssDeclarations(List<CssDeclaration> cssDeclarations) {
        for (ICssSelector pageSelector : this.pageSelectors) {
            this.body.add(new CssNonStandardRuleSet(pageSelector, cssDeclarations));
        }
    }

    public void addStatementToBody(CssStatement statement) {
        if (statement instanceof CssMarginRule) {
            ((CssMarginRule) statement).setPageSelectors(this.pageSelectors);
        }
        this.body.add(statement);
    }

    public void addStatementsToBody(Collection<CssStatement> statements) {
        for (CssStatement statement : statements) {
            addStatementToBody(statement);
        }
    }
}
