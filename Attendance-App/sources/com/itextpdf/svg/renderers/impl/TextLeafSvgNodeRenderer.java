package com.itextpdf.svg.renderers.impl;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.property.RenderingMode;
import com.itextpdf.layout.renderer.TextRenderer;
import com.itextpdf.svg.SvgConstants;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.SvgDrawContext;
import com.itextpdf.svg.utils.SvgTextUtil;
import com.itextpdf.svg.utils.TextRectangle;

public class TextLeafSvgNodeRenderer extends AbstractSvgNodeRenderer implements ISvgTextNodeRenderer, ISvgTextNodeHelper {
    public ISvgNodeRenderer createDeepCopy() {
        TextLeafSvgNodeRenderer copy = new TextLeafSvgNodeRenderer();
        deepCopyAttributesAndStyles(copy);
        return copy;
    }

    public float getTextContentLength(float parentFontSize, PdfFont font) {
        if (font == null || this.attributesAndStyles == null || !this.attributesAndStyles.containsKey(SvgConstants.Attributes.TEXT_CONTENT)) {
            return 0.0f;
        }
        return font.getWidth((String) this.attributesAndStyles.get(SvgConstants.Attributes.TEXT_CONTENT), SvgTextUtil.resolveFontSize(this, parentFontSize));
    }

    public float[] getRelativeTranslation() {
        return new float[]{0.0f, 0.0f};
    }

    public boolean containsRelativeMove() {
        return false;
    }

    public boolean containsAbsolutePositionChange() {
        return false;
    }

    public float[][] getAbsolutePositionChanges() {
        float[] part = {0.0f};
        return new float[][]{part, part};
    }

    public TextRectangle getTextRectangle(SvgDrawContext context, Point basePoint) {
        if (!(getParent() instanceof TextSvgBranchRenderer) || basePoint == null) {
            return null;
        }
        float parentFontSize = ((TextSvgBranchRenderer) getParent()).getFontSize();
        PdfFont parentFont = ((TextSvgBranchRenderer) getParent()).getFont();
        float textLength = getTextContentLength(parentFontSize, parentFont);
        float[] fontAscenderDescenderFromMetrics = TextRenderer.calculateAscenderDescender(parentFont, RenderingMode.HTML_MODE);
        float fontAscender = (fontAscenderDescenderFromMetrics[0] / 1000.0f) * parentFontSize;
        return new TextRectangle((float) basePoint.getX(), ((float) basePoint.getY()) - fontAscender, textLength, fontAscender - ((fontAscenderDescenderFromMetrics[1] / 1000.0f) * parentFontSize), (float) basePoint.getY());
    }

    /* access modifiers changed from: protected */
    public void doDraw(SvgDrawContext context) {
        if (this.attributesAndStyles != null && this.attributesAndStyles.containsKey(SvgConstants.Attributes.TEXT_CONTENT)) {
            PdfCanvas currentCanvas = context.getCurrentCanvas();
            currentCanvas.moveText((double) context.getTextMove()[0], (double) context.getTextMove()[1]);
            currentCanvas.showText((String) this.attributesAndStyles.get(SvgConstants.Attributes.TEXT_CONTENT));
        }
    }

    /* access modifiers changed from: protected */
    public boolean canElementFill() {
        return false;
    }
}
