package com.itextpdf.styledxmlparser.css;

import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.CssRuleSetComparator;
import com.itextpdf.styledxmlparser.LogMessageConstant;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.IShorthandResolver;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.ShorthandResolverFactory;
import com.itextpdf.styledxmlparser.css.validate.CssDeclarationValidationMaster;
import com.itextpdf.styledxmlparser.node.INode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.LoggerFactory;

public class CssStyleSheet {
    private List<CssStatement> statements = new ArrayList();

    public void addStatement(CssStatement statement) {
        this.statements.add(statement);
    }

    public void appendCssStyleSheet(CssStyleSheet anotherCssStyleSheet) {
        this.statements.addAll(anotherCssStyleSheet.statements);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (CssStatement statement : this.statements) {
            if (sb.length() > 0) {
                sb.append("\n");
            }
            sb.append(statement.toString());
        }
        return sb.toString();
    }

    public List<CssStatement> getStatements() {
        return Collections.unmodifiableList(this.statements);
    }

    public List<CssDeclaration> getCssDeclarations(INode node, MediaDeviceDescription deviceDescription) {
        List<CssRuleSet> ruleSets = getCssRuleSets(node, deviceDescription);
        Map<String, CssDeclaration> declarations = new LinkedHashMap<>();
        for (CssRuleSet ruleSet : ruleSets) {
            populateDeclarationsMap(ruleSet.getNormalDeclarations(), declarations);
        }
        for (CssRuleSet ruleSet2 : ruleSets) {
            populateDeclarationsMap(ruleSet2.getImportantDeclarations(), declarations);
        }
        return new ArrayList(declarations.values());
    }

    public static Map<String, String> extractStylesFromRuleSets(List<CssRuleSet> ruleSets) {
        Map<String, CssDeclaration> declarations = new LinkedHashMap<>();
        for (CssRuleSet ruleSet : ruleSets) {
            populateDeclarationsMap(ruleSet.getNormalDeclarations(), declarations);
        }
        for (CssRuleSet ruleSet2 : ruleSets) {
            populateDeclarationsMap(ruleSet2.getImportantDeclarations(), declarations);
        }
        Map<String, String> stringMap = new LinkedHashMap<>();
        for (Map.Entry<String, CssDeclaration> entry : declarations.entrySet()) {
            stringMap.put(entry.getKey(), entry.getValue().getExpression());
        }
        return stringMap;
    }

    private static void populateDeclarationsMap(List<CssDeclaration> declarations, Map<String, CssDeclaration> map) {
        for (CssDeclaration declaration : declarations) {
            IShorthandResolver shorthandResolver = ShorthandResolverFactory.getShorthandResolver(declaration.getProperty());
            if (shorthandResolver == null) {
                putDeclarationInMapIfValid(map, declaration);
            } else {
                populateDeclarationsMap(shorthandResolver.resolveShorthand(declaration.getExpression()), map);
            }
        }
    }

    public List<CssRuleSet> getCssRuleSets(INode node, MediaDeviceDescription deviceDescription) {
        List<CssRuleSet> ruleSets = new ArrayList<>();
        for (CssStatement statement : this.statements) {
            ruleSets.addAll(statement.getCssRuleSets(node, deviceDescription));
        }
        Collections.sort(ruleSets, new CssRuleSetComparator());
        return ruleSets;
    }

    private static void putDeclarationInMapIfValid(Map<String, CssDeclaration> stylesMap, CssDeclaration cssDeclaration) {
        if (CssDeclarationValidationMaster.checkDeclaration(cssDeclaration)) {
            stylesMap.put(cssDeclaration.getProperty(), cssDeclaration);
            return;
        }
        LoggerFactory.getLogger((Class<?>) ICssResolver.class).warn(MessageFormatUtil.format(LogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, cssDeclaration));
    }
}
