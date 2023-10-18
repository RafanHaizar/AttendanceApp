package com.itextpdf.svg.utils;

import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.geom.Rectangle;

public class TextRectangle extends Rectangle {
    private static final long serialVersionUID = -1263921426258495543L;
    private float textBaseLineYCoordinate;

    public TextRectangle(float x, float y, float width, float height, float textBaseLineYCoordinate2) {
        super(x, y, width, height);
        this.textBaseLineYCoordinate = textBaseLineYCoordinate2;
    }

    public Point getTextBaseLineRightPoint() {
        return new Point((double) getRight(), (double) this.textBaseLineYCoordinate);
    }
}
