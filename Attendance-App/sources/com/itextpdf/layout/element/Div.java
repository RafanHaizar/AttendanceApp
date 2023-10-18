package com.itextpdf.layout.element;

import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties;
import com.itextpdf.kernel.pdf.tagutils.DefaultAccessibilityProperties;
import com.itextpdf.layout.renderer.DivRenderer;
import com.itextpdf.layout.renderer.IRenderer;

public class Div extends BlockElement<Div> {
    protected DefaultAccessibilityProperties tagProperties;

    public Div add(IBlockElement element) {
        this.childElements.add(element);
        return this;
    }

    public Div add(Image element) {
        this.childElements.add(element);
        return this;
    }

    public Div add(AreaBreak areaBreak) {
        this.childElements.add(areaBreak);
        return this;
    }

    public AccessibilityProperties getAccessibilityProperties() {
        if (this.tagProperties == null) {
            this.tagProperties = new DefaultAccessibilityProperties(StandardRoles.DIV);
        }
        return this.tagProperties;
    }

    public Div setFillAvailableArea(boolean fillArea) {
        setProperty(86, Boolean.valueOf(fillArea));
        return this;
    }

    public Div setFillAvailableAreaOnSplit(boolean fillAreaOnSplit) {
        setProperty(87, Boolean.valueOf(fillAreaOnSplit));
        return this;
    }

    /* access modifiers changed from: protected */
    public IRenderer makeNewRenderer() {
        return new DivRenderer(this);
    }
}
