package com.itextpdf.styledxmlparser.css;

import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.selector.ICssSelector;
import com.itextpdf.styledxmlparser.node.INode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class CssRuleSet extends CssStatement {
    private static final Pattern importantMatcher = Pattern.compile(".*!\\s*important$");
    private List<CssDeclaration> importantDeclarations;
    private List<CssDeclaration> normalDeclarations;
    private ICssSelector selector;

    public CssRuleSet(ICssSelector selector2, List<CssDeclaration> declarations) {
        this.selector = selector2;
        this.normalDeclarations = new ArrayList();
        ArrayList arrayList = new ArrayList();
        this.importantDeclarations = arrayList;
        splitDeclarationsIntoNormalAndImportant(declarations, this.normalDeclarations, arrayList);
    }

    public CssRuleSet(ICssSelector selector2, List<CssDeclaration> normalDeclarations2, List<CssDeclaration> importantDeclarations2) {
        this.selector = selector2;
        this.normalDeclarations = normalDeclarations2;
        this.importantDeclarations = importantDeclarations2;
    }

    public List<CssRuleSet> getCssRuleSets(INode element, MediaDeviceDescription deviceDescription) {
        if (this.selector.matches(element)) {
            return Collections.singletonList(this);
        }
        return super.getCssRuleSets(element, deviceDescription);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.selector.toString());
        sb.append(" {\n");
        for (int i = 0; i < this.normalDeclarations.size(); i++) {
            if (i > 0) {
                sb.append(";").append("\n");
            }
            sb.append("    ").append(this.normalDeclarations.get(i).toString());
        }
        for (int i2 = 0; i2 < this.importantDeclarations.size(); i2++) {
            if (i2 > 0 || this.normalDeclarations.size() > 0) {
                sb.append(";").append("\n");
            }
            sb.append("    ").append(this.importantDeclarations.get(i2).toString()).append(" !important");
        }
        sb.append("\n}");
        return sb.toString();
    }

    public ICssSelector getSelector() {
        return this.selector;
    }

    public List<CssDeclaration> getNormalDeclarations() {
        return this.normalDeclarations;
    }

    public List<CssDeclaration> getImportantDeclarations() {
        return this.importantDeclarations;
    }

    private static void splitDeclarationsIntoNormalAndImportant(List<CssDeclaration> declarations, List<CssDeclaration> normalDeclarations2, List<CssDeclaration> importantDeclarations2) {
        for (CssDeclaration declaration : declarations) {
            int exclIndex = declaration.getExpression().indexOf(33);
            if (exclIndex <= 0 || !importantMatcher.matcher(declaration.getExpression()).matches()) {
                normalDeclarations2.add(declaration);
            } else {
                importantDeclarations2.add(new CssDeclaration(declaration.getProperty(), declaration.getExpression().substring(0, exclIndex).trim()));
            }
        }
    }
}
