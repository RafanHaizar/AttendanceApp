package com.itextpdf.svg.renderers.path.impl;

import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.svg.renderers.path.IPathShape;
import java.util.Map;

public abstract class AbstractPathShape implements IPathShape {
    protected String[] coordinates;
    protected final IOperatorConverter copier;
    protected Map<String, String> properties;
    protected boolean relative;

    public AbstractPathShape() {
        this(false);
    }

    public AbstractPathShape(boolean relative2) {
        this(relative2, new DefaultOperatorConverter());
    }

    public AbstractPathShape(boolean relative2, IOperatorConverter copier2) {
        this.relative = relative2;
        this.copier = copier2;
    }

    public boolean isRelative() {
        return this.relative;
    }

    /* access modifiers changed from: protected */
    public Point createPoint(String coordX, String coordY) {
        return new Point(CssUtils.parseDouble(coordX).doubleValue(), CssUtils.parseDouble(coordY).doubleValue());
    }

    public Point getEndingPoint() {
        String[] strArr = this.coordinates;
        return createPoint(strArr[strArr.length - 2], strArr[strArr.length - 1]);
    }

    public Rectangle getPathShapeRectangle(Point lastPoint) {
        return new Rectangle((float) CssUtils.convertPxToPts(getEndingPoint().getX()), (float) CssUtils.convertPxToPts(getEndingPoint().getY()), 0.0f, 0.0f);
    }
}
