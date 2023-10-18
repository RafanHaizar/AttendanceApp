package com.itextpdf.styledxmlparser.css.parse.syntax;

class CommentStartState implements IParserState {
    private CssParserStateController controller;

    CommentStartState(CssParserStateController controller2) {
        this.controller = controller2;
    }

    public void process(char ch) {
        if (ch == '*') {
            this.controller.enterCommentInnerState();
            return;
        }
        this.controller.appendToBuffer('/');
        this.controller.appendToBuffer(ch);
        this.controller.enterPreviousActiveState();
    }
}
