package com.itextpdf.layout.minmaxwidth;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.MessageFormatUtil;
import org.slf4j.LoggerFactory;

public final class MinMaxWidthUtils {
    private static final float eps = 0.01f;
    private static final float max = 32760.0f;

    public static float getEps() {
        return eps;
    }

    public static float getInfWidth() {
        return max;
    }

    private static float getInfHeight() {
        return 1000000.0f;
    }

    public static boolean isEqual(double x, double y) {
        return Math.abs(x - y) < 0.009999999776482582d;
    }

    public static MinMaxWidth countDefaultMinMaxWidth(IRenderer renderer) {
        LayoutResult result = renderer.layout(new LayoutContext(new LayoutArea(1, new Rectangle(getInfWidth(), getInfHeight()))));
        if (result.getStatus() == 3) {
            return new MinMaxWidth();
        }
        return new MinMaxWidth(0.0f, result.getOccupiedArea().getBBox().getWidth(), 0.0f);
    }

    public static float getBorderWidth(IPropertyContainer element) {
        Border border = (Border) element.getProperty(9);
        Border rightBorder = (Border) element.getProperty(12);
        Border leftBorder = (Border) element.getProperty(11);
        if (!element.hasOwnProperty(12)) {
            rightBorder = border;
        }
        if (!element.hasOwnProperty(11)) {
            leftBorder = border;
        }
        float leftBorderWidth = 0.0f;
        float rightBorderWidth = rightBorder != null ? rightBorder.getWidth() : 0.0f;
        if (leftBorder != null) {
            leftBorderWidth = leftBorder.getWidth();
        }
        return rightBorderWidth + leftBorderWidth;
    }

    public static float getMarginsWidth(IPropertyContainer element) {
        UnitValue rightMargin = (UnitValue) element.getProperty(45);
        Class<MinMaxWidthUtils> cls = MinMaxWidthUtils.class;
        if (rightMargin != null && !rightMargin.isPointValue()) {
            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 45));
        }
        UnitValue leftMargin = (UnitValue) element.getProperty(44);
        if (leftMargin != null && !leftMargin.isPointValue()) {
            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 44));
        }
        float leftMarginWidth = 0.0f;
        float rightMarginWidth = rightMargin != null ? rightMargin.getValue() : 0.0f;
        if (leftMargin != null) {
            leftMarginWidth = leftMargin.getValue();
        }
        return rightMarginWidth + leftMarginWidth;
    }

    public static float getPaddingWidth(IPropertyContainer element) {
        UnitValue rightPadding = (UnitValue) element.getProperty(49);
        Class<MinMaxWidthUtils> cls = MinMaxWidthUtils.class;
        if (rightPadding != null && !rightPadding.isPointValue()) {
            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 49));
        }
        UnitValue leftPadding = (UnitValue) element.getProperty(48);
        if (leftPadding != null && !leftPadding.isPointValue()) {
            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 48));
        }
        float leftPaddingWidth = 0.0f;
        float rightPaddingWidth = rightPadding != null ? rightPadding.getValue() : 0.0f;
        if (leftPadding != null) {
            leftPaddingWidth = leftPadding.getValue();
        }
        return rightPaddingWidth + leftPaddingWidth;
    }
}
