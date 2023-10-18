package com.itextpdf.kernel.geom;

import java.util.ArrayList;
import java.util.List;

public class BezierCurve implements IShape {
    public static double curveCollinearityEpsilon = 1.0E-30d;
    public static double distanceToleranceManhattan = 0.4d;
    public static double distanceToleranceSquare = 0.025d;
    private static final long serialVersionUID = -2158496565016776969L;
    private final List<Point> controlPoints;

    public BezierCurve(List<Point> controlPoints2) {
        this.controlPoints = new ArrayList(controlPoints2);
    }

    public List<Point> getBasePoints() {
        return this.controlPoints;
    }

    public List<Point> getPiecewiseLinearApproximation() {
        List<Point> points = new ArrayList<>();
        List<Point> list = points;
        points.add(this.controlPoints.get(0));
        double x = this.controlPoints.get(0).getX();
        List<Point> points2 = points;
        double d = x;
        recursiveApproximation(d, this.controlPoints.get(0).getY(), this.controlPoints.get(1).getX(), this.controlPoints.get(1).getY(), this.controlPoints.get(2).getX(), this.controlPoints.get(2).getY(), this.controlPoints.get(3).getX(), this.controlPoints.get(3).getY(), list);
        List<Point> list2 = this.controlPoints;
        List<Point> points3 = points2;
        points3.add(list2.get(list2.size() - 1));
        return points3;
    }

    private void recursiveApproximation(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4, List<Point> points) {
        List<Point> list = points;
        double x12 = (x1 + x2) / 2.0d;
        double y12 = (y1 + y2) / 2.0d;
        double x23 = (x2 + x3) / 2.0d;
        double y23 = (y2 + y3) / 2.0d;
        double x34 = (x3 + x4) / 2.0d;
        double y34 = (y3 + y4) / 2.0d;
        double x123 = (x12 + x23) / 2.0d;
        double y123 = (y12 + y23) / 2.0d;
        double x234 = (x23 + x34) / 2.0d;
        double y234 = (y23 + y34) / 2.0d;
        double x1234 = (x123 + x234) / 2.0d;
        double y1234 = (y123 + y234) / 2.0d;
        double dx = x4 - x1;
        double dy = y4 - y1;
        double d2 = Math.abs(((x2 - x4) * dy) - ((y2 - y4) * dx));
        double d3 = Math.abs(((x3 - x4) * dy) - ((y3 - y4) * dx));
        double d = curveCollinearityEpsilon;
        if (d2 > d || d3 > d) {
            if ((d2 + d3) * (d2 + d3) <= distanceToleranceSquare * ((dx * dx) + (dy * dy))) {
                list.add(new Point(x1234, y1234));
                return;
            }
        } else if (Math.abs(((x1 + x3) - x2) - x2) + Math.abs(((y1 + y3) - y2) - y2) + Math.abs(((x2 + x4) - x3) - x3) + Math.abs(((y2 + y4) - y3) - y3) <= distanceToleranceManhattan) {
            list.add(new Point(x1234, y1234));
            return;
        }
        double y12342 = y1234;
        List<Point> list2 = points;
        recursiveApproximation(x1, y1, x12, y12, x123, y123, x1234, y12342, list2);
        recursiveApproximation(x1234, y12342, x234, y234, x34, y34, x4, y4, list2);
    }
}
