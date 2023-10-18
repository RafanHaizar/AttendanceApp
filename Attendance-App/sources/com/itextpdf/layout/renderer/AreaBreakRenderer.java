package com.itextpdf.layout.renderer;

import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import java.util.List;

public class AreaBreakRenderer implements IRenderer {
    protected AreaBreak areaBreak;

    public AreaBreakRenderer(AreaBreak areaBreak2) {
        this.areaBreak = areaBreak2;
    }

    public void addChild(IRenderer renderer) {
        throw new RuntimeException();
    }

    public LayoutResult layout(LayoutContext layoutContext) {
        return new LayoutResult(3, (LayoutArea) null, (IRenderer) null, (IRenderer) null, this).setAreaBreak(this.areaBreak);
    }

    public void draw(DrawContext drawContext) {
        throw new UnsupportedOperationException();
    }

    public LayoutArea getOccupiedArea() {
        throw new UnsupportedOperationException();
    }

    public boolean hasProperty(int property) {
        return false;
    }

    public boolean hasOwnProperty(int property) {
        return false;
    }

    public <T1> T1 getProperty(int key) {
        return null;
    }

    public <T1> T1 getOwnProperty(int property) {
        return null;
    }

    public <T1> T1 getDefaultProperty(int property) {
        return null;
    }

    public <T1> T1 getProperty(int property, T1 t1) {
        throw new UnsupportedOperationException();
    }

    public void setProperty(int property, Object value) {
        throw new UnsupportedOperationException();
    }

    public void deleteOwnProperty(int property) {
    }

    public IRenderer setParent(IRenderer parent) {
        return this;
    }

    public IPropertyContainer getModelElement() {
        return null;
    }

    public IRenderer getParent() {
        return null;
    }

    public List<IRenderer> getChildRenderers() {
        return null;
    }

    public boolean isFlushed() {
        return false;
    }

    public void move(float dx, float dy) {
        throw new UnsupportedOperationException();
    }

    public IRenderer getNextRenderer() {
        return null;
    }
}
