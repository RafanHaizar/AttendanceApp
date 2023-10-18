package com.itextpdf.svg.renderers.impl;

import com.itextpdf.kernel.geom.Point;
import com.itextpdf.svg.renderers.SvgDrawContext;
import com.itextpdf.svg.utils.TextRectangle;

@Deprecated
public interface ISvgTextNodeHelper {
    TextRectangle getTextRectangle(SvgDrawContext svgDrawContext, Point point);
}
