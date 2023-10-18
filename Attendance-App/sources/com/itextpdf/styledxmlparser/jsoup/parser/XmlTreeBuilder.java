package com.itextpdf.styledxmlparser.jsoup.parser;

import com.itextpdf.styledxmlparser.jsoup.Jsoup;
import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
import com.itextpdf.styledxmlparser.jsoup.nodes.Comment;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.itextpdf.styledxmlparser.jsoup.nodes.DocumentType;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.jsoup.nodes.Node;
import com.itextpdf.styledxmlparser.jsoup.nodes.TextNode;
import com.itextpdf.styledxmlparser.jsoup.nodes.XmlDeclaration;
import com.itextpdf.styledxmlparser.jsoup.parser.Token;
import java.util.List;

public class XmlTreeBuilder extends TreeBuilder {
    /* access modifiers changed from: protected */
    public void initialiseParse(String input, String baseUri, ParseErrorList errors) {
        super.initialiseParse(input, baseUri, errors);
        this.stack.add(this.doc);
        this.doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
    }

    /* renamed from: com.itextpdf.styledxmlparser.jsoup.parser.XmlTreeBuilder$1 */
    static /* synthetic */ class C15871 {

        /* renamed from: $SwitchMap$com$itextpdf$styledxmlparser$jsoup$parser$Token$TokenType */
        static final /* synthetic */ int[] f1626xd695eb0c;

        static {
            int[] iArr = new int[Token.TokenType.values().length];
            f1626xd695eb0c = iArr;
            try {
                iArr[Token.TokenType.StartTag.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1626xd695eb0c[Token.TokenType.EndTag.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1626xd695eb0c[Token.TokenType.Comment.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1626xd695eb0c[Token.TokenType.Character.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1626xd695eb0c[Token.TokenType.Doctype.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1626xd695eb0c[Token.TokenType.EOF.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean process(Token token) {
        switch (C15871.f1626xd695eb0c[token.type.ordinal()]) {
            case 1:
                insert(token.asStartTag());
                return true;
            case 2:
                popStackToClose(token.asEndTag());
                return true;
            case 3:
                insert(token.asComment());
                return true;
            case 4:
                insert(token.asCharacter());
                return true;
            case 5:
                insert(token.asDoctype());
                return true;
            case 6:
                return true;
            default:
                Validate.fail("Unexpected token type: " + token.type);
                return true;
        }
    }

    private void insertNode(Node node) {
        currentElement().appendChild(node);
    }

    /* access modifiers changed from: package-private */
    public Element insert(Token.StartTag startTag) {
        Tag tag = Tag.valueOf(startTag.name());
        Element el = new Element(tag, this.baseUri, startTag.attributes);
        insertNode(el);
        if (startTag.isSelfClosing()) {
            this.tokeniser.acknowledgeSelfClosingFlag();
            if (!tag.isKnownTag()) {
                tag.setSelfClosing();
            }
        } else {
            this.stack.add(el);
        }
        return el;
    }

    /* access modifiers changed from: package-private */
    public void insert(Token.Comment commentToken) {
        Comment comment = new Comment(commentToken.getData(), this.baseUri);
        Node insert = comment;
        if (commentToken.bogus) {
            String data = comment.getData();
            if (data.length() > 1 && (data.startsWith("!") || data.startsWith("?"))) {
                Element el = Jsoup.parse("<" + data.substring(1, data.length() - 1) + ">", this.baseUri, Parser.xmlParser()).child(0);
                insert = new XmlDeclaration(el.tagName(), comment.baseUri(), data.startsWith("!"));
                insert.attributes().addAll(el.attributes());
            }
        }
        insertNode(insert);
    }

    /* access modifiers changed from: package-private */
    public void insert(Token.Character characterToken) {
        insertNode(new TextNode(characterToken.getData(), this.baseUri));
    }

    /* access modifiers changed from: package-private */
    public void insert(Token.Doctype d) {
        insertNode(new DocumentType(d.getName(), d.getPublicIdentifier(), d.getSystemIdentifier(), this.baseUri));
    }

    private void popStackToClose(Token.EndTag endTag) {
        String elName = endTag.name();
        Element firstFound = null;
        int pos = this.stack.size() - 1;
        while (true) {
            if (pos < 0) {
                break;
            }
            Element next = (Element) this.stack.get(pos);
            if (next.nodeName().equals(elName)) {
                firstFound = next;
                break;
            }
            pos--;
        }
        if (firstFound != null) {
            int pos2 = this.stack.size() - 1;
            while (pos2 >= 0) {
                Element next2 = (Element) this.stack.get(pos2);
                this.stack.remove(pos2);
                if (next2 != firstFound) {
                    pos2--;
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public List<Node> parseFragment(String inputFragment, String baseUri, ParseErrorList errors) {
        initialiseParse(inputFragment, baseUri, errors);
        runParser();
        return this.doc.childNodes();
    }
}
