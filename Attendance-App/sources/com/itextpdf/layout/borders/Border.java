package com.itextpdf.layout.borders;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.property.TransparentColor;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.MessageFormatUtil;
import org.slf4j.LoggerFactory;

public abstract class Border {
    private static final float CURV = 0.447f;
    public static final int DASHED = 1;
    public static final int DOTTED = 2;
    public static final int DOUBLE = 3;
    public static final Border NO_BORDER = null;
    public static final int ROUND_DOTS = 4;
    public static final int SOLID = 0;
    public static final int _3D_GROOVE = 5;
    public static final int _3D_INSET = 6;
    public static final int _3D_OUTSET = 7;
    public static final int _3D_RIDGE = 8;
    private int hash;
    protected TransparentColor transparentColor;
    protected int type;
    protected float width;

    public enum Side {
        NONE,
        TOP,
        RIGHT,
        BOTTOM,
        LEFT
    }

    public abstract void draw(PdfCanvas pdfCanvas, float f, float f2, float f3, float f4, Side side, float f5, float f6);

    public abstract void drawCellBorder(PdfCanvas pdfCanvas, float f, float f2, float f3, float f4, Side side);

    public abstract int getType();

    protected Border(float width2) {
        this(ColorConstants.BLACK, width2);
    }

    protected Border(Color color, float width2) {
        this.transparentColor = new TransparentColor(color);
        this.width = width2;
    }

    protected Border(Color color, float width2, float opacity) {
        this.transparentColor = new TransparentColor(color, opacity);
        this.width = width2;
    }

    public void draw(PdfCanvas canvas, float x1, float y1, float x2, float y2, float borderRadius, Side defaultSide, float borderWidthBefore, float borderWidthAfter) {
        draw(canvas, x1, y1, x2, y2, borderRadius, borderRadius, borderRadius, borderRadius, defaultSide, borderWidthBefore, borderWidthAfter);
    }

    public void draw(PdfCanvas canvas, float x1, float y1, float x2, float y2, float horizontalRadius1, float verticalRadius1, float horizontalRadius2, float verticalRadius2, Side defaultSide, float borderWidthBefore, float borderWidthAfter) {
        LoggerFactory.getLogger((Class<?>) Border.class).warn(MessageFormatUtil.format(LogMessageConstant.METHOD_IS_NOT_IMPLEMENTED_BY_DEFAULT_OTHER_METHOD_WILL_BE_USED, "Border#draw(PdfCanvas, float, float, float, float, float, float, float, float, Side, float, float", "Border#draw(PdfCanvas, float, float, float, float, Side, float, float)"));
        draw(canvas, x1, y1, x2, y2, defaultSide, borderWidthBefore, borderWidthAfter);
    }

    public Color getColor() {
        return this.transparentColor.getColor();
    }

    public float getOpacity() {
        return this.transparentColor.getOpacity();
    }

    public float getWidth() {
        return this.width;
    }

    public void setColor(Color color) {
        this.transparentColor = new TransparentColor(color, this.transparentColor.getOpacity());
    }

    public void setWidth(float width2) {
        this.width = width2;
    }

    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (!(anObject instanceof Border)) {
            return false;
        }
        Border anotherBorder = (Border) anObject;
        if (anotherBorder.getType() == getType() && anotherBorder.getColor().equals(getColor()) && anotherBorder.getWidth() == getWidth() && anotherBorder.transparentColor.getOpacity() == this.transparentColor.getOpacity()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int h = this.hash;
        if (h != 0) {
            return h;
        }
        int h2 = (((((int) getWidth()) * 31) + getColor().hashCode()) * 31) + ((int) this.transparentColor.getOpacity());
        this.hash = h2;
        return h2;
    }

    /* access modifiers changed from: protected */
    public Side getBorderSide(float x1, float y1, float x2, float y2, Side defaultSide) {
        boolean isLeft = false;
        boolean isRight = false;
        boolean z = true;
        if (Math.abs(y2 - y1) > 5.0E-4f) {
            isLeft = y2 - y1 > 0.0f;
            isRight = y2 - y1 < 0.0f;
        }
        boolean isTop = false;
        boolean isBottom = false;
        if (Math.abs(x2 - x1) > 5.0E-4f) {
            isTop = x2 - x1 > 0.0f;
            if (x2 - x1 >= 0.0f) {
                z = false;
            }
            isBottom = z;
        }
        if (isTop) {
            return isLeft ? Side.LEFT : Side.TOP;
        }
        if (isRight) {
            return Side.RIGHT;
        }
        if (isBottom) {
            return Side.BOTTOM;
        }
        if (isLeft) {
            return Side.LEFT;
        }
        return defaultSide;
    }

