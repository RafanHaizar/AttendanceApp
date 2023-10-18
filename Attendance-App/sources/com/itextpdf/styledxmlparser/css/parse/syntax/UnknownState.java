package com.itextpdf.styledxmlparser.css.parse.syntax;

class UnknownState implements IParserState {
    private CssParserStateController controller;

    UnknownState(CssParserStateController controller2) {
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
        } else if ((ch == '-' && this.controller.getBufferContents().endsWith("<!-")) || (ch == '>' && this.controller.getBufferContents().endsWith("--"))) {
            this.controller.resetBuffer();
        } else if ((ch != '[' || !this.controller.getBufferContents().endsWith("<![CDATA")) && (ch != '>' || !this.controller.getBufferContents().endsWith("]]"))) {
            this.controller.appendToBuffer(ch);
        } else {
            this.controller.resetBuffer();
        }
    }
}
