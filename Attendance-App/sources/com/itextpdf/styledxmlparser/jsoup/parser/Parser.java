package com.itextpdf.styledxmlparser.jsoup.parser;

import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.jsoup.nodes.Node;
import java.util.List;

public class Parser {
    private static final int DEFAULT_MAX_ERRORS = 0;
    private ParseErrorList errors;
    private int maxErrors = 0;
    private TreeBuilder treeBuilder;

    public Parser(TreeBuilder treeBuilder2) {
        this.treeBuilder = treeBuilder2;
    }

    public Document parseInput(String html, String baseUri) {
        ParseErrorList tracking = isTrackErrors() ? ParseErrorList.tracking(this.maxErrors) : ParseErrorList.noTracking();
        this.errors = tracking;
        return this.treeBuilder.parse(html, baseUri, tracking);
    }

    public TreeBuilder getTreeBuilder() {
        return this.treeBuilder;
    }

    public Parser setTreeBuilder(TreeBuilder treeBuilder2) {
        this.treeBuilder = treeBuilder2;
        return this;
    }

    public boolean isTrackErrors() {
        return this.maxErrors > 0;
    }

    public Parser setTrackErrors(int maxErrors2) {
        this.maxErrors = maxErrors2;
        return this;
    }

    public List<ParseError> getErrors() {
        return this.errors;
    }

    public static Document parse(String html, String baseUri) {
        return new HtmlTreeBuilder().parse(html, baseUri, ParseErrorList.noTracking());
    }

    public static Document parseXml(String xml, String baseUri) {
        return new XmlTreeBuilder().parse(xml, baseUri, ParseErrorList.noTracking());
    }

    public static List<Node> parseFragment(String fragmentHtml, Element context, String baseUri) {
        return new HtmlTreeBuilder().parseFragment(fragmentHtml, context, baseUri, ParseErrorList.noTracking());
    }

    public static List<Node> parseXmlFragment(String fragmentXml, String baseUri) {
        return new XmlTreeBuilder().parseFragment(fragmentXml, baseUri, ParseErrorList.noTracking());
    }

    public static Document parseBodyFragment(String bodyHtml, String baseUri) {
        Document doc = Document.createShell(baseUri);
        Element body = doc.body();
        List<Node> nodeList = parseFragment(bodyHtml, body, baseUri);
        Node[] nodes = (Node[]) nodeList.toArray(new Node[nodeList.size()]);
        for (int i = nodes.length - 1; i > 0; i--) {
            nodes[i].remove();
        }
        for (Node node : nodes) {
            body.appendChild(node);
        }
        return doc;
    }

    public static String unescapeEntities(String string, boolean inAttribute) {
        return new Tokeniser(new CharacterReader(string), ParseErrorList.noTracking()).unescapeEntities(inAttribute);
    }

    public static Document parseBodyFragmentRelaxed(String bodyHtml, String baseUri) {
        return parse(bodyHtml, baseUri);
    }

    public static Parser htmlParser() {
        return new Parser(new HtmlTreeBuilder());
    }

    public static Parser xmlParser() {
        return new Parser(new XmlTreeBuilder());
    }
}
