package com.itextpdf.svg.renderers.path.impl;

import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.svg.exceptions.SvgExceptionMessageConstant;
import com.itextpdf.svg.exceptions.SvgLogMessageConstant;
import com.itextpdf.svg.exceptions.SvgProcessingException;
import java.util.Arrays;
import java.util.List;

public class EllipticalCurveTo extends AbstractPathShape {
    static final int ARGUMENT_SIZE = 7;
    private static final double EPS = 1.0E-5d;
    private Point startPoint;

    public EllipticalCurveTo() {
        this(false);
    }

    public EllipticalCurveTo(boolean relative) {
        super(relative);
    }

    public void setCoordinates(String[] inputCoordinates, Point previous) {
        this.startPoint = previous;
        if (inputCoordinates.length >= 7) {
            this.coordinates = new String[7];
            System.arraycopy(inputCoordinates, 0, this.coordinates, 0, 7);
            double[] initialPoint = {previous.getX(), previous.getY()};
            if (isRelative()) {
                String[] absoluteEndPoint = this.copier.makeCoordinatesAbsolute(new String[]{inputCoordinates[5], inputCoordinates[6]}, initialPoint);
                this.coordinates[5] = absoluteEndPoint[0];
                this.coordinates[6] = absoluteEndPoint[1];
                return;
            }
            return;
        }
        throw new IllegalArgumentException(MessageFormatUtil.format(SvgLogMessageConstant.ARC_TO_EXPECTS_FOLLOWING_PARAMETERS_GOT_0, Arrays.toString(inputCoordinates)));
    }

    public void draw(PdfCanvas canvas) {
        Point end;
        PdfCanvas pdfCanvas;
        EllipseArc arc;
        double rotation;
        PdfCanvas pdfCanvas2 = canvas;
        Point start = new Point(this.startPoint.f1280x * 0.75d, this.startPoint.f1281y * 0.75d);
        double rx = (double) Math.abs(CssUtils.parseAbsoluteLength(this.coordinates[0]));
        double ry = (double) Math.abs(CssUtils.parseAbsoluteLength(this.coordinates[1]));
        double rotation2 = Math.toRadians(Double.parseDouble(this.coordinates[2]) % 360.0d);
        boolean largeArc = !CssUtils.compareFloats(CssUtils.parseFloat(this.coordinates[3]).floatValue(), 0.0f);
        boolean sweep = !CssUtils.compareFloats(CssUtils.parseFloat(this.coordinates[4]).floatValue(), 0.0f);
        double rx2 = rx;
        Point end2 = new Point((double) CssUtils.parseAbsoluteLength(this.coordinates[5]), (double) CssUtils.parseAbsoluteLength(this.coordinates[6]));
        if (!CssUtils.compareFloats(start.f1280x, end2.f1280x) || !CssUtils.compareFloats(start.f1281y, end2.f1281y)) {
            double rx3 = rx2;
            if (CssUtils.compareFloats(rx3, 0.0d)) {
                pdfCanvas = pdfCanvas2;
                Point point = start;
                end = end2;
                double d = rx3;
                double d2 = rotation2;
                double d3 = ry;
            } else if (CssUtils.compareFloats(ry, 0.0d)) {
                pdfCanvas = pdfCanvas2;
                Point point2 = start;
                end = end2;
                double d4 = rx3;
                double d5 = rotation2;
                double d6 = ry;
            } else {
                if (CssUtils.compareFloats(rotation2, 0.0d)) {
                    rotation = rotation2;
                    double d7 = ry;
                    arc = EllipseArc.getEllipse(start, end2, rx3, ry, sweep, largeArc);
                } else {
                    rotation = rotation2;
                    AffineTransform normalizer = AffineTransform.getRotateInstance(-rotation);
                    normalizer.translate(-start.f1280x, -start.f1281y);
                    Point newArcEnd = normalizer.transform(end2, (Point) null);
                    newArcEnd.translate(start.f1280x, start.f1281y);
                    arc = EllipseArc.getEllipse(start, newArcEnd, rx3, ry, sweep, largeArc);
                }
                Point point3 = start;
                double d8 = rx3;
                Point end3 = end2;
                double rotation3 = rotation;
                Point[][] points = makePoints(PdfCanvas.bezierArc(arc.f1662ll.f1280x, arc.f1662ll.f1281y, arc.f1663ur.f1280x, arc.f1663ur.f1281y, arc.startAng, arc.extent));
                if (sweep) {
                    Point[][] points2 = rotate(points, rotation3, points[0][0]);
                    for (int i = 0; i < points2.length; i++) {
                        drawCurve(canvas, points2[i][1], points2[i][2], points2[i][3]);
                    }
                    PdfCanvas pdfCanvas3 = canvas;
                    Point point4 = end3;
                    return;
                }
                PdfCanvas pdfCanvas4 = canvas;
                Point[][] points3 = rotate(points, rotation3, points[points.length - 1][3]);
                for (int i2 = points3.length - 1; i2 >= 0; i2--) {
                    drawCurve(pdfCanvas4, points3[i2][2], points3[i2][1], points3[i2][0]);
                }
                Point point5 = end3;
                return;
            }
            Point end4 = end;
            pdfCanvas.lineTo(end4.f1280x, end4.f1281y);
        }
    }

