package com.itextpdf.kernel.pdf.canvas.parser.clipper;

import com.itextpdf.kernel.pdf.canvas.parser.clipper.ClipperBase;
import com.itextpdf.kernel.pdf.canvas.parser.clipper.Edge;
import com.itextpdf.kernel.pdf.canvas.parser.clipper.IClipper;
import com.itextpdf.kernel.pdf.canvas.parser.clipper.Path;
import com.itextpdf.kernel.pdf.canvas.parser.clipper.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class DefaultClipper extends ClipperBase {
    private static final Logger LOGGER = Logger.getLogger(DefaultClipper.class.getName());
    private Edge activeEdges;
    private IClipper.PolyFillType clipFillType;
    private IClipper.ClipType clipType;
    private final List<Path.Join> ghostJoins;
    private final List<IntersectNode> intersectList;
    private final Comparator<IntersectNode> intersectNodeComparer;
    private final List<Path.Join> joins;
    private Path.Maxima maxima;
    protected final List<Path.OutRec> polyOuts;
    private final boolean reverseSolution;
    private ClipperBase.Scanbeam scanbeam;
    private Edge sortedEdges;
    private final boolean strictlySimple;
    private IClipper.PolyFillType subjFillType;
    private boolean usingPolyTree;
    public IClipper.IZFillCallback zFillFunction;

    private class IntersectNode {
        Edge Edge2;
        Edge edge1;

        /* renamed from: pt */
        private Point.LongPoint f1486pt;

        private IntersectNode() {
        }

        public Point.LongPoint getPt() {
            return this.f1486pt;
        }

        public void setPt(Point.LongPoint pt) {
            this.f1486pt = pt;
        }
    }

    private static void getHorzDirection(Edge HorzEdge, IClipper.Direction[] Dir, long[] Left, long[] Right) {
        if (HorzEdge.getBot().getX() < HorzEdge.getTop().getX()) {
            Left[0] = HorzEdge.getBot().getX();
            Right[0] = HorzEdge.getTop().getX();
            Dir[0] = IClipper.Direction.LEFT_TO_RIGHT;
            return;
        }
        Left[0] = HorzEdge.getTop().getX();
        Right[0] = HorzEdge.getBot().getX();
        Dir[0] = IClipper.Direction.RIGHT_TO_LEFT;
    }

    private static boolean getOverlap(long a1, long a2, long b1, long b2, long[] Left, long[] Right) {
        if (a1 < a2) {
            if (b1 < b2) {
                Left[0] = Math.max(a1, b1);
                Right[0] = Math.min(a2, b2);
            } else {
                Left[0] = Math.max(a1, b2);
                Right[0] = Math.min(a2, b1);
            }
        } else if (b1 < b2) {
            Left[0] = Math.max(a2, b1);
            Right[0] = Math.min(a1, b2);
        } else {
            Left[0] = Math.max(a2, b2);
            Right[0] = Math.min(a1, b1);
        }
        if (Left[0] < Right[0]) {
            return true;
        }
        return false;
    }

    private static boolean isParam1RightOfParam2(Path.OutRec outRec1, Path.OutRec outRec2) {
        do {
            outRec1 = outRec1.firstLeft;
            if (outRec1 == outRec2) {
                return true;
            }
        } while (outRec1 != null);
        return false;
    }

    private static int isPointInPolygon(Point.LongPoint pt, Path.OutPt op) {
        Path.OutPt op2;
        long ptx;
        Path.OutPt startOp;
        boolean z;
        Path.OutPt op3 = op;
        long ptx2 = pt.getX();
        long pty = pt.getY();
        long poly0x = op.getPt().getX();
        long poly0y = op.getPt().getY();
        long poly0x2 = poly0x;
        int result = 0;
        Path.OutPt startOp2 = op;
        while (true) {
            Path.OutPt op4 = startOp2.next;
            long poly1x = op4.getPt().getX();
            long poly1y = op4.getPt().getY();
            if (poly1y != pty) {
                op2 = op4;
            } else if (poly1x == ptx2) {
                return -1;
            } else if (poly0y == pty) {
                boolean z2 = poly1x > ptx2;
                if (poly0x2 < ptx2) {
                    op2 = op4;
                    z = true;
                } else {
                    op2 = op4;
                    z = false;
                }
                if (z2 == z) {
                    return -1;
                }
            } else {
                op2 = op4;
            }
            if ((poly0y < pty) == (poly1y < pty)) {
                startOp = op3;
                ptx = ptx2;
                long j = poly0x2;
            } else if (poly0x2 < ptx2) {
                startOp = op3;
                ptx = ptx2;
                long poly0x3 = poly0x2;
                if (poly1x > ptx) {
                    double d = (double) (poly0x3 - ptx);
                    double d2 = (double) (poly1y - pty);
                    Double.isNaN(d);
                    Double.isNaN(d2);
                    double d3 = d * d2;
                    double d4 = (double) (poly1x - ptx);
                    double d5 = (double) (poly0y - pty);
                    Double.isNaN(d4);
                    Double.isNaN(d5);
                    double d6 = d3 - (d4 * d5);
                    if (d6 == 0.0d) {
                        return -1;
                    }
                    if ((d6 > 0.0d) == (poly1y > poly0y)) {
                        result = 1 - result;
                    }
                }
            } else if (poly1x > ptx2) {
                result = 1 - result;
                startOp = op3;
                ptx = ptx2;
                long j2 = poly0x2;
            } else {
                startOp = op3;
                double d7 = (double) (poly0x2 - ptx2);
                long j3 = poly0x2;
                double d8 = (double) (poly1y - pty);
                Double.isNaN(d7);
                Double.isNaN(d8);
                double d9 = d7 * d8;
                double d10 = (double) (poly1x - ptx2);
                ptx = ptx2;
                double d11 = (double) (poly0y - pty);
                Double.isNaN(d10);
                Double.isNaN(d11);
                double d12 = d9 - (d10 * d11);
                if (d12 == 0.0d) {
                    return -1;
                }
                if ((d12 > 0.0d) == (poly1y > poly0y)) {
                    result = 1 - result;
                }
            }
            poly0x2 = poly1x;
            poly0y = poly1y;
            Path.OutPt startOp3 = startOp;
            Path.OutPt op5 = op2;
            if (startOp3 == op5) {
                return result;
            }
            ptx2 = ptx;
            Path.OutPt outPt = op5;
            op3 = startOp3;
            startOp2 = outPt;
        }
    }

    private static boolean joinHorz(Path.OutPt op1, Path.OutPt op1b, Path.OutPt op2, Path.OutPt op2b, Point.LongPoint Pt, boolean DiscardLeft) {
        Path.OutPt op1b2;
        Path.OutPt op2b2;
        IClipper.Direction Dir1 = op1.getPt().getX() > op1b.getPt().getX() ? IClipper.Direction.RIGHT_TO_LEFT : IClipper.Direction.LEFT_TO_RIGHT;
        IClipper.Direction Dir2 = op2.getPt().getX() > op2b.getPt().getX() ? IClipper.Direction.RIGHT_TO_LEFT : IClipper.Direction.LEFT_TO_RIGHT;
        boolean z = false;
        if (Dir1 == Dir2) {
            return false;
        }
        if (Dir1 == IClipper.Direction.LEFT_TO_RIGHT) {
            while (op1.next.getPt().getX() <= Pt.getX() && op1.next.getPt().getX() >= op1.getPt().getX() && op1.next.getPt().getY() == Pt.getY()) {
                op1 = op1.next;
            }
            if (DiscardLeft && op1.getPt().getX() != Pt.getX()) {
                op1 = op1.next;
            }
            op1b2 = op1.duplicate(!DiscardLeft);
            if (!op1b2.getPt().equals(Pt)) {
                op1 = op1b2;
                op1.setPt(Pt);
                op1b2 = op1.duplicate(!DiscardLeft);
            }
        } else {
            while (op1.next.getPt().getX() >= Pt.getX() && op1.next.getPt().getX() <= op1.getPt().getX() && op1.next.getPt().getY() == Pt.getY()) {
                op1 = op1.next;
            }
            if (!DiscardLeft && op1.getPt().getX() != Pt.getX()) {
                op1 = op1.next;
            }
            op1b2 = op1.duplicate(DiscardLeft);
            if (!op1b2.getPt().equals(Pt)) {
                op1 = op1b2;
                op1.setPt(Pt);
                op1b2 = op1.duplicate(DiscardLeft);
            }
        }
        if (Dir2 == IClipper.Direction.LEFT_TO_RIGHT) {
            while (op2.next.getPt().getX() <= Pt.getX() && op2.next.getPt().getX() >= op2.getPt().getX() && op2.next.getPt().getY() == Pt.getY()) {
                op2 = op2.next;
            }
            if (DiscardLeft && op2.getPt().getX() != Pt.getX()) {
                op2 = op2.next;
            }
            op2b2 = op2.duplicate(!DiscardLeft);
            if (!op2b2.getPt().equals(Pt)) {
                op2 = op2b2;
                op2.setPt(Pt);
                op2b2 = op2.duplicate(!DiscardLeft);
            }
        } else {
            while (op2.next.getPt().getX() >= Pt.getX() && op2.next.getPt().getX() <= op2.getPt().getX() && op2.next.getPt().getY() == Pt.getY()) {
                op2 = op2.next;
            }
            if (!DiscardLeft && op2.getPt().getX() != Pt.getX()) {
                op2 = op2.next;
            }
            op2b2 = op2.duplicate(DiscardLeft);
            if (!op2b2.getPt().equals(Pt)) {
                op2 = op2b2;
                op2.setPt(Pt);
                op2b2 = op2.duplicate(DiscardLeft);
            }
        }
        if (Dir1 == IClipper.Direction.LEFT_TO_RIGHT) {
            z = true;
        }
        if (z == DiscardLeft) {
            op1.prev = op2;
            op2.next = op1;
            op1b2.next = op2b2;
            op2b2.prev = op1b2;
        } else {
            op1.next = op2;
            op2.prev = op1;
            op1b2.prev = op2b2;
            op2b2.next = op1b2;
        }
        return true;
    }

    private boolean joinPoints(Path.Join j, Path.OutRec outRec1, Path.OutRec outRec2) {
        Path.OutPt op1b;
        Path.OutPt op2b;
        Point.LongPoint Pt;
        Path.Join join = j;
        Path.OutRec outRec = outRec1;
        Path.OutRec outRec3 = outRec2;
        Path.OutPt op1 = join.outPt1;
        Path.OutPt op2 = join.outPt2;
        boolean DiscardLeftSide = true;
        boolean isHorizontal = join.outPt1.getPt().getY() == j.getOffPt().getY();
        if (!isHorizontal || !j.getOffPt().equals(join.outPt1.getPt()) || !j.getOffPt().equals(join.outPt2.getPt())) {
            if (isHorizontal) {
                Path.OutPt op1b2 = op1;
                while (op1.prev.getPt().getY() == op1.getPt().getY() && op1.prev != op1b2 && op1.prev != op2) {
                    op1 = op1.prev;
                }
                while (op1b2.next.getPt().getY() == op1b2.getPt().getY() && op1b2.next != op1 && op1b2.next != op2) {
                    op1b2 = op1b2.next;
                }
                if (op1b2.next == op1 || op1b2.next == op2) {
                    return false;
                }
                Path.OutPt op2b2 = op2;
                while (op2.prev.getPt().getY() == op2.getPt().getY() && op2.prev != op2b2 && op2.prev != op1b2) {
                    op2 = op2.prev;
                }
                while (op2b2.next.getPt().getY() == op2b2.getPt().getY() && op2b2.next != op2 && op2b2.next != op1) {
                    op2b2 = op2b2.next;
                }
                if (op2b2.next == op2 || op2b2.next == op1) {
                    return false;
                }
                long[] LeftV = new long[1];
                long[] RightV = new long[1];
                if (!getOverlap(op1.getPt().getX(), op1b2.getPt().getX(), op2.getPt().getX(), op2b2.getPt().getX(), LeftV, RightV)) {
                    return false;
                }
                long Left = LeftV[0];
                long Right = RightV[0];
                if (op1.getPt().getX() >= Left && op1.getPt().getX() <= Right) {
                    Point.LongPoint Pt2 = new Point.LongPoint(op1.getPt());
                    if (op1.getPt().getX() <= op1b2.getPt().getX()) {
                        DiscardLeftSide = false;
                    }
                    Pt = Pt2;
                } else if (op2.getPt().getX() >= Left && op2.getPt().getX() <= Right) {
                    Point.LongPoint Pt3 = new Point.LongPoint(op2.getPt());
                    if (op2.getPt().getX() <= op2b2.getPt().getX()) {
                        DiscardLeftSide = false;
                    }
                    Pt = Pt3;
                } else if (op1b2.getPt().getX() < Left || op1b2.getPt().getX() > Right) {
                    Point.LongPoint Pt4 = new Point.LongPoint(op2b2.getPt());
                    if (op2b2.getPt().getX() <= op2.getPt().getX()) {
                        DiscardLeftSide = false;
                    }
                    Pt = Pt4;
                } else {
                    Point.LongPoint Pt5 = new Point.LongPoint(op1b2.getPt());
                    if (op1b2.getPt().getX() <= op1.getPt().getX()) {
                        DiscardLeftSide = false;
                    }
                    Pt = Pt5;
                }
                join.outPt1 = op1;
                join.outPt2 = op2;
                return joinHorz(op1, op1b2, op2, op2b2, Pt, DiscardLeftSide);
            }
            Path.OutPt op1b3 = op1.next;
            while (op1b.getPt().equals(op1.getPt()) && op1b != op1) {
                op1b3 = op1b.next;
            }
            boolean Reverse1 = op1b.getPt().getY() > op1.getPt().getY() || !Point.slopesEqual(op1.getPt(), op1b.getPt(), j.getOffPt(), this.useFullRange);
            if (Reverse1) {
                op1b = op1.prev;
                while (op1b.getPt().equals(op1.getPt()) && op1b != op1) {
                    op1b = op1b.prev;
                }
                if (op1b.getPt().getY() > op1.getPt().getY() || !Point.slopesEqual(op1.getPt(), op1b.getPt(), j.getOffPt(), this.useFullRange)) {
                    return false;
                }
            }
            Path.OutPt op2b3 = op2.next;
            while (op2b.getPt().equals(op2.getPt()) && op2b != op2) {
                op2b3 = op2b.next;
            }
            boolean Reverse2 = op2b.getPt().getY() > op2.getPt().getY() || !Point.slopesEqual(op2.getPt(), op2b.getPt(), j.getOffPt(), this.useFullRange);
            if (Reverse2) {
                op2b = op2.prev;
                while (op2b.getPt().equals(op2.getPt()) && op2b != op2) {
                    op2b = op2b.prev;
                }
                if (op2b.getPt().getY() > op2.getPt().getY() || !Point.slopesEqual(op2.getPt(), op2b.getPt(), j.getOffPt(), this.useFullRange)) {
                    return false;
                }
            }
            if (op1b == op1 || op2b == op2 || op1b == op2b || (outRec == outRec3 && Reverse1 == Reverse2)) {
                return false;
            }
            if (Reverse1) {
                Path.OutPt op1b4 = op1.duplicate(false);
                Path.OutPt op2b4 = op2.duplicate(true);
                op1.prev = op2;
                op2.next = op1;
                op1b4.next = op2b4;
                op2b4.prev = op1b4;
                join.outPt1 = op1;
                join.outPt2 = op1b4;
                return true;
            }
            Path.OutPt op1b5 = op1.duplicate(true);
            Path.OutPt op2b5 = op2.duplicate(false);
            op1.next = op2;
            op2.prev = op1;
            op1b5.prev = op2b5;
            op2b5.next = op1b5;
            join.outPt1 = op1;
            join.outPt2 = op1b5;
            return true;
        } else if (outRec != outRec3) {
            return false;
        } else {
            Path.OutPt op1b6 = join.outPt1.next;
            while (op1b6 != op1 && op1b6.getPt().equals(j.getOffPt())) {
                op1b6 = op1b6.next;
            }
            boolean reverse1 = op1b6.getPt().getY() > j.getOffPt().getY();
            Path.OutPt op2b6 = join.outPt2.next;
            while (op2b6 != op2 && op2b6.getPt().equals(j.getOffPt())) {
                op2b6 = op2b6.next;
            }
            if (reverse1 == (op2b6.getPt().getY() > j.getOffPt().getY())) {
                return false;
            }
            if (reverse1) {
                Path.OutPt op1b7 = op1.duplicate(false);
                Path.OutPt op2b7 = op2.duplicate(true);
                op1.prev = op2;
                op2.next = op1;
                op1b7.next = op2b7;
                op2b7.prev = op1b7;
                join.outPt1 = op1;
                join.outPt2 = op1b7;
                return true;
            }
            Path.OutPt op1b8 = op1.duplicate(true);
            Path.OutPt op2b8 = op2.duplicate(false);
            op1.next = op2;
            op2.prev = op1;
            op1b8.prev = op2b8;
            op2b8.next = op1b8;
            join.outPt1 = op1;
            join.outPt2 = op1b8;
            return true;
        }
    }

    private static Paths minkowski(Path pattern, Path path, boolean IsSum, boolean IsClosed) {
        Path path2 = path;
        int delta = IsClosed;
        int polyCnt = pattern.size();
        int pathCnt = path.size();
        Paths result = new Paths(pathCnt);
        if (IsSum) {
            for (int i = 0; i < pathCnt; i++) {
                Path p = new Path(polyCnt);
                Iterator it = pattern.iterator();
                while (it.hasNext()) {
                    Point.LongPoint ip = (Point.LongPoint) it.next();
                    Iterator it2 = it;
                    Point.LongPoint longPoint = r9;
                    Point.LongPoint longPoint2 = new Point.LongPoint(ip.getX() + ((Point.LongPoint) path2.get(i)).getX(), ip.getY() + ((Point.LongPoint) path2.get(i)).getY(), 0);
                    p.add(longPoint);
                    it = it2;
                }
                result.add(p);
            }
        } else {
            int i2 = 0;
            while (i2 < pathCnt) {
                Path p2 = new Path(polyCnt);
                Iterator it3 = pattern.iterator();
                while (it3.hasNext()) {
                    Point.LongPoint ip2 = (Point.LongPoint) it3.next();
                    long x = ((Point.LongPoint) path2.get(i2)).getX() - ip2.getX();
                    Point.LongPoint longPoint3 = r9;
                    Point.LongPoint longPoint4 = new Point.LongPoint(x, ((Point.LongPoint) path2.get(i2)).getY() - ip2.getY(), 0);
                    p2.add(longPoint3);
                    path2 = path;
                }
                result.add(p2);
                i2++;
                path2 = path;
            }
        }
        Paths quads = new Paths((pathCnt + ((int) delta)) * (polyCnt + 1));
        for (int i3 = 0; i3 < (pathCnt - 1) + delta; i3++) {
            for (int j = 0; j < polyCnt; j++) {
                Path quad = new Path(4);
                quad.add(((Path) result.get(i3 % pathCnt)).get(j % polyCnt));
                quad.add(((Path) result.get((i3 + 1) % pathCnt)).get(j % polyCnt));
                quad.add(((Path) result.get((i3 + 1) % pathCnt)).get((j + 1) % polyCnt));
                quad.add(((Path) result.get(i3 % pathCnt)).get((j + 1) % polyCnt));
                if (!quad.orientation()) {
                    Collections.reverse(quad);
                }
                quads.add(quad);
            }
        }
        return quads;
    }

    public static Paths minkowskiDiff(Path poly1, Path poly2) {
        Paths paths = minkowski(poly1, poly2, false, true);
        DefaultClipper c = new DefaultClipper();
        c.addPaths(paths, IClipper.PolyType.SUBJECT, true);
        c.execute(IClipper.ClipType.UNION, paths, IClipper.PolyFillType.NON_ZERO, IClipper.PolyFillType.NON_ZERO);
        return paths;
    }

    public static Paths minkowskiSum(Path pattern, Path path, boolean pathIsClosed) {
        Paths paths = minkowski(pattern, path, true, pathIsClosed);
        DefaultClipper c = new DefaultClipper();
        c.addPaths(paths, IClipper.PolyType.SUBJECT, true);
        c.execute(IClipper.ClipType.UNION, paths, IClipper.PolyFillType.NON_ZERO, IClipper.PolyFillType.NON_ZERO);
        return paths;
    }

    public static Paths minkowskiSum(Path pattern, Paths paths, boolean pathIsClosed) {
        Paths solution = new Paths();
        DefaultClipper c = new DefaultClipper();
        for (int i = 0; i < paths.size(); i++) {
            c.addPaths(minkowski(pattern, (Path) paths.get(i), true, pathIsClosed), IClipper.PolyType.SUBJECT, true);
            if (pathIsClosed) {
                c.addPath(((Path) paths.get(i)).TranslatePath((Point.LongPoint) pattern.get(0)), IClipper.PolyType.CLIP, true);
            }
        }
        c.execute(IClipper.ClipType.UNION, solution, IClipper.PolyFillType.NON_ZERO, IClipper.PolyFillType.NON_ZERO);
        return solution;
    }

    private static boolean poly2ContainsPoly1(Path.OutPt outPt1, Path.OutPt outPt2) {
        Path.OutPt op = outPt1;
        do {
            int res = isPointInPolygon(op.getPt(), outPt2);
            if (res < 0) {
                op = op.next;
            } else if (res > 0) {
                return true;
            } else {
                return false;
            }
        } while (op != outPt1);
        return true;
    }

    public static Paths simplifyPolygon(Path poly) {
        return simplifyPolygon(poly, IClipper.PolyFillType.EVEN_ODD);
    }

    public static Paths simplifyPolygon(Path poly, IClipper.PolyFillType fillType) {
        Paths result = new Paths();
        DefaultClipper c = new DefaultClipper(2);
        c.addPath(poly, IClipper.PolyType.SUBJECT, true);
        c.execute(IClipper.ClipType.UNION, result, fillType, fillType);
        return result;
    }

    public static Paths simplifyPolygons(Paths polys) {
        return simplifyPolygons(polys, IClipper.PolyFillType.EVEN_ODD);
    }

    public static Paths simplifyPolygons(Paths polys, IClipper.PolyFillType fillType) {
        Paths result = new Paths();
        DefaultClipper c = new DefaultClipper(2);
        c.addPaths(polys, IClipper.PolyType.SUBJECT, true);
        c.execute(IClipper.ClipType.UNION, result, fillType, fillType);
        return result;
    }

    public DefaultClipper() {
        this(0);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DefaultClipper(int InitOptions) {
        super((InitOptions & 4) != 0);
        boolean z = true;
        this.scanbeam = null;
        this.maxima = null;
        this.activeEdges = null;
        this.sortedEdges = null;
        this.intersectList = new ArrayList();
        this.intersectNodeComparer = new Comparator<IntersectNode>() {
            public int compare(IntersectNode o1, IntersectNode o2) {
                long i = o2.getPt().getY() - o1.getPt().getY();
                if (i > 0) {
                    return 1;
                }
                if (i < 0) {
                    return -1;
                }
                return 0;
            }
        };
        this.usingPolyTree = false;
        this.polyOuts = new ArrayList();
        this.joins = new ArrayList();
        this.ghostJoins = new ArrayList();
        this.reverseSolution = (InitOptions & 1) != 0;
        this.strictlySimple = (InitOptions & 2) == 0 ? false : z;
        this.zFillFunction = null;
    }

    private void insertScanbeam(long Y) {
        ClipperBase.Scanbeam scanbeam2 = this.scanbeam;
        if (scanbeam2 == null) {
            ClipperBase.Scanbeam scanbeam3 = new ClipperBase.Scanbeam();
            this.scanbeam = scanbeam3;
            scanbeam3.next = null;
            this.scanbeam.f1482y = Y;
        } else if (Y > scanbeam2.f1482y) {
            ClipperBase.Scanbeam newSb = new ClipperBase.Scanbeam();
            newSb.f1482y = Y;
            newSb.next = this.scanbeam;
            this.scanbeam = newSb;
        } else {
            ClipperBase.Scanbeam sb2 = this.scanbeam;
            while (sb2.next != null && Y <= sb2.next.f1482y) {
                sb2 = sb2.next;
            }
            if (Y != sb2.f1482y) {
                ClipperBase.Scanbeam newSb2 = new ClipperBase.Scanbeam();
                newSb2.f1482y = Y;
                newSb2.next = sb2.next;
                sb2.next = newSb2;
            }
        }
    }

    private void InsertMaxima(long X) {
        Path.Maxima newMax = new Path.Maxima();
        newMax.f1489X = X;
        Path.Maxima maxima2 = this.maxima;
        if (maxima2 == null) {
            this.maxima = newMax;
            newMax.Next = null;
            this.maxima.Prev = null;
        } else if (X < maxima2.f1489X) {
            newMax.Next = this.maxima;
            newMax.Prev = null;
            this.maxima = newMax;
        } else {
            Path.Maxima m = this.maxima;
            while (m.Next != null && X >= m.Next.f1489X) {
                m = m.Next;
            }
            if (X != m.f1489X) {
                newMax.Next = m.Next;
                newMax.Prev = m;
                if (m.Next != null) {
                    m.Next.Prev = newMax;
                }
                m.Next = newMax;
            }
        }
    }

    private void addEdgeToSEL(Edge edge) {
        LOGGER.entering(DefaultClipper.class.getName(), "addEdgeToSEL");
        Edge edge2 = this.sortedEdges;
        if (edge2 == null) {
            this.sortedEdges = edge;
            edge.prevInSEL = null;
            edge.nextInSEL = null;
            return;
        }
        edge.nextInSEL = edge2;
        edge.prevInSEL = null;
        this.sortedEdges.prevInSEL = edge;
        this.sortedEdges = edge;
    }

    private void addGhostJoin(Path.OutPt Op, Point.LongPoint OffPt) {
        Path.Join j = new Path.Join();
        j.outPt1 = Op;
        j.setOffPt(OffPt);
        this.ghostJoins.add(j);
    }

    private void addJoin(Path.OutPt Op1, Path.OutPt Op2, Point.LongPoint OffPt) {
        LOGGER.entering(DefaultClipper.class.getName(), "addJoin");
        Path.Join j = new Path.Join();
        j.outPt1 = Op1;
        j.outPt2 = Op2;
        j.setOffPt(OffPt);
        this.joins.add(j);
    }

    private void addLocalMaxPoly(Edge e1, Edge e2, Point.LongPoint pt) {
        addOutPt(e1, pt);
        if (e2.windDelta == 0) {
            addOutPt(e2, pt);
        }
        if (e1.outIdx == e2.outIdx) {
            e1.outIdx = -1;
            e2.outIdx = -1;
        } else if (e1.outIdx < e2.outIdx) {
            appendPolygon(e1, e2);
        } else {
            appendPolygon(e2, e1);
        }
    }

    private Path.OutPt addLocalMinPoly(Edge e1, Edge e2, Point.LongPoint pt) {
        Edge prevE;
        Edge e;
        Path.OutPt result;
        LOGGER.entering(DefaultClipper.class.getName(), "addLocalMinPoly");
        if (e2.isHorizontal() || e1.deltaX > e2.deltaX) {
            result = addOutPt(e1, pt);
            e2.outIdx = e1.outIdx;
            e1.side = Edge.Side.LEFT;
            e2.side = Edge.Side.RIGHT;
            e = e1;
            if (e.prevInAEL == e2) {
                prevE = e2.prevInAEL;
            } else {
                prevE = e.prevInAEL;
            }
        } else {
            result = addOutPt(e2, pt);
            e1.outIdx = e2.outIdx;
            e1.side = Edge.Side.RIGHT;
            e2.side = Edge.Side.LEFT;
            e = e2;
            if (e.prevInAEL == e1) {
                prevE = e1.prevInAEL;
            } else {
                prevE = e.prevInAEL;
            }
        }
        if (prevE != null && prevE.outIdx >= 0 && Edge.topX(prevE, pt.getY()) == Edge.topX(e, pt.getY()) && Edge.slopesEqual(e, prevE, this.useFullRange) && e.windDelta != 0 && prevE.windDelta != 0) {
            addJoin(result, addOutPt(prevE, pt), e.getTop());
        }
        return result;
    }

    private Path.OutPt addOutPt(Edge e, Point.LongPoint pt) {
        Logger logger = LOGGER;
        logger.entering(DefaultClipper.class.getName(), "addOutPt");
        boolean ToFront = true;
        if (e.outIdx < 0) {
            Path.OutRec outRec = createOutRec();
            if (e.windDelta != 0) {
                ToFront = false;
            }
            outRec.isOpen = ToFront;
            Path.OutPt newOp = new Path.OutPt();
            outRec.pts = newOp;
            newOp.idx = outRec.Idx;
            newOp.f1490pt = pt;
            newOp.next = newOp;
            newOp.prev = newOp;
            if (!outRec.isOpen) {
                setHoleState(e, outRec);
            }
            e.outIdx = outRec.Idx;
            return newOp;
        }
        Path.OutRec outRec2 = this.polyOuts.get(e.outIdx);
        Path.OutPt op = outRec2.getPoints();
        if (e.side != Edge.Side.LEFT) {
            ToFront = false;
        }
        logger.finest("op=" + op.getPointCount());
        logger.finest(ToFront + " " + pt + " " + op.getPt());
        if (ToFront && pt.equals(op.getPt())) {
            return op;
        }
        if (!ToFront && pt.equals(op.prev.getPt())) {
            return op.prev;
        }
        Path.OutPt newOp2 = new Path.OutPt();
        newOp2.idx = outRec2.Idx;
        newOp2.setPt(new Point.LongPoint(pt));
        newOp2.next = op;
        newOp2.prev = op.prev;
        newOp2.prev.next = newOp2;
        op.prev = newOp2;
        if (ToFront) {
            outRec2.setPoints(newOp2);
        }
        return newOp2;
    }

    private Path.OutPt GetLastOutPt(Edge e) {
        Path.OutRec outRec = this.polyOuts.get(e.outIdx);
        if (e.side == Edge.Side.LEFT) {
            return outRec.pts;
        }
        return outRec.pts.prev;
    }

    private void appendPolygon(Edge e1, Edge e2) {
        Path.OutRec holeStateRec;
        Edge.Side side;
        Logger logger = LOGGER;
        logger.entering(DefaultClipper.class.getName(), "appendPolygon");
        Path.OutRec outRec1 = this.polyOuts.get(e1.outIdx);
        Path.OutRec outRec2 = this.polyOuts.get(e2.outIdx);
        logger.finest("" + e1.outIdx);
        logger.finest("" + e2.outIdx);
        if (isParam1RightOfParam2(outRec1, outRec2)) {
            holeStateRec = outRec2;
        } else if (isParam1RightOfParam2(outRec2, outRec1)) {
            holeStateRec = outRec1;
        } else {
            holeStateRec = Path.OutPt.getLowerMostRec(outRec1, outRec2);
        }
        Path.OutPt p1_lft = outRec1.getPoints();
        Path.OutPt p1_rt = p1_lft.prev;
        Path.OutPt p2_lft = outRec2.getPoints();
        Path.OutPt p2_rt = p2_lft.prev;
        logger.finest("p1_lft.getPointCount() = " + p1_lft.getPointCount());
        logger.finest("p1_rt.getPointCount() = " + p1_rt.getPointCount());
        logger.finest("p2_lft.getPointCount() = " + p2_lft.getPointCount());
        logger.finest("p2_rt.getPointCount() = " + p2_rt.getPointCount());
        if (e1.side == Edge.Side.LEFT) {
            if (e2.side == Edge.Side.LEFT) {
                p2_lft.reversePolyPtLinks();
                p2_lft.next = p1_lft;
                p1_lft.prev = p2_lft;
                p1_rt.next = p2_rt;
                p2_rt.prev = p1_rt;
                outRec1.setPoints(p2_rt);
            } else {
                p2_rt.next = p1_lft;
                p1_lft.prev = p2_rt;
                p2_lft.prev = p1_rt;
                p1_rt.next = p2_lft;
                outRec1.setPoints(p2_lft);
            }
            side = Edge.Side.LEFT;
        } else {
            if (e2.side == Edge.Side.RIGHT) {
                p2_lft.reversePolyPtLinks();
                p1_rt.next = p2_rt;
                p2_rt.prev = p1_rt;
                p2_lft.next = p1_lft;
                p1_lft.prev = p2_lft;
            } else {
                p1_rt.next = p2_lft;
                p2_lft.prev = p1_rt;
                p1_lft.prev = p2_rt;
                p2_rt.next = p1_lft;
            }
            side = Edge.Side.RIGHT;
        }
        outRec1.bottomPt = null;
        if (holeStateRec.equals(outRec2)) {
            if (outRec2.firstLeft != outRec1) {
                outRec1.firstLeft = outRec2.firstLeft;
            }
            outRec1.isHole = outRec2.isHole;
        }
        outRec2.setPoints((Path.OutPt) null);
        outRec2.bottomPt = null;
        outRec2.firstLeft = outRec1;
        int OKIdx = e1.outIdx;
        int ObsoleteIdx = e2.outIdx;
        e1.outIdx = -1;
        e2.outIdx = -1;
        Edge e = this.activeEdges;
        while (true) {
            if (e == null) {
                break;
            } else if (e.outIdx == ObsoleteIdx) {
                e.outIdx = OKIdx;
                e.side = side;
                break;
            } else {
                e = e.nextInAEL;
            }
        }
        outRec2.Idx = outRec1.Idx;
    }

    private void buildIntersectList(long topY) {
        if (this.activeEdges != null) {
            Edge e = this.activeEdges;
            this.sortedEdges = e;
            while (e != null) {
                e.prevInSEL = e.prevInAEL;
                e.nextInSEL = e.nextInAEL;
                e.getCurrent().setX(Long.valueOf(Edge.topX(e, topY)));
                e = e.nextInAEL;
            }
            boolean isModified = true;
            while (isModified && this.sortedEdges != null) {
                isModified = false;
                Edge e2 = this.sortedEdges;
                while (e2.nextInSEL != null) {
                    Edge eNext = e2.nextInSEL;
                    Point.LongPoint[] pt = new Point.LongPoint[1];
                    if (e2.getCurrent().getX() > eNext.getCurrent().getX()) {
                        intersectPoint(e2, eNext, pt);
                        IntersectNode newNode = new IntersectNode();
                        newNode.edge1 = e2;
                        newNode.Edge2 = eNext;
                        newNode.setPt(pt[0]);
                        this.intersectList.add(newNode);
                        swapPositionsInSEL(e2, eNext);
                        isModified = true;
                    } else {
                        e2 = eNext;
                    }
                }
                if (e2.prevInSEL == null) {
                    break;
                }
                e2.prevInSEL.nextInSEL = null;
            }
            this.sortedEdges = null;
        }
    }

    private void buildResult(Paths polyg) {
        polyg.clear();
        for (int i = 0; i < this.polyOuts.size(); i++) {
            Path.OutRec outRec = this.polyOuts.get(i);
            if (outRec.getPoints() != null) {
                Path.OutPt p = outRec.getPoints().prev;
                int cnt = p.getPointCount();
                LOGGER.finest("cnt = " + cnt);
                if (cnt >= 2) {
                    Path pg = new Path(cnt);
                    for (int j = 0; j < cnt; j++) {
                        pg.add(p.getPt());
                        p = p.prev;
                    }
                    polyg.add(pg);
                }
            }
        }
    }

    private void buildResult2(PolyTree polytree) {
        polytree.Clear();
        for (int i = 0; i < this.polyOuts.size(); i++) {
            Path.OutRec outRec = this.polyOuts.get(i);
            int cnt = outRec.getPoints() != null ? outRec.getPoints().getPointCount() : 0;
            if ((!outRec.isOpen || cnt >= 2) && (outRec.isOpen || cnt >= 3)) {
                outRec.fixHoleLinkage();
                PolyNode pn = new PolyNode();
                polytree.getAllPolys().add(pn);
                outRec.polyNode = pn;
                Path.OutPt op = outRec.getPoints().prev;
                for (int j = 0; j < cnt; j++) {
                    pn.getPolygon().add(op.getPt());
                    op = op.prev;
                }
            }
        }
        for (int i2 = 0; i2 < this.polyOuts.size(); i2++) {
            Path.OutRec outRec2 = this.polyOuts.get(i2);
            if (outRec2.polyNode != null) {
                if (outRec2.isOpen) {
                    outRec2.polyNode.setOpen(true);
                    polytree.addChild(outRec2.polyNode);
                } else if (outRec2.firstLeft == null || outRec2.firstLeft.polyNode == null) {
                    polytree.addChild(outRec2.polyNode);
                } else {
                    outRec2.firstLeft.polyNode.addChild(outRec2.polyNode);
                }
            }
        }
    }

    private void copyAELToSEL() {
        Edge e = this.activeEdges;
        this.sortedEdges = e;
        while (e != null) {
            e.prevInSEL = e.prevInAEL;
            e.nextInSEL = e.nextInAEL;
            e = e.nextInAEL;
        }
    }

    private Path.OutRec createOutRec() {
        Path.OutRec result = new Path.OutRec();
        result.Idx = -1;
        result.isHole = false;
        result.isOpen = false;
        result.firstLeft = null;
        result.setPoints((Path.OutPt) null);
        result.bottomPt = null;
        result.polyNode = null;
        this.polyOuts.add(result);
        result.Idx = this.polyOuts.size() - 1;
        return result;
    }

    private void deleteFromAEL(Edge e) {
        Logger logger = LOGGER;
        Class<DefaultClipper> cls = DefaultClipper.class;
        logger.entering(cls.getName(), "deleteFromAEL");
        Edge AelPrev = e.prevInAEL;
        Edge AelNext = e.nextInAEL;
        if (AelPrev != null || AelNext != null || e == this.activeEdges) {
            if (AelPrev != null) {
                AelPrev.nextInAEL = AelNext;
            } else {
                this.activeEdges = AelNext;
            }
            if (AelNext != null) {
                AelNext.prevInAEL = AelPrev;
            }
            e.nextInAEL = null;
            e.prevInAEL = null;
            logger.exiting(cls.getName(), "deleteFromAEL");
        }
    }

    private void deleteFromSEL(Edge e) {
        LOGGER.entering(DefaultClipper.class.getName(), "deleteFromSEL");
        Edge SelPrev = e.prevInSEL;
        Edge SelNext = e.nextInSEL;
        if (SelPrev != null || SelNext != null || e.equals(this.sortedEdges)) {
            if (SelPrev != null) {
                SelPrev.nextInSEL = SelNext;
            } else {
                this.sortedEdges = SelNext;
            }
            if (SelNext != null) {
                SelNext.prevInSEL = SelPrev;
            }
            e.nextInSEL = null;
            e.prevInSEL = null;
        }
    }

    private boolean doHorzSegmentsOverlap(long seg1a, long seg1b, long seg2a, long seg2b) {
        if (seg1a > seg1b) {
            long tmp = seg1a;
            seg1a = seg1b;
            seg1b = tmp;
        }
        if (seg2a > seg2b) {
            long tmp2 = seg2a;
            seg2a = seg2b;
            seg2b = tmp2;
        }
        return seg1a < seg2b && seg2a < seg1b;
    }

    private void doMaxima(Edge e) {
        Edge eMaxPair = e.getMaximaPair();
        if (eMaxPair == null) {
            if (e.outIdx >= 0) {
                addOutPt(e, e.getTop());
            }
            deleteFromAEL(e);
            return;
        }
        Edge eNext = e.nextInAEL;
        while (eNext != null && eNext != eMaxPair) {
            Point.LongPoint tmp = new Point.LongPoint(e.getTop());
            intersectEdges(e, eNext, tmp);
            e.setTop(tmp);
            swapPositionsInAEL(e, eNext);
            eNext = e.nextInAEL;
        }
        if (e.outIdx == -1 && eMaxPair.outIdx == -1) {
            deleteFromAEL(e);
            deleteFromAEL(eMaxPair);
        } else if (e.outIdx >= 0 && eMaxPair.outIdx >= 0) {
            if (e.outIdx >= 0) {
                addLocalMaxPoly(e, eMaxPair, e.getTop());
            }
            deleteFromAEL(e);
            deleteFromAEL(eMaxPair);
        } else if (e.windDelta == 0) {
            if (e.outIdx >= 0) {
                addOutPt(e, e.getTop());
                e.outIdx = -1;
            }
            deleteFromAEL(e);
            if (eMaxPair.outIdx >= 0) {
                addOutPt(eMaxPair, e.getTop());
                eMaxPair.outIdx = -1;
            }
            deleteFromAEL(eMaxPair);
        } else {
            throw new IllegalStateException("DoMaxima error");
        }
    }

    private void doSimplePolygons() {
        int i = 0;
        while (i < this.polyOuts.size()) {
            int i2 = i + 1;
            Path.OutRec outrec = this.polyOuts.get(i);
            Path.OutPt op = outrec.getPoints();
            if (op == null || outrec.isOpen) {
                i = i2;
            } else {
                do {
                    Path.OutPt op2 = op.next;
                    while (op2 != outrec.getPoints()) {
                        if (op.getPt().equals(op2.getPt()) && !op2.next.equals(op) && !op2.prev.equals(op)) {
                            Path.OutPt op3 = op.prev;
                            Path.OutPt op4 = op2.prev;
                            op.prev = op4;
                            op4.next = op;
                            op2.prev = op3;
                            op3.next = op2;
                            outrec.setPoints(op);
                            Path.OutRec outrec2 = createOutRec();
                            outrec2.setPoints(op2);
                            updateOutPtIdxs(outrec2);
                            if (poly2ContainsPoly1(outrec2.getPoints(), outrec.getPoints())) {
                                outrec2.isHole = !outrec.isHole;
                                outrec2.firstLeft = outrec;
                                if (this.usingPolyTree) {
                                    fixupFirstLefts2(outrec2, outrec);
                                }
                            } else if (poly2ContainsPoly1(outrec.getPoints(), outrec2.getPoints())) {
                                outrec2.isHole = outrec.isHole;
                                outrec.isHole = !outrec2.isHole;
                                outrec2.firstLeft = outrec.firstLeft;
                                outrec.firstLeft = outrec2;
                                if (this.usingPolyTree) {
                                    fixupFirstLefts2(outrec, outrec2);
                                }
                            } else {
                                outrec2.isHole = outrec.isHole;
                                outrec2.firstLeft = outrec.firstLeft;
                                if (this.usingPolyTree) {
                                    fixupFirstLefts1(outrec, outrec2);
                                }
                            }
                            op2 = op;
                        }
                        op2 = op2.next;
                    }
                    op = op.next;
                } while (op != outrec.getPoints());
                i = i2;
            }
        }
    }

    private boolean EdgesAdjacent(IntersectNode inode) {
        return inode.edge1.nextInSEL == inode.Edge2 || inode.edge1.prevInSEL == inode.Edge2;
    }

    public boolean execute(IClipper.ClipType clipType2, Paths solution, IClipper.PolyFillType FillType) {
        return execute(clipType2, solution, FillType, FillType);
    }

    public boolean execute(IClipper.ClipType clipType2, PolyTree polytree) {
        return execute(clipType2, polytree, IClipper.PolyFillType.EVEN_ODD);
    }

    public boolean execute(IClipper.ClipType clipType2, PolyTree polytree, IClipper.PolyFillType FillType) {
        return execute(clipType2, polytree, FillType, FillType);
    }

    public boolean execute(IClipper.ClipType clipType2, Paths solution) {
        return execute(clipType2, solution, IClipper.PolyFillType.EVEN_ODD);
    }

    public boolean execute(IClipper.ClipType clipType2, Paths solution, IClipper.PolyFillType subjFillType2, IClipper.PolyFillType clipFillType2) {
        boolean succeeded;
        synchronized (this) {
            if (!this.hasOpenPaths) {
                solution.clear();
                this.subjFillType = subjFillType2;
                this.clipFillType = clipFillType2;
                this.clipType = clipType2;
                this.usingPolyTree = false;
                try {
                    succeeded = executeInternal();
                    if (succeeded) {
                        buildResult(solution);
                    }
                } finally {
                    this.polyOuts.clear();
                }
            } else {
                throw new IllegalStateException("Error: PolyTree struct is needed for open path clipping.");
            }
        }
        return succeeded;
    }

    public boolean execute(IClipper.ClipType clipType2, PolyTree polytree, IClipper.PolyFillType subjFillType2, IClipper.PolyFillType clipFillType2) {
        boolean succeeded;
        synchronized (this) {
            this.subjFillType = subjFillType2;
            this.clipFillType = clipFillType2;
            this.clipType = clipType2;
            this.usingPolyTree = true;
            try {
                succeeded = executeInternal();
                if (succeeded) {
                    buildResult2(polytree);
                }
            } finally {
                this.polyOuts.clear();
            }
        }
        return succeeded;
    }

    private boolean executeInternal() {
        try {
            reset();
            if (this.currentLM == null) {
                return false;
            }
            long botY = popScanbeam();
            while (true) {
                insertLocalMinimaIntoAEL(botY);
                processHorizontals();
                this.ghostJoins.clear();
                if (this.scanbeam != null) {
                    long topY = popScanbeam();
                    if (processIntersections(topY)) {
                        processEdgesAtTopOfScanbeam(topY);
                        botY = topY;
                        if (this.scanbeam == null && this.currentLM == null) {
                            break;
                        }
                    } else {
                        this.joins.clear();
                        this.ghostJoins.clear();
                        return false;
                    }
                } else {
                    break;
                }
            }
            int i = 0;
            while (true) {
                boolean z = true;
                if (i >= this.polyOuts.size()) {
                    break;
                }
                Path.OutRec outRec = this.polyOuts.get(i);
                if (outRec.pts != null) {
                    if (!outRec.isOpen) {
                        boolean z2 = outRec.isHole ^ this.reverseSolution;
                        if (outRec.area() <= 0.0d) {
                            z = false;
                        }
                        if (z2 == z) {
                            outRec.getPoints().reversePolyPtLinks();
                        }
                    }
                }
                i++;
            }
            joinCommonEdges();
            for (int i2 = 0; i2 < this.polyOuts.size(); i2++) {
                Path.OutRec outRec2 = this.polyOuts.get(i2);
                if (outRec2.getPoints() != null) {
                    if (outRec2.isOpen) {
                        fixupOutPolyline(outRec2);
                    } else {
                        fixupOutPolygon(outRec2);
                    }
                }
            }
            if (this.strictlySimple != 0) {
                doSimplePolygons();
            }
            this.joins.clear();
            this.ghostJoins.clear();
            return true;
        } finally {
            this.joins.clear();
            this.ghostJoins.clear();
        }
    }

    private void fixupFirstLefts1(Path.OutRec OldOutRec, Path.OutRec NewOutRec) {
        for (int i = 0; i < this.polyOuts.size(); i++) {
            Path.OutRec outRec = this.polyOuts.get(i);
            if (outRec.getPoints() != null && outRec.firstLeft != null && parseFirstLeft(outRec.firstLeft).equals(OldOutRec) && poly2ContainsPoly1(outRec.getPoints(), NewOutRec.getPoints())) {
                outRec.firstLeft = NewOutRec;
            }
        }
    }

    private void fixupFirstLefts2(Path.OutRec OldOutRec, Path.OutRec NewOutRec) {
        for (Path.OutRec outRec : this.polyOuts) {
            if (outRec.firstLeft == OldOutRec) {
                outRec.firstLeft = NewOutRec;
            }
        }
    }

    private boolean fixupIntersectionOrder() {
        Collections.sort(this.intersectList, this.intersectNodeComparer);
        copyAELToSEL();
        int cnt = this.intersectList.size();
        for (int i = 0; i < cnt; i++) {
            if (!EdgesAdjacent(this.intersectList.get(i))) {
                int j = i + 1;
                while (j < cnt && !EdgesAdjacent(this.intersectList.get(j))) {
                    j++;
                }
                if (j == cnt) {
                    return false;
                }
                List<IntersectNode> list = this.intersectList;
                list.set(i, list.get(j));
                this.intersectList.set(j, this.intersectList.get(i));
            }
            swapPositionsInSEL(this.intersectList.get(i).edge1, this.intersectList.get(i).Edge2);
        }
        return true;
    }

    private void fixupOutPolyline(Path.OutRec outrec) {
        Path.OutPt pp = outrec.pts;
        Path.OutPt lastPP = pp.prev;
        while (pp != lastPP) {
            pp = pp.next;
            if (pp.f1490pt.equals(pp.prev.f1490pt)) {
                if (pp == lastPP) {
                    lastPP = pp.prev;
                }
                Path.OutPt tmpPP = pp.prev;
                tmpPP.next = pp.next;
                pp.next.prev = tmpPP;
                pp = tmpPP;
            }
        }
        if (pp == pp.prev) {
            outrec.pts = null;
        }
    }

    private void fixupOutPolygon(Path.OutRec outRec) {
        Path.OutPt lastOK = null;
        outRec.bottomPt = null;
        Path.OutPt pp = outRec.getPoints();
        boolean preserveCol = this.preserveCollinear || this.strictlySimple;
        while (pp.prev != pp && pp.prev != pp.next) {
            if (pp.getPt().equals(pp.next.getPt()) || pp.getPt().equals(pp.prev.getPt()) || (Point.slopesEqual(pp.prev.getPt(), pp.getPt(), pp.next.getPt(), this.useFullRange) && (!preserveCol || !Point.isPt2BetweenPt1AndPt3(pp.prev.getPt(), pp.getPt(), pp.next.getPt())))) {
                lastOK = null;
                pp.prev.next = pp.next;
                pp.next.prev = pp.prev;
                pp = pp.prev;
            } else if (pp == lastOK) {
                outRec.setPoints(pp);
                return;
            } else {
                if (lastOK == null) {
                    lastOK = pp;
                }
                pp = pp.next;
            }
        }
        outRec.setPoints((Path.OutPt) null);
    }

    private Path.OutRec getOutRec(int idx) {
        Path.OutRec outrec = this.polyOuts.get(idx);
        while (true) {
            Path.OutRec outrec2 = outrec;
            if (outrec2 == this.polyOuts.get(outrec2.Idx)) {
                return outrec2;
            }
            outrec = this.polyOuts.get(outrec2.Idx);
        }
    }

    private void insertEdgeIntoAEL(Edge edge, Edge startEdge) {
        Logger logger = LOGGER;
        logger.entering(DefaultClipper.class.getName(), "insertEdgeIntoAEL");
        Edge edge2 = this.activeEdges;
        if (edge2 == null) {
            edge.prevInAEL = null;
            edge.nextInAEL = null;
            logger.finest("Edge " + edge.outIdx + " -> " + null);
            this.activeEdges = edge;
        } else if (startEdge != null || !Edge.doesE2InsertBeforeE1(edge2, edge)) {
            logger.finest("activeEdges unchanged");
            if (startEdge == null) {
                startEdge = this.activeEdges;
            }
            while (startEdge.nextInAEL != null && !Edge.doesE2InsertBeforeE1(startEdge.nextInAEL, edge)) {
                startEdge = startEdge.nextInAEL;
            }
            edge.nextInAEL = startEdge.nextInAEL;
            if (startEdge.nextInAEL != null) {
                startEdge.nextInAEL.prevInAEL = edge;
            }
            edge.prevInAEL = startEdge;
            startEdge.nextInAEL = edge;
        } else {
            edge.prevInAEL = null;
            edge.nextInAEL = this.activeEdges;
            logger.finest("Edge " + edge.outIdx + " -> " + edge.nextInAEL.outIdx);
            this.activeEdges.prevInAEL = edge;
            this.activeEdges = edge;
        }
    }

    private void insertLocalMinimaIntoAEL(long botY) {
        Path.OutPt Op1;
        LOGGER.entering(DefaultClipper.class.getName(), "insertLocalMinimaIntoAEL");
        while (this.currentLM != null && this.currentLM.f1481y == botY) {
            Edge lb = this.currentLM.leftBound;
            Edge rb = this.currentLM.rightBound;
            popLocalMinima();
            Path.OutPt Op12 = null;
            if (lb == null) {
                insertEdgeIntoAEL(rb, (Edge) null);
                updateWindingCount(rb);
                if (rb.isContributing(this.clipFillType, this.subjFillType, this.clipType)) {
                    Op1 = addOutPt(rb, rb.getBot());
                } else {
                    Op1 = null;
                }
            } else if (rb == null) {
                insertEdgeIntoAEL(lb, (Edge) null);
                updateWindingCount(lb);
                if (lb.isContributing(this.clipFillType, this.subjFillType, this.clipType)) {
                    Op12 = addOutPt(lb, lb.getBot());
                }
                insertScanbeam(lb.getTop().getY());
                Op1 = Op12;
            } else {
                insertEdgeIntoAEL(lb, (Edge) null);
                insertEdgeIntoAEL(rb, lb);
                updateWindingCount(lb);
                rb.windCnt = lb.windCnt;
                rb.windCnt2 = lb.windCnt2;
                if (lb.isContributing(this.clipFillType, this.subjFillType, this.clipType)) {
                    Op12 = addLocalMinPoly(lb, rb, lb.getBot());
                }
                insertScanbeam(lb.getTop().getY());
                Op1 = Op12;
            }
            if (rb != null) {
                if (rb.isHorizontal()) {
                    addEdgeToSEL(rb);
                } else {
                    insertScanbeam(rb.getTop().getY());
                }
            }
            if (!(lb == null || rb == null)) {
                if (Op1 != null && rb.isHorizontal() && this.ghostJoins.size() > 0 && rb.windDelta != 0) {
                    for (int i = 0; i < this.ghostJoins.size(); i++) {
                        Path.Join j = this.ghostJoins.get(i);
                        if (doHorzSegmentsOverlap(j.outPt1.getPt().getX(), j.getOffPt().getX(), rb.getBot().getX(), rb.getTop().getX())) {
                            addJoin(j.outPt1, Op1, j.getOffPt());
                        }
                    }
                }
                if (lb.outIdx >= 0 && lb.prevInAEL != null && lb.prevInAEL.getCurrent().getX() == lb.getBot().getX() && lb.prevInAEL.outIdx >= 0 && Edge.slopesEqual(lb.prevInAEL, lb, this.useFullRange) && lb.windDelta != 0 && lb.prevInAEL.windDelta != 0) {
                    addJoin(Op1, addOutPt(lb.prevInAEL, lb.getBot()), lb.getTop());
                }
                if (lb.nextInAEL != rb) {
                    if (rb.outIdx >= 0 && rb.prevInAEL.outIdx >= 0 && Edge.slopesEqual(rb.prevInAEL, rb, this.useFullRange) && rb.windDelta != 0 && rb.prevInAEL.windDelta != 0) {
                        addJoin(Op1, addOutPt(rb.prevInAEL, rb.getBot()), rb.getTop());
                    }
                    Edge e = lb.nextInAEL;
                    if (e != null) {
                        while (e != rb) {
                            intersectEdges(rb, e, lb.getCurrent());
                            e = e.nextInAEL;
                        }
                    }
                }
            }
        }
    }

    private void intersectEdges(Edge e1, Edge e2, Point.LongPoint pt) {
        IClipper.PolyFillType e1FillType2;
        IClipper.PolyFillType e1FillType;
        IClipper.PolyFillType e2FillType2;
        IClipper.PolyFillType e2FillType;
        int e1Wc;
        int e2Wc;
        int e1Wc2;
        int e2Wc2;
        Edge edge = e1;
        Edge edge2 = e2;
        Point.LongPoint longPoint = pt;
        LOGGER.entering(DefaultClipper.class.getName(), "insersectEdges");
        int i = 0;
        boolean e1Contributing = edge.outIdx >= 0;
        boolean e2Contributing = edge2.outIdx >= 0;
        setZ(longPoint, edge, edge2);
        if (edge.windDelta != 0 && edge2.windDelta != 0) {
            if (edge.polyTyp != edge2.polyTyp) {
                if (!edge2.isEvenOddFillType(this.clipFillType, this.subjFillType)) {
                    edge.windCnt2 += edge2.windDelta;
                } else {
                    edge.windCnt2 = edge.windCnt2 == 0 ? 1 : 0;
                }
                if (!edge.isEvenOddFillType(this.clipFillType, this.subjFillType)) {
                    edge2.windCnt2 -= edge.windDelta;
                } else {
                    if (edge2.windCnt2 == 0) {
                        i = 1;
                    }
                    edge2.windCnt2 = i;
                }
            } else if (edge.isEvenOddFillType(this.clipFillType, this.subjFillType)) {
                int oldE1WindCnt = edge.windCnt;
                edge.windCnt = edge2.windCnt;
                edge2.windCnt = oldE1WindCnt;
            } else {
                if (edge.windCnt + edge2.windDelta == 0) {
                    edge.windCnt = -edge.windCnt;
                } else {
                    edge.windCnt += edge2.windDelta;
                }
                if (edge2.windCnt - edge.windDelta == 0) {
                    edge2.windCnt = -edge2.windCnt;
                } else {
                    edge2.windCnt -= edge.windDelta;
                }
            }
            if (edge.polyTyp == IClipper.PolyType.SUBJECT) {
                e1FillType = this.subjFillType;
                e1FillType2 = this.clipFillType;
            } else {
                e1FillType = this.clipFillType;
                e1FillType2 = this.subjFillType;
            }
            if (edge2.polyTyp == IClipper.PolyType.SUBJECT) {
                e2FillType = this.subjFillType;
                e2FillType2 = this.clipFillType;
            } else {
                e2FillType = this.clipFillType;
                e2FillType2 = this.subjFillType;
            }
            switch (C14342.f1485x682d75a5[e1FillType.ordinal()]) {
                case 1:
                    e1Wc = edge.windCnt;
                    break;
                case 2:
                    e1Wc = -edge.windCnt;
                    break;
                default:
                    e1Wc = Math.abs(edge.windCnt);
                    break;
            }
            switch (C14342.f1485x682d75a5[e2FillType.ordinal()]) {
                case 1:
                    e2Wc = edge2.windCnt;
                    break;
                case 2:
                    e2Wc = -edge2.windCnt;
                    break;
                default:
                    e2Wc = Math.abs(edge2.windCnt);
                    break;
            }
            if (!e1Contributing || !e2Contributing) {
                if (e1Contributing) {
                    if (e2Wc == 0 || e2Wc == 1) {
                        addOutPt(edge, longPoint);
                        Edge.swapSides(e1, e2);
                        Edge.swapPolyIndexes(e1, e2);
                    }
                } else if (e2Contributing) {
                    if (e1Wc == 0 || e1Wc == 1) {
                        addOutPt(edge2, longPoint);
                        Edge.swapSides(e1, e2);
                        Edge.swapPolyIndexes(e1, e2);
                    }
                } else if (e1Wc != 0 && e1Wc != 1) {
                } else {
                    if (e2Wc == 0 || e2Wc == 1) {
                        switch (C14342.f1485x682d75a5[e1FillType2.ordinal()]) {
                            case 1:
                                e1Wc2 = edge.windCnt2;
                                break;
                            case 2:
                                e1Wc2 = -edge.windCnt2;
                                break;
                            default:
                                e1Wc2 = Math.abs(edge.windCnt2);
                                break;
                        }
                        switch (C14342.f1485x682d75a5[e2FillType2.ordinal()]) {
                            case 1:
                                e2Wc2 = edge2.windCnt2;
                                break;
                            case 2:
                                e2Wc2 = -edge2.windCnt2;
                                break;
                            default:
                                e2Wc2 = Math.abs(edge2.windCnt2);
                                break;
                        }
                        if (edge.polyTyp != edge2.polyTyp) {
                            addLocalMinPoly(e1, e2, pt);
                        } else if (e1Wc == 1 && e2Wc == 1) {
                            switch (C14342.f1484xa4df9306[this.clipType.ordinal()]) {
                                case 1:
                                    if (e1Wc2 > 0 && e2Wc2 > 0) {
                                        addLocalMinPoly(e1, e2, pt);
                                        return;
                                    }
                                    return;
                                case 2:
                                    if (e1Wc2 <= 0 && e2Wc2 <= 0) {
                                        addLocalMinPoly(e1, e2, pt);
                                        return;
                                    }
                                    return;
                                case 3:
                                    if ((edge.polyTyp == IClipper.PolyType.CLIP && e1Wc2 > 0 && e2Wc2 > 0) || (edge.polyTyp == IClipper.PolyType.SUBJECT && e1Wc2 <= 0 && e2Wc2 <= 0)) {
                                        addLocalMinPoly(e1, e2, pt);
                                        return;
                                    }
                                    return;
                                case 4:
                                    addLocalMinPoly(e1, e2, pt);
                                    return;
                                default:
                                    return;
                            }
                        } else {
                            Edge.swapSides(e1, e2);
                        }
                    }
                }
            } else if ((e1Wc == 0 || e1Wc == 1) && ((e2Wc == 0 || e2Wc == 1) && (edge.polyTyp == edge2.polyTyp || this.clipType == IClipper.ClipType.XOR))) {
                addOutPt(edge, longPoint);
                addOutPt(edge2, longPoint);
                Edge.swapSides(e1, e2);
                Edge.swapPolyIndexes(e1, e2);
            } else {
                addLocalMaxPoly(e1, e2, pt);
            }
        } else if (edge.windDelta != 0 || edge2.windDelta != 0) {
            if (edge.polyTyp == edge2.polyTyp && edge.windDelta != edge2.windDelta && this.clipType == IClipper.ClipType.UNION) {
                if (edge.windDelta == 0) {
                    if (e2Contributing) {
                        addOutPt(edge, longPoint);
                        if (e1Contributing) {
                            edge.outIdx = -1;
                        }
                    }
                } else if (e1Contributing) {
                    addOutPt(edge2, longPoint);
                    if (e2Contributing) {
                        edge2.outIdx = -1;
                    }
                }
            } else if (edge.polyTyp == edge2.polyTyp) {
            } else {
                if (edge.windDelta == 0 && Math.abs(edge2.windCnt) == 1 && (this.clipType != IClipper.ClipType.UNION || edge2.windCnt2 == 0)) {
                    addOutPt(edge, longPoint);
                    if (e1Contributing) {
                        edge.outIdx = -1;
                    }
                } else if (edge2.windDelta != 0 || Math.abs(edge.windCnt) != 1) {
                } else {
                    if (this.clipType != IClipper.ClipType.UNION || edge.windCnt2 == 0) {
                        addOutPt(edge2, longPoint);
                        if (e2Contributing) {
                            edge2.outIdx = -1;
                        }
                    }
                }
            }
        }
    }

    /* renamed from: com.itextpdf.kernel.pdf.canvas.parser.clipper.DefaultClipper$2 */
    static /* synthetic */ class C14342 {

        /* renamed from: $SwitchMap$com$itextpdf$kernel$pdf$canvas$parser$clipper$IClipper$ClipType */
        static final /* synthetic */ int[] f1484xa4df9306;

        /* renamed from: $SwitchMap$com$itextpdf$kernel$pdf$canvas$parser$clipper$IClipper$PolyFillType */
        static final /* synthetic */ int[] f1485x682d75a5;

        static {
            int[] iArr = new int[IClipper.ClipType.values().length];
            f1484xa4df9306 = iArr;
            try {
                iArr[IClipper.ClipType.INTERSECTION.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1484xa4df9306[IClipper.ClipType.UNION.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1484xa4df9306[IClipper.ClipType.DIFFERENCE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1484xa4df9306[IClipper.ClipType.XOR.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            int[] iArr2 = new int[IClipper.PolyFillType.values().length];
            f1485x682d75a5 = iArr2;
            try {
                iArr2[IClipper.PolyFillType.POSITIVE.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1485x682d75a5[IClipper.PolyFillType.NEGATIVE.ordinal()] = 2;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    private void intersectPoint(Edge edge1, Edge edge2, Point.LongPoint[] ipV) {
        Point.LongPoint ip = new Point.LongPoint();
        ipV[0] = ip;
        if (edge1.deltaX == edge2.deltaX) {
            ip.setY(Long.valueOf(edge1.getCurrent().getY()));
            ip.setX(Long.valueOf(Edge.topX(edge1, ip.getY())));
            return;
        }
        if (edge1.getDelta().getX() == 0) {
            ip.setX(Long.valueOf(edge1.getBot().getX()));
            if (edge2.isHorizontal()) {
                ip.setY(Long.valueOf(edge2.getBot().getY()));
            } else {
                double y = (double) edge2.getBot().getY();
                double x = (double) edge2.getBot().getX();
                double d = edge2.deltaX;
                Double.isNaN(x);
                Double.isNaN(y);
                double b2 = y - (x / d);
                double x2 = (double) ip.getX();
                double d2 = edge2.deltaX;
                Double.isNaN(x2);
                ip.setY(Long.valueOf(Math.round((x2 / d2) + b2)));
            }
        } else if (edge2.getDelta().getX() == 0) {
            ip.setX(Long.valueOf(edge2.getBot().getX()));
            if (edge1.isHorizontal()) {
                ip.setY(Long.valueOf(edge1.getBot().getY()));
            } else {
                double y2 = (double) edge1.getBot().getY();
                double x3 = (double) edge1.getBot().getX();
                double d3 = edge1.deltaX;
                Double.isNaN(x3);
                Double.isNaN(y2);
                double b1 = y2 - (x3 / d3);
                double x4 = (double) ip.getX();
                double d4 = edge1.deltaX;
                Double.isNaN(x4);
                ip.setY(Long.valueOf(Math.round((x4 / d4) + b1)));
            }
        } else {
            double x5 = (double) edge1.getBot().getX();
            double y3 = (double) edge1.getBot().getY();
            double d5 = edge1.deltaX;
            Double.isNaN(y3);
            Double.isNaN(x5);
            double b12 = x5 - (y3 * d5);
            double x6 = (double) edge2.getBot().getX();
            double y4 = (double) edge2.getBot().getY();
            double d6 = edge2.deltaX;
            Double.isNaN(y4);
            Double.isNaN(x6);
            double b22 = x6 - (y4 * d6);
            double q = (b22 - b12) / (edge1.deltaX - edge2.deltaX);
            ip.setY(Long.valueOf(Math.round(q)));
            if (Math.abs(edge1.deltaX) < Math.abs(edge2.deltaX)) {
                ip.setX(Long.valueOf(Math.round((edge1.deltaX * q) + b12)));
            } else {
                ip.setX(Long.valueOf(Math.round((edge2.deltaX * q) + b22)));
            }
        }
        if (ip.getY() < edge1.getTop().getY() || ip.getY() < edge2.getTop().getY()) {
            if (edge1.getTop().getY() > edge2.getTop().getY()) {
                ip.setY(Long.valueOf(edge1.getTop().getY()));
            } else {
                ip.setY(Long.valueOf(edge2.getTop().getY()));
            }
            if (Math.abs(edge1.deltaX) < Math.abs(edge2.deltaX)) {
                ip.setX(Long.valueOf(Edge.topX(edge1, ip.getY())));
            } else {
                ip.setX(Long.valueOf(Edge.topX(edge2, ip.getY())));
            }
        }
        if (ip.getY() > edge1.getCurrent().getY()) {
            ip.setY(Long.valueOf(edge1.getCurrent().getY()));
            if (Math.abs(edge1.deltaX) > Math.abs(edge2.deltaX)) {
                ip.setX(Long.valueOf(Edge.topX(edge2, ip.getY())));
            } else {
                ip.setX(Long.valueOf(Edge.topX(edge1, ip.getY())));
            }
        }
    }

    private void joinCommonEdges() {
        Path.OutRec holeStateRec;
        for (int i = 0; i < this.joins.size(); i++) {
            Path.Join join = this.joins.get(i);
            Path.OutRec outRec1 = getOutRec(join.outPt1.idx);
            Path.OutRec outRec2 = getOutRec(join.outPt2.idx);
            if (outRec1.getPoints() != null && outRec2.getPoints() != null && !outRec1.isOpen && !outRec2.isOpen) {
                if (outRec1 == outRec2) {
                    holeStateRec = outRec1;
                } else if (isParam1RightOfParam2(outRec1, outRec2)) {
                    holeStateRec = outRec2;
                } else if (isParam1RightOfParam2(outRec2, outRec1)) {
                    holeStateRec = outRec1;
                } else {
                    holeStateRec = Path.OutPt.getLowerMostRec(outRec1, outRec2);
                }
                if (joinPoints(join, outRec1, outRec2)) {
                    if (outRec1 == outRec2) {
                        outRec1.setPoints(join.outPt1);
                        outRec1.bottomPt = null;
                        Path.OutRec outRec22 = createOutRec();
                        outRec22.setPoints(join.outPt2);
                        updateOutPtIdxs(outRec22);
                        boolean z = true;
                        if (this.usingPolyTree) {
                            for (int j = 0; j < this.polyOuts.size() - 1; j++) {
                                Path.OutRec oRec = this.polyOuts.get(j);
                                if (oRec.getPoints() != null && parseFirstLeft(oRec.firstLeft) == outRec1 && oRec.isHole != outRec1.isHole && poly2ContainsPoly1(oRec.getPoints(), join.outPt2)) {
                                    oRec.firstLeft = outRec22;
                                }
                            }
                        }
                        if (poly2ContainsPoly1(outRec22.getPoints(), outRec1.getPoints())) {
                            outRec22.isHole = !outRec1.isHole;
                            outRec22.firstLeft = outRec1;
                            if (this.usingPolyTree) {
                                fixupFirstLefts2(outRec22, outRec1);
                            }
                            boolean z2 = outRec22.isHole ^ this.reverseSolution;
                            if (outRec22.area() <= 0.0d) {
                                z = false;
                            }
                            if (z2 == z) {
                                outRec22.getPoints().reversePolyPtLinks();
                            }
                        } else if (poly2ContainsPoly1(outRec1.getPoints(), outRec22.getPoints())) {
                            outRec22.isHole = outRec1.isHole;
                            outRec1.isHole = !outRec22.isHole;
                            outRec22.firstLeft = outRec1.firstLeft;
                            outRec1.firstLeft = outRec22;
                            if (this.usingPolyTree) {
                                fixupFirstLefts2(outRec1, outRec22);
                            }
                            boolean z3 = outRec1.isHole ^ this.reverseSolution;
                            if (outRec1.area() <= 0.0d) {
                                z = false;
                            }
                            if (z3 == z) {
                                outRec1.getPoints().reversePolyPtLinks();
                            }
                        } else {
                            outRec22.isHole = outRec1.isHole;
                            outRec22.firstLeft = outRec1.firstLeft;
                            if (this.usingPolyTree) {
                                fixupFirstLefts1(outRec1, outRec22);
                            }
                        }
                    } else {
                        outRec2.setPoints((Path.OutPt) null);
                        outRec2.bottomPt = null;
                        outRec2.Idx = outRec1.Idx;
                        outRec1.isHole = holeStateRec.isHole;
                        if (holeStateRec == outRec2) {
                            outRec1.firstLeft = outRec2.firstLeft;
                        }
                        outRec2.firstLeft = outRec1;
                        if (this.usingPolyTree) {
                            fixupFirstLefts2(outRec2, outRec1);
                        }
                    }
                }
            }
        }
    }

    private long popScanbeam() {
        LOGGER.entering(DefaultClipper.class.getName(), "popBeam");
        long y = this.scanbeam.f1482y;
        this.scanbeam = this.scanbeam.next;
        return y;
    }

    private void processEdgesAtTopOfScanbeam(long topY) {
        long j = topY;
        Class<DefaultClipper> cls = DefaultClipper.class;
        LOGGER.entering(cls.getName(), "processEdgesAtTopOfScanbeam");
        Edge e = this.activeEdges;
        while (e != null) {
            boolean IsMaximaEdge = e.isMaxima((double) j);
            if (IsMaximaEdge) {
                Edge eMaxPair = e.getMaximaPair();
                IsMaximaEdge = eMaxPair == null || !eMaxPair.isHorizontal();
            }
            if (IsMaximaEdge) {
                if (this.strictlySimple) {
                    InsertMaxima(e.getTop().getX());
                }
                Edge ePrev = e.prevInAEL;
                doMaxima(e);
                if (ePrev == null) {
                    e = this.activeEdges;
                } else {
                    e = ePrev.nextInAEL;
                }
            } else {
                if (!e.isIntermediate((double) j) || !e.nextInLML.isHorizontal()) {
                    e.getCurrent().setX(Long.valueOf(Edge.topX(e, j)));
                    e.getCurrent().setY(Long.valueOf(topY));
                } else {
                    Edge[] t = {e};
                    updateEdgeIntoAEL(t);
                    e = t[0];
                    if (e.outIdx >= 0) {
                        addOutPt(e, e.getBot());
                    }
                    addEdgeToSEL(e);
                }
                if (this.strictlySimple) {
                    Edge ePrev2 = e.prevInAEL;
                    if (e.outIdx >= 0 && e.windDelta != 0 && ePrev2 != null && ePrev2.outIdx >= 0 && ePrev2.getCurrent().getX() == e.getCurrent().getX() && ePrev2.windDelta != 0) {
                        Point.LongPoint ip = new Point.LongPoint(e.getCurrent());
                        setZ(ip, ePrev2, e);
                        addJoin(addOutPt(ePrev2, ip), addOutPt(e, ip), ip);
                    }
                }
                e = e.nextInAEL;
            }
        }
        processHorizontals();
        this.maxima = null;
        Edge e2 = this.activeEdges;
        while (e2 != null) {
            if (e2.isIntermediate((double) j)) {
                Path.OutPt op = null;
                if (e2.outIdx >= 0) {
                    op = addOutPt(e2, e2.getTop());
                }
                Edge[] t2 = {e2};
                updateEdgeIntoAEL(t2);
                e2 = t2[0];
                Edge ePrev3 = e2.prevInAEL;
                Edge eNext = e2.nextInAEL;
                if (ePrev3 != null && ePrev3.getCurrent().equals(e2.getBot()) && op != null && ePrev3.outIdx >= 0 && ePrev3.getCurrent().getY() > ePrev3.getTop().getY() && Edge.slopesEqual(e2, ePrev3, this.useFullRange) && e2.windDelta != 0 && ePrev3.windDelta != 0) {
                    addJoin(op, addOutPt(ePrev3, e2.getBot()), e2.getTop());
                } else if (eNext != null && eNext.getCurrent().equals(e2.getBot()) && op != null && eNext.outIdx >= 0 && eNext.getCurrent().getY() > eNext.getTop().getY() && Edge.slopesEqual(e2, eNext, this.useFullRange) && e2.windDelta != 0 && eNext.windDelta != 0) {
                    addJoin(op, addOutPt(eNext, e2.getBot()), e2.getTop());
                }
            }
            e2 = e2.nextInAEL;
        }
        LOGGER.exiting(cls.getName(), "processEdgesAtTopOfScanbeam");
    }

    private void processHorizontal(Edge horzEdge) {
        Edge eMaxPair;
        Edge eLastHorz;
        IClipper.Direction[] dir;
        boolean IsOpen;
        Edge horzEdge2;
        Edge eMaxPair2;
        Path.Maxima currMax;
        IClipper.Direction[] dir2;
        long[] horzLeft;
        IClipper.Direction[] dir3;
        Edge eNextHorz;
        Path.Maxima currMax2;
        Edge horzEdge3;
        boolean IsOpen2;
        Edge eLastHorz2;
        Path.Maxima currMax3;
        Edge eMaxPair3;
        Edge e;
        Edge horzEdge4;
        boolean IsOpen3;
        Edge eLastHorz3;
        Path.Maxima currMax4;
        Edge eMaxPair4;
        Edge eNextHorz2;
        Edge e2;
        IClipper.Direction[] dir4;
        Edge edge = horzEdge;
        LOGGER.entering(DefaultClipper.class.getName(), "isHorizontal");
        IClipper.Direction[] dir5 = new IClipper.Direction[1];
        long[] horzLeft2 = new long[1];
        long[] horzRight = new long[1];
        char c = 0;
        boolean IsOpen4 = edge.outIdx >= 0 && this.polyOuts.get(edge.outIdx).isOpen;
        getHorzDirection(edge, dir5, horzLeft2, horzRight);
        Edge eLastHorz4 = horzEdge;
        while (eLastHorz4.nextInLML != null && eLastHorz4.nextInLML.isHorizontal()) {
            eLastHorz4 = eLastHorz4.nextInLML;
        }
        if (eLastHorz4.nextInLML == null) {
            eMaxPair = eLastHorz4.getMaximaPair();
        } else {
            eMaxPair = null;
        }
        Path.Maxima currMax5 = this.maxima;
        if (currMax5 != null) {
            if (dir5[0] == IClipper.Direction.LEFT_TO_RIGHT) {
                while (currMax5 != null && currMax5.f1489X <= horzEdge.getBot().getX()) {
                    currMax5 = currMax5.Next;
                }
                if (currMax5 != null && currMax5.f1489X >= eLastHorz4.getBot().getX()) {
                    currMax5 = null;
                }
            } else {
                while (currMax5.Next != null && currMax5.Next.f1489X < horzEdge.getBot().getX()) {
                    currMax5 = currMax5.Next;
                }
                if (currMax5.f1489X <= eLastHorz4.getTop().getX()) {
                    currMax5 = null;
                }
            }
        }
        Path.OutPt op1 = null;
        Edge horzEdge5 = edge;
        while (true) {
            boolean IsLastHorz = horzEdge5 == eLastHorz4;
            Edge e3 = horzEdge5.getNextInAEL(dir5[c]);
            while (true) {
                if (e3 == null) {
                    Edge edge2 = e3;
                    eLastHorz = eLastHorz4;
                    dir = dir5;
                    IsOpen = IsOpen4;
                    horzEdge2 = horzEdge5;
                    eMaxPair2 = eMaxPair;
                    currMax = currMax5;
                    break;
                }
                if (currMax5 != null) {
                    if (dir5[c] == IClipper.Direction.LEFT_TO_RIGHT) {
                        while (currMax5 != null && currMax5.f1489X < e3.getCurrent().getX()) {
                            if (horzEdge5.outIdx < 0 || IsOpen4) {
                                dir4 = dir5;
                            } else {
                                dir4 = dir5;
                                addOutPt(horzEdge5, new Point.LongPoint(currMax5.f1489X, horzEdge5.getBot().getY()));
                            }
                            currMax5 = currMax5.Next;
                            dir5 = dir4;
                        }
                        dir = dir5;
                    } else {
                        dir = dir5;
                        while (currMax5 != null && currMax5.f1489X > e3.getCurrent().getX()) {
                            if (horzEdge5.outIdx >= 0 && !IsOpen4) {
                                addOutPt(horzEdge5, new Point.LongPoint(currMax5.f1489X, horzEdge5.getBot().getY()));
                            }
                            currMax5 = currMax5.Prev;
                        }
                    }
                    currMax2 = currMax5;
                } else {
                    dir = dir5;
                    currMax2 = currMax5;
                }
                if ((dir[c] != IClipper.Direction.LEFT_TO_RIGHT || e3.getCurrent().getX() <= horzRight[c]) && ((dir[c] != IClipper.Direction.RIGHT_TO_LEFT || e3.getCurrent().getX() >= horzLeft2[c]) && (e3.getCurrent().getX() != horzEdge5.getTop().getX() || horzEdge5.nextInLML == null || e3.deltaX >= horzEdge5.nextInLML.deltaX))) {
                    if (horzEdge5.outIdx < 0 || IsOpen4) {
                        horzEdge3 = horzEdge5;
                        eLastHorz2 = eLastHorz4;
                        currMax3 = currMax2;
                        IsOpen2 = IsOpen4;
                        e = e3;
                        eMaxPair3 = eMaxPair;
                    } else {
                        Path.OutPt op12 = addOutPt(horzEdge5, e3.getCurrent());
                        Edge eNextHorz3 = this.sortedEdges;
                        while (eNextHorz3 != null) {
                            if (eNextHorz3.outIdx >= 0) {
                                long x = horzEdge5.getBot().getX();
                                long x2 = horzEdge5.getTop().getX();
                                long x3 = eNextHorz3.getBot().getX();
                                long x4 = eNextHorz3.getTop().getX();
                                eNextHorz2 = eNextHorz3;
                                long j = x2;
                                horzEdge4 = horzEdge5;
                                currMax4 = currMax2;
                                e2 = e3;
                                long j2 = x3;
                                eLastHorz3 = eLastHorz4;
                                IsOpen3 = IsOpen4;
                                eMaxPair4 = eMaxPair;
                                if (doHorzSegmentsOverlap(x, j, j2, x4)) {
                                    addJoin(GetLastOutPt(eNextHorz2), op12, eNextHorz2.getTop());
                                }
                            } else {
                                eNextHorz2 = eNextHorz3;
                                horzEdge4 = horzEdge5;
                                eLastHorz3 = eLastHorz4;
                                currMax4 = currMax2;
                                IsOpen3 = IsOpen4;
                                e2 = e3;
                                eMaxPair4 = eMaxPair;
                            }
                            eNextHorz3 = eNextHorz2.nextInSEL;
                            horzEdge5 = horzEdge4;
                            e3 = e2;
                            eMaxPair = eMaxPair4;
                            currMax2 = currMax4;
                            eLastHorz4 = eLastHorz3;
                            IsOpen4 = IsOpen3;
                        }
                        Edge edge3 = eNextHorz3;
                        horzEdge3 = horzEdge5;
                        eLastHorz2 = eLastHorz4;
                        currMax3 = currMax2;
                        IsOpen2 = IsOpen4;
                        e = e3;
                        eMaxPair3 = eMaxPair;
                        addGhostJoin(op12, horzEdge3.getBot());
                        op1 = op12;
                    }
                    if (e != eMaxPair3 || !IsLastHorz) {
                        Edge horzEdge6 = horzEdge3;
                        if (dir[0] == IClipper.Direction.LEFT_TO_RIGHT) {
                            intersectEdges(horzEdge6, e, new Point.LongPoint(e.getCurrent().getX(), horzEdge6.getCurrent().getY()));
                        } else {
                            intersectEdges(e, horzEdge6, new Point.LongPoint(e.getCurrent().getX(), horzEdge6.getCurrent().getY()));
                        }
                        Edge eNext = e.getNextInAEL(dir[0]);
                        swapPositionsInAEL(horzEdge6, e);
                        e3 = eNext;
                        horzEdge5 = horzEdge6;
                        eMaxPair = eMaxPair3;
                        dir5 = dir;
                        currMax5 = currMax3;
                        eLastHorz4 = eLastHorz2;
                        IsOpen4 = IsOpen2;
                        c = 0;
                    } else {
                        Edge horzEdge7 = horzEdge3;
                        if (horzEdge7.outIdx >= 0) {
                            addLocalMaxPoly(horzEdge7, eMaxPair3, horzEdge7.getTop());
                        }
                        deleteFromAEL(horzEdge7);
                        deleteFromAEL(eMaxPair3);
                        return;
                    }
                }
            }
            horzEdge2 = horzEdge5;
            eLastHorz = eLastHorz4;
            currMax = currMax2;
            IsOpen = IsOpen4;
            Edge edge4 = e3;
            eMaxPair2 = eMaxPair;
            if (horzEdge2.nextInLML == null) {
                dir2 = dir;
                break;
            } else if (!horzEdge2.nextInLML.isHorizontal()) {
                dir2 = dir;
                break;
            } else {
                Edge[] temp = {horzEdge2};
                updateEdgeIntoAEL(temp);
                horzEdge5 = temp[0];
                if (horzEdge5.outIdx >= 0) {
                    addOutPt(horzEdge5, horzEdge5.getBot());
                }
                IClipper.Direction[] dir6 = dir;
                getHorzDirection(horzEdge5, dir6, horzLeft2, horzRight);
                dir5 = dir6;
                currMax5 = currMax;
                eMaxPair = eMaxPair2;
                eLastHorz4 = eLastHorz;
                IsOpen4 = IsOpen;
                c = 0;
            }
        }
        if (horzEdge2.outIdx < 0 || op1 != null) {
            long[] jArr = horzLeft2;
        } else {
            Path.OutPt op13 = GetLastOutPt(horzEdge2);
            Edge eNextHorz4 = this.sortedEdges;
            while (eNextHorz4 != null) {
                if (eNextHorz4.outIdx >= 0) {
                    dir3 = dir2;
                    horzLeft = horzLeft2;
                    eNextHorz = eNextHorz4;
                    if (doHorzSegmentsOverlap(horzEdge2.getBot().getX(), horzEdge2.getTop().getX(), eNextHorz4.getBot().getX(), eNextHorz4.getTop().getX())) {
                        addJoin(GetLastOutPt(eNextHorz), op13, eNextHorz.getTop());
                    }
                } else {
                    dir3 = dir2;
                    horzLeft = horzLeft2;
                    eNextHorz = eNextHorz4;
                }
                eNextHorz4 = eNextHorz.nextInSEL;
                dir2 = dir3;
                horzLeft2 = horzLeft;
            }
            long[] jArr2 = horzLeft2;
            Edge edge5 = eNextHorz4;
            addGhostJoin(op13, horzEdge2.getTop());
            Path.OutPt outPt = op13;
        }
        if (horzEdge2.nextInLML == null) {
            if (horzEdge2.outIdx >= 0) {
                addOutPt(horzEdge2, horzEdge2.getTop());
            }
            deleteFromAEL(horzEdge2);
            Edge edge6 = horzEdge2;
        } else if (horzEdge2.outIdx >= 0) {
            Path.OutPt op14 = addOutPt(horzEdge2, horzEdge2.getTop());
            Edge[] t = {horzEdge2};
            updateEdgeIntoAEL(t);
            Edge horzEdge8 = t[0];
            if (horzEdge8.windDelta != 0) {
                Edge ePrev = horzEdge8.prevInAEL;
                Edge eNext2 = horzEdge8.nextInAEL;
                if (ePrev != null && ePrev.getCurrent().equals(horzEdge8.getBot()) && ePrev.windDelta != 0 && ePrev.outIdx >= 0 && ePrev.getCurrent().getY() > ePrev.getTop().getY() && Edge.slopesEqual(horzEdge8, ePrev, this.useFullRange)) {
                    addJoin(op14, addOutPt(ePrev, horzEdge8.getBot()), horzEdge8.getTop());
                } else if (eNext2 != null && eNext2.getCurrent().equals(horzEdge8.getBot()) && eNext2.windDelta != 0 && eNext2.outIdx >= 0 && eNext2.getCurrent().getY() > eNext2.getTop().getY() && Edge.slopesEqual(horzEdge8, eNext2, this.useFullRange)) {
                    addJoin(op14, addOutPt(eNext2, horzEdge8.getBot()), horzEdge8.getTop());
                }
            }
        } else {
            Edge[] t2 = {horzEdge2};
            updateEdgeIntoAEL(t2);
            Edge edge7 = t2[0];
        }
    }

    private void processHorizontals() {
        LOGGER.entering(DefaultClipper.class.getName(), "processHorizontals");
        Edge horzEdge = this.sortedEdges;
        while (horzEdge != null) {
            deleteFromSEL(horzEdge);
            processHorizontal(horzEdge);
            horzEdge = this.sortedEdges;
        }
    }

    private boolean processIntersections(long topY) {
        LOGGER.entering(DefaultClipper.class.getName(), "processIntersections");
        if (this.activeEdges == null) {
            return true;
        }
        try {
            buildIntersectList(topY);
            if (this.intersectList.size() == 0) {
                return true;
            }
            if (this.intersectList.size() != 1) {
                if (!fixupIntersectionOrder()) {
                    return false;
                }
            }
            processIntersectList();
            this.sortedEdges = null;
            return true;
        } catch (Exception e) {
            this.sortedEdges = null;
            this.intersectList.clear();
            throw new IllegalStateException("ProcessIntersections error", e);
        }
    }

    private void processIntersectList() {
        for (int i = 0; i < this.intersectList.size(); i++) {
            IntersectNode iNode = this.intersectList.get(i);
            intersectEdges(iNode.edge1, iNode.Edge2, iNode.getPt());
            swapPositionsInAEL(iNode.edge1, iNode.Edge2);
        }
        this.intersectList.clear();
    }

    /* access modifiers changed from: protected */
    public void reset() {
        super.reset();
        this.scanbeam = null;
        this.maxima = null;
        this.activeEdges = null;
        this.sortedEdges = null;
        for (ClipperBase.LocalMinima lm = this.minimaList; lm != null; lm = lm.next) {
            insertScanbeam(lm.f1481y);
        }
    }

    private void setHoleState(Edge e, Path.OutRec outRec) {
        boolean isHole = false;
        Edge e2 = e.prevInAEL;
        while (true) {
            boolean z = true;
            if (e2 == null) {
                break;
            }
            if (e2.outIdx >= 0 && e2.windDelta != 0) {
                if (isHole) {
                    z = false;
                }
                isHole = z;
                if (outRec.firstLeft == null) {
                    outRec.firstLeft = this.polyOuts.get(e2.outIdx);
                }
            }
            e2 = e2.prevInAEL;
        }
        if (isHole) {
            outRec.isHole = true;
        }
    }

    private void setZ(Point.LongPoint pt, Edge e1, Edge e2) {
        if (pt.getZ() == 0 && this.zFillFunction != null) {
            if (pt.equals(e1.getBot())) {
                pt.setZ(Long.valueOf(e1.getBot().getZ()));
            } else if (pt.equals(e1.getTop())) {
                pt.setZ(Long.valueOf(e1.getTop().getZ()));
            } else if (pt.equals(e2.getBot())) {
                pt.setZ(Long.valueOf(e2.getBot().getZ()));
            } else if (pt.equals(e2.getTop())) {
                pt.setZ(Long.valueOf(e2.getTop().getZ()));
            } else {
                this.zFillFunction.zFill(e1.getBot(), e1.getTop(), e2.getBot(), e2.getTop(), pt);
            }
        }
    }

    private void swapPositionsInAEL(Edge edge1, Edge edge2) {
        Logger logger = LOGGER;
        Class<DefaultClipper> cls = DefaultClipper.class;
        logger.entering(cls.getName(), "swapPositionsInAEL");
        if (edge1.nextInAEL != edge1.prevInAEL && edge2.nextInAEL != edge2.prevInAEL) {
            if (edge1.nextInAEL == edge2) {
                Edge next = edge2.nextInAEL;
                if (next != null) {
                    next.prevInAEL = edge1;
                }
                Edge prev = edge1.prevInAEL;
                if (prev != null) {
                    prev.nextInAEL = edge2;
                }
                edge2.prevInAEL = prev;
                edge2.nextInAEL = edge1;
                edge1.prevInAEL = edge2;
                edge1.nextInAEL = next;
            } else if (edge2.nextInAEL == edge1) {
                Edge next2 = edge1.nextInAEL;
                if (next2 != null) {
                    next2.prevInAEL = edge2;
                }
                Edge prev2 = edge2.prevInAEL;
                if (prev2 != null) {
                    prev2.nextInAEL = edge1;
                }
                edge1.prevInAEL = prev2;
                edge1.nextInAEL = edge2;
                edge2.prevInAEL = edge1;
                edge2.nextInAEL = next2;
            } else {
                Edge next3 = edge1.nextInAEL;
                Edge prev3 = edge1.prevInAEL;
                edge1.nextInAEL = edge2.nextInAEL;
                if (edge1.nextInAEL != null) {
                    edge1.nextInAEL.prevInAEL = edge1;
                }
                edge1.prevInAEL = edge2.prevInAEL;
                if (edge1.prevInAEL != null) {
                    edge1.prevInAEL.nextInAEL = edge1;
                }
                edge2.nextInAEL = next3;
                if (edge2.nextInAEL != null) {
                    edge2.nextInAEL.prevInAEL = edge2;
                }
                edge2.prevInAEL = prev3;
                if (edge2.prevInAEL != null) {
                    edge2.prevInAEL.nextInAEL = edge2;
                }
            }
            if (edge1.prevInAEL == null) {
                this.activeEdges = edge1;
            } else if (edge2.prevInAEL == null) {
                this.activeEdges = edge2;
            }
            logger.exiting(cls.getName(), "swapPositionsInAEL");
        }
    }

    private void swapPositionsInSEL(Edge edge1, Edge edge2) {
        if (edge1.nextInSEL != null || edge1.prevInSEL != null) {
            if (edge2.nextInSEL != null || edge2.prevInSEL != null) {
                if (edge1.nextInSEL == edge2) {
                    Edge next = edge2.nextInSEL;
                    if (next != null) {
                        next.prevInSEL = edge1;
                    }
                    Edge prev = edge1.prevInSEL;
                    if (prev != null) {
                        prev.nextInSEL = edge2;
                    }
                    edge2.prevInSEL = prev;
                    edge2.nextInSEL = edge1;
                    edge1.prevInSEL = edge2;
                    edge1.nextInSEL = next;
                } else if (edge2.nextInSEL == edge1) {
                    Edge next2 = edge1.nextInSEL;
                    if (next2 != null) {
                        next2.prevInSEL = edge2;
                    }
                    Edge prev2 = edge2.prevInSEL;
                    if (prev2 != null) {
                        prev2.nextInSEL = edge1;
                    }
                    edge1.prevInSEL = prev2;
                    edge1.nextInSEL = edge2;
                    edge2.prevInSEL = edge1;
                    edge2.nextInSEL = next2;
                } else {
                    Edge next3 = edge1.nextInSEL;
                    Edge prev3 = edge1.prevInSEL;
                    edge1.nextInSEL = edge2.nextInSEL;
                    if (edge1.nextInSEL != null) {
                        edge1.nextInSEL.prevInSEL = edge1;
                    }
                    edge1.prevInSEL = edge2.prevInSEL;
                    if (edge1.prevInSEL != null) {
                        edge1.prevInSEL.nextInSEL = edge1;
                    }
                    edge2.nextInSEL = next3;
                    if (edge2.nextInSEL != null) {
                        edge2.nextInSEL.prevInSEL = edge2;
                    }
                    edge2.prevInSEL = prev3;
                    if (edge2.prevInSEL != null) {
                        edge2.prevInSEL.nextInSEL = edge2;
                    }
                }
                if (edge1.prevInSEL == null) {
                    this.sortedEdges = edge1;
                } else if (edge2.prevInSEL == null) {
                    this.sortedEdges = edge2;
                }
            }
        }
    }

    private void updateEdgeIntoAEL(Edge[] eV) {
        Edge e = eV[0];
        if (e.nextInLML != null) {
            Edge AelPrev = e.prevInAEL;
            Edge AelNext = e.nextInAEL;
            e.nextInLML.outIdx = e.outIdx;
            if (AelPrev != null) {
                AelPrev.nextInAEL = e.nextInLML;
            } else {
                this.activeEdges = e.nextInLML;
            }
            if (AelNext != null) {
                AelNext.prevInAEL = e.nextInLML;
            }
            e.nextInLML.side = e.side;
            e.nextInLML.windDelta = e.windDelta;
            e.nextInLML.windCnt = e.windCnt;
            e.nextInLML.windCnt2 = e.windCnt2;
            Edge edge = e.nextInLML;
            Edge e2 = edge;
            eV[0] = edge;
            e2.setCurrent(e2.getBot());
            e2.prevInAEL = AelPrev;
            e2.nextInAEL = AelNext;
            if (!e2.isHorizontal()) {
                insertScanbeam(e2.getTop().getY());
                return;
            }
            return;
        }
        throw new IllegalStateException("UpdateEdgeIntoAEL: invalid call");
    }

    private void updateOutPtIdxs(Path.OutRec outrec) {
        Path.OutPt op = outrec.getPoints();
        do {
            op.idx = outrec.Idx;
            op = op.prev;
        } while (op != outrec.getPoints());
    }

    private void updateWindingCount(Edge edge) {
        Edge e;
        LOGGER.entering(DefaultClipper.class.getName(), "updateWindingCount");
        Edge e2 = edge.prevInAEL;
        while (e2 != null && (e2.polyTyp != edge.polyTyp || e2.windDelta == 0)) {
            e2 = e2.prevInAEL;
        }
        if (e2 == null) {
            edge.windCnt = edge.windDelta == 0 ? 1 : edge.windDelta;
            edge.windCnt2 = 0;
            e = this.activeEdges;
        } else if (edge.windDelta == 0 && this.clipType != IClipper.ClipType.UNION) {
            edge.windCnt = 1;
            edge.windCnt2 = e2.windCnt2;
            e = e2.nextInAEL;
        } else if (edge.isEvenOddFillType(this.clipFillType, this.subjFillType)) {
            if (edge.windDelta == 0) {
                boolean Inside = true;
                for (Edge e22 = e2.prevInAEL; e22 != null; e22 = e22.prevInAEL) {
                    if (e22.polyTyp == e2.polyTyp && e22.windDelta != 0) {
                        Inside = !Inside;
                    }
                }
                edge.windCnt = Inside ? 0 : 1;
            } else {
                edge.windCnt = edge.windDelta;
            }
            edge.windCnt2 = e2.windCnt2;
            e = e2.nextInAEL;
        } else {
            if (e2.windCnt * e2.windDelta < 0) {
                if (Math.abs(e2.windCnt) <= 1) {
                    edge.windCnt = edge.windDelta == 0 ? 1 : edge.windDelta;
                } else if (e2.windDelta * edge.windDelta < 0) {
                    edge.windCnt = e2.windCnt;
                } else {
                    edge.windCnt = e2.windCnt + edge.windDelta;
                }
            } else if (edge.windDelta == 0) {
                edge.windCnt = e2.windCnt < 0 ? e2.windCnt - 1 : e2.windCnt + 1;
            } else if (e2.windDelta * edge.windDelta < 0) {
                edge.windCnt = e2.windCnt;
            } else {
                edge.windCnt = e2.windCnt + edge.windDelta;
            }
            edge.windCnt2 = e2.windCnt2;
            e = e2.nextInAEL;
        }
        if (edge.isEvenOddAltFillType(this.clipFillType, this.subjFillType)) {
            while (e != edge) {
                if (e.windDelta != 0) {
                    edge.windCnt2 = edge.windCnt2 == 0 ? 1 : 0;
                }
                e = e.nextInAEL;
            }
            return;
        }
        while (e != edge) {
            edge.windCnt2 += e.windDelta;
            e = e.nextInAEL;
        }
    }
}
