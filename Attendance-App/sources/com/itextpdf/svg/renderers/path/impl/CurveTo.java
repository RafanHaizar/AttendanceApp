package com.itextpdf.svg.renderers.path.impl;

import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.svg.exceptions.SvgExceptionMessageConstant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CurveTo extends AbstractPathShape implements IControlPointCurve {
    static final int ARGUMENT_SIZE = 6;
    private static double ZERO_EPSILON = 1.0E-12d;

    public CurveTo() {
        this(false);
    }

    public CurveTo(boolean relative) {
        this(relative, new DefaultOperatorConverter());
    }

    public CurveTo(boolean relative, IOperatorConverter copier) {
        super(relative, copier);
    }

    public void draw(PdfCanvas canvas) {
        float x1 = CssUtils.parseAbsoluteLength(this.coordinates[0]);
        float y1 = CssUtils.parseAbsoluteLength(this.coordinates[1]);
        float x2 = CssUtils.parseAbsoluteLength(this.coordinates[2]);
        float f = x1;
        float f2 = y1;
        float f3 = x2;
        PdfCanvas pdfCanvas = canvas;
        pdfCanvas.curveTo((double) x1, (double) y1, (double) x2, (double) CssUtils.parseAbsoluteLength(this.coordinates[3]), (double) CssUtils.parseAbsoluteLength(this.coordinates[4]), (double) CssUtils.parseAbsoluteLength(this.coordinates[5]));
    }

    public void setCoordinates(String[] inputCoordinates, Point startPoint) {
        if (inputCoordinates.length >= 6) {
            this.coordinates = new String[6];
            System.arraycopy(inputCoordinates, 0, this.coordinates, 0, 6);
            double[] initialPoint = {startPoint.getX(), startPoint.getY()};
            if (isRelative()) {
                this.coordinates = this.copier.makeCoordinatesAbsolute(this.coordinates, initialPoint);
                return;
            }
            return;
        }
        throw new IllegalArgumentException(MessageFormatUtil.format(SvgExceptionMessageConstant.CURVE_TO_EXPECTS_FOLLOWING_PARAMETERS_GOT_0, Arrays.toString(inputCoordinates)));
    }

    public Point getLastControlPoint() {
        return createPoint(this.coordinates[2], this.coordinates[3]);
    }

    public Rectangle getPathShapeRectangle(Point lastPoint) {
        Point firstControlPoint = getFirstControlPoint();
        Point lastControlPoint = getLastControlPoint();
        Point endingPoint = getEndingPoint();
        double[] points = getBezierMinMaxPoints(lastPoint.getX(), lastPoint.getY(), firstControlPoint.getX(), firstControlPoint.getY(), lastControlPoint.getX(), lastControlPoint.getY(), endingPoint.getX(), endingPoint.getY());
        return new Rectangle((float) CssUtils.convertPxToPts(points[0]), (float) CssUtils.convertPxToPts(points[1]), (float) CssUtils.convertPxToPts(points[2] - points[0]), (float) CssUtils.convertPxToPts(points[3] - points[1]));
    }

    private Point getFirstControlPoint() {
        return createPoint(this.coordinates[0], this.coordinates[1]);
    }

    private static double[] getBezierMinMaxPoints(double x0, double y0, double x1, double y1, double x2, double y2, double x3, double y3) {
        double d = x0;
        double d2 = y0;
        double d3 = x3;
        double d4 = y3;
        double xMin = Math.min(d, d3);
        double yMin = Math.min(d2, d4);
        double xMax = Math.max(d, d3);
        double yMax = Math.max(d2, d4);
        double[] extremeTValues = getTValuesInExtremePoints(x0, y0, x1, y1, x2, y2, x3, y3);
        int length = extremeTValues.length;
        int i = 0;
        double d5 = xMin;
        double yValue = yMax;
        double yMax2 = d5;
        double d6 = yMin;
        double xMax2 = xMax;
        double xMax3 = d6;
        while (i < length) {
            double t = extremeTValues[i];
            double[] extremeTValues2 = extremeTValues;
            double yMax3 = yValue;
            double yMax4 = t;
            double[] extremeTValues3 = extremeTValues2;
            double yMax5 = yMax3;
            int i2 = i;
            double xValue = calculateExtremeCoordinate(yMax4, x0, x1, x2, x3);
            double yValue2 = calculateExtremeCoordinate(yMax4, y0, y1, y2, y3);
            yMax2 = Math.min(xValue, yMax2);
            xMax3 = Math.min(yValue2, xMax3);
            xMax2 = Math.max(xValue, xMax2);
            yValue = Math.max(yValue2, yMax5);
            i = i2 + 1;
            double d7 = x3;
            double d8 = y3;
            length = length;
            extremeTValues = extremeTValues3;
        }
        double[] dArr = extremeTValues;
        return new double[]{yMax2, xMax3, xMax2, yValue};
    }

    private static double[] getTValuesInExtremePoints(double x0, double y0, double x1, double y1, double x2, double y2, double x3, double y3) {
        List<Double> tValuesList = new ArrayList<>(calculateTValues(x0, x1, x2, x3));
        tValuesList.addAll(calculateTValues(y0, y1, y2, y3));
        double[] tValuesArray = new double[tValuesList.size()];
        for (int i = 0; i < tValuesList.size(); i++) {
            tValuesArray[i] = tValuesList.get(i).doubleValue();
        }
        return tValuesArray;
    }

    private static List<Double> calculateTValues(double p0, double p1, double p2, double p3) {
        double d = p0;
        List<Double> tValuesList = new ArrayList<>();
        double a = ((((-d) + (p1 * 3.0d)) - (p2 * 3.0d)) + p3) * 3.0d;
        double b = (((d * 3.0d) - (6.0d * p1)) + (p2 * 3.0d)) * 2.0d;
        double c = (p1 * 3.0d) - (3.0d * d);
        if (Math.abs(a) >= ZERO_EPSILON) {
            double discriminant = (b * b) - ((4.0d * c) * a);
            if (discriminant > 0.0d || Math.abs(discriminant) >= ZERO_EPSILON) {
                double discriminantSqrt = Math.sqrt(discriminant);
                addTValueToList(((-b) + discriminantSqrt) / (a * 2.0d), tValuesList);
                addTValueToList(((-b) - discriminantSqrt) / (2.0d * a), tValuesList);
            } else {
                addTValueToList((-b) / (2.0d * a), tValuesList);
            }
        } else if (Math.abs(b) >= ZERO_EPSILON) {
            addTValueToList((-c) / b, tValuesList);
        }
        return tValuesList;
    }

    private static void addTValueToList(double t, List<Double> tValuesList) {
        if (0.0d <= t && t <= 1.0d) {
            tValuesList.add(Double.valueOf(t));
        }
    }

    private static double calculateExtremeCoordinate(double t, double p0, double p1, double p2, double p3) {
        double minusT = 1.0d - t;
        return (minusT * minusT * minusT * p0) + (minusT * 3.0d * minusT * t * p1) + (3.0d * minusT * t * t * p2) + (t * t * t * p3);
    }
}
