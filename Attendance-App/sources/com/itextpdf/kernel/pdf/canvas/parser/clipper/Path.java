package com.itextpdf.kernel.pdf.canvas.parser.clipper;

import com.itextpdf.kernel.pdf.canvas.parser.clipper.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Path extends ArrayList<Point.LongPoint> {
    private static final long serialVersionUID = -7120161578077546673L;

    static class Join {
        private Point.LongPoint offPt;
        OutPt outPt1;
        OutPt outPt2;

        Join() {
        }

        public Point.LongPoint getOffPt() {
            return this.offPt;
        }

        public void setOffPt(Point.LongPoint offPt2) {
            this.offPt = offPt2;
        }
    }

    static class OutPt {
        int idx;
        OutPt next;
        OutPt prev;

        /* renamed from: pt */
        protected Point.LongPoint f1490pt;

        OutPt() {
        }

        public static OutRec getLowerMostRec(OutRec outRec1, OutRec outRec2) {
            if (outRec1.bottomPt == null) {
                outRec1.bottomPt = outRec1.pts.getBottomPt();
            }
            if (outRec2.bottomPt == null) {
                outRec2.bottomPt = outRec2.pts.getBottomPt();
            }
            OutPt bPt1 = outRec1.bottomPt;
            OutPt bPt2 = outRec2.bottomPt;
            if (bPt1.getPt().getY() > bPt2.getPt().getY()) {
                return outRec1;
            }
            if (bPt1.getPt().getY() < bPt2.getPt().getY()) {
                return outRec2;
            }
            if (bPt1.getPt().getX() < bPt2.getPt().getX()) {
                return outRec1;
            }
            if (bPt1.getPt().getX() > bPt2.getPt().getX() || bPt1.next == bPt1) {
                return outRec2;
            }
            if (bPt2.next != bPt2 && !isFirstBottomPt(bPt1, bPt2)) {
                return outRec2;
            }
            return outRec1;
        }

        private static boolean isFirstBottomPt(OutPt btmPt1, OutPt btmPt2) {
            OutPt p = btmPt1.prev;
            while (p.getPt().equals(btmPt1.getPt()) && !p.equals(btmPt1)) {
                p = p.prev;
            }
            double dx1p = Math.abs(Point.LongPoint.getDeltaX(btmPt1.getPt(), p.getPt()));
            OutPt p2 = btmPt1.next;
            while (p2.getPt().equals(btmPt1.getPt()) && !p2.equals(btmPt1)) {
                p2 = p2.next;
            }
            double dx1n = Math.abs(Point.LongPoint.getDeltaX(btmPt1.getPt(), p2.getPt()));
            OutPt p3 = btmPt2.prev;
            while (p3.getPt().equals(btmPt2.getPt()) && !p3.equals(btmPt2)) {
                p3 = p3.prev;
            }
            double dx2p = Math.abs(Point.LongPoint.getDeltaX(btmPt2.getPt(), p3.getPt()));
            OutPt p4 = btmPt2.next;
            while (p4.getPt().equals(btmPt2.getPt()) && p4.equals(btmPt2)) {
                p4 = p4.next;
            }
            double dx2n = Math.abs(Point.LongPoint.getDeltaX(btmPt2.getPt(), p4.getPt()));
            return (dx1p >= dx2p && dx1p >= dx2n) || (dx1n >= dx2p && dx1n >= dx2n);
        }

        public OutPt duplicate(boolean InsertAfter) {
            OutPt result = new OutPt();
            result.setPt(new Point.LongPoint(getPt()));
            result.idx = this.idx;
            if (InsertAfter) {
                result.next = this.next;
                result.prev = this;
                this.next.prev = result;
                this.next = result;
            } else {
                result.prev = this.prev;
                result.next = this;
                this.prev.next = result;
                this.prev = result;
            }
            return result;
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0068, code lost:
            if (r0 != null) goto L_0x006a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x006a, code lost:
            if (r0 == r1) goto L_0x0086;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0070, code lost:
            if (isFirstBottomPt(r1, r0) != false) goto L_0x0073;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0072, code lost:
            r2 = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0073, code lost:
            r0 = r0.next;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0081, code lost:
            if (r0.getPt().equals(r2.getPt()) != false) goto L_0x006a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0083, code lost:
            r0 = r0.next;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0086, code lost:
            return r2;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public com.itextpdf.kernel.pdf.canvas.parser.clipper.Path.OutPt getBottomPt() {
            /*
                r8 = this;
                r0 = 0
                com.itextpdf.kernel.pdf.canvas.parser.clipper.Path$OutPt r1 = r8.next
                r2 = r8
            L_0x0004:
                if (r1 == r2) goto L_0x0068
                com.itextpdf.kernel.pdf.canvas.parser.clipper.Point$LongPoint r3 = r1.getPt()
                long r3 = r3.getY()
                com.itextpdf.kernel.pdf.canvas.parser.clipper.Point$LongPoint r5 = r2.getPt()
                long r5 = r5.getY()
                int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                if (r7 <= 0) goto L_0x001d
                r2 = r1
                r0 = 0
                goto L_0x0065
            L_0x001d:
                com.itextpdf.kernel.pdf.canvas.parser.clipper.Point$LongPoint r3 = r1.getPt()
                long r3 = r3.getY()
                com.itextpdf.kernel.pdf.canvas.parser.clipper.Point$LongPoint r5 = r2.getPt()
                long r5 = r5.getY()
                int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                if (r7 != 0) goto L_0x0065
                com.itextpdf.kernel.pdf.canvas.parser.clipper.Point$LongPoint r3 = r1.getPt()
                long r3 = r3.getX()
                com.itextpdf.kernel.pdf.canvas.parser.clipper.Point$LongPoint r5 = r2.getPt()
                long r5 = r5.getX()
                int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                if (r7 > 0) goto L_0x0065
                com.itextpdf.kernel.pdf.canvas.parser.clipper.Point$LongPoint r3 = r1.getPt()
                long r3 = r3.getX()
                com.itextpdf.kernel.pdf.canvas.parser.clipper.Point$LongPoint r5 = r2.getPt()
                long r5 = r5.getX()
                int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                if (r7 >= 0) goto L_0x005c
                r0 = 0
                r2 = r1
                goto L_0x0065
            L_0x005c:
                com.itextpdf.kernel.pdf.canvas.parser.clipper.Path$OutPt r3 = r1.next
                if (r3 == r2) goto L_0x0065
                com.itextpdf.kernel.pdf.canvas.parser.clipper.Path$OutPt r3 = r1.prev
                if (r3 == r2) goto L_0x0065
                r0 = r1
            L_0x0065:
                com.itextpdf.kernel.pdf.canvas.parser.clipper.Path$OutPt r1 = r1.next
                goto L_0x0004
            L_0x0068:
                if (r0 == 0) goto L_0x0086
            L_0x006a:
                if (r0 == r1) goto L_0x0086
                boolean r3 = isFirstBottomPt(r1, r0)
                if (r3 != 0) goto L_0x0073
                r2 = r0
            L_0x0073:
                com.itextpdf.kernel.pdf.canvas.parser.clipper.Path$OutPt r0 = r0.next
            L_0x0075:
                com.itextpdf.kernel.pdf.canvas.parser.clipper.Point$LongPoint r3 = r0.getPt()
                com.itextpdf.kernel.pdf.canvas.parser.clipper.Point$LongPoint r4 = r2.getPt()
                boolean r3 = r3.equals(r4)
                if (r3 != 0) goto L_0x006a
                com.itextpdf.kernel.pdf.canvas.parser.clipper.Path$OutPt r0 = r0.next
                goto L_0x0075
            L_0x0086:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.pdf.canvas.parser.clipper.Path.OutPt.getBottomPt():com.itextpdf.kernel.pdf.canvas.parser.clipper.Path$OutPt");
        }

        public int getPointCount() {
            int result = 0;
            OutPt p = this;
            do {
                result++;
                p = p.next;
                if (p == this) {
                    break;
                }
            } while (p != null);
            return result;
        }

        public Point.LongPoint getPt() {
            return this.f1490pt;
        }

        public void reversePolyPtLinks() {
            OutPt pp1 = this;
            do {
                OutPt pp2 = pp1.next;
                pp1.next = pp1.prev;
                pp1.prev = pp2;
                pp1 = pp2;
            } while (pp1 != this);
        }

        public void setPt(Point.LongPoint pt) {
            this.f1490pt = pt;
        }
    }

    protected static class Maxima {
        protected Maxima Next;
        protected Maxima Prev;

        /* renamed from: X */
        protected long f1489X;

        protected Maxima() {
        }
    }

    static class OutRec {
        int Idx;
        OutPt bottomPt;
        OutRec firstLeft;
        boolean isHole;
        boolean isOpen;
        PolyNode polyNode;
        protected OutPt pts;

        OutRec() {
        }

        public double area() {
            OutPt op = this.pts;
            if (op == null) {
                return 0.0d;
            }
            double a = 0.0d;
            do {
                double x = (double) (op.prev.getPt().getX() + op.getPt().getX());
                double y = (double) (op.prev.getPt().getY() - op.getPt().getY());
                Double.isNaN(x);
                Double.isNaN(y);
                a += x * y;
                op = op.next;
            } while (op != this.pts);
            return 0.5d * a;
        }

        public void fixHoleLinkage() {
            OutRec outRec = this.firstLeft;
            if (outRec == null) {
                return;
            }
            if (this.isHole == outRec.isHole || outRec.pts == null) {
                OutRec orfl = this.firstLeft;
                while (orfl != null && (orfl.isHole == this.isHole || orfl.pts == null)) {
                    orfl = orfl.firstLeft;
                }
                this.firstLeft = orfl;
            }
        }

        public OutPt getPoints() {
            return this.pts;
        }

        public void setPoints(OutPt pts2) {
            this.pts = pts2;
        }
    }

    private static OutPt excludeOp(OutPt op) {
        OutPt result = op.prev;
        result.next = op.next;
        op.next.prev = result;
        result.idx = 0;
        return result;
    }

    public Path() {
    }

    public Path(Point.LongPoint[] points) {
        this();
        for (Point.LongPoint point : points) {
            add(point);
        }
    }

    public Path(int cnt) {
        super(cnt);
    }

    public Path(Collection<? extends Point.LongPoint> c) {
        super(c);
    }

    public double area() {
        int cnt = size();
        if (cnt < 3) {
            return 0.0d;
        }
        double a = 0.0d;
        int j = cnt - 1;
        for (int i = 0; i < cnt; i++) {
            double x = (double) ((Point.LongPoint) get(j)).getX();
            double x2 = (double) ((Point.LongPoint) get(i)).getX();
            Double.isNaN(x);
            Double.isNaN(x2);
            double d = x + x2;
            double y = (double) ((Point.LongPoint) get(j)).getY();
            double y2 = (double) ((Point.LongPoint) get(i)).getY();
            Double.isNaN(y);
            Double.isNaN(y2);
            a += d * (y - y2);
            j = i;
        }
        return (-a) * 0.5d;
    }

    public Path cleanPolygon() {
        return cleanPolygon(1.415d);
    }

    public Path cleanPolygon(double distance) {
        int cnt = size();
        if (cnt == 0) {
            return new Path();
        }
        OutPt[] outPts = new OutPt[cnt];
        for (int i = 0; i < cnt; i++) {
            outPts[i] = new OutPt();
        }
        for (int i2 = 0; i2 < cnt; i2++) {
            outPts[i2].f1490pt = (Point.LongPoint) get(i2);
            outPts[i2].next = outPts[(i2 + 1) % cnt];
            outPts[i2].next.prev = outPts[i2];
            outPts[i2].idx = 0;
        }
        double distSqrd = distance * distance;
        OutPt op = outPts[0];
        while (op.idx == 0 && op.next != op.prev) {
            if (Point.arePointsClose(op.f1490pt, op.prev.f1490pt, distSqrd)) {
                op = excludeOp(op);
                cnt--;
            } else if (Point.arePointsClose(op.prev.f1490pt, op.next.f1490pt, distSqrd)) {
                excludeOp(op.next);
                op = excludeOp(op);
                cnt -= 2;
            } else if (Point.slopesNearCollinear(op.prev.f1490pt, op.f1490pt, op.next.f1490pt, distSqrd)) {
                op = excludeOp(op);
                cnt--;
            } else {
                op.idx = 1;
                op = op.next;
            }
        }
        if (cnt < 3) {
            cnt = 0;
        }
        Path result = new Path(cnt);
        for (int i3 = 0; i3 < cnt; i3++) {
            result.add(op.f1490pt);
            op = op.next;
        }
        return result;
    }

    public int isPointInPolygon(Point.LongPoint pt) {
        int i;
        int result = 0;
        int cnt = size();
        int i2 = 0;
        if (cnt < 3) {
            return 0;
        }
        Point.LongPoint ip = (Point.LongPoint) get(0);
        int i3 = 1;
        while (i3 <= cnt) {
            Point.LongPoint ipNext = (Point.LongPoint) (i3 == cnt ? get(i2) : get(i3));
            boolean z = true;
            if (ipNext.getY() == pt.getY()) {
                if (ipNext.getX() != pt.getX()) {
                    if (ip.getY() == pt.getY()) {
                        if ((ipNext.getX() > pt.getX()) == (ip.getX() < pt.getX())) {
                        }
                    }
                }
                return -1;
            }
            if ((ip.getY() < pt.getY()) == (ipNext.getY() < pt.getY())) {
                i = i3;
            } else if (ip.getX() < pt.getX()) {
                i = i3;
                if (ipNext.getX() > pt.getX()) {
                    double x = (double) (ip.getX() - pt.getX());
                    double y = (double) (ipNext.getY() - pt.getY());
                    Double.isNaN(x);
                    Double.isNaN(y);
                    double d = x * y;
                    double x2 = (double) (ipNext.getX() - pt.getX());
                    double y2 = (double) (ip.getY() - pt.getY());
                    Double.isNaN(x2);
                    Double.isNaN(y2);
                    double d2 = d - (x2 * y2);
                    if (d2 == 0.0d) {
                        return -1;
                    }
                    boolean z2 = d2 > 0.0d;
                    if (ipNext.getY() <= ip.getY()) {
                        z = false;
                    }
                    if (z2 == z) {
                        result = 1 - result;
                    }
                } else {
                    continue;
                }
            } else if (ipNext.getX() > pt.getX()) {
                result = 1 - result;
                i = i3;
            } else {
                double x3 = (double) (ip.getX() - pt.getX());
                double y3 = (double) (ipNext.getY() - pt.getY());
                Double.isNaN(x3);
                Double.isNaN(y3);
                double d3 = x3 * y3;
                double x4 = (double) (ipNext.getX() - pt.getX());
                i = i3;
                double y4 = (double) (ip.getY() - pt.getY());
                Double.isNaN(x4);
                Double.isNaN(y4);
                double d4 = d3 - (x4 * y4);
                if (d4 == 0.0d) {
                    return -1;
                }
                boolean z3 = d4 > 0.0d;
                if (ipNext.getY() <= ip.getY()) {
                    z = false;
                }
                if (z3 == z) {
                    result = 1 - result;
                }
            }
            ip = ipNext;
            i3 = i + 1;
            i2 = 0;
        }
        return result;
    }

    public boolean orientation() {
        return area() >= 0.0d;
    }

    public void reverse() {
        Collections.reverse(this);
    }

    public Path TranslatePath(Point.LongPoint delta) {
        Path outPath = new Path(size());
        for (int i = 0; i < size(); i++) {
            outPath.add(new Point.LongPoint(((Point.LongPoint) get(i)).getX() + delta.getX(), ((Point.LongPoint) get(i)).getY() + delta.getY()));
        }
        return outPath;
    }
}
