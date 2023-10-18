package com.itextpdf.styledxmlparser.node.impl.jsoup.node;

import com.itextpdf.styledxmlparser.jsoup.nodes.DataNode;
import com.itextpdf.styledxmlparser.node.IDataNode;

public class JsoupDataNode extends JsoupNode implements IDataNode {
    private DataNode dataNode;

    public JsoupDataNode(DataNode dataNode2) {
        super(dataNode2);
        this.dataNode = dataNode2;
    }

    public String getWholeData() {
        return this.dataNode.getWholeData();
    }
}