    static Point[][] rotate(Point[][] list, double rotation, Point rotator) {
        if (CssUtils.compareFloats(rotation, 0.0d)) {
            return list;
        }
        Point[][] result = new Point[list.length][];
        AffineTransform transRotTrans = AffineTransform.getRotateInstance(rotation, rotator.f1280x, rotator.f1281y);
        for (int i = 0; i < list.length; i++) {
            Point[] input = list[i];
            Point[] row = new Point[input.length];
            for (int j = 0; j < input.length; j++) {
                row[j] = transRotTrans.transform(input[j], (Point) null);
            }
            result[i] = row;
        }
        return result;
    }

    /* access modifiers changed from: package-private */
    public String[] getCoordinates() {
        return this.coordinates;
    }

    private static void drawCurve(PdfCanvas canvas, Point cp1, Point cp2, Point end) {
        Point point = cp1;
        Point point2 = cp2;
        Point point3 = end;
        canvas.curveTo(point.f1280x, point.f1281y, point2.f1280x, point2.f1281y, point3.f1280x, point3.f1281y);
    }

    private Point[][] makePoints(List<double[]> input) {
        Point[][] result = new Point[input.size()][];
        for (int i = 0; i < input.size(); i++) {
            result[i] = new Point[(input.get(i).length / 2)];
            for (int j = 0; j < input.get(i).length; j += 2) {
                result[i][j / 2] = new Point(input.get(i)[j], input.get(i)[j + 1]);
            }
        }
        return result;
    }

    static class EllipseArc {
        final double extent;

        /* renamed from: ll */
        final Point f1662ll;
        final double startAng;

        /* renamed from: ur */
        final Point f1663ur;

        EllipseArc(Point center, double a, double b, double startAng2, double extent2) {
            this.f1662ll = new Point(center.f1280x - a, center.f1281y - b);
            this.f1663ur = new Point(center.f1280x + a, center.f1281y + b);
            this.startAng = startAng2;
            this.extent = extent2;
        }

        static EllipseArc getEllipse(Point start, Point end, double a, double b, boolean sweep, boolean largeArc) {
            Point point = start;
            Point point2 = end;
            double r1 = (point.f1280x - point2.f1280x) / (-2.0d * a);
            double r2 = (point.f1281y - point2.f1281y) / (2.0d * b);
            double factor = Math.sqrt((r1 * r1) + (r2 * r2));
            if (factor > 1.0d) {
                return getEllipse(start, end, a * factor, b * factor, sweep, largeArc);
            }
            double between1 = Math.atan(r1 / r2);
            double between2 = Math.asin(factor);
            EllipseArc result = calculatePossibleMiddle(start, end, a, b, between1 + between2, sweep, largeArc);
            if (result != null) {
                return result;
            }
            EllipseArc result2 = calculatePossibleMiddle(start, end, a, b, (between1 + 3.141592653589793d) - between2, sweep, largeArc);
            if (result2 != null) {
                return result2;
            }
            EllipseArc result3 = calculatePossibleMiddle(start, end, a, b, between1 + 3.141592653589793d + between2, sweep, largeArc);
            if (result3 != null) {
                return result3;
            }
            EllipseArc result4 = calculatePossibleMiddle(start, end, a, b, between1 - between2, sweep, largeArc);
            if (result4 != null) {
                return result4;
            }
            throw new SvgProcessingException(SvgExceptionMessageConstant.COULD_NOT_DETERMINE_MIDDLE_POINT_OF_ELLIPTICAL_ARC);
        }

