package com.itextpdf.kernel.geom;

public class LineSegment {
    private final Vector endPoint;
    private final Vector startPoint;

    public LineSegment(Vector startPoint2, Vector endPoint2) {
        this.startPoint = startPoint2;
        this.endPoint = endPoint2;
    }

    public Vector getStartPoint() {
        return this.startPoint;
    }

    public Vector getEndPoint() {
        return this.endPoint;
    }

    public float getLength() {
        return this.endPoint.subtract(this.startPoint).length();
    }

    public Rectangle getBoundingRectangle() {
        float x1 = getStartPoint().get(0);
        float y1 = getStartPoint().get(1);
        float x2 = getEndPoint().get(0);
        float y2 = getEndPoint().get(1);
        return new Rectangle(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
    }

    public LineSegment transformBy(Matrix m) {
        return new LineSegment(this.startPoint.cross(m), this.endPoint.cross(m));
    }

    public boolean containsSegment(LineSegment other) {
        return other != null && containsPoint(other.startPoint) && containsPoint(other.endPoint);
    }

    public boolean containsPoint(Vector point) {
        if (point == null) {
            return false;
        }
        Vector diff1 = point.subtract(this.startPoint);
        if (diff1.get(0) < 0.0f || diff1.get(1) < 0.0f || diff1.get(2) < 0.0f) {
            return false;
        }
        Vector diff2 = this.endPoint.subtract(point);
        if (diff2.get(0) < 0.0f || diff2.get(1) < 0.0f || diff2.get(2) < 0.0f) {
            return false;
        }
        return true;
    }
}
