package com.itextpdf.kernel.pdf.canvas.parser.clipper;

import com.itextpdf.kernel.pdf.canvas.parser.clipper.IClipper;
import com.itextpdf.kernel.pdf.canvas.parser.clipper.Point;
import java.math.BigInteger;
import java.util.logging.Logger;

class Edge {
    protected static final double HORIZONTAL = -3.4E38d;
    private static final Logger LOGGER = Logger.getLogger(Edge.class.getName());
    protected static final int SKIP = -2;
    protected static final int UNASSIGNED = -1;
    private final Point.LongPoint bot = new Point.LongPoint();
    private final Point.LongPoint current = new Point.LongPoint();
    private final Point.LongPoint delta = new Point.LongPoint();
    double deltaX;
    Edge next;
    Edge nextInAEL;
    Edge nextInLML;
    Edge nextInSEL;
    int outIdx;
    IClipper.PolyType polyTyp;
    Edge prev;
    Edge prevInAEL;
    Edge prevInSEL;
    Side side;
    private final Point.LongPoint top = new Point.LongPoint();
    int windCnt;
    int windCnt2;
    int windDelta;

    enum Side {
        LEFT,
        RIGHT
    }

    static boolean doesE2InsertBeforeE1(Edge e1, Edge e2) {
        if (e2.current.getX() == e1.current.getX()) {
            if (e2.top.getY() > e1.top.getY()) {
                if (e2.top.getX() < topX(e1, e2.top.getY())) {
                    return true;
                }
                return false;
            } else if (e1.top.getX() > topX(e2, e1.top.getY())) {
                return true;
            } else {
                return false;
            }
        } else if (e2.current.getX() < e1.current.getX()) {
            return true;
        } else {
            return false;
        }
    }

    static boolean slopesEqual(Edge e1, Edge e2, boolean useFullRange) {
        if (useFullRange) {
            return BigInteger.valueOf(e1.getDelta().getY()).multiply(BigInteger.valueOf(e2.getDelta().getX())).equals(BigInteger.valueOf(e1.getDelta().getX()).multiply(BigInteger.valueOf(e2.getDelta().getY())));
        }
        return e1.getDelta().getY() * e2.getDelta().getX() == e1.getDelta().getX() * e2.getDelta().getY();
    }

    static void swapPolyIndexes(Edge edge1, Edge edge2) {
        int outIdx2 = edge1.outIdx;
        edge1.outIdx = edge2.outIdx;
        edge2.outIdx = outIdx2;
    }

    static void swapSides(Edge edge1, Edge edge2) {
        Side side2 = edge1.side;
        edge1.side = edge2.side;
        edge2.side = side2;
    }

    static long topX(Edge edge, long currentY) {
        if (currentY == edge.getTop().getY()) {
            return edge.getTop().getX();
        }
        long x = edge.getBot().getX();
        double d = edge.deltaX;
        double y = (double) (currentY - edge.getBot().getY());
        Double.isNaN(y);
        return x + Math.round(d * y);
    }

    public Edge findNextLocMin() {
        Edge e = this;
        while (true) {
            if (!e.bot.equals(e.prev.bot) || e.current.equals(e.top)) {
                e = e.next;
            } else if (e.deltaX != HORIZONTAL && e.prev.deltaX != HORIZONTAL) {
                return e;
            } else {
                while (e.prev.deltaX == HORIZONTAL) {
                    e = e.prev;
                }
                Edge e2 = e;
                while (e.deltaX == HORIZONTAL) {
                    e = e.next;
                }
                if (e.top.getY() != e.prev.bot.getY()) {
                    if (e2.prev.bot.getX() < e.bot.getX()) {
                        return e2;
                    }
                    return e;
                }
            }
        }
    }

    public Point.LongPoint getBot() {
        return this.bot;
    }

    public Point.LongPoint getCurrent() {
        return this.current;
    }

    public Point.LongPoint getDelta() {
        return this.delta;
    }

