package com.itextpdf.styledxmlparser;

import com.itextpdf.styledxmlparser.node.IDocumentNode;
import java.io.IOException;
import java.io.InputStream;

public interface IXmlParser {
    IDocumentNode parse(InputStream inputStream, String str) throws IOException;

    IDocumentNode parse(String str);
}
