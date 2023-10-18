package com.itextpdf.styledxmlparser.css;

import com.itextpdf.layout.font.Range;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import java.util.ArrayList;
import java.util.List;

public class CssFontFaceRule extends CssNestedAtRule {
    private List<CssDeclaration> properties;

    public CssFontFaceRule() {
        this("");
    }

    @Deprecated
    public CssFontFaceRule(String ruleParameters) {
        super("font-face", ruleParameters);
    }

    public List<CssDeclaration> getProperties() {
        return new ArrayList(this.properties);
    }

    public void addBodyCssDeclarations(List<CssDeclaration> cssDeclarations) {
        this.properties = new ArrayList(cssDeclarations);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("@").append(getRuleName()).append(" {").append("\n");
        for (CssDeclaration declaration : this.properties) {
            sb.append("    ");
            sb.append(declaration);
            sb.append(";\n");
        }
        sb.append("}");
        return sb.toString();
    }

    public Range resolveUnicodeRange() {
        Range range = null;
        for (CssDeclaration descriptor : getProperties()) {
            if ("unicode-range".equals(descriptor.getProperty())) {
                range = CssUtils.parseUnicodeRange(descriptor.getExpression());
            }
        }
        return range;
    }
}
