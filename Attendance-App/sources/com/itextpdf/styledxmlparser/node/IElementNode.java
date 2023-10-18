package com.itextpdf.styledxmlparser.node;

import java.util.List;
import java.util.Map;

public interface IElementNode extends INode, IStylesContainer {
    void addAdditionalHtmlStyles(Map<String, String> map);

    List<Map<String, String>> getAdditionalHtmlStyles();

    String getAttribute(String str);

    IAttributes getAttributes();

    String getLang();

    String name();
}
