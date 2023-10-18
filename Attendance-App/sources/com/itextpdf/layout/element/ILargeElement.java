package com.itextpdf.layout.element;

import com.itextpdf.layout.Document;

public interface ILargeElement extends IElement {
    void complete();

    void flush();

    void flushContent();

    boolean isComplete();

    void setDocument(Document document);
}
