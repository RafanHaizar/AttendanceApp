package com.itextpdf.styledxmlparser.css.parse.syntax;

class PropertiesState implements IParserState {
    private CssParserStateController controller;

    PropertiesState(CssParserStateController controller2) {
        this.controller = controller2;
    }

    public void process(char ch) {
        if (ch == '}') {
            this.controller.storeCurrentProperties();
            this.controller.enterUnknownStateIfNestedBlocksFinished();
        } else if (ch == '/') {
            this.controller.enterCommentStartState();
        } else {
            this.controller.appendToBuffer(ch);
        }
    }
}
