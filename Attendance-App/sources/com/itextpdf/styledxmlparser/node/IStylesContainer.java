package com.itextpdf.styledxmlparser.node;

import java.util.Map;

public interface IStylesContainer {
    Map<String, String> getStyles();

    void setStyles(Map<String, String> map);
}
