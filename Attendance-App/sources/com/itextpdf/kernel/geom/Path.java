package com.itextpdf.kernel.geom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Path implements Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String START_PATH_ERR_MSG = "Path shall start with \"re\" or \"m\" operator";
    private static final long serialVersionUID = 1658560770858987684L;
    private Point currentPoint;
    private List<Subpath> subpaths = new ArrayList();

    public Path() {
    }

    public Path(List<? extends Subpath> subpaths2) {
        addSubpaths(subpaths2);
    }

    public Path(Path path) {
        addSubpaths(path.getSubpaths());
    }

    public List<Subpath> getSubpaths() {
        return this.subpaths;
    }

    public void addSubpath(Subpath subpath) {
        this.subpaths.add(subpath);
        this.currentPoint = subpath.getLastPoint();
    }

    public void addSubpaths(List<? extends Subpath> subpaths2) {
        if (subpaths2.size() > 0) {
            for (Subpath subpath : subpaths2) {
                this.subpaths.add(new Subpath(subpath));
            }
            this.currentPoint = this.subpaths.get(subpaths2.size() - 1).getLastPoint();
        }
    }

    public Point getCurrentPoint() {
        return this.currentPoint;
    }

    public void moveTo(float x, float y) {
        Subpath lastSubpath;
        this.currentPoint = new Point((double) x, (double) y);
        if (this.subpaths.size() > 0) {
            List<Subpath> list = this.subpaths;
            lastSubpath = list.get(list.size() - 1);
        } else {
            lastSubpath = null;
        }
        if (lastSubpath == null || !lastSubpath.isSinglePointOpen()) {
            this.subpaths.add(new Subpath(this.currentPoint));
        } else {
            lastSubpath.setStartPoint(this.currentPoint);
        }
    }

    public void lineTo(float x, float y) {
        if (this.currentPoint != null) {
            Point targetPoint = new Point((double) x, (double) y);
            getLastSubpath().addSegment(new Line(this.currentPoint, targetPoint));
            this.currentPoint = targetPoint;
            return;
        }
        throw new RuntimeException(START_PATH_ERR_MSG);
    }

    public void curveTo(float x1, float y1, float x2, float y2, float x3, float y3) {
        if (this.currentPoint != null) {
            Point secondPoint = new Point((double) x1, (double) y1);
            Point thirdPoint = new Point((double) x2, (double) y2);
            Point fourthPoint = new Point((double) x3, (double) y3);
            getLastSubpath().addSegment(new BezierCurve(new ArrayList<>(Arrays.asList(new Point[]{this.currentPoint, secondPoint, thirdPoint, fourthPoint}))));
            this.currentPoint = fourthPoint;
            return;
        }
        throw new RuntimeException(START_PATH_ERR_MSG);
    }

    public void curveTo(float x2, float y2, float x3, float y3) {
        Point point = this.currentPoint;
        if (point != null) {
            curveTo((float) point.getX(), (float) this.currentPoint.getY(), x2, y2, x3, y3);
            return;
        }
        throw new RuntimeException(START_PATH_ERR_MSG);
    }

    public void curveFromTo(float x1, float y1, float x3, float y3) {
        if (this.currentPoint != null) {
            curveTo(x1, y1, x3, y3, x3, y3);
            return;
        }
        throw new RuntimeException(START_PATH_ERR_MSG);
    }

    public void rectangle(Rectangle rect) {
        rectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    public void rectangle(float x, float y, float w, float h) {
        moveTo(x, y);
        lineTo(x + w, y);
        lineTo(x + w, y + h);
        lineTo(x, y + h);
        closeSubpath();
    }

    public void closeSubpath() {
        if (!isEmpty()) {
            Subpath lastSubpath = getLastSubpath();
            lastSubpath.setClosed(true);
            Point startPoint = lastSubpath.getStartPoint();
            moveTo((float) startPoint.getX(), (float) startPoint.getY());
        }
    }

    public void closeAllSubpaths() {
        for (Subpath subpath : this.subpaths) {
            subpath.setClosed(true);
        }
    }

    public List<Integer> replaceCloseWithLine() {
        List<Integer> modifiedSubpathsIndices = new ArrayList<>();
        int i = 0;
        for (Subpath subpath : this.subpaths) {
            if (subpath.isClosed()) {
                subpath.setClosed(false);
                subpath.addSegment(new Line(subpath.getLastPoint(), subpath.getStartPoint()));
                modifiedSubpathsIndices.add(Integer.valueOf(i));
            }
            i++;
        }
        return modifiedSubpathsIndices;
    }

    public boolean isEmpty() {
        return this.subpaths.size() == 0;
    }

    private Subpath getLastSubpath() {
        if (this.subpaths.size() > 0) {
            List<Subpath> list = this.subpaths;
            return list.get(list.size() - 1);
        }
        throw new AssertionError();
    }
}
