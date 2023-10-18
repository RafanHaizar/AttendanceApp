package com.itextpdf.layout.element;

import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.ElementPropertyContainer;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.renderer.IRenderer;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractElement<T extends IElement> extends ElementPropertyContainer<T> implements IElement {
    protected List<IElement> childElements = new ArrayList();
    protected IRenderer nextRenderer;
    protected Set<Style> styles;

    /* access modifiers changed from: protected */
    public abstract IRenderer makeNewRenderer();

    public IRenderer getRenderer() {
        IRenderer iRenderer = this.nextRenderer;
        if (iRenderer == null) {
            return makeNewRenderer();
        }
        IRenderer renderer = this.nextRenderer;
        this.nextRenderer = iRenderer.getNextRenderer();
        return renderer;
    }

    public void setNextRenderer(IRenderer renderer) {
        this.nextRenderer = renderer;
    }

    public IRenderer createRendererSubTree() {
        IRenderer rendererRoot = getRenderer();
        for (IElement child : this.childElements) {
            rendererRoot.addChild(child.createRendererSubTree());
        }
        return rendererRoot;
    }

    public boolean hasProperty(int property) {
        boolean hasProperty = super.hasProperty(property);
        Set<Style> set = this.styles;
        if (set == null || set.size() <= 0 || hasProperty) {
            return hasProperty;
        }
        for (Style style : this.styles) {
            if (style.hasProperty(property)) {
                return true;
            }
        }
        return hasProperty;
    }

    public <T1> T1 getProperty(int property) {
        Object result = super.getProperty(property);
        Set<Style> set = this.styles;
        if (set != null && set.size() > 0 && result == null && !super.hasProperty(property)) {
            for (Style style : this.styles) {
                Object foundInStyle = style.getProperty(property);
                if (foundInStyle != null || style.hasProperty(property)) {
                    result = foundInStyle;
                }
            }
        }
        return result;
    }

    public T addStyle(Style style) {
        if (this.styles == null) {
            this.styles = new LinkedHashSet();
        }
        this.styles.add(style);
        return this;
    }

    public List<IElement> getChildren() {
        return this.childElements;
    }

    public boolean isEmpty() {
        return this.childElements.size() == 0;
    }

    public T setAction(PdfAction action) {
        setProperty(1, action);
        return this;
    }

    public T setPageNumber(int pageNumber) {
        setProperty(51, Integer.valueOf(pageNumber));
        return this;
    }
}
