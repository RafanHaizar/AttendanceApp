package com.itextpdf.kernel.pdf.canvas.parser.clipper;

import com.itextpdf.kernel.pdf.canvas.parser.clipper.Edge;
import com.itextpdf.kernel.pdf.canvas.parser.clipper.IClipper;
import com.itextpdf.kernel.pdf.canvas.parser.clipper.Path;
import com.itextpdf.kernel.pdf.canvas.parser.clipper.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class ClipperBase implements IClipper {
    private static final long HI_RANGE = 4611686018427387903L;
    private static final Logger LOGGER = Logger.getLogger(IClipper.class.getName());
    private static final long LOW_RANGE = 1073741823;
    protected LocalMinima currentLM = null;
    private final List<List<Edge>> edges = new ArrayList();
    protected boolean hasOpenPaths = false;
    protected LocalMinima minimaList = null;
    protected final boolean preserveCollinear;
    protected boolean useFullRange;

    protected class LocalMinima {
        Edge leftBound;
        LocalMinima next;
        Edge rightBound;

        /* renamed from: y */
        long f1481y;

        protected LocalMinima() {
        }
    }

    protected class Scanbeam {
        Scanbeam next;

        /* renamed from: y */
        long f1482y;

        protected Scanbeam() {
        }
    }

    private static void initEdge(Edge e, Edge eNext, Edge ePrev, Point.LongPoint pt) {
        e.next = eNext;
        e.prev = ePrev;
        e.setCurrent(new Point.LongPoint(pt));
        e.outIdx = -1;
    }

    private static void initEdge2(Edge e, IClipper.PolyType polyType) {
        if (e.getCurrent().getY() >= e.next.getCurrent().getY()) {
            e.setBot(new Point.LongPoint(e.getCurrent()));
            e.setTop(new Point.LongPoint(e.next.getCurrent()));
        } else {
            e.setTop(new Point.LongPoint(e.getCurrent()));
            e.setBot(new Point.LongPoint(e.next.getCurrent()));
        }
        e.updateDeltaX();
        e.polyTyp = polyType;
    }

    private static boolean rangeTest(Point.LongPoint Pt, boolean useFullRange2) {
        if (useFullRange2) {
            if (Pt.getX() > 4611686018427387903L || Pt.getY() > 4611686018427387903L || (-Pt.getX()) > 4611686018427387903L || (-Pt.getY()) > 4611686018427387903L) {
                throw new ClipperException(ClipperExceptionConstant.COORDINATE_OUTSIDE_ALLOWED_RANGE);
            }
        } else if (Pt.getX() > 1073741823 || Pt.getY() > 1073741823 || (-Pt.getX()) > 1073741823 || (-Pt.getY()) > 1073741823) {
            return rangeTest(Pt, true);
        }
        return useFullRange2;
    }

    private static Edge removeEdge(Edge e) {
        e.prev.next = e.next;
        e.next.prev = e.prev;
        Edge result = e.next;
        e.prev = null;
        return result;
    }

    protected ClipperBase(boolean preserveCollinear2) {
        this.preserveCollinear = preserveCollinear2;
    }

    public boolean addPath(Path pg, IClipper.PolyType polyType, boolean Closed) {
        Edge E2;
        boolean leftBoundIsForward;
        LocalMinima localMinima;
        Path path = pg;
        IClipper.PolyType polyType2 = polyType;
        if (Closed || polyType2 != IClipper.PolyType.CLIP) {
            boolean leftBoundIsForward2 = true;
            int highI = pg.size() - 1;
            boolean z = false;
            if (Closed) {
                while (highI > 0 && ((Point.LongPoint) path.get(highI)).equals(path.get(0))) {
                    highI--;
                }
            }
            while (highI > 0 && ((Point.LongPoint) path.get(highI)).equals(path.get(highI - 1))) {
                highI--;
            }
            if ((Closed && highI < 2) || (!Closed && highI < 1)) {
                return false;
            }
            List<Edge> edges2 = new ArrayList<>(highI + 1);
            for (int i = 0; i <= highI; i++) {
                edges2.add(new Edge());
            }
            boolean IsFlat = true;
            edges2.get(1).setCurrent(new Point.LongPoint((Point.LongPoint) path.get(1)));
            this.useFullRange = rangeTest((Point.LongPoint) path.get(0), this.useFullRange);
            this.useFullRange = rangeTest((Point.LongPoint) path.get(highI), this.useFullRange);
            initEdge(edges2.get(0), edges2.get(1), edges2.get(highI), (Point.LongPoint) path.get(0));
            initEdge(edges2.get(highI), edges2.get(0), edges2.get(highI - 1), (Point.LongPoint) path.get(highI));
            for (int i2 = highI - 1; i2 >= 1; i2--) {
                this.useFullRange = rangeTest((Point.LongPoint) path.get(i2), this.useFullRange);
                initEdge(edges2.get(i2), edges2.get(i2 + 1), edges2.get(i2 - 1), (Point.LongPoint) path.get(i2));
            }
            Edge eStart = edges2.get(0);
            Edge e = eStart;
            Edge eLoopStop = eStart;
            while (true) {
                if (!e.getCurrent().equals(e.next.getCurrent()) || (!Closed && e.next.equals(eStart))) {
                    if (e.prev == e.next) {
                        break;
                    } else if (!Closed || !Point.slopesEqual(e.prev.getCurrent(), e.getCurrent(), e.next.getCurrent(), this.useFullRange) || (isPreserveCollinear() && Point.isPt2BetweenPt1AndPt3(e.prev.getCurrent(), e.getCurrent(), e.next.getCurrent()))) {
                        e = e.next;
                        if (e != eLoopStop) {
                            if (!Closed && e.next == eStart) {
                                break;
                            }
                        } else {
                            break;
                        }
                    } else {
                        if (e == eStart) {
                            eStart = e.next;
                        }
                        e = removeEdge(e).prev;
                        eLoopStop = e;
                    }
                } else if (e == e.next) {
                    break;
                } else {
                    if (e == eStart) {
                        eStart = e.next;
                    }
                    e = removeEdge(e);
                    eLoopStop = e;
                }
            }
            if ((!Closed && e == e.next) || (Closed && e.prev == e.next)) {
                return false;
            }
            if (!Closed) {
                this.hasOpenPaths = true;
                eStart.prev.outIdx = -2;
            }
            Edge E22 = eStart;
            while (true) {
                initEdge2(E22, polyType2);
                E22 = E22.next;
                if (IsFlat && E22.getCurrent().getY() != eStart.getCurrent().getY()) {
                    IsFlat = false;
                }
                if (E22 == eStart) {
                    break;
                }
                leftBoundIsForward2 = true;
                z = false;
            }
            LocalMinima localMinima2 = null;
            if (!IsFlat) {
                this.edges.add(edges2);
                Edge EMin = null;
                if (E22.prev.getBot().equals(E22.prev.getTop())) {
                    E22 = E22.next;
                }
                while (true) {
                    Edge e2 = E2.findNextLocMin();
                    if (e2 == EMin) {
                        return leftBoundIsForward2;
                    }
                    if (EMin == null) {
                        EMin = e2;
                    }
                    LocalMinima locMin = new LocalMinima();
                    locMin.next = localMinima2;
                    Edge EMin2 = EMin;
                    locMin.f1481y = e2.getBot().getY();
                    if (e2.deltaX < e2.prev.deltaX) {
                        locMin.leftBound = e2.prev;
                        locMin.rightBound = e2;
                        leftBoundIsForward = false;
                    } else {
                        locMin.leftBound = e2;
                        locMin.rightBound = e2.prev;
                        leftBoundIsForward = true;
                    }
                    locMin.leftBound.side = Edge.Side.LEFT;
                    locMin.rightBound.side = Edge.Side.RIGHT;
                    if (!Closed) {
                        locMin.leftBound.windDelta = 0;
                    } else if (locMin.leftBound.next == locMin.rightBound) {
                        locMin.leftBound.windDelta = -1;
                    } else {
                        locMin.leftBound.windDelta = 1;
                    }
                    locMin.rightBound.windDelta = -locMin.leftBound.windDelta;
                    Edge e3 = processBound(locMin.leftBound, leftBoundIsForward);
                    if (e3.outIdx == -2) {
                        e3 = processBound(e3, leftBoundIsForward);
                    }
                    E2 = processBound(locMin.rightBound, !leftBoundIsForward);
                    if (E2.outIdx == -2) {
                        E2 = processBound(E2, !leftBoundIsForward);
                    }
                    if (locMin.leftBound.outIdx == -2) {
                        localMinima = null;
                        locMin.leftBound = null;
                    } else {
                        localMinima = null;
                        if (locMin.rightBound.outIdx == -2) {
                            locMin.rightBound = null;
                        }
                    }
                    insertLocalMinima(locMin);
                    if (!leftBoundIsForward) {
                        Edge e4 = E2;
                    } else {
                        E2 = e3;
                    }
                    localMinima2 = localMinima;
                    EMin = EMin2;
                    leftBoundIsForward2 = true;
                }
            } else if (Closed) {
                return z;
            } else {
                E22.prev.outIdx = -2;
                LocalMinima locMin2 = new LocalMinima();
                locMin2.next = null;
                locMin2.f1481y = E22.getBot().getY();
                locMin2.leftBound = null;
                locMin2.rightBound = E22;
                locMin2.rightBound.side = Edge.Side.RIGHT;
                locMin2.rightBound.windDelta = z ? 1 : 0;
                while (true) {
                    if (E22.getBot().getX() != E22.prev.getTop().getX()) {
                        E22.reverseHorizontal();
                    }
                    if (E22.next.outIdx == -2) {
                        insertLocalMinima(locMin2);
                        this.edges.add(edges2);
                        return leftBoundIsForward2;
                    }
                    E22.nextInLML = E22.next;
                    E22 = E22.next;
                }
            }
        } else {
            throw new IllegalStateException("AddPath: Open paths must be subject.");
        }
    }

    public boolean addPaths(Paths ppg, IClipper.PolyType polyType, boolean closed) {
        boolean result = false;
        for (int i = 0; i < ppg.size(); i++) {
            if (addPath((Path) ppg.get(i), polyType, closed)) {
                result = true;
            }
        }
        return result;
    }

    public void clear() {
        disposeLocalMinimaList();
        this.edges.clear();
        this.useFullRange = false;
        this.hasOpenPaths = false;
    }

    private void disposeLocalMinimaList() {
        while (true) {
            LocalMinima localMinima = this.minimaList;
            if (localMinima != null) {
                LocalMinima tmpLm = localMinima.next;
                this.minimaList = null;
                this.minimaList = tmpLm;
            } else {
                this.currentLM = null;
                return;
            }
        }
    }

    private void insertLocalMinima(LocalMinima newLm) {
        if (this.minimaList == null) {
            this.minimaList = newLm;
        } else if (newLm.f1481y >= this.minimaList.f1481y) {
            newLm.next = this.minimaList;
            this.minimaList = newLm;
        } else {
            LocalMinima tmpLm = this.minimaList;
            while (tmpLm.next != null && newLm.f1481y < tmpLm.next.f1481y) {
                tmpLm = tmpLm.next;
            }
            newLm.next = tmpLm.next;
            tmpLm.next = newLm;
        }
    }

    public boolean isPreserveCollinear() {
        return this.preserveCollinear;
    }

    /* access modifiers changed from: protected */
    public void popLocalMinima() {
        LOGGER.entering(ClipperBase.class.getName(), "popLocalMinima");
        LocalMinima localMinima = this.currentLM;
        if (localMinima != null) {
            this.currentLM = localMinima.next;
        }
    }

    private Edge processBound(Edge e, boolean LeftBoundIsForward) {
        Edge EStart;
        Edge e2;
        Edge result = e;
        if (result.outIdx == -2) {
            Edge e3 = result;
            if (LeftBoundIsForward) {
                while (e3.getTop().getY() == e3.next.getBot().getY()) {
                    e3 = e3.next;
                }
                while (e3 != result && e3.deltaX == -3.4E38d) {
                    e3 = e3.prev;
                }
            } else {
                while (e3.getTop().getY() == e3.prev.getBot().getY()) {
                    e3 = e3.prev;
                }
                while (e3 != result && e3.deltaX == -3.4E38d) {
                    e3 = e3.next;
                }
            }
            if (e3 != result) {
                if (LeftBoundIsForward) {
                    e2 = result.next;
                } else {
                    e2 = result.prev;
                }
                LocalMinima locMin = new LocalMinima();
                locMin.next = null;
                locMin.f1481y = e2.getBot().getY();
                locMin.leftBound = null;
                locMin.rightBound = e2;
                e2.windDelta = 0;
                Edge result2 = processBound(e2, LeftBoundIsForward);
                insertLocalMinima(locMin);
                return result2;
            } else if (LeftBoundIsForward) {
                return e3.next;
            } else {
                return e3.prev;
            }
        } else {
            if (e.deltaX == -3.4E38d) {
                if (LeftBoundIsForward) {
                    EStart = e.prev;
                } else {
                    EStart = e.next;
                }
                if (EStart.deltaX == -3.4E38d) {
                    if (!(EStart.getBot().getX() == e.getBot().getX() || EStart.getTop().getX() == e.getBot().getX())) {
                        e.reverseHorizontal();
                    }
                } else if (EStart.getBot().getX() != e.getBot().getX()) {
                    e.reverseHorizontal();
                }
            }
            Edge EStart2 = e;
            if (LeftBoundIsForward) {
                while (result.getTop().getY() == result.next.getBot().getY() && result.next.outIdx != -2) {
                    result = result.next;
                }
                if (result.deltaX == -3.4E38d && result.next.outIdx != -2) {
                    Edge Horz = result;
                    while (Horz.prev.deltaX == -3.4E38d) {
                        Horz = Horz.prev;
                    }
                    if (Horz.prev.getTop().getX() > result.next.getTop().getX()) {
                        result = Horz.prev;
                    }
                }
                while (e != result) {
                    e.nextInLML = e.next;
                    if (!(e.deltaX != -3.4E38d || e == EStart2 || e.getBot().getX() == e.prev.getTop().getX())) {
                        e.reverseHorizontal();
                    }
                    e = e.next;
                }
                if (!(e.deltaX != -3.4E38d || e == EStart2 || e.getBot().getX() == e.prev.getTop().getX())) {
                    e.reverseHorizontal();
                }
                return result.next;
            }
            while (result.getTop().getY() == result.prev.getBot().getY() && result.prev.outIdx != -2) {
                result = result.prev;
            }
            if (result.deltaX == -3.4E38d && result.prev.outIdx != -2) {
                Edge Horz2 = result;
                while (Horz2.next.deltaX == -3.4E38d) {
                    Horz2 = Horz2.next;
                }
                if (Horz2.next.getTop().getX() == result.prev.getTop().getX() || Horz2.next.getTop().getX() > result.prev.getTop().getX()) {
                    result = Horz2.next;
                }
            }
            while (e != result) {
                e.nextInLML = e.prev;
                if (!(e.deltaX != -3.4E38d || e == EStart2 || e.getBot().getX() == e.next.getTop().getX())) {
                    e.reverseHorizontal();
                }
                e = e.prev;
            }
            if (!(e.deltaX != -3.4E38d || e == EStart2 || e.getBot().getX() == e.next.getTop().getX())) {
                e.reverseHorizontal();
            }
            return result.prev;
        }
    }

    protected static Path.OutRec parseFirstLeft(Path.OutRec FirstLeft) {
        while (FirstLeft != null && FirstLeft.getPoints() == null) {
            FirstLeft = FirstLeft.firstLeft;
        }
        return FirstLeft;
    }

    /* access modifiers changed from: protected */
    public void reset() {
        LocalMinima localMinima = this.minimaList;
        this.currentLM = localMinima;
        if (localMinima != null) {
            for (LocalMinima lm = this.minimaList; lm != null; lm = lm.next) {
                Edge e = lm.leftBound;
                if (e != null) {
                    e.setCurrent(new Point.LongPoint(e.getBot()));
                    e.side = Edge.Side.LEFT;
                    e.outIdx = -1;
                }
                Edge e2 = lm.rightBound;
                if (e2 != null) {
                    e2.setCurrent(new Point.LongPoint(e2.getBot()));
                    e2.side = Edge.Side.RIGHT;
                    e2.outIdx = -1;
                }
            }
        }
    }
}
