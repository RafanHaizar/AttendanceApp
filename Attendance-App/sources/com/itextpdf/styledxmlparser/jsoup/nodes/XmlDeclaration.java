package com.itextpdf.styledxmlparser.jsoup.nodes;

import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import java.io.IOException;

public class XmlDeclaration extends Node {
    private final boolean isProcessingInstruction;
    private final String name;

    public XmlDeclaration(String name2, String baseUri, boolean isProcessingInstruction2) {
        super(baseUri);
        Validate.notNull(name2);
        this.name = name2;
        this.isProcessingInstruction = isProcessingInstruction2;
    }

    public String nodeName() {
        return "#declaration";
    }

    public String name() {
        return this.name;
    }

    public String getWholeDeclaration() {
        return this.attributes.html().trim();
    }

    /* access modifiers changed from: package-private */
    public void outerHtmlHead(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
        String str = "!";
        accum.append("<").append(this.isProcessingInstruction ? str : "?").append(this.name);
        this.attributes.html(accum, out);
        if (!this.isProcessingInstruction) {
            str = "?";
        }
        accum.append(str).append(">");
    }

    /* access modifiers changed from: package-private */
    public void outerHtmlTail(Appendable accum, int depth, Document.OutputSettings out) {
    }

    public String toString() {
        return outerHtml();
    }
}
