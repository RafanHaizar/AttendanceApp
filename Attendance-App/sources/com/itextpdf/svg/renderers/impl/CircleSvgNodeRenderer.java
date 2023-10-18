package com.itextpdf.svg.renderers.impl;

import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.svg.SvgConstants;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;

public class CircleSvgNodeRenderer extends EllipseSvgNodeRenderer {
    /* access modifiers changed from: protected */
    public boolean setParameters() {
        this.f1650cx = 0.0f;
        this.f1651cy = 0.0f;
        if (getAttribute(SvgConstants.Attributes.f1632CX) != null) {
            this.f1650cx = CssUtils.parseAbsoluteLength(getAttribute(SvgConstants.Attributes.f1632CX));
        }
        if (getAttribute(SvgConstants.Attributes.f1633CY) != null) {
            this.f1651cy = CssUtils.parseAbsoluteLength(getAttribute(SvgConstants.Attributes.f1633CY));
        }
        if (getAttribute("r") == null || CssUtils.parseAbsoluteLength(getAttribute("r")) <= 0.0f) {
            return false;
        }
        this.f1652rx = CssUtils.parseAbsoluteLength(getAttribute("r"));
        this.f1653ry = this.f1652rx;
        return true;
    }

    public ISvgNodeRenderer createDeepCopy() {
        CircleSvgNodeRenderer copy = new CircleSvgNodeRenderer();
        deepCopyAttributesAndStyles(copy);
        return copy;
    }
}
