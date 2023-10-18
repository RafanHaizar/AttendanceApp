package com.itextpdf.styledxmlparser.css;

public abstract class CssAtRule extends CssStatement {
    String ruleName;

    CssAtRule(String ruleName2) {
        this.ruleName = ruleName2;
    }

    public String getRuleName() {
        return this.ruleName;
    }
}
