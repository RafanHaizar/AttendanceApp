package com.itextpdf.kernel.pdf.canvas.parser.clipper;

import java.lang.Comparable;
import java.lang.Number;
import java.math.BigInteger;
import java.util.Comparator;

public abstract class Point<T extends Number & Comparable<T>> {
    private static final NumberComparator NUMBER_COMPARATOR = new NumberComparator();

    /* renamed from: x */
    protected T f1492x;

    /* renamed from: y */
    protected T f1493y;

    /* renamed from: z */
    protected T f1494z;

    protected Point(Point<T> pt) {
        this(pt.f1492x, pt.f1493y, pt.f1494z);
    }

    protected Point(T x, T y, T z) {
        this.f1492x = x;
        this.f1493y = y;
        this.f1494z = z;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Point)) {
            return false;
        }
        Point<?> a = (Point) obj;
        NumberComparator numberComparator = NUMBER_COMPARATOR;
        if (numberComparator.compare(this.f1492x, a.f1492x) == 0 && numberComparator.compare(this.f1493y, a.f1493y) == 0) {
            return true;
        }
        return false;
    }

    public void set(Point<T> other) {
        this.f1492x = other.f1492x;
        this.f1493y = other.f1493y;
        this.f1494z = other.f1494z;
    }

    public void setX(T x) {
        this.f1492x = x;
    }

    public void setY(T y) {
        this.f1493y = y;
    }

    public void setZ(T z) {
        this.f1494z = z;
    }

    public String toString() {
        return "Point [x=" + this.f1492x + ", y=" + this.f1493y + ", z=" + this.f1494z + "]";
    }

    protected static boolean isPt2BetweenPt1AndPt3(LongPoint pt1, LongPoint pt2, LongPoint pt3) {
        if (pt1.equals(pt3) || pt1.equals(pt2) || pt3.equals(pt2)) {
            return false;
        }
        if (pt1.getX() != pt3.getX()) {
            if ((((Long) pt2.f1492x).longValue() > ((Long) pt1.f1492x).longValue()) == (((Long) pt2.f1492x).longValue() < ((Long) pt3.f1492x).longValue())) {
                return true;
            }
            return false;
        }
        if ((((Long) pt2.f1493y).longValue() > ((Long) pt1.f1493y).longValue()) == (((Long) pt2.f1493y).longValue() < ((Long) pt3.f1493y).longValue())) {
            return true;
        }
        return false;
    }

    protected static boolean slopesEqual(LongPoint pt1, LongPoint pt2, LongPoint pt3, boolean useFullRange) {
        return slopesEqual(pt1, pt2, pt2, pt3, useFullRange);
    }

    protected static boolean slopesEqual(LongPoint pt1, LongPoint pt2, LongPoint pt3, LongPoint pt4, boolean useFullRange) {
        if (useFullRange) {
            return BigInteger.valueOf(pt1.getY() - pt2.getY()).multiply(BigInteger.valueOf(pt3.getX() - pt4.getX())).equals(BigInteger.valueOf(pt1.getX() - pt2.getX()).multiply(BigInteger.valueOf(pt3.getY() - pt4.getY())));
        }
        return ((pt1.getY() - pt2.getY()) * (pt3.getX() - pt4.getX())) - ((pt1.getX() - pt2.getX()) * (pt3.getY() - pt4.getY())) == 0;
    }

    static boolean arePointsClose(Point<? extends Number> pt1, Point<? extends Number> pt2, double distSqrd) {
        double dx = pt1.f1492x.doubleValue() - pt2.f1492x.doubleValue();
        double dy = pt1.f1493y.doubleValue() - pt2.f1493y.doubleValue();
        return (dx * dx) + (dy * dy) <= distSqrd;
    }

    static double distanceFromLineSqrd(Point<? extends Number> pt, Point<? extends Number> ln1, Point<? extends Number> ln2) {
        double A = ln1.f1493y.doubleValue() - ln2.f1493y.doubleValue();
        double B = ln2.f1492x.doubleValue() - ln1.f1492x.doubleValue();
        double C = ((pt.f1492x.doubleValue() * A) + (pt.f1493y.doubleValue() * B)) - ((ln1.f1492x.doubleValue() * A) + (ln1.f1493y.doubleValue() * B));
        return (C * C) / ((A * A) + (B * B));
    }

    static DoublePoint getUnitNormal(LongPoint pt1, LongPoint pt2) {
        double dx = (double) (((Long) pt2.f1492x).longValue() - ((Long) pt1.f1492x).longValue());
        double dy = (double) (((Long) pt2.f1493y).longValue() - ((Long) pt1.f1493y).longValue());
        if (dx == 0.0d && dy == 0.0d) {
            return new DoublePoint();
        }
        Double.isNaN(dx);
        Double.isNaN(dx);
        Double.isNaN(dy);
        Double.isNaN(dy);
        double f = 1.0d / Math.sqrt((dx * dx) + (dy * dy));
        Double.isNaN(dx);
        Double.isNaN(dy);
        return new DoublePoint(dy * f, -(dx * f));
    }

    static boolean slopesNearCollinear(LongPoint pt1, LongPoint pt2, LongPoint pt3, double distSqrd) {
        if (Math.abs(((Long) pt1.f1492x).longValue() - ((Long) pt2.f1492x).longValue()) > Math.abs(((Long) pt1.f1493y).longValue() - ((Long) pt2.f1493y).longValue())) {
            if ((((Long) pt1.f1492x).longValue() > ((Long) pt2.f1492x).longValue()) != (((Long) pt1.f1492x).longValue() < ((Long) pt3.f1492x).longValue())) {
                if ((((Long) pt2.f1492x).longValue() > ((Long) pt1.f1492x).longValue()) == (((Long) pt2.f1492x).longValue() < ((Long) pt3.f1492x).longValue())) {
                    if (distanceFromLineSqrd(pt2, pt1, pt3) < distSqrd) {
                        return true;
                    }
                    return false;
                } else if (distanceFromLineSqrd(pt3, pt1, pt2) < distSqrd) {
                    return true;
                } else {
                    return false;
                }
            } else if (distanceFromLineSqrd(pt1, pt2, pt3) < distSqrd) {
                return true;
            } else {
                return false;
            }
        } else {
            if ((((Long) pt1.f1493y).longValue() > ((Long) pt2.f1493y).longValue()) != (((Long) pt1.f1493y).longValue() < ((Long) pt3.f1493y).longValue())) {
                if ((((Long) pt2.f1493y).longValue() > ((Long) pt1.f1493y).longValue()) == (((Long) pt2.f1493y).longValue() < ((Long) pt3.f1493y).longValue())) {
                    if (distanceFromLineSqrd(pt2, pt1, pt3) < distSqrd) {
                        return true;
                    }
                    return false;
                } else if (distanceFromLineSqrd(pt3, pt1, pt2) < distSqrd) {
                    return true;
                } else {
                    return false;
                }
            } else if (distanceFromLineSqrd(pt1, pt2, pt3) < distSqrd) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static class DoublePoint extends Point<Double> {
        public DoublePoint() {
            this(0.0d, 0.0d);
        }

        public DoublePoint(double x, double y) {
            this(x, y, 0.0d);
        }

        public DoublePoint(double x, double y, double z) {
            super(Double.valueOf(x), Double.valueOf(y), Double.valueOf(z));
        }

        public DoublePoint(DoublePoint other) {
            super(other);
        }

        public double getX() {
            return ((Double) this.f1492x).doubleValue();
        }

        public double getY() {
            return ((Double) this.f1493y).doubleValue();
        }

        public double getZ() {
            return ((Double) this.f1494z).doubleValue();
        }
    }

    public static class LongPoint extends Point<Long> {
        public LongPoint() {
            this(0, 0);
        }

        public LongPoint(long x, long y) {
            this(x, y, 0);
        }

        public LongPoint(double x, double y) {
            this((long) x, (long) y);
        }

        public LongPoint(long x, long y, long z) {
            super(Long.valueOf(x), Long.valueOf(y), Long.valueOf(z));
        }

        public LongPoint(LongPoint other) {
            super(other);
        }

        public static double getDeltaX(LongPoint pt1, LongPoint pt2) {
            if (pt1.getY() == pt2.getY()) {
                return -3.4E38d;
            }
            double x = (double) (pt2.getX() - pt1.getX());
            double y = (double) (pt2.getY() - pt1.getY());
            Double.isNaN(x);
            Double.isNaN(y);
            return x / y;
        }

        public long getX() {
            return ((Long) this.f1492x).longValue();
        }

        public long getY() {
            return ((Long) this.f1493y).longValue();
        }

        public long getZ() {
            return ((Long) this.f1494z).longValue();
        }
    }

    private static class NumberComparator<T extends Number & Comparable<T>> implements Comparator<T> {
        private NumberComparator() {
        }

        public int compare(T a, T b) throws ClassCastException {
            return ((Comparable) a).compareTo(b);
        }
    }
}
