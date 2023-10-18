package com.itextpdf.svg.renderers.path.impl;

import com.itextpdf.kernel.geom.Point;
import com.itextpdf.svg.utils.SvgCssUtils;

public class VerticalLineTo extends LineTo {
    static final int ARGUMENT_SIZE = 1;

    public VerticalLineTo() {
        this(false);
    }

    public VerticalLineTo(boolean relative) {
        super(relative);
    }

    public void setCoordinates(String[] inputCoordinates, Point startPoint) {
        String[] normalizedCoords = new String[2];
        normalizedCoords[0] = isRelative() ? "0" : SvgCssUtils.convertDoubleToString(startPoint.getX());
        normalizedCoords[1] = inputCoordinates[0];
        super.setCoordinates(normalizedCoords, startPoint);
    }
}
