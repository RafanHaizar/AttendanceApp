package com.itextpdf.styledxmlparser.jsoup.nodes;

import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import java.io.IOException;

public class DataNode extends Node {
    private static final String DATA_KEY = "data";

    public DataNode(String data, String baseUri) {
        super(baseUri);
        this.attributes.put(DATA_KEY, data);
    }

    public String nodeName() {
        return "#data";
    }

    public String getWholeData() {
        return this.attributes.get(DATA_KEY);
    }

    public DataNode setWholeData(String data) {
        this.attributes.put(DATA_KEY, data);
        return this;
    }

    /* access modifiers changed from: package-private */
    public void outerHtmlHead(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
        accum.append(getWholeData());
    }

    /* access modifiers changed from: package-private */
    public void outerHtmlTail(Appendable accum, int depth, Document.OutputSettings out) {
    }

    public String toString() {
        return outerHtml();
    }

    public static DataNode createFromEncoded(String encodedData, String baseUri) {
        return new DataNode(Entities.unescape(encodedData), baseUri);
    }
}
