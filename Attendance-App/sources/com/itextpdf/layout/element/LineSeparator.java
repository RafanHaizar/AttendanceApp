package com.itextpdf.layout.element;

import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties;
import com.itextpdf.kernel.pdf.tagutils.DefaultAccessibilityProperties;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.LineSeparatorRenderer;

public class LineSeparator extends BlockElement<LineSeparator> {
    protected DefaultAccessibilityProperties tagProperties;

    public LineSeparator(ILineDrawer lineDrawer) {
        setProperty(35, lineDrawer);
    }

    public AccessibilityProperties getAccessibilityProperties() {
        if (this.tagProperties == null) {
            this.tagProperties = new DefaultAccessibilityProperties(StandardRoles.ARTIFACT);
        }
        return this.tagProperties;
    }

    /* access modifiers changed from: protected */
    public IRenderer makeNewRenderer() {
        return new LineSeparatorRenderer(this);
    }
}
