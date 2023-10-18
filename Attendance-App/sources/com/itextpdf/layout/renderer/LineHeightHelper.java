package com.itextpdf.layout.renderer;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.property.LineHeight;
import com.itextpdf.layout.property.RenderingMode;

class LineHeightHelper {
    private static float DEFAULT_LINE_HEIGHT_COEFF = 1.15f;

    private LineHeightHelper() {
    }

    static float[] getActualAscenderDescender(AbstractRenderer renderer) {
        float lineHeight = calculateLineHeight(renderer);
        float[] fontAscenderDescender = getFontAscenderDescenderNormalized(renderer);
        float leading = lineHeight - (fontAscenderDescender[0] - fontAscenderDescender[1]);
        return new float[]{fontAscenderDescender[0] + (leading / 2.0f), fontAscenderDescender[1] - (leading / 2.0f)};
    }

    static float[] getFontAscenderDescenderNormalized(AbstractRenderer renderer) {
        PdfFont font = renderer.resolveFirstPdfFont();
        float fontSize = renderer.getPropertyAsUnitValue(24).getValue();
        float[] fontAscenderDescenderFromMetrics = TextRenderer.calculateAscenderDescender(font, RenderingMode.HTML_MODE);
        return new float[]{(fontAscenderDescenderFromMetrics[0] / 1000.0f) * fontSize, (fontAscenderDescenderFromMetrics[1] / 1000.0f) * fontSize};
    }

    static float calculateLineHeight(AbstractRenderer renderer) {
        LineHeight lineHeight = (LineHeight) renderer.getProperty(124);
        float fontSize = renderer.getPropertyAsUnitValue(24).getValue();
        if (lineHeight == null || lineHeight.isNormalValue() || lineHeight.getValue() < 0.0f) {
            float lineHeightValue = DEFAULT_LINE_HEIGHT_COEFF * fontSize;
            float[] fontAscenderDescender = getFontAscenderDescenderNormalized(renderer);
            float fontAscenderDescenderSum = fontAscenderDescender[0] - fontAscenderDescender[1];
            if (fontAscenderDescenderSum > lineHeightValue) {
                return fontAscenderDescenderSum;
            }
            return lineHeightValue;
        } else if (lineHeight.isFixedValue()) {
            return lineHeight.getValue();
        } else {
            return lineHeight.getValue() * fontSize;
        }
    }
}
