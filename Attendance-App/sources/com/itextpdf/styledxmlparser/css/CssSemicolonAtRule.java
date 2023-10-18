package com.itextpdf.styledxmlparser.css;

import com.itextpdf.p026io.util.MessageFormatUtil;

public class CssSemicolonAtRule extends CssAtRule {
    private String ruleParams;

    public CssSemicolonAtRule(String ruleDeclaration) {
        super(CssNestedAtRuleFactory.extractRuleNameFromDeclaration(ruleDeclaration.trim()));
        this.ruleParams = ruleDeclaration.trim().substring(this.ruleName.length()).trim();
    }

    public String toString() {
        return MessageFormatUtil.format("@{0} {1};", this.ruleName, this.ruleParams);
    }
}