        static EllipseArc calculatePossibleMiddle(Point start, Point end, double a, double b, double startToCenterAngle, boolean sweep, boolean largeArc) {
            Point point = start;
            Point point2 = end;
            double x0 = point.f1280x - (Math.cos(startToCenterAngle) * a);
            double y0 = point.f1281y - (Math.sin(startToCenterAngle) * b);
            Point center = new Point(x0, y0);
            double check = Math.pow((point2.f1280x - center.f1280x) / a, 2.0d) + Math.pow((point2.f1281y - center.f1281y) / b, 2.0d);
            if (CssUtils.compareFloats(check, 1.0d)) {
                Point point3 = center;
                double d = a;
                double d2 = b;
                double theta1 = calculateAngle(start, point3, d, d2);
                double theta2 = calculateAngle(end, point3, d, d2);
                double startAngl = 0.0d;
                double extent2 = 0.0d;
                long deltaTheta = Math.abs(Math.round(theta2 - theta1));
                if (largeArc) {
                    if (sweep) {
                        if (theta2 > theta1 && deltaTheta >= 180) {
                            startAngl = theta1;
                            extent2 = theta2 - theta1;
                        }
                        if (theta1 > theta2 && deltaTheta <= 180) {
                            startAngl = theta1;
                            extent2 = (360.0d - theta1) + theta2;
                        }
                    } else {
                        if (theta2 > theta1 && deltaTheta <= 180) {
                            startAngl = theta2;
                            extent2 = (360.0d - theta2) + theta1;
                        }
                        if (theta1 > theta2 && deltaTheta >= 180) {
                            startAngl = theta2;
                            extent2 = theta1 - theta2;
                        }
                    }
                } else if (sweep) {
                    if (theta2 > theta1 && deltaTheta <= 180) {
                        startAngl = theta1;
                        extent2 = theta2 - theta1;
                    }
                    if (theta1 > theta2 && deltaTheta >= 180) {
                        startAngl = theta1;
                        extent2 = (360.0d - theta1) + theta2;
                    }
                } else {
                    if (theta2 > theta1 && deltaTheta >= 180) {
                        startAngl = theta2;
                        extent2 = (360.0d - theta2) + theta1;
                    }
                    if (theta1 > theta2 && deltaTheta <= 180) {
                        startAngl = theta2;
                        extent2 = theta1 - theta2;
                    }
                }
                if (startAngl < 0.0d || extent2 <= 0.0d) {
                    Point point4 = center;
                    double d3 = y0;
                    double d4 = x0;
                    return null;
                }
                double d5 = check;
                Point point5 = center;
                double d6 = y0;
                double d7 = x0;
                return new EllipseArc(center, a, b, startAngl, extent2);
            }
            Point point6 = center;
            double d8 = y0;
            double d9 = x0;
            return null;
        }

        static double calculateAngle(Point pt, Point center, double a, double b) {
            double result = Math.pow((pt.f1280x - center.f1280x) / a, 2.0d) + Math.pow((pt.f1281y - center.f1281y) / b, 2.0d);
            double sin = (pt.f1281y - center.f1281y) / b;
            double cos = Math.max(Math.min((pt.f1280x - center.f1280x) / a, 1.0d), -1.0d);
            if ((cos >= 0.0d && sin >= 0.0d) || (cos < 0.0d && sin >= 0.0d)) {
                result = toDegrees(Math.acos(cos));
            }
            if ((cos < 0.0d || sin >= 0.0d) && (cos >= 0.0d || sin >= 0.0d)) {
                return result;
            }
            return 360.0d - toDegrees(Math.acos(cos));
        }