    public Edge getMaximaPair() {
        Edge result = null;
        if (this.next.top.equals(this.top) && this.next.nextInLML == null) {
            result = this.next;
        } else if (this.prev.top.equals(this.top) && this.prev.nextInLML == null) {
            result = this.prev;
        }
        if (result != null) {
            if (result.outIdx == -2) {
                return null;
            }
            if (result.nextInAEL != result.prevInAEL || result.isHorizontal()) {
                return result;
            }
            return null;
        }
        return result;
    }

    public Edge getNextInAEL(IClipper.Direction direction) {
        return direction == IClipper.Direction.LEFT_TO_RIGHT ? this.nextInAEL : this.prevInAEL;
    }

    public Point.LongPoint getTop() {
        return this.top;
    }

    public boolean isContributing(IClipper.PolyFillType clipFillType, IClipper.PolyFillType subjFillType, IClipper.ClipType clipType) {
        IClipper.PolyFillType pft2;
        IClipper.PolyFillType pft;
        LOGGER.entering(Edge.class.getName(), "isContributing");
        if (this.polyTyp == IClipper.PolyType.SUBJECT) {
            pft = subjFillType;
            pft2 = clipFillType;
        } else {
            pft = clipFillType;
            pft2 = subjFillType;
        }
        switch (C14351.f1488x682d75a5[pft.ordinal()]) {
            case 1:
                if (this.windDelta == 0 && this.windCnt != 1) {
                    return false;
                }
            case 2:
                if (Math.abs(this.windCnt) != 1) {
                    return false;
                }
                break;
            case 3:
                if (this.windCnt != 1) {
                    return false;
                }
                break;
            default:
                if (this.windCnt != -1) {
                    return false;
                }
                break;
        }
        switch (C14351.f1487xa4df9306[clipType.ordinal()]) {
            case 1:
                switch (C14351.f1488x682d75a5[pft2.ordinal()]) {
                    case 1:
                    case 2:
                        if (this.windCnt2 != 0) {
                            return true;
                        }
                        return false;
                    case 3:
                        if (this.windCnt2 > 0) {
                            return true;
                        }
                        return false;
                    default:
                        if (this.windCnt2 < 0) {
                            return true;
                        }
                        return false;
                }
            case 2:
                switch (C14351.f1488x682d75a5[pft2.ordinal()]) {
                    case 1:
                    case 2:
                        if (this.windCnt2 == 0) {
                            return true;
                        }
                        return false;
                    case 3:
                        if (this.windCnt2 <= 0) {
                            return true;
                        }
                        return false;
                    default:
                        if (this.windCnt2 >= 0) {
                            return true;
                        }
                        return false;
                }
            case 3:
                if (this.polyTyp == IClipper.PolyType.SUBJECT) {
                    switch (C14351.f1488x682d75a5[pft2.ordinal()]) {
                        case 1:
                        case 2:
                            if (this.windCnt2 == 0) {
                                return true;
                            }
                            return false;
                        case 3:
                            if (this.windCnt2 <= 0) {
                                return true;
                            }
                            return false;
                        default:
                            if (this.windCnt2 >= 0) {
                                return true;
                            }
                            return false;
                    }
                } else {
                    switch (C14351.f1488x682d75a5[pft2.ordinal()]) {
                        case 1:
                        case 2:
                            if (this.windCnt2 != 0) {
                                return true;
                            }
                            return false;
                        case 3:
                            if (this.windCnt2 > 0) {
                                return true;
                            }
                            return false;
                        default:
                            if (this.windCnt2 < 0) {
                                return true;
                            }
                            return false;
                    }
                }
            case 4:
                if (this.windDelta != 0) {
                    return true;
                }
                switch (C14351.f1488x682d75a5[pft2.ordinal()]) {
                    case 1:
                    case 2:
                        if (this.windCnt2 == 0) {
                            return true;
                        }
                        return false;
                    case 3:
                        if (this.windCnt2 <= 0) {
                            return true;
                        }
                        return false;
                    default:
                        if (this.windCnt2 >= 0) {
                            return true;
                        }
                        return false;
                }
            default:
                return true;
        }
    }

