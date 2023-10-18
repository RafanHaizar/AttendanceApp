package com.itextpdf.styledxmlparser.css.page;

import com.itextpdf.styledxmlparser.css.CssDeclaration;
import com.itextpdf.styledxmlparser.css.CssRuleSet;
import com.itextpdf.styledxmlparser.css.selector.ICssSelector;
import java.util.List;

class CssNonStandardRuleSet extends CssRuleSet {
    public CssNonStandardRuleSet(ICssSelector selector, List<CssDeclaration> declarations) {
        super(selector, declarations);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getNormalDeclarations().size(); i++) {
            if (i > 0) {
                sb.append(";").append("\n");
            }
            sb.append(getNormalDeclarations().get(i).toString());
        }
        for (int i2 = 0; i2 < getImportantDeclarations().size(); i2++) {
            if (i2 > 0 || getNormalDeclarations().size() > 0) {
                sb.append(";").append("\n");
            }
            sb.append(getImportantDeclarations().get(i2).toString()).append(" !important");
        }
        return sb.toString();
    }
}