        static double toDegrees(double radians) {
            return (180.0d * radians) / 3.141592653589793d;
        }
    }

    public Rectangle getPathShapeRectangle(Point lastPoint) {
        double[] points = getEllipticalArcMinMaxPoints(lastPoint.getX(), lastPoint.getY(), getCoordinate(0), getCoordinate(1), getCoordinate(2), getCoordinate(3) != 0.0d, getCoordinate(4) != 0.0d, getCoordinate(5), getCoordinate(6));
        return new Rectangle((float) CssUtils.convertPxToPts(points[0]), (float) CssUtils.convertPxToPts(points[1]), (float) CssUtils.convertPxToPts(points[2] - points[0]), (float) CssUtils.convertPxToPts(points[3] - points[1]));
    }

    private double getCoordinate(int index) {
        return CssUtils.parseDouble(this.coordinates[index]).doubleValue();
    }

    private double[] getEllipticalArcMinMaxPoints(double x1, double y1, double rx, double ry, double phi, boolean largeArc, boolean sweep, double x2, double y2) {
        double d;
        double d2;
        double d3;
        int i;
        double angle2;
        double temp;
        double d4;
        double d5;
        double d6 = x1;
        double d7 = y1;
        double d8 = x2;
        double d9 = y2;
        double phi2 = Math.toRadians(phi);
        double rx2 = Math.abs(rx);
        double ry2 = Math.abs(ry);
        if (rx2 == 0.0d) {
            i = 4;
            d2 = d6;
            d = d9;
            d3 = d7;
        } else if (ry2 == 0.0d) {
            i = 4;
            d2 = d6;
            d = d9;
            d3 = d7;
        } else {
            double[] centerCoordinatesAndRxRy = getEllipseCenterCoordinates(x1, y1, rx2, ry2, phi2, largeArc, sweep, x2, y2);
            if (centerCoordinatesAndRxRy == null) {
                double d10 = x1;
                double d11 = x2;
                double d12 = y1;
                double d13 = y2;
                return new double[]{Math.min(d10, d11), Math.min(d12, d13), Math.max(d10, d11), Math.max(d12, d13)};
            }
            double d14 = y1;
            double d15 = x2;
            double d16 = y2;
            double cx = centerCoordinatesAndRxRy[0];
            double cy = centerCoordinatesAndRxRy[1];
            double[][] extremeCoordinatesAndThetas = getExtremeCoordinatesAndAngles(centerCoordinatesAndRxRy[2], centerCoordinatesAndRxRy[3], phi2, cx, cy);
            double[] extremeCoordinates = extremeCoordinatesAndThetas[0];
            double[] angles = extremeCoordinatesAndThetas[1];
            double xMin = extremeCoordinates[0];
            double yMin = extremeCoordinates[1];
            double xMax = extremeCoordinates[2];
            double yMax = extremeCoordinates[3];
            double xMinAngle = angles[0];
            double yMinAngle = angles[1];
            double xMaxAngle = angles[2];
            double yMaxAngle = angles[3];
            double d17 = y1;
            double angle1 = getAngleBetweenVectors(x1 - cx, d17 - cy);
            double d18 = cx;
            double d19 = y2;
            double angle22 = getAngleBetweenVectors(x2 - cx, d19 - cy);
            if (!sweep) {
                double temp2 = angle1;
                angle1 = angle22;
                angle22 = temp2;
            }
            boolean otherArc = angle1 > angle22;
            if (otherArc) {
                double temp3 = angle1;
                double angle12 = angle22;
                double angle23 = temp3;
                temp = angle12;
                angle2 = angle23;
            } else {
                temp = angle1;
                angle2 = angle22;
            }
            double[][] dArr = extremeCoordinatesAndThetas;
            double[] dArr2 = extremeCoordinates;
            double d20 = x2;
            double[] dArr3 = angles;
            double[] dArr4 = centerCoordinatesAndRxRy;
            double d21 = d17;
            if (!isPointOnTheArc(xMinAngle, temp, angle2, otherArc)) {
                d4 = x1;
                xMin = Math.min(d4, d20);
            } else {
                d4 = x1;
            }
            double d22 = d4;
            if (!isPointOnTheArc(xMaxAngle, temp, angle2, otherArc)) {
                xMax = Math.max(d22, d20);
            }
            if (!isPointOnTheArc(yMinAngle, temp, angle2, otherArc)) {
                d5 = y1;
                yMin = Math.min(d5, d19);
            } else {
                d5 = y1;
            }
            double d23 = d5;
            if (!isPointOnTheArc(yMaxAngle, temp, angle2, otherArc)) {
                yMax = Math.max(d23, d19);
            }
            return new double[]{xMin, yMin, xMax, yMax};
        }
        double[] dArr5 = new double[i];
        double d24 = x2;
        double d25 = d3;
        dArr5[0] = Math.min(d2, d24);
        dArr5[1] = Math.min(d25, d);
        dArr5[2] = Math.max(d2, d24);
        dArr5[3] = Math.max(d25, d);
        return dArr5;
    }

