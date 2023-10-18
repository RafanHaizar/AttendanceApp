package com.itextpdf.kernel.pdf.canvas.parser.clipper;

import com.itextpdf.kernel.pdf.canvas.parser.clipper.IClipper;
import com.itextpdf.kernel.pdf.canvas.parser.clipper.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ClipperOffset {
    private static final double DEFAULT_ARC_TOLERANCE = 0.25d;
    private static final double TOLERANCE = 1.0E-20d;
    private static final double TWO_PI = 6.283185307179586d;
    private final double arcTolerance;
    private double cos;
    private double delta;
    private Path destPoly;
    private Paths destPolys;
    private double inA;
    private Point.LongPoint lowest;
    private double miterLim;
    private final double miterLimit;
    private final List<Point.DoublePoint> normals;
    private final PolyNode polyNodes;
    private double sin;
    private Path srcPoly;
    private double stepsPerRad;

    private static boolean nearZero(double val) {
        return val > -1.0E-20d && val < TOLERANCE;
    }

    public ClipperOffset() {
        this(2.0d, DEFAULT_ARC_TOLERANCE);
    }

    public ClipperOffset(double miterLimit2) {
        this(miterLimit2, DEFAULT_ARC_TOLERANCE);
    }

    public ClipperOffset(double miterLimit2, double arcTolerance2) {
        this.miterLimit = miterLimit2;
        this.arcTolerance = arcTolerance2;
        Point.LongPoint longPoint = new Point.LongPoint();
        this.lowest = longPoint;
        longPoint.setX(-1L);
        this.polyNodes = new PolyNode();
        this.normals = new ArrayList();
    }

    public void addPath(Path path, IClipper.JoinType joinType, IClipper.EndType endType) {
        int highI = path.size() - 1;
        if (highI >= 0) {
            PolyNode newNode = new PolyNode();
            newNode.setJoinType(joinType);
            newNode.setEndType(endType);
            if (endType == IClipper.EndType.CLOSED_LINE || endType == IClipper.EndType.CLOSED_POLYGON) {
                while (highI > 0 && ((Point.LongPoint) path.get(0)).equals(path.get(highI))) {
                    highI--;
                }
            }
            newNode.getPolygon().add(path.get(0));
            int j = 0;
            int k = 0;
            for (int i = 1; i <= highI; i++) {
                if (!((Point.LongPoint) newNode.getPolygon().get(j)).equals(path.get(i))) {
                    j++;
                    newNode.getPolygon().add(path.get(i));
                    if (((Point.LongPoint) path.get(i)).getY() > ((Point.LongPoint) newNode.getPolygon().get(k)).getY() || (((Point.LongPoint) path.get(i)).getY() == ((Point.LongPoint) newNode.getPolygon().get(k)).getY() && ((Point.LongPoint) path.get(i)).getX() < ((Point.LongPoint) newNode.getPolygon().get(k)).getX())) {
                        k = j;
                    }
                }
            }
            if (endType != IClipper.EndType.CLOSED_POLYGON || j >= 2) {
                this.polyNodes.addChild(newNode);
                if (endType == IClipper.EndType.CLOSED_POLYGON) {
                    if (this.lowest.getX() < 0) {
                        this.lowest = new Point.LongPoint((long) (this.polyNodes.getChildCount() - 1), (long) k);
                        return;
                    }
                    Point.LongPoint ip = (Point.LongPoint) this.polyNodes.getChilds().get((int) this.lowest.getX()).getPolygon().get((int) this.lowest.getY());
                    if (((Point.LongPoint) newNode.getPolygon().get(k)).getY() > ip.getY() || (((Point.LongPoint) newNode.getPolygon().get(k)).getY() == ip.getY() && ((Point.LongPoint) newNode.getPolygon().get(k)).getX() < ip.getX())) {
                        this.lowest = new Point.LongPoint((long) (this.polyNodes.getChildCount() - 1), (long) k);
                    }
                }
            }
        }
    }

    public void addPaths(Paths paths, IClipper.JoinType joinType, IClipper.EndType endType) {
        Iterator it = paths.iterator();
        while (it.hasNext()) {
            addPath((Path) it.next(), joinType, endType);
        }
    }

    public void clear() {
        this.polyNodes.getChilds().clear();
        this.lowest.setX(-1L);
    }

    private void doMiter(int j, int k, double r) {
        int i = j;
        int i2 = k;
        double q = this.delta / r;
        Path path = this.destPoly;
        double x = (double) ((Point.LongPoint) this.srcPoly.get(i)).getX();
        Double.isNaN(x);
        long round = Math.round(x + ((this.normals.get(i2).getX() + this.normals.get(i).getX()) * q));
        double y = (double) ((Point.LongPoint) this.srcPoly.get(i)).getY();
        Double.isNaN(y);
        path.add(new Point.LongPoint(round, Math.round(y + ((this.normals.get(i2).getY() + this.normals.get(i).getY()) * q))));
    }

    private void doOffset(double delta2) {
        double y;
        double steps;
        double y2;
        int i;
        double d = delta2;
        this.destPolys = new Paths();
        this.delta = d;
        if (nearZero(delta2)) {
            for (int i2 = 0; i2 < this.polyNodes.getChildCount(); i2++) {
                PolyNode node = this.polyNodes.getChilds().get(i2);
                if (node.getEndType() == IClipper.EndType.CLOSED_POLYGON) {
                    this.destPolys.add(node.getPolygon());
                }
            }
            return;
        }
        double d2 = this.miterLimit;
        if (d2 > 2.0d) {
            this.miterLim = 2.0d / (d2 * d2);
        } else {
            this.miterLim = 0.5d;
        }
        double d3 = this.arcTolerance;
        double d4 = 0.0d;
        if (d3 <= 0.0d) {
            y = DEFAULT_ARC_TOLERANCE;
        } else if (d3 > Math.abs(delta2) * DEFAULT_ARC_TOLERANCE) {
            y = Math.abs(delta2) * DEFAULT_ARC_TOLERANCE;
        } else {
            y = this.arcTolerance;
        }
        double steps2 = 3.141592653589793d / Math.acos(1.0d - (y / Math.abs(delta2)));
        this.sin = Math.sin(TWO_PI / steps2);
        this.cos = Math.cos(TWO_PI / steps2);
        this.stepsPerRad = steps2 / TWO_PI;
        if (d < 0.0d) {
            this.sin = -this.sin;
        }
        int i3 = 0;
        while (i3 < this.polyNodes.getChildCount()) {
            PolyNode node2 = this.polyNodes.getChilds().get(i3);
            Path polygon = node2.getPolygon();
            this.srcPoly = polygon;
            int len = polygon.size();
            if (len != 0) {
                if (d <= d4) {
                    if (len < 3) {
                        y2 = y;
                        i = i3;
                        steps = steps2;
                    } else if (node2.getEndType() != IClipper.EndType.CLOSED_POLYGON) {
                        y2 = y;
                        i = i3;
                        steps = steps2;
                    }
                }
                this.destPoly = new Path();
                if (len == 1) {
                    if (node2.getJoinType() == IClipper.JoinType.ROUND) {
                        double X = 1.0d;
                        double Y = 0.0d;
                        int j = 1;
                        while (true) {
                            double X2 = X;
                            if (((double) j) > steps2) {
                                break;
                            }
                            Path path = this.destPoly;
                            double x = (double) ((Point.LongPoint) this.srcPoly.get(0)).getX();
                            Double.isNaN(x);
                            long round = Math.round(x + (X2 * d));
                            double y3 = y;
                            double y4 = (double) ((Point.LongPoint) this.srcPoly.get(0)).getY();
                            Double.isNaN(y4);
                            path.add(new Point.LongPoint(round, Math.round(y4 + (Y * d))));
                            double d5 = this.cos;
                            double steps3 = steps2;
                            double steps4 = this.sin;
                            X = (X2 * d5) - (steps4 * Y);
                            Y = (steps4 * X2) + (d5 * Y);
                            j++;
                            i3 = i3;
                            y = y3;
                            steps2 = steps3;
                        }
                        y2 = y;
                        i = i3;
                        steps = steps2;
                    } else {
                        y2 = y;
                        i = i3;
                        steps = steps2;
                        double X3 = -1.0d;
                        int j2 = 0;
                        double Y2 = -1.0d;
                        while (j2 < 4) {
                            Path path2 = this.destPoly;
                            double x2 = (double) ((Point.LongPoint) this.srcPoly.get(0)).getX();
                            Double.isNaN(x2);
                            long round2 = Math.round(x2 + (X3 * d));
                            int j3 = j2;
                            double y5 = (double) ((Point.LongPoint) this.srcPoly.get(0)).getY();
                            Double.isNaN(y5);
                            path2.add(new Point.LongPoint(round2, Math.round(y5 + (Y2 * d))));
                            if (X3 < 0.0d) {
                                X3 = 1.0d;
                            } else if (Y2 < 0.0d) {
                                Y2 = 1.0d;
                            } else {
                                X3 = -1.0d;
                            }
                            j2 = j3 + 1;
                        }
                        int i4 = j2;
                    }
                    this.destPolys.add(this.destPoly);
                    d4 = 0.0d;
                } else {
                    y2 = y;
                    i = i3;
                    steps = steps2;
                    this.normals.clear();
                    for (int j4 = 0; j4 < len - 1; j4++) {
                        this.normals.add(Point.getUnitNormal((Point.LongPoint) this.srcPoly.get(j4), (Point.LongPoint) this.srcPoly.get(j4 + 1)));
                    }
                    if (node2.getEndType() == IClipper.EndType.CLOSED_LINE || node2.getEndType() == IClipper.EndType.CLOSED_POLYGON) {
                        this.normals.add(Point.getUnitNormal((Point.LongPoint) this.srcPoly.get(len - 1), (Point.LongPoint) this.srcPoly.get(0)));
                    } else {
                        this.normals.add(new Point.DoublePoint(this.normals.get(len - 2)));
                    }
                    if (node2.getEndType() == IClipper.EndType.CLOSED_POLYGON) {
                        int[] k = {len - 1};
                        for (int j5 = 0; j5 < len; j5++) {
                            offsetPoint(j5, k, node2.getJoinType());
                        }
                        this.destPolys.add(this.destPoly);
                        d4 = 0.0d;
                    } else if (node2.getEndType() == IClipper.EndType.CLOSED_LINE) {
                        int[] k2 = {len - 1};
                        for (int j6 = 0; j6 < len; j6++) {
                            offsetPoint(j6, k2, node2.getJoinType());
                        }
                        this.destPolys.add(this.destPoly);
                        this.destPoly = new Path();
                        Point.DoublePoint n = this.normals.get(len - 1);
                        for (int j7 = len - 1; j7 > 0; j7--) {
                            this.normals.set(j7, new Point.DoublePoint(-this.normals.get(j7 - 1).getX(), -this.normals.get(j7 - 1).getY()));
                        }
                        this.normals.set(0, new Point.DoublePoint(-n.getX(), -n.getY(), 0.0d));
                        k2[0] = 0;
                        for (int j8 = len - 1; j8 >= 0; j8--) {
                            offsetPoint(j8, k2, node2.getJoinType());
                        }
                        this.destPolys.add(this.destPoly);
                        d4 = 0.0d;
                    } else {
                        int[] k3 = new int[1];
                        for (int j9 = 1; j9 < len - 1; j9++) {
                            offsetPoint(j9, k3, node2.getJoinType());
                        }
                        if (node2.getEndType() == IClipper.EndType.OPEN_BUTT) {
                            int j10 = len - 1;
                            double x3 = (double) ((Point.LongPoint) this.srcPoly.get(j10)).getX();
                            Double.isNaN(x3);
                            long round3 = Math.round(x3 + (this.normals.get(j10).getX() * d));
                            double y6 = (double) ((Point.LongPoint) this.srcPoly.get(j10)).getY();
                            Double.isNaN(y6);
                            this.destPoly.add(new Point.LongPoint(round3, Math.round(y6 + (this.normals.get(j10).getY() * d)), 0));
                            double x4 = (double) ((Point.LongPoint) this.srcPoly.get(j10)).getX();
                            Double.isNaN(x4);
                            long round4 = Math.round(x4 - (this.normals.get(j10).getX() * d));
                            double y7 = (double) ((Point.LongPoint) this.srcPoly.get(j10)).getY();
                            Double.isNaN(y7);
                            this.destPoly.add(new Point.LongPoint(round4, Math.round(y7 - (this.normals.get(j10).getY() * d)), 0));
                        } else {
                            int j11 = len - 1;
                            k3[0] = len - 2;
                            this.inA = 0.0d;
                            this.normals.set(j11, new Point.DoublePoint(-this.normals.get(j11).getX(), -this.normals.get(j11).getY()));
                            if (node2.getEndType() == IClipper.EndType.OPEN_SQUARE) {
                                doSquare(j11, k3[0], true);
                            } else {
                                doRound(j11, k3[0]);
                            }
                        }
                        for (int j12 = len - 1; j12 > 0; j12--) {
                            this.normals.set(j12, new Point.DoublePoint(-this.normals.get(j12 - 1).getX(), -this.normals.get(j12 - 1).getY()));
                        }
                        this.normals.set(0, new Point.DoublePoint(-this.normals.get(1).getX(), -this.normals.get(1).getY()));
                        k3[0] = len - 1;
                        for (int j13 = k3[0] - 1; j13 > 0; j13--) {
                            offsetPoint(j13, k3, node2.getJoinType());
                        }
                        if (node2.getEndType() == IClipper.EndType.OPEN_BUTT) {
                            double x5 = (double) ((Point.LongPoint) this.srcPoly.get(0)).getX();
                            Double.isNaN(x5);
                            long round5 = Math.round(x5 - (this.normals.get(0).getX() * d));
                            double y8 = (double) ((Point.LongPoint) this.srcPoly.get(0)).getY();
                            Double.isNaN(y8);
                            this.destPoly.add(new Point.LongPoint(round5, Math.round(y8 - (this.normals.get(0).getY() * d))));
                            double x6 = (double) ((Point.LongPoint) this.srcPoly.get(0)).getX();
                            Double.isNaN(x6);
                            long round6 = Math.round(x6 + (this.normals.get(0).getX() * d));
                            double y9 = (double) ((Point.LongPoint) this.srcPoly.get(0)).getY();
                            Double.isNaN(y9);
                            this.destPoly.add(new Point.LongPoint(round6, Math.round(y9 + (this.normals.get(0).getY() * d))));
                            d4 = 0.0d;
                        } else {
                            k3[0] = 1;
                            d4 = 0.0d;
                            this.inA = 0.0d;
                            if (node2.getEndType() == IClipper.EndType.OPEN_SQUARE) {
                                doSquare(0, 1, true);
                            } else {
                                doRound(0, 1);
                            }
                        }
                        this.destPolys.add(this.destPoly);
                    }
                }
            } else {
                y2 = y;
                i = i3;
                steps = steps2;
            }
            i3 = i + 1;
            y = y2;
            steps2 = steps;
        }
    }

    private void doRound(int j, int k) {
        int i = j;
        int i2 = k;
        double a = Math.atan2(this.inA, (this.normals.get(i2).getX() * this.normals.get(i).getX()) + (this.normals.get(i2).getY() * this.normals.get(i).getY()));
        double X = this.normals.get(i2).getX();
        double Y = this.normals.get(i2).getY();
        int i3 = 0;
        for (int steps = Math.max((int) Math.round(this.stepsPerRad * Math.abs(a)), 1); i3 < steps; steps = steps) {
            Path path = this.destPoly;
            double x = (double) ((Point.LongPoint) this.srcPoly.get(i)).getX();
            Double.isNaN(x);
            long round = Math.round(x + (this.delta * X));
            double y = (double) ((Point.LongPoint) this.srcPoly.get(i)).getY();
            Double.isNaN(y);
            path.add(new Point.LongPoint(round, Math.round(y + (this.delta * Y))));
            double X2 = X;
            double d = this.cos;
            double d2 = this.sin;
            X = (X * d) - (d2 * Y);
            Y = (d2 * X2) + (d * Y);
            i3++;
            int i4 = k;
            a = a;
        }
        Path path2 = this.destPoly;
        double x2 = (double) ((Point.LongPoint) this.srcPoly.get(i)).getX();
        Double.isNaN(x2);
        long round2 = Math.round(x2 + (this.normals.get(i).getX() * this.delta));
        double y2 = (double) ((Point.LongPoint) this.srcPoly.get(i)).getY();
        double d3 = X;
        Double.isNaN(y2);
        path2.add(new Point.LongPoint(round2, Math.round(y2 + (this.normals.get(i).getY() * this.delta))));
    }

    private void doSquare(int j, int k, boolean addExtra) {
        int i = j;
        int i2 = k;
        double nkx = this.normals.get(i2).getX();
        double nky = this.normals.get(i2).getY();
        double njx = this.normals.get(i).getX();
        double njy = this.normals.get(i).getY();
        double sjx = (double) ((Point.LongPoint) this.srcPoly.get(i)).getX();
        double sjy = (double) ((Point.LongPoint) this.srcPoly.get(i)).getY();
        double njx2 = njx;
        double dx = Math.tan(Math.atan2(this.inA, (nkx * njx) + (nky * njy)) / 4.0d);
        Path path = this.destPoly;
        double njy2 = njy;
        double njy3 = this.delta;
        double d = 0.0d;
        double d2 = addExtra ? nky * dx : 0.0d;
        Double.isNaN(sjx);
        long round = Math.round((njy3 * (nkx - d2)) + sjx);
        double d3 = this.delta;
        double d4 = addExtra ? nkx * dx : 0.0d;
        Double.isNaN(sjy);
        path.add(new Point.LongPoint(round, Math.round((d3 * (nky + d4)) + sjy), 0));
        Path path2 = this.destPoly;
        double d5 = this.delta;
        double d6 = addExtra ? njy2 * dx : 0.0d;
        Double.isNaN(sjx);
        long round2 = Math.round((d5 * (njx2 + d6)) + sjx);
        double d7 = this.delta;
        if (addExtra) {
            d = njx2 * dx;
        }
        Double.isNaN(sjy);
        path2.add(new Point.LongPoint(round2, Math.round((d7 * (njy2 - d)) + sjy), 0));
    }

    public void execute(Paths solution, double delta2) {
        Paths paths = solution;
        double d = delta2;
        solution.clear();
        fixOrientations();
        doOffset(d);
        DefaultClipper clpr = new DefaultClipper(1);
        clpr.addPaths(this.destPolys, IClipper.PolyType.SUBJECT, true);
        if (d > 0.0d) {
            clpr.execute(IClipper.ClipType.UNION, paths, IClipper.PolyFillType.POSITIVE, IClipper.PolyFillType.POSITIVE);
            return;
        }
        LongRect r = this.destPolys.getBounds();
        Path outer = new Path(4);
        outer.add(new Point.LongPoint(r.left - 10, r.bottom + 10, 0));
        outer.add(new Point.LongPoint(r.right + 10, r.bottom + 10, 0));
        outer.add(new Point.LongPoint(r.right + 10, r.top - 10, 0));
        outer.add(new Point.LongPoint(r.left - 10, r.top - 10, 0));
        clpr.addPath(outer, IClipper.PolyType.SUBJECT, true);
        clpr.execute(IClipper.ClipType.UNION, paths, IClipper.PolyFillType.NEGATIVE, IClipper.PolyFillType.NEGATIVE);
        if (solution.size() > 0) {
            paths.remove(0);
        }
    }

    public void execute(PolyTree solution, double delta2) {
        PolyTree polyTree = solution;
        double d = delta2;
        solution.Clear();
        fixOrientations();
        doOffset(d);
        DefaultClipper clpr = new DefaultClipper(1);
        clpr.addPaths(this.destPolys, IClipper.PolyType.SUBJECT, true);
        if (d > 0.0d) {
            clpr.execute(IClipper.ClipType.UNION, polyTree, IClipper.PolyFillType.POSITIVE, IClipper.PolyFillType.POSITIVE);
            return;
        }
        LongRect r = this.destPolys.getBounds();
        Path outer = new Path(4);
        outer.add(new Point.LongPoint(r.left - 10, r.bottom + 10, 0));
        outer.add(new Point.LongPoint(r.right + 10, r.bottom + 10, 0));
        outer.add(new Point.LongPoint(r.right + 10, r.top - 10, 0));
        outer.add(new Point.LongPoint(r.left - 10, r.top - 10, 0));
        clpr.addPath(outer, IClipper.PolyType.SUBJECT, true);
        clpr.execute(IClipper.ClipType.UNION, polyTree, IClipper.PolyFillType.NEGATIVE, IClipper.PolyFillType.NEGATIVE);
        if (solution.getChildCount() != 1 || solution.getChilds().get(0).getChildCount() <= 0) {
            solution.Clear();
            return;
        }
        PolyNode outerNode = solution.getChilds().get(0);
        solution.getChilds().set(0, outerNode.getChilds().get(0));
        solution.getChilds().get(0).setParent(polyTree);
        for (int i = 1; i < outerNode.getChildCount(); i++) {
            polyTree.addChild(outerNode.getChilds().get(i));
        }
    }

    private void fixOrientations() {
        if (this.lowest.getX() < 0 || this.polyNodes.childs.get((int) this.lowest.getX()).getPolygon().orientation()) {
            for (int i = 0; i < this.polyNodes.getChildCount(); i++) {
                PolyNode node = this.polyNodes.childs.get(i);
                if (node.getEndType() == IClipper.EndType.CLOSED_LINE && !node.getPolygon().orientation()) {
                    Collections.reverse(node.getPolygon());
                }
            }
            return;
        }
        for (int i2 = 0; i2 < this.polyNodes.getChildCount(); i2++) {
            PolyNode node2 = this.polyNodes.childs.get(i2);
            if (node2.getEndType() == IClipper.EndType.CLOSED_POLYGON || (node2.getEndType() == IClipper.EndType.CLOSED_LINE && node2.getPolygon().orientation())) {
                Collections.reverse(node2.getPolygon());
            }
        }
    }

    private void offsetPoint(int j, int[] kV, IClipper.JoinType jointype) {
        double njy;
        double njx;
        long sjy;
        char c;
        int i = j;
        int k = kV[0];
        double nkx = this.normals.get(k).getX();
        double nky = this.normals.get(k).getY();
        double njy2 = this.normals.get(i).getY();
        double njx2 = this.normals.get(i).getX();
        long sjx = ((Point.LongPoint) this.srcPoly.get(i)).getX();
        long sjy2 = ((Point.LongPoint) this.srcPoly.get(i)).getY();
        int k2 = k;
        double d = (nkx * njy2) - (njx2 * nky);
        this.inA = d;
        long sjy3 = sjy2;
        if (Math.abs(d * this.delta) < 1.0d) {
            double cosA = (nkx * njx2) + (njy2 * nky);
            if (cosA > 0.0d) {
                Path path = this.destPoly;
                double d2 = cosA;
                double cosA2 = (double) sjx;
                double d3 = njy2;
                Double.isNaN(cosA2);
                long round = Math.round(cosA2 + (this.delta * nkx));
                double d4 = (double) sjy3;
                double d5 = njx2;
                Double.isNaN(d4);
                path.add(new Point.LongPoint(round, Math.round(d4 + (this.delta * nky)), 0));
                return;
            }
            njy = njy2;
            sjy = sjy3;
            njx = njx2;
        } else {
            njy = njy2;
            sjy = sjy3;
            njx = njx2;
            double njy3 = this.inA;
            if (njy3 > 1.0d) {
                this.inA = 1.0d;
            } else if (njy3 < -1.0d) {
                this.inA = -1.0d;
            }
        }
        if (this.inA * this.delta >= 0.0d) {
            double nkx2 = nkx;
            switch (C14321.f1483x1a9e4fe0[jointype.ordinal()]) {
                case 1:
                    int k3 = k2;
                    double r = (njx * nkx2) + 1.0d + (njy * nky);
                    if (r < this.miterLim) {
                        c = 0;
                        doSquare(i, k3, false);
                        break;
                    } else {
                        doMiter(i, k3, r);
                        c = 0;
                        break;
                    }
                case 2:
                    doSquare(i, k2, false);
                    c = 0;
                    break;
                case 3:
                    doRound(i, k2);
                    c = 0;
                    break;
                default:
                    int i2 = k2;
                    c = 0;
                    break;
            }
        } else {
            Path path2 = this.destPoly;
            double d6 = (double) sjx;
            Double.isNaN(d6);
            long round2 = Math.round(d6 + (this.delta * nkx));
            double d7 = (double) sjy;
            double d8 = nkx;
            Double.isNaN(d7);
            path2.add(new Point.LongPoint(round2, Math.round(d7 + (this.delta * nky))));
            this.destPoly.add(this.srcPoly.get(i));
            Path path3 = this.destPoly;
            double d9 = (double) sjx;
            Double.isNaN(d9);
            long round3 = Math.round(d9 + (this.delta * njx));
            double d10 = (double) sjy;
            Double.isNaN(d10);
            path3.add(new Point.LongPoint(round3, Math.round(d10 + (this.delta * njy))));
            int i3 = k2;
            c = 0;
        }
        kV[c] = i;
    }

    /* renamed from: com.itextpdf.kernel.pdf.canvas.parser.clipper.ClipperOffset$1 */
    static /* synthetic */ class C14321 {

        /* renamed from: $SwitchMap$com$itextpdf$kernel$pdf$canvas$parser$clipper$IClipper$JoinType */
        static final /* synthetic */ int[] f1483x1a9e4fe0;

        static {
            int[] iArr = new int[IClipper.JoinType.values().length];
            f1483x1a9e4fe0 = iArr;
            try {
                iArr[IClipper.JoinType.MITER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1483x1a9e4fe0[IClipper.JoinType.BEVEL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1483x1a9e4fe0[IClipper.JoinType.ROUND.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }
}
