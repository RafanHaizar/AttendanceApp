package com.itextpdf.styledxmlparser.css.parse.syntax;

class RuleState implements IParserState {
    private CssParserStateController controller;

    RuleState(CssParserStateController controller2) {
        this.controller = controller2;
    }

    public void process(char ch) {
        if (ch == '{') {
            this.controller.pushBlockPrecedingAtRule();
            this.controller.enterRuleStateBasedOnItsType();
        } else if (ch == ';') {
            this.controller.storeSemicolonAtRule();
            this.controller.enterUnknownState();
        } else {
            this.controller.appendToBuffer(ch);
        }
    }
}
