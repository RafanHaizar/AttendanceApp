package com.itextpdf.styledxmlparser.css.parse.syntax;

class CommentInnerState implements IParserState {
    private CssParserStateController controller;

    CommentInnerState(CssParserStateController controller2) {
        this.controller = controller2;
    }

    public void process(char ch) {
        if (ch == '*') {
            this.controller.enterCommentEndState();
        }
    }
}
