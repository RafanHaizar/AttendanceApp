package com.itextpdf.kernel.geom;

import com.itextpdf.p026io.util.HashCode;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.io.Serializable;

public class Point implements Serializable, Cloneable {
    private static final long serialVersionUID = -5276940640259749850L;

    /* renamed from: x */
    public double f1280x;

    /* renamed from: y */
    public double f1281y;

    public Point() {
        setLocation(0, 0);
    }

    public Point(int x, int y) {
        setLocation(x, y);
    }

    public Point(double x, double y) {
        setLocation(x, y);
    }

    public Point(Point p) {
        setLocation(p.f1280x, p.f1281y);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Point)) {
            return false;
        }
        Point p = (Point) obj;
        if (this.f1280x == p.f1280x && this.f1281y == p.f1281y) {
            return true;
        }
        return false;
    }

    public String toString() {
        return MessageFormatUtil.format("Point: [x={0},y={1}]", Double.valueOf(this.f1280x), Double.valueOf(this.f1281y));
    }

    public double getX() {
        return this.f1280x;
    }

    public double getY() {
        return this.f1281y;
    }

    public Point getLocation() {
        return new Point(this.f1280x, this.f1281y);
    }

    public void setLocation(Point p) {
        setLocation(p.f1280x, p.f1281y);
    }

    public void setLocation(int x, int y) {
        setLocation((double) x, (double) y);
    }

    public void setLocation(double x, double y) {
        this.f1280x = x;
        this.f1281y = y;
    }

    public void move(double x, double y) {
        setLocation(x, y);
    }

    public void translate(double dx, double dy) {
        this.f1280x += dx;
        this.f1281y += dy;
    }

    public int hashCode() {
        HashCode hash = new HashCode();
        hash.append(getX());
        hash.append(getY());
        return hash.hashCode();
    }

    public static double distanceSq(double x1, double y1, double x2, double y2) {
        double x22 = x2 - x1;
        double y22 = y2 - y1;
        return (x22 * x22) + (y22 * y22);
    }

    public double distanceSq(double px, double py) {
        return distanceSq(getX(), getY(), px, py);
    }

    public double distanceSq(Point p) {
        return distanceSq(getX(), getY(), p.getX(), p.getY());
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(distanceSq(x1, y1, x2, y2));
    }

    public double distance(double px, double py) {
        return Math.sqrt(distanceSq(px, py));
    }

    public double distance(Point p) {
        return Math.sqrt(distanceSq(p));
    }

    public Object clone() {
        return new Point(this.f1280x, this.f1281y);
    }
}
