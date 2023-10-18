package com.itextpdf.layout.element;

import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.TabRenderer;

public class Tab extends AbstractElement<Tab> implements ILeafElement {
    /* access modifiers changed from: protected */
    public IRenderer makeNewRenderer() {
        return new TabRenderer(this);
    }
}
