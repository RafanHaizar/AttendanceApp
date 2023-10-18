package com.itextpdf.styledxmlparser.css;

import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.css.util.CssUtils;

public class CssDeclaration {
    private String expression;
    private String property;

    public CssDeclaration(String property2, String expression2) {
        this.property = CssUtils.normalizeCssProperty(property2);
        this.expression = CssUtils.normalizeCssProperty(expression2);
    }

    public String toString() {
        return MessageFormatUtil.format("{0}: {1}", this.property, this.expression);
    }

    public String getProperty() {
        return this.property;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression2) {
        this.expression = expression2;
    }
}
