package com.itextpdf.layout.borders;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.borders.Border;

public abstract class Border3D extends Border {
    private static final DeviceRgb GRAY = new DeviceRgb(212, 208, 200);

    /* access modifiers changed from: protected */
    public abstract void setInnerHalfColor(PdfCanvas pdfCanvas, Border.Side side);

    /* access modifiers changed from: protected */
    public abstract void setOuterHalfColor(PdfCanvas pdfCanvas, Border.Side side);

    protected Border3D(float width) {
        this(GRAY, width);
    }

    protected Border3D(DeviceRgb color, float width) {
        super(color, width);
    }

    protected Border3D(DeviceCmyk color, float width) {
        super(color, width);
    }

    protected Border3D(DeviceGray color, float width) {
        super(color, width);
    }

    protected Border3D(DeviceRgb color, float width, float opacity) {
        super(color, width, opacity);
    }

    protected Border3D(DeviceCmyk color, float width, float opacity) {
        super(color, width, opacity);
    }

    protected Border3D(DeviceGray color, float width, float opacity) {
        super(color, width, opacity);
    }

    public void draw(PdfCanvas canvas, float x1, float y1, float x2, float y2, Border.Side defaultSide, float borderWidthBefore, float borderWidthAfter) {
        float y12;
        float x12;
        float y22;
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
        float widthHalf = this.width / 2.0f;
        float halfOfWidthBefore = borderWidthBefore / 2.0f;
        float halfOfWidthAfter = borderWidthAfter / 2.0f;
        Border.Side borderSide = getBorderSide(x1, y1, x2, y2, defaultSide);
        switch (C14551.$SwitchMap$com$itextpdf$layout$borders$Border$Side[borderSide.ordinal()]) {
            case 1:
                x3 = f3 + halfOfWidthAfter;
                y3 = f4 + widthHalf;
                x4 = f - halfOfWidthBefore;
                y4 = f2 + widthHalf;
                break;
            case 2:
                x3 = f3 + widthHalf;
                y3 = f4 - halfOfWidthAfter;
                x4 = f + widthHalf;
                y4 = f2 + halfOfWidthBefore;
                break;
            case 3:
                x3 = f3 - halfOfWidthAfter;
                y3 = f4 - widthHalf;
                x4 = f + halfOfWidthBefore;
                y4 = f2 - widthHalf;
                break;
            case 4:
                x3 = f3 - widthHalf;
                y3 = f4 + halfOfWidthAfter;
                x4 = f - widthHalf;
                y4 = f2 - halfOfWidthBefore;
                break;
        }
        canvas.saveState();
        this.transparentColor.applyFillTransparency(pdfCanvas);
        setInnerHalfColor(pdfCanvas, borderSide);
        pdfCanvas.moveTo((double) f, (double) f2).lineTo((double) f3, (double) f4).lineTo((double) x3, (double) y3).lineTo((double) x4, (double) y4).lineTo((double) f, (double) f2).fill();
        switch (C14551.$SwitchMap$com$itextpdf$layout$borders$Border$Side[borderSide.ordinal()]) {
            case 1:
                x22 = f3 + borderWidthAfter;
                y22 = this.width + f4;
                x12 = f - borderWidthBefore;
                y12 = this.width + f2;
                break;
            case 2:
                x22 = this.width + f3;
                y22 = f4 - borderWidthAfter;
                x12 = this.width + f;
                y12 = f2 + borderWidthBefore;
                break;
            case 3:
                x22 = f3 - borderWidthAfter;
                y22 = f4 - this.width;
                x12 = f + borderWidthBefore;
                y12 = f2 - this.width;
                break;
            case 4:
                x22 = f3 - this.width;
                y22 = f4 + borderWidthAfter;
                x12 = f - this.width;
                y12 = f2 - borderWidthBefore;
                break;
            default:
                x12 = f;
                y12 = f2;
                x22 = f3;
                y22 = f4;
                break;
        }
        setOuterHalfColor(pdfCanvas, borderSide);
        pdfCanvas.moveTo((double) x12, (double) y12).lineTo((double) x22, (double) y22).lineTo((double) x3, (double) y3).lineTo((double) x4, (double) y4).lineTo((double) x12, (double) y12).fill();
        canvas.restoreState();
    }

    /* renamed from: com.itextpdf.layout.borders.Border3D$1 */
    static /* synthetic */ class C14551 {
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
        canvas.saveState().setStrokeColor(this.transparentColor.getColor());
        this.transparentColor.applyStrokeTransparency(canvas);
        canvas.setLineWidth(this.width).moveTo((double) x1, (double) y1).lineTo((double) x2, (double) y2).stroke().restoreState();
    }

    /* access modifiers changed from: protected */
    public Color getDarkerColor() {
        Color color = this.transparentColor.getColor();
        if (color instanceof DeviceRgb) {
            return DeviceRgb.makeDarker((DeviceRgb) color);
        }
        if (color instanceof DeviceCmyk) {
            return DeviceCmyk.makeDarker((DeviceCmyk) color);
        }
        if (color instanceof DeviceGray) {
            return DeviceGray.makeDarker((DeviceGray) color);
        }
        return color;
    }
}