    private double[] getEllipseCenterCoordinates(double x1, double y1, double rx, double ry, double phi, boolean largeArc, boolean sweep, double x2, double y2) {
        double ratio;
        double ry2;
        double x1Prime = ((Math.cos(phi) * (x1 - x2)) / 2.0d) + ((Math.sin(phi) * (y1 - y2)) / 2.0d);
        double y1Prime = (((-Math.sin(phi)) * (x1 - x2)) / 2.0d) + ((Math.cos(phi) * (y1 - y2)) / 2.0d);
        double radicant = (((((rx * rx) * ry) * ry) - (((rx * rx) * y1Prime) * y1Prime)) - (((ry * ry) * x1Prime) * x1Prime)) / ((((rx * rx) * y1Prime) * y1Prime) + (((ry * ry) * x1Prime) * x1Prime));
        double cxPrime = 0.0d;
        double cyPrime = 0.0d;
        if (radicant < 0.0d) {
            double ratio2 = rx / ry;
            double radicant2 = (y1Prime * y1Prime) + ((x1Prime * x1Prime) / (ratio2 * ratio2));
            if (radicant2 < 0.0d) {
                return null;
            }
            double ry3 = Math.sqrt(radicant2);
            ratio = ratio2 * ry3;
            ry2 = ry3;
            boolean z = largeArc;
            boolean z2 = sweep;
        } else {
            double factor = (largeArc == sweep ? -1.0d : 1.0d) * Math.sqrt(radicant);
            cxPrime = ((factor * rx) * y1Prime) / ry;
            cyPrime = (((-factor) * ry) * x1Prime) / rx;
            ratio = rx;
            ry2 = ry;
        }
        double d = x1Prime;
        return new double[]{((Math.cos(phi) * cxPrime) - (Math.sin(phi) * cyPrime)) + ((x1 + x2) / 2.0d), (Math.sin(phi) * cxPrime) + (Math.cos(phi) * cyPrime) + ((y1 + y2) / 2.0d), ratio, ry2};
    }

