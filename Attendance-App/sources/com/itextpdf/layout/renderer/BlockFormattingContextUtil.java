package com.itextpdf.layout.renderer;

import com.itextpdf.layout.property.OverflowPropertyValue;
import com.itextpdf.p026io.util.NumberUtil;

public class BlockFormattingContextUtil {
    public static boolean isRendererCreateBfc(IRenderer renderer) {
        return (renderer instanceof RootRenderer) || (renderer instanceof CellRenderer) || isInlineBlock(renderer) || FloatingHelper.isRendererFloating(renderer) || isAbsolutePosition(renderer) || isFixedPosition(renderer) || isCaption(renderer) || AbstractRenderer.isOverflowProperty(OverflowPropertyValue.HIDDEN, renderer, 103) || AbstractRenderer.isOverflowProperty(OverflowPropertyValue.HIDDEN, renderer, 104);
    }

    private static boolean isInlineBlock(IRenderer renderer) {
        return (renderer.getParent() instanceof LineRenderer) && ((renderer instanceof BlockRenderer) || (renderer instanceof TableRenderer));
    }

    private static boolean isAbsolutePosition(IRenderer renderer) {
        Integer num = 3;
        return num.equals(NumberUtil.asInteger(renderer.getProperty(52)));
    }

    private static boolean isFixedPosition(IRenderer renderer) {
        Integer num = 4;
        return num.equals(NumberUtil.asInteger(renderer.getProperty(52)));
    }

    private static boolean isCaption(IRenderer renderer) {
        return (renderer.getParent() instanceof TableRenderer) && (renderer instanceof DivRenderer);
    }
}
