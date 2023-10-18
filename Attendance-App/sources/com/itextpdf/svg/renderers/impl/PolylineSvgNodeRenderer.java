package com.itextpdf.svg.renderers.impl;

import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.geom.Vector;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.svg.MarkerVertexType;
import com.itextpdf.svg.SvgConstants;
import com.itextpdf.svg.exceptions.SvgLogMessageConstant;
import com.itextpdf.svg.exceptions.SvgProcessingException;
import com.itextpdf.svg.renderers.IMarkerCapable;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.SvgDrawContext;
import com.itextpdf.svg.utils.SvgCoordinateUtils;
import com.itextpdf.svg.utils.SvgCssUtils;
import java.util.ArrayList;
import java.util.List;

public class PolylineSvgNodeRenderer extends AbstractSvgNodeRenderer implements IMarkerCapable {
    protected List<Point> points = new ArrayList();

    /* access modifiers changed from: protected */
    public List<Point> getPoints() {
        return this.points;
    }

    /* access modifiers changed from: protected */
    public void setPoints(String pointsAttribute) {
        if (pointsAttribute != null) {
            List<String> points2 = SvgCssUtils.splitValueList(pointsAttribute);
            if (points2.size() % 2 == 0) {
                this.points.clear();
                for (int i = 0; i < points2.size(); i += 2) {
                    this.points.add(new Point((double) CssUtils.parseAbsoluteLength(points2.get(i)), (double) CssUtils.parseAbsoluteLength(points2.get(i + 1))));
                }
                return;
            }
            throw new SvgProcessingException(SvgLogMessageConstant.POINTS_ATTRIBUTE_INVALID_LIST).setMessageParams(pointsAttribute);
        }
    }

    /* access modifiers changed from: protected */
    public Rectangle getObjectBoundingBox(SvgDrawContext context) {
        setPoints(getAttribute(SvgConstants.Attributes.POINTS));
        if (this.points.size() <= 1) {
            return super.getObjectBoundingBox(context);
        }
        Point firstPoint = this.points.get(0);
        double minX = firstPoint.getX();
        double minY = firstPoint.getY();
        double maxX = minX;
        double maxY = minY;
        for (int i = 1; i < this.points.size(); i++) {
            Point current = this.points.get(i);
            double currentX = current.getX();
            minX = Math.min(minX, currentX);
            maxX = Math.max(maxX, currentX);
            double currentY = current.getY();
            minY = Math.min(minY, currentY);
            maxY = Math.max(maxY, currentY);
        }
        Point point = firstPoint;
        double d = minX;
        return new Rectangle((float) minX, (float) minY, (float) (maxX - minX), (float) (maxY - minY));
    }

    /* access modifiers changed from: protected */
    public void doDraw(SvgDrawContext context) {
        setPoints(this.attributesAndStyles.containsKey(SvgConstants.Attributes.POINTS) ? (String) this.attributesAndStyles.get(SvgConstants.Attributes.POINTS) : null);
        PdfCanvas canvas = context.getCurrentCanvas();
        canvas.writeLiteral("% polyline\n");
        if (this.points.size() > 1) {
            Point currentPoint = this.points.get(0);
            canvas.moveTo(currentPoint.getX(), currentPoint.getY());
            for (int x = 1; x < this.points.size(); x++) {
                Point currentPoint2 = this.points.get(x);
                canvas.lineTo(currentPoint2.getX(), currentPoint2.getY());
            }
        }
    }

    public ISvgNodeRenderer createDeepCopy() {
        PolylineSvgNodeRenderer copy = new PolylineSvgNodeRenderer();
        deepCopyAttributesAndStyles(copy);
        return copy;
    }

    public void drawMarker(SvgDrawContext context, MarkerVertexType markerVertexType) {
        Point point = null;
        if (MarkerVertexType.MARKER_START.equals(markerVertexType)) {
            point = this.points.get(0);
        } else if (MarkerVertexType.MARKER_END.equals(markerVertexType)) {
            List<Point> list = this.points;
            point = list.get(list.size() - 1);
        }
        if (point != null) {
            MarkerSvgNodeRenderer.drawMarker(context, SvgCssUtils.convertDoubleToString(CssUtils.convertPtsToPx(point.f1280x)), SvgCssUtils.convertDoubleToString(CssUtils.convertPtsToPx(point.f1281y)), markerVertexType, this);
        }
    }

    public double getAutoOrientAngle(MarkerSvgNodeRenderer marker, boolean reverse) {
        if (this.points.size() <= 1) {
            return 0.0d;
        }
        Vector v = new Vector(0.0f, 0.0f, 0.0f);
        if (SvgConstants.Attributes.MARKER_END.equals(marker.attributesAndStyles.get(SvgConstants.Tags.MARKER))) {
            List<Point> list = this.points;
            Point lastPoint = list.get(list.size() - 1);
            List<Point> list2 = this.points;
            Point secondToLastPoint = list2.get(list2.size() - 2);
            v = new Vector((float) (lastPoint.getX() - secondToLastPoint.getX()), (float) (lastPoint.getY() - secondToLastPoint.getY()), 0.0f);
        } else if (SvgConstants.Attributes.MARKER_START.equals(marker.attributesAndStyles.get(SvgConstants.Tags.MARKER))) {
            Point firstPoint = this.points.get(0);
            Point secondPoint = this.points.get(1);
            v = new Vector((float) (secondPoint.getX() - firstPoint.getX()), (float) (secondPoint.getY() - firstPoint.getY()), 0.0f);
        }
        double rotAngle = SvgCoordinateUtils.calculateAngleBetweenTwoVectors(new Vector(1.0f, 0.0f, 0.0f), v);
        return (v.get(1) < 0.0f || reverse) ? -1.0d * rotAngle : rotAngle;
    }
}
