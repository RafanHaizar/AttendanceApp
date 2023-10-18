package com.itextpdf.svg.renderers.path.impl;

import com.itextpdf.kernel.geom.Point;
import com.itextpdf.svg.utils.SvgCssUtils;

public class HorizontalLineTo extends LineTo {
    static final int ARGUMENT_SIZE = 1;

    public HorizontalLineTo() {
        this(false);
    }

    public HorizontalLineTo(boolean relative) {
        super(relative);
    }

    public void setCoordinates(String[] inputCoordinates, Point startPoint) {
        String[] normalizedCoords = new String[2];
        normalizedCoords[0] = inputCoordinates[0];
        normalizedCoords[1] = isRelative() ? "0" : SvgCssUtils.convertDoubleToString(startPoint.getY());
        super.setCoordinates(normalizedCoords, startPoint);
    }
}
