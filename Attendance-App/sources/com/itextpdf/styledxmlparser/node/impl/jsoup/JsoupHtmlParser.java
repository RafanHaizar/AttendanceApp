package com.itextpdf.styledxmlparser.node.impl.jsoup;

import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.IXmlParser;
import com.itextpdf.styledxmlparser.LogMessageConstant;
import com.itextpdf.styledxmlparser.jsoup.Jsoup;
import com.itextpdf.styledxmlparser.jsoup.nodes.Comment;
import com.itextpdf.styledxmlparser.jsoup.nodes.DataNode;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.itextpdf.styledxmlparser.jsoup.nodes.DocumentType;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.jsoup.nodes.Node;
import com.itextpdf.styledxmlparser.jsoup.nodes.TextNode;
import com.itextpdf.styledxmlparser.node.IDocumentNode;
import com.itextpdf.styledxmlparser.node.INode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupDataNode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupDocumentNode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupDocumentTypeNode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupElementNode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupTextNode;
import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsoupHtmlParser implements IXmlParser {
    private static Logger logger = LoggerFactory.getLogger((Class<?>) JsoupHtmlParser.class);

    public IDocumentNode parse(InputStream htmlStream, String charset) throws IOException {
        INode result = wrapJsoupHierarchy(Jsoup.parse(htmlStream, charset, ""));
        if (result instanceof IDocumentNode) {
            return (IDocumentNode) result;
        }
        throw new IllegalStateException();
    }

    public IDocumentNode parse(String html) {
        INode result = wrapJsoupHierarchy(Jsoup.parse(html));
        if (result instanceof IDocumentNode) {
            return (IDocumentNode) result;
        }
        throw new IllegalStateException();
    }

    private INode wrapJsoupHierarchy(Node jsoupNode) {
        INode resultNode = null;
        if (jsoupNode instanceof Document) {
            resultNode = new JsoupDocumentNode((Document) jsoupNode);
        } else if (jsoupNode instanceof TextNode) {
            resultNode = new JsoupTextNode((TextNode) jsoupNode);
        } else if (jsoupNode instanceof Element) {
            resultNode = new JsoupElementNode((Element) jsoupNode);
        } else if (jsoupNode instanceof DataNode) {
            resultNode = new JsoupDataNode((DataNode) jsoupNode);
        } else if (jsoupNode instanceof DocumentType) {
            resultNode = new JsoupDocumentTypeNode((DocumentType) jsoupNode);
        } else if (!(jsoupNode instanceof Comment)) {
            logger.error(MessageFormatUtil.format(LogMessageConstant.ERROR_PARSING_COULD_NOT_MAP_NODE, jsoupNode.getClass()));
        }
        for (Node node : jsoupNode.childNodes()) {
            INode childNode = wrapJsoupHierarchy(node);
            if (childNode != null) {
                resultNode.addChild(childNode);
            }
        }
        return resultNode;
    }
}
