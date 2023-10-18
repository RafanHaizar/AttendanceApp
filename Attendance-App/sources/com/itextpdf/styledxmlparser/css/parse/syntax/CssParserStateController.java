package com.itextpdf.styledxmlparser.css.parse.syntax;

import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.LogMessageConstant;
import com.itextpdf.styledxmlparser.css.CssDeclaration;
import com.itextpdf.styledxmlparser.css.CssNestedAtRule;
import com.itextpdf.styledxmlparser.css.CssNestedAtRuleFactory;
import com.itextpdf.styledxmlparser.css.CssRuleName;
import com.itextpdf.styledxmlparser.css.CssRuleSet;
import com.itextpdf.styledxmlparser.css.CssSemicolonAtRule;
import com.itextpdf.styledxmlparser.css.CssStyleSheet;
import com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer;
import com.itextpdf.styledxmlparser.css.parse.CssRuleSetParser;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.styledxmlparser.resolver.resource.UriResolver;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import org.slf4j.LoggerFactory;

public final class CssParserStateController {
    private static final Set<String> CONDITIONAL_GROUP_RULES = Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{CssRuleName.MEDIA})));
    private static final Set<String> SUPPORTED_RULES = Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{CssRuleName.MEDIA, "page", CssRuleName.TOP_LEFT_CORNER, CssRuleName.TOP_LEFT, CssRuleName.TOP_CENTER, CssRuleName.TOP_RIGHT, CssRuleName.TOP_RIGHT_CORNER, CssRuleName.BOTTOM_LEFT_CORNER, CssRuleName.BOTTOM_LEFT, CssRuleName.BOTTOM_CENTER, CssRuleName.BOTTOM_RIGHT, CssRuleName.BOTTOM_RIGHT_CORNER, CssRuleName.LEFT_TOP, CssRuleName.LEFT_MIDDLE, CssRuleName.LEFT_BOTTOM, CssRuleName.RIGHT_TOP, CssRuleName.RIGHT_MIDDLE, CssRuleName.RIGHT_BOTTOM, "font-face"})));
    private final IParserState atRuleBlockState;
    private StringBuilder buffer;
    private final IParserState commendEndState;
    private final IParserState commendInnerState;
    private final IParserState commentStartState;
    private final IParserState conditionalGroupAtRuleBlockState;
    private String currentSelector;
    private IParserState currentState;
    private boolean isCurrentRuleSupported;
    private Stack<CssNestedAtRule> nestedAtRules;
    private IParserState previousActiveState;
    private final IParserState propertiesState;
    private final IParserState ruleState;
    private Stack<List<CssDeclaration>> storedPropertiesWithoutSelector;
    private CssStyleSheet styleSheet;
    private final IParserState unknownState;
    private UriResolver uriResolver;

    public CssParserStateController() {
        this("");
    }

    public CssParserStateController(String baseUrl) {
        this.isCurrentRuleSupported = true;
        this.buffer = new StringBuilder();
        if (baseUrl != null && baseUrl.length() > 0) {
            this.uriResolver = new UriResolver(baseUrl);
        }
        this.styleSheet = new CssStyleSheet();
        this.nestedAtRules = new Stack<>();
        this.storedPropertiesWithoutSelector = new Stack<>();
        this.commentStartState = new CommentStartState(this);
        this.commendEndState = new CommentEndState(this);
        this.commendInnerState = new CommentInnerState(this);
        UnknownState unknownState2 = new UnknownState(this);
        this.unknownState = unknownState2;
        this.ruleState = new RuleState(this);
        this.propertiesState = new PropertiesState(this);
        this.atRuleBlockState = new AtRuleBlockState(this);
        this.conditionalGroupAtRuleBlockState = new ConditionalGroupAtRuleBlockState(this);
        this.currentState = unknownState2;
    }

    public void process(char ch) {
        this.currentState.process(ch);
    }

    public CssStyleSheet getParsingResult() {
        return this.styleSheet;
    }

    /* access modifiers changed from: package-private */
    public void appendToBuffer(char ch) {
        this.buffer.append(ch);
    }

    /* access modifiers changed from: package-private */
    public String getBufferContents() {
        return this.buffer.toString();
    }

    /* access modifiers changed from: package-private */
    public void resetBuffer() {
        this.buffer.setLength(0);
    }

    /* access modifiers changed from: package-private */
    public void enterPreviousActiveState() {
        setState(this.previousActiveState);
    }

    /* access modifiers changed from: package-private */
    public void enterCommentStartState() {
        saveActiveState();
        setState(this.commentStartState);
    }

    /* access modifiers changed from: package-private */
    public void enterCommentEndState() {
        setState(this.commendEndState);
    }

    /* access modifiers changed from: package-private */
    public void enterCommentInnerState() {
        setState(this.commendInnerState);
    }

    /* access modifiers changed from: package-private */
    public void enterRuleState() {
        setState(this.ruleState);
    }

    /* access modifiers changed from: package-private */
    public void enterUnknownStateIfNestedBlocksFinished() {
        if (this.nestedAtRules.size() == 0) {
            setState(this.unknownState);
        } else {
            enterRuleStateBasedOnItsType();
        }
    }

    /* access modifiers changed from: package-private */
    public void enterRuleStateBasedOnItsType() {
        if (currentAtRuleIsConditionalGroupRule()) {
            enterConditionalGroupAtRuleBlockState();
        } else {
            enterAtRuleBlockState();
        }
    }

    /* access modifiers changed from: package-private */
    public void enterUnknownState() {
        setState(this.unknownState);
    }

    /* access modifiers changed from: package-private */
    public void enterAtRuleBlockState() {
        setState(this.atRuleBlockState);
    }

    /* access modifiers changed from: package-private */
    public void enterConditionalGroupAtRuleBlockState() {
        setState(this.conditionalGroupAtRuleBlockState);
    }

    /* access modifiers changed from: package-private */
    public void enterPropertiesState() {
        setState(this.propertiesState);
    }

    /* access modifiers changed from: package-private */
    public void storeCurrentSelector() {
        this.currentSelector = this.buffer.toString();
        this.buffer.setLength(0);
    }

    /* access modifiers changed from: package-private */
    public void storeCurrentProperties() {
        if (this.isCurrentRuleSupported) {
            processProperties(this.currentSelector, this.buffer.toString());
        }
        this.currentSelector = null;
        this.buffer.setLength(0);
    }

    /* access modifiers changed from: package-private */
    public void storeCurrentPropertiesWithoutSelector() {
        if (this.isCurrentRuleSupported) {
            processProperties(this.buffer.toString());
        }
        this.buffer.setLength(0);
    }

    /* access modifiers changed from: package-private */
    public void storeSemicolonAtRule() {
        if (this.isCurrentRuleSupported) {
            processSemicolonAtRule(this.buffer.toString());
        }
        this.buffer.setLength(0);
    }

    /* access modifiers changed from: package-private */
    public void finishAtRuleBlock() {
        List<CssDeclaration> storedProps = this.storedPropertiesWithoutSelector.pop();
        CssNestedAtRule atRule = this.nestedAtRules.pop();
        if (this.isCurrentRuleSupported) {
            processFinishedAtRuleBlock(atRule);
            if (!storedProps.isEmpty()) {
                atRule.addBodyCssDeclarations(storedProps);
            }
        }
        this.isCurrentRuleSupported = isCurrentRuleSupported();
        this.buffer.setLength(0);
    }

    /* access modifiers changed from: package-private */
    public void pushBlockPrecedingAtRule() {
        this.nestedAtRules.push(CssNestedAtRuleFactory.createNestedRule(this.buffer.toString()));
        this.storedPropertiesWithoutSelector.push(new ArrayList());
        this.isCurrentRuleSupported = isCurrentRuleSupported();
        this.buffer.setLength(0);
    }

    private void saveActiveState() {
        this.previousActiveState = this.currentState;
    }

    private void setState(IParserState state) {
        this.currentState = state;
    }

    private void processProperties(String selector, String properties) {
        List<CssRuleSet> ruleSets = CssRuleSetParser.parseRuleSet(selector, properties);
        for (CssRuleSet ruleSet : ruleSets) {
            normalizeDeclarationURIs(ruleSet.getNormalDeclarations());
            normalizeDeclarationURIs(ruleSet.getImportantDeclarations());
        }
        for (CssRuleSet ruleSet2 : ruleSets) {
            if (this.nestedAtRules.size() == 0) {
                this.styleSheet.addStatement(ruleSet2);
            } else {
                this.nestedAtRules.peek().addStatementToBody(ruleSet2);
            }
        }
    }

    private void processProperties(String properties) {
        if (this.storedPropertiesWithoutSelector.size() > 0) {
            List<CssDeclaration> cssDeclarations = CssRuleSetParser.parsePropertyDeclarations(properties);
            normalizeDeclarationURIs(cssDeclarations);
            this.storedPropertiesWithoutSelector.peek().addAll(cssDeclarations);
        }
    }

    private void normalizeDeclarationURIs(List<CssDeclaration> declarations) {
        String strToAppend;
        if (this.uriResolver != null) {
            for (CssDeclaration declaration : declarations) {
                if (declaration.getExpression().contains("url(")) {
                    CssDeclarationValueTokenizer tokenizer = new CssDeclarationValueTokenizer(declaration.getExpression());
                    StringBuilder normalizedDeclaration = new StringBuilder();
                    while (true) {
                        CssDeclarationValueTokenizer.Token nextValidToken = tokenizer.getNextValidToken();
                        CssDeclarationValueTokenizer.Token token = nextValidToken;
                        if (nextValidToken == null) {
                            break;
                        }
                        if (token.getType() != CssDeclarationValueTokenizer.TokenType.FUNCTION || !token.getValue().startsWith("url(")) {
                            strToAppend = token.getValue();
                        } else {
                            String url = token.getValue().trim();
                            String url2 = url.substring(4, url.length() - 1).trim();
                            if (CssUtils.isBase64Data(url2)) {
                                strToAppend = token.getValue().trim();
                            } else {
                                if ((url2.startsWith("'") && url2.endsWith("'")) || (url2.startsWith("\"") && url2.endsWith("\""))) {
                                    url2 = url2.substring(1, url2.length() - 1);
                                }
                                String url3 = url2.trim();
                                String finalUrl = url3;
                                try {
                                    finalUrl = this.uriResolver.resolveAgainstBaseUri(url3).toExternalForm();
                                } catch (MalformedURLException e) {
                                }
                                strToAppend = MessageFormatUtil.format("url({0})", finalUrl);
                            }
                        }
                        if (normalizedDeclaration.length() > 0) {
                            normalizedDeclaration.append(' ');
                        }
                        normalizedDeclaration.append(strToAppend);
                    }
                    declaration.setExpression(normalizedDeclaration.toString());
                }
            }
        }
    }

    private void processSemicolonAtRule(String ruleStr) {
        this.styleSheet.addStatement(new CssSemicolonAtRule(ruleStr));
    }

    private void processFinishedAtRuleBlock(CssNestedAtRule atRule) {
        if (this.nestedAtRules.size() != 0) {
            this.nestedAtRules.peek().addStatementToBody(atRule);
        } else {
            this.styleSheet.addStatement(atRule);
        }
    }

    private boolean isCurrentRuleSupported() {
        boolean isSupported = this.nestedAtRules.isEmpty() || SUPPORTED_RULES.contains(this.nestedAtRules.peek().getRuleName());
        if (!isSupported) {
            LoggerFactory.getLogger(getClass()).error(MessageFormatUtil.format(LogMessageConstant.RULE_IS_NOT_SUPPORTED, this.nestedAtRules.peek().getRuleName()));
        }
        return isSupported;
    }

    private boolean currentAtRuleIsConditionalGroupRule() {
        return !this.isCurrentRuleSupported || (this.nestedAtRules.size() > 0 && CONDITIONAL_GROUP_RULES.contains(this.nestedAtRules.peek().getRuleName()));
    }
}
