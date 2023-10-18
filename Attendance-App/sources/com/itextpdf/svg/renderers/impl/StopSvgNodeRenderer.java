package com.itextpdf.svg.renderers.impl;

import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.svg.SvgConstants;
import com.itextpdf.svg.exceptions.SvgLogMessageConstant;
import com.itextpdf.svg.renderers.INoDrawSvgNodeRenderer;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.SvgDrawContext;

public class StopSvgNodeRenderer extends NoDrawOperationSvgNodeRenderer implements INoDrawSvgNodeRenderer {
    public double getOffset() {
        Double offset = null;
        String offsetAttribute = getAttribute("offset");
        if (CssUtils.isPercentageValue(offsetAttribute)) {
            offset = Double.valueOf((double) CssUtils.parseRelativeValue(offsetAttribute, 1.0f));
        } else if (CssUtils.isNumericValue(offsetAttribute)) {
            offset = CssUtils.parseDouble(offsetAttribute);
        }
        double result = offset != null ? offset.doubleValue() : 0.0d;
        if (result > 1.0d) {
            return 1.0d;
        }
        if (result > 0.0d) {
            return result;
        }
        return 0.0d;
    }

    public float[] getStopColor() {
        float[] color = null;
        String colorValue = getAttribute(SvgConstants.Tags.STOP_COLOR);
        if (colorValue != null) {
            color = WebColors.getRGBAColor(colorValue);
        }
        if (color == null) {
            return WebColors.getRGBAColor("black");
        }
        return color;
    }

    public float getStopOpacity() {
        Float result = null;
        String opacityValue = getAttribute(SvgConstants.Tags.STOP_OPACITY);
        if (opacityValue != null && !"none".equalsIgnoreCase(opacityValue)) {
            result = CssUtils.parseFloat(opacityValue);
        }
        if (result != null) {
            return result.floatValue();
        }
        return 1.0f;
    }

    public ISvgNodeRenderer createDeepCopy() {
        StopSvgNodeRenderer copy = new StopSvgNodeRenderer();
        deepCopyAttributesAndStyles(copy);
        return copy;
    }

    /* access modifiers changed from: protected */
    public void doDraw(SvgDrawContext context) {
        throw new UnsupportedOperationException(SvgLogMessageConstant.DRAW_NO_DRAW);
    }
}
