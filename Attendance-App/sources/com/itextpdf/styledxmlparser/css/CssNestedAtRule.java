package com.itextpdf.styledxmlparser.css;

import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.node.INode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CssNestedAtRule extends CssAtRule {
    protected List<CssStatement> body = new ArrayList();
    private String ruleParameters;

    public CssNestedAtRule(String ruleName, String ruleParameters2) {
        super(ruleName);
        this.ruleParameters = ruleParameters2;
    }

    public void addStatementToBody(CssStatement statement) {
        this.body.add(statement);
    }

    public void addStatementsToBody(Collection<CssStatement> statements) {
        this.body.addAll(statements);
    }

    public void addBodyCssDeclarations(List<CssDeclaration> list) {
    }

    public List<CssRuleSet> getCssRuleSets(INode node, MediaDeviceDescription deviceDescription) {
        List<CssRuleSet> result = new ArrayList<>();
        for (CssStatement childStatement : this.body) {
            result.addAll(childStatement.getCssRuleSets(node, deviceDescription));
        }
        return result;
    }

    public List<CssStatement> getStatements() {
        return this.body;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(MessageFormatUtil.format("@{0} {1} ", this.ruleName, this.ruleParameters));
        sb.append("{");
        sb.append("\n");
        for (int i = 0; i < this.body.size(); i++) {
            sb.append("    ");
            sb.append(this.body.get(i).toString().replace("\n", "\n    "));
            if (i != this.body.size() - 1) {
                sb.append("\n");
            }
        }
        sb.append("\n}");
        return sb.toString();
    }

    public String getRuleParameters() {
        return this.ruleParameters;
    }
}
