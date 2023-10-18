package com.itextpdf.kernel.pdf.canvas.parser.clipper;

import com.itextpdf.kernel.pdf.canvas.parser.clipper.Point;
import com.itextpdf.kernel.pdf.canvas.parser.clipper.PolyNode;
import java.util.ArrayList;
import java.util.Iterator;

public class Paths extends ArrayList<Path> {
    private static final long serialVersionUID = 1910552127810480852L;

    public static Paths closedPathsFromPolyTree(PolyTree polytree) {
        Paths result = new Paths();
        result.addPolyNode(polytree, PolyNode.NodeType.CLOSED);
        return result;
    }

    public static Paths makePolyTreeToPaths(PolyTree polytree) {
        Paths result = new Paths();
        result.addPolyNode(polytree, PolyNode.NodeType.ANY);
        return result;
    }

    public static Paths openPathsFromPolyTree(PolyTree polytree) {
        Paths result = new Paths();
        for (PolyNode c : polytree.getChilds()) {
            if (c.isOpen()) {
                result.add(c.getPolygon());
            }
        }
        return result;
    }

    public Paths() {
    }

    public Paths(int initialCapacity) {
        super(initialCapacity);
    }

    /* renamed from: com.itextpdf.kernel.pdf.canvas.parser.clipper.Paths$1 */
    static /* synthetic */ class C14361 {

        /* renamed from: $SwitchMap$com$itextpdf$kernel$pdf$canvas$parser$clipper$PolyNode$NodeType */
        static final /* synthetic */ int[] f1491x16f7cf2e;

        static {
            int[] iArr = new int[PolyNode.NodeType.values().length];
            f1491x16f7cf2e = iArr;
            try {
                iArr[PolyNode.NodeType.OPEN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1491x16f7cf2e[PolyNode.NodeType.CLOSED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public void addPolyNode(PolyNode polynode, PolyNode.NodeType nt) {
        boolean match = true;
        switch (C14361.f1491x16f7cf2e[nt.ordinal()]) {
            case 1:
                return;
            case 2:
                match = !polynode.isOpen();
                break;
        }
        if (polynode.getPolygon().size() > 0 && match) {
            add(polynode.getPolygon());
        }
        for (PolyNode pn : polynode.getChilds()) {
            addPolyNode(pn, nt);
        }
    }

    public Paths cleanPolygons() {
        return cleanPolygons(1.415d);
    }

    public Paths cleanPolygons(double distance) {
        Paths result = new Paths(size());
        for (int i = 0; i < size(); i++) {
            result.add(((Path) get(i)).cleanPolygon(distance));
        }
        return result;
    }

    public LongRect getBounds() {
        int i = 0;
        int cnt = size();
        LongRect result = new LongRect();
        while (i < cnt && ((Path) get(i)).isEmpty()) {
            i++;
        }
        if (i == cnt) {
            return result;
        }
        result.left = ((Point.LongPoint) ((Path) get(i)).get(0)).getX();
        result.right = result.left;
        result.top = ((Point.LongPoint) ((Path) get(i)).get(0)).getY();
        result.bottom = result.top;
        while (i < cnt) {
            for (int j = 0; j < ((Path) get(i)).size(); j++) {
                if (((Point.LongPoint) ((Path) get(i)).get(j)).getX() < result.left) {
                    result.left = ((Point.LongPoint) ((Path) get(i)).get(j)).getX();
                } else if (((Point.LongPoint) ((Path) get(i)).get(j)).getX() > result.right) {
                    result.right = ((Point.LongPoint) ((Path) get(i)).get(j)).getX();
                }
                if (((Point.LongPoint) ((Path) get(i)).get(j)).getY() < result.top) {
                    result.top = ((Point.LongPoint) ((Path) get(i)).get(j)).getY();
                } else if (((Point.LongPoint) ((Path) get(i)).get(j)).getY() > result.bottom) {
                    result.bottom = ((Point.LongPoint) ((Path) get(i)).get(j)).getY();
                }
            }
            i++;
        }
        return result;
    }

    public void reversePaths() {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Path) it.next()).reverse();
        }
    }
}
