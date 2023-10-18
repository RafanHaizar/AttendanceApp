package com.itextpdf.styledxmlparser.css.parse.syntax;

class AtRuleBlockState implements IParserState {
    private CssParserStateController controller;

    AtRuleBlockState(CssParserStateController controller2) {
        this.controller = controller2;
    }

    public void process(char ch) {
        if (ch == '/') {
            this.controller.enterCommentStartState();
        } else if (ch == '@') {
            this.controller.storeCurrentPropertiesWithoutSelector();
            this.controller.enterRuleState();
        } else if (ch == '}') {
            this.controller.storeCurrentPropertiesWithoutSelector();
            this.controller.finishAtRuleBlock();
            this.controller.enterUnknownStateIfNestedBlocksFinished();
        } else {
            this.controller.appendToBuffer(ch);
        }
    }
}
