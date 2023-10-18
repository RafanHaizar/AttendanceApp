package com.itextpdf.svg.renderers.impl;

import com.itextpdf.kernel.geom.Point;
import com.itextpdf.svg.renderers.IMarkerCapable;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;

public class PolygonSvgNodeRenderer extends PolylineSvgNodeRenderer implements IMarkerCapable {
    /* access modifiers changed from: protected */
    public void setPoints(String pointsAttribute) {
        super.setPoints(pointsAttribute);
        connectPoints();
    }

    private void connectPoints() {
        if (this.points.size() >= 2) {
            Point start = (Point) this.points.get(0);
            Point end = (Point) this.points.get(this.points.size() - 1);
            if (Double.compare(start.f1280x, end.f1280x) != 0 || Double.compare(start.f1281y, end.f1281y) != 0) {
                this.points.add(new Point(start.f1280x, start.f1281y));
            }
        }
    }

    public ISvgNodeRenderer createDeepCopy() {
        PolygonSvgNodeRenderer copy = new PolygonSvgNodeRenderer();
        deepCopyAttributesAndStyles(copy);
        return copy;
    }
}
