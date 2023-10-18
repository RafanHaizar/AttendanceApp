package com.itextpdf.styledxmlparser.jsoup.nodes;

import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import java.io.IOException;

public class Comment extends Node {
    private static final String COMMENT_KEY = "comment";

    public Comment(String data, String baseUri) {
        super(baseUri);
        this.attributes.put(COMMENT_KEY, data);
    }

    public String nodeName() {
        return "#comment";
    }

    public String getData() {
        return this.attributes.get(COMMENT_KEY);
    }

    /* access modifiers changed from: package-private */
    public void outerHtmlHead(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
        if (out.prettyPrint()) {
            indent(accum, depth, out);
        }
        accum.append("<!--").append(getData()).append("-->");
    }

    /* access modifiers changed from: package-private */
    public void outerHtmlTail(Appendable accum, int depth, Document.OutputSettings out) {
    }

    public String toString() {
        return outerHtml();
    }
}
