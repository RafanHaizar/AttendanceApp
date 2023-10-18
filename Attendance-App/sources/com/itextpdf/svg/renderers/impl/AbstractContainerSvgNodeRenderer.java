package com.itextpdf.svg.renderers.impl;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.styledxmlparser.css.resolve.CssDefaults;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.svg.SvgConstants;
import com.itextpdf.svg.renderers.SvgDrawContext;

public abstract class AbstractContainerSvgNodeRenderer extends AbstractBranchSvgNodeRenderer {
    public float getCurrentFontSize() {
        String fontSizeValue = getAttribute("font-size");
        if (fontSizeValue == null) {
            fontSizeValue = CssDefaults.getDefaultValue("font-size");
        }
        return CssUtils.parseAbsoluteFontSize(fontSizeValue);
    }

    public boolean canConstructViewPort() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean canElementFill() {
        return false;
    }

    /* access modifiers changed from: protected */
    public void doDraw(SvgDrawContext context) {
        context.addViewPort(calculateViewPort(context));
        super.doDraw(context);
    }

    /* access modifiers changed from: package-private */
    public Rectangle calculateViewPort(SvgDrawContext context) {
        Rectangle currentViewPort = context.getCurrentViewPort();
        float portX = currentViewPort.getX();
        float portY = currentViewPort.getY();
        float portWidth = currentViewPort.getWidth();
        float portHeight = currentViewPort.getHeight();
        if (this.attributesAndStyles != null) {
            if (this.attributesAndStyles.containsKey(SvgConstants.Attributes.f1641X)) {
                portX = CssUtils.parseAbsoluteLength((String) this.attributesAndStyles.get(SvgConstants.Attributes.f1641X));
            }
            if (this.attributesAndStyles.containsKey(SvgConstants.Attributes.f1644Y)) {
                portY = CssUtils.parseAbsoluteLength((String) this.attributesAndStyles.get(SvgConstants.Attributes.f1644Y));
            }
            if (this.attributesAndStyles.containsKey("width")) {
                portWidth = CssUtils.parseAbsoluteLength((String) this.attributesAndStyles.get("width"));
            }
            if (this.attributesAndStyles.containsKey("height")) {
                portHeight = CssUtils.parseAbsoluteLength((String) this.attributesAndStyles.get("height"));
            }
        }
        return new Rectangle(portX, portY, portWidth, portHeight);
    }
}
