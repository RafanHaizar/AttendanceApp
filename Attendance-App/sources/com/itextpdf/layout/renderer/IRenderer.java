package com.itextpdf.layout.renderer;

import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import java.util.List;

public interface IRenderer extends IPropertyContainer {
    void addChild(IRenderer iRenderer);

    void draw(DrawContext drawContext);

    List<IRenderer> getChildRenderers();

    IPropertyContainer getModelElement();

    IRenderer getNextRenderer();

    LayoutArea getOccupiedArea();

    IRenderer getParent();

    <T1> T1 getProperty(int i, T1 t1);

    boolean isFlushed();

    LayoutResult layout(LayoutContext layoutContext);

    void move(float f, float f2);

    IRenderer setParent(IRenderer iRenderer);
}
