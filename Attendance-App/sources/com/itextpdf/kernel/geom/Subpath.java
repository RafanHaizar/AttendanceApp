package com.itextpdf.kernel.geom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Subpath implements Serializable {
    private static final long serialVersionUID = -3464451279777771490L;
    private boolean closed;
    private List<IShape> segments;
    private Point startPoint;

    public Subpath() {
        this.segments = new ArrayList();
    }

    public Subpath(Subpath subpath) {
        ArrayList arrayList = new ArrayList();
        this.segments = arrayList;
        this.startPoint = subpath.startPoint;
        arrayList.addAll(subpath.getSegments());
        this.closed = subpath.closed;
    }

    public Subpath(Point startPoint2) {
        this((float) startPoint2.getX(), (float) startPoint2.getY());
    }

    public Subpath(float startPointX, float startPointY) {
        this.segments = new ArrayList();
        this.startPoint = new Point((double) startPointX, (double) startPointY);
    }

    public void setStartPoint(Point startPoint2) {
        setStartPoint((float) startPoint2.getX(), (float) startPoint2.getY());
    }

    public void setStartPoint(float x, float y) {
        this.startPoint = new Point((double) x, (double) y);
    }

    public Point getStartPoint() {
        return this.startPoint;
    }

    public Point getLastPoint() {
        Point lastPoint = this.startPoint;
        if (this.segments.size() <= 0 || this.closed) {
            return lastPoint;
        }
        List<IShape> list = this.segments;
        IShape shape = list.get(list.size() - 1);
        return shape.getBasePoints().get(shape.getBasePoints().size() - 1);
    }

    public void addSegment(IShape segment) {
        if (!this.closed) {
            if (isSinglePointOpen()) {
                this.startPoint = segment.getBasePoints().get(0);
            }
            this.segments.add(segment);
        }
    }

    public List<IShape> getSegments() {
        return this.segments;
    }

    public boolean isEmpty() {
        return this.startPoint == null;
    }

    public boolean isSinglePointOpen() {
        return this.segments.size() == 0 && !this.closed;
    }

    public boolean isSinglePointClosed() {
        return this.segments.size() == 0 && this.closed;
    }

    public boolean isClosed() {
        return this.closed;
    }

    public void setClosed(boolean closed2) {
        this.closed = closed2;
    }

    public boolean isDegenerate() {
        if (this.segments.size() > 0 && this.closed) {
            return false;
        }
        for (IShape segment : this.segments) {
            if (new HashSet<>(segment.getBasePoints()).size() != 1) {
                return false;
            }
        }
        if (this.segments.size() > 0 || this.closed) {
            return true;
        }
        return false;
    }

    public List<Point> getPiecewiseLinearApproximation() {
        List<Point> segApprox;
        List<Point> result = new ArrayList<>();
        if (this.segments.size() == 0) {
            return result;
        }
        if (this.segments.get(0) instanceof BezierCurve) {
            result.addAll(((BezierCurve) this.segments.get(0)).getPiecewiseLinearApproximation());
        } else {
            result.addAll(this.segments.get(0).getBasePoints());
        }
        for (int i = 1; i < this.segments.size(); i++) {
            if (this.segments.get(i) instanceof BezierCurve) {
                List<Point> segApprox2 = ((BezierCurve) this.segments.get(i)).getPiecewiseLinearApproximation();
                segApprox = segApprox2.subList(1, segApprox2.size());
            } else {
                List<Point> segApprox3 = this.segments.get(i).getBasePoints();
                segApprox = segApprox3.subList(1, segApprox3.size());
            }
            result.addAll(segApprox);
        }
        return result;
    }
}
