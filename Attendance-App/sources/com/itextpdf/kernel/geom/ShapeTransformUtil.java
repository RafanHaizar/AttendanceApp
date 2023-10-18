package com.itextpdf.kernel.geom;

import com.itextpdf.kernel.PdfException;
import java.util.Arrays;
import java.util.List;

public final class ShapeTransformUtil {
    public static BezierCurve transformBezierCurve(BezierCurve bezierCurve, Matrix ctm) {
        return (BezierCurve) transformSegment(bezierCurve, ctm);
    }

    public static Line transformLine(Line line, Matrix ctm) {
        return (Line) transformSegment(line, ctm);
    }

    public static Path transformPath(Path path, Matrix ctm) {
        Path newPath = new Path();
        for (Subpath subpath : path.getSubpaths()) {
            newPath.addSubpath(transformSubpath(subpath, ctm));
        }
        return newPath;
    }

    private static Subpath transformSubpath(Subpath subpath, Matrix ctm) {
        Subpath newSubpath = new Subpath();
        newSubpath.setClosed(subpath.isClosed());
        for (IShape segment : subpath.getSegments()) {
            newSubpath.addSegment(transformSegment(segment, ctm));
        }
        return newSubpath;
    }

    private static IShape transformSegment(IShape segment, Matrix ctm) {
        List<Point> basePoints = segment.getBasePoints();
        Point[] newBasePoints = transformPoints(ctm, (Point[]) basePoints.toArray(new Point[basePoints.size()]));
        if (segment instanceof BezierCurve) {
            return new BezierCurve(Arrays.asList(newBasePoints));
        }
        return new Line(newBasePoints[0], newBasePoints[1]);
    }

    private static Point[] transformPoints(Matrix ctm, Point... points) {
        try {
            AffineTransform t = new AffineTransform((double) ctm.get(0), (double) ctm.get(1), (double) ctm.get(3), (double) ctm.get(4), (double) ctm.get(6), (double) ctm.get(7)).createInverse();
            Point[] newPoints = new Point[points.length];
            t.transform(points, 0, newPoints, 0, points.length);
            return newPoints;
        } catch (NoninvertibleTransformException e) {
            throw new PdfException(PdfException.NoninvertibleMatrixCannotBeProcessed, (Throwable) e);
        }
    }
}
