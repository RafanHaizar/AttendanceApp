package com.itextpdf.styledxmlparser.css.parse.syntax;

class ConditionalGroupAtRuleBlockState implements IParserState {
    private CssParserStateController controller;

    ConditionalGroupAtRuleBlockState(CssParserStateController controller2) {
        this.controller = controller2;
    }

    public void process(char ch) {
        if (ch == '/') {
            this.controller.enterCommentStartState();
        } else if (ch == '@') {
            this.controller.enterRuleState();
        } else if (ch == '{') {
            this.controller.storeCurrentSelector();
            this.controller.enterPropertiesState();
        } else if (ch == '}') {
            this.controller.finishAtRuleBlock();
            this.controller.enterUnknownStateIfNestedBlocksFinished();
        } else {
            this.controller.appendToBuffer(ch);
        }
    }
}
