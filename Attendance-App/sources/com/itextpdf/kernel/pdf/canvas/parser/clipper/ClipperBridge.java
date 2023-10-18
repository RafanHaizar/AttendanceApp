package com.itextpdf.kernel.pdf.canvas.parser.clipper;

import com.itextpdf.kernel.geom.Path;
import com.itextpdf.kernel.geom.Subpath;
import com.itextpdf.kernel.pdf.canvas.parser.clipper.IClipper;
import com.itextpdf.kernel.pdf.canvas.parser.clipper.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ClipperBridge {
    public static double floatMultiplier = Math.pow(10.0d, 14.0d);

    public static Path convertToPath(PolyTree result) {
        Path path = new Path();
        for (PolyNode node = result.getFirst(); node != null; node = node.getNext()) {
            addContour(path, node.getContour(), !node.isOpen());
        }
        return path;
    }

    public static void addPath(IClipper clipper, Path path, IClipper.PolyType polyType) {
        for (Subpath subpath : path.getSubpaths()) {
            if (!subpath.isSinglePointClosed() && !subpath.isSinglePointOpen()) {
                clipper.addPath(new Path((Collection<? extends Point.LongPoint>) convertToLongPoints(subpath.getPiecewiseLinearApproximation())), polyType, subpath.isClosed());
            }
        }
    }

    public static List<Subpath> addPath(ClipperOffset offset, Path path, IClipper.JoinType joinType, IClipper.EndType endType) {
        IClipper.EndType et;
        List<Subpath> degenerateSubpaths = new ArrayList<>();
        for (Subpath subpath : path.getSubpaths()) {
            if (subpath.isDegenerate()) {
                degenerateSubpaths.add(subpath);
            } else if (!subpath.isSinglePointClosed() && !subpath.isSinglePointOpen()) {
                if (subpath.isClosed()) {
                    et = IClipper.EndType.CLOSED_LINE;
                } else {
                    et = endType;
                }
                offset.addPath(new Path((Collection<? extends Point.LongPoint>) convertToLongPoints(subpath.getPiecewiseLinearApproximation())), joinType, et);
            }
        }
        return degenerateSubpaths;
    }

    public static List<com.itextpdf.kernel.geom.Point> convertToFloatPoints(List<Point.LongPoint> points) {
        List<com.itextpdf.kernel.geom.Point> convertedPoints = new ArrayList<>(points.size());
        for (Point.LongPoint point : points) {
            double x = (double) point.getX();
            double d = floatMultiplier;
            Double.isNaN(x);
            double d2 = x / d;
            double y = (double) point.getY();
            double d3 = floatMultiplier;
            Double.isNaN(y);
            convertedPoints.add(new com.itextpdf.kernel.geom.Point(d2, y / d3));
        }
        return convertedPoints;
    }

    public static List<Point.LongPoint> convertToLongPoints(List<com.itextpdf.kernel.geom.Point> points) {
        List<Point.LongPoint> convertedPoints = new ArrayList<>(points.size());
        for (com.itextpdf.kernel.geom.Point point : points) {
            convertedPoints.add(new Point.LongPoint(floatMultiplier * point.getX(), floatMultiplier * point.getY()));
        }
        return convertedPoints;
    }

    public static IClipper.JoinType getJoinType(int lineJoinStyle) {
        switch (lineJoinStyle) {
            case 0:
                return IClipper.JoinType.MITER;
            case 2:
                return IClipper.JoinType.BEVEL;
            default:
                return IClipper.JoinType.ROUND;
        }
    }

    public static IClipper.EndType getEndType(int lineCapStyle) {
        switch (lineCapStyle) {
            case 0:
                return IClipper.EndType.OPEN_BUTT;
            case 2:
                return IClipper.EndType.OPEN_SQUARE;
            default:
                return IClipper.EndType.OPEN_ROUND;
        }
    }

    public static IClipper.PolyFillType getFillType(int fillingRule) {
        IClipper.PolyFillType fillType = IClipper.PolyFillType.NON_ZERO;
        if (fillingRule == 2) {
            return IClipper.PolyFillType.EVEN_ODD;
        }
        return fillType;
    }

    public static boolean addPolygonToClipper(IClipper clipper, com.itextpdf.kernel.geom.Point[] polyVertices, IClipper.PolyType polyType) {
        return clipper.addPath(new Path((Collection<? extends Point.LongPoint>) convertToLongPoints(new ArrayList(Arrays.asList(polyVertices)))), polyType, true);
    }

    public static boolean addPolylineSubjectToClipper(IClipper clipper, com.itextpdf.kernel.geom.Point[] lineVertices) {
        return clipper.addPath(new Path((Collection<? extends Point.LongPoint>) convertToLongPoints(new ArrayList(Arrays.asList(lineVertices)))), IClipper.PolyType.SUBJECT, false);
    }

    static void addContour(Path path, List<Point.LongPoint> contour, boolean close) {
        List<com.itextpdf.kernel.geom.Point> floatContour = convertToFloatPoints(contour);
        com.itextpdf.kernel.geom.Point point = floatContour.get(0);
        path.moveTo((float) point.getX(), (float) point.getY());
        for (int i = 1; i < floatContour.size(); i++) {
            com.itextpdf.kernel.geom.Point point2 = floatContour.get(i);
            path.lineTo((float) point2.getX(), (float) point2.getY());
        }
        if (close) {
            path.closeSubpath();
        }
    }

    @Deprecated
    public static void addRectToClipper(IClipper clipper, com.itextpdf.kernel.geom.Point[] rectVertices, IClipper.PolyType polyType) {
        clipper.addPath(new Path((Collection<? extends Point.LongPoint>) convertToLongPoints(new ArrayList(Arrays.asList(rectVertices)))), polyType, true);
    }
}
