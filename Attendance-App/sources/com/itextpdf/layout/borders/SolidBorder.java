package com.itextpdf.layout.borders;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.borders.Border;

public class SolidBorder extends Border {
    public SolidBorder(float width) {
        super(width);
    }

    public SolidBorder(Color color, float width) {
        super(color, width);
    }

    public SolidBorder(Color color, float width, float opacity) {
        super(color, width, opacity);
    }

    public int getType() {
        return 0;
    }

    public void draw(PdfCanvas canvas, float x1, float y1, float x2, float y2, Border.Side defaultSide, float borderWidthBefore, float borderWidthAfter) {
        PdfCanvas pdfCanvas = canvas;
        float f = x1;
        float f2 = y1;
        float f3 = x2;
        float f4 = y2;
        float x3 = 0.0f;
        float y3 = 0.0f;
        float x4 = 0.0f;
        float y4 = 0.0f;
        switch (C14611.$SwitchMap$com$itextpdf$layout$borders$Border$Side[getBorderSide(x1, y1, x2, y2, defaultSide).ordinal()]) {
            case 1:
                x3 = f3 + borderWidthAfter;
                y3 = f4 + this.width;
                x4 = f - borderWidthBefore;
                y4 = f2 + this.width;
                break;
            case 2:
                x3 = f3 + this.width;
                y3 = f4 - borderWidthAfter;
                x4 = f + this.width;
                y4 = f2 + borderWidthBefore;
                break;
            case 3:
                x3 = f3 - borderWidthAfter;
                y3 = f4 - this.width;
                x4 = f + borderWidthBefore;
                y4 = f2 - this.width;
                break;
            case 4:
                x3 = f3 - this.width;
                y3 = f4 + borderWidthAfter;
                x4 = f - this.width;
                y4 = f2 - borderWidthBefore;
                break;
        }
        canvas.saveState().setFillColor(this.transparentColor.getColor());
        this.transparentColor.applyFillTransparency(pdfCanvas);
        pdfCanvas.moveTo((double) f, (double) f2).lineTo((double) f3, (double) f4).lineTo((double) x3, (double) y3).lineTo((double) x4, (double) y4).lineTo((double) f, (double) f2).fill().restoreState();
    }

