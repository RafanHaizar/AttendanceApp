package com.itextpdf.layout.borders;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.borders.Border;

public class DoubleBorder extends Border {
    public DoubleBorder(float width) {
        super(width);
    }

    public DoubleBorder(Color color, float width) {
        super(color, width);
    }

    public DoubleBorder(Color color, float width, float opacity) {
        super(color, width, opacity);
    }

    public int getType() {
        return 3;
    }

    public void draw(PdfCanvas canvas, float x1, float y1, float x2, float y2, Border.Side defaultSide, float borderWidthBefore, float borderWidthAfter) {
        float x12;
        float y22;
        float y12;
        float x22;
        PdfCanvas pdfCanvas = canvas;
        float f = x1;
        float f2 = y1;
        float f3 = x2;
        float f4 = y2;
        float x3 = 0.0f;
        float y3 = 0.0f;
        float x4 = 0.0f;
        float y4 = 0.0f;
        float thirdOfWidth = this.width / 3.0f;
        float thirdOfWidthBefore = borderWidthBefore / 3.0f;
        float thirdOfWidthAfter = borderWidthAfter / 3.0f;
        Border.Side borderSide = getBorderSide(x1, y1, x2, y2, defaultSide);
        switch (C14561.$SwitchMap$com$itextpdf$layout$borders$Border$Side[borderSide.ordinal()]) {
            case 1:
                x3 = f3 + thirdOfWidthAfter;
                y3 = f4 + thirdOfWidth;
                x4 = f - thirdOfWidthBefore;
                y4 = f2 + thirdOfWidth;
                break;
            case 2:
                x3 = f3 + thirdOfWidth;
                y3 = f4 - thirdOfWidthAfter;
                x4 = f + thirdOfWidth;
                y4 = f2 + thirdOfWidthBefore;
                break;
            case 3:
                x3 = f3 - thirdOfWidthAfter;
                y3 = f4 - thirdOfWidth;
                x4 = f + thirdOfWidthBefore;
                y4 = f2 - thirdOfWidth;
                break;
            case 4:
                x3 = f3 - thirdOfWidth;
                y3 = f4 + thirdOfWidthAfter;
                x4 = f - thirdOfWidth;
                y4 = f2 - thirdOfWidthBefore;
                break;
        }
        canvas.saveState().setFillColor(this.transparentColor.getColor());
        this.transparentColor.applyFillTransparency(pdfCanvas);
        pdfCanvas.moveTo((double) f, (double) f2).lineTo((double) f3, (double) f4).lineTo((double) x3, (double) y3).lineTo((double) x4, (double) y4).lineTo((double) f, (double) f2).fill();
        switch (C14561.$SwitchMap$com$itextpdf$layout$borders$Border$Side[borderSide.ordinal()]) {
            case 1:
                x22 = (thirdOfWidthAfter * 2.0f) + f3;
                y22 = (thirdOfWidth * 2.0f) + f4;
                x3 += thirdOfWidthAfter * 2.0f;
                y3 += thirdOfWidth * 2.0f;
                x4 -= thirdOfWidthBefore * 2.0f;
                y4 += thirdOfWidth * 2.0f;
                x12 = f - (thirdOfWidthBefore * 2.0f);
                y12 = (2.0f * thirdOfWidth) + f2;
                break;
            case 2:
                x22 = (thirdOfWidth * 2.0f) + f3;
                y22 = f4 - (thirdOfWidthAfter * 2.0f);
                x3 += thirdOfWidth * 2.0f;
                y3 -= thirdOfWidthAfter * 2.0f;
                x4 += thirdOfWidth * 2.0f;
                y4 += thirdOfWidthBefore * 2.0f;
                x12 = (thirdOfWidth * 2.0f) + f;
                y12 = (2.0f * thirdOfWidthBefore) + f2;
                break;
            case 3:
                x22 = f3 - (thirdOfWidthAfter * 2.0f);
                y22 = f4 - (thirdOfWidth * 2.0f);
                x3 -= thirdOfWidthAfter * 2.0f;
                y3 -= thirdOfWidth * 2.0f;
                x4 += thirdOfWidthBefore * 2.0f;
                y4 -= thirdOfWidth * 2.0f;
                x12 = (thirdOfWidthBefore * 2.0f) + f;
                y12 = f2 - (2.0f * thirdOfWidth);
                break;
            case 4:
                x22 = f3 - (thirdOfWidth * 2.0f);
                y22 = (thirdOfWidthAfter * 2.0f) + f4;
                x3 -= thirdOfWidth * 2.0f;
                y3 += thirdOfWidthAfter * 2.0f;
                x4 -= thirdOfWidth * 2.0f;
                y4 -= thirdOfWidthBefore * 2.0f;
                x12 = f - (thirdOfWidth * 2.0f);
                y12 = f2 - (2.0f * thirdOfWidthBefore);
                break;
            default:
                x12 = f;
                y12 = f2;
                x22 = f3;
                y22 = f4;
                break;
        }
        pdfCanvas.moveTo((double) x12, (double) y12).lineTo((double) x22, (double) y22).lineTo((double) x3, (double) y3).lineTo((double) x4, (double) y4).lineTo((double) x12, (double) y12).fill().restoreState();
    }

