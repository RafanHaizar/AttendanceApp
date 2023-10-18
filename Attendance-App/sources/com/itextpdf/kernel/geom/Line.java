package com.itextpdf.kernel.geom;

import java.util.ArrayList;
import java.util.List;

public class Line implements IShape {
    private static final long serialVersionUID = 4796508543986646437L;

    /* renamed from: p1 */
    private final Point f1258p1;

    /* renamed from: p2 */
    private final Point f1259p2;

    public Line() {
        this(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public Line(float x1, float y1, float x2, float y2) {
        this.f1258p1 = new Point((double) x1, (double) y1);
        this.f1259p2 = new Point((double) x2, (double) y2);
    }

    public Line(Point p1, Point p2) {
        this((float) p1.getX(), (float) p1.getY(), (float) p2.getX(), (float) p2.getY());
    }

    public List<Point> getBasePoints() {
        List<Point> basePoints = new ArrayList<>(2);
        basePoints.add(this.f1258p1);
        basePoints.add(this.f1259p2);
        return basePoints;
    }
}