    /* renamed from: com.itextpdf.layout.borders.SolidBorder$1 */
    static /* synthetic */ class C14611 {
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

    public void draw(PdfCanvas canvas, float x1, float y1, float x2, float y2, float horizontalRadius1, float verticalRadius1, float horizontalRadius2, float verticalRadius2, Border.Side defaultSide, float borderWidthBefore, float borderWidthAfter) {
        float y12;
        float x22;
        float y22;
        float innerRadiusSecond;
        float innerRadiusAfter;
        float y3;
        float x3;
        float x4;
        float x42;
        float y32;
        float x32;
        float x43;
        float y13;
        float innerRadiusAfter2;
        float y4;
        float x33;
        float x44;
        float x12;
        float y33;
        float y14;
        float innerRadiusSecond2;
        float innerRadiusAfter3;
        float y34;
        float x34;
        float x45;
        float y15;
        PdfCanvas pdfCanvas = canvas;
        float x13 = x1;
        float f = y1;
        float f2 = x2;
        float f3 = y2;
        float x35 = 0.0f;
        float y35 = 0.0f;
        float x46 = 0.0f;
        float y42 = 0.0f;
        Border.Side borderSide = getBorderSide(x1, y1, x2, y2, defaultSide);
        switch (C14611.$SwitchMap$com$itextpdf$layout$borders$Border$Side[borderSide.ordinal()]) {
            case 1:
                float x14 = x13;
                float y16 = f;
                y22 = f3;
                float x36 = f2 + borderWidthAfter;
                float y36 = y22 + this.width;
                float x47 = x14 - borderWidthBefore;
                float y43 = y16 + this.width;
                float innerRadiusBefore = Math.max(0.0f, horizontalRadius1 - borderWidthBefore);
                float innerRadiusFirst = Math.max(0.0f, verticalRadius1 - this.width);
                float innerRadiusSecond3 = Math.max(0.0f, verticalRadius2 - this.width);
                float innerRadiusAfter4 = Math.max(0.0f, horizontalRadius2 - borderWidthAfter);
                if (innerRadiusBefore > innerRadiusFirst) {
                    x3 = x36;
                    y3 = y36;
                    innerRadiusAfter = innerRadiusAfter4;
                    innerRadiusSecond = innerRadiusSecond3;
                    x14 = (float) getIntersectionPoint(new Point((double) x14, (double) y16), new Point((double) x47, (double) y43), new Point((double) x47, (double) (y16 - innerRadiusFirst)), new Point((double) (x14 + innerRadiusBefore), (double) (y16 - innerRadiusFirst))).getX();
                    y16 -= innerRadiusFirst;
                    x4 = x47;
                } else {
                    innerRadiusSecond = innerRadiusSecond3;
                    innerRadiusAfter = innerRadiusAfter4;
                    x3 = x36;
                    y3 = y36;
                    float x48 = x47;
                    if (0.0f == innerRadiusBefore || 0.0f == innerRadiusFirst) {
                        x4 = x48;
                    } else {
                        x4 = x48;
                        y16 = (float) getIntersectionPoint(new Point((double) x14, (double) y16), new Point((double) x4, (double) y43), new Point((double) (x14 + innerRadiusBefore), (double) y16), new Point((double) (x14 + innerRadiusBefore), (double) (y16 - innerRadiusFirst))).getY();
                        x14 += innerRadiusBefore;
                    }
                }
                if (innerRadiusAfter <= innerRadiusSecond) {
                    float f4 = x2;
                    float x15 = x14;
                    float y17 = y16;
                    float x37 = x3;
                    y35 = y3;
                    float y37 = innerRadiusBefore;
                    float y44 = y43;
                    if (0.0f != innerRadiusAfter && 0.0f != innerRadiusSecond) {
                        y22 = (float) getIntersectionPoint(new Point((double) f4, (double) y22), new Point((double) x37, (double) y35), new Point((double) (f4 - innerRadiusAfter), (double) y22), new Point((double) (f4 - innerRadiusAfter), (double) (y22 - innerRadiusSecond))).getY();
                        x22 = f4 - innerRadiusAfter;
                        y12 = y17;
                        x46 = x4;
                        x35 = x37;
                        y42 = y44;
                        x13 = x15;
                        break;
                    } else {
                        y12 = y17;
                        x22 = f4;
                        x46 = x4;
                        x35 = x37;
                        y42 = y44;
                        x13 = x15;
                        break;
                    }
                } else {
                    float f5 = x2;
                    float x38 = x3;
                    float x16 = x14;
                    y35 = y3;
                    float f6 = innerRadiusBefore;
                    x22 = (float) getIntersectionPoint(new Point((double) f5, (double) y22), new Point((double) x38, (double) y35), new Point((double) f5, (double) (y22 - innerRadiusSecond)), new Point((double) (f5 - innerRadiusAfter), (double) (y22 - innerRadiusSecond))).getX();
                    y22 -= innerRadiusSecond;
                    y12 = y16;
                    x46 = x4;
                    x35 = x38;
                    y42 = y43;
                    x13 = x16;
                    break;
                }
                break;
            case 2:
                float f7 = f;
                float x39 = f2 + this.width;
                float y38 = f3 - borderWidthAfter;
                float x17 = x1;
                float x49 = x17 + this.width;
                float y45 = f7 + borderWidthBefore;
                float innerRadiusBefore2 = Math.max(0.0f, verticalRadius1 - borderWidthBefore);
                float innerRadiusFirst2 = Math.max(0.0f, horizontalRadius1 - this.width);
                float innerRadiusSecond4 = Math.max(0.0f, horizontalRadius2 - this.width);
                float innerRadiusAfter5 = Math.max(0.0f, verticalRadius2 - borderWidthAfter);
                if (innerRadiusFirst2 > innerRadiusBefore2) {
                    x32 = x39;
                    y32 = y38;
                    x17 = (float) getIntersectionPoint(new Point((double) x17, (double) f7), new Point((double) x49, (double) y45), new Point((double) x17, (double) (f7 - innerRadiusBefore2)), new Point((double) (x17 - innerRadiusFirst2), (double) (f7 - innerRadiusBefore2))).getX();
                    y13 = f7 - innerRadiusBefore2;
                    x43 = x49;
                    x42 = y45;
                } else {
                    x32 = x39;
                    y32 = y38;
                    float x410 = x49;
                    if (0.0f == innerRadiusBefore2 || 0.0f == innerRadiusFirst2) {
                        x43 = x410;
                        x42 = y45;
                        y13 = f7;
                    } else {
                        x43 = x410;
                        x42 = y45;
                        y13 = (float) getIntersectionPoint(new Point((double) x17, (double) f7), new Point((double) x43, (double) y45), new Point((double) (x17 - innerRadiusFirst2), (double) f7), new Point((double) (x17 - innerRadiusFirst2), (double) (f7 - innerRadiusBefore2))).getY();
                        x17 -= innerRadiusFirst2;
                    }
                }
                if (innerRadiusAfter5 <= innerRadiusSecond4) {
                    float f8 = x2;
                    float x18 = x17;
                    float y18 = y13;
                    float y39 = y32;
                    float x310 = x32;
                    float x311 = innerRadiusBefore2;
                    if (0.0f != innerRadiusAfter5 && 0.0f != innerRadiusSecond4) {
                        float f9 = y2;
                        float x312 = x310;
                        float f10 = innerRadiusFirst2;
                        float f11 = innerRadiusSecond4;
                        float f12 = f9 + innerRadiusAfter5;
                        x13 = x18;
                        y12 = y18;
                        x35 = x312;
                        x46 = x43;
                        y35 = y39;
                        y42 = x42;
                        float f13 = f12;
                        x22 = (float) getIntersectionPoint(new Point((double) f8, (double) f9), new Point((double) x312, (double) y39), new Point((double) f8, (double) (f9 + innerRadiusAfter5)), new Point((double) (f8 - innerRadiusSecond4), (double) (f9 + innerRadiusAfter5))).getX();
                        y22 = f13;
                        break;
                    } else {
                        float f14 = innerRadiusFirst2;
                        float x313 = x310;
                        float x314 = innerRadiusSecond4;
                        x35 = x313;
                        y22 = y2;
                        x22 = f8;
                        x46 = x43;
                        y35 = y39;
                        y42 = x42;
                        x13 = x18;
                        y12 = y18;
                        break;
                    }
                } else {
                    float f15 = x2;
                    float f16 = y2;
                    float x315 = x32;
                    float f17 = innerRadiusBefore2;
                    float x19 = x17;
                    float y310 = y32;
                    y22 = (float) getIntersectionPoint(new Point((double) f15, (double) f16), new Point((double) x315, (double) y310), new Point((double) (f15 - innerRadiusSecond4), (double) f16), new Point((double) (f15 - innerRadiusSecond4), (double) (f16 + innerRadiusAfter5))).getY();
                    x22 = f15 - innerRadiusSecond4;
                    x13 = x19;
                    y12 = y13;
                    x46 = x43;
                    y35 = y310;
                    x35 = x315;
                    y42 = x42;
                    break;
                }
                break;
            case 3:
                float x316 = f2 - borderWidthAfter;
                float y311 = f3 - this.width;
                float x411 = x13 + borderWidthBefore;
                float y46 = f - this.width;
                float innerRadiusBefore3 = Math.max(0.0f, horizontalRadius1 - borderWidthBefore);
                float innerRadiusFirst3 = Math.max(0.0f, verticalRadius1 - this.width);
                float innerRadiusSecond5 = Math.max(0.0f, verticalRadius2 - this.width);
                float innerRadiusAfter6 = Math.max(0.0f, horizontalRadius2 - borderWidthAfter);
                if (innerRadiusFirst3 > innerRadiusBefore3) {
                    x33 = x316;
                    y33 = y311;
                    y4 = y46;
                    innerRadiusAfter2 = innerRadiusAfter6;
                    y14 = (float) getIntersectionPoint(new Point((double) x13, (double) f), new Point((double) x411, (double) y46), new Point((double) (x13 - innerRadiusBefore3), (double) f), new Point((double) (x13 - innerRadiusBefore3), (double) (f + innerRadiusFirst3))).getY();
                    x12 = x13 - innerRadiusBefore3;
                    x44 = x411;
                } else {
                    innerRadiusAfter2 = innerRadiusAfter6;
                    x33 = x316;
                    y33 = y311;
                    float x412 = x411;
                    y4 = y46;
                    if (0.0f == innerRadiusBefore3 || 0.0f == innerRadiusFirst3) {
                        x44 = x412;
                        y14 = y1;
                        x12 = x1;
                    } else {
                        float f18 = y1;
                        x44 = x412;
                        float f19 = f18 + innerRadiusFirst3;
                        x12 = (float) getIntersectionPoint(new Point((double) x13, (double) f18), new Point((double) x44, (double) y4), new Point((double) x13, (double) (f18 + innerRadiusFirst3)), new Point((double) (x13 - innerRadiusBefore3), (double) (f18 + innerRadiusFirst3))).getX();
                        y14 = f19;
                    }
                }
                if (innerRadiusAfter2 <= innerRadiusSecond5) {
                    float f20 = x2;
                    float f21 = y2;
                    float f22 = innerRadiusBefore3;
                    float y19 = y14;
                    float x110 = x12;
                    x35 = x33;
                    float x317 = innerRadiusFirst3;
                    if (0.0f != innerRadiusAfter2 && 0.0f != innerRadiusSecond5) {
                        y22 = (float) getIntersectionPoint(new Point((double) f20, (double) f21), new Point((double) x35, (double) y33), new Point((double) (f20 + innerRadiusAfter2), (double) f21), new Point((double) (f20 + innerRadiusAfter2), (double) (f21 + innerRadiusSecond5))).getY();
                        x22 = f20 + innerRadiusAfter2;
                        y12 = y19;
                        x13 = x110;
                        y35 = y33;
                        x46 = x44;
                        y42 = y4;
                        break;
                    } else {
                        y12 = y19;
                        y35 = y33;
                        x22 = f20;
                        y22 = f21;
                        x46 = x44;
                        y42 = y4;
                        x13 = x110;
                        break;
                    }
                } else {
                    float f23 = x2;
                    float f24 = y2;
                    x35 = x33;
                    float f25 = innerRadiusBefore3;
                    float x111 = x12;
                    float f26 = innerRadiusFirst3;
                    y12 = y14;
                    x13 = x111;
                    y35 = y33;
                    x46 = x44;
                    y42 = y4;
                    x22 = (float) getIntersectionPoint(new Point((double) f23, (double) f24), new Point((double) x35, (double) y33), new Point((double) f23, (double) (f24 + innerRadiusSecond5)), new Point((double) (f23 + innerRadiusAfter2), (double) (f24 + innerRadiusSecond5))).getX();
                    y22 = f24 + innerRadiusSecond5;
                    break;
                }
                break;
            case 4:
                float x318 = f2 - this.width;
                float y312 = f3 + borderWidthAfter;
                float x413 = x13 - this.width;
                float y47 = f - borderWidthBefore;
                float innerRadiusBefore4 = Math.max(0.0f, verticalRadius1 - borderWidthBefore);
                float innerRadiusFirst4 = Math.max(0.0f, horizontalRadius1 - this.width);
                float innerRadiusSecond6 = Math.max(0.0f, horizontalRadius2 - this.width);
                float innerRadiusAfter7 = Math.max(0.0f, verticalRadius2 - borderWidthAfter);
                if (innerRadiusFirst4 > innerRadiusBefore4) {
                    x34 = x318;
                    y34 = y312;
                    innerRadiusAfter3 = innerRadiusAfter7;
                    innerRadiusSecond2 = innerRadiusSecond6;
                    y15 = f + innerRadiusBefore4;
                    float f27 = innerRadiusBefore4;
                    x13 = (float) getIntersectionPoint(new Point((double) x13, (double) f), new Point((double) x413, (double) y47), new Point((double) x13, (double) (f + innerRadiusBefore4)), new Point((double) (x13 + innerRadiusFirst4), (double) (f + innerRadiusBefore4))).getX();
                    x45 = x413;
                    Border.Side side = borderSide;
                } else {
                    innerRadiusSecond2 = innerRadiusSecond6;
                    innerRadiusAfter3 = innerRadiusAfter7;
                    x34 = x318;
                    y34 = y312;
                    float x414 = x413;
                    if (0.0f == innerRadiusBefore4 || 0.0f == innerRadiusFirst4) {
                        x45 = x414;
                        Border.Side side2 = borderSide;
                        y15 = f;
                    } else {
                        x45 = x414;
                        Border.Side side3 = borderSide;
                        float f28 = innerRadiusBefore4;
                        y15 = (float) getIntersectionPoint(new Point((double) x13, (double) f), new Point((double) x45, (double) y47), new Point((double) (x13 + innerRadiusFirst4), (double) f), new Point((double) (x13 + innerRadiusFirst4), (double) (f + innerRadiusBefore4))).getY();
                        x13 += innerRadiusFirst4;
                    }
                }
                if (innerRadiusAfter3 <= innerRadiusSecond2) {
                    x22 = x2;
                    float f29 = y2;
                    float f30 = innerRadiusFirst4;
                    float y110 = y15;
                    float x319 = x34;
                    y35 = y34;
                    float x320 = x45;
                    float y48 = y47;
                    if (0.0f != innerRadiusAfter3 && 0.0f != innerRadiusSecond2) {
                        y12 = y110;
                        x35 = x319;
                        x46 = x320;
                        y42 = y48;
                        x22 = (float) getIntersectionPoint(new Point((double) x22, (double) f29), new Point((double) x319, (double) y35), new Point((double) x22, (double) (f29 - innerRadiusAfter3)), new Point((double) (x22 + innerRadiusSecond2), (double) (f29 - innerRadiusAfter3))).getX();
                        y22 = f29 - innerRadiusAfter3;
                        break;
                    } else {
                        y12 = y110;
                        y22 = f29;
                        x35 = x319;
                        x46 = x320;
                        y42 = y48;
                        break;
                    }
                } else {
                    float f31 = x2;
                    float f32 = y2;
                    float x321 = x34;
                    y35 = y34;
                    float f33 = innerRadiusFirst4;
                    y22 = (float) getIntersectionPoint(new Point((double) f31, (double) f32), new Point((double) x321, (double) y35), new Point((double) (f31 + innerRadiusSecond2), (double) f32), new Point((double) (f31 + innerRadiusSecond2), (double) (f32 - innerRadiusAfter3))).getY();
                    x22 = f31 + innerRadiusSecond2;
                    y12 = y15;
                    x35 = x321;
                    x46 = x45;
                    y42 = y47;
                    break;
                }
                break;
            default:
                Border.Side side4 = borderSide;
                float f34 = x13;
                y12 = f;
                y22 = f3;
                x22 = f2;
                break;
        }
        canvas.saveState().setFillColor(this.transparentColor.getColor());
        PdfCanvas pdfCanvas2 = canvas;
        this.transparentColor.applyFillTransparency(pdfCanvas2);
        pdfCanvas2.moveTo((double) x13, (double) y12).lineTo((double) x22, (double) y22).lineTo((double) x35, (double) y35).lineTo((double) x46, (double) y42).lineTo((double) x13, (double) y12).fill().restoreState();
    }

    public void drawCellBorder(PdfCanvas canvas, float x1, float y1, float x2, float y2, Border.Side defaultSide) {
        canvas.saveState().setStrokeColor(this.transparentColor.getColor());
        this.transparentColor.applyStrokeTransparency(canvas);
        canvas.setLineWidth(this.width).moveTo((double) x1, (double) y1).lineTo((double) x2, (double) y2).stroke().restoreState();
    }
}