    /* renamed from: com.itextpdf.kernel.pdf.canvas.parser.clipper.Edge$1 */
    static /* synthetic */ class C14351 {

        /* renamed from: $SwitchMap$com$itextpdf$kernel$pdf$canvas$parser$clipper$IClipper$ClipType */
        static final /* synthetic */ int[] f1487xa4df9306;

        /* renamed from: $SwitchMap$com$itextpdf$kernel$pdf$canvas$parser$clipper$IClipper$PolyFillType */
        static final /* synthetic */ int[] f1488x682d75a5;

        static {
            int[] iArr = new int[IClipper.ClipType.values().length];
            f1487xa4df9306 = iArr;
            try {
                iArr[IClipper.ClipType.INTERSECTION.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1487xa4df9306[IClipper.ClipType.UNION.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1487xa4df9306[IClipper.ClipType.DIFFERENCE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1487xa4df9306[IClipper.ClipType.XOR.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            int[] iArr2 = new int[IClipper.PolyFillType.values().length];
            f1488x682d75a5 = iArr2;
            try {
                iArr2[IClipper.PolyFillType.EVEN_ODD.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1488x682d75a5[IClipper.PolyFillType.NON_ZERO.ordinal()] = 2;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f1488x682d75a5[IClipper.PolyFillType.POSITIVE.ordinal()] = 3;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public boolean isEvenOddAltFillType(IClipper.PolyFillType clipFillType, IClipper.PolyFillType subjFillType) {
        if (this.polyTyp == IClipper.PolyType.SUBJECT) {
            if (clipFillType == IClipper.PolyFillType.EVEN_ODD) {
                return true;
            }
            return false;
        } else if (subjFillType == IClipper.PolyFillType.EVEN_ODD) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isEvenOddFillType(IClipper.PolyFillType clipFillType, IClipper.PolyFillType subjFillType) {
        if (this.polyTyp == IClipper.PolyType.SUBJECT) {
            if (subjFillType == IClipper.PolyFillType.EVEN_ODD) {
                return true;
            }
            return false;
        } else if (clipFillType == IClipper.PolyFillType.EVEN_ODD) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isHorizontal() {
        return this.delta.getY() == 0;
    }

    public boolean isIntermediate(double y) {
        return ((double) this.top.getY()) == y && this.nextInLML != null;
    }

    public boolean isMaxima(double Y) {
        return ((double) this.top.getY()) == Y && this.nextInLML == null;
    }

    public void reverseHorizontal() {
        long temp = this.top.getX();
        this.top.setX(Long.valueOf(this.bot.getX()));
        this.bot.setX(Long.valueOf(temp));
        long temp2 = this.top.getZ();
        this.top.setZ(Long.valueOf(this.bot.getZ()));
        this.bot.setZ(Long.valueOf(temp2));
    }

    public void setBot(Point.LongPoint bot2) {
        this.bot.set(bot2);
    }

    public void setCurrent(Point.LongPoint current2) {
        this.current.set(current2);
    }

    public void setTop(Point.LongPoint top2) {
        this.top.set(top2);
    }

    public String toString() {
        return "TEdge [Bot=" + this.bot + ", Curr=" + this.current + ", Top=" + this.top + ", Delta=" + this.delta + ", Dx=" + this.deltaX + ", PolyTyp=" + this.polyTyp + ", Side=" + this.side + ", WindDelta=" + this.windDelta + ", WindCnt=" + this.windCnt + ", WindCnt2=" + this.windCnt2 + ", OutIdx=" + this.outIdx + ", Next=" + this.next + ", Prev=" + this.prev + ", NextInLML=" + this.nextInLML + ", NextInAEL=" + this.nextInAEL + ", PrevInAEL=" + this.prevInAEL + ", NextInSEL=" + this.nextInSEL + ", PrevInSEL=" + this.prevInSEL + "]";
    }

    public void updateDeltaX() {
        this.delta.setX(Long.valueOf(this.top.getX() - this.bot.getX()));
        this.delta.setY(Long.valueOf(this.top.getY() - this.bot.getY()));
        if (this.delta.getY() == 0) {
            this.deltaX = HORIZONTAL;
            return;
        }
        double x = (double) this.delta.getX();
        double y = (double) this.delta.getY();
        Double.isNaN(x);
        Double.isNaN(y);
        this.deltaX = x / y;
    }
}
