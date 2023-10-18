package com.itextpdf.styledxmlparser.css.parse.syntax;

class CommentEndState implements IParserState {
    private CssParserStateController controller;

    CommentEndState(CssParserStateController controller2) {
        this.controller = controller2;
    }

    public void process(char ch) {
        if (ch == '/') {
            this.controller.enterPreviousActiveState();
        } else if (ch != '*') {
            this.controller.enterCommentInnerState();
        }
    }
}
