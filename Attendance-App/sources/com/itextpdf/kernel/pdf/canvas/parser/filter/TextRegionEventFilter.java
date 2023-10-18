package com.itextpdf.kernel.pdf.canvas.parser.filter;

import com.itextpdf.kernel.geom.LineSegment;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.geom.Vector;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;

public class TextRegionEventFilter implements IEventFilter {
    private final Rectangle filterRect;

    public TextRegionEventFilter(Rectangle filterRect2) {
        this.filterRect = filterRect2;
    }

    public boolean accept(IEventData data, EventType type) {
        if (!type.equals(EventType.RENDER_TEXT)) {
            return false;
        }
        LineSegment segment = ((TextRenderInfo) data).getBaseline();
        Vector startPoint = segment.getStartPoint();
        Vector endPoint = segment.getEndPoint();
        float x1 = startPoint.get(0);
        float y1 = startPoint.get(1);
        float x2 = endPoint.get(0);
        float y2 = endPoint.get(1);
        Rectangle rectangle = this.filterRect;
        if (rectangle == null || rectangle.intersectsLine(x1, y1, x2, y2)) {
            return true;
        }
        return false;
    }
}
