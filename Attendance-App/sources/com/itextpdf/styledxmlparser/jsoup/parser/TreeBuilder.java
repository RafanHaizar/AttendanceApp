package com.itextpdf.styledxmlparser.jsoup.parser;

import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
import com.itextpdf.styledxmlparser.jsoup.nodes.Attributes;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.jsoup.parser.Token;
import java.util.ArrayList;

public abstract class TreeBuilder {
    protected String baseUri;
    Token currentToken;
    protected Document doc;
    private Token.EndTag end = new Token.EndTag();
    ParseErrorList errors;
    CharacterReader reader;
    protected ArrayList<Element> stack;
    private Token.StartTag start = new Token.StartTag();
    Tokeniser tokeniser;

    /* access modifiers changed from: package-private */
    public abstract boolean process(Token token);

    /* access modifiers changed from: package-private */
    public void initialiseParse(String input, String baseUri2, ParseErrorList errors2) {
        Validate.notNull(input, "String input must not be null");
        Validate.notNull(baseUri2, "BaseURI must not be null");
        this.doc = new Document(baseUri2);
        this.reader = new CharacterReader(input);
        this.errors = errors2;
        this.tokeniser = new Tokeniser(this.reader, errors2);
        this.stack = new ArrayList<>(32);
        this.baseUri = baseUri2;
    }

    /* access modifiers changed from: package-private */
    public Document parse(String input, String baseUri2) {
        return parse(input, baseUri2, ParseErrorList.noTracking());
    }

    /* access modifiers changed from: package-private */
    public Document parse(String input, String baseUri2, ParseErrorList errors2) {
        initialiseParse(input, baseUri2, errors2);
        runParser();
        return this.doc;
    }

    /* access modifiers changed from: protected */
    public void runParser() {
        Token token;
        do {
            token = this.tokeniser.read();
            process(token);
            token.reset();
        } while (token.type != Token.TokenType.EOF);
    }

    /* access modifiers changed from: protected */
    public boolean processStartTag(String name) {
        Token token = this.currentToken;
        Token.StartTag startTag = this.start;
        if (token == startTag) {
            return process(new Token.StartTag().name(name));
        }
        return process(((Token.Tag) startTag.reset()).name(name));
    }

    public boolean processStartTag(String name, Attributes attrs) {
        Token token = this.currentToken;
        Token.StartTag startTag = this.start;
        if (token == startTag) {
            return process(new Token.StartTag().nameAttr(name, attrs));
        }
        startTag.reset();
        this.start.nameAttr(name, attrs);
        return process(this.start);
    }

    /* access modifiers changed from: protected */
    public boolean processEndTag(String name) {
        Token token = this.currentToken;
        Token.EndTag endTag = this.end;
        if (token == endTag) {
            return process(new Token.EndTag().name(name));
        }
        return process(((Token.Tag) endTag.reset()).name(name));
    }

    /* access modifiers changed from: protected */
    public Element currentElement() {
        int size = this.stack.size();
        if (size > 0) {
            return this.stack.get(size - 1);
        }
        return null;
    }
}