    /* renamed from: com.itextpdf.layout.borders.DoubleBorder$1 */
    static /* synthetic */ class C14561 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$layout$borders$Border$Side;

        static {
            int[] iArr = new int[Border.Side.values().length];
            $SwitchMap$com$itextpdf$layout$borders$Border$Side = iArr;
            try {
                iArr[Border.Side.TOP.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$borders$Border$Side[Border.Side.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$borders$Border$Side[Border.Side.BOTTOM.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$borders$Border$Side[Border.Side.LEFT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public void drawCellBorder(PdfCanvas canvas, float x1, float y1, float x2, float y2, Border.Side defaultSide) {
        float thirdOfWidth = this.width / 3.0f;
        Border.Side borderSide = getBorderSide(x1, y1, x2, y2, defaultSide);
        switch (C14561.$SwitchMap$com$itextpdf$layout$borders$Border$Side[borderSide.ordinal()]) {
            case 1:
                y1 -= thirdOfWidth;
                y2 = y1;
                break;
            case 2:
                x1 -= thirdOfWidth;
                x2 -= thirdOfWidth;
                y1 += thirdOfWidth;
                y2 -= thirdOfWidth;
                break;
        }
        canvas.saveState().setLineWidth(thirdOfWidth).setStrokeColor(this.transparentColor.getColor());
        this.transparentColor.applyStrokeTransparency(canvas);
        canvas.moveTo((double) x1, (double) y1).lineTo((double) x2, (double) y2).stroke().restoreState();
        switch (C14561.$SwitchMap$com$itextpdf$layout$borders$Border$Side[borderSide.ordinal()]) {
            case 1:
                y2 += thirdOfWidth * 2.0f;
                y1 += 2.0f * thirdOfWidth;
                break;
            case 2:
                x2 += thirdOfWidth * 2.0f;
                x1 += 2.0f * thirdOfWidth;
                break;
            case 3:
                x2 -= thirdOfWidth * 2.0f;
                y2 -= thirdOfWidth * 2.0f;
                x1 += thirdOfWidth * 2.0f;
                y1 -= 2.0f * thirdOfWidth;
                break;
            case 4:
                y2 += thirdOfWidth * 2.0f;
                x1 -= thirdOfWidth * 2.0f;
                y1 -= 2.0f * thirdOfWidth;
                break;
        }
        canvas.saveState().setLineWidth(thirdOfWidth).setStrokeColor(this.transparentColor.getColor());
        this.transparentColor.applyStrokeTransparency(canvas);
        canvas.moveTo((double) x1, (double) y1).lineTo((double) x2, (double) y2).stroke().restoreState();
    }
}
