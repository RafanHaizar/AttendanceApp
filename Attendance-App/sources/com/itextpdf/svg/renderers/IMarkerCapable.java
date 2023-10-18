package com.itextpdf.svg.renderers;

import com.itextpdf.svg.MarkerVertexType;
import com.itextpdf.svg.renderers.impl.MarkerSvgNodeRenderer;

public interface IMarkerCapable {
    void drawMarker(SvgDrawContext svgDrawContext, MarkerVertexType markerVertexType);

    double getAutoOrientAngle(MarkerSvgNodeRenderer markerSvgNodeRenderer, boolean z);
}
