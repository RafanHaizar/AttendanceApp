package com.itextpdf.layout.element;

import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.renderer.IRenderer;

public interface IElement extends IPropertyContainer {
    IRenderer createRendererSubTree();

    IRenderer getRenderer();

    void setNextRenderer(IRenderer iRenderer);
}
