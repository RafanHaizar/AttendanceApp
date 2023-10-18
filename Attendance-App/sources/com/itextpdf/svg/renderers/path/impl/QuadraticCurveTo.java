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

public class QuadraticCurveTo extends AbstractPathShape implements IControlPointCurve {
    static final int ARGUMENT_SIZE = 4;

    public QuadraticCurveTo() {
        this(false);
    }

    public QuadraticCurveTo(boolean relative) {
        this(relative, new DefaultOperatorConverter());
    }

    public QuadraticCurveTo(boolean relative, IOperatorConverter copier) {
        super(relative, copier);
    }

    public void draw(PdfCanvas canvas) {
        PdfCanvas pdfCanvas = canvas;
        pdfCanvas.curveTo((double) CssUtils.parseAbsoluteLength(this.coordinates[0]), (double) CssUtils.parseAbsoluteLength(this.coordinates[1]), (double) CssUtils.parseAbsoluteLength(this.coordinates[2]), (double) CssUtils.parseAbsoluteLength(this.coordinates[3]));
    }

    public void setCoordinates(String[] inputCoordinates, Point startPoint) {
        if (inputCoordinates.length >= 4) {
            this.coordinates = new String[4];
            System.arraycopy(inputCoordinates, 0, this.coordinates, 0, 4);
            double[] initialPoint = {startPoint.getX(), startPoint.getY()};
            if (isRelative()) {
                this.coordinates = this.copier.makeCoordinatesAbsolute(this.coordinates, initialPoint);
                return;
            }
            return;
        }
        throw new IllegalArgumentException(MessageFormatUtil.format(SvgExceptionMessageConstant.QUADRATIC_CURVE_TO_EXPECTS_FOLLOWING_PARAMETERS_GOT_0, Arrays.toString(this.coordinates)));
    }

    public Point getLastControlPoint() {
        return createPoint(this.coordinates[0], this.coordinates[1]);
    }

    public Rectangle getPathShapeRectangle(Point lastPoint) {
        Point controlPoint = getLastControlPoint();
        Point endingPoint = getEndingPoint();
        double[] points = getBezierMinMaxPoints(lastPoint.getX(), lastPoint.getY(), controlPoint.getX(), controlPoint.getY(), endingPoint.getX(), endingPoint.getY());
        return new Rectangle((float) CssUtils.convertPxToPts(points[0]), (float) CssUtils.convertPxToPts(points[1]), (float) CssUtils.convertPxToPts(points[2] - points[0]), (float) CssUtils.convertPxToPts(points[3] - points[1]));
    }

    private static double[] getBezierMinMaxPoints(double x0, double y0, double x1, double y1, double x2, double y2) {
        double d = x0;
        double d2 = y0;
        double d3 = x2;
        double d4 = y2;
        double xMin = Math.min(d, d3);
        double yMin = Math.min(d2, d4);
        double xMax = Math.max(d, d3);
        double yMax = Math.max(d2, d4);
        double[] extremeTValues = getExtremeTValues(x0, y0, x1, y1, x2, y2);
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
            double[] extremeTValues4 = extremeTValues3;
            double yMax5 = yMax3;
            double yMax6 = yMax2;
            int i2 = i;
            double xValue = calculateExtremeCoordinate(yMax4, x0, x1, x2);
            double yValue2 = calculateExtremeCoordinate(yMax4, y0, y1, y2);
            yMax2 = Math.min(xValue, yMax6);
            xMax3 = Math.min(yValue2, xMax3);
            xMax2 = Math.max(xValue, xMax2);
            yValue = Math.max(yValue2, yMax5);
            i = i2 + 1;
            double d7 = x2;
            extremeTValues = extremeTValues4;
            length = length;
            double d8 = y2;
        }
        double[] dArr = extremeTValues;
        return new double[]{yMax2, xMax3, xMax2, yValue};
    }

    private static double[] getExtremeTValues(double x0, double y0, double x1, double y1, double x2, double y2) {
        List<Double> tValuesList = new ArrayList<>();
        addTValueToList(getTValue(x0, x1, x2), tValuesList);
        addTValueToList(getTValue(y0, y1, y2), tValuesList);
        double[] tValuesArray = new double[tValuesList.size()];
        for (int i = 0; i < tValuesList.size(); i++) {
            tValuesArray[i] = tValuesList.get(i).doubleValue();
        }
        return tValuesArray;
    }

    private static void addTValueToList(double t, List<Double> tValuesList) {
        if (0.0d <= t && t <= 1.0d) {
            tValuesList.add(Double.valueOf(t));
        }
    }

    private static double getTValue(double p0, double p1, double p2) {
        return (-((p1 * 2.0d) - (p0 * 2.0d))) / (2.0d * ((p0 - (p1 * 2.0d)) + p2));
    }

    private static double calculateExtremeCoordinate(double t, double p0, double p1, double p2) {
        double minusT = 1.0d - t;
        return (minusT * minusT * p0) + (2.0d * minusT * t * p1) + (t * t * p2);
    }
}