    /* access modifiers changed from: protected */
    public Point getIntersectionPoint(Point lineBeg, Point lineEnd, Point clipLineBeg, Point clipLineEnd) {
        double A1 = lineBeg.getY() - lineEnd.getY();
        double A2 = clipLineBeg.getY() - clipLineEnd.getY();
        double B1 = lineEnd.getX() - lineBeg.getX();
        double B2 = clipLineEnd.getX() - clipLineBeg.getX();
        double C1 = (lineBeg.getX() * lineEnd.getY()) - (lineBeg.getY() * lineEnd.getX());
        double C2 = (clipLineBeg.getX() * clipLineEnd.getY()) - (clipLineBeg.getY() * clipLineEnd.getX());
        double M = (B1 * A2) - (B2 * A1);
        double d = B1;
        double d2 = A1;
        return new Point(((B2 * C1) - (B1 * C2)) / M, ((C2 * A1) - (C1 * A2)) / M);
    }

    /* access modifiers changed from: protected */
    public float getDotsGap(double distance, float initialGap) {
        double d = (double) initialGap;
        Double.isNaN(d);
        double gapsNum = Math.ceil(distance / d);
        if (gapsNum == 0.0d) {
            return initialGap;
        }
        return (float) (distance / gapsNum);
    }

    /* access modifiers changed from: protected */
    public void drawDiscontinuousBorders(PdfCanvas canvas, Rectangle boundingRectangle, float[] horizontalRadii, float[] verticalRadii, Side defaultSide, float borderWidthBefore, float borderWidthAfter) {
        float y3;
        PdfCanvas pdfCanvas;
        float x3;
        PdfCanvas pdfCanvas2;
        PdfCanvas pdfCanvas3;
        PdfCanvas pdfCanvas4;
        PdfCanvas pdfCanvas5 = canvas;
        float x1 = boundingRectangle.getX();
        float y1 = boundingRectangle.getY();
        float x2 = boundingRectangle.getRight();
        float y2 = boundingRectangle.getTop();
        float horizontalRadius1 = horizontalRadii[0];
        float horizontalRadius2 = horizontalRadii[1];
        float verticalRadius1 = verticalRadii[0];
        float verticalRadius2 = verticalRadii[1];
        float x0 = boundingRectangle.getX();
        float y0 = boundingRectangle.getY();
        float x32 = boundingRectangle.getRight();
        float y32 = boundingRectangle.getTop();
        float widthHalf = this.width / 2.0f;
        Side borderSide = getBorderSide(x1, y1, x2, y2, defaultSide);
        switch (C14541.$SwitchMap$com$itextpdf$layout$borders$Border$Side[borderSide.ordinal()]) {
            case 1:
                float x22 = x2;
                float y22 = y2;
                float horizontalRadius12 = horizontalRadius1;
                float verticalRadius22 = verticalRadius2;
                float horizontalRadius13 = x1;
                float y12 = y1;
                float innerRadiusBefore = Math.max(0.0f, horizontalRadius12 - borderWidthBefore);
                float innerRadiusFirst = Math.max(0.0f, verticalRadius1 - this.width);
                float innerRadiusSecond = Math.max(0.0f, verticalRadius22 - this.width);
                float x02 = x0 - (borderWidthBefore / 2.0f);
                float y02 = y0 - innerRadiusFirst;
                float x33 = x32 + (borderWidthAfter / 2.0f);
                float y33 = y32 - innerRadiusSecond;
                float innerRadiusFirst2 = innerRadiusFirst;
                float innerRadiusSecond2 = innerRadiusSecond;
                float innerRadiusAfter = Math.max(0.0f, horizontalRadius2 - borderWidthAfter);
                float x12 = horizontalRadius13;
                float y13 = y12;
                Point clipPoint1 = getIntersectionPoint(new Point((double) (horizontalRadius13 - borderWidthBefore), (double) (this.width + y12)), new Point((double) horizontalRadius13, (double) y12), new Point((double) x02, (double) y02), new Point((double) (x02 + 10.0f), (double) y02));
                float y03 = y02;
                float x03 = x02;
                Point clipPoint2 = getIntersectionPoint(new Point((double) (x22 + borderWidthAfter), (double) (y22 + this.width)), new Point((double) x22, (double) y22), new Point((double) x33, (double) y33), new Point((double) (x33 - 10.0f), (double) y33));
                if (clipPoint1.f1280x > clipPoint2.f1280x) {
                    Point clipPoint = getIntersectionPoint(new Point((double) (x12 - borderWidthBefore), (double) (y13 + this.width)), clipPoint1, clipPoint2, new Point((double) (x22 + borderWidthAfter), (double) (y22 + this.width)));
                    pdfCanvas = canvas;
                    x3 = x33;
                    y3 = y33;
                    pdfCanvas.moveTo((double) (x12 - borderWidthBefore), (double) (y13 + this.width)).lineTo(clipPoint.f1280x, clipPoint.f1281y).lineTo((double) (x22 + borderWidthAfter), (double) (y22 + this.width)).lineTo((double) (x12 - borderWidthBefore), (double) (y13 + this.width));
                } else {
                    pdfCanvas = canvas;
                    x3 = x33;
                    y3 = y33;
                    pdfCanvas.moveTo((double) (x12 - borderWidthBefore), (double) (y13 + this.width)).lineTo(clipPoint1.f1280x, clipPoint1.f1281y).lineTo(clipPoint2.f1280x, clipPoint2.f1281y).lineTo((double) (x22 + borderWidthAfter), (double) (y22 + this.width)).lineTo((double) (x12 - borderWidthBefore), (double) (y13 + this.width));
                }
                canvas.clip().endPath();
                float x13 = x12 + innerRadiusBefore;
                float y14 = y13 + widthHalf;
                float x23 = x22 - innerRadiusAfter;
                float y23 = y22 + widthHalf;
                float x04 = x03;
                float y04 = y03;
                Point point = clipPoint2;
                Point point2 = clipPoint1;
                float y34 = y3;
                float x34 = x3;
                float y35 = y34;
                pdfCanvas.moveTo((double) x04, (double) y04).curveTo((double) x04, (double) ((innerRadiusFirst2 * CURV) + y04), (double) (x13 - (innerRadiusBefore * CURV)), (double) y14, (double) x13, (double) y14).lineTo((double) x23, (double) y23).curveTo((double) ((innerRadiusAfter * CURV) + x23), (double) y23, (double) x34, (double) (y34 + (CURV * innerRadiusSecond2)), (double) x34, (double) y35);
                float innerRadiusAfter2 = x04;
                float f = y14;
                float f2 = x23;
                float f3 = x13;
                float f4 = y04;
                float y15 = y35;
                float x05 = x34;
                break;
            case 2:
                float y16 = y1;
                float x24 = x2;
                float y24 = y2;
                float verticalRadius23 = verticalRadius2;
                float x14 = x1;
                float innerRadiusBefore2 = Math.max(0.0f, verticalRadius1 - borderWidthBefore);
                float innerRadiusFirst3 = Math.max(0.0f, horizontalRadius1 - this.width);
                float innerRadiusSecond3 = Math.max(0.0f, horizontalRadius2 - this.width);
                float x06 = x0 - innerRadiusFirst3;
                float y05 = y0 + (borderWidthBefore / 2.0f);
                float x35 = x32 - innerRadiusSecond3;
                float y36 = y32 - (borderWidthAfter / 2.0f);
                float x15 = x14;
                float innerRadiusFirst4 = innerRadiusFirst3;
                float innerRadiusSecond4 = innerRadiusSecond3;
                float y17 = y16;
                float innerRadiusAfter3 = Math.max(0.0f, verticalRadius23 - borderWidthAfter);
                float x07 = x06;
                Point clipPoint12 = getIntersectionPoint(new Point((double) (this.width + x14), (double) (y16 + borderWidthBefore)), new Point((double) x15, (double) y17), new Point((double) x06, (double) y05), new Point((double) x06, (double) (y05 - 10.0f)));
                float y06 = y05;
                float x36 = x35;
                float y37 = y36;
                Point clipPoint22 = getIntersectionPoint(new Point((double) (x24 + this.width), (double) (y24 - borderWidthAfter)), new Point((double) x24, (double) y24), new Point((double) x35, (double) y36), new Point((double) x35, (double) (y36 - 10.0f)));
                if (clipPoint12.f1281y < clipPoint22.f1281y) {
                    Point clipPoint3 = getIntersectionPoint(new Point((double) (x15 + this.width), (double) (y17 + borderWidthBefore)), clipPoint12, clipPoint22, new Point((double) (x24 + this.width), (double) (y24 - borderWidthAfter)));
                    pdfCanvas2 = canvas;
                    pdfCanvas2.moveTo((double) (x15 + this.width), (double) (y17 + borderWidthBefore)).lineTo(clipPoint3.f1280x, clipPoint3.f1281y).lineTo((double) (this.width + x24), (double) (y24 - borderWidthAfter)).lineTo((double) (x15 + this.width), (double) (y17 + borderWidthBefore)).clip().endPath();
                } else {
                    pdfCanvas2 = canvas;
                    pdfCanvas2.moveTo((double) (x15 + this.width), (double) (y17 + borderWidthBefore)).lineTo(clipPoint12.f1280x, clipPoint12.f1281y).lineTo(clipPoint22.f1280x, clipPoint22.f1281y).lineTo((double) (this.width + x24), (double) (y24 - borderWidthAfter)).lineTo((double) (x15 + this.width), (double) (y17 + borderWidthBefore)).clip().endPath();
                }
                canvas.clip().endPath();
                float x16 = x15 + widthHalf;
                float y18 = y17 - innerRadiusBefore2;
                float x25 = x24 + widthHalf;
                float y25 = y24 + innerRadiusAfter3;
                float x08 = x07;
                float y07 = y06;
                Point point3 = clipPoint12;
                Point point4 = clipPoint22;
                float x09 = x08;
                float f5 = innerRadiusBefore2;
                float y38 = y37;
                float x37 = x36;
                pdfCanvas2.moveTo((double) x08, (double) y07).curveTo((double) ((innerRadiusFirst4 * CURV) + x08), (double) y07, (double) x16, (double) (y18 + (innerRadiusBefore2 * CURV)), (double) x16, (double) y18).lineTo((double) x25, (double) y25).curveTo((double) x25, (double) (y25 - (innerRadiusAfter3 * CURV)), (double) (x36 + (innerRadiusSecond4 * CURV)), (double) y38, (double) x37, (double) y38);
                float f6 = y38;
                float y39 = x25;
                float f7 = y25;
                float f8 = x09;
                float x26 = y07;
                float y26 = x37;
                break;
            case 3:
                float x17 = x1;
                float y19 = y1;
                float innerRadiusBefore3 = Math.max(0.0f, horizontalRadius1 - borderWidthBefore);
                float innerRadiusFirst5 = Math.max(0.0f, verticalRadius1 - this.width);
                float innerRadiusSecond5 = Math.max(0.0f, verticalRadius2 - this.width);
                float x010 = x0 + (borderWidthBefore / 2.0f);
                float y08 = y0 + innerRadiusFirst5;
                float y310 = y32 + innerRadiusSecond5;
                float innerRadiusAfter4 = Math.max(0.0f, horizontalRadius2 - borderWidthAfter);
                float innerRadiusSecond6 = innerRadiusSecond5;
                float x18 = x17;
                float y110 = y19;
                float innerRadiusFirst6 = innerRadiusFirst5;
                float x38 = x32 - (borderWidthAfter / 2.0f);
                float y111 = y110;
                Point clipPoint13 = getIntersectionPoint(new Point((double) (x17 + borderWidthBefore), (double) (y19 - this.width)), new Point((double) x17, (double) y110), new Point((double) x010, (double) y08), new Point((double) (x010 - 10.0f), (double) y08));
                float y09 = y08;
                float y311 = y310;
                float x011 = x010;
                float x27 = x2;
                float y27 = y2;
                Point clipPoint23 = getIntersectionPoint(new Point((double) (x2 - borderWidthAfter), (double) (y2 - this.width)), new Point((double) x2, (double) y2), new Point((double) x38, (double) y311), new Point((double) (10.0f + x38), (double) y311));
                if (clipPoint13.f1280x < clipPoint23.f1280x) {
                    Point clipPoint4 = getIntersectionPoint(new Point((double) (x18 + borderWidthBefore), (double) (y111 - this.width)), clipPoint13, clipPoint23, new Point((double) (x27 - borderWidthAfter), (double) (y27 - this.width)));
                    pdfCanvas3 = canvas;
                    pdfCanvas3.moveTo((double) (x18 + borderWidthBefore), (double) (y111 - this.width)).lineTo(clipPoint4.f1280x, clipPoint4.f1281y).lineTo((double) (x27 - borderWidthAfter), (double) (y27 - this.width)).lineTo((double) (x18 + borderWidthBefore), (double) (y111 - this.width));
                } else {
                    pdfCanvas3 = canvas;
                    pdfCanvas3.moveTo((double) (x18 + borderWidthBefore), (double) (y111 - this.width)).lineTo(clipPoint13.f1280x, clipPoint13.f1281y).lineTo(clipPoint23.f1280x, clipPoint23.f1281y).lineTo((double) (x27 - borderWidthAfter), (double) (y27 - this.width)).lineTo((double) (x18 + borderWidthBefore), (double) (y111 - this.width));
                }
                canvas.clip().endPath();
                float x19 = x18 - innerRadiusBefore3;
                float y112 = y111 - widthHalf;
                float x28 = x27 + innerRadiusAfter4;
                float x012 = x011;
                Point point5 = clipPoint13;
                PdfCanvas moveTo = pdfCanvas3.moveTo((double) x012, (double) y09);
                float y010 = y09;
                double d = (double) (x19 + (innerRadiusBefore3 * CURV));
                Point point6 = clipPoint23;
                float x39 = x38;
                double d2 = (double) x19;
                float x110 = x19;
                float y312 = y311;
                float y28 = y27 - widthHalf;
                float x310 = x39;
                double d3 = (double) x310;
                float f9 = innerRadiusBefore3;
                double d4 = (double) x310;
                float x311 = y312;
                moveTo.curveTo((double) x012, (double) (y09 - (innerRadiusFirst6 * CURV)), d, (double) y112, d2, (double) y112).lineTo((double) x28, (double) y28).curveTo((double) (x28 - (innerRadiusAfter4 * CURV)), (double) y28, d3, (double) (y312 - (CURV * innerRadiusSecond6)), d4, (double) x311);
                float f10 = y112;
                float f11 = x012;
                float f12 = x28;
                float f13 = x39;
                float f14 = y28;
                float f15 = x110;
                float x29 = x311;
                float x013 = y010;
                break;
            case 4:
                float innerRadiusBefore4 = Math.max(0.0f, verticalRadius1 - borderWidthBefore);
                float innerRadiusFirst7 = Math.max(0.0f, horizontalRadius1 - this.width);
                float innerRadiusSecond7 = Math.max(0.0f, horizontalRadius2 - this.width);
                float innerRadiusAfter5 = Math.max(0.0f, verticalRadius2 - borderWidthAfter);
                float x014 = x0 + innerRadiusFirst7;
                Side side = borderSide;
                float y011 = y0 - (borderWidthBefore / 2.0f);
                float f16 = horizontalRadius2;
                int i = verticalRadius2;
                float y313 = y32 + (borderWidthAfter / 2.0f);
                int i2 = verticalRadius1;
                int i3 = horizontalRadius1;
                float innerRadiusAfter6 = innerRadiusAfter5;
                float innerRadiusSecond8 = innerRadiusSecond7;
                float x111 = x1;
                float y113 = y1;
                float innerRadiusFirst8 = innerRadiusFirst7;
                Point clipPoint14 = getIntersectionPoint(new Point((double) (x1 - this.width), (double) (y1 - borderWidthBefore)), new Point((double) x1, (double) y1), new Point((double) x014, (double) y011), new Point((double) x014, (double) (y011 + 10.0f)));
                float x312 = x32 + innerRadiusSecond7;
                float y012 = y011;
                float innerRadiusBefore5 = innerRadiusBefore4;
                float x313 = x312;
                float x015 = x014;
                Point clipPoint24 = getIntersectionPoint(new Point((double) (x2 - this.width), (double) (y2 + borderWidthAfter)), new Point((double) x2, (double) y2), new Point((double) x312, (double) y313), new Point((double) x312, (double) (10.0f + y313)));
                if (clipPoint14.f1281y > clipPoint24.f1281y) {
                    Point clipPoint5 = getIntersectionPoint(new Point((double) (x111 - this.width), (double) (y113 - borderWidthBefore)), clipPoint14, clipPoint24, new Point((double) (x2 - this.width), (double) (y2 + borderWidthAfter)));
                    pdfCanvas4 = canvas;
                    pdfCanvas4.moveTo((double) (x111 - this.width), (double) (y113 - borderWidthBefore)).lineTo(clipPoint5.f1280x, clipPoint5.f1281y).lineTo((double) (x2 - this.width), (double) (y2 + borderWidthAfter)).lineTo((double) (x111 - this.width), (double) (y113 - borderWidthBefore));
                } else {
                    pdfCanvas4 = canvas;
                    pdfCanvas4.moveTo((double) (x111 - this.width), (double) (y113 - borderWidthBefore)).lineTo(clipPoint14.f1280x, clipPoint14.f1281y).lineTo(clipPoint24.f1280x, clipPoint24.f1281y).lineTo((double) (x2 - this.width), (double) (y2 + borderWidthAfter)).lineTo((double) (x111 - this.width), (double) (y113 - borderWidthBefore));
                }
                canvas.clip().endPath();
                float x112 = x111 - widthHalf;
                float y114 = y113 + innerRadiusBefore5;
                float y29 = y2 - innerRadiusAfter6;
                float x016 = x015;
                float y013 = y012;
                Point clipPoint15 = clipPoint14;
                Point point7 = clipPoint15;
                float f17 = innerRadiusBefore5;
                double d5 = (double) (y114 - (innerRadiusBefore5 * CURV));
                float x210 = x2 - widthHalf;
                float y314 = y313;
                float x314 = x313;
                Point point8 = clipPoint24;
                pdfCanvas4.moveTo((double) x016, (double) y013).curveTo((double) (x016 - (innerRadiusFirst8 * CURV)), (double) y013, (double) x112, d5, (double) x112, (double) y114).lineTo((double) x210, (double) y29).curveTo((double) x210, (double) ((innerRadiusAfter6 * CURV) + y29), (double) (x313 - (CURV * innerRadiusSecond8)), (double) y314, (double) x314, (double) y314);
                float f18 = x314;
                float f19 = x015;
                float f20 = y114;
                float f21 = x112;
                float x017 = y013;
                float x315 = x210;
                float y115 = y314;
                break;
            default:
                Side side2 = borderSide;
                float f22 = x1;
                float f23 = y1;
                float f24 = x2;
                float f25 = y2;
                int i4 = horizontalRadius1;
                float f26 = horizontalRadius2;
                int i5 = verticalRadius1;
                int i6 = verticalRadius2;
                break;
        }
        canvas.stroke().restoreState();
    }

    /* renamed from: com.itextpdf.layout.borders.Border$1 */
    static /* synthetic */ class C14541 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$layout$borders$Border$Side;

        static {
            int[] iArr = new int[Side.values().length];
            $SwitchMap$com$itextpdf$layout$borders$Border$Side = iArr;
            try {
                iArr[Side.TOP.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$borders$Border$Side[Side.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$borders$Border$Side[Side.BOTTOM.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$borders$Border$Side[Side.LEFT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public float[] getStartingPointsForBorderSide(float x1, float y1, float x2, float y2, Side defaultSide) {
        float widthHalf = this.width / 2.0f;
        switch (C14541.$SwitchMap$com$itextpdf$layout$borders$Border$Side[getBorderSide(x1, y1, x2, y2, defaultSide).ordinal()]) {
            case 1:
                y1 += widthHalf;
                y2 += widthHalf;
                break;
            case 2:
                x1 += widthHalf;
                x2 += widthHalf;
                break;
            case 3:
                y1 -= widthHalf;
                y2 -= widthHalf;
                break;
            case 4:
                x1 -= widthHalf;
                x2 -= widthHalf;
                break;
        }
        return new float[]{x1, y1, x2, y2};
    }
}
