package com.itextpdf.styledxmlparser.node;

public interface IAttributes extends Iterable<IAttribute> {
    String getAttribute(String str);

    void setAttribute(String str, String str2);

    int size();
}