    private double[][] getExtremeCoordinatesAndAngles(double rx, double ry, double phi, double cx, double cy) {
        double yMax;
        double yMin;
        double xMax;
        double xMin;
        double txMax;
        double tempY;
        double yMaxAngle;
        double xMinAngle;
        double d = rx;
        double d2 = ry;
        double d3 = phi;
        if (anglesAreEquals(d3, 0.0d) || anglesAreEquals(d3, 3.141592653589793d)) {
            xMin = cx - d;
            xMinAngle = getAngleBetweenVectors(-d, 0.0d);
            xMax = cx + d;
            tempY = getAngleBetweenVectors(d, 0.0d);
            yMin = cy - d2;
            txMax = getAngleBetweenVectors(0.0d, -d2);
            yMax = cy + d2;
            yMaxAngle = getAngleBetweenVectors(0.0d, d2);
        } else if (anglesAreEquals(d3, 1.5707963267948966d) || anglesAreEquals(d3, 4.71238898038469d)) {
            xMin = cx - d2;
            xMinAngle = getAngleBetweenVectors(-d2, 0.0d);
            xMax = cx + d2;
            tempY = getAngleBetweenVectors(d2, 0.0d);
            yMin = cy - d;
            txMax = getAngleBetweenVectors(0.0d, -d);
            yMax = cy + d;
            yMaxAngle = getAngleBetweenVectors(0.0d, d);
        } else {
            double txMin = -Math.atan((Math.tan(phi) * d2) / d);
            double txMax2 = 3.141592653589793d - Math.atan((Math.tan(phi) * d2) / d);
            xMin = (cx + ((Math.cos(txMin) * d) * Math.cos(phi))) - ((Math.sin(txMin) * d2) * Math.sin(phi));
            xMax = (cx + ((Math.cos(txMax2) * d) * Math.cos(phi))) - ((Math.sin(txMax2) * d2) * Math.sin(phi));
            if (xMin > xMax) {
                double temp = xMin;
                xMin = xMax;
                xMax = temp;
                double temp2 = txMin;
                txMin = txMax2;
                txMax2 = temp2;
            }
            double xMinAngle2 = getAngleBetweenVectors(xMin - cx, ((cy + ((Math.cos(txMin) * d) * Math.sin(phi))) + ((Math.sin(txMin) * d2) * Math.cos(phi))) - cy);
            double tempY2 = xMinAngle2;
            double d4 = txMin;
            double xMaxAngle = getAngleBetweenVectors(xMax - cx, ((cy + ((Math.cos(txMax2) * d) * Math.sin(phi))) + ((Math.sin(txMax2) * d2) * Math.cos(phi))) - cy);
            double tyMin = Math.atan(d2 / (Math.tan(phi) * d));
            double tyMax = Math.atan(d2 / (Math.tan(phi) * d)) + 3.141592653589793d;
            yMin = cy + (Math.cos(tyMin) * d * Math.sin(phi)) + (Math.sin(tyMin) * d2 * Math.cos(phi));
            yMax = cy + (Math.cos(tyMax) * d * Math.sin(phi)) + (Math.sin(tyMax) * d2 * Math.cos(phi));
            if (yMin > yMax) {
                double temp3 = yMin;
                yMin = yMax;
                yMax = temp3;
                double temp4 = tyMin;
                tyMin = tyMax;
                tyMax = temp4;
            }
            double xMaxAngle2 = xMaxAngle;
            double d5 = tyMin;
            double yMinAngle = getAngleBetweenVectors(((cx + ((Math.cos(tyMin) * d) * Math.cos(phi))) - ((Math.sin(tyMin) * d2) * Math.sin(phi))) - cx, yMin - cy);
            double tmpX = (cx + ((Math.cos(tyMax) * d) * Math.cos(phi))) - ((Math.sin(tyMax) * d2) * Math.sin(phi));
            double tmpX2 = yMinAngle;
            double d6 = tmpX;
            yMaxAngle = getAngleBetweenVectors(tmpX - cx, yMax - cy);
            xMinAngle = tempY2;
            txMax = tmpX2;
            tempY = xMaxAngle2;
        }
        return new double[][]{new double[]{xMin, yMin, xMax, yMax}, new double[]{xMinAngle, txMax, tempY, yMaxAngle}};
    }

    private boolean isPointOnTheArc(double pointAngle, double angle1, double angle2, boolean otherArc) {
        if (otherArc != (angle1 <= pointAngle && angle2 >= pointAngle)) {
            return true;
        }
        return false;
    }

    private double getAngleBetweenVectors(double bx, double by) {
        return (((by > 0.0d ? 1.0d : -1.0d) * Math.acos(bx / Math.sqrt((bx * bx) + (by * by)))) + 6.283185307179586d) % 6.283185307179586d;
    }

    private boolean anglesAreEquals(double angle1, double angle2) {
        return Math.abs(angle1 - angle2) < 1.0E-5d;
    }
}
